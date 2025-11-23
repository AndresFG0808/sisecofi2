import React, { useEffect, useMemo, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { Link, useParams, useSearchParams } from "react-router-dom";
import moment from "moment";
import {
  useGetDictamenAsociadoQuery,
  useLazyGetReporteDictamenesQuery,
} from "./store";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import Loader from "../../../../../components/Loader";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";

export function DictamenesAsociados() {
  const [dataTable, setDataTable] = useState([]);
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");

  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);

  const { data: dictamenesAsociados, isLoading: isLoadingDictamenes } =
    useGetDictamenAsociadoQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });
  const [getReporte, { isLoading: isLoadingReporte, isFetching }] =
    useLazyGetReporteDictamenesQuery();

  useEffect(() => {
    if (dictamenesAsociados) {
      setDataTable(dictamenesAsociados);
    }
  }, [dictamenesAsociados]);

  const onGetReporte = async () => {
    try {
      const res = await getReporte(idContrato || idNuevoContrato).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };
  const columns = useMemo(
    () => [
      {
        accessorKey: "id",
        header: "Id del dictamen",
        cell: (props) => {
          //console.log(props.row.original);
          const dictamenState = {
            idDictamen: props.row.original?.idBd,
            idContrato: parseInt(idContrato || idNuevoContrato),
            idDictamenVisual: props.getValue(),
            idProveedor: props.row.original?.idProveedor,
            editableDuplicateButton: true,
            editable: true,
          }
          return (
            <Link
              to="/consumoServicios/consumoServicios/dictamen"
              state={{ dictamenState }}
            >
              {props.getValue()}
            </Link>
          );
        },
      },
      {
        accessorKey: "periodoControl",
        header: "Periodo control",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "periodoInicio",
        header: "Periodo inicio",
        cell: (props) => <>{moment(props.getValue()).format("DD/MM/YYYY")}</>,
      },
      {
        accessorKey: "periodoFin",
        header: "Periodo fin",
        cell: (props) => <>{moment(props.getValue()).format("DD/MM/YYYY")}</>,
      },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "montoDictaminado",
        header: "Monto",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "montoDictaminadoPesos",
        header: "Monto en pesos",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "tipoCambioReferencial",
        header: "Tipo de cambio referencial",
        cell: (props) => <>{props.getValue()}</>,
      },
    ],
    []
  );

  return (
    <>
      {isLoadingDictamenes || isLoadingReporte || isFetching ? (
        <Loader />
      ) : null}
      <Row>
        <Col md={12} className="text-end mb-3">
          <IconButton
            type={"excel"}
            tooltip={"Exportar a Excel"}
            onClick={() => {
              onGetReporte();
            }}
            disabled={dataTable?.length <= 0}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <TablaEditable
            dataTable={dataTable}
            columns={columns}
            hasPagination
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
