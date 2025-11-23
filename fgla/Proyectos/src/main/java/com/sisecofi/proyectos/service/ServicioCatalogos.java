package com.sisecofi.proyectos.service;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

import java.util.List;

public interface ServicioCatalogos {
    List<BaseCatalogoModel> obtenerContratoConvenioInfo();

	List<ContratoNombreDto> obtenerContratosInfo();

    List<BaseCatalogoModel> obtenetComitesInfo();

    List<BaseCatalogoModel> obtenerAfectacionInfo();

    List<BaseCatalogoModel> obtenerSesionInfo();

	List<BaseCatalogoModel> obtenerSesionNumeroInfo();

    List<BaseCatalogoModel> obtenerPlantillaInfo();

    List<BaseCatalogoModel> obtenerTipoDeMonedaInfo();
    
    List<BaseCatalogoModel> obtenerAdmonGenerales();
	
	List<BaseCatalogoModel> obtenerAdmonCentrales();
	
	List<BaseCatalogoModel> obtenerClasificacionProyecto();
	
	List<BaseCatalogoModel> obtenerFinanciamiento();
	
	List<BaseCatalogoModel> obtenerEstatus();
	
	BaseCatalogoModel obtenerEstatusInicial();
	
	List<BaseCatalogoModel> obtenerTipoProcedimiento();
	
	List<BaseCatalogoModel> obtenerPeriodo();
	
	List<BaseCatalogoModel> obtenerFases();
	
	List<BaseCatalogoModel> obtenerAlineaciones();
	
	List<BaseCatalogoModel> obtenerInvestigacionMercado();
	
	List<BaseCatalogoModel> obtenerOjetivosAlineacion(Integer idAlineacion);
	
	List<BaseCatalogoModel> obtenerAdmonCentralPorGeneral(Integer idAdmonGeneral);
	
	List<BaseCatalogoModel> obtenerAdmistracionPorCentrales();
	
	List<BaseCatalogoModel> obtenerAdmistracionPorCentral(Integer idAdmoncentral);

	List<BaseCatalogoModel> obteneREstatusRCP();

	List<BaseCatalogoModel> obteneEstatusEnProceso();

	List<BaseCatalogoModel> obtenerTodosEstatus();

	List<BaseCatalogoModel> obtenerUrl();

	List<ContratoNombreDto> obtenerContratosInfoIdProyecto(Long idProyecto);
	
}
