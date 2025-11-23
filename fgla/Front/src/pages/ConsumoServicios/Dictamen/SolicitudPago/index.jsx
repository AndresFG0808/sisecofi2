import React, { useCallback, useContext, useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Formik, Form, FieldArray } from "formik";
import { Row, Col, Button } from "react-bootstrap";
import * as Yup from 'yup';
import moment from 'moment';
import TextField from "../../../../components/formInputs/TextField";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import FormularioSolicitudPago from "./FormularioSolicitudPago";
import { Accordion } from "../../../../components";
import IconButton from "../../../../components/buttons/IconButton";
import FileField from "../../../../components/formInputs/FileField";
import VerDocumento from "../SolicitudPago/Components/VerDocumento";
import { postData, getData, putData } from "../../../../functions/api";
import Loader from "../../../../components/Loader";
import showMessage from "../../../../components/Messages";
import { NOTA_PAGO } from "../../../../constants/messages";
import Select from "../../../../components/formInputs/Select";
import BasicModal from "../../../../modals/BasicModal";
import { useToast } from "../../../../hooks/useToast";
import { DictamenContext } from "../context";
import Authorization from "../../../../components/Authorization";
import {
  useGetGestionDocumentalDictamenQuery,
} from "../GestionDocumental/store";

const SolicitudPago = ({ index }) => {
  const { state } = useLocation();
  const { idDictamen: idDictamenState, idContrato, idProveedor, editable, estatus } = { ...state?.dictamenState };
  const { errorToast } = useToast();
  const [isPagado, setIsPagado] = useState(false);
  const isEditable = estatus === 'Cancelado' ? true : !editable;
  const [loading, setLoading] = useState(true);
  const [showMensaje, setShowMensaje] = useState(false);
  const [message, setMessage] = useState("");
  const [catalogoTipoPago, setCatalogoTipoPago] = useState("");
  const { onReloadDictamenInfo, } = useContext(DictamenContext);
  const [formularioActivo, setFormularioActivo] = useState(editable);
  const [notapagoActiva, setNotaPagoActiva] = useState(null);
  const [estatusSolicitudPago, setEstatusSolicitudPago] = useState(false);
  const [estatusDictamen, setEstatusDictamen] = useState(null);
  const [referenciaActiva, setReferenciaActiva] = useState(true);
  const [activaReferenciaPago, setActivaReferenciaPago] = useState(false);
  const [ocultarPago, setOcultarPago] = useState(false);
  const [timer, setTimer] = useState(false);
  const [isOpenModalDocumento, setIsOpenModalDocumento] = useState(false);
  const [solicitudDePago, setSolicitudDePago] = useState(false);
  const [documentData, setDocumentData] = useState("");
  const [modalKey, setModalKey] = useState(0);
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [openModalSol, setOpenModalSol] = useState(false);
  const [desboqueo, setDesbloqueo] = useState(false);
  const [showReplaceModal, setShowReplaceModal] = useState(false);
  const [initialDataTable, setInitialDataTable] = useState(null);
  const [nuevoRegistro, setNuevoRegistro] = useState(false);
  const [documentoGenerado, setDocumentoGenerado] = useState(false);
  const [originalFile, setOriginalFile] = useState(null);
  const [referenciaConvenioColaboracionState, setReferenciaConvenioColaboracionState] = useState(false);
  const [errorCarga, setErrorCarga] = useState(false);
  const [hide, setHide] = useState(false);
  const [carga, setCarga] = useState(false);
  const [accionTipo, setAccionTipo] = useState(null);
  const [cargaCompleta, setCargaCompleta] = useState(false);
  const [newFile, setNewFile] = useState(null);
  const estatusNotaPago = [
    { estatus: true, nombre: "status", primaryKey: 1 },
    { estatus: true, nombre: "cancelado", primaryKey: 2 },
  ];
  const monedaDictamen = [{ estatus: true, nombre: "MXN", primaryKey: 1 }];
  const tasaDictamen = [{ estatus: true, nombre: "Tasa", primaryKey: 1 }];
  const [idSolicitudPago, setIdSolicitudPago] = useState("");
  const [fileLoaded, setFileLoaded] = useState(null);
  const [initialValuesReferenciaPago, setInitialValuesReferenciaPago] = useState({
    tipoNotificacion: '',
    oficioNotificacion: '',
    fechaNotificacion: '',
    notapagos: [],
  });
  const [initialValuesSolicitudPago, setInitialValuesSolicitudPago] = useState({
    oficioSolicitudPago: '',
    fechaSolicitud: '',
    pdfFile: '',
    documentoGenerado: documentoGenerado,
  });
  const [idDictamen, setIdDictamen] = useState(null);
  const [tipoCambio, setTipoCambio] = useState(null);

  const { refetch } =
    useGetGestionDocumentalDictamenQuery(encodeURIComponent(idDictamenState));

  useEffect(() => {
    let {
      idDictamen,
      tipoCambioReferencial,
      estatus,
    } = { ...state?.dictamenState }

    setIdDictamen(idDictamen);
    setTipoCambio(tipoCambioReferencial);
    setEstatusDictamen(estatus);
  }, [state]);

  const permisosIniciales = {
    leer: true,
    edicion: true,
  };

  const [permisos, setPermisos] = useState(permisosIniciales);

  useEffect(() => {
    //console.log(estatusSolicitudPago)
    //console.log(estatusDictamen)
    const estadosBloqueados = ["cancelado", "dictaminado", "proforma", "inicial"];
    let estatusValido = !estadosBloqueados.includes(
      estatusDictamen?.toLowerCase()
    );

    setPermisos((prev) => ({
      ...prev,
      modificar: estatusValido && editable,
    }));
  }, [estatusDictamen, editable]);
  const { modificar } = permisos;

  useEffect(() => {
    console.log(documentoGenerado)
  }, [idDictamenState, documentoGenerado]);

  useEffect(() => {
    const loadSolicitudPagoData = async () => {
      try {
        const response = await postData(`/admin-devengados/obtener-solicitud-pago`, { idDictamen: idDictamenState });
        //console.log(response.data);
        const lastRecord = response.data[response.data.length - 1];
        setCarga(false)
        const ruta = lastRecord.rutaSolicitudPago;
        const idSoli = lastRecord.idSolicitudPago;
        const nombre = lastRecord.rutaSolicitudPago
        if (lastRecord.documentoGenerado === true) {
          setDocumentoGenerado(true)
        }
        if (lastRecord.estatusSolicitud === true) {
          setEstatusSolicitudPago(true)
          setActivaReferenciaPago(true)
          setHide(true);
        }
        if (lastRecord.estatusPagado === true) {
          setDesbloqueo(true)
          setOcultarPago(true)
          setActivaReferenciaPago(true);
          setHide(true);
        }


        setInitialValuesSolicitudPago({
          oficioSolicitudPago: lastRecord.oficioSoliciturPago || '',
          fechaSolicitud: lastRecord.fechaSolicitud ? lastRecord.fechaSolicitud.split("T")[0] : '',
          pdfFile: nombre || "",
          documentoGenerado: documentoGenerado,
        });

        if (ruta) {
          try {
            const documentoDescarga = await getData(`/admin-devengados/solicitud-factura/descarga/oficio?path=${ruta}`);
            const fileName = ruta.split('/').pop();
            const file = convertirBase64AFile(documentoDescarga.data, fileName);
            setFileLoaded(file);
            setOriginalFile(file);

            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(file);
            const fileInput = document.getElementById("pdfFile");
            if (fileInput) {
              fileInput.files = dataTransfer.files;
            }

            setInitialValuesSolicitudPago((prevValues) => ({
              ...prevValues,
              pdfFile: file,
            }));
          } catch (error) {
            console.error("Error al descargar el archivo desde SatCloud");
            setInitialValuesSolicitudPago((prevValues) => ({
              ...prevValues,
              pdfFile: "",
            }));
          }
        }
        setIdSolicitudPago(idSoli);

        if (idSoli) {
          try {
            const responseRefPago = await getData(`/admin-devengados/referencia-pago/${idSoli}`);
            //console.log("Datos de referencia-pago:", responseRefPago.data);
            const esConvenio = responseRefPago.data.aplicaConvenioColaboracion
            setHide(true);
            if (esConvenio === true) {
              setReferenciaConvenioColaboracionState(true);
            } else {
              setReferenciaConvenioColaboracionState(false);
            }
            const allIdReferenciaPagoNull = responseRefPago.data.facturaModelList.every((factura) => {
              return factura.referenciaPagoModel?.idReferenciaPago === null;
            });
            if (allIdReferenciaPagoNull) {
              setNuevoRegistro(true);
            }
            const notapagosData = await Promise.all(
              responseRefPago.data.facturaModelList.map(async (factura, index) => {
                const referenciaPagoFileInputId = `referenciaPagoFile_${index}`;
                const convenioColaboracionFileInputId = `convenioColaboracionFile_${index}`;

                const referenciaPagoPDF = factura.referenciaPagoModel.rutaFichaNAFIN
                  ? await descargarYConvertirArchivoPDF(
                    factura.referenciaPagoModel.rutaFichaNAFIN,
                    `Ficha_Pago_${factura.referenciaPagoModel.idReferenciaPago}.pdf`,
                    referenciaPagoFileInputId
                  )
                  : null;

                const convenioColaboracionPDF = factura.referenciaConvenioColaboracion?.rutaFichaNAFIN
                  ? await descargarYConvertirArchivoPDF(
                    factura.referenciaConvenioColaboracion.rutaFichaNAFIN,
                    `Ficha_Convenio_${factura.referenciaConvenioColaboracion.idReferenciaPago}.pdf`,
                    convenioColaboracionFileInputId
                  )
                  : null;

                return {
                  referenciaPagoModel: {
                    idReferenciaPago: factura.referenciaPagoModel.idReferenciaPago,
                    idFactura: factura.referenciaPagoModel.idFactura,
                    folio: factura.referenciaPagoModel.folio,
                    desglose: factura.referenciaPagoModel.idDesglose,
                    folioFichaPago: factura.referenciaPagoModel.folioFichaPago,
                    fechaPago: factura.referenciaPagoModel.fechaPago ? moment(factura.referenciaPagoModel.fechaPago).format("YYYY-MM-DD") : "",
                    tipoCambioPagado: factura.referenciaPagoModel.tipoCambioPagado,
                    pagadoNAFIN: factura.referenciaPagoModel.pagadoNAFIN,
                    moneda: factura.referenciaPagoModel.tipoMoneda,
                    pdfFile: referenciaPagoPDF,
                  },
                  referenciaConvenioColaboracion: factura.referenciaConvenioColaboracion
                    ? {
                      folio: factura.referenciaConvenioColaboracion?.folio || "",
                      idReferenciaPago: factura.referenciaConvenioColaboracion.idReferenciaPago,
                      moneda: factura.referenciaConvenioColaboracion.tipoMoneda,
                      idFactura: factura.referenciaPagoModel.idFactura,
                      desglose: factura.referenciaConvenioColaboracion?.idDesglose || null,
                      folioFichaPago: factura.referenciaConvenioColaboracion?.folioFichaPago || "",
                      fechaPago: factura.referenciaConvenioColaboracion?.fechaPago ? moment(factura.referenciaConvenioColaboracion.fechaPago).format("YYYY-MM-DD") : "",
                      tipoCambioPagado: factura.referenciaConvenioColaboracion?.tipoCambioPagado || null,
                      pagadoNAFIN: factura.referenciaConvenioColaboracion?.pagadoNAFIN || null,
                      pdfFile: convenioColaboracionPDF,
                    }
                    : {
                      folio: "",
                      desglose: null,
                      folioFichaPago: "",
                      fechaPago: "",
                      tipoCambioPagado: null,
                      pagadoNAFIN: null,
                      pdfFile: null,
                    },
                };
              })
            );

            setInitialValuesReferenciaPago({
              tipoNotificacion: responseRefPago.data.facturaModelList[0].referenciaPagoModel.idTipoNotificacionPago,
              oficioNotificacion: responseRefPago.data.facturaModelList[0].referenciaPagoModel.oficioNotificacionPago,
              fechaNotificacion: responseRefPago.data.facturaModelList[0].referenciaPagoModel.fechaNotificacion?.split("T")[0] || "",
              notapagos: notapagosData,
              referenciaConvenioColaboracionState: false,
            });


            setCargaCompleta(true)
            setLoading(false);
          } catch (error) {
            console.error("Error en handleLoadRef:", error);
            setLoading(false);
          }
        }
      } catch (error) {
        console.error("Error fetching data:", error);
        setLoading(false);
      }
    };

    fetchCatalogo();
    loadSolicitudPagoData();
  }, [idDictamenState, carga]);

  const descargarYConvertirArchivoPDF = async (ruta, nombreArchivo, fileInputId) => {
    if (!ruta) return null;
    try {
      const documentoDescarga = await getData(`/admin-devengados/solicitud-factura/descarga/oficio?path=${ruta}`);
      const fileName = ruta.split('/').pop();
      const file = convertirBase64AFile(documentoDescarga.data, fileName);

      if (fileInputId) {
        const dataTransfer = new DataTransfer();
        dataTransfer.items.add(file);
        const fileInput = document.getElementById(fileInputId);
        if (fileInput) {
          fileInput.files = dataTransfer.files;
        }
      }
      return file;
    } catch (error) {
      console.error(`Error al descargar archivo PDF desde ${ruta}:`, error);
      return null;
    }
  };

  const fetchCatalogo = async () => {
    try {
      const response = await getData(`/admin-devengados/tipo-notificacion-pago`);
      //console.log(response.data)
      //console.log("API response:", response.data);
      const mappedData = response.data.map((item) => ({
        nombre: item.nombre,
        idTipoPago: item.primaryKey,
      }));
      setCatalogoTipoPago(mappedData);
      //console.log("estatusNotaPago:", mappedData);
    } catch (error) {
      setLoading(false);
      console.error("Error fetching dictamen data:", error);
    }
  };

  const handleCloseMessage = () => {
    setMessage("");
    setShowMensaje(false);
  };

  const convertirBase64AFile = (base64String, nombre) => {
    const byteCharacters = atob(base64String);
    const byteNumbers = Array.from(byteCharacters).map((char) => char.charCodeAt(0));
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: "application/pdf" });
    return new File([blob], nombre, { type: "application/pdf" });
  };

  const handleFileChange = (event, setFieldValue) => {
    const file = event.currentTarget.files[0];
    if (file && file.type === "application/pdf") {
      if (fileLoaded) {
        setNewFile(file);

      } else {
        setFieldValue("nombreDocumento", file);
        setFileLoaded(file);
      }
    } else {
      showMessage("Debe subir un archivo PDF válido");
      setFieldValue("nombreDocumento", "");
      setFileLoaded(null);
    }
  };

  const handleConfirmReplace = (setFieldValue) => {
    setFieldValue("nombreDocumento", newFile);
    setFileLoaded(newFile);
    setShowReplaceModal(false);
    setNewFile(null);
  };

  const handleCancelReplace = () => {
    setShowReplaceModal(false);
    setNewFile(null);
    if (originalFile) {
      setFileLoaded(originalFile);
    }
  };
  const validationSchemaReferenciaPago = Yup.object().shape({
    tipoNotificacion: Yup.number()
      .required("Dato requerido"),

    fechaNotificacion: Yup.date()
      .required("Dato requerido")
      .typeError("Fecha inválida"),
    notapagos: Yup.array().of(
      Yup.object().shape({
        referenciaPagoModel: Yup.object().shape({
          folio: Yup.string()
            .max(40, "Máximo 40 caracteres")
            .required("Dato requerido"),
          desglose: Yup.number()
            .required("Dato requerido")
            .oneOf([1, 2], "Desglose inválido"),
          folioFichaPago: Yup.string()
            .required("Dato requerido"),
          fechaPago: Yup.date()
            .required("Dato requerido")
            .typeError("Fecha inválida"),
          pagadoNAFIN: Yup.string()
            .required("Dato requerido"),
          /* pdfFile: Yup.mixed()
            .required("Dato requerido")
            .test(
              "fileFormat",
              "Solo se permiten archivos PDF",
              (value) => value && (value.type === "application/pdf" || (value.nombreArchivo && value.nombreArchivo.endsWith(".pdf")))
            ), */
        }),
        referenciaConvenioColaboracion: referenciaConvenioColaboracionState
          ? Yup.object().shape({
            folio: Yup.string()
              .max(40, "Máximo 40 caracteres")
              .required("Dato requerido"),
            desglose: Yup.number()
              .required("Dato requerido")
              .oneOf([1, 2], "Desglose inválido"),
            folioFichaPago: Yup.string()
              .required("Dato requerido"),
            fechaPago: Yup.date()
              .required("Dato requerido")
              .typeError("Fecha inválida"),
            pagadoNAFIN: Yup.string()
              .required("Dato requerido"),
            /* pdfFile: Yup.mixed()
              .required("Dato requerido")
              .test(
                "fileFormat",
                "Solo se permiten archivos PDF",
                (value) => value && (value.type === "application/pdf" || (value.nombreArchivo && value.nombreArchivo.endsWith(".pdf")))
              ), */
          })
          : Yup.object().notRequired(),
      })
    ),
  });

  const nameInput = useCallback(
    (name) => `notapagos[${index}].${name}`,
    [index]
  );

  const cargarPDF = (event, setFieldValue) => {
    let { files } = event.target;
    if (files && files.length > 0) {
      const file = files[0];
      let { name } = file;
      setFieldValue(nameInput("archivoPdf"), file);
      setFieldValue(nameInput("nombrePdf"), name);
      setFieldValue(nameInput("pdfCargado"), true);
    }
  };

  const handleCanceModal = () => {
    setIsOpenModal(false);
  };

  const validationSchemaSolicitudPago = Yup.object().shape({
    oficioSolicitudPago: Yup.string().required("Dato requerido"),
    fechaSolicitud: Yup.string().required("Dato requerido"),
  });


  const convertFileToBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });
  };

  const handleOpenShowPdf = (values) => {
    setDocumentData("https://www.orimi.com/pdf-test.pdf");
    setIsOpenModalDocumento(true);
    setModalKey((prevKey) => prevKey + 1);
  };


  const handleCloseShowPdf = () => {
    setIsOpenModalDocumento(false);
    setIsOpenModal(false);
    setDocumentData("");
  };


  /*const handleGuardarSolicitudPago = async (values) => {

    if (!idSolicitudPago) {
      if (documentoGenerado === false) {
        setErrorCarga(true)
      }
      if (documentoGenerado === true) {
        try {
          setLoading(true);
          setErrorCarga(false)
          try {
            const payload = {
              idDictamen: idDictamenState,
              oficioSolicitudPago: values.oficioSolicitudPago,
              fechaSolicitud: `${values.fechaSolicitud}T00:00:00.000Z`,
              archivoPdf: values.pdfFileB64,
              solicitudPago: false,
              nombreArchivo: values.nombreArchivo,
              documentoGenerado: documentoGenerado
            };
            const response = await postData(`/admin-devengados/solicitud-pago`, payload);
            setCarga(true)
            setReferenciaActiva(false);
            showMessage("Los datos se guardaron correctamente.");
            setLoading(false);
            setInitialDataTable(response.data);
            await onReloadDictamenInfo(idDictamen);
          } catch (error) {
            if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
              showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
              await onReloadDictamenInfo(idDictamen);
              setLoading(false);
            } else {
              showMessage(NOTA_PAGO.MSG001);
              await onReloadDictamenInfo(idDictamen);
              setLoading(false);
            }
         }
        } catch (error) {
          if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
            showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
            await onReloadDictamenInfo(idDictamen);
            setLoading(false);
          } else {
            showMessage(NOTA_PAGO.MSG001);
            await onReloadDictamenInfo(idDictamen);
            setLoading(false);
          }
        }
     }
      refetch();
    } else {
      if (documentoGenerado === false) {
        setErrorCarga(true)
      }
      if (documentoGenerado === true) {
        if (solicitudDePago) {

          const payload = {
            idDictamen: {
              idDictamen: idDictamenState
            },
            archivo: values.pdfFileB64,
            estatusSolicitud: true,
            idSolicitudPago: idSolicitudPago,
            nombreArchivo: values.nombreArchivo,
            documentoGenerado: documentoGenerado
          };
          console.log(payload);
         setActivaReferenciaPago(true);
         if (estatusSolicitudPago === false) {
           try {
             setLoading(true);
             const response = await putData(`/admin-devengados/estatus-solicitud-pago`, payload);
             await onReloadDictamenInfo(idDictamen);
             setEstatusSolicitudPago(true);
             setHide(true);
           console.log(response.data);
               setActivaReferenciaPago(true);
               showMessage(NOTA_PAGO.MSG008);
               setLoading(false);
             } catch (error) {
               if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
                 showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
                 await onReloadDictamenInfo(idDictamen);
                 setActivaReferenciaPago(false);
                 setLoading(false);
               } else {
                 showMessage(NOTA_PAGO.MSG001);
                 setActivaReferenciaPago(false);
                 await onReloadDictamenInfo(idDictamen);
                 setLoading(false);
               }
             }
           }

         } else {
           try {
             setLoading(true);
             setErrorCarga(false)
             try {
               const payload = {
                 idDictamen: {
                   idDictamen: idDictamenState
                 },
                 idSolicitudPago: idSolicitudPago,
                 oficioSolicitudPago: values.oficioSolicitudPago,
                 fechaSolicitud: `${values.fechaSolicitud}T00:00:00.000Z`,
                 archivoPdf: values.pdfFileB64,
                 nombreArchivo: values.nombreArchivo,
                 documentoGenerado: documentoGenerado
               };
               const response = await putData(`/admin-devengados/editar-solicitud-pago`, payload);
               console.log(response.data);
               setReferenciaActiva(false);
               setLoading(false);
               showMessage("Los datos se guardaron correctamente.");
               setInitialDataTable(response.data);

             } catch (error) {
               if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
                 showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
                 await onReloadDictamenInfo(idDictamen);
                 setLoading(false);
               } else {
                 showMessage(NOTA_PAGO.MSG001);
                 await onReloadDictamenInfo(idDictamen);
                 setLoading(false);
               }
             }
           } catch (error) {
             if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
               showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
               await onReloadDictamenInfo(idDictamen);
               setLoading(false);
             } else {
               showMessage(NOTA_PAGO.MSG001);
               await onReloadDictamenInfo(idDictamen);
               setLoading(false);
             }
           }
         }
       }
       refetch();
     }
   };
   */


  const handleGuardarSolicitudPago = async (values) => {
    if (!idSolicitudPago && !values.pdfFileB64) {
      showMessage("El archivo PDF es requerido.");
      return;
    }

    if (!idSolicitudPago) {

      try {
        setLoading(true);
        setErrorCarga(false);

        const payload = {
          idDictamen: idDictamenState,
          oficioSolicitudPago: values.oficioSolicitudPago,
          fechaSolicitud: `${values.fechaSolicitud}T00:00:00.000Z`,
          archivoPdf: values.pdfFileB64 || null,
          solicitudPago: false,
          nombreArchivo: values.nombreArchivo || null,
          documentoGenerado: documentoGenerado || false
        };

        const response = await postData(`/admin-devengados/solicitud-pago`, payload);
        setCarga(true);
        setReferenciaActiva(false);
        showMessage("Los datos se guardaron correctamente.");
        setLoading(false);
        setInitialDataTable(response.data);
        await onReloadDictamenInfo(idDictamen);

      } catch (error) {
        if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
          showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente");
          await onReloadDictamenInfo(idDictamen);
          setLoading(false);
        } else {
          showMessage(NOTA_PAGO.MSG001);
          await onReloadDictamenInfo(idDictamen);
          setLoading(false);
        }
      }
      refetch();

    } else {

      if (solicitudDePago) {

        const payload = {
          idDictamen: {
            idDictamen: idDictamenState
          },
          archivo: values.pdfFileB64 || null,
          estatusSolicitud: true,
          idSolicitudPago: idSolicitudPago,
          nombreArchivo: values.nombreArchivo || null,
          documentoGenerado: documentoGenerado || false
        };

        //console.log(payload);
        setActivaReferenciaPago(true);

        if (estatusSolicitudPago === false) {
          try {
            setLoading(true);
            const response = await putData(`/admin-devengados/estatus-solicitud-pago`, payload);
            await onReloadDictamenInfo(idDictamen);
            setEstatusSolicitudPago(true);
            setHide(true);
            console.log(response.data);
            setActivaReferenciaPago(true);
            showMessage(NOTA_PAGO.MSG008);
            setLoading(false);
          } catch (error) {
            if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
              showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente");
              await onReloadDictamenInfo(idDictamen);
              setActivaReferenciaPago(false);
              setLoading(false);
            } else {
              showMessage(NOTA_PAGO.MSG001);
              setActivaReferenciaPago(false);
              await onReloadDictamenInfo(idDictamen);
              setLoading(false);
            }
          }
        }

      } else {

        try {
          setLoading(true);
          setErrorCarga(false);

          const payload = {
            idDictamen: {
              idDictamen: idDictamenState
            },
            idSolicitudPago: idSolicitudPago,
            oficioSolicitudPago: values.oficioSolicitudPago,
            fechaSolicitud: `${values.fechaSolicitud}T00:00:00.000Z`,
            archivoPdf: values.pdfFileB64 || null,
            nombreArchivo: values.nombreArchivo || null,
            documentoGenerado: documentoGenerado || false
          };

          const response = await putData(`/admin-devengados/editar-solicitud-pago`, payload);
          //console.log(response.data);
          setReferenciaActiva(false);
          setLoading(false);
          showMessage("Los datos se guardaron correctamente.");
          setInitialDataTable(response.data);

        } catch (error) {
          if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
            showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente");
            await onReloadDictamenInfo(idDictamen);
            setLoading(false);
          } else {
            showMessage(NOTA_PAGO.MSG001);
            await onReloadDictamenInfo(idDictamen);
            setLoading(false);
          }
        }
      }
      refetch();
    }
  };


  const handleGuardarReferenciaPago = async (values, isPagado, referenciaConvenioColaboracionState) => {
    setTimer(true)
    try {
      setLoading(true);
      const idDictamen = idDictamenState;

      let infoReferenciaPagoDto;
      if (referenciaConvenioColaboracionState === true) {
        infoReferenciaPagoDto = values.notapagos.flatMap((notapago) => {
          const referenciaPagoModelData = {
            idReferenciaPago: notapago.referenciaPagoModel.idReferenciaPago || null,
            idFactura: notapago.referenciaPagoModel.idFactura || null,
            tipoNotificacionPago: values.tipoNotificacion,
            oficioNotificacionPago: values.oficioNotificacion,
            fechaNotificacion: `${values.fechaNotificacion}T06:00:00`,
            folioFichaPago: notapago.referenciaPagoModel.folioFichaPago,
            fechaPago: `${notapago.referenciaPagoModel.fechaPago}T06:00:00`,
            tipoCambioPagado: parseFloat(notapago.referenciaPagoModel.tipoCambioPagado),
            pagadoNAFIN: parseFloat(notapago.referenciaPagoModel.pagadoNAFIN),
            archivoFicha: notapago.referenciaPagoModel.pdfFile ? notapago.referenciaPagoModel.pdfFile.archivo : null,
            nombreArchivo: notapago.referenciaPagoModel.pdfFile ? notapago.referenciaPagoModel.pdfFile.nombreArchivo : null,
            idDesglose: notapago.referenciaPagoModel.desglose,
            convenioColaboracion: false,
          };

          const referenciaConvenioColaboracionData = notapago.referenciaConvenioColaboracion
            ? {
              idReferenciaPago: notapago.referenciaConvenioColaboracion.idReferenciaPago,
              idFactura: notapago.referenciaConvenioColaboracion.idFactura,
              tipoNotificacionPago: values.tipoNotificacion,
              oficioNotificacionPago: values.oficioNotificacion,
              fechaNotificacion: `${values.fechaNotificacion}T06:00:00`,
              folioFichaPago: notapago.referenciaConvenioColaboracion.folioFichaPago,
              fechaPago: `${notapago.referenciaConvenioColaboracion.fechaPago}T06:00:00`,
              tipoCambioPagado: parseFloat(notapago.referenciaConvenioColaboracion.tipoCambioPagado),
              pagadoNAFIN: parseFloat(notapago.referenciaConvenioColaboracion.pagadoNAFIN),
              archivoFicha: notapago.referenciaConvenioColaboracion.pdfFile ? notapago.referenciaConvenioColaboracion.pdfFile.archivo : null,
              nombreArchivo: notapago.referenciaConvenioColaboracion.pdfFile ? notapago.referenciaConvenioColaboracion.pdfFile.nombreArchivo : null,
              idDesglose: notapago.referenciaConvenioColaboracion.desglose,
              convenioColaboracion: true,
            }
            : null;

          return referenciaConvenioColaboracionData
            ? [referenciaPagoModelData, referenciaConvenioColaboracionData]
            : [referenciaPagoModelData];
        });
      } if (referenciaConvenioColaboracionState === false) {
        infoReferenciaPagoDto = values.notapagos.flatMap((notapago) => {
          const referenciaPagoModelData = {
            idReferenciaPago: notapago.referenciaPagoModel.idReferenciaPago || null,
            idFactura: notapago.referenciaPagoModel.idFactura || null,
            tipoNotificacionPago: values.tipoNotificacion,
            oficioNotificacionPago: values.oficioNotificacion,
            fechaNotificacion: `${values.fechaNotificacion}T06:00:00`,
            folioFichaPago: notapago.referenciaPagoModel.folioFichaPago,
            fechaPago: `${notapago.referenciaPagoModel.fechaPago}T06:00:00`,
            tipoCambioPagado: parseFloat(notapago.referenciaPagoModel.tipoCambioPagado),
            pagadoNAFIN: parseFloat(notapago.referenciaPagoModel.pagadoNAFIN),
            archivoFicha: notapago.referenciaPagoModel.pdfFile ? notapago.referenciaPagoModel.pdfFile.archivo : null,
            nombreArchivo: notapago.referenciaPagoModel.pdfFile ? notapago.referenciaPagoModel.pdfFile.nombreArchivo : null,
            idDesglose: notapago.referenciaPagoModel.desglose,
            convenioColaboracion: false,
          };

          return [referenciaPagoModelData];
        });
      }

      const payload = {
        dictamenId: {
          idDictamen,
        },
        idSolicitudPago: idSolicitudPago,
        estatusPagado: isPagado,
        infoReferenciaPagoDto,
      };

      if (isPagado) {
        setCarga(true);
        const response = await putData(`/admin-devengados/editar-referencia-pago`, payload);
        setTimer(false)
        if (response && response.data) {
          await onReloadDictamenInfo(idDictamen);
          setTimer(false)
          showMessage(NOTA_PAGO.MSG006);
          setDesbloqueo(true);
          setOcultarPago(true);
          //console.log(response.data);

          const dictamenId = {
            idDictamen: idDictamenState
          };
          const pagado = await putData(`/admin-devengados/actualizar-resumen-facturado`, dictamenId);
          setTimer(false)
        }
      } else {
        const response = nuevoRegistro
          ? await postData(`/admin-devengados/referencia-pago`, payload)
          : await putData(`/admin-devengados/editar-referencia-pago`, payload);
        await onReloadDictamenInfo(idDictamen);
        setTimer(false)
        console.log(response.data);
        setCarga(true);
        setCargaCompleta(false);
        showMessage("Los datos se guardaron correctamente.");
      }

      setReferenciaActiva(false);
      setLoading(false);
      refetch();
    } catch (error) {
      setTimer(false)
      if (error.response.data.mensaje.includes('Se perdió la conexión con Sat Cloud, favor de intentar nuevamente')) {
        showMessage("Se perdió la conexión con Sat Cloud, favor de intentar nuevamente")
        await onReloadDictamenInfo(idDictamen);
        setLoading(false);
      } else {
        showMessage(NOTA_PAGO.MSG001);
        await onReloadDictamenInfo(idDictamen);
        setLoading(false);
      }
    }
  };

  const handleApprove = () => {
    setCarga(true);
    setHide(false);
    setIsOpenModal(false);
    handleCanceModal();
  };

  return (
    <>
      {loading && <Loader />}
      <BasicModal
        size="md"
        show={openModalSol}
        onHide={() => {
          setOpenModalSol(false);
        }}
        title="Mensaje"
        denyText="No"
        handleDeny={() => {
          setOpenModalSol(false);
        }}
        approveText={"Sí"}
        handleApprove={() => {
          setCarga(true)
          setOpenModalSol(false);
          handleCanceModal();
        }}
      >
        {NOTA_PAGO.MSG009}
      </BasicModal>

      <SingleBasicModal
        size="md"
        show={showMensaje}
        onHide={handleCloseMessage}
        title="Mensaje"
        approveText={"Aceptar"}
      >
        {message}
      </SingleBasicModal>

      <Formik
        initialValues={initialValuesSolicitudPago}
        validationSchema={validationSchemaSolicitudPago}
        onSubmit={handleGuardarSolicitudPago}
        enableReinitialize
      >
        {({ values, handleSubmit, setFieldValue, isValid, errors, touched, handleBlur }


        ) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={3}>
                <TextField
                  label="Oficio de solicitud de pago*:"
                  name="oficioSolicitudPago"
                  value={values.oficioSolicitudPago}
                  onBlur={handleBlur}
                  onChange={(e) => setFieldValue('oficioSolicitudPago', e.target.value)}
                  disabled={!modificar || desboqueo || estatusSolicitudPago}
                  className={touched.oficioSolicitudPago && (errors.oficioSolicitudPago ? 'is-invalid' : 'is-valid')}
                  helperText={touched.oficioSolicitudPago ? errors.oficioSolicitudPago : ''}
                />
              </Col>
              <Col md={3}>
                <TextFieldDate
                  label="Fecha de solicitud*:"
                  name="fechaSolicitud"
                  value={values.fechaSolicitud ? moment(values.fechaSolicitud).format('YYYY-MM-DD') : ''}
                  onBlur={handleBlur}
                  onChange={(e) => setFieldValue('fechaSolicitud', e.target.value)}
                  disabled={!modificar || desboqueo || estatusSolicitudPago}
                  className={touched.fechaSolicitud && (errors.fechaSolicitud ? 'is-invalid' : 'is-valid')}
                  helperText={touched.fechaSolicitud ? errors.fechaSolicitud : ''}
                />
              </Col>
              <Col md={3} className="text-center">
                <p>Generar documento:</p>
                <IconButton
                  type="excel"
                  name="documentoGenerado"
                  disabled={!values.oficioSolicitudPago || !values.fechaSolicitud || !modificar || desboqueo || estatusSolicitudPago}
                  onClick={() => handleOpenShowPdf(values)}
                  // className={touched.documentoGenerado && (errors.documentoGenerado ? 'is-invalid' : 'is-valid')}
                  className=""
                  //helperText={touched.documentoGenerado ? errors.documentoGenerado : ''}
                  helperText=""
                >

                </IconButton>
                {/* {errorCarga && (
                  <p className="text-danger">Descarga requerida</p>
                )} */}

              </Col>
              <Col md={3}>
                <FileField
                  label={"Añadir PDF:"}
                  accept={"application/pdf"}
                  name="pdfFile"
                  disabled={!modificar || desboqueo || estatusSolicitudPago}
                  onChange={(event) => {
                    handleFileChange(event, setFieldValue)
                    const file = event.currentTarget.files[0];
                    if (file && file.type === "application/pdf") {
                      setFieldValue("pdfFile", file);
                      setFieldValue("nombreArchivo", file.name);
                      const reader = new FileReader();
                      reader.onload = (e) => {
                        const base64string = e.target.result.split(",")[1];
                        setFieldValue("pdfFileB64", base64string);
                      };
                      reader.onerror = (error) => { };
                      reader.readAsDataURL(file);
                    } else {
                      showMessage(NOTA_PAGO.MSG004);
                      setFieldValue("pdfFile", null);
                      setFieldValue("pdfFileB64", null);
                      setFieldValue("nombreArchivo", null);
                      event.target.value = null;
                    }
                  }}
                  md={3}
                  showClasses={true}
                />
              </Col>

            </Row>

            <Row>
              <Col md={12} className="text-end">
                <Authorization process={"CON_SERV_ADMIN_DICT"}>
                  <Button variant="red"
                    className="btn-sm ms-2 waves-effect waves-light"
                    type="button"
                    disabled={!modificar || desboqueo || estatusSolicitudPago}
                    onClick={() => setOpenModalSol(true)}>
                    Cancelar
                  </Button>
                  <Button variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={() => {
                      setSolicitudDePago(false);
                    }}
                    type="submit" disabled={!modificar || desboqueo || estatusSolicitudPago}>
                    Guardar
                  </Button>
                </Authorization>
                {estatusSolicitudPago ? (
                  <p></p>
                ) : (
                  <Authorization process={"CON_SERV_DICT_STA_SOLPAG"}>
                    <Button variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      type="submit"
                      onClick={() => {
                        setSolicitudDePago(true);
                      }}
                      disabled={!values.pdfFile || !modificar || desboqueo || estatusSolicitudPago}>
                      Solicitud de pago
                    </Button>
                  </Authorization>
                )}
              </Col>
            </Row>
            <VerDocumento
              title="Solicitud de pago"
              show={isOpenModalDocumento}
              urlPdfBlob={documentData}
              oficioSolicitudPago={values.oficioSolicitudPago}
              fechaSolicitud={values.fechaSolicitud}
              onHide={handleCloseShowPdf}
              setDocumentoGenerado={setDocumentoGenerado}
              setErrorCarga={setErrorCarga}
            />
            <BasicModal
              show={showReplaceModal}
              handleDeny={handleCancelReplace}
              handleApprove={() => handleConfirmReplace(setFieldValue)}
              title="Confirmación de reemplazo"
              approveText="Sí"
              denyText="No"
            >
              ¿Desea reemplazar el documento cargado previamente?
            </BasicModal>
          </Form>
        )}
      </Formik>

      {activaReferenciaPago && (
        <Formik
          initialValues={initialValuesReferenciaPago}
          validationSchema={validationSchemaReferenciaPago}
          onSubmit={(values, { setSubmitting }) => {
            handleGuardarReferenciaPago(values, values.isPagado, values.referenciaConvenioColaboracionState);
            setSubmitting(false);
          }}
          enableReinitialize
        >
          {({
            values,
            handleSubmit,
            setFieldValue,
            handleBlur,
            errors,
            touched,
            validateForm,
          }) => (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Accordion title="Referencia de pago" show={hide}>
                <Row>
                  <Col md={4}>
                    <Select
                      disabled={!modificar || desboqueo}
                      label="Tipo de notificación de pago*:"
                      name="tipoNotificacion"
                      value={values.tipoNotificacion}
                      onBlur={handleBlur}
                      onChange={(e) => {
                        const selectedValue = e.target.value;
                        setFieldValue("tipoNotificacion", selectedValue);
                        if ([1, 3, 4].includes(Number(selectedValue))) {
                          setFieldValue("oficioNotificacion", " ");
                        }
                      }}
                      className={
                        touched.tipoNotificacion &&
                        (errors.tipoNotificacion ? "is-invalid" : "is-valid")
                      }
                      helperText={
                        touched.tipoNotificacion ? errors.tipoNotificacion : ""
                      }
                      options={catalogoTipoPago}
                      keyValue="idTipoPago"
                      keyTextValue="nombre"
                    />
                  </Col>
                  <Col md={4}>
                    <TextField
                      label="Oficio de notificación de pago*:"
                      name="oficioNotificacion"
                      disabled={!editable || desboqueo || [3, 4].includes(Number(values.tipoNotificacion))}
                      value={values.oficioNotificacion}
                      onBlur={handleBlur}
                      onChange={(e) => setFieldValue("oficioNotificacion", e.target.value)}
                      className={
                        touched.oficioNotificacion &&
                        (errors.oficioNotificacion ? "is-invalid" : "is-valid")
                      }
                      helperText={
                        touched.oficioNotificacion ? errors.oficioNotificacion : ""
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <TextFieldDate
                      label="Fecha de notificación*:"
                      name="fechaNotificacion"
                      disabled={!editable || desboqueo}
                      value={values.fechaNotificacion}
                      onBlur={handleBlur}
                      onChange={(e) => setFieldValue("fechaNotificacion", e.target.value)}
                      className={
                        touched.fechaNotificacion &&
                        (errors.fechaNotificacion ? "is-invalid" : "is-valid")
                      }
                      helperText={
                        touched.fechaNotificacion ? errors.fechaNotificacion : ""
                      }
                    />
                  </Col>
                </Row>
                <hr className="my-divider" />

                <FieldArray name="notapagos">
                  {(arrayHelpers) => (
                    <>
                      {values.notapagos.map((notapago, index) => (
                        <div key={index}>
                          <FormularioSolicitudPago
                            formularioActivo={formularioActivo && notapagoActiva === index}
                            index={index}
                            numeroIncremental={index + 1}
                            notapago={notapago}
                            arrayHelpers={arrayHelpers}
                            handleShowMessage={setShowMensaje}
                            estatusCatalogo={estatusNotaPago}
                            referenciaConvenioColaboracionState={referenciaConvenioColaboracionState}
                            monedaDictamen={monedaDictamen}
                            tasaDictamen={tasaDictamen}
                            desboqueo={desboqueo}
                            disabled={!editable || desboqueo}
                            errors={errors}
                            touched={touched}
                          />
                          <hr />
                        </div>
                      ))}
                    </>
                  )}
                </FieldArray>

                <Row>
                  <Col md={12} className="text-end">
                    <Authorization process={"CON_SERV_ADMIN_DICT"}>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="button"
                        disabled={!editable || desboqueo}
                        onClick={() => setIsOpenModal(true)}
                      >
                        Cancelar
                      </Button>
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="submit"
                        disabled={timer || !editable || desboqueo}
                        onClick={() => {
                          setIsPagado(false);
                          validateForm().then((errors) => {
                            console.log(errors)
                            if (Object.keys(errors).length === 0) {
                              setFieldValue("isPagado", false, false);
                              setFieldValue("referenciaConvenioColaboracionState", referenciaConvenioColaboracionState)
                            } else {
                              errorToast(NOTA_PAGO.MSG005);
                            }
                          });
                        }}
                      >
                        Guardar
                      </Button>
                    </Authorization>
                    {ocultarPago ? (
                      <p></p>
                    ) : (

                      <Authorization process={"CON_SERV_DICT_STA_PAG"}>
                        <Button
                          variant="gray"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                          disabled={!editable || desboqueo}
                          onClick={() => {
                            setIsPagado(true);
                            validateForm().then((errors) => {
                              console.log(errors)
                              if (Object.keys(errors).length === 0) {
                                setFieldValue("isPagado", true, false);
                                setFieldValue("referenciaConvenioColaboracionState", referenciaConvenioColaboracionState)
                              } else {
                                errorToast(NOTA_PAGO.MSG005);
                              }
                            });
                          }}
                        >
                          Pagado
                        </Button>
                      </Authorization>
                    )}


                  </Col>
                </Row>
              </Accordion>
              <BasicModal
                size="md"
                show={isOpenModal}
                onHide={() => {
                  setIsOpenModal(false);
                }}
                title="Mensaje"
                denyText="No"
                handleDeny={() => {
                  setIsOpenModal(false);
                }}
                approveText={"Sí"}
                handleApprove={handleApprove}
              >
                {NOTA_PAGO.MSG009}
              </BasicModal>
            </Form>
          )}
        </Formik>
      )}
    </>
  );
};

export default SolicitudPago;
