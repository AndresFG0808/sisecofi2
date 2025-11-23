import React, { useEffect, useState } from "react";
import { TextField } from "../../../../components/formInputs";

export function InputEditableCell({
  getValue,
  row,
  column,
  table
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [errorsRow, setErrosRow] = useState(null);

  const onBlur = () => {
    table.options.meta?.updateData(row.index, column.id, value, row.getParentRow()?.index);
  };

  const onHandleChange = (e) => {
    setValue(e.target.value);
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  useEffect(() => {
    setErrosRow(row.original.errors || null);
  }, [row]);


  if (!row.getIsSelected()) {
    return <>{value}</>;
  } else {
    return (
      <>
        <TextField
          value={value}
          onChange={onHandleChange}
          onBlur={onBlur}
          className={errorsRow && (errorsRow[column.id] && !value ? 'is-invalid' : 'is-valid')}
          helperText={errorsRow && (errorsRow[column.id] ? errorsRow[column.id] : '')}
        />
      </>
    );
  }
}
