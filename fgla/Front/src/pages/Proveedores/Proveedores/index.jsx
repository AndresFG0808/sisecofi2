import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Container, Form, Row, Col, Button } from 'react-bootstrap';
import { Select } from "../../../components/formInputs";
import { MainTitle, Loader, Accordion } from "../../../components";
import TablaPaginadaProveedores from './componets/TablaPaginadaProveedores';
import { Formik } from "formik";
import { downloadExcelBlob, injectActions } from "../../../functions/utils";
import IconButton from '../../../components/buttons/IconButton';
import DetalleDictamen from "../Proveedores/DetalleDictamen";
import DetalleDirectorio from "../Proveedores/DetalleDirectorio";
import DetalleTitulos from "../Proveedores/DetalleTitulos";
import { postData, getData, downloadDocument, putData } from '../../../functions/api';
import { PROVEEDORES as MESSAGES } from '../../../constants/messages';
import { useToast } from "../../../hooks/useToast";
import BasicModal from "../../../modals/BasicModal";
import { Tooltip } from "../../../components/Tooltip";
import showMessage from '../../../components/Messages';
import Authorization from '../../../components/Authorization';
import { useGetAuthorization } from '../../../hooks/useGetAuthorization';

const PAGEABLE = {
    totalPages: 0,
    totalElements: 0,
    pageNumber: 0,
    size: 15
};

const VALORES_INICIALES = {
    giroEmpresa: "",
    tituloDeServicio: "",
    cumpleDictamen: "",
    nombreProveedor: "",
    nombreComercial: "",
    rfc: "",
    direccion: "",
    omentarios: "",
    estatus: "",
    idAgs: "",
    idGiroEmpresarial: "",
};


