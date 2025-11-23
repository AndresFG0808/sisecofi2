import React, { useCallback, useEffect, useState } from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { Row, Col, Button } from "react-bootstrap";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import { useDispatch, useSelector } from "react-redux";
import {
  GetPlantillaById,
  GetPlantillaDocumentos,
  CargarArchivos,
  ActualizarArchivos,
  BorrarArchivos,
  GetSatCloud,
  DescargarFolder,
  GetInfoComites,
  GetDetalleProyecto,
  GetInfoComiteFiles,
} from "../../../../../store/infoComites/infoComitesActions";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { ModalSatCloud } from "../../../../../components";
import { VerDocumento, Loader } from "../../../../../components";
import { GetColumns } from "./EstructuraDocumentalColumns";
import { ADMINISTRAR_INFO_COMITES } from "../../../../../constants/messages";
import { DownloadFileBase64 } from "../Components/DownloadFile";
import { useToast } from "../../../../../hooks/useToast";
import { GESTION_DOCUMENTAL } from "../../../../../constants/messages";
import Authorization from "../../../../../components/Authorization";
import _ from "lodash";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { useFormikContext } from "formik";

export default function EstructuraDocumentalTable2({
  disableForm = false,
  disableDocsButtons = false,
  handleClose,
  handleCloseConfirm,
  showMessage,
  isEdit = false,
  catalogos,
  idPlantillaOriginal = null,
}) {
  //#region Data inicial

  const { values, setFieldValue } = useFormikContext();
  const { getMessageExists } = useErrorMessages(GESTION_DOCUMENTAL);

  const { proyecto } = useSelector((state) => state.proyectos);

  const [idProyecto, setIdProyecto] = useState(null);

  useEffect(() => {
    if (proyecto?.idProyecto) {
      setIdProyecto(proyecto.idProyecto);
    } else if (proyecto?.proyecto?.idProyecto) {
      setIdProyecto(proyecto.proyecto.idProyecto);
    }
  }, [proyecto]);

  const { errorToast } = useToast();

  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch();

  const [dataTable, setDataTable] = useState([]);
  const [plantillaVigente, setPlantillaVigente] = useState("");

  const SetPlantillaInfo = useCallback((plantillaInfo) => {
    if (Array.isArray(plantillaInfo?.carpetas)) {
      let { idPlantillaVigente } = plantillaInfo;
      setPlantillaVigente(idPlantillaVigente);
      let processData = moveArchivosToSubCarpetas(plantillaInfo);
      setDataTable([...processData]);
    }
  }, []);

  const GetPlantillaIdComite = useCallback(
    (idComiteProyecto) => {
      setIsLoading(true);
      dispatch(GetPlantillaDocumentos(idComiteProyecto))
        .then((response) => {
          setIsLoading(false);
          if (response?.payload) {
            let data = JSON.parse(JSON.stringify(response.payload));
            let {
              archivoOtrosDocumentos,
              archivosPlantillaComite,
              arhivoOtrosDocumentosExterno,
              carpetasPlantilla,
            } = data;
            let processData = moveArchivosToSubCarpetas(
              carpetasPlantilla,
              archivosPlantillaComite,
              archivoOtrosDocumentos,
              arhivoOtrosDocumentosExterno
            );
            setDataTable(processData);
          }
        })
        .catch((error) => {});
    },
    [dispatch]
  );

  const GetPlantillaIdPlantilla = useCallback(
    (idPlantilla) => {
      setIsLoading(true);
      dispatch(GetPlantillaById(idPlantilla))
        .then((response) => {
          setIsLoading(false);
          if (response.error) {
          } else if (response?.payload) {
            let data = JSON.parse(JSON.stringify(response.payload));
            let { carpetasPlantilla, idPlantillaVigente } = data;
            setPlantillaVigente(idPlantillaVigente);
            if (
              Array.isArray(carpetasPlantilla) &&
              carpetasPlantilla.length > 0
            ) {
              let processData = moveArchivosToSubCarpetas(carpetasPlantilla);
              setDataTable(processData);
            }
          }
        })
        .catch((error) => {});
    },
    [dispatch]
  );

  const [lastIdPlantlla, setLastIdPlantilla] = useState(null);
  const [documentList, setDocumentList] = useState([]);
  let { idComiteProyecto, idPlantilla, informacionArchivos } = values;

  const [comitePath, setComitePath] = useState(null);

  function cutText(text, word) {
    if (typeof text !== "string" || typeof word !== "string") {
      throw new Error("Both parameters must be strings.");
    }

    if (text.length === 0 || word.length === 0) {
      return text; // If the text or the word is empty, return the complete text.
    }

    const wordIndex = text.toLowerCase().lastIndexOf(word.toLowerCase());
    if (wordIndex !== -1) {
      const endWord = wordIndex;
      return text.substring(0, endWord);
    } else {
      return text; // If the word is not found, return the complete text.
    }
  }

  useEffect(() => {
    let files = _.isArray(informacionArchivos)
      ? informacionArchivos.filter((s) => s.tamanoMb && s.tamanoMb > 0)
      : [];
    let hasDocuments = Array.isArray(informacionArchivos) && files.length > 0;
    if (hasDocuments) {
      let { carpeta } = files[0];
      setComitePath(cutText(carpeta, "/"));
    }
    if (!hasDocuments) {
      setComitePath(null);
    }
  }, [informacionArchivos]);

  useEffect(() => {
    // let hasDocuments =
    // Array.isArray(informacionArchivos) &&
    // informacionArchivos.filter((s) => s.tamanoMb && s.tamanoMb > 0).length >
    //   0;
    let hasRecords = !_.isEmpty(informacionArchivos);

    if (
      idPlantillaOriginal?.toString() === idPlantilla?.toString() &&
      hasRecords
    ) {
      setDocumentList([]);
      GetPlantillaIdComite(idComiteProyecto);
      setLastIdPlantilla(null);
    } else if (idPlantilla) {
      if (lastIdPlantlla !== idPlantilla) {
        setLastIdPlantilla(idPlantilla);
        setDocumentList([]);
        GetPlantillaIdPlantilla(idPlantilla);
      }
    } else {
      setDocumentList([]);
      setLastIdPlantilla(null);
      setDataTable([]);
    }
  }, [
    GetPlantillaIdComite,
    GetPlantillaIdPlantilla,
    lastIdPlantlla,
    idPlantillaOriginal,
    informacionArchivos,
    idPlantilla,
    idComiteProyecto,
  ]);

  //#endregion

  //#region Utilities
  let IsEmptyArray = (list) => {
    return !Array.isArray(list) || list.length === 0;
  };

  //#endregion

  //#region Documents Functions

  const SubRowsName = "archivos";

  const GetRowData = (row) => {
    let route = [];
    let rutaCarpetas = [];
    // let idCarpetaPlantillaComite = null;
    let id = null;
    let nivel = null;

    if (row) {
      let parent = row.getParentRow();
      nivel = parent?.original["nivel"];

      id = row.id;
    }
    return {
      ...row.original,
      nivel,
      route,
      rutaCarpetas,
      // idCarpetaPlantillaComite,
      id,
    };
  };

  const GetDocumentById = (row) => {
    let docRow = GetRowData(row);
    let { idArchivoPlantilla, route, idArchivoPlantillaComite, id } = docRow;

    let document = documentList.find((s) => id === s.idTable);
    if (!document) {
      document = {
        ...docRow,
        idArchivoPlantilla,
        idArchivoPlantillaComite,
        route,
        base64: null,
        path: "",
        extension: "",
        type: "",
        erased: false,
        idTable: id,
      };
    }
    return document;
  };

  const UpdateDocumentList = (row, document, updateDataTable = true) => {
    let { id } = GetRowData(row);

    var _documentList = [...documentList];
    let indexDoc = _documentList.findIndex((s) => id === s.idTable);
    if (indexDoc !== -1) {
      _documentList[indexDoc] = { ...document, erased: false };
    }
    if (indexDoc === -1) {
      _documentList.push(document);
    }
    setDocumentList(_documentList);
    if (updateDataTable) {
      UpdateDataTable(_documentList);
    }
  };

  const GetInfoFiles = () => {
    dispatch(GetInfoComiteFiles(idComiteProyecto)).then((response) => {
      if (response?.payload?.data) {
        let { informacionArchivos, informacionArchivosOtrosDocumentos } =
          response.payload.data;
        let infoFiles = [
          ...informacionArchivos,
          ...informacionArchivosOtrosDocumentos,
        ];
        setFieldValue("informacionArchivos", infoFiles);
      }
    });
  };

  const DeleteDocument = (row) => {
    let { id, ruta, idArchivoPlantillaComite, idArchivoPlantilla } =
      GetRowData(row);

    let dataToDelete = {
      idComiteProyecto,
      idArchivoPlantillaComite,
      path: ruta,
    };
    if (!idArchivoPlantilla) {
      dataToDelete.idArchivoOtrosDocumentos = idArchivoPlantillaComite;
      dataToDelete.idArchivoPlantillaComite = null;
    }

    setIsLoading(true);
    dispatch(BorrarArchivos({ archivosEliminados: [dataToDelete] })).then(
      (response) => {
        setIsLoading(false);
        if (response.error) {
          CloseError(response);
        } else {
          GetInfoFiles();
          var _documentList = [...documentList];
          let indexDoc = _documentList.findIndex((s) => id === s.idTable);
          if (indexDoc !== -1) {
            let document = {
              ..._documentList[indexDoc],
              erased: true,
              justificacion: "",
              tamanoMb: null,
              ruta: "",
              noAplica: false,
              date: "",
              // idArchivoPlantillaComite: null,
            };
            _documentList[indexDoc] = document;
          } else {
            let doc = GetDocumentById(row);
            _documentList.push({
              ...doc,
              erased: true,
              justificacion: "",
              tamanoMb: null,
              ruta: "",
              noAplica: false,
              date: "",
              // idArchivoPlantillaComite: null,
            });
          }
          setDocumentList(_documentList);
          UpdateDataTable(_documentList);
          showMessage(GESTION_DOCUMENTAL.MSG002);
        }

        dispatch(GetInfoComites(idProyecto));
        dispatch(GetDetalleProyecto(idProyecto));
      }
    );
  };

  //#endregion

  //#region Set Info Table

  const IsEmptyList = (list) => !Array.isArray(list) || list.length === 0;

  const UpdateDataTable = useCallback(
    (documents) => {
      let hasData = !IsEmptyList(dataTable);
      if (hasData) {
        let _dataTable = [...dataTable];
        documents.forEach((doc) => {
          let {
            erased,
            otherFile,
            idTable,
            idArchivoOtrosDocumentosComite,
            nombre,
          } = doc;

          let folder = null;
          let fileIndex;

          if (idTable) {
            let _route = idTable.split(".");

            _route.forEach((idRow, index) => {
              if (index === 0) {
                folder = dataTable[idRow];
              } else if (folder?.[SubRowsName]?.[idRow]?.[SubRowsName]) {
                folder = folder[SubRowsName][idRow];
              } else {
                fileIndex = idRow;
              }
            });
          }

          let hasFiles = folder && !IsEmptyList(folder[SubRowsName]);

          if (!hasFiles) {
            folder[SubRowsName] = [];
          }
          //tiene archivos

          let files = [...folder[SubRowsName]];

          if (fileIndex === -1 && otherFile) {
            files.push(doc);
          }
          //actualzar valor del archivo
          if (fileIndex !== -1) {
            if (!erased) {
              let _file = {
                ...files[fileIndex],
                ...doc,
              };
              files[fileIndex] = _file;
            }

            if (erased) {
              if (otherFile || idArchivoOtrosDocumentosComite) {
                files.splice(fileIndex, 1);
              } else {
                let _nombre = nombre.includes("_")
                  ? nombre.split("_")[0]
                  : nombre;

                let _file = {
                  ...files[fileIndex],
                  noAplica: false,
                  path: null,
                  ruta: null,
                  date: null,
                  size: null,
                  justificacion: null,
                  rutaCarpetas: null,
                  base64: null,
                  tamanoMb: null,
                  nombre: _nombre,
                  isLoadFile: false,
                  // idArchivoPlantillaComite: null,
                };
                files[fileIndex] = _file;
              }
            }
          }
          //actualizar folder
          folder[SubRowsName] = files;
        });

        setDataTable(_dataTable);
      }
    },
    [dataTable]
  );

  //#endregion

  //#region Columns

  const [modalMesssage, setModalMessage] = useState("");
  const [showMessageModal, setShowMessageModal] = useState(false);
  const handleShowMessage = (message) => {
    setShowMessageModal(true);
    setModalMessage(message);
  };
  const handleCloseMessage = () => {
    setModalMessage("");
    setShowMessageModal(false);
  };

  const [documentData, setDocumentData] = useState("");
  const [showVerDoc, setShowVerDoc] = useState(false);

  const handeShowPdf = (url) => {
    setDocumentData(url);
    setShowVerDoc(true);
  };
  const handleCloseShowPdf = () => {
    setDocumentData("");
    setShowVerDoc(false);
  };

  const [showSatCloud, setShowSatCloud] = useState(false);
  const [dataSatCloud, setDataSatCloud] = useState({});
  const showModalSatCloud = () => setShowSatCloud(true);
  const closeModalSatCloud = () => setShowSatCloud(false);

  let handleSatCloud = (data) => {
    if (data) {
      setIsLoading(true);
      dispatch(GetSatCloud({ path: data }))
        .then((data) => {
          setIsLoading(false);
          let { temporal, url } = data.payload;
          if (temporal && url) {
            showModalSatCloud();
            setDataSatCloud({ password: temporal, url });
          } else {
            handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG012);
          }
        })
        .catch((error) => {
          setIsLoading(false);
          handleShowMessage(ADMINISTRAR_INFO_COMITES.MSG012);
        });
    }
  };
  const handleDownload = (path) => {
    if (path) {
      setIsLoading(true);
      dispatch(
        DescargarFolder({
          path: path,
        })
      ).then((data) => {
        setIsLoading(false);
        if (data.error) {
          handleShowMessage(GESTION_DOCUMENTAL.MSG007);
        } else if (data?.payload)
          DownloadFileBase64(data.payload, "Descarga.zip", "application/zip");
      });
    }
  };

  const [infoComites, setInfoComites] = useState(values);
  useEffect(() => {
    setInfoComites(values);
  }, [values]);
  const columns = GetColumns(
    documentList,
    idComiteProyecto,
    disableForm,
    GetDocumentById,
    UpdateDocumentList,
    DeleteDocument,
    handleShowMessage,
    handeShowPdf,
    handleSatCloud,
    infoComites,
    setIsLoading,
    handleDownload
  );
  //#endregion

  //#region Save Info

  function ValidateDataToSave(documentList) {
    let { idComiteProyecto, acuerdo } = values;

    let updatedDocs = documentList.filter((s) => !s.erased);
    let erasedDocs = documentList.filter((s) => s.erased && s.ruta);

    let dataToDelete = erasedDocs.map((document) => {
      let { ruta, idArchivoPlantillaComite } = document;
      return {
        idComiteProyecto,
        idArchivoPlantillaComite,
        path: ruta,
      };
    });
    let dataToSave = [];
    let dataToUpdate = [];

    updatedDocs.forEach((document, index) => {
      let {
        nombre,
        noAplica = false,
        justificacion = "",
        base64 = null,
        nameFile,
        otherFile,
        idArchivoPlantillaComite,
        idArchivoPlantilla,
        idCarpetaPlantilla,
        descripcion,
        descripcionCarpeta,
        ruta = "",
        otherFileSaved
      } = document;
      let _document = null;

      if (nombre?.includes(" ")) nombre = nombre.replaceAll(" ", "_");

      if (!idArchivoPlantillaComite) {
        let _acuerdo = acuerdo ? acuerdo.replaceAll(" ", "_") : acuerdo;
        if (nameFile) {
          if (nombre.includes(".")) {
            nombre = nombre.split(".")[0];
          }
          let _nameFile = nameFile.split(".");
          if (_nameFile.length > 1)
            nombre = nombre + "_" + _acuerdo + "." + _nameFile[1];
        }

        _document = {
          descripcion,
          nombre,
          noAplica,
          justificacion,
          idArchivoPlantilla,
          idCarpetaPlantilla,
          archivoCargado: base64 ?? null,
          archivoCargadoOtrosDocumentos: base64 ?? null,
          otherFile,
          descripcionCarpeta,
        };
        if (otherFile) {
          delete _document.archivoCargado;
          delete _document.idArchivoPlantilla;
        }
        if (!otherFile) {
          delete _document.archivoCargadoOtrosDocumentos;
          delete _document.idCarpetaPlantilla;
        }
        dataToSave.push(_document);
      }

      if (idArchivoPlantillaComite) {
        if (nameFile) {
          let extension = nameFile.split(".")[1];
          let _nombre = nombre.split(".")[0];
          nombre = `${_nombre}.${extension}`;
        }
        if (otherFileSaved) {
          _document = {
            idArchivoPlantillaComite,
            descripcion,
            nombre,
            noAplica,
            justificacion,

            idCarpetaPlantilla,
            archivoCargadoOtrosDocumentos: base64 ?? null,
            otherFile:otherFileSaved,
            descripcionCarpeta,
          };
          dataToSave.push(_document);
        } else {
          _document = {
            descripcion,
            idComiteProyecto,
            idPlantillaVigente: plantillaVigente,
            idArchivoPlantillaComite,
            descripcionCarpeta,
            archivoPlantillaComiteDto: {
              nombre,
              ruta,
              noAplica,
              justificacion,
            },
            archivoCargado: base64 ?? null,
            archivoCargadoOtrosDocumentos: base64 ?? null,
            otherFile,
          };
          dataToUpdate.push(_document);
        }
      }
    });

    return {
      idComiteProyecto,
      plantillaVigente,
      dataToDelete,
      dataToSave,
      dataToUpdate,
    };
  }

  const CloseOk = () => {
    setIsLoading(false);
    GetInfoFiles();
    setDocumentList([]);
    GetPlantillaIdComite(idComiteProyecto);
    setLastIdPlantilla(null);
    dispatch(GetInfoComites(idProyecto));
    dispatch(GetDetalleProyecto(idProyecto));
    showMessage(GESTION_DOCUMENTAL.MSG005);
    // handleClose();
  };

  const errorValidations = (errorMessage) => {
    if (getMessageExists(errorMessage)) {
      handleShowMessage(errorMessage);
    } else {
      handleShowMessage("Ocurrió un error");
    }
  };
  const CloseError = (responsePayload) => {
    let { response } = responsePayload.payload;
    const message =
      _.isArray(response?.data?.mensaje) && !_.isEmpty(response.data.mensaje)
        ? response.data.mensaje[0]
        : response?.data?.mensaje;

    // GetInfoFiles();
    dispatch(GetInfoComites(idProyecto));
    errorValidations(message);
    setIsLoading(false);
  };

  async function UpdateDocuments() {
    if (documentList.length > 0) {
      let { idComiteProyecto, plantillaVigente, dataToSave, dataToUpdate } =
        ValidateDataToSave(documentList, true);

      const _CreateDocs = () => {
        let ArchivoPlantillaComiteDto = dataToSave.filter((s) => !s.otherFile);
        let ArchivoOtrosDocumentosDto = dataToSave.filter((s) => s.otherFile);
        dispatch(
          CargarArchivos({
            archivosCargados: [
              {
                idComiteProyecto,
                idPlantillaVigente: plantillaVigente,
                archivoPlantillaComiteDto: ArchivoPlantillaComiteDto,
                archivoOtrosDocumentosDto: ArchivoOtrosDocumentosDto,
              },
            ],
          })
        ).then((response) => {
          if (response.error) {
            CloseError(response);
          } else {
            if (dataToUpdate.length > 0) {
              _ActualizarDocs();
            } else {
              CloseOk();
            }
          }
        });
      };

      const _ActualizarDocs = () => {
        dispatch(
          ActualizarArchivos({
            archivosActualizados: [...dataToUpdate],
          })
        ).then((response) => {
          if (response.error) {
            CloseError(response);
          } else {
            CloseOk();
          }
        });
      };

      // if (dataToDelete.length > 0) {
      //   _BorrarDocs();
      // } else
      if (dataToSave.length > 0) {
        _CreateDocs();
      } else if (dataToUpdate.length > 0) {
        _ActualizarDocs();
      }
    } else {
      CloseOk();
    }
  }

  const SaveDocuments = () => {
    // let hasErrors = ValidateRequeridos(dataTable);
    // if (hasErrors) {
    //   showMessage(GESTION_DOCUMENTAL.MSG004);
    // } else {
    setIsLoading(true);
    UpdateDocuments();
    // }
  };

  const CancelDocuments = () => {
    handleCloseConfirm(GESTION_DOCUMENTAL.MSG001);
  };
  //#endregion
  return (
    <>
      {isLoading && <Loader />}
      <Row className="mt-5">
        <Authorization process={"PROY_INFO_COM_DOWN"}>
          <Col md={12} className="text-end pe-4">
            <IconButton
              type={"download"}
              onClick={() => handleSatCloud(comitePath)}
              tooltip={"SATCloud"}
              disabled={!comitePath}
            />
            <IconButton
              type={"zip"}
              onClick={() => handleDownload(comitePath)}
              tooltip={"Descarga masiva"}
              disabled={!comitePath}
            />
          </Col>
        </Authorization>
      </Row>
      <TablaEditable
        dataTable={dataTable}
        columns={columns}
        header={"Estructura documental"}
        hasPagination
        subRows={SubRowsName}
      />
      <Col md={12} className="text-end">
        <Button
          variant="red"
          className="btn-sm ms-2 waves-effect waves-light"
          type="button"
          onClick={CancelDocuments}
          disabled={disableDocsButtons}
        >
          Cancelar
        </Button>
        <Button
          variant="green"
          className="btn-sm ms-2 waves-effect waves-light"
          type="button"
          disabled={disableDocsButtons}
          onClick={SaveDocuments}
        >
          Guardar
        </Button>

        <SingleBasicModal
          size="md"
          approveText={"Aceptar"}
          show={showMessageModal}
          title="Mensaje"
          onHide={handleCloseMessage}
        >
          {modalMesssage}
        </SingleBasicModal>
        <VerDocumento
          title={"Ver PDF"}
          show={showVerDoc}
          urlPdfBlob={documentData}
          onHide={handleCloseShowPdf}
        />
        <ModalSatCloud
          show={showSatCloud}
          handleClose={closeModalSatCloud}
          url={dataSatCloud?.url ?? ""}
          password={dataSatCloud?.password ?? ""}
        />
      </Col>
    </>
  );
}

