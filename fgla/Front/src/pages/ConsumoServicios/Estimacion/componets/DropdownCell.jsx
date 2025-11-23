import React, { useEffect, useState } from "react";
import Select from "../../../../components/formInputs/Select";
export function DropdownCell({
  getValue,
  row,
  column,
  table,
  render,
  keyValue,
  keyTextValue,
  options
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const { updateData } = table.options.meta;

  

  const onBlur = () => {
    table.options.meta?.updateData(
      row.index,
      column.id,
      value,
      row.getParentRow()?.index
    );
  };

  const onHandleChange = (e) => {
    updateData(row.index, column.id, e.target.value);
    setValue(e.target.value);
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  if (!row.getIsSelected()) {
    return <>{value} </>;
  } else {
    return (
      <>
        <Select
          value={value}
          onChange={onHandleChange}
          onBlur={onBlur}
          options={options}
          keyValue={keyValue}
          keyTextValue={keyTextValue}
          className="input-table"
        />
      </>
    );
  }
}
