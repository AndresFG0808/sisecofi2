import React, { useEffect, useState } from "react";
import { useDebounce } from "../../../../../../../../hooks/useDebounce";
import { TextFieldIcon } from "../../../../../../../../components/formInputs";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";

const updateColumns = (data, monto) => {
  if (!data) return [];
  return data?.map((row) => {
    if (row.tipoConsumo === "Volumetría") {
      return { ...row, incrementoMonto: monto };
    } else {
      return row;
    }
  });
};

export function InputMonto({
  row,
  column,
  table,
  getValue,
  name,
  decimals = 2,
  options,
  isDetalle,
  disabledAlways = false,
  convenio = [],
}) {
  const [value, setValue] = useState(getValue());
  const [focus, setFocus] = useState(false);
  const debouncedValue = useDebounce(value, 10);

  useEffect(() => {
    if (row.getValue("tipoConsumo") === "Volumetría") {
      const result = updateColumns(table.options.meta.getDataTable(), value);
      table.options.meta.revertData(result);
    }
  }, [debouncedValue]);
  const handleChange = (event) => {
    setValue(event.target.value);
  };
  console.log(getValue(), row.id);
  const handleBlur = (event) => {
    setFocus(false);
  };
  const handleFocus = (event) => {
    setValue(getValue());
    setFocus(true);
  };

  return (
    <>
      <TextFieldIcon
        name={`${name}-${row.original.UUID}`}
        startIcon={faDollarSign}
        onChange={handleChange}
        onBlur={handleBlur}
        value={focus ? value : getValue()}
        onFocus={handleFocus}
      />
    </>
  );
}
