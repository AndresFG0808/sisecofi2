import { useEffect, useState } from 'react';
import { FormCheck } from "react-bootstrap"
import IconButton from "../../../../components/buttons/IconButton";

export function ToggleCell({ getValue, row, column, table, type, cancelDiscardAdmin }) {

  const [rowDiscard, setRowDiscard] = useState(null);

  useEffect(() => {
    let isEditable = row.getIsSelected() ? true : row.original.primaryKey === '' ? true : false;
    row.toggleSelected(isEditable, { selectChildren: false });
  }, []);

  useEffect(() => {
    if (rowDiscard) {
      let isEditable = row.primaryKey === rowDiscard.primaryKey ? false : row.original.primaryKey === '' ? true : row.getIsSelected();
      row.toggleSelected(isEditable, { selectChildren: false });
      setRowDiscard(null);
    }
  }, [row]);

  const onToggleEdit = () => {
    row.toggleSelected(true, { selectChildren: false });
  };

  const onToggleCancel = () => {
    console.log()
    setRowDiscard(row.original.primaryKey === '' ? null : row);
    cancelDiscardAdmin(row.original);
  };

  switch (type) {
    case "switch": {
      return <FormCheck type="switch" onClick={onToggleEdit} />;
    }
    default: {
      return (
        row.getIsSelected() ? <IconButton iconSize="lg" type={"undo"} onClick={onToggleCancel} tooltip={"Descartar"} tableContainer/> :
          <IconButton iconSize="lg" type={"edit"} onClick={onToggleEdit} tooltip={"Editar"} tableContainer />
      );
    }
  }
}
