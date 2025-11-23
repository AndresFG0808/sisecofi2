import React from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from 'react-router-dom';
import { Tooltip } from '../Tooltip';
import Authorization from '../Authorization';

const Item = ({ to, icon, label, render, menuAccess, tooltip, expanded, setChildrenActive }) => {

    const validateActive = (isActive) => {
        setChildrenActive && isActive && setChildrenActive(isActive);
        return isActive === true ? " lgi-active" : "";
    }

    return (
        <Authorization process={menuAccess}>
            <NavLink
                to={to}
                className={({ isActive }) => `list-group-item list-group-item-action align-items-center${validateActive(isActive)}`}
            >
                <div className="d-flex w-100 align-items-center">
                    {
                        tooltip === true && expanded === false ?
                            <Tooltip placement="top" text={label} >
                                <FontAwesomeIcon icon={icon} className='sidebar-icon' />
                            </Tooltip>
                            :
                            <FontAwesomeIcon icon={icon} className='sidebar-icon' />
                    }
                    <span className={(expanded ? "sidebar-label " : "sidebar-label-collapsed ") + "ms-2"}>{label}</span>
                </div>
            </NavLink>
        </Authorization>
    );
};

export default Item;