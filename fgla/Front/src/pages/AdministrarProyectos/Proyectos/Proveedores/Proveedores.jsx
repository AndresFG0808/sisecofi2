import React, { useEffect, useState, useRef } from "react";
import EditableCell from "./components/EditableCell";
import { ToggleCell } from "./components/ToggleCell";
import { DropCell } from "./components/DropCell";
import { CommentCell } from "./components/CommentCell";
import { Tooltip } from "../../../../components/Tooltip";
import {
  BotonesAcciones,
  BotonesAccionesTop,
} from "./components/BotonesAcciones";
import showMessage from "../../../../components/Messages";
import { Loader } from "../../../../components";
import { providerTableTitle } from "./constants";
import Authorization from "../../../../components/Authorization";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import { MESSAGES } from "./constants";
import moment from "moment";
import {
  catalogFilterByText,
  dateFilterFormat,
  isNonNullableDate,
} from "./utils/filters";
import TableComponent from "./components/TableComponent";
import { getData, putData, downloadDocument } from "../../../../functions/api";
import cloneDeep from "lodash/cloneDeep";
import isEmpty from "lodash/isEmpty";
import { providerModel, validateText, dateFormat } from "./constants";
import { useDispatch, useSelector } from "react-redux";
import { useLazyGetFichaQuery } from "../../store";
import { isNil } from "lodash";
import { downloadExcelBlob } from "../../../../functions/utils";
import { GetDetalleProyecto } from "../../../../store/infoComites/infoComitesActions";
import { useGetAuthorization } from "../../../../hooks/useGetAuthorization";

let originalDataTable = [];

