import React, { memo, useState, useEffect, useRef, useContext } from "react";
import TableComponent from "../../../AdministrarProyectos/Proyectos/Proveedores/components/TableComponent";
import BasicModal from "../../../../modals/BasicModal";
import isEmpty from "lodash/isEmpty";
import { Form } from "react-bootstrap";

/*
Reglas:
RNA108
*/

function Estimacion({ setIsBasicModal, isBasicModal, onSuccess, }) {
  const tableReference = useRef();

  const [dataTable, setDataTable] = useState([
    {
      identifier: "1",
      isEditable: false,
      checked: false,
      tipo: '1',
    },
  ]);

  const CustomId = ({ getValue }) => {
    const id = getValue();
    return <>{id.indexOf("+") <= -1 && <span>{id}</span>}</>;
  };

  const CustomCheckedHeader = ({ tableReference, isEditable }) => {
    const [checked, setChecked] = useState(false);

    const data = tableReference.current
      ? [...tableReference.current.table.options.meta.getDataTable()]
      : [];

    useEffect(() => {
      if (isEmpty(data) === false) {
        const filterCheckedData = data.filter((item) => item.checked === true);
        if (
          isEmpty(data) === false &&
          filterCheckedData.length === data.length
        ) {
          setChecked(true);
        } else {
          setChecked(false);
        }
      } else {
        setChecked(false);
      }
    }, [data]);

    return (
      <>
        Seleccionar
        <br></br>
        <Form.Check
          type={"checkbox"}
          id={"1"}
          onChange={() => {
            const value = !checked;
            setChecked(value);
            if (isEmpty(data) === false) {
              tableReference.current.table.options.meta.revertData(
                data.map((item) => {
                  const newItem = { ...item };
                  newItem.checked = value;
                  return newItem;
                })
              );
            }
          }}
          style={{ cursor: "pointer" }}
          disabled={!isEditable}
          checked={checked}
        />
      </>
    );
  };

  const CustomChecked = ({ getValue, onChangeChecked, row, isEditable }) => {
    const checked = getValue();
    const { identifier } = row.original;
    return (
      <>
        <Form.Check
          type={"checkbox"}
          id={"1"}
          disabled={!isEditable}
          onChange={() => {
            onChangeChecked(identifier, !checked);
          }}
          style={{ cursor: "pointer" }}
          checked={checked}
        />
      </>
    );
  };

  const onChangeChecked = async (identifier, value) => {
    const data = [...tableReference.current.table.options.meta.getDataTable()];

    const dataArray = [...data].map((item) => {
      const checked = item.identifier === identifier ? value : item.checked;
      const newItem = { ...item };
      newItem.checked = checked;
      return { ...newItem };
    });

    await tableReference.current.table.options.meta.revertData(dataArray);
  };

  const columns = [
    {
      enableSorting: false,
      enableColumnFilter: false,
      accessorKey: "identifier",
      header: "Id",
      filterFn: "includesString",
      cell: (props) => <CustomId getValue={props.getValue} row={props.row} />,
    },



    {
      accessorKey: "monto",
      header: "Monto",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
  ]

  return (
    <>
      <BasicModal
        handleApprove={onSuccess}
        handleDeny={() => setIsBasicModal(false)}
        denyText={"Regresar"}
        approveText={"Guardar"}
        show={isBasicModal}
        title={"Estimación"}
        onHide={() => setIsBasicModal(false)}
      >
        <TableComponent
          tableReference={tableReference}
          dataTable={dataTable}
          columns={columns}
          providerTableTitle=""
          setDataTable={() => {}}
        />
      </BasicModal>
    </>
  );
}

export default Estimacion;
