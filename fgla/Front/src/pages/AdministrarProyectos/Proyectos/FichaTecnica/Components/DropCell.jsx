import React from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../../components/Tooltip";

export function DropCell({ row, table, column, getValue }) {
  const { removeRow } = table.options.meta;

  const onDropRow = () => {
    removeRow(row.index);
  };
  return (
    <>
      <Tooltip text={"Eliminar / Cancelar"} placement="top">
        <span>
          <IconButton type={"drop"} iconSize="1x" onClick={onDropRow} />
        </span>
      </Tooltip>
    </>
  );
}
