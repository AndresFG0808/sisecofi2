import React, { memo, useState } from "react";
import { TablaDinamica } from "../../../../components/table";
import { injectActions } from "../../../../functions/utils";

const HEADERS = [
  { dataField: "nombreFase", text: "Fase" },
  { dataField: "subtotal", text: "Subtotal" },
  { dataField: "deducciones", text: "Deducciones" },
  { dataField: "ieps", text: "IEPS" },
  { dataField: "iva", text: "IVA" },
  { dataField: "impuestos", text: "Otros impuestos" },
  { dataField: "total", text: "Total" },
  {
    dataField: "totalPesos",
    text: "Total en pesos",
  },
];

const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};

const ID_KEY_NAME = "idConsumoServicio";

function TableComponent({dataTable,}) {

  const [pageable, setPageable] = useState(PAGEABLE);

  const onChangeStatusProyecto = (id) => () => {};

  const handleEdit = (id) => () => {};

  const handleShow = (id) => () => {};

  const updateDataTable = (values) => {};

  return (
    <>
      <TablaDinamica
        idKeyName={ID_KEY_NAME}
        idKeyLink={ID_KEY_NAME}
        headers={HEADERS}
        header="Resumen consolidado"
        data={injectActions(dataTable, {})}
        actionFns={{ handleEdit, handleShow }}
        onChangeStatus={onChangeStatusProyecto}
        pageable={pageable}
        updateData={updateDataTable}
      />
    </>
  );
}

export default memo(TableComponent, (prevProps, nextProps) => {
  return prevProps.dataTable === nextProps.dataTable;
});
