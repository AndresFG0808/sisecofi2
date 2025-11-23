import React, { useEffect, useState } from "react";
import { TextField } from "../../../../components/formInputs";

export function InputEditableCell({
  getValue,
  row,
  column,
  table,
  handleChange,
  cellErrors,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  const onBlur = () => {
    table.options.meta?.updateData(row.index, column.id, value, row.getParentRow()?.index);
    console.log("parent id row => ", row.getParentRow()?.index);
    console.log("row => ", row);
  };

  const onHandleChange = (e) => {
    if (column.id === "correoElectronico") {
      console.log("e => ", e.target.value);
      setValue(e.target.value.toLowerCase());
    } else if (column.id === "anio") {
      const { value } = e.target;
      if (/^\d{0,4}$/.test(value)) {
        setValue(value);
      }
    } else {
      console.log("e => ", e.target.value);
      setValue(e.target.value);
    }
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  const error = cellErrors[column.id];

  const inputTableStyle = {
    width: "100%",
  };

  const errorBorderStyle = {
    border: "1px solid red",
  };

  if (!row.getIsSelected()) {
    return <>{value}</>;
  } else {
    return (
      <div style={inputTableStyle}>
        <TextField
          value={value}
          onChange={onHandleChange}
          name={"liderProyecto"}
          onBlur={onBlur}
          className={error && (error ? 'is-invalid' : '')}
          helperText={error ? error : ''}
          style={error ? errorBorderStyle : {}}
        />
      </div>
    );
  }
}