const HEADERS = [
    { dataField: "idProveedor", text: "Id", sort: true, },
    { dataField: "nombreProveedor", text: "Nombre del proveedor", filter: true, sort: true, },
    { dataField: "nombreComercial", text: "Nombre comercial", filter: true, sort: true, },
    { dataField: "idAgs", text: "Id AGS", filter: true, sort: true, },
    { dataField: "giroDeLaEmpresa", text: "Giro de la empresa", sort: true, },
    { dataField: "directorioContacto", text: "Directorio de contacto" },
    { dataField: "rfc", text: "RFC", filter: true, sort: true, },
    { dataField: "representanteLegal", text: "Representante legal", filter: true, sort: true, },
    { dataField: "tituloDeServicio", text: "Título de servicio" },
    { dataField: "vigencia", text: "Vigencia" },
    { dataField: "vencimientoTitulo", text: "Fecha de vencimiento", filter: true, sort: true, },
    { dataField: "cumpleDictamen", text: "Cumple dictamen" },
    { dataField: "estatus", text: "Estatus", formatter: 'switch' },
    { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];

const Proveedores = () => {
    const ID_KEY_NAME = "idProveedor";
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [showDetalleDictamen, setShowDetalleDictamen] = useState(false);
    const [showDetalleDirectorio, setShowDetalleDirectorio] = useState(false);
    const [showDetalleTitulos, setShowDetalleTitulos] = useState(false);
    const [valoresIniciales, setValoresIniciales] = useState({ ...VALORES_INICIALES });
    const [dataTable, setDataTable] = useState([]);
    const [ver, setVer] = useState(false);
    const [edit, setEdit] = useState(false);
    const [giroEmpresaOptions, setGiroEmpresaOptions] = useState([]);
    const [proveedoresCat, setProveedoresCat] = useState([]);
    const [valuesConsulta, setValuesConsulta] = useState({});
    const [selectedProveedorId, setSelectedProveedorId] = useState(null);
    const [selectedDirectorio, setSelectedDirectorio] = useState([]);
    const [selectedTitulo, setSelectedTitulo] = useState([]);
    const [tituloServicioOptions, setTituloServicioOptions] = useState([]);
    const [resultadoOptions, setResultadoOptions] = useState([]);
    const [selectedDictamen, setSelectedDictamen] = useState([]);
    const [searchExcell, setSearchExcell] = useState([]);
    const [pageable, setPageable] = useState(PAGEABLE);
    const [showModal, setShowModal] = useState(false);
    const [pageSearch, setPageSearch] = useState(false);
    const [modalMessage, setModalMessage] = useState("");
    const [idEstatusChange, setIdEstatusChange] = useState("");
    const { isAuthorized: canEditProv } = useGetAuthorization("PROV_MODIF");
    const [estatusTitulos,setEstatusTitulos]=useState([]);

    useEffect(() => {
        getDataInit()

    }, []);

    const getDataInit = () => {
        setLoading(false);
        let consultaGiro = getData('/proveedores/consultar-todos-giros');
        let titulos = getData('/proveedores/consultar-todos-titulos-servicios');
        let dictamen = getData(`/proveedores/obtener-resultado-dictamen`);
        let estatusTitulos = getData("/proveedores/consultar-todos-estatus-semaforo")

        Promise.all([consultaGiro, titulos, dictamen,estatusTitulos])
            .then(([consultaGiroResp, titulosResp, dictamenResp,estatusTitulosResp]) => {
                const optionsConsultaGiro = consultaGiroResp.data.map(item => ({
                    giroEmpresarial: item.nombre,
                    idGiroEmpresarial: item.primaryKey
                }));

                const optionsTitulos = titulosResp.data.map(item => ({
                    nombreTituloServicio: item.nombre,
                    idServicioTitulo: item.primaryKey
                }));

                const optionsDictamen = dictamenResp.data.map(item => ({
                    resultado: item.nombre,
                    idCatResultadoDictamen: item.primaryKey
                }));

                setEstatusTitulos(estatusTitulosResp.data);
                setGiroEmpresaOptions(optionsConsultaGiro);
                setTituloServicioOptions(optionsTitulos);
                setResultadoOptions(optionsDictamen);
                setLoading(false);
            })
            .catch((error) => {
                showMessage(MESSAGES.MSG004);
                setLoading(false);
            });
    };

    const createPayloadWithoutPagination = (data) => {
        const updatedValues = {
            idTituloServicio: data.tituloDeServicio ? parseInt(data.tituloDeServicio, 10) : "",
            idGiroEmpresarial: data.idGiroEmpresarial ? parseInt(data.idGiroEmpresarial, 10) : "",
            idCatResultadoDictamen: data.cumpleDictamen ? parseInt(data.cumpleDictamen, 10) : ""
        };

        return updatedValues;
    };

    const buscarProveedor = async (data, isPagination = false) => {
        const fetchData = async (payload) => {
            try {
                console.log(payload);
                const proveedoresResp = await postData('/proveedores/proveedores-general', payload);
                if (proveedoresResp.data.content.length === 0) {
                    showMessage(MESSAGES.MSG006);
                    setDataTable([]);
                } else {
                    const updatedContent = proveedoresResp.data.content.map(item => ({
                        ...item,
                        ...payload
                    }));

                    const updatedData = {
                        ...proveedoresResp.data,
                        content: updatedContent
                    };

                    setProveedoresCat(updatedData.content);
                    processData(updatedData);
                }
            } catch (error) {
                console.error("error => ", error);
                showMessage(MESSAGES.MSG004);
            } finally {
                setLoading(false);
            }
        };

        setLoading(true);
        if (isPagination) {
            const payload = {
                ...valuesConsulta,
                page: data.page || 0,
                size: data.size || 15,
            };
            setSearchExcell(payload);
            await fetchData(payload);
        } else {
            const updatedValues = createPayloadWithoutPagination(data);
            setValuesConsulta(updatedValues);
            const payload = {
                ...updatedValues,
                page: data.page || 0,
                size: data.size || 15,
            };
            setSearchExcell(payload);
            await fetchData(payload);
        }
    };

    const processData = (data) => {
        if (!Array.isArray(data.content)) {
            console.error("processData: data.content no es un arreglo", data.content);
            return;
        }

        let processedDataTable = data.content.map((item) => ({

            ...item,
            directorioContacto: convertirDirectorio(item),
            tituloDeServicio: convertirTitulo(item),
            cumpleDictamen: convertirDictamen(item),
            vigencia: semaforoVigenciaColor(item)
        }));

        let pageable = {
            totalPages: data.totalPages || 0,
            totalElements: data.totalElements || 0,
            pageNumber: data.number || 0,
            size: data.size || 15
        };

        setPageable(pageable);
        setDataTable(processedDataTable);
    };


    const semaforoVigenciaColor = (data) => (
        <div style={{ whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" }}>
            <span><CirculoVigencia color={data.vigencia} />{data.vigencia}</span>
        </div>
    );
    const CirculoVigencia = ({ color }) => {
        if (!color) {
            return null;
        }

        const isZeroM = color === '0m';
        const numero = parseFloat(color.replace('m', ''));

        const circleColor = (num, isZeroM) => {
            if (isZeroM) return '#FF0000';
            if (num === 0) return null;
            if (num > 0 && num <= 3) return '#FF0000';
            if (num > 3) return 'white';
            return 'gray';
        };

        const borderColor = (num, isZeroM) => {
            if (num > 3) return 'black';
            return 'transparent';
        };

        const tooltipText = (num, isZeroM) => {
            if (isZeroM || (num > 0 && num <= 3)) {
                return 'Menor a tres meses respecto a la fecha de hoy';
            }
            return 'Mayor a tres meses respecto a la fecha de hoy';
        };

        const circleStyle = {
            width: '16px',
            height: '16px',
            borderRadius: '50%',
            backgroundColor: circleColor(numero, isZeroM),
            display: 'inline-block',
            marginRight: '5px',
            border: `1px solid ${borderColor(numero, isZeroM)}`
        };

        return circleColor(numero, isZeroM) ? (
            <Tooltip text={tooltipText(numero, isZeroM)}>
                <div style={circleStyle} />
            </Tooltip>
        ) : null;
    };

    const convertirDictamen = (data) => (
        <u>
            <span onClick={() => verDictamen(data)} style={{ cursor: 'pointer' }}>
                {data.cumpleDictamen}
            </span>
        </u>
    );

    const convertirTitulo = (data) => (
        <u>
            <span onClick={() => verTitulo(data)} style={{ cursor: 'pointer' }}>
                {data.tituloDeServicio}
            </span>
        </u>
    );

    const convertirDirectorio = (data) => (
        <u>
            <span onClick={() => verTelefono(data.idProveedor)} style={{ cursor: 'pointer' }}>
                <IconButton
                    type="phone"
                    iconSize="lg"
                    tableContainer
                />
            </span>
        </u>
    );

    const handleAltaProveedor = () => {
        navigate('/proveedores/proveedores/alta');
    };
    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleDownloadExcel = async () => {
        const idGiroEmpresarial = searchExcell.idGiroEmpresarial || '0';
        const idTituloServicio = searchExcell.idTituloServicio || '0';
        const idCatResultadoDictamen = searchExcell.idCatResultadoDictamen || '0';
       
        let basePath = '/proveedores/reporte-general-proveedor';
        let queryParams = [];

        if (idGiroEmpresarial !== '0') {
            queryParams.push(`idGiroEmpresarial=${idGiroEmpresarial}`);
        }
        if (idTituloServicio !== '0') {
            queryParams.push(`idTituloServicio=${idTituloServicio}`);
        }
        if (idCatResultadoDictamen !== '0') {
            queryParams.push(`idCatResultadoDictamen=${idCatResultadoDictamen}`);
        }

        let fullPath = queryParams.length > 0 ? `${basePath}?${queryParams.join('&')}` : basePath;

        console.log(fullPath);
        setLoading(false);

        try {
            setLoading(true);
            const response = await downloadDocument(fullPath);
            downloadExcelBlob(response.data, "Reporte general de proveedor");
            setLoading(false);
        } catch (error) {
            showMessage(MESSAGES.MSG005);
            setLoading(false);
        }
    };

    const verTelefono = async (idProveedor) => {
        if (idProveedor) {
            try {
                const directorioResp = await getData(`/proveedores/proveedor/${idProveedor}`);
                setSelectedProveedorId(idProveedor);
                setSelectedDirectorio(directorioResp.data.directorioContactos);
                setShowDetalleDirectorio(true);
            } catch (error) {
                showMessage(MESSAGES.MSG004)
            }
        }
    };

    const verDictamen = async (data) => {

        if (data.idProveedor) {
            const payload = {
                idProveedor: data.idProveedor,
                idTituloServicio: data.idTituloServicio,
                idCatResultadoDictamen: data.idCatResultadoDictamen,
                idGiroEmpresarial: data.idGiroEmpresarial,
            }
            try {
                const response = await postData(`/proveedores/proveedor-validar-cumple`, payload);
                setSelectedProveedorId(data.idProveedor);
                setSelectedDictamen(response.data);
                setShowDetalleDictamen(true);
            } catch (error) {
                showMessage(MESSAGES.MSG004);
            }
        }
    };

    const verTitulo = async (data) => {
        if (data.idProveedor) {
            const payload = {
                idProveedor: data.idProveedor,
                idTituloServicio: data.idTituloServicio,
                idGiroEmpresarial: data.idGiroEmpresarial,
                idCatResultadoDictamen: data.idCatResultadoDictamen
            }
            try {
                const response = await postData(`/proveedores/proveedor-titulos-general`, payload);
                setSelectedProveedorId(data.idProveedor);
                setSelectedTitulo(response.data);
                setShowDetalleTitulos(true);
            } catch (error) {
                showMessage(MESSAGES.MSG004)
            }
        }

    };

    const handleShow = (idProveedor) => () => {
        const index = dataTable.findIndex(item => item.idProveedor === idProveedor);
        if (index !== -1) {
            setVer(true);
            navigate('/proveedores/proveedores/detalle', { state: { ver: true, idProveedor } });
        }
    };

    const handleEdit = (idProveedor) => () => {
        const index = dataTable.findIndex(item => item.idProveedor === idProveedor);
        if (index !== -1) {
            setEdit(true);
            navigate('/proveedores/proveedores/editar', { state: { edit: true, idProveedor } });
        }
    };

    const updateProveedorEstado = async (id, newStatus) => {
        try {
            const giroEmpresaDropResp = await getData(`/proveedores/proveedor/${id}`);
            const { giroEmpresarialModel = {} } = giroEmpresaDropResp.data;
            const idGiroEmpresarial = giroEmpresarialModel.primaryKey ? parseInt(giroEmpresarialModel.primaryKey, 10) : "";

            const estado = {
                nombreProveedor: giroEmpresaDropResp.data.nombreProveedor,
                nombreComercial: giroEmpresaDropResp.data.nombreComercial,
                rfc: giroEmpresaDropResp.data.rfc,
                direccion: giroEmpresaDropResp.data.direccion,
                comentarios: giroEmpresaDropResp.data.comentarios,
                estatus: newStatus,
                idAgs: giroEmpresaDropResp.data.idAgs,
                idGiroEmpresarial
            };

            const response = await putData(`/proveedores/proveedor/${id}`, estado);
            const obj = response.data;
            const updatedItem = {
                nombreProveedor: obj.nombreProveedor,
                nombreComercial: obj.nombreComercial,
                rfc: obj.rfc,
                direccion: obj.direccion,
                comentarios: obj.comentarios,
                estatus: obj.estatus,
                idAgs: obj.idAgs,
                idGiroEmpresarial: obj.idAgs
            };

            const indexUpdate = dataTable.findIndex((reg) => reg[ID_KEY_NAME] === obj[ID_KEY_NAME]);
            if (indexUpdate !== -1) {
                const newDataTable = [...dataTable];
                newDataTable.splice(indexUpdate, 1, updatedItem);
                setDataTable(newDataTable);
            } else {
                showMessage(MESSAGES.MSG008);
            }
            return response;
        } catch (error) {
            showMessage(MESSAGES.MSG004)
            setLoading(false);
            throw error;
        }
    };



    const onChangeStatusProveedor = (id) => async () => {
        setIdEstatusChange(id);
        try {
            let object = dataTable.find((element) => element[ID_KEY_NAME] === id);

            if (object.estatus === true) {
                setShowModal(true);
                setModalMessage(MESSAGES.MSG009);
            } else {
                await updateProveedorEstado(id, !object.estatus);
                setDataTable(prevState =>
                    prevState.map(item =>
                        item[ID_KEY_NAME] === id ? { ...item, estatus: !object.estatus } : item
                    )
                );
            }
        } catch (error) {
            showMessage(MESSAGES.MSG003);
            setLoading(false);
        }
    };


    const handleDeny = () => {

        handleCloseModal();
    };

    const handleAccept = async () => {
        try {
            let object = dataTable.find((element) => element[ID_KEY_NAME] === idEstatusChange);
            await updateProveedorEstado(idEstatusChange, !object.estatus);
            setDataTable(prevState =>
                prevState.map(item =>
                    item[ID_KEY_NAME] === idEstatusChange ? { ...item, estatus: !object.estatus } : item
                )
            );
        } catch (error) {
            showMessage(MESSAGES.MSG003);
            setLoading(false);
        }
        handleCloseModal();
    };

    const handleApprove = () => {
        setShowDetalleDictamen(false);
        setShowDetalleDirectorio(false);
        setShowDetalleTitulos(false);
    };

    const updateDataTable = (values) => {
        console.log(values);
        let data = {
            ...valuesConsulta,
            ...values
        };
        console.log(data);
        setPageSearch(true);
        setValuesConsulta(data);
        buscarProveedor(data, true);
    };

    return (
        <Container className='mt-3 px-3'>
            <MainTitle title="Proveedores" />
            <Accordion title="Buscar proveedor">
                {loading && <Loader />}
                <Formik
                    initialValues={valoresIniciales}
                    enableReinitialize
                    onSubmit={(values, { resetForm }) => buscarProveedor(values)}
                >
                    {({
                        handleSubmit,
                        handleChange,
                        handleBlur,
                        resetForm,
                        values,
                        errors,
                        isValid
                    }) => (
                        <Form autoComplete="off" onSubmit={handleSubmit}>
                            <Row>
                                <Col md={4}>
                                    <Select
                                        label="Giro de la empresa:"
                                        name="idGiroEmpresarial"
                                        value={values.idGiroEmpresarial}
                                        onChange={handleChange}
                                        options={giroEmpresaOptions}
                                        keyValue="idGiroEmpresarial"
                                        keyTextValue="giroEmpresarial"
                                    />
                                </Col>
                                <Col md={4}>
                                    <Select
                                        label="Título de servicio:"
                                        name="tituloDeServicio"
                                        value={values.tituloDeServicio}
                                        onChange={handleChange}
                                        options={tituloServicioOptions}
                                        keyValue="idServicioTitulo"
                                        keyTextValue="nombreTituloServicio"
                                    />
                                </Col>
                                <Col md={4}>
                                    <Select
                                        label="Cumple dictamen:"
                                        name="cumpleDictamen"
                                        value={values.cumpleDictamen}
                                        onChange={handleChange}
                                        options={resultadoOptions}
                                        keyValue="idCatResultadoDictamen"
                                        keyTextValue="resultado"
                                    />
                                </Col>
                            </Row>
                            <Authorization process={"PROV_CONS"}>
                                <Row>
                                    <Col md={12} className="text-end mb-2">
                                        <Button
                                            variant="gray"
                                            className="btn-sm ms-2 waves-effect waves-light"
                                            type="submit"
                                        >
                                            Buscar
                                        </Button>
                                    </Col>
                                </Row>
                            </Authorization>
                            <Row>
                                <Col md={12} className="text-end mb-2">
                                    <Authorization process={"PROV_ALTA"}>
                                        <IconButton
                                            type="add"
                                            onClick={handleAltaProveedor}
                                            tooltip={"Nuevo"}
                                        />
                                    </Authorization>
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
                <TablaPaginadaProveedores
                    idKeyName={ID_KEY_NAME}
                    idKeyLink={ID_KEY_NAME}
                    headers={HEADERS}
                    data={injectActions(dataTable, {
                        edit: canEditProv,
                        show: true
                    })}
                    actionFns={{ handleEdit, handleShow }}
                    onChangeStatus={onChangeStatusProveedor}
                    pageable={pageable}
                    updateData={updateDataTable}
                    valuesConsulta={valuesConsulta}
                />

            </Accordion>
            <DetalleDictamen
                show={showDetalleDictamen}
                onHide={() => setShowDetalleDictamen(false)}
                title="Detalle dictamen técnico"
                approveText={"Cerrar"}
                handleApprove={handleApprove}
                idProveedor={selectedProveedorId}
                dictamenData={selectedDictamen}
            >
                Dictamen
            </DetalleDictamen>
            <DetalleDirectorio
                show={showDetalleDirectorio}
                onHide={() => setShowDetalleDirectorio(false)}
                title="Detalle directorio de contacto"
                approveText={"Cerrar"}
                handleApprove={handleApprove}
                idProveedor={selectedProveedorId}
                directorioData={selectedDirectorio}
            >
                Directorio
            </DetalleDirectorio>
            <DetalleTitulos
                show={showDetalleTitulos}
                onHide={() => setShowDetalleTitulos(false)}
                title="Detalle títulos"
                approveText={"Cerrar"}
                handleApprove={handleApprove}
                idProveedor={selectedProveedorId}
                directorioTitulo={selectedTitulo}
                estatusTitulos={estatusTitulos}
            >
                Titulos
            </DetalleTitulos>
            <BasicModal
                show={showModal}
                onHide={handleCloseModal}
                title={"Mensaje"}
                size={"md"}
                denyText="No"
                handleDeny={handleDeny}
                approveText={"Sí"}
                handleApprove={handleAccept}
            >
                {modalMessage}
            </BasicModal>
        </Container>
    );
};

export default Proveedores;
