package com.sisecofi.contratos.service.convenio_modificatorio;

import java.util.List;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;

public interface ServicioConvenioPlantilla {
   
    Boolean asociarPlantillas (ConvenioPlantilla asociacion, Long idConvenio);
    
    Boolean editarAsociacionPlantillas (ConvenioPlantilla lista);
    
    Boolean eliminarAsociacionPlantillas(List<Long> ids);
    
    List<ConvenioPlantilla> obtenerAsociaciones(Long idConvenio);
}
