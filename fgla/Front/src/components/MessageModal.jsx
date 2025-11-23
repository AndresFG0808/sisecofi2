import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

const MessageModal = ({ show, title, message, onClose, onConfirm }) => {

    const handleCloseModal = () => {
        onClose();
    };

    const handleConfirm = () => {
        onConfirm();
    };

    return (
        <Modal show={show} onHide={handleCloseModal}>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>{message}</p>
            </Modal.Body>
            <Modal.Footer>
                <Button
                    variant="primary"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={onConfirm ? handleConfirm : handleCloseModal}>
                    Aceptar
                </Button>
                {
                    onConfirm &&
                    <Button
                        variant="secondary"
                        className="btn-sm ms-2 waves-effect waves-light"
                        onClick={handleCloseModal}>
                        Cancelar
                    </Button>
                }
            </Modal.Footer>
        </Modal>
    );
};

export default MessageModal;
