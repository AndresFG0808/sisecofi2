import React, { useEffect, useState } from "react";
import {
  useGetAdmonCentralesQuery,
  useGetProveedoresQuery,
  useGetEstatusContratosQuery,
  usePostContratosMutation,
  usePostReporteContratoMutation,
} from "./store";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import MainTitle from "../../../../../components/MainTitle";
import { Accordion, LabelValue, Loader } from "../../../../../components";
import { Formik } from "formik";
import { Select, TextFieldDate } from "../../../../../components/formInputs";
import { Tooltip } from "../../../../../components/Tooltip";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaPaginada } from "../../../../../components/table";
import { useNavigate } from "react-router-dom";
import "./styles.css";
import { injectActions } from "../../../../../functions/utils";
import {
  HEADERS,
  INITIAL_VALUES,
  PAGEABLE,
  contratosSchema,
  rearrangeResponse,
} from "./utils";
import { ALTA_CONTRATOS } from "../../../../../constants/messages";
import { useToast } from "../../../../../hooks/useToast";
import { useDispatch } from "react-redux";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import moment from "moment";
import _, { values } from "lodash";
import Authorization from "../../../../../components/Authorization";
import { useGetAuthorization } from "../../../../../hooks/useGetAuthorization";

export function Contratos() {
  const ID_KEY_NAME = "idContrato";
  const { getMessageExists } = useErrorMessages(ALTA_CONTRATOS);
  const [consulta, setConsulta] = useState({});
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
  const [pageable, setPageable] = useState(PAGEABLE);
  const dispatch = useDispatch();
  const { errorToast } = useToast();
  const navigate = useNavigate();
  const { data: proveedores, isLoading: isProveedoresLoading } =
    useGetProveedoresQuery();
  const { data: admonCentrales, isLoading: isAdmonCentralesLoading } =
    useGetAdmonCentralesQuery();
  const { data: estatusContratos, isLoading: isEstatusContratosLoading } =
    useGetEstatusContratosQuery();
  const [
    buscarContratos,
    { isLoading: isLoadingBuscarContrato, data: contratos },
  ] = usePostContratosMutation();
  const [generarReporte, { isLoading: isLoadingReporte }] =
    usePostReporteContratoMutation();
  const [dataTable, setDataTable] = useState([]);
  const { isAuthorized: canEdit } = useGetAuthorization("CONT_MODIF");

  useEffect(() => {
    if (contratos) {
      setDataTable(rearrangeResponse(contratos.content));
      setPageable({
        totalPages: contratos?.totalPages,
        totalElements: contratos?.totalElements,
        pageNumber: contratos?.number,
        size: contratos?.size,
      });
    }
  }, [contratos]);

  const handleAltaContrato = () => {
    let path = "/contratos/contratos/alta";
    navigate(path);
  };

  const handleEdit = (id) => () => {
    const currentContrato = dataTable.find(
      ({ idContrato }) => idContrato === id
    );
    const path = `/contratos/contratos/editar/${currentContrato.idContrato}`;
    navigate(path);
  };
  const handleShow = (id) => () => {
    const currentContrato = dataTable.find(
      ({ idContrato }) => idContrato === id
    );
    const path = `/contratos/contratos/verDetalle/${currentContrato.idContrato}`;
    navigate(path);
  };

  const updateDataTable = (values) => {
    const data = {
      ...consulta,
      ...values,
    };
    setConsulta(data);
    buscarContratos({ data });
  };

  const handleGetReporte = async () => {
    try {
      if (!contratosSchema(consulta)) throw new Error(ALTA_CONTRATOS.MSG008);
      const res = await generarReporte({
        data: consulta,
      }).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage(
          "Ocurrió un error. Favor de intentarlo más tarde."
        );
        setShowSingleBasicModal(true);
      }
    }
  };

  const onSubmit = async (values, _) => {
    try {
      if (!contratosSchema(values)) {
        errorToast(ALTA_CONTRATOS.MSG008);
        return;
      }
      const data = {
        idAdministracionCentral: parseInt(values.admonCentral),
        idEstatusContrato: parseInt(values.estatusContrato),
        fechaInicio:
          values.fecha_inicio === null
            ? null
            : moment(new Date(values.fecha_inicio)).toISOString(),
        fechaTermino:
          values.fecha_termino === null
            ? null
            : moment(new Date(values.fecha_termino))
                .add(1, "day")
                .toISOString(),
        idProveedor: parseInt(values.proveedor),
        size: pageable.size,
      };
      setConsulta(data);
      await buscarContratos({ data }).unwrap();
    } catch (error) {
      if (error?.data?.mensaje[0].includes("No existen")) {
        setDataTable([]);
        setSingleBasicMessage(ALTA_CONTRATOS.MSG002);
        setShowSingleBasicModal(true);
      } else if (getMessageExists(error?.data?.mensaje[0])) {
        setSingleBasicMessage(error?.data?.mensaje[0]);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error, favor de intentar nuevamente");
        setShowSingleBasicModal(true);
      }
    }
  };
  return (
    <>
      <Container className="mt-3 px3">
        <MainTitle title={"Contratos"} />
        <Accordion title={"Búsqueda"}>
          <Formik
            enableReinitialize
            onSubmit={onSubmit}
            initialValues={INITIAL_VALUES}
          >
            {({
              handleSubmit,
              handleChange,
              handleBlur,
              values,
              errors,
              touched,
            }) => {
              return (
                <>
                  {isProveedoresLoading ||
                  isLoadingBuscarContrato ||
                  isEstatusContratosLoading ||
                  isAdmonCentralesLoading ||
                  isLoadingReporte ? (
                    <Loader />
                  ) : null}
                  <Form autoComplete="off" onSubmit={handleSubmit}>
                    <Row>
                      <Col md={4}>
                        <Select
                          options={estatusContratos}
                          name={"estatusContrato"}
                          value={values.estatusContrato}
                          label={"Estatus del contrato:"}
                          keyValue={"primaryKey"}
                          keyTextValue={"nombre"}
                          onChange={handleChange}
                        />
                      </Col>
                      <Col md={4}>
                        <Row className="vigencia">
                          <LabelValue label={"Vigencia:"} />
                        </Row>
                        <Row className="">
                          <Col md={4} className={"input-date"}>
                            <TextFieldDate
                              name={"fecha_inicio"}
                              value={values.fecha_inicio}
                              label={"De:"}
                              onChange={handleChange}
                            />
                          </Col>
                          <Col md={2}></Col>
                          <Col md={4} className={"input-date"}>
                            <TextFieldDate
                              name={"fecha_termino"}
                              value={values.fecha_termino}
                              label={"Al:"}
                              onChange={handleChange}
                            />
                          </Col>
                        </Row>
                      </Col>
                      <Col md={4}>
                        <Select
                          options={proveedores}
                          keyValue={"idProveedor"}
                          keyTextValue={"nombreProveedor"}
                          name={"proveedor"}
                          value={values.proveedor}
                          label={"Proveedor:"}
                          onChange={handleChange}
                        />
                      </Col>
                    </Row>
                    <Row>
                      <Col md={4}>
                        <Select
                          options={admonCentrales}
                          keyValue={"primaryKey"}
                          keyTextValue={"acronimo"}
                          name={"admonCentral"}
                          value={values.admonCentral}
                          label={"Administración central:"}
                          onChange={handleChange}
                        />
                      </Col>
                      <Col md={4}></Col>
                      <Col md={4}>
                        <Button
                          variant="gray"
                          className="btn-sm ms-2 waves-effect waves-light"
                          type="submit"
                        >
                          Buscar
                        </Button>
                      </Col>
                    </Row>
                  </Form>
                </>
              );
            }}
          </Formik>
          <SingleBasicModal
            size={"md"}
            title={"Mensaje"}
            approveText={"Aceptar"}
            show={showSingleBasicModal}
            onHide={() => {
              setShowSingleBasicModal(false);
            }}
          >
            {singleBasicMessage}
          </SingleBasicModal>
          <Row>
            <Col md={12} className="text-end mb-3">
              <Tooltip placement={"top"} text={"Nuevo contrato"}>
                <span>
                  <Authorization process={"CONT_ALTA"}>
                    <IconButton type={"add"} onClick={handleAltaContrato} />
                  </Authorization>
                </span>
              </Tooltip>
              <Tooltip placement={"top"} text={"Exportar a excel"}>
                <span>
                  <IconButton
                    type={"excel"}
                    onClick={handleGetReporte}
                    disabled={dataTable.length <= 0}
                  />
                </span>
              </Tooltip>
            </Col>
          </Row>
          <TablaPaginada
            idKeyName={ID_KEY_NAME}
            idKeyLink={ID_KEY_NAME}
            header={"Contratos"}
            headers={HEADERS}
            data={injectActions(dataTable, {
              edit: canEdit,
              show: true,
            })}
            actionFns={{ handleEdit, handleShow }}
            updateData={updateDataTable}
            pageable={pageable}
          />
        </Accordion>
      </Container>
    </>
  );
}
