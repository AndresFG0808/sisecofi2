import React from 'react';
import { Modal, Row, Col, Button } from 'react-bootstrap';

const SingleBasicModal = ({
  show,
  onHide,
  size,
  asTitle,
  title,
  children,
  approveText,
  ...props
}) => (
  <Modal show={show} size={size} onHide={onHide} {...props}>
    <Modal.Header closeButton>
      <Modal.Title as={asTitle}>
        <div className="text-center">{title}</div>
      </Modal.Title>
    </Modal.Header>
    <Modal.Body className="text-center">
      {children}
    </Modal.Body>
    <Modal.Footer>
      <Row className="text-end">
        <Col md={12}>
          <Button
            variant="green"
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={onHide}
          >
            {approveText}
          </Button>
        </Col>
      </Row>
    </Modal.Footer>
  </Modal>
);

SingleBasicModal.defaultProps = {
  size: 'md',
  asTitle: 'h3',
};

export default SingleBasicModal;
