import React, {
  useState,
  useEffect,
  useCallback,
  useContext,
  useRef,
} from "react";
import { Button, Col, Row } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import {
  useGgetCatDesgloceQuery, // Correcto: corresponde a ggetCatDesgloce
  useGetValidarConvenioProformaQuery,
  useGetCatDescuentosProformaQuery,
  usePostBuscarDpMutation,
  usePostGuardarDpMutation,
  usePutActualizarDpMutation,
  useDeleteEliminarDpMutation,
  usePostMonedaDDpMutation,
  usePostCargarArchivoDDpMutation,
  usePutRechazarProformaMutation,
  usePostConsultarArchivoDdpMutation,
  usePutDescargarArchivoDdpMutation,
  usePutValidarDictamenMutation,
} from "./store";
import Loader from "../../../../components/Loader";
import { useToast } from "../../../../hooks/useToast";
import { PROFORMA } from "../../../../constants/messages";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import BasicModal from "../../../../modals/BasicModal";
import _ from "lodash";
import { useLocation } from "react-router-dom";
import {
  validateCellIcon,
  validateCellSelect,
  LabelCell,
} from "./Components/TableCells";
import VerDocumento from "./Components/VerDocumento";
import FileField from "../../../../components/formInputs/FileField";
import ComentariosSimple from "../../../../components/ComentariosSimple";
import { DictamenContext } from "../context";
import { Formik } from "formik";
import * as yup from "yup";
import { Form } from "react-bootstrap";
import { ActionCell } from "./Components/TableCells";
import {
  DownloadFileBase64,
  getFileExtension,
} from "../../../../functions/utils/base64.utils";
import Authorization from "../../../../components/Authorization";
import {
  useGetGestionDocumentalDictamenQuery,
} from "../GestionDocumental/store";

