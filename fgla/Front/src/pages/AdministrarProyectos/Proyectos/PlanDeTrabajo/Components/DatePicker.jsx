import React, { useEffect, useState } from "react";
import moment from "moment";
import { TextFieldDate } from "../../../../../components/formInputs";
import { useDebounce } from "../../../../../hooks/useDebounce";

const FORMAT_DATE = "DD/MM/YYYY";
const FORMAT_DATE_INPUT = "YYYY-MM-DD";

export function DatePicker({
  getValue,
  row,
  column,
  table,
  onChangeValidation,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [minDate, setMinDate] = useState("");
  const [maxDate, setMaxDate] = useState("");

  const onChange = (e) => {
    //console.log("onChange pb => ", e.target.value);
    //console.log("onChange vacio => ", e.target.value === "");
    const { value } = e.target;
    setValue(value);
  };

  const handleBlur = (e) => {
    let newDate = "";
    const { value } = e.target;

    if (value && value !== "" && moment(value, FORMAT_DATE_INPUT).isValid()) {
      newDate = moment(value, FORMAT_DATE_INPUT).format(FORMAT_DATE);
    }

    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = { ...row.original, [column.id]: newDate };
      const final = updateRowFromSubRow(row, newRow);
      table.options.meta?.updateSubRows(parentIndex, final);
    } else {
      table.options.meta?.updateData(row.index, column.id, newDate);
    }
    let { idTarea } = row.original;
    onChangeValidation(idTarea, column.id, newDate || null, row.original);
  };

  useEffect(() => {
    setValue(dateFormatInput(initialValue));
    setMinDate(
      column.id === "fechaFinReal"
        ? moment(row.original.fechaInicioReal, FORMAT_DATE).format(
            FORMAT_DATE_INPUT
          )
        : null
    );
    setMaxDate(
      column.id === "fechaInicioReal"
        ? moment(row.original.fechaFinReal, FORMAT_DATE).format(
            FORMAT_DATE_INPUT
          )
        : null
    );

    //console.log("fechaFinReal ? ", column.id === "fechaFinReal", " minDate ===>", column.id === "fechaFinReal" ? moment(row.original.fechaInicioReal, FORMAT_DATE).format(FORMAT_DATE_INPUT) : null);
    //console.log("fechaInicioReal ? ", column.id === "fechaInicioReal", " maxDate ===> ", column.id === "fechaInicioReal" ? moment(row.original.fechaFinReal, FORMAT_DATE).format(FORMAT_DATE_INPUT) : null);
  }, [initialValue, row]);

  const dateFormatInput = (date) => {
    let validDate = moment(date, FORMAT_DATE).isValid();
    //console.log("dateFormatInput => ", validDate, date);
    let formatedDate =
      validDate && date && date !== null && date !== ""
        ? moment(date.substring(0, 10), FORMAT_DATE).format(FORMAT_DATE_INPUT)
        : date;
    return formatedDate;
  };

  if (!row.getIsSelected()) {
    return (
      <>{value ? moment(value, FORMAT_DATE_INPUT).format(FORMAT_DATE) : ""}</>
    );
  } else {
    return (
      <TextFieldDate
        value={value}
        onChange={onChange}
        minDate={minDate}
        maxDate={maxDate}
        onBlur={handleBlur}
      />
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
