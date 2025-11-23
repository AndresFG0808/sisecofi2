import React, { useState, useEffect, useRef, useContext } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button } from 'react-bootstrap';
import { Formik } from "formik";
import * as yup from "yup";
import { FileField, TextField, TextFieldDate } from "../../../../components/formInputs";
import { Loader } from "../../../../components";
import IconButton from "../../../../components/buttons/IconButton";
import { Tooltip } from "../../../../components/Tooltip";
import { putData, postData, getData, postMultipartData } from '../../../../functions/api';
import { useToast } from "../../../../hooks/useToast";
import { PROFORMA } from "../../../../constants/messages";
import BasicModal from "../../../../modals/BasicModal";
import showMessage from '../../../../components/Messages';
import Authorization from '../../../../components/Authorization';
import {
  useGetGestionDocumentalDictamenQuery,
} from "../GestionDocumental/store";
import { DictamenContext } from "../context";

const VALORES_INICIALES = {
  idDictamen: "",
  noOficioSolicitud: "",
  fechaSolicitud: "",
  ruta: "",
  nombreDocumento: "",
  fechaRecepcionFactura: "",
  banderaFactura: true,
  tieneServicios: false
};

const FormularioSolicitudFacturas = ({ isDetalle, ver, reload, submitForm, setIdProveedor }) => {
  const { errorToast } = useToast();

  const fileInputRef = useRef(null);
  const formikRef = useRef(null);
  const { state } = useLocation();
  const { idDictamen: idDictamenState } = { ...state?.dictamenState };
  const [showModal, setShowModal] = useState(false);
  const [showPagado, setShowPagado] = useState(false);
  const [isPagado, setIsPagado] = useState(false);
  const [backMessage, setBackMessage] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const [disabledInputs, setDisabledInputs] = useState(false);
  const [idDictamen, setIdDictamen] = useState(null);
  const [idContrato, setIdContrato] = useState(null);
  const [tipoCambio, setTipoCambio] = useState(null);
  const [estatusDictamen, setEstatusDictamen] = useState(null);
  const [editable, setEditable] = useState(null);
  const [valoresIniciales, setValoresIniciales] = useState(VALORES_INICIALES);
  const [facturasRecibidas, setFacturasRecibidas] = useState(false);
  const [documento, setDocumento] = useState("");
  const [fileLoaded, setFileLoaded] = useState(null);
  const [forceReload, setForceReload] = useState(false);
  const [showReplaceModal, setShowReplaceModal] = useState(false);
  const [disabledBandera, setDisabledBandera] = useState(false);
  const [nuevoRegistro, setNuevoRegistro] = useState(false);
  const [newFile, setNewFile] = useState(null);
  const [originalFile, setOriginalFile] = useState(null);
  const [documentoModificado, setDocumentoModificado] = useState("");
  const [isDownloading, setIsDownloading] = useState(false);
  const [recepcionFacturaCheked, setRecepcionFacturaCheked] = useState(false);
  const [cancelar, setCancelar] = useState(false);
  const { onReloadDictamenInfo, } = useContext(DictamenContext);

  const { refetch } =
    useGetGestionDocumentalDictamenQuery(encodeURIComponent(idDictamenState));

  const esquema = yup.object().shape({
    noOficioSolicitud: yup.string().required("Dato requerido"),
    fechaSolicitud: yup.date().required("Dato requerido"),
    nombreDocumento: yup.mixed().required("Dato requerido").test(
      "fileName",
      "Debe subir un archivo válido",
      (value) => value && value.name
    ),
    fechaRecepcionFactura: disabledInputs === true
      ? yup.date().required("Dato requerido")
      : yup.date().notRequired()
    ,
  });

  const convertirBase64AFile = (base64String, nombre) => {
    const byteCharacters = atob(base64String);
    const byteNumbers = new Array(byteCharacters.length).fill().map((_, i) => byteCharacters.charCodeAt(i));
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: "application/pdf" });
    return new File([blob], nombre, { type: "application/pdf" });
  };

  useEffect(() => {
    let {
      idDictamen,
      idContrato,
      /*       idProveedor, */
      tipoCambioReferencial,
      estatus,
      editable,
    } = { ...state?.dictamenState };
    setIdContrato(idContrato);
    setIdDictamen(idDictamen);
    /*     setIdProveedor(idProveedor); */
    setTipoCambio(tipoCambioReferencial);
    setEstatusDictamen(estatus);
    setEditable(editable);
  }, [state, setIdProveedor]);

  const permisosIniciales = {
    leer: true,
    edicion: true,
  };

  const [permisos, setPermisos] = useState(permisosIniciales);

  useEffect(() => {
    let estatusValido = ["proforma"].includes(
      estatusDictamen?.toLowerCase()
    );

    setPermisos((prev) => ({
      ...prev,
      modificar: estatusValido && editable,
    }));
  }, [estatusDictamen, editable]);
  const { modificar } = permisos;

  useEffect(() => {
    const fetchData = async (setFieldValue) => {

      try {
        const respuestaFactura = await postData('/admin-devengados/buscar-solicitud-factura', {
          idDictamen: idDictamenState,
        });
        if (respuestaFactura && respuestaFactura.data) {
          const { dictamenId, noOficioSolicitud, fechaSolicitud, fechaRecepcionFactura, banderaFactura, nombre, ruta, tieneServicios } = respuestaFactura.data;


          const convertirFecha = (fecha) => {
            if (!fecha) return "";
            const [dia, mes, anio] = fecha.split('/');
            return `${anio}-${mes}-${dia}`;
          };
          setValoresIniciales({
            idDictamen: dictamenId || "",
            noOficioSolicitud: noOficioSolicitud || "",
            fechaSolicitud: convertirFecha(fechaSolicitud) || "",
            ruta: ruta || "",
            nombreDocumento: nombre || "",
            fechaRecepcionFactura: convertirFecha(fechaRecepcionFactura) || "",
            banderaFactura: banderaFactura || true,
            tieneServicios: tieneServicios || false,
          });

          if (ruta === "" || ruta === null) {
            setDisabledInputs(false)
          }
          if (banderaFactura === true) {
            setDisabledBandera(true)
            setRecepcionFacturaCheked(true)
            setFacturasRecibidas(true)
          }
          if (tieneServicios === true) {
            setIsPagado(true)
          }
          if (ruta) {
            setLoading(true);
            setIsDownloading(true);
            const documentoDescarga = await getData(`/admin-devengados/solicitud-factura/descarga/oficio?path=${ruta}`);
            setIsDownloading(false);
            setForceReload(false);
            setCancelar(true);
            const file = convertirBase64AFile(documentoDescarga.data, nombre);
            setDocumento(documentoDescarga.data);
            setFileLoaded(file);
            setOriginalFile(file);

            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(file);
            const fileInput = document.getElementById("nombreDocumento");
            if (fileInput) {
              fileInput.files = dataTransfer.files;
            }

            setValoresIniciales({
              idDictamen: dictamenId || "",
              noOficioSolicitud: noOficioSolicitud || "",
              fechaSolicitud: convertirFecha(fechaSolicitud) || "",
              ruta: ruta || "",
              nombreDocumento: nombre || "",
              fechaRecepcionFactura: convertirFecha(fechaRecepcionFactura) || "",
              banderaFactura: banderaFactura || true,
              tieneServicios: tieneServicios || false,
            });

            setValoresIniciales((prevValues) => ({
              ...prevValues,
              nombreDocumento: file,
            }));
            setDisabledInputs(true);
          }
          setFacturasRecibidas(banderaFactura || false);
          setRecepcionFacturaCheked(banderaFactura || false);
          setNuevoRegistro(false)
          setLoading(false);
        } else {
          setDisabledInputs(false);
          setLoading(false);
        }
      } catch (error) {
        setLoading(false);
        setIsDownloading(false);
        console.error(error);
        setNuevoRegistro(true)
        setDisabledInputs(false);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [reload, idDictamenState, forceReload,]);

  const handleFileChange = (event, setFieldValue) => {
    const file = event.currentTarget.files[0];
    if (file && file.type === "application/pdf") {
      if (fileLoaded) {
        setNewFile(file);
        setShowReplaceModal(true);
        setDocumentoModificado(true);
      } else {
        setFieldValue("nombreDocumento", file);
        setDocumentoModificado(false);
        setFileLoaded(file);
      }
    } else {
      showMessage(PROFORMA.MSG014);
      setFieldValue("nombreDocumento", "");
      setFileLoaded(null);
      setDocumentoModificado(false);
      if (fileInputRef.current) fileInputRef.current.value = "";
    }
  };

  const handleConfirmReplace = (setFieldValue) => {
    setFieldValue("nombreDocumento", newFile);
    setFileLoaded(newFile);
    setShowReplaceModal(false);
    setNewFile(newFile);
  };

  const handleCancelReplace = () => {
    setShowReplaceModal(false);
    setNewFile(null);
    if (originalFile) {
      setFileLoaded(originalFile);
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(originalFile);
      const fileInput = document.getElementById("nombreDocumento");
      if (fileInput) {
        fileInput.files = dataTransfer.files;
      }
    }
  };

  const handleDownloadFile = () => {
    if (fileLoaded) {
      const url = URL.createObjectURL(fileLoaded);
      const link = document.createElement("a");
      link.href = url;
      link.download = fileLoaded.name;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  };

  useEffect(() => {
    console.log(facturasRecibidas);
  }, [facturasRecibidas]);



  const handleGoBack = () => {
    setShowModal(true)
  };

  const handleCloseModal = () => {
    setModalMessage('MESSAGES.MSG009');
    setBackMessage(false)
    setShowModal(false);
  };

  const handleCloseModalPagado = () => {
    setShowPagado(false);
  };

  const ejecutarSubmit = async (values, forzarFacturasRecibidas = false, isFacturadoClick = false) => {
    setLoading(true);

    const formatDate = (date) => {
      const parsedDate = new Date(date);
      parsedDate.setDate(parsedDate.getDate() + 1);
      const year = parsedDate.getFullYear();
      const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
      const day = String(parsedDate.getDate()).padStart(2, '0');

      return `${year}-${month}-${day}T00:00:00`;
    };

    const fechaSolicitudISO = formatDate(values.fechaSolicitud);
    const fechaRecepcionISO = formatDate(values.fechaRecepcionFactura);

    if (nuevoRegistro === true) {
      try {
        const formData = new FormData();
        formData.append('idDictamen', idDictamenState);
        formData.append('noOficioSolicitud', values.noOficioSolicitud);
        formData.append('fechaSolicitud', fechaSolicitudISO);
        formData.append('documento', values.nombreDocumento);

        await postMultipartData('/admin-devengados/solicitud-factura/guardar', formData);
        refetch();
        setDisabledInputs(true);
        setForceReload(true);
        setNuevoRegistro(false);
        setCancelar(true);
        showMessage(PROFORMA.MSG011);
      } catch (error) {
        if (error.message) {
          showMessage('Ocurrió un error')
        }
        if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
          showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
          setLoading(false);
          setFacturasRecibidas(true)
          setRecepcionFacturaCheked(true)
        } else {
          showMessage(PROFORMA.MSG002)
          setNuevoRegistro(true)
          setLoading(false);
        }
      } finally {
        setLoading(false);
      }

    } else {
      try {
        const formData = new FormData();
        formData.append('idDictamen', idDictamenState);
        formData.append('noOficioSolicitud', values.noOficioSolicitud);
        formData.append('fechaSolicitud', fechaSolicitudISO);
        formData.append('ruta', "path/del/documento/");
        formData.append('documento', values.nombreDocumento);

        if (values.fechaRecepcionFactura) {
          formData.append('fechaRecepcionFactura', fechaRecepcionISO);
        } else {
          formData.append('fechaRecepcionFactura', "");
        }
        formData.append('banderaFactura', forzarFacturasRecibidas || facturasRecibidas);

        await postMultipartData('/admin-devengados/solicitud-factura/actualizar', formData);
        refetch();
        setDisabledInputs(true);
        setForceReload(true);
        showMessage(PROFORMA.MSG011);
      } catch (error) {
        showMessage(PROFORMA.MSG002);
        if (isFacturadoClick) {
          setFacturasRecibidas(false);
          setRecepcionFacturaCheked(false)
        }
      } finally {
        setLoading(false);
      }
    }


  };


  const handleSubmit = async (values) => {
    await ejecutarSubmit(values);
  };

  const handleFacturadoClick = async (values, validateForm, setTouched) => {
    setTouched({
      noOficioSolicitud: true,
      fechaSolicitud: true,
      nombreDocumento: true,
      fechaRecepcionFactura: true,
    });

    const validationErrors = await validateForm();

    if (Object.keys(validationErrors).length > 0) {
      errorToast(PROFORMA.MSG005);
      return;
    }

    if (values.fechaRecepcionFactura && values.fechaSolicitud && new Date(values.fechaRecepcionFactura) >= new Date(values.fechaSolicitud)) {
      setFacturasRecibidas(true);
      setRecepcionFacturaCheked(true)
      await ejecutarSubmit(values, true, true);
    } else {
      showMessage(PROFORMA.MSG008);
      setRecepcionFacturaCheked(false)
      setFacturasRecibidas(false);
    }
  };

  const handleConfirmPagado = () => {
    setShowPagado(true);
  };

  const handleEstatusPagado = async () => {
    setShowPagado(false);
    try {
      setLoading(true);
      const idDictamen = idDictamenState;

      const response = await putData(`/admin-devengados/solicitud-factura-pagado/${idDictamen}`);
      if (response && response.data) {
        showMessage("El estatus del Dictamen ha cambiado a “Pagado”.");
        console.log(response.data);
      }
      await onReloadDictamenInfo(idDictamen);
      setLoading(false);
      refetch();
    } catch (error) {
      showMessage("Ocurrió un error al cambiar el estatus del dictamen. Favor de intentar nuevamente.");
      setLoading(false);
    }
  };

  useEffect(() => {
    if (submitForm)
      formikRef.current.submitForm();
  }, [submitForm]);

  return (
    <>
      {loading && <Loader zIndex={`${loading ? "10" : "9999"}`} />}
      <Formik
        innerRef={formikRef}
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={esquema}
        onSubmit={handleSubmit}
        validateOnMount={true}
      >
        {({
          handleSubmit, handleChange, handleBlur, values, errors, touched, setFieldValue,
          isValid, resetForm, validateForm, setTouched
        }) => {


          const handleDeny = () => {
            handleCloseModal();
          };

          const handleAccept = () => {
            setFacturasRecibidas(false);
            setRecepcionFacturaCheked(false)
            if (cancelar === false) {
              resetForm({
                values: {
                  idDictamen: "",
                  noOficioSolicitud: "",
                  fechaSolicitud: "",
                  ruta: "",
                  nombreDocumento: "",
                  fechaRecepcionFactura: "",
                  banderaFactura: false
                }
              });

              if (fileInputRef.current) {
                fileInputRef.current.value = "";
              }
            } else {
              setForceReload(true);
            }


            handleCloseModal();
          };

          return (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Row>
                <Col md={4}>
                  <TextField
                    label="Número de oficio de solicitud de factura*:"
                    name="noOficioSolicitud"
                    value={values.noOficioSolicitud}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={touched.noOficioSolicitud && (errors.noOficioSolicitud ? 'is-invalid' : 'is-valid')}
                    helperText={touched.noOficioSolicitud ? errors.noOficioSolicitud : ''}
                    disabled={disabledBandera || !modificar}
                  />

                </Col>
                <Col md={4}>
                  <TextFieldDate
                    label="Fecha de solicitud factura*:"
                    name="fechaSolicitud"
                    value={values.fechaSolicitud}
                    disabled={disabledBandera || !modificar}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={touched.fechaSolicitud && (errors.fechaSolicitud ? 'is-invalid' : 'is-valid')}
                    helperText={touched.fechaSolicitud ? errors.fechaSolicitud : ''}
                  />
                </Col>
                <Col md={4}>
                  <FileField
                    label="Cargar oficio*:"
                    name="nombreDocumento"
                    ref={fileInputRef}
                    disabled={disabledBandera || !modificar}
                    accept=".pdf"
                    onChange={(e) => handleFileChange(e, setFieldValue)}
                    className={touched.nombreDocumento && (errors.nombreDocumento ? 'is-invalid' : 'is-valid')}
                    helperText={touched.nombreDocumento ? errors.nombreDocumento : ''}
                  />
                  {disabledInputs && (
                    <button
                      type="button"
                      className="btn btn-link p-0"
                      onClick={handleDownloadFile}
                      style={{ display: "block", marginTop: "0.5rem", textAlign: "left" }}
                    >
                      Ver
                    </button>
                  )}
                </Col>

                <Col md={4}>
                  <TextFieldDate
                    label={"Fecha de recepción factura*:"}
                    name="fechaRecepcionFactura"
                    value={values.fechaRecepcionFactura}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    disabled={!disabledInputs || !modificar || disabledBandera || !isPagado}
                    min={values.fechaSolicitud}
                    className={touched.fechaRecepcionFactura && (errors.fechaRecepcionFactura ? 'is-invalid' : '')}
                    helperText={touched.fechaRecepcionFactura ? errors.fechaRecepcionFactura : ''}
                  />
                </Col>

                <Col md={3} mt-4>
                  {!facturasRecibidas ? (
                    <div style={{ display: "flex", alignItems: "center", marginTop: "1.8rem" }}>
                      <Tooltip placement="top" text={"Recepción de facturas"}>
                        <span>
                          <IconButton
                            type="facturado"
                            onClick={() => handleFacturadoClick(values, validateForm, setTouched)}
                            disabled={isDownloading || !disabledInputs || !modificar || !isPagado}
                          />

                        </span>
                      </Tooltip>

                    </div>
                  ) : (
                    <div style={{ marginTop: "2rem" }}>
                      <Form.Check
                        type="checkbox"
                        label="Facturas recibidas"
                        checked={facturasRecibidas}
                        onChange={() => ""/* setFacturasRecibidas(false) */}
                        style={{ pointerEvents: "none" }}
                      />
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
                {PROFORMA.MSG009}
              </BasicModal>
              <BasicModal
                show={showPagado}
                size={"lg"}
                onHide={handleCloseModal}
                title="AVISO IMPORTANTE"
                denyText="Cancelar"
                handleDeny={handleCloseModalPagado}
                approveText="Continuar"
                handleApprove={() => handleEstatusPagado()}
              >
                {PROFORMA.MSG021}
              </BasicModal>
              <BasicModal
                show={showReplaceModal}
                handleDeny={handleCancelReplace}
                onHide={handleCloseModal}
                handleApprove={() => handleConfirmReplace(setFieldValue)}
                title="Confirmación de reemplazo"
                message="¿Desea reemplazar el documento cargado previamente?"
                approveText="Sí"
                denyText="No"
              >
                {PROFORMA.MSG012}
              </BasicModal>
              <Row>
                <Col md={12} className="text-end">
                  <Authorization process={"CON_SERV_DICT_SOLFAC"}>
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={handleConfirmPagado}
                      disabled={!modificar || !disabledInputs || isPagado}
                    >
                      Pagado
                    </Button>
                    <Button
                      variant="red"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={handleGoBack}
                      disabled={ver === true || !modificar || recepcionFacturaCheked}
                    >
                      Cancelar
                    </Button>
                    <Button
                      variant="green"
                      className="btn-sm ms-2 waves-effect waves-light"
                      type="submit"
                      disabled={ver === true || !modificar || isDownloading || recepcionFacturaCheked}
                      onClick={() => {
                        if (!isValid) {
                          errorToast(PROFORMA.MSG005);
                        }
                      }}
                    >
                      Guardar
                    </Button>
                  </Authorization>
                </Col>
              </Row>
            </Form>
          )
        }}

      </Formik>

    </>
  );
};

export default FormularioSolicitudFacturas;
