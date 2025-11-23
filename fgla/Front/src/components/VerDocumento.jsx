import React from "react";
import { Modal } from "react-bootstrap";
import Loader from "./Loader";

const VerDocumento = ({ loading, title, show, onHide, urlPdfBlob }) => {

  return (
    <Modal
      show={show}
      dialogClassName="modalMax modal-document"
      onHide={onHide}
      size="lg"
      centered
    >
      <Modal.Header closeButton className="modal-title">
        <Modal.Title class="col-11 text-center fw-bold">{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {loading ?
          <Loader /> :
          <iframe
            src={urlPdfBlob}
            width="100%"
            height="100%"
            frameborder="0"
          ></iframe>
        }
      </Modal.Body>
    </Modal>
  );
};

VerDocumento.defaultProps = {
  title: "Informe",
  show: false,
};
export default VerDocumento;
