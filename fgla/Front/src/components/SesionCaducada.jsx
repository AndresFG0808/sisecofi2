import React, { useState } from 'react';
import { ROUTES } from '../constants/routes';
import client from 'axios';
import SingleBasicModal from '../modals/SingleBasicModal';

const SesionCaducada = () => {
    const sessionExpired = sessionStorage.getItem('sesionCaducada') ? true : false;
    const [showModal, setShowModal] = useState(false);

    client.interceptors.request.use(x => {
        x.headers.Authorization = 'Bearer ' + sessionStorage.getItem('access_token');
        if (sessionExpired) {
            console.log("interceptors.request SESION CADUCADA => ", sessionExpired);
            setShowModal(true);
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    reject('Session expired');
                }, 1000);
            });
        }
        console.log("interceptors.request SESION ACTIVA => ", x);
        return x;
    })

    client.interceptors.response.use(x => {
        console.log("interceptors.response => ", x);
        return x;
    }, (error) => {
        if (error.response !== undefined && error.response.status === 401) {
            sessionStorage.setItem('sesionCaducada', 'true')
            setShowModal(true);
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    reject(error);
                }, 100);
            });
        } else {
            return Promise.reject(error);
        }
    });

    const handleLogout = () => {
        console.log("SESION TERMINADA =================>");
        sessionStorage.setItem('accessTokenSession', null);
        sessionStorage.clear();
        window.location.replace(ROUTES.inicio.path);
        setShowModal(false);
    };

    return (
        <>
            {showModal && <div id='modal-root-msg'>
                <SingleBasicModal
                    size="md"
                    approveText="Aceptar"
                    show={showModal}
                    title="La sesión ha expirado"
                    onHide={handleLogout}
                    backdrop="static"
                    keyboard={false}
                >
                    Será redirigido a la página de ingreso
                </SingleBasicModal>
            </div>}
        </>
    );
}

export default SesionCaducada;