function formatearFecha(fechaISO) {
  const fecha = new Date(fechaISO);
  const dia = String(fecha.getDate()).padStart(2, "0");
  const mes = String(fecha.getMonth() + 1).padStart(2, "0"); // Los meses comienzan en 0
  const año = fecha.getFullYear();

  return `${dia}/${mes}/${año}`;
}

function removeExtension(filename) {
  const lastDotIndex = filename.lastIndexOf(".");
  if (lastDotIndex === -1) return filename;
  return filename.substring(0, lastDotIndex);
}

const validateOtrosArchivos = (archivos, idCarpetaPlantilla) => {
  if (!Array.isArray(archivos)) {
    return [];
  }

  if (idCarpetaPlantilla) {
    return archivos
      .filter((s) => s.idCarpetaPlantilla === idCarpetaPlantilla)
      .map((s) => {
        if (s?.nombre) {
          let _descripcion = removeExtension(s.nombre);
          let { tamanoMb = "", fechaModificacion = "" } = s;
          return {
            ...s,
            descripcion: _descripcion,
            obligatorio: false,
            idArchivoPlantillaComite: s.id,
            size: tamanoMb ? tamanoMb.toFixed(3) + " MB" : "",
            date: fechaModificacion ? formatearFecha(fechaModificacion) : "",
            otherFileSaved:true
          };
        }
        return s;
      });
  }

  return archivos.map((s) => {
    if (s?.nombre) {
      let _descripcion = removeExtension(s.nombre);
      let { tamanoMb = "", fechaModificacion = "" } = s;
      return {
        ...s,
        descripcion: _descripcion,
        obligatorio: false,
        idArchivoPlantillaComite: s.id,
        size: tamanoMb ? tamanoMb.toFixed(3) + " MB" : "",
        date: fechaModificacion ? formatearFecha(fechaModificacion) : "",
        otherFileSaved:true
      
      };
    }
    return s;
  });
};

