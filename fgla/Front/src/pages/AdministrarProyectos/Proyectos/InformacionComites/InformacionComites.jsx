import React, { useEffect, useState } from "react";
import { Row, Col } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import IngresoDatos from "./IngresoDatos";
import InformacionComiteTable from "./InformacionComiteTable/InformacionComiteTable";
import ModalComponent from "./Components/ModalComponent";
import {
  DownloadReport,
  GetInfoComites,
  clearInfoComite,
} from "../../../../store/infoComites/infoComitesActions";
import { useDispatch, useSelector } from "react-redux";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import { ADMINISTRAR_INFO_COMITES } from "../../../../constants/messages";
import { DownloadFileBase64 } from "./Components/DownloadFile";
import BasicModal from "../../../../modals/BasicModal";
import DetalleComite from "./DetalleComite";
import { Loader } from "../../../../components";
import Authorization from "../../../../components/Authorization";
import { InfoComitesProvider } from "./InfoComitesContext";

export default function InformacionComites({ data }) {
  var dispatch = useDispatch();
  const [isLoading, setIsLoading] = useState(false);
  const { proyecto, editable } = useSelector((state) => state.proyectos);

  const [idProyecto, setIdProyecto] = useState(null);

  useEffect(() => {
    if (proyecto?.idProyecto) {
      setIdProyecto(proyecto.idProyecto);
    } else if (proyecto?.proyecto?.idProyecto) {
      setIdProyecto(proyecto.proyecto.idProyecto);
    }
  }, [proyecto]);

  //#region Modal IngresoDatos
  const [showIngresoDatos, setShowIngresoDatos] = useState(false);
  const handleShowIngresoDatos = () => setShowIngresoDatos(true);
  const handleCloseIngresoDatos = () => {
    setShowIngresoDatos(false);
    dispatch(GetInfoComites(idProyecto));
  };

  //#region Confirm Modal
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalMessage, setConfirmModalMesage] = useState("");

  const handleShowConfirmModal = (
    message = ADMINISTRAR_INFO_COMITES.MSG004
  ) => {
    if (message) setConfirmModalMesage(message);
    setShowConfirmModal(true);
  };
  const handleCloseConfirmModal = () => {
    setConfirmModalMesage("");
    setShowConfirmModal(false);
  };

  const handleConfirm = (func) => {
    setShowConfirmModal(false);
    handleCloseIngresoDatos();
  };

  //#endregion

  //#region Detalle Comite
  const [idComiteProyecto, setIdComiteProyecto] = useState(null);
  const [showDetalleComite, setShowDetalleComite] = useState(false);
  const handleShowDetalleComite = () => {
    setShowDetalleComite(true);
    dispatch(clearInfoComite());
  };
  const handleCloseDetalleComite = () => {
    setIdComiteProyecto(null);
    setShowDetalleComite(false);
    dispatch(GetInfoComites(idProyecto));
    dispatch(clearInfoComite());
  };
  const [disableEdit, setDisableEdit] = useState(true);

  const handleView = (idComiteProyecto) => {
    setIdComiteProyecto(idComiteProyecto);
    setDisableEdit(true);
    handleShowDetalleComite();
  };

  const handleEdit = (idComiteProyecto) => {
    setIdComiteProyecto(idComiteProyecto);
    setDisableEdit(false);
    handleShowDetalleComite();
  };

  const [showConfirmModalDetalle, setShowConfirmModalDetalle] = useState(false);
  const [confirmModalMessageDetalle, setConfirmModalMesageDetalle] =
    useState("");

  const handleShowConfirmModalDetalle = (
    message = ADMINISTRAR_INFO_COMITES.MSG004
  ) => {
    if (message) setConfirmModalMesageDetalle(message);
    setShowConfirmModalDetalle(true);
  };
  const handleCloseConfirmModalDetalle = () => {
    setConfirmModalMesageDetalle("");
    setShowConfirmModalDetalle(false);
  };

  const handleConfirmDetalle = (func) => {
    dispatch(clearInfoComite());
    setShowConfirmModalDetalle(false);
    handleCloseDetalleComite();
  };

  //#endregion

  const handleDownloadExcel = () => {
    setIsLoading(true);
    dispatch(DownloadReport(idProyecto))
      .then((response) => {
        setIsLoading(false);
        if (response?.error) {
          ShowMessage(ADMINISTRAR_INFO_COMITES.MSG009);
        } else if (response?.payload)
          DownloadFileBase64(
            response.payload,
            "Reporte.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          );
      })
      .catch((error) => {
        setIsLoading(false);
        ShowMessage(ADMINISTRAR_INFO_COMITES.MSG009);
      });
  };

  const [messageModal, setMessageModal] = useState("");
  const [showMessageModal, setShowMessageModal] = useState(false);
  const ShowMessage = (message = ADMINISTRAR_INFO_COMITES.MSG002) => {
    setMessageModal(message);
    setShowMessageModal(true);
  };
  const HideMessage = () => {
    setShowMessageModal(false);
  };
  const [comiteCount, setComiteCount] = useState(0);
  return (
    <InfoComitesProvider>
      {isLoading && <Loader />}
      <Row>
        <Col md={12} className="text-end mb-2">
          <Authorization process={"PROY_INFO_COM_ADMIN"}>
            <IconButton
              type="add"
              onClick={handleShowIngresoDatos}
              disabled={!editable}
              tooltip={"Nuevo"}
            />
          </Authorization>
          <IconButton
            type="excel"
            onClick={handleDownloadExcel}
            tooltip={"Exportar a Excel"}
            disabled={comiteCount === 0}
          />
        </Col>
      </Row>
      <InformacionComiteTable
        handleEdit={handleEdit}
        handleShow={handleView}
        showMessage={ShowMessage}
        idProyecto={idProyecto}
        setComiteCount={setComiteCount}
      />

      <ModalComponent
        show={showIngresoDatos}
        handleClose={handleShowConfirmModal}
        title={"Información del Comité"}
      >
        <IngresoDatos
          handleClose={handleCloseIngresoDatos}
          handleCloseConfirm={handleShowConfirmModal}
          showMessage={ShowMessage}
          idProyecto={idProyecto}
        />
        <BasicModal
          size="md"
          handleApprove={handleConfirm}
          handleDeny={handleCloseConfirmModal}
          denyText={"No"}
          approveText={"Sí"}
          show={showConfirmModal}
          title="Mensaje"
          onHide={handleCloseConfirmModal}
        >
          {confirmModalMessage}
        </BasicModal>
      </ModalComponent>

      <ModalComponent
        show={showDetalleComite}
        handleClose={
          disableEdit ? handleCloseDetalleComite : handleShowConfirmModalDetalle
        }
        title={"Detalle del Comité"}
      >
        <DetalleComite
          idComiteProyecto={idComiteProyecto}
          disableEdit={disableEdit}
          handleClose={handleCloseDetalleComite}
          handleCloseConfirm={handleShowConfirmModalDetalle}
          showMessage={ShowMessage}
        />
        <BasicModal
          size="md"
          handleApprove={handleConfirmDetalle}
          handleDeny={handleCloseConfirmModalDetalle}
          denyText={"No"}
          approveText={"Sí"}
          show={showConfirmModalDetalle}
          title="Mensaje"
          onHide={handleCloseConfirmModalDetalle}
        >
          {confirmModalMessageDetalle}
        </BasicModal>
      </ModalComponent>

      <SingleBasicModal
        size="md"
        approveText={"Aceptar"}
        show={showMessageModal}
        title="Mensaje"
        onHide={HideMessage}
      >
        {messageModal}
      </SingleBasicModal>
    </InfoComitesProvider>
  );
}
