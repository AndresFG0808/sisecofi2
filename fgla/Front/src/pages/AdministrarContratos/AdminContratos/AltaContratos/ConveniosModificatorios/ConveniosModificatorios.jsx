import React, { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { Col, Row } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import TablaPaginada from "../../../../../components/table/TablaPaginada";
import {
  useGetConveniosByContratoQuery,
  usePostFindConveniosMutation,
  usePostReporteConveniosMutation,
} from "./Convenio/store";
import { Loader } from "../../../../../components";
import { injectActions } from "../../../../../functions/utils";
import { rearrangeData } from "./utils";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import { useGetAuthorization } from "../../../../../hooks/useGetAuthorization";
import Authorization from "../../../../../components/Authorization";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";

const HEADERS = [
  {
    dataField: "numeroConvenio",
    text: "Número de convenio",
    filter: true,
    sort: true,
  },
  { dataField: "tipo", text: "Tipo", filter: true, sort: true },
  { dataField: "fechaFirma", text: "Fecha de firma", filter: true, sort: true },
  { dataField: "fechaFin", text: "Fecha fin", filter: true, sort: true },
  { dataField: "montoMaximo", text: "Monto máximo", filter: true, sort: true },
  { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];
const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};

export function ConveniosModificatorios({ isDetalle }) {
  const ID_KEY_NAME = "idConvenioModificatorio";
  const { idContrato } = useParams();
  // const { getMessageExists } = useErrorMessages();
  const [searchParams] = useSearchParams();
  const [consulta, setConsulta] = useState({});
  const idNuevoContrato = searchParams.get("q");
  const navigate = useNavigate();
  const [dataTable, setDataTable] = useState([]);
  const [pageable, setPageable] = useState(PAGEABLE);
  const { isAuthorized: isAdmin } = useGetAuthorization("CONT_CM_ADMIN");
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);

  const [buscarConvenios, { data: convenios, isFetching, isLoading }] =
    usePostFindConveniosMutation();
  const [descargarReporte, { isLoading: isLoadingReporte }] =
    usePostReporteConveniosMutation();

  useEffect(() => {
    const data = {
      size: 15,
      page: 0,
      idContrato: parseInt(idContrato || idNuevoContrato),
    };
    buscarConvenios({ data });
  }, []);
  useEffect(() => {
    if (convenios) {
      setDataTable(() => rearrangeData(convenios));
      setPageable({
        totalPages: convenios?.totalPages,
        totalElements: convenios?.totalElements,
        pageNumber: convenios?.number,
        size: convenios?.size,
      });
    }
  }, [convenios]);

  const handleEdit = (id) => () => {
    const currentConvenio = dataTable.find(
      ({ idConvenioModificatorio }) => idConvenioModificatorio === id
    );
    const path = `/contratos/contratos/editar/${
      idContrato || idNuevoContrato
    }/convenio/editar/${currentConvenio.idConvenioModificatorio}`;
    navigate(path);
  };
  const handleShow = (id) => () => {
    const currentConvenio = dataTable.find(
      ({ idConvenioModificatorio }) => idConvenioModificatorio === id
    );
    const path = `/contratos/contratos/editar/${
      idContrato || idNuevoContrato
    }/convenio/verDetalle/${currentConvenio.idConvenioModificatorio}`;
    navigate(path);
  };
  const handleDownloadReporte = async () => {
    try {
      const res = await descargarReporte(
        idContrato || idNuevoContrato
      ).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      if (error) {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };

  const updateDataTable = (values) => {
    const data = {
      ...consulta,
      ...values,
      idContrato: parseInt(idContrato || idNuevoContrato),
    };
    setConsulta(data);
    buscarConvenios({ data });
  };
  return (
    <>
      {isLoading || isFetching || isLoadingReporte ? <Loader /> : null}
      <Row>
        <Col md={12} className="text-end mb-2 mt-4">
          <Authorization process={"CONT_CM_ADMIN"}>
            <IconButton
              type={"add"}
              onClick={() =>
                navigate(
                  `/contratos/contratos/editar/${
                    idContrato || idNuevoContrato
                  }/convenio/alta`
                )
              }
              disabled={isDetalle || !isAdmin}
              tooltip={"Agregar"}
            />
          </Authorization>
          <IconButton
            type={"excel"}
            disabled={dataTable?.length <= 0}
            onClick={handleDownloadReporte}
            tooltip={"Exportar a Excel"}
          />
        </Col>
      </Row>
      <Row>
        <Col>
          <TablaPaginada
            idKeyName={ID_KEY_NAME}
            idKeyLink={ID_KEY_NAME}
            headers={HEADERS}
            data={injectActions(dataTable, { edit: isAdmin, show: true })}
            actionFns={{ handleEdit, handleShow }}
            updateData={updateDataTable}
            pageable={pageable}
          />
        </Col>
      </Row>

      <SingleBasicModal
        size={"md"}
        show={showSingleBasic}
        approveText={"Aceptar"}
        title={"Mensaje"}
        onHide={() => {
          setShowSingleBasic(false);
        }}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
