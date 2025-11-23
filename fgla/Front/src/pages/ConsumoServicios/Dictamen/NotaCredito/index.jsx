import React, {
  useState,
  useEffect,
  useMemo,
  useCallback,
  useContext,
} from "react";
import { Row, Col, Button, Form } from "react-bootstrap";
import { Formik, FieldArray } from "formik";
import { useToast } from "../../../../hooks/useToast";
import Loader from "../../../../components/Loader";
import IconButton from "../../../../components/buttons/IconButton";
import ComentariosSimple from "../../../../components/ComentariosSimple";
import FormularioNotaCredito from "./FormularioNotaCredito";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import BasicModal from "../../../../modals/BasicModal";
import _ from "lodash";
import { NOTA_CREDITO } from "../../../../constants/messages";
import {
  useGetGestionDocumentalDictamenQuery,
} from "../GestionDocumental/store";
import {
  emptyNotaCredito,
  FormatNotaCredito,
  validationSchema,
} from "./Components/NotaCreditoUtilities";
import { useLocation } from "react-router-dom";
import { DictamenContext } from "../context";
import {
  usePostLeerXMLNotaMutation,
  usePostNotascreditoMutation,
  usePostGuardarNotaMutation,
  usePostActualizarNotaMutation,
  usePostCancelarNotaMutation,
  useGetCatStatusNotasQuery,
  useGetCatIvaNotasQuery,
  useGetCatTipoMonedaNotasQuery,
  useGetValidarConvenioNotaQuery,
  usePostActualizarResumenNotasMutation,
} from "./store";
import Authorization from "../../../../components/Authorization";

