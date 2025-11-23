import React, { useEffect, useMemo, useRef, useState, useContext } from "react";
import { Modal, Card, Button, Col, Form, FormCheck, Row } from "react-bootstrap";
import TextDisplay from "../../../components/LabelValue";
import { TablaEditable } from "../../../components/table/TablaEditable";
import IconButton from "../../../components/buttons/IconButton";
import { Tooltip } from "../../../components/Tooltip";
import VerDocumento from "../../../components/VerDocumento";
import Loader from "../../../components/Loader";
import Authorization from "../../../components/Authorization";
import { ActionsCell } from "./components/ActionsCell";
import { FormCheckCell } from "./components/FormCheckCell";
import { RadialButton } from "./components/RadialButton";
import { Justificacion } from "./components/Justificacion";
import { ExpandedCell } from "./components/ExpandedCell";
import { DownloadFileBase64 } from "./components/DownloadFile";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import ModalSatCloud from "../../../components/ModalSatCloud";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import _ from "lodash";
import moment from "moment";
import {
    useDeleteArchivoMutation,
    useGetMatrizDocumentalQuery,
    usePostDescargaMasivaMutation,
    usePostDescargaSatCloudMutation,
    usePutCargarArchivoFaseMutation,
    usePutDescargarArchivoMutation,
    useSaveArchivoMutation,
  } from "./store";
import { ControlContext } from "./components/ControlContext";
import {
    base64ToPdfBlobUrl,
    getTotalDocuments,
    updateRowFromSubRow,
    isFaseType,
    extractSubRows,
    addUUIDsAndClone,
    downloadFileFromPath,
  } from "./utils";
import { useToast } from "../../../hooks/useToast";
import { useErrorMessages } from "../../../hooks/useErrorMessages";
import { GESTION_DOCUMENTAL } from "../../../constants/messages";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import BasicModal from "../../../modals/BasicModal";

//Comité
import { ComitesProvider } from "./ComitesContext";
import { useDispatch, useSelector } from "react-redux";
import { ADMINISTRAR_INFO_COMITES } from "../../../constants/messages";
import InformacionComiteTable from "./InformacionComiteTable/InformacionComiteTable";
import ModalComponent from "./components/ModalComponent";
import {
    DownloadReport,
    GetInfoComites,
  } from "../../../store/infoComites/infoComitesActions";
import IngresoDatos from "./IngresoDatos";
import DetalleComite from "./DetalleComite";

const CustomTitle = ({value, type}) => {
      if(value){
        if(type){
            return (
                <Modal.Title>Información de comités</Modal.Title>
            )
        }else{
            return (
                <Modal.Title>Control Documental</Modal.Title>
            )
        }
                
        } else {
          return (
            <Modal.Title>Ver PDF</Modal.Title>
          )
        }
  }


