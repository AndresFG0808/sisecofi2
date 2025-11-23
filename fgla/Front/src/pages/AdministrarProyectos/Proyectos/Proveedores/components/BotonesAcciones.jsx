import React, { useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import { Tooltip } from "../../../../../components/Tooltip";
import IconButton from "../../../../../components/buttons/IconButton";
import CancelModal from "./CancelModal";
import { MESSAGES } from "../constants";
import { Form } from "react-bootstrap";
import Authorization from "../../../../../components/Authorization";

export const BotonesAcciones = ({
  isVisible,
  handleFetchProvider,
  handleCancelAddProviders,
  modalTitle,
  editable,
}) => {
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);

  const handleCancelButton = () => setIsOpenCancelModal(true);

  const handleApprove = () => {
    setIsOpenCancelModal(false);
    handleCancelAddProviders();
  };

  return (
    <>
      <Row>
        <Col md={12} className="text-end">
          {isVisible && (
            <>
              <Button
                variant="red"
                disabled={!editable}
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={handleCancelButton}
              >
                Cancelar
              </Button>
              <Button
                disabled={!editable}
                variant="green"
                className="btn-sm ms-2 waves-effect waves-light"
                type="submit"
                onClick={handleFetchProvider}
              >
                Guardar
              </Button>
              <CancelModal
                handleApprove={handleApprove}
                handleDeny={() => setIsOpenCancelModal(false)}
                isOpenCancelModal={isOpenCancelModal}
                title={modalTitle}
              />
            </>
          )}
        </Col>
      </Row>
    </>
  );
};

BotonesAcciones.defaultProps = {
  modalTitle: MESSAGES.MSG006,
};

export const BotonesAccionesTop = ({
  handleAddProvider,
  handleDownloadExcel,
  disabledAddProviderButton,
  disabledExcelButton,
  editable,
  isChecked = true,
  onChangeCheck = () => {},
  isVisibleCheck = false,
  isVisibleAddAndExcel = true,
  type = "",
  process,
}) => {
  const [checked, setChecked] = useState(isChecked);
  return (
    <>
      <Row>
        <Col md="6" className="text-start mb-3">
          {isVisibleCheck && ( //Esto se se utiliza?
            <div style={{ display: "flex" }}>
              <p>Sí &nbsp;</p>
              <Form.Check
                type={"checkbox"}
                id={"1"}
                disabled={!editable}
                onChange={() => {
                  const value = !checked;
                  setChecked(value);
                  onChangeCheck(value);
                }}
                style={{ cursor: "pointer" }}
                checked={checked}
              />
            </div>
          )}
        </Col>
        {isVisibleAddAndExcel && (
          <Col md="6" className="text-end mb-2">
            {type === "" ? (
              <IconButton
                disabled={!editable}
                type="add"
                onClick={handleAddProvider}
                tooltip={"Nuevo"}
              />
            ) : (
              <Authorization process={process}>
                <IconButton
                  disabled={!editable}
                  type="add"
                  onClick={handleAddProvider}
                  tooltip={"Nuevo"}
                />
              </Authorization>
            )}

            <IconButton
              type="excel"
              onClick={handleDownloadExcel}
              disabled={disabledExcelButton}
              tooltip={"Exportar a Excel"}
            />
          </Col>
        )}
      </Row>
    </>
  );
};
