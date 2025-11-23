import React, { useState, useMemo } from 'react';
import { Row, Col } from "react-bootstrap";
import { TablaEditable } from '../../../../components/table/TablaEditable';
import IconButton from '../../../../components/buttons/IconButton';
import { ToggleCell } from '../components/ToggleCell';
import { InputEditableCell } from '../components/InputEditableCell';
import { Check } from '../components/Check';
import { DatePicker } from '../components/DatePicker';
import moment from 'moment';
import { nanoid } from "nanoid";
import { ADMIN_CATALOGOS as MESSAGES } from "../../../../constants/messages";
import BasicModal from '../../../../modals/BasicModal';

const NUEVO_ADMIN = {
    primaryKey: "",
    administrador: "",
    correo: "",
    telefono: "",
    fechaInicioVigencia: "",
    fechaFinVigencia: "",
    estatus: true
};

const FORMAT_DATE = "DD/MM/YYYY";

const Administradores = ({ administradores, setAdministradores, errorsTable, downloadAdministradores }) => {
    const [adminCopy, setAdminCopy] = useState([...administradores]);
    const [cancelDiscardModal, setCancelDiscardModal] = useState(false);
    const [adminData, setAdminData] = useState(null);

    const columns = useMemo(() => [
        {
            accessorKey: "id",
            header: "Id",
            cell: (props) => <p>{props.getValue()}</p>,
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "administrador",
            header: "Nombre completo",
            cell: (props) => (
                <InputEditableCell
                    getValue={props.getValue}
                    column={props.column}
                    row={props.row}
                    table={props.table}
                    errors={errorsTable}
                />
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "correo",
            header: "Correo",
            cell: (props) => (
                <InputEditableCell
                    getValue={props.getValue}
                    column={props.column}
                    row={props.row}
                    table={props.table}
                    errors={errorsTable}
                />
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "telefono",
            header: "Telefono",
            cell: (props) => (
                <InputEditableCell
                    getValue={props.getValue}
                    column={props.column}
                    row={props.row}
                    table={props.table}
                    errors={errorsTable}
                />
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "fechaInicioVigencia",
            header: "Fecha inicio de vigencia",
            cell: (props) => (
                <DatePicker
                    getValue={props.getValue}
                    column={props.column}
                    row={props.row}
                    table={props.table}
                    startEnd="start"
                />
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "fechaFinVigencia",
            header: "Fecha fin de vigencia",
            cell: (props) => (
                <DatePicker
                    getValue={props.getValue}
                    column={props.column}
                    row={props.row}
                    table={props.table}
                    startEnd="end"
                />
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        },
        {
            accessorKey: "fechaModificacion",
            header: "Última modificación",
            cell: (props) => <p>{dateFormat(props.getValue())}</p>,
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
                        cancelDiscardAdmin={cancelDiscardAdmin}
                    />
                </>
            ),
            enableColumnFilter: false,
            enableSorting: false,
            footer: (props) => props.column.id,
        }
    ], []);

    const dateFormat = (date) => {
        let formatedDateTime = date ? moment(date).format(FORMAT_DATE) : "";
        return formatedDateTime;
    }

    const nuevoAdministrador = () => {
        setAdministradores([...administradores, { ...NUEVO_ADMIN, IdAdmin: nanoid(), id: administradores.length + 1 }]);
    };

    const cancelDiscardAdmin = (data) => {
        setAdminData(data);
        setCancelDiscardModal(true);
    }

    const cancelDiscardAdminAccept = () => {
        setCancelDiscardModal(false);

        if (adminData.primaryKey) {
            let originalAdmin = adminCopy.find(item => item.primaryKey === adminData.primaryKey);
            let newAdmins = administradores.map(item => item.primaryKey === adminData.primaryKey ? originalAdmin : item);
            setAdministradores([...newAdmins]);
        } else {
            setAdministradores((prev) => {
                return prev.filter(item => item.IdAdmin !== adminData.IdAdmin);
            });
        }
    }

    return (
        <>
            <Row>
                <Col md={12} className="text-end mb-2">
                    <IconButton
                        type="add"
                        onClick={nuevoAdministrador}
                        tooltip={"Nuevo"}
                    />
                    <IconButton
                        type="excel"
                        onClick={downloadAdministradores}
                        disabled={administradores.length === 0}
                        tooltip={"Exportar a Excel"}
                    />
                </Col>
            </Row>

            <Row>
                <TablaEditable
                    header="Administradores"
                    dataTable={administradores}
                    columns={columns}
                    hasPagination={true}
                    onUpdate={setAdministradores}
                />
            </Row>

            <BasicModal
                size="md"
                handleApprove={cancelDiscardAdminAccept}
                handleDeny={() => setCancelDiscardModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={cancelDiscardModal}
                title="Mensaje"
                onHide={() => setCancelDiscardModal(false)}
            >
                {MESSAGES.MSG003}
            </BasicModal>

        </>
    )
}

export default Administradores;
