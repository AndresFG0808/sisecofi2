import React, { useEffect, useState } from "react";
import { Formik } from "formik";
import { MainTitle, Loader } from "../../../components";
import { Container, Row, Col, Button, Form } from "react-bootstrap";
import FormDatosGenerales from "./IngresoDatos/FormDatosGenerales";
import FormDatosMonetarios from "./IngresoDatos/FormDatosFinancieros";
import EstructuraDocumentalTable2 from "./IngresoDatos/EstructuraDocumentalTable2";
import { CreateValidationSchema } from "./components/ValidationSchema";
import { useToast } from "../../../hooks/useToast";
import { useDispatch, useSelector } from "react-redux";
import {
  CreateComite,
  UpdateComite,
  GetInfoComites,
  GetDetalleProyecto,
} from "../../../store/infoComites/infoComitesActions";
import { InitialValues } from "./components/InputsComponents";
import { ADMINISTRAR_INFO_COMITES } from "../../../constants/messages";
import { Modal } from "react-bootstrap";

export default function IngresoDatos({
  handleClose,
  showMessage,
  handleCloseConfirm,
  idProyecto,
}) {
  var dispatch = useDispatch();
  const { errorToast } = useToast();

  const [isLoading, setIsLoading] = useState(false);

  const infoComites = useSelector((state) => state.infoComites);
  const catalogos = useSelector((state) => state.catalogos);

  var { plantillaInfo } = infoComites;

  function convertirFechaISO(fechaSimple) {
    const fecha = new Date(fechaSimple);
    fecha.setUTCHours(0, 0, 0, 0);
    return fecha.toISOString();
  }

  //#region Submit
  const [disableDocsButtons, setDisableDocsButtons] = useState(true);
  const ActiveDocsButton = () => {
    setDisableDocsButtons(false);
  };
  function formatNumberWithDecimals(number, decimals) {
    try {
      let formattedNumber = number.toFixed(decimals);
      return formattedNumber;
    } catch (error) {
      return "";
    }
  }
  const [idComiteProyecto, setIdComiteProyecto] = useState(null);
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
      idProyecto,
    };

    dispatch(
      values?.idComiteProyecto ? UpdateComite(_values) : CreateComite(_values)
    )
      .then((response) => {
        // showMessage();
        setIsLoading(false);
        if (response?.error) {
          showMessage(ADMINISTRAR_INFO_COMITES.MSG007);
        } else {
          if (response?.payload?.data?.idComiteProyecto) {
            let { idComiteProyecto } = response.payload.data;
            setIdComiteProyecto(idComiteProyecto);
            ActiveDocsButton();
          }
        }
        dispatch(GetInfoComites(idProyecto));
        dispatch(GetDetalleProyecto(idProyecto));
      })
      .catch((error) => {
        setIsLoading(false);
        showMessage(ADMINISTRAR_INFO_COMITES.MSG007);
      });
  };
  //#endregion
  const [initialValues, setinitialValues] = useState(InitialValues());

  useEffect(() => {
    if (Array.isArray(catalogos?.catalogos?.afectacion)) {
      setinitialValues(InitialValues(catalogos.catalogos.afectacion.length));
    }
  }, [catalogos, setinitialValues]);

  const [validationSchema, setValidationSchema] = useState(
    CreateValidationSchema(catalogos)
  );
  useEffect(() => {
    setValidationSchema(CreateValidationSchema(catalogos));
  }, [catalogos]);
  return (
    <Formik
      initialValues={initialValues}
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
        setSubmitting,
        setFieldValue,
      }) => {
        function isEmptyObject(obj) {
          return Object.keys(obj).length === 0;
        }
        // if(isSubmitting && !isEmptyObject(errors)){
        //   setSubmitting(false);
        //   showMessage(ADMINISTRAR_INFO_COMITES.MSG001);
        // }
        if (idComiteProyecto && !values.idComiteProyecto) {
          setFieldValue("idComiteProyecto", idComiteProyecto);
        }
        return (
          <>
            {isLoading && <Loader />}
            <Modal.Body>
              <Container className="mt-3 px-3">
                <MainTitle title="General" />
                <FormDatosGenerales
                  catalogos={catalogos}
                  infoComites={infoComites}
                />
              </Container>
              <EstructuraDocumentalTable2
                plantillaInfo={values?.idPlantilla ? plantillaInfo : null}
                values={values}
                disableDocsButtons={disableDocsButtons}
                handleClose={handleClose}
                handleCloseConfirm={handleCloseConfirm}
                showMessage={showMessage}
                documents={[]}
                catalogos={catalogos}
              />
              {/* <GestionDocumentalTable/> */}
              <Container className="mt-3 px-3">
                <MainTitle title="Monetario" />
                <FormDatosMonetarios catalogos={catalogos} />
              </Container>
            </Modal.Body>
            <Modal.Footer>
              <Row>
                <Form autoComplete="off" onSubmit={handleSubmit}>
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
                        !isValid && errorToast(ADMINISTRAR_INFO_COMITES.MSG005);
                      }}
                    >
                      Guardar
                    </Button>
                  </Col>
                </Form>
              </Row>
            </Modal.Footer>
          </>
        );
      }}
    </Formik>
  );
}
