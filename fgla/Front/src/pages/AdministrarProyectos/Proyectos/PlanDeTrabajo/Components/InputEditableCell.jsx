import React, { useEffect, useRef, useState } from "react";
import { TextField } from "../../../../../components/formInputs";
import { validateNumber } from "../../../../../functions/utils";
import { useDebounce } from "../../../../../hooks/useDebounce";

export function InputEditableCell({
  getValue,
  row,
  column,
  table,
  percentage,
  decimals,
  onChangeValidation,
  loadingUpdate,
}) {
  const inputRef = useRef(null);
  const initialValue = getValue();
  const [value, setValue] = useState(getValue());
  const [dbValue, setDbValue] = useState(null);
  const debouncedValue = useDebounce(dbValue, 800);

  // useEffect(() => {
  //   if (dbValue !== null) {
  //     // console.log("debouncedValue ===> ", column.id);
  //     let { idTarea } = row.original;

  //     const parentIndex = row.parentId
  //       ? parseInt(row.parentId.split("")[0])
  //       : undefined;
  //     const newRow = { ...row.original, [column.id]: value };
  //     const final = updateRowFromSubRow(row, newRow);
  //     table.options.meta?.updateSubRows(parentIndex, final);

  //     onChangeValidation(idTarea, column.id, Number(value), row.original);
  //     setDbValue(null);
  //   }
  // }, [debouncedValue]);

  const onHandleChange = (e) => {
    const { value } = e.target;
    if (validateNumber(value, decimals) || value === "") {
      //console.log("onHandleChange > percentage => ", percentage, value);
      if (value !== "" && percentage) {
        parseInt(value) <= 100 && setValue(value);
        parseInt(value) <= 100 && setDbValue(value);
      } else {
        setValue(value);
        setDbValue(value);
      }
    }
  };

  const handleBlur = () => {
    if (dbValue !== null) {
      // console.log("debouncedValue ===> ", column.id);
      let { idTarea } = row.original;

      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = { ...row.original, [column.id]: value };
      const final = updateRowFromSubRow(row, newRow);
      table.options.meta?.updateSubRows(parentIndex, final);

      onChangeValidation(idTarea, column.id, Number(value), row.original);
      setDbValue(null);
    }
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  if (!row.getIsSelected()) {
    let valor = value === null || value === undefined ? "" : value.toString();
    //console.log("value.toString() => ", value.toString());
    return (
      <>
        {valor}
        {percentage && valor && "%"}
      </>
    );
  } else {
    return (
      <>
        <TextField
          value={value}
          onChange={onHandleChange}
          name={"TextField"}
          disabled={loadingUpdate}
          autoComplete="off"
          ref={inputRef}
          onBlur={handleBlur}
        />
      </>
    );
  }
}

function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = bottomRow.getParentRow().original;
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
