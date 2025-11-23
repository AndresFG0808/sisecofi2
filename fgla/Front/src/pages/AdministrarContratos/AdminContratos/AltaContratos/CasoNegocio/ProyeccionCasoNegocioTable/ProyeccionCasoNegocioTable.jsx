import React, { useEffect, useState } from "react";
import { TablaEditable } from "../../../../../../components/table/TablaEditable";
import { getData } from "../../../../../../functions/api";
import _ from "lodash";

export default function ProyeccionCasoNegocioTable({
  idContrato,
  setIsLoading,
  mapa,
  cargado
}) {
  const [columns, setColumns] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  useEffect(() => {
    if (mapa) {
      let _dataTable = [];
      if (mapa && !_.isEmpty(mapa)) {
        const keys = Object.keys(mapa);
        if (Array.isArray(keys)) {
          keys.forEach((rowKey, index) => {
            let row = mapa[rowKey];
            if (index === 0) {
              let headers = row.map((item, subIndex) => {
                if (subIndex === 1) {
                  return {
                    accessorKey: "sub" + subIndex,
                    header: item,
                    cell: LabelCell,
                    filterFn: "includesString",
                  };
                } else if (subIndex > 1) {
                  return {
                    accessorKey: "sub" + subIndex,
                    header: item,
                    cell: LabelCell,
                    enableSorting: false,
                    enableColumnFilter: false,
                  };
                }
                return null;
              });
              setColumns(headers.filter((s) => s));
            } else {
              let rowData = {};
              row.forEach((item, subIndex) => {
                rowData["sub" + subIndex] = item;
              });

              _dataTable.push(rowData);
            }
          });
        }
      }
      setDataTable(_dataTable);
    }
  }, [mapa]);

  return (
    <TablaEditable
      // ref={ref}
      dataTable={dataTable}
      columns={columns}
      header={"Proyección de caso de negocio"}
      hasPagination
      isFiltered
      onUpdate={() => {}}
    />
  );
}

function LabelCell({ getValue }) {
  const initialValue = getValue();

  return <>{initialValue}</>;
}
