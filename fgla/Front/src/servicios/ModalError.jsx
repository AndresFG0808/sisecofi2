import { useContext, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import { StoreContext } from "../store/StoreProvider";
import { types } from "../store/StoreReduce";


export const checkWinner = () => {
    return (
        <div>
            <ModalError></ModalError>
        </div>
    );
}
const ModalError = ({ mostrar }) => {
    const [store, dispath] = useContext(StoreContext);
    const [showModal, setShowModal] = useState(mostrar.modal);
    const handleClose = () => setShowModal(false);
    return (
        <Modal
            show={mostrar.modal}
            onHide={handleClose}
            backdrop="static"
            keyboard={false}
            size="md"
        >
            <Modal.Header>
                <Modal.Title>{mostrar.mensajes.id} </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {mostrar.mensajes.msj}
                <br></br>               
                {mostrar.debug && (                    
                    <p className="parrafo-error">{mostrar.mensajes.detalle}</p>
                )}
            </Modal.Body>
            <Modal.Footer>
                <Button
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={() => { dispath({ type: types.ocultar }) }}
                >
                    Aceptar
                </Button>
            </Modal.Footer>
        </Modal >
    );
}

export default ModalError;