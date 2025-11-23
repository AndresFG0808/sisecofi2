import { FieldArray, Formik } from "formik";
import React, { useEffect, useState } from "react";
import { Form, Col, Row, Button } from "react-bootstrap";
import TextField from "../../../../../components/formInputs/TextField";
import Select from "../../../../../components/formInputs/Select";
import LabelValue from "../../../../../components/LabelValue";
import TextArea from "../../../../../components/formInputs/TextArea";
import {
  INITIAL_VALUES,
  datosGeneralesSchema,
  rearrangeResponse,
} from "./utils";
import IconButton from "../../../../../components/buttons/IconButton";
import { Participantes } from "./components";
import {
  useGetConvenioColaboracionQuery,
  useGetDatosGeneralesQuery,
  useGetDominioTecnologicoQuery,
  useGetFondeoContratoQuery,
  useGetProveedorQuery,
  useGetTipoProcedimientoQuery,
  usePostDatosGeneralesMutation,
  usePutDatosGeneralesMutation,
} from "./store";
import { Loader } from "../../../../../components";
import { useParams, useSearchParams } from "react-router-dom";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import BasicModal from "../../../../../modals/BasicModal";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import { useToast } from "../../../../../hooks/useToast";
import _, { isString } from "lodash";
import Authorization from "../../../../../components/Authorization";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";

