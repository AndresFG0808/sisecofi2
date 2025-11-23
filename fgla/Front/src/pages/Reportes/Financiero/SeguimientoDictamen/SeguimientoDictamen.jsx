import React, { useEffect, useMemo, useRef, useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { Accordion, Loader, MainTitle } from "../../../../components";
import { Select, SelectMultiple, TextFieldDate } from "../../../../components/formInputs";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { useToast } from "../../../../hooks/useToast";
import { downloadDocumentPost, getData, postData } from "../../../../functions/api";
import showMessage from "../../../../components/Messages";
import IconButton from "../../../../components/buttons/IconButton";
import { downloadExcelBlob } from "../../../../functions/utils";
import { Formik } from "formik";
import * as yup from "yup";
import { isEmpty } from "lodash";
import Authorization from "../../../../components/Authorization";
import { logError } from '../../../../components/utils/logError.js';

const VALORES_INICIALES = {
  idProyecto: "",
  nombreCortoProyecto: "",
  idContratoVigente: "",
  nombreCortoContrato: "",
  idVerificadorContrato: "",
  listaIdEstatusDictamen: [0],
  pageSize: 15,
  pageNumber: 0
};

const MSG004 = "No se encontraron resultados de la búsqueda.";

const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15
};

export function SeguimientoDictamen() {
  const ID_KEY_NAME = "idPista";
  const { errorToast } = useToast();
  const [isVisibleTable, setIsVisibleTable] = useState(false);
  const [loading, setLoading] = useState(false);
  const tableReference = useRef();
  const [dataTable, setDataTable] = useState([]);
  const [pageable, setPageable] = useState(PAGEABLE);
  const [proyectoOptions, setProyectoOptions] = useState([]);
  const [constratoVigenteOptions, setConstratoVigenteOptions] = useState([]);
  const [contratoOptions, setContratoOptions] = useState([]);
  const [verificadorContratoOptions, setVerificadorContratoOptions] = useState([]);
  const [estatusDictamenOptions, setEstatusDictamenOptions] = useState([]);
  const [valuesConsulta, setValuesConsulta] = useState({});
  const [valoresIniciales, setValoresIniciales] = useState({ ...VALORES_INICIALES });
  const [dynamicColumns, setDynamicColumns] = useState([]);
  const [nombreCortoText, setNombreCortoText] = useState([]);

  const formRef = useRef();

  const esquema = yup.object({

  });

  useEffect(() => {
    getDataInit();
  }, []);

  const getDataInit = () => {
    let proyectos = getData("/reportes/financiero/catalogo/seguimiento-dictamen/nombre-corto-proyecto");
    let constratoVigente = getData("/reportes/financiero/catalogo/seguimiento-dictamen/contrato-vigente");
    let estatusDictamen = getData("/reportes/financiero/catalogo/seguimiento-dictamen/estatus-dictamen");

    Promise.all([proyectos, constratoVigente, estatusDictamen])
      .then(([proyectosResp, constratoVigenteResp, estatusDictamenResp]) => {
        setProyectoOptions(proyectosResp.data);
        setConstratoVigenteOptions(constratoVigenteResp.data);
        setEstatusDictamenOptions(estatusDictamenResp.data);
        
        let opcionSi = constratoVigenteResp.data.find(item => item.nombre === "Sí");
        let idSi = opcionSi.primaryKey;
        if (idSi) {
          setValoresIniciales({
            ...valoresIniciales,
            idContratoVigente: idSi
          });
        }
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage("");
      });
  };

  const getContratos = (values) => {
    if (values.nombreCortoProyecto)
      setNombreCortoText(values.nombreCortoProyecto);

    postData('/reportes/financiero/catalogo/seguimiento-dictamen/nombre-corto-contrato', values)
      .then((response) => {
        setContratoOptions(response.data);
      })
      .catch((error) => {
        logError("error => ", error);
      });
  }

  const getVerificadores = (e) => {
    let contratoNombre = e.target.value;
    let contrato = contratoOptions.find(item => item.nombreCorto === contratoNombre);
    if (contrato) {
      getData("/reportes/financiero/catalogo/seguimiento-dictamen/verificador-contrato/" + contratoNombre)
        .then((response) => {
          setVerificadorContratoOptions(response.data);
        })
        .catch((error) => {
          logError("error => ", error);
        });
    } else {
      setVerificadorContratoOptions([]);
    }
  }

  const consultarReporte = (values) => {
    setValuesConsulta(values);
    setIsVisibleTable(true);
    getReporte(values);
  }

  const updateReporte = (values) => {
    let newValues = {
      ...valuesConsulta,
      pageNumber: values.page,
      pageSize: values.size
    };
    getReporte(newValues);
  }

  const getReporte = (data) => {
    setLoading(true);
    postData('/reportes/financiero/seguimiento-dictamen/reporte', data)
      .then((response) => {
        setLoading(false);
        processData(response.data);
        
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage("");
      });
  };

  const processData = (data) => {
    let processedDataTable = [];
    if (isEmpty(data.content)) showMessage(MSG004);
    data.content.forEach((item) => {
      let row = {
        ...item,
        importeFacturaInicial: item.inicial?.importeFacturaSinImpuestos,
        netoDolaresInicial: item.inicial?.netoPagarUsd,
        netoPesosInicial: item.inicial?.netoPagarPesos,

        importeFacturaDictaminado: item.dictaminado?.importeFacturaSinImpuestos,
        netoDolaresDictaminado: item.dictaminado?.netoPagarUsd,
        netoPesosDictaminado: item.dictaminado?.netoPagarPesos,

        importeFacturaProforma: item.proforma?.importeFacturaSinImpuestos,
        netoDolaresProforma: item.proforma?.netoPagarUsd,
        netoPesosProforma: item.proforma?.netoPagarPesos,

        importeFacturaFacturado: item.facturado?.importeFacturaSinImpuestos,
        netoDolaresFacturado: item.facturado?.netoPagarUsd,
        netoPesosFacturado: item.facturado?.netoPagarPesos,

        importeFacturaSolicituddepago: item.solicitudPago?.importeFacturaSinImpuestos,
        netoDolaresSolicituddepago: item.solicitudPago?.netoPagarUsd,
        netoPesosSolicituddepago: item.solicitudPago?.netoPagarPesos,

        importeFacturaPagado: item.pagado?.importeFacturaSinImpuestos,
        netoDolaresPagado: item.pagado?.netoPagarUsd,
        netoPesosPagado: item.pagado?.netoPagarPesos,

        importeFacturaCancelado: item.cancelado?.importeFacturaSinImpuestos,
        netoDolaresCancelado: item.cancelado?.netoPagarUsd,
        netoPesosCancelado: item.cancelado?.netoPagarPesos,
      };
      processedDataTable.push(row);
    });

    let pageable = {
      totalPages: data.totalPages,
      totalElements: data.totalElements,
      pageNumber: data.number,
      size: data.size
    };

    setPageable(pageable);
    setDataTable(processedDataTable);
    const {
      listaIdEstatusDictamen,
    } = formRef.current.values;
    if (listaIdEstatusDictamen.length === 1 && listaIdEstatusDictamen[0] == 0)
      getDynamicColumns(listaIdEstatusDictamen);
  };

  const handleDownloadExcel = () => {
    setLoading(true);
    downloadDocumentPost('/reportes/financiero/seguimiento-dictamen/reporte-export', valuesConsulta)
      .then((response) => {
        setLoading(false);
        downloadExcelBlob(response.data, 'reporte_seguimiento-de-dictamen');
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage("");
      });
  };

  const getDynamicColumns = (estatusSelected) => {
    const allOption = estatusDictamenOptions.find(item => item.nombre.toLowerCase().includes("todos"));
    const isAllSelected = allOption && estatusSelected.includes(allOption.primaryKey);

    const filteredOptions = isAllSelected
      ? estatusDictamenOptions.filter(item => item.primaryKey !== allOption.primaryKey)
      : estatusDictamenOptions.filter(item => estatusSelected.includes(item.primaryKey));

    const arrayColumns = filteredOptions.map(item => getColumnsGroup(item.nombre));
    setDynamicColumns(arrayColumns);
  };

  const getColumnsGroup = (estatus) => {
    let group = {
      header: estatus,
      columns: [
        {
          accessorKey: "importeFactura" + estatus.replaceAll(" ", ""),
          header: "Importe de factura sin impuesto",
          cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>
        },
        {
          accessorKey: "netoDolares" + estatus.replaceAll(" ", ""),
          header: "Neto a pagar en dólares",
          cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>
        },
        {
          accessorKey: "netoPesos" + estatus.replaceAll(" ", ""),
          header: "Neto a pagar en pesos",
          cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>
        }
      ]
    };
    return group;
  }

  const columns = useMemo(() => [
    {
      header: "Detalle general",
      columns: [
        {
          header: " ",
          columns: [
            {
              accessorKey: "nombreCortoProyecto",
              header: "Nombre corto del proyecto",
              cell: (props) => <p>{props.getValue()}</p>
            },
            {
              accessorKey: "nombreCortoContrato",
              header: "Nombre corto del contrato",
              cell: (props) => <p>{props.getValue()}</p>
            },
            {
              accessorKey: "verificador",
              header: "Verificador",
              cell: (props) => <p>{props.getValue()}</p>
            },
            {
              accessorKey: "periodoControl",
              header: "Periodo de control",
              cell: (props) => <p>{props.getValue()}</p>
            }
          ]

        },
        ...dynamicColumns
      ]
    }
  ], [dynamicColumns]);

  const handleClear = () => {
    console.log("handleClear => ");
    setIsVisibleTable(false);
    setLoading(true);
    setDataTable([]);
    setValoresIniciales({ ...VALORES_INICIALES });
    setDynamicColumns([]);
    getDataInit();
  }

  return (
    <Container className='mt-3 px-3'>

      {loading && <Loader />}

      <MainTitle title="Reporte Financiero" />

      <Accordion           collapse={false}
          showChevron={false} title={"Criterios de búsqueda"}>

        <Formik
          innerRef={(f) => (formRef.current = f)}
          initialValues={{ ...valoresIniciales }}
          enableReinitialize
          validationSchema={esquema}
          validateOnMount={true}
          onSubmit={(e, { resetForm }) => consultarReporte(e, resetForm)}
        >
          {({
            handleSubmit,
            handleChange,
            handleBlur,
            resetForm,
            setFieldValue,
            values,
            errors,
            touched,
            isValid
          }) => (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Row>
                <Col md={4} of>
                  <Select
                    label={"Nombre corto del proyecto:"}
                    name="idProyecto"
                    keyValue={"idProyecto"}
                    value={values.idProyecto}
                    onChange={e => {
                      handleChange(e);
                      const selectedText = e.target.options[e.target.selectedIndex].text;
                      setFieldValue('nombreCortoProyecto', selectedText);
                      getContratos({ idProyecto: e.target.value, idContratoVigente: values.idContratoVigente, nombreCortoProyecto: selectedText })
                    }}
                    options={proyectoOptions}
                    keyTextValue="nombreCorto"
                  />
                  <input type="hidden" name="nombreCortoProyecto" keyValue={"nombreCortoProyecto"} value={values.nombreCortoProyecto} />
                </Col>
              </Row>
              <Row>
                <Col md={4}>
                  <Select
                    label={"Contrato vigente:"}
                    name="idContratoVigente"
                    value={values.idContratoVigente}
                    onChange={e => { handleChange(e); getContratos({ idProyecto: values.idProyecto, nombreCortoProyecto: nombreCortoText, idContratoVigente: e.target.value }) }}
                    options={constratoVigenteOptions}
                    keyValue="primaryKey"
                    keyTextValue="nombre"
                  />
                </Col>
                <Col md={4}>
                  <Select
                    label="Nombre corto del contrato:"
                    name="nombreCortoContrato"
                    value={values.nombreCortoContrato}
                    onChange={e => { handleChange(e); getVerificadores(e); setFieldValue("idVerificadorContrato", "") }}
                    options={contratoOptions}
                    keyValue="nombreCorto"
                    keyTextValue="nombreCorto"
                    className={touched.nombreCortoContrato && (errors.nombreCortoContrato ? 'is-invalid' : 'is-valid')}
                    helperText={touched.nombreCortoContrato ? errors.nombreCortoContrato : ''}
                  />
                </Col>
                <Col md={4}>
                  <Select
                    label={"Verificador del contrato:"}
                    name="idVerificadorContrato"
                    value={values.idVerificadorContrato}
                    onChange={handleChange}
                    options={verificadorContratoOptions}
                    keyValue="idVerificador"
                    keyTextValue="nombre"
                  />
                </Col>
                <Col md={4}>
                  <SelectMultiple
                    label="Estatus del dictamen:"
                    name="listaIdEstatusDictamen"
                    values={values.listaIdEstatusDictamen}
                    onChange={e => { getDynamicColumns(e) }}
                    options={estatusDictamenOptions}
                    keyValue="primaryKey"
                    keyTextValue="nombre"
                  />
                </Col>
              </Row>

              <Row>
                <Col md={12} className="text-end mb-2">
                  <Button
                    variant="gray"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={handleClear}
                  >
                    Limpiar
                  </Button>
                  <Authorization process={"REP_RESFIN_SEG_DICT_CONS"}>
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={() => { console.log("isValid => ", !isValid); !isValid && errorToast(""); }}
                      type="submit"
                    >
                      Buscar
                    </Button>
                  </Authorization>
                </Col>
              </Row>
              {
                isVisibleTable && (
              <Row>
                <Col md={12} className="text-end mb-2">
                  <Authorization process={"REP_RESFIN_SEG_DICT_CONS"}>
                    <IconButton
                      type="excel"
                      onClick={handleDownloadExcel}
                      disabled={dataTable.length === 0}
                      tooltip={"Exportar a Excel"}
                    />
                  </Authorization>
                </Col>
              </Row>
                )
              }
            </Form>
          )}
        </Formik>
        {
          isVisibleTable && (
            <Row>
            <Col md={12} className="group-columns">
              <TablaEditable
                header={"Seguimiento de dictamen"}
                dataTable={dataTable}
                columns={columns}
                onUpdate={setDataTable}
                onDelete={setDataTable}
                ref={tableReference}
                hasPagination
                manualPagination
                pageable={pageable}
                onChangePagination={updateReporte}
                colSpanSubCols={25}
              />
            </Col>
          </Row>
          )
        }
      </Accordion>
    </Container>
  );
}
