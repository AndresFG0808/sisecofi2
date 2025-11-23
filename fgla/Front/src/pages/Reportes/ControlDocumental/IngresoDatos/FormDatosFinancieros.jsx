import React, { useCallback, useEffect, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import { TextField, TextFieldIcon} from "../../../../components/formInputs";
import { useFormikContext } from "formik";
import { SelectInput } from "../components/InputsComponents";
import { mapOptionsSelect } from "../components/ValidationSchema";

export default function FormDatosMonetarios({
  disableForm = false,
  catalogos,
}) {
  const {
    values,
    handleChange,
    handleSubmit,
    handleBlur,
    touched,
    errors,
    setFieldValue,
    setFieldTouched,
  } = useFormikContext();
  var { moneda } = catalogos.catalogos;

  function filtrarNumeros(cadena) {
    const regex = /[\d.,]+/g;
    const numeros = cadena.match(regex);
    if (!numeros) {
      return ""; // Si no se encuentran números, devolvemos una cadena vacía
    }
    let result = numeros.join("");

    const numerosFiltrados = result;
    return numerosFiltrados;
  }

  const HandleChangeMonto = (event) => {
    var { value, name } = event.target;
    setFieldValue(name, filtrarNumeros(value));
    setFieldValue(name + "Value", filtrarNumeros(value).replaceAll(",", ""));
    setFieldTouched(name, true);
  };

  const FormatMoney = (value, decimales) => {
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value, decimales);
    let formatMoney = FORMAT_MONEY.format(_value).split("$")[1];
    return formatMoney;
  };

  const handleBlurMonto = (event, decimales = 2) => {
    var { value, name } = event.target;
    if (value) {
      let formatMoney = FormatMoney(value, decimales);
      setFieldValue(name, formatMoney);
    }
    setFieldTouched(name, true);
    handleBlur(event);
    UpdateMonto();
  };

  //Calcular MontoMXN
  const [disableTipoCambio, setDisableTioCambio] = useState(false);

  const IsMxn = useCallback(
    (value) => {
      let monedaCatalogo = mapOptionsSelect(moneda);
      let option = monedaCatalogo.find(
        (s) => value?.toString() === s.idValue?.toString()
      );

      let isMXN = option?.value?.toLowerCase() === "mxn";
      return isMXN;
    },
    [moneda]
  );

  const UpdateMonto = useCallback(() => {
    var {
      montoAutorizado,
      montoAutorizadoValue,
      idTipoMoneda,
      tipoCambioValue,
    } = values;
    let parsedMontoAutorizado = parseFloat(montoAutorizadoValue);
    let parsedTipoCambio = parseFloat(tipoCambioValue);

    let isMXN = IsMxn(idTipoMoneda);

    if (
      !isNaN(parsedMontoAutorizado) &&
      !isNaN(parsedTipoCambio) &&
      idTipoMoneda &&
      !isMXN
    ) {
      let montoValue = parsedMontoAutorizado * parsedTipoCambio;
      let monto = FormatMoney(montoValue.toString(), 2);
      setFieldValue("montoValue", montoValue);
      setFieldValue("monto", monto);
    }
    if (isMXN) {
      setFieldValue("monto", montoAutorizado);
      setFieldValue("montoValue", montoAutorizadoValue);
      setFieldValue("tipoCambio", "");
      setFieldValue("tipoCambioValue", "");
      setDisableTioCambio(true);
    } else {
      setDisableTioCambio(false);
    }
  }, [IsMxn, setFieldValue, values]);

  let { idTipoMoneda } = values;
  const [lastMoneda, setLastMoneda] = useState(null);
  useEffect(() => {
    if (idTipoMoneda && lastMoneda !== idTipoMoneda) {
      UpdateMonto();
      setLastMoneda(idTipoMoneda);
    }
  }, [UpdateMonto, idTipoMoneda, lastMoneda]);
  // UpdateMonto();
  return (
    <Form autoComplete="off" onSubmit={handleSubmit}>
      <Row>
        <Col md={4}>
          {/* <TextFieldNumber */}
          <TextFieldIcon
            startIcon={faDollarSign}
            label="Monto autorizado (C/IVA):"
            name="montoAutorizado"
            value={values.montoAutorizado}
            onChange={HandleChangeMonto}
            onBlur={handleBlurMonto}
            className={
              touched.montoAutorizado && errors.montoAutorizado
                ? "is-invalid"
                : ""
            }
            helperText={touched.montoAutorizado ? errors.montoAutorizado : ""}
            disabled={disableForm}
          />
        </Col>
        <Col md={4}>
          <SelectInput
            label="Tipo moneda:"
            name="idTipoMoneda"
            options={mapOptionsSelect(moneda)}
            onBlur={UpdateMonto}
            onChange={(event) => {
              var { value, name } = event.target;
              let isMXN = IsMxn(value);
              setDisableTioCambio(isMXN);
              if (isMXN) {
                setFieldValue("tipoCambio", "");
                setFieldValue("tipoCambioValue", "");
              }
              setFieldValue(name, value);
            }}
            disableForm={disableForm}
            showClases={false}
          />
        </Col>
        <Col md={4}>
          <TextField
            label="Tipo de cambio:"
            name="tipoCambio"
            value={values.tipoCambio}
            onChange={HandleChangeMonto}
            onBlur={(event) => {
              handleBlurMonto(event, 4);
            }}
            className={
              touched.tipoCambio && errors.tipoCambio ? "is-invalid" : ""
            }
            helperText={touched.tipoCambio ? errors.tipoCambio : ""}
            disabled={disableForm || disableTipoCambio}
          />
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          {/* <TextFieldNumber */}

          <TextFieldIcon
            startIcon={faDollarSign}
            label="Monto en MXN:"
            name="monto"
            value={values.monto}
            onChange={HandleChangeMonto}
            onBlur={handleBlurMonto}
            className={touched.monto && errors.monto ? "is-invalid" : ""}
            helperText={touched.monto ? errors.monto : ""}
            disabled={disableForm}
          />
        </Col>

        <Col md={8}>
          <TextField
            maxLength={2000}
            label="Comentarios:"
            name="comentarios"
            value={values.comentarios}
            onChange={handleChange}
            onBlur={handleBlur}
            className={
              touched.comentarios && errors.comentarios ? "is-invalid" : ""
            }
            helperText={touched.comentarios ? errors.comentarios : ""}
            disabled={disableForm}
          />
        </Col>
      </Row>
    </Form>
  );
}