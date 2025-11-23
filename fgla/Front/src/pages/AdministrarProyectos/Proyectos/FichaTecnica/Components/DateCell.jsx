import { faCalendarAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import TextFieldDate from "../../../../../components/formInputs/TextFieldDate";
import moment from "moment";

const isWeekday = (date) => {
  const day = date.getDay();
  return day !== 0 && day !== 6;
};
export function DateCell({ getValue, row, column, table, isToggle, type }) {
  const date = getValue();
  const { updateData } = table.options.meta;
  const maxDate = new Date();

  if (isToggle) {
    if (!row.getIsSelected()) {
      return (
        <>
          <p>{moment(date).format("DD-MM-YYYY") || date}</p>
        </>
      );
    } else {
      switch (type) {
        case "text-field": {
          return (
            <>
              <TextFieldDate
                name={"fechaAsignacion"}
                value={date}
                onChange={(event) =>
                  updateData(row.index, column.id, event.target.value)
                }
              />
            </>
          );
        }
        default: {
          return (
            <>
              <DatePicker
                selected={date}
                onChange={(date) => updateData(row.index, column.id, date)}
                customInput={<FontAwesomeIcon icon={faCalendarAlt} />}
                maxDate={maxDate}
              />
            </>
          );
        }
      }
    }
  } else {
    return (
      <>
        {type === "text-field" ? (
          <>
            <TextFieldDate
              name={"fechaAsignacion"}
              value={date}
              onChange={(event) =>
                updateData(row.index, column.id, event.target.value)
              }
            />
          </>
        ) : (
          <DatePicker
            selected={date}
            onChange={(date) => updateData(row.index, column.id, date)}
            customInput={<FontAwesomeIcon icon={faCalendarAlt} />}
            maxDate={maxDate}
            filterDate={isWeekday}
          />
        )}
      </>
    );
  }
}