export function DatosGenerales({ isDetalle }) {
  const [datosGenerales, setDatosGenerales] = useState(INITIAL_VALUES);
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams({ q: "" });
  const idNuevoContrato = searchParams.get("q");
  const [showCancel, setShowCancel] = useState(false);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [previousProveedores, setPreviousProveedores] = useState(new Map());

  const { data: dominioTecnologico, isLoading: isLoadingDominioTecnologico } =
    useGetDominioTecnologicoQuery();
  const { data: proveedores, isLoading: isLoadingProveedor } =
    useGetProveedorQuery();
  const { data: fondeoContrato, isLoading: isLoadingFondeoContrato } =
    useGetFondeoContratoQuery();
  const { data: tipoProcedimiento, isLoading: isLoadingTipoProcedimiento } =
    useGetTipoProcedimientoQuery();
  const {
    data: convenioColaboracion,
    isLoading: isLoadingConvenioColaboracion,
  } = useGetConvenioColaboracionQuery();
  const [guardarDatosGenerales, { isLoading: isLoadingDatosGuardados }] =
    usePostDatosGeneralesMutation();
  const [actualizarDatosGenerales, { isLoading: isLoadingActualizar }] =
    usePutDatosGeneralesMutation();

  const { data: datosGeneralesQ, isFetching: isLoadingDatosGenerales } =
    useGetDatosGeneralesQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });

  useEffect(() => {
    if (datosGeneralesQ) {
      setDatosGenerales(() => rearrangeResponse(datosGeneralesQ));
      setPreviousProveedores(
        new Map(
          datosGeneralesQ.proveedores.map((proveedor) => [
            proveedor.idProveedor,
            true,
          ])
        )
      );
    }
  }, [datosGeneralesQ]);

  const onReset = () => {
    if (datosGeneralesQ) {
      setDatosGenerales(() => rearrangeResponse(datosGeneralesQ));
      setPreviousProveedores(
        new Map(
          datosGeneralesQ.proveedores.map((proveedor) => [
            proveedor.idProveedor,
            true,
          ])
        )
      );
    } else {
      setDatosGenerales(() => INITIAL_VALUES);
      setPreviousProveedores(new Map());
    }
  };
  const handleProveedor = (event, setFieldValue, rfcId) => {
    const { value, id } = event.target;
    if (value) {
      const rfc = proveedores?.find(
        (proveedor) => proveedor.idProveedor === parseInt(value)
      )?.rfc;

      setFieldValue(id, value);
      setFieldValue(rfcId, rfc);
    } else {
      setFieldValue(id, value);
      setFieldValue(rfcId, value);
    }
  };

  const onSubmit = async (values, { resetForm }) => {
    try {
      const newIds = [];
      values.proveedores.forEach((proveedor) => {
        if (!previousProveedores.get(parseInt(proveedor.id))) {
          newIds.push(parseInt(proveedor.id));
        }
      });
      const data = {
        idContrato: parseInt(idContrato || idNuevoContrato),
        numeroContrato: values.numeroContrato,
        numeroContratoCompraNet: values.numeroContratoCompraNet,
        acuerdo: values.acuerdo,
        idsProveedores: newIds,
        idTipoProcedimiento: parseInt(values.tipoProcedimiento),
        numeroProcedimiento: values.numeroProcedimiento,
        idCatConvenioColaboracion: parseInt(values.convenioColaboracion),
        idDominioTecnologico: parseInt(values.dominioTecnologico),
        idFondeoContrato: parseInt(values.fondeoContrato),
        objetivoServicio: values.objetivoServicio,
        alcanceServicio: values.alcanceServicio,
        titulosServicio: values.tituloServicio,
      };
      if (!datosGeneralesQ) {
        await guardarDatosGenerales({ data }).unwrap();
      } else {
        await actualizarDatosGenerales({
          data: {
            ...data,
            idsProveedoresEliminados: values.proveedoresEliminados,
          },
        }).unwrap();
      }

      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
      resetForm();
    } catch (error) {
      const mensaje = error.data.mensaje[0];
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
        initialValues={datosGenerales}
        onSubmit={onSubmit}
        validationSchema={datosGeneralesSchema}
        validateOnMount
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          setFieldValue,
          errors,
          isValid,
          values,
          touched,
          resetForm,
        }) => (
          <>
            {isLoadingDominioTecnologico ||
            isLoadingProveedor ||
            isLoadingFondeoContrato ||
            isLoadingTipoProcedimiento ||
            isLoadingDatosGuardados ||
            isLoadingDatosGenerales ||
            isLoadingConvenioColaboracion ||
            isLoadingActualizar ? (
              <Loader zIndex={`${isLoadingDatosGenerales ? "10" : "9999"}`} />
            ) : null}
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Row>
                <Col md={4}>
                  <TextField
                    disabled={isDetalle}
                    maxLength={"25"}
                    onChange={(e) => {
                      const inputValue = e.target.value;
                      if (inputValue.length <= 25) {
                        handleChange(e);
                      }
                    }}
                    name={"numeroContrato"}
                    value={values.numeroContrato}
                    label={"Número de contrato*:"}
                    helperText={
                      touched.numeroContrato ? errors.numeroContrato : ""
                    }
                    className={
                      touched.numeroContrato &&
                      (errors.numeroContrato ? "is-invalid" : "is-valid")
                    }
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    disabled={isDetalle}
                    onChange={handleChange}
                    name={"numeroContratoCompraNet"}
                    value={values.numeroContratoCompraNet}
                    label={"Número de contrato CompraNet:"}
                    helperText={
                      touched.numeroContratoCompraNet
                        ? errors.numeroContratoCompraNet
                        : ""
                    }
                    className={
                      touched.numeroContratoCompraNet &&
                      (errors.numeroContratoCompraNet
                        ? "is-invalid"
                        : "is-valid")
                    }
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    disabled={isDetalle}
                    onChange={handleChange}
                    name={"acuerdo"}
                    value={values.acuerdo}
                    label={"Acuerdo:"}
                  />
                </Col>
              </Row>

              <FieldArray
                name="proveedores"
                render={(arrayHelpers) => {
                  return (
                    <>
                      {values.proveedores ? (
                        <>
                          <Row>
                            <Col md={4}>
                              <Row className="align-items-center">
                                <Col md={12}>
                                  <Select
                                    onChange={(e) => {
                                      handleProveedor(
                                        e,
                                        setFieldValue,
                                        "proveedores.0.rfc"
                                      );
                                    }}
                                    name={"proveedores.0.id"}
                                    options={proveedores}
                                    keyValue={"idProveedor"}
                                    keyTextValue={"nombreProveedor"}
                                    value={values?.proveedores?.[0]?.id}
                                    label={"Proveedor*:"}
                                    helperText={
                                      touched?.proveedores?.[0]?.id
                                        ? errors.proveedores?.[0]?.id
                                        : ""
                                    }
                                    className={
                                      touched.proveedores?.[0]?.id &&
                                      (errors.proveedores?.[0]?.id
                                        ? "is-invalid"
                                        : "is-valid")
                                    }
                                    disabled={isDetalle}
                                  />
                                </Col>
                              </Row>
                            </Col>
                            <Col md={4}>
                              <Row className="align-items-center">
                                <Col md={5}>
                                  <LabelValue
                                    name={"proveedores.0.rfc"}
                                    label={"RFC"}
                                    value={values?.proveedores?.[0]?.rfc}
                                  />
                                </Col>
                                <Col md={7} className=" text-start pt-2">
                                  <Row>
                                    <Col md={2}>
                                      <Authorization process={"CONT_DG_ADMIN"}>
                                        <IconButton
                                          type={"add"}
                                          onClick={() => {
                                            arrayHelpers.push({ id: null });
                                          }}
                                          disabled={isDetalle}
                                        />
                                      </Authorization>
                                    </Col>
                                    <Col md={2}>
                                      {values.proveedores.length > 1 ? (
                                        <Authorization
                                          process={"CONT_DG_ADMIN"}
                                        >
                                          <IconButton
                                            type={"delete"}
                                            onClick={() => {
                                              if (datosGeneralesQ) {
                                                setFieldValue(
                                                  "proveedoresEliminados",
                                                  [
                                                    ...values?.proveedoresEliminados,
                                                    values?.proveedores?.[0]
                                                      ?.id ??
                                                      parseInt(
                                                        values?.proveedores?.[0]
                                                          ?.id
                                                      ),
                                                  ]
                                                );
                                              }
                                              arrayHelpers.remove(0);
                                            }}
                                            disabled={isDetalle}
                                          />
                                        </Authorization>
                                      ) : null}
                                    </Col>
                                  </Row>
                                </Col>
                              </Row>
                            </Col>
                          </Row>
                        </>
                      ) : null}
                      {values.proveedores && values?.proveedores.length > 1
                        ? values?.proveedores?.map((proveedor, index) => {
                            if (index === 0) return <></>;
                            return (
                              <>
                                <Row>
                                  <Col md={4}>
                                    <Row className="align-items-center">
                                      <Col md={12}>
                                        <Select
                                          onChange={(e) => {
                                            handleProveedor(
                                              e,
                                              setFieldValue,
                                              `proveedores.${index}.rfc`
                                            );
                                          }}
                                          name={`proveedores.${index}.id`}
                                          options={proveedores}
                                          keyValue={"idProveedor"}
                                          keyTextValue={"nombreProveedor"}
                                          value={proveedor.id}
                                          helperText={
                                            touched?.proveedores?.[index]?.id
                                              ? errors?.proveedores?.[index]?.id
                                              : ""
                                          }
                                          className={
                                            touched?.proveedores?.[index]?.id &&
                                            (errors?.proveedores?.[index]?.id
                                              ? "is-invalid"
                                              : "is-valid")
                                          }
                                          disabled={isDetalle}
                                        />
                                      </Col>
                                    </Row>
                                  </Col>
                                  <Col md={4}>
                                    <Row className="align-items-center">
                                      <Col md={5}>
                                        <LabelValue
                                          name={`proveedores.${index}.rfc`}
                                          value={
                                            values?.proveedores?.[index]?.rfc
                                          }
                                        />
                                      </Col>
                                      <Col md={7} className="text-start pb-2">
                                        <Authorization
                                          process={"CONT_DG_ADMIN"}
                                        >
                                          <IconButton
                                            type={"delete"}
                                            onClick={() => {
                                              if (datosGeneralesQ) {
                                                setFieldValue(
                                                  "proveedoresEliminados",
                                                  [
                                                    ...values?.proveedoresEliminados,
                                                    values?.proveedores?.[index]
                                                      ?.id ??
                                                      parseInt(
                                                        values?.proveedores?.[
                                                          index
                                                        ]?.id
                                                      ),
                                                  ]
                                                );
                                              }
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
                <Col md={4}>
                  <Select
                    onChange={handleChange}
                    name={"tipoProcedimiento"}
                    value={values.tipoProcedimiento}
                    label={"Tipo de procedimiento*:"}
                    options={tipoProcedimiento}
                    keyValue={"primaryKey"}
                    keyTextValue={"nombre"}
                    helperText={
                      touched.tipoProcedimiento ? errors.tipoProcedimiento : ""
                    }
                    className={
                      touched.tipoProcedimiento &&
                      (errors.tipoProcedimiento ? "is-invalid" : "is-valid")
                    }
                    disabled={isDetalle}
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    onChange={handleChange}
                    name={"numeroProcedimiento"}
                    value={values.numeroProcedimiento}
                    label={"Número de procedimiento*:"}
                    helperText={
                      touched.numeroProcedimiento
                        ? errors.numeroProcedimiento
                        : ""
                    }
                    className={
                      touched.numeroProcedimiento &&
                      (errors.numeroProcedimiento ? "is-invalid" : "is-valid")
                    }
                    disabled={isDetalle}
                  />
                </Col>
                <Col md={4}>
                  <Select
                    onChange={handleChange}
                    name={"convenioColaboracion"}
                    value={values.convenioColaboracion}
                    label={"Convenio de colaboración:"}
                    keyValue={"primaryKey"}
                    keyTextValue={"nombre"}
                    options={convenioColaboracion}
                    disabled={isDetalle}
                  />
                </Col>
              </Row>
              <Row>
                <Col md={4}>
                  <Select
                    disabled={isDetalle}
                    onChange={handleChange}
                    name={"dominioTecnologico"}
                    value={values.dominioTecnologico}
                    label={"Dominio tecnológico:"}
                    options={dominioTecnologico}
                    keyValue={"primaryKey"}
                    keyTextValue={"nombre"}
                  />
                </Col>
                <Col md={4}>
                  <Select
                    disabled={isDetalle}
                    onChange={handleChange}
                    name={"fondeoContrato"}
                    value={values.fondeoContrato}
                    label={"Fondeo del contrato:"}
                    options={fondeoContrato}
                    keyValue={"primaryKey"}
                    keyTextValue={"nombre"}
                    helperText={
                      touched.fondeoContrato ? errors.fondeoContrato : ""
                    }
                    className={
                      touched.fondeoContrato &&
                      (errors.fondeoContrato ? "is-invalid" : "is-valid")
                    }
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    onChange={handleChange}
                    name={"tituloServicio"}
                    label={"Título de servicio*:"}
                    value={values.tituloServicio}
                    helperText={
                      touched.tituloServicio ? errors.tituloServicio : ""
                    }
                    className={
                      touched.tituloServicio &&
                      (errors.tituloServicio ? "is-invalid" : "is-valid")
                    }
                    disabled={isDetalle}
                  />
                </Col>
              </Row>
              <Row>
                <Col md={12}>
                  <TextArea
                    onChange={handleChange}
                    name={"objetivoServicio"}
                    value={values.objetivoServicio}
                    label={"Objetivo del servicio*:"}
                    helperText={
                      touched.objetivoServicio ? errors.objetivoServicio : ""
                    }
                    className={
                      touched.objetivoServicio &&
                      (errors.objetivoServicio ? "is-invalid" : "is-valid")
                    }
                    disabled={isDetalle}
                  />
                </Col>
              </Row>
              <Row>
                <Col md={12}>
                  <TextArea
                    onChange={handleChange}
                    name={"alcanceServicio"}
                    value={values.alcanceServicio}
                    label={"Alcance del servicio*:"}
                    helperText={
                      touched.alcanceServicio ? errors.alcanceServicio : ""
                    }
                    className={
                      touched.alcanceServicio &&
                      (errors.alcanceServicio ? "is-invalid" : "is-valid")
                    }
                    disabled={isDetalle}
                  />
                </Col>
              </Row>
              <Row>
                <Col md={12} className="text-end">
                  <Authorization process={"CONT_DG_ADMIN"}>
                    {!isDetalle ? (
                      <>
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
                          variant="green"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                          onClick={() => {
                            const { proveedores } = { ...errors };
                            if (!isValid) {
                              isString(proveedores)
                                ? errorToast(proveedores)
                                : errorToast(MODIFICAR_CONTRATO.MSG001);
                            }
                          }}
                        >
                          Guardar
                        </Button>
                      </>
                    ) : null}
                  </Authorization>
                </Col>
              </Row>
            </Form>
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
          </>
        )}
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
      <Participantes isDetalle={isDetalle} />
    </>
  );
}
