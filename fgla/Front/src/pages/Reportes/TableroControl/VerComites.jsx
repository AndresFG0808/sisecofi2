import React from 'react';
import { Modal, Button } from 'react-bootstrap';
import Comites from './Comites';

const VerComites = ({ show, setShow,data }) => {
    return (
        <Modal
            show={show}
            onHide={() => setShow(false)}
            size="xl"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>Resultado de búsqueda</Modal.Title>
            </Modal.Header>
            <Modal.Body className='px-2 py-0'>
                <Comites data={data} />
            </Modal.Body>
            <Modal.Footer>
                <Button
                    variant="red"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={() => setShow(false)}
                >
                    Cerrar
                </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default VerComites;
