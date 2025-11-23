import moment from "moment";
import React, { useEffect, useState } from "react";
import TextFieldDate from "../../../../../components/formInputs/TextFieldDate";

const FORMAT_DATE = "DD/MM/YYYY";
const FORMAT_DATE_INPUT = "YYYY-MM-DD";
const isWeekday = (date) => {
  const day = date.getDay();
  return day !== 0 && day !== 6;
};
export function DateIconCell({
  getValue,
  row,
  column,
  table,
  conditional = false,
  bias,
  name,
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const { original } = row;
  const { updateData } = table.options.meta;
  const today = new Date();
  const maxDate = new Date(today);
  maxDate.setDate(today.getDate() - 1);

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  const onChange = (e) => {
    setValue(e.target.value);
  };
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

  const onBlur = () => {
    updateData(row.index, column.id, moment(value).toISOString());
  };

  if (conditional) {
    return null;
  } else {
    return (
      <>
        {original.isNewRow || row.getIsSelected() ? (
          <>
            <TextFieldDate
              name={`${name}-${row.id}`}
              value={dateFormatInput(value)}
              onChange={onChange}
              onBlur={onBlur}
              maxDate={maxDate.toISOString().split("T")[0]}
            />
          </>
        ) : (
          dateFormat(value)
        )}
      </>
    );
  }
}
