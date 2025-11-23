import React, { useState, useEffect, useRef, useContext } from "react";
import { Form, Row, Col, Button, Container } from "react-bootstrap";
import Accordion from "../../../../components/Accordion";
import LabelValue from "../../../../components/LabelValue";
import MainTitle from "../../../../components/MainTitle";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { ToggleCell } from "./ToggleCell";
import moment from "moment";
import BasicModal from "../../../../modals/BasicModal";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import Loader from "../../../../components/Loader";
import CancelModal from "./CancelModal";
import VerDocumento from "./VerDocumento";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import { Formik } from "formik";
import * as yup from "yup";
import IconButton from "../../../../components/buttons/IconButton";
import { postData, getData, putData } from "../../../../functions/api";
import { Tooltip } from "../../../../components/Tooltip";
import Select from "../../../../components/formInputs/Select";
import { useToast } from "../../../../hooks/useToast";
import showMessage from "../../../../components/Messages";
import { ProyectosContext } from "../ProyectosContext";
import SelectCell from "./SelectCell";
import { isEmpty } from "lodash";
import DatepickerCell from "./DatepickerCell";
import InputCell from "./InputCell";
import Authorization from "../../../../components/Authorization";
import { MESSAGGES } from "./uitils";

import { useDispatch, useSelector } from "react-redux";
import { GetDetalleProyecto } from "../../../../store/infoComites/infoComitesActions";
const MSG017 =
  'No puedes continuar si el “Estatus del proyecto” no está en “Proceso de cierre"';
const MSG002 = "El proyecto no tiene asignado ninguna plantilla documental.";
const MSG003 =
  "Se perderán todos los cambios realizados. ¿Está seguro de que desea continuar?";
const MSG004 =
  "Se modificará el “Estatus del RCP” a “Cancelado”. ¿Está seguro de que desea continuar?";
const MSG006 =
  "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).";
const MSG007 =
  "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).";
const MSG011 =
  "Se modificará el “Estatus RCP” a “En proceso”. ¿Está seguro de que desea continuar?";
const MSG012 =
  "Se modificará el “Estatus RCP” a “Revisado por Área de Planeación”. ¿Está seguro de que desea continuar?";
const MSG013 =
  "Se modificará el “Estatus RCP” a “Validado por Líder de Proyecto”. ¿Está seguro de que desea continuar?";

const MSG014 =
  "Aún existen entregables con estatus “Pendiente”. Intente nuevamente.";
const MSG015 =
  "Las fechas no pueden ser menor a la fecha de inicio del proyecto o mayor a la fecha fin del proyecto.";
const MESSAGE_CIERRE = "No se encontro un cierre proyecto";
const MESSAGE_ERROR_CIERRE =
  'Cannot invoke "com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel.getHistoricoModel()" because the return value of "com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel.getFichaTecnicaModel()" is null';

const ERROR_MESSAGE = "Ocurrió un error";
/*
• Área de planeación activa o no Fecha de entrega
•	Cuando el valor del registro en el campo “Estatus” sea “No aplica” el campo “# de páginas” se inactivará.
•	Cuando el valor del registro en el campo “Estatus” sea “Entregado” el campo “# de páginas” se activará
•	En caso de que seleccione la opción “En proceso”, continúa en el (FA13).
•	En caso de que seleccione la opción “Revisado por AP”, continúa en el (FA14).
•	En caso de que seleccione la opción “Validado por AP”, continúa en el (FA15).
•	En caso de que seleccione la opción “Generar RCP”, continúa en el (FA16).
  RNA182 IMPORTANTE! para botones
*/

const VALORES_INICIALES = {
  nombreCortoProyecto: "",
  areaPlaneacion: "",
  fechaEntrega: "",
  año: "",
  lider: "",
  tipoCambio: "",
  idFormateado: "",
  nombreCompleto: "",
  numeroContrato: "",
  proveedor: "",
  estatusRCP: "",
  idProyectoAGP: "",
  porcentajePlaneado: "",
  porcentajeReal: "",
};
const selectPlaceholder = "Seleccione una opción";

const MSG005 = "Favor de ingresar los datos obligatorios.";
const MSG010 =
  "La estructura de la información ingresada es incorrecta. Intente nuevamente.";
const MSG016 = "Error al generar el enlace y contraseña. Intente nuevamente.";

