import React, { useEffect, useMemo, useRef, useState } from "react";
import { TablaEditable } from "../../../../../../../components/table/TablaEditable";
import { Button, Col, FormCheck, Row } from "react-bootstrap";
import IconButton from "../../../../../../../components/buttons/IconButton";
import {
  useGetServiciosConvenioQuery,
  useLazyGetReporteServiciosConvenioQuery,
  usePostGuardarRegistroServiciosConvenioMutation,
  usePostValidarServiciosConvenioMutation,
} from "./store";
import { Loader } from "../../../../../../../components";
import { useParams, useSearchParams } from "react-router-dom";
import {
  InputCurrencyCell,
  InputMaxCurrency,
  InputMaxService,
  InputPrecio,
} from "./components";
import { InputCell } from "./components/InputCell";
import { FormatMoney } from "../../../../../../../functions/utils";
import { DownloadFileBase64 } from "../../../../../../../functions/utils/base64.utils";
import { rearrange } from "./utils";
import _ from "lodash";
import BasicModal from "../../../../../../../modals/BasicModal";
import SingleBasicModal from "../../../../../../../modals/SingleBasicModal";
import "./styles.css";
import { useErrorMessages } from "../../../../../../../hooks/useErrorMessages";
import { EDITAR_CONVENIOS } from "../../../../../../../constants/messages";

