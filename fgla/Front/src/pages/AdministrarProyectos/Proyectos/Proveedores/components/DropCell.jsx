import React, { useState } from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../../components/Tooltip";
import BasicModal from "../../../../../modals/BasicModal";
import { MESSAGES, } from "../constants";

export function DropCell({ row, table, onRemoveProvider, editable }) {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const { removeRow } = table.options.meta;
  const { identifier, isNewProvider } = row.original;
  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);

  const onDeleteRow = async () => {
    const onRemoveSuccess = () => {
      if (row.getIsSelected()) {
        onToggleEdit(!row.getIsSelected());
      }
      removeRow(row.index);
      setIsOpenModal(false);
    };
    onRemoveProvider(identifier, onRemoveSuccess, table);
  };

  return (
    <>
      {isNewProvider !== true && (
        <>
          <BasicModal
            handleApprove={onDeleteRow}
            handleDeny={() => setIsOpenModal(false)}
            denyText={"No"}
            approveText={"Sí"}
            show={isOpenModal}
            title={'Mensaje'}
            onHide={() => setIsOpenModal(false)}
          >{MESSAGES.MSG008}</BasicModal>
          <IconButton
            disabled={!editable}
            iconSize="lg"
            type="drop"
            onClick={() => setIsOpenModal(true)}
            tooltip={"Eliminar"}
            tableContainer
          />
        </>
      )}
    </>
  );
}
