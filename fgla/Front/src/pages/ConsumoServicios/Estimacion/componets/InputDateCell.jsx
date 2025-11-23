import React, { useEffect, useState } from "react";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";

export function InputDateCell({
  getValue,
  row,
  column,
  table,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  const formatDate = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
  };

  const onBlur = () => {
    const formattedValue = formatDate(value);
    table.options.meta?.updateData(row.index, column.id, formattedValue, row.getParentRow()?.index);
    console.log("parent id row => ", row.getParentRow()?.index);
    console.log("row => ", row);
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
        <TextFieldDate
          value={value}
          onChange={onHandleChange}
          name={"liderProyecto"}
          onBlur={onBlur}
          className="input-table"
        />
      </>
    );
  }
}
