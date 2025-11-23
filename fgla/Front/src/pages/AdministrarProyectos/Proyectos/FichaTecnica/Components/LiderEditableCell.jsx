import React, { useEffect, useState } from "react";
import Select from "../../../../../components/formInputs/Select";
import { Col, Row } from "react-bootstrap";

export function LiderEditableCell({
  getValue,
  row,
  column,
  table,
  name = "",
  options = [],
  keyValue = "",
  keyTextValue = "",
  callback,
  useFilter = false,
  filterProperty = null,
  displayProperty,
  errors,
}) {
  const [value, setValue] = useState("");
  const [valid, setValid] = useState(true);
  const { original } = row;
  const { updateSubRows } = table.options.meta;

  // value será idUsuarioStr (string)
  useEffect(() => {
    // 1) Si la fila ya trae idUsuarioStr (por rearrangeLideres), úsalo
    if (original?.idUsuarioStr) {
      setValue(String(original.idUsuarioStr));
      return;
    }
    // 2) Si no, intenta encontrar por nombre el idUsuarioStr en options
    const currentName = getValue?.();
    if (currentName) {
      const found = options.find((o) => o?.nombre === currentName);
      if (found?.[keyValue]) {
        setValue(String(found[keyValue]));
      }
    }
  }, [original?.idUsuarioStr, options, getValue, keyValue]);

  const isIdValid = (v) => typeof v === "string" && v.trim().length > 0;

  const onChange = (e) => {
    const newVal = e.target.value; // idUsuarioStr
    setValue(newVal);

    // OJO: no escribimos column.id ("nombre") con el idUsuarioStr
    // Dejamos que el callback actualice la fila (nombreDisplay, correo, puesto, idReferencia, nivel, etc.)
    if (useFilter) {
      const display = options?.find((o) => String(o?.[keyValue]) === String(newVal));
      updateSubRows(row.index, {
        ...original,
        // Si quieres actualizar algo local aquí, hazlo:
        [displayProperty]: display?.[keyTextValue] ?? original?.[displayProperty],
      });
    }

    setValid(isIdValid(newVal));
    if (callback) callback(e);
  };

  const onBlur = (e) => {
    setValid(isIdValid(e.target.value));
  };

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          name={name}
          value={value}                    // <- idUsuarioStr
          onChange={onChange}
          options={Array.isArray(options) ? options : []}
          keyValue={keyValue}              // "idUsuarioStr"
          keyTextValue={keyTextValue}      // "nombre"
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
          helperText={original.errors?.[column.id] ? original.errors?.[column.id] : ""}
          onBlur={onBlur}
          //keyStatus="estatus"
          //hideDisabledOptions={true}
        />
      ) : (
        original?.[displayProperty]
      )}
    </>
  );
}
