import { FieldArray, Formik } from "formik";
import React, { useEffect, useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import TextField from "../../../../../components/formInputs/TextField";
import Select from "../../../../../components/formInputs/Select";
import IconButton from "../../../../../components/buttons/IconButton";
import { INITIAL_VALUES, gruposServicioSchema, rearrangeData } from "./utils";
import {
  useDeleteGruposMutation,
  useGetGrupoQuery,
  useGetTipoConsumoQuery,
  usePostGuardarGruposMutation,
  usePutActualizarGruposMutation,
} from "./store";
import Loader from "../../../../../components/Loader";
import { useParams, useSearchParams } from "react-router-dom";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import BasicModal from "../../../../../modals/BasicModal";
import { useToast } from "../../../../../hooks/useToast";
import _ from "lodash";
import Authorization from "../../../../../components/Authorization";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";

export function GruposServicio({ isDetalle }) {
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const [grupoServicio, setGrupoServicio] = useState(INITIAL_VALUES);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const { errorToast } = useToast();
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showCancel, setShowCancel] = useState(false);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");
  const [deletedGrupos, setDeletedGrupos] = useState([]);

  const { data: tipoConsumo, isLoading: isLoadingTipoConsumo } =
    useGetTipoConsumoQuery();
  const [actualizarGrupo, { isLoading: isLoadingActualizar }] =
    usePutActualizarGruposMutation();
  const [guardarGrupo, { isLoading: isLoadingGuardar }] =
    usePostGuardarGruposMutation();
  const [borrarGrupos, { isLoading: isLoadingBorrar }] =
    useDeleteGruposMutation();
  const { data: grupoServicioQ, isLoading: isLoadingGrupoServicio } =
    useGetGrupoQuery(idContrato || idNuevoContrato, {
      skip: idContrato || idNuevoContrato !== undefined ? false : true,
    });

  useEffect(() => {
    if (grupoServicioQ) {
      const previousData = rearrangeData(grupoServicioQ);
      setGrupoServicio(previousData);
      setMemoizedData(
        new Map(
          previousData?.gruposServicio?.map((grupo) => [grupo.UUID, grupo])
        )
      );
      setDeletedGrupos([]);
    }
  }, [grupoServicioQ]);

  const onReset = () => {
    if (grupoServicioQ) {
      setGrupoServicio(() => ({
        gruposServicio: grupoServicioQ?.map((grupo) => ({
          grupo: grupo?.grupoServiciosModel?.grupo,
          tipoConsumo: grupo?.tipoConsumo?.primaryKey,
        })),
      }));
    } else {
      setGrupoServicio(() => INITIAL_VALUES);
    }
  };
  const onSubmit = async (values, { resetForm }) => {
    try {
      const modifiedGrupos = [];
      const newGrupos = [];

      values?.gruposServicio?.forEach((grupo) => {
        const existentGrupo = memoizedData.get(grupo.UUID);
        if (!existentGrupo) {
          newGrupos.push(grupo);
          return;
        }
        if (!_.isEqual(grupo, existentGrupo)) {
          modifiedGrupos.push(grupo);
          return;
        }
      });
      if (deletedGrupos.filter((grupo) => grupo !== undefined).length > 0) {
        const res = await borrarGrupos({
          data: deletedGrupos.filter((grupo) => grupo !== undefined),
        }).unwrap();
        if (getMessageExists(res)) {
          setSingleBasicMessage(res);
          setShowSingleBasic(true);
          resetForm();
          return;
        }
      }
      if (newGrupos.length > 0) {
        const data = newGrupos?.map((gpoServicio) => ({
          idContrato: parseInt(idNuevoContrato || idContrato),
          grupo: gpoServicio.grupo,
          idTipoConsumo: parseInt(gpoServicio.tipoConsumo),
        }));
        await guardarGrupo({ data }).unwrap();
      }
      if (modifiedGrupos.length > 0) {
        const data = modifiedGrupos?.map((gpoServicio) => ({
          idContrato: parseInt(idNuevoContrato || idContrato),
          grupo: gpoServicio.grupo,
          idTipoConsumo: parseInt(gpoServicio.tipoConsumo),
          idGrupoServicio: parseInt(gpoServicio.idGrupoServicio),
        }));
        await actualizarGrupo({ data }).unwrap();
      }
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
      resetForm();
    } catch (error) {
      const mensaje = error?.data?.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };

  return (
    <>
      <Formik
        enableReinitialize
        initialValues={grupoServicio}
        onSubmit={onSubmit}
        validationSchema={gruposServicioSchema}
        validateOnMount
      >
        {({
          handleSubmit,
          handleChange,
          errors,
          values,
          touched,
          resetForm,
          isValid,
        }) => {
          return (
            <>
              {isLoadingTipoConsumo ||
              isLoadingActualizar ||
              isLoadingGuardar ||
              isLoadingGrupoServicio ||
              isLoadingBorrar ? (
                <Loader />
              ) : null}
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <FieldArray
                  name="gruposServicio"
                  render={(arrayHelpers) => {
                    return (
                      <>
                        {values.gruposServicio ? (
                          <>
                            <Row>
                              <Col md={4}>
                                <TextField
                                  name={`gruposServicio.${0}.grupo`}
                                  value={values?.gruposServicio[0]?.grupo}
                                  label={"Grupo*:"}
                                  onChange={handleChange}
                                  helperText={
                                    touched?.gruposServicio?.[0]?.grupo
                                      ? errors?.gruposServicio?.[0]?.grupo
                                      : ""
                                  }
                                  className={
                                    touched?.gruposServicio?.[0]?.grupo &&
                                    (errors?.gruposServicio?.[0]?.grupo
                                      ? "is-invalid"
                                      : "is-valid")
                                  }
                                  disabled={isDetalle}
                                />
                              </Col>
                              <Col md={6}>
                                <Row className="align-items-center">
                                  <Col>
                                    <Select
                                      options={tipoConsumo}
                                      keyValue={"primaryKey"}
                                      keyTextValue={"nombre"}
                                      name={`gruposServicio.${0}.tipoConsumo`}
                                      value={
                                        values?.gruposServicio[0]?.tipoConsumo
                                      }
                                      label={"Tipo de consumo*:"}
                                      onChange={handleChange}
                                      helperText={
                                        touched?.gruposServicio?.[0]
                                          ?.tipoConsumo
                                          ? errors?.gruposServicio?.[0]
                                              ?.tipoConsumo
                                          : ""
                                      }
                                      className={
                                        touched?.gruposServicio?.[0]
                                          ?.tipoConsumo &&
                                        (errors?.gruposServicio?.[0]
                                          ?.tipoConsumo
                                          ? "is-invalid"
                                          : "is-valid")
                                      }
                                      disabled={isDetalle}
                                    />
                                  </Col>
                                  <Col>
                                    <Authorization process={"CONT_GSC_ADMIN"}>
                                      <IconButton
                                        type={"add"}
                                        onClick={() => {
                                          arrayHelpers.push({
                                            grupo: null,
                                            tipoConsumo: null,
                                          });
                                        }}
                                        disabled={isDetalle}
                                      />
                                      {/* <IconButton
                                        type={"delete"}
                                        onClick={() => {
                                          setDeletedGrupos((prev) => [
                                            ...prev,
                                            values.gruposServicio[0]
                                              ?.idGrupoServicio,
                                          ]);
                                          arrayHelpers.remove(0);
                                        }}
                                        disabled={isDetalle}
                                      /> */}
                                    </Authorization>
                                  </Col>
                                </Row>
                              </Col>
                            </Row>
                          </>
                        ) : null}
                        {values.gruposServicio
                          ? values.gruposServicio?.map((grupo, index) => {
                              if (index === 0) return <></>;
                              return (
                                <>
                                  <Row>
                                    <Col md={4}>
                                      <TextField
                                        name={`gruposServicio.${index}.grupo`}
                                        value={grupo.grupo}
                                        onChange={handleChange}
                                        helperText={
                                          touched?.gruposServicio?.[index]
                                            ?.grupo
                                            ? errors?.gruposServicio?.[index]
                                                ?.grupo
                                            : ""
                                        }
                                        className={
                                          touched?.gruposServicio?.[index]
                                            ?.grupo &&
                                          (errors?.gruposServicio?.[index]
                                            ?.grupo
                                            ? "is-invalid"
                                            : "is-valid")
                                        }
                                        disabled={isDetalle}
                                      />
                                    </Col>
                                    <Col md={6}>
                                      <Row className="align-items-center">
                                        <Col>
                                          <Select
                                            options={tipoConsumo}
                                            keyValue={"primaryKey"}
                                            keyTextValue={"nombre"}
                                            name={`gruposServicio.${index}.tipoConsumo`}
                                            value={grupo.tipoConsumo}
                                            onChange={handleChange}
                                            helperText={
                                              touched?.gruposServicio?.[index]
                                                ?.tipoConsumo
                                                ? errors?.gruposServicio?.[
                                                    index
                                                  ]?.tipoConsumo
                                                : ""
                                            }
                                            className={
                                              touched?.gruposServicio?.[index]
                                                ?.tipoConsumo &&
                                              (errors?.gruposServicio?.[index]
                                                ?.tipoConsumo
                                                ? "is-invalid"
                                                : "is-valid")
                                            }
                                            disabled={isDetalle}
                                          />
                                        </Col>
                                        <Col>
                                          <Authorization
                                            process={"CONT_GSC_ADMIN"}
                                          >
                                            <IconButton
                                              type={"delete"}
                                              onClick={() => {
                                                setDeletedGrupos((prev) => [
                                                  ...prev,
                                                  grupo?.idGrupoServicio,
                                                ]);
                                                arrayHelpers.remove(index);
                                              }}
                                              disabled={isDetalle}
                                            />
                                          </Authorization>
                                        </Col>
                                      </Row>
                                    </Col>
                                  </Row>
                                </>
                              );
                            })
                          : null}
                      </>
                    );
                  }}
                />
                <Row>
                  <Col md={12} className="text-end">
                    {!isDetalle ? (
                      <>
                        <Authorization process={"CONT_GSC_ADMIN"}>
                          <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => {
                              setShowCancel(true);
                            }}
                          >
                            Cancelar
                          </Button>
                          <Button
                            type="submit"
                            variant="green"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => {
                              !isValid && errorToast(MODIFICAR_CONTRATO.MSG001);
                            }}
                          >
                            Guardar
                          </Button>
                        </Authorization>
                      </>
                    ) : null}
                  </Col>
                </Row>
                <BasicModal
                  size={"md"}
                  denyText={"No"}
                  approveText={"Sí"}
                  title={"Mensaje"}
                  show={showCancel}
                  handleDeny={() => setShowCancel(false)}
                  onHide={() => setShowCancel(false)}
                  handleApprove={() => {
                    resetForm();
                    onReset();
                    setShowCancel(false);
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
        show={showSingleBasic}
        onHide={() => {
          setShowSingleBasic(false);
        }}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
