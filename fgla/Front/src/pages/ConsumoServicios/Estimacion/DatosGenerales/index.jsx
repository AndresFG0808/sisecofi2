import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import FormularioDatosGenerales from "./FormularioDatosGenerales";


const DatosGenerales = ({reloadRegistro, setReloadRegistroServicios, setEstadoInicial,setReloadRegistro,actualizaFecha,idDEstimacionDuplicado, setIdDesencriptado ,setShowVolumetriaActiva, showVolumetriaActiva ,dicRelacionados, setupdateMonto, updateMonto, setIsDuplicado,setVolumetríaActiva,setEstimacionCancelada, volumetriaActiva, setIdContrato,idEstimacion, getIdProveedor, proyecto, setIdEstimacion, setlastModificacion, setFechasContrato,  }) => {
  const [loading, setLoading] = useState(true);
  const { state } = useLocation();
  const [ver, setVer] = useState(state?.ver || false);
  const [edit, setEdit] = useState(state?.edit || false);

  const location = useLocation();
  const editable = location?.state?.estimacionState?.editable;
  console.log("Editable flag:", editable);

  idEstimacion = idEstimacion === undefined ? "" : idEstimacion;

  useEffect(() => {
    setEdit(true);
    getDataInit();
    setIdEstimacion(idEstimacion)
  }, []);

  const getDataInit = () => {
    setLoading(false);
  };

  return (
    <>
      <FormularioDatosGenerales reloadRegistro={reloadRegistro} setReloadRegistroServicios={setReloadRegistroServicios} actualizaFecha={actualizaFecha} idDEstimacionDuplicado={idDEstimacionDuplicado} setIdDesencriptado={setIdDesencriptado} setReloadRegistro={setReloadRegistro} setShowVolumetriaActiva={setShowVolumetriaActiva}  showVolumetriaActiva={showVolumetriaActiva} dicRelacionados={dicRelacionados} ver={ver} edit={edit} setIdEstimacion={setIdEstimacion} setlastModificacion={setlastModificacion} setFechasContrato={setFechasContrato} idEstimacion={idEstimacion} getIdProveedor={getIdProveedor}setIdContrato={setIdContrato} volumetriaActiva={volumetriaActiva} setVolumetríaActiva={setVolumetríaActiva} setEstimacionCancelada={setEstimacionCancelada} setIsDuplicado={setIsDuplicado} setEstadoInicial={setEstadoInicial} setupdateMonto={setupdateMonto} updateMonto={updateMonto}/>
    </>
  );
};

export default DatosGenerales;
