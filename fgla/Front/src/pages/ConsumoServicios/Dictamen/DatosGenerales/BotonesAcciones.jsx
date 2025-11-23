import React, { useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import CancelModal from "./CancelModal";
import { Form } from "react-bootstrap";
import Authorization from "../../../../components/Authorization";
import { useGetAuthorization } from "../../../../hooks/useGetAuthorization";

export const BotonesAcciones = ({
  isVisible,
  handleFetchProvider,
  handleCancelAddProviders,
  modalTitle,
  editable,
  isVisibleCancelButton = true,
  secondaryButton = "Cancelar",
  showCancelModal = true,
  cancelButtonType = "button",
  processType = "",
  process,
}) => {
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);

  const handleCancelButton = () =>
    showCancelModal ? setIsOpenCancelModal(true) : handleCancelAddProviders();

  const handleApprove = () => {
    setIsOpenCancelModal(false);
    handleCancelAddProviders();
  };

  return (
    <>
      <Row>
        <Col md={12} className="text-end">
          {processType === "" ? (
            isVisible && (
              <>
                {isVisibleCancelButton && (
                  <Button
                    variant="red"
                    disabled={!editable}
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={handleCancelButton}
                    type={cancelButtonType}
                  >
                    {secondaryButton}
                  </Button>
                )}
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
            )
          ) : processType === "2" ? (
            <Authorization process={process}>
              <>
                {isVisibleCancelButton && (
                  <Button
                    variant="red"
                    disabled={!editable}
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={handleCancelButton}
                    type={cancelButtonType}
                  >
                    {secondaryButton}
                  </Button>
                )}
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
            </Authorization>
          ) : (
            <>
              <Authorization process={process}>
                <>
                  {isVisibleCancelButton && (
                    <Button
                      variant="red"
                      disabled={!editable}
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={handleCancelButton}
                      type={cancelButtonType}
                    >
                      {secondaryButton}
                    </Button>
                  )}
                </>
              </Authorization>
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
  modalTitle: "",
};

export const BotonesAccionesTop = ({
  handleAddProvider,
  handleDownloadExcel,
  disabledAddProviderButton,
  disabledExcelButton,
  editable,
  isChecked = true,
  processType,
  onChangeCheck = () => {},
  isVisibleCheck = false,
  isVisibleAddAndExcel = true,
  buttonAddText = "Nuevo",
}) => {
  const { isAuthorized: isAuthorizedContractual } = useGetAuthorization(
    "CON_SERV_DICT_PCONT_ADMIN"
  );
  const { isAuthorized: isAuthorizedCnvencional } = useGetAuthorization(
    "CON_SERV_DICT_PCONV_ADMIN"
  );
  const { isAuthorized: isAuthorizedDeduccion } = useGetAuthorization(
    "CON_SERV_DICT_DEDUC_ADMIN"
  );
  const [checked, setChecked] = useState(isChecked);
  const isGetPermissionByRol = () => {
    let isVisible = false;
    if (processType == 1 && isAuthorizedContractual) {
      isVisible = true;
    }
    if (processType == 2 && isAuthorizedCnvencional) {
      isVisible = true;
    }
    if (processType == 3 && isAuthorizedDeduccion) {
      isVisible = true;
    }
    return isVisible;
  };
  return (
    <>
      <Row>
        {/* <Col md="6" className="text-start mb-3">
          {isVisibleCheck && ( //Esto se se utiliza?
            <div style={{ display: "flex" }}>
              <p>Sí &nbsp;</p>
              <Form.Check
                type={"checkbox"}
                id={"1"}
                disabled={false}
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
        </Col> */}
        {isVisibleAddAndExcel && (
          <Col md="12" className="text-end mb-2">
            {isGetPermissionByRol() === true ? (
              <IconButton
                disabled={!editable}
                type="add"
                onClick={handleAddProvider}
                tooltip={buttonAddText}
              />
            ) : (
              <Authorization process={"CON_SERV_ADMIN_DICT"}>
                <IconButton
                  disabled={!editable}
                  type="add"
                  onClick={handleAddProvider}
                  tooltip={buttonAddText}
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
