import React from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from 'react-router-dom';

const SidebarItem = ({ to, icon, label, render }) => {
    return (
        render &&
        <NavLink
            to={to}
            className={({isActive}) => `list-group-item list-group-item-action align-items-center${isActive ? " lgi-active" : ""}` }
        >
            <div className="d-flex w-100 justify-content-start align-items-center">
                <FontAwesomeIcon icon={icon} className='sidebar-icon' />
                <span className="ms-2">{label}</span>
            </div>
        </NavLink>
    );
};

export default SidebarItem;