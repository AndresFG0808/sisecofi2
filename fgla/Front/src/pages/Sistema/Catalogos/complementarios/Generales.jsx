import React, { useState, useEffect } from 'react';
import { TablaDinamica } from '../../../../components/table';
import { injectActions } from "../../../../functions/utils";

const HEADERS = [
    { dataField: "primaryKey", text: "Id", filter: true, sort: true },
    { dataField: "administracion", text: "Administración", filter: true, sort: true },
    { dataField: "acronimo", text: "Acrónimo", filter: true, sort: true },
    { dataField: "administrador", text: "Administrador general", filter: true, sort: true },
    { dataField: "puesto", text: "Puesto", filter: true, sort: true },
    { dataField: "fechaCreacion", text: "Fecha de creación", filter: true, sort: true },
    { dataField: "fechaModificacion", text: "Última modificación", filter: true, sort: true },
    { dataField: "estatus", text: "Estatus", formatter: "switch" },
    { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];

const Generales = ({ data, handleEdit, cambiarEstatus }) => {
    const ID_KEY_NAME = "primaryKey";
    const [dataTable, setDataTable] = useState(data);

    useEffect(() => {
        setDataTable(data)
    }, [data]);

    return (
        <TablaDinamica
            idKeyName={ID_KEY_NAME}
            idKeyLink={ID_KEY_NAME}
            headers={HEADERS}
            data={injectActions(dataTable, { edit: true })}
            actionFns={{ handleEdit }}
            onChangeStatus={cambiarEstatus}
        />
    );
}

export default Generales;