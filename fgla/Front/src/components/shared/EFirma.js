import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Alert from "react-bootstrap/Alert";
import useScript from "../../hooks/useScript";
import Loading from "./Loading/Loading";

const {
  REACT_APP_TAF_ENDPOINT_WINGET,
  REACT_APP_TAF_ENDPOINT_JQUERY
} = process.env;

const wingetUrl = REACT_APP_TAF_ENDPOINT_WINGET;
const jQuery = REACT_APP_TAF_ENDPOINT_JQUERY;

const EFirma = ({ title, onClose, redireccionar }) => {
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [content, setContent] = useState(false);

  useScript(wingetUrl);
  useScript(jQuery);
  
  useEffect(() => {
    const timer = setInterval(function () {
      console.log("Cargando Windget...");
      const btnEnviarForm = document.getElementById("btnEnviarForm");
      const tituloGobMx = document.getElementsByClassName("tituloGobMx");
      if (btnEnviarForm && tituloGobMx) {
        btnEnviarForm.onclick = btnEnviarFIELOnClick;
        tituloGobMx[0].style.textAlign = 'left';
        if (title) {
          tituloGobMx[0].innerHTML = title;
          setContent(true);
          setLoading(false);
        } else {
          tituloGobMx[0].innerHTML = 'Firmar con e.firma';
          var buttonCerrar = document.createElement("input");
          buttonCerrar.setAttribute('type', "button");
          buttonCerrar.setAttribute('value', "Cerrar");
          buttonCerrar.setAttribute('class', btnEnviarForm.getAttribute('class'))
          buttonCerrar.innerHTML = "Cerrar";
          var newButton = btnEnviarForm.parentElement;
          newButton.appendChild(buttonCerrar);
          //newButton.insertBefore(buttonCerrar, btnEnviarForm);
          buttonCerrar.onclick = onClose;
          setContent(true);
          setLoading(false);
        }
        clearInterval(timer);
      }
    }, 200);
  }, []);

  function btnEnviarFIELOnClick() {
    const dataUser =     {usr_rfc:sessionStorage.getItem("siisef_user_rfc")};  // JSON.parse(sessionStorage.getItem("auth"));
    const rfcFiel = document.getElementById("txtRFC").value;
    const rfcUser = dataUser.usr_rfc;
    if (rfcUser === rfcFiel) {
      setLoading(true);
      try {
        const cadenaOriginal = null;
        const validarRFCSession = document.getElementById("txtRFC").value;
        const datos = [{ cadenaOriginal }];
        const datoRFC = { validarRFCSession };
        const callback = function (msgError, resultado) {
          if (msgError && msgError !== 0) {
            setError(window.catalogoErrores[msgError].msg_vista);
            setLoading(false);
          } else {
            setLoading(false);
            const { firmaDigital, cadenaOriginalGenerada, /* cadenaOriginal */ } = resultado[0];
            sessionStorage.setItem("firmaDigital", firmaDigital);
            sessionStorage.setItem("cadenaOriginalGenerada", cadenaOriginalGenerada);
            redireccionar();
          }
        };
        window.generaFirmaIndividual(datos, datoRFC, callback);
        // window.generaFirmaMasivo(datos, datoRFC, callback);
      } catch (err) {
        setError("Vaya, algo salió mal inesperadamente!");
      }
    } else {
      if (rfcFiel !== '') {
        setError("La Fiel utilizada no corresponde al RFC del usuario en sesión");
      }
    }
  }

  return (
    <>

      <Loading active={loading}>
        {
          content ? ("") : (<div style={{ height: "75vh", width: "100vw" }} />)
        }
        <Container>
          {error && <Alert variant="danger">{error}</Alert>}
          <div id="firmado-widget-container" />
        </Container>
      </Loading>
    </>
  );
};

export default EFirma;
