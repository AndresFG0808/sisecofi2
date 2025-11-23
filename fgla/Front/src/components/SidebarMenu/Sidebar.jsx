import React, { useEffect, useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { Navigate, useRoutes } from 'react-router-dom';
import ItemCollapse from './ItemCollapse';
import Item from './Item';
import { Tooltip } from '../Tooltip';
import { MENU } from '../../constants/menu'
import '../../styles/estilos-side-nav.css';

const Sidebar = ({ children, expanded, setExpanded }) => {
    const [expandedSidebar, setExpandedSidebar] = useState(expanded);

    useEffect(() => {
        setExpandedSidebar(expanded);
    }, [expanded]);

    const USER_ROLE = sessionStorage.getItem('user_rol');
    const tokenExists = sessionStorage.getItem('access_token') ? true : false;

    return (
        <Container fluid >
            <Row id="list-group-item list-group-item-action lgi-active mb-3">
                {
                    tokenExists &&
                    <div
                        id="sidebar-container"
                        //onMouseEnter={() => setExpanded(true)}
                        onMouseLeave={() => setExpandedSidebar(expanded)}
                        className={(expandedSidebar ? "sidebar-expanded" : "sidebar-collapsed") + " myNavTop d-none d-md-block"}>
                        <ul className="list-group">
                            {
                                MENU.map((item, index) => {

                                    if (item.subs && item.subs.length > 0) {

                                        let hasRoles = 'allowedRoles' in item ? (item.allowedRoles.length > 0) : false;
                                        let access = hasRoles ? (item.allowedRoles.some(item => item === USER_ROLE)) : true;
                                        let hasLabel = 'label' in item ? true : false;
                                        let renderItem = access && hasLabel;

                                        return <ItemCollapse
                                            key={index}
                                            to={item.path}
                                            icon={item.icon}
                                            label={item.label}
                                            render={renderItem}
                                            menuAccess={item.menuAccess}
                                            subs={item.subs}
                                            expanded={expandedSidebar}
                                            setExpandedSidebar={setExpandedSidebar}
                                        />

                                    } else {
                                        let hasRoles = 'allowedRoles' in item ? (item.allowedRoles.length > 0) : false;
                                        let access = hasRoles ? (item.allowedRoles.some(item => item === USER_ROLE)) : true;
                                        let hasLabel = 'label' in item ? true : false;
                                        let renderItem = access && hasLabel;

                                        return <Item
                                            key={index}
                                            to={item.path}
                                            icon={item.icon}
                                            label={item.label}
                                            render={renderItem}
                                            menuAccess={item.menuAccess}
                                            tooltip={true}
                                            expanded={expandedSidebar}
                                        />
                                    }
                                })
                            }
                        </ul>
                    </div>
                }
                <Col className="content-container">
                    <Row>
                        <Col className="p-0">{children}</Col>
                    </Row>
                </Col>
            </Row>
        </Container>
    );
};

export default Sidebar;