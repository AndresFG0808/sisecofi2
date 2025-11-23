import React from 'react';
import { Container } from 'react-bootstrap';
import { faTableList } from "@fortawesome/free-solid-svg-icons";
import { Sidebar } from '../../components';
import Proveedores from './Proveedores';
import Proveedor from './Proveedores/Proveedor';
import { ROUTES } from '../../constants/routes';

const ProveedoresMain = () => {

    const pathname = ROUTES.proveedores.menuPath;

    const sidebarItems = [
        {
            path: "/proveedores",
            element: <Proveedores />,
        },
        {
            path: "/proveedores/alta",
            element: <Proveedor />
        },
        {
            path: "/proveedores/editar",
            element: <Proveedor />
        },
        {
            path: "/proveedores/detalle",
            element: <Proveedor />
        }
    ]

    return (
        <Container fluid >
            <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
        </Container>
    );
}

export default ProveedoresMain;