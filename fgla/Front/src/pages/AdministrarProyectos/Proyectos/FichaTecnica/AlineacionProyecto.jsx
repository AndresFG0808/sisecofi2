import React, { useEffect, useMemo, useRef, useState } from "react";
import { Col, Row } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import LabelValue from "../../../../components/LabelValue";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Tooltip } from "../../../../components/Tooltip";
import { EditableCell } from "./Components/EditableCell";
import { ToggleCell } from "./Components/ToggleCell";
import {
  useGetAlineacionQuery,
  useGetPeriodosQuery,
  useLazyGetObjetivosQuery,
  useLazyGetReporteAlineacionQuery,
} from "../../store";
import BasicModal from "../../../../modals/BasicModal";
import { MODIFICAR_PROYECTOS } from "../../../../constants/messages";
import { DownloadFileBase64 } from "../InformacionComites/Components/DownloadFile";
import Loader from "../../../../components/Loader";
import { DependentSelectCell } from "./Components/DependentSelectCell";
import { useSelector } from "react-redux";
import { nanoid } from "nanoid";
import { rearrangeAlineacion } from "./utils";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";
import SingleBasicModal from "../../../../modals/SingleBasicModal";

export function AlineacionProyecto({
  handleChange,
  values,
  data,
  onChangeInitialValues,
  onChangeAlineacionesEliminadas,
  rearrangeTable,
  editable,
  setParentMemoizedAlineacionData,
}) {
  const tableReference = useRef();
  const { getMessageExists } = useErrorMessages(MODIFICAR_PROYECTOS);
  const { proyecto } = useSelector((state) => state.proyectos);
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [selectedRow, setSelectedRow] = useState(null);
  const [dataTable, setDataTable] = useState([]);
  const [showUndoModal, setShowUndoModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [singleModalText, setSingleModalText] = useState("");
  const [showSingleModal, setShowSingleModal] = useState(false);

  useEffect(() => {
    if (data) {
      if (
        !data?.some((row) => !!row.errors) ||
        !tableReference.current.table.getIsSomeRowsSelected()
      ) {
        tableReference?.current?.table.toggleAllRowsSelected(false);
      }
      const previousAlineacion = rearrangeAlineacion(data);
      setDataTable(previousAlineacion);
      const memoizedFunction = new Map(
        previousAlineacion.map((alineacion) => [alineacion.UUID, alineacion])
      );
      setMemoizedData(memoizedFunction);
      setParentMemoizedAlineacionData(memoizedFunction);
      onChangeInitialValues(previousAlineacion);
    }
  }, [data]);

  const { data: alineaciones, isFetching: isLoadingAlineaciones } =
    useGetAlineacionQuery();
  const { data: periodos, isFetching: isLoadingPeriodos } =
    useGetPeriodosQuery();
  const [getObjetivos, { data: objetivos, isFetching: isLoadingObjetivos }] =
    useLazyGetObjetivosQuery();

  const [getReporteAlineacion, { isFetching: isLoadingReporte }] =
    useLazyGetReporteAlineacionQuery();

  const handleOnChange = (e) => {
    getObjetivos(e.target.value);
  };
  const onGetRowData = (row) => {
    setSelectedRow(row);
  };

  const handleUpdate = (newData) => {
    setDataTable(newData);
    onChangeInitialValues(newData);
  };

  const handleAddAlineacion = () => {
    const newAlineacion = {
      UUID: nanoid(),
      mapa: "",
      periodo: "",
      objetivo: "",
      edit: true,
      isNewRow: true,
      mapaDisplay: "",
      periodoDisplay: "",
      objetivoDisplay: "",
    };
    setDataTable((prev) => [...prev, newAlineacion]);
    const memoizedFunction = (map) =>
      new Map(map.set(newAlineacion.UUID, newAlineacion));
    setMemoizedData(memoizedFunction);
    setParentMemoizedAlineacionData(memoizedFunction);
    onChangeInitialValues((prev) => [...prev, newAlineacion]);
  };

  const handleDeleteRow = (newData) => {
    setDataTable(newData);
    onChangeInitialValues(newData);
  };
  const handleDownloadExcel = async () => {
    try {
      const res = await getReporteAlineacion(
        proyecto?.proyecto?.idProyecto
      ).unwrap();
      DownloadFileBase64(
        res,
        "Reporte.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleModalText(mensaje);
        setShowSingleModal(true);
      } else {
        setSingleModalText("Ocurrió un error.");
        setShowSingleModal(true);
      }
    }
  };
  const columns = useMemo(
    () => [
      {
        accessorKey: "mapa",
        header: "Mapa",
        cell: (props) => (
          // On select para llamar a objetivos
          <EditableCell
            name="mapa"
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            options={alineaciones}
            keyValue={"primaryKey"}
            keyTextValue="nombre"
            // callback={handleOnChange}
            displayProperty={"mapaDisplay"}
          />
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "periodo",
        header: "Periodo",
        cell: (props) => (
          <EditableCell
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            options={periodos}
            keyValue={"primaryKey"}
            keyTextValue="nombre"
            displayProperty={"periodoDisplay"}
          />
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "objetivo",
        header: "Objetivo",
        cell: (props) => (
          <DependentSelectCell
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            options={objetivos}
            keyValue={"primaryKey"}
            keyTextValue="objetivo"
            // displayProperty={"objetivoDisplay"}
          />
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "edit",
        header: "",
        cell: (props) => (
          <>
            {props.row.getIsSelected() || props.row.original.isNewRow ? (
              <IconButton
                type={"undo"}
                iconSize="1x"
                onClick={() => {
                  onGetRowData(props.row);
                  setShowUndoModal(true);
                }}
                disabled={!editable}
              />
            ) : (
              <>
                <ToggleCell row={props.row} disabled={!editable} />
                <IconButton
                  type={"drop"}
                  iconSize="1x"
                  onClick={() => {
                    props.table.options.meta.getRowData({
                      ...props.row.original,
                      column: props.column.id,
                      index: props.row.index,
                    });
                    setShowDeleteModal(true);
                  }}
                  disabled={!editable}
                />
              </>
            )}
          </>
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
    ],
    [alineaciones, objetivos, periodos, editable]
  );
  return (
    <>
      {isLoadingObjetivos ||
      isLoadingAlineaciones ||
      isLoadingPeriodos ||
      isLoadingReporte ? (
        <Loader />
      ) : null}
      <Row className="mt-5 d-flex align-items-center">
        <Col md={6}>
          <LabelValue label="Alineación del proyecto*:" />
        </Col>
        <Col md={6} className="text-end mb-3">
          <Tooltip placement="top" text={"Agregar alineación"}>
            <span>
              <IconButton
                type="add"
                onClick={handleAddAlineacion}
                disabled={!editable}
              />
            </span>
          </Tooltip>
          <Tooltip placement="top" text={"Exportar alineación"}>
            <span>
              <IconButton
                type="excel"
                onClick={handleDownloadExcel}
                disabled={data === undefined || data.length === 0}
              />
            </span>
          </Tooltip>
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <TablaEditable
            ref={tableReference}
            dataTable={dataTable}
            columns={columns}
            onUpdate={handleUpdate}
            onDelete={handleDeleteRow}
            onGetRowData={onGetRowData}
          />
        </Col>
      </Row>
      <BasicModal
        size={"md"}
        handleApprove={() => {
          setShowDeleteModal(false);
          onChangeAlineacionesEliminadas((prev) => [
            ...prev,
            selectedRow.idAlineacion,
          ]);
          tableReference.current?.table.options?.meta.removeRow(
            selectedRow.index
          );
        }}
        handleDeny={() => setShowDeleteModal(false)}
        denyText={"No"}
        approveText={"Sí"}
        show={showDeleteModal}
        title={"Mensaje"}
        onHide={() => setShowDeleteModal(false)}
      >
        {MODIFICAR_PROYECTOS.MSG007}
      </BasicModal>
      <BasicModal
        size={"md"}
        handleApprove={() => {
          if (selectedRow?.original?.isNewRow) {
            selectedRow.toggleSelected(!selectedRow.getIsSelected(), {
              selectChildren: false,
            });
            tableReference.current?.table.options?.meta.removeRow(
              selectedRow.index
            );
          } else {
            selectedRow.toggleSelected(!selectedRow.getIsSelected(), {
              selectChildren: false,
            });
            tableReference.current?.table.options?.meta.updateSubRows(
              selectedRow.index,
              memoizedData.get(selectedRow.original.UUID)
            );
          }
          setShowUndoModal(false);
        }}
        handleDeny={() => setShowUndoModal(false)}
        denyText={"No"}
        approveText={"Sí"}
        show={showUndoModal}
        title={"Mensaje"}
        onHide={() => setShowUndoModal(false)}
      >
        {MODIFICAR_PROYECTOS.MSG018}
      </BasicModal>
      <SingleBasicModal
        show={showSingleModal}
        title={"Mensaje"}
        size={"md"}
        approveText={"Aceptar"}
        onHide={() => setShowSingleModal(false)}
      >
        {singleModalText}
      </SingleBasicModal>
    </>
  );
}
