import React, { useEffect, useMemo, useRef, useState } from "react";
import { Row, Col, FormCheck } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import LabelValue from "../../../../components/LabelValue";
import { Tooltip } from "../../../../components/Tooltip";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { ToggleCell } from "./Components/ToggleCell";
import { DownloadFileBase64 } from "../InformacionComites/Components/DownloadFile";
import BasicModal from "../../../../modals/BasicModal";
import { MODIFICAR_PROYECTOS } from "../../../../constants/messages";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import {
  useLazyGetReporteLideresQuery,
  useGetProyectosUsuariosQuery,
} from "../../store";
import { DateIconCell } from "./Components/DateIconCell";
import { useSelector } from "react-redux";
import Loader from "../../../../components/Loader";
import { nanoid } from "nanoid";
import { LiderEditableCell } from "./Components/LiderEditableCell";
import { rearrangeLideres } from "./utils";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";

export function LiderProyecto({
  handleChange,
  values,
  data,
  onChangeInitialValues,
  onChangeLideresEliminados,
  editable,
  setParentMemoizedLeadersData,
}) {
  const ref = useRef();
  const { getMessageExists } = useErrorMessages(MODIFICAR_PROYECTOS);
  const { proyecto } = useSelector((state) => state.proyectos);
  const [getReporteLideres, { isLoading: isLoadingReporte }] =
    useLazyGetReporteLideresQuery();
  const [memoizedData, setMemoizedData] = useState(new Map());
  const [dataTable, setDataTable] = useState([]);
  // usuarios: [{ idUsuario: number, idUsuarioStr: string, nivel, nombre, correo, puesto }]
  const { data: usuarios } = useGetProyectosUsuariosQuery();
  const [show, setShow] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [showSingleModal, setShowSingleModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showUndoModal, setShowUndoModal] = useState(false);
  const [singleModalText, setSingleModalText] = useState("");

  useEffect(() => {
    if (data) {
      const previousLeaders = rearrangeLideres(data);
      setDataTable(previousLeaders);
      const memoizedFunction = new Map(
        previousLeaders.map((leader) => [leader.UUID, leader])
      );
      setMemoizedData(memoizedFunction);
      setParentMemoizedLeadersData(memoizedFunction);
      onChangeInitialValues(previousLeaders);
    }
    ref?.current?.table.toggleAllRowsSelected(false);
    ref?.current?.table.resetPageIndex();
  }, [data]);

  const onLeaderActive = (lideres) => {
    if (lideres.length === 0) return false;
    return lideres?.find((leader) => leader.estatus === true);
  };

  const onGetRowData = (row) => {
    setSelectedUser(row);
  };

  const onUpdateTable = (newData) => {
    setDataTable(newData);
    onChangeInitialValues(newData);
  };

  const onDeleteRow = (newData) => {
    setDataTable(newData);
    onChangeInitialValues(newData);
  };

  const handleAddUser = () => {
    if (onLeaderActive(dataTable)) {
      setShowSingleModal(true);
      setSingleModalText(MODIFICAR_PROYECTOS.MSG002);
      return;
    }
    const newUser = {
      UUID: nanoid(),
      // Inicializamos nuevos campos para backend y mapeo
      idUsuario: null,       // number
      idUsuarioStr: "",      // string (clave única para el select)
      idReferencia: null,    // number (lo que enviará el backend)
      nivel: null,

      nombre: "",
      puesto: "",
      correo: "",
      nombreDisplay: "",
      fechaInicio: null,
      fechaFin: null,
      estatus: false,
      isNewRow: true,
    };
    setDataTable((prev) => [...prev, newUser]);
    const memoizedFunction = (map) => new Map(map.set(newUser.UUID, newUser));
    setMemoizedData(memoizedFunction);
    setParentMemoizedLeadersData(memoizedFunction);
    onChangeInitialValues((prev) => [...prev, newUser]);
  };

  const handleDownloadExcel = async () => {
    try {
      const res = await getReporteLideres(
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

  const opcionesLideres = useMemo(() => {
    const arr = Array.isArray(usuarios)
      ? usuarios
      : Array.isArray(usuarios?.content)
        ? usuarios.content
        : Array.isArray(usuarios?.data)
          ? usuarios.data
          : [];

    // normaliza tipos por si vienen raros
    return arr.map(u => ({
      ...u,
      idUsuarioStr: String(u.idUsuarioStr ?? u.idUsuario ?? ""), // asegura string
      nombre: u.nombre ?? "",                                    // evita undefined
    }));
  }, [usuarios]);

  const columns = useMemo(
    () => [
      {
        accessorKey: "nombre",
        header: "Líder de proyecto",
        cell: (props) => (
          <LiderEditableCell
            name="nombre"
            getValue={props.getValue}
            column={props.column}
            row={props.row}
            table={props.table}
            options={opcionesLideres}          
            keyValue="idUsuarioStr"
            keyTextValue="nombre"
            displayProperty="nombreDisplay"
            callback={(e) => {
              const { value } = e.target; 
              const user = opcionesLideres.find(u => u.idUsuarioStr === value);
              props.table.options.meta.updateSubRows(props.row.index, {
                ...props.row.original,
                correo: user?.correo,
                puesto: user?.puesto,
                nombreDisplay: user?.nombre,
                idUsuario: user?.idUsuario ?? null,
                idUsuarioStr: user?.idUsuarioStr ?? "",
                idReferencia: user?.idUsuario ?? null,
                nivel: user?.nivel ?? null,
              });
            }}
          />
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "puesto",
        header: (props) => (
          <>
            <Tooltip
              placement="top"
              text={"Puesto registrado en el módulo de Usuarios."}
            >
              <span>{"Puesto"}</span>
            </Tooltip>
          </>
        ),
        cell: (props) => <span>{props.getValue()}</span>,
        enableColumnFilter: false,
        enableSorting: false,
      },
      {
        accessorKey: "correo",
        header: "Correo",
        cell: (props) => <p>{props.getValue()}</p>,
        enableColumnFilter: false,
      },
      {
        accessorKey: "fechaInicio",
        header: "Fecha inicio",
        cell: (props) => (
          <DateIconCell
            name={"fechaInicio"}
            column={props.column}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            bias={"start"}
          />
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "fechaFin",
        header: "Fecha fin",
        cell: (props) => (
          <DateIconCell
            column={props.column}
            name={"fechaFin"}
            getValue={props.getValue}
            row={props.row}
            table={props.table}
            bias={"end"}
            conditional={props.row.original.estatus}
          />
        ),
        enableColumnFilter: false,
      },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => (
          <>
            <FormCheck
              onClick={() => {
                props.table.options.meta.getRowData({
                  ...props.row.original,
                  column: props.column.id,
                  index: props.row.index,
                });
                setShow(true);
              }}
              onChange={(e) => { }}
              type="switch"
              checked={props.getValue()}
              disabled={
                !props.row.original.isNewRow && !props.row.getIsSelected()
              }
            />
          </>
        ),
        enableSorting: false,
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
                  disabled={!editable}
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
                />
              </>
            )}
          </>
        ),
        enableSorting: false,
        enableColumnFilter: false,
      },
    ],
    [usuarios, editable]
  );

  return (
    <>
      {isLoadingReporte ? <Loader /> : null}
      <Row className="mt-3 d-flex align-items-center">
        <Col md={6}>
          <LabelValue label="Líder de proyecto*:" />
        </Col>
        <Col md={6} className="text-end">
          <Tooltip placement="top" text={"Agregar líder"}>
            <span>
              <IconButton
                type="add"
                onClick={handleAddUser}
                disabled={!editable}
              />
            </span>
          </Tooltip>
          <Tooltip placement="top" text={"Exportar histórico"}>
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
            ref={ref}
            dataTable={dataTable}
            columns={columns}
            header={"Histórico"}
            hasPagination
            onUpdate={onUpdateTable}
            onDelete={onDeleteRow}
            onGetRowData={onGetRowData}
            autoResetPageIndex={false}
          />
        </Col>
      </Row>
      <BasicModal
        size={"md"}
        handleApprove={() => {
          setShow(false);
          if (selectedUser.estatus === true) {
            setSingleModalText(MODIFICAR_PROYECTOS.MSG023);
            setShowSingleModal(true);
            ref.current?.table.options?.meta.updateData(
              selectedUser.index,
              selectedUser.column,
              !selectedUser.estatus
            );
          } else {
            if (onLeaderActive(dataTable)) {
              setShowSingleModal(true);
              setSingleModalText(MODIFICAR_PROYECTOS.MSG002);
            } else {
              ref.current?.table.options?.meta.updateData(
                selectedUser.index,
                selectedUser.column,
                !selectedUser.estatus
              );
            }
          }
        }}
        handleDeny={() => setShow(false)}
        denyText={"No"}
        approveText={"Sí"}
        show={show}
        title={"Mensaje"}
        onHide={() => setShow(false)}
      >
        {MODIFICAR_PROYECTOS.MSG022}
      </BasicModal>
      <BasicModal
        size={"md"}
        handleApprove={() => {
          setShowDeleteModal(false);
          onChangeLideresEliminados((prev) => [
            ...prev,
            selectedUser?.idHistorico,
          ]);
          ref.current?.table.options?.meta.removeRow(selectedUser.index);
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
          if (selectedUser?.original?.isNewRow) {
            selectedUser.toggleSelected(!selectedUser.getIsSelected(), {
              selectChildren: false,
            });
            ref.current?.table.options?.meta.removeRow(selectedUser.index);
          } else {
            selectedUser.toggleSelected(!selectedUser.getIsSelected(), {
              selectChildren: false,
            });
            ref.current?.table.options?.meta.updateSubRows(
              selectedUser.index,
              memoizedData.get(selectedUser.original.UUID)
            );
          }
          setShowUndoModal(false);
        }}
        handleDeny={() => {
          setShowUndoModal(false);
        }}
        denyText={"No"}
        approveText={"Sí"}
        show={showUndoModal}
        title={"Mensaje"}
        onHide={() => setShowUndoModal(false)}
      >
        {MODIFICAR_PROYECTOS.MSG018}
      </BasicModal>
      <SingleBasicModal
        size={"md"}
        show={showSingleModal}
        approveText={"Aceptar"}
        title={"Mensaje"}
        onHide={() => {
          setShowSingleModal(false);
        }}
      >
        {singleModalText}
      </SingleBasicModal>
    </>
  );
}
