import React, { useEffect, useState } from "react";
import { Row, Col, Form, Button } from "react-bootstrap";
import { LabelValue, Loader } from "../../../../../../components";
import { Formik } from "formik";
import { useToast } from "../../../../../../hooks/useToast";
import {
  REGISTRO_CONVENIO,
  //registroSchema,
  INITIAL_VALUES,
  rearrangeRegistro,
  calculateDifferenceDays,
  rearrangeConvenio,
  rearrangeNewConvenio,
  EDITAR_CONVENIOS,
} from "./utils";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import BasicModal from "../../../../../../modals/BasicModal";
import {
  FormCheck,
  Select,
  TextArea,
  TextField,
  TextFieldDate,
  TextFieldIcon,
} from "../../../../../../components/formInputs";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import { FormatMoney, filtrarNumeros } from "../../../../../../functions/utils";
import {
  useGetConvenioByIdQuery,
  useGetDatosInicialesQuery,
  useGetPorcentajeIvaQuery,
  useGetVigenciaMontosQuery,
  usePostRegistrarConvenioMutation,
  usePutModificarConvenioMutation,
} from "./store";
import "./styles.css";
import { useErrorMessages } from "../../../../../../hooks/useErrorMessages";
import SingleBasicModal from "../../../../../../modals/SingleBasicModal";
import * as yup from "yup";
import _ from "lodash";
import { useGetTipoMonedaCMQuery } from "./store";
import moment from "moment";

