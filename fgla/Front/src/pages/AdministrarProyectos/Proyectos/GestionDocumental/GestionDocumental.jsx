import React, { useEffect, useMemo, useRef, useState, useContext } from "react";
import { Button, Col, Form, FormCheck, Row } from "react-bootstrap";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import IconButton from "../../../../components/buttons/IconButton";
import "./styles.css";
import TextDisplay from "../../../../components/LabelValue";
import { ExpandedCell } from "../FichaTecnica/Components/ExpandedCell";
import { ProyectosContext } from "../ProyectosContext";
import {
  useDeleteArchivoMutation,
  useGetMatrizDocumentalQuery,
  usePostDescargaMasivaMutation,
  usePostDescargaSatCloudMutation,
  usePutDescargarArchivoMutation,
  useSaveArchivoMutation,
} from "./store";
import Loader from "../../../../components/Loader";
import {
  base64ToPdfBlobUrl,
  getTotalDocuments,
  updateRowFromSubRow,
  isFaseType,
  extractSubRows,
  addUUIDsAndClone,
  downloadFileFromPath,
} from "./utils";
import ModalSatCloud from "../../../../components/ModalSatCloud";
import moment from "moment";
import { useSelector } from "react-redux";
import { DownloadFileBase64 } from "../InformacionComites/Components/DownloadFile";
import { Tooltip } from "../../../../components/Tooltip";
import VerDocumento from "../../../../components/VerDocumento";
import { ActionsCell } from "./components/ActionsCell";
import BasicModal from "../../../../modals/BasicModal";
import { GESTION_DOCUMENTAL } from "../../../../constants/messages";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import { FormCheckCell } from "./components/FormCheckCell";
import { useToast } from "../../../../hooks/useToast";
import { Justificacion } from "./components/Justificacion";
import _ from "lodash";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";
import { RadialButton } from "./components/RadialButton";
import Authorization from "../../../../components/Authorization";
import { useDispatch } from "react-redux";
import { GetDetalleProyecto } from "../../../../store/infoComites/infoComitesActions";

