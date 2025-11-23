import React, { useEffect, useState } from "react";
import moment from "moment";
import { TextFieldDate } from '../../../../components/formInputs';

const FORMAT_DATE = "DD/MM/YYYY";
const FORMAT_DATE_INPUT = "YYYY-MM-DD";

export function DatePicker({
  getValue,
  row,
  column,
  table,
  startEnd
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const [errorsRow, setErrosRow] = useState(null);
  const [limitDate, setLimitDate] = useState({ minDate: null, maxDate: null })

  const onBlur = () => {
    if (startEnd && startEnd === "end") {
      table.options.meta?.updateData(row.index, column.id, moment(value).endOf("day").utc(true).toISOString(), row.getParentRow()?.index);
    } else {
      table.options.meta?.updateData(row.index, column.id, moment(value).utc(true).toISOString(), row.getParentRow()?.index);
    }
  };

  const onHandleChange = (e) => {
    setValue(e.target.value);
  };

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  useEffect(() => {
    setErrosRow(row.original.errors || null);
    let { fechaInicioVigencia, fechaFinVigencia } = row.original;

    let inicio = fechaInicioVigencia ? fechaInicioVigencia.split("T")[0] : null;
    let fin = fechaFinVigencia ? fechaFinVigencia.split("T")[0] : null;
    setLimitDate({
      minDate: startEnd === "end" ? inicio : null,
      maxDate: startEnd === "start" ? fin : null
    });
  }, [row]);

  const dateFormat = (date) => {
    let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
    return formatedDateTime;
  }

  const dateFormatInput = (date) => {
    let formatedDateTime = (date !== null && date !== "") ? moment(date.substring(0, 10)).format(FORMAT_DATE_INPUT) : "";
    return formatedDateTime;
  }

  if (!row.getIsSelected()) {
    return <>{dateFormat(value)}</>;
  } else {
    return (
      <TextFieldDate
        name="fecha"
        value={dateFormatInput(value)}
        onChange={onHandleChange}
        onBlur={onBlur}
        className={errorsRow && errorsRow[column.id] && (errorsRow[column.id] && !value ? 'is-invalid' : 'is-valid')}
        helperText={errorsRow && errorsRow[column.id] && (errorsRow[column.id] ? errorsRow[column.id] : '')}
        minDate={limitDate.minDate}
        maxDate={limitDate.maxDate}
      />
    );
  }
}
