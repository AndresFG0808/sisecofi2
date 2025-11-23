import React, { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import { TablaDinamica } from '../../../components/table';
import { downloadExcelBlob, injectActions } from "../../../functions/utils";
import { Row, Col, Button } from 'react-bootstrap';
import IconButton from '../../../components/buttons/IconButton';
import { downloadDocument } from '../../../functions/api';
import { Tooltip } from "../../../components/Tooltip";
import showMessage from '../../../components/Messages';
import { PROVEEDORES as MESSAGES } from '../../../constants/messages';

const HEADERS = [
    { dataField: "ordenDictamenProveedor", text: "Id" },
    { dataField: "nombreTituloServicio", text: "Servicio", filter: true, sort: true },
    { dataField: "anio", text: "Año", filter: true, sort: true },
    { dataField: "responsable", text: "Responsable", filter: true, sort: true },
    { dataField: "resultado", text: "Resultado", filter: true, sort: true },
    { dataField: "observacion", text: "Observación", filter: true, sort: true }
];


const DetalleDictamen = ({
    show,
    onHide,
    size,
    asTitle,
    title,
    handleApprove,
    approveText,
    idProveedor,
    dictamenData,
    ...props
}) => {
    const ID_KEY_NAME = "idDirectorioContacto";
    const [dataTable, setDataTable] = useState([]);

    useEffect(() => {
        if (dictamenData) {
            setDataTable(dictamenData);
        }
    }, [dictamenData]);

    const handleCloseModal = () => {
        onHide();
    };

    const handleEdit = (id) => () => {

    };

    const handleShow = (idProveedor) => async () => {

    };

    const onChangeStatusProveedor = (id) => () => {

    };

    const handleDownloadExcel = async () => {
        try {
            const response = await downloadDocument(
                `/proveedores/reporte-dictamen-tecnico?idProveedor=${idProveedor}`
            );
            downloadExcelBlob(response.data, "Dictamen técnico");
        } catch (error) {
            showMessage(MESSAGES.MSG005);
        }
    };

    return (
        <Modal show={show} size={size} onHide={handleCloseModal} centered {...props}>
            <Modal.Header closeButton>
                <Modal.Title as={asTitle}>
                    <div className="text-center">{title}</div>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row>
                    <Col md={12} className="text-end mb-2">
                        <IconButton
                            type="excel"
                            onClick={handleDownloadExcel}
                            disabled={dataTable.length === 0}
                            tooltip={"Exportar a Excel"}
                        />
                    </Col>
                </Row>
                <TablaDinamica
                    idKeyName={ID_KEY_NAME}
                    idKeyLink={ID_KEY_NAME}
                    headers={HEADERS}
                    data={injectActions(dataTable)}
                    actionFns={{ handleEdit, handleShow }}
                    onChangeStatus={onChangeStatusProveedor}
                />
            </Modal.Body>
            <Modal.Footer>
                <Row>
                    <Col md={12} className="text-end">
                        <Button
                            variant="red"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={handleApprove}
                        >
                            {approveText}
                        </Button>
                    </Col>
                </Row>
            </Modal.Footer>
        </Modal>
    );
};

DetalleDictamen.defaultProps = {
    size: 'lg',
    asTitle: 'h3',
};

export default DetalleDictamen;
