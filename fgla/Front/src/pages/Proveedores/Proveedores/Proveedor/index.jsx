import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import { Container, Row, Col, Button } from 'react-bootstrap';
import { MainTitle, Loader, Accordion } from "../../../../components";
import DatosGenerales from './DatosGenerales';
import DirectorioContacto from './DirectorioContacto';
import InformacionTitulos from './InformacionTitulos';
import DictamenTecnico from './DictamenTecnico';
import BasicModal from "../../../../modals/BasicModal";
import { PROVEEDORES as MESSAGES } from '../../../../constants/messages';

const SECCIONES_INICIAL = {
    fichaTecnica: true,
    asociarFases: true,
    gestionDocumental: true,
    infoComites: true,
    planTrabajo: true,
    proveedores: true,
    rcp: true
}

const Proveedor = () => {
    const { state } = useLocation();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [seccionesBloqueadas, setSeccionesBloqueadas] = useState(SECCIONES_INICIAL);
    const [proceso, setProceso] = useState("");
    const [proveedor, setProveedor] = useState(state?.proveedor ? state.proveedor : null);
    const [ver, setVer] = useState(state?.ver || false);
    const [edit, setEdit] = useState(state?.edit || false);
    const [idProveedor, setIdProveedor] = useState(null);
    const [idDirectorio, setIdDirectorio] = useState(null);
    const [idTitulos, setIdTitulos] = useState(null);
    const [showModal, setShowModal] = useState(false);


    useEffect(() => {
        getDataInit();

    }, [idProveedor]);

    const getDataInit = () => {

        setLoading(false);
    };

    const handleGoBack = () => {
        if (ver === true) {
            navigate('/proveedores/proveedores');
            handleCloseModal();
        } else {
            setShowModal(true);
        }
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleDeny = () => {
        handleCloseModal();
    };

    const handleAccept = () => {

        navigate('/proveedores/proveedores');
        handleCloseModal();

    };



    return (
        <Container className='mt-3 px-3'>
            <MainTitle title={"Proveedor"} />
            <Accordion title="Datos generales">
                {loading && <Loader />}
                <DatosGenerales proveedor={proveedor} ver={ver} edit={edit} setProveedor={setProveedor} setIdProveedor={setIdProveedor} />
            </Accordion>
            <Accordion title="Directorio de contacto" show={false} disabled={!idProveedor && !edit && !ver}>
                <DirectorioContacto proveedor={proveedor} setProveedor={setProveedor} ver={ver} edit={edit} idProveedor={idProveedor} setIdDirectorio={setIdDirectorio} />
            </Accordion>
            <Accordion title="Títulos de servicio" show={false} disabled={!idProveedor && !edit && !ver}>
                <InformacionTitulos proveedor={proveedor} ver={ver} edit={edit} idProveedor={idProveedor} setIdTitulos={setIdTitulos} />
            </Accordion>
            <Accordion title="Dictamen técnico" show={false} disabled={!idProveedor && !edit && !ver}>
                <DictamenTecnico proveedor={proveedor} ver={ver} edit={edit} idProveedor={idProveedor} />
            </Accordion>
            <Row>
                <BasicModal
                    show={showModal}
                    onHide={handleCloseModal}
                    size={"md"}
                    title="Mensaje"
                    denyText="No"
                    handleDeny={handleDeny}
                    approveText="Sí"
                    handleApprove={handleAccept}
                >
                    {MESSAGES.MSG002}
                </BasicModal>
                <Col md={12} className="text-end mb-2">
                    <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleGoBack}
                    >
                        Regresar
                    </Button>
                </Col>
            </Row>

        </Container >
    );
}

export default Proveedor;
