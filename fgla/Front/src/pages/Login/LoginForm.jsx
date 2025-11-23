import React, { useState } from 'react';
import { ROUTES } from '../../constants/routes';
import { Formik } from "formik";
import * as yup from 'yup';
import { Row, Col, Card, Form, InputGroup, Container } from 'react-bootstrap';
import { MainTitle } from '../../components';
import LoadingButton from '../../components/buttons/LoadingButton';
import logoSAT from '../../img/logoSAT.png';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faKey } from "@fortawesome/free-solid-svg-icons";
import { faEye, faEyeSlash } from "@fortawesome/free-regular-svg-icons";
import { getToken } from '../../functions/api';
import { useToast } from "../../hooks/useToast";
import { LOGIN as MESSAGES } from "../../constants/messages";
import jwtDecode from "jwt-decode";
import { logError } from '../../components/utils/logError.js';

const token = '';

const VALORES_INICIALES = {
    username: "",
    password: ""
};

const esquema = yup.object({
    username: yup.string().required("Dato requerido"),
    password: yup.string().required("Dato requerido")
});

const LoginForm = () => {
    const { errorToast } = useToast();
    const [loadingSesion, setLoadingSesion] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const handleRedirect = () => {
        sessionStorage.setItem('habilitarbtn', 'ocultame');
        sessionStorage.setItem("habilitarmenu", "mostrar");
        window.location.replace(ROUTES.inicio.path);
    };

    const handleShowPassword = () => {
        setShowPassword(!showPassword);
    };

    // Usar cuando no hay servicios disponibles
    const submitChanges2 = (values) => {
        console.error("submitChanges2 => Sesion de prueba");
        alert("Si estas viendo este mensaje en ambiente DEV o PROD favor de reportarlo");

        try {
            const decoded = jwtDecode(token);
            const currentTime = Date.now() / 1000; // Obtiene la fecha y hora actual en segundos
            if (decoded.exp < currentTime) {
                console.error('El token ha expirado');
            } else {
                console.log('El token es válido');
                sessionStorage.setItem('access_token', token);
                validarUsuario(token);
            }
        } catch (err) {
            console.error("catch => ", err); // Muestra el error si el token no es válido
        }
    }

    const submitChanges = (values) => {
        setLoadingSesion(true);
        let { username, password } = values;

        let data = "grant_type=password&username=" +
            encodeURIComponent(username) +
            "&password=" + encodeURIComponent(password);

        getToken('/oauth/token', data)
            .then((response) => {
                sessionStorage.setItem('access_token', response.data.access_token);
                validarUsuario(response.data.access_token);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoadingSesion(false);
                if (error.code == "ERR_NETWORK") {
                    errorToast(MESSAGES.error);
                } else {
                    errorToast(MESSAGES.errorLogin);
                }
            });
    }

    const validarUsuario = (access_token) => {

        if (access_token) {
            let decoded = jwtDecode(access_token);
            let userName = decoded.user_name;
            let userRole = decoded.authorities;

            if (userRole.length === 0 || userRole === "") {
                console.log("no tiene roles");
                alert("No tiene roles");
            } else {
                sessionStorage.setItem("user_rol", userRole);
                sessionStorage.setItem("user_name", userName);
                handleRedirect();
            }
        }
    }

    return (
        <Container>
            <Row className='login-container p-4'>
                <Card className='shadow'>
                    <Card.Body>
                        <Row className="h-100 logo-sat-center">
                            <Col lg={7} md={6} sm={8} xs={8} className="logo-sat">
                                <img src={logoSAT} className="img-fluid-sat" alt="SAT" title="SAT" />
                            </Col>

                            <Col lg={5} md={6} sm={12} xs={12} className="container-login my-auto">
                                <Formik
                                    initialValues={{ ...VALORES_INICIALES }}
                                    validationSchema={esquema}
                                    onSubmit={(e, { resetForm }) => submitChanges(e, resetForm)}
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
                                        <Form onSubmit={handleSubmit}>

                                            <Card className='login-card px-xxl-4'>
                                                <Card.Body>
                                                    <Row className="justify-content-md-center login-style">

                                                        <Col lg={11} md={12}>
                                                            <MainTitle title="Iniciar sesión" />
                                                        </Col>

                                                        <Col lg={11} md={12} className="mb-3">
                                                            <Form.Group>
                                                                <Form.Label>Usuario</Form.Label>
                                                                <InputGroup className={`inputGroup-login ${touched.username && (errors.username ? "is-invalid" : "")}`}>
                                                                    <InputGroup.Text className="input-icon">
                                                                        <FontAwesomeIcon icon={faUser} />
                                                                    </InputGroup.Text>
                                                                    <Form.Control
                                                                        name="username"
                                                                        value={values.username}
                                                                        onChange={handleChange}
                                                                        onBlur={handleBlur}
                                                                        placeholder="usuario"
                                                                        className={`input-user ${touched.username &&
                                                                            (errors.username
                                                                                ? "is-invalid" : "")
                                                                            }`}
                                                                    ></Form.Control>
                                                                </InputGroup>
                                                                <Form.Control.Feedback type="invalid">
                                                                    {errors.username}
                                                                </Form.Control.Feedback>
                                                            </Form.Group>
                                                        </Col>

                                                        <Col lg={11} md={12} className="mb-3">
                                                            <Form.Group>
                                                                <Form.Label>Contraseña</Form.Label>
                                                                <InputGroup className={`inputGroup-login ${touched.password && (errors.password ? "is-invalid" : "")}`}>
                                                                    <InputGroup.Text className="input-icon">
                                                                        <FontAwesomeIcon icon={faKey} />
                                                                    </InputGroup.Text>
                                                                    <Form.Control
                                                                        name="password"
                                                                        value={values.password}
                                                                        onChange={handleChange}
                                                                        onBlur={handleBlur}
                                                                        placeholder="contraseña"
                                                                        type={showPassword ? 'text' : 'password'}
                                                                        className={`input-password ${touched.password &&
                                                                            (errors.password
                                                                                ? "is-invalid" : "")
                                                                            }`}
                                                                    >
                                                                    </Form.Control>
                                                                    <InputGroup.Text>
                                                                        <FontAwesomeIcon onClick={handleShowPassword} icon={showPassword ? faEyeSlash : faEye} />
                                                                    </InputGroup.Text>
                                                                </InputGroup>
                                                                <Form.Control.Feedback type="invalid">
                                                                    {errors.password}
                                                                </Form.Control.Feedback>
                                                            </Form.Group>
                                                        </Col>

                                                        <Col lg={11} md={12} >
                                                            <LoadingButton
                                                                variant="green"
                                                                className="btn-sm waves-effect waves-light btn-login mt-4 mb-2"
                                                                type="submit"
                                                                loading={loadingSesion}
                                                            >
                                                                Ingresar
                                                            </LoadingButton>
                                                        </Col>
                                                    </Row>

                                                </Card.Body>

                                            </Card>
                                        </Form>
                                    )}
                                </Formik>

                            </Col>
                        </Row>
                    </Card.Body>
                </Card>
            </Row>
        </Container>
    );
};

export default LoginForm;
