import React, { useState, useRef } from "react";
import { Row, Col, Button, Form } from "react-bootstrap";
import { Tooltip } from "../../../../../components/Tooltip";
import IconButton from "../../../../../components/buttons/IconButton";
import FileField from "../../../../../components/formInputs/FileField";
import { getData, postData } from "../../../../../functions/api";
import { CASO_DE_NEGOCIO } from "../../../../../constants/messages";
import { Formik } from "formik";
import * as yup from "yup";
import Authorization from "../../../../../components/Authorization";
import { usePostProcesarProyeccionCNMutation } from "./store";
import _ from "lodash";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";

// import { useToast } from "../../../../../hooks/useToast";
export default function CasoNegocioActions({
  idContrato,
  handleShowMessage,
  handleShowConfirmModal,
  setIsLoading,
  mapa,
  setMapa,
  cargado,
  getDataCasoNegocio,
  isDetalle,
  dataCasoNegocio,
}) {
  const formRef = useRef();
  const { getMessageExists } = useErrorMessages(CASO_DE_NEGOCIO);

  const [postProcesarProyeccion] = usePostProcesarProyeccionCNMutation();
  // const { errorToast } = useToast();

  //#region Descarga Layout & Excel
  const validateDownload = (response, fileName) => {
    setIsLoading(false);
    if (response.data) {
      handleShowMessage(CASO_DE_NEGOCIO.MSG007);
      DownloadFileBase64(
        response.data,
        fileName,
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } else {
      handleShowMessage(CASO_DE_NEGOCIO.MSG005);
    }
  };

  const validateError = (error) => {
    setIsLoading(false);
    handleShowMessage(CASO_DE_NEGOCIO.MSG005);
  };

  const handleDownloadLayout = () => {
    if (idContrato) {
      setIsLoading(true);
      getData("/admin-contratos/layout/" + idContrato)
        .then((response) => validateDownload(response, "layout.xlsx"))
        .catch(validateError);
    }
  };

  const handleDownloadExcel = () => {
    if (idContrato) {
      setIsLoading(true);
      getData("/admin-contratos/exportar-excel/" + idContrato)
        .then((response) =>
          validateDownload(response, "Proyección de caso de negocio.xlsx")
        )
        .catch(validateError);
    }
  };

  //#endregion

  //#region Cargar Proyección
  const [proyeccionData, setProyeccionData] = useState(null);
  const handleFileChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      // Leer el archivo y convertirlo a Base64
      let reader = new FileReader();
      reader.onload = (e) => {
        let base64String = e.target.result.split(",")[1];
        let proyeccionData = {
          archivo: base64String,
          nombreArchivo: file.name,
        };
        setFileText(file.name);
        setProyeccionData(proyeccionData);
      };
      reader.onerror = (error) => {
        // console.log(error);
      };
      reader.readAsDataURL(file);
    }
  };

  //#endregion

  //#region Procesar Proyeccion

  const handleProcess = () => {
    if (proyeccionData) {
      if (cargado) {
        handleShowConfirmModal(CASO_DE_NEGOCIO.MSG006, processProjection);
      } else {
        processProjection();
      }
    }
  };
  const processProjection = () => {
    setIsLoading(true);
    postProcesarProyeccion({ idContrato, data: proyeccionData }).then(
      (response) => {
        if (response.error) {
          let { error } = response;
          getDataCasoNegocio();
          setIsLoading(false);
          let mensaje = getMessageExists(error?.data?.mensaje[0]) || error?.data?.mensaje[0].startsWith("Verifique el layout de carga")
          ? error.data.mensaje[0]
          : CASO_DE_NEGOCIO.MSG003;
          
          handleShowMessage(mensaje);
        } else {
          getDataCasoNegocio();
          if (response?.data) {
            setIsLoading(false);
            // setMapa(response.data);
            handleShowMessage(CASO_DE_NEGOCIO.MSG004);
            setProyeccionData(null);
            setFileText("");
          }
        }
      }
    );
  };

  //#endregion
  const [initialValues, setInitialValues] = useState({
    proyeccionFile: "",
  });
  const validationSchema = yup.object({});

  const setFileText = async (fileName, elementId = "proyeccionFile") => {
    await formRef.current.setFieldTouched(elementId, true);
    // await setFilesErrors((prevErrors) => ({
    //   ...prevErrors,
    //   [elementId]: null,
    // }));
    formRef.current.values[elementId] = null;
    setTimeout(() => {
      const file = new File([""], fileName, {
        type: "text/plain",
      });
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(file);
      const fileInput = document.getElementById(elementId);
      fileInput.files = dataTransfer.files;
    }, [200]);
    return null;
  };

  return (
    <Formik
      innerRef={(f) => (formRef.current = f)}
      initialValues={initialValues}
      enableReinitialize
      validationSchema={validationSchema}
      validateOnMount={true}
      onChange={() => {}}
      onSubmit={(values, { resetForm }) =>
        () => {}}
    >
      {({
        handleSubmit,
        handleChange,
        values,
        setFieldValue,
        setFieldTouched,
        touched,
        errors,
      }) => {
        console.log(values);

        return (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={4}>
                <Row>
                  <Col md={12}>Descargar layout:</Col>
                  <Col md={3}>
                    <Tooltip placement="top" text={"Descargar layout"}>
                      <span>
                        <IconButton
                          type="excel2"
                          onClick={handleDownloadLayout}
                          disabled={isDetalle}
                        />
                      </span>
                    </Tooltip>
                  </Col>
                </Row>
              </Col>
              <Col md={4}>
                <Authorization process={"CONT_CN_BTN_PP"}>
                  <FileField
                    label={"Archivo proyección CN:"}
                    className={""}
                    accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                    onChange={handleFileChange}
                    disabled={isDetalle}
                    name={"proyeccionFile"}
                  />
                </Authorization>
              </Col>
              <Col md={1}></Col>
              <Col md={2} className="">
                <div>‎ </div>
                <Authorization process={"CONT_CN_BTN_PP"}>
                  <Button
                    variant="gray"
                    className="ms-2 waves-effect waves-light "
                    size="sm"
                    type="button"
                    onClick={handleProcess}
                    disabled={isDetalle}
                  >
                    Procesar proyección
                  </Button>
                </Authorization>
              </Col>
              <Col md={12}></Col>
            </Row>
            <Row>
              <Col md={12} className="text-end mb-3">
                <Tooltip placement="top" text={"Exportar a Excel"}>
                  <span>
                    <IconButton
                      type="excel"
                      onClick={handleDownloadExcel}
                      disabled={_.isEmpty(dataCasoNegocio)}
                    />
                  </span>
                </Tooltip>
              </Col>
            </Row>
          </Form>
        );
      }}
    </Formik>
  );
}

function base64toFile(base64, filename, mimeType) {
  const binaryString = window.atob(base64);
  const len = binaryString.length;
  const bytes = new Uint8Array(len);
  for (let i = 0; i < len; ++i) {
    bytes[i] = binaryString.charCodeAt(i);
  }
  return new File([bytes], filename, { type: mimeType });
}

function DownloadFileBase64(base64, filename, mimeType) {
  const blob = base64toFile(base64, filename, mimeType);
  const url = URL.createObjectURL(blob);

  const link = document.createElement("a");
  link.href = url;
  link.download = filename;
  link.click();
  URL.revokeObjectURL(url);
}
