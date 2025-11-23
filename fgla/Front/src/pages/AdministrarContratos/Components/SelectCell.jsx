import React from "react";
import Select from "../../../components/formInputs/Select";

export function SelectCell({
  row,
  table,
  column,
  name,
  getValue,
  options,
  keyValue,
  keyTextValue,
  displayValue,
  defaultOptionText,
  disabled,
  isUser = false,
  onChangeExtra,
}) {
  const value = getValue();
  const { original } = row;
  const { meta } = table.options;

  const handleChange = (event) => {
    const newValue = event.target.value;

    const newRow = {
      ...row.original,
      [column.id]: newValue,
    };

    // Si se debe actualizar también el display
    if (displayValue) {
      const selectedOption = options?.find(
        (option) => `${option[keyValue]}` === `${newValue}`
      );

      newRow[displayValue] = selectedOption?.[keyTextValue] || "";
      meta.updateSubRows(row.index, newRow);
    } else {
      meta.updateData(row.index, column.id, newValue);
    }

    if (onChangeExtra) {
      onChangeExtra(newRow); // fila ya actualizada
    }
  };

  const handleBlur = () => {};

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          disabled={disabled}
          name={`${name + row.id}`}
          onChange={handleChange}
          onBlur={handleBlur}
          value={value}
          options={options}
          keyValue={keyValue}
          keyTextValue={keyTextValue}
          defaultOptionText={defaultOptionText}
          helperText={
            original?.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
        />
      ) : displayValue ? (
        original?.[displayValue]
      ) : (
        getValue()
      )}
    </>
  );
}
