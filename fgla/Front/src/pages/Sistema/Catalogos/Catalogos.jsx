import { getData, postData, putData, downloadDocument } from "../../../functions/api";


export const getAllCatalogs = () => getData('/catalogos/catalogos');

export const getAllComplementarios = () => getData('/catalogos/catalogos-complementarios');

export const getDataCatalog = (idCatalog) => getData('/catalogos/catalogos/informacion/' + idCatalog);

export const getCentrales = (idGeneral) => getData('/catalogos/catalogos/informacion/centrales/' + idGeneral);

export const getAdministraciones = (idCentral) => getData('/catalogos/catalogos/informacion/administraciones/' + idCentral);

export const getMapas = (idAlineacion) => getData('/catalogos/catalogos/informacion/mapas/' + idAlineacion);

export const downloadCentrales = (idGeneral) => downloadDocument('/catalogos/reporte/central/' + idGeneral);

export const downloadAdministraciones = (idCentral) => downloadDocument('/catalogos/reporte/administracion/' + idCentral);

export const downloadMapas = (idAlineacion) => downloadDocument('/catalogos/reporte/mapas/' + idAlineacion);

export const downloadAdminGenerales = (idGeneral) => downloadDocument('/catalogos/reporte/general/administrador/' + idGeneral);

export const downloadAdminCentrales = (idCentral) => downloadDocument('/catalogos/reporte/central/administrador/' + idCentral);

export const downloadAminAdmons = (idAdmon) => downloadDocument('/catalogos/reporte/administracion/administrador/' + idAdmon);

export const getDataCatalogComp = (idComp, idCatalog) => getData('/catalogos/catalogos/informacion/' + idComp + '/' + idCatalog);

export const getMetadataCatalog = (idCatalog) => getData('/catalogos/catalogos/alta-meta/' + idCatalog);

export const saveDataCatalog = (idCatalog, data) => putData('/catalogos/catalogos/guardar/' + idCatalog, data);

export const updateDataCatalog = (idCatalog, data) => postData('/catalogos/catalogos/actualizar/' + idCatalog, data);

export const downloadCatalog = (idCatalog) => downloadDocument('/catalogos/reporte/' + idCatalog);

export const putGenerales = (data) => putData('/catalogos/catalogos/guardar-admon-general', data);

export const putCentrales = (data) => putData('/catalogos/catalogos/guardar-admon-centrales', data);

export const putAdministraciones = (data) => putData('/catalogos/catalogos/guardar-admnistraciones', data);


export const getEmpleados = (idAdministracion) => 
   getData(`/catalogos/empleados-administracion/${idAdministracion}`);


export const putEmpleados = (idAdministracion, data) => 
    putData(`/catalogos/empleados-administracion-guardar/${idAdministracion}`, data);


export const downloadEmpleados = (idAdministracion) => 
    downloadDocument(`/catalogos/reporte/empleados-administracion/${idAdministracion}`, { responseType: 'blob' });


export const getTipoEmpleado =  ()=> 
   getData(`/catalogos/tipo-empleado`);




