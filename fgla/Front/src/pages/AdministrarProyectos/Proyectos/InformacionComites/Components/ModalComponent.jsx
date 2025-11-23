import React from "react";
import { Modal } from "react-bootstrap";

export default function ModalComponent({ show, handleClose, children, title }) {
  return (
    <Modal show={show} onHide={handleClose} size="xl">
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      {children}
    </Modal>
  );
}
