package com.sisecofi.contratos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

public interface ServicioContratoPlantilla {

	List<PlantillaVigenteModel> obtenerPlantillas();
   
    Boolean asociarPlantillas (ContratoPlantilla asociacion, Long idContrato);
    
    Boolean editarAsociacionPlantillas (ContratoPlantilla lista);
    
    Boolean eliminarAsociacionPlantillas(List<Long> ids);
    
    List<ContratoPlantilla> obtenerAsociaciones(Long idContrato);
}