const CustomBody = ({value,data, type}) => {
    //Gestión Documental Comité
    var dispatch = useDispatch();
    const [isLoading, setIsLoading] = useState(false);

    //#region Modal IngresoDatos
    const [showIngresoDatos, setShowIngresoDatos] = useState(false);
    const handleShowIngresoDatos = () => setShowIngresoDatos(true);
    const handleCloseIngresoDatos = () => {
        setShowIngresoDatos(false);
        dispatch(GetInfoComites(data.idProyecto));
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

    //#region Detalle Comite
    const [idComiteProyecto, setIdComiteProyecto] = useState(null);
    const [showDetalleComite, setShowDetalleComite] = useState(false);
    const handleShowDetalleComite = () => setShowDetalleComite(true);
    const handleCloseDetalleComite = () => {
        setIdComiteProyecto(null);
        setShowDetalleComite(false);
        dispatch(GetInfoComites(data.idProyecto));
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
        setShowConfirmModalDetalle(false);
        handleCloseDetalleComite();
    };

    //#endregion

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

    //Fin Documental Comité

    //Gestión Documental Proyecto
    const [loading, setLoading] = useState(true);
    const { proyecto, editable } = useState(data);
    const [memoizedData, setMemoizedData] = useState(new Map());
    const tableReference = useRef();
    const { errorToast } = useToast();
    const { getMessageExists } = useErrorMessages(GESTION_DOCUMENTAL);
    const [cacheBase64, setCacheBase64] = useState();
    const [showDuplicatedFile, setShowDuplicatedFile] = useState(false);
    const [justificacionDisplay, setJustificacionDisplay] = useState("");
    const [currentFilter, setCurrentFilter] = useState("");
    const [isSaving, setIsSaving] = useState(false);
    const [initialValues, setInitialValues] = useState([]);
    const [showSatCloudModal, setShowSatCloudModal] = useState(false);
    const [showJustificacionModal, setShowJustificacionModal] = useState(false);
    const [showDocumentoPDF, setShowDocumentoPDF] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [showCancelModal, setShowCancelModal] = useState(false);
    const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
    const [singleBasicMessage, setSingleBasicMessage] = useState("");
    const [selectedRow, setSelectedRow] = useState();
    const [pdfDocumentData, setPdfDocumentData] = useState("");
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
    // const {data, isError, isFetching} = useState({
    //     data: [],
    //     isError: false,
    //     isFetching: false,
    // });
    const {
        data: gestionDocumental,
        isError,
        isFetching,
    } = useGetMatrizDocumentalQuery({
    identificador: data.identificador ?? 0,
    idReferencia: data.idReferencia ?? 0,
    });
    
    const [dataT, setDataT] = useState([]);

    const [postDescargaMasiva, { isLoading: isLoadingDescargaMasiva }] =
    usePostDescargaMasivaMutation();

    const [
        postDescargaSatCloud,
        { data: descargaSatCloud, isLoading: isLoadingDescargaSat },
    ] = usePostDescargaSatCloudMutation();

    const [putDescargarArchivo, { isLoading: isLoadingDescargaArchivo }] =
        usePutDescargarArchivoMutation();

    const [
        putCargarArchivoFase,
        { data: cargarArchivo, isLoading: isLoadingModificarArchivo },
        ] = usePutCargarArchivoFaseMutation();
    
    const [deleteArchivo, { isLoading: isDeleteLoading }] =
        useDeleteArchivoMutation();
    
    const [saveArchivo, { isLoading: isSavingLoading }] =
        useSaveArchivoMutation();
    
    const { onReloadRCPInfo } = useContext(ControlContext);

    useEffect(() => {
    if (gestionDocumental) {
        console.log("gestionDocumental:" + gestionDocumental);
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
        console.log(error);
    }
    };

    const handleDescargaSatCloud = async (data) => {
    try {
        await postDescargaSatCloud({ data }).unwrap();
        setShowSatCloudModal(true);
    } catch (error) {
        console.log(error);
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
    } catch (error) {}
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
        console.log(error);
    }
    };
    const onDescargaProjectMasiva = () => {
    handleDescargaMasiva({
        idProyecto: parseInt(data.idProyecto),
    });
    };
    const onDescargaProjectSatCloud = () => {
    handleDescargaSatCloud({
        idProyecto: parseInt(data.idProyecto),
    });
    };
    const onDescargaByRowSatCloud = (row) => {
    const { original } = row;
    handleDescargaSatCloud({
        idProyecto: parseInt(data.idProyecto),
        path: original.ruta,
    });
    };
    const onDescargaByRowMasiva = (row) => {
    const { original } = row;
    handleDescargaMasiva({
        idProyecto: parseInt(data.idProyecto),
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

    const handleDownloadExcel = () => {
    setIsLoading(true);
    dispatch(DownloadReport(data.idProyecto))
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
    const onSubmitDeleteRow = async () => {
        try {
            const { original, parentId } = selectedRow;
            console.log(original);
            const data = [
            {
                ...original,
                ...(dataT[parseInt(parentId.split("")[0])]?.descripcion ===
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
        } catch (error) {
            const { message } = error;
            const mensaje = error;
            errorToast(message);
            if (getMessageExists(mensaje?.data?.mensaje[0])) {
            errorToast(mensaje?.data?.mensaje[0]);
            } else {
            errorToast("Ocurrió un error, favor de intentar nuevamente");
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
            console.log(archivosAEnviar);
            setIsSaving(true);
            archivosAEnviar?.forEach((archivo) => {
            console.log(archivo);
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
            formData.append("nombreFase", archivo?.fase);
            formData.append("idProyecto", data.idProyecto);
            formData.append("obligatorio", archivo?.obligatorio);
            formData.append("noAplica", archivo?.noAplica);
            formData.append(
                "justificacion",
                archivo?.justificacion ? archivo?.justificacion : null
            );
    
            formData.append("carpeta", archivo?.carpeta);
            formData.append("descripcion", archivo?.descripcion);
            console.log(formData);
            requestsToPost.push(saveArchivo({ data: formData }).unwrap());
            });
    
            await Promise.all(requestsToPost);
            if (onReloadRCPInfo !== null) await onReloadRCPInfo();
            setIsSaving(false);
            setSingleBasicMessage(GESTION_DOCUMENTAL.MSG005);
            setShowSingleBasicModal(true);
        } catch (error) {
            console.log(error);
            setIsSaving(false);
            const { message } = error;
            const mensaje = error;
            errorToast(message);
            if (getMessageExists(mensaje?.data?.mensaje[0])) {
            errorToast(mensaje?.data?.mensaje[0]);
            } else {
            errorToast("Ocurrió un error, favor de intentar nuevamente");
            }
        }
        };
    const onChangeRadialForm = (currentFilter) => {
        const id = currentFilter?.toLowerCase();
        switch (id) {
            case "todos": {
            tableReference.current.setColumnFilters((prev) => []);
            break;
            }
            case "pendientes": {
            tableReference.current.setColumnFilters((prev) => [
                { id: "cargado", value: false },
            ]);
            break;
            }
            case "cargados": {
            tableReference.current.setColumnFilters((prev) => [
                { id: "cargado", value: true },
            ]);
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
                {props.row.original.hasOwnProperty("justificacion") ? (
                    <Tooltip text={"Justificación"}>
                    <button
                        style={{ border: "1px solid transparent" }}
                        onClick={() => {
                        setSelectedRow({
                            ...props.row,
                            noAplica: props.row.original.noAplica,
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
                    getValue={props.getValue}
                    row={props.row}
                    onDownloadSat={onDescargaByRowSatCloud}
                    onDownloadMasiva={onDescargaByRowMasiva}
                    onShowFile={onVisualizarPdfByRow}
                    onDelete={onDeleteArchivo}
                    // onUploadFile={subirArchivo}
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
        ],
        []
        );

    //console.log("data en modal: "+ data);
    //console.log("data idProyecto: "+ data.idProyecto);
    //console.log("Data in Proyecto: " + proyecto);

      if(value){
            if(type){ //Comité
                return (
                    <ComitesProvider>
                    {isLoading && <Loader />}
                    <Row>
                        <Col md={12} className="text-end mb-2">
                        {/* <Authorization process={"PROY_INFO_COM_ADMIN"}> */}
                            <IconButton
                            type="add"
                            onClick={handleShowIngresoDatos}
                            disabled={!editable}
                            tooltip={"Nuevo"}
                            />
                        {/* </Authorization> */}
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
                        idProyecto={data.idProyecto}
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
                        idProyecto={data.idProyecto}
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
                    </ComitesProvider>
                );
            }else{ //Proyecto
                return (
                    <>
                        {
                        isFetching ||
                        isLoadingDescargaMasiva ||
                        isLoadingDescargaArchivo ||
                        isLoadingModificarArchivo ||
                        isLoadingDescargaSat ||
                        isSavingLoading ||
                        isSaving ||
                        isDeleteLoading ? (
                            <Loader zIndex={`${isFetching ? "10000000" : "99999"}`} />
                        ) : null
                        }
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
                            {/* <Authorization process={"PROY_GD_DOWN_MASIVA"}> */}
                                <IconButton
                                tooltip={"Descarga masiva SATCloud"}
                                type={"download"}
                                onClick={() => {
                                    onDescargaProjectSatCloud();
                                }}
                                />
                                <IconButton
                                tooltip={"Descarga masiva del proyecto"}
                                type={"zip"}
                                onClick={() => {
                                    onDescargaProjectMasiva();
                                }}
                                />
                            {/* </Authorization> */}
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
                                initialVisibility={{ cargado: false }}
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
                            handleCancel={(value) => {
                            if (value || !editable) {
                                setJustificacionDisplay("");
                                setShowJustificacionModal(false);
                            }
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
                )
            }        
        } else {
          return (
            <>
                
                <div className='content-bitacora-dictaminador'>
                        <iframe 
                            src={"data:application/pdf;base64," + data}
                            title="Vista previa"
                            style={{
                            display: "flex",
                            flexGrow: 1,
                            width: "100%",
                            height: "600px",
                            border: "none",
                            }}
                        />
                        
                </div>
            </>
          )
        }
  }


const ReporteControlDocumental = ({ show, setShow, data, viewControlDocumental, typeControlDocumentalComite }) => {

    return (
        <Modal
            show={show}
            onHide={() => setShow(false)}
            size="lg"
            centered
        >
            <Modal.Header closeButton>
                <CustomTitle
                    value={viewControlDocumental}
                    type={typeControlDocumentalComite}
                />      
            </Modal.Header>
            <Modal.Body className='px-2 py-0'>
                <CustomBody 
                    value={viewControlDocumental}
                    data={data}
                    type={typeControlDocumentalComite}
                />
            </Modal.Body>
            <Modal.Footer>
                <Button
                    variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={() => setShow(false)}
                >
                    Cerrar
                </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default ReporteControlDocumental;
