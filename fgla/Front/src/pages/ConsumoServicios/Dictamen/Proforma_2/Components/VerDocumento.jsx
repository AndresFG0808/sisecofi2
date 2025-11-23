import React, { useState, useEffect } from "react";
import { Button, Modal, Row, Col, Form } from "react-bootstrap";
import Loader from "../../../../../components/Loader";
import Select from "../../../../../components/formInputs/Select";
import {
  DownloadFileBase64,
  Base64ToPdfBlobUrl,
  validMimeTypes,
} from "../../../../../functions/utils/base64.utils";
import {
  useGetPlantillasDdpQuery,
  useLazyGetPlantillasDdpQuery,
  usePostPlantillaDdpMutation,
} from "../store";
import _ from "lodash";
import { Formik, useFormikContext } from "formik";
import * as yup from "yup";
import { PROFORMA } from "../../../../../constants/messages";
import { useToast } from "../../../../../hooks/useToast";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { errorValidations } from "../../../../../functions/utils";

const VerDocumento = ({
  loading,
  show,
  onHide,
  onGuardar,
  vistaPrevia = true,
  idDictamen,
  handleShowMessage,
}) => {
  const { errorToast } = useToast();

  const [title, setTitle] = useState("");
  useEffect(() => {
    setTitle(
      vistaPrevia ? "Vista previa de la proforma" : "Generar proforma"
    );
  }, [vistaPrevia]);

  const [selectedPlantilla, setSelectedPlantilla] = useState("");
  const [selectedFormato, setSelectedFormato] = useState("");
  const [pdfUrl, setPdfUrl] = useState("");
  const { getMessageExists } = useErrorMessages(PROFORMA);
  //#region  Servicios
  const { iLoading: isLoadingPlantillas, data: dataPlantillas } =
    useGetPlantillasDdpQuery();
  const [postReporte, { isLoading: isLoadingReporte }] =
    usePostPlantillaDdpMutation();

  const [plantillaCat, setPlantillaCat] = useState([]);
  useEffect(() => {
    if (!_.isEmpty(dataPlantillas)) {
      setPlantillaCat(dataPlantillas);
    }
  }, [dataPlantillas, setPlantillaCat]);

  //#endregion

  //#region Formulario
  const handleRadioChange = (e, setFieldValue) => {
    let { name, id } = e.target;
    setFieldValue(name, id);
  };

  //#endregion

  const handlePrevisualiza = (values) => {
    postReporte({
      tipoArchivo: "PDF",
      idSubPlantillador: values.idSubPlantillador,
      idDictamen,
      plantilla: false,
    }).then((response) => {
      if (response.error) {
        const error = response.error;
        errorValidations(getMessageExists, error);
      } else {
        let { data } = response;
        setPdfUrl(Base64ToPdfBlobUrl(data));
      }
    });
  };

  const handleGenerarProforma = (values) => {
    postReporte({
      ...values,
      idDictamen,
      plantilla: false,
    }).then((response) => {
      if (response.error) {
        handleShowMessage(PROFORMA.MSG003);
      } else {
        let { data } = response;
        let mimeType = "";
        let fileName = "";

        if (values?.tipoArchivo === "PDF") {
          fileName = "proforma.pdf";
          mimeType = validMimeTypes["pdf"];
        } else {
          fileName = "proforma.xlsx";
          mimeType = validMimeTypes["xlsx"];
        }
        DownloadFileBase64(data, fileName, mimeType);
      }
    });
  };

  const handleSubmit = (values) => {

    let { vistaPrevia } = values;
    if (vistaPrevia) {
      handlePrevisualiza(values);
    } else {
      handleGenerarProforma(values);
    }
  };

  const [etiquetaDictamen, setEtiquetaDictamen] = useState("");
  useEffect(() => {
    if (idDictamen) setEtiquetaDictamen(idDictamen);
  }, [idDictamen]);

  const handleClose = () => {
    setPdfUrl("");
    onHide();
  };

  const validationSchemaVP = vistaPrevia
    ? yup.object().shape({
        idSubPlantillador: yup.string().required("Dato requerido"),
      })
    : yup.object().shape({
        idSubPlantillador: yup.string().required("Dato requerido"),
        tipoArchivo: yup.string().required("Dato requerido"),
      });

  const initialValuesVP = {
    idSubPlantillador: "",
    tipoArchivo: "",
    vistaPrevia: true,
  };
  return (
    <Modal
      show={show}
      dialogClassName="modalMax modal-document"
      onHide={handleClose}
      size="lg"
      centered
    >
      <Modal.Header closeButton className="modal-title">
        <Modal.Title className="col-11 text-center fw-bold">
          {title}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ height: "80vh", overflowY: "auto" }}>
        <Formik
          initialValues={initialValuesVP}
          enableReinitialize={true}
          validationSchema={validationSchemaVP}
          onSubmit={handleSubmit}
          validateOnMount={true}
        >
          {({
            values,
            handleSubmit,
            isValid,
            setFieldValue,
            errors,
            touched,
          }) => {

            return (
              <Form
                autoComplete="off"
                onSubmit={handleSubmit}
                style={{ height: "100%" }}
              >
                {(isLoadingPlantillas || isLoadingReporte) && <Loader />}
                <Row style={{ height: "100%" }}>
                  <Col md={3} style={{ height: "100%" }}>
                    {vistaPrevia && (
                      <>
                        <SelectInput
                          label="Tipo de plantilla:*"
                          name="idSubPlantillador"
                          value={selectedPlantilla}
                          options={plantillaCat}
                          keyValue="idSubPlantillador"
                          keyTextValue="nombre"
                          disabled={plantillaCat.length === 0}
                        />
                        <div className="d-flex justify-content-end">
                          <Button
                            variant="gray"
                            className="btn-sm ms-2 waves-effect waves-light"
                            type="submit"
                            onClick={() => {
                              !isValid && errorToast(PROFORMA.MSG005);
                            }}
                          >
                            Previsualizar
                          </Button>
                        </div>
                      </>
                    )}

                    {!vistaPrevia && (
                      <>
                        <SelectInput
                          label="Tipo de plantilla:*"
                          name="idSubPlantillador"
                          value={selectedPlantilla}
                          options={plantillaCat}
                          keyValue="idSubPlantillador"
                          keyTextValue="nombre"
                          disabled={plantillaCat.length === 0}
                        />
                        <div className="mt-4">
                          <h6>Formato para exportar</h6>
                          <FormCheck
                            type="radio"
                            id="PDF"
                            name="tipoArchivo"
                            label="PDF"
                            onChange={(e) =>
                              handleRadioChange(e, setFieldValue)
                            }
                          />
                          <FormCheck
                            type="radio"
                            id="EXCEL"
                            name="tipoArchivo"
                            label="Excel"
                            className="mt-2"
                            onChange={(e) =>
                              handleRadioChange(e, setFieldValue)
                            }
                          />
                        </div>
                        <div className="d-flex justify-content-end">
                          <Button
                            variant="gray"
                            className="btn-sm ms-2 waves-effect waves-light"
                            // disabled={!selectedPlantilla || !selectedFormato}
                            type="submit"
                            onClick={() => {
                              setFieldValue("vistaPrevia", true);
                              !isValid && errorToast(PROFORMA.MSG005);
                            }}
                          >
                            Previsualizar
                          </Button>
                        </div>
                      </>
                    )}
                  </Col>
                  <Col
                    md={9}
                    className="d-flex flex-column"
                    style={{ height: "100%" }}
                  >
                    <label className="mb-2">Factura proforma: </label>
                    <strong className="mb-3">{etiquetaDictamen}</strong>
                    <iframe
                      src={pdfUrl}
                      title="Vista previa"
                      style={{
                        display: "flex",
                        flexGrow: 1,
                        width: "100%",
                        height: "100%",
                        border: "none",
                      }}
                    />

                    <Row>
                      <Col md={12} className="text-end mt-3">
                        {vistaPrevia && (
                          <>
                            <Button
                              variant="green"
                              className="btn-sm ms-2 waves-effect waves-light"
                              type="submit"
                              onClick={handleClose}
                            >
                              Aceptar
                            </Button>
                          </>
                        )}
                        {!vistaPrevia && (
                          <>
                            <Button
                              variant="red"
                              className="btn-sm ms-2 waves-effect waves-light"
                              disabled={false}
                              onClick={handleClose}
                            >
                              Cancelar
                            </Button>
                            <Button
                              variant="green"
                              className="btn-sm ms-2 waves-effect waves-light"
                              type="submit"
                              onClick={() => {
                                setFieldValue("vistaPrevia", false);
                                !isValid && errorToast(PROFORMA.MSG005);
                              }}
                            >
                              Guardar
                            </Button>
                          </>
                        )}
                      </Col>
                    </Row>
                  </Col>
                </Row>
              </Form>
            );
          }}
        </Formik>
      </Modal.Body>
    </Modal>
  );
};

