import React, { useEffect, useState } from "react";
import { TextField } from "../../../../../../../../components/formInputs";
import { formatCurrency } from "../../../../../../../../functions/utils";
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}
export function InputMaxService({ row, readOnly, getValue }) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const { original } = row;
  const isVolumetria = original?.tipoConsumo
    ?.toLowerCase()
    ?.includes("volumetría");

  useEffect(() => {
    const result = isVolumetria
      ? safeParseFloat(original?.numeroMaximoServicios) +
        safeParseFloat(original?.compensacionServicios) +
        safeParseFloat(original?.incrementoServicios)
      : (safeParseFloat(original?.montoMaximo) +
          safeParseFloat(original?.compensacionMonto) +
          safeParseFloat(original?.incrementoMonto)) /
        safeParseFloat(original?.precioUnitario);
    setValue(result);
  }, [original]);
  return (
    <>
      {(row.getIsSelected() || row.original.primeraVez === true) &&
      !readOnly ? (
        <TextField disabled value={formatCurrency(value, 6) || 0} />
      ) : (
        formatCurrency(value, 6) || 0
      )}
    </>
  );
}
