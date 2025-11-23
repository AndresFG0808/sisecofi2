import React, {
  useEffect,
  useMemo,
  useState,
  useContext,
  useRef,
  useCallback,
} from "react";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Form, Row, Col, Button } from "react-bootstrap";
import { AdministrarUsuarioContext } from "../AdministrarUsuarioContext";
import { ADMINISTRAR_USUARIOS_SISTEMA } from "../../../../constants/messages";
import Authorization from "../../../../components/Authorization";
import {
  usePutGuardarUsuariosDAMutation,
  usePutGuardarUsuariosMutation,
} from "../store";
import _ from "lodash";
import { Loader } from "../../../../components";
export default function AdministrarUsuariosTable({
  dataTable,
  actionType,
  resetPage,
}) {
  const tableRef = useRef();

  const [putGuardarUsuarios, { isLoading: isLoadingUsuarios }] =
    usePutGuardarUsuariosMutation();
  const [putGuardarUsuariosDA, { isLoading: isLoadingUsuariosDA }] =
    usePutGuardarUsuariosDAMutation();

  const { handleShowConfirmModal, handleShowMessage } = useContext(
    AdministrarUsuarioContext
  );
  function LabelCell({ getValue }) {
    const initialValue = getValue();

    return <>{initialValue}</>;
  }

  const CheckCell = useCallback(
    ({ getValue, row, disabled, typeCell, table, column }) => {
      const { updateData } = table.options.meta;

      const aprove = () => {
        updateData(row.index, column.id, false);
      };
      const deny = () => {
        updateData(row.index, column.id, true);
      };

      const handleChange = (e) => {
        let { checked } = e.target;
        if (typeCell === "switch" && !checked) {
          handleShowConfirmModal(
            ADMINISTRAR_USUARIOS_SISTEMA.MSG005,
            aprove,
            deny
          );
        } else {
          updateData(row.index, column.id, checked);
        }
      };
      return (
        <Authorization process={"USR_ADMIN"}>
          <Form.Check
            type={typeCell}
            disabled={disabled}
            checked={getValue()}
            name={"formCheck"}
            onChange={handleChange}
          />
        </Authorization>
      );
    },
    [handleShowConfirmModal]
  );

  const tableInfo = useMemo(() => {
    const columns = [
      {
        accessorKey: "nombres",
        header: "Nombre(s)",
        cell: LabelCell,
        filterFn: "includesString",
      },
      {
        accessorKey: "apellidoPaterno",
        header: "Primer apellido",
        cell: LabelCell,
        filterFn: "includesString",
      },
      {
        accessorKey: "apellidoMaterno",
        header: "Segundo apellido",
        cell: LabelCell,
        filterFn: "includesString",
      },
      {
        accessorKey: "rfcCorto",
        header: "RFC corto",
        cell: LabelCell,
        filterFn: "includesString",
      },
      {
        accessorKey: "administracion",
        header: "Administración",
        cell: LabelCell,
        filterFn: "includesString",
      },
    ];
    return [
      {
        actionType: "directorio",
        header: "Empleados SAT",
        columns: [
          ...columns,
          {
            accessorKey: "estatus",
            header: "Seleccionar",
            cell: (props) => CheckCell({ typeCell: "checkbox", ...props }),
            enableSorting: false,
            enableColumnFilter: false,
          },
        ],
      },
      {
        actionType: "sistema",
        header: "Usuarios registrados en el sistema",
        columns: [
          ...columns,
          {
            accessorKey: "estatus",
            header: "Estatus",
            cell: (props) => CheckCell({ typeCell: "switch", ...props }),
            enableSorting: false,
            enableColumnFilter: false,
          },
        ],
      },
    ];
  }, [CheckCell]);

  const [header, setHeader] = useState("");
  const [columns, setColumns] = useState([]);

  useEffect(() => {
    let _tableInfo = tableInfo.find((s) => s.actionType === actionType);
    if (_tableInfo) {
      setHeader(_tableInfo.header);
      setColumns(_tableInfo.columns);
    }
  }, [tableInfo, actionType]);

  const [data, setData] = useState(dataTable);

  useEffect(() => {
    if (Array.isArray(dataTable)) {
      tableRef.current.setColumnFilters((prev) => []);
      let _dataTable = dataTable.map((element) => {
        return { ...element, selected: false };
      });
      setData(_dataTable);
    }
  }, [dataTable]);

  const resetTable = () => {
    setData(dataTable);
  };
  const handleCancel = () => {
    handleShowConfirmModal(ADMINISTRAR_USUARIOS_SISTEMA.MSG004, resetTable);
  };

  function getDifference(original, modified) {
    let diference = original
      .map((item) =>
        modified.find(
          (s) => s.rfcCorto === item.rfcCorto && s.estatus !== item.estatus
        )
      )
      .filter((s) => s);

    const sinDuplicados = Array.from(
      new Map(diference.map((obj) => [obj.rfcCorto, obj])).values()
    );

    return sinDuplicados;
  }
  const saveSistema = () => {
    let usersToSave = getDifference(dataTable, data);
    if (usersToSave && usersToSave.length > 0) {
      // validar servicios
      putGuardarUsuarios(usersToSave).then((response) => {
        if (response.error) {
          handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG009);
        } else {
          handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG002);
          resetPage();
        }
      });
    }
  };

  const saveDA = () => {
    let usersToSave = getDifference(dataTable, data);
    if (usersToSave && usersToSave.length > 0) {
      //Validar servicio
      putGuardarUsuarios(usersToSave).then((response) => {
        if (response.error) {
          handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG009);
        } else {
          handleShowMessage(ADMINISTRAR_USUARIOS_SISTEMA.MSG002);
          resetPage();
        }
      });
    }
  };

  const handleSave = () => {
    if (actionType === "sistema") saveSistema();
    else if (actionType === "directorio") saveDA();
  };

  const onUpdateTable = (newData) => {
    setData(newData);
  };

  return (
    <>
      {(isLoadingUsuarios || isLoadingUsuariosDA) && <Loader />}
      <TablaEditable
        ref={tableRef}
        dataTable={data}
        columns={columns}
        header={header}
        hasPagination
        isFiltered
        onUpdate={onUpdateTable}
      />
      <Row>
        <Authorization process={"USR_ADMIN"}>
          <Col md={12} className="text-end">
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              type="button"
              onClick={() => handleCancel()}
              disabled={_.isEmpty(data)}
            >
              Cancelar
            </Button>
            <Button
              variant="green"
              className="btn-sm ms-2 waves-effect waves-light"
              type="button"
              disabled={_.isEmpty(data)}
              onClick={handleSave}
            >
              Guardar
            </Button>
          </Col>
        </Authorization>
      </Row>
    </>
  );
}
