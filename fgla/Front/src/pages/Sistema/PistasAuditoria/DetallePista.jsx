import React from 'react';
import { Modal, Card, Button } from 'react-bootstrap';

const PistasAuditoria = ({ show, setShow, data }) => {

    return (
        <Modal
            show={show}
            onHide={() => setShow(false)}
            size="lg"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>Detalle de pistas</Modal.Title>
            </Modal.Header>
            <Modal.Body className='px-2 py-0'>
                <div className='content-bitacora-dictaminador'>
                    <Card className='mt-4 mx-3 card-pistas shadow-emphasis'>
                        <Card.Header>
                            <b>Detalle movimiento</b>
                        </Card.Header>
                        <Card.Body>
                            {data.detalleMovimiento}
                        </Card.Body>
                    </Card>

                    <Card className='mt-4 mx-3 card-pistas shadow'>
                        <Card.Header>
                            Detalle movimiento anterior
                        </Card.Header>
                        <Card.Body>
                            {data.detalleMovimientoAnterior}
                        </Card.Body>
                    </Card>

                    <Card className='my-4 mx-3 card-pistas shadow'>
                        <Card.Header>
                            Última modificación
                        </Card.Header>
                        <Card.Body>
                            {data.ultimaModificacion}
                        </Card.Body>
                    </Card>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button
                    variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={() => setShow(false)}
                >
                    Cerrar
                </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default PistasAuditoria;
