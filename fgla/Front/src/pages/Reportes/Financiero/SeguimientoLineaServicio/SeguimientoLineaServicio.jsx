import React, { useState, useEffect, useRef, useMemo } from "react";
import { Col, Container, Row, Button, Form } from "react-bootstrap";
import { Accordion } from "../../../../components";
import { FormCheck, Select } from "../../../../components/formInputs";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Formik } from "formik";
import Loader from "../../../../components/Loader";
import * as yup from "yup";
import MainTitle from "../../../../components/MainTitle";
import LabelValue from "../../../../components/LabelValue";
import SelectMultiple from "../../../../components/formInputs/SelectMultiple";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import Authorization from "../../../../components/Authorization";
import {
  postData,
  getData,
  downloadDocumentPost,
} from "../../../../functions/api";
import showMessage from "../../../../components/Messages";
import { isEmpty } from "lodash";
import IconButton from "../../../../components/buttons/IconButton";
import { downloadExcelBlob } from "../../../../functions/utils";
import moment from "moment";

const VALORES_INICIALES = {
  nombreCortoProyecto: "",
  contratoVigente: "1",
  nombreCortoContrato: "",
  convenioColaboracion: "",
  estatusVolumetria: [1],
  conceptoServicio: [],
  periodoFin: "",
  periodoInicio: "",
};
const selectPlaceholder = "Seleccione una opción";
const esquema = yup.object({
  // nombreCortoProyecto: yup.string().required("Dato requerido"),
});

const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};

const MSG001 =
  "El usuario no tiene proyectos asignados. Favor de validar con el administrador del sistema.";
const MSG002 =
  "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).";
const MSG003 =
  "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).";
const MSG004 = "No se encontraron resultados de la búsqueda.";
const MSG005 =
  "El periodo de inicio seleccionado no es correcto. Por favor verifique los datos.";
const MSG006 =
  "El periodo de fin seleccionado no es correcto. Por favor verifique los datos.";
const MSG007 = "Procesando…";

