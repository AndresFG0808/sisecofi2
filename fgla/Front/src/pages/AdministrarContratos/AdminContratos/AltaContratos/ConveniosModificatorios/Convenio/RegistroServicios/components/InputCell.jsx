import React, { useEffect, useState } from "react";
import { TextField } from "../../../../../../../../components/formInputs";
import {
  filtrarNumeros,
  filtrarNumerosNegativos,
  formatCurrency,
} from "../../../../../../../../functions/utils";
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}

export function InputCell({
  row,
  column,
  table,
  getValue,
  name,
  isDetalle,
  options,
  disabledAlways = false,
  convenio = [],
  acceptNegative,
}) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const { original } = row;

  const isVolumetria = original?.tipoConsumo
    ?.toLowerCase()
    ?.includes("volumetría");

  const isBolsa = original?.tipoConsumo?.toLowerCase()?.includes("bolsa");

  const isTiempoOrAdmon =
    convenio.length > 0
      ? convenio.every(
          (cvn) => cvn.includes("tiempo") || cvn.includes("administrativo")
        )
      : false;
  const isMonto =
    convenio.length > 0 ? convenio.some((cvn) => cvn.includes("monto")) : false;

  const isAlcance =
    convenio.length > 0
      ? convenio.some((cvn) => cvn.includes("alcance"))
      : false;

  useEffect(() => {
    setValue(currentValue);
  }, [currentValue]);

  const handleChange = (event) => {
    const { value } = event.target;
    setValue(
      acceptNegative ? filtrarNumerosNegativos(value) : filtrarNumeros(value)
    );
  };
  const handleBlur = (event) => {
    if (options.sideEffect) {
      const val = safeParseFloat(String(value).replace(/,/g, ""));
      const result = options.sideEffect(val);
      const newRow = {
        ...original,
        [column.id]: value,
        options,
        [options.changedKey]: result,
      };
      table.options.meta.updateSubRows(row.index, newRow);
    } else {
      table.options.meta.updateData(row.index, column.id, value);
    }
  };
  return (
    <>
      {(row.getIsSelected() || original?.primeraVez === true) && !isDetalle ? (
        <TextField
          name={`${name}-${row?.original?.UUID}`}
          value={
            !isTiempoOrAdmon ||
            !isBolsa ||
            (isVolumetria &&
              isAlcance &&
              options.activationKey === "alcance") ||
            (isVolumetria && isMonto && options.activationKey === "monto")
              ? value
              : isNaN(options?.calculateValue())
              ? "0"
              : formatCurrency(options?.calculateValue(), 2)
          }
          onBlur={handleBlur}
          onChange={handleChange}
          disabled={
            isTiempoOrAdmon ||
            isBolsa ||
            (!isAlcance && options.activationKey === "alcance") ||
            (!isMonto && options.activationKey === "monto") ||
            disabledAlways
          }
        />
      ) : currentValue ? (
        formatCurrency(currentValue, 6)
      ) : (
        "0"
      )}
    </>
  );
}
