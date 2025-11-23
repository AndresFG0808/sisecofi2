import React, { useEffect, useRef, useState } from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../../components/Tooltip";
import CancelModal from "./CancelModal";
import { MESSAGES } from "../constants";
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
}) {
  const { removeRow } = table?.options?.meta;
  const { identifier, isNewProvider } = row.original;
  const onToggleEdit = (isOpen) => row.toggleSelected(isOpen);
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);

  function usePrevious(value) {
    const ref = useRef();
    useEffect(() => {
      ref.current = value;
    });
    return ref.current;
  }

  const prevIsNewProvider = usePrevious(isNewProvider);

  useEffect(() => {
    if (isNewProvider) {
      onToggleEdit(true);
    } else if (prevIsNewProvider && isNewProvider === false) {
      onToggleEdit(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isNewProvider]);

  useEffect(() => {
    if (row.getIsSelected() && isActiveAddProvider === false) {
      onClickEditToggle(false, row.original, table, false);
      onToggleEdit(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isActiveAddProvider]);

  const onClickEditProvider = () => {
    let isToggle = !row.getIsSelected();
    onClickEditToggle(isToggle, row.original, table);
    // onToggleEdit(isToggle);
  };

  const onDeleteRow = async () => {
    const onRemoveSuccess = () => {
      if (row.getIsSelected()) {
        onToggleEdit(!row.getIsSelected());
      }
      removeRow(row.index);
    };
    onRemoveProvider(identifier, onRemoveSuccess);
  };

  const onClickModal = () => {
    if (row.original.identifier.indexOf("+") > -1) {
      onDeleteRow();
    } else {
      let isToggle = !row.getIsSelected();
      onClickRevert(isToggle, row.original, rows);
    }
  };

  return (
    <>
      {isNewProvider !== true && row.getIsSelected() !== true ? (
        <IconButton
          iconSize="lg"
          type="edit"
          onClick={onClickEditProvider}
          disabled={
            editable === false ? true :
              isActiveEditableProvider === true ? false : false // isActiveAddProvider
          }
          tooltip={"Editar"}
          tableContainer
        />
      ) : (
        <>
          <IconButton
            iconSize="lg"
            type="undo"
            onClick={() => setIsOpenCancelModal(true)}
            disabled={false}
            tooltip={"Descartar"}
            tableContainer
          />
          <CancelModal
            handleApprove={onClickModal}
            handleDeny={() => setIsOpenCancelModal(false)}
            isOpenCancelModal={isOpenCancelModal}
            title={MESSAGES.MSG006}
          />
        </>
      )}
    </>
  );
}
