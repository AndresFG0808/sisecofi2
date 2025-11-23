import React from 'react';
import { Row, Col } from 'react-bootstrap';
import { Navigate, useRoutes } from 'react-router-dom';

const Sidebar = ({ pathname, sidebarItems }) => {
    //TODO: Redux use selector
    const USER_ROLE = sessionStorage.getItem('user_rol');

    const getRoutes = () => {
        let routes = [];
        sidebarItems.forEach(item => {
            let hasRoles = 'allowedRoles' in item ? (item.allowedRoles.length > 0) : false;
            let hasPermission = hasRoles ? (item.allowedRoles.some(item => item === USER_ROLE)) : true;

            let newItem = {
                path: item.path,
                element: hasPermission ? item.element : <Navigate to="/" />
            }

            routes.push(newItem);
        });
        return routes;
    }

    const ROUTES = useRoutes(getRoutes());

    return (
        <Row id="list-group-item list-group-item-action lgi-active mb-3">
            <Col className="content-container">
                <Row>
                    <Col className="p-0">{ROUTES}</Col>
                </Row>
            </Col>
        </Row>
    );
};

export default Sidebar;