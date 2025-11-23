import React, { useState } from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../../components/Tooltip";
import CancelModal from "./CancelModal";
import BasicModal from "../../../../../modals/BasicModal";
import { PROFORMA as MESSAGES } from "../../../../../constants/messages";
import { FormCheck } from "react-bootstrap";

export function ToggleCell({
  row,
  onClickEditToggle,
  isActiveAddProvider,
  isActiveEditableProvider,
  onClickRevert,
  table,
  onRemoveProvider,
  rows,
  editable,
  onActiveEditIcon,
  onRevertRow,
  onRemovedRow,
  getValue,
  column,
  type
}) {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);

  const { removeRow } = table?.options?.meta;
  const { isEditable = false, isNewRegister } = row.original;
  const { updateData } = table.options.meta;
  const { index } = row;

  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);

  switch (type) {
    case "switch": {
      return <FormCheck type="switch" onClick={onToggleEdit} />;
    }

    default: {
      return (
        <>
          {isNewRegister ? (
            <>
              {isEditable === false ? (
                <>
                  <Tooltip placement="top" text={"Editar"}>
                    <span>
                      <IconButton
                        iconSize="1x"
                        type="edit"
                        onClick={() => {
                          updateData(index, "isEditable", true);
                          onActiveEditIcon(true);
                        }}
                        disabled={false}
                      />
                    </span>
                  </Tooltip>
                  <Tooltip placement="top" text={"Eliminar"}>
                    <span>
                      <IconButton
                        disabled={false}
                        iconSize="1x"
                        type="drop"
                        onClick={() => {
                          setIsOpenModal(true);
                        }}
                      />
                    </span>
                  </Tooltip>
                  <BasicModal
                    size="md"
                    handleApprove={() => {
                      setIsOpenModal(false);
                      removeRow(row.index);
                      onRemoveProvider();
                    }}
                    handleDeny={() => setIsOpenModal(false)}
                    denyText={"No"}
                    approveText={"Sí"}
                    show={isOpenModal}
                    title={"Mensaje"}
                    onHide={() => setIsOpenModal(false)}
                  >
                    {MESSAGES.MSG007}
                  </BasicModal>

                </>
              ) : (
                <>
                  <Tooltip placement="top" text={"Descartar"}>
                    <span>
                      <IconButton
                        iconSize="1x"
                        type="undo"
                        onClick={() => {
                          setIsOpenCancelModal(true);
                          onActiveEditIcon(false);
                        }}
                        disabled={false}
                      />
                    </span>
                  </Tooltip>
                  {isOpenCancelModal && (
                    <CancelModal
                      handleApprove={async () => {
                        if (isNewRegister) {
                          removeRow(row.index);
                        } else {
                          onRevertRow(row.original);
                          updateData(index, "isEditable", false);
                        }
                        setIsOpenCancelModal(false);
                      }}
                      handleDeny={() => setIsOpenCancelModal(false)}
                      isOpenCancelModal={isOpenCancelModal}
                      title={MESSAGES.MSG009}
                    />
                  )}
                </>
              )}
            </>
          ) : null}
        </>
      );
    }
  }
}
