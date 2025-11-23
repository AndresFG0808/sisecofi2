import React, { useState } from 'react';
import { Accordion } from '../../../../components';
import Editor from "./Editor";
import { TablaDinamica } from '../../../../components/table';
import { injectActions } from '../../../../functions/utils';

const HEADERS = [
    { dataField: "variable", text: "Variable" },
    { dataField: "desc", text: "Descripción" }
];
//SOL PAGO
/* const DATA_TABLE = [
    { idVar: 1, variable: "${Administración_Central}", desc: "Variable donde se debe mostrar el nombre de la Administración Central del proyecto." },
    { idVar: 2, variable: "${Administración_General} ", desc: "Variable donde se debe mostrar el nombre de la Administraci ón General del proyecto." },
    { idVar: 3, variable: "${Nombre_proyecto}", desc: "Variable donde se debe mostrar el nombre corto del proyecto." },
    { idVar: 4, variable: "${Id}", desc: "Variable donde se debe mostrar el identificador del sistema del proyecto." },
    { idVar: 5, variable: "${Nombre_largo_contrato}", desc: "Variabledonde se debe mostrar el nombre largo del contrato." },
    { idVar: 6, variable: "${Número_contrato}", desc: "Variable donde se debe mostrar el número del contrato asociado con el dictamen." },
    { idVar: 7, variable: "${Administración_Coordinación}", desc: "Administración de Coordinación de Servicios Tecnológicos." },
] */


// PROFORMA
const DATA_TABLE = [
    { idVar: 1, variable: "${Administración_Central}", desc: "Variable donde se debe mostrar el nombre de la Administración Central del proyecto." },
    { idVar: 2, variable: "${Administración_General} ", desc: "Variable donde se debe mostrar el nombre de la Administraci ón General del proyecto." },
    { idVar: 3, variable: "${Nombre_proyecto}", desc: "Variable donde se debe mostrar el nombre corto del proyecto." },
    { idVar: 4, variable: "${Id}", desc: "Variable donde se debe mostrar el identificador del sistema del proyecto." },
    { idVar: 5, variable: "${Nombre_largo_contrato}", desc: "Variabledonde se debe mostrar el nombre largo del contrato." },
    { idVar: 6, variable: "${Número_contrato}", desc: "Variable donde se debe mostrar el número del contrato asociado con el dictamen." }
]

const Header = ({ header, setHeader }) => {
    const [dataTable, setDataTable] = useState(DATA_TABLE);

    return (
        <Accordion title="Encabezado" show={false}>
            <Editor currentEdition={header} setCurrentEdition={setHeader} />
            <br />
            <TablaDinamica
                headers={HEADERS}
                data={injectActions(dataTable)}
                header={"Variables disponibles"}
                hasPagination={false}
            />

        </Accordion>
    );
}

export default Header;