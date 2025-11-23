import React, { useState, useEffect, useRef } from "react";
import TableComponent from "../../../AdministrarProyectos/Proyectos/Proveedores/components/TableComponent";
import BasicModal from "../../../../modals/BasicModal";
import { Form } from "react-bootstrap";
import moment from "moment";

const FORMAT_DATE = "DD/MM/YYYY";
const OPTIONS_MONEY = { style: 'currency', currency: 'USD' };
const FORMAT_MONEY = new Intl.NumberFormat('en-US', OPTIONS_MONEY);

function Estimacion({ setIsBasicModal, isBasicModal, onSuccess, dataEstimaciones, setSelectedEstimacion }) {
  const tableReference = useRef();
  const [dataTable, setDataTable] = useState(dataEstimaciones);

  useEffect(() => {
    processData(dataEstimaciones);
  }, [dataEstimaciones])

  const processData = (data) => {
    let processedDataTable = [];
    data.forEach((item, index) => {
      let row = {
        ...item,
        identifier: index + 1,
        fechaInicio: dateFormat(item.fechaInicio),
        fechaTermino: dateFormat(item.fechaTermino),
        montoEstimadoMoney: FORMAT_MONEY.format(item.montoEstimado),
        montoEstimadoPesosMoney: FORMAT_MONEY.format(item.montoEstimadoPesos),
      };
      processedDataTable.push(row);
    });
    setDataTable(processedDataTable);
  };

  const dateFormat = (date) => {
    let formatedDateTime = date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
    return formatedDateTime;
  }

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
      let checked = false;
      if (item.identifier === identifier) {
        checked = value;
        setSelectedEstimacion(value ? item : null);
      }
      return { ...item, checked };
    });
    await tableReference.current.table.options.meta.revertData(dataArray);
  };

  const columns = [
    {
      accessorKey: "checked",
      header: "Seleccionar",
      enableSorting: false,
      enableColumnFilter: false,
      cell: (props) => (
        <CustomChecked
          onChangeChecked={onChangeChecked}
          row={props.row}
          getValue={props.getValue}
          isEditable={true}
        />
      ),
    },
    {
      enableSorting: false,
      enableColumnFilter: false,
      accessorKey: "idEstimacion",
      header: "Id",
      filterFn: "includesString"
    },
    {
      accessorKey: "periodoControl",
      header: "Periodo control",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "fechaInicio",
      header: "Periodo de inicio",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "fechaTermino",
      header: "Periodo fin",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "montoEstimadoMoney",
      header: "Monto",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "montoEstimadoPesosMoney",
      header: "Monto en pesos",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
  ];

  return (
    <>
      <BasicModal
        size={'xl'}
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
          setDataTable={() => { }}
        />
      </BasicModal>
    </>
  );
}

export default Estimacion;
