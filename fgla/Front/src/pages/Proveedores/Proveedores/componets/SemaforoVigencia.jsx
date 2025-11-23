import React, { useEffect, useState } from "react";

export function SemaforoVigencia({
  getValue,
  row,
  column,
  table,
  render,
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
  }, [initialValue]);

  return <>
    <div style={{ whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" }}>
      {render && render()} {value}
    </div>
  </>;
}
