import React, { useState, useEffect, useRef, useContext, useMemo } from "react";
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
  estatusVolumetria: "",
  estatus: "",
  idDominioTecnologico: "",
  periodoFin: "",
  periodoInicio: "",
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
// NO OLVIDES EL FA11 que es mistrar mensajes de error de las fechas del periodo
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

export function EstadoFinanciero() {
  const tableReference = useRef();
  const [estatusCatalog, setEstatusCatalog] = useState([]);
  const [isVisibleTable, setIsVisibleTable] = useState(false);

  const [isActiveFacturas, setIsActiveFacturas] = useState(false);
  const [isActiveDeducciones, setIsActiveDeducciones] = useState(false);
  const [isActivePenalizaciones, setIsActivePenalizaciones] = useState(false);
  const [isActiveReintegros, setIsActiveReintegros] = useState(false);

  const [isChangeContrato, setIsChangeContrato] = useState(false);

  const [dominioTecnologicoCatalog, setDominioTecnologicoCatalog] = useState(
    []
  );

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
      "/reportes/financiero/catalogo/estado-financiero/nombre-corto-proyecto"
    );
    if (isEmpty(nombreProyectoCortoResponse) === false) {
      setNombreCortoProyectoCatalog([...nombreProyectoCortoResponse]);
    } else {
      showMessage(MSG001);
    }

    const contratoVigenteCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/estado-financiero/contrato-vigente"
    );
    if (isEmpty(contratoVigenteCatalogResponse) === false)
      setContratoVigenteCatalog([...contratoVigenteCatalogResponse]);

    const estatusCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/estado-financiero/estatus-proyecto"
    );
    console.log("oejdoiejdoije ", estatusCatalogResponse);
    if (isEmpty(estatusCatalogResponse) === false)
      setEstatusCatalog([...estatusCatalogResponse]);

    const dominiosTecnologicosCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/estado-financiero/dominios-tecnologicos"
    );
    console.log("oejdoiejdoije ", dominiosTecnologicosCatalogResponse);
    if (isEmpty(dominiosTecnologicosCatalogResponse) === false)
      setDominioTecnologicoCatalog([...dominiosTecnologicosCatalogResponse]);

    const convenioColaboracionCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/estado-financiero/convenio-colaboracion"
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
        periodoFin,
        periodoInicio,
      } = values;

      const data = {
        periodoInicio:
          periodoInicio === ""
            ? ""
            : moment(periodoInicio, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),
        periodoFin:
          periodoFin === ""
            ? ""
            : moment(periodoFin, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),
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

        pageNumber: pageable.pageNumber,
        pageSize: pageable.size,
      };
      const reportResponse = await downloadDocumentPost(
        "/reportes/financiero/estado-financiero/reporte-export",
        data
      );
      await downloadExcelBlob(reportResponse.data,
        "reporte_financiero_estado_financiero"
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
      periodoFin,
      periodoInicio,
    } = values;

    const data = {
      periodoInicio:
        periodoInicio === ""
          ? ""
          : moment(periodoInicio, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),
      periodoFin:
        periodoFin === ""
          ? ""
          : moment(periodoFin, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss"),
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

      pageNumber: pageableArg.pageNumber,
      pageSize: pageableArg.size,
    };
    const response = await onPostService(
      "/reportes/financiero/estado-financiero/reporte",
      data,
      MSG002
    );

    console.log("eoij3eioj3ioje ", response.content);

    const { content } = response;
    if (response !== null && isEmpty(content) === false) {
      const array = content.map((item) => {
        const {
          dictamenCc,
          dictamenSat,
          dominio,
          moneda,
          montoDolares,
          montoDolaresDictaminado,
          montoDolaresEstimado,
          montoPesos,
          montoPesosDictaminado,
          montoPesosEstimado,
          nombreCortoContrato,
          nombreCortoProyecto,
          numeroContrato,
          pagadoCc,
          pagadoSat,
          periodoControl,
          proveedor,
          tipoCambioReal,
          tipoCambioReferencialDictaminado,
          tipoCambioReferencialEstimado,
          vigente,
        } = item;

        let pageableObj = {
          totalPages: response.totalPages,
          totalElements: response.totalElements,
          pageNumber: response.number,
          size: response.size,
        };
        setPageable({ ...pageableObj });
        return {
          dictamenCc,
          dictamenSat,
          dominio,
          moneda,
          montoDolares,
          montoDolaresDictaminado,
          montoDolaresEstimado,
          montoPesos,
          montoPesosDictaminado,
          montoPesosEstimado,
          nombreCortoContrato,
          nombreCortoProyecto,
          numeroContrato,
          pagadoCc,
          pagadoSat,
          periodoControl,
          proveedor,
          tipoCambioReal,
          tipoCambioReferencialDictaminado,
          tipoCambioReferencialEstimado,
          vigente,
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
    setIsSearched(false);
    setPageable(PAGEABLE);
    onGetInitialData();
    setDataTable([]);
  };

  const CustomCell = ({ row, column, width }) => {
    const value = row.original[column.id];
    return <p style={{ width }}>{value}</p>;
  };

  const onGetColumns = () => {
    const subCols = [
      {
        header: " ",
        accessorKey: "1",
        enableSorting: false,
        enableColumnFilter: false,
        columns: [
          {
            header: "Nombre corto del proyecto",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "nombreCortoProyecto",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Nombre corto del contrato",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "nombreCortoContrato",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Dominio",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "dominio",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Proveedor",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "proveedor",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Vigente",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "vigente",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Número del contrato",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "numeroContrato",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Periodo de control",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "periodoControl",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            header: "Moneda",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "moneda",
            cell: (props) => <p>{props.getValue()}</p>,
          },
        ],
      },
      {
        header: "Estimado",
        columns: [
          {
            accessorKey: "tipoCambioReferencialEstimado",
            header: "Tipo de cambio referencial",
            enableSorting: true,
            enableColumnFilter: true,
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
            accessorKey: "montoDolaresEstimado",
            header: "Monto en dólares",
            enableSorting: true,
            enableColumnFilter: true,
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
            accessorKey: "montoPesosEstimado",
            header: "Monto en pesos",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),
          },
        ],
      },
      {
        header: "Dictamen",
        columns: [
          {
            accessorKey: "tipoCambioReferencialDictaminado",
            header: "Tipo de cambio referencial",
            enableSorting: true,
            enableColumnFilter: true,
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
            accessorKey: "montoDolaresDictaminado",
            header: "Monto en dolares",
            enableSorting: true,
            enableColumnFilter: true,
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
            accessorKey: "montoPesosDictaminado",
            header: "Monto en pesos",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),
          },
        ],
      },
      {
        header: "Dictamen pagado",
        columns: [
          {
            accessorKey: "tipoCambioReal",
            header: "Tipo de cambio real",
            enableSorting: true,
            enableColumnFilter: true,
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
            accessorKey: "montoDolares",
            header: "Monto en dólares",
            enableSorting: true,
            enableColumnFilter: true,
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
            accessorKey: "montoPesos",
            header: "Monto en pesos",
            enableSorting: true,
            enableColumnFilter: true,
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),
          },
        ],
      },
      {
        header: " ",
        accessorKey: "3",
        enableSorting: false,
        enableColumnFilter: false,
        columns: [
          {
            header: "Dictamen SAT",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "dictamenSat",
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),          },
        ],
      },
    ];
    if (formRef.current.values.convenioColaboracion == 1) {
      subCols.push({
        header: " ",
        accessorKey: "5",
        enableSorting: false,
        enableColumnFilter: false,
        columns: [
          {
            header: "Dictamen CC",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "dictamenCc",
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),
          },
        ],
      });

    }

    subCols.push(
      {
        header: " ",
        accessorKey: "7",
        enableSorting: false,
        enableColumnFilter: false,
        columns: [
          {
            header: "Pagado SAT",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "pagadoSat",
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),          },
        ],
      },
    )

    if (formRef.current.values.convenioColaboracion == 1) {


      subCols.push({
        header: " ",
        accessorKey: "8",
        enableSorting: false,
        enableColumnFilter: false,
        columns: [
          {
            header: "Pagado CC",
            columns: [],
            enableSorting: true,
            enableColumnFilter: true,
            accessorKey: "pagadoCc",
            cell: (props) => (
              <CustomCell
                column={props.column}
                getValue={props.getValue}
                row={props.row}
                width={"130px"}
              />
            ),
          },
        ],
      });
    }

    let columns = [
      {
        header: "Detalle general",
        columns: subCols,
      },
    ];
    return [...columns];
  };

  // Definición de columnas con sub-columnas y headers
  const columns = formRef.current ? onGetColumns() : [];

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
                        if (e.target.value !== "") {
                          setLoading(true);
                          formRef.current.setFieldValue("idProyecto", "");
                          const nombreProyectoCortoResponse =
                            await onGetService(
                              `/reportes/financiero/catalogo/estado-financiero/nombre-corto-proyecto/${e.target.value}`
                            );
                          formRef.current.setFieldValue(
                            "nombreCortoProyecto",
                            ""
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
                          const { contratoVigente, idDominioTecnologico } =
                            values;
                          const data = {
                            idProyecto: e.target.value,
                            idContratoVigente: contratoVigente,
                            idDominioTecnologico: idDominioTecnologico,
                          };
                          const response = await onPostService(
                            "/reportes/financiero/catalogo/estado-financiero/nombre-corto-contrato",
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
                          const { nombreCortoProyecto, idDominioTecnologico } =
                            values;
                          if (nombreCortoProyecto !== "") {
                            const data = {
                              idProyecto: nombreCortoProyecto,
                              idContratoVigente: e.target.value,
                              idDominioTecnologico: idDominioTecnologico,
                            };
                            const response = await onPostService(
                              "/reportes/financiero/catalogo/estado-financiero/nombre-corto-contrato",
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
                          const { nombreCortoProyecto, contratoVigente } =
                            values;
                          if (nombreCortoProyecto !== "") {
                            const data = {
                              idProyecto: nombreCortoProyecto,
                              idContratoVigente: contratoVigente,
                              idDominioTecnologico: e.target.value,
                            };
                            const response = await onPostService(
                              "/reportes/financiero/catalogo/estado-financiero/nombre-corto-contrato",
                              data
                            );
                            if (isEmpty(response) === false)
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
                </Row>

                <Row>
                  <Col md={3}>
                    <TextFieldDate
                      label={"Periodo de inicio:"}
                      name="periodoInicio"
                      disabled={false}
                      value={values.periodoInicio}
                      onChange={(e) => {
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
                  <Col md={12} className="text-end mb-2">
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={onClear}
                    >
                      Limpiar
                    </Button>
                    <Authorization process={"REP_RESFIN_EST_FIN_CONS"}>
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
                  <Authorization process={"REP_RESFIN_EST_FIN_CONS"}>
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
                        header="Estado financiero"
                        columns={columns}
                        dataTable={dataTable}
                        manualPagination
                        pageable={pageable}
                        hasPagination
                        colSpanSubCols={20}
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
