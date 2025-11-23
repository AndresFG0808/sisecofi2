import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link, useLocation } from 'react-router-dom';
import { Collapse } from 'react-bootstrap';
import Item from './Item';
import { faChevronUp, faChevronDown, faCaretRight } from "@fortawesome/free-solid-svg-icons";
import { Tooltip } from '../Tooltip';
import Authorization from '../Authorization';

const ItemCollapse = ({ to, icon, label, render, menuAccess, subs, margin, tooltip, expanded, setExpandedSidebar, setActive }) => {

    const { pathname } = useLocation();
    const [openList, setOpenList] = useState(pathname.includes(to));
    const [childrenActive, setChildrenActive] = useState(false);
    const USER_ROLE = sessionStorage.getItem('user_rol');
    const tokenExists = sessionStorage.getItem('access_token') ? true : false;

    useEffect(() => {
        setOpenList(pathname.includes(to));
        setChildrenActive(childrenActive && pathname.includes(to));
    }, [pathname, to]);

    useEffect(() => {
        setOpenList(expanded && (pathname.includes(to) || openList));
    }, [expanded]);

    const handleClickItem = (e) => {
        e.preventDefault();
        e.stopPropagation();
        console.log("handleClickItem => ");
        setExpandedSidebar && setExpandedSidebar(true);
        setOpenList(!openList);
    }

    return (
        <Authorization process={menuAccess}>
            <Link
                className={`list-group-item list-group-item-action align-items-center${expanded === false && childrenActive === true ? " lgi-active" : ""}`}
                onClick={handleClickItem} aria-expanded={openList} aria-controls="collapse-menu" >
                <div className="d-flex w-100 align-items-center">
                    {
                        tooltip === false && expanded === true ? <FontAwesomeIcon icon={icon} className='sidebar-icon' /> :
                            <Tooltip placement="top" text={label} >
                                <FontAwesomeIcon icon={icon} className='sidebar-icon' />
                            </Tooltip>
                    }
                    <span className={(expanded ? "sidebar-label " : "sidebar-label-collapsed ") + "ms-2"}>{label}</span>
                </div>
                {expanded && <FontAwesomeIcon icon={openList ? faChevronUp : faChevronDown} />}
            </Link>
            <Collapse in={openList}>
                <ul /* style={margin && { backgroundColor: 'rgba(0, 0, 0, 0.2)' }} */ id="collapse-menu" className={"list-group list-subs" + (margin && " ms-2") /* + (margin && " mx-2") */}>
                    {
                        subs.map((opcion, index) => {

                            if (opcion.subs && opcion.subs.length > 0) {
                                let hasRoles = 'allowedRoles' in opcion ? (opcion.allowedRoles.length > 0) : false;
                                let access = hasRoles ? (opcion.allowedRoles.some(item => item === USER_ROLE)) : true;
                                let hasLabel = 'label' in opcion ? true : false;
                                let renderItem = access && hasLabel;

                                return <ItemCollapse
                                    key={index}
                                    to={opcion.path}
                                    icon={faCaretRight}
                                    label={opcion.label}
                                    menuAccess={opcion.menuAccess}
                                    render={renderItem}
                                    subs={opcion.subs}
                                    margin={true}
                                    tooltip={false}
                                    expanded={expanded}
                                    setActive={setChildrenActive}
                                />

                            } else {
                                let hasRoles = 'allowedRoles' in opcion ? (opcion.allowedRoles.length > 0) : false;
                                let access = hasRoles ? (opcion.allowedRoles.some(item => item === USER_ROLE)) : true;
                                let hasLabel = 'label' in opcion ? true : false;
                                let renderItem = access && hasLabel;

                                return <Item
                                    key={index}
                                    to={opcion.path}
                                    icon={faCaretRight}
                                    label={opcion.label}
                                    render={renderItem}
                                    menuAccess={opcion.menuAccess}
                                    expanded={expanded}
                                    setChildrenActive={setActive ? setActive : setChildrenActive}
                                />
                            }
                        })
                    }
                </ul>
            </Collapse>
        </Authorization>
    )
};

export default ItemCollapse;