import React from "react";
import { NavLink } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { COLORS } from "../constants/colors";
import { ROUTES } from "../constants/routes";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import '../styles/estilos-top-nav.css';

const styles = {
  link: {
    textDecoration: "none",
    color: COLORS.black,
  },
};

const Menu = ({ handleExpandCollapse, handleLogout }) => {

  let tokenExists = sessionStorage.getItem('access_token') ? true : false;
  let userName = sessionStorage.getItem('user_name');

  return (
    <Navbar expand="lg" className="myMenuTop">
      <Container>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">
            {tokenExists && (
              <FontAwesomeIcon icon={faBars} onClick={handleExpandCollapse} className="menu-button" />
            )}
          </Nav>
        </Navbar.Collapse>
        <Navbar.Collapse className="justify-content-end">
          {tokenExists && (
            < >

              <>
                Bienvenido {userName}
              </>

              <Nav.Link
                as={NavLink}
                to={ROUTES.inicio.path}
                style={styles.link}
              >
                Inicio
              </Nav.Link>

              <Nav.Link
                onClick={handleLogout}
                style={styles.link}>
                Cerrar
              </Nav.Link>
            </>
          )}

        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Menu;
