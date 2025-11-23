import React, { useEffect, useState } from "react";
import { Col, Row, Button, Form } from "react-bootstrap";
import TextDisplay from "../../../../../components/LabelValue";
import Select from "../../../../../components/formInputs/Select";
import IconButton from "../../../../../components/buttons/IconButton";
import { FieldArray, Formik } from "formik";
import { INITIAL_VALUES, plantillaSchema, rearrange } from "./utils";
import { useParams, useSearchParams } from "react-router-dom";
import {
  useGetAsociacionesContratoQuery,
  useGetPlantillasQuery,
  usePostAsociarPlantillaMutation,
  usePutEliminarAsociacionesMutation,
} from "./store";
import Loader from "../../../../../components/Loader";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import _, { isString } from "lodash";
import BasicModal from "../../../../../modals/BasicModal";
import Authorization from "../../../../../components/Authorization";
import { useToast } from "../../../../../hooks/useToast";

export function AsignacionPlantilla({ isDetalle }) {
  const { idContrato } = useParams();
  const { errorToast } = useToast();
  const [searchParams] = useSearchParams();
  const [memoizedData, setMemoizedData] = useState(new Map());
  const idNuevoContrato = searchParams.get("q");
  const [asociacionesEliminadas, setAsociacionesEliminadas] = useState([]);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [showResetModal, setShowResetModal] = useState(false);
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const [asignacionPlantilla, setAsignacionPlantilla] =
    useState(INITIAL_VALUES);
  const [eliminarAsociaciones, { isLoading: isLoadingEliminar }] =
    usePutEliminarAsociacionesMutation();
  const [asociarPlantillas, { isLoading: isLoadingAsociar }] =
    usePostAsociarPlantillaMutation();
  const { data: plantillas, isLoading: isLoadingPlantillas } =
    useGetPlantillasQuery();
  const { data: asociaciones, isLoading: isLoadingAsociaciones } =
    useGetAsociacionesContratoQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });

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
      setAsociacionesEliminadas([]);
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
      setAsociacionesEliminadas([]);
    } else {
      setAsignacionPlantilla(INITIAL_VALUES);
      setMemoizedData(new Map());
      setAsociacionesEliminadas([]);
    }
  };

  const handleSubmit = async (values, { resetForm }) => {
    try {
      const newAsociaciones = [];
      const modifiedAsociaciones = [];

      values.plantillas.forEach((el) => {
        if (el?.idContratoPlantilla !== null || memoizedData.get(el.UUID)) {
          if (!_.isEqual(memoizedData.get(el.UUID), el)) {
            modifiedAsociaciones.push(
              asociarPlantillas({
                data: {
                  idPlantillaVigente: parseInt(el.plantilla),
                  idContratoPlantilla: parseInt(el?.idContratoPlantilla),
                },
                id: idContrato || idNuevoContrato,
              }).unwrap()
            );
          }
        } else {
          newAsociaciones.push(
            asociarPlantillas({
              data: {
                idPlantillaVigente: parseInt(el.plantilla),
              },
              id: idContrato || idNuevoContrato,
            }).unwrap()
          );
        }
      });

      if (asociacionesEliminadas.filter((aE) => aE !== null).length > 0) {
        await eliminarAsociaciones({
          data: asociacionesEliminadas.filter((aE) => aE !== null),
        }).unwrap();
      }
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
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };
  return (
    <>
      {isLoadingPlantillas ||
      isLoadingEliminar ||
      isLoadingAsociar ||
      isLoadingAsociaciones ? (
        <Loader />
      ) : null}
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
          values,
          errors,
          touched,
          isValid,
        }) => {
          return (
            <>
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
                                  name={"plantillas.0.plantilla"}
                                  options={plantillas}
                                  keyValue={"idPlantillaVigente"}
                                  keyTextValue={"nombre"}
                                  value={values?.plantillas?.[0]?.plantilla}
                                  onChange={handleChange}
                                  disabled={isDetalle}
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
                                <Authorization process={"CONT_AP_ADMIN"}>
                                  <IconButton
                                    type={"add"}
                                    onClick={() => {
                                      arrayHelpers.push({
                                        plantilla: "",
                                        idContratoPlantilla: null,
                                      });
                                    }}
                                    disabled={isDetalle}
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
                                            name={`plantillas.${index}.plantilla`}
                                            options={plantillas}
                                            keyValue={"idPlantillaVigente"}
                                            keyTextValue={"nombre"}
                                            value={plantilla.plantilla}
                                            onChange={handleChange}
                                            disabled={isDetalle}
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
                                            process={"CONT_AP_ADMIN"}
                                          >
                                            <IconButton
                                              type={"delete"}
                                              onClick={() => {
                                                setAsociacionesEliminadas(
                                                  (prev) => [
                                                    ...prev,
                                                    plantilla.idContratoPlantilla,
                                                  ]
                                                );
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
                        <Authorization process={"CONT_AP_ADMIN"}>
                          <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => setShowResetModal(true)}
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
              </Form>
              <BasicModal
                show={showResetModal}
                onHide={() => setShowResetModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                title={"Mensaje"}
                handleDeny={() => setShowResetModal(false)}
                handleApprove={() => {
                  resetForm();
                  handleReset();
                  setShowResetModal(false);
                }}
              >
                {MODIFICAR_CONTRATO.MSG002}
              </BasicModal>
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