const VerificacionRCP = () => {
  const dispatch = useDispatch();

  const { proyecto, editable } = useSelector((state) => {
    return state.proyectos;
  });
  const idProyecto = proyecto?.proyecto?.idProyecto;
  const idFormateado = proyecto?.proyecto?.idFormateado;
  const estatusProyecto = proyecto?.estatus?.nombre;

  const { errorToast } = useToast();

  const [isOpenModalDocumento, setIsOpenModalDocumento] = useState(false);
  const [documentData, setDocumentData] = useState("");
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [documentoGenerado, setDocumentoGenerado] = useState(false);
  const [errorCarga, setErrorCarga] = useState(false);
  const [modalKey, setModalKey] = useState(0);
  const handleCloseShowPdf = () => {
    setIsOpenModalDocumento(false);
    setIsOpenModal(false);
    setDocumentData("");
  };
  const handleOpenShowPdf = () => {
    setDocumentData("https://www.orimi.com/pdf-test.pdf");
    setIsOpenModalDocumento(true);
    setModalKey((prevKey) => prevKey + 1);
  };

  const [idEstatusInicial, setIdEstatusInicial] = useState("");
  const [idCierre, setIdCierre] = useState("");
  const [isDisabledCierre, setIsDisabledCierre] = useState(true);
  const [satCloudKeys, setSatCloudKeys] = useState(null);

  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);
  const [isOpenCancelStatusModal, setIsOpenCancelStatusModal] = useState(false);
  const [isOpenSATCLOUD, setIsOpenSATCLOUD] = useState(false);
  const [isOpenButtonsModal, setIsOpenButtonsModal] = useState(false);
  const [isOpenButtonsModalMessage, setIsOpenButtonsModalMessage] =
    useState(false);
  const [typeModal, setTypeModal] = useState(null);

  const [valoresIniciales, setValoresIniciales] = useState({
    ...VALORES_INICIALES,
  });
  const [areaPlaneacionErrorText, setAreaPlaneacionErrorText] = useState(null);
  const [loading, setLoading] = useState(true);

  const [disableFechaEntrega, setDisableFechaEntrega] = useState(true);
  const [fechaEntregaErrorText, setFechaEntregaErrorText] = useState(null);

  const { setOnReloadRCPInfo, onReloadRCPInfo } = useContext(ProyectosContext);

  const [statusCatalog, setStatusCatalog] = useState([]);
  const handleStatusFilter = (...args) => {
    const catalogFilterByText = (
      catalog,
      idKey,
      text,
      row,
      columnId,
      filterValue
    ) => {
      const rowValue = row.original[columnId];
      const rowObject = catalog.find((item) => item[idKey] === rowValue);
      return rowObject
        ? rowObject[text]
            .trim()
            .toLowerCase()
            .includes(filterValue.trim().toLowerCase())
        : false;
    };

    return catalogFilterByText(statusCatalog, "primaryKey", "nombre", ...args);
  };
  const dateFilterFormat = (dateFormat, moment, row, columnId, filterValue) => {
    const rowValue = row.original[columnId];
    return rowValue !== null
      ? moment(rowValue).format(dateFormat).includes(filterValue)
      : false;
  };
  const handleDateFiltro = (...args) =>
    dateFilterFormat("DD/MM/YYYY", moment, ...args);

  const [areaPlaneacionCatalog, setAreaPlaneacionCatalog] = useState([]);
  const [RCPCatalog, setRCPCatalog] = useState([]);
  const [RCPEstatusCatalog, setRCPEstatusCatalog] = useState([]);

  const [isActiveAddProvider, setIsActiveAddProvider] = useState(false);

  const [dataTable, setDataTable] = useState([]);
  const [dataTableDeleted, setDataTableDeleted] = useState([]);

  const formRef = useRef();
  const handleResetForm = () => {
    if (formRef.current) {
      formRef.current.resetForm(); // Resetea el formulario utilizando la referencia
    }
  };

  const esquema = yup.object({});

  const onPostService = async (url, data, errorMssage = MSG006) => {
    try {
      const response = await postData(url, data);

      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(errorMssage);
      } else if (MSG015 === err?.response?.data?.mensaje[0]) {
        showMessage(MSG015);
      } else {
        let errorMessage =
          err?.response?.data !== "" && err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === MSG006;
        if (errorIdDuplicado && err?.response?.status !== 403) {
          showMessage(errorMessage);
        } else if (err?.response?.status !== 403) {
          showMessage(errorMssage);
        }
      }
      return null;
    }
  };

  const onPutService = async (url, data, errorMssage = MSG006) => {
    try {
      const response = await putData(url, data);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(errorMssage);
      } else {
        let errorMessage =
          err?.response?.data !== "" && err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === MSG006;
        if (errorIdDuplicado && err?.response?.status !== 403) {
          showMessage(errorMessage);
        } else if (err?.response?.status !== 403) {
          showMessage(errorMssage);
        }
      }
      return null;
    }
  };

  const onGetService = async (url, MSG = null) => {
    try {
      const response = await getData(url);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(MSG007);
      } else {
        let errorMessage =
          err?.response?.data !== "" && err?.response?.data?.mensaje[0];
        if (
          err?.response?.status !== 403 &&
          errorMessage !== MESSAGE_CIERRE &&
          MESSAGE_ERROR_CIERRE !== errorMessage
        ) {
          showMessage(MSG !== null ? MSG : errorMessage);
        } else if (
          err?.response?.status !== 403 &&
          errorMessage !== MESSAGE_CIERRE &&
          MESSAGE_ERROR_CIERRE !== errorMessage
        ) {
          showMessage(ERROR_MESSAGE);
        }
      }
      return null;
    }
  };

  const onCalculatePercentage = async (data, statusCatalog) => {
    let archivos = [];
    if (isEmpty(data) === false) {
      archivos = [...data].map((element) => {
        return {
          id: element.id,
          nombre: element.nombre,
          descripcion: element.descripcion,
          nivel: element.nivel,
          orden: element.orden,
          tipo: element.tipo,
          obligatorio: element.obligatorio,
          noAplica: element.noAplica,
          estatus: true,
          cargado: element.cargado,
          extension: element.extension,
          ruta: element.ruta,
          tamanoMb: element.tamanoMb,
          fechaModificacion: element.fechaModificacion,
          justificacion: element.justificacion,
          carpeta: element.carpeta,
          fechaDocumento:
            element.fechaDocumento === ""
              ? ""
              : moment(element.fechaDocumento, "YYYY-MM-DD").format(
                  "YYYY-MM-DDTHH:mm:ss"
                ),
          numeroPaginas: element.paginas,
          estatusDocumento:
            element.estatus === ""
              ? ""
              : statusCatalog.filter(
                  (filterElement) => filterElement.primaryKey == element.estatus
                )[0].nombre,
          type: element.type,
          idContrato: element.idContrato,
        };
      });
    }
    const response = await onPostService(
      `/proyectos/calcular-porcentajes`,
      archivos
    );
    if (
      (response !== null && response[1] && isNaN(response[1]) === false) ||
      response[1] === 0
    )
      formRef.current.setFieldValue("porcentajeReal", response[1]);
    return null;
  };

  const onGetInitialData = async () => {
    try {
      setLoading(true);

      await onGetAreaPlaneacion();

      const responseCierre = await onObtenerCierre();
      if (responseCierre !== null) {
        const { fechaEntrega, idUser, idEstatusRcp } = responseCierre;
        const responseCatalogRCPStatus = await onGetService(
          `/proyectos/estatus-RCP`
        );
        setRCPEstatusCatalog(responseCatalogRCPStatus);
        formRef.current.values.estatusRCP =
          isEmpty(responseCatalogRCPStatus) === false
            ? responseCatalogRCPStatus.filter(
                (item) => item.primaryKey === idEstatusRcp
              )[0].nombre
            : "";
        formRef.current.values.areaPlaneacion = idUser;
        formRef.current.values.fechaEntrega =
          fechaEntrega !== null && fechaEntrega !== ""
            ? moment(fechaEntrega).format("YYYY-MM-DD")
            : "";
        setIdCierre(responseCierre.idCierre);
      } else {
        const responseCatalogRCPStatus = await onGetService(
          `/proyectos/estatus-inicial-proceso`
        );
        formRef.current.values.estatusRCP = responseCatalogRCPStatus[0].nombre;
        setIdEstatusInicial(responseCatalogRCPStatus[0].primaryKey);
      }
      const responseCatalog = await onGetEstatus();
      setRCPCatalog([...responseCatalog]);
      const dataTable = await onGetDocumentsTable(responseCatalog);
      await onCalculatePercentage(dataTable, responseCatalog);
      const responseRCPInfo = await onInformacionProyectoCierre();
      if (responseRCPInfo !== null && isEmpty(responseRCPInfo) === false) {
        const { nombreCorto, idProyectoAGP, nombreCompleto, lider } =
          responseRCPInfo;
        formRef.current.values.nombreCortoProyecto = nombreCorto;
        formRef.current.values.idProyectoAGP = idProyectoAGP;
        formRef.current.values.nombreCompleto = nombreCompleto;
        formRef.current.values.lider = lider;
        formRef.current.values.porcentajePlaneado = "100.00 %";
      }
    } catch (err) {}

    const responseValidateStatusRCP = await onValidateStatus();
    if (responseValidateStatusRCP !== null) setIsDisabledCierre(false);
    setLoading(false);
    return null;
  };

  const onValidateStatus = async () => {
    const response = await onGetService(
      `/proyectos/validar-estatus-proyecto/${idProyecto}`,
      MSG017
    );
    return response;
  };

  const onGenerarRCP = async () => {
    setLoading(true);
    if (formRef.current.values.fechaEntrega === "") {
      setFechaEntregaErrorText("Dato requerido");
      setLoading(false);
      errorToast(MSG005);
    }
    const responseRCP = await onGetService(
      `/proyectos/generar-rcp/${idCierre}`
    );
    await onGetInitialData();
    if (responseRCP !== null) {
      setLoading(false);
      handleOpenShowPdf();
      dispatch(GetDetalleProyecto(idProyecto));
    } else {
      setLoading(false);
    }
  };

  const onGetEstatus = async () => {
    const response = await onGetService(`/proyectos/estatus-RCP`);
    // setStatusCatalog([...response]);
    setStatusCatalog([
      {
        primaryKey: "1",
        nombre: "Pendiente",
      },
      {
        primaryKey: "2",
        nombre: "No aplica",
      },
      {
        primaryKey: "3",
        nombre: "Entregado",
      },
      {
        primaryKey: "4",
        nombre: "Sin documento",
      },
    ]);
    return [
      {
        primaryKey: "1",
        nombre: "Pendiente",
      },
      {
        primaryKey: "2",
        nombre: "No aplica",
      },
      {
        primaryKey: "3",
        nombre: "Entregado",
      },
      {
        primaryKey: "4",
        nombre: "Sin documento",
      },
    ];
  };

  const onGetAreaPlaneacion = async () => {
    const response = await onGetService(`/proyectos/area-planeacion`);
    if (response !== null) {
      setAreaPlaneacionCatalog(
        response.map((item) => ({
          primaryKey: item.idUser,
          nombre: item.nombre,
        }))
      );
    }
    return response;
  };

  const onSaveCierre = async (values) => {
    const data = [...tableReference.current.table.options.meta.getDataTable()];

    let isError = false;

    if (values.areaPlaneacion === "") {
      isError = true;
      setAreaPlaneacionErrorText("Campo requerido");
    }

    for (let element in data) {
      if ((idCierre !== "" && data[element].isEditable) || idCierre === "") {
        if (data[element].estatus === null || data[element].estatus === "") {
          isError = true;
          data[element].estatusErrorText = "Campo requerido";
        } else {
          if (
            data[element].estatus == 3 &&
            data[element].fechaDocumento === ""
          ) {
            // entregado
            isError = true;
            data[element].fechaDocumentoErrorText = "Campo requerido";
            data[element].paginasErrorText = "Campo requerido";
            data[element].justificacionErrorText = null;
          } else if (
            data[element].estatus == 2 &&
            data[element].justificacion === ""
          ) {
            isError = true;
            data[element].justificacionErrorText = "Campo requerido";
            data[element].paginasErrorText = null;
          }
        }
      }
    }

    if (isError) {
      tableReference.current.table.options.meta.revertData([...data]);
      errorToast(MSG005);
    } else {
      let archivos = [];
      if (isEmpty(data) === false) {
        archivos = [...data]
          .filter((item) => item.isEditable)
          .map((element) => {
            return {
              id: element.id,
              nombre: element.nombre,
              descripcion: element.descripcion,
              nivel: element.nivel,
              orden: element.orden,
              tipo: element.tipo,
              obligatorio: element.obligatorio,
              noAplica: element.noAplica,
              estatus: true,
              cargado: element.cargado,
              ruta: element.carpeta,
              tamanoMb: element.tamanoMb,
              fechaModificacion: element.fechaModificacion,
              justificacion: element.justificacion,
              carpeta: element.carpeta,
              fechaDocumento:
                element.fechaDocumento === ""
                  ? ""
                  : moment(element.fechaDocumento, "YYYY-MM-DD").format(
                      "YYYY-MM-DDTHH:mm:ss"
                    ),
              numeroPaginas: element.paginas,
              estatusDocumento:
                element.estatus === ""
                  ? ""
                  : statusCatalog.filter(
                      (filterElement) =>
                        filterElement.primaryKey == element.estatus
                    )[0].nombre,
              type: element.type,
              idContrato: element.idContrato,
            };
          });
      }
      setLoading(true);
      const responseSave = await onSaveProcesoCierre(values, archivos);
      if (responseSave !== null) {
        await onGetInitialData();
      dispatch(GetDetalleProyecto(idProyecto));
        showMessage(MESSAGGES.MSG001);
      }
      setLoading(false);

      setIsActiveAddProvider(false);
    }
  };

  const onInformacionProyectoCierre = async () => {
    const response = await onGetService(
      `/proyectos/informacion-proyecto-cierre/${idProyecto}`
    );
    return response;
  };

  const onObtenerCierre = async () => {
    const response = await onGetService(
      `/proyectos/obtener-cierre/${idProyecto}`
    );
    return response;
  };

  const onSaveProcesoCierre = async (values, archivos) => {
    const {
      fechaEntrega,
      areaPlaneacion,
      estatusRCP,
      porcentajeReal,
      porcentajePlaneado,
    } = values;
    // pato
    const cierreModel = {
      idCierre: idCierre,
      idEstatusRcp:
        estatusRCP === "En proceso"
          ? idEstatusInicial
          : isEmpty(RCPEstatusCatalog) === false
          ? RCPEstatusCatalog.filter((item) => item.nombre === estatusRCP)[0]
              .primaryKey
          : idEstatusInicial,
      estatus: true,
      idProyecto: idProyecto,
      idUser:
        areaPlaneacion !== "" && areaPlaneacion !== null
          ? parseInt(areaPlaneacion, 10)
          : "",
      fechaEntrega:
        fechaEntrega !== ""
          ? moment(fechaEntrega, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss")
          : "",
      porcentajePlaneado: 100.0,
      porcentajeReal: porcentajeReal,
    };
    const response = await onPostService(
      idCierre === ""
        ? `/proyectos/guardar-proceso-cierre`
        : `/proyectos/modificar-proceso-cierre`,
      {
        cierreModel,
        archivos,
      }
    );
    return response;
  };

  const onGetDocumentsTable = async (catalog) => {
    try {
      const response = await onGetService(
        `/proyectos/obtener-archivos-seccion/${idProyecto}`
      );
      let data = [];
      if (response !== null && isEmpty(response) === false) {
        data = response.map((item, index) => {
          const {
            estatusDocumento,
            numeroPaginas,
            justificacion,
            fechaDocumento,
            id,
            fase,
            descripcion,
          } = item;

          return {
            ...item,
            ...{
              id,
              index: index + 1,
              entregable: descripcion,
              fase: fase,
              estatus:
                estatusDocumento !== null
                  ? catalog.filter(
                      (itemFilter) => itemFilter.nombre === estatusDocumento
                    )[0].primaryKey
                  : "",
              estatusTexto: estatusDocumento,
              isUpdated: false,
              isEditable: false,
              isNewRegister: false,
              fechaDocumento:
                fechaDocumento === null || fechaDocumento === ""
                  ? ""
                  : moment(fechaDocumento).format("YYYY-MM-DD"),
              justificacion:
                justificacion === null || justificacion === "null"
                  ? ""
                  : justificacion,
              paginas: numeroPaginas === null ? "" : numeroPaginas,
              estatusErrorText: null,
              fechaDocumentoErrorText: null,
              justificacionErrorText: null,
              paginasErrorText: null,
            },
          };
        });
        setDataTable([...data]);
      }
      return [...data];
    } catch (err) {}
    return null;
  };

  useEffect(() => {
    if (estatusProyecto) {
      if (onReloadRCPInfo === null) setOnReloadRCPInfo(() => onGetInitialData);
      onGetInitialData();
    }
  }, [estatusProyecto]);

  const tableReference = useRef();

  const onActiveEditIcon = (value, tableProps) => {
    if (value === false) {
      const table = [...tableProps.table.options.meta.getDataTable()];
      const isEditableRows = table.filter((item) => item.isEditable === true);
      if (isEmpty(isEditableRows)) setIsActiveAddProvider(value);
    } else {
      setIsActiveAddProvider(value);
    }
  };

  const CustomHeader = ({ title, tooltipMessage }) => (
      <Tooltip placement="top"  text={"Número consecutivo"}>
        <span style={{cursor:'pointer'}}>{title}</span>
      </Tooltip>
  );

  const CustomHeaderActions = ({ title, tooltipMessage }) => (
    <div style={{ width: "80px" }}>
     <span>{title}</span>
    </div>
  );

  const columns = React.useMemo(
    () => {
      return [
        {
          accessorKey: "index",
          filterFn: "includesString",
          header: (props) => <CustomHeader {...props} title="#" />,
        },
        {
          accessorKey: "entregable",
          header: "Entregable",
          filterFn: "includesString",
        },
        {
          accessorKey: "fase",
          header: "Fase",
          filterFn: "includesString",
        },
        {
          accessorKey: "estatus",
          header: "Estatus",
          filterFn: handleStatusFilter,
          cell: (props) => (
            <SelectCell
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              onGetService={() => {}}
              onPostService={() => {}}
              urlByDocument={""}
              idDictamen={"idDictamen"}
              idContrato={"idContrato"}
              options={statusCatalog}
            />
          ),
        },
        {
          accessorKey: "fechaDocumento",
          header: "Fecha del documento",
          filterFn: handleDateFiltro,
          cell: (props) => (
            <DatepickerCell
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              onGetService={() => {}}
              onPostService={() => {}}
              urlByDocument={""}
              idDictamen={"idDictamen"}
              idContrato={"idContrato"}
            />
          ),
        },
        {
          accessorKey: "justificacion",
          header: "Justificación",
          filterFn: "includesString",
          cell: (props) => (
            <InputCell
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              onGetService={() => {}}
              onPostService={() => {}}
              urlByDocument={""}
              idDictamen={"idDictamen"}
              idContrato={"idContrato"}
            />
          ),
        },
        {
          accessorKey: "paginas",
          header: "# de páginas",
          filterFn: "includesString",
          cell: (props) => (
            <InputCell
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              onGetService={() => {}}
              onPostService={() => {}}
              urlByDocument={""}
              idDictamen={"idDictamen"}
              idContrato={"idContrato"}
            />
          ),
        },
        {
          size: 300, //set column size for this column
          accessorKey: "acciones",
          enableSorting: false,
          enableColumnFilter: false,
          header: (props) => <CustomHeaderActions {...props} title="Acciones" />,
          cell: (props) => (
            <>
              <Authorization process={"PROY_VRCP_MODIF"}>
                <ToggleCell
                  editable={false}
                  row={props.row}
                  message={MSG003}
                  errorDownloadFileText={MSG007}
                  idCierre={idCierre}
                  onPostService={onPostService}
                  setLoading={setLoading}
                  onClickEditToggle={() => {}}
                  onRemovedRow={(row) => {
                    if (row.original.isNewRegister === false) {
                      setDataTableDeleted((oldArray) => [
                        ...oldArray,
                        row.original,
                      ]);
                    }
                  }}
                  onRevertRow={(row) => {
                    const originalRow = dataTable.filter(
                      (item) => item.id === row.id
                    )[0];
                    const table = [...props.table.options.meta.getDataTable()];
                    props.table.options.meta.revertData(
                      table.map((item) => {
                        const newItem =
                          item.id === row.id ? { ...originalRow } : { ...item };
                        return newItem;
                      })
                    );
                  }}
                  column={props.column}
                  table={props.table}
                  isDisabled={
                    editable === false
                      ? !editable
                      : isDisabledCierre
                      ? isDisabledCierre
                      : formRef.current.values.estatusRCP ===
                          "Revisado por Área de Planeación" ||
                        formRef.current.values.estatusRCP === "Cancelado" ||
                        formRef.current.values.estatusRCP === "RCP entregado" ||
                        formRef.current.values.estatusRCP ===
                          "Validado por Líder de Proyecto"
                  }
                  onActiveEditIcon={(value) => onActiveEditIcon(value, props)}
                  onRemoveProvider={() => {
                    setIsActiveAddProvider(true);
                  }}
                />
              </Authorization>
            </>
          ),
        },
      ];
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [
      statusCatalog,
      dataTableDeleted,
      MSG003,
      setLoading,
      dataTable,
      formRef,
      MSG007,
      idCierre,
      isDisabledCierre,
      editable,
      onPostService,
    ]
  );

  return (
    <Container className="mt-3 px-3">
      <Formik
        innerRef={(f) => (formRef.current = f)}
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={esquema}
        validateOnMount={true}
        onChange={() => {}}
        onSubmit={(values, { resetForm }) =>
          () => {}}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          resetForm,
          values,
          errors,
          touched,
          isValid,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            {loading && <Loader />}
            <Row>
              <Col md={3}>
                <LabelValue
                  label="Nombre corto:"
                  value={values.nombreCortoProyecto}
                />
              </Col>

              <Col md={1}></Col>

              <Col md={4}>
                <LabelValue label="Id proyecto sistema:" value={idFormateado} />
              </Col>

              <Col md={3}>
                <LabelValue label="Estatus RCP:" value={values.estatusRCP} />
              </Col>

              <Col md={1}>
                <Authorization process={"PROY_VRCP_STA_CANCEL"}>
                  <Tooltip placement="top" text={"Cancelar estatus RCP"}>
                    <span>
                      <IconButton
                        type="cancel"
                        onClick={() => {
                          setIsOpenCancelStatusModal(true);
                        }}
                        disabled={
                          idCierre === ""
                            ? true
                            : isDisabledCierre
                            ? isDisabledCierre
                            : values.estatusRCP === "Cancelado"
                            ? true
                            : false
                        }
                      />
                    </span>
                  </Tooltip>{" "}
                </Authorization>
                <BasicModal
                  show={isOpenCancelStatusModal}
                  onHide={() => {
                    setIsOpenCancelStatusModal(false);
                  }}
                  title="Mensaje"
                  size="md"
                  denyText="No"
                  handleDeny={() => {
                    setIsOpenCancelStatusModal(false);
                  }}
                  approveText={"Si"}
                  handleApprove={async () => {
                    setLoading(true);
                    const response = await onGetService(
                      `/proyectos/cancelar-cierre/${idCierre}`
                    );
                    await onGetInitialData();
                    setLoading(false);
                    setIsOpenCancelStatusModal(false);
                  }}
                >
                  {MSG004}
                </BasicModal>
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <LabelValue
                  label="Id proyecto AGP:"
                  value={values.idProyectoAGP}
                />
              </Col>

              <Col md={8}>
                <LabelValue
                  label="Nombre completo del proyecto:"
                  value={values.nombreCompleto}
                />
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <LabelValue label="Líder del proyecto:" value={values.lider} />
              </Col>
              <Col md={4}>
                <Select
                  name={"areaPlaneacion"}
                  label={"Área de planeación*:"}
                  options={areaPlaneacionCatalog}
                  keyValue={"primaryKey"}
                  keyTextValue={"nombre"}
                  readOnly={false}
                  disabled={
                    isDisabledCierre
                      ? isDisabledCierre
                      : values.estatusRCP === "Cancelado" ||
                        values.estatusRCP === "RCP entregado" ||
                        values.estatusRCP === "Revisado por Área de Planeación"
                      ? true
                      : !editable
                  }
                  value={values.areaPlaneacion}
                  onChange={(e) => {
                    handleChange(e);
                    setAreaPlaneacionErrorText("");
                  }}
                  className={
                    areaPlaneacionErrorText === null
                      ? ""
                      : areaPlaneacionErrorText !== ""
                      ? "is-invalid"
                      : "is-valid"
                  }
                  helperText={areaPlaneacionErrorText}
                  defaultOptionText={selectPlaceholder}
                />
              </Col>
              <Col md={3}>
                <TextFieldDate
                  label={"Fecha de entrega:"}
                  name="fechaEntrega"
                  disabled={
                    isDisabledCierre
                      ? isDisabledCierre
                      : values.estatusRCP === "Cancelado"
                      ? true
                      : !editable /*disableFechaEntrega*/
                  }
                  value={values.fechaEntrega}
                  onChange={(e) => {
                    handleChange(e);
                    setFechaEntregaErrorText("");
                  }}
                  className={
                    fechaEntregaErrorText === null
                      ? ""
                      : touched.fechaEntrega &&
                        (fechaEntregaErrorText !== ""
                          ? "is-invalid"
                          : "is-valid")
                  }
                  helperText={fechaEntregaErrorText}
                  minDate={null}
                  maxDate={null}
                />
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <LabelValue
                  label="% Planeado:"
                  value={values.porcentajePlaneado}
                />
              </Col>
              <Col md={4}>
                <LabelValue
                  label="% Real:"
                  value={
                    values.porcentajeReal !== ""
                      ? `${parseFloat(values.porcentajeReal, 10).toFixed(2)}` +
                        " %"
                      : ""
                  }
                />
              </Col>
            </Row>

            <Row>
              <Authorization process={"PROY_VRCP_DOWN_MASIVA"}>
                <Col md="12" className="text-end mb-2">
                  <IconButton
                    disabled={isDisabledCierre}
                    type="download"
                    onClick={async () => {
                      setLoading(true);
                      const response = await onPostService(
                        `/proyectos/descarga-sat-cloud-cierre`,
                        { idProyecto, path: `/PROYECTO/${idProyecto}` }
                      );
                      setSatCloudKeys({ ...response });
                      const isValid = true;
                      if (isValid) {
                        setIsOpenSATCLOUD(true);
                      } else {
                        showMessage(MSG016);
                      }
                      setLoading(false);
                    }}
                    tooltip={"SATCloud"}
                  />

                  {satCloudKeys !== null && (
                    <SingleBasicModal
                      handleApprove={() => setIsOpenSATCLOUD(false)}
                      handleDeny={() => setIsOpenSATCLOUD(false)}
                      approveText={"Cerrar"}
                      show={isOpenSATCLOUD}
                      title={"Mensaje"}
                      onHide={() => setIsOpenSATCLOUD(false)}
                    >
                      <Row>
                        <Col md={10} className="text-start">
                          <strong>{"Datos de la descarga"}</strong>
                        </Col>
                      </Row>
                      <br />
                      <Row>
                        <Col md={10} className="text-start">
                          <label className="d-block form-label">
                            <strong>URL:</strong>{" "}
                            <a href={satCloudKeys.url}>{satCloudKeys.url}</a>
                          </label>
                        </Col>
                      </Row>
                      <Row>
                        <Row>
                          <Col md={10} className="text-start">
                            <label className="d-block form-label">
                              <strong>Contrseña:</strong>{" "}
                              <span>{satCloudKeys.temporal}</span>{" "}
                              <Tooltip
                                placement="top"
                                text={"Copiar contraseña"}
                              >
                                <span>
                                  <IconButton
                                    iconSize="1x"
                                    type="twoFilesBlack"
                                    onClick={() =>
                                      navigator.clipboard.writeText(
                                        satCloudKeys.temporal
                                      )
                                    }
                                    disabled={false}
                                  />
                                </span>
                              </Tooltip>{" "}
                            </label>
                          </Col>
                        </Row>
                      </Row>
                    </SingleBasicModal>
                  )}

                  <IconButton
                    type="zip"
                    onClick={async () => {
                      setLoading(true);
                      const data = [...dataTable].filter(
                        (item) => item.ruta !== "" && item.ruta !== null
                      );
                      let fileName = "";
                      for (let element in data) {
                        fileName = `${data[element].nombre}.${data[element].extension}`;
                      }
                      const response = await onPostService(
                        `/proyectos/descarga-masiva-cierre`,
                        { idProyecto, path: `/PROYECTO/${idProyecto}` }
                      );

                      DownloadFileBase64(
                        response,
                        "Proyecto" + idProyecto,
                        "application/zip"
                      );
                      setLoading(false);
                    }}
                    disabled={false}
                    tooltip={"Descargar ZIP"}
                  />
                </Col>
              </Authorization>
            </Row>

            <Row>
              <Col>
                <TablaEditable
                  ref={tableReference}
                  dataTable={dataTable}
                  columns={columns}
                  header={"RCP"}
                  hasPagination={true}
                  isFiltered={true}
                  autoResetPageIndex={false}
                  onUpdate={(data, prop, value) => {
                    // setDataTable(data, prop, value);
                  }}
                />
              </Col>
            </Row>

            <Row>
              <Col md={4} className="text-start">
                <Authorization process={"PROY_VRCP_STA_EPROC"}>
                  <Button
                    disabled={
                      isDisabledCierre
                        ? isDisabledCierre
                        : values.estatusRCP === "En proceso"
                        ? true
                        : false
                    }
                    variant="gray"
                    className="btn-sm ms-2 waves-effect waves-light"
                    type="submit"
                    onClick={async () => {
                      setTypeModal("1");
                      setIsOpenButtonsModalMessage(MSG011);
                      setIsOpenButtonsModal(true);
                    }}
                  >
                    En proceso
                  </Button>
                </Authorization>
              </Col>

              {editable === true && (
                <Col md={8} className="text-end">
                  {/*
                                          <Button
                        variant="red"
                        disabled={false}
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => {}}
                      >
                        {"Regresar"}
                      </Button>
                        */}

                  <Button
                    variant="red"
                    disabled={
                      isDisabledCierre === true
                        ? isDisabledCierre
                        : values.estatusRCP ===
                            "Revisado por Área de Planeación" ||
                          values.estatusRCP ===
                            "Validado por Líder de Proyecto" ||
                          values.estatusRCP === "RCP entregado" ||
                          values.estatusRCP === "Cancelado"
                        ? true
                        : false
                    }
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={() => setIsOpenCancelModal(true)}
                  >
                    {"Cancelar"}
                  </Button>
                  <Button
                    disabled={
                      isDisabledCierre === true
                        ? isDisabledCierre
                        : values.estatusRCP ===
                            "Revisado por Área de Planeación" ||
                          values.estatusRCP ===
                            "Validado por Líder de Proyecto" ||
                          values.estatusRCP === "RCP entregado" ||
                          values.estatusRCP === "Cancelado"
                        ? true
                        : false
                    }
                    variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                    type="submit"
                    onClick={() => onSaveCierre(values)}
                  >
                    Guardar
                  </Button>
                  {values.estatusRCP === "En proceso" && (
                    <Authorization process={"PROY_VRCP_STA_REV_AP"}>
                      <Tooltip
                        placement="top"
                        text={"Revisado por Área de Planeación"}
                      >
                        <Button
                          disabled={
                            isDisabledCierre
                              ? isDisabledCierre
                              : values.estatusRCP === "Cancelado"
                          }
                          variant="gray"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                          onClick={() => {
                            /*
                            6.	Valida que no exista ningún entregable con estatus “Pendiente”.
                            •	En caso de que exista un entregable con estatus “Pendiente”, continúa en el FA17
                            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            sigue el lunes con esto we, itera todo el array del tablero y validas va? :v
                             */
                            let isError = false;
                            const pendienteData = dataTable.filter(
                              (item) => item.estatus === "1" || item.estatus === "4"
                            );

                            if (
                              isEmpty(pendienteData) === false &&
                              pendienteData !== null
                            ) {
                              showMessage(MSG014);
                              return;
                            }
                            if (values.areaPlaneacion === "") {
                              isError = true;
                              setAreaPlaneacionErrorText("Campo requerido");
                            }
                            if (isError) {
                              errorToast(MSG005);
                            } else {
                              // showMessage(MSG014); FA17
                              setTypeModal("2");
                              setIsOpenButtonsModalMessage(MSG012);
                              setIsOpenButtonsModal(true);
                            }
                          }}
                        >
                          Revisado por AP
                        </Button>
                      </Tooltip>
                    </Authorization>
                  )}

                  <VerDocumento
                    title="Generar RCP"
                    idProyecto={idProyecto}
                    onGetService={onGetService}
                    show={isOpenModalDocumento}
                    urlPdfBlob={documentData}
                    oficioSolicitudPago={values.oficioSolicitudPago}
                    fechaSolicitud={values.fechaSolicitud}
                    onHide={handleCloseShowPdf}
                    setDocumentoGenerado={setDocumentoGenerado}
                    setErrorCarga={setErrorCarga}
                    setLoaderPrincipal={setLoading}
                  />

                  {values.estatusRCP === "Revisado por Área de Planeación" && (
                    <Authorization process={"PROY_VRCP_STA_VAL_LP"}>
                      <Tooltip
                        placement="top"
                        text={"Validado por Líder de Proyecto"}
                      >
                        <Button
                          disabled={false}
                          variant="gray"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                          onClick={() => {
                            setIsOpenButtonsModalMessage(MSG013);
                            setTypeModal("3");
                            setIsOpenButtonsModal(true);
                            /* FA15
                            1.	Guarda en la BD el valor de los campos “Estatus RCP” y “Fecha de entrega”.
      2.	Se ocultan las opciones “Revisado por AP” y “Validado por LP”. 
      3.	Activa y se visualiza la opción “Generar RCP” de acuerdo con la (RNA182).
                            */
                          }}
                        >
                          Validado por LP
                        </Button>
                      </Tooltip>
                    </Authorization>
                  )}

                  {(values.estatusRCP === "Validado por Líder de Proyecto" ||
                    values.estatusRCP === "Validado por LP" ||
                    values.estatusRCP === "RCP entregado") && (
                    <Authorization process={"PROY_VRCP_STA_GEN_RCP"}>
                        <Button
                          disabled={false}
                          variant="gray"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                          onClick={onGenerarRCP}
                        >
                          Generar RCP
                        </Button>
                    </Authorization>
                  )}
                </Col>
              )}

              <CancelModal
                handleApprove={async () => {
                  setDataTableDeleted([]);
                  tableReference.current.table.options.meta.revertData([
                    ...dataTable,
                  ]);
                  setIsActiveAddProvider(false);
                  setIsOpenCancelModal(false);
                }}
                handleDeny={() => setIsOpenCancelModal(false)}
                isOpenCancelModal={isOpenCancelModal}
                title={MSG003}
              />
              <BasicModal
                show={isOpenButtonsModal}
                onHide={() => {
                  setIsOpenButtonsModal(false);
                }}
                title="Mensaje"
                size="md"
                denyText="No"
                handleDeny={() => {
                  setIsOpenButtonsModal(false);
                }}
                approveText={"Si"}
                handleApprove={async () => {
                  if (typeModal === "1") {
                    setLoading(true);
                    const response = await onGetService(
                      `/proyectos/estatus-en-proceso/${idCierre}`
                    );
                    const { idEstatusRcp } = response;
                    const responseCatalogRCPStatus = await onGetService(
                      `/proyectos/estatus-RCP`
                    );
                    formRef.current.values.estatusRCP =
                      isEmpty(responseCatalogRCPStatus) === false
                        ? responseCatalogRCPStatus.filter(
                            (item) => item.primaryKey === idEstatusRcp
                          )[0].nombre
                        : "";

                    setLoading(false);
                  } else if (typeModal === "2") {
                    const { fechaEntrega, areaPlaneacion, estatusRCP } = values;
                    const data = [
                      ...tableReference.current.table.options.meta.getDataTable(),
                    ];
                    let archivos = [];
                    if (isEmpty(data) === false) {
                      archivos = [...data]
                        // .filter((item) => item.isEditable)
                        .map((element) => {
                          return {
                            id: element.id,
                            nombre: element.nombre,
                            descripcion: element.descripcion,
                            nivel: element.nivel,
                            orden: element.orden,
                            tipo: element.tipo,
                            obligatorio: element.obligatorio,
                            noAplica: element.noAplica,
                            estatus: true,
                            cargado: element.cargado,
                            extension: element.extension,
                            ruta: element.ruta,
                            tamanoMb: element.tamanoMb,
                            fechaModificacion: element.fechaModificacion,
                            justificacion: element.justificacion,
                            carpeta: element.carpeta,
                            fechaDocumento:
                              element.fechaDocumento === ""
                                ? ""
                                : moment(
                                    element.fechaDocumento,
                                    "YYYY-MM-DD"
                                  ).format("YYYY-MM-DDTHH:mm:ss"),
                            numeroPaginas: element.paginas,
                            estatusDocumento:
                              element.estatus === ""
                                ? ""
                                : statusCatalog.filter(
                                    (filterElement) =>
                                      filterElement.primaryKey ==
                                      element.estatus
                                  )[0].nombre,
                            type: element.type,
                            idContrato: element.idContrato,
                          };
                        });
                    }
                    await onSaveProcesoCierre(values, []);
                    const response = await onPostService(
                      `/proyectos/revisado-AP/${idCierre}`,
                      archivos,
                      MSG015
                    );
                    if (response !== null) {
                      await onGetInitialData();
                    }
                    setLoading(false);
                  } else if (typeModal === "3") {
                    await onGetService(`/proyectos/validado-LP/${idCierre}`);
                    await onGetInitialData();
                  }
                  dispatch(GetDetalleProyecto(idProyecto));
                  setIsOpenButtonsModal(false);
                }}
              >
                {isOpenButtonsModalMessage}
              </BasicModal>
            </Row>
          </Form>
        )}
      </Formik>
    </Container>
  );
};

export default VerificacionRCP;
