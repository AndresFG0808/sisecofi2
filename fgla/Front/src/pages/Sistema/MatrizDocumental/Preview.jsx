import React, { useEffect, useState } from 'react';
import { Row, Col } from "react-bootstrap";
import { TablaEditable } from '../../../components/table/TablaEditable';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import IconButton from '../../../components/buttons/IconButton';
import { TextField } from '../../../components/formInputs';
import { ToggleCell } from './components/ToggleCell';
import "./styles.css";
import { InputEditableCell } from './components/InputEditableCell';
import { LabelValue } from '../../../components';
import { Check } from './components/Check';

const Preview = ({ dataPreview, setDataPreview, handleDownload, errorNombre }) => {

    const [data, setData] = useState(dataPreview.carpetas);
    const [nombrePlantilla, setNombrePlantilla] = useState('');
    const [idPlantilla, setIdPlantilla] = useState(dataPreview.plantillaVigenteModel.idPlantillaVigente);

    useEffect(() => {
        setData(dataPreview.carpetas);
        setNombrePlantilla(dataPreview.plantillaVigenteModel.nombre);
        setIdPlantilla(dataPreview.plantillaVigenteModel.idPlantillaVigente);
        console.log("dataPreview => ", dataPreview);
    }, [dataPreview]);

    useEffect(() => {
        setDataPreview({
            ...dataPreview,
            carpetas: data
        });
    }, [data]);

    const columns = [
        {
            accessorKey: "nombre",
            header: "Prefijo",
            cell: ({ row, getValue, table, column }) => (
                <>
                    <div
                        style={{
                            paddingLeft: `${row.depth * 2}rem`,
                        }}
                    >
                        <div>
                            {row.getCanExpand() ? (
                                <button
                                    {...{
                                        onClick: row.getToggleExpandedHandler(),
                                        style: {
                                            border: "1px solid transparent",
                                            background: "transparent",
                                        },
                                    }}
                                >
                                    {row.getIsExpanded() ? (
                                        <FontAwesomeIcon icon={faChevronDown} />
                                    ) : (
                                        <FontAwesomeIcon icon={faChevronRight} />
                                    )}
                                </button>
                            ) : (
                                ""
                            )}
                            {<InputEditableCell
                                getValue={getValue}
                                column={column}
                                row={row}
                                table={table}
                            />}
                        </div>
                    </div>
                </>
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "descripcion",
            header: "Descripción",
            cell: (props) => (
                <InputEditableCell
                    getValue={props.getValue}
                    column={props.column}
                    row={props.row}
                    table={props.table}
                />
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "tipo",
            header: "Tipo",
            cell: (props) => <p>{props.getValue()}</p>,
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "obligatorio",
            header: "Obligatorio",
            cell: (props) => (
                <div className="check-box-black">
                    {
                        props.row.original.tipo === "Archivo" &&
                        <Check
                            getValue={props.getValue}
                            column={props.column}
                            row={props.row}
                            table={props.table}
                        />
                    }
                </div>
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
                    <ToggleCell
                        getValue={props.getValue}
                        column={props.column}
                        row={props.row}
                        table={props.table}
                        asignado={dataPreview.plantillaVigenteModel.asignado}
                    />
                </>
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
    ];

    const handleChangeNombre = (e) => {
        console.log("handleChangeNombre > ", e.target.value);
        setNombrePlantilla(e.target.value);
    }

    const handleBlurNombre = (e) => {
        console.log("handleBlurNombre > ", e.target.value);
        setDataPreview({
            ...dataPreview,
            plantillaVigenteModel: {
                ...dataPreview.plantillaVigenteModel,
                nombre: nombrePlantilla
            }
        });
    }

    return (
        <>
            <Row>
                <Col md={4}>
                    <TextField
                        label="Nombre de la plantilla*:"
                        name="nombrePlantilla"
                        value={nombrePlantilla}
                        onChange={e => handleChangeNombre(e)}
                        onBlur={e => handleBlurNombre(e)}
                        autoComplete="off"
                        className={errorNombre ? 'is-invalid' : ''}
                        helperText={errorNombre ? 'Dato requerido' : ''}
                    />
                </Col>

                <Col md={4}>
                    <LabelValue
                        label="Fase:"
                        value={dataPreview?.catFaseProyecto?.nombre}
                    />
                </Col>
            </Row>

            <Row>
                <Col md={12} className="text-end mb-2">
                    <IconButton
                        type="excel"
                        disabled={idPlantilla === null}
                        onClick={handleDownload(idPlantilla)}
                    />
                </Col>
            </Row>

            <Row className='table-preview'>
                <TablaEditable
                    header="Configuración documental"
                    dataTable={data}
                    columns={columns}
                    hasPagination={true}
                    subRows="subCarpetas"
                    onUpdate={setData}
                />
            </Row>
        </>
    )
}

export default Preview;
