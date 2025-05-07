import * as React from "react";
import ReactDOM from "react-dom";

type Props = {
  mode: string;
  minLines: number;
  maxLines: number;
  readOnly: boolean;
  onChange?: (value: string) => void;
  className: string;
  id: string;
  content: React.ReactNode;
};

class AceEditor extends React.Component<Props> {
  editor: any = null;

  componentDidMount() {
    const component = this;

    const node = ReactDOM.findDOMNode(component.refs.editor);
    try {
      const editor = ace.edit(node);
      editor.setTheme("ace/theme/xcode");
      editor.getSession().setMode("ace/mode/" + component.props.mode);
      editor.setShowPrintMargin(false);
      editor.setOptions({ minLines: component.props.minLines });
      editor.setOptions({ maxLines: component.props.maxLines });
      editor.setReadOnly(component.props.readOnly);

      editor.setValue(component.props.content || "", 1);

      editor.getSession().on("change", function () {
        component.props.onChange?.(editor.getSession().getValue());
      });
    } catch (error) {
      Loggerhead.error(
        "Failed to initialize AceEditor, please check if `ace-editor/ace.js` and related dependencies have been imported in your Jade/JSP template"
      );
      Loggerhead.error(error);
    }
  }

  componentDidUpdate(prevProps: Props) {
    if (prevProps.content !== this.props.content && this.editor) {
      this.editor.setValue(this.props.content || "", 1);
    }
  }

  render() {
    return <div ref="editor" className={this.props.className} id={this.props.id} />;
  }
}

export { AceEditor };
