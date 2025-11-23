import React, { useState, useEffect } from "react";
import MainTitle from "../../../components/MainTitle";
import Loader from "../../../components/Loader";
import Accordion from "../../../components/Accordion";
import { useNavigate } from "react-router-dom";
import { Container, Row, Col, Button } from "react-bootstrap";
import DictamenesAsociados from "./DictamenesAsociados";
import FacturasAsociadas from "./FacturasAsociadas";
import RegistroServicios from "./ServiciosRegistro";
import DatosGenerales from "./DatosGenerales";
import BasicModal from "../../../modals/BasicModal";
import { isEmpty } from "lodash";
import { getData } from "../../../functions/api";
import { CREAR_ESTIMACION as MESSAGES } from "../../../constants/messages";

const Estimacion = () => {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [volumetriaActiva, setVolumetríaActiva] = useState(false);
  const [idEstimacion, setIdEstimacion] = useState("");
  const [idDesencriptado, setIdDesencriptado] = useState("");
  const [ultimaModificacion, setlastModificacion] = useState("");
  const [fechasContrato, setFechasContrato] = useState(null);
  const [idProveedor, getIdProveedor] = useState("");
  const [idContrato, setIdContrato] = useState("");
  const [idDEstimacionDuplicado, setIdDEstimacionDuplicado] = useState("");
  const [estimacionCancelada, setEstimacionCancelada] = useState(false);
  const [estadoInicial, setEstadoInicial] = useState(false);
  const [isduplicado, setIsDuplicado] = useState(false);
  const [updateMonto, setupdateMonto] = useState(false);
  const [actualizaFecha, setActualizaFecha] = useState(false);
  const [dicRelacionados, setDicRelacionados] = useState(null);
  const [originalEstate, setOriginalEstate] = useState([]);
  const [showVolumetriaActiva, setShowVolumetriaActiva] = useState(false);
  const [reload, setReloadRegistro] = useState(false);
  const [reloadRegistro, setReloadRegistroServicios] = useState(false);

  useEffect(() => {
    console.log(showVolumetriaActiva);
    getDataInit();

    if (idEstimacion) {
      fetchData();
    }
  }, [
    idEstimacion,
    idDesencriptado,
    dicRelacionados,
    reload,
    actualizaFecha,
    showVolumetriaActiva,
  ]);

  const getDataInit = () => {
    setLoading(false);
  };

  const fetchData = async () => {
    try {
      const response = await getData(
        `/admin-devengados/servicios-estimados?id=${idEstimacion}`
      );
      setOriginalEstate(response.data);
    } catch (error) {
      console.log("Error en la consulta de servicios estimados:", error);
      setLoading(false);
    }
  };

  const handleGoBack = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleDeny = () => {
    handleCloseModal();
  };

  const handleAccept = () => {
    navigate("/consumoServicios/consumoServicios");
    handleCloseModal();
  };
  const handleSetIdEstimacion = (cadena) => {
    const encodedCadena = encodeURIComponent(cadena);
    setIdEstimacion(encodedCadena);
  };

  if (loading) {
    return <Loader />;
  }

  return (
    <Container className="mt-3 px-3">
      <MainTitle title="Consumo de Servicios - Estimación" />
      <div className="text-end">
        {/* {isEmpty(fechasContrato) === false && (
          <strong style={{ color: "#ee2a2a" }}>
            {fechasContrato} &nbsp;&nbsp;&nbsp;
          </strong>
        )} */}
        {isEmpty(ultimaModificacion) === false && (
          <>
            <strong>Última modificación: </strong>
            {ultimaModificacion}
          </>
        )}
      </div>
      <Accordion
        title="Datos generales estimación"
        showChevron={false}
        disabled={true}
      >
        <DatosGenerales
          reloadRegistro={reloadRegistro}
          setReloadRegistroServicios={setReloadRegistroServicios}
          dicRelacionados={dicRelacionados}
          setIdDesencriptado={setIdDesencriptado}
          setIdEstimacion={handleSetIdEstimacion}
          setlastModificacion={setlastModificacion}
          setFechasContrato={setFechasContrato}
          idEstimacion={idEstimacion}
          getIdProveedor={getIdProveedor}
          setIdContrato={setIdContrato}
          volumetriaActiva={volumetriaActiva}
          showVolumetriaActiva={showVolumetriaActiva}
          actualizaFecha={actualizaFecha}
          idDEstimacionDuplicado={idDEstimacionDuplicado}
          setReloadRegistro={setReloadRegistro}
          setShowVolumetriaActiva={setShowVolumetriaActiva}
          setVolumetríaActiva={setVolumetríaActiva}
          setEstimacionCancelada={setEstimacionCancelada}
          setEstadoInicial={setEstadoInicial}
          setIsDuplicado={setIsDuplicado}
          updateMonto={updateMonto}
          setupdateMonto={setupdateMonto}
        />
      </Accordion>
      <Accordion
        title="Registro de servicios"
        show={false}
        disabled={!idEstimacion}
      >
        <RegistroServicios
          setReloadRegistroServicios={setReloadRegistroServicios}
          setlastModificacion={setlastModificacion}
          setReloadRegistro={setReloadRegistro}
          reload={reload}
          setShowVolumetriaActiva={setShowVolumetriaActiva}
          setDicRelacionados={setDicRelacionados}
          isduplicado={isduplicado}
          idDesencriptado={idDesencriptado}
          setActualizaFecha={setActualizaFecha}
          setIdDEstimacionDuplicado={setIdDEstimacionDuplicado}
          idEstimacion={idEstimacion}
          setVolumetríaActiva={setVolumetríaActiva}
          volumetriaActiva={volumetriaActiva}
          estimacionCancelada={estimacionCancelada}
          estadoInicial={estadoInicial}
          originalEstate={originalEstate}
          setupdateMonto={setupdateMonto}
        />
      </Accordion>
      <Accordion
        title="Dictámenes asociados"
        show={false}
        disabled={!idEstimacion}
      >
        <DictamenesAsociados
          idEstimacion={idEstimacion}
          idProveedor={idProveedor}
          idContrato={idContrato}
        />
      </Accordion>
      <Accordion
        title="Facturas asociadas"
        show={false}
        disabled={!idEstimacion}
      >
        <FacturasAsociadas
          idEstimacion={idEstimacion}
          idProveedor={idProveedor}
          idContrato={idContrato}
        />
      </Accordion>
      <Row>
        <BasicModal
          show={showModal}
          onHide={handleCloseModal}
          size={"md"}
          title="Mensaje"
          denyText="No"
          handleDeny={handleDeny}
          approveText="Sí"
          handleApprove={handleAccept}
        >
          {MESSAGES.MSG002}
        </BasicModal>
        <Col md={12} className="text-end mb-2">
          <Button
            variant="red"
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={handleGoBack}
          >
            Regresar
          </Button>
        </Col>
      </Row>
    </Container>
  );
};
export default Estimacion;
