import React, { useState, useEffect, useCallback } from "react";
import { TablaEditable } from "../../../components/table/TablaEditable";
import * as catalogosSlice from "../../../store/catalogos/catalogosSlice";
import { useSelector, useDispatch } from "react-redux";
import { Loader } from "../../../components";
import {
  usePutDescargarArchivoTCMutation,
  usePutDescargarFolderTCMutation,
  usePutFolderSatCloutTCMutation,
} from "./store";
import { fileCell, LabelCell, LabelCellArray, mapComites } from "./utilities";
import { useToast } from "../../../hooks/useToast";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import {
  DownloadFileBase64,
  getFileExtension,
  validMimeTypes,
} from "../../../functions/utils/base64.utils";
import ModalSatCloud from "../../../components/ModalSatCloud";

const Comites = ({ data }) => {
  //#region Message Modal
  const { errorToast } = useToast();

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

  //#region servicios
  const dispatch = useDispatch();

  const catalogos = useSelector((state) => state.catalogos);
  const { isLoading: isLoadingCatalogos, catalogos: catalogosData } = catalogos;


  useEffect(() => {
    dispatch(catalogosSlice.getContratoConvenio());
    dispatch(catalogosSlice.getAfectacion());
    dispatch(catalogosSlice.getComite());
  }, [dispatch]);

  //#endregion

  //#region Descargas

  const [putArchivo, { isLoading: isLoadingArchivo }] =
    usePutDescargarArchivoTCMutation();
  const [putFolder, { isLoading: isLoadingFolder }] =
    usePutDescargarFolderTCMutation();
  const [putFolderSC, { isLoading: isLoadingFolderSC }] =
    usePutFolderSatCloutTCMutation();

  const dercargarArchivo = (path) => {
    let { extension, fileName } = getFileExtension(path);
    let mimetype = validMimeTypes[extension];

    putArchivo({ path }).then((response) => {
      if (response.error) {
        handleShowMessage(
          "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."
        );
      } else {
        DownloadFileBase64(response.data, fileName, mimetype);
      }
    });
  };

  function removeLastSegment(str) {
    const lastSlashIndex = str.lastIndexOf("/");
    return lastSlashIndex !== -1 ? str.slice(0, lastSlashIndex) : str;
  }
  const descargarFolder = (path) => {
    putFolder({ path: path }).then((response) => {
      if (response.error) {
        handleShowMessage(
          "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."
        );
      } else {
        DownloadFileBase64(
          response.data,
          "Descarga.zip",
          validMimeTypes["zip"]
        );
      }
    });
  };

  const [showSatCloud, setShowSatCloud] = useState(false);
  const [dataSatCloud, setDataSatCloud] = useState({ url: "", password: "" });
  const handleShowSatCloud = (url = "", password = "") => {
    setDataSatCloud({ url, password });
    setShowSatCloud(true);
  };

  const handleCloseSatCloud = () => {
    setShowSatCloud(false);
    setDataSatCloud({ url: "", password: "" });
  };
  const descargarSatCloud = (path) => {
    putFolderSC({ path: path }).then((response) => {
      if (response.error) {
        handleShowMessage(
          "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."
        );
      } else {
        console.log(response.data);
        let { url, temporal } = response.data;

        handleShowSatCloud(url, temporal);
      }
    });
  };

  //#endregion

  //#region Map Data
  const [dataTable, setDataTable] = useState([]);

  useEffect(() => {
    setDataTable(mapComites(data, catalogosData));
  }, [catalogosData, data]);

  //#endregion

  const columns = [
    {
      accessorKey: "contratoConvenio",
      header: "Contrato/ Convenio",
      cell: LabelCell,
    },
    {
      accessorKey: "afectacion",
      header: "Afectación",
      cell: LabelCellArray,
    },
    {
      accessorKey: "comite",
      header: "Comité",
      cell: LabelCell,
    },
    {
      accessorKey: "fechaSesion",
      header: "Fecha de sesión",
      cell: LabelCell,
    },
    {
      accessorKey: "sesion",
      header: "Sesión",
      cell: (props) =>
        fileCell({
          dercargarArchivo,
          descargarFolder,
          descargarSatCloud,
          ...props,
        }),
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "acuerdo",
      header: "Acuerdo",
      cell: LabelCell,
    },
    {
      accessorKey: "vigencia",
      header: "Vigencia",
      cell: LabelCell,
    },
    {
      accessorKey: "montoAutorizado",
      header: "Monto autorizado (C/IVA)",
      cell: LabelCell,
    },
    {
      accessorKey: "monto",
      header: "Monto en pesos",
      cell: LabelCell,
    },
  ];

  return (
    <>
      {(isLoadingCatalogos ||
        isLoadingArchivo ||
        isLoadingFolder ||
        isLoadingFolderSC) && <Loader />}
      <ModalSatCloud
        show={showSatCloud}
        handleClose={handleCloseSatCloud}
        url={dataSatCloud.url}
        password={dataSatCloud.password}
      />
      <SingleBasicModal
        size="md"
        show={showMessage}
        onHide={handleCloseMessage}
        title="Mensaje"
        approveText={"Aceptar"}
      >
        {message}
      </SingleBasicModal>
      <TablaEditable hasPagination dataTable={dataTable} columns={columns} />
    </>
  );
};

export default Comites;
