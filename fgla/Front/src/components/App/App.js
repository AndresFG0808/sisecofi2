import React, { Suspense, useEffect, useState } from 'react';
import { HashRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { ROUTES } from '../../constants/routes';
import { DYNAMIC_COMPONENTS as COMPONENT } from '../../components/App/dynamicComponents';
import values from 'lodash/values';
import Encabezado from '../Encabezado';
import IdpsService from '../../functions/api/IDPService.jsx';
import Menu from '../Menu';
import '../../styles/index.sass';
import '../../styles/estilos-sat.css';
import '../../styles/estilos-login.css';
import '../../styles/estilos-button.css';
import '../../styles/estilos-select.css';
import '../../styles/general.css';
import '../../styles/estilos-picklist.css';
import Loader from '../Loader';
import PrivateRoutes from './PrivateRoutes';
import StoreProvider from '../../store/StoreProvider';
import Sidebar from '../SidebarMenu/Sidebar';
import { persistor } from "../../store";
import Login from '../../pages/Login/index.jsx';
import { refreshToken } from '../../functions/api';
import Authorization from '../Authorization.jsx';
import jwtDecode from 'jwt-decode';
import showMessage from '../Messages.jsx';
import { logError } from '../utils/logError.js';
import MaintenancePage from '../MaintenancePage.jsx';

const {
  REACT_APP_ENV_CONTEXT
} = process.env

function App() {
  const [expanded, setExpanded] = useState(true);
  const [render, setRender] = useState(false);
  const [loadingEnv, setLoadingEnv] = useState(true);
   /*  - - - - - - - BANDERA MANTENIMIENTO - - - - - - - - */
  const [maintenanceMode, setMaintenanceMode] = useState(false); 
   /*  - - - - - - - BANDERA MANTENIMIENTO- - - - - - - - */
  console.log("===> MODO:", process.env.NODE_ENV);
  console.log("VERSIÓN ===> SISECOFI-INTEGRACION_25-06-2025_1.0 <=== VERSIÓN")
  /*  - - - - - - - OBTENER PARAMETRO CODE - - - - - - - - */

  const getTokenExpiration = () => {
    let time = 0;

    let decoded = jwtDecode(sessionStorage.getItem('access_token'));
    let tokenExp = decoded.exp;

    let actualTime = Date.now() / 1000;
    let difference = (tokenExp - actualTime) / 60;
    time = Number(difference.toFixed(2));

    if (time > 0) {
      console.log("Token válido por =>", time, "minutos");
    } else {
      console.log("Token caducado");
    }

    return time;
  }

  useEffect(() => {

    let token = sessionStorage.getItem('access_token');
    let intervalId = 0;

    const validateToken = () => {
      let tokenExpiration = getTokenExpiration();

      if (tokenExpiration < 1) {
        console.log("token < 1 minuto o caducado ==> EJECUTA refreshToken");
        refreshToken();
      }
    };

    if (token) {
      validateToken();
      intervalId = setInterval(validateToken, 57000);
    }

    return () => clearInterval(intervalId);
  }, []);

  useEffect(() => {
    let expandedSesion = JSON.parse(sessionStorage.getItem('sidebarExpanded'));
    console.log("expandedSesion => ", expandedSesion);
    setExpanded(expandedSesion !== null ? expandedSesion : expanded);
  }, []);

  useEffect(() => {
    console.log("cargar variables de configuración en public ===> ");
    fetch(REACT_APP_ENV_CONTEXT + '/config_env.json')
      .then(response => response.json())
      .then(data => {
        console.log("datos de config_env.json => ", data);
        window.config = { env: data };

        setTimeout(() => {
          validateSesion();
        }, 1000)
      })
      .catch(error => {
        logError('Error al cargar los datos:', error)
        showMessage("Error al cargar las variables de ambiente");
        setRender(prev => false);
        setLoadingEnv(false);
      });
  }, []);

  useEffect(() => {
    console.log("Datos desde config_env.json guardados en window.config => ", window.config);
  }, [render])

  const validateSesion = () => {
    console.log("===> Recuperación de token <===");

    if (sessionStorage.getItem('access_token')) {
      let tokenExpiration = getTokenExpiration();

      if (tokenExpiration < 1) {
        console.log("token < 1 minuto o caducado ==> EJECUTA refreshToken");
        const callbackFnRender = () => {
          setRender(prev => true);
          setLoadingEnv(false);
        }
        refreshToken(callbackFnRender);
      } else {
        setRender(prev => true);
        setLoadingEnv(false);
      }
    } else {
      setRender(prev => true);
      setLoadingEnv(false);
    }

  }

  console.log('===> render APP');
  const urlParams = new URLSearchParams(window.location.search);
  const authorizationCode = urlParams.get('code');
  if (authorizationCode) {
    sessionStorage.setItem('codeSession', authorizationCode);
  }

  //----------------------------------------------------------------------

  const handleLogout = () => {
    //[CERRAR]--> limpiar todas las variables
    console.log("handleLogout");
    persistor.pause();
    persistor.flush().then(() => persistor.purge());
    IdpsService.getRevokeToken();
    sessionStorage.clear();

    window.history.pushState(null, document.title, window.location.href);
    window.addEventListener('popstate', function (event) {
      window.history.pushState(null, document.title, window.location.href);
    });
    window.location.replace(ROUTES.inicio.path);
  };

  const handleExpandCollapse = () => {
    console.log("handleExpandCollapse => ");
    setExpanded(!expanded);
    sessionStorage.setItem('sidebarExpanded', !expanded);
  }

  const getElement = (route, index) => {
    const { active, process, path, component, exact } = route;
    const element = COMPONENT[component];
    let redirect = <Navigate to="/" replace />;

    return <Route
      key={index}
      path={path}
      element={active && process ? <Authorization process={process} redirect={redirect}>{element}</Authorization> : element}
      exact={exact}
    />
  };

  //FTV: Revisar redirect a login cuando no existe token
  // parece hacerse 2 redirect y en el segundo ya se tiene el code,
  // en ese momento no debería hacer redirect a login, agregar validacion de cuando no existe token pero si code esperar a la respuesta del token

  if (!render && loadingEnv) {
    return <Loader />
  }

  if (!render) {
    return <></>
  }

  //hacer una validacionn de mantenimiento 
  //state mantenimiento

  if(maintenanceMode){
    return <MaintenancePage />;
  }



  return (
    <Router>
      <StoreProvider>
        <Encabezado />
        <Menu handleExpandCollapse={handleExpandCollapse} handleLogout={handleLogout} />
        <Sidebar expanded={expanded} setExpanded={setExpanded}>
          <Suspense fallback={<Loader />}>
            <Routes>
              <Route element={<PrivateRoutes />} >
                {values(ROUTES).map((route, index) => (
                  getElement(route, index)
                ))}
              </Route>
              <Route path="/login" element={<Login />} />
              <Route path="*" element={<Navigate to="/home" replace />} />
            </Routes>
          </Suspense>
        </Sidebar>
      </StoreProvider>
    </Router>
  );
}

//hacer una validacionn de mantenimiento 

export default App;