import { useGetAuthorization } from "../../../../../../../hooks/useGetAuthorization";
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}
export function RegistroServicios({ isDetalle }) {
  const { isAuthorized } = useGetAuthorization(["CONT_CM_ADMIN"]);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const { getMessageExists } = useErrorMessages(EDITAR_CONVENIOS);
  const tableReference = useRef();
  const { idConvenio } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoConvenio = searchParams.get("cm");
  const [dataTable, setDataTable] = useState([]);
  const [showUndoModal, setShowUndoModal] = useState(false);
  const [selectedRow, setSelectedRow] = useState();
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [showCancelar, setShowCancelar] = useState(false);
  const readOnly = isDetalle || !isAuthorized;

  const { data: registroServicios, isFetching: isLoadingServicios } =
    useGetServiciosConvenioQuery(idConvenio || idNuevoConvenio);
  const [descargarReporte, { isLoading: isLoadingDescargar }] =
    useLazyGetReporteServiciosConvenioQuery();

  const [guardarRegistro, { isLoading: isLoadingGuardar }] =
    usePostGuardarRegistroServiciosConvenioMutation();

  const [validarServicios, { isLoading: isLoadingValidar }] =
    usePostValidarServiciosConvenioMutation();

  useEffect(() => {
    if (registroServicios) {
      const prevRegistros = rearrange(registroServicios);
      setDataTable(prevRegistros);
      setMemoizedData(
        new Map(prevRegistros?.map((servicio) => [servicio.UUID, servicio]))
      );
      //tableReference?.current?.table.toggleAllRowsSelected(false);
    }
  }, [registroServicios]);

  const handleDescargarReporte = async () => {
    try {
      const res = await descargarReporte(
        idConvenio || idNuevoConvenio
      ).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      setSingleBasicMessage("Ocurrió un error. Favor de intentar más tarde.");
      setShowSingleBasic(true);
    }
  };
  const handleSubmit = async () => {
    try {
      const modifiedData = dataTable?.reduce((prevRows, currentRow) => {
        const memoizedRow = memoizedData.get(currentRow.UUID);
        if (!_.isEqual(memoizedRow, currentRow)) {
          _.unset(currentRow, "UUID");
          const curatedRow = _.mapValues(currentRow, (value) =>
            value === null ? 0 : value
          );
          prevRows.push(curatedRow);
        }
        return prevRows;
      }, []);
      await guardarRegistro({ data: modifiedData }).unwrap();
      setSingleBasicMessage("Se guardó correctamente la información.");
      setShowSingleBasic(true);
      tableReference?.current?.table.toggleAllRowsSelected(false);
    } catch (error) {
      const mensaje = error?.data?.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage(EDITAR_CONVENIOS.MSG018);
        setShowSingleBasic(true);
      }
    }
  };
  const handleValidate = async () => {
    try {
      const data = dataTable?.reduce((prevRows, currentRow) => {
        _.unset(currentRow, "UUID");
        prevRows.push(currentRow);

        return prevRows;
      }, []);
      const res = await validarServicios({ data }).unwrap();
      setSingleBasicMessage(res);
      setShowSingleBasic(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage(EDITAR_CONVENIOS.MSG018);
        setShowSingleBasic(true);
      }
    }
  };
  const handleReset = () => {
    if (registroServicios) {
      const prevRegistros = rearrange(registroServicios);
      setDataTable(prevRegistros);
      setMemoizedData(
        new Map(prevRegistros?.map((servicio) => [servicio.UUID, servicio]))
      );
      tableReference?.current?.table.toggleAllRowsSelected(false);
    } else {
      setDataTable([]);
      memoizedData(new Map());
      tableReference?.current?.table.toggleAllRowsSelected(false);
    }
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "id",
        header: "Id",
        cell: (props) => <>{props.row.index + 1}</>,
      },
      {
        accessorKey: "tipoConsumo",
        header: "Tipo de consumo",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "concepto",
        header: "Conceptos de servicio",
        cell: (props) => <>{props.getValue()}</>,
      },
      {
        accessorKey: "numeroMaximoServicios",
        header: "Número de servicios máximos",
        cell: (props) => <>{props.getValue() ?? 0}</>,
        enableColumnFilter: false,
      },
      {
        accessorKey: "montoMaximo",
        header: "Monto máximo",
        cell: (props) => (
          <>${props.getValue() ? FormatMoney(props.getValue(), 2) : 0}</>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "precioUnitario",
        header: "Precio unitario",
        cell: (props) => (
          <>
            <InputPrecio
              isDetalle={readOnly}
              name={"precio"}
              getValue={props.getValue}
              row={props.row}
              column={props.column}
              table={props.table}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "compensacionServicios",
        header: "Compensación (Número de servicios)",
        cell: (props) => (
          <>
            <InputCell
              acceptNegative
              isDetalle={readOnly}
              getValue={props.getValue}
              row={props.row}
              column={props.column}
              table={props.table}
              options={{
                calculateValue: () => {
                  return (
                    safeParseFloat(props?.row?.original?.compensacionMonto) /
                    safeParseFloat(props?.row?.original?.precioUnitario)
                  ).toFixed(6);
                },
                sideEffect: (value) => {
                  return (
                    value * safeParseFloat(props?.row?.original?.precioUnitario)
                  ).toFixed(2);
                },
                changedKey: "compensacionMonto",
                activationKey: "alcance",
              }}
              convenio={registroServicios.tipoConvenio}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "compensacionMonto",
        header: "Compensación (Monto)",
        cell: (props) => (
          <>
            <InputCurrencyCell
              acceptNegative
              isDetalle={readOnly}
              getValue={props.getValue}
              row={props.row}
              column={props.column}
              table={props.table}
              options={{
                calculateValue: () => {
                  const result =
                    safeParseFloat(
                      props?.row?.original?.compensacionServicios
                    ) * safeParseFloat(props?.row?.original?.precioUnitario);
                  return result.toFixed(2);
                },
                sideEffect: (value) => {
                  return (
                    value / safeParseFloat(props?.row?.original?.precioUnitario)
                  ).toFixed(6);
                },
                changedKey: "compensacionServicios",
                activationKey: "alcance",
              }}
              convenio={registroServicios.tipoConvenio}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "incrementoServicios",
        header: "Incremento (Número de servicios)",
        cell: (props) => (
          <>
            <InputCell
              isDetalle={readOnly}
              getValue={props.getValue}
              row={props.row}
              column={props.column}
              table={props.table}
              options={{
                calculateValue: () => {
                  return (
                    safeParseFloat(props?.row?.original?.incrementoMonto) /
                    safeParseFloat(props?.row?.original?.precioUnitario)
                  ).toFixed(6);
                },
                sideEffect: (value) => {
                  return (
                    value * safeParseFloat(props?.row?.original?.precioUnitario)
                  ).toFixed(2);
                },
                changedKey: "incrementoMonto",
                activationKey: "monto",
              }}
              convenio={registroServicios.tipoConvenio}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "incrementoMonto",
        header: "Incremento (Monto)",
        cell: (props) => (
          <>
            <InputCurrencyCell
              isDetalle={readOnly}
              getValue={props.getValue}
              row={props.row}
              column={props.column}
              table={props.table}
              options={{
                calculateValue: () => {
                  const result =
                    safeParseFloat(props?.row?.original?.incrementoServicios) *
                    safeParseFloat(props?.row?.original?.precioUnitario);
                  return result.toFixed(2);
                },
                sideEffect: (value) => {
                  return (
                    value / safeParseFloat(props?.row?.original?.precioUnitario)
                  ).toFixed(6);
                },
                changedKey: "incrementoServicios",
                activationKey: "monto",
              }}
              convenio={registroServicios.tipoConvenio}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "numeroTotalServicios",
        header: "Número total de servicios",
        cell: (props) => (
          <>
            <InputMaxService
              row={props.row}
              readOnly={props.readOnly}
              getValue={props.getValue}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "montoMaximoTotal",
        header: "Monto máximo total",
        cell: (props) => (
          <>
            <InputMaxCurrency
              row={props.row}
              readOnly={readOnly}
              getValue={props.getValue}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "ieps",
        header: "Aplica IEPS",
        cell: (props) => (
          <>
            <FormCheck
              checked={props.getValue()}
              onChange={(e) => {
                if (
                  props.row.getIsSelected() ||
                  props.row.original.primeraVez === true
                ) {
                  props.table.options.meta.updateData(
                    props.row.index,
                    props.column.id,
                    !props.getValue()
                  );
                }
              }}
              disabled={
                readOnly ||
                (!props.row.getIsSelected() &&
                  props.row.original.primeraVez !== true)
              }
            />
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <>
            {(props.row.getIsSelected() ||
              props.row.original?.primeraVez === true) &&
            !readOnly ? (
              <IconButton
                type={"undo"}
                iconSize={"lg"}
                onClick={() => {
                  setSelectedRow({
                    ...props.row,
                    UUID: props.row.original.UUID,
                  });
                  setShowUndoModal(true);
                }}
                isDetalle={readOnly}
              />
            ) : !readOnly ? (
              <IconButton
                type={"edit"}
                iconSize={"lg"}
                tooltip={"Editar"}
                onClick={() => {
                  props.row.toggleSelected(!props.row.getIsSelected(), {
                    selectChildren: false,
                  });
                }}
                disabled={readOnly}
              />
            ) : null}
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
    ],
    [registroServicios]
  );
  return (
    <>
      {isLoadingServicios ||
      isLoadingDescargar ||
      isLoadingGuardar ||
      isLoadingValidar ? (
        <Loader zIndex={isLoadingServicios ? "10" : "9999"} />
      ) : null}
      {isLoadingServicios || showSingleBasic ? (
        <div>
          <div className="skeleton" style={{ width: "100%", height: 40 }} />
          <div className="skeleton" style={{ width: "100%", height: 40 }} />
          <div className="skeleton" style={{ width: "100%", height: 40 }} />
          <div className="skeleton" style={{ width: "100%", height: 40 }} />
          <div className="skeleton" style={{ width: "100%", height: 40 }} />
        </div>
      ) : (
        <div>
          <Row className="registro-servicios">
            <Col md={12} className="text-end">
              <IconButton
                type={"excel"}
                tooltip={"Exportar a excel"}
                disabled={dataTable?.length <= 0}
                onClick={handleDescargarReporte}
              />
            </Col>
          </Row>
          <Row>
            <Col md={12} className="registro-servicios">
              <TablaEditable
                columns={columns}
                dataTable={dataTable}
                hasPagination
                ref={tableReference}
                onUpdate={setDataTable}
                onDelete={setDataTable}
              />
            </Col>
          </Row>
        </div>
      )}

      <Row>
        <Col md={12} className="text-end">
          {!readOnly ? (
            <>
              <Button
                variant="gray"
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={handleValidate}
              >
                Validar
              </Button>
              <Button
                variant="red"
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={() => {
                  if (dataTable.length <= 0) return;
                  setShowCancelar(true);
                }}
              >
                Cancelar
              </Button>
              <Button
                variant="green"
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={handleSubmit}
              >
                Guardar
              </Button>
            </>
          ) : null}
        </Col>
      </Row>

      <BasicModal
        title={"Mensaje"}
        size={"md"}
        approveText={"Sí"}
        denyText={"No"}
        onHide={() => {
          setShowUndoModal(false);
        }}
        handleDeny={() => {
          setShowUndoModal(false);
        }}
        handleApprove={() => {
          const memoizedRow = memoizedData.get(selectedRow.UUID);
          tableReference.current.table.options.meta.updateRowById(
            selectedRow.UUID,
            memoizedRow
          );
          selectedRow.toggleSelected(false);
          setShowUndoModal(false);
        }}
        show={showUndoModal}
      >
        {"¿Desea cancelar? Los cambios modificados se perderán."}
      </BasicModal>
      <BasicModal
        show={showCancelar}
        title={"Mensaje"}
        size={"md"}
        approveText={"Sí"}
        denyText={"No"}
        onHide={() => setShowCancelar(false)}
        handleDeny={() => setShowCancelar(false)}
        handleApprove={() => {
          handleReset();
          setShowCancelar(false);
        }}
      >
        {"¿Desea cancelar? Los cambios modificados se perderán."}
      </BasicModal>
      <SingleBasicModal
        show={showSingleBasic}
        onHide={() => setShowSingleBasic(false)}
        approveText={"Aceptar"}
        title={"Mensaje"}
        size={"md"}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
