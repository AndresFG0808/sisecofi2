import React, { useState, useEffect, useRef, useContext } from "react";
import TableComponent from "../../../AdministrarProyectos/Proyectos/Proveedores/components/TableComponent";
import { useToast } from "../../../../hooks/useToast";
import {
  postData,
  getData,
  putData,
  deleteData,
  downloadDocumentPost,
} from "../../../../functions/api";
import Loader from "../../../../components/Loader";
import { useLocation } from "react-router-dom";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import showMessage from "../../../../components/Messages";

import isEmpty from "lodash/isEmpty";
import EditField from "./EditField";
import { ToggleCell } from "./ToggleCell";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import Estimacion from "./Estimacion";
import SelectCell from "./SelectCell";
import {
  BotonesAcciones,
  BotonesAccionesTop,
} from "../DatosGenerales/BotonesAcciones";
import { DictamenContext } from "../context";
import { isPending } from "@reduxjs/toolkit";
import { width } from "@fortawesome/free-regular-svg-icons/faCalendarAlt";

const MESSAGES = {
  MSG003: "",
  MSG006:
    "Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01).",
  MSG002: "Se guardaron los datos de forma correcta.",
  MSG013:
    "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
};

function TableContainer({ type }) {
  const location = useLocation();
  const [isParticipanteContrato, setIsParticipanteContrato] = useState(false);
  const state = location.state;
  const dictamenState = state?.dictamenState || {};
  let idDictamen = dictamenState?.idDictamen;
  let idContrato = dictamenState?.idContrato;
  let editable = dictamenState?.editable;
  const estatus = dictamenState?.estatus;
  const {
    isEditable,
    onReloadDictamenInfo,
    setOnClearPenasContractuales,
    setOnClearPenasConvencionales,
    setOnClearDeducciones,
    onReloadPenasInfo,
    onReloadProformaInfo,
  } = useContext(DictamenContext);

  const [isBasicModal, setIsBasicModal] = useState(false);
  const [isBasicModalEstimacion, setIsBasicModalEstimacion] = useState(false);

  const [typeCatalog, setTypeCatalog] = useState([]);
  const [desgloseCatalog, setDesgloseCatalog] = useState([]);

  const dateFilterFormat = (dateFormat, moment, row, columnId, filterValue) => {
    const rowValue = row.original[columnId];
    return rowValue !== null
      ? moment(rowValue).format(dateFormat).includes(filterValue)
      : false;
  };

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

  const textFilterByText = (idKey, row, columnId, filterValue) => {
    const rowValue = row.original[idKey];
    return rowValue
      ? rowValue.trim().toLowerCase().includes(filterValue.trim().toLowerCase())
      : false;
  };

  const handleTipoFilter = (...args) => {
    return catalogFilterByText(typeCatalog, "primaryKey", "nombre", ...args);
  };

  const handleConceptosServicio = (...args) => {
    return textFilterByText("conceptosServicio", ...args);
  };

  const handleDocumentosFilter = (...args) => {
    return textFilterByText("documentoNombre", ...args);
  };

  const onGetValidateParticipanteContrato = async () => {
    const response = await onPostService(
      `/admin-devengados/validar-responsabilidad-dictaminado`,
      { idDictamen }
    );
    setIsParticipanteContrato(response);
    return response;
  };

  useEffect(() => {
    if (estatus === "Proforma" || estatus === "Dictaminado") {
      onGetInitialData();
      onGetValidateParticipanteContrato();
    } else {
      setIsParticipanteContrato(true);
    }
  }, [estatus]);

  const handleProveedorFiltro = () => {
    return false;
  };

  const [loading, setLoading] = useState(true);

  const tableReference = useRef();

  const { errorToast } = useToast();

  const [isChecked, setIsChecked] = useState(true);
  const [isVisibleTable, setIsVisibleTable] = useState(true);

  const [dataTable, setDataTable] = useState(null);
  const [dataTableDeleted, setDataTableDeleted] = useState([]);

  const [isActiveAddProvider, setIsActiveAddProvider] = useState(false);

  const [messageModal, setMessageModal] = useState("");

  const onGetFileNameByIdTypeScreen = (typeArg) => {
    let url = "";
    const format = "xlsx";
    switch (typeArg) {
      case 1:
        url = `Penas contractuales.`;
        break;
      case 2:
        url = `Penas convencionales.`;
        break;
      case 3:
        url = `Deducciones.`;
        break;

      default:
        return null;
    }
    return `${url}${format}`;
  };

  const getUrlByIdPenaType = (type, urlFunction) => {
    let url = null;
    switch (`${urlFunction}${type}`) {
      case "onGetTipoPena1":
        url = `/admin-devengados/tipo-pena_contractual`;
        break;
      case "onGetTipoPena2":
        url = `/admin-devengados/tipo-pena_convencional`;
        break;
      case "onGetTipoPena3":
        url = `/admin-devengados/tipo-pena_deduccion`;
        break;
      case "onGetDesglose1":
        url = `/admin-devengados/desglose/${idContrato}`;
        break;
      case "onGetDesglose2":
        url = `/admin-devengados/desglose/${idContrato}`;
        break;
      case "onGetDesglose3":
        url = `/admin-devengados/desglose/${idContrato}`;
        break;
      case "onGetPenas1":
        url = `/admin-devengados/penas-contractuales`;
        break;
      case "onGetPenas2":
        url = `/admin-devengados/penas-convencionales`;
        break;
      case "onGetPenas3":
        url = `/admin-devengados/deducciones`;
        break;
      case "onGetDocumentByIdType1":
        url = `/admin-devengados/obtener-documentos-pena-contractual/`;
        break;
      case "onGetDocumentByIdType2":
        url = `/admin-devengados/obtener-documentos-pena-contractual/`;
        break;
      case "onGetDocumentByIdType3":
        url = `/admin-devengados/obtener-documentos-pena-contractual/`;
        break;
      case "handleDownloadExcel1":
        url = `/admin-devengados/generar-excel-penas-contractuales`;
        break;
      case "handleDownloadExcel2":
        url = `/admin-devengados/generar-excel-penas-convencionales`;
        break;
      case "handleDownloadExcel3":
        url = `/admin-devengados/generar-excel-deducciones`;
        break;
      case "onSavePenas1":
        url = `/admin-devengados/penas-contractuales-guardar`;
        break;
      case "onSavePenas2":
        url = `/admin-devengados/penas-convencionales-guardar`;
        break;
      case "onSavePenas3":
        url = `/admin-devengados/deducciones-guardar`;
        break;
      case "onUpdatePenas1":
        url = `/admin-devengados/penas-contractuales-modificar`;
        break;
      case "onUpdatePenas2":
        url = `/admin-devengados/penas-convencionales-modificar`;
        break;
      case "onUpdatePenas3":
        url = `/admin-devengados/deducciones-modificar`;
        break;
      case "onDeletePenas1":
        url = `/admin-devengados/eliminar-pena-contractual`;
        break;
      case "onDeletePenas2":
        url = `/admin-devengados/eliminar-pena-convencional`;
        break;
      case "onDeletePenas3":
        url = `/admin-devengados/eliminar-deducciones`;
        break;

      default:
        return null;
    }
    return url;
  };

  useEffect(() => {
    if (dataTable !== null && isEmpty(dataTable) === false) onClearSection();
  }, [tableReference, dataTable]);

  const onClearMethod = () => {
    setDataTable([]);
    tableReference.current !== null && tableReference.current.table.options.meta.revertData([]);
  };

  const onClearSection = () => {
    switch (type) {
      case 1:
        setOnClearPenasContractuales(() => onClearMethod);
        break;
      case 2:
        setOnClearPenasConvencionales(() => onClearMethod);
        break;
      case 3:
        setOnClearDeducciones(() => onClearMethod);
        break;
      default:
        return null;
    }
  };

  const onGetInitialData = async () => {
    setLoading(true);
    await onGetTipoPena();
    await onGetDesglose();
    await onGetPenas();
    setLoading(false);
  };

  const onGetPenas = async () => {
    const response = await onPostService(
      getUrlByIdPenaType(type, "onGetPenas"),
      {
        idDictamen: idDictamen,
      }
    );
    const { data } = response;
    if (isEmpty(data) === false) {
      const table = data.map((item, index) => {
        const {
          idPenaPrimary,
          idTipoPena,
          idDocumento,
          monto,
          penaAplicable,
          idDesglose,
          conceptoServicio,
          documentoNombre,
        } = item;
        return {
          index: index + 1,
          identifier: idPenaPrimary.toString(),
          isEditable: false,
          checked: false,
          documentoNombre,
          tipo: idTipoPena,
          documentos: idDocumento,
          monto: monto,
          penaAplicable: penaAplicable, // depende de lo seleccionado en documentos
          desglose: idDesglose, // la opción SAT viene por default
          conceptosServicio: conceptoServicio === null ? "" : conceptoServicio,
          isNewRegister: false,
          documentosHelperText: "",
          tipoHelperText: "",
          conceptosServicioHelperText: "",
        };
      });
      setDataTable(table);
    } else {
      setDataTable([]);
    }
    return null;
  };

  const onGetTipoPena = async () => {
    const data = {
      idDictamen,
    };
    const response = await onPostService(
      getUrlByIdPenaType(type, "onGetTipoPena"),
      data
    );
    if (isEmpty(response) === false) {
      setTypeCatalog([...response]);
    }
    return response;
  };

  const onGetDesglose = async (typeArg) => {
    const response = await onGetService(
      getUrlByIdPenaType(type, "onGetDesglose")
    );
    if (isEmpty(response) === false) {
      setDesgloseCatalog([...response]);
    }
    return response;
  };

  useEffect(() => {
    onGetInitialData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const getDocumentName = (element) => {
    let documentName = "";
    const { documentosCatalog, documentoNombre } = element;
    if (isEmpty(documentosCatalog)) {
      documentName = documentoNombre;
    } else {
      documentName = documentosCatalog.filter(
        (item) => item.id == parseInt(element.documentos, 10)
      )[0].informeDocumental;
    }
    return documentName;
  };

  const onGetService = async (url) => {
    try {
      const response = await getData(url);
      return response.data;
    } catch (err) {
      setMessageModal(
        err.message
          ? err.message
          : "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
      );
      setIsBasicModal(true);
      return null;
    }
  };

  const onPostService = async (url, data) => {
    try {
      const response = await postData(url, data);
      return response.data;
    } catch (err) {
      setMessageModal(
        err.message
          ? err.message
          : "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
      );
      setIsBasicModal(true);
      return null;
    }
  };

  const onPutService = async (url, data) => {
    try {
      const response = await putData(url, data);
      return response.data;
    } catch (err) {
      setMessageModal(
        err.message
          ? err.message
          : "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
      );
      setIsBasicModal(true);
      return null;
    }
  };

  const onDeleteService = async (url, data) => {
    try {
      const response = await deleteData(url, data);
      return response.data;
    } catch (err) {
      setMessageModal(
        err.message
          ? err.message
          : "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
      );
      setIsBasicModal(true);
      return null;
    }
  };

  const handleDownloadExcel = async () => {
    setLoading(true);
    try {
      const data = dataTable
        .filter((item) => item.isNewRegister === false)
        .map((item, index) => {
          const {
            identifier,
            tipo,
            documentos,
            penaAplicable,
            desglose,
            monto,
          } = item;
          return {
            idDictamen,
            idPenaPrimary: index + 1,
            dictamenId: idDictamen,
            idTipoPena: tipo,
            tipoPena: typeCatalog.filter(
              (item) => item.primaryKey == parseInt(tipo, 10)
            )[0].nombre,
            idDesglose: parseInt(item.desglose, 10),
            idPenaContractualDocumento: documentos,
            penaAplicable: penaAplicable,
            desglose: desgloseCatalog.filter(
              (item) => item.primaryKey == desglose
            )[0]?.nombre,
            conceptoServicio: getConceptoServicio(item),
            monto,
            idDocumento: parseInt(item.documentos, 10),
            documentoNombre: getDocumentName(item),
          };
        });
      let reportResponse = await onPostService(
        getUrlByIdPenaType(type, "handleDownloadExcel"),
        data
      );
      await DownloadFileBase64(
        reportResponse.data,
        onGetFileNameByIdTypeScreen(type),
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (err) {
      let errorMessage =
        err?.response?.data !== "" && err?.response?.data?.mensaje[0];
      let errorIdDuplicado = errorMessage === "MSG011";
      if (errorIdDuplicado && err?.response?.status !== 403) {
        showMessage(errorMessage);
      } else if (err?.response?.status !== 403) {
        showMessage("MSG012");
      }
    }
    setLoading(false);
  };

  const onActiveEditIcon = (value, tableProps) => {
    if (value === false) {
      const table = [...tableProps.table.options.meta.getDataTable()];
      const isEditableRows = table.filter((item) => item.isEditable === true);
      if (isEmpty(isEditableRows)) setIsActiveAddProvider(value);
    } else {
      setIsActiveAddProvider(value);
    }
  };

  useEffect(() => {
    if (isChecked === false) {
      tableReference.current.table.options.meta.revertData([...dataTable]);
      setIsActiveAddProvider(false);
      setIsVisibleTable(false);
    } else {
      setIsVisibleTable(true);
    }
  }, [isChecked, dataTable]);

  const CustomCell = ({ row, column, width }) => {
    const value = row.original[column.id];
    return <p style={{ width }}>{value}</p>;
  };

  const CustomHeader = ({ title, width }) => (
    <span style={{ width }}>{title}</span>
  );

  const getColumnsByIDSection = (
    type,
    typeCatalogArg,
    desgloseCatalogArg,
    estatusArg
  ) => {
    let columns = [];
    if (type === 1 || type === 2) {
      columns = [
        {
          accessorKey: "index",
          header: "Id",
          width: "20px",
          filterFn: "includesString",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width="10px"
            />
          ),
        },

        {
          accessorKey: "tipo",
          header: "Tipo",
          filterFn: handleTipoFilter,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              type={type}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              onGetService={onGetService}
              onPostService={onPostService}
              urlByDocument={getUrlByIdPenaType(type, "onGetDocumentByIdType")}
              idDictamen={idDictamen}
              idContrato={idContrato}
              options={typeCatalogArg}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "documentos",
          header: "Documentos",
          filterFn: handleDocumentosFilter,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              type={type}
              keyValue={"id"}
              keyTextValue="informeDocumental"
              idContrato={idContrato}
              onGetService={onGetService}
              onPostService={onPostService}
              column={props.column}
              table={props.table}
              options={[]}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "penaAplicable",
          header: "Pena aplicable",
          enableSorting: false,
          enableColumnFilter: false,
        },

        {
          accessorKey: "desglose",
          header: "Desglose",
          filterFn: handleProveedorFiltro,
          enableSorting: false,
          enableColumnFilter: false,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              options={desgloseCatalogArg}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "monto",
          header: "Monto",
          filterFn: "includesString",
          enableSorting: false,
          enableColumnFilter: false,
          cell: (props) => (
            <EditField
              getValue={props.getValue}
              type={"select"}
              estatus={estatusArg}
              tableType={type}
              rowClassName="col-provider"
              isRequired={true}
              column={props.column}
              row={props.row}
              table={props.table}
              isEditable={estatusArg === "Inicial" ? false : true}
            />
          ),
        },

        {
          accessorKey: "acciones",
          header: "Acciones",
          enableSorting: false,
          enableColumnFilter: false,
          cell: (props) => (
            <>
              {isParticipanteContrato === true && (
                <>
                  {type == 3 && estatus === "Proforma" ? null : (
                    <ToggleCell
                      editable={permission()}
                      estatusEditable={isEditable}
                      estatus={estatusArg}
                      row={props.row}
                      onClickEditToggle={() => {}}
                      tableType={type}
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
                          (item) => item.identifier === row.identifier
                        )[0];
                        const table = [
                          ...props.table.options.meta.getDataTable(),
                        ];
                        props.table.options.meta.revertData(
                          table.map((item) => {
                            const newItem =
                              item.identifier === row.identifier
                                ? { ...originalRow }
                                : { ...item };
                            return newItem;
                          })
                        );
                      }}
                      column={props.column}
                      table={props.table}
                      onActiveEditIcon={(value) =>
                        onActiveEditIcon(value, props)
                      }
                      onRemoveProvider={() => {
                        setIsActiveAddProvider(true);
                      }}
                    />
                  )}
                </>
              )}
            </>
          ),
        },
      ];
    } else {
      columns = [
        {
          accessorKey: "index",
          header: "Id",
          filterFn: "includesString",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width="50px"
            />
          ),
        },

        {
          accessorKey: "tipo",
          header: "Tipo",
          filterFn: handleTipoFilter,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              type={type}
              column={props.column}
              table={props.table}
              onGetService={onGetService}
              onPostService={onPostService}
              urlByDocument={getUrlByIdPenaType(type, "onGetDocumentByIdType")}
              idDictamen={idDictamen}
              idContrato={idContrato}
              options={typeCatalogArg}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "documentos",
          header: "Documentos",
          filterFn: handleDocumentosFilter,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              type={type}
              keyValue={"id"}
              keyTextValue="informeDocumental"
              idContrato={idContrato}
              onGetService={onGetService}
              onPostService={onPostService}
              column={props.column}
              table={props.table}
              options={[]}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "penaAplicable",
          header: "Deducción aplicable",
          enableSorting: false,
          enableColumnFilter: false,
        },

        {
          accessorKey: "desglose",
          header: "Desglose",
          filterFn: handleProveedorFiltro,
          enableSorting: false,
          enableColumnFilter: false,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              keyValue={"primaryKey"}
              keyTextValue="nombre"
              column={props.column}
              table={props.table}
              options={desgloseCatalogArg}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "conceptosServicio",
          header: "Conceptos de servicio",
          filterFn: handleConceptosServicio,
          cell: (props) => (
            <SelectCell
              tableType={type}
              getValue={props.getValue}
              row={props.row}
              type={type}
              keyValue={"id"}
              idDictamen={idDictamen}
              keyTextValue="nombre"
              idContrato={idContrato}
              onGetService={onGetService}
              column={props.column}
              table={props.table}
              options={[]}
              status={estatusArg}
            />
          ),
        },

        {
          accessorKey: "monto",
          header: "Monto",
          filterFn: "includesString",
          enableSorting: false,
          enableColumnFilter: false,
          cell: (props) => (
            <EditField
              getValue={props.getValue}
              type={"select"}
              tableType={type}
              estatus={estatusArg}
              rowClassName="col-provider"
              isRequired={true}
              column={props.column}
              row={props.row}
              table={props.table}
              isEditable={estatusArg === "Inicial" ? false : true}
            />
          ),
        },

        {
          accessorKey: "acciones",
          header: "Acciones",
          enableSorting: false,
          enableColumnFilter: false,
          cell: (props) => (
            <>
              <ToggleCell
                editable={permission()}
                tableType={type}
                estatusEditable={isEditable}
                estatus={estatusArg}
                row={props.row}
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
                    (item) => item.identifier === row.identifier
                  )[0];
                  const table = [...props.table.options.meta.getDataTable()];
                  props.table.options.meta.revertData(
                    table.map((item) => {
                      const newItem =
                        item.identifier === row.identifier
                          ? { ...originalRow }
                          : { ...item };
                      return newItem;
                    })
                  );
                }}
                column={props.column}
                table={props.table}
                onActiveEditIcon={(value) => onActiveEditIcon(value, props)}
                onRemoveProvider={() => {
                  setIsActiveAddProvider(true);
                }}
              />
            </>
          ),
        },
      ];
    }
    return columns;
  };

  const columns = React.useMemo(
    () => getColumnsByIDSection(type, typeCatalog, desgloseCatalog, estatus),
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [
      typeCatalog,
      dataTableDeleted,
      desgloseCatalog,
      estatus,
      dataTable,
      editable,
      isEditable,
    ]
  );

  const getConceptoServicio = (element) => {
    let name = "";
    const { conceptosServicio, conceptoServiciosCatalog } = element;
    if (
      conceptoServiciosCatalog !== undefined &&
      isEmpty(conceptoServiciosCatalog) === false &&
      conceptosServicio !== ""
    ) {
      name = conceptoServiciosCatalog.filter(
        (item) => item.id == conceptosServicio
      )[0].nombre;
    }
    if (conceptosServicio !== "" && conceptosServicio !== undefined) {
      name = conceptosServicio;
    }
    return name;
  };
  const permission = () => (isEditable === false ? false : editable);
  const onGetProcess = (type) => {
    let process = "";
    if (true) {
      process = "";
    }
  };
  console.log("dpiekdoikepodkepk 1 ", estatus)
  return (
    <>
      {loading && <Loader />}

      {/*FA06 depende de que el estatus sea Inicial, si es Dictaminado nos manda hasta el FA21*/}
      <BotonesAccionesTop
        processType={type}
        process={"CON_SERV_ADMIN_DICT"}
        handleAddProvider={() => {
          const data = [
            ...tableReference.current.table.options.meta.getDataTable(),
          ];
          const index = data.length;
          data.push({
            index: index + 1,
            identifier: index,
            checked: false,
            tipo: "",
            documentos: "",
            monto: estatus === "Inicial" ? 0 : "",
            penaAplicable: "",
            desglose: "1",
            conceptosServicio: "",
            isNewRegister: true,
            isEditable: true,
            documentosHelperText: "",
            tipoHelperText: "",
            conceptosServicioHelperText: "",
            montoHelperText: "",
            documentosCatalog: [],
            conceptoServiciosCatalog: [],
          });
          tableReference.current.table.options.meta.revertData([...data]);
          setIsActiveAddProvider(true);
        }}
        disabledAddProviderButton={() => {}}
        handleDownloadExcel={handleDownloadExcel}
        onChangeCheck={setIsChecked}
        isChecked={isChecked}
        disabledExcelButton={isEmpty(dataTable)}
        editable={permission()}
        isVisibleCheck={true}
        isVisibleAddAndExcel={isChecked}
        buttonAddText="Agregar"
      />
      {isVisibleTable &&
        isEmpty(typeCatalog) === false &&
        dataTable !== null && (
          <>
            <TableComponent
              tableReference={tableReference}
              dataTable={dataTable}
              columns={columns}
              providerTableTitle=""
              setDataTable={() => {}}
            />
            <BotonesAcciones
              editable={estatus === "Dictaminado" ? true : permission()}
              isVisible={isActiveAddProvider}
              handleFetchProvider={async () => {
                try {
                  setLoading(true);
                  let responseNewRegister = null;
                  let responseUpdateRegister = null;
                  let responseDelete = null;
                  const data =
                    tableReference.current.table.options.meta.getDataTable();
                  let isError = false;
                  const editData = [...data].map((item) => {
                    const editableItem = { ...item };
                    if (editableItem.documentos === "") {
                      editableItem.documentosHelperText = "Dato requerido";
                      isError = true;
                    }
                    if (editableItem.tipo === "") {
                      editableItem.tipoHelperText = "Dato requerido";
                      isError = true;
                    }

                    if ((type == 3) && editableItem.tipo == 1 && editableItem.conceptosServicio === '') {
                      editableItem.conceptosServicioHelperText = "Dato requerido";
                      isError = true;
                    }


                    if (
                      estatus === "Dictaminado" &&
                      editableItem.monto.toString().trim() === ""
                    ) {
                      editableItem.montoHelperText = "Dato requerido";
                      isError = true;
                    }

                    editableItem.checked = false;
                    return editableItem;
                  });

                  setDataTable(editData);
                  if (isError === false) {
                    const arrayNewRegisters = [];
                    const arrayUpdateRegisters = [];
                    for (let element in editData) {
                      if (editData[element].isNewRegister) {
                        const data = {
                          dictamenId: idDictamen,
                          idTipoPena: parseInt(editData[element].tipo, 10),
                          tipoPena: typeCatalog.filter(
                            (item) =>
                              item.primaryKey ==
                              parseInt(editData[element].tipo, 10)
                          )[0].nombre,

                          penaAplicable: editData[element].penaAplicable,

                          desglose: editData[element].desglose === '' ? 'SAT' : desgloseCatalog.filter(
                            (item) =>
                              item.primaryKey ==
                              parseInt(editData[element].desglose, 10)
                          )[0].nombre,
                          idDesglose: editData[element].desglose === '' ? '1' : parseInt(editData[element].desglose, 10),

                          conceptoServicio: getConceptoServicio(
                            editData[element]
                          ),
                          monto: parseFloat(editData[element].monto),
                          idDocumento: parseInt(
                            editData[element].documentos,
                            10
                          ),
                          documentoNombre: getDocumentName(editData[element]),
                        };
                        arrayNewRegisters.push(data);
                      } else {
                        const data = {
                          idPenaPrimary: editData[element].identifier,
                          idPenaContractual:
                            editData[element].idPenaContractual,
                          dictamenId: idDictamen,
                          idContrato,

                          idTipoPena: parseInt(editData[element].tipo, 10),
                          tipoPena: typeCatalog.filter(
                            (item) =>
                              item.primaryKey ==
                              parseInt(editData[element].tipo, 10)
                          )[0].nombre,
                          penaAplicable: editData[element].penaAplicable,

                          desglose: editData[element].desglose === '' ? 'SAT' : desgloseCatalog.filter(
                            (item) =>
                              item.primaryKey ==
                              parseInt(editData[element].desglose, 10)
                          )[0].nombre,
                          idDesglose: editData[element].desglose === '' ? '1' : parseInt(editData[element].desglose, 10),

                          idDocumento: parseInt(
                            editData[element].documentos,
                            10
                          ),
                          documentoNombre: getDocumentName(editData[element]),
                          monto: parseFloat(editData[element].monto),
                          conceptoServicio: getConceptoServicio(
                            editData[element]
                          ),
                        };
                        arrayUpdateRegisters.push(data);
                      }
                    }
                    if (isEmpty(arrayNewRegisters) === false) {
                      responseNewRegister = await onPostService(
                        getUrlByIdPenaType(type, "onSavePenas"),
                        arrayNewRegisters
                      );
                    }
                    if (isEmpty(arrayUpdateRegisters) === false) {
                      responseUpdateRegister = await onPutService(
                        getUrlByIdPenaType(type, "onUpdatePenas"),
                        arrayUpdateRegisters
                      );
                    }
                    if (isEmpty(dataTableDeleted) === false) {
                      responseDelete = await onDeleteService(
                        getUrlByIdPenaType(type, "onDeletePenas"),
                        {
                          ids: dataTableDeleted.map((item) => {
                            return parseInt(item.identifier, 10);
                          }),
                        }
                      );
                    }
                    await onGetPenas();
                    setDataTableDeleted([]);
                    setIsActiveAddProvider(false);
                    await onReloadDictamenInfo(idDictamen);
                    if (typeof onReloadPenasInfo === "function")
                      onReloadPenasInfo();
                    if (typeof onReloadProformaInfo === "function")
                      onReloadProformaInfo();
                    setLoading(false);
                    setMessageModal(MESSAGES.MSG002);
                    setIsBasicModal(true);
                    return;
                  } else {
                    errorToast(MESSAGES.MSG013);
                  }

                  setLoading(false);
                } catch (err) {
                  console.log("doijeodijeoij ", err);
                }
              }}
              modalTitle={
                "Se perderá la información capturada ¿Desea continuar?"
              }
              handleCancelAddProviders={() => {
                setDataTableDeleted([]);
                tableReference.current.table.options.meta.revertData(
                  [...dataTable].filter((item) => item.isNewRegister === false)
                );
                setIsActiveAddProvider(false);
              }}
            />
          </>
        )}

      <SingleBasicModal
        handleApprove={() => setIsBasicModal(false)}
        handleDeny={() => setIsBasicModal(false)}
        approveText={"Aceptar"}
        size="md"
        show={isBasicModal}
        title={"Mensaje"}
        onHide={() => setIsBasicModal(false)}
      >
        {messageModal}
      </SingleBasicModal>

      <SingleBasicModal
        handleApprove={() => setIsBasicModal(false)}
        handleDeny={() => setIsBasicModal(false)}
        approveText={"Aceptar"}
        size="md"
        show={isBasicModal}
        title={"Mensaje"}
        onHide={() => setIsBasicModal(false)}
      >
        {messageModal}
      </SingleBasicModal>

      {isBasicModalEstimacion && (
        <Estimacion
          setIsBasicModal={setIsBasicModalEstimacion}
          isBasicModal={isBasicModalEstimacion}
          onSuccess={() => {
            setMessageModal(
              "Se  actualizó  el  registro  de  los  servicios  de  acuerdo  con  la  estimación seleccionada."
            );
            setIsBasicModal(true);
            setIsBasicModalEstimacion(false);
          }}
        />
      )}
    </>
  );
}

export default TableContainer;
