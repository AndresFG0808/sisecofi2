import React, { useEffect, useState } from "react";
import { FormCheck } from "react-bootstrap";
import SwitchButton from "../../../../components/buttons/SwitchButton";

export function Check({
  getValue,
  row,
  column,
  table,
  handleChange,
  type
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  const onHandleChange = () => {
    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = { ...row.original, [column.id]: !value };
      const final = updateRowFromSubRow(row, newRow);
      table.options.meta?.updateSubRows(parentIndex, final);
    } else {
      table.options.meta?.updateData(row.index, column.id, !value);
    }
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  if (type && type === "switch") {
    return <SwitchButton
      value={value}
      onChange={onHandleChange}
      name={"SwitchButton"}
    />;
  } else {
    return (
      <FormCheck
        checked={value}
        onChange={onHandleChange}
      />
    );
  }
}

function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = bottomRow.getParentRow().original;
  updatedRow.archivos[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
