import {
  FileCell,
  SelectedLabelCell,
  MoneyCell,
  ActionCell,
} from "./CellComponents";
import { useDispatch } from "react-redux";
import { useState,useContext } from "react";
import {
  DescargarFolder,
  GetSatCloud,
  DeleteComite,
  GetInfoComites,
  DescargarArchivo,
  GetDetalleProyecto,
} from "../../../../../store/infoComites/infoComitesActions";
import {
  DownloadFileBase64,
  getMimeType,
  validMimeTypes,
  getFileExtension,
} from "./DownloadFile";
import { ADMINISTRAR_INFO_COMITES } from "../../../../../constants/messages";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { ModalSatCloud } from "../../../../../components";
import BasicModal from "../../../../../modals/BasicModal";
import { InfoComitesContext } from "../InfoComitesContext";

export function SelectedCellProps(props, options) {
  return (
    <SelectedLabelCell
      getValue={props.getValue}
      column={props.column}
      row={props.row}
      table={props.table}
      isToggle
      options={options}
    />
  );
}

export function FileCellProps(props, setIsLoading) {
  const { handleShowMessage } = useContext(InfoComitesContext);
  const dispatch = useDispatch();



  const handleDownloadFolder = (path, totalFiles, name) => {
    setIsLoading(true);
    dispatch(
      totalFiles > 1
        ? DescargarFolder({ path: path })
        : DescargarArchivo({ path: path })
    )
      .then((data) => {
        setIsLoading(false);
        if (data?.error) {
          handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG009);
        } else if (data?.payload) {
          if (totalFiles > 1) {
            DownloadFileBase64(data.payload, "Descarga.zip", "application/zip");
          }
          if (totalFiles === 1) {
            let extencioName = getFileExtension(path);
            if (extencioName) {
              let { extension, fileName } = extencioName;
              let mimeType = validMimeTypes[extension.toLowerCase()];
              DownloadFileBase64(data.payload, fileName, mimeType);
            } else {
              handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG009);
            }
          }
        }
      })
      .catch((error) => {
        setIsLoading(false);
        handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG009);
      });
  };

  const [dataSatCloud, setDataSatCloud] = useState({});
  const [showSatCloud, setShowSatCloud] = useState(false);
  const showModalSatCloud = () => setShowSatCloud(true);
  const closeModalSatCloud = () => setShowSatCloud(false);

  let handleShowSatCloud = (path) => {
    setIsLoading(true);
    dispatch(
      GetSatCloud({
        path: path,
      })
    )
      .then((data) => {
        setIsLoading(false);
        let { temporal, url } = data.payload;
        if (temporal && url) {
          showModalSatCloud();
          setDataSatCloud(data.payload);
        } else {
          handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG012);
        }
      })
      .catch((error) => {
        setIsLoading(false);
        handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG012);
      });
  };

  return (
    <>
      <FileCell
        getValue={props.getValue}
        column={props.column}
        row={props.row}
        table={props.table}
        isToggle
        handleDownloadFolder={handleDownloadFolder}
        handleShowSatCloud={handleShowSatCloud}
      />
      <ModalSatCloud
        show={showSatCloud}
        handleClose={closeModalSatCloud}
        url={dataSatCloud?.url ?? ""}
        password={dataSatCloud?.temporal ?? ""}
      />
    </>
  );
}

export function MoneyCellProps(props) {
  return (
    <MoneyCell
      getValue={props.getValue}
      column={props.column}
      row={props.row}
      table={props.table}
      isToggle
    />
  );
}

export function ActionCellProps({
  props,
  handleShow,
  ShowMessage,
  handleEdit,
  idProyecto,
  editable,
  setIsLoading,
}) {
  const dispatch = useDispatch();
  const { handleShowMessage } = useContext(InfoComitesContext);
  // var rowCount = table.getRowCount();
  // console.log(ADMINISTRAR_INFO_COMITES.MSG011)
  const OpenConfirmModal = (idComiteProyecto) => {
    setIdComiteProyectoDel(idComiteProyecto);
    setShowConfirmModal(true);
  };

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [idComiteProyectoDel, setIdComiteProyectoDel] = useState(null);
  const CloseConfirmModal = () => {
    setIdComiteProyectoDel(null);
    setShowConfirmModal(false);
  };

  let handleDelete = (idComiteProyecto, filesCount) => {
    if (!filesCount || filesCount === 0 || filesCount.length === 0) {
      OpenConfirmModal(idComiteProyecto);
    }
    if (filesCount > 0) {
      handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG011);
    }
  };

  function BorrarComite() {
    setIsLoading(true);

    if (idComiteProyectoDel) {
      dispatch(DeleteComite(idComiteProyectoDel)).then((res) => {
        setIsLoading(false);
        if (res?.error) {
          handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG010);
        } else {
          dispatch(GetInfoComites(idProyecto));
          dispatch(GetDetalleProyecto(idProyecto));
        }
      });
      CloseConfirmModal();
    }
  }

  //#region MessageModal
 
  //#endregion

  return (
    <>
      <ActionCell
        getValue={props.getValue}
        column={props.column}
        row={props.row}
        table={props.table}
        isToggle
        handleShow={handleShow}
        handleEdit={handleEdit}
        handleDelete={handleDelete}
        editable={editable}
      />
      <BasicModal
        size="md"
        handleApprove={BorrarComite}
        handleDeny={CloseConfirmModal}
        denyText={"No"}
        approveText={"Sí"}
        show={showConfirmModal}
        title="Mensaje"
        onHide={CloseConfirmModal}
      >
        {ADMINISTRAR_INFO_COMITES.MSG003}
      </BasicModal>
    
    </>
  );
}
