import { FieldArray, Formik } from "formik";
import React, { useEffect, useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import TextDisplay from "../../../../../../../components/LabelValue";
import { Select } from "../../../../../../../components/formInputs";
import IconButton from "../../../../../../../components/buttons/IconButton";
import SingleBasicModal from "../../../../../../../modals/SingleBasicModal";
import { useParams, useSearchParams } from "react-router-dom";
import { useErrorMessages } from "../../../../../../../hooks/useErrorMessages";
import { MODIFICAR_CONTRATO } from "../../../../../../../constants/messages";
import { INITIAL_VALUES, rearrange, plantillaSchema } from "./utils";
import {
  useGetAsociacionesConvenioQuery,
  useGetPlantillasConvenioQuery,
  usePostAsociarPlantillaConvenioMutation,
  usePutEliminarAsociacionesConvenioMutation,
} from "./store";
import Loader from "../../../../../../../components/Loader";
import _, { isString } from "lodash";
import BasicModal from "../../../../../../../modals/BasicModal";
import Authorization from "../../../../../../../components/Authorization";
import { useGetAuthorization } from "../../../../../../../hooks/useGetAuthorization";
import { useToast } from "../../../../../../../hooks/useToast";

export function AsignacionPlantilla({ isDetalle }) {
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [deletedAsociaciones, setDeletedAsociaciones] = useState([]);
  const { idConvenio } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoConvenio = searchParams.get("cm");
  const { errorToast } = useToast();
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [showCancelar, setShowCancelar] = useState(false);
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const [asignacionPlantilla, setAsignacionPlantilla] =
    useState(INITIAL_VALUES);
  const [eliminarAsociaciones, { isLoading: isLoadingEliminar }] =
    usePutEliminarAsociacionesConvenioMutation();
  const [asociarPlantillas, { isLoading: isLoadingAsociar }] =
    usePostAsociarPlantillaConvenioMutation();
  const { data: plantillas, isLoading: isLoadingPlantillas } =
    useGetPlantillasConvenioQuery();
  const { data: asociaciones, isLoading: isLoadingAsociaciones } =
    useGetAsociacionesConvenioQuery(idConvenio || idNuevoConvenio, {
      skip:
        idConvenio !== undefined || idNuevoConvenio !== undefined
          ? false
          : true,
    });
  const { isAuthorized } = useGetAuthorization("CONT_CM_ASIG_PLANT");
  const disabled = isDetalle || !isAuthorized;

  useEffect(() => {
    if (asociaciones) {
      const prevAsociaciones = rearrange(asociaciones);
      setAsignacionPlantilla(prevAsociaciones);
      setMemoizedData(
        new Map(
          prevAsociaciones.plantillas?.map((asociacion) => [
            asociacion?.UUID,
            asociacion,
          ])
        )
      );
      setDeletedAsociaciones([]);
    }
  }, [asociaciones]);

  const handleReset = () => {
    if (asociaciones) {
      const prevAsociaciones = rearrange(asociaciones);
      setAsignacionPlantilla(prevAsociaciones);
      setMemoizedData(
        new Map(
          prevAsociaciones.plantillas?.map((asociacion) => [
            asociacion?.UUID,
            asociacion,
          ])
        )
      );
      setDeletedAsociaciones([]);
    } else {
      setAsignacionPlantilla(INITIAL_VALUES);
      setDeletedAsociaciones([]);
      setMemoizedData(new Map());
    }
  };

  const handleSubmit = async (values, { resetForm }) => {
    try {
      const newAsociaciones = [];
      const modifiedAsociaciones = [];
      if (deletedAsociaciones.filter((aE) => aE !== null).length > 0) {
        await eliminarAsociaciones({
          data: deletedAsociaciones.filter((aE) => aE !== null),
        }).unwrap();
      }
      values.plantillas.forEach((el) => {
        if (el?.idConvenioPlantilla !== null || memoizedData.get(el.UUID)) {
          if (!_.isEqual(memoizedData.get(el.UUID), el)) {
            modifiedAsociaciones.push(
              asociarPlantillas({
                data: {
                  idPlantillaVigente: parseInt(el.plantilla),
                  idConvenioPlantilla: parseInt(el?.idConvenioPlantilla),
                },
                id: idConvenio || idNuevoConvenio,
              }).unwrap()
            );
          }
        } else {
          newAsociaciones.push(
            asociarPlantillas({
              data: {
                idPlantillaVigente: parseInt(el.plantilla),
              },
              id: idConvenio || idNuevoConvenio,
            }).unwrap()
          );
        }
      });
      await Promise.all(modifiedAsociaciones);
      await Promise.all(newAsociaciones);
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
      resetForm();
    } catch (error) {
      if (getMessageExists(error.data.mensaje[0])) {
        setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage(MODIFICAR_CONTRATO.MSG006);
        setShowSingleBasic(true);
      }
    }
  };
  return (
    <>
      <Formik
        initialValues={asignacionPlantilla}
        enableReinitialize
        onSubmit={handleSubmit}
        validationSchema={plantillaSchema}
        validateOnMount
      >
        {({
          handleChange,
          handleBlur,
          resetForm,
          handleSubmit,
          setFieldValue,
          values,
          errors,
          touched,
          isValid,
        }) => {
          return (
            <>
              {isLoadingEliminar ||
              isLoadingAsociar ||
              isLoadingPlantillas ||
              isLoadingAsociaciones ? (
                <Loader />
              ) : null}
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <FieldArray
                  name="plantillas"
                  render={(arrayHelpers) => {
                    return (
                      <>
                        <Row>
                          <Col md={4}>
                            <TextDisplay label={"Asignar plantilla:"} />
                          </Col>
                        </Row>
                        <Row>
                          <Col md={4}>
                            <Row>
                              <Col md={10}>
                                <Select
                                  disabled={disabled}
                                  name={"plantillas.0.plantilla"}
                                  options={plantillas}
                                  keyValue={"idPlantillaVigente"}
                                  keyTextValue={"nombre"}
                                  value={values?.plantillas?.[0]?.plantilla}
                                  onChange={handleChange}
                                  helperText={
                                    touched.plantillas?.[0]?.plantilla
                                      ? errors.plantillas?.[0]?.plantilla
                                      : ""
                                  }
                                  className={
                                    touched.plantillas?.[0]?.plantilla &&
                                    (errors.plantillas?.[0]?.plantilla
                                      ? "is-invalid"
                                      : "is-valid")
                                  }
                                />
                              </Col>
                              <Col md={2} className="d-flex">
                                <Authorization process={"CONT_CM_ASIG_PLANT"}>
                                  <IconButton
                                    disabled={disabled}
                                    type={"add"}
                                    onClick={() => {
                                      arrayHelpers.push({
                                        plantilla: "",
                                        idConvenioPlantilla: null,
                                        UUID: null,
                                      });
                                    }}
                                  />
                                </Authorization>
                              </Col>
                            </Row>
                          </Col>
                        </Row>
                        {values.plantillas && values.plantillas.length > 1
                          ? values.plantillas.map((plantilla, index) => {
                              if (index === 0) return null;
                              return (
                                <>
                                  <Row>
                                    <Col md={4}>
                                      <Row>
                                        <Col md={10}>
                                          <Select
                                            disabled={disabled}
                                            name={`plantillas.${index}.plantilla`}
                                            options={plantillas}
                                            keyValue={"idPlantillaVigente"}
                                            keyTextValue={"nombre"}
                                            value={plantilla.plantilla}
                                            onChange={handleChange}
                                            helperText={
                                              touched.plantillas?.[index]
                                                ?.plantilla
                                                ? errors.plantillas?.[index]
                                                    ?.plantilla
                                                : ""
                                            }
                                            className={
                                              touched.plantillas?.[index]
                                                ?.plantilla &&
                                              (errors.plantillas?.[index]
                                                ?.plantilla
                                                ? "is-invalid"
                                                : "is-valid")
                                            }
                                          />
                                        </Col>
                                        <Col md={2} className="d-flex">
                                          <Authorization
                                            process={"CONT_CM_ASIG_PLANT"}
                                          >
                                            <IconButton
                                              disabled={disabled}
                                              type={"delete"}
                                              onClick={() => {
                                                console.log(plantilla);
                                                setDeletedAsociaciones(
                                                  (prev) => [
                                                    ...prev,
                                                    plantilla.idConvenioPlantilla,
                                                  ]
                                                );
                                                arrayHelpers.remove(index);
                                              }}
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
                    {!disabled ? (
                      <>
                        <Authorization process={"CONT_CM_ASIG_PLANT"}>
                          <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => setShowCancelar(true)}
                          >
                            Cancelar
                          </Button>
                          <Button
                            variant="green"
                            className="btn-sm ms-2 waves-effect waves-light"
                            type="submit"
                            onClick={() => {
                              const { plantillas } = { ...errors };
                              if (!isValid) {
                                isString(plantillas)
                                  ? errorToast(plantillas)
                                  : errorToast(MODIFICAR_CONTRATO.MSG001);
                              }
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
                  title={"Mensaje"}
                  size={"md"}
                  approveText={"Sí"}
                  denyText={"No"}
                  show={showCancelar}
                  onHide={() => setShowCancelar(false)}
                  handleDeny={() => setShowCancelar(false)}
                  handleApprove={() => {
                    handleReset();
                    resetForm();
                    setShowCancelar(false);
                  }}
                >
                  {"¿Desea cancelar? Los cambios modificados se perderán."}
                </BasicModal>
              </Form>
            </>
          );
        }}
      </Formik>
      <SingleBasicModal
        size={"md"}
        show={showSingleBasic}
        approveText={"Aceptar"}
        title={"Mensaje"}
        onHide={() => {
          setShowSingleBasic(false);
        }}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
