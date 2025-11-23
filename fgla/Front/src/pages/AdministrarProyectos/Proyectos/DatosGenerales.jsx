import React, { useState, useEffect } from 'react';
import { Form, Row, Col, Button } from 'react-bootstrap';
import { TextField } from "../../../components/formInputs";
import { Loader, LabelValue } from "../../../components";
import { Formik } from "formik";
import * as yup from "yup";
import IconButton from '../../../components/buttons/IconButton';
import { postData, getData, putData } from '../../../functions/api';
import { useDispatch, useSelector } from 'react-redux';
import { onEditProyecto, onToggleAllSections, onToggleEditar } from '../../../store/pryectos';
import { useToast } from '../../../hooks/useToast';
import moment from "moment";
import showMessage from '../../../components/Messages';
import { ALTA_PROYECTOS as MESSAGES, MODIFICAR_PROYECTOS } from '../../../constants/messages';
import Authorization from '../../../components/Authorization';
import { GetDetalleProyecto } from '../../../store/infoComites/infoComitesActions';
import { useGetAuthorization } from '../../../hooks/useGetAuthorization';
import { useErrorMessages } from '../../../hooks/useErrorMessages';
import BasicModal from '../../../modals/BasicModal';
import { errorValidations } from '../../../functions/utils';
import { logError } from '../../../components/utils/logError.js';

const VALORES_INICIALES = {
    idProyecto: "",
    nombreCorto: "",
    estatusStr: "Inicial",
    idEstatusProyecto: 0,
    nombreProyecto: "",
    idAgp: ""
};

const FORMAT_DATE_TIME = "DD/MM/YYYY HH:mm:ss";

