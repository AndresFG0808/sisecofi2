import React, { useEffect, useState } from "react";
import Select from "../../../../../../components/formInputs/Select";

export function SelectGrupoCell({
  options,
  getValue,
  row,
  column,
  table,
  name,
  displayValue,
}) {
  const value = getValue();
  const { meta } = table.options;
  const { original } = row;

  const [grupo, setGrupo] = useState([]);
  const handleChange = (event) => {
    const tipoConsumo = grupo.find(
      (gpo) => gpo?.idGrupoServicio === parseInt(event.target.value)
    )?.catTipoConsumo?.nombre;
    const newRow = {
      ...row.original,
      [column.id]: event.target.value,
      tipoConsumo,
    };
    meta.updateSubRows(row.index, newRow);
  };
  useEffect(() => {
    if (options) {
      setGrupo(options.map((option) => option.grupoServiciosModel));
    }
  }, [options]);

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          name={`${name + row.id}`}
          options={grupo}
          onChange={handleChange}
          value={value}
          keyValue={"idGrupoServicio"}
          keyTextValue={"grupo"}
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
