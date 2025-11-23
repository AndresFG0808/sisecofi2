import React, { useEffect, useState } from "react";
import { TextFieldIcon } from "../../../../../../../../components/formInputs";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import {
  FormatMoney,
  filtrarNumeros,
} from "../../../../../../../../functions/utils";

export function InputPrecio({
  row,
  column,
  table,
  getValue,
  name,
  decimals = 2,
  isDetalle,
  convenio = [],
}) {
  const currentValue = getValue() ?? 0;
  const [value, setValue] = useState(currentValue);

  const isDisabled =
    convenio.length > 0
      ? convenio.every(
          (cvn) => cvn.includes("tiempo") || cvn.includes("administrativo")
        )
      : false;

  useEffect(() => {
    setValue(currentValue);
  }, [currentValue]);

  const handleChange = (event) => {
    const { value } = event.target;
    setValue(filtrarNumeros(value));
  };

  const handleBlur = (event) => {
    const { value } = event.target;
    if (value) {
      const formatMoney = FormatMoney(value, decimals);
      setValue(formatMoney);
      table.options.meta.updateData(row.index, column.id, value);
    }
  };
  return (
    <>
      {(row.getIsSelected() || row?.original?.primeraVez === true) &&
      !isDetalle &&
      !isDisabled ? (
        <TextFieldIcon
          name={`${name}-${row.original.UUID}`}
          startIcon={faDollarSign}
          value={value}
          onChange={handleChange}
          onBlur={handleBlur}
        />
      ) : (
        <>${FormatMoney(getValue(), decimals) || 0}</>
      )}
    </>
  );
}
