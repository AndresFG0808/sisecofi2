import React, { useState, useEffect, useMemo, useRef } from 'react';
import { Container, Form, Row, Col, Button } from 'react-bootstrap';
import { Select, TextFieldDate } from '../../../components/formInputs';
import { MainTitle, Accordion, Loader, ModalSatCloud } from "../../../components";
import { TablaEditable } from '../../../components/table/TablaEditable';
import { downloadExcelBlob } from '../../../functions/utils';
import { Formik } from "formik";
import * as yup from "yup";
import IconButton from '../../../components/buttons/IconButton';
import { useToast } from '../../../hooks/useToast';
import { getData, postData, downloadDocumentPost, putData, deleteData, downloadDocument } from '../../../functions/api';
import showMessage from '../../../components/Messages';
import { PAPELERA_RECICLAJE } from '../../../constants/messages';
import BasicModal from '../../../modals/BasicModal';
import isEmpty from "lodash/isEmpty";
import { logError } from '../../../components/utils/logError.js';

const VALORES_INICIALES = {
    idUsuario: "",
    fecha: "",
    fechaFinal: "",
    page: 0,
    size: 15
}

const PAGEABLE = {
    totalPages: 0,
    totalElements: 0,
    pageNumber: 0,
    size: 15
};

const PapeleraReciclaje = () => {
    const tableReference = useRef();
    const { errorToast } = useToast();
    const [loading, setLoading] = useState(true);
    const [initialValues, setInitialValues] = useState({ ...VALORES_INICIALES });
    const [dataTable, setDataTable] = useState([]);
    const [dataTableSelected, setDataTableSelected] = useState([]);
    const [pageable, setPageable] = useState(PAGEABLE);
    const [valuesConsulta, setValuesConsulta] = useState({});
    const [empleadoOptions, setEmpleadoOptions] = useState([]);
    const [showSatCloudModal, setShowSatCloudModal] = useState(false);
    const [descargaSatCloud, setDescargaSatCloud] = useState(null);
    const [deleteModal, setDeleteModal] = useState(false);
    const [restoreModal, setRestoreModal] = useState(false);
    const [itemChecked, setItemChecked] = useState([]);
    const [allChecked, setAllChecked] = useState(false);

    const esquema = yup.object().shape({
        idEmpleado: yup.string(),
        /* fecha: yup.string().when('fechaFinal', {
            is: (fechaFinal) => fechaFinal ? true : false,
            then: () => yup.string().required('Dato requerido'),
            otherwise: () => yup.string().nullable(),
        }),
        fechaFinal: yup.string().when('fecha', {
            is: (fecha) => fecha ? true : false,
            then: () => yup.string().required('Dato requerido'),
            otherwise: () => yup.string().nullable(),
        }) */
    });

    useEffect(() => {
        getDataInit();
    }, []);

    const getDataInit = () => {
        getData("/reportes/papelera-reciclaje/usuarioSAT")
            .then((response) => {
                setLoading(false);
                setEmpleadoOptions(response.data);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(PAPELERA_RECICLAJE.MSG002);
            });
    };

    const buscarPapelera = (data) => {
        console.log("buscarPapelera > data => ", data);
        const { idUsuario, fecha, fechaFinal } = data;
        if ((fecha && fechaFinal) && !(Date.parse(fecha) <= Date.parse(fechaFinal))) {
            errorToast(PAPELERA_RECICLAJE.MSG001);
        } else if (idUsuario || fecha || fechaFinal) {
            setValuesConsulta(data);
            getPapelera(data);
        } else {
            errorToast(PAPELERA_RECICLAJE.MSG007);
        }
    }

    const getPapelera = (data) => {
        setLoading(true);
        console.log("getPapelera => ", data);

        let values = {
            ...data,
            fecha: new Date(data.fecha),
            fechaFinal: new Date(data.fechaFinal)
        }

        postData('/reportes/papelera-reciclaje/buscar-papelera', values)
            .then((response) => {
                setLoading(false);
                if (Array.isArray(response.data?.content) && response.data.content.length > 0) {
                    processData(response.data);
                } else {
                    showMessage(PAPELERA_RECICLAJE.MSG008);
                }
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                setDataTable([]);
                let erroresPistas = Object.values(PAPELERA_RECICLAJE);
                let errorMessage = error?.response?.data?.mensaje[0];
                let errorPistas = erroresPistas.includes(errorMessage);
                if (errorPistas) {
                    showMessage(errorMessage);
                } else {
                    showMessage("Ocurrió un error");
                }
            });
    }

    const processData = (data) => {
        let processedDataTable = [];
        data.content.forEach((item) => {
            let row = {
                ...item,
                checked: false
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
            ...values
        };
        setValuesConsulta(data);
        getPapelera(data);
    }

    const handleDownloadExcel = () => {
        //SE DEBE DESCARGAR TODA LA INFORMACIÓN DE LA TABLA NO SOLO LOS ELEMENTOS SELECCIONADOS, SE REVISÓ CON ANGEL
        const data = [...tableReference.current.table.options.meta.getDataTable()];
        const selected = data.filter(o => o.checked).map(o => o.idPapelera);
        if (selected.length > 0) {
            setLoading(true);
            downloadDocumentPost('/reportes/papelera-reciclaje/exportar-excel', { idPapelera: selected })
                .then((response) => {
                    setLoading(false);
                    downloadExcelBlob(response.data, 'reporte-papelera');
                })
                .catch((error) => {
                    logError("error => ", error);
                    setLoading(false);
                    showMessage(PAPELERA_RECICLAJE.MSG002);
                });
        }
    };

    const handleDownloadZip = () => {
        const data = [...tableReference.current.table.options.meta.getDataTable()];
        const selected = data.filter(o => o.checked).map(o => o.idPapelera);
        if (selected.length > 0) {
            setLoading(true);
            downloadDocumentPost('/reportes/papelera-reciclaje/descargar-zip', { idPapelera: selected })
                .then((response) => {
                    setLoading(false);
                    let file = new Blob([response.data]);
                    let fileURL = URL.createObjectURL(file);
                    let link = document.createElement("a");
                    link.href = fileURL;
                    link.setAttribute("download", "documentos-papelera.zip");
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                })
                .catch((error) => {
                    logError("error => ", error);
                    setLoading(false);
                    showMessage(PAPELERA_RECICLAJE.MSG002);
                });
        }
    };

    const handleDownload = (idPapelera) => () => {
        console.log("handleDownload > idPapelera => ", idPapelera);
        setLoading(true);
        const data = [...tableReference.current.table.options.meta.getDataTable()];
        let file = data.find(file => file.idPapelera === idPapelera);
        let fileName = file.pathOriginal.split("/");

        downloadDocument("/reportes/papelera-reciclaje/descargar-archivo/" + idPapelera)
            .then((response) => {
                setLoading(false);
                let file = new Blob([response.data]);
                let fileURL = URL.createObjectURL(file);
                let link = document.createElement("a");
                link.href = fileURL;
                link.setAttribute("download", fileName.pop());
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage(PAPELERA_RECICLAJE.MSG003);
            });
    }

    const handleClear = (resetForm) => () => {
        console.log("handleClear");
        setDataTable([]);
        setDataTableSelected([]);
        setItemChecked([]);
        setInitialValues({ ...VALORES_INICIALES });
        resetForm();
    }

    const deleteAccept = () => {
        const data = [...tableReference.current.table.options.meta.getDataTable()];
        let selectedDelete = data.filter(item => item.checked);
        console.log("deleteAccept > selected => ", selectedDelete);
        setLoading(true);
        setDeleteModal(false);

        deleteData('/reportes/papelera-reciclaje/eliminar-archivo-papelera', selectedDelete)
            .then((response) => {
                setLoading(false);
                showMessage(PAPELERA_RECICLAJE.MSG012);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                let messages = Object.values(PAPELERA_RECICLAJE);
                let errorMessage = error?.response?.data?.mensaje[0];
                let controlledError = messages.includes(errorMessage);
                if (controlledError) {
                    showMessage(errorMessage);
                } else {
                    showMessage(PAPELERA_RECICLAJE.MSG003);
                }
            });
    }

    const restoreAccept = () => {
        const data = [...tableReference.current.table.options.meta.getDataTable()];
        let selected = data.filter(item => item.checked);
        console.log("restoreAccept > selected => ", selected);
        setLoading(true);
        setRestoreModal(false);

        putData('/reportes/papelera-reciclaje/restaurar-archivo-papelera', selected)
            .then((response) => {
                setLoading(false);
                showMessage(PAPELERA_RECICLAJE.MSG013);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                let messages = Object.values(PAPELERA_RECICLAJE);
                let errorMessage = error?.response?.data?.mensaje[0];
                let controlledError = messages.includes(errorMessage);
                if (controlledError) {
                    showMessage(errorMessage);
                } else {
                    showMessage(PAPELERA_RECICLAJE.MSG003);
                }
            });
    }

    const CustomCheckedHeader = ({ tableReference, isEditable }) => {
        const [checked, setChecked] = useState(false);

        const data = tableReference.current
            ? [...tableReference.current.table.options.meta.getDataTable()]
            : [];

        useEffect(() => {
            if (isEmpty(data) === false) {
                const filterCheckedData = data.filter((item) => item.checked === true);
                if (
                    isEmpty(data) === false &&
                    filterCheckedData.length === data.length
                ) {
                    setChecked(true);
                } else {
                    setChecked(false);
                }
            } else {
                setChecked(false);
            }
        }, [data]);

        return (
            <>
                <Form.Check
                    type={"checkbox"}
                    id={"1"}
                    onChange={() => {
                        const value = !checked;
                        setChecked(value);
                        if (isEmpty(data) === false) {
                            let newData = data.map((item) => {
                                const newItem = { ...item };
                                newItem.checked = value;
                                return newItem;
                            });
                            tableReference.current.table.options.meta.revertData(newData);
                            let selected = newData.filter(item => item.checked);
                            setItemChecked(selected);
                        } else {
                            setItemChecked([]);
                        }
                    }}
                    style={{ cursor: "pointer" }}
                    disabled={!isEditable}
                    checked={checked}
                />
            </>
        );
    };

    const CustomChecked = ({ getValue, onChangeChecked, row, isEditable }) => {
        const checked = getValue();
        const { idPapelera } = row.original;
        return (
            <>
                <Form.Check
                    type={"checkbox"}
                    id={"1"}
                    disabled={!isEditable}
                    onChange={() => {
                        onChangeChecked(idPapelera, !checked);
                    }}
                    style={{ cursor: "pointer" }}
                    checked={checked}
                />
            </>
        );
    };

    const onChangeChecked = async (idPapelera, value) => {
        const data = [...tableReference.current.table.options.meta.getDataTable()];

        const dataArray = [...data].map((item) => {
            return {
                ...item,
                checked: item.idPapelera === idPapelera ? value : item.checked
            };
        });

        let selected = dataArray.filter(item => item.checked);
        setItemChecked(selected);
        await tableReference.current.table.options.meta.revertData(dataArray);
    };

    const columns = useMemo(() => [
        {
            accessorKey: "checked",
            header: (props) => (
                <CustomCheckedHeader
                    isEditable={true}
                    tableReference={tableReference}
                />
            ),
            cell: (props) => (
                <CustomChecked
                    onChangeChecked={onChangeChecked}
                    row={props.row}
                    getValue={props.getValue}
                    isEditable={true}
                />
            ),
            enableColumnFilter: false,
            enableSorting: false
        },
        {
            accessorKey: "idProyectoFormateado",
            header: "Id de proyecto",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "nombreCorto",
            header: "Nombre corto",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "fase",
            header: "Fase",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "plantilla",
            header: "Plantilla",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "nombreDocumento",
            header: "Nombre del documento",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "descripcion",
            header: "Descripción",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "fechaEliminacion",
            header: "Fecha eliminación",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "usuarioElimina",
            header: "Usuario que eliminó",
            cell: (props) => <p>{props.getValue()}</p>
        },
        {
            accessorKey: "tamano",
            header: "Tamaño",
            cell: (props) => <p>{props.getValue()}</p>,
            enableColumnFilter: false
        },
        {
            accessorKey: "acciones",
            header: "Acciones",
            cell: (props) => (
                <IconButton
                    type={"tableDownload"}
                    iconSize="lg"
                    tooltip={"Descargar"}
                    onClick={handleDownload(props.row.original.idPapelera)}
                />
            ),
            enableColumnFilter: false,
            enableSorting: false
        },
    ], []);

    return (

        <Container className='mt-3 px-3'>

            {loading && <Loader />}

            <MainTitle title="Papelera de reciclaje" />

            <Accordion title={"Criterios de búsqueda"}>

                <Formik
                    initialValues={{ ...initialValues }}
                    validationSchema={esquema}
                    validateOnMount={true}
                    enableReinitialize
                    onSubmit={(e, { resetForm }) => buscarPapelera(e, resetForm)}
                >
                    {({
                        handleSubmit,
                        handleChange,
                        handleBlur,
                        resetForm,
                        values,
                        errors,
                        touched,
                        isValid
                    }) => (
                        <Form autoComplete="off" onSubmit={handleSubmit}>
                            <Row>
                                <Col md={4} className='mt-4'>
                                    <Select
                                        label="Empleado SAT:"
                                        name="idUsuario"
                                        value={values.idUsuario}
                                        onChange={handleChange}
                                        options={empleadoOptions}
                                        keyValue="idUser"
                                        keyTextValue="nombre"
                                    />
                                </Col>

                                <Col md={4}>
                                    <Row>
                                        <label>Fecha de eliminación</label>
                                        <Col md={6}>
                                            <TextFieldDate
                                                label="Desde:"
                                                name="fecha"
                                                value={values.fecha}
                                                onChange={handleChange}
                                                maxDate={values.fechaFinal}
                                                className={touched.fecha && (errors.fecha ? 'is-invalid' : '')}
                                                helperText={touched.fecha ? errors.fecha : ''}
                                            />
                                        </Col>
                                        <Col md={6}>
                                            <TextFieldDate
                                                label="Hasta:"
                                                name="fechaFinal"
                                                value={values.fechaFinal}
                                                onChange={handleChange}
                                                minDate={values.fecha}
                                                className={touched.fechaFinal && (errors.fechaFinal ? 'is-invalid' : '')}
                                                helperText={touched.fechaFinal ? errors.fechaFinal : ''}
                                            />
                                        </Col>
                                    </Row>
                                </Col>

                                <Col md={4} className='mt-4'>
                                    <Button
                                        variant="gray"
                                        className="btn-sm ms-2 waves-effect waves-light mt-4"
                                        onClick={handleClear(resetForm)}
                                    >
                                        Limpiar
                                    </Button>
                                    <Button
                                        variant="green"
                                        className="btn-sm ms-2 waves-effect waves-light mt-4"
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
                                        disabled={itemChecked.length === 0}
                                        tooltip={"Exportar documentos"}
                                    />
                                    <IconButton
                                        type="zip"
                                        onClick={handleDownloadZip}
                                        disabled={itemChecked.length === 0}
                                        tooltip={"Descargar ZIP"}
                                    />
                                </Col>
                            </Row>
                        </Form>
                    )}
                </Formik>

                <TablaEditable
                    ref={tableReference}
                    header="Archivos eliminados"
                    dataTable={dataTable}
                    manualPagination
                    pageable={pageable}
                    hasPagination
                    colSpanSubCols={25}
                    columns={columns}
                    onUpdate={setDataTable}
                    onChangePagination={updateDataTable}
                />

                <Col md={12} className='text-end mt-4'>
                    <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => setDeleteModal(true)}
                    >
                        Eliminar
                    </Button>
                    <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => setRestoreModal(true)}
                    >
                        Restaurar
                    </Button>
                </Col>

                <ModalSatCloud
                    show={showSatCloudModal}
                    handleClose={() => { setShowSatCloudModal(false); setDescargaSatCloud(null); }}
                    url={descargaSatCloud ? descargaSatCloud.url : ""}
                    password={descargaSatCloud ? descargaSatCloud.password : ""}
                />

            </Accordion>

            <BasicModal
                size="md"
                handleApprove={deleteAccept}
                handleDeny={() => setDeleteModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={deleteModal}
                title="Mensaje"
                onHide={() => setDeleteModal(false)}
            >
                {PAPELERA_RECICLAJE.MSG009}
            </BasicModal>

            <BasicModal
                size="md"
                handleApprove={restoreAccept}
                handleDeny={() => setRestoreModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={restoreModal}
                title="Mensaje"
                onHide={() => setRestoreModal(false)}
            >
                {PAPELERA_RECICLAJE.MSG011}
            </BasicModal>

        </Container >
    );
}

export default PapeleraReciclaje;
