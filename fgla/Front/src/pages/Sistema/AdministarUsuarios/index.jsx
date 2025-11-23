import React from "react";
import { Container } from "react-bootstrap";
import { MainTitle } from "../../../components";
import { AgrearUsuarioSistema } from "./AgregarUsuarioSistema";
import { AdministrarUsuarioProvider } from "./AdministrarUsuarioContext";

const AdministrarUsuarios = () => {
  return (
    <AdministrarUsuarioProvider>
      <Container className="mt-3 px-3">
        <MainTitle title="Usuarios del Sistema" />
        <AgrearUsuarioSistema />
      </Container>
    </AdministrarUsuarioProvider>
  );
};

export default AdministrarUsuarios;
