import React, { useState, useEffect } from 'react';
import Container from 'react-bootstrap/Container';
import Alert from 'react-bootstrap/Alert';
import useScript from '../hooks/useScript';
import { config } from '../constants/config';
import { MESSAGES } from '../constants/messages';
import { getCatalogDescription, stringFormatDate } from '../functions/utils';
import CenteringContainer from '../components/CenteringContainer';
import Loader from '../components/Loader';

const wingetUrl =
  'https://wwwuat.siat.sat.gob.mx/PTSC/fwidget/restServices/m2.firmado.sat.general.js';
const jQuery =
  'https://aplicaciones.sat.gob.mx/PTSC/fwidget/resources/js/fiel/jquery/jquery-1.6.4.min.js';

const EFirma = ({ callbackSuccess, informe, catalogos, title }) => {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  useScript(jQuery);
  useScript(wingetUrl);
  useEffect(() => {
    const timer = setInterval(function () {
      console.log('Cargando Windget...');
      const btnEnviarForm = document.getElementById('btnEnviarForm');
      const tituloGobMx = document.getElementsByClassName('tituloGobMx');
      if (btnEnviarForm && tituloGobMx) {
        btnEnviarForm.onclick = btnEnviarFIELOnClick;
        if (title) {
          tituloGobMx[0].innerHTML = title;
          setLoading(false);
        } else {
          setLoading(false);
        }
        clearInterval(timer);
      }
    }, 200);
  }, []);

  const obtenerCadena = (informe, catalogos) => {
    const { idInforme, rfcContri, ejercicio, idTipoInforme, idInformePresenta } = informe;
    const { informeAPresentar, tipoInforme } = catalogos;
    const tipoDeInforme = getCatalogDescription(
      idInformePresenta,
      informeAPresentar,
      'idValor',
      'dvalor',
    );
    const tipo = getCatalogDescription(idTipoInforme, tipoInforme, 'idValor', 'dvalor');
    const fPresentacion = stringFormatDate('-');
    console.log('obtenerCadena -> fPresentacion', fPresentacion);
    return `| TIPO DE INFORME: ${tipoDeInforme} |  EJERCICIO: ${ejercicio} | TIPO INFORME: ${tipo} | FECHA DE PRESENTACION: ${fPresentacion} RFC: ${rfcContri} | ID: ${idInforme} |`;
  };

  function btnEnviarFIELOnClick() {
    console.log('Enviando...');
    try {
      const cadenaOriginal = informe ? obtenerCadena(informe, catalogos) : '';
      console.log('btnEnviarFIELOnClick -> cadenaOriginal', cadenaOriginal);
      const validarRFCSession = informe
        ? informe.rfcContri
        : document.getElementById('txtRFC').value;
      window.generaFirmaIndividual([{ cadenaOriginal }], { validarRFCSession }, function (
        error,
        resultado,
      ) {
        if (error && error !== 0) {
          setError(window.catalogoErrores[error].msg_vista);
        } else {
          callbackSuccess({
            setError,
            results: resultado[0],
            rfc: validarRFCSession,
          });
        }
      });
    } catch (error) {
      setError(MESSAGES.error);
    }
  }

  return (
    <Container>
      {error && <Alert variant="danger">{error}</Alert>}
      {loading ? (
        <div style={{ height: '100vh' }}>
          <CenteringContainer>
            <Loader />
          </CenteringContainer>
        </div>
      ) : (
        <br />
      )}
      <div id="firmado-widget-container" />
    </Container>
  );
};

export default EFirma;
