import React, { useEffect, useState } from "react";
import TextField from "../../../../../components/formInputs/TextField";

export function InputEditableCell({
  getValue,
  row,
  column,
  table,
  handleChange,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  const onBlur = () => {
    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = { ...row.original, [column.id]: value };
      const final = updateRowFromSubRow(row, newRow);
      table.options.meta?.updateSubRows(parentIndex, final);
    } else {
      table.options.meta?.updateData(row.index, column.id, value);
    }
  };

  const onHandleChange = (e) => {
    setValue(e.target.value);
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  if (!row.getIsSelected()) {
    return <>{value}</>;
  } else {
    return (
      <>
        <TextField
          value={value}
          onChange={onHandleChange}
          name={"liderProyecto"}
          onBlur={onBlur}
        />
      </>
    );
  }
}

function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = bottomRow.getParentRow().original;
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
