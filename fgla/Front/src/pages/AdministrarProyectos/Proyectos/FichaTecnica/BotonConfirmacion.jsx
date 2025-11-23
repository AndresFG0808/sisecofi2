import React from "react";
import { Button, Col, Row } from "react-bootstrap";
import { useToast } from "../../../../hooks/useToast";

export function BotonConfirmacion({ handleCancel, isValid, message }) {
  const { errorToast } = useToast();
  return (
    <>
      <Row>
        <Col md={12} className="text-end">
          <Button
            variant="red"
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={handleCancel}
          >
            Cancelar
          </Button>
          <Button
            variant="green"
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={() => {
              !isValid && errorToast(message);
            }}
            type="submit"
          >
            Guardar
          </Button>
        </Col>
      </Row>
    </>
  );
}