export function TableComponent({ isDetalle }) {
  const formRef = useRef();
  const {
    onReloadDictamenInfo,
    onReloadProformaInfo,
    setOnReloadProformaInfo,
    setShowSecciones,
  } = useContext(DictamenContext);

  const [idDictamen, setIdDictamen] = useState(null);
  const [idContrato, setIdContrato] = useState(null);
  const [idProveedor, setIdProveedor] = useState(null);
  const [tipoCambio, setTipoCambio] = useState(null);
  const [estatusDictamen, setEstatusDictamen] = useState(null);
  const [editable, setEditable] = useState(null);
  const location = useLocation();

  const { refetch } =
    useGetGestionDocumentalDictamenQuery(encodeURIComponent(idDictamen));

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

  console.log(estatusDictamen);

  const permisosIniciales = {
    leer: true,
    edicion: true,
  };

  const [permisos, setPermisos] = useState(permisosIniciales);
  const [dictamenValidado, setDictamenValidado] = useState(false);

  const [editar, setEditar] = useState(false);

  useEffect(() => {
    let { leer, edicion } = permisos;

    setEditar(edicion);
  }, [permisos]);

  
  useEffect(() => {
    let estatusValido = ["dictaminado", "proforma"].includes(
      estatusDictamen?.toLowerCase()
    );
    ///Validar permisos posterior aqui
    setDictamenValidado(
      ["proforma", "facturado", "pagado", "solicitud de pago"].includes(
        estatusDictamen?.toLowerCase()
      )
    );

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

  const handleShowConfirmModal = useCallback(
    (title, approve = _confirmData.approve, deny = _confirmData.deny) => {
      setConfirmModalMesage(title);
      setConfirmData({ approve, deny });
      setShowConfirmModal(true);
    },
    [_confirmData]
  );
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
  // const [
  //   postPenasDeducciones,
  //   { data: deduccionesDescuentosData, isLoading: isLoadingDDP },
  // ] = usePostResumenDeduccionesDescuentosPenalizacionesMutation();
  const { data: catadesgloce } = useGgetCatDesgloceQuery();
  const { data: catDescuentosData } = useGetCatDescuentosProformaQuery();

  const [postBuscar, { isLoading: isLodingBuscar, data: descuentosData }] =
    usePostBuscarDpMutation();
  const [postGuardar, { isLoading: isLoadingGuardar }] =
    usePostGuardarDpMutation();
  const [putActualizar, { isLoading: isLoadingActualizar }] =
    usePutActualizarDpMutation();
  const [postEliminar, { isLoading: isLoadingEliminar }] =
    useDeleteEliminarDpMutation();

  const [postMoneda, { isLoading: isLoadingMoneda, data: monedaData }] =
    usePostMonedaDDpMutation();

  const [monedaDictamen, setMonedaDictamen] = useState("");
  useEffect(() => {
    if (idDictamen && monedaData) {
      if (!_.isEmpty(monedaData)) {
        let { moneda } = monedaData[0];
        setMonedaDictamen(moneda);
      }
    }
  }, [idDictamen, monedaData]);

  console.log(monedaData);

  //aplica cc

  const [aplicaCC, setAplicaCC] = useState(false);
  const { isLoading: isLoadingAplicaCC, data: dataCC } =
    useGetValidarConvenioProformaQuery(idContrato, { skip: !idContrato });

  useEffect(() => {
    if (dataCC?.data) setAplicaCC(dataCC.data);
  }, [dataCC]);

  const [catDescuentos, setCatDescuentos] = useState([]);
  useEffect(() => {
    if (!_.isEmpty(catDescuentosData) && !aplicaCC) {
      setCatDescuentos(
        catDescuentosData.filter(
          (item) => !item?.nombreDescuento?.toLowerCase().includes("cc")
        )
      );
    } else {
      setCatDescuentos(catDescuentosData);
    }
  }, [catDescuentosData, aplicaCC]);

  //#region Archivo
  const [postCargarArchivo, { isLoading: isLoadingCargar }] =
    usePostCargarArchivoDDpMutation();

  const [
    getConsultarArchivo,
    { isLoading: isLoadingConsultarArchivo, data: dataArchivo },
  ] = usePostConsultarArchivoDdpMutation();
  console.log(dataArchivo);

  const [putDescargarArchivo, { isLoading: isLoadingDescargarArchivo }] =
    usePutDescargarArchivoDdpMutation();
  //#endregion

  const [putRechazarProforma, { isLoading: isLoadingRechazarProforma }] =
    usePutRechazarProformaMutation();

  const [putValidarDictamen, { isLoading: isLoadingValidarDictamen }] =
    usePutValidarDictamenMutation();
  //#region Botones Funtions

  //#region Tabla

  const [dataTable, setDataTable] = useState([]);
  const [deleteItems, setDeleteItems] = useState([]);
  const [hasError, sethasError] = useState(false);

  console.log(dataTable);

  // #region Listado Inicial
  const findValue = (
    catalogo,
    id,
    catalogoKey = "idDesgloce",
    catalogoNombre = "nombre"
  ) => {
    if (!_.isEmpty(catalogo)) {
      let item = catalogo.find((s) => s[catalogoKey] == id);
      if (item) {
        return item[catalogoNombre];
      }
    }
    return id;
  };

  const reduceDDp = useCallback((lista, aplicaCC, catadesgloce, moneda) => {
    let result = [];
    if (_.isArray(catadesgloce) && _.isArray(lista)) {
      let cat = catadesgloce;
      if (!aplicaCC) {
        cat = cat.filter((s) => s.nombre !== "CC");
      }

      const origenIds = [
        { id: "Penas Convencionales", text: "Penalización convencional" },
        { id: "Penas Contractuales", text: "Penalización contractual " },
        { id: "Deducción", text: "Deducción" },
      ];

      origenIds.forEach((idOrigen) => {
        cat.forEach((itemCat) => {
          let items = lista.filter(
            (s) =>
              s.idOrigen === idOrigen.id && s.nombreDescuento === itemCat.nombre
          );
          let total = items.reduce(
            (acumulador, item) => acumulador + safeParseFloat(item.monto),
            0
          );

          let resultItem = {
            monto: total,
            isFetched: true,
            tipoText: `${idOrigen.text} ${itemCat.nombre}`,
            moneda,
          };
          result.push(resultItem);
        });
      });
    }
    return result;
  }, []);

  const mapDataDescuentos = useCallback((data, catalogo, isFetched = false) => {
    if (_.isEmpty(data)) return [];
    return data.map((item) => {
      return {
        ...item,
        idTipo: item.idTipoDescuento,
        tipoText: findValue(
          catalogo,
          item.idTipoDescuento,
          "idTipoDescuento",
          "nombreDescuento"
        ),
        moneda: item.moneda || "MXN",
        isFetched,
      };
    });
  }, []);

  const [originalData, setOriginalData] = useState([]);

  useEffect(() => {
    if (_.isArray(descuentosData)) {
      let otrasSecciones = descuentosData.filter((s) => !s.idDdp);
      let mapData = reduceDDp(
        otrasSecciones,
        aplicaCC,
        catadesgloce,
        monedaDictamen
      );
      let descuentos = descuentosData.filter((s) => s.idDdp);

      let data = [...mapData, ...mapDataDescuentos(descuentos, catDescuentos)];
      setDataTable(data);
      setOriginalData(data);
    }
  }, [
    catadesgloce,
    descuentosData,
    mapDataDescuentos,
    catDescuentos,
    aplicaCC,
    reduceDDp,
    monedaDictamen,
  ]);

  const columns = [
    {
      accessorKey: "identifier",
      header: "No",
      cell: (props) => <>{props.row.index + 1}</>,
      // enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "tipoText",
      header: "Tipo",
      cell: (props) =>
        validateCellSelect({
          ...props,
          catalogo: catDescuentos,
          keyValue: "idTipoDescuento",
          keyTextValue: "nombreDescuento",
          hasError,
        }),
      enableColumnFilter: false,
      enableSorting: false,
    },
    {
      accessorKey: "moneda",
      header: "Moneda",
      cell: LabelCell,
      enableColumnFilter: false,
      enableSorting: false,
    },
    {
      accessorKey: "monto",
      header: "Monto",
      cell: (props) => validateCellIcon({ ...props, hasError }),
      enableColumnFilter: false,
    },
    {
      accessorKey: "edicion",
      header: "Acciones",
      cell: (props) =>
        ActionCell({
          ...props,
          originalData,
          setDataTable,
          handleShowConfirmModal,
          setDeleteItems,
          isDetalle,
          modificar,
          dictamenValidado,
        }),
      enableSorting: false,
      enableColumnFilter: false,
    },
  ];

  //#endregion
  //#region Acciones Tabla

  const Buscar = useCallback(
    (action = () => {}) => {
      // postPenasDeducciones({ idDictamen });
      postBuscar({ idDictamen: idDictamen }).then(() => {
        postMoneda({ idDictamen: idDictamen }).then(() => {
          getConsultarArchivo({ idDictamen }).then(() => {
            action();
          });
        });
      });
    },
    [idDictamen, postBuscar, postMoneda, getConsultarArchivo]
  );

  useEffect(() => {
    if (idDictamen) {
      if (onReloadProformaInfo === null) setOnReloadProformaInfo(() => Buscar);
      Buscar();
    }
  }, [idDictamen, Buscar, onReloadProformaInfo, setOnReloadProformaInfo]);
  //#endregion

  //#region Agregar

  const emptyItem = {
    idTipo: "",
    dictamenId: idDictamen,
    monto: "",
    edicion: true,
    moneda: monedaDictamen,
  };

  const handleAdd = () => {
    setDataTable([...dataTable, { ...emptyItem }]);
  };

  //#endregion

  //#region  Guardar
  const ValidateError = (response, defaultMessage = "") => {
    const message =
      _.isArray(response.error?.data?.mensaje) &&
      !_.isEmpty(response.error.data.mensaje)
        ? response.error.data.mensaje[0]
        : response.error?.data?.mensaje || defaultMessage;

    handleShowMessage(message);
  };
  const validatDictamenAction = () => {
    putValidarDictamen({ idDictamen }).then((response) => {
      setDeleteItems([]);
      if (response.error) {
        Buscar();
        ValidateError(response);
      } else {
        Buscar(()=>{

          onReloadDictamenInfo(idDictamen).then(() => {
            handleShowMessage(PROFORMA.MSG001);
          });
        });
      }
    });
  };

  const ValidFile = () => {
    return fileData || fileLoaded;
  };
  const [fileError, setFileError] = useState(false);

  const handleDictamenValidate = () => {
    let validFile = ValidFile();
    let notSavedItems = dataTable.filter((s) => s.edicion);

    //if (!validFile) {
     // setFileError(true);
      //sethasError(true)
      //errorToast(PROFORMA.MSG005);
    //}
    if (
      
      fileData ||
      !_.isEmpty(deleteItems) ||
      !_.isEmpty(notSavedItems)
    ) {
      setFileError(false);
      handleSave(true);
    } else {
      setFileError(false);
      sethasError(false)
      
      validatDictamenAction();
    }
  };
  const ValidData = () => {
    let validation = (item) => {
      let _monto = item.monto
        ? safeParseFloat(("" + item.monto).replaceAll(",", ""))
        : item.monto;
      let invalidMonto = !_monto && _monto !== 0;
      return item.edicion && (!item.idTipo || invalidMonto);
    };
    let incompleteData = dataTable.filter((s) => s.edicion).find(validation);
    if (incompleteData) return false;
    return true;
  };

  //#region Acctions save
  const handleOk = (validarDictamen = false) => {
    setTimeout(() => {
      if (validarDictamen) {
        validatDictamenAction();
      } else {
        setDeleteItems([]);
         //MSG004
        Buscar(()=>{handleShowMessage(PROFORMA.MSG011);});
      }
    }, 100);
  };

  const actualizar = (dataUpdate, validarDictamen = false) => {
    putActualizar(dataUpdate).then((response) => {
      if (response.error) {
        handleShowMessage(PROFORMA.MSG002); //MSG002
      } else if (fileData) {
        handleLoadFile(validarDictamen);
      } else {
        handleOk(validarDictamen);
      }
    });
  };
  const guardar = (dataCreate, dataUpdate, validarDictamen = false) => {
    postGuardar(dataCreate).then((response) => {
      if (response.error) {
        handleShowMessage(PROFORMA.MSG002); //MSG002
      } else {
        if (!_.isEmpty(dataUpdate)) {
          actualizar(dataUpdate, validarDictamen);
        } else if (fileData) {
          handleLoadFile(validarDictamen);
        } else {
          handleOk(validarDictamen);
        }
      }
    });
  };

  const borrar = (
    datadelete,
    dataCreate,
    dataUpdate,
    validarDictamen = false
  ) => {
    postEliminar(datadelete).then((response) => {
      if (response.error) {
        handleShowMessage(PROFORMA.MSG002); //MSG022
      } else {
        if (!_.isEmpty(dataCreate)) {
          guardar(dataCreate, dataUpdate, validarDictamen);
        } else if (!_.isEmpty(dataUpdate)) {
          actualizar(dataUpdate, validarDictamen);
        } else if (fileData) {
          handleLoadFile(validarDictamen);
        } else {
          handleOk(validarDictamen);
        }
      }
    });
  };

  //#endregion

  const handleSave = (validarDictamen) => {
    let validData = ValidData();

    if (!validData) {
      sethasError(true);
      errorToast(PROFORMA.MSG005); //MSG 001
    } else {
      sethasError(false);
      setFileError(false);
      let _dataTable = dataTable.map((s) => ({
        ...s,
        dictamenId: idDictamen,
        idTipoDescuento: s.idTipo,
      }));
      let createItems = _dataTable.filter((s) => s.edicion && !s.idDdp);
      let updateItems = _dataTable.filter((s) => s.edicion && s.idDdp);
      if (!_.isEmpty(deleteItems)) {
        borrar(deleteItems, createItems, updateItems, validarDictamen);
      } else if (!_.isEmpty(createItems)) {
        guardar(createItems, updateItems, validarDictamen);
      } else if (!_.isEmpty(updateItems)) {
        actualizar(updateItems, validarDictamen);
      } else if (fileData) {
        handleLoadFile(validarDictamen);
      }
    }
  };
  //#endregion

  //#region Ver Doc
  const [isOpenModalDocumento, setIsOpenModalDocumento] = useState(false);

  const [vistaPrevia, setVistaPrevia] = useState(true);
  const handleOpenShowPdf = (vistaPrevia = true) => {
    setIsOpenModalDocumento(true);
    setVistaPrevia(vistaPrevia);
  };
  const handleCloseShowPdf = () => {
    setIsOpenModalDocumento(false);
    setVistaPrevia(true);
  };

  //#endregion
  //#endregion

  //#region Buttons Actions

  const handleCancelButton = () => {
    const cancel = () => {
      setDeleteItems([]);
      setDataTable([]);
      setFileData(null);
      Buscar();
    };
    handleShowConfirmModal(PROFORMA.MSG009, cancel);
  };

  //#endregion
  function safeParseFloat(value, defaultValue = 0) {
    value = "" + value;
    value = value.replaceAll(",", "");
    return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
  }

  const [totalTable, setTotalTable] = useState(0);
  //#region Total Table
  useEffect(() => {
    if (!_.isEmpty(dataTable)) {
      let total = dataTable.reduce(
        (sum, item) => sum + safeParseFloat(item.monto),
        0
      );
      setTotalTable(total);
    }
  }, [dataTable]);

  const [acceptType, setAcceptType] = useState("");
  const [errotTypeMessage, setErrorTypeMessage] = useState("");
  useEffect(() => {
    setAcceptType(
      totalTable === 0
        ? "application/pdf"
        : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );
    setErrorTypeMessage(totalTable === 0 ? PROFORMA.MSG014 : PROFORMA.MSG013);
  }, [totalTable]);

  //#endregion

  //#region  File
  const [fileLoaded, setFileLoaded] = useState();
  const [fileData, setFileData] = useState(null);

  useEffect(() => {
    if (dataArchivo?.path) {
      setFileLoaded({ ...dataArchivo, name: dataArchivo.nombre });
    }
  }, [dataArchivo]);

  useEffect(() => {
    if (fileLoaded?.name) {
      setFileText(fileLoaded.name);
    }
  }, [fileLoaded]);

  const setFileText = async (fileName, elementId = "ddpFile") => {
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

  const handleFileChange = (event, handleChange) => {
    handleChange(event);
    let { files } = event.target;
    if (!_.isEmpty(files)) {
      const file = files[0];

      let { type } = file;
      const loadFile = () => {
        setFileData(file);
        setFileText(file.name);
      };
      const setLastFile = () => {
        if (fileData) {
          setFileText(fileData.name);
        } else if (fileLoaded) {
          setFileText(fileLoaded.name);
        }
      };
      if (file && type === acceptType) {
        if (fileLoaded || fileData) {
          handleShowConfirmModal(PROFORMA.MSG012, loadFile, setLastFile);
        } else {
          loadFile();
        }
      } else {
        handleShowMessage(errotTypeMessage);
        setFileData(null);
      }
    }
  };

  const handleLoadFile = (validarDictamen = false) => {
    if (fileData) {
      postCargarArchivo({
        detallePenasDeducciones: fileData,
        idDictamen,
        nombrePenasDeducciones: fileData.name,
      }).then((response) => {
        refetch(); // Actualiza gestión documental con Redux toolkit query
        if (response.error) {
          Buscar();
          ValidateError(response,PROFORMA.MSG002);
        } else {
          setFileData(null);
          handleOk(validarDictamen);
        }
      });
    }
  };
  const handleDownloadFile = () => {
    if (fileLoaded?.path) {
      putDescargarArchivo({
        path: fileLoaded.path,
        dictamenId: idDictamen,
      }).then((response) => {
        if (response.error) {
          handleShowMessage(PROFORMA.MSG003);
        } else {
          let { data } = response;
          let { extension } = getFileExtension(fileLoaded.path);
          let name = fileLoaded.name + "." + extension;
          DownloadFileBase64(
            data,
            name,
            "xlsx" === extension
              ? "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
              : "application/pdf"
          );
        }
      });
    }
  };
  //#endregion
  //#region Rechazado

  const [showJustificacionModal, setShowJustificacionModal] = useState(false);
  const handleShowJustificacion = () => {
    setShowJustificacionModal(true);
  };

  const handleRechazo = () => {
    handleShowConfirmModal(PROFORMA.MSG004, handleShowJustificacion);
  };

  const cancelRechazo = () => {
    setShowJustificacionModal(false);
  };

  const handleRechazoAction = (message) => {
    cancelRechazo();
    putRechazarProforma({
      dictamenId: {
        idDictamen,
      },
      descripcion: message,
    }).then((response) => {
      if (response?.error) {
      } else {
        setShowSecciones((prevSecciones) => ({
          ...prevSecciones,
          Proforma: false,
        }));
        onReloadDictamenInfo(idDictamen);
      }
    });
  };

  //#endregion

  

  const [initialValues, setInitialValues] = useState({
    ddpFile: "",
  });
  const validationSchema = yup.object({});
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
       

        return (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            {(isLoadingAplicaCC ||
              isLoadingActualizar ||
              isLoadingEliminar ||
              isLoadingGuardar ||
              isLodingBuscar ||
              isLoadingMoneda ||
              isLoadingValidarDictamen ||
              isLoadingCargar ||
              isLoadingConsultarArchivo ||
              isLoadingRechazarProforma ||
              isLoadingDescargarArchivo) && <Loader />}
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
              handleCancel={cancelRechazo}
              handleSave={handleRechazoAction}
              maxLength={500}
            />
            <VerDocumento
              title="Seleccione un convenio modificatorios"
              show={isOpenModalDocumento}
              onHide={handleCloseShowPdf}
              vistaPrevia={vistaPrevia}
              idDictamen={idDictamen}
              handleShowMessage={handleShowMessage}
            />
            <Row>
              <Col md={12} className="text-end">
                <Authorization process={"CON_SERV_DICT_DDP_ADMIN"}>
                  <IconButton
                    type={"add"}
                    onClick={handleAdd}
                    disabled={isDetalle || !modificar || dictamenValidado}
                    tooltip={"Nuevo registro"}
                  />
                </Authorization>
                <IconButton
                  type="rotate"
                  onClick={() => Buscar()}
                  disabled={isDetalle || !modificar || dictamenValidado}
                  tooltip={"Actualizar montos de deducciones y penalizaciones"}
                />
                <IconButton
                  type="search"
                  disabled={isDetalle || !modificar || dictamenValidado}
                  onClick={handleOpenShowPdf}
                  tooltip={"Vista previa de la proforma"}
                />
              </Col>
            </Row>
            <Row>
              <Col md={12}>
                <TablaEditable
                  hasPagination
                  dataTable={dataTable}
                  columns={columns}
                  onDelete={setDataTable}
                  onUpdate={setDataTable}
                />
              </Col>
            </Row>
            <Row>
              <Col md={4}>
                <Authorization process={"CON_SERV_DICT_DDP_ADMIN"}>
                  <FileField
                    label="Detalle de penas y deducciones"
                    onChange={(e) => handleFileChange(e, handleChange)}
                    accept={acceptType}
                    disabled={isDetalle || !modificar || dictamenValidado}
                    name={"ddpFile"}
                    className=""
                    helperText={""}
                    // key={fileData ? fileData.name : "empty"}
                  />
                </Authorization>
                {fileLoaded && (
                  <a href="javascript:void(0)" onClick={handleDownloadFile}>
                    Ver
                  </a>
                )}
              </Col>
              <Col md={8} className="text-end mb-2 mt-4">
                {!dictamenValidado && (
                  <>
                    <Authorization process={"CON_SERV_DICT_DDP_ADMIN"}>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleCancelButton}
                        disabled={isDetalle || !modificar}
                      >
                        Cancelar
                      </Button>
                      <Button
                        variant="green"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => handleSave(false)}
                        disabled={isDetalle || !modificar}
                      >
                        Guardar
                      </Button>
                    </Authorization>
                    <Authorization process={"CON_SERV_DICT_DDP_BTN_RECHAZA"}>
                      <Button
                        variant="red"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleRechazo}
                        disabled={isDetalle || !modificar}
                      >
                        Rechazado
                      </Button>
                    </Authorization>
                    <Authorization process={"CON_SERV_DICT_DDP_STA_PROFORMA"}>
                      <Button
                        variant="gray"
                        className="btn-sm ms-2 waves-effect waves-light"
                        type="button"
                        onClick={handleDictamenValidate}
                        disabled={isDetalle || !modificar}
                      >
                        Validar dictamen
                      </Button>
                    </Authorization>
                  </>
                )}
                {dictamenValidado && (
                  <Authorization process={"CON_SERV_DICT_DDP_BTN_GEN_PROF"}>
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      type="button"
                      onClick={() => handleOpenShowPdf(false)}
                      disabled={isDetalle || !modificar}
                    >
                      Generar proforma
                    </Button>
                  </Authorization>
                )}
              </Col>
            </Row>
          </Form>
        );
      }}
    </Formik>
  );
}
