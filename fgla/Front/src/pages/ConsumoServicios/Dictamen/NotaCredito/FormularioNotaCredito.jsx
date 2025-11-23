import React, { useEffect, useState, useCallback } from "react";
import { Col, Row, Button, Container } from "react-bootstrap";
import Accordion from "../../../../components/Accordion";
import { FileFieldArray } from "../../../../extraComponents/formInputsArray/FileFieldArray";
import { TextFieldDateArray } from "../../../../extraComponents/formInputsArray/TextFieldDateArray";
import { TextFieldArray } from "../../../../extraComponents/formInputsArray/TextFieldArray";
import { TextFieldIconArray } from "../../../../extraComponents/formInputsArray/TextFieldIconArray";
import { TextAreadArray } from "../../../../extraComponents/formInputsArray/TextAreaArray";
import { SelectArray } from "../../../../extraComponents/formInputsArray/SelectArray";
import { useFormikContext, getIn } from "formik";
import { NOTA_CREDITO } from "../../../../constants/messages";
import {
  FormatNotaCredito,
  FormatMoney,
} from "./Components/NotaCreditoUtilities";
import _ from "lodash";
export default function FormularioNotaCredito({
  estatusCat,
  monedaCat,
  tasaCat,
  index,
  formularioActivo = false,
  handleShowMessage,
  handleShowConfirmModal,
  arrayHelpers,
  idContrato,
  idProveedor,
  idDictamen,
  leerXml,
  aplicaCC,
  tipoCambio = 1,
}) {
  //#region  Values
  const nameInput = useCallback(
    (name) => `notasCredito[${index}].${name}`,
    [index]
  );
  const { values, setFieldValue, setFieldTouched, handleBlur, isSubmitting } =
    useFormikContext();

  let {
    numeroNotaCredito,
    idNotaCredito,
    archivoXML,
    xmlCargado,
    pdfCargado,
    xmlLeido,
    totalValue,
    montoSatValue,
    montoCCValue,
    nombreXML = "",
    nombrePdf = "",
    archivoPdf,
    idEstatusNotaCredito,
    notaActiva = false,
  } = values.notasCredito[index];

  const getStatusLabel = (idEstatusNotaCredito, estatusCat) => {
    if (idEstatusNotaCredito && !_.isEmpty(estatusCat)) {
      let estatusOption = estatusCat.find(
        (s) => s.primaryKey == idEstatusNotaCredito
      );
      if (estatusOption) {
        return estatusOption.nombre;
      }
    }
  };

  //#endregion

  const setFileText = useCallback((fileName, name) => {
    // setFieldTouched(name, true);
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
    setFileText(nombreXML, nameInput("xmlCargado"));
  }, [nameInput, nombreXML, setFileText]);
  useEffect(() => {
    setFileText(nombrePdf, nameInput("pdfCargado"));
  }, [nameInput, nombrePdf, setFileText]);

  //#region Actions
  const cargarXML = (event) => {
    let { files } = event.target;
    if (!_.isEmpty(files)) {
      const file = files[0];
      let { name, type } = file;
      if (file && type === "text/xml") {
        setFieldValue(nameInput("archivoXML"), file);
        setFieldValue(nameInput("nombreXML"), name);
        setFieldValue(nameInput("xmlCargado"), true);
        setFieldValue(nameInput("pdfCargado"), false);
        setFieldValue(nameInput("xmlLeido"), false);
      } else {
        handleShowMessage(NOTA_CREDITO.MSG005);
      }
    }
  };

  const handleLeerXML = () => {
    const ValidacionXML = () => {
      let data = {
        archivoXml: archivoXML,
        idContrato,
        idProveedor,
        idDictamen: idDictamen,
        seccion: "Nota",
      };

      leerXml(data).then((response) => {
        if (response?.error) {
          let mensaje = NOTA_CREDITO.MSG015;
          if (!_.isEmpty(response?.error?.data?.mensaje)) {
            mensaje = response.error.data.mensaje[0];
          }
          handleShowMessage(mensaje);
        } else {
          let { data } = response.data;
          let _values = FormatNotaCredito(
            data,
            index,
            monedaCat,
            tasaCat,
            estatusCat,
            aplicaCC
          );
          arrayHelpers.replace(index, {
            ...values[index],
            ..._values,
            xmlCargado,
            archivoXML,
            nombreXML,
            pdfCargado,
            nombrePdf,
            archivoPdf,
            xmlLeido: true,
            notaActiva,
            numeroNotaCredito,
            idNotaCredito,
          });
        }
      });
    };

    if (idNotaCredito) {
      handleShowConfirmModal(NOTA_CREDITO.MSG002, ValidacionXML);
    } else {
      ValidacionXML();
    }
  };
  const cargarPDF = (event) => {
    let { files } = event.target;
    if (!_.isEmpty(files)) {
      const file = files[0];

      if (file) {
        let { name } = file;
        setFieldValue(nameInput("archivoPdf"), file);
        setFieldValue(nameInput("nombrePdf"), name);
        setFieldValue(nameInput("pdfCargado"), true);
      }
    }
  };

  //#endregion

  //#region Validar Montos
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

  const UpdateMonto = useCallback(
    (isMontoSat) => {
      const setMontoSat = (montoSatValue) => {
        let porcenaje =
          (parseFloat(montoSatValue) / parseFloat(totalValue)) * 100;
        porcenaje = porcenaje.toFixed(2);

        let name = nameInput("porcentajeSat");
        let nameValue = nameInput("porcentajeSatValue");

        setFieldValue(name, porcenaje);
        setFieldValue(nameValue, porcenaje);

        if (aplicaCC) {
          let _tipoCambio = tipoCambio ? tipoCambio : 1;
          let montoPesosSatValue = montoSatValue * _tipoCambio;
          let nameValue = nameInput("montoPesosSatValue");
          let name = nameInput("montoPesosSat");

          setFieldValue(name, FormatMoney(montoPesosSatValue));
          setFieldValue(nameValue, montoPesosSatValue);
        }
      };

      const setMontoCC = (montoCCValue) => {
        let porcenaje =
          (parseFloat(montoCCValue) / parseFloat(totalValue)) * 100;
        porcenaje = porcenaje.toFixed(2);

        let name = nameInput("porcentajeCC");
        let nameValue = nameInput("porcentajeCCValue");

        setFieldValue(name, porcenaje);
        setFieldValue(nameValue, porcenaje);

        if (aplicaCC) {
          let _tipoCambio = tipoCambio ? tipoCambio : 1;
          let montoPesosCCValue = montoCCValue * _tipoCambio;
          let nameValue = nameInput("montoPesosCCValue");
          let name = nameInput("montoPesosCC");

          setFieldValue(name, FormatMoney(montoPesosCCValue));
          setFieldValue(nameValue, montoPesosCCValue);
        }
      };

      if (isMontoSat && totalValue && montoSatValue) {
        setMontoSat(montoSatValue);
        if (aplicaCC) {
          let _montoCCValue = Math.max(totalValue - montoSatValue, 0).toFixed(
            2
          );
          let name = nameInput("montoCC");
          let nameValue = nameInput("montoCCValue");
          setFieldValue(nameValue, _montoCCValue);
          setFieldValue(name, FormatMoney(_montoCCValue));
          setMontoCC(_montoCCValue);
        }
      }

      if (!isMontoSat && totalValue && montoCCValue) {
        setMontoCC(montoCCValue);
        if (aplicaCC) {
          let _montoSatValue = Math.max(totalValue - montoCCValue, 0).toFixed(
            2
          );
          let name = nameInput("montoSat");
          let nameValue = nameInput("montoSatValue");
          setFieldValue(nameValue, _montoSatValue);
          setFieldValue(name, FormatMoney(_montoSatValue));

          setMontoSat(_montoSatValue);
        }
      }
    },
    [
      montoCCValue,
      montoSatValue,
      nameInput,
      setFieldValue,
      totalValue,
      aplicaCC,
      tipoCambio,
    ]
  );

  const [lastValues, setLastValues] = useState(null);
  useEffect(() => {
    if (values != lastValues) {
      setLastValues(values);
    }
  }, [lastValues, values]);

  const ValidMonto = (name) => {
    let totalValue = getIn(values, nameInput("totalValue"));
    let montoValue = getIn(values, name + "Value");

    if (parseFloat(montoValue) > parseFloat(totalValue)) {
      handleShowMessage(NOTA_CREDITO.MSG010);
      return false;
    }
    return true;
  };

  const handleBlurMonto = (event, decimales = 2) => {
    var { value, name } = event.target;
    if (value) {
      let formatMoney = FormatMoney(value, decimales);
      setFieldValue(name, formatMoney);
    }

    setFieldTouched(name, true);
    handleBlur(event);
    // let validMonto = ValidMonto(name);
    // if (validMonto) {
    UpdateMonto(name.includes("montoSat"));
    // }
  };

  const handleChangeMonto = (event) => {
    var { value, name } = event.target;
    setFieldValue(name, filtrarNumeros(value));
    setFieldValue(name + "Value", filtrarNumeros(value).replaceAll(",", ""));
    setFieldTouched(name, true);
  };
  //#endregion

  return (
    <React.Fragment key={index}>
      <Row className="mb-3">
        <Col md={2}>
          <strong>No {numeroNotaCredito}</strong>
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          <Row>
            <FileFieldArray
              label={"Archivo a cargar*:"}
              md={9}
              disabled={!formularioActivo}
              accept="text/xml"
              name={nameInput("xmlCargado")}
              onChange={cargarXML}
              showClasses={notaActiva}
            />
            <Col md={3}>
              <div style={{ marginBottom: "0.5rem" }}>‎ </div>
              <Button
                variant="gray"
                className="btn-sm ms-2 waves-effect waves-light"
                style={{ padding: "0.275rem 0.55rem" }}
                type="button"
                disabled={!formularioActivo || !archivoXML}
                onClick={handleLeerXML}
              >
                Leer XML
              </Button>
            </Col>
          </Row>
        </Col>
        <FileFieldArray
          label={"Añadir PDF*:"}
          disabled={!formularioActivo || !xmlLeido}
          accept={"application/pdf"}
          name={nameInput("pdfCargado")}
          onChange={cargarPDF}
          md={4}
          showClasses={notaActiva}
        />
        <TextFieldArray
          label={"Folio*:"}
          disabled={true}
          name={nameInput("folio")}
          maxLength={18}
          showClasses={notaActiva}
          md={4}
        />{" "}
      </Row>
      <Row>
        <TextFieldArray
          label={"Comprobante fiscal*:"}
          disabled={true}
          name={nameInput("comprobanteFiscal")}
          maxLength={40}
          md={4}
          showClasses={notaActiva}
        />{" "}
        <TextFieldDateArray
          label={"Fecha de generacion*:"}
          disabled={true}
          name={nameInput("fechaFacturacion")}
          md={4}
          showClasses={notaActiva}
        />{" "}
        <Col md={4}>
          <div>Estatus:</div>
          <div>
            <strong>{getStatusLabel(idEstatusNotaCredito, estatusCat)}</strong>
          </div>
        </Col>
      </Row>
      <Row>
        <SelectArray
          label={"Moneda*:"}
          placeholder={"MXN"}
          disabled={true}
          name={nameInput("idTipoMoneda")}
          options={monedaCat}
          md={4}
          showClasses={notaActiva}
        />
        <SelectArray
          label={"Tasa*:"}
          disabled
          name={nameInput("idTasa")}
          options={tasaCat}
          keyTextValue="porcentaje"
          showClasses={notaActiva}
          md={4}
        />
        <TextFieldIconArray
          label={"Subtotal"}
          disabled={true}
          name={nameInput("subTotal")}
          showClasses={false}
          md={4}
        />
      </Row>
      <Row>
        <TextFieldIconArray
          label={"IEPS*:"}
          disabled={true}
          name={nameInput("ieps")}
          md={4}
          showClasses={notaActiva}
        />
        <TextFieldIconArray
          label={"IVA*:"}
          disabled={true}
          name={nameInput("iva")}
          md={4}
          showClasses={notaActiva}
        />
        <TextFieldIconArray
          label={"Otros impuestos:"}
          disabled={true}
          name={nameInput("otrosImpuestos")}
          showClasses={false}
          md={4}
        />
      </Row>
      <Row>
        <TextFieldIconArray
          label={"Total:"}
          disabled={true}
          name={nameInput("total")}
          showClasses={false}
          md={4}
        />
        <TextFieldIconArray
          label={"Total en pesos:"}
          disabled={true}
          name={nameInput("totalPesos")}
          showClasses={false}
          md={4}
        />
      </Row>
      <Row>
        <TextAreadArray
          label={"Comentarios:"}
          disabled={!formularioActivo}
          name={nameInput("comentarios")}
          showClasses={false}
          maxLength={500}
          md={4}
        />
      </Row>

      <Row>
        <Container className="mt-3 px-3">
          <Accordion
            showChevron={false}
            title="Desglose de montos"
            className={"shadow-none"}
            show={true}
            collapse={false}
          >
            <Row>
              <TextFieldArray
                label={"% SAT:"}
                disabled={true}
                name={nameInput("porcentajeSat")}
                md={4}
                showClasses={false}
              />
              <TextFieldIconArray
                label={"Monto*:"}
                name={nameInput("montoSat")}
                disabled={!formularioActivo}
                onBlur={handleBlurMonto}
                onChange={handleChangeMonto}
                md={4}
                showClasses={notaActiva}
              />
              <TextFieldIconArray
                label={"Monto en pesos*:"}
                disabled={true}
                name={nameInput("montoPesosSat")}
                md={4}
                showClasses={notaActiva}
              />
            </Row>
            {aplicaCC && (
              <Row>
                <TextFieldArray
                  label={"% Convenio de Colaboración:"}
                  disabled={true}
                  name={nameInput("porcentajeCC")}
                  md={4}
                  showClasses={false}
                />

                <TextFieldIconArray
                  label={"Monto*:"}
                  disabled={!formularioActivo}
                  md={4}
                  onBlur={handleBlurMonto}
                  onChange={handleChangeMonto}
                  name={nameInput("montoCC")}
                  showClasses={notaActiva}
                />

                <TextFieldIconArray
                  label={"Monto en pesos*:"}
                  disabled={true}
                  name={nameInput("montoPesosCC")}
                  md={4}
                  showClasses={notaActiva}
                />
              </Row>
            )}
          </Accordion>
        </Container>
      </Row>
    </React.Fragment>
  );
}
