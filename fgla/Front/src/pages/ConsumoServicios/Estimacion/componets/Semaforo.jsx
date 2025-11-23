import React, { useEffect, useState } from "react";
import TextField from "../../../../components/formInputs/TextField";

export function Semaforo({
  getValue,
  row,
  column,
  table,
  render,
}) {

  const initialValue = getValue() ?? 0;
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
  }, [initialValue]);

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <span>{value}</span>
      {render && render()}
    </div>
  );
}

