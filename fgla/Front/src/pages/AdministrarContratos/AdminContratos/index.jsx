import React, { useState } from "react";
import { Container, Row, Col, Button } from "react-bootstrap";
import MainTitle from "../../../components/MainTitle";
import Accordion from "../../../components/Accordion";
import {
  AsignacionPlantilla,
  AtrasoPrestacion,
  Cierre,
  ConveniosModificatorios,
  DictamenesAsociados,
  FacturasAsociadas,
  Identificacion,
  InformeDocumentalServicio,
  InformesPeriodicos,
  LayoutInformes,
  NivelesServicio,
  PenasContractuales,
  ReintegrosAsociados,
} from "./AltaContratos";
import { DatosGenerales } from "./AltaContratos/DatosGenerales";
import { VigenciaMontos } from "./AltaContratos/VigenciaMontos/VigenciaMontos";
import { CasoNegocio } from "./AltaContratos/CasoNegocio";
import { GruposServicio } from "./AltaContratos/GruposServicio";
import { RegistroServicios } from "./AltaContratos/RegistroServicios";
import { InformesDocumentales } from "./AltaContratos/InformesDocumentales/InformesDocumentales";
import { GestionDocumental } from "./AltaContratos/GestionDocumental";
import {
  useLocation,
  useNavigate,
  useParams,
  useSearchParams,
} from "react-router-dom";
import BasicModal from "../../../modals/BasicModal";
import { MODIFICAR_CONTRATO } from "../../../constants/messages";
import {
  useGetContratoQuery,
  useGetUltimaModificacionContratoQuery,
} from "./AltaContratos/Identificacion/store";
import Authorization from "../../../components/Authorization";
import moment from "moment";
import { useGetAuthorization } from "../../../hooks/useGetAuthorization";

const AdminContratos = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");
  const disabled = idContrato || idNuevoContrato ? false : true;
  const [showRegresar, setShowRegresar] = useState(false);
  const { data } = useGetContratoQuery(idContrato || idNuevoContrato);
  const { data: ultimaModificacion } = useGetUltimaModificacionContratoQuery(
    idContrato || idNuevoContrato
  );

  const { isAuthorized: isLectura } = useGetAuthorization([""]);
  const isDetalle =
    location.pathname.includes("verDetalle") ||
    data?.estatusContrato === "Cerrado" ||
    data?.estatusContrato === "Cancelado" ||
    isLectura;

  const handleGoBack = () => {
    const path = "/contratos/contratos";
    navigate(path);
  };

  return (
    <>
      <Container className="mt-3 px-3">
        <MainTitle title="Contratos" />

        <Row className="justify-content-end mb-2">
          <Col className="text-end" md={6}>
            Última modificación:&nbsp;{" "}
            {ultimaModificacion ? ultimaModificacion : null}
          </Col>
        </Row>
        <Accordion title="Identificación" show={true}>
          <Identificacion isDetalle={isDetalle} />
        </Accordion>
        <Accordion title="Datos generales" show={false} disabled={disabled}>
          <DatosGenerales isDetalle={isDetalle} />
        </Accordion>
        <Accordion title="Vigencia y montos" show={false} disabled={disabled}>
          <VigenciaMontos isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Grupos de servicio y/o conceptos"
          show={false}
          disabled={disabled}
        >
          <GruposServicio isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Registro de servicios"
          show={false}
          disabled={disabled}
        >
          <RegistroServicios isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Proyección de caso de negocio"
          show={false}
          disabled={disabled}
        >
          <CasoNegocio isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Cargar layout de los informes"
          show={false}
          disabled={disabled}
        >
          <LayoutInformes isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Atraso en el inicio de la prestación"
          show={false}
          disabled={disabled}
        >
          <AtrasoPrestacion isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Informes documentales por única vez"
          show={false}
          disabled={disabled}
        >
          <InformesDocumentales isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Informes documentales periódicos"
          show={false}
          disabled={disabled}
        >
          <InformesPeriodicos isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Informes documentales de los servicios"
          show={false}
          disabled={disabled}
        >
          <InformeDocumentalServicio isDetalle={isDetalle} />
        </Accordion>
        <Accordion title="Penas contractuales" show={false} disabled={disabled}>
          <PenasContractuales isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Niveles de servicio(SLA)"
          show={false}
          disabled={disabled}
        >
          <NivelesServicio isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Asignación de plantilla"
          show={false}
          disabled={disabled}
        >
          <AsignacionPlantilla isDetalle={isDetalle} />
        </Accordion>
        <Accordion title="Gestión documental" show={false} disabled={disabled}>
          <GestionDocumental isDetalle={isDetalle} />
        </Accordion>

        {/* <Authorization process={"CONT_CM_ADMIN"}> */}
        <Accordion
          title="Convenios modificatorios"
          show={false}
          disabled={disabled}
        >
          <ConveniosModificatorios isDetalle={isDetalle} />
        </Accordion>
        {/* </Authorization> */}

        {/* SE QUITAN LAS VALIDACIONES YA QUE SE DEBEN MOSTRAR EN MODO LECTURA NO OCULTARSE */}
        {/* PENDIENTE AJUSTES DE VALIDACION MODO LECTURA POR SECCIONES */}

        <Accordion
          title="Dictámenes asociados"
          show={false}
          disabled={disabled}
        >
          <DictamenesAsociados isDetalle={isDetalle} />
        </Accordion>
        <Accordion title="Facturas asociadas" show={false} disabled={disabled}>
          <FacturasAsociadas isDetalle={isDetalle} />
        </Accordion>
        <Accordion
          title="Reintegros asociados"
          show={false}
          disabled={disabled}
        >
          <ReintegrosAsociados isDetalle={isDetalle} />
        </Accordion>
        <Accordion title="Cierre" show={false} disabled={disabled}>
          <Cierre isDetalle={isDetalle} />
        </Accordion>
        <Row>
          <Col md={12} className="text-end mb-2">
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={() => setShowRegresar(true)}
            >
              Regresar
            </Button>
          </Col>
        </Row>
      </Container>
      <BasicModal
        show={showRegresar}
        size={"md"}
        title="Mensaje"
        approveText={"Sí"}
        denyText={"No"}
        onHide={() => setShowRegresar(false)}
        handleDeny={() => setShowRegresar(false)}
        handleApprove={() => {
          setShowRegresar(false);
          handleGoBack();
        }}
      >
        {MODIFICAR_CONTRATO.MSG002}
      </BasicModal>
    </>
  );
};

export default AdminContratos;