export function GestionDocumental() {
  const dispatch = useDispatch();

  const [memoizedData, setMemoizedData] = useState(new Map());
  const tableReference = useRef();
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(GESTION_DOCUMENTAL);
  const [cacheBase64, setCacheBase64] = useState();
  const { proyecto, editable } = useSelector((state) => state.proyectos);
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
  const [postDescargaMasiva, { isLoading: isLoadingDescargaMasiva }] =
    usePostDescargaMasivaMutation();
  const [
    postDescargaSatCloud,
    { data: descargaSatCloud, isLoading: isLoadingDescargaSat },
  ] = usePostDescargaSatCloudMutation();

  const [putDescargarArchivo, { isLoading: isLoadingDescargaArchivo }] =
    usePutDescargarArchivoMutation();

  const { onReloadRCPInfo } = useContext(ProyectosContext);

  const { data: gestionDocumental, isFetching } = useGetMatrizDocumentalQuery(
    proyecto?.proyecto?.idProyecto
  );
  const [dataT, setDataT] = useState([]);

  const [deleteArchivo, { isLoading: isDeleteLoading }] =
    useDeleteArchivoMutation();

  const [saveArchivo, { isLoading: isSavingLoading }] =
    useSaveArchivoMutation();

  useEffect(() => {
    if (gestionDocumental) {
      const normalizedData = addUUIDsAndClone(gestionDocumental);
      setDataT(normalizedData);
      setInitialValues(normalizedData);
      const dataToMemoized = extractSubRows(normalizedData);
      setMemoizedData(
        new Map(dataToMemoized.map((archivo) => [archivo.UUID, archivo]))
      );
      setTotalDocuments(() => getTotalDocuments(gestionDocumental));
    }
  }, [gestionDocumental]);

  const handleDescargaMasiva = async (data) => {
    try {
      const res = await postDescargaMasiva({ data }).unwrap();
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
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };

  const handleDescargaSatCloud = async (data) => {
    try {
      await postDescargaSatCloud({ data }).unwrap();
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
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };

  const handleVisualizarArchivo = async (path) => {
    try {
      const formData = new FormData();
      formData.append("path", path);
      const res = await putDescargarArchivo({ data: formData }).unwrap();
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
        setSingleBasicMessage("Ocurrió un error.");
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
      const res = await putDescargarArchivo({ data: formData }).unwrap();
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
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const onDescargaProjectMasiva = () => {
    handleDescargaMasiva({
      idProyecto: parseInt(proyecto?.proyecto?.idProyecto),
    });
  };
  const onDescargaProjectSatCloud = () => {
    handleDescargaSatCloud({
      idProyecto: parseInt(proyecto?.proyecto?.idProyecto),
    });
  };
  const onDescargaByRowSatCloud = (row) => {
    const { original } = row;
    handleDescargaSatCloud({
      idProyecto: parseInt(proyecto?.proyecto?.idProyecto),
      path: original.ruta,
    });
  };
  const onDescargaByRowMasiva = (row) => {
    const { original } = row;
    handleDescargaMasiva({
      idProyecto: parseInt(proyecto?.proyecto?.idProyecto),
      path: original.ruta,
    });
  };

  const onCancel = () => {
    setDataT([...initialValues]);
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
          ...(dataT[parseInt(parentId.split(".")[0])]?.descripcion ===
          "Otros Documentos"
            ? { type: "tipoProyecto" }
            : isFaseType(original.ruta)
            ? { type: "tipoFase" }
            : { type: "tipoPlantilla" }),
        },
      ];
      await deleteArchivo({ data }).unwrap();
      setSingleBasicMessage(GESTION_DOCUMENTAL.MSG002);
      setShowSingleBasicModal(true);
      dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
    } catch (error) {
      const mensaje = error?.data?.mensaje[0];
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
        const formData = new FormData();
        if (!archivo?.isNewRow) {
          formData.append("idArchivo", archivo?.id);
        } else {
          formData.append("idArchivo", null);
        }
        if (archivo?.fase === "Otros Documentos") {
          formData.append("type", "tipoProyecto");
        } else if (isFaseType(archivo.carpeta)) {
          formData.append("type", "tipoFase");
        } else {
          formData.append("type", "tipoPlantilla");
        }
        if (archivo?.file) {
          formData.append("file", archivo?.file);
        }
        if (archivo?.justificacion) {
          formData.append("justificacion", archivo?.justificacion);
        }
        formData.append("nombreFase", archivo?.fase || "Otros Documentos");
        formData.append("idProyecto", proyecto?.proyecto?.idProyecto);
        formData.append("obligatorio", archivo?.obligatorio);
        formData.append("noAplica", archivo?.noAplica);
        formData.append("carpeta", archivo?.carpeta);
        formData.append("descripcion", archivo?.descripcion || "Documento");
        requestsToPost.push(saveArchivo({ data: formData }).unwrap());
      });

      await Promise.all(requestsToPost);
      if (onReloadRCPInfo !== null) await onReloadRCPInfo();
      setIsSaving(false);
      setSingleBasicMessage(GESTION_DOCUMENTAL.MSG005);
      setShowSingleBasicModal(true);
      dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
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

  function updateRowByUUID(data, uuid, update) {
    return data.map(row => {
      if (row.UUID === uuid) {
        return { ...row, ...update };
      }
      if (row.subRows && row.subRows.length) {
        return { ...row, subRows: updateRowByUUID(row.subRows, uuid, update) };
      }
      return row;
    });
  }

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
            editable={editable}
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
  cell: (props) => {
    const checked = props.getValue();

    return (
      <div className="check-box-black">
        <FormCheck
          checked={checked}
          onChange={(e) => {
            const rowUUID = props.row.original.UUID;

            setDataT((prev) =>
              updateRowByUUID(prev, rowUUID, {
                obligatorio: e.target.checked,
                noAplica: e.target.checked ? false : props.row.original.noAplica,
                justificacion: e.target.checked ? "" : props.row.original.justificacion,
              })
            );
          }}
        />
      </div>
    );
  },
  enableColumnFilter: false,
  enableSorting: false,
  footer: (props) => props.column.id,
},
      {
  accessorKey: "noAplica",
  header: "No aplica",
  cell: (props) => (
    <FormCheckCell
      editable={editable}
      column={props.column}
      row={props.row}
      table={props.table}
      getValue={props.getValue}
      callback={() => {
        if (!props.getValue()) {
          setJustificacionDisplay("");
          setSelectedRow({
            ...props.row,
            noAplica: true,                // marca intención
            obligatorio: true,             // para luego volverlo false al guardar
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
              editable={editable}
              row={props.row}
              onDownloadSat={onDescargaByRowSatCloud}
              onDownloadMasiva={onDescargaByRowMasiva}
              onShowFile={onVisualizarPdfByRow}
              onDelete={onDeleteArchivo}
              callback={(newDocument) => {
                setDataT(prevData =>
                  updateRowByUUID(prevData, newDocument.rowUUID, {
                    file: newDocument.file,
                    tamanoMb: newDocument.tamanoMb,
                    estatus: true,
                    cargado: true,
                    fechaModificacion: new Date(),
                  })
                );
                setSelectedRow(props.row);
                setCacheBase64(newDocument);
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

const [descargaActiva,setDescargaActiva]= useState(false);

useEffect(() => {
  if (!_.isEmpty(dataT)) {
    const descarga = dataT.some((item) => item?.descarga);
    setDescargaActiva(descarga);
  }
}, [dataT]);


  return (
    <>
      {isFetching ||
      isLoadingDescargaMasiva ||
      isLoadingDescargaArchivo ||
      isLoadingDescargaSat ||
      isSavingLoading ||
      isSaving ||
      isDeleteLoading ? (
        <Loader zIndex={`${isFetching ? "10" : "99999"}`} />
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
          <Authorization process={"PROY_GD_DOWN_MASIVA"}>
            <IconButton
              tooltip={"Descarga masiva SATCloud"}
              type={"download"}
              onClick={() => {
                onDescargaProjectSatCloud();
              }}
              disabled={!descargaActiva}
            />
            <IconButton
              tooltip={"Descarga masiva del proyecto"}
              type={"zip"}
              onClick={() => {
                onDescargaProjectMasiva();
              }}
              disabled={!descargaActiva}
            />
          </Authorization>
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
      {editable ? (
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
  editable={editable}
  show={showJustificacionModal}
  handleCancel={() => {
    setJustificacionDisplay("");
    setShowJustificacionModal(false);
  }}
  handleSave={(comentario) => {
    if (!comentario || !selectedRow) {
      setShowJustificacionModal(false);
      return;
    }

    const rowUUID = selectedRow.original.UUID;

    // 1) Actualiza la FUENTE DE LA VERDAD
    setDataT((prev) =>
      updateRowByUUID(prev, rowUUID, {
        justificacion: comentario,
        noAplica: true,                       // se marcó "No aplica"
        estatus: selectedRow?.estatus
          ? true
          : selectedRow.original.estatus,
        obligatorio: false,                   // al “no aplica”, deja de ser obligatorio
      })
    );

    setSelectedRow(null);
    setJustificacionDisplay("");
    setShowJustificacionModal(false);
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
}
