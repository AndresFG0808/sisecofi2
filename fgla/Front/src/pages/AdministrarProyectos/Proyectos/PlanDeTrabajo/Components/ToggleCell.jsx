import { useEffect } from "react";
import { FormCheck } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";

export function ToggleCell({ table, row, column, descartarCambios, editable }) {
  const showEdit = !row.getCanExpand() && !row.getIsSelected();
  const showUndo = row.getIsSelected();
  const { updateData, updateRowById } = table.options.meta;
  const { id } = row;

  useEffect(() => {
    let isEditable = row.getIsSelected()
      ? true
      : row.original.primaryKey === ""
      ? true
      : false;
    row.toggleSelected(isEditable, { selectChildren: false });
  }, []);

  const onToggleEdit = () => {
    row.toggleSelected(true, { selectChildren: false });
  };

  const handleCancelEdit = () => {
    
    // updateRowById(row.id,originalData)
    row.toggleSelected(false, { selectChildren: false });
    descartarCambios(row.original.idTarea, row, table);

    // console.log("handleCancelEdit => ");
  };

  return (
    <>
      {showEdit && (
        <IconButton
          iconSize="lg"
          type={"edit"}
          onClick={onToggleEdit}
          tooltip={"Editar"}
          disabled={!editable}
          tableContainer
        />
      )}
      {showUndo && (
        <IconButton
          iconSize="lg"
          type={"undo"}
          onClick={handleCancelEdit}
          tooltip={"Descartar"}
          tableContainer
        />
      )}
    </>
  );
}
