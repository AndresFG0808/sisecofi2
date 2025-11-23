import React, { useEffect, useState } from "react";
import Select from "../../../../../components/formInputs/Select";
import { useLazyGetObjetivosQuery } from "../../../store";
import Loader from "../../../../../components/Loader";

export function DependentSelectCell({ row, column, table, getValue }) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const { original, index } = row;
  const { mapa, errors } = original;
  const { id } = column;
  const { options } = table;
  const [getObjetivos, { data: objetivos, isLoading: isLoadingObjetivos }] =
    useLazyGetObjetivosQuery();

  useEffect(() => {
    setValue(currentValue);
  }, [currentValue]);

  useEffect(() => {
    if (!mapa) return;
    getObjetivos(mapa);
    if (row.getIsSelected() || original.isNewRow) {
      options.meta.updateData(index, id, "");
    }
  }, [mapa]);

  const onChange = (e) => {
    setValue(e.target.value);
    options.meta.updateData(index, id, e.target.value);
  };

  return (
    <>
      {isLoadingObjetivos ? <Loader /> : null}
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          options={objetivos}
          keyValue={"primaryKey"}
          keyTextValue={"objetivo"}
          onChange={onChange}
          value={value}
          name={`objetivo-${row.id}`}
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
          helperText={
            original?.errors?.[column.id] ? original?.errors?.[column.id] : ""
          }
          keyStatus="estatus"
          hideDisabledOptions={true}
        />
      ) : (
        original.objetivoDisplay
      )}
    </>
  );
}
