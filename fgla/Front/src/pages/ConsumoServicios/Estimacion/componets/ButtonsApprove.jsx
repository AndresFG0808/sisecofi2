import React from 'react';
import { Row, Col, Button } from 'react-bootstrap'

const ButtonsApprove = ({ handleApprove, handleDeny, approveText, denyText }) => (
  <Row className="text-end">
    <Col md={12}>
      <Button
        variant="green"
        className="btn-sm ms-2 waves-effect waves-light"
        onClick={handleApprove}
      >
        {approveText}
      </Button>
    </Col>
  </Row>
);

ButtonsApprove.defaultProps = {
  approveText: 'Guardar',
  typeApprove: 'button',
};

export default ButtonsApprove;
