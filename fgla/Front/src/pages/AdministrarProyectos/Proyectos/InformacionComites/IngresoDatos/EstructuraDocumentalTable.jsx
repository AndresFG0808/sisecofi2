import React, { useState } from "react";


import { TablaDinamica } from "../../../../../components/table";
import { injectActions } from "../../../../../functions/utils";

export default function EstructuraDocumentalTable() {
  const ID_KEY_NAME = "idContrato";

  const HEADERS = [
    { dataField: "prefijo", text: "Prefijo" },
    { dataField: "requerido", text: "Requerido" },
    { dataField: "noAplica", text: "No aplica" },
    { dataField: "estatus", text: "Estatus" },
    { dataField: "comentario", text: "" },
    { dataField: "tamaño", text: "Tamaño" },
    { dataField: "ultimaModificacion", text: "Última modificación" },
    { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
  ];

  const DATA_TEMP = [
    {
      prefijo: "Prefijo",
      requerido: true,
      noAplica: true,
      estatus: "",
      comentario: "...",
      tamaño: "1 MB",
      ultimaModificacion: "01/01/2024",
    },
    {
      prefijo: "Prefijo",
      requerido: true,
      noAplica: true,
      estatus: "",
      comentario: "...",
      tamaño: "1 MB",
      ultimaModificacion: "01/01/2024",
    },
  ];

  const [dataTable, setDataTable] = useState(DATA_TEMP);

  return (
    <TablaDinamica
      idKeyName={ID_KEY_NAME}
      idKeyLink={ID_KEY_NAME}
      headers={HEADERS}
      data={injectActions(dataTable, {
        edit: true,
        remove: true,
      })}
    />
  );
}
