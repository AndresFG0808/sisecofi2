import React from "react";
import Select from "../../../components/formInputs/Select";

export function SelectCellMod({
  row,
  table,
  column,
  name,
  getValue,
  options,
  keyValue,
  keyTextValue,
  displayValue,
  defaultOptionText,
  disabled,
  isUser = false,
  onChangeExtra,
}) {
  const { original } = row;
  const { meta } = table.options;

  // Construir el valor como "idUsuario-nivel"
  const value =
  original?.nombreServidorPublico != null && original?.nivel != null
    ? `${original.nombreServidorPublico}-${original.nivel}`
    : "";

  const handleChange = (event) => {
  const [idUsuario, nivel] = event.target.value.split("-").map(Number);

  const updatedOption = options?.find(
    (option) => option.idUsuario === idUsuario && option.nivel === nivel
  );

  if (!updatedOption) {
    return;
  }

  const updatedErrors = { ...original.errors };
  delete updatedErrors[column.id];

  const newRow = {
  ...original,
  usuarioInformacion: {
    idUsuario,
    nivel,
    nombre: updatedOption?.[keyTextValue],
  },
  nombreServidorPublico: idUsuario, // aseguramos string válido
  nivel: nivel,
  ...(displayValue && {
    [displayValue]: updatedOption?.[keyTextValue],
  }),
  errors: updatedErrors,
};




  meta.updateRowById(original.UUID, newRow);

  if (onChangeExtra) onChangeExtra(newRow);
};


  const handleBlur = () => {};

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          disabled={disabled}
          name={`${name + row.id}`}
          onChange={handleChange}
          onBlur={handleBlur}
          value={value}
          options={options}
          keyValue={(opt) => `${opt.idUsuario}-${opt.nivel}`}
          keyTextValue={keyTextValue}
          defaultOptionText={defaultOptionText}
          helperText={
            original?.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
        />
      ) : displayValue ? (
        original?.[displayValue]
      ) : (
        getValue()
      )}
    </>
  );
}