const NotaCredito = () => {
  const { onReloadDictamenInfo } = useContext(DictamenContext);

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

  console.log(estatusDictamen);
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
    let estatusValido = ["facturado"].includes(estatusDictamen?.toLowerCase());
    ///Validar permisos posterior aqui
    setPermisos((prev) => ({
      ...prev,
      modificar: estatusValido && editable,
    }));
  }, [estatusDictamen, editable]);

  const { alta, cancelar, consultar, modificar } = permisos;
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

  //#region  Servicios

  //Leer Xml
  const [leerXml, { isLoading: isLoadingLeerXml }] =
    usePostLeerXMLNotaMutation();
  // Validar CC
  const [aplicaCC, setAplicaCC] = useState(false);
  const { isLoading: isLoadingAplicaCC, data: dataCC } =
    useGetValidarConvenioNotaQuery(idContrato, { skip: !idContrato });
  useEffect(() => {
    if (dataCC?.data) setAplicaCC(dataCC.data);
  }, [dataCC]);
  //obtenerNotas
  const [getNotas, { isLoading: isLoadingNotas, data: notasCreditoData }] =
    usePostNotascreditoMutation();
  console.log("data", notasCreditoData);

  const [postActualizarResumen, { isLoading: isLoadingActualizarResumen }] =
    usePostActualizarResumenNotasMutation();

  //Guardar notas
  const [postGuardarNotas, { isLoading: isLoadingGuardar }] =
    usePostGuardarNotaMutation();
  //Actualizar Factura
  const [postActualizarNota, { isLoading: isLoadingActualizarNota }] =
    usePostActualizarNotaMutation();
  //Cancelar notas
  const [postCancelarNota, { isLoading: isLoadingCancelar }] =
    usePostCancelarNotaMutation();

  //Cat estatus
  const { isLoading: isLoadingEstatus, data: dataEstatus } =
    useGetCatStatusNotasQuery();

  //Cat Iva
  const { isLoading: isLoadingIva, data: dataTasa } = useGetCatIvaNotasQuery();

  //Cat Moneda
  const { isLoading: isLoadingMoneda, data: dataMoneda } =
    useGetCatTipoMonedaNotasQuery();

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

  //#endregion
  //#endregion

  //#region InformacionNotaCredito
  const [hasNotasCredito, setHasNotasCredito] = useState(false);
  const [initialValues, setInitialValues] = useState({
    notasCredito: [emptyNotaCredito],
  });

  useEffect(() => {
    let _notasCredito = [emptyNotaCredito];
    if (!_.isEmpty(notasCreditoData?.data)) {
      _notasCredito = notasCreditoData.data;
      setHasNotasCredito(true);
      _notasCredito = _.orderBy(_notasCredito, ["idNotaCredito"], ["asc"]).map(
        (factura, index) =>
          FormatNotaCredito(
            factura,
            index,
            monedaCat,
            tasaCat,
            estatusCat,
            aplicaCC
          )
      );
    }
    setInitialValues({ notasCredito: _notasCredito });
  }, [notasCreditoData?.data, monedaCat, estatusCat, tasaCat, aplicaCC]);

  //#endregion
  useEffect(() => {
    if (idDictamen) getNotas(idDictamen);
  }, [getNotas, idDictamen]);

  const [editarDictamen, setEditarDictamen] = useState(true); // Valida que se esta editando y el status del dictamen PROFORMA, FACTURADO

  //#region Obtener Catalogos

  const notaCreditoCancelada = (notaCredito, estatusCat = []) => {
    let { idNotaCredito, estatus, idEstatusNotaCredito } = notaCredito;
    let cancelStatus = estatusCat.find((s) =>
      ["cancelada", "cancelado"].includes(s.nombre?.toLowerCase())
    );
    if (
      !idEstatusNotaCredito ||
      !idNotaCredito ||
      !estatus ||
      (cancelStatus && cancelStatus.primaryKey == idEstatusNotaCredito)
    ) {
      return true;
    }

    return false;
  };

  //#region  Acciones NotaCredito
  const [formularioActivo, setFormularioActivo] = useState(false); //habilita el formulario
  const [valoresOriginales, setValoresOriginales] = useState(null);

  const handleNuevoRegistro = (arrayHelpers, values) => {
    let { notasCredito } = values;
    let newNota = {
      ...emptyNotaCredito,
      notaActiva: true,
      numeroNotaCredito: notasCredito?.length + 1 || 1,
    };
    arrayHelpers.insert(0, newNota);
    setValoresOriginales(JSON.parse(JSON.stringify(notasCredito)));
    setFormularioActivo(true);
  };
  const nameNota = (name, index) => `notasCredito[${index}].${name}`;

  const handleEditarRegistro = (index, values, setFieldValue) => {
    let { notasCredito } = values;
    setValoresOriginales(JSON.parse(JSON.stringify(notasCredito)));
    setFormularioActivo(true);
    setFieldValue(nameNota("notaActiva", index), true);
  };

  const [showJustificacionModal, setShowJustificacionModal] = useState(false);
  const handleShowJustificacion = () => {
    setShowJustificacionModal(true);
  };

  const [cancelarNotaCreditoValues, setCancelarNotaCreditoValues] =
    useState(null);

  const cancelarNotaCreditoAction = (justificacion) => {
    setShowJustificacionModal(false);
    let { idNotaCredito } = cancelarNotaCreditoValues.notaCredito;

    postCancelarNota({ id: idNotaCredito, data: justificacion }).then(
      (response) => {
        postActualizarResumen({ idDictamen });
        if (response?.error) {
          const message =
            _.isArray(response.error?.data?.mensaje) &&
            !_.isEmpty(response.error.data.mensaje)
              ? response.error.data.mensaje[0]
              : response.error?.data?.mensaje || NOTA_CREDITO.MSG006;
          handleShowMessage(message);
        } else {
          getNotas(idDictamen);
          handleShowMessage(NOTA_CREDITO.MSG004);
        }
        onReloadDictamenInfo(idDictamen);
      }
    );
  };

  const cancelarNotaCreditoDeny = () => {
    setShowJustificacionModal(false);
    setCancelarNotaCreditoValues(null);
  };

  const handleCancelarNotaCredito = (arrayHelpers, index, notaCredito) => {
    setCancelarNotaCreditoValues({ arrayHelpers, index, notaCredito });

    handleShowConfirmModal(NOTA_CREDITO.MSG003, handleShowJustificacion);
  };
  //#endregion

  //#region  Acciones
  const handleCancelar = (setFieldValue) => {
    const cancelEdicion = () => {
     
        setFieldValue("notasCredito", valoresOriginales);
      setFormularioActivo(false);
    };
    handleShowConfirmModal(NOTA_CREDITO.MSG008, cancelEdicion);
  };

  const validateDataToSave = (factura) => {
    let {
      idNotaCredito,
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

    if (!idNotaCredito) {
      let data = {
        archivoXml: archivoXML,
        pdf: archivoPdf,
        dictamenId: idDictamen,
        comentarios: comentarios ? comentarios : "",
        archivoCargar: nombreXML,
        idContrato,
        idProveedor,
        archivoPdf: nombrePdf,
        monto: montoSatValue,
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
        idFacturaNota: idNotaCredito,
        archivoXml: archivoXML,
        pdf: archivoPdf,
        dictamenId: idDictamen,
        comentarios: comentarios ? comentarios : "",
        archivoCargar: nombreXML ? nombreXML : "archivo.xml",
        idContrato,
        idProveedor,
        archivoPdf: nombrePdf ? nombrePdf : "factura.pdf",
        monto: montoSatValue,
        idTipoMoneda,
        idIva: idTasa,

        porcentajeSat: porcentajeSatValue,
        montoSat: montoSatValue,
        montoPesoSat: montoPesosSatValue,
        porcentajeCC: porcentajeCCValue,
        montoCC: montoCCValue,
        montoPesosCC: montoPesosCCValue,
        tipoDocumento: "Nota",
      };
      return data;
    }
  };

  const handleGuardar = (values, { setFieldValue }) => {
    //validar guardado de notasCredito servicio
    let notaToSave = values?.notasCredito?.find((s) => s?.notaActiva);
    if (notaToSave) {
      let { idNotaCredito } = notaToSave;

      let data = validateDataToSave(notaToSave);

      let validateResponse = (response) => {
        if (response?.error) {
          const message =
            _.isArray(response.error?.data?.mensaje) &&
            !_.isEmpty(response.error.data.mensaje)
              ? response.error.data.mensaje[0]
              : response.error?.data?.mensaje || NOTA_CREDITO.MSG001;

          handleShowMessage(message);
        } else {
          postActualizarResumen({ idDictamen });
          onReloadDictamenInfo(idDictamen).then(() => {
            getNotas(idDictamen).then(() => {
              refetch();
              handleShowMessage(NOTA_CREDITO.MSG001);
              setFormularioActivo(false);
            });
          });
        }
      };

      if (!idNotaCredito) {
        postGuardarNotas(data).then(validateResponse);
      } else {
        postActualizarNota(data).then(validateResponse);
      }
    }
  };
  //#endregion

  return (
    <>
      {(isLoadingLeerXml ||
        isLoadingAplicaCC ||
        isLoadingNotas ||
        isLoadingGuardar ||
        isLoadingActualizarNota ||
        isLoadingCancelar ||
        isLoadingEstatus ||
        isLoadingIva ||
        isLoadingMoneda) && <Loader />}
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
        handleCancel={cancelarNotaCreditoDeny}
        handleSave={cancelarNotaCreditoAction}
        maxLength={500}
      />
      <Formik
        initialValues={initialValues}
        enableReinitialize={true}
        validationSchema={validationSchema}
        onSubmit={handleGuardar}
        validateOnMount={true}
      >
        {({ values, handleSubmit, isValid, setFieldValue, errors }) => {
          return (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <FieldArray name="notasCredito">
                {(arrayHelpers) => {
                  return (
                    <>
                      {!_.isEmpty(values?.notasCredito) > 0 && (
                        <>
                          {values.notasCredito.map((notaCredito, index) => {
                            let { idNotaCredito, notaActiva = false } =
                              notaCredito;
                            return (
                              <React.Fragment key={index}>
                                <Authorization
                                  process={"CON_SERV_DICT_NC_ADMIN"}
                                >
                                  <Row>
                                    <Col md={8} />

                                    <Col md={4} className="text-end">
                                      {index === 0 && (
                                        <IconButton
                                          tooltip={"Nuevo registro"}
                                          type="add"
                                          onClick={() => {
                                            if (!hasNotasCredito) {
                                              handleEditarRegistro(
                                                index,
                                                values,
                                                setFieldValue
                                              );
                                            } else {
                                              handleNuevoRegistro(
                                                arrayHelpers,
                                                values
                                              );
                                            }
                                          }}
                                          disabled={
                                            !modificar ||
                                            !editarDictamen ||
                                            formularioActivo
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
                                          !hasNotasCredito ||
                                          !idNotaCredito ||
                                          (idNotaCredito &&
                                            (!editarDictamen ||
                                              formularioActivo ||
                                              notaCreditoCancelada(
                                                notaCredito,
                                                estatusCat
                                              )))
                                        }
                                      />
                                      <IconButton
                                        type="cancel"
                                        onClick={() =>
                                          handleCancelarNotaCredito(
                                            arrayHelpers,
                                            index,
                                            notaCredito
                                          )
                                        }
                                        disabled={
                                          !modificar ||
                                          !hasNotasCredito ||
                                          !idNotaCredito ||
                                          (idNotaCredito &&
                                            (!editarDictamen ||
                                              formularioActivo ||
                                              notaCreditoCancelada(
                                                notaCredito,
                                                estatusCat
                                              )))
                                        }
                                      />
                                      {/* </Tooltip> */}
                                    </Col>
                                  </Row>
                                </Authorization>
                                <FormularioNotaCredito
                                  index={index}
                                  arrayHelpers={arrayHelpers}
                                  handleShowMessage={handleShowMessage}
                                  handleShowConfirmModal={
                                    handleShowConfirmModal
                                  }
                                  formularioActivo={
                                    formularioActivo && notaActiva
                                  }
                                  estatusCat={estatusCat}
                                  monedaCat={monedaCat}
                                  tasaCat={tasaCat}
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
                      )}
                    </>
                  );
                }}
              </FieldArray>
              {values?.notasCredito?.length > 0 && (
                <Authorization process={"CON_SERV_DICT_NC_ADMIN"}>
                  <Row>
                    <Col md={12} className="text-end">
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
                          !isValid && errorToast(NOTA_CREDITO.MSG009);
                        }}
                        disabled={!modificar || !formularioActivo}
                      >
                        Guardar
                      </Button>
                    </Col>
                  </Row>
                </Authorization>
              )}
            </Form>
          );
        }}
      </Formik>
    </>
  );
};

export default NotaCredito;
