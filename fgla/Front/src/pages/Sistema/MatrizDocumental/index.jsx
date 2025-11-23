import React, { useState, useEffect, useRef } from 'react';
import { Container, Row, Col, Button, Modal } from 'react-bootstrap';
import { MainTitle, Accordion, Loader } from "../../../components";
import { Select } from "../../../components/formInputs"
import FileFieldValue from '../../../extraComponents/formInputsArray/FileFieldValue';
import IconButton from '../../../components/buttons/IconButton';
import { TablaDinamica } from '../../../components/table';
import { addOrReplace, injectActions, downloadExcelBlob } from '../../../functions/utils';
import { getData, uploadDocumentPut, postData, downloadDocument } from '../../../functions/api';
import Preview from './Preview';
import moment from "moment";
import { MATRIZ_DOCUMENTAL } from '../../../constants/messages';
import showMessage from '../../../components/Messages';
import { useToast } from '../../../hooks/useToast';
import BasicModal from '../../../modals/BasicModal';
import { logError } from '../../../components/utils/logError.js';

const FORMAT_DATE = "DD/MM/YYYY";

const MatrizDocumental = () => {
    const ID_KEY_NAME = "idPlantillaVigente";
    const fileInputRef = useRef();
    const { errorToast } = useToast();
    const [loading, setLoading] = useState(true);
    const [show, setShow] = useState(false);
    const [fileExcel, setFileExcel] = useState(null);
    const [errorExcel, setErrorExcel] = useState(null);
    const [errorNombre, setErrorNombre] = useState(null);
    const [faseOptions, setFaseOptions] = useState([]);
    const [dataPreview, setDataPreview] = useState([]);
    const [idFase, setIdFase] = useState("");
    const [plantillaList, setPlantillaList] = useState([]);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [confirmModal, setConfirmModal] = useState(false);
    const [cancelModal, setCancelModal] = useState(false);
    const [idUpdate, setIdUpdate] = useState(null);

    const getLinkEdit = (id, value) => {
        return <u>
            <span onClick={editPlantilla(id)} style={{ cursor: 'pointer' }}>
                {value}
            </span>
        </u>;
    }

    const HEADERS = [
        { dataField: "idPlantillaVigente", text: "Id", filter: true, sort: true },
        { dataField: "nombre", text: "Nombre de plantilla", filter: true, sort: true, formatter: "custom", customFn: getLinkEdit },
        { dataField: "fase", text: "Fase", filter: true, sort: true },
        { dataField: "fecha", text: "Fecha de creación", filter: true, sort: true },
        { dataField: "fechaModificacion", text: "Última modificación", filter: true, sort: true },
        { dataField: "estado", text: "Estatus", formatter: "switch" },
        { dataField: "acciones", text: "Acciones" }
    ]

    useEffect(() => {
        getDataInit();
        getPlantillas();
    }, []);

    const getDataInit = () => {
        setLoading(true);
        getData('/administracion/plantillas/fase-proyecto')
            .then((response) => {
                setFaseOptions(response.data);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage("Ocurrió un error");
            });
    };

    const getPlantillas = () => {
        getData('/administracion/plantillas/plantillas')
            .then((response) => {
                processData(response.data);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage("Ocurrió un error");
            });
    }

    const processData = (data) => {
        let processedDataTable = [];
        data.forEach((item) => {
            let row = {
                ...item,
                fecha: dateFormat(item.fecha),
                fechaModificacion: dateFormat(item.fechaModificacion),
                fase: item?.catFaseProyecto?.nombre
            };
            processedDataTable.push(row);
        });
        setPlantillaList(processedDataTable);
    };

    const handlePreview = () => {
        setErrorNombre(false);
        setLoading(true);
        const formData = new FormData();
        formData.append('file', fileExcel);
        formData.append('idFase', idFase);
        formData.append('pestanna', 0);

        uploadDocumentPut('/administracion/plantillas/vista-previa-plantilla', formData)
            .then((response) => {
                setDataPreview(response.data);
                setLoading(false);
                handleShow();
                setFileExcel(null);
                fileInputRef.current = '';
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                setFileExcel(null);
                fileInputRef.current = '';
                let erroresPlantilla = Object.values(MATRIZ_DOCUMENTAL);
                let errorMessage = error?.response?.data?.mensaje[0];
                let errorPlantilla = erroresPlantilla.includes(errorMessage);
                console.log("errorPlantilla => ", errorPlantilla);
                logError("errorMessage => ", errorMessage);
                if (errorPlantilla) {
                    showMessage(errorMessage);
                } else {
                    showMessage(MATRIZ_DOCUMENTAL.error);
                }
            });
    };

    const handleSelectExcel = (file) => {
        console.log("handleSelectExcel =>");
        let hasFile = file.target.files[0] === undefined ? false : true;

        if (hasFile) {
            let documento = file.target.files[0];
            let extPermitida = '.XLSX';
            let fileExtension = "." + documento.name.split('.').pop().toUpperCase();
            let fileExtensionAccept = fileExtension === extPermitida.toUpperCase()

            if (!fileExtensionAccept) {
                setFileExcel(null);
                setErrorExcel("Archivo no valido");
                showMessage(MATRIZ_DOCUMENTAL.MSG003);
            } else {
                setFileExcel(documento);
                setErrorExcel(null);
            }
        }
    }

    const handleGuardarPlantilla = () => {

        if (dataPreview.plantillaVigenteModel.nombre === '') {
            setErrorNombre(true);
            errorToast(MATRIZ_DOCUMENTAL.MSG002);
            return;
        }

        let idPlantilla = dataPreview.plantillaVigenteModel.idPlantillaVigente;

        if (idPlantilla) {
            updatePlantilla();
        } else {
            cargaPlantilla();
        }
    }

    const cargaPlantilla = () => {
        setLoading(true);
        postData('/administracion/plantillas/cargar-plantilla/' + dataPreview.idFase, dataPreview)
            .then((response) => {
                setLoading(false);
                handleClose();
                let newObj = {
                    ...response.data,
                    fecha: dateFormat(response.data?.fecha),
                    fechaModificacion: dateFormat(response.data?.fechaModificacion),
                    fase: response.data?.catFaseProyecto?.nombre
                };
                setPlantillaList([...plantillaList, newObj]);
                showMessage(MATRIZ_DOCUMENTAL.MSG004);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                let erroresPlantilla = [MATRIZ_DOCUMENTAL.MSG005];
                let errorMessage = error?.response?.data?.mensaje[0];
                let errorPlantilla = erroresPlantilla.includes(errorMessage.trim());
                if (errorPlantilla) {
                    showMessage(errorMessage);
                } else {
                    showMessage(MATRIZ_DOCUMENTAL.MSG010);
                }
            });
    }

    const updatePlantilla = () => {
        setLoading(true);
        let idPlantilla = dataPreview.plantillaVigenteModel.idPlantillaVigente;

        postData('/administracion/plantillas/actualizar-plantilla/' + idPlantilla, dataPreview)
            .then((response) => {
                setLoading(false);
                handleClose();
                let newObj = {
                    ...response.data,
                    fecha: dateFormat(response.data?.fecha),
                    fechaModificacion: dateFormat(response.data?.fechaModificacion),
                    fase: response.data?.catFaseProyecto?.nombre
                };
                setPlantillaList(addOrReplace(plantillaList, newObj, ID_KEY_NAME));
                showMessage(MATRIZ_DOCUMENTAL.MSG004);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
            });
    }

    const handleChangeFase = (e) => {
        setIdFase(parseInt(e.target.value));
        setDataPreview({ ...dataPreview, idFase: e.target.value });
    }

    const dateFormat = (date) => {
        let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
        return formatedDateTime;
    }

    const handleDownloadPlantilla = () => {
        downloadDocument('/administracion/plantillas/generar-plantilla-base')
            .then((response) => {
                downloadExcelBlob(response.data, 'plantilla-base');
            })
            .catch((error) => {
                logError("error => ", error);
            });
    }

    const handleDownload = (id) => () => {
        setLoading(true);
        downloadDocument('/administracion/plantillas/generar-reporte/' + id)
            .then((response) => {
                downloadExcelBlob(response.data, 'plantilla-' + id);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
            });
    }

    const editPlantilla = (id) => () => {
        setErrorNombre(false);
        setLoading(true);
        getData('/administracion/plantillas/obtener-plantilla/' + id)
            .then((response) => {
                setDataPreview(response.data);
                setIdFase(response.data.idFase);
                setLoading(false);
                handleShow();
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(MATRIZ_DOCUMENTAL.MSG011);
            });
    }

    const handleChangeStatus = (id) => () => {
        let object = plantillaList.find((element) => element[ID_KEY_NAME] === id);
        if (object.asignado === true) {
            showMessage(MATRIZ_DOCUMENTAL.MSG008);
        } else {
            setConfirmModal(true);
            setIdUpdate(object.idPlantillaVigente);
        }
    }

    const changeStatus = (data) => {
        setLoading(true);
        postData('/administracion/plantillas/modificar-estatus-plantilla/' + data.idPlantillaVigente, !data.estado)
            .then((response) => {
                setLoading(false);
                handleClose();
                let newObj = {
                    ...response.data,
                    fecha: dateFormat(response.data.fecha),
                    fechaModificacion: dateFormat(response.data?.fechaModificacion),
                    fase: response.data?.catFaseProyecto?.nombre
                };
                setPlantillaList(addOrReplace(plantillaList, newObj, ID_KEY_NAME));
                setIdUpdate(null);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(MATRIZ_DOCUMENTAL.MSG010);
            });
    }

    const cambiarEstatusAccept = () => {
        setConfirmModal(false);
        if (idUpdate) {
            let object = plantillaList.find((element) => element[ID_KEY_NAME] === idUpdate);
            changeStatus(object);
        }
    }

    const cancelSaveUpdateAccept = () => {
        setShow(false);
        setCancelModal(false);
        setIdFase('');
    }

    return (

        <Container className='mt-3 px-3'>

            {loading && <Loader />}

            <MainTitle title="Matriz Documental" />

            <Accordion title="Cargar plantilla de documentos">
                <Row>
                    <Col md={1} className='mt-4 text-center'>
                        <IconButton
                            type="excel"
                            onClick={handleDownloadPlantilla}
                            tooltip={"Plantilla tipo"}
                        />
                    </Col>
                    <Col md={4}>
                        <FileFieldValue
                            ref={fileInputRef}
                            value={fileExcel}
                            label="Archivo a cargar*:"
                            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                            onChange={handleSelectExcel}
                            className={errorExcel ? 'is-invalid' : ''}
                            helperText={errorExcel ? errorExcel : ''}
                        />
                    </Col>

                    <Col md={4}>
                        <Select
                            label="Fase*:"
                            name="idFase"
                            value={idFase}
                            onChange={e => handleChangeFase(e)}
                            options={faseOptions}
                            keyValue="primaryKey"
                            keyTextValue="nombre"
                        />
                    </Col>

                    <Col md={3} className='mt-4'>
                        <Button
                            variant="green"
                            className="btn-sm waves-effect waves-light mx-5"
                            disabled={fileExcel === null || idFase === ""}
                            onClick={handlePreview}
                        >
                            Vista previa
                        </Button>
                    </Col>
                </Row>

                <Modal
                    show={show}
                    onHide={() => setCancelModal(true)}
                    size="xl"
                >
                    <Modal.Header closeButton>
                        <Modal.Title>Cargar plantilla de documentos</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {
                            show && <Preview
                                dataPreview={dataPreview}
                                faseOptions={faseOptions}
                                setDataPreview={setDataPreview}
                                handleChangeFase={handleChangeFase}
                                handleDownload={handleDownload}
                                errorNombre={errorNombre}
                            />
                        }
                    </Modal.Body>
                    <Modal.Footer>
                        <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => setCancelModal(true)}
                        >
                            Cancelar
                        </Button>
                        <Button
                            variant="green"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={handleGuardarPlantilla}
                        >
                            Guardar
                        </Button>
                    </Modal.Footer>
                </Modal>
            </Accordion>

            <Accordion title="Plantillas creadas">
                <TablaDinamica
                    idKeyName={ID_KEY_NAME}
                    idKeyLink={ID_KEY_NAME}
                    headers={HEADERS}
                    header="Plantillas"
                    data={injectActions(plantillaList, { download: true })}
                    actionFns={{ handleDownload }}
                    onChangeStatus={handleChangeStatus}
                />
            </Accordion>

            <BasicModal
                size="md"
                handleApprove={cambiarEstatusAccept}
                handleDeny={() => setConfirmModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={confirmModal}
                title="Mensaje"
                onHide={() => setConfirmModal(false)}
            >
                {MATRIZ_DOCUMENTAL.MSG009}
            </BasicModal>

            <BasicModal
                size="md"
                handleApprove={cancelSaveUpdateAccept}
                handleDeny={() => setCancelModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={cancelModal}
                title="Mensaje"
                onHide={() => setCancelModal(false)}
            >
                {MATRIZ_DOCUMENTAL.MSG007}
            </BasicModal>
        </Container >
    );
}

export default MatrizDocumental;