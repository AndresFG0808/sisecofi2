package com.sisecofi.admindevengados.controller;

import java.util.List;

import com.sisecofi.admindevengados.service.PenasContractualesService;
import com.sisecofi.admindevengados.service.facturas.FacturaService;
import com.sisecofi.admindevengados.service.gestion_documental.ServicioGestionDocumental;
import com.sisecofi.libreria.comunes.dto.dictamen.PenasContractualesByIdDto;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class DictamenControllerInterno {

	private final DictamenService dictamenService;
	private final FacturaService facturaService;
	private final PenasContractualesService penasContractualesService;
	private final ServicioGestionDocumental servicioGestionDocumental;


	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO + "/obtener-dictamenes-id")
	@ConsumoInterno
	public ResponseEntity<ResponseGeneric<dictamenDto>> obtenerDictamenesId(@RequestBody DictamenId idDictamen) {

		dictamenDto dictamen = dictamenService.obtenerDictamenesId(idDictamen.getIdDictamen());
		ResponseGeneric<dictamenDto> data = ResponseGeneric.<dictamenDto>builder().build();
		data.setData(dictamen);
		data.setMsj(ErroresEnum.DICTAMEN_OBTENIDO);
		return new ResponseEntity<>(data, org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/obtener-contrato-id/{idContrato}")
	@ConsumoInterno
	public ResponseEntity<List<Dictamen>> obtenerContratoDictamene(@PathVariable Long idContrato) {
		return new ResponseEntity<>(dictamenService.obtenerContratosDictamen(idContrato), org.springframework.http.HttpStatus.OK);
	}
		
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/obtener-facturas-contrato/{idContrato}")
	@ConsumoInterno
	@Transactional
	public ResponseEntity<ResponseGeneric<List<FacturaContratoDto>>> obtenerFacturasContrato(@PathVariable Long idContrato) {
		List<FacturaContratoDto> facturas = facturaService.obtenerFacturasContrato(idContrato);
		ResponseGeneric<List<FacturaContratoDto>> data = ResponseGeneric.<List<FacturaContratoDto>>builder().build();
		data.setData(facturas);
		data.setMsj(ErroresEnum.VALIDAR_FACTURAS_RECIBIDAS);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/obtener-dictamen-contrato/{idContrato}")
	@ConsumoInterno
	public ResponseEntity<List<DevengadoBusquedaResponse>> obtenerDictamenesPorIdContrato(@PathVariable Long idContrato) {
		return new ResponseEntity<>(dictamenService.obtenerDictamenesPorIdContrato(idContrato), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/estatus-dictamenes/{idContrato}/{nombreEstatus}")
	@ConsumoInterno
	public List<Dictamen> obtenerDictamenesByEstatus(@PathVariable String nombreEstatus, @PathVariable Long idContrato) {
		return dictamenService.obtenerDictamenesByEstatus(nombreEstatus, idContrato);
	}

	@PostMapping("/"+ Constantes.PATH_BASE_INTERNO +"/penas-contractuales")
	@ConsumoInterno
	public List<PenasContractualesModel> obtenerPenasContractuales(@RequestBody PenasContractualesByIdDto penasContractualesByIdDto) {
		return penasContractualesService.obtenerPenasContractualesPorIds(penasContractualesByIdDto);
	}
	
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO+"/dictamenes/obtener-archivos/{idProyecto}")
	@ConsumoInterno
	public List<Archivo> obtenerArchivos(@PathVariable Long idProyecto) {
		return dictamenService.obtenerArchivos(idProyecto);
	}
	
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/dictamenes/obtener-otros-archivos/{idProyecto}")
	@ConsumoInterno
	public List<Archivo> obtenerOtrosArchivosDictamen(@PathVariable Long idProyecto) {
		return dictamenService.obtenerOtrosArchivosDictamen(idProyecto);
	}
	
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO+"/dictamenes/validar-cancelacion-proyecto/{idProyecto}")
	@ConsumoInterno
	public Boolean validarCancelacionProyecto(@PathVariable Long idProyecto) {
		return dictamenService.validarCancelacionProyecto(idProyecto);
	}
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/gestion-documental-dictamen/{idDitamen}")
	@ConsumoInterno
	public ResponseEntity<List<CarpetaDtoResponse>> obtenerContratoNombreCorto(@PathVariable("idDitamen") Long idDitamen) {
		return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumental(idDitamen), HttpStatus.OK);
	}
	

	@GetMapping("/"+ Constantes.PATH_BASE_INTERNO +"/verificar-penas/{idContrato}/{idTipo}")
	@ConsumoInterno
	public ResponseEntity<Long> validarPenas(@PathVariable("idContrato") Long idContrato, @PathVariable("idTipo") Integer idTipo) {
		return new ResponseEntity<>(dictamenService.validarPenas(idContrato, idTipo), HttpStatus.OK);
	}
	
}
