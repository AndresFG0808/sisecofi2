import React, { useEffect, useState, useMemo } from "react";
import { Row } from "react-bootstrap";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { ToggleCell } from "./Components/ToggleCell";
import { InputEditableCell } from "./Components/InputEditableCell";
import { Check } from "./Components/Check";
import { DatePicker } from "./Components/DatePicker";
import { ExpandCell } from "./Components/ExpandCell";
import Authorization from "../../../../components/Authorization";
import { original } from "@reduxjs/toolkit";

const PlanTabla = ({
  tableRef,
  dataTable,
  setDataTable,
  planGuardado,
  updatePlan,
  levelExpand,
  actualizarPlanReales,
  // actualizarCompletado,
  descartarCambios,
  updateTable,
  editable,
  loadingUpdate,
}) => {
  const onChangeData = (idTarea, key, value, original) => {
    console.log("onChangeData => ", dataTable);
    let data = { [key]: value };
    actualizarPlanReales(idTarea, key, data, original);
  };

  // const onChangeCompletado = (idTarea, key, value) => {
  //     console.log("onChangeCompletado => ", dataTable);
  //     let data = { [key]: value };
  //     actualizarCompletado(idTarea, key, data,original);
  // }

  const columns = useMemo(
    () => [
      {
        accessorKey: "idTarea",
        header: "Id tarea",
        cell: (props) => <p>{props.getValue()}</p>,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "nombreTarea",
        header: "Nombre de la tarea",
        cell: (props) => (
          <ExpandCell
            getValue={props.getValue}
            row={props.row}
            levelExpand={levelExpand}
          />
        ),
        size: 300,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "activo",
        header: "Activo",
        cell: (props) => <Check getValue={props.getValue} />,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "duracionPlaneada",
        header: "Duración planeada",
        cell: (props) => <p>{props.getValue()}</p>,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "fechaInicioPlaneada",
        header: "Fecha de inicio planeada",
        cell: (props) => <p>{props.getValue()}</p>,
        size: 110,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "fechaFinPlaneada",
        header: "Fecha fin planeada",
        cell: (props) => <p>{props.getValue()}</p>,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "duracionReal",
        header: "Duración real",
        cell: (props) => (
          <InputEditableCell
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            decimals={2}
            onChangeValidation={onChangeData}
            loadingUpdate={loadingUpdate}
          />
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "fechaInicioReal",
        header: "Fecha inicio real",
        cell: (props) => (
          <DatePicker
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            onChangeValidation={onChangeData}
          />
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "fechaFinReal",
        header: "Fecha fin real",
        cell: (props) => (
          <DatePicker
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            onChangeValidation={onChangeData}
          />
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "predecesora",
        header: "Predecesora",
        cell: (props) => <p>{props.getValue()}</p>,
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "planeado",
        header: "Planeado %",
        cell: (props) => (
          <p>
            {props.getValue()}
            {props.getValue() !== undefined && props.getValue() !== null
              ? "%"
              : ""}
          </p>
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "completado",
        header: "Completado %",
        cell: (props) => (
          <InputEditableCell
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            percentage={true}
            onChangeValidation={onChangeData}
            loadingUpdate={loadingUpdate}
          />
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) => (
          <Authorization process={"PROY_PT_MODIF"}>
            <ToggleCell
              getValue={props.getValue}
              column={props.column}
              row={props.row}
              table={props.table}
              descartarCambios={descartarCambios}
              editable={editable}
            />
          </Authorization>
        ),
        enableColumnFilter: false,
        enableSorting: false,
        footer: (props) => props.column.id,
      },
    ],
    [
      planGuardado,
      updatePlan,
      levelExpand,
      updateTable,
      loadingUpdate,
      descartarCambios,
    ]
  );

  console.log(dataTable);

  return (
    <Row>
      <TablaEditable
        ref={tableRef}
        header="Plan de trabajo"
        dataTable={dataTable}
        columns={columns}
        hasPagination={true}
        onUpdate={setDataTable}
      />
    </Row>
  );
};

export default PlanTabla;
