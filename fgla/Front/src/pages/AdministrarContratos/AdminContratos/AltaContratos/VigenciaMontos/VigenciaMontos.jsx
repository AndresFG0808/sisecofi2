import { Formik } from "formik";
import React, { useEffect, useState } from "react";
import { Col, Form, Row, Button } from "react-bootstrap";
import TextFieldDate from "../../../../../components/formInputs/TextFieldDate";
import Select from "../../../../../components/formInputs/Select";
import TextFieldIcon from "../../../../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TextField from "../../../../../components/formInputs/TextField";
import TextDisplay from "../../../../../components/LabelValue";
import FormCheck from "../../../../../components/formInputs/FormCheck";
import {
  INITIAL_VALUES,
  calculateDifferenceDays,
  rearrangeVigenciaMontos,
  vigenciaMontosSchema,
  filtrarNumeros,
} from "./utils";
import {
  useGetPorcentajeIepsQuery,
  useGetPorcentajeIvaQuery,
  useGetTipoMonedaQuery,
  usePostVigenciaMontosMutation,
  useGetVigenciaMontosQuery,
  usePutVigenciaMontosMutation,
} from "./store";
import Loader from "../../../../../components/Loader";
import moment from "moment";
import { useParams, useSearchParams } from "react-router-dom";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import { useSelector } from "react-redux";
import BasicModal from "../../../../../modals/BasicModal";
import { useToast } from "../../../../../hooks/useToast";
import { FormatMoney, safeParseFloat } from "../../../../../functions/utils";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import "./styles.css";
import Authorization from "../../../../../components/Authorization";

