import React, { useState, useEffect, useRef } from "react";
import { Col, Container, Row, Button, Form } from "react-bootstrap";
import { Accordion } from "../../../../components";
import { FormCheck, Select } from "../../../../components/formInputs";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Formik } from "formik";
import Loader from "../../../../components/Loader";
import * as yup from "yup";
import MainTitle from "../../../../components/MainTitle";
import LabelValue from "../../../../components/LabelValue";
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
import Authorization from "../../../../components/Authorization";

const VALORES_INICIALES = {
  nombreCortoProyecto: "",
  contratoVigente: "1",
  nombreCortoContrato: "",
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

export function FacturasPenalizaciones() {
  const tableReference = useRef();
  const [isChangeContrato, setIsChangeContrato] = useState(false);

  const [isActiveFacturas, setIsActiveFacturas] = useState(false);
  const [isActiveDeducciones, setIsActiveDeducciones] = useState(false);
  const [isActivePenalizaciones, setIsActivePenalizaciones] = useState(false);
  const [isActiveReintegros, setIsActiveReintegros] = useState(false);

  const [isActiveDetalleFinanciero, setIsActiveDetalleFinanciero] =
    useState(false);
  const [isActiveDetalleCM, setIsActiveDetalleCM] = useState(false);
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

  const [isVisibleTable, setIsVisibleTable] = useState(false);
  
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
      "/reportes/financiero/catalogo/tipo/nombre-corto-proyecto"
    );
    if (isEmpty(nombreProyectoCortoResponse) === false) {
      setNombreCortoProyectoCatalog([...nombreProyectoCortoResponse]);
    } else {
      showMessage(MSG001);
    }

    const contratoVigenteCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/tipo/contrato-vigente"
    );
    if (isEmpty(contratoVigenteCatalogResponse) === false)
      setContratoVigenteCatalog([...contratoVigenteCatalogResponse]);

    const convenioColaboracionCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/resumen/convenio-colaboracion"
    );
    if (isEmpty(convenioColaboracionCatalogResponse) === false)
      setConvenioColaboracionCatalog([...convenioColaboracionCatalogResponse]);
    setIsActiveFacturas(true);
    setLoading(false);
    return null;
  };

  const onGetSelectedSections = () => {
    const arraySections = [];
    if (isActiveFacturas) arraySections.push("Facturas");
    if (isActiveDeducciones) arraySections.push("Deducciones");
    if (isActiveReintegros) arraySections.push("Reintegros");
    if (isActivePenalizaciones) arraySections.push("Penalizaciones");
    return arraySections; 
  };

  const handleDownloadExcel = async (values) => {
    try {
      setLoading(true);
      const {
        nombreCortoProyecto,
        contratoVigente,
        nombreCortoContrato,
        convenioColaboracion,
      } = values;
      const data = {
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
        tipos: onGetSelectedSections(),
      };
      const reportResponse = await downloadDocumentPost(
        "/reportes/financiero/tipo/reporte-export",
        data
      );
      await downloadExcelBlob(
        reportResponse.data,
        "reporte_financiero_Facturas_penalizaciones_deducciones_reintegros"
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
      nombreCortoContrato,
      convenioColaboracion,
    } = values;
    const data = {
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
      tipos: onGetSelectedSections(),
    };
    const response = await onPostService(
      "/reportes/financiero/tipo/reporte",
      data,
      MSG002
    );

    const { content } = response;
    if (response !== null && isEmpty(content) === false) {
      const array = content.map((item) => {
        const {
          detalleGeneral,
          detalleFactura,
          detalleDeduccion,
          detallePenalizacion,
          detalleReintegro,
        } = item;

        let pageableObj = {
          totalPages: response.totalPages,
          totalElements: response.totalElements,
          pageNumber: response.number,
          size: response.size,
        };
        setPageable({ ...pageableObj });

        return {
          ...detalleGeneral,
          ...detalleFactura,
          ...detalleReintegro,
          montoDolaresCc2: detalleDeduccion !== null && detalleDeduccion.montoDolaresCc,
          montoDolaresSat2: detalleDeduccion !== null && detalleDeduccion.montoDolaresSat,
          montoPesosCc2: detalleDeduccion !== null && detalleDeduccion.montoPesosCc,
          montoPesosSat2: detalleDeduccion !== null && detalleDeduccion.montoPesosSat,
          montoDolaresCc3: detallePenalizacion !== null && detallePenalizacion.montoDolaresCc,
          montoDolaresSat3: detallePenalizacion !== null && detallePenalizacion.montoDolaresSat,
          montoPesosCc3: detallePenalizacion !== null && detallePenalizacion.montoPesosCc,
          montoPesosSat3: detallePenalizacion !== null && detallePenalizacion.montoPesosSat,
        };
      });

      setDataTable([...array]);
    } else {
      setDataTable([]);
    }

    if (isEmpty(content)) {
      showMessage(MSG004);
    }
    setIsSearched(true);
    setLoading(false);
  };

  const onClear = () => {
    setIsVisibleTable(false);
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
    setIsActiveDetalleFinanciero(false);
    setIsActiveDetalleCM(false);
    setIsSearched(false);
    setPageable(PAGEABLE);
    onGetInitialData();
    setDataTable([]);
  };

  const CustomCell = ({ row, column, width = "auto" }) => {
    const value = row.original[column.id];
    return <p style={{ width }}>{value}</p>;
  };

  const onGetColumns = (
    isActiveFacturas,
    isActiveDeducciones,
    isActivePenalizaciones,
    isActiveReintegros,
    formRef
  ) => {
    const detalleGeneralCols = [
      {
        accessorKey: "nombreCortoProyecto",
        header: "Nombre corto del proyecto",
        cell: (props) => <p>{props.getValue()}</p>,
      },
      {
        accessorKey: "nombreCortoContrato",
        header: "Nombre corto del contrato",
        cell: (props) => <p>{props.getValue()}</p>,
      },
      {
        accessorKey: "numeroContrato",
        header: "Número del contrato",
        cell: (props) => <p>{props.getValue()}</p>,
      },
      {
        accessorKey: "proveedor",
        header: "Proveedor",
        cell: (props) => <p>{props.getValue()}</p>,
      },
    ];



    if (
        isActiveFacturas ||
        isActiveDeducciones || 
        isActivePenalizaciones
    ) {
      detalleGeneralCols.push({
        accessorKey: "periodoInicio",
        header: "Periodo de incio",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"100px"}
          />
        ),
      });
      detalleGeneralCols.push({
        accessorKey: "periodoFin",
        header: "Periodo fin",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"100px"}
          />
        ),
      });
      detalleGeneralCols.push({
        accessorKey: "periodoControl",
        header: "Periodo de control",
        cell: (props) => <p>{props.getValue()}</p>,
      });
      detalleGeneralCols.push({
        accessorKey: "descripcion",
        header: "Descripción",
        cell: (props) => <p>{props.getValue()}</p>,
        enableSorting: false,
        enableColumnFilter: false,
      });
    }
    let columns = [
      {
        header: "Detalle general",
        columns: detalleGeneralCols,
      },
    ];
    if (isActiveFacturas) {
      let subCols = [
        {
          accessorKey: "folioFactura",
          header: "Folio factura",
          cell: (props) => <p>{props.getValue()}</p>,
        },
        {
          accessorKey: "comprobanteFiscal",
          header: "Comprobante fiscal",
          cell: (props) => <p>{props.getValue()}</p>,
        },

        {
          accessorKey: "fechaFacturacion",
          header: "Fecha de facturación",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"100px"}
            />
          ),
        },
        {
          accessorKey: "fechaPago",
          header: "Fecha de pago",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"100px"}
            />
          ),
        },

        {
          accessorKey: "moneda",
          header: "Moneda",
          cell: (props) => <p>{props.getValue()}</p>,
        },
        {
          accessorKey: "tipoCambio",
          header: "Tipo de cambio",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },

        {
          accessorKey: "montoDolaresSat",
          header: "Monto en dólares SAT",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },
      ];

      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "montoDolaresCc",
          header: "Monto en dólares CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }

      subCols.push({
        accessorKey: "montoPesosSat",
        header: "Monto en pesos SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"130px"}
          />
        ),
      });

      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "montoPesosCc",
          header: "Monto en pesos CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }

      subCols.push({
        accessorKey: "otroImpuestosSat",
        header: "Otros impuestos SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"130px"}
          />
        ),
      });

      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "otroImpuestosCc",
          header: "Otros impuestos CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }

      subCols.push({
        accessorKey: "folioFichaPagoSat",
        header: "Folio de ficha de pago SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"130px"}
          />
        ),
      });

      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "folioFichaPagoCc",
          header: "Folio de ficha de pago CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }

      subCols.push({
        accessorKey: "comentarios",
        header: "Comentarios",
        cell: (props) => <p>{props.getValue()}</p>,
      });

      columns = [
        ...columns,
        ...[
          {
            header: "Detalle facturas",
            columns: subCols,
          },
        ],
      ];
    }
    if (isActiveDeducciones) {
      let subCols = [
        {
          accessorKey: "montoDolaresSat2",
          header: "Monto en dólares SAT",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },
      ];
      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "montoDolaresCc2",
          header: "Monto en dólares CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }
      subCols.push({
        accessorKey: "montoPesosSat2",
        header: "Monto en pesos SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"130px"}
          />
        ),
      });
      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "montoPesosCc2",
          header: "Monto en pesos CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }

      columns = [
        ...columns,
        ...[
          {
            header: "Detalle deducciones",
            columns: subCols,
          },
        ],
      ];
    }
    if (isActivePenalizaciones) {
      let subCols = [
        {
          accessorKey: "montoDolaresSat3",
          header: "Monto en dólares SAT",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },
      ];
      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "montoDolaresCc3",
          header: "Monto en dólares CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }
      subCols.push({
        accessorKey: "montoPesosSat3",
        header: "Monto en pesos SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"130px"}
          />
        ),
      });
      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "montoPesosCc3",
          header: "Monto en pesos CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }

      columns = [
        ...columns,
        ...[
          {
            header: "Detalle penalizaciones",
            columns: subCols,
          },
        ],
      ];
    }
    if (isActiveReintegros) {
      let subCols = [
        {
          accessorKey: "tipo",
          header: "Tipo",
          cell: (props) => <p>{props.getValue()}</p>,
        },
        {
          accessorKey: "importe",
          header: "Importe",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },

        {
          accessorKey: "intereses",
          header: "Interés",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },
        {
          accessorKey: "totalSat",
          header: "Total SAT",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        },
      ];
      if (formRef.current.values.convenioColaboracion == 1) {
        subCols.push({
          accessorKey: "totalCc",
          header: "Total CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width={"130px"}
            />
          ),
        });
      }
      subCols.push({
        accessorKey: "fechaReintegro",
        header: "Fecha de reintegro",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width={"100px"}
          />
        ),
      });

      columns = [
        ...columns,
        ...[
          {
            header: "Detalle reintegros",
            columns: subCols,
          },
        ],
      ];
    }
    return [...columns];
  };

  // Definición de columnas con sub-columnas y headers
  const columns = onGetColumns(
    isActiveFacturas,
    isActiveDeducciones,
    isActivePenalizaciones,
    isActiveReintegros,
    formRef
  );

  const onGetColsSpan = () => {
    let colsSpan = 8;
    if (isActiveFacturas) {
      colsSpan = colsSpan + 15;
    }
    if (isActiveDeducciones) {
      colsSpan = colsSpan + 4;
    }
    if (isActivePenalizaciones) {
      colsSpan = colsSpan + 4;
    }
    if (isActiveReintegros) {
      colsSpan = colsSpan + 6;
    }
    return colsSpan;

    /*
    <FormCheck
    value={isActiveFacturas}
    onChange={() => {
      setIsActiveFacturas(!isActiveFacturas);
    }}
    type={"checkbox"}
  />
  <p>&nbsp;&nbsp;&nbsp;&nbsp;Deducciones&nbsp;</p>
  <FormCheck
    value={isActiveDeducciones}
    type={"checkbox"}
    onChange={() => {
      setIsActiveDeducciones(!isActiveDeducciones);
    }}
  />
  <p>&nbsp;&nbsp;&nbsp;&nbsp;Penalizaciones&nbsp;</p>
  <FormCheck
    value={isActivePenalizaciones}
    type={"checkbox"}
    onChange={() => {
      setIsActivePenalizaciones(!isActivePenalizaciones);
    }}
  />
  <p>&nbsp;&nbsp;&nbsp;&nbsp;Reintegros&nbsp;</p>
  <FormCheck
    value={isActiveReintegros}
    type={"checkbox"}
    onChange={() => {
      setIsActiveReintegros(!isActiveReintegros);
    }}
*/
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
                          const { contratoVigente } = values;
                          const data = {
                            idProyecto: e.target.value,
                            idContratoVigente: contratoVigente,
                          };
                          const response = await onPostService(
                            "/reportes/financiero/catalogo/tipo/nombre-corto-contrato",
                            data
                          );
                          setNombreCortoContratoCatalog([...response]);
                          formRef.current.setFieldValue(
                            "nombreCortoContrato",
                            ""
                          );
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
                              "/reportes/financiero/catalogo/tipo/nombre-corto-contrato",
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
                  <Col md={12}>
                    <Row>
                      <LabelValue label="Tipo:" />
                    </Row>
                    <Row>
                      <Col md={12} className="d-flex">
                        <p>Facturas&nbsp;</p>
                        <FormCheck
                          value={isActiveFacturas}
                          onChange={() => {
                            setIsActiveFacturas(!isActiveFacturas);
                          }}
                          type={"checkbox"}
                        />
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;Deducciones&nbsp;</p>
                        <FormCheck
                          value={isActiveDeducciones}
                          type={"checkbox"}
                          onChange={() => {
                            setIsActiveDeducciones(!isActiveDeducciones);
                          }}
                        />
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;Penalizaciones&nbsp;</p>
                        <FormCheck
                          value={isActivePenalizaciones}
                          type={"checkbox"}
                          onChange={() => {
                            setIsActivePenalizaciones(!isActivePenalizaciones);
                          }}
                        />
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;Reintegros&nbsp;</p>
                        <FormCheck
                          value={isActiveReintegros}
                          type={"checkbox"}
                          onChange={() => {
                            setIsActiveReintegros(!isActiveReintegros);
                          }}
                        />
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
                    <Authorization process={"REP_RESFIN_FPDR_CONS"}>
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
                    <Authorization process={"REP_RESFIN_FPDR_CONS"}>
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
                        header="Facturas/penalizaciones/deducciones/reintegros"
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
              </Form>
            )}
          </Formik>
        </Accordion>
      </Container>
    </>
  );
}
