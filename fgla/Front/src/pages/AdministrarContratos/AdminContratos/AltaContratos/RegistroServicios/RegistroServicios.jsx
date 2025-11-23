import React, { useEffect, useMemo, useRef, useState } from "react";
import { Button, Col, FormCheck, Row } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import {
  useDeleteRegistroServiciosMutation,
  useGetGrupoServiciosQuery,
  useGetRegistroServiciosQuery,
  useGetTipoUnidadQuery,
  useLazyGetValidarRegistroServiciosQuery,
  usePostRegistroServiciosMutation,
  usePostReporteRegistroServiciosMutation,
  usePutRegistroServiciosMutation,
  useGetDependenciasQuery,
} from "./store";
import {
  curateNewRegistros,
  rearrangeServicios,
  registroServiciosValidation,
  template,
} from "./utils";
import "./styles.css";
import { SelectCell, InputCell, InputCurrencyCell } from "../../../Components";
import { useParams, useSearchParams } from "react-router-dom";
import Loader from "../../../../../components/Loader";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import BasicModal from "../../../../../modals/BasicModal";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import { useToast } from "../../../../../hooks/useToast";
import _ from "lodash";
import { SelectGrupoCell } from "./components/SelectGrupoCell";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import Authorization from "../../../../../components/Authorization";
import { OpenText } from "../../../Components/OpenText";
import { TableDraggableCell } from "../../../../../components/table/TableDraggableCell";
function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}
export function RegistroServicios({ isDetalle }) {
  const { errorToast, infoToast } = useToast();
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const idNuevoContrato = searchParams.get("q");
  const tableReference = useRef();
  const [dataTable, setDataTable] = useState([]);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [deletedRegistros, setDeletedRegistros] = useState([]);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [showCancel, setShowCancel] = useState(false);
  const [showUndo, setShowUndo] = useState(false);
  const [showDrop, setShowDrop] = useState(false);
  const [selectedRow, setSelectedRow] = useState();

  const { data: grupos, isFetching: isLoadingGrupos } =
    useGetGrupoServiciosQuery(idContrato || idNuevoContrato, {
      skip: idContrato || idNuevoContrato !== undefined ? false : true,
    });
  const { data: dependencias, isFetching: isLoadingDependencias } =
    useGetDependenciasQuery(idContrato || idNuevoContrato, {
      skip: idContrato || idNuevoContrato !== undefined ? false : true,
    });

  const { data: registrosServicio, isFetching: isLoadingRegistroServicio } =
    useGetRegistroServiciosQuery(idContrato || idNuevoContrato, {
      skip: idContrato || idNuevoContrato !== undefined ? false : true,
    });
  const { data: tipoUnidad, isLoading: isLoadingTipoUnidad } =
    useGetTipoUnidadQuery();
  const [crearRegistro, { isLoading: isLoadingCrear }] =
    usePostRegistroServiciosMutation();
  const [actualizarRegistro, { isLoading: isLoadingActualizar }] =
    usePutRegistroServiciosMutation();
  const [eliminarRegistros, { isLoading: isLoadingEliminar }] =
    useDeleteRegistroServiciosMutation();
  const [validarRegistro, { isLoading: isLoadingValidar }] =
    useLazyGetValidarRegistroServiciosQuery();
  const [descargarReporte, { isLoading: isLoadingDescargar }] =
    usePostReporteRegistroServiciosMutation();

  useEffect(() => {
    if (registrosServicio) {
      const previousRegistros = rearrangeServicios(
        registrosServicio,
        idContrato || idNuevoContrato
      );
      setDataTable(previousRegistros);
      setMemoizedData(
        new Map(previousRegistros.map((registro) => [registro.UUID, registro]))
      );
      tableReference?.current?.table.toggleAllRowsSelected(false);
    }
  }, [registrosServicio]);

  const onAddRow = () => {
    const newRow = template();
    setDataTable((prev) => [...prev, newRow]);
    setMemoizedData((map) => new Map(map.set(newRow.UUID, newRow)));
  };
  const onReset = () => {
    const newData = [...memoizedData?.values()];
    const resetData = newData.reduce((prev, current) => {
      if (current.isNewRow) return prev;
      prev.push(current);
      return prev;
    }, []);
    setDataTable(resetData);
    setDeletedRegistros(() => []);
    tableReference?.current?.table.toggleAllRowsSelected(false);
    setShowCancel(false);
  };

  const onDrop = () => {
    const row = memoizedData.get(selectedRow);
    console.log(row);
    setDeletedRegistros((prev) => [...prev, row?.idServicioContrato]);
    tableReference.current.table.options.meta.removeRowById(selectedRow);
    setShowDrop(false);
  };
  const onDescargarReporte = async () => {
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
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };

  const onSubmit = async () => {
    try {
      if (dataTable?.length <= 0 && deletedRegistros?.length <= 0) {
        errorToast("No se ha agregado ningún campo en la tabla");
        return;
      }

      const registrosErrors = await registroServiciosValidation(dataTable);
      if (registrosErrors?.dataErrors) {
        errorToast(MODIFICAR_CONTRATO.MSG001);
        setDataTable(registrosErrors.resultados);
        return;
      }

      const newRegistros = [];
      const modifiedRegistros = [];
      dataTable.forEach((row) => {
        if (row.isNewRow) {
          newRegistros.push(row);
          return;
        }
        const memoizedRow = memoizedData.get(row.UUID);
        if (!_.isEqual(row, memoizedRow)) {
          modifiedRegistros.push(row);
          return;
        }
      });
      if (deletedRegistros.length > 0) {
        const data = deletedRegistros;
        await eliminarRegistros({ data }).unwrap();
      }
      if (newRegistros.length > 0) {
        const maxOrden =
          dataTable.length > 0
            ? Math.max(...dataTable.map((r) => r.orden || 0))
            : 0;
        let nextOrden = maxOrden + 1;

        const registrosConOrden = newRegistros.map((row) => ({
          ...row,
          orden: nextOrden++,
        }));

        const data = curateNewRegistros(
          registrosConOrden,
          idContrato || idNuevoContrato
        );

        await crearRegistro({ data }).unwrap();
      }
      if (modifiedRegistros.length > 0) {
        const registrosConOrden = modifiedRegistros.map((row, index) => ({
          ...row,
          orden: index + 1,
        }));

        const data = curateNewRegistros(
          registrosConOrden,
          idContrato || idNuevoContrato,
          true
        );



        await actualizarRegistro({ data }).unwrap();
      }
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
      tableReference?.current?.table.toggleAllRowsSelected(false);
    } catch (error) {
      const mensaje = error?.data?.mensaje[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };

  const handleValidate = async () => {
    try {
      if (dataTable.length <= 0) return;
      const res = await validarRegistro(idContrato || idNuevoContrato).unwrap();
      setSingleBasicMessage(res);
      setShowSingleBasic(true);
    } catch (error) {
      setSingleBasicMessage("Ocurrió un error.");
      setShowSingleBasic(true);
    }
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "drag-handle",
        header: "",
        cell: ({ row }) => (
          <TableDraggableCell
            rowId={row.id}
            row={row}
            isDisabled={dependencias}
          />
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
      {
        accessorKey: "id",
        header: "Id",
        cell: (props) => <>{props.row.index + 1}</>,
        filterFn: (row, _, filterValue) => {
          return (row?.index + 1)?.toString()?.includes(filterValue);
        },
      },
      {
        accessorKey: "grupo",
        header: "Grupo",
        cell: (props) => (
          <SelectGrupoCell
            row={props.row}
            column={props.column}
            name={"grupoServicio"}
            table={props.table}
            getValue={props.getValue}
            options={grupos}
            displayValue={"grupoDisplay"}
          />
        ),
        filterFn: (row, columnId, filterValue) => {
          return row?.original?.grupoDisplay
            ?.toLowerCase()
            ?.trim()
            ?.includes(filterValue.toLowerCase());
        },
      },
      {
        accessorKey: "tipoConsumo",
        header: "Tipo de consumo",
        cell: (props) => <> {props.getValue()}</>,
      },
      {
        accessorKey: "claveProductoServicio",
        header: "Clave productos y servicios",
        cell: (props) => (
          <OpenText
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"claveProductoServicio"}
          />
        ),
      },
      {
        accessorKey: "conceptoServicio",
        header: "Conceptos de servicio",
        cell: (props) => (
          <OpenText
            acceptSpaces
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"conceptoServicio"}
          />
        ),
      },
      {
        accessorKey: "tipoUnidad",
        header: "Tipo de unidad",
        cell: (props) => (
          <SelectCell
            options={tipoUnidad}
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"tipoUnidad"}
            keyValue={"primaryKey"}
            keyTextValue={"nombre"}
            displayValue={"tipoUnidadDisplay"}
          />
        ),
        filterFn: (row, columnId, filterValue) => {
          return row?.original?.tipoUnidadDisplay
            ?.toLowerCase()
            ?.trim()
            ?.includes(filterValue.toLowerCase());
        },
      },
      {
        accessorKey: "precioUnitario",
        header: "Precio unitario",
        cell: (props) => (
          <InputCell
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"precioUnitario"}
            isPrecioUnitario
          />
        ),
      },
      {
        accessorKey: "cantidadServiciosMinima",
        header: "Cantidad de servicios mínima",
        cell: (props) => (
          <InputCell
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"cantidadServiciosMinima"}
            isBolsa
            decimals={6}
            displayDecimals={6}
            nonDependent={false}
            options={{
              valueOperation: () => {
                return (
                  safeParseFloat(props.row.original.montoMinimo) /
                  safeParseFloat(props.row.original.precioUnitario)
                );
              },
            }}
          />
        ),
      },
      {
        accessorKey: "cantidadServicios",
        header: "Cantidad de servicios máxima",
        cell: (props) => (
          <InputCell
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"cantidadServicios"}
            isBolsa
            nonDependent={false}
            decimals={6}
            displayDecimals={6}
            options={{
              valueOperation: () => {
                return (
                  safeParseFloat(props.row.original.montoMaximo) /
                  safeParseFloat(props.row.original.precioUnitario)
                );
              },
            }}
          />
        ),
      },
      {
        accessorKey: "montoMinimo",
        header: "Monto mínimo",
        cell: (props) => (
          <InputCurrencyCell
            row={props.row}
            getValue={props.getValue}
            column={props.column}
            table={props.table}
            name={"montoMinimo"}
            decimals={2}
            nonDependent={false}
            options={{
              valueOperation: () =>
                parseFloat(
                  String(props.row.original.cantidadServiciosMinima).replace(
                    /,/g,
                    ""
                  )
                ) *
                parseFloat(
                  String(props?.row?.original?.precioUnitario).replace(/,/g, "")
                ),
            }}
          />
        ),
      },
      {
        accessorKey: "montoMaximo",
        header: "Monto máximo",
        cell: (props) => (
          <>
            <InputCurrencyCell
              row={props.row}
              getValue={props.getValue}
              column={props.column}
              table={props.table}
              name={"montoMaximo"}
              decimals={2}
              nonDependent={false}
              options={{
                valueOperation: () =>
                  parseFloat(
                    String(props.row.original.cantidadServicios).replace(
                      /,/g,
                      ""
                    )
                  ) *
                  parseFloat(
                    String(props.row.original?.precioUnitario).replace(/,/g, "")
                  ),
              }}
            />
          </>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "aplicaIEPS",
        header: "Aplica IEPS",
        cell: (props) => (
          <div className="check-box-black">
            <FormCheck
              checked={props.getValue()}
              onChange={(e) => {
                if (props.row.getIsSelected() || props.row.original.isNewRow) {
                  props.table.options.meta.updateData(
                    props.row.index,
                    props.column.id,
                    !props.getValue()
                  );
                }
              }}
              disabled={
                (!props.row.getIsSelected() && !props.row.original.isNewRow) ||
                isDetalle
              }
            />
          </div>
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <>
            {props.row.getIsSelected() || props.row.original.isNewRow ? (
              <IconButton
                type={"undo"}
                iconSize="lg"
                tooltip={"Cancelar"}
                onClick={() => {
                  setSelectedRow({
                    ...props.row,
                    UUID: props.row.original.UUID,
                  });
                  setShowUndo(true);
                }}
                disabled={isDetalle}
              />
            ) : (
              <>
                <Authorization process={"CONT_RDS_ADMIN"}>
                  <IconButton
                    type={"edit"}
                    iconSize="lg"
                    tooltip={"Editar"}
                    onClick={() => {
                      props.row.toggleSelected(!props.row.getIsSelected(), {
                        selectChildren: false,
                      });
                    }}
                    disabled={isDetalle}
                  />
                  <IconButton
                    type={"drop"}
                    iconSize="lg"
                    tooltip={"Eliminar"}
                    onClick={() => {
                      setSelectedRow(props.row.original.UUID);
                      setShowDrop(true);
                    }}
                    disabled={isDetalle}
                  />
                </Authorization>
              </>
            )}
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
    ],
    [grupos, tipoUnidad]
  );
  return (
    <>
      {isLoadingGrupos ||
        isLoadingCrear ||
        isLoadingActualizar ||
        isLoadingValidar ||
        isLoadingRegistroServicio ||
        isLoadingEliminar ||
        isLoadingTipoUnidad ||
        isLoadingDescargar ? (
        <Loader zIndex={`${isLoadingRegistroServicio ? "10" : "9999"}`} />
      ) : null}
      <Row>
        <Col md={12} className="text-end">
          <Authorization process={"CONT_RDS_ADMIN"}>
            <IconButton
              type={"add"}
              tooltip={"Agregar"}
              onClick={() => {
                onAddRow();
              }}
              disabled={isDetalle}
            />
          </Authorization>
          <IconButton
            type={"excel"}
            tooltip={"Exportar a excel"}
            onClick={() => onDescargarReporte()}
            disabled={registrosServicio?.length <= 0}
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
            isDraggable
            rowId={"idServicioContrato"}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12} className="text-end">
          {!isDetalle ? (
            <>
              <Authorization process={"CONT_RDS_BTN_VALIDAR"}>
                <Button
                  variant="gray"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={handleValidate}
                >
                  Validar
                </Button>
              </Authorization>
              <Authorization process={"CONT_RDS_ADMIN"}>
                <Button
                  variant="red"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={() => {
                    if (dataTable.length <= 0 && deletedRegistros.length <= 0)
                      return;
                    setShowCancel(true);
                  }}
                >
                  Cancelar
                </Button>
                <Button
                  variant="green"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={onSubmit}
                >
                  Guardar
                </Button>
              </Authorization>
            </>
          ) : null}
        </Col>
      </Row>
      <SingleBasicModal
        show={showSingleBasic}
        onHide={() => setShowSingleBasic(false)}
        approveText={"Aceptar"}
        title={"Mensaje"}
        size={"md"}
      >
        {singleBasicMessage}
      </SingleBasicModal>
      <BasicModal
        show={showCancel}
        onHide={() => setShowCancel(false)}
        title={"Mensaje"}
        size={"md"}
        approveText={"Sí"}
        denyText={"No"}
        handleDeny={() => setShowCancel(false)}
        handleApprove={onReset}
      >
        {MODIFICAR_CONTRATO.MSG002}
      </BasicModal>
      <BasicModal
        show={showDrop}
        onHide={() => setShowDrop(false)}
        title={"Mensaje"}
        size={"md"}
        approveText={"Sí"}
        denyText={"No"}
        handleDeny={() => setShowDrop(false)}
        handleApprove={onDrop}
      >
        {MODIFICAR_CONTRATO.MSG017}
      </BasicModal>
      <BasicModal
        show={showUndo}
        onHide={() => setShowUndo(false)}
        title={"Mensaje"}
        size={"md"}
        approveText={"Sí"}
        denyText={"No"}
        handleDeny={() => setShowUndo(false)}
        handleApprove={() => {
          const memoizedRow = memoizedData.get(selectedRow.UUID);
          if (memoizedRow.isNewRow) {
            tableReference.current.table.options.meta.removeRowById(
              selectedRow.UUID
            );
            selectedRow.toggleSelected(false);
          } else {
            tableReference.current.table.options.meta.updateRowById(
              selectedRow.UUID,
              memoizedRow
            );
            selectedRow.toggleSelected(false);
          }
          setShowUndo(false);
        }}
      >
        {MODIFICAR_CONTRATO.MSG017}
      </BasicModal>
    </>
  );
}
