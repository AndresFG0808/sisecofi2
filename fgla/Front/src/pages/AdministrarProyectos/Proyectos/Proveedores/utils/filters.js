export const dateFilterFormat = (
  dateFormat,
  moment,
  row,
  columnId,
  filterValue
) => {
  const rowValue = row.original[columnId];
  return rowValue !== null
    ? moment(rowValue).format(dateFormat).includes(filterValue)
    : false;
};

export const catalogFilterByText = (catalog, row, columnId, filterValue) => {
  const rowValue = row.original[columnId];
  const rowObject = catalog.find((item) => item.id === rowValue);
  return rowObject
    ? rowObject.texto
        .trim()
        .toLowerCase()
        .includes(filterValue.trim().toLowerCase())
    : false;
};
const validations = ["si", "sí", "s"];
export const isNonNullableDate = (idKey, options, date) => {
  const isYes = validations.includes(
    options?.find((option) => option?.id === idKey)?.texto?.toLowerCase()
  );
  return isYes && !!!date;
};
