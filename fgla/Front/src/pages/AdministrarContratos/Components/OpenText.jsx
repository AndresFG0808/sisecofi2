import React, { useEffect, useState } from "react";
import TextField from "../../../components/formInputs/TextField";
const alphanumericRegex = /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]*$/;

export function OpenText({
  row,
  column,
  table,
  getValue,
  name,
  acceptSpaces = false,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  const { original } = row;

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  const handleChange = (event) => {
    const { value } = event.target;
    if (!acceptSpaces) {
      if (alphanumericRegex.test(value)) {
        setValue(value);
      }
    } else {
      setValue(value);
    }
  };
  const handleBlur = (event) => {
    const { value } = event.target;
    table.options.meta.updateData(row.index, column.id, value);
  };

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <TextField
          name={`${name + row.original.UUID}`}
          onChange={handleChange}
          onBlur={handleBlur}
          value={value}
          helperText={
            original?.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
        />
      ) : (
        getValue()
      )}
    </>
  );
}
