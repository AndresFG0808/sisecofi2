import React, { createContext, useCallback, useState } from "react";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import BasicModal from "../../../modals/BasicModal";
export const ComitesContext = createContext();

//#region Message Modal
export const ComitesProvider = ({ children }) => {
  const [actionType, setActionType] = useState("");

  const [showMessage, setShowMessage] = useState(false);
  const [message, setMessage] = useState("");
  const handleCloseMessage = () => {
    setMessage("");
    setShowMessage(false);
  };
  const handleShowMessage = (message) => {
    setMessage(message);
    setShowMessage(true);
  };
  //#endregion

  //#region Confirm Modal

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalMessage, setConfirmModalMesage] = useState("");
  const _confirmData = {
    approve: () => {},
    deny: () => {},
  };
  const [confirmData, setConfirmData] = useState(_confirmData);

  const handleShowConfirmModal = (
    title,
    approve = _confirmData.approve,
    deny = _confirmData.deny
  ) => {
    setConfirmModalMesage(title);
    setConfirmData({ approve, deny });
    setShowConfirmModal(true);
  };
  const handleApprove = () => {
    if (confirmData?.approve) {
      confirmData.approve();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  const handleDenny = () => {
    if (confirmData?.deny) {
      confirmData.deny();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };
  //#endregion
  const valueContext = {
    actionType,
    setActionType,
    handleShowMessage,
    handleShowConfirmModal,
  };
  return (
    <ComitesContext.Provider value={valueContext}>
      <>
        {children}
        <SingleBasicModal
          size="md"
          show={showMessage}
          onHide={handleCloseMessage}
          title="Mensaje"
          approveText={"Aceptar"}
        >
          {message}
        </SingleBasicModal>
        <BasicModal
          size="md"
          handleApprove={handleApprove}
          handleDeny={handleDenny}
          denyText={"No"}
          approveText={"Sí"}
          show={showConfirmModal}
          title="Mensaje"
          onHide={handleDenny}
        >
          {confirmModalMessage}
        </BasicModal>
      </>
    </ComitesContext.Provider>
  );
};
