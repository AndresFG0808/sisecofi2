import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Container, Row, Button, Col } from 'react-bootstrap';
import { MainTitle, Loader } from '../../../../components';
import { postData, putData } from '../../../../functions/api';
import Header from './Header';
import Footer from './Footer';
import Content from './Content';
import { TextField } from '../../../../components/formInputs';
import { MODIFICAR_PLANTILLA } from '../../../../constants/messages';
import BasicModal from '../../../../modals/BasicModal';
import { useToast } from '../../../../hooks/useToast';
import showMessage from '../../../../components/Messages';
import { useErrorMessages } from '../../../../hooks/useErrorMessages';
import { errorValidations } from '../../../../functions/utils';
import { logError } from '../../../../components/utils/logError.js';

const Plantillador = () => {
    const { state } = useLocation();
    const { errorToast } = useToast();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [isOpen, setIsOpen] = useState(false);
    const [plantilla, setPlantilla] = useState(JSON.parse(state.plantilla));
    const [errors, setErrors] = useState({});
    const [header, setHeader] = useState("");
    const [contenido, setContenido] = useState("");
    const [footer, setFooter] = useState("");
    const { getMessageExists } = useErrorMessages(MODIFICAR_PLANTILLA);

    useEffect(() => {
        getDataInit();
    }, []);

    const getDataInit = () => {
        postData("/administracion/plantillador/contenido-plantillador", plantilla)
            .then((response) => {
                setLoading(false);
                setPlantilla(response.data);
                setHeader(response.data.header);
                setContenido(response.data.contenido);
                setFooter(response.data.footer);
            })
            .catch((error) => {
                setLoading(false);
            });
    }

    const handleSaveEdition = () => {
        let errorsValidation = validateFields();

        if (Object.keys(errorsValidation).length !== 0) {
            errorToast("Favor de ingresar los datos obligatorios marcados con un asterisco (*).");
            return;
        }

        setLoading(true);

        let dataPlantilla = {
            ...plantilla,
            header,
            contenido,
            footer
        };

        putData('/administracion/plantillador/plantillas/modificar-contenido', dataPlantilla)
            .then((response) => {
                setLoading(false);
                showMessage("Plantilla actualizada correctamente.");
            })
            .catch((error) => {
                setLoading(false);
                logError("error => ", error);
                errorValidations(getMessageExists, error);
            });
    }

    const validateFields = () => {
        let errors = {};
        let { nombre, comentarios } = plantilla;

        if (nombre === "") {
            errors.nombre = "Dato requerido";
        } else {
            delete errors.nombre;
        }

        if (comentarios === "") {
            errors.comentarios = "Dato requerido";
        } else {
            delete errors.comentarios;
        }

        setErrors(errors);
        return errors;
    }

    const handleChangeInput = (e) => {
        let { name, value } = e.target;

        let newData = {
            ...plantilla,
            [name]: value
        };
        setPlantilla(newData);
    }

    const handleGoBackApprove = () => {
        setIsOpen(false);
        navigate("/sistema/plantillas");
    };

    return (

        <Container className='mt-3 px-3'>

            {loading && <Loader />}

            <MainTitle title="Plantillas" />

            <Row className='mb-4'>
                <Col md={8}>
                    <TextField
                        label="Nombre de la plantilla*:"
                        name="nombre"
                        value={plantilla?.nombre}
                        onChange={e => handleChangeInput(e)}
                        autoComplete="off"
                        className={errors.nombre && (errors.nombre && !plantilla?.nombre ? 'is-invalid' : 'is-valid')}
                        helperText={errors.nombre ? errors.nombre : ''}
                    />
                </Col>

                <Col md={8}>
                    <TextField
                        label="Comentarios*:"
                        name="comentarios"
                        value={plantilla?.comentarios}
                        onChange={e => handleChangeInput(e)}
                        autoComplete="off"
                        className={errors.comentarios && (errors.comentarios && !plantilla?.comentarios ? 'is-invalid' : 'is-valid')}
                        helperText={errors.comentarios ? errors.comentarios : ''}
                    />
                </Col>
            </Row>

            <Header header={header} setHeader={setHeader} plantillaData={plantilla} />

            <Content contenido={contenido} setContenido={setContenido} plantillaData={plantilla} />

            <Footer footer={footer} setFooter={setFooter} plantillaData={plantilla} />

            <Row>
                <Col md={12} className="text-end">
                    <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => setIsOpen(true)}
                    >
                        Cancelar
                    </Button>

                    <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleSaveEdition}
                    >
                        Guardar
                    </Button>
                </Col>
            </Row>

            <BasicModal
                show={isOpen}
                size={"md"}
                title="Mensaje"
                approveText={"Sí"}
                denyText={"No"}
                handleApprove={handleGoBackApprove}
                handleDeny={() => setIsOpen(false)}
                onHide={() => setIsOpen(false)}
            >
                {MODIFICAR_PLANTILLA.MSG001}
            </BasicModal>

        </Container >
    );
}

export default Plantillador;