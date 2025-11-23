import React, { useState } from 'react';
import { Formik } from "formik";
import { Container, Row, Col, Button, Modal } from 'react-bootstrap';
import { Accordion, Loader, LabelValue } from '../../../../components';
import showMessage from '../../../../components/Messages';
import { useToast } from "../../../../hooks/useToast";
import { Select } from "../../../../components/formInputs";
import { ADMIN_CATALOGOS_MOD, ADMIN_CATALOGOS as MESSAGES } from "../../../../constants/messages";
import IconButton from '../../../../components/buttons/IconButton';
import Form from 'react-bootstrap/Form';
import moment from "moment";
import { TablaDinamica } from '../../../../components/table';
import { injectActions } from "../../../../functions/utils";
import BasicModal from '../../../../modals/BasicModal';
import Generales from './Generales';
import Mapas from './Mapas';
import Centrales from './Centrales';
import Administradores from './Administradores';
import { addOrReplace, downloadExcelBlob } from '../../../../functions/utils';
import { renderInput, getMetadataForm, getInitialValues, validaMostrarCampo, getHeadersTable, validarAdminsEstatus, adminRequieredValidations, getValidationSchema, errorValidations } from '../utils';
import { getDataCatalog, getMetadataCatalog, saveDataCatalog, updateDataCatalog, downloadCatalog, putGenerales, downloadAdminGenerales } from '../Catalogos';
import { Tooltip } from '../../../../components/Tooltip';
import { useErrorMessages } from '../../../../hooks/useErrorMessages';
import { logError } from '../../../../components/utils/logError.js';

const FORMAT_DATE = "DD/MM/YYYY";

