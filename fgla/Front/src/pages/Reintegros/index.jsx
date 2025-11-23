import React from 'react';
import { Container } from 'react-bootstrap';
import { Sidebar } from '../../components';
import { ROUTES } from '../../constants/routes';

import ReintegrosSecciones from './reintegros';
const Reintegros = () => {

    const pathname = ROUTES.reintegros.menuPath;

    const sidebarItems = [
        {
            path: "/",
            element: <ReintegrosSecciones/>
        }
    ]

    return (
        <Container fluid >
            <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
        </Container>
    );
}

export default Reintegros;