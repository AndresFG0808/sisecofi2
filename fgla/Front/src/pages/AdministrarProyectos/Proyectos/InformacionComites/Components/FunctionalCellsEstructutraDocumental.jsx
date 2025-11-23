import { useState, useRef, useEffect } from "react";
import {
  ExpandibleCell,
  CheckCell,
  CheckJustifyCell,
  CellEstatus,
  CellComentarios,
  ActionDocsCell,
} from "./CellComponents";
import { useDispatch } from "react-redux";
import {
  DescargarFolder,
  DescargarArchivo,
} from "../../../../../store/infoComites/infoComitesActions";
import {
  DownloadFileBase64,
  getMimeType,
  validMimeTypes,
  getFileExtension,
} from "./DownloadFile";
import { FormControl } from "react-bootstrap";
import BasicModal from "../../../../../modals/BasicModal";
import { GESTION_DOCUMENTAL } from "../../../../../constants/messages";
import { Loader } from "../../../../../components";
import _, { forEach } from "lodash";

// function makeid(length) {
//   let result = "";
//   const characters =
//     "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//   const charactersLength = characters.length;
//   let counter = 0;
//   while (counter < length) {
//     result += characters.charAt(Math.floor(Math.random() * charactersLength));
//     counter += 1;
//   }
//   return result;
// }

export function ExpandibleCellProps(
  props,
  disableForm,
  GetDocumentById,
  UpdateDocumentList,
  documentList,
  handleShowMessage,
  setIsLoading
) {
  const dispatch = useDispatch();
  let { row } = props;

  const handleAddDocument = (row) => {
    let { archivos } = row.original;

    let rowData = GetDocumentById(row);

    let idFileTable = Array.isArray(archivos) ? archivos.length : 0;

    let idTable = rowData.idTable + "." + idFileTable;
    let newDocument = {
      ...rowData,
      idArchivoPlantilla: null,
      idArchivoPlantillaComite: null,
      otherFile: true,
      nombre: "",
      descripcion: "",
      idTable,
      id: idTable,
    };

    delete newDocument.archivos;
    delete newDocument.otrosDocumentos;
    UpdateDocumentList(row, newDocument);
  };

  const handleChange = (value) => {
    let document = GetDocumentById(row);
    document = {
      ...document,
      nombre: value,
      descripcion: value,
    };

    UpdateDocumentList(row, document);
  };

  const handleDownload = (ruta) => {
    setIsLoading(true);
    let extencioName = getFileExtension(ruta);
    if (extencioName) {
      let { extension, fileName } = extencioName;
      let mimeType = validMimeTypes[extension.toLowerCase()];
      dispatch(
        DescargarArchivo({
          path: ruta,
        })
      ).then((response) => {
        setIsLoading(false);
        if (response.error) {
          handleShowMessage(
            "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
          );
        } else {
          DownloadFileBase64(response.payload, fileName, mimeType);
        }
      });
    }
  };

  return (
    <>
      <ExpandibleCell
        {...props}
        handleAddDocument={handleAddDocument}
        disableForm={disableForm}
        handleSave={handleChange}
        documentList={documentList}
        handleDownload={handleDownload}
      />
    </>
  );
}

export function CheckCellProps(props, disableForm, disable) {
  const handleChange = () => {};
  return (
    <CheckCell
      {...props}
      disabled={disableForm || disable}
      handleChange={handleChange}
    />
  );
}

export function NoApplyCellProps(
  props,
  disableForm,
  disable,
  GetDocumentById,
  UpdateDocumentList
) {
  let { row } = props;

  const handleChange = (noAplica, justificacion) => {
    let document = GetDocumentById(row);
    document = {
      ...document,
      noAplica: noAplica,
      justificacion: justificacion,
    };
    UpdateDocumentList(row, document);
  };

  return (
    <CheckJustifyCell
      {...props}
      disabled={disableForm || disable}
      handleChange={handleChange}
    />
  );
}

