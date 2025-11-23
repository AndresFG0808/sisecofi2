package com.sisecofi.admindevengados.service;

import java.math.BigDecimal;
import java.util.List;

import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.dto.ResumenConsolidadoDto;
import com.sisecofi.admindevengados.model.ResumenConsolidadoModel;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.util.exception.NexusException;

public interface DictamenService {

	Dictamen guardarDictamen(Long idContrato, dictamenDto dictamen);

	List<Dictamen> obtenerDictamenes();

	dictamenDto obtenerDictamenesId(Long dictamenId);

	ContratoDto obtenerContratoDto(Long idContrato);

	List<Dictamen> obtenerContratosDictamen(Long idContrato);

	List<Dictamen> obtenerDictamenesByEstatus(String nombreEstatus, Long idContrato);

	List<Dictamen> anterior(Long dictamenId);

	List<Dictamen> siguiente(Long dictamenId);

	Dictamen editarDictamen(Long dictamenId, dictamenDto dictamendto);

	boolean cancelarDictamen(Long dictamenId, String descripcion);

	List<ResumenConsolidadoDto> obtenerResumenConsolidado(Long dictamenId);

	BigDecimal generarMonto(Long dictamenId);

	List<DevengadoBusquedaResponse> obtenerDictamenesByEstatusAndProveedor(Long idContrato, Integer idEstatusDictamen,
			Long idProveedor);

	Dictamen actualizarEstatusInicial(Long dictamenId);

	Dictamen duplicarDictamen(Long idDictamen, Boolean registrosDictaminados, Boolean penasContractuales,
			Boolean penasConvencionales, Boolean deducciones, dictamenDto dictamenDto);

	List<ResumenConsolidadoModel> actualizarMontoResumenConsolidado(Long dictamenId, BigDecimal monto);

	Integer validarDictamenAnteriorYSiguiente(Long dictamenId);

	Integer validarTipoCambio(Long idContrato);

	List<DevengadoBusquedaResponse> obtenerDictamenesPorIdContrato(Long idContrato);

	String generarFormatoIdDictamen(String nombreCorto, Long idProveedor);

	String ultimaModificacionGeneral();

	Integer buscarEstatusDictamen(String estatus);

	String generarRuta(String idDictamen, ContratoModel contrato);

	String generarRutaFase(String idDictamen, ContratoModel contrato);

	String generarRutaDictamen(String idDictamen, ContratoModel contrato);
	
	List <Archivo> obtenerArchivos(Long idProyecto);


	String generarRutaProforma(String idDictamen, ContratoModel contrato);

	List<Archivo> obtenerOtrosArchivosDictamen(Long idProyecto);

	String validarIva(Long idContrato);

	List<ResumenConsolidadoModel> actualizarResumenConsolidado(Long dictamenId);

	List<ResumenConsolidadoModel> actualizarResumeFacturado(Long dictamenId);

	Long validarPenas(Long idContrato, Integer idTipo);

	void crearArchivos(Long dictamenId, String nombreCorto, Long idProyecto, Boolean plantillaAsignada, String idDictamenVisual);

	String actualizarCheckPenasDeducciones(Long idDictamen, Boolean checkContractual, Boolean checkConvencional,
			Boolean checkDeduccion);

	String generarRutaDictamen(String idDictamen, String nombrecorto, Long idProyecto);
	
	Boolean validarCancelacionProyecto(Long idProyecto);

	ContratoDtoLigeroComun obtenerContratoLigero(Long idContrato);

	Boolean sincronizarArchivos(Long idDictamen) throws NexusException;

}