export const Proveedores = () => {
  const dispatch = useDispatch();

  const [idSelectDefault, setIdSelectDefault] = useState("");

  let tableReference = useRef();
  const { proyecto, editable: editableState } = useSelector(
    (state) => state.proyectos
  );
  const { isAuthorized: accessPP } = useGetAuthorization("PROY_PP_ADMIN");
  const [editable, setIsEditable] = useState(editableState || !accessPP);
  const idProyecto = proyecto?.proyecto?.idProyecto;
  const [trigger, { data: fichaTecnica }] = useLazyGetFichaQuery();
  const [tipoProcedimiento, setTipoProcedimiento] = useState("");
  const [table, setTable] = useState(null);
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [dataTableRemoved, setDataTableRemoved] = useState([]);
  const [originalRow, setOriginalRow] = useState([]);
  const [catalogoProveedor, setCatalogoProveedor] = useState([]);
  const [catalogoIMercado, setCatalogoIMercado] = useState([]);
  const [catalogoRespuestaIM, setCatalogoRespuestaIM] = useState([]);
  const [catalogoJuntaAclaracion, setCatalogoJuntaAclaracion] = useState([]);
  const [catalogoLicitacionPublica, setCatalogoLicitacionPublica] = useState(
    []
  );

  const [hasReRenderTable, setHasReRenderTable] = useState(false);
  const [hasActiveUseStateRender, setHasActiveUseStateRender] = useState(false);

  const [isActiveAddProvider, setIsActiveAddProvider] = useState(false);
  const [isActiveEditableProvider, setIsActiveEditableProvider] =
    useState(false);

  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);
  const [messageModal, setMessageModal] = useState("");

  const [isEptyProvidersModal, setIsEptyProvidersModal] = useState(false);
  const handleProveedorFiltro = (...args) =>
    catalogFilterByText(catalogoProveedor, ...args);
  const handleIdIMercadoFiltro = (...args) =>
    catalogFilterByText(catalogoIMercado, ...args);
  const handleIdRespuestaIMFiltro = (...args) =>
    catalogFilterByText(catalogoRespuestaIM, ...args);
  const handleIdJuntaAclaracionFiltro = (...args) =>
    catalogFilterByText(catalogoJuntaAclaracion, ...args);
  const handleIdLicitacionPublicaFiltro = (...args) =>
    catalogFilterByText(catalogoLicitacionPublica, ...args);
  const handleDateFiltro = (...args) =>
    dateFilterFormat(dateFormat, moment, ...args);

  useEffect(() => {
    const onSetTipoProcedimiento = () => {
      setTipoProcedimiento(fichaTecnica?.tipoProcedimiento?.primaryKey);
    };
    if (isEmpty(fichaTecnica) === false) onSetTipoProcedimiento();
  }, [fichaTecnica]);

  const onGetProviders = async () => {
    try {
      const providerCatalogResponse = await getData(
        `/proyectos/proveedores-asignados/proyecto/${idProyecto}`
      );
      return [...providerCatalogResponse.data];
    } catch (err) {
      showMessage(MESSAGES.MSG003);
      return [];
    }
  };

  const onGetActiveProviders = async () => {
    try {
      const providerCatalogResponse = await getData(
        "/proyectos/proveedores-activos"
      );
      return [...providerCatalogResponse.data];
    } catch (err) {
      showMessage(MESSAGES.MSG003);
      return [];
    }
  };

  const onGetProvidersAnswers = async () => {
    try {
      let respondeCatalogProvidersAnswers = await getData(
        "/proyectos/respuesta-proveedor"
      );
      return [...respondeCatalogProvidersAnswers.data];
    } catch (err) {
      err.message ? showMessage(err.message) : showMessage("Error genérico");
      return [];
    }
  };

  const onGetInitialData = async () => {
    const providerCatalogResponse = await onGetActiveProviders();
    if (isEmpty(providerCatalogResponse) === false) {
      setCatalogoProveedor(
        [...providerCatalogResponse].map((item) => {
          return {
            id: item.idProveedor.toString(),
            texto: item.nombreProveedor,
          };
        })
      );
    }
    const providersAnswersResponse = await onGetProvidersAnswers();
    if (isEmpty(providersAnswersResponse) === false) {
      const defaultItem = providersAnswersResponse.filter(
        (item) => item.descripcion === "N/A"
      )[0];
      if (isNil(defaultItem) === false) {
        setIdSelectDefault(defaultItem.primaryKey.toString());
      }
      const catalogProvidersAnswers = [...providersAnswersResponse].map(
        (item, index) => {
          return {
            index: index + 1,
            id: item.primaryKey.toString(),
            texto: item.nombre,
          };
        }
      );
      setCatalogoIMercado(catalogProvidersAnswers);
      setCatalogoRespuestaIM(catalogProvidersAnswers);
      setCatalogoJuntaAclaracion(catalogProvidersAnswers);
      setCatalogoLicitacionPublica(catalogProvidersAnswers);
    }

    const providers = await onGetProviders();
    if (isEmpty(providers) === false) {
      const dataArray = providers.map((item, index) => {
        return {
          index: index + 1,
          identifier: item.id ? item.id.toString() : "",
          idProyecto,
          requiredFields: [
            {
              requiredField: "idProveedor",
              helperText: "",
            },
          ],
          idProveedor: item.idProveedor ? item.idProveedor.toString() : "",
          idInvestigacionMercado: item.idInvestigacionMercado
            ? item.idInvestigacionMercado.toString()
            : "",
          fechaIm: item.fechaIm,
          respuestaIm: item.respuestaIm ? item.respuestaIm.toString() : "",
          fechaRespuestaIm: item.fechaRespuestaIm,
          juntaAclaracion: item.juntaAclaracion
            ? item.juntaAclaracion.toString()
            : "",
          fechaJa: item.fechaJa,
          licitacionPublica: item.licitacionPublica
            ? item.licitacionPublica.toString()
            : "",
          fechaLp: item.fechaLp,
          comentario: item.comentario,
          isEditable: false,
        };
      });
      setDataTable(() => {
        return cloneDeep([...dataArray]);
      });
      originalDataTable = cloneDeep([...dataArray]);
      setDataTableRemoved([]);
    }
    setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    setLoading(false);
  };

  const onRemoveProvider = async (id, onRemoveSuccess, tableRef) => {
    setTable(tableRef);

    setDataTable((prevDataTable) => {
      setDataTableRemoved((prevDataTableRemoved) =>
        prevDataTableRemoved.concat(
          [...tableReference.current.table.options.meta.getDataTable()].filter(
            (item) => item.identifier === id
          )
        )
      );
      return [...tableReference.current.table.options.meta.getDataTable()]
        .filter((item) => item.identifier !== id)
        .map((item, index) => {
          const obj = { ...item };
          obj.index = index + 1;
          return obj;
        });
    });

    onRemoveSuccess();
    setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    setIsActiveAddProvider(true);
  };

  useEffect(() => {
    onGetInitialData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const onValidateEmptyProviders = (dataTableArg) => {
    return dataTableArg.length === 0;
  };

  const handleApproveEmptyProviders = () => {
    handleAddProvider();
    setIsEptyProvidersModal(false);
  };

  const handleAddProvider = async () => {
    setLoading(true);
    const proyectoData = await trigger(idProyecto);
    setIsActiveAddProvider(true);
    const newProvider = { ...providerModel };
    await setDataTable((prevDataTable) => {
      newProvider.identifier = (prevDataTable.length + 1).toString() + "+";
      newProvider.index = prevDataTable.length + 1;
      if (
        proyectoData.data.tipoProcedimiento !== null &&
        (proyectoData.data.tipoProcedimiento.primaryKey == 4 ||
          proyectoData.data.tipoProcedimiento.primaryKey == 2)
      ) {
        newProvider.juntaAclaracion = idSelectDefault;
        newProvider.licitacionPublica = idSelectDefault;
      }
      return [...prevDataTable, { ...newProvider }];
    });
    setLoading(false);
  };

  const handleCancelAddProviders = async () => {
    const datanew = cloneDeep(
      originalDataTable.map((item) => {
        return { ...item };
      })
    );
    setDataTable(datanew);
    tableReference.current.table.options.meta.revertData(datanew);
    setOriginalRow([]);
    setIsActiveAddProvider(
      (prevIsActiveAddProvider) => !prevIsActiveAddProvider
    );
    setIsActiveEditableProvider(
      (prevIsActiveEditableProvider) => !prevIsActiveEditableProvider
    );
    setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
  };

  const onValidateRequiredFields = async () => {
    let isError = false;
    const onEditableTableState = (item) => {
      if (item.isNewProvider) {
        item.requiredFields = item.requiredFields.map(
          (requiredFieldsElement) => {
            if (
              (requiredFieldsElement.requiredField &&
                item[requiredFieldsElement.requiredField] === null) ||
              (requiredFieldsElement.requiredField &&
                item[requiredFieldsElement.requiredField] === "")
            ) {
              requiredFieldsElement.helperText = validateText;
              isError = true;
            } else {
              if (requiredFieldsElement.helperText)
                requiredFieldsElement.helperText = "";
            }
            return requiredFieldsElement;
          }
        );
      }
      return null;
    };
    await setDataTable((prevDataTable) =>
      [...prevDataTable].map((item) => {
        const editableItem = onEditableTableState(item);
        return editableItem ? editableItem : item;
      })
    );
    return isError;
  };

  const handleFetchProvider = async () => {
    setLoading(true);
    let isError = await onValidateRequiredFields();
    const isDateEmpty = dataTable.some((row) => {
      return (
        isNonNullableDate(
          row?.idInvestigacionMercado,
          catalogoIMercado,
          row?.fechaIm
        ) ||
        isNonNullableDate(
          row?.respuestaIm,
          catalogoRespuestaIM,
          row.fechaRespuestaIm
        ) ||
        isNonNullableDate(
          row?.juntaAclaracion,
          catalogoJuntaAclaracion,
          row?.fechaJa
        ) ||
        isNonNullableDate(
          row?.licitacionPublica,
          catalogoLicitacionPublica,
          row?.fechaLp
        )
      );
    });
    if (isDateEmpty) {
      setLoading(false);
      showMessage(
        "Favor de ingresar los datos obligatorios marcados con un asterisco (*)."
      );
      return;
    }
    if (isError === false) {
      try {
        const API_URL = "/proyectos/proveedores-asignados/guardar";
        const data = {
          agregarModificar: [...dataTable].map((item, index) => {
            item.comentario =
              isNil(table) === false
                ? table.getRow(index).original.comentario
                : item.comentario;
            return {
              id: item.isNewProvider ? null : item.identifier,
              idProyecto: idProyecto,
              idProveedor: item.idProveedor,
              idInvestigacionMercado:
                item.idInvestigacionMercado === ""
                  ? null
                  : item.idInvestigacionMercado,
              fechaIM: item.fechaIm,
              respuestaIm: item.respuestaIm,
              fechaRespuestaIM: item.fechaRespuestaIm,
              juntaAclaracion: item.juntaAclaracion,
              fechaJA: item.fechaJa,
              licitacionPublica: item.licitacionPublica,
              fechaLP: item.fechaLp,
              comentario: item.comentario,
            };
          }),
          eliminar: isEmpty(dataTableRemoved)
            ? []
            : dataTableRemoved.map((item) => parseInt(item.identifier, 10)),
        };
        await putData(API_URL, data);
      } catch (err) {
        if (
          err?.response?.message?.includes(
            "Favor de ingresar los datos obligatorios marcados con un asterisco (*)."
          )
        ) {
          showMessage(err.response.message);
        } else {
          showMessage(MESSAGES.MSG002);
        }
        isError = true;
      }
    } else {
      showMessage(MESSAGES.MSG007);
    }
    if (isError === false) {
      dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));

      const providers = await onGetProviders();

      const dataArray = providers.map((item, index) => ({
        index: index + 1,
        identifier: item.id ? item.id.toString() : "",
        idProyecto,
        requiredFields: [
          {
            requiredField: "idProveedor",
            helperText: "",
          },
        ],
        idProveedor: item.idProveedor ? item.idProveedor.toString() : "",
        idInvestigacionMercado: item.idInvestigacionMercado
          ? item.idInvestigacionMercado.toString()
          : "",
        fechaIm: item.fechaIm,
        respuestaIm: item.respuestaIm ? item.respuestaIm.toString() : "",
        fechaRespuestaIm: item.fechaRespuestaIm,
        juntaAclaracion: item.juntaAclaracion
          ? item.juntaAclaracion.toString()
          : "",
        fechaJa: item.fechaJa,
        licitacionPublica: item.licitacionPublica
          ? item.licitacionPublica.toString()
          : "",
        fechaLp: item.fechaLp,
        comentario: item.comentario,
        isEditable: false,
      }));

      originalDataTable = cloneDeep([...dataArray]);
      setDataTableRemoved([]);
      setIsActiveAddProvider(
        (prevIsActiveAddProvider) => !prevIsActiveAddProvider
      );
      setHasActiveUseStateRender(true);

      setDataTable(cloneDeep([...dataArray]));
      tableReference !== null &&
        tableReference.current.table.options.meta.revertData([...dataArray]);
      setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    }
    setLoading(false);
    showMessage(MESSAGES.MSG001);
  };
  const handleDownloadExcel = async () => {
    setLoading(true);
    try {
      let reportResponse = await downloadDocument(
        `/proyectos/proveedores-asignados/reporte/proyecto/${idProyecto}`
      );
      downloadExcelBlob(reportResponse.data, "Proveedores participantes");
    } catch (err) {
      showMessage(MESSAGES.MSG004);
    }

    setLoading(false);
  };

  const onClickEditToggle = async (
    isVisibleButtonsSection,
    row,
    tableRef,
    isActiveLoader = true
  ) => {
    if (isActiveLoader) setLoading(true);
    setTable(tableRef);
    const proyectoData = await trigger(idProyecto);
    if (isVisibleButtonsSection) {
      setOriginalRow([...originalRow, { ...row }]);
    } else {
      setOriginalRow([]);
    }
    setIsActiveEditableProvider(isVisibleButtonsSection);
    setIsActiveAddProvider(isVisibleButtonsSection);
    await setDataTable((prevDataTable) => {
      const array = prevDataTable.map((item) => {
        const itemnew = { ...item };
        if (itemnew.identifier === row.identifier) {
          itemnew.comentario = row.comentario;
          itemnew.isEditable = isVisibleButtonsSection;
          if (
            proyectoData.data.tipoProcedimiento !== null &&
            (proyectoData.data.tipoProcedimiento.primaryKey == 4 ||
              proyectoData.data.tipoProcedimiento.primaryKey == 2)
          ) {
            itemnew.juntaAclaracion =
              itemnew.juntaAclaracion === ""
                ? idSelectDefault
                : itemnew.juntaAclaracion;
            itemnew.licitacionPublica =
              itemnew.licitacionPublica === ""
                ? idSelectDefault
                : itemnew.licitacionPublica;
          }
          return itemnew;
        }
        return itemnew;
      });
      tableRef.options.meta.revertData(array);
      return array;
    });
    setLoading(false);
  };

  useEffect(() => {
    if (hasActiveUseStateRender === true) {
      setHasActiveUseStateRender(false);
      setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    }
  }, [hasActiveUseStateRender, dataTable]);

  const onClickRevert = async (isVisibleButtonsSection, row) => {
    const revertEditedItems = (editedArray, originalArray, identifier) => {
      return editedArray.map((editedItem) => {
        const originalItem = originalArray.find(
          (original) =>
            original.identifier === identifier &&
            editedItem.identifier === identifier
        );
        const selectedItem = originalItem ? originalItem : editedItem;
        return selectedItem;
      });
    };

    await setDataTable((prevDataTable) => {
      const array = revertEditedItems(
        prevDataTable,
        originalRow,
        row.identifier
      );
      return array;
    });
    setHasActiveUseStateRender(true);
  };

  const CustomHeader = ({ title, tooltipMessage }) => (
    <Tooltip placement="top" text={tooltipMessage}>
      <span>{title}</span>
    </Tooltip>
  );

  const onEmptyActiveProviders = () => setIsEptyProvidersModal(true);

  const onUpdateComment = (tableRef) => {
    setIsActiveAddProvider(true);
    setTable(tableRef);
  };

  const columns = React.useMemo(
    () => [
      {
        accessorKey: "index",
        header: "Id",
        filterFn: "includesString",
      },
      {
        accessorKey: "idProveedor",
        header: "Proveedor ",
        filterFn: handleProveedorFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            type={"select"}
            rowClassName="col-provider"
            isRequired={true}
            options={catalogoProveedor}
            isEditable={props.row.getIsSelected()}
            column={props.column}
            row={props.row}
            table={props.table}
            isActiveAddProvider={isActiveAddProvider}
            onEmptyActiveProviders={onEmptyActiveProviders}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
          />
        ),
      },
      {
        accessorKey: "idInvestigacionMercado",
        header: "Investigación mercado ",
        filterFn: handleIdIMercadoFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            type={"select"}
            rowClassName="col-provider"
            options={catalogoIMercado}
            column={props.column}
            isEditable={props.row.getIsSelected()}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
          />
        ),
      },
      {
        accessorKey: "fechaIm",
        header: (props) => (
          <CustomHeader
            {...props}
            title="Fecha IM "
            tooltipMessage="Fecha de investigación de mercado"
          />
        ),
        filterFn: handleDateFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            type={"date"}
            isEditable={props.row.getIsSelected()}
            column={props.column}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
            validationKey="idInvestigacionMercado"
            options={catalogoIMercado}
          />
        ),
      },
      {
        accessorKey: "respuestaIm",
        header: (props) => (
          <CustomHeader
            {...props}
            title="Respuesta IM "
            tooltipMessage="Respuesta de investigación de mercado"
          />
        ),
        filterFn: handleIdRespuestaIMFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            column={props.column}
            type={"select"}
            rowClassName="col-provider"
            isEditable={props.row.getIsSelected()}
            options={catalogoRespuestaIM}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
          />
        ),
      },
      {
        accessorKey: "fechaRespuestaIm",
        header: (props) => (
          <CustomHeader
            {...props}
            title="Fecha respuesta IM "
            tooltipMessage="Fecha respuesta de investigación de mercado"
          />
        ),
        filterFn: handleDateFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            column={props.column}
            isEditable={props.row.getIsSelected()}
            type={"date"}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
            validationKey="respuestaIm"
            options={catalogoRespuestaIM}
          />
        ),
      },
      {
        accessorKey: "juntaAclaracion",
        header: "Junta de aclaraciones ",
        filterFn: handleIdJuntaAclaracionFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            tipoProcedimiento={tipoProcedimiento}
            column={props.column}
            isEditable={props.row.getIsSelected()}
            type={"select"}
            rowClassName="col-provider"
            options={catalogoJuntaAclaracion}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
          />
        ),
      },
      {
        accessorKey: "fechaJa",
        header: (props) => (
          <CustomHeader
            {...props}
            title="Fecha JA "
            tooltipMessage="Fecha Junta de Aclaraciones"
          />
        ),
        filterFn: handleDateFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            column={props.column}
            isEditable={props.row.getIsSelected()}
            type={"date"}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
            validationKey="juntaAclaracion"
            options={catalogoJuntaAclaracion}
          />
        ),
      },
      {
        accessorKey: "licitacionPublica",
        header: (props) => (
          <CustomHeader
            {...props}
            title="Prop Eco en Procedimiento "
            tooltipMessage="Propuesta Economica en Procedimiento"
          />
        ),
        filterFn: handleIdLicitacionPublicaFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            tipoProcedimiento={tipoProcedimiento}
            column={props.column}
            type={"select"}
            rowClassName="col-provider"
            isEditable={props.row.getIsSelected()}
            options={catalogoLicitacionPublica}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
          />
        ),
      },
      {
        accessorKey: "fechaLp",
        header: (props) => (
          <CustomHeader
            {...props}
            title="Fecha PEP "
            tooltipMessage="Fecha de Propuesta Economica en Procedimiento"
          />
        ),
        filterFn: handleDateFiltro,
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            column={props.column}
            isEditable={props.row.getIsSelected()}
            type={"date"}
            row={props.row}
            table={props.table}
            onChange={(tableRef) => {
              setTable(tableRef);
            }}
            validationKey="licitacionPublica"
            options={catalogoLicitacionPublica}
          />
        ),
      },
      {
        accessorKey: "comentario",
        header: "Comentario ",
        enableSorting: false,
        enableColumnFilter: false,
        cell: (props) => (
          <CommentCell
            editable={editable}
            processType="2"
            process={"PROY_PP_ADMIN"}
            onUpdateComment={onUpdateComment}
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            originalRow={originalRow}
          />
        ),
      },
      {
        accessorKey: "acciones",
        header: "Acciones ",
        enableSorting: false,
        enableColumnFilter: false,
        cell: (props) => (
          <>
            <Authorization process={"PROY_PP_ADMIN"}>
              <ToggleCell
                editable={editable}
                row={props.row}
                onClickEditToggle={onClickEditToggle}
                onClickRevert={onClickRevert}
                isActiveAddProvider={isActiveAddProvider}
                isActiveEditableProvider={isActiveEditableProvider}
                column={props.column}
                table={props.table}
                onRemoveProvider={onRemoveProvider}
              />
            </Authorization>
            <Authorization process={"PROY_PP_ADMIN"}>
              <DropCell
                editable={editable}
                getValue={props.getValue}
                column={props.column}
                row={props.row}
                table={props.table}
                onRemoveProvider={onRemoveProvider}
              />
            </Authorization>
          </>
        ),
      },
    ],
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [isActiveAddProvider, originalRow, hasReRenderTable, onUpdateComment]
  );

  useEffect(() => {
    // Se forzará a no abrir ya que cambió el caso de uso
    if (onValidateEmptyProviders(dataTable)) setIsEptyProvidersModal(false);
  }, [dataTable]);

  return (
    <>
      {loading && <Loader />}
      <BotonesAccionesTop
        type="2"
        process="PROY_PP_ADMIN"
        handleAddProvider={handleAddProvider}
        disabledAddProviderButton={isActiveEditableProvider}
        handleDownloadExcel={handleDownloadExcel}
        disabledExcelButton={isEmpty(dataTable)}
        editable={editable}
      />
      <SingleBasicModal
        handleApprove={() => setIsOpenCancelModal(false)}
        handleDeny={() => setIsOpenCancelModal(false)}
        approveText={"Aceptar"}
        show={isOpenCancelModal}
        title={"Mensaje"}
        onHide={() => setIsOpenCancelModal(false)}
      >
        {messageModal}
      </SingleBasicModal>
      <SingleBasicModal
        handleApprove={handleApproveEmptyProviders}
        handleDeny={() => setIsEptyProvidersModal(false)}
        approveText={"Aceptar"}
        show={isEptyProvidersModal}
        title={"Mensaje"}
        onHide={() => setIsEptyProvidersModal(false)}
      >
        {MESSAGES.MSG005}
      </SingleBasicModal>
      <TableComponent
        tableReference={tableReference}
        dataTable={dataTable}
        columns={columns}
        providerTableTitle={providerTableTitle}
        setDataTable={(data, prop) => {
          if (isActiveAddProvider && prop !== "comentario") setDataTable(data);
        }}
      />
      <BotonesAcciones
        editable={editable}
        isVisible={isActiveAddProvider}
        handleFetchProvider={handleFetchProvider}
        handleCancelAddProviders={handleCancelAddProviders}
      />
    </>
  );
};
