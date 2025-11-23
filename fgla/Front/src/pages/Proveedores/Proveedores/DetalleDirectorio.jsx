import React, { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import { TablaDinamica } from '../../../components/table';
import { downloadExcelBlob, injectActions } from "../../../functions/utils";
import { Row, Col, Button } from 'react-bootstrap';
import IconButton from '../../../components/buttons/IconButton';
import { downloadDocument, putData, getData } from '../../../functions/api';
import { useToast } from "../../../hooks/useToast";
import { PROVEEDORES as MESSAGES } from '../../../constants/messages';
import { Tooltip } from "../../../components/Tooltip";
import showMessage from '../../../components/Messages';
import BasicModal from '../../../modals/BasicModal';
import Loader from '../../../components/Loader';

const HEADERS = [
    { dataField: "ordenDirectorio", text: "Id" },
    { dataField: "nombreContacto", text: "Nombre del contacto", filter: true, sort: true, },
    { dataField: "telefonoOficina", text: "Teléfono oficina", filter: true, sort: true, },
    { dataField: "telefonoCelular", text: "Teléfono celular", filter: true, sort: true, },
    { dataField: "correoElectronico", text: "Correo electrónico", filter: true, sort: true },
    { dataField: "representanteLegal", text: "Representante legal", formatter: 'switch' },
    { dataField: "comentarios", text: "Comentarios", filter: true, sort: true, }
];

const DetalleDirectorio = ({
    show,
    onHide,
    size,
    asTitle,
    title,
    handleApprove,
    approveText,
    idProveedor,
    directorioData,
    ...props
}) => {
    const [loading, setLoading] = useState(false);
    const ID_KEY_NAME = "idDirectorioContacto";
    const [dataTable, setDataTable] = useState([]);
    const [data, setData] = useState(true);
    const [idEstatusChange, setIdRepresentanteChange] = useState("");
    const [showConfirmModal, setShowConfirmModal] = useState(false);
    const [selectedId, setSelectedId] = useState(null);

    useEffect(() => {
        if (directorioData) {
            setDataTable(directorioData);
        }
    }, [directorioData, data]);

    const handleCloseModal = () => {
        onHide();
    };

    const onChangeStatus = (idDirectorioContacto) => async () => {
        setIdRepresentanteChange(idDirectorioContacto);
        let object = dataTable.find((element) => element[ID_KEY_NAME] === idDirectorioContacto);
        if (object.representanteLegal) {
            setSelectedId(idDirectorioContacto);
            setShowConfirmModal(true);
        } else {
            await updateRepresentanteEstado(idDirectorioContacto, !object.representanteLegal);
            setDataTable(prevState =>
                prevState.map(item =>
                    item[ID_KEY_NAME] === idDirectorioContacto ? { ...item, representanteLegal: !object.representanteLegal } : item
                )
            );
        }
    };

    const updateRepresentanteEstado = async (idDirectorioContacto, newStatus) => {
        try {
            setLoading(true);
            const directorioResp = await getData(`/proveedores/proveedor/${idProveedor}`);

            const estado = {
                idDirectorioContacto: directorioResp.data.idDirectorioContacto,
                nombreContacto: directorioResp.data.nombreContacto,
                telefonoOficina: directorioResp.data.telefonoOficina,
                telefonoCelular: directorioResp.data.telefonoCelular,
                correoElectronico: directorioResp.data.correoElectronico,
                comentarios: directorioResp.data.comentarios,
                representanteLegal: newStatus,
                idProveedor: directorioResp.data.idProveedor
            };

            let response = await putData(`/proveedores/directorio/${idDirectorioContacto}`, estado);
            const obj = response.data;
            const updatedItem = {
                idDirectorioContacto: obj.idDirectorioContacto,
                nombreContacto: obj.nombreContacto,
                telefonoOficina: obj.telefonoOficina,
                telefonoCelular: obj.telefonoCelular,
                correoElectronico: obj.correoElectronico,
                comentarios: obj.comentarios,
                representanteLegal: obj.representanteLegal,
                idProveedor: obj.idProveedor
            };
            setLoading(false);
            const indexUpdate = dataTable.findIndex((reg) => reg[ID_KEY_NAME] === obj[ID_KEY_NAME]);
            if (indexUpdate !== -1) {
                const newDataTable = [...dataTable];
                newDataTable.splice(indexUpdate, 1, updatedItem);
                setDataTable(newDataTable);
                setLoading(false);
            } else {
                setLoading(false);
                showMessage(MESSAGES.MSG003);
            }
            showMessage(MESSAGES.MSG008);
            return response;
        } catch (error) {
            setLoading(false);
            showMessage(MESSAGES.MSG003);
            throw error;
        }
    };

    const handleConfirmAccept = async () => {
        if (selectedId) {
            await updateRepresentanteEstado(selectedId, false);
            setDataTable(prevState =>
                prevState.map(item =>
                    item[ID_KEY_NAME] === selectedId ? { ...item, representanteLegal: false } : item
                )
            );
        }
        setShowConfirmModal(false);
    };

    const handleConfirmDeny = () => {
        setShowConfirmModal(false);
    };

    const handleDownloadExcel = async () => {
        try {
            const response = await downloadDocument(`/proveedores/reporte-directorio?idProveedor=${idProveedor}`);
            downloadExcelBlob(response.data, "Directorio de contacto");
        } catch (error) {
            showMessage(MESSAGES.MSG005);
        }
    };

    return (
        <>
            {loading && <Loader />}
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
                        onChangeStatus={onChangeStatus}
                    />
                    <BasicModal
                        show={showConfirmModal}
                        size={"md"}
                        onHide={handleConfirmDeny}
                        title="Confirmación"
                        denyText={"No"}
                        handleDeny={handleConfirmDeny}
                        approveText={"Sí"}
                        handleApprove={handleConfirmAccept}
                    >
                        {MESSAGES.MSG009}
                    </BasicModal>
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
        </>
    );

};

DetalleDirectorio.defaultProps = {
    size: 'lg',
    asTitle: 'h3',
};

export default DetalleDirectorio;