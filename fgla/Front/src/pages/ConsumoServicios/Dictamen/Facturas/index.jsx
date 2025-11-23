import React, { useState, useEffect, useMemo, useContext } from "react";
import FormularioFacturas from "./FormularioFacturas";
import { Formik, FieldArray } from "formik";
import Loader from "../../../../components/Loader";
import { Row, Col, Button, Form } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import BasicModal from "../../../../modals/BasicModal";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import {
  GENERAR_FACTURA,
  PROFORMA,
  REGISTRAR_REINTEGROS,
} from "../../../../constants/messages";
import { useToast } from "../../../../hooks/useToast";
import {
  validationSchema,
  emptyFactura,
  FormatFactura,
  validarFechasSF,
} from "./Components/FacturasUtilities";
import _ from "lodash";
import ComentariosSimple from "../../../../components/ComentariosSimple";
import {
  usePostFacturasMutation,
  useGetFacturasRecibidasQuery,
  usePostGuardarFacturaMutation,
  usePostCancelarFacturaMutation,
  usePostLeerXMLFacturaMutation,
  useGetCatEstatusFacturasQuery,
  useGetCatMonedaFacturasQuery,
  useGetCatTasaFacturasQuery,
  useGetValidarConvenioQuery,
  usePostActualizarFacturaMutation,
  usePostValidarRegresarProformaMutation,
  usePostRegresarProformaMutation,
  usePostBuscarSolicitudFacturaMutation,
  usePostActualizarResumenFacturasMutation,
  usePostGuardarFacturasRecibidasMutation,
} from "./store";
import { useLocation } from "react-router-dom";
import { DictamenContext } from "../context";
import { getDataParams } from "../../../../functions/api";
import moment from "moment";
import Authorization from "../../../../components/Authorization";
import {
  useGetGestionDocumentalDictamenQuery,
} from "../GestionDocumental/store";
const Facturas = ({ setReload, setSubmitForm }) => {
  const { onReloadDictamenInfo, setShowSecciones, SECCIONES_INICIAL, } =
    useContext(DictamenContext);

  const [idDictamen, setIdDictamen] = useState(null);
  const [idContrato, setIdContrato] = useState(null);
  const [idProveedor, setIdProveedor] = useState(null);
  const [tipoCambio, setTipoCambio] = useState(null);
  const [estatusDictamen, setEstatusDictamen] = useState(null);
  const [editable, setEditable] = useState(null);
  const location = useLocation();

  useEffect(() => {
    let {
      idDictamen,
      idContrato,
      idProveedor,
      tipoCambioReferencial,
      estatus,
      editable,
    } = location?.state?.dictamenState ?? {};
    setIdContrato(idContrato);
    setIdDictamen(idDictamen);
    setIdProveedor(idProveedor);
    setTipoCambio(tipoCambioReferencial);
    setEstatusDictamen(estatus);
    setEditable(editable);
  }, [location]);

    const { refetch } =
      useGetGestionDocumentalDictamenQuery(encodeURIComponent(idDictamen));


  //#region Verificar Permisos

  const initialPermisos = useMemo(() => {
    return {
      alta: false,
      cancelar: false,
      modificar: false,
      consultar: false,
    };
  }, []);

  const [permisos, setPermisos] = useState(initialPermisos);

  useEffect(() => {
    let estatusValido = ["proforma", "facturado"].includes(
      estatusDictamen?.toLowerCase()
    );
    ///Validar permisos posterior aqui
    setPermisos((prev) => ({
      ...prev,
      modificar: estatusValido && editable,
    }));
  }, [estatusDictamen, editable]);

  const { modificar } = permisos;
  //#endregion

  //#region Message Modal
  const { errorToast } = useToast();

  const [showMessage, setShowMessage] = useState(false);
  const [message, setMessage] = useState("");
  const handleCloseMessage = () => {
    setMessage("");
    setShowMessage(false);
  };
  const handleShowMessage = (message) => {
    setMessage(message);
    setShowMessage(true);
  };
  //#endregion

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

  //#region Servicios

  const [getBuscarSF, { isLoading: isLoadingBuscaSF }] =
    usePostBuscarSolicitudFacturaMutation();

  //Validar Error

  const ValidateError = (response, defaultMessage = "") => {
    const message =
      _.isArray(response.error?.data?.mensaje) &&
      !_.isEmpty(response.error.data.mensaje)
        ? response.error.data.mensaje[0]
        : response.error?.data?.mensaje || defaultMessage;

    handleShowMessage(message);
  };

  //Get Facturas

  const [getFacturas, { isLoading: isLoadingFacturas, data: facturasData }] =
    usePostFacturasMutation();

  //aplica cc

  const [aplicaCC, setAplicaCC] = useState(false);
  const { isLoading: isLoadingAplicaCC, data: dataCC } =
    useGetValidarConvenioQuery(idContrato, { skip: !idContrato });

  useEffect(() => {
    if (dataCC?.data) setAplicaCC(dataCC.data);
  }, [dataCC]);
  // facturas recibidas
  const {
    isLoading: isLoadingFacturasRecibidas,
    // data: dataFacturasRecibidas,
  } = useGetFacturasRecibidasQuery(idDictamen, { skip: !idDictamen });

  //Guardar Facturas
  const [postGuardarFacturas, { isLoading: isLoadingGuardarFactura }] =
    usePostGuardarFacturaMutation();
  //Actualizar Factura
  const [postActualizarFactura, { isLoading: isLoadingActualizarFactura }] =
    usePostActualizarFacturaMutation();

  //Cancelar facturas
  const [postCancelarFacturas, { isLoading: isLoadingCancelar }] =
    usePostCancelarFacturaMutation();

  const { isLoading: isLoadingEstatus, data: dataEstatus } =
    useGetCatEstatusFacturasQuery();
  const { isLoading: isLoadingMoneda, data: dataMoneda } =
    useGetCatMonedaFacturasQuery();
  const { isLoading: isLoadingTasa, data: dataTasa } =
    useGetCatTasaFacturasQuery();

  //Regresar Proforma
  const [
    postValidarRegresarProforma,
    { isLoading: isLoadingValidarRegresarProforma },
  ] = usePostValidarRegresarProformaMutation();
  const [postRegresarProforma, { isLoading: isLoadingRegresarProforma }] =
    usePostRegresarProformaMutation();

  const [
    postFacturasRecibidas,
    { isLoading: isLoadingFacturasRecibidasGuardar },
  ] = usePostGuardarFacturasRecibidasMutation();
  //#endregion

  const [facturasRecibidas, setFacturasRecibidas] = useState(true); //Verificar donde se obtiene

  //#region Obtener Catalogos

  const [estatusCat, setEstatusCat] = useState([]);
  useEffect(() => {
    setEstatusCat(dataEstatus);
  }, [dataEstatus]);

  const [monedaCat, setMonedaCat] = useState([]);
  useEffect(() => {
    setMonedaCat(dataMoneda);
  }, [dataMoneda]);

  const [tasaCat, setTasaCat] = useState([]);
  useEffect(() => {
    setTasaCat(dataTasa);
  }, [dataTasa]);

  const [postActualizarResumen, { isLoading: isLoadingActualizarResumen }] =
    usePostActualizarResumenFacturasMutation();
  //Leer Xml

  const [leerXml, { isLoading: isLoadingLeerXml }] =
    usePostLeerXMLFacturaMutation();

  //#endregion

  //#region Informacion Factura

  const [initialValues, setInitialValues] = useState({
    facturas: [emptyFactura],
  });

  const [hasFacturas, setHasFacturas] = useState(false);

  ////Validate Data
  useEffect(() => {
    if (!_.isEmpty(facturasData?.data)) {
      let { data = [] } = facturasData;
      let facturas = _.orderBy(data, ["idFactura"], ["asc"]).map(
        (factura, index) =>
          FormatFactura(
            factura,
            index,
            monedaCat,
            tasaCat,
            estatusCat,
            aplicaCC
          )
      );
      setInitialValues({ facturas });
      setHasFacturas(true);
    }
  }, [facturasData, monedaCat, estatusCat, tasaCat, aplicaCC]);

  useEffect(() => {
    if (idDictamen) getFacturas(idDictamen);
  }, [getFacturas, idDictamen]);
  //#endregion

  //#region Acciones Facturas
  const [formularioActivo, setFormularioActivo] = useState(false); //habilita el formulario
  const [valoresOriginales, setValoresOriginales] = useState(null);

  const LlamarProforma = (dataSolicitud) => {
    let { fechaRecepcionFactura, fechaSolicitud, noOficioSolicitud, nombre } =
      dataSolicitud;

    let fechasValidas = validarFechasSF(fechaRecepcionFactura, fechaSolicitud);
    if (
      !fechaSolicitud ||
      !fechaRecepcionFactura ||
      !noOficioSolicitud ||
      !nombre
    ) {
      errorToast(PROFORMA.MSG005);
      setSubmitForm(true);
    } else if (!fechasValidas) {
      errorToast(PROFORMA.MSG008);
      // setSubmitForm(true);
    } else {
      postFacturasRecibidas({
        idDictamen,
        banderaFactura: true,
      }).then((response) => {
        if (response.error) {
          ValidateError(response, REGISTRAR_REINTEGROS.MSG001);
        } else {
          setReload(true);
          handleShowMessage(REGISTRAR_REINTEGROS.MSG010);
        }
      });
    }
  };

  const handleNuevoRegistro = (arrayHelpers, values) => {
    let { facturas } = values;
    arrayHelpers.insert(0, {
      ...emptyFactura,
      facturaActiva: true,
      numeroFactura: facturas?.length + 1 || 1,
    });
    setValoresOriginales(JSON.parse(JSON.stringify(facturas)));
    setFormularioActivo(true);
  };

  const validarFacturasRecibidas = (
    arrayHelpers,
    values,
    index,
    setFieldValue
  ) => {
    const addFactura = () => {
      if (!hasFacturas) {
        handleEditarRegistro(index, values, setFieldValue);
      } else {
        handleNuevoRegistro(arrayHelpers, values, index, setFieldValue);
      }
    };
    getBuscarSF({ idDictamen }).then((response) => {
      if (response.error) {
        handleShowConfirmModal(GENERAR_FACTURA.MSG014, () =>
          LlamarProforma({})
        );
      } else {
        let { data } = response;
        if (!_.isEmpty(data)) {
          let last = null;
          if (_.isArray(data) && _.isEmpty(data)) {
            last = data.at(-1);
          } else {
            last = data;
          }
          if (last.banderaFactura) {
            addFactura();
          } else {
            handleShowConfirmModal(GENERAR_FACTURA.MSG014, () =>
              LlamarProforma(last)
            );
          }
        } else {
          handleShowConfirmModal(GENERAR_FACTURA.MSG014, () =>
            LlamarProforma({})
          );
        }
      }
    });
  };

  let nameFactura = (name, index) => `facturas[${index}].${name}`;

  const handleEditarRegistro = (index, values, setFieldValue) => {
    let { facturas } = values;
    setValoresOriginales(JSON.parse(JSON.stringify(facturas)));
    setFormularioActivo(true);
    setFieldValue(nameFactura("facturaActiva", index), true);
  };

  const facturaCancelada = (factura, estatusCat = []) => {
    let { idFactura, idEstatusFactura } = factura;
    let cancelStatus = estatusCat.find((s) =>
      ["cancelada", "cancelado"].includes(s.nombre?.toLowerCase())
    );
    if (
      !idEstatusFactura ||
      !idFactura ||
      (cancelStatus && cancelStatus.primaryKey == factura.idEstatusFactura)
    ) {
      return true;
    }

    return false;
  };

  const [showJustificacionModal, setShowJustificacionModal] = useState(false);
  const handleShowJustificacion = () => {
    setShowJustificacionModal(true);
  };
  const [cancelarFacturasValues, setCancelarFacturasValues] = useState(null);

  const cancelarFacturaAction = (justificacion) => {
    setShowJustificacionModal(false);
    let { idFactura } = cancelarFacturasValues.factura;

    postCancelarFacturas({ idFactura, data: justificacion }).then(
      (response) => {
        if (response?.error) {
          ValidateError(response, GENERAR_FACTURA.MSG005);
        } else {
          getFacturas(idDictamen);
          handleShowMessage(GENERAR_FACTURA.MSG006);
          onReloadDictamenInfo(idDictamen);
        }
        postActualizarResumen({ idDictamen });
      }
    );
  };

  const cancelarFacturaDeny = () => {
    setShowJustificacionModal(false);
    setCancelarFacturasValues(null);
  };

  const handleCancelarFactura = (arrayHelpers, index, factura) => {
    setCancelarFacturasValues({ arrayHelpers, index, factura });

    handleShowConfirmModal(GENERAR_FACTURA.MSG010, handleShowJustificacion);
  };

  //#endregion

  //#region  Acciones Generales

  const handleRegresarProforma = () => {
    const regresarProformaAction = () => {
      postRegresarProforma({ idDictamen }).then(async(response) => {
        await onReloadDictamenInfo(idDictamen);
        setReload(true);
        const showSections = { ...SECCIONES_INICIAL };
        showSections.Deducciones = false;
        showSections.Factura = false;
        showSections.Facturas = false;
        showSections.GestionDocumental = false;
        showSections.NotaCredito = false;
        showSections.PenasContractuales = false;
        showSections.PenasConvencionales = false;
        showSections.Proforma = false;
        showSections.RegistroServiciosDictaminados = false;
        showSections.SolicitudPago = false;
        showSections.SoporteDocumentalDictamen = false;
        setShowSecciones({ ...showSections });
      });
    };

    postValidarRegresarProforma({ idDictamen }).then((response) => {
      if (response.error) {
        ValidateError(response, "");
      } else if (response?.data) {
        handleShowMessage(GENERAR_FACTURA.MSG015);
      } else {
        handleShowConfirmModal(GENERAR_FACTURA.MSG016, regresarProformaAction);
      }
    });
  };

  const handleCancelar = (setFieldValue) => {
    const CancelEdicion = () => {
      setFieldValue("facturas", valoresOriginales);
      setFormularioActivo(false);
    };

    handleShowConfirmModal(GENERAR_FACTURA.MSG008, CancelEdicion);
  };

  const validateDataToSave = (factura) => {
    let {
      idFactura,
      archivoXML,
      archivoPdf,
      comentarios = "",
      nombreXML,
      nombrePdf,
      idTipoMoneda,
      idTasa,
      porcentajeSatValue,
      montoSatValue,
      montoPesosSatValue,

      porcentajeCCValue,
      montoCCValue,
      montoPesosCCValue,
    } = factura;

    if (!idFactura) {
      let data = {
        archivoXml: archivoXML,
        pdf: archivoPdf,
        dictamenId: idDictamen,
        comentarios: comentarios ? comentarios : "",
        archivoCargar: nombreXML,
        idContrato,
        // idProveedor,
        archivoPdf: nombrePdf,
        // monto: montoSatValue,
        idTipoMoneda,
        idIva: idTasa,

        porcentajeSat: porcentajeSatValue,
        montoSat: montoSatValue,
        montoPesoSat: montoPesosSatValue,
        porcentajeCC: porcentajeCCValue,
        montoCC: montoCCValue,
        montoPesosCC: montoPesosCCValue,
      };
      return data;
    } else {
      let data = {
        idFacturaNota: idFactura,
        archivoXml: archivoXML,
        pdf: archivoPdf,
        dictamenId: idDictamen,
        comentarios: comentarios ? comentarios : "",
        archivoCargar: nombreXML ? nombreXML : "archivo.xml",
        idContrato,
        // idProveedor,
        archivoPdf: nombrePdf ? nombrePdf : "factura.pdf",
        // monto: montoSatValue,
        idTipoMoneda,
        idIva: idTasa,

        porcentajeSat: porcentajeSatValue,
        montoSat: montoSatValue,
        montoPesoSat: montoPesosSatValue,
        porcentajeCC: porcentajeCCValue,
        montoCC: montoCCValue,
        montoPesosCC: montoPesosCCValue,
        tipoDocumento: "Factura",
      };
      return data;
    }
  };

  const handleGuardar = (values, { resetForm, setFieldValue }) => {
    //validar guardado de facturas servicio
    let facturaToSave = values?.facturas?.find((s) => s?.facturaActiva);
    if (facturaToSave) {
      let { idFactura } = facturaToSave;

      let data = validateDataToSave(facturaToSave);

      let validateResponse = (response) => {
        if (response?.error) {
          ValidateError(response, GENERAR_FACTURA.MSG004);
        } else {
          postActualizarResumen({ idDictamen });
          onReloadDictamenInfo(idDictamen).then(() => {
            getFacturas(idDictamen).then(() => {
              refetch();
              setFormularioActivo(false);
              handleShowMessage(GENERAR_FACTURA.MSG006);
            });
          });
        }
      };

      if (!idFactura) {
        postGuardarFacturas(data).then(validateResponse);
      } else {
        postActualizarFactura(data).then(validateResponse);
      }
    }
  };

  //#endregion

  return (
    <>
      {(isLoadingFacturas ||
        isLoadingLeerXml ||
        isLoadingGuardarFactura ||
        isLoadingActualizarFactura ||
        isLoadingCancelar ||
        isLoadingFacturasRecibidas ||
        isLoadingEstatus ||
        isLoadingMoneda ||
        isLoadingTasa ||
        isLoadingAplicaCC ||
        isLoadingFacturasRecibidasGuardar ||
        // isLoadingActualizarResumen ||
        isLoadingRegresarProforma ||
        isLoadingValidarRegresarProforma) && <Loader />}
      <SingleBasicModal
        size="md"
        show={showMessage}
        onHide={handleCloseMessage}
        title="Mensaje"
        approveText={"Aceptar"}
      >
        {message}
      </SingleBasicModal>
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
      <ComentariosSimple
        show={showJustificacionModal}
        comentario=""
        handleCancel={cancelarFacturaDeny}
        handleSave={cancelarFacturaAction}
        maxLength={500}
      />
      <Formik
        initialValues={initialValues}
        enableReinitialize={true}
        validationSchema={validationSchema}
        onSubmit={handleGuardar}
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
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <FieldArray name="facturas">
                {(arrayHelpers) => {
                  return (
                    <React.Fragment>
                      <>
                        {values.facturas.map((factura, index) => {
                          let { idFactura, facturaActiva = false } = factura;
                          return (
                            <React.Fragment key={index}>
                              <Row>
                                <Col md={8} />

                                <>
                                  <Col md={4} className="text-end">
                                    {!hasFacturas && (
                                      <Authorization
                                        process={"CON_SERV_DICT_STA_PROFORMA"}
                                      >
                                        <Button
                                          variant="gray"
                                          className="btn-sm ms-2 waves-effect waves-light"
                                          type="button"
                                          onClick={() =>
                                            handleRegresarProforma(values)
                                          }
                                          disabled={!modificar}
                                        >
                                          Regresar a proforma
                                        </Button>
                                      </Authorization>
                                    )}
                                    {/* </Col>
                                    <Col md={2}> */}
                                    <Authorization
                                      process={"CON_SERV_DICT_FAC_ADMIN"}
                                    >
                                      {index === 0 && (
                                        <IconButton
                                          tooltip={"Nuevo registro"}
                                          type="add"
                                          onClick={() => {
                                            validarFacturasRecibidas(
                                              arrayHelpers,
                                              values,
                                              index,
                                              setFieldValue
                                            );
                                          }}
                                          disabled={
                                            !modificar || formularioActivo
                                          }
                                        />
                                      )}
                                      <IconButton
                                        type="edit"
                                        onClick={() =>
                                          handleEditarRegistro(
                                            index,
                                            values,
                                            setFieldValue
                                          )
                                        }
                                        disabled={
                                          !modificar ||
                                          !hasFacturas ||
                                          !idFactura ||
                                          (idFactura &&
                                            (formularioActivo ||
                                              facturaCancelada(
                                                factura,
                                                estatusCat
                                              )))
                                        }
                                      />
                                      <IconButton
                                        type="cancel"
                                        onClick={() =>
                                          handleCancelarFactura(
                                            arrayHelpers,
                                            index,
                                            factura
                                          )
                                        }
                                        disabled={
                                          !modificar ||
                                          !hasFacturas ||
                                          !idFactura ||
                                          (idFactura &&
                                            (formularioActivo ||
                                              facturaCancelada(
                                                factura,
                                                estatusCat
                                              )))
                                        }
                                      />
                                    </Authorization>
                                  </Col>
                                </>
                              </Row>
                              <FormularioFacturas
                                formularioActivo={facturaActiva}
                                index={index}
                                factura={factura}
                                arrayHelpers={arrayHelpers}
                                handleShowMessage={handleShowMessage}
                                handleShowConfirmModal={handleShowConfirmModal}
                                //catalogos validar obtencion
                                estatusCatalogo={estatusCat}
                                monedaDictamen={monedaCat}
                                tasaDictamen={tasaCat}
                                idProveedor={idProveedor}
                                idContrato={idContrato}
                                idDictamen={idDictamen}
                                leerXml={leerXml}
                                aplicaCC={aplicaCC}
                                tipoCambio={tipoCambio}
                              />
                              <hr />
                            </React.Fragment>
                          );
                        })}
                      </>
                    </React.Fragment>
                  );
                }}
              </FieldArray>
              {values?.facturas?.length > 0 && (
                <Row>
                  <Col md={12} className="text-end">
                    {hasFacturas && (
                      <Authorization process={"CON_SERV_DICT_STA_PROFORMA"}>
                        <Button
                          variant="gray"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="button"
                          onClick={() => handleRegresarProforma(values)}
                          disabled={!modificar}
                        >
                          Regresar a proforma
                        </Button>
                      </Authorization>
                    )}
                    <Authorization process={"CON_SERV_DICT_FAC_ADMIN"}>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="button"
                        onClick={() => handleCancelar(setFieldValue)}
                        disabled={!modificar || !formularioActivo}
                      >
                        Cancelar
                      </Button>
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="submit"
                        onClick={() => {
                          !isValid && errorToast(GENERAR_FACTURA.MSG004);
                        }}
                        disabled={!modificar || !formularioActivo}
                      >
                        Guardar
                      </Button>
                    </Authorization>
                  </Col>
                </Row>
              )}
            </Form>
          );
        }}
      </Formik>
    </>
  );
};

export default Facturas;
