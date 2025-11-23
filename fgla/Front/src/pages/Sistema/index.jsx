import React from 'react';
import { Container } from 'react-bootstrap';
import { Sidebar } from '../../components';
import AsignarProyecto from './AsignarProyecto';
import Catalogos from './Catalogos';
import PistasAuditoria from './PistasAuditoria';
import MatrizDocumental from './MatrizDocumental';
import { ROUTES } from '../../constants/routes';
import Plantillas from './Plantillas';
import Plantillador from './Plantillas/Plantillador';
import AdministrarUsuarios from './AdministarUsuarios';
import PapeleraReciclaje from './PapeleraReciclaje';

const AdministrarSistema = () => {

    const pathname = ROUTES.sistema.menuPath;

    const sidebarItems = [
        {
            path: "/asignarProyecto",
            element: <AsignarProyecto />,
        },
        {
            path: "/catalogos",
            element: <Catalogos />,
        },
        {
            path: "/pistasAuditoria",
            element: <PistasAuditoria />,
        },
        {
            path: "/matrizDocumental",
            element: <MatrizDocumental />,
        },
        {
            path: "/plantillas",
            element: <Plantillas />
        },
        {
            path: "/plantillas/crear",
            element: <Plantillador />
        },
        {
            path: "/plantillas/editar",
            element: <Plantillador />
        },
        {
            path: "/usuarios",
            element: <AdministrarUsuarios />
        },
        {
            path: "/papelera",
            element: <PapeleraReciclaje />
        }
    ]

    return (
        <Container fluid >
            <Sidebar pathname={pathname} sidebarItems={sidebarItems} />
        </Container>
    );
}

export default AdministrarSistema;