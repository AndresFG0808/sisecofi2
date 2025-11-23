import React, { useState, useEffect } from 'react';
import { Container, Form, Row, Col, Button } from 'react-bootstrap';
import { Select, SelectMultiple, TextFieldDate } from '../../../components/formInputs';
import { MainTitle, Accordion, Loader } from "../../../components";
import { TablaPaginada } from '../../../components/table';
import { downloadExcelBlob, injectActions } from '../../../functions/utils';
import { Formik } from "formik";
import * as yup from "yup";
import IconButton from '../../../components/buttons/IconButton';
import { useToast } from '../../../hooks/useToast';
import { getData, postData, downloadDocumentPost } from '../../../functions/api';
import showMessage from '../../../components/Messages';
import DetallePista from './DetallePista';
import { PISTAS_AUDITORIA } from '../../../constants/messages';
import { logError } from '../../../components/utils/logError.js';

const VALORES_INICIALES = {
    fechaInicio: "",
    fechaFin: "",
    idEmpleado: [],
    idModulo: "",
    idSeccion: "",
    tipoMovimiento: "",

    page: 0,
    size: 15
}

const HEADERS = [
    { dataField: "modulo", text: "Módulo", sort: true },
    { dataField: "seccion", text: "Sección", filter: true, sort: true },
    { dataField: "fechaHora", text: "Fecha y Hora", filter: true, sort: true },
    { dataField: "empleado", text: "Empleado SAT", filter: true, sort: true },
    { dataField: "rfc", text: "RFC", filter: true, sort: true },
    { dataField: "tipoMovimiento", text: "Tipo de movimiento" },
    { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];

const PAGEABLE = {
    totalPages: 0,
    totalElements: 0,
    pageNumber: 0,
    size: 15
};

const PistasAuditoria = () => {
    const ID_KEY_NAME = "idPista";
    const { errorToast } = useToast();
    const [loading, setLoading] = useState(true);
    const [dataTable, setDataTable] = useState([]);
    const [pageable, setPageable] = useState({ ...PAGEABLE });
    const [valuesConsulta, setValuesConsulta] = useState({});
    const [empleadoOptions, setEmpleadoOptions] = useState([]);
    const [moduloOptions, setModuloOptions] = useState([]);
    const [seccionOptions, setSeccionOptions] = useState([]);
    const [tipoMovimientoOptions, setTipoMovimientoOptions] = useState([]);
    const [dataPista, setDataPista] = useState({});
    const [detallePista, setDetallePista] = useState(false);

    const esquema = yup.object({
        fechaInicio: yup.string().required("Dato requerido"),
        fechaFin: yup.string().required("Dato requerido"),
        idModulo: yup.string().required("Dato requerido")
    });

    useEffect(() => {
        getDataInit();
    }, []);

    const getDataInit = () => {
        let empleados = getData("/reportes/pistas/usuarios");
        let modulos = getData("/reportes/pistas/modulo-pista");
        let movimientos = getData("/reportes/pistas/movimiento-pista");

        Promise.all([empleados, modulos, movimientos])
            .then(([empleadosResp, modulosResp, movimientosResp]) => {
                setEmpleadoOptions(empleadosResp.data);
                setModuloOptions(modulosResp.data);
                setTipoMovimientoOptions(movimientosResp.data);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(PISTAS_AUDITORIA.MSG002);
            });
    };

    const handleChangeModulo = (e) => {
        setLoading(true);
        let idModulo = e.target.value;

        getData("/reportes/pistas/seccion-pista/" + idModulo)
            .then((response) => {
                setSeccionOptions(response.data);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
            });
    }

    const buscarPistas = (data) => {
        console.log("buscarPistas > data => ", data);
        let values = {
            ...data,
            idModulo: data.idModulo ? [Number(data.idModulo)] : [],
            idSeccion: data.idSeccion ? [Number(data.idSeccion)] : [],
            tipoMovimiento: data.tipoMovimiento ? [Number(data.tipoMovimiento)] : []
        };
        setValuesConsulta(values);
        getPistas(values);
    }

    const getPistas = (data) => {
        setLoading(true);
        postData('/reportes/pistas/reporte-pistas', data)
            .then((response) => {
                processData(response.data);
                if (response.data.content.length === 0) {
                    showMessage(PISTAS_AUDITORIA.MSG003);
                }
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                let erroresPistas = Object.values(PISTAS_AUDITORIA);
                let errorMessage = error?.response?.data?.mensaje[0];
                let errorPistas = erroresPistas.includes(errorMessage);
                if (errorPistas) {
                    showMessage(errorMessage);
                } else {
                    showMessage(PISTAS_AUDITORIA.error);
                }
                setPageable({ ...PAGEABLE });
                setDataTable([]);
            });
    }

    const processData = (data) => {
        let processedDataTable = [];
        data.content.forEach((item) => {
            let row = {
                ...item,
            };
            processedDataTable.push(row);
        });

        let pageable = {
            totalPages: data.totalPages,
            totalElements: data.totalElements,
            pageNumber: data.number,
            size: data.size
        };

        setPageable(pageable);
        setDataTable(processedDataTable);
    };

    const updateDataTable = (values) => {

        let data = {
            ...valuesConsulta,
            ...values,
            page: values.page
        };
        setValuesConsulta(data);
        getPistas(data);
    }

    const handleDownloadExcel = () => {
        console.log("handleDownloadExcel => ");
        setLoading(true);
        downloadDocumentPost('/reportes/pistas/reporte-pistas-export', valuesConsulta)
            .then((response) => {
                setLoading(false);
                downloadExcelBlob(response.data, 'reporte-pistas');
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(PISTAS_AUDITORIA.MSG002);
            });
    };

    const handleShow = (idPista) => () => {
        console.log("handleShow > idPista => ", idPista);
        setLoading(true);
        getData("/reportes/pistas/pista/" + idPista)
            .then((response) => {
                setLoading(false);
                console.log("response => ", response);
                setDataPista(response.data);
                setDetallePista(true);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
            });
    }

    return (

        <Container className='mt-3 px-3'>

            {loading && <Loader />}

            <MainTitle title="Pistas auditoria" />

            <Accordion title={"Consultar pistas de auditoría"}>

                <Formik
                    initialValues={{ ...VALORES_INICIALES }}
                    enableReinitialize
                    validationSchema={esquema}
                    validateOnMount={true}
                    onSubmit={(e, { resetForm }) => buscarPistas(e, resetForm)}
                >
                    {({
                        handleSubmit,
                        handleChange,
                        handleBlur,
                        resetForm,
                        setFieldValue,
                        values,
                        errors,
                        touched,
                        isValid
                    }) => (
                        <Form autoComplete="off" onSubmit={handleSubmit}>
                            <Row>

                                <Col md={4}>
                                    <TextFieldDate
                                        label="Fecha inicio*:"
                                        name="fechaInicio"
                                        value={values.fechaInicio}
                                        maxDate={values.fechaFin}
                                        onChange={handleChange}
                                        className={touched.fechaInicio && (errors.fechaInicio ? 'is-invalid' : 'is-valid')}
                                        helperText={touched.fechaInicio ? errors.fechaInicio : ''}
                                    />
                                </Col>

                                <Col md={4}>
                                    <TextFieldDate
                                        label="Fecha fin*:"
                                        name="fechaFin"
                                        value={values.fechaFin}
                                        minDate={values.fechaInicio}
                                        onChange={handleChange}
                                        className={touched.fechaFin && (errors.fechaFin ? 'is-invalid' : 'is-valid')}
                                        helperText={touched.fechaFin ? errors.fechaFin : ''}
                                    />
                                </Col>

                                <Col md={4}>
                                    <SelectMultiple
                                        label="Empleado SAT:"
                                        name="idEmpleado"
                                        values={values.idEmpleado}
                                        onChange={(value) =>
                                            setFieldValue("idEmpleado", value)
                                          }
                                        options={empleadoOptions}
                                        keyValue="idUser"
                                        keyTextValue="nombre"
                                    />
                                </Col>

                                <Col md={4}>
                                    <Select
                                        label="Módulo*:"
                                        name="idModulo"
                                        value={values.idModulo}
                                        onChange={e => { handleChange(e); handleChangeModulo(e); setFieldValue("idSeccion", ""); console.log("value => ", e.target.value) }}
                                        options={moduloOptions}
                                        keyValue="primaryKey"
                                        keyTextValue="nombre"
                                        className={touched.idModulo && (errors.idModulo ? 'is-invalid' : 'is-valid')}
                                        helperText={touched.idModulo ? errors.idModulo : ''}
                                    />
                                </Col>
                                <Col md={4}>
                                    <Select
                                        label={"Sección:"}
                                        name="idSeccion"
                                        value={values.idSeccion}
                                        onChange={handleChange}
                                        options={seccionOptions}
                                        keyValue="primaryKey"
                                        keyTextValue="nombre"
                                    />
                                </Col>
                                <Col md={4}>
                                    <Select
                                        label="Tipo de movimiento:"
                                        name="tipoMovimiento"
                                        value={values.tipoMovimiento}
                                        onChange={handleChange}
                                        options={tipoMovimientoOptions} z
                                        keyValue="primaryKey"
                                        keyTextValue="nombre"
                                    />
                                </Col>
                            </Row>
                            <Row>
                                <Col md={12} className="text-end mb-2">
                                    <Button
                                        variant="green"
                                        className="btn-sm ms-2 waves-effect waves-light"
                                        onClick={() => { if (!(values.fechaInicio && values.fechaFin && values.idModulo)) { errorToast(PISTAS_AUDITORIA.REQUERIDOS); } }}
                                        type="submit"
                                    >
                                        Buscar
                                    </Button>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={12} className="text-end mb-2">
                                    <IconButton
                                        type="excel"
                                        onClick={handleDownloadExcel}
                                        disabled={dataTable.length === 0}
                                        tooltip={"Exportar a Excel"}
                                    />
                                </Col>
                            </Row>
                        </Form>
                    )}
                </Formik>

                <TablaPaginada
                    idKeyName={ID_KEY_NAME}
                    idKeyLink={ID_KEY_NAME}
                    headers={HEADERS}
                    header="Movimientos"
                    data={injectActions(dataTable, { show: true })}
                    actionFns={{ handleShow }}
                    pageable={pageable}
                    updateData={updateDataTable}
                />

                <DetallePista show={detallePista} setShow={setDetallePista} data={dataPista} />

            </Accordion>
        </Container >
    );
}

export default PistasAuditoria;
