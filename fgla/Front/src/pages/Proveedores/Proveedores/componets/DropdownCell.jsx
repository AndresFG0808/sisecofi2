import React, { useEffect, useState } from "react";
import { Select } from "../../../../components/formInputs";

export function DropdownCell({
  getValue,
  row,
  column,
  table,
  keyValue,
  keyStatus,
  keyTextValue,
  options,
  name,
  hideDisabledOptions,
  cellErrors,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(null);
  const [displayValue, setDisplayValue] = useState(getValue());
  const { updateData } = table.options.meta;

  const onHandleChange = (e) => {
    const newValue = e.target.value;
    updateData(row.index, column.id, newValue);
    setValue(newValue);
  };

  useEffect(() => {
    let obj = options.find((item) => item[keyTextValue] === initialValue || item[keyValue] === Number(initialValue));
    if (obj) {
      setValue(obj[keyValue]);
      table.options.meta?.updateData(row.index, column.id, obj[keyValue]);
      setDisplayValue(obj[keyTextValue]);
    } else {
      // Maneja el caso en el que los catálogos no concuerden
      setValue(null);
      setDisplayValue(initialValue);
    }
  }, [initialValue, options]);

  const error = cellErrors[column.id];

  const inputTableStyle = {
    width: "100%",
  };

  const errorBorderStyle = {
    border: "1px solid red",
  };

  if (!row.getIsSelected()) {
    return <>{displayValue}</>;
  } else {
    return (
      <div style={inputTableStyle}>
        <Select
          name={name}
          value={value}
          onChange={onHandleChange}
          options={options}
          keyValue={keyValue}
          keyTextValue={keyTextValue}
          keyStatus={keyStatus}
          hideDisabledOptions={hideDisabledOptions}
          className={error && (error ? 'is-invalid' : '')}
          helperText={error ? error : ''}
          style={error ? errorBorderStyle : {}}
        />
      </div>
    );
  }
}