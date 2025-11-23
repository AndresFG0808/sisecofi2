import { Formik } from "formik";
import React, { useEffect, useState } from "react";
import { Form } from "react-bootstrap";
import { AdministracionProyecto } from "./AdministracionProyecto";
import { LiderProyecto } from "./LiderProyecto";
import { AlineacionProyecto } from "./AlineacionProyecto";
import { InformacionProyecto } from "./InformacionProyecto";
import { BotonConfirmacion } from "./BotonConfirmacion";
import BasicModal from "../../../../modals/BasicModal";
import { MODIFICAR_PROYECTOS } from "../../../../constants/messages";
import { useGetFichaQuery, usePostModificarFichaMutation } from "../../store";
import { useSelector } from "react-redux";
import {
  fichaSchema,
  VALORES_INICIALES,
  lideresSchema,
  alineacionSchema,
  arrangeFicha,
  requiredValidation,
  liderTableErrors,
} from "./utils";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import Loader from "../../../../components/Loader";
import moment from "moment";
import { useToast } from "../../../../hooks/useToast";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";
import _ from "lodash";
import { useDispatch } from "react-redux";
import { GetDetalleProyecto } from "../../../../store/infoComites/infoComitesActions";
import { useGetAuthorization } from "../../../../hooks/useGetAuthorization";

