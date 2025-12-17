import * as React from "react";
import { useEffect } from "react";

type Props = {
  children: React.ReactNode;
  top?: string;
};

export const SectionToolbar = ({ children, top }: Props) => {
  useEffect(() => {
    handleSst?.();
  }, []);

  return (
    <div className="spacewalk-section-toolbar" style={{ top: `${top}px` }}>
      {children}
    </div>
  );
};
