import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import {
  FormCheck,
  Select,
  TextArea,
  TextField,
} from "../../../../components/formInputs";
import { LabelValue, Loader } from "../../../../components";
import { Formik } from "formik";
import * as yup from "yup";
import { postData, getData, putData } from "../../../../functions/api";
import { useToast } from "../../../../hooks/useToast";
import { PROVEEDORES as MESSAGES } from "../../../../constants/messages";
import BasicModal from "../../../../modals/BasicModal";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import showMessage from "../../../../components/Messages";
import Authorization from "../../../../components/Authorization";

const VALORES_INICIALES = {
  idProveedor: "",
  nombreProveedor: "",
  nombreComercial: "",
  rfc: "",
  direccion: "",
  comentarios: "",
  estatus: true,
  idAgs: "",
  giroEmpresarialModel: {
    idGiroEmpresarial: 0,
    giroEmpresarial: "",
  },
};

const DatosGenerales = ({
  chageStatusSeccion,
  ver,
  edit,
  proveedor,
  setIdProveedor,
}) => {
  //#region Confirm Modal

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalMessage, setConfirmModalMesage] = useState("");
  const _confirmData = {
    approve: () => {},
    deny: () => {},
  };
  const [confirmData, setConfirmData] = useState(_confirmData);

  const handleShowConfirmModal = (
    title,
    approve = _confirmData.approve,
    deny = _confirmData.deny
  ) => {
    setConfirmModalMesage(title);
    setConfirmData({ approve, deny });
    setShowConfirmModal(true);
  };
  const handleApprove = () => {
    if (confirmData?.approve) {
      confirmData.approve();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  const handleDenny = () => {
    if (confirmData?.deny) {
      confirmData.deny();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  //#endregion

  const { errorToast } = useToast();
  const location = useLocation();
  const navigate = useNavigate();
  const { state } = location;
  const idProveedorState = state?.idProveedor || null;
  const [actualiza, setActualiza] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [backMessage, setBackMessage] = useState(false);
  const [isSubmit, setIsSubmit] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const [idProveedor, setIdProveedorState] = useState(idProveedorState);
  const [closeEstatus, setCloseEstatus] = useState(false);
  const [valoresIniciales, setValoresIniciales] = useState(
    proveedor
      ? {
          ...proveedor,
          giroEmpresarialModel: proveedor.giroEmpresarialModel || {
            idGiroEmpresarial: 0,
            giroEmpresarial: "",
          },
          estatus: proveedor.estatus || false,
        }
      : { ...VALORES_INICIALES, idProveedor: idProveedor || "" }
  );

  const esquema = yup.object().shape({
    nombreProveedor: yup.string().required("Dato requerido"),
    nombreComercial: yup.string().required("Dato requerido"),
    giroEmpresarialModel: yup.object().shape({
      idGiroEmpresarial: yup
        .number()
        .required("Dato requerido")
        .min(1, "Dato requerido"),
    }),
    rfc: yup
      .string()
      .matches(
        /^([A-ZÑ&]{3,4})(\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01]))([A-Z\d]{2})([A\d])$/,
        { message: "RFC no válido", excludeEmptyString: true }
      ),
  });

  const [giroEmpresaOptions, setGiroEmpresaOptions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      if (idProveedor) {
        try {
          const proveedorResp = await getData(
            `/proveedores/proveedor/${idProveedor}`
          );
          setValoresIniciales({
            ...VALORES_INICIALES,
            ...proveedorResp.data,
            giroEmpresarialModel: {
              idGiroEmpresarial:
                proveedorResp.data.giroEmpresarialModel.primaryKey,
              giroEmpresarial: proveedorResp.data.giroEmpresarialModel.nombre,
            },
          });
        } catch (error) {
          showMessage(MESSAGES.MSG004);
          setLoading(false);
        }
      }
      try {
        const giroEmpresaDropResp = await getData(
          "/proveedores/consultar-todos-giros"
        );
        const optionsConsultaGiro = giroEmpresaDropResp.data.map((item) => ({
          giroEmpresarial: item.nombre,
          idGiroEmpresarial: item.primaryKey,
        }));
        setGiroEmpresaOptions(optionsConsultaGiro);
      } catch (error) {
        console.error("Error fetching giros empresariales:", error);
      }
      setLoading(false);
    };
    fetchData();
  }, [idProveedor]);

  useEffect(() => {
    if (actualiza) {
      const fetchUpdatedData = async () => {
        try {
          const proveedorResp = await getData(
            `/proveedores/proveedor/${idProveedor}`
          );
          console.log(proveedorResp);
          setValoresIniciales({
            ...VALORES_INICIALES,
            ...proveedorResp.data,
            giroEmpresarialModel: {
              idGiroEmpresarial:
                proveedorResp.data.giroEmpresarialModel.primaryKey,
              giroEmpresarial: proveedorResp.data.giroEmpresarialModel.nombre,
            },
          });
        } catch (error) {
          showMessage(MESSAGES.MSG004);
          setLoading(false);
        }
        setLoading(false);
      };
      fetchUpdatedData();
      setActualiza(false);
    }
  }, [actualiza, idProveedor]);

  const handleGoBack = () => {
    setCloseEstatus(false);
    setBackMessage(true);
    if (ver === true) {
      navigate("/proveedores/proveedores");
    } else {
      setShowModal(true);
      setModalMessage(MESSAGES.MSG002);
    }
    setIsSubmit(false);
  };

  const handleAccept = () => {
    if (closeEstatus === true) {
      handleCloseModal();
    } else {
      setCloseEstatus(false);
      navigate("/proveedores/proveedores");
      handleCloseModal();
    }
  };

  const handleCloseModal = () => {
    setModalMessage(MESSAGES.MSG009);
    setBackMessage(false);
    setShowModal(false);
  };

  function catchError(error, objetoAEnviar) {
    setLoading(false);
    if (
      error?.response?.data?.mensaje?.some((msg) =>
        msg.includes("El RFC capturado ya lo tiene registrado")
      )
    ) {
      let mensajeResponse = error.response.data.mensaje.find((msg) =>
        msg.includes("El RFC capturado ya lo tiene registrado")
      );

      let mensaje = `${
        mensajeResponse
          ? mensajeResponse
          : "El RFC capturado ya se encuentra registrado."
      } ¿Estás seguro de continuar?`;

      handleShowConfirmModal(mensaje, async () => {
        try {
          setLoading(true);
          let response;
          let _objetoAEnviar = { ...objetoAEnviar, duplicado: true };
          if (idProveedor) {
            response = await putData(
              `/proveedores/proveedor/${idProveedor}`,
              _objetoAEnviar
            );
          } else {
            response = await postData(
              "/proveedores/crear-proveedor",
              _objetoAEnviar
            );
            if (response && response.data && response.data.idProveedor) {
              setIdProveedorState(response.data.idProveedor);
              setIdProveedor(response.data.idProveedor);
            }
          }

          if (response && response.data) {
            console.log("Successful response:", response);
            console.log("Response data:", response.data);
            setActualiza(true);
            showMessage(MESSAGES.MSG008);
            setValoresIniciales({
              ...VALORES_INICIALES,
              ...response.data,
              giroEmpresarialModel: response.data.giroEmpresarialModel || {
                idGiroEmpresarial: 0,
                giroEmpresarial: "",
              },
            });
          }
          setLoading(false);
        } catch (error) {
          if (
            error.response.data.mensaje.includes(
              "El RFC no tiene el formato correcto"
            )
          ) {
            showMessage("El RFC no tiene el formato correcto");
            setLoading(false);
          } else if (
            error.response.data.mensaje.includes(
              "El Nombre del proveedor ya se encuentra almacenado en la BD"
            )
          ) {
            showMessage(MESSAGES.MSG007);
            setLoading(false);
          } else if (
            error.response.data.mensaje.includes(
              "El ID AGS ya se encuentra almacenado en la BD"
            )
          ) {
            showMessage("El ID AGS ya se encuentra almacenado en la BD");
            setLoading(false);
          } else {
            showMessage(MESSAGES.MSG003);
            setLoading(false);
          }
        }
      });
    } else if (
      error.response.data.mensaje.includes(
        "El RFC no tiene el formato correcto"
      )
    ) {
      showMessage("El RFC no tiene el formato correcto");
      setLoading(false);
    } else if (
      error.response.data.mensaje.includes(
        "El Nombre del proveedor ya se encuentra almacenado en la BD"
      )
    ) {
      showMessage(MESSAGES.MSG007);
      setLoading(false);
    } else if (
      error.response.data.mensaje.includes(
        "El ID AGS ya se encuentra almacenado en la BD"
      )
    ) {
      showMessage("El ID AGS ya se encuentra almacenado en la BD");
      setLoading(false);
    } else {
      showMessage(MESSAGES.MSG003);
      setLoading(false);
    }
  }

  const handleSubmit = async (data) => {
    setLoading(true);
    const activo = !!data.estatus;
    const objetoAEnviar = {
      ...data,
      estatus: activo,
      idGiroEmpresarial: data.giroEmpresarialModel
        ? data.giroEmpresarialModel.idGiroEmpresarial
        : 0,
    };

    try {
      let response;

      if (idProveedor) {
        response = await putData(
          `/proveedores/proveedor/${idProveedor}`,
          objetoAEnviar
        );
      } else {
        response = await postData("/proveedores/crear-proveedor", {
          ...objetoAEnviar,
          duplicado: false,
        });
        if (response && response.data && response.data.idProveedor) {
          setIdProveedorState(response.data.idProveedor);
          setIdProveedor(response.data.idProveedor);
        }
      }

      if (response && response.data) {
        console.log("Successful response:", response);
        console.log("Response data:", response.data);
        setActualiza(true);
        showMessage(MESSAGES.MSG008);
        setValoresIniciales({
          ...VALORES_INICIALES,
          ...response.data,
          giroEmpresarialModel: response.data.giroEmpresarialModel || {
            idGiroEmpresarial: 0,
            giroEmpresarial: "",
          },
        });
      }
    } catch (error) {
      catchError(error, objetoAEnviar);
    }
  };

  const handleInputChangeIdAgs = (e, handleChange) => {
    const { value } = e.target;
    if (/^\d*$/.test(value) && value.length <= 9) {
      handleChange(e);
    }
  };
  const estatusEvent = (e) => {
    const isChecked = e.target.checked;
    if (isChecked === false && edit === true) {
      setModalMessage(MESSAGES.MSG009);
      setShowModal(true);
      setCloseEstatus(true);
    } else {
      setCloseEstatus(false);
    }
  };

  return (
    <>
      {loading && <Loader />}
      <BasicModal
        size="md"
        handleApprove={handleApprove}
        handleDeny={handleDenny}
        denyText={"No"}
        approveText={"Sí"}
        show={showConfirmModal}
        title="Mensaje"
        onHide={handleDenny}
      >
        {confirmModalMessage}
      </BasicModal>
      <Formik
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={esquema}
        onSubmit={handleSubmit}
        validateOnMount={true}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          values,
          errors,
          touched,
          validateField,
          setFieldValue,
          isValid,
        }) => {
          const handleDeny = () => {
            setCloseEstatus(false);
            setFieldValue("estatus", true);
            handleCloseModal();
          };
          return (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Row>
                {idProveedor ? (
                  <Col md={12}>
                    <div className="d-flex align-items-center gap-3 mb-2 ">
                      <Form.Label className="">Id Proveedor:</Form.Label>
                      <Form.Label className="fw-bold ">
                        {values.idProveedor}
                      </Form.Label>
                    </div>
                  </Col>
                ) : null}
                <Col md={4}>
                  <TextField
                    label="Nombre del proveedor*:"
                    name="nombreProveedor"
                    value={values.nombreProveedor}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      touched.nombreProveedor &&
                      (errors.nombreProveedor ? "is-invalid" : "is-valid")
                    }
                    helperText={
                      touched.nombreProveedor ? errors.nombreProveedor : ""
                    }
                    disabled={ver === true}
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    label="Nombre comercial*:"
                    name="nombreComercial"
                    value={values.nombreComercial}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      touched.nombreComercial &&
                      (errors.nombreComercial ? "is-invalid" : "is-valid")
                    }
                    helperText={
                      touched.nombreComercial ? errors.nombreComercial : ""
                    }
                    disabled={ver === true}
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    label="RFC:"
                    name="rfc"
                    value={values.rfc}
                    onChange={(e) => {
                      const upperCaseValue = e.target.value.toUpperCase();
                      setFieldValue("rfc", upperCaseValue);
                      validateField("rfc");
                    }}
                    onBlur={handleBlur}
                    className={touched.rfc && errors.rfc ? "is-invalid" : ""}
                    helperText={touched.rfc ? errors.rfc : ""}
                    disabled={ver === true}
                  />
                </Col>
                <Col md={8}>
                  <TextField
                    label="Dirección:"
                    name="direccion"
                    value={values.direccion}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    disabled={ver === true}
                  />
                </Col>
                <Col md={4}>
                  <Select
                    label="Giro de la empresa*:"
                    name="giroEmpresarialModel.idGiroEmpresarial"
                    value={values.giroEmpresarialModel.idGiroEmpresarial}
                    onChange={(e) =>
                      setFieldValue(
                        "giroEmpresarialModel.idGiroEmpresarial",
                        e.target.value
                      )
                    }
                    options={giroEmpresaOptions}
                    keyValue="idGiroEmpresarial"
                    keyTextValue="giroEmpresarial"
                    onBlur={handleBlur}
                    className={
                      touched.giroEmpresarialModel &&
                      touched.giroEmpresarialModel.idGiroEmpresarial &&
                      (errors.giroEmpresarialModel &&
                      errors.giroEmpresarialModel.idGiroEmpresarial
                        ? "is-invalid"
                        : "is-valid")
                    }
                    helperText={
                      touched.giroEmpresarialModel &&
                      touched.giroEmpresarialModel.idGiroEmpresarial &&
                      errors.giroEmpresarialModel
                        ? errors.giroEmpresarialModel.idGiroEmpresarial
                        : ""
                    }
                    disabled={ver === true}
                  />
                </Col>
                <Col md={8}>
                  <TextArea
                    label="Comentarios:"
                    name="comentarios"
                    value={values.comentarios}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    helperText={touched.comentarios ? errors.comentarios : ""}
                    disabled={ver === true}
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    label="Id AGS:"
                    name="idAgs"
                    value={values.idAgs}
                    onChange={(e) => handleInputChangeIdAgs(e, handleChange)}
                    onBlur={handleBlur}
                    className={
                      touched.idAgs && errors.idAgs ? "is-invalid" : ""
                    }
                    helperText={touched.idAgs ? errors.idAgs : ""}
                    disabled={ver === true}
                  />
                </Col>
                <Col md={4}>
                  <FormCheck
                    label="Estatus*:"
                    name="estatus"
                    value={values.estatus}
                    onChange={(e) => {
                      setFieldValue("estatus", e.target.checked);
                      estatusEvent(e, handleChange);
                    }}
                    onBlur={handleBlur}
                    disabled={ver === true}
                  />
                </Col>
              </Row>
              <Row>
                <Col md={12} className="text-end">
                  {ver === true ? (
                    <></>
                  ) : (
                    <div>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleGoBack}
                      >
                        Cancelar
                      </Button>
                      <Authorization process={"PROV_BTN_GUARDARDG"}>
                        <Button
                          variant="green"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                          onClick={() => {
                            if (!isValid) {
                              const rfcError = errors.rfc && touched.rfc;
                              if (!rfcError) {
                                errorToast(MESSAGES.MSG001);
                              }
                            }
                          }}
                        >
                          Guardar
                        </Button>
                      </Authorization>
                    </div>
                  )}
                </Col>
              </Row>
              <BasicModal
                show={showModal}
                size={"md"}
                onHide={handleCloseModal}
                title="Mensaje"
                denyText="No"
                handleDeny={handleDeny}
                approveText="Sí"
                handleApprove={handleAccept}
              >
                {modalMessage}
              </BasicModal>
            </Form>
          );
        }}
      </Formik>
    </>
  );
};

export default DatosGenerales;
