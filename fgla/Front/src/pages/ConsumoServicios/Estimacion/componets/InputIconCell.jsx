import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckCircle } from "@fortawesome/free-solid-svg-icons";
import TextField from "../../../../components/formInputs/TextField";

export function InputIconCell({
  getValue,
  row,
  column,
  table,
  isEditable,
  onValueChange,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [error, setError] = useState("");

  const validateInteger = (value) => {
    const regex = /^[0-9]*$/;
    if (!regex.test(value)) {
      setError("Solo números son permitidos.");
      return false;
    }
    setError("");
    return true;
  };

  const onHandleChange = (e) => {
    const inputValue = e.target.value;
    if (validateInteger(inputValue)) {
      setValue(inputValue);
      if (onValueChange) {
        onValueChange({ ...row.original, [column.id]: inputValue });
      }
    }
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  if (!isEditable) {
    return (
      <>
        {value === true ? (
          <FontAwesomeIcon icon={faCheckCircle} className="text-success" />
        ) : (
          ""
        )}
      </>
    );
  } else {
    return (
      <>
        <TextField
          value={value}
          onChange={onHandleChange}
          name={column.id}
          className="input-table"
        />
        {error && <div className="error-message">{error}</div>}
      </>
    );
  }
}
