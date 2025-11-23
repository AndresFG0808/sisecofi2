import React from 'react';
import { Row, Col, Button } from 'react-bootstrap'

const ButtonsApproveDeny = ({ handleApprove, handleDeny, approveText, denyText }) => (
  <Row className="text-end">
    <Col md={12}>
      <Button
        variant="red"
        className="btn-sm ms-2 waves-effect waves-light"
        onClick={handleDeny}
      >
        {denyText}
      </Button>
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

ButtonsApproveDeny.defaultProps = {
  denyText: 'Cancelar',
  approveText: 'Guardar',
  typeDeny: 'button',
  typeApprove: 'button',
};

export default ButtonsApproveDeny;
