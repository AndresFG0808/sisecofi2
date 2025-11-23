import React from "react";
import { Container } from "react-bootstrap";
import Sidebar from "../../components/Sidebar";
import { ROUTES } from "../../constants/routes";
import ConsumoServicios from './Servicios';
import Estimacion from './Estimacion';
import Dictamen from './Dictamen';

const ConsumoServiciosIndex = () => {
  const pathname = ROUTES.consumoServicios.menuPath;

  const sidebarItems = [
    {
      path: "/consumoServicios",
      element: <ConsumoServicios />,
    },
    {
      path: "/consumoServicios/estimacion",
      element: <Estimacion />,
    },
    {
      path: "/consumoServicios/dictamen",
      element: <Dictamen />,
    },
  ];

  return (
    <Container fluid>
      <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
    </Container>
  );
};

export default ConsumoServiciosIndex;