VerDocumento.defaultProps = {
  title: "Informe",
  show: false,
};

export default VerDocumento;

function SelectInput({
  name,
  label,
  options,
  disabled,
  showClasses = true,
  onChange,
  onBlur,
  keyValue = "primaryKey",
  keyTextValue = "nombre",
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let className = "";
  let helperText = "";

  if (showClasses) {
    const isTouched = touched[name];
    const hasError = errors[name];

    className = isTouched ? (hasError ? "is-invalid" : "is-valid") : "";

    helperText = isTouched ? (hasError ? hasError : "") : "";
  }

  return (
    <Select
      label={label}
      name={name}
      value={values[name]}
      options={options}
      keyValue={keyValue}
      keyTextValue={keyTextValue}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={className}
      helperText={helperText}
      disabled={disabled}
    />
  );
}

function FormCheck({
  type = "radio",
  id = "exportarExcel",
  name = "tipoArchivo",
  label = "Excel",
  onChange,
  showClasses = true,
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let className = "";
  let helperText = "";

  if (showClasses) {
    const isTouched = touched[name];
    const hasError = errors[name];

    className = isTouched ? (hasError ? "is-invalid" : "is-valid") : "";

    helperText = isTouched ? (hasError ? hasError : "") : "";
  }
  return (
    <>
      <Form.Check
        type={type}
        id={id}
        name={name}
        label={label}
        className={"mt-2 " + className}
        onChange={onChange}
      />
      <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    </>
  );
}