export function FichaTecnica() {
  const dispatch = useDispatch();
  const { getMessageExists } = useErrorMessages(MODIFICAR_PROYECTOS);
  const { proyecto, editable } = useSelector((state) => state.proyectos);
  const { errorToast } = useToast();
  const { data: fichaTecnica, isLoading } = useGetFichaQuery(
    proyecto?.proyecto?.idProyecto
  );
  const [valoresIniciales, setValoresIniciales] = useState(VALORES_INICIALES);
  const [parentMemoizedLeadersData, setParentMemoizedLeadersData] = useState(
    new Map()
  );
  const [parentMemoizedAlineacionData, setParentMemoizedAlineacionData] =
    useState(new Map());
  const [lideresIniciales, setLideresIniciales] = useState(undefined);
  const [alineacionesIniciales, setAlineacionesIniciales] = useState(undefined);
  const [fichaResponse, setFichaResponse] = useState(undefined);
  const [lideres, setLideres] = useState([]);
  const [alineaciones, setAlineaciones] = useState([]);
  const [lideresEliminados, setLideresEliminados] = useState([]);
  const [alineacionesEliminadas, setAlineacionesEliminadas] = useState([]);
  const [rearrangeTable] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [isSingleOpen, setIsSingleOpen] = useState(false);
  const [displayMessage, setDisplayMessage] = useState("");
  const { isAuthorized: accessFT } = useGetAuthorization("PROY_FT_ADMIN");

  const [postModificarFicha, { isLoading: isPostLoading }] =
    usePostModificarFichaMutation();
  const handleCancel = () => {
    setIsOpen(true);
  };

  const isInicial = proyecto?.estatus?.nombre
    ?.toLowerCase()
    ?.includes("inicial");

  const able = (editable || !accessFT) && isInicial;

  const isCerradoCancelado = ["cerrado", "cancelado"].includes(
    proyecto?.estatus?.nombre?.toLowerCase()
  );
  const ableLiderProyecto = (editable || !accessFT) && !isCerradoCancelado;

  useEffect(() => {
    if (fichaTecnica?.ficha) {
      setValoresIniciales(() => arrangeFicha(fichaTecnica?.ficha));
      setFichaResponse(fichaTecnica);
      setLideresIniciales(fichaTecnica?.lideres);
      setAlineacionesIniciales(fichaTecnica?.alineaciones);
      setLideresEliminados([]);
      setAlineacionesEliminadas([]);
    }
  }, [fichaTecnica]);

  const onCancel = () => {
    if (fichaTecnica?.ficha) {
      setValoresIniciales(() => arrangeFicha(fichaTecnica?.ficha));
      setFichaResponse(fichaTecnica);
      setLideresIniciales([...fichaTecnica?.lideres]);
      setAlineacionesIniciales([...fichaTecnica?.alineaciones]);
      setLideresEliminados([]);
      setAlineacionesEliminadas([]);
    } else {
      setValoresIniciales(VALORES_INICIALES);
      setLideresIniciales([]);
      setAlineacionesIniciales([]);
      setLideresEliminados([]);
      setAlineacionesEliminadas([]);
    }
  };
  const onSubmit = async (values, { resetForm }) => {
    try {
      if (alineaciones.length <= 0 || lideres.length <= 0) {
        errorToast(MODIFICAR_PROYECTOS.MSG001);
        return;
      }
      const alineacionValidation = await requiredValidation(
        alineaciones,
        alineacionSchema
      );
      if (alineacionValidation.dataErrors) {
        errorToast(MODIFICAR_PROYECTOS.MSG001);
        setAlineacionesIniciales(() => alineacionValidation.resultados);
        setAlineaciones(() => alineacionValidation.resultados);
        return;
      }
      const lideresToSend = lideres.reduce((prevLider, lider) => {
        const memoizedLider = parentMemoizedLeadersData.get(lider.UUID);

        if (lider.isNewRow) {
          prevLider.push({
            // nuevos campos para backend
            idReferencia: lider.idReferencia ?? lider.idUsuario ?? null, // number
            nivel: lider.nivel ?? null,

            // lo que ya mandabas
            //nombre: lider.nombreDisplay,
           // puesto: lider.puesto,
            //correo: lider.correo,
            fechaInicio: moment(lider.fechaInicio).toISOString(),
            fechaFin:
              lider.fechaFin !== null ? moment(lider.fechaFin).toISOString() : null,
            estatus: lider.estatus,
          });
        }

        if (!lider.isNewRow && !_.isEqual(lider, memoizedLider)) {
          prevLider.push({
            idHistorico: lider.idHistorico,

            idReferencia: lider.idReferencia ?? lider.idUsuario ?? null, 
            nivel: lider.nivel ?? null,

            nombre: lider.nombreDisplay,
            puesto: lider.puesto,
            correo: lider.correo,
            fechaInicio: moment(lider.fechaInicio).toISOString(),
            fechaFin:
              lider.fechaFin !== null ? moment(lider.fechaFin).toISOString() : null,
            estatus: lider.estatus,
          });
        }

        return prevLider;
      }, []);
      await lideresSchema.validate(lideresToSend);
      const alienation = alineaciones.reduce((prevAlineaciones, alineacion) => {
        const memoizedAlineation = parentMemoizedAlineacionData.get(
          alineacion.UUID
        );

        if (alineacion.isNewRow) {
          prevAlineaciones.push({
            idMapa: parseInt(alineacion.mapa),
            idPeriodo: parseInt(alineacion.periodo),
            idObjetivo: parseInt(alineacion.objetivo),
          });
        }
        if (
          !alineacion.isNewRow &&
          !_.isEqual(alineacion, memoizedAlineation)
        ) {
          prevAlineaciones.push({
            idMapa: parseInt(alineacion.mapa),
            idPeriodo: parseInt(alineacion.periodo),
            idObjetivo: parseInt(alineacion.objetivo),
            idFichaAlineacion: parseInt(alineacion.idAlineacion),
          });
        }

        return prevAlineaciones;
      }, []);

      if (
        values.idAdmonCentrales.length < 0 &&
        values.idAdmonCentrales?.[0] !== ""
      ) {
        errorToast(MODIFICAR_PROYECTOS.MSG020);
        return;
      }
      const data = {
        ficha: {
          idAdmonPatrocinadora: parseInt(values.idAdmonPatrocinadora),
          ...(values.idAdmonParticipante
            ? { idAdmonParticipante: parseInt(values.idAdmonParticipante) }
            : {}),
          idClasificacionProyecto: parseInt(values.idClasificacionProyecto),
          fechaInicio: moment(values.fechaInicio).toISOString(),
          fechaTermino: moment(values.fechaTermino).toISOString(),
          objetivoGeneral: values.objetivoGeneral,
          montoSolicitado: values.montoSolicitado
            ? parseFloat(
              String(values.montoSolicitado).replace(/,/g, "")
            ).toFixed(2)
            : null,
          alcance: values.alcance,
          idAdmonCentrales: [...values.idAdmonCentrales],
          idAreaPlaneacion: parseInt(values.idAreaPlaneacion),
          idProyecto: parseInt(proyecto?.proyecto?.idProyecto),
          idFinanciamiento: parseInt(values.idFinanciamiento),
          idTipoProcedimiento: parseInt(values.idTipoProcedimiento),
          idTipoMoneda: parseInt(values.idTipoMoneda),
        },
        lideres: [...lideresToSend],
        alineaciones: [...alienation],
        lideresEliminados: [...lideresEliminados],
        alineacionesEliminadas: [...alineacionesEliminadas],
      };

      await postModificarFicha({
        data,
        id: proyecto?.proyecto?.idProyecto,
      }).unwrap();

      resetForm();
      setIsSingleOpen(true);
      setDisplayMessage(MODIFICAR_PROYECTOS.MSG010);
      dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];

      if (error?.errors?.[0]) {
        if (liderTableErrors.includes(error?.errors?.[0])) {
          errorToast(error?.errors?.[0]);
        } else {
          errorToast(MODIFICAR_PROYECTOS.MSG001);
        }
        return;
      }
      if (getMessageExists(mensaje)) {
        if (mensaje === MODIFICAR_PROYECTOS.MSG001) {
          errorToast(MODIFICAR_PROYECTOS.MSG001);
        } else {
          setDisplayMessage(mensaje);
          setIsSingleOpen(true);
        }
      } else {
        setDisplayMessage("Ocurrió un error.");
        setIsSingleOpen(true);
      }
    }
  };

  return (
    <>
      <Formik
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={fichaSchema}
        onSubmit={onSubmit}
        validateOnMount={true}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          setFieldValue,
          resetForm,
          values,
          errors,
          isValid,
          touched,
        }) => {
          return (
            <>
              {isPostLoading || isLoading ? (
                <Loader
                  zIndex={`${isLoading || isPostLoading ? "10" : "99999"}`}
                />
              ) : null}
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <AdministracionProyecto
                  data={fichaResponse}
                  handleChange={handleChange}
                  values={values}
                  errors={errors}
                  touched={touched}
                  onBlur={handleBlur}
                  editable={able}
                />
                <LiderProyecto
                  data={lideresIniciales}
                  handleChange={handleChange}
                  values={values}
                  onChangeInitialValues={setLideres}
                  onChangeLideresEliminados={setLideresEliminados}
                  rearrangeTable={rearrangeTable}
                  editable={ableLiderProyecto}
                  setParentMemoizedLeadersData={setParentMemoizedLeadersData}
                />
                <AlineacionProyecto
                  data={alineacionesIniciales}
                  handleChange={handleChange}
                  values={values}
                  onChangeInitialValues={setAlineaciones}
                  onChangeAlineacionesEliminadas={setAlineacionesEliminadas}
                  rearrangeTable={rearrangeTable}
                  editable={able}
                  setParentMemoizedAlineacionData={
                    setParentMemoizedAlineacionData
                  }
                />
                <InformacionProyecto
                  data={fichaResponse}
                  handleChange={handleChange}
                  values={values}
                  errors={errors}
                  touched={touched}
                  onBlur={handleBlur}
                  editable={able}
                  setFieldValue={setFieldValue}
                />
                {ableLiderProyecto ? (
                  <BotonConfirmacion
                    handleCancel={handleCancel}
                    isValid={isValid}
                    message={MODIFICAR_PROYECTOS.MSG001}
                  />
                ) : null}
                <BasicModal
                  handleDeny={() => setIsOpen(false)}
                  size={"md"}
                  handleApprove={() => {
                    resetForm();
                    onCancel();
                    setIsOpen(false);
                  }}
                  denyText={"No"}
                  approveText={"Sí"}
                  show={isOpen}
                  title={"Mensaje"}
                  onHide={() => setIsOpen(false)}
                >
                  {MODIFICAR_PROYECTOS.MSG018}
                </BasicModal>
                <SingleBasicModal
                  size={"md"}
                  title={"Mensaje"}
                  approveText={"Aceptar"}
                  show={isSingleOpen}
                  onHide={() => {
                    setIsSingleOpen(false);
                  }}
                >
                  {displayMessage}
                </SingleBasicModal>
              </Form>
            </>
          );
        }}
      </Formik>
    </>
  );
}
