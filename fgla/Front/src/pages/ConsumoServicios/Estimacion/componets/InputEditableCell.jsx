import React, { useEffect, useState } from "react";
import TextField from "../../../../components/formInputs/TextField";
import { logError } from '../../../../components/utils/logError.js';

export function InputEditableCell({
  getValue,
  row,
  column,
  table,
  isEditable,
  onValueChange,
  onChangeData,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [error, setError] = useState("");
  const { updateDatapromise } = table.options.meta;
  const { index } = row;
  const { id } = column;

  const formatNumber = (num) => {
    if (num === null || num === undefined) return "";
    if (num === " ") return " ";
    if (num === 0) return "0";
    const parts = num.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
  };

  const unformatNumber = (num) => {
    if (!num) return "";
    return num.toString().replace(/,/g, "");
  };

  const validateNumber = (inputValue) => {
    const unformattedValue = unformatNumber(inputValue);
    const [integerPart, decimalPart] = unformattedValue.split(".");

    if (unformattedValue.startsWith("-")) {
      setError("");
      return false;
    }

    if (/[^0-9.]/.test(unformattedValue) || unformattedValue.includes("-")) {
      setError("");
      return false;
    }

    if (integerPart.length > 20) {
      setError("Número inválido. Máximo 20 dígitos enteros.");
      return false;
    }

    if (decimalPart && decimalPart.length > 6) {
      setError("Número inválido. Máximo 6 dígitos decimales.");
      return false;
    }

    setError("");
    return true;
  };

  const onHandleChange = (e) => {
    const inputValue = e.target.value.trim();
    if (inputValue === " ") {
      setValue(" ");
      return;
    }
    const unformattedValue = unformatNumber(inputValue);

    if (validateNumber(unformattedValue)) {
      const formattedValue = formatNumber(unformattedValue);
      setValue(formattedValue);
    }
  };

  useEffect(() => {
    setValue(formatNumber(initialValue));
  }, [initialValue]);

  if (!isEditable) {
    return <>{value}</>;
  } else {
    return (
      <>
        <TextField
          value={value}
          onChange={onHandleChange}
          name={column.id}
          className="input-table"
          onBlur={(e) => {
            const unformattedValue = unformatNumber(e.target.value);
            if (validateNumber(unformattedValue)) {
              updateDatapromise(index, id, unformattedValue)
                .then(() => {
                  onValueChange();
                })
                .catch((error) => {
                  logError("Error actualizando valor:", error);
                });
            }
          }}
        />
        {error && <div className="error-message">{error}</div>}
      </>
    );
  }
}