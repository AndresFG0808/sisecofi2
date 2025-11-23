import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Row, Col, Button } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { onEditProyecto } from "../../../store/pryectos";
import BasicModal from "../../../modals/BasicModal";
import {MESSAGES} from './constants';
const Proyecto = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [isOpenModal, setIsOpenModal] = useState(false);

  const handleGoBack = () => {
    let path = "/consumoServicios/consumoServicios";
    dispatch(onEditProyecto(undefined));
    navigate(path);
  };

  return (
    <Row>
      <Col md={12} className="text-end mb-2">
        <Button
          variant="red"
          className="btn-sm ms-2 waves-effect waves-light"
          onClick={() => setIsOpenModal(true)}
        >
          Regresar
        </Button>

        {isOpenModal && (
          <BasicModal
            size="md"
            show={isOpenModal}
            onHide={() => {
              setIsOpenModal(false);
            }}
            title="Mensaje"
            denyText="No"
            handleDeny={() => {
              setIsOpenModal(false);
            }}
            approveText={"Sí"}
            handleApprove={handleGoBack}
          >{MESSAGES.MSG008}</BasicModal>
        )}
      </Col>
    </Row>
  );
};

export default Proyecto;
