import React from 'react';
import { Container } from 'react-bootstrap';
import { faTableList } from "@fortawesome/free-solid-svg-icons";
import Sidebar from '../../components/Sidebar';
import AdminContratos from './AdminContratos';
import { ROUTES } from '../../constants/routes';
import { Contratos } from './AdminContratos/AltaContratos';
import Convenio from './AdminContratos/AltaContratos/ConveniosModificatorios/Convenio';

const AdministrarContratos = () => {

    const pathname = ROUTES.contratos.menuPath;

    const sidebarItems = [
        {
            path: "/contratos",
            element: <Contratos />,
        },
        {
            path: "/contratos/alta",
            element: <AdminContratos />,
        },
        {
            path: "/contratos/editar/:idContrato",
            element: <AdminContratos />,
        },
        {
            path: "/contratos/verDetalle/:idContrato",
            element: <AdminContratos />,
        },
        {
            path: "/contratos/editar/:idContrato/convenio/alta",
            element: <Convenio />
        },
        {
            path: "/contratos/editar/:idContrato/convenio/editar/:idConvenio",
            element: <Convenio />
        },
        {
            path: "/contratos/editar/:idContrato/convenio/verDetalle/:idConvenio",
            element: <Convenio />
        }
    ]

    return (
        <Container fluid >
            <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
        </Container>
    );
}

export default AdministrarContratos;