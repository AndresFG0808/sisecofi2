import React, { useEffect, useRef, useState } from "react";
import IconButton from "../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../components/Tooltip";
import CancelModal from "../DatosGenerales/CancelModal";
import BasicModal from "../../../../modals/BasicModal";
import { MESSAGES } from "./constants";
import Authorization from "../../../../components/Authorization";

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
  estatus,
  estatusEditable,
  onActiveEditIcon,
  onRevertRow,
  onRemovedRow,
  tableType,
}) {
  const [isOpenModal, setIsOpenModal] = useState(false);

  const { removeRow } = table?.options?.meta;
  const { isEditable, isNewRegister } = row.original;
  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);
  const { updateData } = table.options.meta;
  const { index } = row;

  const onGetProcess = (tableType) => {
    let process = '';
    if (tableType == 1) {
      process = 'CON_SERV_DICT_PCONT_ADMIN';
    }
    if (tableType == 2) {
      process = 'CON_SERV_DICT_PCONV_ADMIN';
    }
    if (tableType == 3) {
      process = 'CON_SERV_DICT_DEDUC_ADMIN';
    }
    return process;
  }
  
  return (
    <>
      {isEditable === false ? (
        <>
          {(estatusEditable || estatus === "Dictaminado") && (
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
          )}
          {(estatusEditable || editable === true) && (
            <Authorization process={onGetProcess(tableType)}>
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
            </Authorization>
          )}

          <BasicModal
            handleApprove={() => {
              setIsOpenModal(false);
              removeRow(row.index);
              onRemovedRow(row);
              onRemoveProvider();
            }}
            handleDeny={() => setIsOpenModal(false)}
            denyText={"No"}
            approveText={"Sí"}
            size="md"
            show={isOpenModal}
            title={"Mensaje"}
            onHide={() => setIsOpenModal(false)}
          >
            {MESSAGES.MSG010}
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
              title={MESSAGES.MSG016}
            />
          )}
        </>
      )}
    </>
  );
}
