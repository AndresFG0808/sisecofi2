import React from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import SISECOFI from '../img/SISECOFI_V3.png';
//import logoSAT from '../img/logoSAT.png';

const Inicio = () => (
  <Container>
    <Row className='container-full-height p-4'>
      <Col md={12} className="logo-sat">
        {/* <h2 style={{ color: 'red' }}>El sitio estara en mantenimiento el dia de hoy 18 de Julio a partir de un horario de 3:00pm a 4:00pm</h2> */}
        <img src={SISECOFI} className="img-fluid-sat" alt="SAT" title="SISECOFI" />
        <h1>BIENVENIDO</h1>
      </Col>
    </Row>
  </Container>
);

export default Inicio;
