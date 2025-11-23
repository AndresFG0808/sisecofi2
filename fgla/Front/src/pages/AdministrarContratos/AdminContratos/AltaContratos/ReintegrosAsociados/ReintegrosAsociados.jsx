import React, { useEffect, useMemo, useState } from "react";
import { Col, Row } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import TextFieldIcon from "../../../../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import {
  useGetReintegrosByContratoQuery,
  useLazyGetReporteReintegrosQuery,
} from "./store";
import { useParams, useSearchParams } from "react-router-dom";
import Loader from "../../../../../components/Loader";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import { FormatMoney } from "../../../../../functions/utils";
import moment from "moment";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";

export function ReintegrosAsociados() {
  const [dataTable, setDataTable] = useState([]);
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);

  const { data: reintegros, isFetching: isFetchingReintegros } =
    useGetReintegrosByContratoQuery(idContrato || idNuevoContrato);
  const [descargarReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReporteReintegrosQuery();

  useEffect(() => {
    if (reintegros) {
      setDataTable(() => reintegros.reintegrosAsociados);
    }
  }, [reintegros]);

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
        accessorKey: "no",
        header: "No",
        cell: (props) => <>{props.row.index + 1}</>,
        enableColumnFilter: false,
      },
      {
        accessorKey: "nombreTipoReintegro",
        header: "Tipo",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "importe",
        header: "Importe",
        cell: (props) => <>${FormatMoney(props.getValue(), 2)}</>,
      },
      {
        accessorKey: "interes",
        header: "Interés",
        cell: (props) => <>${FormatMoney(props.getValue(), 2)}</>,
      },
      {
        accessorKey: "total",
        header: "Total",
        cell: (props) => <>${FormatMoney(props.getValue(), 2)}</>,
      },
      {
        accessorKey: "fechaReintegro",
        header: "Fecha de reintegro",
        cell: (props) => (
          <>
            {props.getValue()
              ? moment(new Date(props.getValue())).format("DD/MM/YYYY")
              : ""}
          </>
        ),
      },
    ],
    []
  );
  return (
    <>
      {isFetchingReintegros || isLoadingReporte ? <Loader /> : null}
      <Row>
        <Col md={12} className="text-end mb-2 mt-4">
          <IconButton
            type={"excel"}
            tooltip={"Exportar a Excel"}
            onClick={handleDownloadReporte}
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

      <Row>
        <Col md={4}>
          <TextFieldIcon
            label={"Σ Importes"}
            value={FormatMoney(reintegros?.importes, 2)}
            disabled={true}
            startIcon={faDollarSign}
          />
        </Col>
        <Col md={4}>
          <TextFieldIcon
            label={"Σ Intereses"}
            value={FormatMoney(reintegros?.intereses, 2)}
            disabled={true}
            startIcon={faDollarSign}
          />
        </Col>
        <Col md={4}>
          <TextFieldIcon
            label={"Σ Totales"}
            value={FormatMoney(reintegros?.totales, 2)}
            disabled={true}
            startIcon={faDollarSign}
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
