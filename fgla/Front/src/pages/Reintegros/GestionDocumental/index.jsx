import React, { useEffect, useMemo, useRef, useState } from "react";
import "./styles.css";
import ModalSatCloud from "../../../components/ModalSatCloud";
import BasicModal from "../../../modals/BasicModal";
import VerDocumento from "../../../components/VerDocumento";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import { Button, Col, Form, FormCheck, Row } from "react-bootstrap";
import { TablaEditable } from "../../../components/table/TablaEditable";
import IconButton from "../../../components/buttons/IconButton";
import { Tooltip } from "../../../components/Tooltip";
import TextDisplay from "../../../components/LabelValue";
import Loader from "../../../components/Loader";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import { GESTION_DOCUMENTAL } from "../../../constants/messages";
import { useToast } from "../../../hooks/useToast";
import { useErrorMessages } from "../../../hooks/useErrorMessages";
import _ from "lodash";
import {
  useGetEstructuraDocumentalReintegroQuery,
  usePostDescargaMasivaReintegroMutation,
  usePostDescargaSatCloudReintegroMutation,
  usePutCargarArchivoReintegroMutation,
  usePutDescargaArchivoReintegroMutation,
  usePutEliminarArchivoReintegroMutation,
} from "./store";
import moment from "moment";
import {
  ActionsCell,
  ExpandedCell,
  RadialButton,
  Justificacion,
  FormCheckCell,
} from "./Components";
import {
  addUUIDsAndClone,
  base64ToPdfBlobUrl,
  downloadFileFromPath,
  extractSubRows,
  getTotalDocuments,
  isFaseType,
  updateRowFromSubRow,
} from "./utils";
import { DownloadFileBase64 } from "../../../functions/utils/base64.utils";
import Authorization from "../../../components/Authorization";

