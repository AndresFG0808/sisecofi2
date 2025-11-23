import React, { useEffect, useState } from "react";
import { TextFieldIcon } from "../../../../../../../../components/formInputs";
import { FormatMoney } from "../../../../../../../../functions/utils";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}

export function InputMaxCurrency({ row, readOnly, getValue }) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const { original } = row;
  const isVolumetria = original?.tipoConsumo
    ?.toLowerCase()
    ?.includes("volumetría");

  useEffect(() => {
    const result = isVolumetria
      ? (safeParseFloat(original?.numeroMaximoServicios) +
          safeParseFloat(original?.compensacionServicios) +
          safeParseFloat(original?.incrementoServicios)) *
        safeParseFloat(original?.precioUnitario)
      : safeParseFloat(original?.montoMaximo) +
        safeParseFloat(original?.compensacionMonto) +
        safeParseFloat(original?.incrementoMonto);
    setValue(result);
  }, [original]);

  return (
    <>
      {(row.getIsSelected() || row.original.primeraVez === true) &&
      !readOnly ? (
        <TextFieldIcon
          disabled
          startIcon={faDollarSign}
          value={FormatMoney(value, 2) || 0}
        />
      ) : (
        <>${FormatMoney(value, 2) || 0}</>
      )}
    </>
  );
}
