import React, { useEffect, useState } from "react";
import { Loader } from "../../../components";
import * as yup from "yup";
import ReintegrosTable from "./Components/ReintegrosTable";
import ReintegrosFilter from "./Components/ReintegrosFilter";
import { useToast } from "../../../hooks/useToast";

import BasicModal from "../../../modals/BasicModal";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import { useLazyGetReintegrosQuery } from "./Store";
import { REGISTRAR_REINTEGROS } from "../../../constants/messages";
import { Formik } from "formik";
import _ from "lodash";

import {
  usePostRRContratosCatMutation,
  useGetRRContratosVigentesCatQuery,
} from "./Store";

export default function RegistrarReintegros({
  setIdContratoGestion,
  setDisabledReintegros,
}) {
  //#region Message Modal
  const { errorToast } = useToast();

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

  //#region Servicio

  const [getReintegros, { isLoading: isLoadingReintegros }] =
    useLazyGetReintegrosQuery();

  const [dataReintegros, setDataReintegros] = useState([]);
  const [disableFilter, setDisableFilter] = useState(false);
  const [showTable, setShowTable] = useState(false);

  //#endregion

  //#region Servicios
  const { data: dataContratoVigente, isLoading: isLoadingContratosVigentes } =
    useGetRRContratosVigentesCatQuery();
  const [contratosVigentesCat, setContratosVigentesCat] = useState([]);
  useEffect(() => {
    setContratosVigentesCat(dataContratoVigente);
  }, [dataContratoVigente]);

  const [
    getContratosCat,
    {
      data: dataContratos,
      isLoading: isLoadingContratos,
      isFetching: isFetchingContratos,
    },
  ] = usePostRRContratosCatMutation();

  const [contratosCat, setContratosCat] = useState([]);
  useEffect(() => {
    setContratosCat(dataContratos);
  }, [dataContratos]);
  //#endregion

  const obtenerReintegros = (idContrato, ejecucion) => {
    setIdContratoGestion(idContrato);
    setIdContrato(idContrato);
    setDisableActions(!ejecucion);
    setDisabledReintegros(!ejecucion);
    getReintegros(idContrato).then((response) => {
      if (response.error) {
        handleShowMessage(REGISTRAR_REINTEGROS.MSG004);
        setShowTable(true);
        setDisableFilter(true);
      } else {
        setDisableFilter(true);
        let { data } = response;
        if (_.isEmpty(data)) {
          handleShowMessage(REGISTRAR_REINTEGROS.MSG004);
        }
        setShowTable(true);
        setDataReintegros(data || []);
      }
    });
  };

  const [idContrato, setIdContrato] = useState(null);
  const [disableActions, setDisableActions] = useState(true);

  const initialValues = {
    contratosVigentes: "",
    contrato: "",
  };

  const validationSchema = yup.object().shape({
    contratosVigentes: yup.string().required("Dato requerido"),
    contrato: yup.string().required("Dato requerido"),
  });

  const handleBuscar = (values, props) => {
    let { contrato } = values;
    let ejecucion = false;
    if (_.isArray(contratosCat)) {
      let contratoCat = contratosCat.find((s) => s.idContrato == contrato);
      ejecucion = contratoCat.ejecucion;
    }

    obtenerReintegros(contrato, ejecucion);
  };

  return (
    <Formik
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={handleBuscar}
    >
      {({ values, isValid, handleSubmit, handleChange, setFieldValue }) => {
        const cancelarReintegros = () => {
          setDisableFilter(false);
          setShowTable(false);
          setDataReintegros([]);
          setIdContrato(null);
          setDisableActions(true);
          setDisabledReintegros(true);
          setFieldValue("contratosVigentes", "");
          setFieldValue("contrato", "");
          setIdContratoGestion(null);
        };

        return (
          <>
            {(isLoadingContratos ||
              isLoadingContratosVigentes ||
              isFetchingContratos ||
              isLoadingReintegros) && <Loader />}
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

            <ReintegrosFilter
              handleShowMessage={handleShowMessage}
              handleShowConfirmModal={handleShowConfirmModal}
              obtenerReintegros={obtenerReintegros}
              disabledFilter={disableFilter}
              contratosVigentesCat={contratosVigentesCat}
              getContratosCat={getContratosCat}
              contratosCat={contratosCat}
            />
            {showTable && (
              <ReintegrosTable
                handleShowMessage={handleShowMessage}
                handleShowConfirmModal={handleShowConfirmModal}
                data={dataReintegros}
                cancelarReintegros={cancelarReintegros}
                obtenerReintegros={obtenerReintegros}
                idContrato={idContrato}
                disableActions={disableActions}
              />
            )}
          </>
        );
      }}
    </Formik>
  );
}