export function CellStatusProps(props, GetDocumentById) {
  return <CellEstatus {...props} />;
}

export function JustifyCell(
  props,
  disableForm,
  disable,
  GetDocumentById,
  UpdateDocumentList
) {
  let { row } = props;
  const handleChange = (value) => {
    let document = GetDocumentById(row);
    document = {
      ...document,
      justificacion: value,
    };

    UpdateDocumentList(row, document);
  };

  return (
    <CellComentarios
      {...props}
      disabled={disableForm || disable}
      handleChange={handleChange}
    />
  );
}

function getDate() {
  const fecha = new Date();
  const dia = String(fecha.getDate()).padStart(2, "0");
  const mes = String(fecha.getMonth() + 1).padStart(2, "0"); // Los meses empiezan desde 0
  const año = fecha.getFullYear();

  return `${dia}/${mes}/${año}`;
}

function getPath(row) {
  let path = "";
  function findfile(archivo) {
    let { archivos, carpeta, tamanoMb } = archivo;
    if (Array.isArray(archivos) && archivos.length > 0) {
      archivos.forEach((archivo) => {
        if (!path) findfile(archivo);
      });
      // findfile(archivos[0]);
    } else if (carpeta && tamanoMb) {
      path = carpeta;
    }
  }

  if (row?.originalSubRows && row.originalSubRows.length > 0) {
    if (_.isArray(row.originalSubRows)) {
      row.originalSubRows.forEach((subRow) => {
        if (!path) findfile(subRow);
      });
    }
  }

  return path;
}

function IsParentRow(row) {
  return row?.getCanExpand() || row?.getIsExpanded();
}

function cutText(text, word) {
  if (typeof text !== "string" || typeof word !== "string") {
    throw new Error("Both parameters must be strings.");
  }

  if (text.length === 0 || word.length === 0) {
    return text; // If the text or the word is empty, return the complete text.
  }

  const wordIndex = text.toLowerCase().indexOf(word.toLowerCase());
  if (wordIndex !== -1) {
    const endWord = wordIndex + word.length;
    return text.substring(0, endWord);
  } else {
    return text; // If the word is not found, return the complete text.
  }
}

