import React, { useEffect, useState } from "react";
import SwitchButton from "../../../../../components/buttons/SwitchButton";

export function Check({ getValue }) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  return (
    <div className="status-plan">
      <SwitchButton
        value={value}
        onChange={() => { }}
      />
    </div>
  )
}
