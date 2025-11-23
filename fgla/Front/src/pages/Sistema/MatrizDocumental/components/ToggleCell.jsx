import { FormCheck } from "react-bootstrap"
import IconButton from "../../../../components/buttons/IconButton";
import showMessage from "../../../../components/Messages";
import { MATRIZ_DOCUMENTAL } from '../../../../constants/messages';

export function ToggleCell({ getValue, row, column, table, type, asignado }) {
  const onToggleEdit = () => {
    if (asignado) {
      showMessage(MATRIZ_DOCUMENTAL.MSG008);
    } else {
      row.toggleSelected(true, { selectChildren: false });
    }
  };
  switch (type) {
    case "switch": {
      return <FormCheck type="switch" onClick={onToggleEdit} />;
    }

    default: {
      return (
        <IconButton
          type={"edit"}
          iconSize="lg"
          onClick={onToggleEdit}
          tooltip={"Editar"}
          tableContainer
        />
      );
    }
  }
}
