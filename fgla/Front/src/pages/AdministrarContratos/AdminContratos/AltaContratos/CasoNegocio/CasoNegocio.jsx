import React, { useState, useEffect, useMemo, useCallback } from "react";
import ProyeccionCasoNegocioTable from "./ProyeccionCasoNegocioTable/ProyeccionCasoNegocioTable";
import CasoNegocioActions from "./CasoNegocioActions";
import BasicModal from "../../../../../modals/BasicModal";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import Loader from "../../../../../components/Loader";
import { getData } from "../../../../../functions/api";
import { useParams, useSearchParams } from "react-router-dom";

export function CasoNegocio({ isDetalle }) {
  const [idContrato, setIdContrato] = useState(null);
  const [candit, setCanEdit] = useState(true);
  const [isLoading, setIsLoading] = useState(false);

  const { idContrato: contratoParam } = useParams();
  let [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    const q = searchParams.get("q");
    setIdContrato(contratoParam || q);
  }, [contratoParam, searchParams]);

  //#region Permisos
  const permisosIniciales = {
    leer: true,
    edicion: true,
  };

  const [permisos, setPermisos] = useState(permisosIniciales);
  const [editar, setEditar] = useState(false);

  useEffect(() => {
    let { leer, edicion } = permisos;

    setEditar(edicion);
  }, [permisos]);

  //#endregion
  //#region Message Modal

  const [showMessage, setShowMessage] = useState(false);
  const [message, setMessage] = useState("");
  const handleCloseMessage = () => {
    setMessage("");
    setShowMessage(false);
  };
  const handleShowMessage = (message) => {
    setMessage(message);
    setShowMessage(true);
  };
  //#endregion

  //#region Confirm Modal

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalMessage, setConfirmModalMesage] = useState("");
  const _confirmData = {
    approve: () => {},
    deny: () => {},
  };
  const [confirmData, setConfirmData] = useState(_confirmData);

  const handleShowConfirmModal = (
    title,
    approve = _confirmData.approve,
    deny = _confirmData.deny
  ) => {
    setConfirmModalMesage(title);
    setConfirmData({ approve, deny });
    setShowConfirmModal(true);
  };
  const handleApprove = () => {
    if (confirmData?.approve) {
      confirmData.approve();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  const handleDenny = () => {
    if (confirmData?.deny) {
      confirmData.deny();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };
  //#endregion

  //#region Get Data
  const [mapa, setMapa] = useState(null);
  const [cargado, setCargado] = useState(false);
  const getDataCasoNegocio = useCallback(() => {
    setIsLoading(true);
    getData("/admin-contratos/caso-negocio/" + idContrato)
      .then((response) => {
        setIsLoading(false);
        if (response?.data) {
          let { mapa, cargado } = response.data;
          setMapa(mapa);
          setCargado(cargado);
        }
      })
      .catch((error) => {
        setIsLoading(false);
      });
  }, [idContrato]);

  useEffect(() => {
    if (idContrato) {
      getDataCasoNegocio();
    }
  }, [getDataCasoNegocio, idContrato]);
  //#endregion

  return (
    <>
      {isLoading && <Loader />}

      <SingleBasicModal
        size="md"
        show={showMessage}
        onHide={handleCloseMessage}
        title="Mensaje"
        approveText={"Aceptar"}
      >
        {message}
      </SingleBasicModal>
      <BasicModal
        size="md"
        handleApprove={handleApprove}
        handleDeny={handleDenny}
        denyText={"No"}
        approveText={"Sí"}
        show={showConfirmModal}
        title="Mensaje"
        onHide={handleDenny}
      >
        {confirmModalMessage}
      </BasicModal>
      {candit && (
        <CasoNegocioActions
          idContrato={idContrato}
          handleShowMessage={handleShowMessage}
          handleShowConfirmModal={handleShowConfirmModal}
          setIsLoading={setIsLoading}
          dataCasoNegocio={mapa}
          setMapa={setMapa}
          cargado={cargado}
          getDataCasoNegocio={getDataCasoNegocio}
          isDetalle={isDetalle}
        />
      )}
      <ProyeccionCasoNegocioTable
        idContrato={idContrato}
        setIsLoading={setIsLoading}
        setMapa={setMapa}
        mapa={mapa}
      />
    </>
  );
}
