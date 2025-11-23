import React, { useEffect, useMemo, useRef, useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Tooltip } from "../../../../components/Tooltip";
import IconButton from "../../../../components/buttons/IconButton";
import { EditableCell } from "../FichaTecnica/Components/EditableCell";
import { ASOCIAR_FASES } from "../../../../constants/messages";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import {
  useGetAsociacionesQuery,
  useLazyGetReporteQuery,
  useGetFasesQuery,
  usePostAsociacionesMutation,
  usePostCrearAsociacionMutation,
} from "./store";
import Loader from "../../../../components/Loader";
import { useSelector } from "react-redux";
import BasicModal from "../../../../modals/BasicModal";
import { DependentSelectCell } from "./components";
import moment from "moment";
import { DateInputCell } from "./components/DateInputCell";
import { nanoid } from "nanoid";
import {
  asociacionesRequiredValidations,
  isIdPlantillaRepeated,
} from "./utils";
import { useToast } from "../../../../hooks/useToast";
import "./styles.css";
import _ from "lodash";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import { useDispatch } from "react-redux";
import { GetDetalleProyecto } from "../../../../store/infoComites/infoComitesActions";
import { useGetAuthorization } from "../../../../hooks/useGetAuthorization";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";

const rearrangeAsociaciones = (data) => {
  if (!data) return [];
  const newArray = data?.map((asociacion) => {
    return {
      UUID: nanoid(),
      primaryKey: asociacion.fase.primaryKey,
      idAsociacion: asociacion.idAsociacion,
      fase: asociacion.fase.primaryKey,
      plantilla: asociacion.plantilla.idPlantillaVigente,
      fechaAsignacion: asociacion.fechaAsignacion,
      isNewRow: false,
      displayPlantilla: asociacion?.plantilla?.nombre,
      displayFase: asociacion?.fase?.nombre,
      cargado: asociacion.cargado,
      lastCachedValue: undefined,
      errors: {},
    };
  });
  return newArray;
};
export function AsociarFases() {
  const tableReference = useRef();
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(ASOCIAR_FASES);
  const { proyecto, editable } = useSelector((state) => state.proyectos);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [isOpen, setIsOpen] = useState(false);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showUndoModal, setShowUndoModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [dataTable, setDataTable] = useState([]);
  const [deletedAssociations, setDeletedAssociations] = useState([]);
  const [selectedRow, setSelectedRow] = useState();
  const [inputErrors, setInputErrors] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const { isAuthorized: accessAFM } = useGetAuthorization("PROY_AFM_ADMIN");
  const [isDisabled, setIsDisabled] = useState(!editable || !accessAFM);
  const [isLoading, setIsLoading] = useState(false);

  const dispatch = useDispatch();

  const { data: asociaciones, isFetching: isLoadingAsociaciones } =
    useGetAsociacionesQuery(proyecto?.proyecto?.idProyecto);

  const [crearAsociacion, { isLoading: isLoadingCrear }] =
    usePostCrearAsociacionMutation();

  const { data: fases, isLoading: isLoadingFases } = useGetFasesQuery();

  const [postAsociaciones, { isLoading: isLoadingPostAsociaciones }] =
    usePostAsociacionesMutation();

  const [getReporte, { isLoading: isLoadingReporte, isError: reporteError }] =
    useLazyGetReporteQuery();

  useEffect(() => {
    if (asociaciones) {
      const previousAsociaciones = rearrangeAsociaciones(asociaciones);
      setDataTable(previousAsociaciones);
      setMemoizedData(
        new Map(
          previousAsociaciones.map((asociacion) => [
            asociacion.UUID,
            asociacion,
          ])
        )
      );
      setDeletedAssociations([]);
    }
  }, [asociaciones]);

  const handleAddAsociacion = () => {
    const newAssociation = {
      UUID: nanoid(),
      primaryKey: "",
      fase: "",
      plantilla: "",
      fechaAsignacion: new Date(),
      isNewRow: true,
      displayPlantilla: "",
      displayFase: "",
      cargado: false,
      lastCachedValue: undefined,
      errors: {},
    };
    setDataTable((prev) => [...prev, newAssociation]);
    setMemoizedData(
      (map) => new Map(map.set(newAssociation.UUID, newAssociation))
    );
  };
  const handleDownloadExcel = async () => {
    if (!proyecto) return;
    try {
      const res = await getReporte(proyecto?.proyecto?.idProyecto).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setIsOpen(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setIsOpen(true);
      }
    }
  };

  const onGetRowData = (row) => {
    setSelectedRow(row);
  };
  const onReset = () => {
    const newData = [...memoizedData?.values()];
    const resetData = newData.reduce((prev, current) => {
      if (current.isNewRow) return prev;
      prev.push(current);
      return prev;
    }, []);
    setDataTable(resetData);
    tableReference?.current?.table.toggleAllRowsSelected(false);
    setShowCancelModal(false);
  };

  const onSubmit = async () => {
    try {
      if (dataTable.length <= 0 && deletedAssociations.length <= 0)
        throw new Error("No se ha agregado ningún campo en la tabla");
      const associationsErrors = await asociacionesRequiredValidations(
        dataTable
      );
      if (associationsErrors.dataErrors) {
        errorToast(ASOCIAR_FASES.MSG004);
        setDataTable(associationsErrors.resultados);
        return;
      }
      if (isIdPlantillaRepeated(dataTable)) {
        errorToast("La plantilla ya se encuentra asociada al proyecto");
        return;
      }
      let dataToSend = [];
      const newAssociations = dataTable.reduce((prev, row) => {
        if (row.isNewRow) {
          prev.push({
            idPlantillaVigente: parseInt(row.plantilla),
            fechaAsignacion: moment(row.fechaAsignacion).format("YYYY-MM-DD"),
          });
        }
        return prev;
      }, []);

      const editedAssociations = dataTable.reduce((prev, row) => {
        const virtualRow = memoizedData.get(row.UUID);
        if (
          !row.isNewRow &&
          !_.isEqual(
            _.omit(row, ["lastCachedValue"]),
            _.omit(virtualRow, ["lastCachedValue"])
          )
        ) {
          prev.push({
            idAsociacion: parseInt(row.idAsociacion),
            idPlantillaVigente: parseInt(row.plantilla),
            fechaAsignacion: moment(row.fechaAsignacion).format("YYYY-MM-DD"),
          });
        }
        return prev;
      }, []);
      setIsLoading(true);
      newAssociations?.forEach((asociacion) => {
        dataToSend.push(
          crearAsociacion({
            id: parseInt(proyecto?.proyecto?.idProyecto),
            data: asociacion,
          }).unwrap()
        );
      });
      await Promise.all(dataToSend);
      const data = {
        asociacionesModificadas: [...editedAssociations],
        asociacionesEliminadas: [...deletedAssociations],
      };
      await postAsociaciones({
        data,
        id: parseInt(proyecto?.proyecto?.idProyecto),
      }).unwrap();
      tableReference?.current?.table.toggleAllRowsSelected(false);
      dispatch(GetDetalleProyecto(proyecto.proyecto.idProyecto));
      setIsLoading(false);
      setSingleBasicMessage(ASOCIAR_FASES.MSG001);
      setIsOpen(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setIsOpen(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setIsOpen(true);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const onCancelar = () => {
    if (dataTable.length <= 0 && deletedAssociations.length <= 0) return;
    setShowCancelModal(true);
  };
  const columns = useMemo(
    () => [
      {
        accessorKey: "fase",
        header: "Fase",
        cell: (props) => (
          <>
            <EditableCell
              name="fase"
              getValue={props.getValue}
              column={props.column}
              row={props.row}
              table={props.table}
              options={fases}
              keyValue="primaryKey"
              keyTextValue="nombre"
              displayProperty={"displayFase"}
              useFilter
            />
          </>
        ),
        filterFn: (row, columnId, inputValue) => {
          return row.original.displayFase
            .toLowerCase()
            .trim()
            .startsWith(inputValue);
        },
        sortingFn: (rowA, rowB, _columnId) => {
          const valueA = rowA.original.displayFase;
          const valueB = rowB.original.displayFase;
          return ("" + valueA).localeCompare(valueB);
        },
      },
      {
        accessorKey: "plantilla",
        header: "Plantilla",
        cell: (props) => (
          <DependentSelectCell
            row={props.row}
            table={props.table}
            column={props.column}
            getValue={props.getValue}
          />
        ),
        filterFn: (row, columnId, inputValue) => {
          return row.original.displayPlantilla
            .toLowerCase()
            .trim()
            .startsWith(inputValue);
        },
        sortingFn: (rowA, rowB, _columnId) => {
          const valueA = rowA.original.displayPlantilla;
          const valueB = rowB.original.displayPlantilla;
          return ("" + valueA).localeCompare(valueB);
        },
      },
      {
        accessorKey: "fechaAsignacion",
        header: "Fecha de asignación",
        cell: (props) => (
          <DateInputCell
            getValue={props.getValue}
            row={props.row}
            column={props.column}
            table={props.table}
          />
        ),
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <>
            {props.row.getIsSelected() || props.row.original.isNewRow ? (
              <Tooltip placement="top" text={"Cancelar / Eliminar"}>
                <span>
                  <IconButton
                    type={"undo"}
                    iconSize="lg"
                    onClick={(e) => {
                      console.log(props.row);
                      setSelectedRow({
                        ...props.row,
                        column: props.column.id,
                        index: props.row.index,
                        UUID: props.row.original.UUID,
                      });
                      setShowUndoModal(true);
                    }}
                  />
                </span>
              </Tooltip>
            ) : (
              <>
                <Tooltip text={"Editar"} placement="top">
                  <span>
                    <IconButton
                      iconSize="lg"
                      type="edit"
                      disabled={isDisabled}
                      onClick={() => {
                        if (props.row.original.cargado) {
                          setSingleBasicMessage(ASOCIAR_FASES.MSG006);
                          setIsOpen(true);
                          return;
                        }
                        props.row.toggleSelected(!props.row.getIsSelected(), {
                          selectChildren: false,
                        });
                      }}
                    />
                  </span>
                </Tooltip>
                <Tooltip placement="top" text={"Eliminar"}>
                  <span>
                    <IconButton
                      disabled={isDisabled}
                      type={"drop"}
                      iconSize="lg"
                      onClick={() => {
                        props.table.options.meta.getRowData({
                          ...props.row.original,
                          column: props.column.id,
                          index: props.row.index,
                        });
                        setShowDeleteModal(true);
                      }}
                    />
                  </span>
                </Tooltip>
              </>
            )}
          </>
        ),
        enableColumnFilter: false,
        enableSorting: false,
      },
    ],
    [fases]
  );
  return (
    <>
      {isLoading ||
      isLoadingAsociaciones ||
      isLoadingFases ||
      isLoadingReporte ||
      isLoadingPostAsociaciones ||
      isLoadingCrear ? (
        <Loader zIndex={`${isLoading ? "10" : "99999"}`} />
      ) : null}
      <Row>
        <Col className="text-end mb-3 ">
          <Tooltip placement="top" text={"Nuevo"}>
            <span style={{ marginRight: "1rem" }}>
              <IconButton
                type="add"
                onClick={handleAddAsociacion}
                disabled={isDisabled}
              />
            </span>
          </Tooltip>
          <Tooltip placement="top" text={"Exportar a excel"}>
            <span>
              <IconButton
                type="excel"
                onClick={handleDownloadExcel}
                disabled={
                  asociaciones === undefined || asociaciones?.length === 0
                }
              />
            </span>
          </Tooltip>
        </Col>
      </Row>
      <Row>
        <Col className="asociar-fases">
          <TablaEditable
            ref={tableReference}
            dataTable={dataTable}
            columns={columns}
            header={"Asociaciones"}
            hasPagination
            isFiltered
            onDelete={setDataTable}
            onUpdate={setDataTable}
            onGetRowData={onGetRowData}
            autoResetPageIndex={false}
          />
        </Col>
      </Row>
      {!isDisabled ? (
        <Row>
          <Col md={12} className="text-end">
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={onCancelar}
              disabled={isDisabled}
            >
              Cancelar
            </Button>
            <Button
              variant="green"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={() => {
                onSubmit();
              }}
              disabled={isDisabled}
            >
              Guardar
            </Button>
          </Col>
        </Row>
      ) : null}
      <BasicModal
        size={"md"}
        handleApprove={() => {
          setDeletedAssociations((prev) => [
            ...prev,
            selectedRow?.idAsociacion,
          ]);
          tableReference.current?.table.options?.meta.removeRowById(
            selectedRow.UUID
          );
          setShowDeleteModal(false);
        }}
        handleDeny={() => setShowDeleteModal(false)}
        denyText={"No"}
        approveText={"Sí"}
        show={showDeleteModal}
        title={"Mensaje"}
        onHide={() => setShowDeleteModal(false)}
      >
        {ASOCIAR_FASES.MSG005}
      </BasicModal>
      <BasicModal
        size={"md"}
        handleApprove={() => {
          console.log(selectedRow);
          if (selectedRow?.original?.isNewRow) {
            selectedRow.toggleSelected(false);
            setDataTable((prev) =>
              prev.filter((row) => row.UUID !== selectedRow.UUID)
            );
          } else {
            selectedRow.toggleSelected(false);
            tableReference.current?.table.options?.meta.updateSubRows(
              selectedRow.index,
              memoizedData.get(selectedRow.UUID)
            );
          }
          setShowUndoModal(false);
        }}
        handleDeny={() => {
          setShowUndoModal(false);
          selectedRow.toggleSelected(true);
        }}
        denyText={"No"}
        approveText={"Sí"}
        show={showUndoModal}
        title={"Mensaje"}
        onHide={() => setShowUndoModal(false)}
      >
        {ASOCIAR_FASES.MSG002}
      </BasicModal>
      <BasicModal
        size={"md"}
        handleApprove={() => {
          onReset();
        }}
        handleDeny={() => {
          setShowCancelModal(false);
        }}
        denyText={"No"}
        approveText={"Sí"}
        show={showCancelModal}
        title={"Mensaje"}
        onHide={() => {
          setShowCancelModal(false);
        }}
      >
        {ASOCIAR_FASES.MSG002}
      </BasicModal>
      <SingleBasicModal
        size={"md"}
        approveText={"Aceptar"}
        show={isOpen}
        title={"Mensaje"}
        onHide={() => setIsOpen(false)}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
