import React, { useCallback, useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useFormikContext } from "formik";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TextField from "../../../../components/formInputs/TextField";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import FileField from "../../../../components/formInputs/FileField";
import Select from "../../../../components/formInputs/Select";
import showMessage from "../../../../components/Messages";
import { NOTA_PAGO } from "../../../../constants/messages";

const catalogoDesgloce = [
  { idDesgloce: 1, nombre: "SAT" },
  { idDesgloce: 2, nombre: "Convenio de colaboración" }
];

export const FormatMoney = (value, decimales = 2) => {
  try {
    value = value ? value.toString() : "0";
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value);
    let formatMoney = FORMAT_MONEY.format(_value).split("$")[1];
    return formatMoney;
  } catch (error) {
    return "0.00";
  }
};

export default function FormularioSolicitudPago({
  index,
  notapago,
  errors,
  touched,
  referenciaConvenioColaboracionState,
  desboqueo,
  numeroIncremental,
}) {
  const [formattedValue, setFormattedValue] = useState('');
  const [formattedPagadoNAFIN, setFormattedPagadoNAFIN] = useState('');
  const [formattedTipoCambioPagado, setFormattedTipoCambioPagado] = useState('');
  const [formattedPagadoNAFINConvenio, setFormattedPagadoNAFINConvenio] = useState('');
  const [formattedTipoCambioPagadoConvenio, setFormattedTipoCambioPagadoConvenio] = useState('');
  const { setFieldValue, handleBlur, values } = useFormikContext();
  let {
    pdfName = ""
  } = values.notapagos[index]

  const formatCurrency = (value, decimales = 2) => {
    if (value === null || value === undefined) return '';
    const numberValue = parseFloat(value);
    if (isNaN(numberValue)) return '';
    return numberValue.toLocaleString('en-US', { minimumFractionDigits: decimales, maximumFractionDigits: decimales });
  };

  const handleBlurFormatTipoCambioPagado = () => {
    const value = values.notapagos[index].referenciaPagoModel.tipoCambioPagado;
    if (typeof value === 'string') {
      const numericValue = parseFloat(value.replace(/,/g, ''));
      if (!isNaN(numericValue)) {
        setFieldValue(nameInput("referenciaPagoModel", "tipoCambioPagado"), numericValue);
        const formattedValue = formatCurrency(numericValue, 4);
        setFormattedTipoCambioPagado(formattedValue);
      } else {
        setFieldValue(nameInput("referenciaPagoModel", "tipoCambioPagado"), '');
        setFormattedTipoCambioPagado('');
      }
    } else if (typeof value === 'number') {
      setFieldValue(nameInput("referenciaPagoModel", "tipoCambioPagado"), value);
      setFormattedTipoCambioPagado(formatCurrency(value, 4));
    } else {
      setFieldValue(nameInput("referenciaPagoModel", "tipoCambioPagado"), '');
      setFormattedTipoCambioPagado('');
    }
  };

  const handleBlurFormatPagadoNAFIN = () => {
    const value = values.notapagos[index].referenciaPagoModel.pagadoNAFIN;
    if (typeof value === 'string') {
      const numericValue = parseFloat(value.replace(/,/g, ''));
      if (!isNaN(numericValue)) {
        setFieldValue(nameInput("referenciaPagoModel", "pagadoNAFIN"), numericValue);
        const formattedValue = formatCurrency(numericValue);
        setFormattedPagadoNAFIN(formattedValue);
      } else {
        setFieldValue(nameInput("referenciaPagoModel", "pagadoNAFIN"), '');
        setFormattedPagadoNAFIN('');
      }
    } else if (typeof value === 'number') {
      setFieldValue(nameInput("referenciaPagoModel", "pagadoNAFIN"), value);
      setFormattedPagadoNAFIN(formatCurrency(value));
    } else {
      setFieldValue(nameInput("referenciaPagoModel", "pagadoNAFIN"), '');
      setFormattedPagadoNAFIN('');
    }
  };

  const handleBlurFormatPagadoNAFINConvenio = () => {
    const value = values.notapagos[index].referenciaConvenioColaboracion.pagadoNAFIN;
    if (typeof value === 'string') {
      const numericValue = parseFloat(value.replace(/,/g, ''));
      if (!isNaN(numericValue)) {
        setFieldValue(nameInput("referenciaConvenioColaboracion", "pagadoNAFIN"), numericValue);
        const formattedValue = formatCurrency(numericValue);
        setFormattedPagadoNAFINConvenio(formattedValue);
      } else {
        setFieldValue(nameInput("referenciaConvenioColaboracion", "pagadoNAFIN"), '');
        setFormattedPagadoNAFINConvenio('');
      }
    } else if (typeof value === 'number') {
      setFieldValue(nameInput("referenciaConvenioColaboracion", "pagadoNAFIN"), value);
      setFormattedPagadoNAFINConvenio(formatCurrency(value));
    } else {
      setFieldValue(nameInput("referenciaConvenioColaboracion", "pagadoNAFIN"), '');
      setFormattedPagadoNAFINConvenio('');
    }
  };

  const handleBlurFormatTipoCambioPagadoConvenio = () => {
    const value = values.notapagos[index].referenciaConvenioColaboracion.tipoCambioPagado;
    if (typeof value === 'string') {
      const numericValue = parseFloat(value.replace(/,/g, ''));
      if (!isNaN(numericValue)) {
        setFieldValue(nameInput("referenciaConvenioColaboracion", "tipoCambioPagado"), numericValue);
        const formattedValue = formatCurrency(numericValue, 4);
        setFormattedTipoCambioPagadoConvenio(formattedValue);
      } else {
        setFieldValue(nameInput("referenciaConvenioColaboracion", "tipoCambioPagado"), '');
        setFormattedTipoCambioPagadoConvenio('');
      }
    } else if (typeof value === 'number') {
      setFieldValue(nameInput("referenciaConvenioColaboracion", "tipoCambioPagado"), value);
      setFormattedTipoCambioPagadoConvenio(formatCurrency(value, 4));
    } else {
      setFieldValue(nameInput("referenciaConvenioColaboracion", "tipoCambioPagado"), '');
      setFormattedTipoCambioPagadoConvenio('');
    }
  };

  const nameInput = useCallback(
    (model, name,) => `notapagos[${index}].${model}.${name}`,
    [index]
  );
  const setFileText = useCallback((fileName, name) => {
    const fileInput = document.getElementById(name);
    const dataTransfer = new DataTransfer();
    fileInput.files = dataTransfer.files;

    if (fileName)
      setTimeout(() => {
        const file = new File([""], fileName, {
          type: "text/plain",
        });
        dataTransfer.items.add(file);

        fileInput.files = dataTransfer.files;
      }, [200]);
    return null;
  }, []);

  useEffect(() => {
    const pdfFileNamePago = values.notapagos[index]?.referenciaPagoModel?.pdfFile?.name || '';
    if (pdfFileNamePago) {
      setFileText(pdfFileNamePago, nameInput("referenciaPagoModel", "pdfFile"));
    }
  }, []);

  useEffect(() => {
    const pdfFileNameConvenio = values.notapagos[index]?.referenciaConvenioColaboracion?.pdfFile?.name || '';
    if (pdfFileNameConvenio) {
      setFileText(pdfFileNameConvenio, nameInput("referenciaConvenioColaboracion", "pdfFile"));
    }
  }, []);

  useEffect(() => {
    const formattedTipoCambioPagado = formatCurrency(notapago.referenciaPagoModel.tipoCambioPagado, 4);
    const formattedPagadoNAFIN = formatCurrency(notapago.referenciaPagoModel.pagadoNAFIN);
    const formattedTipoCambioPagadoConvenio = formatCurrency(notapago.referenciaConvenioColaboracion.tipoCambioPagado, 4);
    const formattedPagadoNAFINConvenio = formatCurrency(notapago.referenciaConvenioColaboracion.pagadoNAFIN);

    setFormattedTipoCambioPagado(formattedTipoCambioPagado);
    setFormattedPagadoNAFIN(formattedPagadoNAFIN);
    setFormattedTipoCambioPagadoConvenio(formattedTipoCambioPagadoConvenio);
    setFormattedPagadoNAFINConvenio(formattedPagadoNAFINConvenio);

    setFieldValue(nameInput("referenciaPagoModel", "tipoCambioPagado"), notapago.referenciaPagoModel.tipoCambioPagado);
    setFieldValue(nameInput("referenciaPagoModel", "pagadoNAFIN"), notapago.referenciaPagoModel.pagadoNAFIN);
    setFieldValue(nameInput("referenciaConvenioColaboracion", "tipoCambioPagado"), notapago.referenciaConvenioColaboracion.tipoCambioPagado);
    setFieldValue(nameInput("referenciaConvenioColaboracion", "pagadoNAFIN"), notapago.referenciaConvenioColaboracion.pagadoNAFIN);

    if (!notapago.referenciaPagoModel.desglose) {
      setFieldValue(nameInput("referenciaPagoModel", "desglose"), 1);
    }
    if (!notapago.referenciaConvenioColaboracion?.desglose) {
      setFieldValue(nameInput("referenciaConvenioColaboracion", "desglose"), 2);
    }
  }, []);


  const handleFileChange = (event, model) => {
    const file = event.target.files[0];
    if (file) {
      if (file.type === "application/pdf") {
        const reader = new FileReader();
        reader.onload = (e) => {
          const base64String = e.target.result.split(",")[1];
          setFieldValue(nameInput(model, "pdfFile"), {
            archivo: base64String,
            nombreArchivo: file.name,
          });
          setFieldValue(nameInput(model, "pdfName"), file.name);

        };

        reader.readAsDataURL(file);
      } else {
        showMessage(NOTA_PAGO.MSG004);
        setFieldValue(nameInput(model, "pdfFile"), null);
        event.target.value = null;
      }
    }
  };

  return (
    <>
      <Row className="mb-3">
        <Col md={2}>
          <strong>Factura {numeroIncremental}</strong>
        </Col>
      </Row>

      <Row>
        <Col md={4}>
          <TextField
            label="Folio*:"
            name={nameInput("referenciaPagoModel", "folio")}
            value={notapago.referenciaPagoModel.folio}
            onBlur={handleBlur}
            disabled

          />
        </Col>
        <Col md={4}>
          <Select
            label="Desglose*:"
            name={nameInput("referenciaPagoModel", "desglose")}
            value={notapago.referenciaPagoModel.desglose}
            disabled={desboqueo}
            onBlur={handleBlur}
            onChange={(e) =>
              setFieldValue(nameInput("referenciaPagoModel", "desglose"), e.target.value)
            }
            options={catalogoDesgloce}
            keyValue="idDesgloce"
            keyTextValue="nombre"
            className={
              touched?.notapagos?.[index]?.referenciaPagoModel?.desglose &&
              (errors?.notapagos?.[index]?.referenciaPagoModel?.desglose ? 'is-invalid' : 'is-valid')
            }
            helperText={
              touched?.notapagos?.[index]?.referenciaPagoModel?.desglose
                ? errors?.notapagos?.[index]?.referenciaPagoModel?.desglose
                : ''
            }
          />
        </Col>
        <Col md={4}>
          <TextField
            label="Folio de ficha de pago*:"
            name={nameInput("referenciaPagoModel", "folioFichaPago")}
            value={notapago.referenciaPagoModel.folioFichaPago}
            onBlur={handleBlur}
            disabled={desboqueo}
            onChange={(e) =>
              setFieldValue(nameInput("referenciaPagoModel", "folioFichaPago"), e.target.value)
            }
            className={
              touched?.notapagos?.[index]?.referenciaPagoModel?.folioFichaPago &&
              (errors?.notapagos?.[index]?.referenciaPagoModel?.folioFichaPago ? 'is-invalid' : 'is-valid')
            }
            helperText={
              touched?.notapagos?.[index]?.referenciaPagoModel?.folioFichaPago
                ? errors?.notapagos?.[index]?.referenciaPagoModel?.folioFichaPago
                : ''
            }
          />
        </Col>
        <Col md={4}>
          <TextFieldDate
            label="Fecha de pago*:"
            name={nameInput("referenciaPagoModel", "fechaPago")}
            value={notapago.referenciaPagoModel.fechaPago}
            onBlur={handleBlur}
            disabled={desboqueo}
            onChange={(e) =>
              setFieldValue(nameInput("referenciaPagoModel", "fechaPago"), e.target.value)
            }
            className={
              touched?.notapagos?.[index]?.referenciaPagoModel?.fechaPago &&
              (errors?.notapagos?.[index]?.referenciaPagoModel?.fechaPago ? 'is-invalid' : 'is-valid')
            }
            helperText={
              touched?.notapagos?.[index]?.referenciaPagoModel?.fechaPago
                ? errors?.notapagos?.[index]?.referenciaPagoModel?.fechaPago
                : ''
            }
          />
        </Col>
        <Col md={4}>
          <TextFieldWithIconLeft
            label="Tipo de cambio pagado:"
            startIcon={faDollarSign}
            name={nameInput("referenciaPagoModel", "tipoCambioPagado")}
            value={formattedTipoCambioPagado}
            onBlur={handleBlurFormatTipoCambioPagado}
            disabled={desboqueo || notapago.referenciaPagoModel?.moneda === "MXN"}
            onChange={(e) => {
              let inputValue = e.target.value;
              inputValue = inputValue.replace(/[^\d.,]/g, '');
              const parts = inputValue.split('.');
              if (parts.length > 2) {
                inputValue = parts[0] + '.' + parts.slice(1).join('');
              }
              if (parts.length === 2 && parts[1].length > 4) {
                inputValue = parts[0] + '.' + parts[1].slice(0, 4);
              }
              setFormattedTipoCambioPagado(inputValue);
              setFieldValue(nameInput("referenciaPagoModel", "tipoCambioPagado"), inputValue);
            }}
          />
        </Col>
        <Col md={4}>
          <TextFieldWithIconLeft
            label="Pagado NAFIN*:"
            startIcon={faDollarSign}
            name={nameInput("referenciaPagoModel", "pagadoNAFIN")}
            value={formattedPagadoNAFIN}
            onBlur={handleBlurFormatPagadoNAFIN}
            disabled={desboqueo}
            onChange={(e) => {
              let inputValue = e.target.value;
              inputValue = inputValue.replace(/[^\d.,]/g, '');
              const parts = inputValue.split('.');
              if (parts.length > 2) {
                inputValue = parts[0] + '.' + parts.slice(1).join('');
              }
              if (parts.length === 2 && parts[1].length > 2) {
                inputValue = parts[0] + '.' + parts[1].slice(0, 2);
              }
              setFormattedPagadoNAFIN(inputValue);
              setFieldValue(nameInput("referenciaPagoModel", "pagadoNAFIN"), inputValue);
            }}
            className={
              touched?.notapagos?.[index]?.referenciaPagoModel?.pagadoNAFIN &&
              (errors?.notapagos?.[index]?.referenciaPagoModel?.pagadoNAFIN ? 'is-invalid' : 'is-valid')
            }
            helperText={
              touched?.notapagos?.[index]?.referenciaPagoModel?.pagadoNAFIN
                ? errors?.notapagos?.[index]?.referenciaPagoModel?.pagadoNAFIN
                : ''
            }
          />
        </Col>
        <Col md={4}>
          <FileField
            label="Ficha NAFIN*:"
            name={nameInput("referenciaPagoModel", "pdfFile")}
            accept="application/pdf"
            disabled={desboqueo}
            onChange={(e) => handleFileChange(e, "referenciaPagoModel")}
            className={
              touched?.notapagos?.[index]?.referenciaPagoModel?.pdfFile &&
              (errors?.notapagos?.[index]?.referenciaPagoModel?.pdfFile ? 'is-invalid' : 'is-valid')
            }
            helperText={
              touched?.notapagos?.[index]?.referenciaPagoModel?.pdfFile
                ? errors?.notapagos?.[index]?.referenciaPagoModel?.pdfFile
                : ''
            }
            value={notapago.referenciaPagoModel?.pdfFile || null}
          />
        </Col>

      </Row>

      {referenciaConvenioColaboracionState && notapago.referenciaConvenioColaboracion && (
        <Row className="mt-4">
          <Col md={4}>
            <TextField
              label="Folio*:"
              name={nameInput("referenciaConvenioColaboracion", "folio")}
              value={notapago.referenciaConvenioColaboracion.folio}
              onBlur={handleBlur}
              disabled
            />
          </Col>
          <Col md={4}>
            <Select
              label="Desglose*:"
              name={nameInput("referenciaConvenioColaboracion", "desglose")}
              value={notapago.referenciaConvenioColaboracion.desglose}
              onBlur={handleBlur}
              onChange={(e) =>
                setFieldValue(nameInput("referenciaConvenioColaboracion", "desglose"), e.target.value)
              }
              options={catalogoDesgloce}
              keyValue="idDesgloce"
              disabled={desboqueo}
              keyTextValue="nombre"
              className={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.desglose &&
                (errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.desglose ? 'is-invalid' : 'is-valid')
              }
              helperText={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.desglose
                  ? errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.desglose
                  : ''
              }
            />
          </Col>
          <Col md={4}>
            <TextField
              label="Folio de ficha de pago*:"
              name={nameInput("referenciaConvenioColaboracion", "folioFichaPago")}
              value={notapago.referenciaConvenioColaboracion.folioFichaPago}
              onBlur={handleBlur}
              disabled={desboqueo}
              onChange={(e) =>
                setFieldValue(nameInput("referenciaConvenioColaboracion", "folioFichaPago"), e.target.value)
              }
              className={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.folioFichaPago &&
                (errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.folioFichaPago ? 'is-invalid' : 'is-valid')
              }
              helperText={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.folioFichaPago
                  ? errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.folioFichaPago
                  : ''
              }
            />
          </Col>
          <Col md={4}>
            <TextFieldDate
              label="Fecha de pago*:"
              name={nameInput("referenciaConvenioColaboracion", "fechaPago")}
              value={notapago.referenciaConvenioColaboracion.fechaPago}
              onBlur={handleBlur}
              disabled={desboqueo}
              onChange={(e) =>
                setFieldValue(nameInput("referenciaConvenioColaboracion", "fechaPago"), e.target.value)
              }
              className={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.fechaPago &&
                (errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.fechaPago ? 'is-invalid' : 'is-valid')
              }
              helperText={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.fechaPago
                  ? errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.fechaPago
                  : ''
              }
            />
          </Col>

          <Col md={4}>
            <TextFieldWithIconLeft
              label="Tipo de cambio pagado:"
              startIcon={faDollarSign}
              name={nameInput("referenciaConvenioColaboracion", "tipoCambioPagado")}
              value={formattedTipoCambioPagadoConvenio}
              onBlur={handleBlurFormatTipoCambioPagadoConvenio}
              disabled={desboqueo || notapago.referenciaConvenioColaboracion?.moneda === "MXN"}
              onChange={(e) => {
                let inputValue = e.target.value;
                inputValue = inputValue.replace(/[^\d.,]/g, '');
                const parts = inputValue.split('.');
                if (parts.length > 2) {
                  inputValue = parts[0] + '.' + parts.slice(1).join('');
                }
                if (parts.length === 2 && parts[1].length > 4) {
                  inputValue = parts[0] + '.' + parts[1].slice(0, 4);
                }
                setFormattedTipoCambioPagadoConvenio(inputValue);
                setFieldValue(nameInput("referenciaConvenioColaboracion", "tipoCambioPagado"), inputValue);
              }}
            />
          </Col>
          <Col md={4}>
            <TextFieldWithIconLeft
              label="Pagado NAFIN*:"
              startIcon={faDollarSign}
              name={nameInput("referenciaConvenioColaboracion", "pagadoNAFIN")}
              value={formattedPagadoNAFINConvenio}
              onBlur={handleBlurFormatPagadoNAFINConvenio}
              disabled={desboqueo}
              onChange={(e) => {
                let inputValue = e.target.value;
                inputValue = inputValue.replace(/[^\d.,]/g, '');
                const parts = inputValue.split('.');
                if (parts.length > 2) {
                  inputValue = parts[0] + '.' + parts.slice(1).join('');
                }
                if (parts.length === 2 && parts[1].length > 2) {
                  inputValue = parts[0] + '.' + parts[1].slice(0, 2);
                }
                setFormattedPagadoNAFINConvenio(inputValue);
                setFieldValue(nameInput("referenciaConvenioColaboracion", "pagadoNAFIN"), inputValue);
              }}
              className={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.pagadoNAFIN &&
                (errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.pagadoNAFIN ? 'is-invalid' : 'is-valid')
              }
              helperText={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.pagadoNAFIN
                  ? errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.pagadoNAFIN
                  : ''
              }
            />
          </Col>
          <Col md={4}>
            <FileField
              label="Ficha NAFIN*:"
              name={nameInput("referenciaConvenioColaboracion", "pdfFile")}
              accept="application/pdf"
              disabled={desboqueo}
              onChange={(e) => handleFileChange(e, "referenciaConvenioColaboracion")}
              className={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.pdfFile &&
                (errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.pdfFile ? 'is-invalid' : 'is-valid')
              }
              helperText={
                touched?.notapagos?.[index]?.referenciaConvenioColaboracion?.pdfFile
                  ? errors?.notapagos?.[index]?.referenciaConvenioColaboracion?.pdfFile
                  : ''
              }
              value={notapago.referenciaPagoModel?.pdfFile || null}
            />
          </Col>

        </Row>
      )}
    </>
  );
}
