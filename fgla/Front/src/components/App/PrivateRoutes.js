import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const PrivateRoutes = () => {

    let tokenExists = sessionStorage.getItem('access_token') ? true : false;

    return tokenExists ? <Outlet /> : <Navigate to="/login" replace={true} />;
};

export default PrivateRoutes;