import React, { useEffect, useState, useMemo } from 'react';
import { useNavigate } from "react-router-dom";
import { TablaEditable } from '../../../components/table/TablaEditable';
import { ActionCell } from './Components/ActionCell';
import { Check } from './Components/Check';
import { ExpandCell } from './Components/ExpandCell';
import { addOrReplace } from '../../../functions/utils';
import BasicModal from '../../../modals/BasicModal';
import { ADMIN_FORMATOS, MODIFICAR_PLANTILLA } from '../../../constants/messages';
import showMessage from '../../../components/Messages';
import { DateCell } from './Components/DateCell';
import { putData } from '../../../functions/api';

const PLANTILLA_NUEVA = {
    fechaModificacion: "",
    estatus: true,
    tipo: "plantilla"
}

const PlantillasTabla = ({ data, setData, addPlantilla, setPlanSelected, setSaveAction, saveAction, updateStatus }) => {
    const navigate = useNavigate();
    const [confirmModal, setConfirmModal] = useState(false);
    const [plantillaUpdate, setPlantillaUpdate] = useState(null);
    const [dataTable, setDataTable] = useState(data);

    useEffect(() => {
        setDataTable(data);
    }, [data]);

    const editPlantilla = (idTipoPlantillador, idPlantillador, row) => () => {
        let tipoPlantilla = row.original.idTipoPlantillador

        let objTipo = dataTable.find(plantilla => plantilla.idTipoPlantillador === tipoPlantilla);
        if (objTipo) {
            let plantillaStr = JSON.stringify(row.original);
            let state = { plantilla: plantillaStr };
            navigate("/sistema/plantillas/editar", { state });
        }
    }

    const addVersion = (idTipoPlantillador, row) => () => {
        row.toggleExpanded(true);
        let objTipo = [...dataTable].find(plantilla => plantilla.idTipoPlantillador === idTipoPlantillador);

        if (!objTipo.subRows)
            objTipo.subRows = [];

        if (objTipo.idTipoPlantillador === 2) {
            addProforma(objTipo);
        } else {
            const { nombre, idTipoPlantillador, comentarios, subRows } = row.original;
            let numVersion = subRows.length + 1;

            let newVersion = {
                ...PLANTILLA_NUEVA,
                nombre: nombre + " v" + numVersion,
                idTipoPlantillador: idTipoPlantillador,
                comentarios: comentarios,
                version: numVersion
            };

            let newObjTipo = {
                ...objTipo,
                maxVersion: numVersion
            }

            setSaveAction("guardarPlantilla");
            addPlantilla(newVersion);
            newObjTipo.subRows.push(newVersion);
            setData(addOrReplace(dataTable, newObjTipo, "idTipoPlantillador"));
        }
    }

    const addProforma = (objTipo) => {

        let numVersion = objTipo.subRows.length + 1;

        let newVersion = {
            nombre: "Proforma v" + numVersion,
            idTipoPlantillador: 2,
            comentarios: "Proforma",
            fechaModificacion: null,
            estatus: true,
            tipo: "subCarpeta",
            subRows: [
                {
                    nombre: "Proforma Factura v" + numVersion,
                    idTipoPlantillador: 2,
                    idTipo: 1,
                    comentarios: "Proforma",
                    fechaModificacion: null,
                    estatus: true,
                    tipo: "plantilla-hoja"
                },
                {
                    nombre: "Proforma Nota de Crédito v" + numVersion,
                    idTipoPlantillador: 2,
                    idTipo: 2,
                    comentarios: "Proforma",
                    fechaModificacion: null,
                    estatus: true,
                    tipo: "plantilla-hoja"
                },
                {
                    nombre: "Proforma Penalización v" + numVersion,
                    idTipoPlantillador: 2,
                    idTipo: 3,
                    comentarios: "Proforma",
                    fechaModificacion: null,
                    estatus: true,
                    tipo: "plantilla-hoja"
                }
            ]
        }

        setSaveAction("guardarProforma");
        addPlantilla(newVersion);
        objTipo.subRows.push(newVersion);
        setData(addOrReplace(dataTable, objTipo, "idTipoPlantillador"));
    }

    const selectPlantillaPlan = (idPlantillador, name) => () => {
        let fileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel";

        const archivo = document.createElement("input");
        archivo.type = "file";
        archivo.accept = fileType;
        archivo.click();

        archivo.onchange = (file) => {
            let hasFile = file.target.files[0] === undefined ? false : true;

            if (hasFile) {
                let documento = file.target.files[0];
                let extPermitida = '.XLSX';
                let fileExtension = "." + documento.name.split('.').pop().toUpperCase();
                let fileExtensionAccept = fileExtension === extPermitida.toUpperCase()

                if (!fileExtensionAccept) {
                    setPlanSelected(null);
                    setSaveAction(null);
                    showMessage(ADMIN_FORMATOS.MSG002);
                } else {
                    setPlanSelected({ documento, idPlantillador, name });
                    setSaveAction("uploadPlan");
                }
            }
        }
    }

    const cambiarEstatus = (data) => () => {
        let { tipo } = data
        if (tipo === "plantilla" || tipo === "subCarpeta") {
            setPlantillaUpdate(prev => data);
            setConfirmModal(true);
        }
    }

    const cambiarEstatusAccept = () => {
        setConfirmModal(false);

        if (plantillaUpdate) {
            updateStatus({ ...plantillaUpdate });
            setPlantillaUpdate(null);
        }
    }

    const columns = useMemo(() => [
        {
            accessorKey: "nombre",
            header: "Nombre de la plantilla",
            cell: (props) => (
                <ExpandCell
                    getValue={props.getValue}
                    row={props.row}
                />
            ),
            size: 300,
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "comentarios",
            header: "Comentarios",
            cell: (props) => <p>{props.getValue()}</p>,
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "fechaModificacion",
            header: "Fecha de modificación",
            cell: (props) => (
                <>
                    <DateCell
                        getValue={props.getValue}
                    />
                </>
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "estatus",
            header: "Estatus",
            cell: (props) => (
                <>
                    <Check
                        type="switch"
                        getValue={props.getValue}
                        column={props.column}
                        row={props.row}
                        table={props.table}
                        onChangeStatus={cambiarEstatus}
                        disabled={saveAction !== null}
                    />
                </>
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "acciones",
            header: "Acciones",
            cell: (props) => (
                <>
                    <ActionCell
                        row={props.row}
                        editPlantilla={editPlantilla}
                        addVersion={addVersion}
                        selectPlantillaPlan={selectPlantillaPlan}
                        disabled={saveAction !== null}
                    />
                </>
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        }
    ], [dataTable, saveAction]);

    return (
        <>
            <TablaEditable
                header="Plantillas"
                dataTable={dataTable}
                columns={columns}
                hasPagination={true}
                onUpdate={setDataTable}
            />

            <BasicModal
                size="md"
                handleApprove={cambiarEstatusAccept}
                handleDeny={() => setConfirmModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={confirmModal}
                title="Mensaje"
                onHide={() => setConfirmModal(false)}
            >
                {ADMIN_FORMATOS.MSG005}
            </BasicModal>
        </>
    )
}

export default PlantillasTabla;
