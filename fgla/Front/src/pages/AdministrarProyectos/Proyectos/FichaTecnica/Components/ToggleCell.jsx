import { Tooltip } from "../../../../../components/Tooltip";
import IconButton from "../../../../../components/buttons/IconButton";

export function ToggleCell({ row, disabled }) {
  const onToggleEdit = () => {
    row.toggleSelected(!row.getIsSelected(), { selectChildren: false });
  };
  return (
    <>
      <Tooltip text={"Editar"} placement="top">
        <span>
          <IconButton iconSize="1x" type="edit" onClick={onToggleEdit} disabled={disabled} />
        </span>
      </Tooltip>
    </>
  );
}
