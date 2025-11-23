import React from "react";
import { Tooltip } from "../../../../../components/Tooltip";

export function ActionCell({ Component, tooltipText, tooltipPlacement }) {
  return (
    <>
      <Tooltip placement={tooltipPlacement} text={tooltipText}>
        <Component />
      </Tooltip>
    </>
  );
}
