import React, { useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import BasicModal from "../../../../modals/BasicModal";
import showMessage from "../../../../components/Messages";
import { putData } from "../../../../functions/api";
import { Tooltip } from "../../../../components/Tooltip";
import Authorization from "../../../../components/Authorization";

export const BotonesAcciones = ({
  isVisible,
  handleFetchProvider,
  handleCancelAddProviders,
  modalTitle,
  editable,
  idEstimacion,
  readOnly,
  onReload,
  estatus,
  setVolumetríaActiva,
  idEstimacioncode,
  setIsDuplicado,
  setIsDuplicated,
  showVolumetriaActiva,
  setShowVolumetriaActiva,
  desbloqueoDuplicado,
  setReload,
  volumetriaActiva,
}) => {
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [isOpenInicialModal, setIsOpenInicialModal] = useState(false);


  const handleApproveInicial = async () => {
    setIsOpenInicialModal(false);
    setLoading(true);
    try {
      let estimacionLimpia = encodeURIComponent(idEstimacioncode);
      const responseCancelEstimacion = await putData(`/admin-devengados/cambiar-a-inicial?id=${estimacionLimpia}`, {});
      setLoading(false);
      setShowVolumetriaActiva(false)
      setVolumetríaActiva(false);
      setIsDuplicado(false)
      setIsDuplicated(false)
      /*  showMessage("Estimación cambiada a Inicial correctamente"); */
      if (onReload) {
        onReload();
      }
    } catch (err) {
      setLoading(false);
      showMessage("Error al cambiar la estimación a Inicial");
    }
  };


  const handleApprove = () => {

    setIsOpenCancelModal(false);
    setIsDuplicado(false)
    setIsDuplicated(false)
    handleCancelAddProviders(() => setIsOpenCancelModal(false));
    setReload(true)
  };
  const CancelVolumetria = () => {
    setIsOpenInicialModal(true)
  };
  const handleDeny = () => {
    setIsOpenCancelModal(false);
  };

  return (
    <>
      <Row>
        <Col md={12} className="text-end">
          {isVisible && (
            <>
              {(showVolumetriaActiva || volumetriaActiva || estatus !== "Inicial") && (
                <Authorization process={"CON_SERV_ESTIM_STA_INICIAL"}>
                  <Button
                    variant="gray"
                    disabled={
                      idEstimacion === "" ||
                      idEstimacion === null ||
                      readOnly ||
                      !editable
                    }
                    className="btn-sm ms-2 waves-effect waves-light"
                    onClick={CancelVolumetria}
                  >
                    <Tooltip
                      placement="top"
                      text={'Cambia la estimación a estatus "Inicial".'}
                    >
                      <span>Inicial</span>
                    </Tooltip>
                  </Button>
                </Authorization>
              )}


              {desbloqueoDuplicado === true ? (
                <>
                  <Authorization process={"CON_SERV_ADMIN_ESTIM"}>
                    <Button
                      variant="red"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={() => setIsOpenCancelModal(true)}
                    >
                      Cancelar
                    </Button>
                    <Button
                      variant="green"
                      className="btn-sm ms-2 waves-effect waves-light"
                      type="submit"
                      onClick={handleFetchProvider}
                    >
                      Guardar
                    </Button>
                  </Authorization>
                </>
              ) : (
                <>
                  <Authorization process={"CON_SERV_ADMIN_ESTIM"}>
                    <Button
                      variant="red"
                      className="btn-sm ms-2 waves-effect waves-light"
                      disabled={showVolumetriaActiva || volumetriaActiva || readOnly || !editable}
                      onClick={() => setIsOpenCancelModal(true)}
                    >
                      Cancelar
                    </Button>
                    <Button
                      variant="green"
                      className="btn-sm ms-2 waves-effect waves-light"
                      disabled={showVolumetriaActiva || volumetriaActiva || readOnly || !editable}
                      type="submit"
                      onClick={handleFetchProvider}
                    >
                      Guardar
                    </Button>
                  </Authorization>
                </>
              )}
              <BasicModal
                handleApprove={handleApprove}
                size={"md"}
                handleDeny={handleDeny}
                denyText={"No"}
                approveText={"Sí"}
                show={isOpenCancelModal}
                title={"Mensaje"}
                onHide={handleDeny}
              >
                {modalTitle}
              </BasicModal>
              <BasicModal
                handleApprove={handleApproveInicial}
                size={"md"}
                handleDeny={() => setIsOpenInicialModal(false)}
                denyText={"No"}
                approveText={"Sí"}
                show={isOpenInicialModal}
                title={"Mensaje"}
                onHide={() => setIsOpenInicialModal(false)}
              >
                ¿Está seguro de reabrir la estimación?.
              </BasicModal>
            </>
          )}
        </Col>
      </Row>
    </>
  );
};

BotonesAcciones.defaultProps = {
  modalTitle: "Estimación",
};
