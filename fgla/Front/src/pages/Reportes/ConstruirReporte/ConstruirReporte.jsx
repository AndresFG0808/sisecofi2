import React, { useCallback, useEffect, useMemo, useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { Accordion, LabelValue, Loader } from "../../../components";
import {
  Select,
  SelectMultiple,
  TextFieldDate,
} from "../../../components/formInputs";
import { TreeStructure } from "../../../components/TreeStructure";
import {
  CONSTRUIR_REPORTES,
  INITIAL_DATA,
  PAGEABLE,
  construirReporteSchema,
  convertTreeToObj,
  data,
  generateHeaders,
  rearrangeReporte,
  rearrangeSendData,
} from "./utils";
import {
  useGetConvenioColaboracionQuery,
  useGetDominoTecnologicoQuery,
  useGetEstatusContratoQuery,
  useGetEstatusProyectoQuery,
  useGetRazonSocialQuery,
  usePostNombreCortoContratoMutation,
  usePostNombreCortoProyectoMutation,
  usePostReporteDinamicoMutation,
  usePostTituloServicioMutation,
} from "./store";
import { TablaEditable } from "../../../components/table/TablaEditable";
import { Formik } from "formik";
import { RadialButton } from "./components";
import IconButton from "../../../components/buttons/IconButton";
import { downloadExcelBlob } from "../../../functions/utils";
import { downloadDocumentPost } from "../../../functions/api";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import { useToast } from "../../../hooks/useToast";
import _ from "lodash";
import "./styles.css";
import { useReporteDinamico } from "../../../hooks/useReporteDinamico";
import Authorization from "../../../components/Authorization";
import { useErrorMessages } from "../../../hooks/useErrorMessages";

export function ConstruirReporte() {
  const { getMessageExists } = useErrorMessages(CONSTRUIR_REPORTES);
  const { errorToast } = useToast();
  const [dataTable, setDataTable] = useState([]);
  const [searchParams, setSearchParams] = useState();
  const [dataColumns, setDataColumns] = useState();
  const [pagination, setPagination] = useState(PAGEABLE);
  const [treeData, setTreeData] = useState(data);
  const [datosIniciales, setDatosIniciales] = useState(INITIAL_DATA);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
  const [reset, setReset] = useState(false);
  const periodos = useReporteDinamico(treeData);
  const [
    getTituloServicio,
    { data: tituloServicio, isLoading: isLoadingTituloServicio },
  ] = usePostTituloServicioMutation();
  const [
    getNombreCortoContrato,
    { data: nombreCortoContrato, isLoading: isLoadingNombreCortoContrato },
  ] = usePostNombreCortoContratoMutation();
  const [
    getNombreCortoProyecto,
    { data: nombreCortoProyecto, isLoading: isLoadingNombreCortoProyecto },
  ] = usePostNombreCortoProyectoMutation();

  const { data: razonSocial, isLoading: isLoadingRazonSocial } =
    useGetRazonSocialQuery();
  const {
    data: convenioColaboracion,
    isLoading: isLoadingConvenioColaboracion,
  } = useGetConvenioColaboracionQuery();
  const { data: estatusProyecto, isLoading: isLoadingEstatusProyecto } =
    useGetEstatusProyectoQuery();
  const { data: estatusContrato, isLoading: isLoadingEstatusContrato } =
    useGetEstatusContratoQuery();
  const { data: dominioTecnologico, isLoading: isLoadingDominioTecnologico } =
    useGetDominoTecnologicoQuery();

  const [
    obtenerReporte,
    { data: reporteDinamico, isLoading: isLoadingObtenerReporte },
  ] = usePostReporteDinamicoMutation();

  const columns = useMemo(() => {
    if (reset) return [];
    return generateHeaders(dataColumns);
  }, [dataTable, reset, dataColumns]);

  const handleChangeRequest = (ids, setFieldValue, type, values) => {
    setFieldValue(type, ids);
    const data = { id: ids };
    switch (type) {
      case "idEstatusProyecto": {
        getNombreCortoProyecto({ data });
        break;
      }
      case "idEstatusContratoProyecto": {
        getNombreCortoContrato({ data });
        break;
      }
      case "idRazonSocial": {
        getTituloServicio({ data });
        break;
      }
    }
  };

  const handleChangeMultiple = (event, id, setFieldValue, values) => {
    setFieldValue(id, event);
  };
  const handleSubmit = async (values) => {
    try {
      const hasDataChanged = !_.isEqual(data, treeData);
      const hasSearchChanged = !_.isEqual(INITIAL_DATA, values);
      if (!hasDataChanged || !hasSearchChanged) {
        setSingleBasicMessage(
          "Favor de ingresar como mínimo un criterio de búsqueda y un campo para reporte."
        );
        setShowSingleBasicModal(true);
        return;
      }
      setSearchParams(values);
      const treeObj = convertTreeToObj(treeData);
      const params = {
        ...values,
        page: pagination.pageNumber,
        size: pagination.size,
      };
      const dataToSend = rearrangeSendData(treeObj, params);
      const res = await obtenerReporte({ data: dataToSend }).unwrap();

      if (!res) {
        setSingleBasicMessage("No se encontraron resultados de la búsqueda.");
        setShowSingleBasicModal(true);
        setDataTable(() => []);
        setPagination((prev) => ({
          ...prev,
          totalPages: 0,
          totalElements: 0,
          pageNumber: 0,
        }));
        setReset(true);
      } else if (res.content.length <= 0) {
        setSingleBasicMessage("No se encontraron resultados de la búsqueda.");
        setShowSingleBasicModal(true);
        setDataTable(() => []);
        setPagination((prev) => ({
          ...prev,
          totalPages: 0,
          totalElements: 0,
          pageNumber: 0,
        }));
        setReset(true);
      } else {
        const { totalPages, totalElements, size, number } = res;
        const paginationState = {
          totalPages,
          totalElements,
          pageNumber: number,
          size,
        };
        setPagination({ ...paginationState });
        setReset(false);
        setDataTable(rearrangeReporte(res));
        setDataColumns(res);
      }
    } catch (error) {
      const mensaje = error.data.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const handlePagination = async (values, newPagination) => {
    try {
      const hasDataChanged = !_.isEqual(data, treeData);
      const hasSearchChanged = !_.isEqual(INITIAL_DATA, values);
      if (!hasDataChanged || !hasSearchChanged) {
        setSingleBasicMessage(
          "Favor de ingresar como mínimo un criterio de búsqueda y un campo para reporte."
        );
        setShowSingleBasicModal(true);
        return;
      }
      const treeObj = convertTreeToObj(treeData);
      const params = {
        ...values,
        page: newPagination.pageNumber,
        size: newPagination.size,
      };
      const dataToSend = rearrangeSendData(treeObj, params);
      const res = await obtenerReporte({ data: dataToSend }).unwrap();

      if (!res) {
        setSingleBasicMessage("No se encontraron resultados de la búsqueda.");
        setShowSingleBasicModal(true);
        setReset(false);
      } else if (res.content.length <= 0) {
        setSingleBasicMessage("No se encontraron resultados de la búsqueda.");
        setShowSingleBasicModal(true);
        setReset(false);
      } else {
        setReset(false);
        setDataTable(rearrangeReporte(res));
        setDataColumns(res);
      }
    } catch (error) {
      const mensaje = error.data.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const handleGetReporte = (values) => {
    const treeObj = convertTreeToObj(treeData);
    const params = {
      ...values,
      page: pagination.pageNumber,
      size: pagination.size,
    };
    const dataToSend = rearrangeSendData(treeObj, params);
    downloadDocumentPost(
      "/reportes/dinamico/reporte-dinamico-export",
      dataToSend
    )
      .then((res) => {
        downloadExcelBlob(res.data, "Reporte");
      })
      .catch((err) => {
        const mensaje = err?.data?.mensaje?.[0];
        if (getMessageExists(mensaje)) {
          setSingleBasicMessage(mensaje);
          setShowSingleBasicModal(true);
        } else {
          setSingleBasicMessage("Ocurrió un error.");
          setShowSingleBasicModal(true);
        }
      });
  };
  return (
    <>
      {isLoadingRazonSocial ||
      isLoadingEstatusContrato ||
      isLoadingDominioTecnologico ||
      isLoadingEstatusProyecto ||
      isLoadingTituloServicio ||
      isLoadingNombreCortoContrato ||
      isLoadingNombreCortoProyecto ||
      isLoadingConvenioColaboracion ||
      isLoadingObtenerReporte ? (
        <Loader zIndex={"1000000"} />
      ) : null}
      <Container className="mt-3 px-3">
        <Row>
          <Col md={9}>
            <Accordion
              title={"Criterios de búsqueda"}
              show={true}
              collapse={false}
            >
              <Authorization process={"REP_CONSREP_CONS"}>
                <Formik
                  validationSchema={construirReporteSchema}
                  enableReinitialize
                  validateOnMount
                  onSubmit={handleSubmit}
                  initialValues={datosIniciales}
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
                    isValid,
                  }) => (
                    <>
                      <Form autoComplete="off" onSubmit={handleSubmit}>
                        <Row className="mb-3">
                          <Row>
                            <Col md={3}>
                              <LabelValue label={"Proyecto"} />
                            </Col>
                          </Row>
                          <Row>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeRequest(
                                    e,
                                    setFieldValue,
                                    "idEstatusProyecto",
                                    values.idEstatusProyecto
                                  );
                                }}
                                label={"Estatus:"}
                                name={"idEstatusProyecto"}
                                values={values.idEstatusProyecto}
                                options={estatusProyecto}
                                keyValue="primaryKey"
                                keyTextValue="nombre"
                              />
                            </Col>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeMultiple(
                                    e,
                                    "idProyectos",
                                    setFieldValue,
                                    values.idProyectos
                                  );
                                }}
                                label={"Nombre corto:"}
                                name={"idProyectos"}
                                values={values.idProyectos}
                                options={nombreCortoProyecto}
                                keyValue="idProyecto"
                                keyTextValue="nombreCorto"
                              />
                            </Col>
                          </Row>
                        </Row>
                        {/* //TODO CONTRATO */}
                        <Row className="mb-3">
                          <Row>
                            <Col md={3}>
                              <LabelValue label={"Contrato"} />
                            </Col>
                          </Row>
                          <Row>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeRequest(
                                    e,
                                    setFieldValue,
                                    "idEstatusContratoProyecto",
                                    values.idEstatusContratoProyecto
                                  );
                                }}
                                label={"Estatus:"}
                                name={"idEstatusContratoProyecto"}
                                values={values.idEstatusContratoProyecto}
                                options={estatusContrato}
                                keyValue="primaryKey"
                                keyTextValue="nombre"
                              />
                            </Col>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeMultiple(
                                    e,
                                    "idContratos",
                                    setFieldValue,
                                    values.idContratos
                                  );
                                }}
                                label={"Nombre corto:"}
                                name={"idContratos"}
                                values={values.idContratos}
                                options={nombreCortoContrato}
                                keyValue="idContrato"
                                keyTextValue="nombreCorto"
                              />
                            </Col>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeMultiple(
                                    e,
                                    "idDominioTecnologicos",
                                    setFieldValue,
                                    values.idDominioTecnologicos
                                  );
                                }}
                                label={"Dominio tecnológico:"}
                                name="idDominioTecnologicos"
                                values={values.idDominioTecnologicos}
                                options={dominioTecnologico}
                                keyValue="primaryKey"
                                keyTextValue="nombre"
                              />
                            </Col>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeMultiple(
                                    e,
                                    "idConveniosColaboracion",
                                    setFieldValue,
                                    values.idConveniosColaboracion
                                  );
                                }}
                                values={values.idConveniosColaboracion}
                                label={"Convenio de colaboración:"}
                                name={"idConveniosColaboracion"}
                                options={convenioColaboracion}
                                keyValue="primaryKey"
                                keyTextValue="nombre"
                              />
                            </Col>
                          </Row>
                        </Row>
                        {/* //TODO CONTRATO */}
                        <Row className="mb-3">
                          <Row>
                            <Col md={3}>
                              <LabelValue label={"Proveedor"} />
                            </Col>
                          </Row>
                          <Row>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeRequest(
                                    e,
                                    setFieldValue,
                                    "idRazonSocial",
                                    values.idRazonSocial
                                  );
                                }}
                                label={"Razón social:"}
                                name={"idRazonSocial"}
                                values={values.idRazonSocial}
                                options={razonSocial}
                                keyValue="idProveedor"
                                keyTextValue="nombreComercial"
                              />
                            </Col>
                            <Col md={3}>
                              <SelectMultiple
                                onChange={(e) => {
                                  handleChangeMultiple(
                                    e,
                                    "idTituloServicio",
                                    setFieldValue,
                                    values.idTituloServicio
                                  );
                                }}
                                label={"Título de servicio:"}
                                name={"idTituloServicio"}
                                values={values.idTituloServicio}
                                options={tituloServicio}
                                keyValue="idTituloServicioProveedor"
                                keyTextValue="numeroTitulo"
                              />
                            </Col>
                          </Row>
                        </Row>
                        <Row className="mb-3">
                          <Row>
                            <Col md={3}>
                              <LabelValue label={"Periodo"} />
                            </Col>
                          </Row>
                          <Row>
                            <Col md={3}>
                              <TextFieldDate
                                label={"Inicio:"}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.fechaInicio}
                                name={"fechaInicio"}
                                helperText={
                                  touched.fechaInicio ? errors.fechaInicio : ""
                                }
                                className={
                                  touched.fechaInicio &&
                                  (errors.fechaInicio ? "is-invalid" : "")
                                }
                              />
                            </Col>
                            <Col md={3}>
                              <TextFieldDate
                                label={"Término:"}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.fechaTermino}
                                name={"fechaTermino"}
                                helperText={
                                  touched.fechaTermino
                                    ? errors.fechaTermino
                                    : ""
                                }
                                className={
                                  touched.fechaTermino &&
                                  (errors.fechaTermino ? "is-invalid" : "")
                                }
                              />
                            </Col>
                            <Col md={3}>
                              <Row>
                                <LabelValue
                                  label={"Presentación de la información"}
                                />
                              </Row>
                              <Row>
                                <RadialButton
                                  md={6}
                                  label={"Acumulada"}
                                  group={"presentacion"}
                                  id={"acumulada"}
                                  value={values.acumulada}
                                  setFieldValue={setFieldValue}
                                  cancelField={"mensual"}
                                  canceledValue={values.mensual}
                                />
                                <RadialButton
                                  md={6}
                                  label={"Mensual"}
                                  group={"presentacion"}
                                  id={"mensual"}
                                  value={values.mensual}
                                  setFieldValue={setFieldValue}
                                  cancelField={"acumulada"}
                                  canceledValue={values.acumulada}
                                />
                              </Row>
                            </Col>
                          </Row>
                        </Row>
                        <Row className="mb-3">
                          <Col md={4}>
                            <Select
                              name="aplicacionPeriodo"
                              label={"Aplicación del periodo:"}
                              options={periodos}
                              keyValue="id"
                              keyTextValue={"displayName"}
                              value={values.aplicacionPeriodo}
                              onChange={handleChange}
                            />
                          </Col>
                          <Col md={4}></Col>
                          <Col md={4}>
                            <Button
                              variant="gray"
                              className="btn-sm ms-2 waves-effect waves-light"
                              type="submit"
                              onClick={() => {
                                !isValid &&
                                  errorToast(
                                    "La fecha ingresada es incorrecta."
                                  );
                              }}
                            >
                              Buscar
                            </Button>
                            <Button
                              variant="red"
                              className="btn-sm ms-2 waves-effect waves-light"
                              onClick={() => {
                                setDataTable(() => []);
                                setReset(true);
                                setTreeData(data);
                                resetForm();
                              }}
                            >
                              Limpiar Campos
                            </Button>
                          </Col>
                        </Row>
                      </Form>
                      <Row>
                        { dataTable.length > 0 ? (
                          <Col md={12} className="text-end mb-2">
                            <IconButton
                              tooltip={"Exportar a excel"}
                              type={"excel"}
                              onClick={() => {
                                handleGetReporte(values);
                              }}
                              disabled={
                                dataTable.length <= 0 && columns.length <= 0
                              }
                            />
                          </Col>
                        ) : ''}
                      </Row>
                    </>
                  )}
                </Formik>
              </Authorization>
            </Accordion>
            <Authorization process={"REP_CONSREP_CONS"}>
              <Row>
                <Container>
                  { dataTable.length > 0 ? (
                    <TablaEditable
                      header={"Reporte"}
                      dataTable={dataTable}
                      columns={columns}
                      hasPagination
                      manualPagination
                      onChangePagination={(pageValues) => {
                        handlePagination(searchParams, {
                          size: pageValues.size,
                          pageNumber: pageValues.page,
                        });
                      }}
                      pageable={pagination}
                    />
                  ) : ''}
                </Container>
              </Row>
            </Authorization>
          </Col>
          <Col md={3}>
            <Accordion
              title={"Campos para reporte"}
              show={true}
              collapse={false}
              className={"tree-structure"}
            >
              <Authorization process={"REP_CONSREP_CONS"}>
                <TreeStructure data={treeData} setData={setTreeData} />
              </Authorization>
            </Accordion>
          </Col>
        </Row>
      </Container>
      <SingleBasicModal
        title={"Mensaje"}
        size={"md"}
        approveText={"Aceptar"}
        show={showSingleBasicModal}
        onHide={() => setShowSingleBasicModal(false)}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
