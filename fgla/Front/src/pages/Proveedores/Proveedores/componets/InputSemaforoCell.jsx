import React, { useEffect, useState } from "react";
import { Select } from "../../../../components/formInputs";
import { getData } from '../../../../functions/api';

export function InputSemaforoCell({
  getValue,
  row,
  column,
  table,
  render,
  estatusOptions,
  keyStatus,
  hideDisabledOptions,
  cellErrors,
}) {
  
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  const onBlur = () => {
    table.options.meta?.updateData(
      row.index,
      column.id,
      value,
      row.getParentRow()?.index
    );
  };

  const onHandleChange = (e) => {
    setValue(e.target.value);
  };

  useEffect(() => {
    setValue(initialValue);
    console.log(estatusOptions)
  }, [initialValue]);

  const error = cellErrors[column.id];

  const inputTableStyle = {
    width: "100%",
  };

  const errorBorderStyle = {
    border: "1px solid red",
  };

  if (!row.getIsSelected()) {
    return <>{render && render()} </>;
  } else {
    return (
      <div style={inputTableStyle}>
        <Select
          value={value}
          onChange={onHandleChange}
          onBlur={onBlur}
          options={estatusOptions}
          keyValue="idEstatusTituloServicio"
          keyTextValue="semaforoEstatus"
          className={error && (error ? 'is-invalid' : '')}
          helperText={error ? error : ''}
          keyStatus={keyStatus}
          hideDisabledOptions={hideDisabledOptions}
          style={error ? errorBorderStyle : {}}
        />
      </div>
    );
  }
}