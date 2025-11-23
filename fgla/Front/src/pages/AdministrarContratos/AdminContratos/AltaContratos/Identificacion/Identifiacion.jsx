import { Formik } from "formik";
import React, { useEffect, useState } from "react";
import { Col, Form, Row, Button } from "react-bootstrap";
import LabelValue from "../../../../../components/LabelValue";
import Select from "../../../../../components/formInputs/Select";
import TextField from "../../../../../components/formInputs/TextField";
import {
  useDeleteContratoMutation,
  useGetContratoQuery,
  useGetProyectosCompletosQuery,
  useGetProyectosQuery,
  usePostGuardarContratoMutation,
  usePutEditarContratoMutation,
  usePutEjecutarContratoMutation,
  usePutIniciarContratoMutation,
} from "./store";
import {
  INITIAL_VALUES,
  identificacionSchema,
  rearrangeContrato,
} from "./utils";
import Loader from "../../../../../components/Loader";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import {
  ALTA_CONTRATOS,
  MODIFICAR_CONTRATO,
} from "../../../../../constants/messages";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setSelectedContrato } from "../../../../../store";
import BasicModal from "../../../../../modals/BasicModal";
import IconButton from "../../../../../components/buttons/IconButton";
import { useToast } from "../../../../../hooks/useToast";
import _ from "lodash";
import Authorization from "../../../../../components/Authorization";

