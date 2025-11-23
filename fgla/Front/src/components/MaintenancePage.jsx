import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import logo4t from "../img/logoSat-4t.png";
import logoMant from "../img/mantenimiento.webp"

const MaintenancePage = () => {
    const maintenanceStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        textAlign: 'center',
        backgroundColor: '#fff'
    };

    const imageStyle = {
        width: 'clamp(250px, 40vw, 400px)', // Ancho adaptable: mínimo 250px, preferido 40% del ancho de la ventana, máximo 400px
        height: 'auto', // Mantiene la proporción de la imagen
        maxWidth: '100%' // Asegura que no se desborde en pantallas muy pequeñas
    };

    return (
        <div style={maintenanceStyle}>
            <Container>
                <Row>
                    <Col>
                        <img src={logoMant} style={imageStyle} className="py-2" alt="Ilustración de sitio en mantenimiento" />
                        <h2>Estamos realizando mejoras en el sitio. Estaremos de vuelta pronto.</h2>
                        <h3>Disculpe las molestias.</h3>
                        <img src={logo4t} className="py-2" alt='LogoInicio' />
                    </Col>
                </Row>
            </Container>
        </div>
    );
};

export default MaintenancePage;
