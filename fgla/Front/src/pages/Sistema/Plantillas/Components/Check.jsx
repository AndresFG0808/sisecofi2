import React, { useEffect, useState } from "react";
import SwitchButton from "../../../../components/buttons/SwitchButton";

export function Check({
  getValue,
  row,
  onChangeStatus,
  disabled
}) {
  const initialValue = getValue();
  const { tipo, idPlantillador, idSubPlantillador } = row.original;
  const [value, setValue] = useState(initialValue);
  const showSwitch = (idPlantillador || idSubPlantillador) && (tipo === "plantilla" || tipo === "subCarpeta");

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  return (
    <>
      {
        showSwitch &&
        <SwitchButton
          value={value}
          onChange={onChangeStatus(row.original)}
          name={"SwitchButton"}
          disabled={disabled}
        />
      }
    </>
  );
}
