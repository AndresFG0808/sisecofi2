import React, { useEffect, useState } from "react";
import { useLazyGetPlantillaFaseByIdQuery } from "../store";
import Select from "../../../../../components/formInputs/Select";
import Loader from "../../../../../components/Loader";
import { useToast } from "../../../../../hooks/useToast";

export function DependentSelectCell({ row, table, column, getValue, errors }) {
  const { errorToast } = useToast();
  const currentVal = getValue();
  const [value, setValue] = useState(currentVal);
  const [valid, setValid] = useState(true);
  const { original, index } = row;
  const { fase } = original;
  const { options } = table;
  const { id } = column;
  const [
    getPlantillaFaseById,
    { data: plantilla, isLoading: isLoadingPlantilla, error, isError },
  ] = useLazyGetPlantillaFaseByIdQuery();
  const isIdValid = (id) => !isNaN(parseInt(id));

  useEffect(() => {
    setValue(currentVal);
  }, [currentVal]);

  useEffect(() => {
    if (!fase) return;
    getPlantillaFaseById(fase);
    options.meta.updateData(index, "lastCachedValue", fase);
    if (row.getIsSelected() || original.isNewRow) {
      options.meta.updateData(index, id, "");
    }
  }, [fase]);

  useEffect(() => {
    if (isError) {
      errorToast("Ocurrió un error, favor de intentarlo de nuevo.");
    }
  }, [isError]);

  const onChange = (e) => {
    setValue(e.target.value);
    const display = plantilla?.find(
      (plan) => plan?.idPlantillaVigente === parseInt(e.target.value)
    );
    options.meta.updateSubRows(row.index, {
      ...original,
      [column.id]: e.target.value,
      displayPlantilla: display?.nombre,
    });

    setValid(isIdValid(e.target.value));
  };
  const onBlur = (e) => {
    setValid(isIdValid(e.target.value));
  };
  return (
    <>
      {isLoadingPlantilla ? <Loader /> : null}
      {original.isNewRow || row.getIsSelected() ? (
        <Select
          options={plantilla}
          keyValue={"idPlantillaVigente"}
          keyTextValue={"nombre"}
          onChange={onChange}
          name={`${plantilla}-${row.id}`}
          value={value}
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
          helperText={
            original.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
          onBlur={onBlur}
        />
      ) : (
        original?.displayPlantilla
      )}
    </>
  );
}