const GestionDocumental = ({ idContratoGestion, disabledReintegros }) => {
  const [dataTable, setDataTable] = useState([]);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const tableReference = useRef();
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(GESTION_DOCUMENTAL);
  const [cacheBase64, setCacheBase64] = useState();
  const [showSatCloudModal, setShowSatCloudModal] = useState(false);
  const [showJustificacionModal, setShowJustificacionModal] = useState(false);
  const [showDocumentoPDF, setShowDocumentoPDF] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [selectedRow, setSelectedRow] = useState();
  const [pdfDocumentData, setPdfDocumentData] = useState("");
  const [showDuplicatedFile, setShowDuplicatedFile] = useState(false);
  const [justificacionDisplay, setJustificacionDisplay] = useState("");
  const [currentFilter, setCurrentFilter] = useState("");
  const [isSaving, setIsSaving] = useState(false);
  const [initialValues, setInitialValues] = useState([]);
  const [totalDocuments, setTotalDocuments] = useState({
    todos: 0,
    pendientes: 0,
    cargados: 0,
  });
  const [showFilter, setShowFilter] = useState(false);
  const [filterButtons, setFilterButtons] = useState([
    { id: "Todos", value: true },
    { id: "Pendientes", value: false },
    { id: "Cargados", value: false },
  ]);

  const { data: estructuraDocumental, isFetching } =
    useGetEstructuraDocumentalReintegroQuery(idContratoGestion, {
      skip: !idContratoGestion,
    });
  const [descargaMasiva, { isLoading: isLoadingDescargaMasiva }] =
    usePostDescargaMasivaReintegroMutation();
  const [
    descargaSat,
    { data: descargaSatCloud, isLoading: isLoadingDescargaSat },
  ] = usePostDescargaSatCloudReintegroMutation();
  const [cargarArchivo, { isLoading: isLoadingCargarArchivo }] =
    usePutCargarArchivoReintegroMutation();
  const [descargaArchivo, { isLoading: isLoadingDescargarArchivo }] =
    usePutDescargaArchivoReintegroMutation();
  const [eliminarArchivo, { isLoading: isLoadingEliminarArchivo }] =
    usePutEliminarArchivoReintegroMutation();

  const disabled = disabledReintegros;

  useEffect(() => {
    if (!idContratoGestion) {
      setDataTable([]);
      setTotalDocuments({ todos: 0, pendientes: 0, cargados: 0 });
    } else if (estructuraDocumental) {
      const normalizedData = addUUIDsAndClone(estructuraDocumental);
      setDataTable(normalizedData);
      setInitialValues(normalizedData);
      const dataToMemoized = extractSubRows(normalizedData);
      setMemoizedData(
        new Map(dataToMemoized.map((archivo) => [archivo.UUID, archivo]))
      );
      setTotalDocuments(() => getTotalDocuments(estructuraDocumental));
    }
  }, [estructuraDocumental, idContratoGestion]);

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
  const onSelectFilterButton = (event) => {
    setCurrentFilter(event.target.id);
    setShowFilter(true);
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
  const onDescargaProjectMasiva = () => {
    handleDescargaMasiva({
      idContratoReintegro: parseInt(idContratoGestion),
    });
  };
  const onDescargaProjectSatCloud = () => {
    handleDescargaSatCloud({
      idContratoReintegro: parseInt(idContratoGestion),
    });
  };
  const onDescargaByRowSatCloud = (row) => {
    const { original } = row;
    handleDescargaSatCloud({
      idContratoReintegro: parseInt(idContratoGestion),
      path: original.ruta,
    });
  };
  const onDescargaByRowMasiva = (row) => {
    const { original } = row;
    handleDescargaMasiva({
      idContratoReintegro: parseInt(idContratoGestion),
      path: original.ruta,
    });
  };

  const onCancel = () => {
    setDataTable([...initialValues]);
    tableReference?.current?.table.toggleAllRowsSelected(false);
    tableReference?.current?.table.toggleAllRowsExpanded(false);
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

  const onSubmitDeleteRow = async () => {
    try {
      const { original, parentId } = selectedRow;
      console.log(original);
      const data = [
        {
          ...original,
          ...(dataTable[parseInt(parentId.split(".")[0])]?.descripcion ===
          "Otros Documentos"
            ? { type: "tipoReintegro" }
            : isFaseType(original.ruta)
            ? { type: "tipoFaseReintegro" }
            : { type: "tipoPlantillaReintegro" }),
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
      const allSubRows = extractSubRows(dataTable);

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
      /* if (
        archivosAEnviar?.some(
          (archivo) =>
            !!!archivo?.descripcion ||
            (!!!archivo?.justificacion && archivo?.noAplica)
        )
      ) {
        errorToast(GESTION_DOCUMENTAL.MSG004);
        return;
      } */
      setIsSaving(true);
      archivosAEnviar?.forEach((archivo) => {
        console.log(archivo);
        const formData = new FormData();
        formData.append("idReintegro", archivo.idReintegro);
        if (!archivo?.isNewRow) {
          formData.append("idArchivo", archivo?.id);
        } else {
          formData.append("idArchivo", null);
        }
        if (archivo?.fase === "Otros Documentos") {
          formData.append("type", "tipoReintegro");
          formData.delete("idReintegro");
        } else if (isFaseType(archivo.carpeta)) {
          formData.append("type", "tipoFaseReintegro");
        } else {
          formData.append("type", "tipoPlantillaReintegro");
        }
        if (archivo?.file) {
          formData.append("file", archivo?.file);
        }
        formData.append("nombreFase", archivo?.fase);
        formData.append("idContrato", idContratoGestion);
        formData.append("obligatorio", archivo?.obligatorio);
        formData.append("noAplica", archivo?.noAplica);
        formData.append(
          "justificacion",
          archivo?.justificacion ? archivo?.justificacion : null
        );

        formData.append("carpeta", archivo?.carpeta);
        formData.append("descripcion", archivo?.descripcion || "Otros Documentos" );
        console.log(formData);
        requestsToPost.push(cargarArchivo({ data: formData }).unwrap());
      });

      await Promise.all(requestsToPost);
      setIsSaving(false);
      setSingleBasicMessage(GESTION_DOCUMENTAL.MSG005);
      setShowSingleBasicModal(true);
    } catch (error) {
      setIsSaving(false);
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
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
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
              <FormCheck
                checked={props.getValue()}
                disabled={true}
                onChange={(e) => {}}
              />
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
        cell: (props) => {
          let butonJustificacion = (disabled = false) => (
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
              disabled={disabled}
            >
              ...
            </button>
          );

          return (
            <>
              {!!props.row.original.justificacion ||
              !!props.row.original.noAplica ? (
                <Tooltip text={"Justificación"}>
                  <Authorization
                    process={"CON_SERV_DICT_REINT_CONS"}
                    redirect={butonJustificacion(true)}
                  >
                    {butonJustificacion(disabled)}
                  </Authorization>
                </Tooltip>
              ) : null}
            </>
          );
        },
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
                setCacheBase64(newDocument);
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
      {isFetching ||
      isSaving ||
      isLoadingDescargarArchivo ||
      isLoadingDescargaMasiva ||
      isLoadingDescargaSat ||
      isLoadingCargarArchivo ||
      isLoadingEliminarArchivo ? (
        <Loader zIndex={isFetching ? "10" : "9999"} />
      ) : null}
      <Row>
        <Col>
          <TextDisplay value={"Estatus de carga:"} />
        </Col>
      </Row>
      <Row>
        <Form className="d-flex ">
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
        </Form>
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
          <Authorization process={"CON_SERV_DICT_REINT_CONS"}>
            <Tooltip text={"Descarga masiva SATCloud"} placement="top">
              <span>
                <IconButton
                  type={"download"}
                  onClick={() => {
                    onDescargaProjectSatCloud();
                  }}
                  tooltip={"Descarga masiva SATCloud"}
                  disabled={!dataTable?.some((item) => item?.descarga)}
                />
              </span>
            </Tooltip>
            <Tooltip text={"Descarga masiva del proyecto"} placement="top">
              <span>
                <IconButton
                  type={"zip"}
                  onClick={() => {
                    onDescargaProjectMasiva();
                  }}
                  disabled={!dataTable?.some((item) => item?.descarga)}
                  tooltip={"Descarga masiva del proyecto"}
                />
              </span>
            </Tooltip>
          </Authorization>
        </Col>
      </Row>
      <Row>
        <Col md={12} className="gestion-documental">
          <TablaEditable
            ref={tableReference}
            header={"Estructura documental"}
            dataTable={dataTable}
            columns={columns}
            hasPagination
            isFiltered
            onUpdate={setDataTable}
            onDelete={setDataTable}
            onGetRowData={onGetRowData}
            initialVisibility={{ cargado: false, extension: false }}
          />
        </Col>
      </Row>
      {!disabled ? (
        <Row>
          <Col md={12} className="text-end">
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
          </Col>
        </Row>
      ) : null}

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
              file: cacheBase64.file,
              tamanoMb: cacheBase64.tamanoMb,
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
  );
};

export default GestionDocumental;