export function VigenciaMontos({ isDetalle }) {
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { errorToast } = useToast();
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");
  const { contrato } = useSelector((state) => state.contratos);
  const [vigenciaMontos, setVigenciaMontos] = useState(INITIAL_VALUES);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [showCancel, setShowCancel] = useState(false);

  const { data: tipoMoneda, isLoading: isLoadingTipoMoneda } =
    useGetTipoMonedaQuery();
  const { data: porcentajeIeps, isLoading: isLoadingPorcentajeIeps } =
    useGetPorcentajeIepsQuery();
  const { data: porcentajeIva, isLoading: isLoadingPorcentajeIva } =
    useGetPorcentajeIvaQuery();

  const [guardarVigenciaMontos, { isLoading: isLoadingGuardarVigencia }] =
    usePostVigenciaMontosMutation();

  const [actualizarVigencia, { isLoading: isLoadingActualizar }] =
    usePutVigenciaMontosMutation();

  const { data: vigenciaMontosQ, isFetching: isLoadingVigenciaMontos } =
    useGetVigenciaMontosQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });
  useEffect(() => {
    if (vigenciaMontosQ) {
      setVigenciaMontos(() => rearrangeVigenciaMontos(vigenciaMontosQ));
    }
  }, [vigenciaMontosQ]);

  const onReset = () => {
    if (vigenciaMontosQ) {
      setVigenciaMontos(() => rearrangeVigenciaMontos(vigenciaMontosQ));
    } else {
      setVigenciaMontos(() => INITIAL_VALUES);
    }
  };

  const handleBlurMonto = (event, setFieldValue, values) => {
    const { value, name } = event.target;
    if (value) {
      const formatMoney = FormatMoney(value, 2);
      setFieldValue(name, formatMoney);
    }
  };

  const handleBlurTipoCambio = (event, setFieldValue, values) => {
    const { value, name } = event.target;
    setFieldValue(name, FormatMoney(value, 4));
  };

  const handleChangeMonto = (event, setFieldValue, values) => {
    const { value, name } = event.target;
    setFieldValue(name, filtrarNumeros(value));
  };

  const handleChangeMXN = (event, setFieldValue, values) => {
    const { value, name } = event.target;
    setFieldValue(name, value);
    if (
      tipoMoneda?.find((moneda) => moneda?.primaryKey === parseInt(value))
        ?.primaryKey === 1
    ) {
      setFieldValue("tipoCambioMaximo", "");
    }
  };

  const onSubmit = async (values, { resetForm }) => {
    try {
      const data = {
        idContrato: parseInt(idNuevoContrato || idContrato),
        fechaInicioVigenciaServicios: moment(
          values.fechaInicioVigenciaServicios
        ).toISOString(),
        fechaFinVigenciaServicios: moment(
          values.fechaFinVigenciaServicios
        ).toISOString(),
        fechaInicioVigenciaContrato: moment(
          values.fechaInicioVigenciaContrato
        ).toISOString(),
        fechaFinVigenciaContrato: moment(
          values.fechaFinVigenciaContrato
        ).toISOString(),
        idTipoMoneda: parseInt(values.idTipoMoneda),
        ...(values.tipoCambioMaximo !== ""
          ? { tipoCambioMaximo: safeParseFloat(values.tipoCambioMaximo) }
          : {}),
        id_iva: parseInt(values.id_iva),
        idIeps: parseInt(values.idIeps),
        montoMinimoSinImpuestos: safeParseFloat(values.montoMinimoSinImpuestos),
        montoMaximoSinImpuestos: safeParseFloat(values.montoMaximoSinImpuestos),
        montoPesosSinImpuestos: values.tipoCambioMaximo
          ? safeParseFloat(values?.montoMaximoSinImpuestos) *
            safeParseFloat(values?.tipoCambioMaximo)
          : safeParseFloat(values?.montoMaximoSinImpuestos),
        montoMinimoConImpuestos: safeParseFloat(values.montoMinimoConImpuestos),
        montoMaximoConImpuestos: safeParseFloat(values.montoMaximoConImpuestos),
        montoPesosConImpuestos: values.tipoCambioMaximo
          ? safeParseFloat(values?.montoMaximoConImpuestos) *
            safeParseFloat(values?.tipoCambioMaximo)
          : parseInt(values.montoMaximoConImpuestos?.replace(/,/g, "")),
      };
      if (!vigenciaMontosQ) {
        await guardarVigenciaMontos({ data }).unwrap();
      } else {
        await actualizarVigencia({
          data: { ...data, idVigenciaMonto: values.idVigenciaMonto },
        }).unwrap();
      }

      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
      resetForm();
    } catch (error) {
      if (getMessageExists(error?.data?.mensaje[0])) {
        setSingleBasicMessage(error?.data?.mensaje[0]);
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
        initialValues={vigenciaMontos}
        onSubmit={onSubmit}
        validationSchema={vigenciaMontosSchema}
        validateOnMount
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          resetForm,
          setFieldValue,
          errors,
          isValid,
          values,
          touched,
        }) => {
          return (
            <>
              {isLoadingTipoMoneda ||
              isLoadingPorcentajeIeps ||
              isLoadingPorcentajeIva ||
              isLoadingVigenciaMontos ||
              isLoadingGuardarVigencia ||
              isLoadingActualizar ? (
                <Loader zIndex={`${isLoadingVigenciaMontos ? "10" : "9999"}`} />
              ) : null}
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <Row>
                  <Col md={4}>
                    <TextFieldDate
                      name={"fechaInicioVigenciaServicios"}
                      value={values?.fechaInicioVigenciaServicios}
                      onChange={(e) => {
                        handleChange(e);
                      }}
                      onBlur={(e) => {
                        handleBlur(e);
                      }}
                      label={"Fecha de inicio de vigencia de los servicios*:"}
                      helperText={
                        touched.fechaInicioVigenciaServicios
                          ? errors.fechaInicioVigenciaServicios
                          : ""
                      }
                      className={
                        touched.fechaInicioVigenciaServicios &&
                        (errors.fechaInicioVigenciaServicios
                          ? "is-invalid"
                          : "is-valid")
                      }
                      disabled={isDetalle}
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldDate
                      disabled={isDetalle}
                      name={"fechaFinVigenciaServicios"}
                      value={values?.fechaFinVigenciaServicios}
                      onChange={(e) => {
                        handleChange(e);
                      }}
                      onBlur={(e) => {
                        handleBlur(e);
                      }}
                      label={"Fecha de fin de vigencia de los servicios*:"}
                      helperText={
                        touched.fechaFinVigenciaServicios
                          ? errors.fechaFinVigenciaServicios
                          : ""
                      }
                      className={
                        touched.fechaFinVigenciaServicios &&
                        (errors.fechaFinVigenciaServicios
                          ? "is-invalid"
                          : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextDisplay
                      label={"Duración de los servicios:"}
                      value={calculateDifferenceDays(
                        values.fechaInicioVigenciaServicios,
                        values.fechaFinVigenciaServicios
                      )}
                      disabled={isDetalle}
                    />
                  </Col>
                </Row>
                <Row className="upper-row">
                  <Col md={4}>
                    <TextFieldDate
                      disabled={isDetalle}
                      name={"fechaInicioVigenciaContrato"}
                      value={values.fechaInicioVigenciaContrato}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      label={"Fecha de inicio de vigencia del contrato*:"}
                      helperText={
                        touched.fechaInicioVigenciaContrato
                          ? errors.fechaInicioVigenciaContrato
                          : ""
                      }
                      className={
                        touched.fechaInicioVigenciaContrato &&
                        (errors.fechaInicioVigenciaContrato
                          ? "is-invalid"
                          : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldDate
                      name={"fechaFinVigenciaContrato"}
                      value={values.fechaFinVigenciaContrato}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      label={"Fecha de fin de vigencia del contrato*:"}
                      helperText={
                        touched.fechaFinVigenciaContrato
                          ? errors.fechaFinVigenciaContrato
                          : ""
                      }
                      className={
                        touched.fechaFinVigenciaContrato &&
                        (errors.fechaFinVigenciaContrato
                          ? "is-invalid"
                          : "is-valid")
                      }
                      disabled={isDetalle}
                    />
                  </Col>
                  <Col md={4}>
                    <Select
                      onChange={(e) => {
                        handleChangeMXN(e, setFieldValue, values);
                      }}
                      name={"idTipoMoneda"}
                      value={values.idTipoMoneda}
                      label={"Moneda*:"}
                      options={tipoMoneda}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      helperText={
                        touched.idTipoMoneda ? errors.idTipoMoneda : ""
                      }
                      className={
                        touched.idTipoMoneda &&
                        (errors.idTipoMoneda ? "is-invalid" : "is-valid")
                      }
                      disabled={isDetalle}
                      keyStatus={"estatus"}
                      hideDisabledOptions
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={4}>
                    <TextField
                      onChange={(e) => {
                        handleChangeMonto(e, setFieldValue, values);
                      }}
                      onBlur={(e) => {
                        handleBlurTipoCambio(e, setFieldValue, values);
                      }}
                      name={"tipoCambioMaximo"}
                      value={
                        tipoMoneda?.find(
                          (moneda) =>
                            moneda?.primaryKey === parseInt(values.idTipoMoneda)
                        )?.primaryKey === 1
                          ? ""
                          : values.tipoCambioMaximo
                      }
                      label={"Tipo de cambio máximo aprobado:"}
                      disabled={
                        isDetalle ||
                        tipoMoneda?.find(
                          (moneda) =>
                            moneda?.primaryKey === parseInt(values.idTipoMoneda)
                        )?.primaryKey === 1
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <Row className="">
                      <Col md={3} className="iva-check check-box-black">
                        <FormCheck
                          disabled={isDetalle}
                          name={"aplicaIva"}
                          value={values.aplicaIva}
                          label={"Aplica IVA*:"}
                          type="checkbox"
                          onChange={handleChange}
                          helperText={touched.aplicaIva ? errors.aplicaIva : ""}
                          className={
                            touched.aplicaIva &&
                            (errors.aplicaIva ? "is-invalid" : "is-valid")
                          }
                        />
                      </Col>
                      <Col md={9} className="iva-porcentaje">
                        <Select
                          name={"id_iva"}
                          value={values.id_iva}
                          options={porcentajeIva}
                          keyValue={"primaryKey"}
                          keyTextValue={"porcentaje"}
                          onChange={handleChange}
                          disabled={!values.aplicaIva || isDetalle}
                          helperText={touched.id_iva ? errors.id_iva : ""}
                          className={
                            touched.id_iva &&
                            (errors.id_iva ? "is-invalid" : "is-valid")
                          }
                        />
                      </Col>
                    </Row>
                  </Col>
                  <Col md={4}>
                    <Select
                      disabled={isDetalle}
                      onChange={handleChange}
                      options={porcentajeIeps}
                      keyValue={"primaryKey"}
                      keyTextValue={"porcentaje"}
                      name={"idIeps"}
                      value={values.idIeps}
                      label={"Porcentaje de IEPS:"}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={4}>
                    <TextFieldIcon
                      disabled={isDetalle}
                      name={"montoMinimoSinImpuestos"}
                      onChange={(e) => {
                        handleChangeMonto(e, setFieldValue, values);
                      }}
                      onBlur={(e) => {
                        handleBlurMonto(e, setFieldValue, values);
                      }}
                      value={values.montoMinimoSinImpuestos}
                      label={"Monto mínimo sin impuestos*:"}
                      startIcon={faDollarSign}
                      helperText={
                        touched.montoMinimoSinImpuestos
                          ? errors.montoMinimoSinImpuestos
                          : ""
                      }
                      className={
                        touched.montoMinimoSinImpuestos &&
                        (errors.montoMinimoSinImpuestos
                          ? "is-invalid"
                          : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldIcon
                      disabled={isDetalle}
                      name={"montoMaximoSinImpuestos"}
                      onChange={(e) => {
                        handleChangeMonto(e, setFieldValue, values);
                      }}
                      onBlur={(e) => {
                        handleBlurMonto(e, setFieldValue, values);
                      }}
                      value={values.montoMaximoSinImpuestos}
                      label={"Monto máximo sin impuestos*:"}
                      startIcon={faDollarSign}
                      helperText={
                        touched.montoMaximoSinImpuestos
                          ? errors.montoMaximoSinImpuestos
                          : ""
                      }
                      className={
                        touched.montoMaximoSinImpuestos &&
                        (errors.montoMaximoSinImpuestos
                          ? "is-invalid"
                          : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldIcon
                      name={"montoPesosSinImpuestos"}
                      value={FormatMoney(
                        values.tipoCambioMaximo
                          ? parseFloat(
                              values?.montoMaximoSinImpuestos?.replace(/,/g, "")
                            ) *
                              parseFloat(
                                values?.tipoCambioMaximo?.replace(/,/g, "")
                              )
                          : values?.montoMaximoSinImpuestos,
                        2
                      )}
                      label={"Monto en pesos sin impuestos*:"}
                      startIcon={faDollarSign}
                      helperText={
                        touched.montoPesosSinImpuestos
                          ? errors.montoPesosSinImpuestos
                          : ""
                      }
                      className={
                        touched.montoPesosSinImpuestos &&
                        (errors.montoPesosSinImpuestos
                          ? "is-invalid"
                          : "is-valid")
                      }
                      disabled={true}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={4}>
                    <TextFieldIcon
                      disabled={isDetalle}
                      name={"montoMinimoConImpuestos"}
                      onChange={(e) => {
                        handleChangeMonto(e, setFieldValue, values);
                      }}
                      onBlur={(e) => {
                        handleBlurMonto(e, setFieldValue, values);
                      }}
                      value={values.montoMinimoConImpuestos}
                      label={"Monto mínimo con impuestos*:"}
                      startIcon={faDollarSign}
                      helperText={
                        touched.montoMinimoConImpuestos
                          ? errors.montoMinimoConImpuestos
                          : ""
                      }
                      className={
                        touched.montoMinimoConImpuestos &&
                        (errors.montoMinimoConImpuestos
                          ? "is-invalid"
                          : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldIcon
                      disabled={isDetalle}
                      name={"montoMaximoConImpuestos"}
                      onChange={(e) => {
                        handleChangeMonto(e, setFieldValue, values);
                      }}
                      onBlur={(e) => {
                        handleBlurMonto(e, setFieldValue, values);
                      }}
                      value={values.montoMaximoConImpuestos}
                      label={"Monto máximo con impuestos*:"}
                      startIcon={faDollarSign}
                      helperText={
                        touched.montoMaximoConImpuestos
                          ? errors.montoMaximoConImpuestos
                          : ""
                      }
                      className={
                        touched.montoMaximoConImpuestos &&
                        (errors.montoMaximoConImpuestos
                          ? "is-invalid"
                          : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldIcon
                      name={"montoPesosConImpuestos"}
                      value={FormatMoney(
                        values.tipoCambioMaximo
                          ? parseFloat(
                              values?.montoMaximoConImpuestos?.replace(/,/g, "")
                            ) *
                              parseFloat(
                                values?.tipoCambioMaximo?.replace(/,/g, "")
                              )
                          : values.montoMaximoConImpuestos,
                        2
                      )}
                      label={"Monto en pesos con impuestos*:"}
                      startIcon={faDollarSign}
                      helperText={
                        touched.montoPesosConImpuestos
                          ? errors.montoPesosConImpuestos
                          : ""
                      }
                      className={
                        touched.montoPesosConImpuestos &&
                        (errors.montoPesosConImpuestos
                          ? "is-invalid"
                          : "is-valid")
                      }
                      disabled={true}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={12} className="text-end">
                    {!isDetalle ? (
                      <>
                        <Authorization process={"CONT_DG_ADMIN"}>
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
