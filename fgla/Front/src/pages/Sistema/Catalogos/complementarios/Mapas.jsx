import React, { useState, useEffect } from 'react';
import { Formik } from "formik";
import { Container, Row, Col, Button, Modal } from 'react-bootstrap';
import { Accordion, Loader } from '../../../../components';
import { useToast } from "../../../../hooks/useToast";
import { Select } from "../../../../components/formInputs";
import showMessage from '../../../../components/Messages';
import { ADMIN_CATALOGOS_MOD, ADMIN_CATALOGOS as MESSAGES } from "../../../../constants/messages";
import IconButton from '../../../../components/buttons/IconButton';
import Form from 'react-bootstrap/Form';
import moment from "moment";
import { TablaDinamica } from '../../../../components/table';
import { injectActions } from "../../../../functions/utils";
import BasicModal from '../../../../modals/BasicModal';
import { addOrReplace, downloadExcelBlob } from '../../../../functions/utils';
import { renderInput, getMetadataForm, getInitialValues, validaMostrarCampo, getHeadersTable, getValidationSchema, errorValidations } from '../utils';
import { getMetadataCatalog, saveDataCatalog, updateDataCatalog, downloadMapas, getMapas } from '../Catalogos';
import { useErrorMessages } from '../../../../hooks/useErrorMessages';
import { logError } from '../../../../components/utils/logError.js';

const FORMAT_DATE = "DD/MM/YYYY";

