import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Container, Row, Col, Button } from "react-bootstrap";
import MainTitle from "../../../components/MainTitle";
import Loader from "../../../components/Loader";
import Accordion from "../../../components/Accordion";
import { useDispatch } from "react-redux";
import { onEditProyecto } from "../../../store/pryectos";
import DatosGenerales from "./DatosGenerales";
import RegistroServiciosDictaminados from "./RegistroServiciosDictaminados";
import Penas from "./Penas";
import SoporteDocumentalDictamen from "./SoporteDocumentalDictamen";
// import Proforma from "./Proforma";
import Proforma from "./Proforma_2/index";
import Facturas from "./Facturas";
import NotaCredito from "./NotaCredito";
import { DictamenContext } from "./context";
import SolicitudPago from "./SolicitudPago";
import SolicitudFactura from "./SolicitudFactura";
import SeccionRegresar from "./SeccionRegresar";
import { isEmpty, isNil, } from "lodash";
import GestionDocumental  from "./GestionDocumental";
const SECCIONES_INICIAL = {
  RegistroServiciosDictaminados: false,
  PenasContractuales: false,
  PenasConvencionales: false,
  Deducciones: false,
  SoporteDocumentalDictamen: false,
  Proforma: false,
  Factura: false,
  Facturas: false,
  NotaCredito: false,
  SolicitudPago: false,
  GestionDocumental: false,
};

const SHOW_SECCIONES = {
  RegistroServiciosDictaminados: false,
  PenasContractuales: false,
  PenasConvencionales: false,
  Deducciones: false,
  SoporteDocumentalDictamen: false,
  Proforma: false,
  Factura: false,
  Facturas: false,
  NotaCredito: false,
  SolicitudPago: false,
  GestionDocumental: false,
};

