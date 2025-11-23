import React, { useEffect, useMemo, useRef, useState } from "react";
import { Button, Col, FormCheck, Row } from "react-bootstrap";
import TextDisplay from "../../../../../../../components/LabelValue";
import IconButton from "../../../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../../../components/table/TablaEditable";
import BasicModal from "../../../../../../../modals/BasicModal";
import SingleBasicModal from "../../../../../../../modals/SingleBasicModal";
import {
  FormCheckCell,
  ActionsCell,
  ExpandedCell,
  Justificacion,
} from "../../../GestionDocumental/components";
import {
  Loader,
  ModalSatCloud,
  VerDocumento,
} from "../../../../../../../components";
import { Tooltip } from "../../../../../../../components/Tooltip";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import { GESTION_DOCUMENTAL } from "../../../../../../../constants/messages";
import { useToast } from "../../../../../../../hooks/useToast";
import { useParams, useSearchParams } from "react-router-dom";
import _ from "lodash";
import moment from "moment";
import { useErrorMessages } from "../../../../../../../hooks/useErrorMessages";

import {
  addUUIDsAndClone,
  base64ToPdfBlobUrl,
  downloadFileFromPath,
  extractSubRows,
  getTotalDocuments,
  isFaseType,
  updateRowFromSubRow,
} from "./utils";
import { RadialButton } from "../../../../../Components";
import {
  useGetEstructuraDocumentalConvenioQuery,
  usePostDescargaMasivaMutation,
  usePostDescargaSatCloudMutation,
  usePutCargarArchivoConvenioMutation,
  usePutDescargaArchivoConvenioMutation,
  usePutEliminarArchivoConvenioMutation,
} from "./store";
import { DownloadFileBase64 } from "../../../../../../../functions/utils/base64.utils";
import "./styles.css";
import { useGetAuthorization } from "../../../../../../../hooks/useGetAuthorization";

