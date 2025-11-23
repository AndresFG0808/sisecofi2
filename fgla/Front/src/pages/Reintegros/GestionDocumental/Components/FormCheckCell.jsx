import React from "react";
import { FormCheck } from "react-bootstrap";
import Authorization from "../../../../components/Authorization";

export function FormCheckCell({
  getValue,
  row,
  table,
  column,
  callback,
  editable,
}) {
  const currentValue = getValue();
  const onChange = (e) => {
    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = {
        ...row.original,
        [column.id]: !currentValue,
        ...(!currentValue ? {} : { justificacion: "", obligatorio: true }),
      };
      const final = updateRowFromSubRow(row, newRow);
      table.options.meta?.updateSubRows(parentIndex, final);
    } else {
      table.options.meta?.updateData(row.index, column.id, !currentValue);
    }
    if (callback) callback();
  };
  return (
    <>
      {currentValue !== undefined ? (
        <Authorization
          process={"CON_SERV_DICT_REINT_CONS"}
          redirect={
            <div className="check-box-black">
              <FormCheck
                disabled={true}
                checked={currentValue}
                onChange={onChange}
              />
            </div>
          }
        >
          <div className="check-box-black">
            <FormCheck
              disabled={editable}
              checked={currentValue}
              onChange={onChange}
            />
          </div>
        </Authorization>
      ) : null}
    </>
  );
}
function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = structuredClone(bottomRow.getParentRow().original);
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
