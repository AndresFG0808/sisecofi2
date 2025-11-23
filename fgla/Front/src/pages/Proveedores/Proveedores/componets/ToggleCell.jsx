import { FormCheck } from "react-bootstrap"
import IconButton from "../../../../components/buttons/IconButton";

export function ToggleCell({ getValue, row, column, table, type }) {
  const onToggleEdit = () => {
    row.toggleSelected(!row.getIsSelected(), { selectChildren: false });
  };
  switch (type) {
    case "switch": {
      return <FormCheck type="switch" onClick={onToggleEdit} />;
    }

    default: {
      return (
        <IconButton iconSize="lg" type={"edit"} onClick={onToggleEdit} tooltip={"Editar"} tableContainer />
      );
    }
  }
}