const formatDecimals = 2;
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}
const Registro = ({ isDetalle }) => {
  const { errorToast } = useToast();
  const navigate = useNavigate();
  const { idContrato, idConvenio } = useParams();
  const [searchParams, setSearchParams] = useSearchParams({ cm: "" });
  const { getMessageExists } = useErrorMessages(REGISTRO_CONVENIO);
  const { getMessageExists: editarMessageExists } =
    useErrorMessages(EDITAR_CONVENIOS);
  const idNuevoContrato = searchParams.get("q");
  const idNuevoConvenio = searchParams.get("cm");
  const [isDisabled, setIsDisabled] = useState(false);
  const [valoresIniciales, setValoresIniciales] = useState(INITIAL_VALUES);
  const [confirmCancellation, setConfirmCancellation] = useState(false);
  const [singleMessage, setSingleMessage] = useState("");
  const [showSingleModal, setShowSingleModal] = useState(false);
  const [montoMaximoSinEditado, setMontoMaximoSinEditado] = useState(false);
  const [montoMaximoEditado, setMontoMaximoEditado] = useState(false);
  const [montoEditado, setMontoEditado] = useState(false);

  const [guardarConvenio, { isLoading: isLoadingGuardar }] =
    usePostRegistrarConvenioMutation();
  const [modificarConvenio, { isLoading: isLoadingModificar }] =
    usePutModificarConvenioMutation();
  const { data: porcentajeIva, isLoading: isLoadingIva } =
    useGetPorcentajeIvaQuery();

  const { data: vigenciaMontos, isLoading: isLoadingVigenciaMontos } =
    useGetVigenciaMontosQuery(idContrato || idNuevoContrato);

  const { data: convenioQ, isFetching } = useGetConvenioByIdQuery(
    idConvenio || idNuevoConvenio
  );

  const { isLoading: isLoadingDatosIniciales, data: datosIniciales } =
    useGetDatosInicialesQuery(idContrato || idNuevoContrato);

  const { isLoading: isLoadingMonedaCat, data: dataMonedaCat } =
    useGetTipoMonedaCMQuery();
  const [monedaNacional, setMonedaNacional] = useState(true);

  useEffect(() => {
    if (dataMonedaCat && vigenciaMontos?.vigenciaMontosModel) {
      let { idTipoMoneda } = vigenciaMontos.vigenciaMontosModel;
      if (idTipoMoneda && !_.isEmpty(dataMonedaCat)) {
        let monedaItem = dataMonedaCat.find(
          (s) => s.primaryKey == idTipoMoneda
        );
        if (monedaItem?.primaryKey === 1) {
          setMonedaNacional(true);
        } else {
          setMonedaNacional(false);
        }
      }
    }
  }, [dataMonedaCat, vigenciaMontos]);

  useEffect(() => {
    if (convenioQ) {
      let _convenioQ = { ...convenioQ };
      _convenioQ = {
        ..._convenioQ,
        ivaMonto: _convenioQ.incremento
          ? _convenioQ * datosIniciales?.ivaCantidad
          : datosIniciales?.ivaCantidad,
      };
      let mappedValues = rearrangeConvenio(_convenioQ);

      setValoresIniciales(mappedValues);
    } else {
      if (datosIniciales) {
        if (vigenciaMontos) {
          const { tipoCambioMaximo } = vigenciaMontos?.vigenciaMontosModel;

          let newConvenioData = rearrangeNewConvenio(
            valoresIniciales,
            datosIniciales
          );
          newConvenioData = {
            ...newConvenioData,
            ...(tipoCambioMaximo
              ? { tipoCambio: tipoCambioMaximo }
              : { tipoCambio: "" }),
          };

          setValoresIniciales(newConvenioData);
        }
      }
    }
  }, [convenioQ, vigenciaMontos, datosIniciales]);

  const onSubmit = async (values, { resetForm }) => {
    try {
      const curatedData = values.tiempo
        ? { ...values, fechaFin: values.fechaFinContratoCM }
        : {
            ...values,
            fechaFin: datosIniciales?.fechaFinServicio,
            fechaFinServicio: datosIniciales?.fechaFinServicio,
          };
      if (!convenioQ) {
        const data = rearrangeRegistro(
          curatedData,
          idContrato || idNuevoContrato
        );
        const res = await guardarConvenio({ data, idContrato }).unwrap();
        setSearchParams(
          (prev) => {
            prev.set("cm", res.idConvenioModificatorio);
            return prev;
          },
          { replace: true }
        );
        setSingleMessage(REGISTRO_CONVENIO.MSG002);
      } else {
        const data = rearrangeRegistro(
          curatedData,
          idContrato || idNuevoContrato
        );
        await modificarConvenio({
          data: {
            ...data,
            idConvenioModificatorio: parseInt(idConvenio || idNuevoConvenio),
          },
        }).unwrap();
        setSingleMessage(
          "El convenio modificatorio fue actualizado exitosamente."
        );
      }
      setShowSingleModal(true);
      resetForm();
    } catch (error) {
      if (getMessageExists(error?.data?.mensaje?.[0])) {
        setSingleMessage(error?.data?.mensaje?.[0]);
        setShowSingleModal(true);
      } else if (editarMessageExists(error?.data?.mensaje?.[0])) {
        setSingleMessage(error?.data?.mensaje?.[0]);
        setShowSingleModal(true);
      } else {
        setSingleMessage(EDITAR_CONVENIOS.MSG018);
        setShowSingleModal(true);
      }
    }
  };
  const handleBlurMonto = (event, setFieldValue, decimales = 2) => {
    const { value, name } = event.target;
    if (value) {
      const formatMoney = FormatMoney(value, decimales);
      setFieldValue(name, formatMoney);
    }
  };
  const handleChangeMonto = (event, setFieldValue) => {
    const { value, name } = event.target;
    setFieldValue(name, filtrarNumeros(value));
  };

  const handleCancelConfirm = () => {
    console.log("handleCancel =>");
    navigate(`/contratos/contratos/editar/${idContrato || idNuevoContrato}`);
  };

  const handleChangeConvenio = (values, resetForm, idConvenio) => {
    const { alcance, monto, tiempo, administrativo, numeroConvenio } = values;
    resetForm();
    setValoresIniciales({
      ...valoresIniciales,
      numeroConvenio,
      alcance,
      monto,
      tiempo,
      administrativo,
      [idConvenio]: !values[idConvenio],
    });
  };

  const validationString = (coditionalField = []) =>
    yup.string().when(coditionalField, (values, schema) => {
      if (values.find((s) => s)) {
        return schema.required("Dato requerido");
      }
      return schema.nullable();
    });

  const validationTipoCambio = (conditionalField = []) =>
    yup.string().when(conditionalField, (values, schema) => {
      if (values.find((s) => s)) {
        return schema;
      }
      return schema.required("Por favor, seleccione al menos un elemento");
    });

  const validationSchema = yup.object().shape({
    numeroConvenio: yup
      .string()
      .max(40, "Se excedió el número de caracteres.")
      .required("Dato requerido")
      .test(
        "inicia-no-contrato",
        "El número de convenio debe empezar con el número de contrato o del último convenio modificatorio.",
        (value) => {
          if (value)
            return value.startsWith(
              datosIniciales?.numeroContrato ||
                datosIniciales?.numeroUltimoConvenio
            );
          return false;
        }
      ),
    fechaFirma: validationString([
      "alcance",
      "monto",
      "tiempo",
      "administrativo",
    ]),
    fechaFinServicio: validationString(["tiempo"]),
    fechaFinContratoCM: validationString(["tiempo"]),
    //incremento: validationString(["monto"]),
    ieps: validationString(["alcance", "monto", "tiempo", "administrativo"]),
    iva: validationString(["monto"]),
    tipoCambio: yup.string().when(["alcance"], (values, schema) => {
      if (values.find((s) => s) && !monedaNacional) {
        return schema.required("Dato requerido");
      }
      return schema.nullable();
    }),
    comentarios: yup.string().max(250, "Se excedió el número de caracteres."),
    tipoConvenio: validationTipoCambio([
      "monto",
      "tiempo",
      "administrativo",
      "alcance",
    ]),
  });

  const calculoMontoPesosAlcance = (
    montoMaximoContratoCMConImpuestos = 0,
    tipoCambio = 0
  ) => {
    if (monedaNacional) {
      return montoMaximoContratoCMConImpuestos;
    } else {
      return safeParseFloat(tipoCambio) * montoMaximoContratoCMConImpuestos;
    }
  };

  const findIva = (item, cat = porcentajeIva) => {
    if (!_.isEmpty(porcentajeIva)) {
      let option = porcentajeIva.find((s) => s.primaryKey == item);
      if (option) {
        let value = safeParseFloat(option.porcentaje);
        let valueIva = (isNaN(value) ? 1 : value) / 100;
        return valueIva;
      }
    }
    return 0;
  };
  const calculoImpuesto = (incremento = 0, subtotal = 0, ieps = 0, iva = 0) => {
    return (
      (safeParseFloat(incremento) +
        safeParseFloat(subtotal) +
        safeParseFloat(ieps)) *
      safeParseFloat(iva)
    );
  };
  const calculoIVA = (incremento = 1, iva = 0) => {
    return safeParseFloat(incremento) * safeParseFloat(iva);
  };

  const calculoMontoMaximoContratoCMSinImpuestos = (
    incremento = 0,
    subTotal = 0
  ) => {
    const _incremento = safeParseFloat(incremento, 0);
    return _incremento + subTotal;
  };

  const calculoMontoMaximoContratoCMConImpuestos = (
    montoMaximoContratoCMSinImpuestos = 0,
    impuestos = 0
  ) => {
    return (
      safeParseFloat(montoMaximoContratoCMSinImpuestos) +
      safeParseFloat(impuestos)
    );
  };

  const calculoMontoPesosMonto = (
    montoMaximoContratoCMConImpuestos = 0,
    tipoCambio = 0,
    iva,
    incremento
  ) => {
    let result = 0;
    if (!monedaNacional) {
      let incrementoIva =
        safeParseFloat(incremento) +
        safeParseFloat(incremento) *
          safeParseFloat(iva) *
          safeParseFloat(tipoCambio);
      result =
        safeParseFloat(montoMaximoContratoCMConImpuestos) + incrementoIva;
    } else {
      result = montoMaximoContratoCMConImpuestos;
    }
    return result;
  };

  //const [lastMontos, setLastMontos] = useState(null);

  const updateMontos = (values, vigenciaMontos) => {
    const { alcance, monto, tiempo, administrativo } = values;
    let _montoPesos = 0;
    let _impuesto = 0;
    let _montoMaximoSinImpuestos = 0;
    let _iva = 0;
    let _montoMaximoConInpuestos = 0;
    let _tipoCambio = 0;
    if (vigenciaMontos) {
      let {
        //montoMaximoContratoCMSinImpuestos,
        //montoMaximoContratoCMConImpuestos,
        //montoPesos,
        incremento = 0,
        subtotal = 0,
        iva,
        tipoCambio,
        ieps,
      } = values;
      _tipoCambio = safeParseFloat(tipoCambio);
      const ivaPer = findIva(iva);
      _iva = calculoIVA(values.incremento, ivaPer);
      _impuesto = calculoImpuesto(values.incremento, subtotal, ieps, ivaPer);
      if (monto) {
        _montoMaximoSinImpuestos = calculoMontoMaximoContratoCMSinImpuestos(
          incremento,
          subtotal
        );
        _montoMaximoConInpuestos = calculoMontoMaximoContratoCMConImpuestos(
          _montoMaximoSinImpuestos,
          _impuesto
        );
        _montoPesos = calculoMontoPesosMonto(
          _montoMaximoConInpuestos,
          tipoCambio,
          ivaPer,
          values.incremento
        );
      }
      if (alcance) {
        _montoMaximoSinImpuestos = calculoMontoMaximoContratoCMSinImpuestos(
          incremento,
          subtotal
        );
        _montoMaximoConInpuestos = calculoMontoMaximoContratoCMConImpuestos(
          values.montoMaximoContratoCMSinImpuestos,
          _impuesto
        );
        _montoPesos = calculoMontoPesosAlcance(
          _montoMaximoConInpuestos,
          values.tipoCambio
        );
      }
      if (tiempo) {
        _montoMaximoSinImpuestos = calculoMontoMaximoContratoCMSinImpuestos(
          incremento,
          subtotal
        );
        _montoMaximoConInpuestos = calculoMontoMaximoContratoCMConImpuestos(
          _montoMaximoSinImpuestos,
          _impuesto
        );
        _montoPesos = calculoMontoPesosMonto(
          _montoMaximoConInpuestos,
          tipoCambio,
          ivaPer,
          values.incremento
        );
      }
      if (administrativo) {
        _montoMaximoSinImpuestos = calculoMontoMaximoContratoCMSinImpuestos(
          incremento,
          subtotal
        );
        _montoMaximoConInpuestos = calculoMontoMaximoContratoCMConImpuestos(
          _montoMaximoSinImpuestos,
          _impuesto
        );
        _montoPesos = calculoMontoPesosMonto(
          _montoMaximoConInpuestos,
          tipoCambio,
          ivaPer,
          values.incremento
        );
      }
    }
    let montos = {
      montoPesos: _montoPesos,
      impuesto: _impuesto,
      montoMaximoContratoCMConImpuestos: _montoMaximoConInpuestos,
      montoMaximoContratoCMSinImpuestos: _montoMaximoSinImpuestos,
      ivaMonto: _iva,
      tipoCambio: _tipoCambio,
    };

    return montos;
  };

  const validateUpdateValue = (
    setFieldValue,
    nameField,
    values,
    newValues,
    format = false
  ) => {
    let value = values[nameField];
    let newValue = newValues[nameField];

    newValue = format ? FormatMoney(newValue) : newValue;

    if (newValue && newValue !== value) {
      setFieldValue(nameField, newValue);
    }
  };
  console.log(valoresIniciales);

  return (
    <>
      {isLoadingGuardar ||
      isLoadingIva ||
      isLoadingModificar ||
      isFetching ||
      isLoadingDatosIniciales ? (
        <Loader />
      ) : null}
      <Formik
        initialValues={valoresIniciales}
        enableReinitialize
        onSubmit={onSubmit}
        validationSchema={validationSchema}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          setFieldValue,
          resetForm,
          values,
          errors,
          touched,
          isValid,
        }) => {
          /* const isAnySelected =
            values.alcance || values.monto || values.administrativo; */
          let montos = updateMontos(values, vigenciaMontos);

          if (!montoEditado) {
            validateUpdateValue(
              setFieldValue,
              "montoPesos",
              values,
              montos,
              true
            );
          }
          if (!montoMaximoSinEditado) {
            validateUpdateValue(
              setFieldValue,
              "montoMaximoContratoCMSinImpuestos",
              values,
              montos,
              true
            );
          }
          if (!montoMaximoEditado) {
            validateUpdateValue(
              setFieldValue,
              "montoMaximoContratoCMConImpuestos",
              values,
              montos,
              true
            );
          }
          validateUpdateValue(setFieldValue, "ivaMonto", values, montos, true);
          validateUpdateValue(setFieldValue, "subtotal", values, montos, true);

          /* const handleChangeNumbers = (event) => {
            let { value, name } = event.target;
            setFieldValue(filtrarNumeros(value));
          }; */

          return (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              {/* {console.log(values, errors)} */}
              <Row>
                {/* row 1 */}
                <Col md={4}>
                  <TextField
                    disabled={isDetalle}
                    label="Número de convenio*:"
                    name={"numeroConvenio"}
                    value={values.numeroConvenio}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={
                      touched.numeroConvenio ? errors.numeroConvenio : ""
                    }
                    className={
                      touched.numeroConvenio &&
                      (errors.numeroConvenio
                        ? "is-invalid"
                        : errors.numeroConvenio !== undefined
                        ? "is-valid"
                        : "")
                    }
                  />
                </Col>

                <Col md={8}>
                  <Row className={"special-label"}>
                    <LabelValue value={"Tipo de convenio*:"} />
                  </Row>
                  <Row className="checkbox-convenio">
                    <Col md={3}>
                      <FormCheck
                        disabled={isDetalle}
                        type={"checkbox"}
                        label={"Alcance"}
                        name={"alcance"}
                        onChange={() => {
                          handleChangeConvenio(values, resetForm, "alcance");
                        }}
                        value={values.alcance}
                        className={
                          touched.tipoConvenio &&
                          (errors.tipoConvenio ? "is-invalid" : "is-valid")
                        }
                      />
                    </Col>
                    <Col md={3}>
                      <FormCheck
                        disabled={isDetalle}
                        type={"checkbox"}
                        label={"Monto"}
                        name={"monto"}
                        onChange={() => {
                          handleChangeConvenio(values, resetForm, "monto");
                        }}
                        value={values.monto}
                        className={
                          touched.tipoConvenio &&
                          (errors.tipoConvenio ? "is-invalid" : "is-valid")
                        }
                      />
                    </Col>
                    <Col md={3}>
                      <FormCheck
                        disabled={isDetalle}
                        type={"checkbox"}
                        label={"Tiempo"}
                        name={"tiempo"}
                        onChange={() => {
                          handleChangeConvenio(values, resetForm, "tiempo");
                        }}
                        value={values.tiempo}
                        className={
                          touched.tipoConvenio &&
                          (errors.tipoConvenio ? "is-invalid" : "is-valid")
                        }
                      />
                    </Col>
                    <Col md={3}>
                      <FormCheck
                        disabled={isDetalle}
                        type={"checkbox"}
                        label={"Administrativo"}
                        name={"administrativo"}
                        onChange={() => {
                          handleChangeConvenio(
                            values,
                            resetForm,
                            "administrativo"
                          );
                        }}
                        value={values.administrativo}
                        className={
                          touched.tipoConvenio &&
                          (errors.tipoConvenio ? "is-invalid" : "is-valid")
                        }
                      />
                    </Col>
                  </Row>
                  <Form.Control.Feedback
                    type="invalid"
                    style={{
                      display:
                        errors.tipoConvenio && touched.alcance
                          ? "block"
                          : "none",
                    }}
                  >
                    {errors.tipoConvenio}
                  </Form.Control.Feedback>
                </Col>

                {/* row 2 */}
                <Col md={4}>
                  <TextFieldDate
                    label="Fecha de firma*:"
                    name={"fechaFirma"}
                    value={values.fechaFirma}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={touched.fechaFirma ? errors.fechaFirma : ""}
                    className={
                      touched.fechaFirma &&
                      (errors.fechaFirma
                        ? "is-invalid"
                        : errors.fechaFirma !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={
                      (!values.alcance &&
                        !values.monto &&
                        !values.tiempo &&
                        !values.administrativo) ||
                      isDetalle
                    }
                  />
                </Col>
                <Col md={4}>
                  {/*  */}

                  <TextFieldDate
                    label="Fecha fin de servicio*:"
                    name={"fechaFinServicio"}
                    value={
                          values.fechaFinServicio
                            ? values.fechaFinServicio
                            : datosIniciales?.fechaFinVigenciaServicios
                            ? moment(new Date(datosIniciales.fechaFinVigenciaServicios)).format("YYYY-MM-DD")
                            : ""
                    }
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={
                      touched.fechaFinServicio ? errors.fechaFinServicio : ""
                    }
                    className={
                      touched.fechaFinServicio &&
                      (errors.fechaFinServicio
                        ? "is-invalid"
                        : errors.fechaFinServicio !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={!values.tiempo || isDetalle }
                  />
                </Col>

                <Col md={4}>
                  <TextFieldDate
                    label="Fecha fin de contrato con CM*:"
                    name={"fechaFinContratoCM"}
                    value={
                      values.fechaFinContratoCM
                        ? values.fechaFinContratoCM
                        : datosIniciales?.fechaFinVigenciaContrato
                        ? moment(new Date(datosIniciales.fechaFinVigenciaContrato)).format("YYYY-MM-DD")
                        : ""
                    }
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={
                      touched.fechaFinContratoCM
                        ? errors.fechaFinContratoCM
                        : ""
                    }
                    className={
                      touched.fechaFinContratoCM &&
                      (errors.fechaFinContratoCM
                        ? "is-invalid"
                        : errors.fechaFinContratoCM !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={!values.tiempo || isDetalle}
                  />
                </Col>

                {/* row 3 */}
                <Col md={4}>
                  <TextField
                    label="Cálculo de días naturales*:"
                    name={"calculoDiasNaturales"}
                    value={
                      calculateDifferenceDays(
                            values.fechaFinContratoCM,
                            vigenciaMontos?.vigenciaMontosModel
                              ?.fechaInicioVigenciaContrato
                          )
                    }
                    onChange={handleChange}
                    onBlur={handleBlur}
                    disabled={true}
                  />
                </Col>

                <Col md={4}>
                  <TextField
                    label="Incremento*:"
                    name={"incremento"}
                    value={values.incremento}
                    onChange={(event) => {
                      handleChangeMonto(event, setFieldValue);
                    }}
                    onBlur={(event) => {
                      handleBlurMonto(event, setFieldValue);
                    }}
                    helperText={touched.incremento ? errors.incremento : ""}
                    className={
                      touched.incremento &&
                      (errors.incremento
                        ? "is-invalid"
                        : errors.incremento !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={!values.monto || isDetalle}
                  />
                </Col>

                <Col md={4}>
                  <TextFieldIcon
                    label="Subtotal*:"
                    startIcon={faDollarSign}
                    name={"subtotal"}
                    value={FormatMoney(
                      safeParseFloat(values.subtotal),
                      formatDecimals
                    )}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    disabled={true}
                  />
                </Col>

                {/* row 4 */}
                <Col md={4}>
                  <TextFieldIcon
                    label="IEPS*:"
                    startIcon={faDollarSign}
                    name={"ieps"}
                    value={values.ieps}
                    onChange={(event) => {
                      handleChangeMonto(event, setFieldValue);
                    }}
                    onBlur={(event) => {
                      handleBlurMonto(event, setFieldValue);
                    }}
                    helperText={touched.ieps ? errors.ieps : ""}
                    className={
                      touched.ieps &&
                      (errors.ieps
                        ? "is-invalid"
                        : errors.ieps !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={(!values.alcance && !values.monto) || isDetalle}
                  />
                </Col>

                <Col md={4}>
                  <label className="form-label">IVA*:</label>
                  <Row>
                    <Col md={4}>
                      <Select
                        options={porcentajeIva}
                        defaultOptionText="Sel."
                        name={"iva"}
                        keyValue={"primaryKey"}
                        keyTextValue={"porcentaje"}
                        value={values.iva}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        helperText={touched.iva ? errors.iva : ""}
                        className={
                          touched.iva &&
                          (errors.iva
                            ? "is-invalid"
                            : errors.iva !== undefined
                            ? "is-valid"
                            : "")
                        }
                        disabled={!values.monto || isDetalle}
                      />
                    </Col>
                    <Col md={8}>
                      {/* (subtotal + incremento +ieps)* iva */}
                      <TextFieldIcon
                        startIcon={faDollarSign}
                        name={"ivaMonto"}
                        value={
                          FormatMoney(
                            safeParseFloat(values.montoMaximoContratoCMSinImpuestos) *
                            (safeParseFloat(
                              porcentajeIva?.find((s) => s.primaryKey === values.iva)?.porcentaje || 0
                            ) / 100)
                          )
                        }
                        onChange={handleChange}
                        onBlur={handleBlur}
                        disabled={true}
                      />
                    </Col>
                  </Row>
                </Col>

                <Col md={4}>
                  <TextFieldIcon
                    label="Tipo de cambio*:"
                    startIcon={faDollarSign}
                    name={"tipoCambio"}
                    value={values.tipoCambio}
                    onChange={(event) => {
                      handleChangeMonto(event, setFieldValue);
                    }}
                    onBlur={(event) => {
                      handleBlurMonto(event, setFieldValue, 4);
                    }}
                    helperText={touched.tipoCambio ? errors.tipoCambio : ""}
                    className={
                      touched.tipoCambio &&
                      (errors.tipoCambio
                        ? "is-invalid"
                        : errors.tipoCambio !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={
                      (!values.alcance && !values.monto) ||
                      monedaNacional ||
                      isDetalle
                    }
                  />
                </Col>
                {/* row 5 */}
                <Col md={4} /* className="label-bold" */>
                  <TextFieldIcon
                  label="Monto máximo del contrato con CM sin impuestos*:"
                  startIcon={faDollarSign}
                  name={"montoMaximoContratoCMSinImpuestos"}
                  value={values.montoMaximoContratoCMSinImpuestos}
                  onChange={e => {
                    handleChange(e);
                    setMontoMaximoSinEditado(true);
                  }}
                  onBlur={handleBlur}
                  disabled={isDetalle}
                />
                </Col>

                <Col md={4} /* className="label-bold" */>
                  <TextFieldIcon
                  label="Monto máximo del contrato con CM con impuestos*:"
                  startIcon={faDollarSign}
                  name={"montoMaximoContratoCMConImpuestos"}
                  value={values.montoMaximoContratoCMConImpuestos}
                  onChange={e => {
                    handleChange(e);
                    setMontoMaximoEditado(true);
                  }}
                  onBlur={handleBlur}
                  disabled={isDetalle}
                />
                </Col>

                <Col md={4} className="monto-pesos">
                  <TextFieldIcon
                    label="Monto en pesos*:"
                    startIcon={faDollarSign}
                    name={"montoPesos"}
                    value={values.montoPesos}
                    onChange={e => {
                      handleChange(e);
                      setMontoEditado(true);
                    }}
                    onBlur={handleBlur}
                    disabled={isDetalle}
                  />
                </Col>

                {/* row 6 */}
                <Col md={8}>
                  <TextArea
                    label="Comentarios:"
                    name={"comentarios"}
                    value={values.comentarios}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={touched.comentarios ? errors.comentarios : ""}
                    className={
                      touched.comentarios &&
                      (errors.comentarios
                        ? "is-invalid"
                        : errors.comentarios !== undefined
                        ? "is-valid"
                        : "")
                    }
                    disabled={
                      (!values.alcance &&
                        !values.monto &&
                        !values.tiempo &&
                        !values.administrativo) ||
                      isDetalle
                    }
                  />
                </Col>
              </Row>

              <Row>
                <Col md={12} className="text-end">
                  {!isDetalle ? (
                    <>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => setConfirmCancellation(true)}
                      >
                        Cancelar
                      </Button>
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="submit"
                        onClick={() => {
                          console.log(errors);
                          !isValid && errorToast(REGISTRO_CONVENIO.MSG001);
                        }}
                        disabled={isDisabled}
                      >
                        Guardar
                      </Button>
                    </>
                  ) : null}
                </Col>
              </Row>
            </Form>
          );
        }}
      </Formik>
      <SingleBasicModal
        size={"md"}
        title={"Mensaje"}
        approveText={"Aceptar"}
        show={showSingleModal}
        onHide={() => {
          setShowSingleModal(false);
        }}
      >
        {singleMessage}
      </SingleBasicModal>
      <BasicModal
        show={confirmCancellation}
        size={"md"}
        title="Mensaje"
        approveText={"Sí"}
        denyText={"No"}
        handleApprove={handleCancelConfirm}
        handleDeny={() => setConfirmCancellation(false)}
        onHide={() => setConfirmCancellation(false)}
      >
        {REGISTRO_CONVENIO.MSG004}
      </BasicModal>
    </>
  );
};

export default Registro;
