import React, { useEffect, useState } from "react";
import TextFieldDate from "../../../components/formInputs/TextFieldDate";
import moment from "moment";
const FORMAT_DATE = "DD/MM/YYYY";
const FORMAT_DATE_INPUT = "YYYY-MM-DD";
const isWeekday = (date) => {
  const day = date.getDay();
  return day !== 0 && day !== 6;
};
export function DateCell({
  name,
  row,
  table,
  column,
  getValue,
  disabled,
  bias,
}) {
  const currentValue = getValue();
  const [value, setValue] = useState(currentValue);
  const { updateData } = table.options.meta;
  const { original } = row;

  useEffect(() => {
    setValue(currentValue);
  }, [currentValue]);

  const dateFormatInput = (date) => {
    const formatDateTime =
      date !== null && date !== ""
        ? moment(date.substring(0, 10)).format(FORMAT_DATE_INPUT)
        : "";
    return formatDateTime;
  };
  const dateFormat = (date) => {
    const formatDateTime =
      date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
    return formatDateTime;
  };

  const handleChange = (event) => {
    setValue(event.target.value);
  };
  const onBlur = () => {
    if (bias === "end") {
      updateData(
        row.index,
        column.id,
        moment(value).endOf("day").utc(true).toISOString()
      );
    } else {
      updateData(row.index, column.id, moment(value).utc(true).toISOString());
    }
  };

  return (
    <>
      {original.isNewRow || row.getIsSelected() ? (
        <TextFieldDate
          disabled={disabled}
          name={`${name + row.id}`}
          onChange={handleChange}
          onBlur={onBlur}
          value={dateFormatInput(value)}
          className={`${original?.errors?.[column.id] ? "is-invalid" : ""}`}
          helperText={
            original.errors?.[column.id] ? original.errors?.[column.id] : ""
          }
        />
      ) : (
        dateFormat(value)
      )}
    </>
  );
}
