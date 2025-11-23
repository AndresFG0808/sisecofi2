import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import IncioBienvenido from './inicio';
import LoginForm from '../../pages/Login/LoginForm';
import jwtDecode from "jwt-decode";

const Inicio = () => {

  const [sesion, setSesion] = useState(false);

  useEffect(() => {

    console.log("useEffect desde inicio");

    let token = sessionStorage.getItem('access_token');

    try {
      const decoded = jwtDecode(token);
      const currentTime = Date.now() / 1000; // Obtiene la fecha y hora actual en segundos
      if (decoded.exp < currentTime) {
        console.error('El token ha expirado');
      } else {
        console.log('El token es válido');
        setSesion(true);
        sessionStorage.setItem('habilitarbtn', 'ocultame');
        sessionStorage.setItem("habilitarmenu", "mostrar");
      }
    } catch (err) {
      console.error("catch => ", err); // Muestra el error si el token no es válido
    }
  }, [sesion]);

  return (
    <>
      {console.log("Entra al componente Inicio")}
      <IncioBienvenido />
    </>
  );
};

export default Inicio;