const Mapas = ({ idComplementario, catalogosOptions }) => {
    const ID_KEY_NAME = "primaryKey";
    const { errorToast } = useToast();
    const [loading, setLoading] = useState(true);
    const [idCatalogoBusqueda, setIdCatalogoBusqueda] = useState('');
    const [catalogoBusquedaText, setCatalogoBusquedaText] = useState('');
    const [valoresIniciales, setValoresIniciales] = useState({});
    const [valoresInicialesEmpty, setValoresInicialesEmpty] = useState({});
    const [headers, setHeaders] = useState([]);
    const [dataTable, setDataTable] = useState([]);
    const [showTable, setShowTable] = useState(false);
    const [confirmModal, setConfirmModal] = useState(false);
    const [cancelModal, setCancelModal] = useState(false);
    const [idUpdate, setIdUpdate] = useState(null);
    const [metadataForm, setMetadataForm] = useState([]);
    const [errorBuscar, setErrorBuscar] = useState(null);
    const [show, setShow] = useState(false);
    const { getMessageExists } = useErrorMessages(MESSAGES);

    useEffect(() => {
        getDataInit();
    }, []);

    const getDataInit = () => {
        getMetadataCatalog(idComplementario)
            .then((response) => {
                let metadata = getMetadataForm(response.data.catalogo.metaData);
                let initialValues = getInitialValues(metadata);
                setValoresIniciales({ ...initialValues });
                setValoresInicialesEmpty({ ...initialValues });
                setMetadataForm(metadata);
                setHeaders(getHeadersTable(metadata, "id"));
                setLoading(false);
            })
            .catch((error) => {
                setLoading(false);
                logError("error => ", error);
                errorValidations(getMessageExists, error);
            });
    };

    const dateFormatNoTime = (date) => {
        let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
        return formatedDateTime;
    }

    const handleAdd = () => {
        setShow(true);
    }

    const submitChanges = (dataForm) => {
        setLoading(true);

        let data = {
            ...dataForm,
            catAliniacion: {
                idAliniacion: idCatalogoBusqueda
            }
        }

        if (data[ID_KEY_NAME]) {
            updateCatalog(data);
        } else {
            saveDataCatalog(idComplementario, data)
                .then((response) => {
                    let obj = response.data;
                    let newItem = {
                        ...obj,
                        id: dataTable.length + 1,
                        fechaCreacion: dateFormatNoTime(obj.fechaCreacion),
                        fechaModificacion: dateFormatNoTime(obj.fechaModificacion),
                    };
                    setDataTable([...dataTable, newItem]);
                    setShow(false);
                    setLoading(false);
                    showMessage(MESSAGES.MSG001);
                })
                .catch((error) => {
                    setShow(false);
                    setLoading(false);
                    logError("error => ", error);
                    errorValidations(getMessageExists, error);
                });
        }
    }

    const cambiarEstatus = (id) => () => {
        setIdUpdate(id);
        setConfirmModal(true);
    }

    const cambiarEstatusAccept = () => {
        if (idUpdate) {
            let object = dataTable.find((element) => element[ID_KEY_NAME] === idUpdate);
            setConfirmModal(false);
            setLoading(true);
            updateCatalog({ ...object, estatus: !object.estatus });
        }
    }

    const updateCatalog = (data) => {
        console.log("updateCatalog => ", data);
        let objData = {};

        for (let propiedad in valoresIniciales) {
            objData[propiedad] = data[propiedad];
        }

        let newData = {
            [ID_KEY_NAME]: data[ID_KEY_NAME],
            ...objData,
            catAliniacion: {
                idAliniacion: idCatalogoBusqueda
            }
        };

        let originalObj = [...dataTable].find(item => item[ID_KEY_NAME] === data[ID_KEY_NAME]);

        updateDataCatalog(idComplementario, newData)
            .then((response) => {
                let obj = response.data;
                let newItem = {
                    ...obj,
                    id: originalObj?.id,
                    fechaCreacion: dateFormatNoTime(obj.fechaCreacion),
                    fechaModificacion: dateFormatNoTime(obj.fechaModificacion),
                };
                setDataTable(addOrReplace(dataTable, newItem, ID_KEY_NAME));
                setLoading(false);
                setShow(false);
                setValoresIniciales({ ...valoresInicialesEmpty });
                showMessage(ADMIN_CATALOGOS_MOD.MSG001);
            })
            .catch((error) => {
                setShow(false);
                setLoading(false);
                setValoresIniciales({ ...valoresInicialesEmpty });
                logError("error => ", error);
                errorValidations(getMessageExists, error);
            });
    }

    const handleBuscar = () => {

        if (idCatalogoBusqueda === '') {
            setErrorBuscar(true);
            errorToast(MESSAGES.MSG004);
            return;
        }

        setLoading(true);
        setDataTable([]);
        getMapas(idCatalogoBusqueda)
            .then((response) => {
                if (response.data.length > 0) {
                    let dataTableTemp = [];
                    response.data.forEach((item, index) => {
                        let row = {
                            ...item,
                            id: index + 1,
                            fechaCreacion: dateFormatNoTime(item.fechaCreacion),
                            fechaModificacion: dateFormatNoTime(item.fechaModificacion),
                        };
                        dataTableTemp.push(row);
                    });

                    // FTV: Revisar si puede afectar este ordenamiento hacerlo antes de asignar el id
                    let newDataTable = dataTableTemp.sort((a, b) => a[ID_KEY_NAME] - b[ID_KEY_NAME]);
                    setDataTable(newDataTable);
                    setShowTable(true);
                    setLoading(false);
                } else {
                    showMessage(MESSAGES.MSG002 + ' ' + catalogoBusquedaText);
                    setDataTable([]);
                    setLoading(false);
                    setShowTable(true);
                }
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                let errorMessage = error?.response?.data?.mensaje[0];
                if (getMessageExists(errorMessage) || errorMessage.includes(MESSAGES.MSG002)) {
                    showMessage(errorMessage);
                } else {
                    showMessage("Ocurrió un error");
                }
            });
    }

    const handleChangeCatalog = (e) => {
        setDataTable([]);
        setShowTable(false);
        let idCatalogo = e.target.value;
        setIdCatalogoBusqueda(idCatalogo);

        if (e.target.value === '') {
            setErrorBuscar(true);
            return;
        }
        setErrorBuscar(false);

        let object = catalogosOptions.find((element) => element.primaryKey === parseInt(idCatalogo));
        setCatalogoBusquedaText(object.nombre);
    }

    const handleEdit = (id) => () => {
        let object = dataTable.find((element) => element[ID_KEY_NAME] === id);
        console.log("handleEdit => ", object);
        let objData = {};
        for (let key in valoresIniciales) {
            objData[key] = object[key];
        }
        objData[ID_KEY_NAME] = id;
        setValoresIniciales({ ...objData });
        setShow(true);
    }

    const downloadExcel = () => {
        setLoading(true);
        downloadMapas(idCatalogoBusqueda)
            .then((response) => {
                downloadExcelBlob(response.data, getExcelName(idCatalogoBusqueda));
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                errorValidations(getMessageExists, error);
            });
    }

    const getExcelName = (id) => {
        let name = "catalogo-";
        let object = catalogosOptions.find((element) => element.primaryKey === Number(id));
        name = name + object.nombre;
        return name;
    }

    const cancelSaveUpdateAccept = () => {
        setShow(false);
        setCancelModal(false);
        setValoresIniciales({ ...valoresInicialesEmpty });
    }

    return (

        <Container className='mt-4 px-0'>

            {loading && <Loader />}

            <Accordion title="Mapas - Objetivos">
                <Row>
                    <Col md={4}>
                        <Select
                            label="Alineación*:"
                            onChange={e => { handleChangeCatalog(e) }}
                            options={catalogosOptions}
                            keyValue="primaryKey"
                            keyTextValue="nombre"
                            className={errorBuscar ? 'is-invalid' : ''}
                            helperText={errorBuscar ? 'Dato requerido' : ''}
                        />
                    </Col>
                    <Col md={4}>
                        <div style={{ paddingTop: "25px" }}>
                            <Button
                                variant="gray"
                                className="btn-sm ms-2 waves-effect waves-light"
                                onClick={handleBuscar}
                            >
                                Buscar
                            </Button>
                        </div>
                    </Col>
                </Row>

                {
                    showTable === true && <>
                        <Row>
                            <Col md={12} className="text-end mb-2">
                                <IconButton
                                    type="add"
                                    onClick={handleAdd}
                                    tooltip={"Nuevo"}
                                />
                                <IconButton
                                    type="excel"
                                    disabled={dataTable.length === 0}
                                    onClick={downloadExcel}
                                    tooltip={"Exportar a Excel"}
                                />
                            </Col>
                        </Row>

                        <TablaDinamica
                            idKeyName={ID_KEY_NAME}
                            idKeyLink={ID_KEY_NAME}
                            headers={headers}
                            data={injectActions(dataTable, { edit: true })}
                            actionFns={{ handleEdit }}
                            onChangeStatus={cambiarEstatus}
                        />
                    </>
                }
            </Accordion>

            <Modal
                show={show}
                onHide={() => setCancelModal(true)}
                size="lg"
                centered
            >
                <Formik
                    initialValues={{ ...valoresIniciales }}
                    enableReinitialize
                    validationSchema={getValidationSchema(valoresIniciales)}
                    validateOnMount={true}
                    onSubmit={(e, { resetForm }) => submitChanges(e, resetForm)}
                >
                    {({
                        handleSubmit,
                        handleChange,
                        setFieldValue,
                        values,
                        errors,
                        touched,
                    }) => (
                        <Form autoComplete="off" onSubmit={handleSubmit}>
                            <Modal.Header closeButton>
                                <Modal.Title>{values[ID_KEY_NAME] ? 'Editar Registro' : 'Nuevo Registro'}</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <Row>
                                    {
                                        metadataForm.map(campo => {
                                            return validaMostrarCampo(campo) &&
                                                <Col key={campo.name} md={campo.name === 'descripcion' ? 8 : 4}>
                                                    {renderInput(campo, handleChange, setFieldValue, values, errors, touched)}
                                                </Col>
                                        })
                                    }
                                </Row>
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
                                    type="submit"
                                >
                                    Guardar
                                </Button>
                            </Modal.Footer>
                        </Form>
                    )}
                </Formik>
            </Modal>

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
                {MESSAGES.MSG010}
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
                {MESSAGES.MSG003}
            </BasicModal>

        </Container >
    );
}

export default Mapas;