const CatalogosComplementarios = ({ catalogosOptions }) => {
    const ID_KEY_NAME = "primaryKey";
    const { errorToast } = useToast();
    const [loading, setLoading] = useState(false);
    const [idCatalogoBusqueda, setIdCatalogoBusqueda] = useState('');
    const [catalogoBusquedaText, setCatalogoBusquedaText] = useState('');
    const [valoresIniciales, setValoresIniciales] = useState({});
    const [valoresInicialesEmpty, setValoresInicialesEmpty] = useState({});
    const [headers, setHeaders] = useState([]);
    const [dataTable, setDataTable] = useState([]);
    const [showTable, setShowTable] = useState(false);
    const [confirmModal, setConfirmModal] = useState(false);
    const [cancelModal, setCancelModal] = useState(false);
    const [confirmModalDesactivar, setConfirmModalDesactivar] = useState(false);
    const [idUpdate, setIdUpdate] = useState(null);
    const [metadataForm, setMetadataForm] = useState([]);
    const [idComplementario, setIdComplementario] = useState(null);
    const [compAlineacion, setCompAlineacion] = useState(false);
    const [compGeneral, setCompGeneral] = useState(false);
    const [administradores, setAdministradores] = useState([]);
    const [errorBuscar, setErrorBuscar] = useState(null);
    const [show, setShow] = useState(false);
    const { getMessageExists } = useErrorMessages(MESSAGES);

    const dateFormatNoTime = (date) => {
        let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
        return formatedDateTime;
    }

    const handleAdd = () => {
        setShow(true);
    }

    const submitChanges = (data, guardarInactivo) => {

        if (compGeneral) {
            setConfirmModalDesactivar(false);

            adminRequieredValidations(administradores).then(({ dataErros, resultados }) => {
                if (dataErros) {
                    setAdministradores(resultados);
                    errorToast(MESSAGES.MSG004);
                    return;
                } else {

                    if (data[ID_KEY_NAME]) {
                        let object = dataTable.find((element) => element[ID_KEY_NAME] === data[ID_KEY_NAME]);
                        let statusChange = data.estatus !== object.estatus;

                        if (data[ID_KEY_NAME] && statusChange && !guardarInactivo) {
                            setConfirmModalDesactivar(true);
                            return;
                        }
                    }

                    if (validarAdminsEstatus(administradores) > 1) {
                        showMessage(MESSAGES.MSG8_MOD);
                        return;
                    }

                    saveUpdateGral(data);
                }
            });

        } else {
            if (data[ID_KEY_NAME]) {
                updateCatalogAlin(data);
            } else {

                setLoading(true);

                saveDataCatalog(idCatalogoBusqueda, data)
                    .then((response) => {
                        let obj = response.data;

                        let newItem = {
                            ...obj,
                            fechaCreacion: dateFormatNoTime(obj.fechaCreacion),
                            fechaModificacion: dateFormatNoTime(obj.fechaModificacion),
                        };

                        setDataTable([...dataTable, newItem]);
                        setShow(false);
                        setLoading(false);
                        showMessage(MESSAGES.MSG001);
                        setValoresIniciales({ ...valoresInicialesEmpty });
                    })
                    .catch((error) => {
                        setShow(false);
                        setLoading(false);
                        setValoresIniciales({ ...valoresInicialesEmpty });
                        logError("error => ", error);
                        errorValidations(getMessageExists, error);
                    });
            }
        }
    }

    const cambiarEstatus = (id) => () => {
        setIdUpdate(id);
        setConfirmModal(true);
    }

    const cambiarEstatusAccept = () => {
        setConfirmModal(false);
        setLoading(true);
        let object = dataTable.find((element) => element[ID_KEY_NAME] === idUpdate);

        if (compGeneral && idUpdate) {
            saveUpdateGral({ ...object, estatus: !object.estatus }, object.administradores);

        } else if (idUpdate) {
            updateCatalogAlin({ ...object, estatus: !object.estatus });
        }
    }

    const saveUpdateGral = (data, admins) => {
        setLoading(true);
        let objData = {};

        for (let propiedad in valoresIniciales) {
            objData[propiedad] = data[propiedad];
        }

        let dataForm = {
            admon: {
                ...objData,
                [ID_KEY_NAME]: data[ID_KEY_NAME]
            },
            administradores: admins ? admins : administradores
        }

        putGenerales(dataForm)
            .then((response) => {
                let obj = response.data.admon;

                let newItem = {
                    ...obj,
                    fechaCreacion: dateFormatNoTime(obj.fechaCreacion),
                    fechaModificacion: dateFormatNoTime(obj.fechaModificacion),
                    administradores: response.data.administradores,
                    administrador: buscarAdministrador(response.data)
                };

                setDataTable(addOrReplace(dataTable, newItem, ID_KEY_NAME));
                setShow(false);
                setLoading(false);
                setValoresIniciales({ ...valoresInicialesEmpty });
                setAdministradores([]);
                if (ID_KEY_NAME in data) {
                    showMessage(ADMIN_CATALOGOS_MOD.MSG001);
                } else {
                    showMessage(MESSAGES.MSG001);
                }
            })
            .catch((error) => {
                setShow(false);
                setLoading(false);
                setValoresIniciales({ ...valoresInicialesEmpty });
                logError("error => ", error);
                errorValidations(getMessageExists, error);
            });
    }

    const updateCatalogAlin = (data) => {
        setLoading(true);
        let objData = {};

        for (let propiedad in valoresIniciales) {
            objData[propiedad] = data[propiedad];
        }

        let newData = {
            ...objData,
            [ID_KEY_NAME]: data[ID_KEY_NAME]
        };

        updateDataCatalog(idCatalogoBusqueda, newData)
            .then((response) => {
                let obj = response.data;
                let newItem = {
                    ...obj,
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
        getDataCatalog(idCatalogoBusqueda)
            .then((response) => {
                if (response.data.length > 0) {
                    let dataTableTemp = [];
                    response.data.forEach((item) => {
                        let row = {
                            ...item,
                            administrador: buscarAdministrador(item),
                            fechaCreacion: dateFormatNoTime(item.fechaCreacion),
                            fechaModificacion: dateFormatNoTime(item.fechaModificacion),
                        };
                        dataTableTemp.push(row);
                    });
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
                setDataTable([]);
                errorValidations(getMessageExists, error);
            });
    }

    const buscarAdministrador = (data) => {
        let administrador = data.administradores?.find(admin => admin.estatus);
        return administrador?.administrador || "";
    }

    const handleChangeCatalog = (e) => {
        setCompAlineacion(false);
        setCompGeneral(false);
        setShowTable(false);
        setDataTable([]);
        let idCatalogo = e.target.value;
        setIdCatalogoBusqueda(idCatalogo);

        if (e.target.value === '') {
            setErrorBuscar(true);
            return;
        }
        setErrorBuscar(false);

        let object = catalogosOptions.find((element) => element.idCatalogo === parseInt(idCatalogo));
        setCatalogoBusquedaText(object.catalogo);

        getMetadataCatalog(idCatalogo)
            .then((response) => {
                let metadata = getMetadataForm(response.data.catalogo.metaData);
                let initialValues = getInitialValues(metadata);
                setValoresIniciales({ ...initialValues });
                setValoresInicialesEmpty({ ...initialValues });
                setMetadataForm(metadata);
                setHeaders(getHeadersTable(metadata, ID_KEY_NAME));
                mostrarComplementario(idCatalogo);
                setIdComplementario(response.data.idCatalogoComplementario);
            })
            .catch((error) => {
                logError("error => ", error);
                errorValidations(getMessageExists, error);
            });
    }

    const mostrarComplementario = (id) => {
        let object = catalogosOptions.find((element) => element.idCatalogo == id);
        setCompAlineacion(object.catalogo === "Alineación");
        setCompGeneral(object.catalogo === "Administración general");
    }

    const handleEdit = (id) => () => {
        let object = dataTable.find((element) => element[ID_KEY_NAME] === id);
        let objData = {};
        for (let key in valoresIniciales) {
            objData[key] = object[key];
        }
        objData[ID_KEY_NAME] = id;
        setValoresIniciales({ ...objData });

        let administradores = "administradores" in object ? [...object.administradores] : [];
        setAdministradores(() => administradores.map((item, i) => ({ ...item, id: i + 1 })));
        setShow(true);
    }

    const downloadExcel = () => {
        setLoading(true);
        downloadCatalog(idCatalogoBusqueda)
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
        let object = catalogosOptions.find((element) => element.idCatalogo === Number(id));
        name = name + object.catalogo;
        return name;
    }

    const cancelSaveUpdateAccept = () => {
        setShow(false);
        setCancelModal(false);
        setTimeout(() => {
            setValoresIniciales({ ...valoresInicialesEmpty });
            setAdministradores([]);
            setConfirmModalDesactivar(false);
        }, 500);
    }

    const downloadAdministradores = () => {
        setLoading(true);
        downloadAdminGenerales(valoresIniciales.primaryKey)
            .then((response) => {
                downloadExcelBlob(response.data, 'administradores-' + valoresIniciales.administracion);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                errorValidations(getMessageExists, error);
            });
    }

    return (

        <Container className='mt-3 px-0'>

            {loading && <Loader />}

            <Accordion title="Catálogos complementarios">
                <Row>
                    <Col md={4}>
                        <Select
                            label="Catálogo*:"
                            onChange={e => { handleChangeCatalog(e) }}
                            options={catalogosOptions}
                            keyValue="idCatalogo"
                            keyTextValue="catalogo"
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


                        {
                            compAlineacion && <TablaDinamica
                                idKeyName={ID_KEY_NAME}
                                idKeyLink={ID_KEY_NAME}
                                headers={headers}
                                data={injectActions(dataTable, { edit: true })}
                                actionFns={{ handleEdit }}
                                onChangeStatus={cambiarEstatus}
                            />
                        }

                        {compGeneral && <Generales data={dataTable} handleEdit={handleEdit} cambiarEstatus={cambiarEstatus} />}
                        {idCatalogoBusqueda && compAlineacion && <Mapas idComplementario={idComplementario} idAlineacion={idCatalogoBusqueda} catalogosOptions={dataTable} />}
                        {idCatalogoBusqueda && compGeneral && <Centrales idComplementario={idComplementario} idGeneral={idCatalogoBusqueda} catalogosOptions={dataTable} />}
                    </>
                }
            </Accordion>

            <Modal
                show={show}
                onHide={() => setCancelModal(true)}
                size={valoresIniciales[ID_KEY_NAME] && compGeneral ? "xl" : "lg"}
                centered
            >

                <Formik
                    initialValues={{ ...valoresIniciales }}
                    enableReinitialize
                    validationSchema={getValidationSchema(valoresIniciales)}
                    validateOnMount={true}
                    onSubmit={(e, { resetForm }) => submitChanges(e)}
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
                                        compGeneral && <Col md={4}>
                                            <LabelValue
                                                label="Catálogo:"
                                                value="Administración general"
                                            />
                                        </Col>
                                    }
                                    {
                                        metadataForm.map(campo => {
                                            return validaMostrarCampo(campo) &&
                                                <>
                                                    {
                                                        campo.name === 'nombre' ?
                                                            <Tooltip placement="bottom" text="Información que aparecerá en las listas de selección" >
                                                                <Col key={campo.name} md={4}>
                                                                    {renderInput(campo, handleChange, setFieldValue, values, errors, touched)}
                                                                </Col>
                                                            </Tooltip> :
                                                            <Col key={campo.name} md={campo.name === 'descripcion' ? 8 : 4}>
                                                                {renderInput(campo, handleChange, setFieldValue, values, errors, touched)}
                                                            </Col>
                                                    }
                                                </>
                                        })
                                    }
                                </Row>
                                {compGeneral && values[ID_KEY_NAME] && <Administradores administradores={administradores} setAdministradores={setAdministradores} downloadAdministradores={downloadAdministradores} />}

                                <BasicModal
                                    size="md"
                                    handleApprove={() => { submitChanges(values, true) }}
                                    handleDeny={() => setConfirmModalDesactivar(false)}
                                    denyText={"No"}
                                    approveText={"Sí"}
                                    show={confirmModalDesactivar}
                                    title="Mensaje"
                                    onHide={() => setConfirmModalDesactivar(false)}
                                >
                                    {MESSAGES.MSG011}
                                </BasicModal>
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

export default CatalogosComplementarios;