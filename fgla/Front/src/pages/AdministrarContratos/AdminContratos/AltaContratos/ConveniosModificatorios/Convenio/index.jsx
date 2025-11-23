import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { Accordion, MainTitle } from "../../../../../../components";
import Registro from "./Registro";
import { RegistroServicios } from "./RegistroServicios";
import { ProyeccionConvenio } from "./ProyeccionConvenio";
import { AsignacionPlantilla } from "./AsignacionPlantilla";
import { GestionDocumental } from "./GestionDocumental";
import {
  useLocation,
  useNavigate,
  useParams,
  useSearchParams,
} from "react-router-dom";
import { useGetUltimaModificacionConvenioQuery } from "./store";
import moment from "moment";
import Authorization from "../../../../../../components/Authorization";

const Convenio = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const isDetalle = location.pathname.includes("verDetalle");
  const { idConvenio } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoConvenio = searchParams.get("cm");
  const disable = idConvenio || idNuevoConvenio ? false : true;
  const { data: convenio } = useGetUltimaModificacionConvenioQuery(
    idConvenio || idNuevoConvenio
  );

  return (
    <Container className="mt-3 px-3">
      <MainTitle title="Convenio Modificatorio" />

      <Row className="justify-content-end mb-2">
        <Col className="text-end" md={6}>
          Última modificación: &nbsp; {convenio ? convenio : null}
        </Col>
      </Row>

      <Accordion title="Registro de convenio modificatorio">
        <Registro isDetalle={isDetalle} />
      </Accordion>

      <Accordion title="Registro de servicios" show={false} disabled={disable}>
        <RegistroServicios isDetalle={isDetalle} />
      </Accordion>

      <Accordion
        title="Proyección de convenio modificatorio"
        show={false}
        disabled={disable}
      >
        <ProyeccionConvenio isDetalle={isDetalle} />
      </Accordion>

      <Accordion
        title="Asignación de plantilla"
        show={false}
        disabled={disable}
      >
        <AsignacionPlantilla isDetalle={isDetalle} />
      </Accordion>

      <Accordion title="Gestión documental" show={false} disabled={disable}>
        <GestionDocumental isDetalle={isDetalle} />
      </Accordion>
    </Container>
  );
};

export default Convenio;
