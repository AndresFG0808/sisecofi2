import React, { useState, useEffect, useRef, useContext, useMemo } from "react";
import { Col, Container, Row, Button, Form } from "react-bootstrap";
import { Accordion } from "../../../../components";
import { FormCheck, Select } from "../../../../components/formInputs";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Formik } from "formik";
import Loader from "../../../../components/Loader";
import * as yup from "yup";
import MainTitle from "../../../../components/MainTitle";
import Authorization from "../../../../components/Authorization";
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

const VALORES_INICIALES = {
  nombreCortoProyecto: "",
  contratoVigente: "1",
  nombreCortoContrato: "",
  convenioColaboracion: "",
  idProyecto: '',
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

export function EstimadoPagado() {
  const tableReference = useRef();

    const [isVisibleTable, setIsVisibleTable] = useState(false);
  

  const [isActiveFacturas, setIsActiveFacturas] = useState(false);
  const [isActiveDeducciones, setIsActiveDeducciones] = useState(false);
  const [isActivePenalizaciones, setIsActivePenalizaciones] = useState(false);
  const [isActiveReintegros, setIsActiveReintegros] = useState(false);

  const [isChangeContrato, setIsChangeContrato] = useState(false);

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
      "/reportes/financiero/catalogo/estimado-pagado/nombre-corto-proyecto"
    );
    if (isEmpty(nombreProyectoCortoResponse) === false) {
      setNombreCortoProyectoCatalog([...nombreProyectoCortoResponse]);
    } else {
      showMessage(MSG001);
    }

    const contratoVigenteCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/estimado-pagado/contrato-vigente"
    );
    if (isEmpty(contratoVigenteCatalogResponse) === false)
      setContratoVigenteCatalog([...contratoVigenteCatalogResponse]);

    const convenioColaboracionCatalogResponse = await onGetService(
      "/reportes/financiero/catalogo/estimado-pagado/convenio-colaboracion"
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
      };
      const reportResponse = await downloadDocumentPost(
        "/reportes/financiero/estimado-pagado/reporte-export",
        data
      );
      await downloadExcelBlob(
        reportResponse.data,
        'reporte_financiero_estimado_pagado'
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
    };
    const response = await onPostService(
      "/reportes/financiero/estimado-pagado/reporte",
      data,
      MSG002
    );

    if (response !== null) {
      const { content } = response;
      if (isEmpty(content) === false) {
        const array = content.map((item) => {
  
          let pageableObj = {
            totalPages: response.totalPages,
            totalElements: response.totalElements,
            pageNumber: response.number,
            size: response.size,
          };
          setPageable({ ...pageableObj });
  
          return {...item};
        });
  
        setDataTable([...array]);
      } else {
        setDataTable([]);
      }
      if (isEmpty(content)) showMessage(MSG004);
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
    return (
      <p style={{width}}>
        {value}
      </p>
    );
  };


  const onGetColumns = () => {
    const detalleFinancieroCols = [
      {
        accessorKey: "estimado",
        header: "Estimado",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ),      },
      {
        accessorKey: "dictaminadoSat",
        header: "Dictaminado SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), 
      },
    ];

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "dictaminadoCc",
          header: "Dictaminado CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ), },
      );
    }
    detalleFinancieroCols.push(
      {
        accessorKey: "deduccionSat",
        header: "Deducción SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ),       },
    );
    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "deduccionCc",
          header: "Deducción CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ),         },
      );
    }


    detalleFinancieroCols.push(
      {
        accessorKey: "notaCreditoSat",
        header: "Nota de crédito SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), },
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "notaCreditoCc",
          header: "Nota de crédito CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ), },
      );
    }

    detalleFinancieroCols.push(
      {
        accessorKey: "subtotalSat",
        header: "Subtotal SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), },
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "subtotalCc",
          header: "Subtotal CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ), },
      );
    }

    detalleFinancieroCols.push(
      {
        accessorKey: "ivaSat",
        header: "IVA SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), },
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "ivaCc",
          header: "IVA CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ), },
      );
    }

    detalleFinancieroCols.push(
      {
        accessorKey: "iepsSat",
        header: "IEPS SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), },
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "iepsCc",
          header: "IEPS CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ),        },
      );
    }

    detalleFinancieroCols.push(
      {
        accessorKey: "otrosImpuestosSat",
        header: "Otros impuestos SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), },
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "otrosImpuestosCc",
          header: "Otros impuestos CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ), },
      );
    }

    detalleFinancieroCols.push(
      {
        accessorKey: "totalPagadoSat",
        header: "Total pagado SAT",
        cell: (props) => (
          <CustomCell
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            width='130px'
          />
        ), },
    );

    if (formRef.current.values.convenioColaboracion == 1) {
      detalleFinancieroCols.push(
        {
          accessorKey: "totalPagadoCc",
          header: "Total pagado CC",
          cell: (props) => (
            <CustomCell
              column={props.column}
              getValue={props.getValue}
              row={props.row}
              width='130px'
            />
          ), },
      );
    }

// -----------------------------------------------------
    let columns = [
      {
        header: "Detalle contrato",
        columns: [
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
            accessorKey: "proveedor",
            header: "Proveedor",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            accessorKey: "numeroContrato",
            header: "Número del contrato",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            accessorKey: "vigente",
            header: "Vigente",
            cell: (props) => <p>{props.getValue()}</p>,
          },
          {
            accessorKey: "periodoControl",
            header: "Periodo de control",
            cell: (props) => <p>{props.getValue()}</p>,
          },
        ],
      },
      {
        header: "Detalle financiero",
        columns: detalleFinancieroCols,
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
        <Accordion title="Criterios de búsqueda" collapse={false} showChevron={false}>
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
                        if (e.target.value !== '') {
                          setLoading(true);
                          const { contratoVigente } = values;
                          const data = {
                            idProyecto: e.target.value,
                            idContratoVigente: contratoVigente,
                          };
                          const response = await onPostService(
                            "/reportes/financiero/catalogo/estimado-pagado/nombre-corto-contrato",
                            data
                          );
                          setNombreCortoContratoCatalog([...response]);
                          formRef.current.setFieldValue("nombreCortoContrato", "");
                          setLoading(false);
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
                        if (e.target.value !== '') {
                          const { nombreCortoProyecto } = values;
                          if (nombreCortoProyecto !== '') {
                            const data = {
                              idProyecto: nombreCortoProyecto,
                              idContratoVigente: e.target.value,
                            };
                            const response = await onPostService(
                              "/reportes/financiero/catalogo/estimado-pagado/nombre-corto-contrato",
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
                  <Col md={12} className="text-end mb-2">
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={onClear}
                    >
                      Limpiar
                    </Button>
                    <Authorization process={"REP_RESFIN_EST_PAG_CONS"}>
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
                  <Authorization process={"REP_RESFIN_EST_PAG_CONS"}>
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
                        header="Estimado/pagado"
                        columns={columns}
                        dataTable={dataTable}
                        manualPagination
                        pageable={pageable}
                        colSpanSubCols={24}
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
