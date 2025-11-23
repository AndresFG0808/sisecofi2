import React, { useEffect, useState } from "react";
import { TextFieldIcon } from "../../../../../../../../components/formInputs";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import {
  FormatMoney,
  filtrarNumeros,
  filtrarNumerosNegativos,
  formatCurrency,
} from "../../../../../../../../functions/utils";
import { useDebounce } from "../../../../../../../../hooks/useDebounce";
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}
const updateColumns = (data, index, newRow) => {
  if (!data) return [];
  return data?.map((row, i) => {
    if (i === index) {
      return newRow;
    }
    if (newRow?.tipoConsumo?.toLowerCase()?.includes("volumetría")) {
      return row;
    }
    if (
      row?.servicioBase?.idGrupoServicio ===
      newRow?.servicioBase?.idGrupoServicio
    ) {
      return {
        ...row,
        compensacionServicios: newRow.compensacionServicios,
        compensacionMonto: newRow.compensacionMonto,  
        incrementoServicios: newRow.incrementoServicios,
        incrementoMonto: newRow.incrementoMonto,
        numeroTotalServicios: newRow.numeroTotalServicios,
        montoMaximoTotal: newRow.montoMaximoTotal,
      };
    }
    
    return row;
  });
};
export function InputCurrencyCell({
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
  acceptNegative,
}) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const [focus, setFocus] = useState(false);
  const debouncedValue = useDebounce(value, 300);
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

  useEffect(() => {
    if (options.sideEffect) {
      const val = safeParseFloat(value);
      const result = options.sideEffect(val);
      const newRow = {
        ...original,
        [column.id]: safeParseFloat(value),
        [options.changedKey]: isFinite(result) ? safeParseFloat(result) : 0,
      };
      const newTable = updateColumns(
        table.options.meta.getDataTable(),
        row.index,
        newRow
      );
      table.options.meta.setFullTable(newTable);
    } else {
      const newRow = {
        ...original,
        [column.id]: safeParseFloat(value),
      };
      const newTable = updateColumns(
        table.options.meta.getDataTable(),
        row.index,
        newRow
      );
      table.options.meta.setFullTable(newTable);
    }
  }, [debouncedValue]);

  const handleChange = (event) => {
    const { value } = event.target;
    const filteredValue = acceptNegative
      ? filtrarNumerosNegativos(value)
      : filtrarNumeros(value);
    setValue(filteredValue);
  };

  const handleBlur = (event) => {
    setFocus(false);
  };

  const handleFocus = (event) => {
    setValue(currentValue);
    setFocus(true);
  };

  return (
    <>
      {(row.getIsSelected() || original?.primeraVez === true) && !isDetalle ? (
        <TextFieldIcon
          name={`${name}-${row.original.UUID}`}
          startIcon={faDollarSign}
          value={
            !isTiempoOrAdmon ||
            !isVolumetria ||
            (isBolsa && isAlcance && options.activationKey === "alcance") ||
            (isBolsa && isMonto && options.activationKey === "monto")
              ? focus
                ? value
                : getValue()
              : formatCurrency(options?.calculateValue(), decimals)
          }
          onChange={handleChange}
          onBlur={handleBlur}
          disabled={
            isTiempoOrAdmon ||
            isVolumetria ||
            (!isAlcance && options.activationKey === "alcance") ||
            (!isMonto && options.activationKey === "monto") ||
            disabledAlways
          }
          onFocus={handleFocus}
        />
      ) : (
        <>${getValue() ? FormatMoney(currentValue, decimals) : "0"}</>
      )}
    </>
  );
}