function base64ToPdfBlobUrl(base64) {
  const byteCharacters = atob(base64);
  const byteArrays = [];

  const sliceSize = 512;
  for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
    const slice = byteCharacters.slice(offset, offset + sliceSize);
    const byteNumbers = new Array(slice.length);

    for (let i = 0; i < slice.length; i++) {
      byteNumbers[i] = slice.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    byteArrays.push(byteArray);
  }

  const blob = new Blob(byteArrays, { type: "application/pdf" });
  const blobUrl = URL.createObjectURL(blob);
  return blobUrl;
}
function getPathBeforeLastSlash(path) {
  // Encuentra la posición del último "/"
  const lastSlashIndex = path.lastIndexOf("/");

  // Si no hay "/" en el string, devuelve el string completo
  if (lastSlashIndex === -1) {
    return path;
  }

  // Corta el string hasta la última "/"
  return path.substring(0, lastSlashIndex);
}
export function ActionDocsCellProps(
  props,
  disableForm,
  GetDocumentById,
  UpdateDocumentList,
  DeleteDocument,
  handleShowMessage,
  handeShowPdf,
  handleSatCloud,
  InfoComites,
  handleDownload
) {
  const [isLoading, setIsLoading] = useState(false);
  let { row } = props;
  let { date, ruta } = row.original;
  let nombre = row?.original["nombre"];
  const dispatch = useDispatch();

  const handleEdit = () => {};

  //#region Update File

  const handleFileChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      // Obtener la ruta del archivo
      let filePath = event.target.value;
      // setFilePath(filePath);

      // Obtener el tipo de archivo
      let fileType = file.type;
      // setFileType(fileType);

      // Leer el archivo y convertirlo a Base64
      let reader = new FileReader();
      let size = (file.size / 1024 / 1024).toFixed(3) + " MB";
      reader.onload = (e) => {
        let base64String = e.target.result.split(",")[1];
        let document = GetDocumentById(row);
        let date = getDate();

        let newDocument = {
          ...document,
          base64: base64String,
          extension: filePath,
          type: fileType,
          date: date,
          size: size,
          nameFile: file.name,
        };
        UpdateDocumentList(row, newDocument);

        // setBase64String(base64String);
      };
      reader.onerror = () => {
        // alert('Error al leer el archivo.');
      };
      reader.readAsDataURL(file);
    }
  };
  const fileInputRef = useRef(null);

  const [showModalReplace, setShowModalReplace] = useState(false);
  const [messageApprove, setMessageApprove] = useState("");

  const handleShowReplaceModal = () => {
    setShowModalReplace(true);
  };

  const handleCloseReplace = () => {
    setShowModalReplace(false);
  };

  const handleUpdate = () => {
    let documentData = GetDocumentById(row);
    let { path, base64, ruta, size } = documentData;
    if (path || base64 || ruta || size) {
      setMessageApprove(
        "Se sustituirá el archivo previamente cargado. ¿Desea continuar?" //MSG010
      );
      handleShowReplaceModal();
    }
    if (!path && !base64) {
      LoadFile();
    }
  };

  const LoadFile = () => {
    handleCloseReplace();
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };
  //#endregion

  //#region deleteFile

  const [showModalDelete, setShowModalDelete] = useState(false);
  const messageDelete = GESTION_DOCUMENTAL.MSG001; //MSG001

  const handleShowDelete = () => {
    if (date) setShowModalDelete(true);
  };
  const handleCloseDelete = () => {
    setShowModalDelete(false);
  };

  const handleDelete = () => {
    handleCloseDelete();
    DeleteDocument(row);
  };
  //#endregion

  //#region SatCloud

  //#endregion



  //#region Show Pdf
  const [documentData, setDocumentData] = useState(
    "" //"https://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf"
  ); //pdf dummy

  const handleShow = () => {
    if (ruta.includes(".pdf")) {
      setIsLoading(true);
      dispatch(
        DescargarArchivo({
          path: ruta,
        })
      ).then((response) => {
        setIsLoading(false);
        if (response.error) {
          handleShowMessage(
            "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
          );
        } else {
          let url = base64ToPdfBlobUrl(response.payload); //response.payload;
          handeShowPdf(url);
        }
      });
    }
  };

  useEffect(() => {
    if (documentData) {
    }
  });
  //#endregion
  let pathFolder = "";
  let isParent = IsParentRow(row);
  if (isParent) {
    let _path = getPath(row);
    if (_path) pathFolder = _path;
  }
  return (
    <>
      {isLoading && <Loader />}
      <ActionDocsCell
        {...props}
        handleShow={handleShow}
        handleDrop={handleShowDelete}
        handleEdit={handleEdit}
        handleUpdate={handleUpdate}
        handleSatCloud={handleSatCloud}
        handleDownload={handleDownload}
        disabled={disableForm}
        pathFolder={pathFolder}
      />


      <FormControl
        ref={fileInputRef}
        onChange={handleFileChange}
        type="file"
        style={{ display: "none" }} // Ocultamos el FormControl
      />
      <BasicModal
        size="md"
        handleApprove={LoadFile}
        handleDeny={handleCloseReplace}
        denyText={"No"}
        approveText={"Sí"}
        show={showModalReplace}
        title="Mensaje"
        onHide={handleCloseReplace}
      >
        {messageApprove}
      </BasicModal>
      <BasicModal
        size="md"
        handleApprove={handleDelete}
        handleDeny={handleCloseDelete}
        denyText={"No"}
        approveText={"Sí"}
        show={showModalDelete}
        title="Mensaje"
        onHide={handleCloseDelete}
      >
        {messageDelete}
      </BasicModal>
    </>
  );
}
