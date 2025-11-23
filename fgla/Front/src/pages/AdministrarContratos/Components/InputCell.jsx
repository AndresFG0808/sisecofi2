import React, { useEffect, useState } from "react";
import TextField from "../../../components/formInputs/TextField";
import {
  formatCurrency,
  filtrarNumeros,
  FormatMoney,
  formatCurrencyString,
} from "../../../functions/utils";
import { useDebounce } from "../../../hooks/useDebounce";

const updateColumns = (data, index, newRow) => {
  if (!data) return [];
  return data?.map((row, i) => {
    if (i === index) {
      return newRow;
    } else if (
      row.tipoConsumo === "Bolsa" &&
      parseInt(row.grupo) === parseInt(newRow.grupo)
    ) {
      return {
        ...row,
        cantidadServiciosMinima: newRow.cantidadServiciosMinima,
        cantidadServicios: newRow.cantidadServicios,
        montoMinimo: newRow.montoMinimo,
        montoMaximo: newRow.montoMaximo,
      };
    } else {
      return row;
    }
  });
};
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
  decimals = 6,
  displayDecimals = 2,
  isBolsa = false,
  nonDependent = true,
  options,
  isPrecioUnitario,
}) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const [focus, setFocus] = useState(false);
  const debouncedValue = useDebounce(value, 10);
  const { original } = row;
  const tipoConsumo = original?.tipoConsumo?.toLowerCase();
  /* const isEditingNewRow = table.options.meta.getDataTable()?.some((row) => row.isNewRow);
  const isEditingRow = table?.getIsSomeRowsSelected() || isEditingNewRow; */

  useEffect(() => {
    if (isPrecioUnitario) {
      const formatMoney = formatCurrencyString(value, decimals, 2);
      table.options.meta.updateData(row.index, column.id, formatMoney);
    } else if (tipoConsumo !== "bolsa") {
      const formatMoney = formatCurrency(safeParseFloat(value), decimals, 2);
      table.options.meta.updateData(row.index, column.id, formatMoney);
    } else {
      const formatMoney = formatCurrency(value, decimals);
      const newRow = {
        ...original,
        [column.id]: formatMoney,
      };
      const newTable = updateColumns(
        table.options.meta.getDataTable(),
        row.index,
        newRow
      );
      table.options.meta.setFullTable(newTable);
    }

    return () => { };
  }, [debouncedValue]);

  useEffect(() => {
    setValue(currentValue);
  }, [currentValue]);

  const handleChange = (event) => {
    const { value } = event.target;
    setValue(filtrarNumeros(value));
  };
  const handleBlur = (event) => {
    setValue(currentValue);
    setFocus(false);
  };

  const handleFocus = (event) => {
    setFocus(true);
  };
  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <TextField
          name={`${name + row.original.UUID}`}
          onChange={handleChange}
          onBlur={handleBlur}
          value={
            options?.valueOperation &&
              ((tipoConsumo === "volumetría" && !isBolsa) ||
                (tipoConsumo === "bolsa" && isBolsa)) &&
              !nonDependent
              ? isNaN(options?.valueOperation())
                ? ""
                : formatCurrency(options?.valueOperation(), 6)
              : focus
                ? value
                : value
          }
          disabled={
            ((tipoConsumo === "volumetría" && !isBolsa) ||
              (tipoConsumo === "bolsa" && isBolsa)) &&
            !nonDependent
          }
          onFocus={handleFocus}
          helperText={
            original?.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
        />
      ) : options?.valueOperation ? (
        formatCurrency(options?.valueOperation(), 6)
      ) : (
        <>
          {isPrecioUnitario ? "$" : ""} {formatCurrency(currentValue, 2)}{" "}
        </>
      )}
    </>
  );
}
