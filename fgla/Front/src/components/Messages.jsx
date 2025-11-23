import React, { useState } from 'react';
import ReactDOM from 'react-dom/client';
import SingleBasicModal from '../modals/SingleBasicModal';

const ModalContent = ({ initialShow, message, onClose, onConfirm }) => {
    const [show, setShow] = useState(initialShow);

    const handleClose = () => {
        setShow(false);
        setTimeout(onClose, 300);
    };

    const handleAccept = () => {
        if (onConfirm) {
            onConfirm();
        }
        handleClose();
    };

    return (
        <div className='show-messages-component'>
            <SingleBasicModal
                size="md"
                approveText="Aceptar"
                show={show}
                title="Mensaje"
                onHide={handleAccept}
            >
                {message}
            </SingleBasicModal>
        </div>
    );
};

const showMessage = (message, onConfirm) => {

    const existingModal = document.getElementById('modal-root-msg');
    if (existingModal) return;

    const modalRoot = document.createElement('div');
    modalRoot.id = 'modal-root-msg';
    document.body.appendChild(modalRoot);
    const root = ReactDOM.createRoot(modalRoot);

    const handleClose = () => {
        root.unmount();
        document.body.removeChild(modalRoot);
    };

    root.render(
        <ModalContent
            initialShow={true}
            message={message}
            onClose={handleClose}
            onConfirm={onConfirm}
        />
    );

    return handleClose;
};

export default showMessage;