export function SeguimientoLineaServicio() {
  const tableReference = useRef();

  const [isActiveFacturas, setIsActiveFacturas] = useState(false);
  const [isActiveDeducciones, setIsActiveDeducciones] = useState(false);
  const [isActivePenalizaciones, setIsActivePenalizaciones] = useState(false);
  const [isActiveReintegros, setIsActiveReintegros] = useState(false);

  const [isVisibleTable, setIsVisibleTable] = useState(false);


  const [isChangingCols, setIsChangingCols] = useState(false);
  const [isSelectedFirstDate, setIsSelectedFirstDate] = useState(false);
  const [isSelectedSecondDate, setIsSelectedSecondDate] = useState(false);

  const [detalleMensualCols, setDetalleMensualCols] = useState([]);

  const [isChangeContrato, setIsChangeContrato] = useState(false);

  const [estatusVolumetriaCatalog, setEstatusVolumetriaCatalog] = useState([]);

  const [isActiveMonto, setIsActiveMonto] = useState(true);
  const [isActiveVolumetria, setIsActiveVolumetria] = useState(true);

  const [isSearched, setIsSearched] = useState(false);
  const [isDisableConvenioColaboracion, setIsDisableConvenioColaboracion] =
    useState(false);

  const [pageable, setPageable] = useState(PAGEABLE);

  const [dataTable, setDataTable] = useState([]);

  const [nombreCortoProyectoCatalog, setNombreCortoProyectoCatalog] = useState(
    []
  );
  const [nombreCortoContratoCatalog, setNombreCortoContratoCatalog] = useState(
    []
  );
  const [contratoVigenteCatalog, setContratoVigenteCatalog] = useState([]);

  const [conceptoServicioCatalog, setConceptoServicioCatalog] = useState([]);

  const [convenioColaboracionCatalog, setConvenioColaboracionCatalog] =
    useState([]);

  const [loading, setLoading] = useState(false);
  const [valoresIniciales, setValoresIniciales] = useState({
    ...VALORES_INICIALES,
  });

  const formRef = useRef();
  const handleResetForm = () => {
    if (formRef.current) {
      formRef.current.resetForm(); // Resetea el formulario utilizando la referencia
    }
  };

  useEffect(() => {
    onGetInitialData();
  }, []);

  const onActiveConvenio = (
    nombreCortoContrato,
    nombreCortoContratoCatalog
  ) => {
    if (
      isEmpty(nombreCortoContratoCatalog) === false &&
      nombreCortoContrato !== "" &&
      nombreCortoContrato !== null
    ) {
      formRef.current.setFieldValue("convenioColaboracion", "");
      setIsDisableConvenioColaboracion(
        nombreCortoContratoCatalog.filter(
          (item) => item.idContrato === parseInt(nombreCortoContrato, 10)
        )[0].convenioColaboracion
      );
    } else if (isDisableConvenioColaboracion) {
      formRef.current.setFieldValue("convenioColaboracion", "");
      setIsDisableConvenioColaboracion(false);
    }
    return null;
  };

  const onGetInitialData = async () => {
    setLoading(true);
    const nombreProyectoCortoResponse = await onGetService(
      "/reportes/financiero/catalogo/seguimiento-linea-servicio/nombre-corto-proyecto"
    );
    if (isEmpty(nombreProyectoCortoResponse) === false) {
      setNombreCortoProyectoCatalog([...nombreProyectoCortoResponse]);
    } else {
      showMessage(MSG001);
    }

    const contratoVigenteCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/seguimiento-linea-servicio/contrato-vigente"
    );
    if (isEmpty(contratoVigenteCatalogResponse) === false)
      setContratoVigenteCatalog([...contratoVigenteCatalogResponse]);

    const estatusVolumetriaCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/seguimiento-linea-servicio/estatus-volumetria"
    );
    if (isEmpty(estatusVolumetriaCatalogResponse) === false)
      setEstatusVolumetriaCatalog([...estatusVolumetriaCatalogResponse]);

    const convenioColaboracionCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/seguimiento-linea-servicio/convenio-colaboracion"
    );
    if (isEmpty(convenioColaboracionCatalogResponse) === false)
      setConvenioColaboracionCatalog([...convenioColaboracionCatalogResponse]);
    setLoading(false);
    return null;
  };

  const handleDownloadExcel = async (values) => {
    try {
      setLoading(true);
      const {
        nombreCortoProyecto,
        contratoVigente,
        nombreCortoContrato,
        convenioColaboracion,
        periodoInicio,
        periodoFin,
        estatusVolumetria,
        conceptoServicio,
      } = values;
      const data = {
        listaIdEstatusVolumetria: estatusVolumetria,
        listaIdServicioContrato: conceptoServicio === '' ? [] : conceptoServicio,
        volumetria: true,
        monto: true,
        periodoInicio:
          periodoInicio === ""
            ? ""
            : moment(periodoInicio, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),
        periodoFin:
          periodoFin === ""
            ? ""
            : moment(periodoFin, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),

        idContratoVigente:
          contratoVigente === ""
            ? contratoVigente
            : parseInt(contratoVigente, 10),
        idConvenioColaboracion:
          convenioColaboracion === ""
            ? convenioColaboracion
            : parseInt(convenioColaboracion, 10),
        nombreCortoProyecto:
          nombreCortoProyecto === ""
            ? nombreCortoProyecto
            : nombreCortoProyectoCatalog.filter(
              (item) => item.idProyecto == nombreCortoProyecto
            )[0].nombreCorto,
        nombreCortoContrato:
          nombreCortoContrato === ""
            ? nombreCortoContrato
            : nombreCortoContratoCatalog.filter(
              (item) => item.idContrato == nombreCortoContrato
            )[0].nombreCorto,

        pageNumber: pageable.pageNumber,
        pageSize: pageable.size,
      };
      const reportResponse = await downloadDocumentPost(
        "/reportes/financiero/seguimiento-linea-servicio/reporte-export",
        data
      );
      await downloadExcelBlob(
        reportResponse.data,
        "reporte_financiero_seguimiento_linea_servicio"
      );
    } catch (err) {
      let errorMessage =
        err?.response?.data !== "" && err?.response?.data?.mensaje[0];
      let errorIdDuplicado = errorMessage === MSG003;
      if (errorIdDuplicado && err?.response?.status !== 403) {
        showMessage(errorMessage);
      } else if (err?.response?.status !== 403) {
        showMessage(MSG003);
      }
    }
    setLoading(false);
  };

  const CustomCell = ({ row, column, width }) => {
    const value = row.original[column.id];
    return <p style={{ width }}>{value}</p>;
  };

  const CustomNumberCell = ({ row, column, width }) => {
    const value = row.original[column.id];
    return (
      <p style={{ width }}>
        {typeof value === "number"
          ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
          : value}
      </p>
    );
  };

  const onPostService = async (url, data, errorMssage = MSG002) => {
    try {
      const response = await postData(url, data);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(errorMssage);
      } else {
        let errorMessage =
          err?.response?.data !== "" && err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === "MSG011";
        if (errorIdDuplicado && err?.response?.status !== 403) {
          showMessage(errorMessage);
        } else if (err?.response?.status !== 403) {
          showMessage(errorMssage);
        }
      }
      return null;
    }
  };

  const onGetService = async (url) => {
    try {
      const response = await getData(url);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(MSG002);
      } else {
        let errorMessage =
          err?.response?.data !== "" && err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === "MSG011";
        if (errorIdDuplicado && err?.response?.status !== 403) {
          showMessage(errorMessage);
        } else if (err?.response?.status !== 403) {
          showMessage(MSG002);
        }
      }
      return null;
    }
  };

  const onGetMonthTag = (month) => month;

  const onSearchResumen = async (
    values,
    pageableArg = pageable,
    isActiveMontoArg = isActiveMonto,
    isActiveVolumetriaArg = isActiveVolumetria
  ) => {
    setLoading(true);
    const {
      nombreCortoProyecto,
      contratoVigente,
      nombreCortoContrato,
      convenioColaboracion,
      periodoInicio,
      periodoFin,
      estatusVolumetria,
      conceptoServicio,
    } = values;
    const data = {
      listaIdEstatusVolumetria: estatusVolumetria,
      listaIdServicioContrato: conceptoServicio === '' ? [] : conceptoServicio,
      volumetria: true,
      monto: true,
      periodoInicio:
        periodoInicio === ""
          ? ""
          : moment(periodoInicio, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),
      periodoFin:
        periodoFin === ""
          ? ""
          : moment(periodoFin, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),

      idContratoVigente:
        contratoVigente === ""
          ? contratoVigente
          : parseInt(contratoVigente, 10),
      idConvenioColaboracion:
        convenioColaboracion === ""
          ? convenioColaboracion
          : parseInt(convenioColaboracion, 10),
      nombreCortoProyecto:
        nombreCortoProyecto === ""
          ? nombreCortoProyecto
          : nombreCortoProyectoCatalog.filter(
            (item) => item.idProyecto == nombreCortoProyecto
          )[0].nombreCorto,
      nombreCortoContrato:
        nombreCortoContrato === ""
          ? nombreCortoContrato
          : nombreCortoContratoCatalog.filter(
            (item) => item.idContrato == nombreCortoContrato
          )[0].nombreCorto,

      pageNumber: pageableArg.pageNumber,
      pageSize: pageableArg.size,
    };
    const response = await onPostService(
      "/reportes/financiero/seguimiento-linea-servicio/reporte",
      data,
      MSG002
    );

    if (response !== null && isEmpty(response.content) === false) {
      const { content } = response;
      if (isEmpty(content)) {
        return showMessage(MSG004);
      }
      const array = content.map((item) => {
        const { detalleGeneral, estimada, planeada, dictaminada, pagada } =
          item;

        const estimadaObj = {};
        for (let index in estimada) {
          estimadaObj[
            `cantidadServiciosEstimada${onGetMonthTag(estimada[index].mes)}${estimada[index].anio
            }`
          ] = estimada[index].cantidadServicios;
          estimadaObj[
            `montoEstimada${onGetMonthTag(estimada[index].mes)}${estimada[index].anio
            }`
          ] = estimada[index].monto;
        }

        const planeadaObj = {};
        for (let index in planeada) {
          planeadaObj[
            `cantidadServiciosPlaneada${onGetMonthTag(planeada[index].mes)}${planeada[index].anio
            }`
          ] = planeada[index].cantidadServicios;
          planeadaObj[
            `montoPlaneada${onGetMonthTag(planeada[index].mes)}${planeada[index].anio
            }`
          ] = planeada[index].monto;
        }

        const dictaminadaObj = {};
        for (let index in dictaminada) {
          dictaminadaObj[
            `cantidadServiciosSatDictaminada${onGetMonthTag(
              dictaminada[index].mes
            )}${dictaminada[index].anio}`
          ] = dictaminada[index].cantidadServiciosSat;
          dictaminadaObj[
            `montoSatDictaminada${onGetMonthTag(dictaminada[index].mes)}${dictaminada[index].anio
            }`
          ] = dictaminada[index].montoSat;

          dictaminadaObj[
            `cantidadServiciosCcDictaminada${onGetMonthTag(
              dictaminada[index].mes
            )}${dictaminada[index].anio}`
          ] = dictaminada[index].cantidadServiciosCc;
          dictaminadaObj[
            `montoCcDictaminada${onGetMonthTag(dictaminada[index].mes)}${dictaminada[index].anio
            }`
          ] = dictaminada[index].montoCc;
        }

        const pagadaObj = {};
        for (let index in pagada) {
          pagadaObj[
            `cantidadServiciosSatPagada${onGetMonthTag(pagada[index].mes)}${pagada[index].anio
            }`
          ] = pagada[index].cantidadServiciosSat;
          pagadaObj[
            `montoSatPagada${onGetMonthTag(pagada[index].mes)}${pagada[index].anio
            }`
          ] = pagada[index].montoSat;

          pagadaObj[
            `cantidadServiciosCcPagada${onGetMonthTag(pagada[index].mes)}${pagada[index].anio
            }`
          ] = pagada[index].cantidadServiciosCc;
          pagadaObj[
            `montoCcPagada${onGetMonthTag(pagada[index].mes)}${pagada[index].anio
            }`
          ] = pagada[index].montoCc;
        }

        let pageableObj = {
          totalPages: response.totalPages,
          totalElements: response.totalElements,
          pageNumber: response.number,
          size: response.size,
        };
        setPageable({ ...pageableObj });
        let colsArray = [];
        if (
          isEmpty(estatusVolumetria) === false &&
          isSelectedFirstDate &&
          isSelectedSecondDate
        ) {
          if (
            isEmpty(estatusVolumetria.filter((item) => item === 2)) === false ||
            isEmpty(estatusVolumetria.filter((item) => item === 1)) === false
          ) {
            const subArray = response.content[0].planeada.map((element) => {
              const subCols = [];

              if (isActiveVolumetriaArg) {
                subCols.push({
                  accessorKey: `cantidadServiciosPlaneada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Cantidad de servicios de ${onGetMonthTag(
                    element.mes
                  )} ${element.anio}`,
                  cell: (props) => (
                    <CustomNumberCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }
              if (isActiveMontoArg) {
                subCols.push({
                  accessorKey: `montoPlaneada${onGetMonthTag(element.mes)}${element.anio
                    }`,
                  header: `Monto de ${onGetMonthTag(element.mes)} ${element.anio
                    }`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }

              return {
                accessorKey: `${onGetMonthTag(element.mes)}-${element.anio
                  }-Planeada`,
                header: `${onGetMonthTag(element.mes)} ${element.anio}`,
                enableSorting: false,
                enableColumnFilter: false,
                cell: (props) => <p>{props.getValue()}</p>,
                columns: subCols,
              };
            });
            colsArray.push({
              accessorKey: "planeada",
              header: "Planeada",
              enableSorting: false,
              enableColumnFilter: false,
              columns: subArray,
            });
          }

          if (
            isEmpty(estatusVolumetria.filter((item) => item === 3)) === false ||
            isEmpty(estatusVolumetria.filter((item) => item === 1)) === false
          ) {
            const subArray = response.content[0].estimada.map((element) => {
              const subCols = [];
              if (isActiveVolumetriaArg) {
                subCols.push({
                  accessorKey: `cantidadServiciosEstimada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Cantidad de servicios de ${onGetMonthTag(
                    element.mes
                  )} ${element.anio}`,
                  cell: (props) => (
                    <CustomNumberCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }
              if (isActiveMontoArg) {
                subCols.push({
                  accessorKey: `montoEstimada${onGetMonthTag(element.mes)}${element.anio
                    }`,
                  header: `Monto de ${onGetMonthTag(element.mes)} ${element.anio
                    }`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }

              return {
                accessorKey: `${onGetMonthTag(element.mes)}-${element.anio
                  }-Estimada`,
                header: `${onGetMonthTag(element.mes)} ${element.anio}`,
                enableSorting: false,
                enableColumnFilter: false,
                cell: (props) => <p>{props.getValue()}</p>,
                columns: subCols,
              };
            });
            colsArray.push({
              accessorKey: "estimada",
              header: "Estimada",
              enableSorting: false,
              enableColumnFilter: false,
              columns: subArray,
            });
          }

          if (
            isEmpty(estatusVolumetria.filter((item) => item === 4)) === false ||
            isEmpty(estatusVolumetria.filter((item) => item === 1)) === false
          ) {
            const subArray = response.content[0].dictaminada.map((element) => {
              const subCols = [];
              if (isActiveVolumetriaArg) {
                subCols.push({
                  accessorKey: `cantidadServiciosSatDictaminada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Cantidad de servicios SAT de ${onGetMonthTag(
                    element.mes
                  )} ${element.anio}`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }
              if (isActiveMontoArg) {
                subCols.push({
                  accessorKey: `montoSatDictaminada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Monto SAT de ${onGetMonthTag(element.mes)} ${element.anio
                    }`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }

              if (isActiveVolumetriaArg && convenioColaboracion == 1) {
                subCols.push({
                  accessorKey: `cantidadServiciosCcDictaminada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Cantidad de servicios CC de ${onGetMonthTag(
                    element.mes
                  )} ${element.anio}`,
                  cell: (props) => <p>{props.getValue()}</p>,
                });
              }
              if (isActiveMontoArg && convenioColaboracion == 1) {
                subCols.push({
                  accessorKey: `montoCcDictaminada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Monto CC de ${onGetMonthTag(element.mes)} ${element.anio
                    }`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }

              return {
                accessorKey: `${onGetMonthTag(element.mes)}-${element.anio
                  }-Dictaminada`,
                header: `${onGetMonthTag(element.mes)} ${element.anio}`,
                enableSorting: false,
                enableColumnFilter: false,
                cell: (props) => <p>{props.getValue()}</p>,
                columns: subCols,
              };
            });
            colsArray.push({
              accessorKey: "dictaminada",
              header: "Dictaminada",
              enableSorting: false,
              enableColumnFilter: false,
              columns: subArray,
            });
          }
          if (
            isEmpty(estatusVolumetria.filter((item) => item === 5)) === false ||
            isEmpty(estatusVolumetria.filter((item) => item === 1)) === false
          ) {
            const subArray = response.content[0].pagada.map((element) => {
              const subCols = [];
              if (isActiveVolumetriaArg) {
                subCols.push({
                  accessorKey: `cantidadServiciosSatPagada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Cantidad de servicios SAT de ${onGetMonthTag(
                    element.mes
                  )} ${element.anio}`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }
              if (isActiveMontoArg) {
                subCols.push({
                  accessorKey: `montoSatPagada${onGetMonthTag(element.mes)}${element.anio
                    }`,
                  header: `Monto SAT de ${onGetMonthTag(element.mes)} ${element.anio
                    }`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }

              if (isActiveVolumetriaArg && convenioColaboracion == 1) {
                subCols.push({
                  accessorKey: `cantidadServiciosCcPagada${onGetMonthTag(
                    element.mes
                  )}${element.anio}`,
                  header: `Cantidad de servicios CC de ${onGetMonthTag(
                    element.mes
                  )} ${element.anio}`,
                  cell: (props) => <p>{props.getValue()}</p>,
                });
              }
              if (isActiveMontoArg && convenioColaboracion == 1) {
                subCols.push({
                  accessorKey: `montoCcPagada${onGetMonthTag(element.mes)}${element.anio
                    }`,
                  header: `Monto CC de ${onGetMonthTag(element.mes)} ${element.anio
                    }`,
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                });
              }

              return {
                accessorKey: `${onGetMonthTag(element.mes)}-${element.anio
                  }-Pagada`,
                header: `${onGetMonthTag(element.mes)} ${element.anio}`,
                enableSorting: false,
                enableColumnFilter: false,
                cell: (props) => <p>{props.getValue()}</p>,
                columns: subCols,
              };
            });
            colsArray.push({
              accessorKey: "pagada",
              header: "Pagada",
              enableSorting: false,
              enableColumnFilter: false,
              columns: subArray,
            });
          }
        }
        setDetalleMensualCols([...colsArray]);
        return {
          ...detalleGeneral,
          ...estimadaObj,
          ...planeadaObj,
          ...dictaminadaObj,
          ...pagadaObj,
        };
      });

      setDataTable([...array]);
    } else {
      setDataTable([]);
      showMessage(MSG004);
    }
    setIsChangingCols(true);
    setIsSearched(true);
    setLoading(false);
    setIsChangingCols(false);
  };

  const onClear = () => {
    setIsVisibleTable(false);
    setIsSelectedFirstDate(false);
    setIsSelectedSecondDate(false);
    handleResetForm();
    setNombreCortoProyectoCatalog([]);
    setContratoVigenteCatalog([]);
    setNombreCortoContratoCatalog([]);
    setConvenioColaboracionCatalog([]);
    setIsActiveFacturas(false);
    setIsActiveDeducciones(false);
    setIsActivePenalizaciones(false);
    setIsActiveReintegros(false);
    setIsDisableConvenioColaboracion(false);
    setIsSearched(false);
    setPageable(PAGEABLE);
    setIsActiveMonto(true);
    setIsActiveVolumetria(true);
    setDataTable([]);
    onGetInitialData();
  };

  const onGetColumns = (detalleMensualColsArgs) => {
    let detalleGeneralCols = [];
    if (isSelectedFirstDate && isSelectedSecondDate) {
      detalleGeneralCols = [
        {
          header: " ",
          accessorKey: "1",
          enableSorting: false,
          enableColumnFilter: false,
          columns: [
            {
              header: " ",
              accessorKey: "2",
              enableSorting: false,
              enableColumnFilter: false,
              columns: [
                {
                  accessorKey: "nombreCortoProyecto",
                  header: "Nombre corto del proyecto",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "nombreCortoContrato",
                  header: "Nombre corto del contrato",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "numeroContrato",
                  header: "Número del contrato",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "estatusVolumetria",
                  header: "Estatus de volumetría",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "numeroConsecutivoConceptoServicio",
                  header: "Número consecutivo del concepto de servicio",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "grupoServicio",
                  header: "Grupo de servicio",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "conceptoServicio",
                  header: "Concepto de servicio",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "tipoConsumo",
                  header: "Tipo de consumo",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "tipoUnidad",
                  header: "Tipo de unidad",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "precioUnitario",
                  header: "Precio unitario",
                  cell: (props) => (
                    <CustomCell
                      column={props.column}
                      getValue={props.getValue}
                      row={props.row}
                      width="130px"
                    />
                  ),
                  columns: [],
                },

                {
                  accessorKey: "aplicaIva",
                  header: "Aplica IVA",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },
                {
                  accessorKey: "aplicaIeps",
                  header: "Aplica IEPS",
                  cell: (props) => <p>{props.getValue()}</p>,
                  columns: [],
                },

                {
                  accessorKey: "cantidadServiciosMinima",
                  header: "Cantidad de servicios mínima",
                  cell: (props) => {
                    const value = props.getValue();
                    return (
                      <p>
                        {typeof value === "number"
                          ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
                          : value}
                      </p>
                    );
                  },
                  columns: [],
                },
                {
                  accessorKey: "cantidadServiciosMaxima",
                  header: "Cantidad de servicios máxima",
                  cell: (props) => {
                    const value = props.getValue();
                    return (
                      <p>
                        {typeof value === "number"
                          ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
                          : value}
                      </p>
                    );
                  },
                  columns: [],
                },
                {
                  accessorKey: "cantidadServiciosMaximaUltimoCM",
                  header: "Cantidad de servicios máxima último CM",
                  cell: (props) => {
                    const value = props.getValue();
                    return (
                      <p>
                        {typeof value === "number"
                          ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
                          : value}
                      </p>
                    );
                  },
                  columns: [],
                },
              ],
            },
          ],
        },
      ];
    } else {
      detalleGeneralCols = [
        {
          accessorKey: "nombreCortoProyecto",
          header: "Nombre corto del proyecto",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "nombreCortoContrato",
          header: "Nombre corto del contrato",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "numeroContrato",
          header: "Número del contrato",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "estatusVolumetria",
          header: "Estatus de volumetría",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "numeroConsecutivoConceptoServicio",
          header: "Número consecutivo del concepto de servicio",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "grupoServicio",
          header: "Grupo de servicio",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "conceptoServicio",
          header: "Concepto de servicio",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "tipoConsumo",
          header: "Tipo de consumo",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "tipoUnidad",
          header: "Tipo de unidad",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "precioUnitario",
          header: "Precio unitario",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width="130px"
            />
          ),
          columns: [],
        },

        {
          accessorKey: "aplicaIva",
          header: "Aplica IVA",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },
        {
          accessorKey: "aplicaIeps",
          header: "Aplica IEPS",
          cell: (props) => <p>{props.getValue()}</p>,
          columns: [],
        },

        {
          accessorKey: "cantidadServiciosMinima",
          header: "Cantidad de servicios mínima",
          cell: (props) => {
            const value = props.getValue();
            return (
              <p>
                {typeof value === "number"
                  ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
                  : value}
              </p>
            );
          },
          columns: [],
        },
        {
          accessorKey: "cantidadServiciosMaxima",
          header: "Cantidad de servicios máxima",
          cell: (props) => {
            const value = props.getValue();
            return (
              <p>
                {typeof value === "number"
                  ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
                  : value}
              </p>
            );
          },
          columns: [],
        },
        {
          accessorKey: "cantidadServiciosMaximaUltimoCM",
          header: "Cantidad de servicios máxima último CM",
          cell: (props) => {
            const value = props.getValue();
            return (
              <p>
                {typeof value === "number"
                  ? value.toLocaleString("es-MX", { minimumFractionDigits: 2, maximumFractionDigits: 2 })
                  : value}
              </p>
            );
          },
          columns: [],
        },
      ];
    }
    let detalleMensualCols = [];
    if (isSelectedFirstDate && isSelectedSecondDate) {
      detalleMensualCols = detalleMensualColsArgs;
    } else {
      detalleMensualCols = [
        {
          header: " ",
          columns: detalleMensualColsArgs,
        },
      ];
    }
    let columns = [
      {
        header: "Detalle general",
        enableHiding: true, // disable hiding for this column
        columns: detalleGeneralCols,
      },
      // pato

      {
        header: "Detalle mensual del periodo",
        columns: detalleMensualCols,
      },
    ];
    return [...columns];
  };

  // Definición de columnas con sub-columnas y headers
  const columns = useMemo(
    () => onGetColumns(detalleMensualCols),
    [detalleMensualCols]
  );

  return (
    <>
      {loading && <Loader />}
      <Container>
        <MainTitle title="Reporte financiero" />
        <Accordion
          title="Criterios de búsqueda"
          collapse={false}
          showChevron={false}
        >
          <Formik
            innerRef={(f) => (formRef.current = f)}
            initialValues={valoresIniciales}
            enableReinitialize
            validationSchema={esquema}
            validateOnMount={true}
            onChange={() => { }}
            onSubmit={(values, { resetForm }) =>
              () => { }}
          >
            {({
              handleSubmit,
              handleChange,
              values,
              setFieldValue,
              setFieldTouched,
              touched,
              errors,
            }) => (
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <Row>
                  <Col md={3}>
                    <Select
                      label={"Nombre corto del proyecto:"}
                      disabled={false}
                      name={"nombreCortoProyecto"}
                      options={nombreCortoProyectoCatalog}
                      keyValue={"idProyecto"}
                      keyTextValue={"nombreCorto"}
                      readOnly={false}
                      value={values.nombreCortoProyecto}
                      onChange={async (e) => {
                        handleChange(e);
                        if (e.target.value !== "") {
                          setLoading(true);
                          const { contratoVigente } = values;
                          const data = {
                            idProyecto: e.target.value,
                            idContratoVigente: contratoVigente,
                          };
                          const response = await onPostService(
                            "/reportes/financiero/catalogo/seguimiento-linea-servicio/nombre-corto-contrato",
                            data
                          );
                          setNombreCortoContratoCatalog([...response]);
                          formRef.current.setFieldValue(
                            "nombreCortoContrato",
                            ""
                          );
                          setLoading(false);
                        } else {
                          setNombreCortoContratoCatalog([]);
                          formRef.current.setFieldValue(
                            "nombreCortoContrato",
                            ""
                          );
                        }
                      }}
                      className={""}
                      helperText={""}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={3}>
                    <Select
                      label={"Contrato vigente:"}
                      disabled={false}
                      name={"contratoVigente"}
                      options={contratoVigenteCatalog}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      readOnly={false}
                      value={values.contratoVigente}
                      onChange={async (e) => {
                        handleChange(e);
                        if (e.target.value !== "") {
                          const { nombreCortoProyecto } = values;
                          if (nombreCortoProyecto !== "") {
                            const data = {
                              idProyecto: nombreCortoProyecto,
                              idContratoVigente: e.target.value,
                            };
                            const response = await onPostService(
                              "/reportes/financiero/catalogo/seguimiento-linea-servicio/nombre-corto-contrato",
                              data
                            );
                            setNombreCortoContratoCatalog([...response]);
                            formRef.current.setFieldValue(
                              "nombreCortoContrato",
                              ""
                            );
                          }
                        } else {
                          setNombreCortoContratoCatalog([]);
                          formRef.current.setFieldValue(
                            "nombreCortoContrato",
                            ""
                          );
                        }
                      }}
                      className={""}
                      helperText={""}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                  <Col md={3}>
                    <Select
                      label={"Nombre corto del contrato:"}
                      disabled={false}
                      name={"nombreCortoContrato"}
                      options={nombreCortoContratoCatalog}
                      keyValue={"idContrato"}
                      keyTextValue={"nombreCorto"}
                      readOnly={false}
                      value={values.nombreCortoContrato}
                      onChange={async (e) => {
                        handleChange(e);
                        setIsChangeContrato(!isChangeContrato);
                        onActiveConvenio(
                          e.target.value,
                          nombreCortoContratoCatalog
                        );
                        if (e.target.value !== "") {
                          const conceptoServicioCatalogResponse =
                            await onGetService(
                              `/reportes/financiero/catalogo/seguimiento-linea-servicio/concepto-servicio/${e.target.value}`
                            );
                          if (
                            isEmpty(conceptoServicioCatalogResponse) === false
                          )
                            setConceptoServicioCatalog([
                              ...conceptoServicioCatalogResponse,
                            ]);
                          formRef.current.setFieldValue("conceptoServicio", "");
                        } else {
                          setConceptoServicioCatalog([]);
                          formRef.current.setFieldValue("conceptoServicio", "");
                        }
                      }}
                      className={""}
                      helperText={""}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                  <Col md={3}>
                    <Select
                      label={"Convenio de colaboración:"}
                      disabled={!isDisableConvenioColaboracion}
                      name={"convenioColaboracion"}
                      options={convenioColaboracionCatalog}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      readOnly={false}
                      value={values.convenioColaboracion}
                      onChange={(e) => {
                        handleChange(e);
                      }}
                      className={""}
                      helperText={""}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                </Row>

                <Row>
                  <Col md={3}>
                    <SelectMultiple
                      label="Concepto de servicio:"
                      name="conceptoServicio"
                      values={values.conceptoServicio}
                      onChange={(value) =>
                        setFieldValue("conceptoServicio", value)
                      }
                      options={conceptoServicioCatalog}
                      keyValue="idServicioContrato"
                      keyTextValue="concepto"
                    />
                  </Col>
                  <Col md={3}>
                    <TextFieldDate
                      label={"Periodo de inicio:"}
                      name="periodoInicio"
                      disabled={false}
                      value={values.periodoInicio}
                      onChange={(e) => {
                        setIsSelectedFirstDate(true);
                        handleChange(e);
                      }}
                      className={""}
                      helperText={""}
                      minDate={null}
                      maxDate={
                        values.periodoFin !== ""
                          ? moment(values.periodoFin, "YYYY/MM/DD")
                            .subtract(1, "days")
                            .format("YYYY-MM-DD")
                          : values.periodoInicio
                      }
                    />
                  </Col>
                  <Col md={3}>
                    <TextFieldDate
                      label={"Periodo fin:"}
                      name="periodoFin"
                      disabled={false}
                      value={values.periodoFin}
                      onChange={(e) => {
                        handleChange(e);
                        setIsSelectedSecondDate(true);
                      }}
                      className={""}
                      helperText={""}
                      minDate={
                        values.periodoInicio !== ""
                          ? moment(values.periodoInicio, "YYYY/MM/DD")
                            .add(1, "days")
                            .format("YYYY-MM-DD")
                          : values.periodoInicio
                      }
                      maxDate={null}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col md={3}>
                    <SelectMultiple
                      label="Estatus de volumetría"
                      name="estatusVolumetria"
                      values={values.estatusVolumetria}
                      onChange={(value) => {
                        setFieldValue("estatusVolumetria", value);
                      }}
                      options={estatusVolumetriaCatalog}
                      keyValue="primaryKey"
                      keyTextValue="nombre"
                    />
                  </Col>
                  <Col md={9}>
                    <Row>
                      <LabelValue label="Detalle mensual del periodo:" />
                    </Row>
                    <Row>
                      <Col md={12}>
                        <Row>
                          <Col md={12} className="d-flex">
                            <FormCheck
                              value={isActiveVolumetria}
                              onChange={() => {
                                const value = !isActiveVolumetria;
                                setIsActiveVolumetria(value);
                                /*
                                onSearchResumen(
                                  values,
                                  pageable,
                                  isActiveMonto,
                                  value
                                );
                                */
                              }}
                              type={"checkbox"}
                            />
                            <p>&nbsp;&nbsp;Volumetría</p>
                          </Col>
                        </Row>
                        <Row>
                          <Col md={12} className="d-flex">
                            <FormCheck
                              value={isActiveMonto}
                              type={"checkbox"}
                              onChange={() => {
                                const value = !isActiveMonto;
                                setIsActiveMonto(value);
                                /*
                                onSearchResumen(
                                  values,
                                  pageable,
                                  value,
                                  isActiveVolumetria
                                );
                                */
                              }}
                            />
                            <p>&nbsp;&nbsp;Monto&nbsp;</p>
                          </Col>
                        </Row>
                      </Col>
                    </Row>
                  </Col>
                </Row>

                <Row>
                  <Col md={12} className="text-end mb-2">
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={onClear}
                    >
                      Limpiar
                    </Button>
                    <Authorization process={"REP_RESFIN_SPLS_CONS"}>
                      <Button
                        variant="gray"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={() => {
                          setIsVisibleTable(true);
                          onSearchResumen(values);
                        }}
                      >
                        Buscar
                      </Button>
                    </Authorization>
                  </Col>
                </Row>
                <Row>
                  <Col md="12" className="text-end mb-2">
                    <Authorization process={"REP_RESFIN_SPLS_CONS"}>
                      {isSearched && isEmpty(dataTable) === false && (
                        <IconButton
                          type="excel"
                          onClick={() => handleDownloadExcel(values)}
                          disabled={false}
                          tooltip={"Exportar a Excel"}
                        />
                      )}
                    </Authorization>
                  </Col>
                </Row>
                {
                  isVisibleTable && (
                    <Row>
                      <Col md={12} className="group-columns">
                        <TablaEditable
                          header="Seguimiento por línea de servicio"
                          columns={isChangingCols ? [] : columns}
                          dataTable={dataTable}
                          manualPagination
                          pageable={pageable}
                          colSpanSubCols={60}
                          hasPagination
                          onChangePagination={(pageValues) => {
                            onSearchResumen(values, {
                              size: pageValues.size,
                              pageNumber: pageValues.page,
                            });
                          }}
                          isSubCols={true}
                        />
                      </Col>
                    </Row>
                  )
                }

              </Form>
            )}
          </Formik>
        </Accordion>
      </Container>
    </>
  );
}