export function GestionDocumental({ isDetalle }) {
  const tableReference = useRef();
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(GESTION_DOCUMENTAL);
  const [dataT, setDataT] = useState([]);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const { idConvenio } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoConvenio = searchParams.get("cm");
  const [showSatCloudModal, setShowSatCloudModal] = useState(false);
  const [showJustificacionModal, setShowJustificacionModal] = useState(false);
  const [showDocumentoPDF, setShowDocumentoPDF] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showFilter, setShowFilter] = useState(false);
  const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [selectedRow, setSelectedRow] = useState();
  const [pdfDocumentData, setPdfDocumentData] = useState("");
  const [showDuplicatedFile, setShowDuplicatedFile] = useState(false);
  const [justificacionDisplay, setJustificacionDisplay] = useState("");
  const [isSaving, setIsSaving] = useState(false);
  const [initialValues, setInitialValues] = useState([]);
  const [cacheFile, setCacheFile] = useState();
  const [currentFilter, setCurrentFilter] = useState("");

  const { isAuthorized } = useGetAuthorization("CONT_CM_GD_ADMIN");
  const disabled = isDetalle || !isAuthorized;
  const [totalDocuments, setTotalDocuments] = useState({
    todos: 0,
    pendientes: 0,
    cargados: 0,
  });
  const [filterButtons, setFilterButtons] = useState([
    { id: "Todos", value: true },
    { id: "Pendientes", value: false },
    { id: "Cargados", value: false },
  ]);

  const { data: estructuraDocumental, isFetching } =
    useGetEstructuraDocumentalConvenioQuery(idConvenio || idNuevoConvenio, {
      skip:
        idConvenio !== undefined || idNuevoConvenio !== undefined
          ? false
          : true,
    });
  const [descargaMasiva, { isLoading: isLoadingDescargaMasiva }] =
    usePostDescargaMasivaMutation();
  const [
    descargaSat,
    { data: descargaSatCloud, isLoading: isLoadingDescargaSat },
  ] = usePostDescargaSatCloudMutation();
  const [cargarArchivo, { isLoading: isLoadingCargarArchivo }] =
    usePutCargarArchivoConvenioMutation();
  const [descargaArchivo, { isLoading: isLoadingDescargarArchivo }] =
    usePutDescargaArchivoConvenioMutation();
  const [eliminarArchivo, { isLoading: isLoadingEliminarArchivo }] =
    usePutEliminarArchivoConvenioMutation();

  useEffect(() => {
    if (estructuraDocumental) {
      const normalizedData = addUUIDsAndClone(estructuraDocumental);
      setDataT(normalizedData);
      setInitialValues(normalizedData);
      const dataToMemoized = extractSubRows(normalizedData);
      setMemoizedData(
        new Map(dataToMemoized.map((archivo) => [archivo.UUID, archivo]))
      );
      setTotalDocuments(() => getTotalDocuments(estructuraDocumental));
    }
  }, [estructuraDocumental]);

  const handleVisualizarArchivo = async (path) => {
    try {
      const formData = new FormData();
      formData.append("path", path);
      const res = await descargaArchivo({ data: formData }).unwrap();
      const pdfDocument = base64ToPdfBlobUrl(res);
      setPdfDocumentData(pdfDocument);
      setShowDocumentoPDF(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      const status = error?.response?.status;
      if (!!status && status == 504) {
        setSingleBasicMessage(
          "Ocurrió un timeout. Favor de intentar nuevamente."
        );
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };
  const handleDescargaArchivo = async (path) => {
    try {
      const formData = new FormData();
      formData.append("path", path);
      const res = await descargaArchivo({ data: formData }).unwrap();
      downloadFileFromPath(res, path);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      const status = error?.response?.status;
      if (!!status && status == 504) {
        setSingleBasicMessage(
          "Ocurrió un timeout. Favor de intentar nuevamente."
        );
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };

  const handleDescargaSatCloud = async (data) => {
    try {
      await descargaSat({ data }).unwrap();
      setShowSatCloudModal(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      const status = error?.response?.status;
      if (!!status && status == 504) {
        setSingleBasicMessage(
          "Ocurrió un timeout. Favor de intentar nuevamente."
        );
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };
  const handleDescargaMasiva = async (data) => {
    try {
      const res = await descargaMasiva({ data }).unwrap();
      DownloadFileBase64(res, "Descarga.zip", "application/zip");
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      const status = error?.response?.status;
      if (!!status && status == 504) {
        setSingleBasicMessage(
          "Ocurrió un timeout. Favor de intentar nuevamente."
        );
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };

  const onDescargaContratoMasiva = () => {
    handleDescargaMasiva({
      idConvenio: parseInt(idConvenio || idNuevoConvenio),
    });
  };

  const onDescargaContratoSatCloud = () => {
    handleDescargaSatCloud({
      idConvenio: parseInt(idConvenio || idNuevoConvenio),
    });
  };

  const onDescargaByRowSatCloud = (row) => {
    const { original } = row;
    handleDescargaSatCloud({
      idConvenio: parseInt(idConvenio || idNuevoConvenio),
      path: original.ruta,
    });
  };
  const onDescargaByRowMasiva = (row) => {
    const { original } = row;
    handleDescargaMasiva({
      idConvenio: parseInt(idConvenio || idNuevoConvenio),
      path: original.ruta,
    });
  };

  const onSelectFilterButton = (event) => {
    setCurrentFilter(event.target.id);
    setShowFilter(true);
  };
  const onVisualizarPdfByRow = (row) => {
    const { ruta } = row;
    handleVisualizarArchivo(ruta);
  };
  const onGetRowData = (row) => {
    setSelectedRow(row);
  };
  const onDeleteArchivo = (row) => {
    setSelectedRow(row);
    setShowDeleteModal(true);
  };
  const onChangeRadialForm = (currentFilter) => {
    const id = currentFilter?.toLowerCase();
    switch (id) {
      case "todos": {
        tableReference.current.setColumnFilters((prev) => []);
        onCancel();
        break;
      }
      case "pendientes": {
        tableReference.current.setColumnFilters((prev) => [
          { id: "extension", value: null },
          { id: "obligatorio", value: true },
        ]);
        onCancel();
        break;
      }
      case "cargados": {
        tableReference.current.setColumnFilters((prev) => [
          { id: "cargado", value: true },
        ]);
        onCancel();
        break;
      }
    }
  };
  const onCancel = () => {
    setDataT([...initialValues]);
    tableReference?.current?.table.toggleAllRowsSelected(false);
    tableReference?.current?.table.toggleAllRowsExpanded(false);
  };
  const onSubmitDeleteRow = async () => {
    try {
      const { original, parentId } = selectedRow;
      const data = [
        {
          ...original,
          ...(dataT[parseInt(parentId.split("")[0])]?.descripcion ===
          "Otros Documentos"
            ? { type: "tipoConvenio" }
            : isFaseType(original.ruta)
            ? { type: "tipoFaseConvenio" }
            : { type: "tipoPlantillaConvenio" }),
        },
      ];
      await eliminarArchivo({ data }).unwrap();
      setSingleBasicMessage(GESTION_DOCUMENTAL.MSG002);
      setShowSingleBasicModal(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      const status = error?.response?.status;
      if (!!status && status == 504) {
        setSingleBasicMessage(
          "Ocurrió un timeout. Favor de intentar nuevamente."
        );
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };

  const onSubmit = async () => {
    try {
      const requestsToPost = [];
      const allSubRows = extractSubRows(dataT);

      const archivosAEnviar = allSubRows.reduce((prevRows, row) => {
        const memoizedRow = memoizedData.get(row.UUID);
        if (!row.id && !row.isNewRow) return prevRows;
        if (row.isNewRow) {
          prevRows.push(row);
          return prevRows;
        }
        if (!_.isEqual(row, memoizedRow)) {
          prevRows.push(row);
          return prevRows;
        }
        return prevRows;
      }, []);
      if (
        archivosAEnviar?.some(
          (archivo) =>
            !!!archivo?.descripcion ||
            (!!!archivo?.justificacion && archivo?.noAplica)
        )
      ) {
        errorToast(GESTION_DOCUMENTAL.MSG004);
        return;
      }
      setIsSaving(true);
      archivosAEnviar?.forEach((archivo) => {
        const formData = new FormData();
        if (!archivo?.isNewRow) {
          formData.append("idArchivo", archivo?.id);
        } else {
          formData.append("idArchivo", null);
        }
        if (archivo?.fase === "Otros Documentos") {
          formData.append("type", "tipoConvenio");
        } else if (isFaseType(archivo.carpeta)) {
          formData.append("type", "tipoFaseConvenio");
        } else {
          formData.append("type", "tipoPlantillaConvenio");
        }
        if (archivo?.file) {
          formData.append("file", archivo?.file);
        }
        if (archivo?.justificacion) {
          formData.append("justificacion", archivo?.justificacion);
        }
        formData.append("nombreFase", archivo?.fase);
        formData.append("idContrato", idConvenio || idNuevoConvenio);
        formData.append("obligatorio", archivo?.obligatorio);
        formData.append("noAplica", archivo?.noAplica);
        formData.append("carpeta", archivo?.carpeta);
        formData.append("descripcion", archivo?.descripcion);
        requestsToPost.push(cargarArchivo({ data: formData }).unwrap());
      });

      await Promise.all(requestsToPost);
      setIsSaving(false);
      setSingleBasicMessage(GESTION_DOCUMENTAL.MSG005);
      setShowSingleBasicModal(true);
    } catch (error) {
      setIsSaving(false);
      const status = error?.response?.status;
      const mensaje = error?.data?.mensaje?.[0];
      if (!!status && status == 504) {
        setSingleBasicMessage(
          "Ocurrió un timeout. Favor de intentar nuevamente."
        );
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "descripcion",
        header: "Descripción",
        cell: (props) => (
          <ExpandedCell
            getValue={props.getValue}
            table={props.table}
            row={props.row}
            callback={(path) => {
              handleDescargaArchivo(path);
            }}
            editable={disabled}
          />
        ),
        footer: (props) => props.column.id,
        filterFn: (row, columnId, filterValue) => {
          return row
            .getValue(columnId)
            .toLowerCase()
            .trim()
            .includes(filterValue.toLowerCase().trim());
        },
      },
      {
        accessorKey: "obligatorio",
        header: "Requerido",
        cell: (props) =>
          props.getValue() !== undefined ? (
            <div className="check-box-black">
              <FormCheck checked={props.getValue()} onChange={(e) => {}} />
            </div>
          ) : null,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "noAplica",
        header: "No aplica",
        cell: (props) => (
          <FormCheckCell
            editable={disabled}
            column={props.column}
            row={props.row}
            table={props.table}
            getValue={props.getValue}
            callback={() => {
              if (!props.getValue()) {
                setJustificacionDisplay("");
                setSelectedRow({
                  ...props.row,
                  noAplica: true,
                  obligatorio: true,
                  estatus: false,
                  justificacion: props.row.original.justificacion,
                });
                setJustificacionDisplay(props.row.original.justificacion);
                setSingleBasicMessage(GESTION_DOCUMENTAL.MSG003);
                setShowSingleBasicModal(true);
              }
            }}
          />
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) =>
          props.getValue() !== undefined ? (
            !props.row.original.cargado && props.row.original.obligatorio ? (
              <Tooltip text={"Pendiente de carga"}>
                <span>
                  <FontAwesomeIcon
                    icon={faTriangleExclamation}
                    style={{ color: "#FFCC00" }}
                    size="lg"
                  />
                </span>
              </Tooltip>
            ) : null
          ) : null,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "justificacion",
        header: "Justificación",
        cell: (props) => (
          <>
            {!!props.row.original.justificacion ||
            !!props.row.original.noAplica ? (
              <Tooltip text={"Justificación"}>
                <button
                  style={{ border: "1px solid transparent" }}
                  onClick={() => {
                    setSelectedRow({
                      ...props.row,
                      noAplica: props.row.original.noAplica,
                      estatus: props.row.original.noAplica,
                      obligatorio: props.row.original.obligatorio,
                    });
                    setJustificacionDisplay(props.getValue());
                    setShowJustificacionModal(true);
                  }}
                >
                  ...
                </button>
              </Tooltip>
            ) : null}
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "tamanoMb",
        header: "Tamaño",
        cell: (props) => (
          <span>
            {props.getValue()
              ? parseFloat(props.getValue()).toFixed(3) + "MB"
              : null}
          </span>
        ),
        footer: (props) => props.column.id,
        enableColumnFilter: false,
      },
      {
        accessorKey: "fechaModificacion",
        header: "Fecha última modificación",
        cell: (props) => (
          <span>
            {props.getValue()
              ? moment(props.getValue()).format("DD/MM/YYYY")
              : null}
          </span>
        ),
        footer: (props) => props.column.id,
        filterFn: (row, columnId, inputValue) => {
          return moment(row.getValue(columnId))
            .format("DD/MM/YYYY")
            .includes(inputValue);
        },
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <>
            <ActionsCell
              table={props.table}
              editable={disabled}
              getValue={props.getValue}
              row={props.row}
              onDownloadSat={onDescargaByRowSatCloud}
              onDownloadMasiva={onDescargaByRowMasiva}
              onShowFile={onVisualizarPdfByRow}
              onDelete={onDeleteArchivo}
              callback={(newDocument) => {
                setSelectedRow(props.row);
                setCacheFile(newDocument);
                setShowDuplicatedFile(true);
              }}
            />
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "cargado",
        header: "",
        cell: (props) => <></>,
        enableColumnFilter: false,
        enableSorting: false,
      },
      {
        accessorKey: "extension",
        header: "",
        cell: (props) => <></>,
        enableColumnFilter: false,
        enableSorting: false,
      },
    ],
    []
  );

  return (
    <>
      <>
        {isFetching ||
        isSaving ||
        isLoadingDescargarArchivo ||
        isLoadingDescargaMasiva ||
        isLoadingDescargaSat ||
        isLoadingCargarArchivo ||
        isLoadingEliminarArchivo ? (
          <Loader zIndex={`${isFetching || isSaving ? "10" : "9999"}`} />
        ) : null}
        <Row>
          <Col>
            <TextDisplay value={"Estatus de carga:"} />
          </Col>
        </Row>
        <Row>
          {filterButtons.map((optionFilter) => (
            <>
              <RadialButton
                onClick={onSelectFilterButton}
                label={optionFilter?.id}
                id={`${optionFilter?.id}`}
                key={`${optionFilter?.id}`}
                group={"filters"}
                checked={optionFilter.value}
              />
            </>
          ))}
        </Row>
        <Row>
          <Col md={4}>
            <TextDisplay value={totalDocuments.todos} />
          </Col>
          <Col md={4}>
            <TextDisplay value={totalDocuments.pendientes} />
          </Col>
          <Col md={4}>
            <TextDisplay value={totalDocuments.cargados} />
          </Col>
        </Row>
        <Row>
          <Col md={12} className="text-end pe-4">
            <IconButton
              type={"download"}
              onClick={() => {
                onDescargaContratoSatCloud();
              }}
              tooltip={"Descarga masiva SATCloud"}
              disabled={!dataT?.some((item) => item?.descarga)}
            />
            <IconButton
              type={"zip"}
              onClick={() => {
                onDescargaContratoMasiva();
              }}
              tooltip={"Descarga masiva del proyecto"}
              disabled={!dataT?.some((item) => item?.descarga)}
            />
          </Col>
        </Row>
        <Row>
          <Col md={12} className="gestion-documental">
            <TablaEditable
              ref={tableReference}
              header={"Estructura documental"}
              dataTable={dataT}
              columns={columns}
              hasPagination
              isFiltered
              onUpdate={setDataT}
              onDelete={setDataT}
              onGetRowData={onGetRowData}
              initialVisibility={{ cargado: false, extension: false }}
            />
          </Col>
        </Row>
        <Row>
          <Col md={12} className="text-end">
            {!disabled ? (
              <>
                <Button
                  variant="red"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={() => {
                    setShowCancelModal(true);
                  }}
                >
                  Cancelar
                </Button>
                <Button
                  variant="green"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={onSubmit}
                  type="submit"
                >
                  Guardar
                </Button>
              </>
            ) : null}
          </Col>
        </Row>

        <BasicModal
          size={"md"}
          handleApprove={() => {
            setShowCancelModal(false);
            onCancel();
          }}
          handleDeny={() => {
            setShowCancelModal(false);
          }}
          show={showCancelModal}
          denyText={"No"}
          approveText={"Sí"}
          title={"Mensaje"}
          onHide={() => {
            setShowCancelModal(false);
          }}
        >
          {GESTION_DOCUMENTAL.MSG009}
        </BasicModal>
        <BasicModal
          size={"md"}
          handleApprove={() => {
            onSubmitDeleteRow();
            setShowDeleteModal(false);
          }}
          handleDeny={() => {
            setShowDeleteModal(false);
          }}
          show={showDeleteModal}
          denyText={"No"}
          approveText={"Sí"}
          title={"Mensaje"}
          onHide={() => {
            setShowDeleteModal(false);
          }}
        >
          {GESTION_DOCUMENTAL.MSG001}
        </BasicModal>
        <SingleBasicModal
          size={"md"}
          show={showSingleBasicModal}
          approveText={"Aceptar"}
          title={"Mensaje"}
          onHide={() => {
            setShowSingleBasicModal(false);
            if (singleBasicMessage === GESTION_DOCUMENTAL.MSG003) {
              setShowJustificacionModal(true);
            }
          }}
        >
          {singleBasicMessage}
        </SingleBasicModal>
        <BasicModal
          size={"md"}
          title={"Mensaje"}
          denyText={"No"}
          approveText={"Sí"}
          handleApprove={() => {
            setFilterButtons((prev) =>
              prev.map((filter) => {
                if (filter.id === currentFilter) {
                  return { ...filter, value: true };
                } else {
                  return { ...filter, value: false };
                }
              })
            );
            onChangeRadialForm(currentFilter);
            setShowFilter(false);
          }}
          handleDeny={() => {
            setShowFilter(false);
          }}
          onHide={() => {
            setShowFilter(false);
          }}
          show={showFilter}
        >
          {GESTION_DOCUMENTAL.MSG009}
        </BasicModal>
        <BasicModal
          size={"md"}
          show={showDuplicatedFile}
          title={"Mensaje"}
          approveText={"Sí"}
          denyText={"No"}
          handleApprove={() => {
            if (selectedRow.parentId) {
              const parentIndex = selectedRow.parentId
                ? parseInt(selectedRow.parentId.split("")[0])
                : undefined;
              const newRow = {
                ...selectedRow.original,
                file: cacheFile.file,
                tamanoMb: cacheFile.tamanoMb,
                estatus: true,
                cargado: true,
                fechaModificacion: new Date(),
              };
              const final = updateRowFromSubRow(selectedRow, newRow);
              tableReference?.current?.table?.options?.meta.updateSubRows(
                parentIndex,
                final
              );
            }
            setShowDuplicatedFile(false);
          }}
          handleDeny={() => {
            setShowDuplicatedFile(false);
          }}
          onHide={() => {
            setShowDuplicatedFile(false);
          }}
        >
          {GESTION_DOCUMENTAL.MSG0010}
        </BasicModal>
        <Justificacion
          disabled={disabled}
          show={showJustificacionModal}
          handleCancel={(value) => {
            setJustificacionDisplay("");
            setShowJustificacionModal(false);
          }}
          handleSave={(comentario) => {
            if (comentario) {
              if (selectedRow) {
                const parentIndex = selectedRow.parentId
                  ? parseInt(selectedRow.parentId.split("")[0])
                  : undefined;
                const newRow = {
                  ...selectedRow.original,
                  justificacion: comentario,
                  noAplica: selectedRow?.noAplica ? true : false,
                  estatus: selectedRow?.estatus
                    ? true
                    : selectedRow.original.estatus,
                  obligatorio: selectedRow?.obligatorio
                    ? false
                    : selectedRow.original.obligatorio,
                };
                const final = updateRowFromSubRow(selectedRow, newRow);
                tableReference.current?.table?.options?.meta?.updateSubRows(
                  parentIndex,
                  final
                );
                console.log(selectedRow);
                setSelectedRow(null);
              }
              setJustificacionDisplay("");
              setShowJustificacionModal(false);
            }
          }}
          justificacion={justificacionDisplay}
        />
        <VerDocumento
          show={showDocumentoPDF}
          onHide={() => setShowDocumentoPDF(false)}
          title={"Ver PDF"}
          urlPdfBlob={pdfDocumentData}
        />
        <ModalSatCloud
          show={showSatCloudModal}
          handleClose={() => setShowSatCloudModal(false)}
          url={descargaSatCloud ? descargaSatCloud.url : ""}
          password={descargaSatCloud ? descargaSatCloud.temporal : ""}
        />
      </>
    </>
  );
}
