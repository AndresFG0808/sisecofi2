import React, { useEffect, useState } from "react";
import { TextFieldDate } from "../../../../components/formInputs";

export function InputDateCell({
  getValue,
  row,
  column,
  table,
  cellErrors,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [newDate, setNewDate] = useState('');

  const formatDate = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
  };

  const parseDate = (dateString) => {
    const [day, month, year] = dateString.split('/');
    return `${year}-${month}-${day}`;
  };

  const onBlur = () => {
    const formattedValue = formatDate(newDate);
    table.options.meta?.updateData(row.index, column.id, formattedValue, row.getParentRow()?.index);
  };

  const onHandleChange = (e) => {
    setNewDate(e.target.value);
    setValue(formatDate(e.target.value));
  };

  useEffect(() => {
    setValue(initialValue);
    const newDateString = parseDate(initialValue);
    setNewDate(newDateString);
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
        <TextFieldDate
          value={newDate}
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