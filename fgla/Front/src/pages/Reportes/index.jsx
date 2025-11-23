import React from "react";
import { Container } from "react-bootstrap";
import { Sidebar } from "../../components";
import { ROUTES } from "../../constants/routes";
import { ConstruirReporte } from "./ConstruirReporte";
import {
  EstadoFinanciero,
  EstimadoPagado,
  FacturasPenalizaciones,
  Resumen,
  SeguimientoDictamen,
  SeguimientoLineaServicio,
} from "./Financiero";
import { TableroControl } from "./TableroControl";
import { ControlDocumental } from "./ControlDocumental";

const Reportes = () => {
  const pathname = ROUTES.reportes.menuPath;

  const sidebarItems = [
    {
      path: "/controlDocumental",
      element: <ControlDocumental />,
    },
    {
      path: "/construir",
      element: <ConstruirReporte />,
    },
    {
      path: "/financiero/resumen",
      element: <Resumen />
    },
    {
      path: "/financiero/seguimientoDictamen",
      element: <SeguimientoDictamen />
    },
    {
      path: "/financiero/facturas-penalizaciones-deducciones-reintegros",
      element: <FacturasPenalizaciones />
    },
    {
      path: "/financiero/estimado-pagado",
      element: <EstimadoPagado/>
    },
    {
      path: "/financiero/seguimientoLineaServicio",
      element: <SeguimientoLineaServicio/>
    },
    {
      path: "/financiero/estadoFinanciero",
      element: <EstadoFinanciero/>
    },
    {
      path: "/tableroDeControl",
      element: <TableroControl />
    },
  ];

  return (
    <Container fluid>
      <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
    </Container>
  );
};

export default Reportes;
