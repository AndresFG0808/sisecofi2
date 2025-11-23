import React, { useEffect, useState } from "react";
import { Formik } from "formik";
import { MainTitle, Loader } from "../../../components";
import { CreateValidationSchema } from "./components/ValidationSchema";
import { Container, Row, Col, Button, Form } from "react-bootstrap";
import FormDatosGenerales from "./IngresoDatos/FormDatosGenerales";
import FormDatosMonetarios from "./IngresoDatos/FormDatosFinancieros";
import EstructuraDocumentalTable2 from "./IngresoDatos/EstructuraDocumentalTable2";
import { useDispatch, useSelector } from "react-redux";
import { InitialValues } from "./components/InputsComponents";
import "../../../store/catalogos/catalogosSlice";
import {
  GetInfoComite,
  UpdateComite,
  GetInfoComites,
  GetDetalleProyecto,
} from "../../../store/infoComites/infoComitesActions";
import { ADMINISTRAR_INFO_COMITES } from "../../../constants/messages";
import { useToast } from "../../../hooks/useToast";
import { Modal } from "react-bootstrap";

export default function DetalleComite({
  idComiteProyecto,

  disableEdit = true,
  handleClose,
  handleCloseConfirm,
  showMessage,
}) {
  var dispatch = useDispatch();
  const { errorToast } = useToast();
  const [isLoading, setIsLoading] = useState(false);
  const infoComites = useSelector((state) => state.infoComites);
  const catalogos = useSelector((state) => state.catalogos);

  const { proyecto } = useSelector((state) => state.proyectos);

  const [idProyecto, setIdProyecto] = useState(null);

  useEffect(() => {
    if (proyecto?.idProyecto) {
      setIdProyecto(proyecto.idProyecto);
    } else if (proyecto?.proyecto?.idProyecto) {
      setIdProyecto(proyecto.proyecto.idProyecto);
    }
  }, [proyecto]);

  var { infoComite } = infoComites;
  var { plantillaInfo } = infoComites;

  const [valoresIniciales, setValoresIniciales] = useState(InitialValues());

  function cutText(text, word) {
    if (typeof text !== "string" || typeof word !== "string") {
      throw new Error("Both parameters must be strings.");
    }

    if (text.length === 0 || word.length === 0) {
      return text; // If the text or the word is empty, return the complete text.
    }

    const wordIndex = text.toLowerCase().lastIndexOf(word.toLowerCase());
    if (wordIndex !== -1) {
      const endWord = wordIndex;
      return text.substring(0, endWord);
    } else {
      return text; // If the word is not found, return the complete text.
    }
  }
  //#region GetInfo comite
  useEffect(() => {
    if (idComiteProyecto) {
      setIsLoading(true)
      dispatch(GetInfoComite(idComiteProyecto)).then((response) => {
        setIsLoading(false)
        if (response?.error) {
          handleClose();
          showMessage(ADMINISTRAR_INFO_COMITES.MSG008);
        }
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const formatDate = (dateStr) => {
    try {
      let dateObj = new Date(dateStr);

      dateObj.setDate(dateObj.getDate());

      let yyyy = dateObj.getFullYear();
      let mm = String(dateObj.getMonth() + 1).padStart(2, "0"); // Los meses son base 0
      let dd = String(dateObj.getDate()).padStart(2, "0");

      return `${yyyy}-${mm}-${dd}`;
    } catch (e) {}
    return dateStr;
  };
  function formatNumberWithDecimals(number, decimals) {
    try {
      let formattedNumber = number.toFixed(decimals);
      return formattedNumber;
    } catch (error) {
      return "";
    }
  }
  const FormatMoney = (value, decimales) => {
    if (value) {
      value = "" + value;
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
    }
    return "";
  };

  const [comitePath, setComitePath] = useState(null);
  useEffect(() => {
    const Validateafectacion = (idsAfectacion, afectacion) => {
      let result = {};
      if (Array.isArray(afectacion) && Array.isArray(idsAfectacion)) {
        afectacion.forEach((item, index) => {
          let isChecked = idsAfectacion.find((s) => s === item.primaryKey);
          result[`afectacion${index}`] = !!isChecked;
        });
        return result;
      }
    };
    if (
      infoComite &&
      infoComite?.data?.informacionComiteProyecto?.idComiteProyecto ===
        idComiteProyecto
    ) {
      var {
        informacionComiteProyecto,
        idsAfectacion,
        informacionArchivos,
        asociasiones,
      } = infoComite.data;

      if (informacionArchivos && informacionArchivos.length > 0) {
        let { carpeta } = informacionArchivos[0];
        setComitePath(cutText(carpeta, "/"));
      }
      let {
        montoAutorizado,
        tipoCambio,
        monto,
        idSesionNumero,
        idSesionClasificacion,
      } = informacionComiteProyecto;
      let _informacionComiteProyecto = {
        ...informacionComiteProyecto,

        montoAutorizadoValue: formatNumberWithDecimals(montoAutorizado, 2),
        montoAutorizado: FormatMoney(montoAutorizado, 2),
        tipoCambioValue: formatNumberWithDecimals(tipoCambio, 4),
        tipoCambio: FormatMoney(tipoCambio, 4),
        montoValue: formatNumberWithDecimals(monto, 2),
        monto: FormatMoney(monto, 2),
        idSesionNumero: idSesionNumero || "",
        idSesionClasificacion: idSesionClasificacion || "",
        informacionArchivos: informacionArchivos,
        fechaSesion: formatDate(informacionComiteProyecto?.fechaSesion),
      };
      if (catalogos?.catalogos?.afectacion) {
        _informacionComiteProyecto = {
          ..._informacionComiteProyecto,
          ...Validateafectacion(idsAfectacion, catalogos.catalogos.afectacion),
          idsAfectacion: idsAfectacion,
        };
      }
      if (asociasiones) {
        _informacionComiteProyecto = {
          ..._informacionComiteProyecto,
          idPlantilla: asociasiones.idPlantillaVigente,
        };
      }
      setValoresIniciales(_informacionComiteProyecto);
    }
  }, [infoComite, catalogos, idComiteProyecto]);

  function convertirFechaISO(fechaSimple) {
    const fecha = new Date(fechaSimple);
    fecha.setUTCHours(0, 0, 0, 0);
    return fecha.toISOString();
  }
  //#endregion

  const onSubmit = (values, { resetForm }) => {
    setIsLoading(true);
    let { montoValue, tipoCambioValue, montoAutorizadoValue, fechaSesion } =
      values;
    const validateNumber = (number, decimals = 2) =>
      number
        ? formatNumberWithDecimals(parseFloat(number, decimals), decimals)
        : "";

    let _values = {
      ...values,
      monto: validateNumber(montoValue),
      tipoCambio: validateNumber(tipoCambioValue, 4),
      montoAutorizado: validateNumber(montoAutorizadoValue),
      fechaSesion: fechaSesion ? convertirFechaISO(fechaSesion) : "",
    };
    if (!disableEdit) {
      dispatch(UpdateComite(_values))
        .then((response) => {
          setIsLoading(false);
          if (response?.error) {
            showMessage(ADMINISTRAR_INFO_COMITES.MSG007);
          } else {
            // showMessage(ADMINISTRAR_INFO_COMITES.MSG007);
            dispatch(GetInfoComites(idProyecto));
            dispatch(GetDetalleProyecto(idProyecto))
          }
        })
        .catch((error) => {
          setIsLoading(false);
          showMessage(ADMINISTRAR_INFO_COMITES.MSG007);
        });
    }
  };

  const [validationSchema, setValidationSchema] = useState(
    CreateValidationSchema(catalogos)
  );
  useEffect(() => {
    setValidationSchema(CreateValidationSchema(catalogos));
  }, [catalogos]);
  return (
    <Formik
      initialValues={valoresIniciales}
      enableReinitialize={true}
      validationSchema={validationSchema}
      onSubmit={onSubmit}
      validateOnMount={true}
    >
      {({
        handleSubmit,
        handleChange,
        handleBlur,
        resetForm,
        values,
        errors,
        isValid,
        isSubmitting,
        setFieldValue,
        setSubmitting,
        setFieldTouched,
      }) => {
        function isEmptyObject(obj) {
          return Object.keys(obj).length === 0;
        }
        // if (isSubmitting && !isEmptyObject(errors)) {
        //   setSubmitting(false);
        //   showMessage(ADMINISTRAR_INFO_COMITES.MSG001);
        // }
        // console.log(errors);

        let { idPlantilla, informacionArchivos } = values;

        let hasDocuments =
          Array.isArray(informacionArchivos) && informacionArchivos.length > 0;
        let initialValuePlantilla = valoresIniciales?.idPlantilla ?? "";
        let ValuePlantilla = idPlantilla ?? "";
        if (hasDocuments && initialValuePlantilla !== ValuePlantilla) {
          showMessage(ADMINISTRAR_INFO_COMITES.MSG011);
          setFieldValue("idPlantilla", initialValuePlantilla);
        }
        return (
          <>
            {isLoading && <Loader />}
            <Modal.Body>
              <Container className="mt-3 px-3">
                <MainTitle title="General" />
                <FormDatosGenerales
                  disableForm={disableEdit}
                  isDetalleComite={true}
                  catalogos={catalogos}
                  infoComites={infoComites}
                />
              </Container>
              <EstructuraDocumentalTable2
                plantillaInfo={plantillaInfo}
                values={values}
                disableForm={disableEdit}
                disableDocsButtons={disableEdit}
                handleClose={handleClose}
                handleCloseConfirm={handleCloseConfirm}
                showMessage={showMessage}
                isEdit={true}
                comitePath={comitePath}
              />
              <Container className="mt-3 px-3">
                <MainTitle title="Monetario" />
                <FormDatosMonetarios
                  disableForm={disableEdit}
                  catalogos={catalogos}
                />
              </Container>
            </Modal.Body>
            <Modal.Footer>
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <Row>
                  {disableEdit && (
                    <Col md={12} className="text-end">
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="button"
                        onClick={() => handleClose()}
                      >
                        Aceptar
                      </Button>
                    </Col>
                  )}
                  {!disableEdit && (
                    <Col md={12} className="text-end">
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="button"
                        onClick={() => handleCloseConfirm()}
                      >
                        Cancelar
                      </Button>
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="submit"
                        onClick={() => {
                          !isValid &&
                            errorToast(ADMINISTRAR_INFO_COMITES.MSG005);
                        }}
                      >
                        Guardar
                      </Button>
                    </Col>
                  )}
                </Row>
              </Form>
            </Modal.Footer>
          </>
        );
      }}
    </Formik>
  );
}
