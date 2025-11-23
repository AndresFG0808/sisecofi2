import React, { useEffect, useRef, useState } from "react";
import MyLoadBarWrapper from "../../components/MyLoadBarWrapper";
import IDPService from "../../functions/api/IDPService";
//import IDPService from "../../functions/api/IDPServiceSAT";
import { Navigate } from "react-router-dom";
import axios from "axios";
import { nanoid } from "nanoid";
import { SHA256, enc } from "crypto-js";
import { ROUTES } from "../../constants/routes";
import { ROLES } from "../../constants/roles";
import jwtDecode from "jwt-decode";
import { useDispatch } from "react-redux";
import { onSetName, onSetRoles } from "../../store";
import showMessage from "../../components/Messages";
import { getData } from "../../functions/api";
import { logError } from '../../components/utils/logError.js';

function Login(props) {
  const loadWrapperRef = useRef();
  const dispatch = useDispatch();
  const [initialState, setInitialState] = useState({
    anchorEl: null,
    onChangePage: props.onChangePage,
    redirect: false,
    onChangeModalView: props.onChangeModalView,
    submitToken: null,
  });
  useEffect(() => {
    componentDidMount();
  }, []);
  console.log(initialState);

  const componentDidMount = () => {
    console.log("---------INICIA LOGIN-----------------");
    if (loadWrapperRef.current) {
      loadWrapperRef?.current?.handleOpenLoadBar(); //spinner
    }

    const accessTokenSession = sessionStorage.getItem("accessTokenSession");
    const accessRefreshToken = sessionStorage.getItem("refreshToken");

    const codeSession = sessionStorage.getItem("codeSession");

    if (accessTokenSession) {
      console.log("FLUJO:3, validar accessTokenSession");
      console.log("Token correcto :::");
      //window.location.replace(ROUTES.inicio.path);
      window.location.replace("/app/pe/sisecofi/index.html#/home");
    } else if (codeSession) {
      console.log("FLUJO:2, cambiar codigo autorizacion por token");
      sessionStorage.removeItem("codeSession");
      const codeVerifier = sessionStorage.getItem("codeVerSession");
      obtenerAccessToken(codeSession, codeVerifier);
    } else {
      console.log("FLUJO:1, obtener codigo autorizacion");
      sessionStorage.removeItem("codeSession");
      const codeVerifier = generarCodigoVerificador();
      const codeChallenger = generarCodigoDesafio(codeVerifier);
      obtenerCodePKCE(codeVerifier, codeChallenger);
    }
  };

  const obtenerCodePKCE = (codeVerifier, codeChallenge) => {
    console.log("codeVerifer:    " + codeVerifier);
    console.log("codeChallenge:  " + codeChallenge);
    console.log("1.3 Guardar codeVerifier en session");
    sessionStorage.setItem("codeVerSession", codeVerifier);
    window.location = IDPService.getAuthCodePKCE(codeChallenge);
  };

  const contador = () => {
    var fecha = new Date();
    var sumarsesion = 5;
    var minutes = fecha.getMinutes();

    fecha.setMinutes(minutes + sumarsesion);
    return fecha;
  };

  const obtenerRefreshToken = () => {
    console.log("Solicitar RefreshToken");
    IDPService.getRefreshToken()
      .then((response) => response.json())
      .then((data) => {
        sessionStorage.removeItem("codeSession");
        sessionStorage.removeItem("codeVerSession");
        sessionStorage.removeItem("accessTokenSession");
        if (data.access_token) {
          sessionStorage.setItem("accessTokenSession", data.access_token);
          sessionStorage.setItem("refreshToken", data.refresh_token);

          sessionStorage.setItem("habilitarbtn", "ocultame");
          sessionStorage.setItem("habilitarmenu", "mostrar");
          sessionStorage.setItem("access_token", data.access_token);

          const contador = contador();
          localStorage.setItem("contador", contador);

          setInitialState((prev) => ({ ...prev, redirect: true }));
          obtenerTokenInfo(data.access_token);
        }
      })
      .catch((ex) => {
        logError("Error:::obtenerRefreshToken->", ex);
        console.log("Se inicia nuevamente el ciclo");
        sessionStorage.removeItem("accessTokenSession");
        sessionStorage.removeItem("codeSession");
        const codeVerifier = generarCodigoVerificador();
        const codeChallenger = generarCodigoDesafio(codeVerifier);
        obtenerCodePKCE(codeVerifier, codeChallenger);
      });
  };

  const pistaLogin = () => {
    getData("/administracion/usuarios/pista-acceso-sistema")
      .then((response) => {
        console.log("response => ", response);
        window.location.replace("/app/pe/sisecofi/index.html#/home");
      })
      .catch((error) => {
        console.log("Ocurrió un error al guardar pista de login");
      })
  }

  const obtenerAccessToken = (code, codeVerifier) => {
    console.log("ejecuta obtenerAccessToken()");
    IDPService.getAccessToken(code, codeVerifier)
      .then((response) => response.json())
      .then((data) => {
        sessionStorage.setItem("token_response", JSON.stringify(data));

        sessionStorage.removeItem("codeSession");
        sessionStorage.removeItem("codeVerSession");
        sessionStorage.removeItem("accessTokenSession");

        if (data.access_token) {
          console.log("Se obtiene token correctamente");

          if (data.refresh_token) {
            sessionStorage.setItem("refreshToken", data.refresh_token);
          }

          validaUsuarioRol(data.access_token);
        }
      })
      .catch((ex) => {
        logError("Error:::obtenerAccessToken->", ex);
      });
  };

  const validaUsuarioRol = (token) => {
    console.log("validaUsuarioRol > token ===> ", token);

    let decoded = jwtDecode(token);
    let userName = decoded.nombreCompleto;
    let roles = decoded.roles;
    let tokenExp = decoded.exp;
    const rls = createArrayRoles(roles);
    // TODO: REDUX
    dispatch(onSetRoles(rls));
    dispatch(onSetName(userName));
    if (roles !== undefined && roles !== "") {
      let rolEncontrado = buscarRol(roles);

      if (rolEncontrado === null) {
        usuarioNoValido();
      } else {
        sessionStorage.setItem("user_name", userName);
        sessionStorage.setItem("user_rol", rolEncontrado.rol);
        sessionStorage.setItem("user_rol_name", rolEncontrado.name);

        sessionStorage.setItem("accessTokenSession", token);
        sessionStorage.setItem("habilitarbtn", "ocultame");
        sessionStorage.setItem("habilitarmenu", "mostrar");
        sessionStorage.setItem("access_token", token);
        setInitialState((prev) => ({ ...prev, redirect: true }));
        //window.location.replace(ROUTES.inicio.path);
        pistaLogin();
      }
    } else {
      usuarioNoValido();
    }
  };
  const createArrayRoles = (stringRoles) => {
    const components = stringRoles.split(",");
    const roles = components
      .map((component) => {
        const match = component.match(/cn=(.*)/);
        return match ? match[1] : null;
      })
      .filter((role) => role !== null);
    return roles;
  };
  const buscarRol = (strRoles) => {
    let rolEncontrado = null;

    Object.entries(ROLES).forEach(([rolKey, rol]) => {
      if (strRoles.includes(rol.rol)) {
        console.log("rol encontrado => ", rol.name);
        rolEncontrado = rol;
      }
    });
    return rolEncontrado;
  };

  const usuarioNoValido = () => {
    console.log("Usuario sin permisos");
    showMessage(
      "Usuario sin permisos, saliendo del sistema...",
      () => { window.location.replace(process.env.REACT_APP_REDIRECT_LOGOUT); });
  };

  const obtenerTokenInfo = async (accessTokenSession) => {
    console.log("ejecuta obtenerTokenInfo()");
    const hardValid = sessionStorage.getItem("hardToken");
    if (hardValid) {
      console.log("prueba DEV()");
      return true;
    } else {
      await axios
        .post("/Demo/Servicios/nidp/oauth/nam/tokeninfo", {
          headers: {
            Authorization:
              "Bearer " + sessionStorage.getItem("accessTokenSession"),
          },
        })
        .then((response) => {
          console.log(response);
        })
        .catch((ex) => {
          logError(ex);
        });
    }
  };

  const base64URLEncode = (str) => {
    return str
      .toString("base64")
      .replace(/\+/g, "-")
      .replace(/\//g, "_")
      .replace(/=/g, "");
  };
  const generarCodigoVerificador = () => {
    console.log("1.1, se genera cadena de 32bytes (codeVerifier)");
    let codeVerifier = nanoid(32);
    return base64URLEncode(codeVerifier);
  };
  const generarCodigoDesafio = (codeVerifier) => {
    console.log("1.2, se encripta cadena con SHA (codeChallenge)");
    let codeChallenge = SHA256(codeVerifier).toString(enc.Hex);
    return base64URLEncode(codeChallenge);
  };

  return (
    <div id="cuerpo_principal" className="Div-cuerpo-principal">
      {initialState.redirect ? (
        <Navigate to={initialState.onChangePage} replace={true} />
      ) : (
        <div hidden={true} />
      )}
      <form id="loginForm">
        <div className="loader-container loader-fullscreen">
          <MyLoadBarWrapper visible={false} ref={loadWrapperRef} />
        </div>
      </form>
    </div>
  );
}

export default Login;
