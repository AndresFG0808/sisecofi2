import React, { useEffect, useMemo, useState } from "react";
import { Col, Row } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import { Link, useParams, useSearchParams } from "react-router-dom";
import {
  useGetObtenerFacturasQuery,
  useLazyGetReporteFacturasQuery,
} from "./store";
import { Loader } from "../../../../../components";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import { FormatMoney } from "../../../../../functions/utils";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import { Tooltip } from "../../../../../components/Tooltip";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
export function FacturasAsociadas() {
  const [dataTable, setDataTable] = useState([]);
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");

  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);

  const { data: facturasAsociadas, isLoading: isLoadingFacturasAsociadas } =
    useGetObtenerFacturasQuery(idContrato || idNuevoContrato, {
      skip:
        idContrato !== undefined || idNuevoContrato !== undefined
          ? false
          : true,
    });
  const [getReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReporteFacturasQuery();

  useEffect(() => {
    if (facturasAsociadas) {
      setDataTable(() => facturasAsociadas);
    }
  }, [facturasAsociadas]);

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
        accessorKey: "idDictamen",
        header: "Id del dictamen",
        cell: (props) => (
          <>
            <Link
              to={"/consumoServicios/consumoServicios/dictamen"}
              state={{
                dictamenState: {
                  idDictamen: props.getValue(),
                  idContrato: parseInt(idContrato || idNuevoContrato),
                  idProveedor: props.row.original?.idProveedor,
                  editableDuplicateButton: true,
                  editable: true,
                },
              }}
            >
              {props.getValue()}
            </Link>
          </>
        ),
      },
      {
        accessorKey: "comprobanteFiscal",
        header: "Comprobante fiscal",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "convenioColaboracion",
        header: "Convenio de colaboración",
        cell: (props) => (
          <>{props.getValue() ? <FontAwesomeIcon icon={faCheck} /> : ""}</>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
      {
        accessorKey: "monto",
        header: "Monto",
        cell: (props) => <>${FormatMoney(props.getValue(), 2)}</>,
      },
      {
        accessorKey: "montoPesos",
        header: "Monto en pesos",
        cell: (props) => <>${FormatMoney(props.getValue(), 2)}</>,
      },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "tipoCambioReferencial",
        header: "Tipo de cambio",
        cell: (props) => (
          <>
            <Tooltip
              placement="top"
              text={
                "En caso de que el estatus sea pagado el tipo de cambio es real a la fecha de pago"
              }
            >
              <span>
                {`${props.getValue() ? "$" : ""}`}
                {FormatMoney(props.getValue(), 4)}
              </span>
            </Tooltip>
          </>
        ),
      },
    ],
    []
  );
  return (
    <>
      {isLoadingFacturasAsociadas || isLoadingReporte ? <Loader /> : null}
      <Row>
        <Col md={12} className="text-end mb-2 mt-4">
          <IconButton
            type={"excel"}
            onClick={onGetReporte}
            disabled={dataTable?.length <= 0}
            tooltip={"Exportar a Excel"}
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