const DatosGenerales = ({ proyecto, setUltimaModificacion, editable }) => {
    const { isAuthorized: accessDG } = useGetAuthorization("PROY_BTN_GUARDARDG");
    const [isDisabled, setIsDisabled] = useState(!editable || !accessDG);
    const { errorToast } = useToast();
    const dispatch = useDispatch();
    const [loading, setLoading] = useState(true);
    const [valoresIniciales, setValoresIniciales] = useState(proyecto ? proyecto : { ...VALORES_INICIALES });
    const [cancelarProyecto, setCancelarProyecto] = useState(null);
    const [avanceEstatus, setAvanceEstatus] = useState(null);
    const [proyectoCancelado, setProyectoCancelado] = useState(false);
    const [proyectoCreado, setProyectoCreado] = useState(false);
    const { getMessageExists } = useErrorMessages(MESSAGES);
    const { getMessageExists: getMessageExistsModif } = useErrorMessages(MODIFICAR_PROYECTOS);
    const [cancelStatusModal, setCancelStatusModal] = useState(false);

    const esquema = yup.object({
        nombreCorto: yup.string().required("Dato requerido"),
        nombreProyecto: yup.string().required("Dato requerido"),
        idAgp: yup.string().required("Dato requerido")
    });
    const { detalleProyecto: ultimaFechaModificacion } = useSelector((state) => state.infoComites);

    useEffect(() => {
        if (ultimaFechaModificacion && (proyecto?.idProyecto || proyectoCreado)) {
            setUltimaModificacion(ultimaFechaModificacion)
        }
    }, [proyecto, proyectoCreado, ultimaFechaModificacion, setUltimaModificacion])

    useEffect(() => {
        dispatch(onToggleAllSections(true));
        if (proyecto && proyecto.idProyecto !== 0) {
            consultaProyecto(proyecto.idProyecto);
        } else {
            getDataInit();
        }
        dispatch(onToggleEditar(editable))
    }, []);

    const getDataInit = (initValues) => {
        let estatusInicial = getData('/proyectos/estatus-inicial');
        Promise.all([estatusInicial])
            .then(([estatusInicialResp]) => {
                setValoresIniciales({
                    ...(initValues || valoresIniciales),
                    idEstatusProyecto: estatusInicialResp.data.primaryKey
                })
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                errorValidations(getMessageExists, error);
            });
    };

    const consultaProyecto = (idProyecto) => {
        getData('/proyectos/ver-detalle/' + idProyecto)
            .then((response) => {
                setValoresIniciales({
                    ...response.data.proyecto,
                    estatusStr: response.data.estatus.nombre
                });
                setAvanceEstatus(response.data.opcionesEstatus);
                setCancelarProyecto(response.data.estatusCancelado);
                setUltimaModificacion(response.data.nombreUsuario + " " + dateFormatDateTime(response.data.ultimaFechaModificacion));
                dispatch(onEditProyecto(response.data));
                dispatch(onToggleAllSections(false));
                getDataInit(
                    {
                        ...response.data.proyecto,
                        estatusStr: response.data.estatus.nombre
                    }
                );
                setProyectoCancelado(response.data.estatus.nombre.toLowerCase().includes("cancelado"));
                if (response.data.estatus.nombre.toLowerCase().includes("cancelado")) dispatch(onToggleEditar(false))
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                errorValidations(getMessageExists, error);
            });
    }

    const dateFormatDateTime = (date) => {
        let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE_TIME) : "";
        return formatedDateTime;
    }

    const guardarDatosGenerales = (data) => {
        setLoading(true);

        if (data.idProyecto) {
            putData('/proyectos/modificar-proyecto/' + data.idProyecto, data)
                .then((response) => {
                    setValoresIniciales({
                        ...response.data.proyecto,
                        estatusStr: response.data.estatus.nombre
                    });
                    setAvanceEstatus(response.data.opcionesEstatus);
                    setCancelarProyecto(response.data.estatusCancelado);
                    setUltimaModificacion(response.data.nombreUsuario + " " + dateFormatDateTime(response.data.ultimaFechaModificacion));
                    setLoading(false);
                    dispatch(onEditProyecto(response.data));
                    showMessage(MODIFICAR_PROYECTOS.MSG006);

                })
                .catch((error) => {
                    logError("error => ", error);
                    setLoading(false);
                    errorValidations(getMessageExists, error);
                });
        } else {
            postData('/proyectos/agregar-proyecto', data)
                .then((response) => {
                    setValoresIniciales({
                        ...response.data.proyecto,
                        estatusStr: response.data.estatus.nombre
                    });
                    setAvanceEstatus(response.data.opcionesEstatus);
                    setCancelarProyecto(response.data.estatusCancelado);
                    setUltimaModificacion(response.data.nombreUsuario + " " + dateFormatDateTime(response.data.ultimaFechaModificacion));
                    setLoading(false);
                    dispatch(onEditProyecto(response.data));
                    dispatch(onToggleAllSections(false));
                    showMessage(MESSAGES.MSG006)
                    setProyectoCreado(true);
                })
                .catch((error) => {
                    logError("error => ", error);
                    setLoading(false);
                    errorValidations(getMessageExists, error);
                });
        }
    };

    const handleChageStatus = (status, idProyecto) => () => {
        setLoading(true);
        setCancelStatusModal(false);

        putData('/proyectos/modificar-estatus/' + idProyecto, status)
            .then((response) => {
                setValoresIniciales({
                    ...response.data.proyecto,
                    estatusStr: response.data.estatus.nombre
                });
                setAvanceEstatus(response.data.opcionesEstatus);
                showMessage(MODIFICAR_PROYECTOS.MSG019);
                setProyectoCancelado(response.data.estatus.nombre.toLowerCase().includes("cancelado"));
                dispatch(onEditProyecto(response.data))
                if (response.data.estatus.nombre.toLowerCase().includes("cancelado")) dispatch(onToggleEditar(false))
                dispatch(GetDetalleProyecto(idProyecto));
                setLoading(false);

            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                setCancelStatusModal(false);
                errorValidations(getMessageExistsModif, error);
            });
    };

    const verificarCancelado = () => {
        setLoading(true);
        getData("/proyectos/verificar-cancelado/" + proyecto?.idProyecto)
            .then((response) => {
                setLoading(false);
                if (response.data) {
                    setCancelStatusModal(true);
                } else {
                    showMessage(MODIFICAR_PROYECTOS.MSG021);
                }
            })
            .catch((error) => {
               logError("error => ", error);
                showMessage("Ocurrió un error");
                setLoading(false);
            })
    }

    return (
        <Formik
            initialValues={valoresIniciales}
            enableReinitialize
            validationSchema={esquema}
            validateOnMount={true}
            onSubmit={(e, { resetForm }) => guardarDatosGenerales(e, resetForm)}
        >
            {({
                handleSubmit,
                handleChange,
                handleBlur,
                values,
                errors,
                touched,
                isValid
            }) => (
                <Form autoComplete="off" onSubmit={handleSubmit}>

                    {loading && <Loader />}

                    <Row>
                        <Col md={4}>
                            <LabelValue
                                label="Id:"
                                value={values.idFormateado}
                            />
                        </Col>
                        <Col md={4}>
                            <TextField
                                label="Nombre corto*:"
                                name="nombreCorto"
                                value={values.nombreCorto}
                                maxLength={50}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                disabled={values.idProyecto}
                                className={touched.nombreCorto && (errors.nombreCorto ? 'is-invalid' : 'is-valid')}
                                helperText={touched.nombreCorto ? errors.nombreCorto : ''}
                            />
                        </Col>
                        <Col md={2}>
                            <LabelValue
                                label="Estatus:"
                                value={values.estatusStr}
                            />
                        </Col>
                        {
                            cancelarProyecto && <Col md={2} className='pt-4 text-end'>
                                <Authorization process={"PROY_STA_CANCEL"} >
                                    <IconButton
                                        type="cancel"
                                        onClick={() => { verificarCancelado() }}
                                        disabled={isDisabled || proyectoCancelado}
                                        tooltip={"Cancelar proyecto"}
                                    />
                                </Authorization>
                            </Col>
                        }
                        <Col md={8}>
                            <TextField
                                label="Nombre del proyecto*:"
                                name="nombreProyecto"
                                value={values.nombreProyecto}
                                maxLength={250}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                className={touched.nombreProyecto && (errors.nombreProyecto ? 'is-invalid' : 'is-valid')}
                                helperText={touched.nombreProyecto ? errors.nombreProyecto : ''}
                                disabled={isDisabled || values.estatusStr === 'Ejecución'}
                            />
                        </Col>
                        <Col md={4}>
                            <TextField
                                label="Id AGP*:"
                                name="idAgp"
                                value={values.idAgp}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                className={touched.idAgp && (errors.idAgp ? 'is-invalid' : 'is-valid')}
                                helperText={touched.idAgp ? errors.idAgp : ''}
                                disabled={isDisabled}
                            />
                        </Col>
                    </Row>

                    {
                        avanceEstatus && <Row>
                            <Col md={12} className="text-end">
                                {avanceEstatus.map(status => (
                                    <Button
                                        variant="gray"
                                        className="btn-sm ms-2 waves-effect waves-light"
                                        onClick={handleChageStatus(status, values.idProyecto)}
                                        disabled={isDisabled}
                                    >
                                        {status.nombre}
                                    </Button>
                                ))}
                            </Col>
                        </Row>
                    }

                    <Authorization process={"PROY_BTN_GUARDARDG"}>
                        <Row>
                            <Col md={12} className="text-end">
                                {
                                    !proyectoCancelado && <Button
                                        variant="green"
                                        className="btn-sm ms-2 waves-effect waves-light"
                                        type="submit"
                                        onClick={() => { !isValid && errorToast(MESSAGES.MSG001) }}
                                        disabled={isDisabled}
                                    >
                                        Guardar
                                    </Button>
                                }
                            </Col>
                        </Row>
                    </Authorization>

                    <BasicModal
                        show={cancelStatusModal}
                        size={"md"}
                        title="Mensaje"
                        approveText={"Sí"}
                        denyText={"No"}
                        handleApprove={handleChageStatus(cancelarProyecto, values.idProyecto)}
                        handleDeny={() => setCancelStatusModal(false)}
                        onHide={() => setCancelStatusModal(false)}
                    >
                        {MODIFICAR_PROYECTOS.MSG017}
                    </BasicModal>
                </Form>
            )}
        </Formik>
    )
}

export default DatosGenerales;