const Proyecto = () => {
  const navigate = useNavigate();

  const location = useLocation();
  const state = location.state;
  const dictamenState = state?.dictamenState || {};
  const estatus = dictamenState?.estatus;

  const [dictaminadoStatusEditable, setDictaminadoStatusEditable] = useState(false);
  const [loading, setLoading] = useState(true);
  const [reload, setReload] = useState(false);
  const [submitForm,setSubmitForm]=useState(false);
  const [serviciosDictaminadosSeleccionados, setServiciosDictaminadosSeleccionados] = useState(null);
  const [onReloadDictamenInfo, setOnReloadDictamenInfo] = useState(null);
  const [onReloadPenasInfo, setOnReloadPenasInfo] = useState(null);
  const [onReloadProformaInfo, setOnReloadProformaInfo] = useState(null);
  const [ultimaModificacion, setUltimaModificacion] = useState("");
  const [fechasContrato, setFechasContrato] = useState(null);
  const [generalInformation, setGeneralInformation] = useState({});
  const [onClearPenasContractuales, setOnClearPenasContractuales] = useState(null);
  const [onClearPenasConvencionales, setOnClearPenasConvencionales] = useState(null);
  const [onClearDeducciones, setOnClearDeducciones] = useState(null);
  const [dictamenInfo, setDictamenInfo] = useState(null);
  const [seccionesInactivas, setSeccionesInactivas] = useState({
    ...SECCIONES_INICIAL,
  });
  const [showSecciones, setShowSecciones] = useState({
    ...SHOW_SECCIONES,
  });
  const [isEditable, setIsEditable] = useState(true);
  const dispatch = useDispatch();

  useEffect(() => {
    getDataInit();
  }, []);

  const getDataInit = () => {
    setLoading(false);
  };

  const handleManagementSectionsByStatus = () => {
    const secciones = { ...seccionesInactivas };
    if (isNil(estatus)) {
      secciones.RegistroServiciosDictaminados = true;
      secciones.PenasContractuales = true;
      secciones.PenasConvencionales = true;
      secciones.Deducciones = true;
      secciones.SoporteDocumentalDictamen = true;
      secciones.GestionDocumental = true;
      secciones.Factura = true;
      secciones.Facturas = true;
      secciones.NotaCredito = true;
      secciones.Proforma = true;
      secciones.SolicitudPago = true;
      setSeccionesInactivas({ ...secciones });
    } else if (estatus === 'Inicial') {
      secciones.RegistroServiciosDictaminados = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Deducciones = false;
      secciones.SoporteDocumentalDictamen = false;
      secciones.GestionDocumental = false;
      secciones.Factura = true;
      secciones.Facturas = true;
      secciones.NotaCredito = true;
      secciones.Proforma = true;
      secciones.SolicitudPago = true;
      setSeccionesInactivas({ ...secciones });
    } else if (estatus !== '' && estatus !== null && estatus === 'Dictaminado') {
        secciones.RegistroServiciosDictaminados = false;
        secciones.PenasContractuales = false;
        secciones.PenasConvencionales = false;
        secciones.Deducciones = false;
        secciones.SoporteDocumentalDictamen = false;
        secciones.GestionDocumental = false;
        secciones.Factura = false;
        secciones.Facturas = false;
        secciones.NotaCredito = false;
        secciones.Proforma = false;
        secciones.SolicitudPago = false;
        setSeccionesInactivas({ ...secciones });
    } else if (estatus !== '' && estatus !== null && estatus === 'Proforma') {
      secciones.RegistroServiciosDictaminados = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Deducciones = false;
      secciones.SoporteDocumentalDictamen = false;
      secciones.GestionDocumental = false;
      secciones.Factura = false;
      secciones.Facturas = false;
      secciones.NotaCredito = false;
      secciones.Proforma = false;
      secciones.SolicitudPago = false;
      setSeccionesInactivas({ ...secciones });
      const seccionesVisibles = {...showSecciones};
      secciones.Proforma = true;
      setShowSecciones({ ...seccionesVisibles });
    } else if (estatus !== '' && estatus !== null && estatus === 'Cancelado') {
      secciones.RegistroServiciosDictaminados = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Deducciones = false;
      secciones.SoporteDocumentalDictamen = false;
      secciones.GestionDocumental = false;
      secciones.Factura = false;
      secciones.Facturas = false;
      secciones.NotaCredito = false;
      secciones.Proforma = false;
      secciones.SolicitudPago = false;
      setSeccionesInactivas({ ...secciones });
    } else if (estatus !== '' && estatus !== null && estatus === 'Pagado') {
      secciones.RegistroServiciosDictaminados = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Deducciones = false;
      secciones.SoporteDocumentalDictamen = false;
      secciones.GestionDocumental = false;
      secciones.Factura = false;
      secciones.Facturas = false;
      secciones.NotaCredito = false;
      secciones.Proforma = false;
      secciones.SolicitudPago = false;
      setSeccionesInactivas({ ...secciones });
    } else if (estatus !== '' && estatus !== null && estatus === 'Solicitud de pago') {
      secciones.RegistroServiciosDictaminados = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Deducciones = false;
      secciones.SoporteDocumentalDictamen = false;
      secciones.GestionDocumental = false;
      secciones.Factura = false;
      secciones.Facturas = false;
      secciones.NotaCredito = false;
      secciones.Proforma = false;
      secciones.SolicitudPago = false;
      setSeccionesInactivas({ ...secciones });
    } else if (estatus !== '' && estatus !== null && estatus === 'Facturado') {
      secciones.RegistroServiciosDictaminados = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Deducciones = false;
      secciones.SoporteDocumentalDictamen = false;
      secciones.GestionDocumental = false;
      secciones.Proforma = false;
      secciones.Factura = false;
      secciones.Facturas = false;
      secciones.NotaCredito = false;
      secciones.SolicitudPago = false;
      setSeccionesInactivas({ ...secciones });
    }
  };  

  useEffect(()=>{
    handleManagementSectionsByStatus();
  }, [estatus]);

  if (loading) {
    return <Loader />;
  }

  const onClickElement = (open, keySection) => {
    const secciones = { ...showSecciones };
    secciones[keySection] = open;
    setShowSecciones({ ...secciones });
  };
  console.log("fechasContrato dictamen ", fechasContrato)
  return (
    <Container className="mt-3 px-3">
      <DictamenContext.Provider
        value={{
          isEditable,
          setLoading,
          setSeccionesInactivas,
          SECCIONES_INICIAL,
          setUltimaModificacion,
          setFechasContrato,
          showSecciones,
          setShowSecciones,
          onReloadDictamenInfo,
          setOnReloadDictamenInfo,
          serviciosDictaminadosSeleccionados,
          setServiciosDictaminadosSeleccionados,
          onClearPenasContractuales,
          setOnClearPenasContractuales,
          onClearPenasConvencionales,
          setOnClearPenasConvencionales,
          onClearDeducciones,
          setOnClearDeducciones,
          setIsEditable,
          onReloadPenasInfo,
          setOnReloadPenasInfo,
          dictaminadoStatusEditable,
          setDictaminadoStatusEditable,
          setOnReloadProformaInfo,
          onReloadProformaInfo,
          setDictamenInfo,
          dictamenInfo,
        }}
      >
        <MainTitle title="Consumo de Servicios - Dictamen" />
        <div className="text-end">
          {/*isEmpty(fechasContrato) === false && (
            <strong style={{ color: "#ee2a2a" }}>
              {fechasContrato} &nbsp;&nbsp;&nbsp;
            </strong>
          )*/}
          {isEmpty(ultimaModificacion) === false && (
            <>
              <strong>Última modificación: </strong>
              {ultimaModificacion}
            </>
          )}
        </div>

        <Accordion title="Datos generales" show={true}>
          <DatosGenerales />
        </Accordion>

        <Accordion
          title="Registro de servicios dictaminados"
          show={showSecciones.RegistroServiciosDictaminados}
          disabled={seccionesInactivas.RegistroServiciosDictaminados}
          onClickElement={(open) =>
            onClickElement(open, "RegistroServiciosDictaminados")
          }
        >
          <RegistroServiciosDictaminados />
        </Accordion>
        <Accordion
          title="Penas contractuales"
          show={showSecciones.PenasContractuales}
          disabled={seccionesInactivas.PenasContractuales}
          onClickElement={(open) =>
            onClickElement(open, "PenasContractuales")
          }
        >
          <Penas type={1} />
        </Accordion>
        <Accordion
          title="Penas convencionales"
          show={showSecciones.PenasContractuales}
          disabled={seccionesInactivas.PenasConvencionales}
          onClickElement={(open) =>
            onClickElement(open, "PenasContractuales")
          }
        >
          <Penas type={2} />
        </Accordion>
        <Accordion
          title="Deducciones"
          show={showSecciones.Deducciones}
          disabled={seccionesInactivas.Deducciones}
          onClickElement={(open) =>
            onClickElement(open, "Deducciones")
          }
        >
          <Penas type={3} />
        </Accordion>
        <Accordion
          title="Soporte documental de dictamen"
          show={showSecciones.SoporteDocumentalDictamen}
          disabled={seccionesInactivas.SoporteDocumentalDictamen}
          onClickElement={(open) =>
            onClickElement(open, "SoporteDocumentalDictamen")
          }
        >
          <SoporteDocumentalDictamen />
        </Accordion>
        <Accordion
          title="Deducciones / descuentos / penalizaciones"
          show={showSecciones.Proforma}
          disabled={seccionesInactivas.Proforma}
          onClickElement={(open) =>
            onClickElement(open, "Proforma")
          }
        >
          <Proforma />
        </Accordion>
        <Accordion
          title="Solicitud de factura"
          show={showSecciones.Factura}
          disabled={seccionesInactivas.Factura}
          onClickElement={(open) =>
            onClickElement(open, "Factura")
          }
        >
          <SolicitudFactura reload={reload}submitForm={submitForm}/>
        </Accordion>
        <Accordion
          title="Facturas"
          show={showSecciones.Facturas}
          disabled={seccionesInactivas.Facturas}
          onClickElement={(open) =>
            onClickElement(open, "Facturas")
          }
        >
          <Facturas setReload={setReload} setSubmitForm={setSubmitForm}/>
        </Accordion>
        <Accordion
          title="Notas de crédito"
          show={showSecciones.NotaCredito}
          disabled={seccionesInactivas.NotaCredito}
          onClickElement={(open) =>
            onClickElement(open, "NotaCredito")
          }
        >
          <NotaCredito />
        </Accordion>
        <Accordion
          title="Solicitud de pago"
          show={showSecciones.SolicitudPago}
          disabled={seccionesInactivas.SolicitudPago}
          onClickElement={(open) =>
            onClickElement(open, "SolicitudPago")
          }
        >
          <SolicitudPago />
        </Accordion>
        <Accordion
          title="Gestión documental"
          show={showSecciones.GestionDocumental}
          disabled={seccionesInactivas.GestionDocumental}
          onClickElement={(open) =>
            onClickElement(open, "GestionDocumental")
          }
        >
          <GestionDocumental/>
        </Accordion>

        <SeccionRegresar />
      </DictamenContext.Provider>
    </Container>
  );
};

export default Proyecto;
