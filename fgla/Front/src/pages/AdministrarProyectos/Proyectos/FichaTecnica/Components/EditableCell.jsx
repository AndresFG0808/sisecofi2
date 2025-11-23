import React, { useEffect, useState } from "react";
import Select from "../../../../../components/formInputs/Select";
import { Col, Row } from "react-bootstrap";

export function EditableCell({
  getValue,
  row,
  column,
  table,
  name = "",
  options = [],
  keyValue = "",
  keyTextValue = "",
  callback,
  useFilter = false,
  filterProperty = null,
  displayProperty,
  errors,
}) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const [valid, setValid] = useState(true);
  const { original } = row;
  const { updateData, updateSubRows } = table.options.meta;
  const isIdValid = (id) => !isNaN(parseInt(id));

  useEffect(() => {
    setValue(currentValue);
  }, [currentValue]);

  const onChange = (e) => {
    setValue(e.target.value);
    if (useFilter) {
      const display = options?.find(
        (option) => option?.[keyValue] === parseInt(e.target.value)
      );
      updateSubRows(row.index, {
        ...original,
        [column.id]: e.target.value,
        [displayProperty]: display?.[keyTextValue],
      });
    } else {
      updateData(row.index, column.id, e.target.value);
    }
    setValid(isIdValid(e.target.value));
    if (callback) callback(e);
  };
  const onBlur = (e) => {
    setValid(isIdValid(e.target.value));
  };

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          name={name + row.id}
          value={value}
          onChange={onChange}
          options={options}
          keyValue={keyValue}
          keyTextValue={keyTextValue}
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
          helperText={
            original.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
          onBlur={onBlur}
          keyStatus="estatus"
          hideDisabledOptions={true}
        />
      ) : (
        original?.[displayProperty]
      )}
    </>
  );
}
