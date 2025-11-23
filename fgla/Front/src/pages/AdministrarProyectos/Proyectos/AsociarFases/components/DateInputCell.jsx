import React, { useState } from "react";
import TextFieldDate from "../../../../../components/formInputs/TextFieldDate";
import moment from "moment";

export function DateInputCell({ getValue, row, column, table }) {
  return <>{moment(getValue()).format("DD/MM/YYYY")}</>;
}
