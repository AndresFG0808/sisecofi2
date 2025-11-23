import React, { useState, useRef, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Container, Row, Col, Button } from "react-bootstrap";
import { MainTitle, Accordion } from "../../../components";
import { FichaTecnica } from "./FichaTecnica/FichaTecnica";
import DatosGenerales from "./DatosGenerales";
import { AsociarFases } from "./AsociarFases/AsociarFases";
import { Proveedores } from "./Proveedores/Proveedores";
import { GestionDocumental } from "./GestionDocumental/GestionDocumental";
import InformacionComites from "./InformacionComites/InformacionComites";
import PlanDeTrabajo from "./PlanDeTrabajo";
import { useSelector, useDispatch } from "react-redux";
import { onEditProyecto, onToggleAllSections } from "../../../store/pryectos";
import BasicModal from "../../../modals/BasicModal";
import { ALTA_PROYECTOS } from "../../../constants/messages";
import VerificacionRCP from "./VerificacionRCP";
import { ProyectosContext } from "./ProyectosContext";

const Proyecto = () => {
  const planTrabajoRef = useRef();
  const navigate = useNavigate();
  const { state } = useLocation();
  const { seccionesInactivas } = useSelector((state) => state.proyectos);
  const [proyecto, setProyecto] = useState(
    state?.proyecto ? JSON.parse(state.proyecto) : null
  );
  const [ultimaModificacion, setUltimaModificacion] = useState("");
  const dispatch = useDispatch();
  const [isOpen, setIsOpen] = useState(false);
  const [isDisabled, setIsDisabled] = useState(state?.editable ? !state.editable : true);
  const [onReloadRCPInfo, setOnReloadRCPInfo] = useState(null);
  const [verPlanTrabajo, setVerPlanTrabajo] = useState(state?.planTrabajo ? JSON.parse(state.planTrabajo) : false);

  useEffect(() => {
    if (verPlanTrabajo) {
      planTrabajoRef.current.scrollIntoView({
        behavior: "smooth",
        block: "center",
        inline: "start"
      });
    }
  }, []);

  const handleGoBack = () => {
    setIsOpen(true);
    console.log("handleGoBack => ");
  };

  const handleGoBackApprove = () => {
    console.log("handleGoBackApprove => ");
    let path = "/proyectos/proyectos";
    dispatch(onEditProyecto(undefined));
    dispatch(onToggleAllSections(true))
    navigate(path);
  };

  return (
    <ProyectosContext.Provider
      value={{
        setOnReloadRCPInfo,
        onReloadRCPInfo,
      }}
    >

      <Container className="mt-3 px-3">
        <MainTitle title="Proyecto" />

        <Row className="justify-content-end mb-2">
          <Col className="text-end" md={6}><b>Última modificación:</b>&nbsp;{ultimaModificacion}</Col>
        </Row>

        <Accordion title="Datos generales" collapse={false} showChevron={false}>
          <DatosGenerales
            proyecto={proyecto}
            setUltimaModificacion={setUltimaModificacion}
            editable={state?.editable ? state.editable : false}
          />
        </Accordion>

        <Accordion
          title="Ficha técnica"
          show={false}
          disabled={seccionesInactivas.fichaTecnica}
        >
          <FichaTecnica />
        </Accordion>

        <Accordion
          title="Asociar fases"
          show={false}
          disabled={seccionesInactivas.asociarFases}
        >
          <AsociarFases />
        </Accordion>

        <Accordion
          title="Gestión documental"
          show={false}
          disabled={seccionesInactivas.gestionDocumental}
        >
          <GestionDocumental />
        </Accordion>

        <Accordion
          title="Información de comités"
          show={false}
          disabled={seccionesInactivas.informacionComites}
        >
          <InformacionComites />
        </Accordion>

        <div ref={planTrabajoRef}>
          <Accordion
            title="Plan de trabajo"
            show={verPlanTrabajo}
            disabled={seccionesInactivas.planTrabajo}
            editable={state?.editable ? state.editable : false}
          >
            <PlanDeTrabajo />
          </Accordion>
        </div>

        <Accordion
          title="Participación de proveedores"
          show={false}
          disabled={seccionesInactivas.participacionProveedores}
        >
          <Proveedores />
        </Accordion>

        <Accordion
          title="Verificación de RCP"
          show={false}
          disabled={seccionesInactivas.verificacionRCP}
        >
          <VerificacionRCP />
        </Accordion>

        <Row>
          <Col md={12} className="text-end mb-2">
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={isDisabled ? handleGoBackApprove : handleGoBack}
            >
              Regresar
            </Button>
          </Col>
        </Row>

        <BasicModal
          show={isOpen}
          size={"md"}
          title="Mensaje"
          approveText={"Sí"}
          denyText={"No"}
          handleApprove={handleGoBackApprove}
          handleDeny={() => setIsOpen(false)}
          onHide={() => setIsOpen(false)}
        >
          {ALTA_PROYECTOS.MSG003}
        </BasicModal>
      </Container>
    </ProyectosContext.Provider>
  );
};

export default Proyecto;
