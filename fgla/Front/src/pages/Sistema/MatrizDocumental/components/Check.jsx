import React, { useEffect, useState } from "react";
import { FormCheck } from "react-bootstrap";
import SwitchButton from "../../../../components/buttons/SwitchButton";

export function Check({
  getValue,
  row,
  column,
  table,
  type
}) {
  const initialValue = getValue();
  const [value, setValue] = useState(initialValue);
  const canEdit = row.getIsSelected();

  const onHandleChange = () => {
    if (row.parentId) {
      const parentIndex = row.parentId
        ? parseInt(row.parentId.split("")[0])
        : undefined;
      const newRow = { ...row.original, [column.id]: !value };
      const final = updateRowFromSubRow(row, newRow);
      table.options.meta?.updateSubRows(parentIndex, final);
    } else {
      table.options.meta?.updateData(row.index, column.id, !value);
    }
  };

  const onHandleChangeSwitch = () => {
    let desactivarCarpeta = !value === false;

    if (desactivarCarpeta && validateActiveSubrows()) {
      onHandleChange();
    } else if (!desactivarCarpeta) {
      onHandleChange();
    }
  };

  const validateActiveSubrows = () => {
    let canDisable = true;
    let hasSubrows = row.original.subCarpetas?.length > 0 || false;

    if (hasSubrows) {
      let subCarpetas = row.original?.subCarpetas;
      let activeSubs = subCarpetas.filter(row => row.estatus);
      canDisable = !activeSubs.length > 0;
    }
    return canDisable;
  }

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  if (type && type === "switch") {
    return <SwitchButton
      value={value}
      onChange={onHandleChangeSwitch}
      name={"SwitchButton"}
      disabled={!canEdit}
    />;
  } else {
    return (
      <FormCheck
        checked={value}
        onChange={onHandleChange}
        disabled={!canEdit}
      />
    );
  }
}

function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = bottomRow.getParentRow().original;
  updatedRow.subCarpetas[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