function moveArchivosToSubCarpetas(
  carpetas,
  archivosPlantilla = [],
  archivosOtros = [],
  archivosOtrosExterno = []
) {
  function processCarpeta(carpeta) {
    let {
      idCarpetaPlantilla,
      archivos,
      descripcion: descripcionCarpeta,
    } = carpeta;
    let _archivos = [];
    if (archivos?.length > 0) {
      _archivos = archivos.map((archivo) => {
        let _archivo = { ...archivo };
        if (archivosPlantilla?.length > 0) {
          let { idArchivoPlantilla } = archivo;
          let archivoCargado = archivosPlantilla.find(
            (s) => s.idArchivoPlantilla === idArchivoPlantilla
          );

          if (archivoCargado) {
            let { tamanoMb = "", fechaModificacion = "" } = archivoCargado;

            return {
              ..._archivo,
              ...archivoCargado,
              idArchivoPlantillaComite: archivoCargado.id,
              size: tamanoMb ? tamanoMb.toFixed(3) + " MB" : "",
              date: fechaModificacion ? formatearFecha(fechaModificacion) : "",
              descripcion: _archivo.descripcion,
              descripcionCarpeta,
            };
          }
        }
        _archivo = { ..._archivo, idCarpetaPlantilla, descripcionCarpeta };

        return _archivo;
      });
    }
    // Mover archivos a las subcarpetas correspondientes
    if (idCarpetaPlantilla) {
      _archivos = Array.isArray(_archivos) ? _archivos : [];
      // Procesar las subcarpetas de forma recursiva
      _archivos = _archivos.map((subCarpeta) => {
        let { otrosDocumentos, _archivos } = subCarpeta;
        if (!otrosDocumentos && _archivos?.length > 0) {
          return processCarpeta(subCarpeta);
        }
        return subCarpeta;
      });

      _archivos.push({
        descripcion: "Otros documentos",
        otrosDocumentos: true,
        archivos: validateOtrosArchivos(archivosOtros, idCarpetaPlantilla),

        idCarpetaPlantilla,
        descripcionCarpeta,
      });
    }

    carpeta = {
      ...carpeta,
      archivos: _archivos,
    };
    return carpeta;
  }

  // Procesar todas las carpetas principales

  let _carpetas = carpetas.map(processCarpeta);

  _carpetas = [
    ..._carpetas,
    {
      descripcion: "Otros documentos",
      otrosDocumentos: true,
      archivos: validateOtrosArchivos(archivosOtrosExterno),
    },
  ];

  return _carpetas;
}


