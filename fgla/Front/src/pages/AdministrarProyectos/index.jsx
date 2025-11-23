import React from 'react';
import { Container } from 'react-bootstrap';
import { Sidebar } from '../../components';
import Proyectos from './Proyectos';
import Proyecto from './Proyectos/Proyecto';
import { ROUTES } from '../../constants/routes';

const AdministrarProyectos = () => {

    const pathname = ROUTES.proyectos.menuPath;

    const sidebarItems = [
        {
            path: "/proyectos",
            element: <Proyectos />
        },
        {
            path: "/proyectos/alta",
            element: <Proyecto />
        },
        {
            path: "/proyectos/editar",
            element: <Proyecto />
        },
        {
            path: "/proyectos/verDetalle",
            element: <Proyecto />
        }
    ]

    return (
        <Container fluid >
            <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
        </Container>
    );
}

export default AdministrarProyectos;