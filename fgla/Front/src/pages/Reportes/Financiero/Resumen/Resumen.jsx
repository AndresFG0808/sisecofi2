import React, { useState, useEffect, useRef, useContext, useMemo } from "react";
import { Col, Container, Row, Button, Form } from "react-bootstrap";
import { Accordion } from "../../../../components";
import { FormCheck, Select } from "../../../../components/formInputs";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Formik } from "formik";
import Loader from "../../../../components/Loader";
import * as yup from "yup";
import { Tooltip } from "../../../../components/Tooltip";
import MainTitle from "../../../../components/MainTitle";
import LabelValue from "../../../../components/LabelValue";
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
import { createColumnHelper } from "@tanstack/react-table";

const VALORES_INICIALES = {
  nombreCortoProyecto: "",
  estatus: "",
  contratoVigente: "1",
  nombreCortoContrato: "",
  idDominioTecnologico: "",
  convenioColaboracion: "",
  idProyecto: "",
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

export function Resumen() {
  const tableReference = useRef();
  const [isActiveDatosGenerales, setIsActiveDatosGenerales] = useState(true);
  const [isActiveDetalleFinanciero, setIsActiveDetalleFinanciero] =
    useState(false);
  const [isActiveDetalleCM, setIsActiveDetalleCM] = useState(false);
  const [isSearched, setIsSearched] = useState(false);
  const [isDisableConvenioColaboracion, setIsDisableConvenioColaboracion] =
    useState(false);

  const [idContratoState, setIdContratoState] = useState("");

  const [pageable, setPageable] = useState(PAGEABLE);
  const [pageableCM, setPageableCM] = useState(PAGEABLE);

  const [dataTable, setDataTable] = useState([]);
  const [dataTableConvenios, setDataTableConvenios] = useState([]);

  const [nombreCortoProyectoCatalog, setNombreCortoProyectoCatalog] = useState(
    []
  );
  const [estatusCatalog, setEstatusCatalog] = useState([]);
  const [nombreCortoContratoCatalog, setNombreCortoContratoCatalog] = useState(
    []
  );
  const [contratoVigenteCatalog, setContratoVigenteCatalog] = useState([]);
  const [dominioTecnologicoCatalog, setDominioTecnologicoCatalog] = useState(
    []
  );
  const [convenioColaboracionCatalog, setConvenioColaboracionCatalog] =
    useState([]);

  const [loading, setLoading] = useState(false);

  const [isVisibleTable, setIsVisibleTable] = useState(false);

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
      "/reportes/financiero/catalogo/resumen/nombre-corto-proyecto"
    );
    if (isEmpty(nombreProyectoCortoResponse) === false) {
      setNombreCortoProyectoCatalog([...nombreProyectoCortoResponse]);
    } else {
      showMessage(MSG001);
    }

    const estatusCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/resumen/estatus-proyecto"
    );
    if (isEmpty(estatusCatalogResponse) === false)
      setEstatusCatalog([...estatusCatalogResponse]);

    const contratoVigenteCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/resumen/contrato-vigente"
    );
    if (isEmpty(contratoVigenteCatalogResponse) === false)
      setContratoVigenteCatalog([...contratoVigenteCatalogResponse]);

    const dominiosTecnologicosCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/resumen/dominios-tecnologicos"
    );
    if (isEmpty(dominiosTecnologicosCatalogResponse) === false)
      setDominioTecnologicoCatalog([...dominiosTecnologicosCatalogResponse]);

    const convenioColaboracionCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/resumen/convenio-colaboracion"
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
        estatus,
        nombreCortoContrato,
        idDominioTecnologico,
        convenioColaboracion,
      } = values;
      const data = {
        idEstatusProyecto: estatus === "" ? estatus : parseInt(estatus, 10),
        idDominiosTecnologicos:
          idDominioTecnologico === ""
            ? idDominioTecnologico
            : parseInt(idDominioTecnologico, 10),
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
        incluirDatosGenerales: true,
        incluirDetalleFinanciero: true,
        incluirDetalleCM: true,
        pageNumber: pageable.pageNumber,
        pageSize: pageable.size,
      };
      const reportResponse = await downloadDocumentPost(
        "/reportes/financiero/resumen/reporte-export",
        data
      );
      await downloadExcelBlob(
        reportResponse.data,
        "reporte_financiero_resumen"
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

  const handleDownloadExcelConvenio = async (values) => {
    try {
      setLoading(true);
      const data = {
        idContrato: idContratoState,
        pageNumber: pageableCM.pageNumber,
        pageSize: pageableCM.size,
      };
      const reportResponse = await downloadDocumentPost(
        "/reportes/financiero/resumen/reporte-detalle-cm-export",
        data
      );
      await downloadExcelBlob(
        reportResponse.data,
        "reporte_financiero_detalle_CM"
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

  const onSearchResumen = async (values, pageableArg = pageable) => {
    setLoading(true);
    const {
      nombreCortoProyecto,
      contratoVigente,
      estatus,
      nombreCortoContrato,
      idDominioTecnologico,
      convenioColaboracion,
    } = values;
    console.log("doeijdoiejodij ",

      nombreCortoProyectoCatalog,

      nombreCortoProyecto


    )
    const data = {
      idEstatusProyecto: estatus === "" ? estatus : parseInt(estatus, 10),
      idDominiosTecnologicos:
        idDominioTecnologico === ""
          ? idDominioTecnologico
          : parseInt(idDominioTecnologico, 10),
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
      incluirDatosGenerales: true,
      incluirDetalleFinanciero: true,
      incluirDetalleCM: true,
      pageNumber: pageableArg.pageNumber,
      pageSize: pageableArg.size,
    };
    const response = await onPostService(
      "/reportes/financiero/resumen/reporte",
      data,
      MSG002
    );
    if (response !== null && isEmpty(response.content) === false) {
      const { content } = response;
      if (isEmpty(content)) {
        showMessage(MSG004);
      }
      const array = content.map((item) => {
        const {
          nombreCortoProyecto,
          nombreCortoContrato,
          detalleGeneral,
          detalleFinanciero,
        } = item;
        const {
          idProyectoAgp,
          estatusProyecto,
          idContrato,
          numeroContrato,
          nombreContrato,
          objetivoServicio,
          alcanceServicio,
          contratoVigente,
          dominio,
          tipoContratacion,
          proveedor,
          fechaInicioContrato,
          fechaFinContrato,
          fechaFinUltimoCm,
          minimoContratadoConImpuestosMxn,
          minimoContratadoConImpuestos,
          maximoContratadoConImpuestosMxn,
          maximoContratadoConImpuestos,
          maximoCmConImpuestos,
          maximoCmConImpuestosMxn,
          minimoContratadoSinImpuestosMxn,
          maximoContratadoSinImpuestosMxn,
          maximoCmSinImpuestosMxn,
          moneda,
          tipoCambio,
          administradorContrato,
          conveniosModificatorios,
          mesesRestantesContrato,
          ultimaEstimacion,
          ultimoDictamen,
          ultimoPago,
        } = detalleGeneral;

        const {
          devengadoAntesDeduccionesSat,
          desgloceMapper,
          pagadoAntesDeduccionesSat,
          deduccionesSat,
          ivaSat,
          iepsSat,
          penalizacionesSat,
          reintegroSat,
          pagadoSat,
          otrosImpuestosSat,
        } = detalleFinanciero;

        const desgloceMapperObj = desgloceMapper === null ? {} : desgloceMapper;

        const {
          devengadoAntesDeduccionesCc,
          pagadoAntesDeduccionesCc,
          deduccionesCc,
          ivaCc,
          iepsCc,
          otrosImpuestosCc,
          penalizacionesCc,
          reintegroCc,
          pagadoCc,
        } = desgloceMapperObj;

        const { totalPages, totalElements, size } = response;

        let pageableObj = {
          totalPages,
          totalElements,
          pageNumber: response.number,
          size,
        };
        setPageable({ ...pageableObj });

        return {
          pagadoSat,
          pagadoCc,
          reintegroCc,
          reintegroSat,
          penalizacionesCc,
          otrosImpuestosCc,
          otrosImpuestosSat,
          penalizacionesSat,
          iepsCc,
          iepsSat,
          ivaSat,
          ivaCc,
          devengadoAntesDeduccionesSat,
          devengadoAntesDeduccionesCc,
          pagadoAntesDeduccionesSat,
          deduccionesSat,
          pagadoAntesDeduccionesCc,
          deduccionesCc,

          nombreCortoProyecto: nombreCortoProyecto,
          nombreCortoContrato: nombreCortoContrato,
          idProyectoAGP: idProyectoAgp,
          estatusProyecto: estatusProyecto,
          idContrato: idContrato,
          numeroContrato: numeroContrato,
          nombreContrato: nombreContrato,
          objetivoServicio: objetivoServicio,
          alcanceServicio: alcanceServicio,
          contratoVigente: contratoVigente,
          dominio: dominio,
          tipoContratacion: tipoContratacion,
          proveedor: proveedor,
          fechaInicioContrato: fechaInicioContrato,
          fechaFinContrato: fechaFinContrato,

          fechaFinUltimoCm: fechaFinUltimoCm,
          minimoContratadoConImpuestosMxn,
          minimoContratadoConImpuestos,
          maximoContratadoConImpuestosMxn: maximoContratadoConImpuestosMxn,
          maximoContratadoConImpuestos,
          maximoCmConImpuestos,
          maximoCmConImpuestosMxn,
          minimoContratadoSinImpuestosMxn,
          maximoContratadoSinImpuestosMxn,
          maximoCmSinImpuestosMxn,
          moneda,
          tipoCambio,
          administradorContrato,
          conveniosModificatorios,
          mesesRestantesContrato,
          ultimaEstimacion,
          ultimoDictamen,
          ultimoPago,
        };
      });

      setDataTable([...array]);
    } else {
      setDataTable([]);
      showMessage(MSG004);
    }

    setIsSearched(true);
    setLoading(false);
  };

  const onClear = () => {
    setIsVisibleTable(false);
    setIdContratoState("");
    handleResetForm();
    setNombreCortoProyectoCatalog([]);
    setEstatusCatalog([]);
    setContratoVigenteCatalog([]);
    setNombreCortoContratoCatalog([]);
    setDominioTecnologicoCatalog([]);
    setConvenioColaboracionCatalog([]);
    setIsActiveDatosGenerales(true);
    setIsActiveDetalleFinanciero(false);
    setIsActiveDetalleCM(false);
    setIsSearched(false);
    setPageable(PAGEABLE);
    setPageableCM(PAGEABLE);
    onGetInitialData();
    setDataTable([]);
  };

  const onSearchCM = async (idContrato, pageableArg = pageableCM) => {
    setLoading(true);
    const response = await onPostService(
      "/reportes/financiero/resumen/reporte-detalle-cm",
      {
        idContrato: idContrato,
        pageNumber: pageableArg.pageNumber,
        pageSize: pageableArg.size,
      },
      MSG002
    );

    const { content } = response;

    let pageableObj = {
      totalPages: response.totalPages,
      totalElements: response.totalElements,
      pageNumber: response.number,
      size: response.size,
    };
    setPageableCM({ ...pageableObj });

    if (isEmpty(content) === false) {
      setDataTableConvenios(content);
    } else {
      setDataTableConvenios([]);
    }

    if (isEmpty(content)) showMessage(MSG004);
    setIsSearched(true);
    setLoading(false);
  };

  const CustomCell = ({ onClick, row, column, width = "auto" }) => {
    const value = row.original[column.id];
    return (
      <p
        style={isActiveDetalleCM ? { cursor: "pointer", width } : { width }}
        onClick={() => {
          onClick(row.original.idContrato);
        }}
      >
        {value}
      </p>
    );
  };

  const columnHelper = createColumnHelper();

  const onGetCM = async (idContrato) => {
    setIdContratoState(idContrato);
    isActiveDetalleCM && onSearchCM(idContrato);
  };

  // Helper para columnas

  // Definición de columnas con sub-columnas y headers
  const columns = [
    columnHelper.group({
      id: "-",
      header: "",
      enableSorting: false,
      enableColumnFilter: false,
      columns: [
        columnHelper.accessor("nombreCortoProyecto", {
          accessorKey: "nombreCortoProyecto",
          header: "Nombre corto del proyecto",
          enableSorting: true,
          enableColumnFilter: true,
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
            />
          ),
        }),
        columnHelper.accessor("nombreCortoContrato", {
          header: "Nombre corto del contrato",
          accessorKey: "nombreCortoContrato",
          enableSorting: true,
          enableColumnFilter: true,
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
            />
          ),
        }),
      ],
    }),
  ];

  if (isActiveDatosGenerales) {
    columns.push(
      columnHelper.group({
        id: "datosGenerales",
        header: "Datos generales",
        enableSorting: false,
        enableColumnFilter: false,
        columns: [
          columnHelper.group({
            id: "idProyectoAGP",
            accessorKey: "idProyectoAGP",
            header: "Id proyecto AGP",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                width="100px"
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "estatusProyecto",
            header: "Estatus del proyecto",
            accessorKey: "estatusProyecto",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "idContrato",
            header: "Id contrato",
            accessorKey: "idContrato",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "numeroContrato",
            header: "Número del contrato",
            accessorKey: "numeroContrato",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "nombreContrato",
            accessorKey: "nombreContrato",
            header: "Nombre del contrato",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "objetivoServicio",
            header: "Objetivo del servicio",
            accessorKey: "objetivoServicio",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "alcanceServicio",
            header: "Alcance del servicio",
            accessorKey: "alcanceServicio",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            accessorKey: "contratoVigente",
            id: "contratoVigente",
            header: "Contrato vigente",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "dominio",
            header: "Dominio",
            accessorKey: "dominio",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "tipoContratacion",
            header: "Tipo de contratacion",
            accessorKey: "tipoContratacion",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "proveedor",
            header: "Proveedor",
            accessorKey: "Proveedor",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "fechaInicioContrato",
            header: "Fecha inicio del contrato",
            accessorKey: "fechaInicioContrato",
            width: "100px",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width="100px"
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "fechaFinContrato",
            header: "Fecha fin del contrato",
            accessorKey: "fechaFinContrato",
            width: "100px",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="100px"
              />
            ),
          }),
          columnHelper.group({
            id: "fechaFinUltimoCm",
            header: "Fecha fin del último CM",
            accessorKey: "fechaFinUltimoCm",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                width="100px"
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "minimoContratadoConImpuestosMxn",
            header: "Mínimo contratado c/ impuestos MXN",
            accessorKey: "minimoContratadoConImpuestosMxn",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "maximoContratadoConImpuestosMxn",
            header: "Máximo contratado c/ impuestos MXN",
            accessorKey: "maximoContratadoConImpuestosMxn",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "maximoCmConImpuestosMxn",
            header: "Máximo CM c/ impuestos MXN",
            accessorKey: "maximoCmConImpuestosMxn",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "minimoContratadoSinImpuestosMxn",
            header: "Mínimo contratado s/ impuestos MXN",
            accessorKey: "minimoContratadoSinImpuestosMxn",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "maximoContratadoSinImpuestosMxn",
            header: "Máximo contratado s/ impuestos MXN",
            accessorKey: "maximoContratadoSinImpuestosMxn",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "maximoCmSinImpuestosMxn",
            header: "Máximo CM s/ impuestos MXN",
            accessorKey: "maximoCmSinImpuestosMxn",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "moneda",
            header: "Moneda",
            accessorKey: "moneda",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "tipoCambio",
            header: "Tipo de cambio",
            accessorKey: "tipoCambio",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "minimoContratadoConImpuestos",
            header: "Mínimo contratado c/ impuestos",
            accessorKey: "minimoContratadoConImpuestos",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "maximoContratadoConImpuestos",
            header: "Máximo contratado c/ impuestos",
            accessorKey: "maximoContratadoConImpuestos",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "maximoCmConImpuestos",
            header: "Máximo CM c/ impuestos",
            accessorKey: "maximoCmConImpuestos",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="130px"
              />
            ),
          }),
          columnHelper.group({
            id: "administradorContrato",
            header: "Administrador del contrato",
            accessorKey: "administradorContrato",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "conveniosModificatorios",
            header: "Convenios Modificatorios",
            accessorKey: "conveniosModificatorios",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "mesesRestantesContrato",
            header: "Meses restantes del contrato",
            accessorKey: "mesesRestantesContrato",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
              />
            ),
          }),
          columnHelper.group({
            id: "ultimaEstimacion",
            header: "Última estimación",
            accessorKey: "ultimaEstimacion",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="100px"
              />
            ),
          }),
          columnHelper.group({
            id: "ultimoDictamen",
            header: "Último dictamen",
            accessorKey: "ultimoDictamen",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="100px"
              />
            ),
          }),
          columnHelper.group({
            id: "ultimoPago",
            header: "Último pago",
            accessorKey: "ultimoPago",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                onClick={onGetCM}
                width="100px"
              />
            ),
          }),
        ],
      })
    );
  }
  if (isActiveDetalleFinanciero) {
    let subCols = [
      columnHelper.group({
        id: "devengadoAntesDeduccionesSat",
        header: "Devengado antes de deducciones SAT",
        accessorKey: "devengadoAntesDeduccionesSat",
        enableSorting: true,
        enableColumnFilter: true,
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
      }),
    ];

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "devengadoAntesDeduccionesCc",
          header: "Devengado antes de deducciones CC",
          accessorKey: "devengadoAntesDeduccionesCc",
          enableSorting: true,
          enableColumnFilter: true,
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "pagadoAntesDeduccionesSat",
        header: "Pagado antes de deducciones SAT",
        accessorKey: "pagadoAntesDeduccionesSat",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "pagadoAntesDeduccionesCc",
          header: "Pagado antes de deducciones CC",
          accessorKey: "pagadoAntesDeduccionesCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "deduccionesSat",
        header: "Deducciones SAT",
        accessorKey: "deduccionesSat",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "deduccionesCc",
          header: "Deducciones CC",
          accessorKey: "deduccionesCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "ivaSat",
        header: "IVA SAT",
        accessorKey: "ivaSat",
        enableSorting: true,
        enableColumnFilter: true,
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "ivaCc",
          header: "IVA CC",
          accessorKey: "ivaCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "iepsSat",
        header: "IEPS SAT",
        accessorKey: "iepsSat",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "iepsCc",
          header: "IEPS CC",
          accessorKey: "iepsCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "otrosImpuestosSat",
        header: "Otros impuestos SAT",
        accessorKey: "otrosImpuestosSat",
        enableSorting: true,
        enableColumnFilter: true,
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "otrosImpuestosCc",
          header: "Otros impuestos CC",
          accessorKey: "otrosImpuestosCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "penalizacionesSat",
        header: "Penalizaciones SAT",
        accessorKey: "penalizacionesSat",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "penalizacionesCc",
          header: "Penalizaciones CC",
          accessorKey: "penalizacionesCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "reintegroSat",
        header: "Reintegro SAT",
        accessorKey: "reintegroSat",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "reintegroCc",
          header: "Reintegro CC",
          accessorKey: "reintegroCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    subCols.push(
      columnHelper.group({
        id: "pagadoSat",
        header: "Pagado SAT",
        accessorKey: "pagadoSat",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            onClick={onGetCM}
            width="130px"
          />
        ),
        enableSorting: true,
        enableColumnFilter: true,
      })
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push(
        columnHelper.group({
          id: "pagadoCc",
          header: "Pagado CC",
          accessorKey: "pagadoCc",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              onClick={onGetCM}
              width="130px"
            />
          ),
          enableSorting: true,
          enableColumnFilter: true,
        })
      );
    }

    columns.push(
      columnHelper.group({
        id: "detalleFinanciero",
        header: "Detalle financiero",
        enableSorting: false,
        enableColumnFilter: false,
        columns: subCols,
      })
    );
  }

  const columnsConvenio = [
    columnHelper.accessor("numeroConvenio", {
      accessorKey: "numeroConvenio",
      header: "Número de convenio",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("tipoConvenio", {
      accessorKey: "tipoConvenio",
      header: "Tipo de convenio",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("fechaFirma", {
      accessorKey: "fechaFirma",
      header: "Fecha de firma",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("fechaFinServicio", {
      accessorKey: "fechaFinServicio",
      header: "Fecha fin de servicio",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("fechaFinContratoCm", {
      accessorKey: "fechaFinContratoCm",
      header: "Fecha fin del contrato con CM",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("montoMaximoContratoCmConImpuestos", {
      accessorKey: "montoMaximoContratoCmConImpuestos",
      header: "Monto máximo del contrato c/ CM con impuestos",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("montoMaximoContratoCmSinImpuestos", {
      accessorKey: "montoMaximoContratoCmSinImpuestos",
      header: "Monto máximo del contrato c/ CM sin impuestos",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("montoPesos", {
      accessorKey: "montoPesos",
      header: "Monto en pesos",
      enableSorting: true,
      enableColumnFilter: true,
    }),
    columnHelper.accessor("comentarios", {
      accessorKey: "comentarios",
      header: "Comentarios",
      enableSorting: false,
      enableColumnFilter: false,
    }),
  ];

  const onGetColsSpan = () => {
    let colsSpan = 2;
    if (isActiveDatosGenerales) {
      colsSpan = colsSpan + 30;
    }
    if (isActiveDetalleFinanciero) {
      colsSpan = colsSpan + 17;
    }
    return colsSpan;
  };

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
                          const { idDominioTecnologico, contratoVigente } = values;
                          const data = {
                            idProyecto: e.target.value,
                            idContratoVigente: contratoVigente,
                            idDominioTecnologico: idDominioTecnologico,
                          };
                          const response = await onPostService(
                            "/reportes/financiero/catalogo/resumen/nombre-corto-contrato",
                            data
                          );
                          if (isEmpty(response) === false) {
                            setNombreCortoContratoCatalog([...response]);
                          } else {
                            setNombreCortoContratoCatalog([]);
                          }
                          formRef.current.setFieldValue("nombreCortoContrato", "");
                        } else {
                          setNombreCortoContratoCatalog([]);
                          formRef.current.setFieldValue("nombreCortoContrato", "");
                        }
                      }}
                      className={""}
                      helperText={""}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                  <Col md={3}>
                    <Select
                      label={"Estatus:"}
                      disabled={false}
                      name={"estatus"}
                      options={estatusCatalog}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      readOnly={false}
                      value={values.estatus}
                      onChange={async (e) => {
                        handleChange(e);
                        formRef.current.setFieldValue("idProyecto", "");
                        setNombreCortoContratoCatalog([]);
                        formRef.current.setFieldValue("nombreCortoContrato", "");
                        if (e.target.value !== "") {
                          setLoading(true);
                          const nombreProyectoCortoResponse =
                            await onGetService(
                              `/reportes/financiero/catalogo/resumen/nombre-corto-proyecto/${e.target.value}`
                            );
                          setNombreCortoProyectoCatalog([
                            ...nombreProyectoCortoResponse,
                          ]);
                          setLoading(false);
                        } else {
                          formRef.current.setFieldValue(
                            "nombreCortoProyecto",
                            ""
                          );
                          setNombreCortoProyectoCatalog([]);
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
                          const { nombreCortoProyecto, idDominioTecnologico } = values;
                          if (nombreCortoProyecto !== "") {
                            const data = {
                              idProyecto: nombreCortoProyecto,
                              idContratoVigente: e.target.value,
                              idDominioTecnologico: idDominioTecnologico,
                            };
                            const response = await onPostService(
                              "/reportes/financiero/catalogo/resumen/nombre-corto-contrato",
                              data
                            );
                            if (isEmpty(response) === false) {
                              setNombreCortoContratoCatalog([...response]);
                            } else {
                              setNombreCortoContratoCatalog([]);
                            }
                            formRef.current.setFieldValue("nombreCortoContrato", "");
                          } else {
                            setNombreCortoContratoCatalog([]);
                            formRef.current.setFieldValue("nombreCortoContrato", "");
                          }
                        } else {
                          setNombreCortoContratoCatalog([]);
                          formRef.current.setFieldValue("nombreCortoContrato", "");
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
                        onActiveConvenio(
                          e.target.value,
                          nombreCortoContratoCatalog
                        );
                      }}
                      className={""}
                      helperText={""}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                  <Col md={3}>
                    <Select
                      label={"Dominio tecnológico:"}
                      disabled={false}
                      name={"idDominioTecnologico"}
                      options={dominioTecnologicoCatalog}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      readOnly={false}
                      value={values.idDominioTecnologico}
                      onChange={async (e) => {
                        handleChange(e);
                        if (e.target.value !== "") {
                          const { nombreCortoProyecto, contratoVigente } = values;
                          if (nombreCortoProyecto !== "") {
                            const data = {
                              idProyecto: nombreCortoProyecto,
                              idContratoVigente: contratoVigente,
                              idDominioTecnologico: e.target.value,
                            };
                            const response = await onPostService(
                              "/reportes/financiero/catalogo/resumen/nombre-corto-contrato",
                              data
                            );
                            if (isEmpty(response) === false) {
                              setNombreCortoContratoCatalog([...response]);
                            } else {
                              setNombreCortoContratoCatalog([]);
                            }
                            formRef.current.setFieldValue("nombreCortoContrato", "");
                          } else {
                            setNombreCortoContratoCatalog([]);
                            formRef.current.setFieldValue("nombreCortoContrato", "");
                          }
                        } else {
                          setNombreCortoContratoCatalog([]);
                          formRef.current.setFieldValue("nombreCortoContrato", "");
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
                  <Col md={9}>
                    <Row>
                      <LabelValue label="Incluir:" />
                    </Row>
                    <Row>
                      <Col md={12} className="d-flex">
                        <p>Datos generales&nbsp;</p>
                        <FormCheck
                          value={isActiveDatosGenerales}
                          onChange={() => {
                            setIsActiveDatosGenerales(!isActiveDatosGenerales);
                          }}
                          type={"checkbox"}
                        />

                        <p>&nbsp;&nbsp;&nbsp;&nbsp;Detalle financiero&nbsp;</p>
                        <FormCheck
                          value={isActiveDetalleFinanciero}
                          type={"checkbox"}
                          onChange={() => {
                            setIsActiveDetalleFinanciero(
                              !isActiveDetalleFinanciero
                            );
                          }}
                        />
                        <Tooltip
                          placement="top"
                          text={
                            "Muestra detalle de todos los convenios modificatorios del contrato."
                          }
                        >
                          <span style={{ display: "flex" }}>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;Detalle CM&nbsp;</p>
                            <FormCheck
                              value={isActiveDetalleCM}
                              type={"checkbox"}
                              onChange={() => {
                                setIdContratoState("");
                                setIsActiveDetalleCM(!isActiveDetalleCM);
                              }}
                            />
                          </span>
                        </Tooltip>
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
                    <Authorization process={"REP_RESFIN_RES_CONS"}>
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
                    <Authorization process={"REP_RESFIN_RES_CONS"}>
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
                          header="Resumen"
                          columns={columns}
                          dataTable={dataTable}
                          manualPagination
                          pageable={pageable}
                          colSpanSubCols={onGetColsSpan()}
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

                <br></br>
                <br></br>
                {isActiveDetalleCM && idContratoState !== "" && (
                  <>
                    <Row>
                      <Col md="12" className="text-end mb-2">
                        {isSearched &&
                          isEmpty(dataTableConvenios) === false && (
                            <IconButton
                              type="excel"
                              onClick={() =>
                                handleDownloadExcelConvenio(values)
                              }
                              disabled={false}
                              tooltip={"Exportar a Excel"}
                            />
                          )}
                      </Col>
                    </Row>
                    <Row>
                      <Col md={12}>
                        <TablaEditable
                          header="Detalle convenio modificatorio"
                          columns={columnsConvenio}
                          dataTable={dataTableConvenios}
                          manualPagination
                          pageable={pageableCM}
                          hasPagination
                          onChangePagination={(pageValues) => {
                            onSearchCM(idContratoState, {
                              size: pageValues.size,
                              pageNumber: pageValues.page,
                            });
                          }}
                          isSubCols={true}
                        />
                      </Col>
                    </Row>
                  </>
                )}
              </Form>
            )}
          </Formik>
        </Accordion>
      </Container>
    </>
  );
}