export function Identificacion({ isDetalle }) {
  const { getMessageExists } = useErrorMessages(ALTA_CONTRATOS);
  const navigate = useNavigate();
  const { errorToast } = useToast();
  const { idContrato } = useParams();
  const [searchParams, setSearchParams] = useSearchParams({ q: "" });
  const idNuevoContrato = searchParams.get("q");
  const dispatch = useDispatch();
  const [singleBasicModal, setShowSingleBasicModal] = useState(false);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [identificacion, setIdentificacion] = useState(INITIAL_VALUES);
  const [showOnCancel, setShowOnCancel] = useState(false);
  const [showOnEjecutar, setShowOnEjecutar] = useState(false);
  const [showOnInciar, setShowOnInciar] = useState(false);
  const [showOnCancelar, setShowOnCancelar] = useState(false);

  const { data: proyectosActivos, isLoading: isLoadingProyectos } =
    useGetProyectosQuery({ skip: !!idContrato });

  const [guardarContrato, { isLoading: isLoadingGuardando }] =
    usePostGuardarContratoMutation();

  const [editarContrato, { isLoading: isLoadingEditar }] =
    usePutEditarContratoMutation();

  const { data: proyectosCompletos, isLoading: isLoadingProyectosCompletos } =
    useGetProyectosCompletosQuery({
      skip: !!idNuevoContrato,
    });

  const [ejecutarContrato, { isLoading: isLoadingEjecutar }] =
    usePutEjecutarContratoMutation();
  const [iniciarContrato, { isLoading: isLoadingIniciar }] =
    usePutIniciarContratoMutation();
  const [cancelarContrato, { isLoading: isLoadingCancelar }] =
    useDeleteContratoMutation();
  const { data: contratoQ, isLoading: isLoadingContratoQ } =
    useGetContratoQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });
  useEffect(() => {
    if (contratoQ) {
      setIdentificacion(() => ({
        idContrato: contratoQ?.idContratoFormato,
        nombreContrato: contratoQ?.nombreContrato,
        nombreCortoContrato: contratoQ?.nombreCorto,
        estatusContrato: contratoQ?.estatusContrato,
        idProyecto: contratoQ?.idProyecto,
      }));
      dispatch(setSelectedContrato(contratoQ.content?.[0]));
    }
  }, [contratoQ]);

  const onEjecutar = async () => {
    try {
      await ejecutarContrato(idContrato || idNuevoContrato).unwrap();
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG015);
      setShowSingleBasicModal(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
        return;
      }
      if (mensaje?.includes("servicio")) {
        setSingleBasicMessage(MODIFICAR_CONTRATO.MSG023);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const onIniciar = async () => {
    try {
      await iniciarContrato(parseInt(idContrato || idContrato)).unwrap();
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG015);
      setShowSingleBasicModal(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const onCancelar = async () => {
    try {
      await cancelarContrato(parseInt(idContrato || idNuevoContrato)).unwrap();
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG015);
      setShowSingleBasicModal(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];

      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
        return;
      }
      if (
        mensaje.includes(
          "Para poder cerrar el contrato, no deben existir dictámenes pendientes de pago. Favor de verificar."
        )
      ) {
        setSingleBasicMessage(
          "Para poder cancelar el contrato, no deben existir dictámenes pendientes de pago. Favor de verificar."
        );
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };

  const onSubmit = async (values, { resetForm }) => {
    try {
      if (!contratoQ) {
        const data = {
          ...(values.idContrato === null
            ? {}
            : { idContrato: values.idContrato }),
          nombreContrato: values.nombreContrato,
          nombreCortoContrato: values.nombreCortoContrato,
          idProyecto: parseInt(values.idProyecto),
        };
        const res = await guardarContrato({ data }).unwrap();
        dispatch(setSelectedContrato(res));
        setSearchParams(
          (prev) => {
            prev.set("q", res.idContrato);
            return prev;
          },
          { replace: true }
        );
        setSingleBasicMessage(ALTA_CONTRATOS.MSG004);
      } else {
        const data = {
          idContrato: parseInt(idContrato || idNuevoContrato),
          nombreContrato: values.nombreContrato,
          nombreCortoContrato: values.nombreCortoContrato,
          idProyecto: parseInt(values.idProyecto),
          estatusContrato: values?.estatusContrato,
        };
        await editarContrato({ data }).unwrap();
        setSingleBasicMessage("Se guardaron los campos modificados.");
      }
      setShowSingleBasicModal(true);
      resetForm();
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];

      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
        return;
      }
      if (mensaje.toLowerCase()?.includes("iguales")) {
        setSingleBasicMessage(ALTA_CONTRATOS.MSG009);
        setShowSingleBasicModal(true);
      } else if (error?.data?.mensaje[0]?.toLowerCase()?.includes("jta")) {
        setSingleBasicMessage(ALTA_CONTRATOS.MSG009);
        setShowSingleBasicModal(true);
      } else if (
        error?.data?.mensaje[0]?.toLowerCase()?.includes("ya existen")
      ) {
        setSingleBasicMessage(ALTA_CONTRATOS.MSG009);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };

  const onReset = () => {
    if (contratoQ) {
      setIdentificacion(() => ({
        idContrato: contratoQ?.idContratoFormato,
        nombreContrato: contratoQ?.nombreContrato,
        nombreCortoContrato: contratoQ?.nombreCorto,
        estatusContrato: contratoQ?.estatusContrato,
        idProyecto: contratoQ?.idProyecto,
      }));
    } else {
      setIdentificacion(() => ({ ...INITIAL_VALUES }));
    }
  };
  return (
    <>
      <Formik
        initialValues={{ ...identificacion }}
        enableReinitialize
        validationSchema={identificacionSchema}
        onSubmit={onSubmit}
        validateOnMount
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          resetForm,
          values,
          isValid,
          errors,
          touched,
        }) => {
          return (
            <>
              {isLoadingProyectos ||
              isLoadingGuardando ||
              isLoadingContratoQ ||
              isLoadingEjecutar ||
              isLoadingCancelar ||
              isLoadingIniciar ||
              isLoadingProyectosCompletos ||
              isLoadingEditar ? (
                <Loader />
              ) : null}
              <Form onSubmit={handleSubmit} autoComplete="off">
                <Row>
                  <Col md={4}>
                    <LabelValue label={"Id"} value={values.idContrato} />
                  </Col>
                  <Col md={4}>
                    <Select
                      onBlur={handleBlur}
                      options={
                        idContrato === undefined ||
                        idNuevoContrato === undefined
                          ? proyectosActivos
                          : proyectosCompletos
                      }
                      keyValue={"idProyecto"}
                      keyTextValue={"nombreCorto"}
                      name={"idProyecto"}
                      label={"Proyecto asociado*:"}
                      onChange={handleChange}
                      value={values?.idProyecto}
                      helperText={touched.idProyecto ? errors.idProyecto : ""}
                      className={
                        touched.idProyecto &&
                        (errors.idProyecto ? "is-invalid" : "is-valid")
                      }
                      disabled={isDetalle}
                    />
                  </Col>
                  <Col md={3}>
                    <LabelValue
                      label={"Estatus"}
                      value={values.estatusContrato}
                    />
                  </Col>
                  <Col md={1} className="pt-4 text-end">
                    {values?.estatusContrato?.toLowerCase() !== "cancelado" &&
                    values.estatusContrato !== null ? (
                      <Authorization process={"CONT_IDENT_STA_CANCEL"}>
                        <IconButton
                          type="cancel"
                          onClick={() => setShowOnCancelar(true)}
                          tooltip={"Cancelar contrato"}
                          disabled={isDetalle}
                        />
                      </Authorization>
                    ) : null}
                  </Col>
                </Row>
                <Row>
                  <Col md={8}>
                    <TextField
                      name={"nombreContrato"}
                      label={"Nombre de contrato*:"}
                      onChange={handleChange}
                      value={values.nombreContrato}
                      helperText={
                        touched.nombreContrato ? errors.nombreContrato : ""
                      }
                      className={
                        touched.nombreContrato &&
                        (errors.nombreContrato ? "is-invalid" : "is-valid")
                      }
                      onBlur={handleBlur}
                      disabled={isDetalle}
                    />
                  </Col>
                  <Col>
                    <TextField
                      name={"nombreCortoContrato"}
                      label={"Nombre corto de contrato*:"}
                      onChange={handleChange}
                      value={values.nombreCortoContrato}
                      helperText={
                        touched.nombreCortoContrato
                          ? errors.nombreCortoContrato
                          : ""
                      }
                      className={
                        touched.nombreCortoContrato &&
                        (errors.nombreCortoContrato ? "is-invalid" : "is-valid")
                      }
                      onBlur={handleBlur}
                      disabled={isDetalle}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={12} className="text-end">
                    {identificacion.idContrato !== null ? (
                      <>
                        {identificacion.estatusContrato !== "Inicial" &&
                        identificacion.estatusContrato !== "Cancelado" ? (
                          <Authorization process={"CONT_IDENT_STA_INICIAL"}>
                            <Button
                              variant="gray"
                              className="btn-sm ms-2 waves-effect waves-light"
                              onClick={() => {
                                setShowOnInciar(true);
                              }}
                              disabled={isDetalle}
                            >
                              Inicial
                            </Button>
                          </Authorization>
                        ) : null}
                        {identificacion.estatusContrato !== "Ejecución" ? (
                          <Authorization process={"CONT_IDENT_STA_EJECUCION"}>
                            <Button
                              variant="gray"
                              className="btn-sm ms-2 waves-effect waves-light"
                              onClick={() => {
                                setShowOnEjecutar(true);
                              }}
                              disabled={isDetalle}
                            >
                              Ejecución
                            </Button>
                          </Authorization>
                        ) : null}
                      </>
                    ) : null}
                    {!isDetalle ? (
                      <Authorization process={"CONT_ALTA"}>
                        <>
                          <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => {
                              setShowOnCancel(true);
                            }}
                          >
                            Cancelar
                          </Button>
                          <Button
                            variant="green"
                            className="btn-sm ms-2 waves-effect waves-light"
                            type="submit"
                            onClick={() => {
                              !isValid && errorToast(MODIFICAR_CONTRATO.MSG001);
                            }}
                          >
                            Guardar
                          </Button>
                        </>
                      </Authorization>
                    ) : null}
                  </Col>
                </Row>
                <BasicModal
                  size={"md"}
                  denyText={"No"}
                  approveText={"Sí"}
                  title={"Mensaje"}
                  show={showOnCancel}
                  handleDeny={() => setShowOnCancel(false)}
                  onHide={() => setShowOnCancel(false)}
                  handleApprove={() => {
                    if (_.isEqual(values, identificacion) && !contratoQ) {
                      navigate(-1);
                    }
                    resetForm();
                    onReset();
                    setShowOnCancel(false);
                  }}
                >
                  {MODIFICAR_CONTRATO.MSG002}
                </BasicModal>
              </Form>
            </>
          );
        }}
      </Formik>
      <SingleBasicModal
        size={"md"}
        title={"Mensaje"}
        approveText={"Aceptar"}
        show={singleBasicModal}
        onHide={() => {
          setShowSingleBasicModal(false);
        }}
      >
        {singleBasicMessage}
      </SingleBasicModal>
      <BasicModal
        size={"md"}
        denyText={"No"}
        approveText={"Sí"}
        title={"Mensaje"}
        show={showOnEjecutar}
        handleDeny={() => setShowOnEjecutar(false)}
        onHide={() => setShowOnEjecutar(false)}
        handleApprove={() => {
          onEjecutar();
          setShowOnEjecutar(false);
        }}
      >
        {MODIFICAR_CONTRATO.MSG024}
      </BasicModal>
      <BasicModal
        size={"md"}
        denyText={"No"}
        approveText={"Sí"}
        title={"Mensaje"}
        show={showOnInciar}
        handleDeny={() => setShowOnInciar(false)}
        onHide={() => setShowOnInciar(false)}
        handleApprove={() => {
          onIniciar();
          setShowOnInciar(false);
        }}
      >
        {MODIFICAR_CONTRATO.MSG025}
      </BasicModal>
      <BasicModal
        size={"md"}
        denyText={"No"}
        approveText={"Sí"}
        title={"Mensaje"}
        show={showOnCancelar}
        handleDeny={() => setShowOnCancelar(false)}
        onHide={() => setShowOnCancelar(false)}
        handleApprove={() => {
          onCancelar();
          setShowOnCancelar(false);
        }}
      >
        {MODIFICAR_CONTRATO.MSG026}
      </BasicModal>
    </>
  );
}
