import React from "react";
import logo4t from "../img/logoSat-4t.png";
import { Container, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";
import styled from 'styled-components';

const Title = styled.div`
  color: #424242;
  font-weight: 600;
  font-size: 26px;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
`;

const Encabezado = () => {

  return (
    <>
      <Container fluid className="header">
        <Container>
          <Row className="headerSAT">
            <Col md={6}>
              <Link to="/">
                <img src={logo4t} className="logoSAT py-2" />
              </Link>
            </Col>
            <Col md={6}>
              <Title>
                SISECOFI
              </Title>
            </Col>
          </Row>
        </Container>
      </Container>
    </>
  );
};

export default Encabezado;
