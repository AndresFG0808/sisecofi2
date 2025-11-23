import React, { useCallback, useEffect, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import { TextField, TextFieldIcon } from "../../../../../components/formInputs";
import { useFormikContext } from "formik";
import { SelectInput } from "../Components/InputsComponents";
import { mapOptionsSelect } from "../Components/ValidationSchema";

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

const filtrarNumeros= useCallback((cadena, decimales, isMontoMxn = false) =>{
  if (!cadena) return "";

  let negative = cadena.startsWith("-");
  let hasDot = cadena.includes(".")
  cadena = cadena.replace(/[^0-9.,]/g, ""); // Eliminar caracteres no numéricos excepto puntos y comas

  let [entero, decimal = ""] = cadena.split(".");
  decimal = decimal.replace(/\D/g, "");

  let enterosFiltrados = entero.replaceAll(",","");

  if (!isMontoMxn && enterosFiltrados.length > 12) {
    entero =FormatMoney(enterosFiltrados.slice(0, 12),0);
    decimal = "";
  }

  cadena = hasDot ? `${entero}.${decimal.slice(0, decimales)}` : entero;
  return negative ? `-${cadena}` : cadena;
},[])


  const HandleChangeMonto = (event, decimales,isMontoMxn = false) => {
    var { value, name } = event.target;
    let _value = filtrarNumeros(value, decimales, isMontoMxn);
    setFieldValue(name, _value);
    setFieldValue(name + "Value", _value.replaceAll(",", ""));
    // setFieldTouched(name, true);
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
    if (isNaN(_value)) {
      return value;
    }
    let _formatMoney = FORMAT_MONEY.format(_value);
    let formatMoney = _formatMoney.replace("$", "");
    return formatMoney;
  };
  const FormatMoney2 = (value, decimales=2) => {
    value = "" + value;
    let [entero, decimal = ""] = value.split(".");
    value = `${entero}.${decimal.slice(0, 2)}`;
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
  
    let _formatMoney = FORMAT_MONEY.format(_value);
    let formatMoney = _formatMoney.replace("$", "");
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
  function insertarCaracter(cadena, caracter) {
    let posicion = cadena.length - 6; // Seis posiciones antes del último carácter
    if (posicion < 0) return cadena; // Si la cadena es muy corta, no se modifica
    return cadena.slice(0, posicion) + caracter + cadena.slice(posicion);
}

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
      
      let montoValue =
        // eslint-disable-next-line no-undef
        BigInt(Math.round(parsedMontoAutorizado * 100)) *
        // eslint-disable-next-line no-undef
        BigInt(Math.round(parsedTipoCambio * 10000));
      montoValue =insertarCaracter( montoValue.toString(),".");
      // montoValue = Number(montoValue) / (100 * 10000);
      montoValue = filtrarNumeros("" + montoValue, 2, true);
      let monto = FormatMoney2(montoValue.toString(), 2);
      setFieldValue("montoValue", montoValue);
      setFieldValue("monto", monto);
    }
    if (isMXN) {
      let monto = montoAutorizado ? FormatMoney2(parsedMontoAutorizado.toString(),2):"";
      setFieldValue("monto", monto);
      setFieldValue("montoValue", montoAutorizadoValue);
      setFieldValue("tipoCambio", "");
      setFieldValue("tipoCambioValue", "");
      setDisableTioCambio(true);
    } else {
      setDisableTioCambio(false);
    }
  }, [IsMxn, filtrarNumeros, setFieldValue, values]);

  let { idTipoMoneda } = values;
  const [lastMoneda, setLastMoneda] = useState(null);
  useEffect(() => {
    if (idTipoMoneda && lastMoneda !== idTipoMoneda) {
      UpdateMonto();
      setLastMoneda(idTipoMoneda);
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [idTipoMoneda, lastMoneda]);
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
            onChange={(event) => HandleChangeMonto(event, 2)}
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
            onChange={(event) => HandleChangeMonto(event, 4)}
            onBlur={(event) => {
              handleBlurMonto(event, 4);
            }}
            className={
              touched.tipoCambio && errors.tipoCambio ? "is-invalid" : ""
            }
            helperText={touched.tipoCambio ? errors.tipoCambio : ""}
            disabled={disableForm || disableTipoCambio || !idTipoMoneda}
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
            onChange={(event) => HandleChangeMonto(event, 2,true)}
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
