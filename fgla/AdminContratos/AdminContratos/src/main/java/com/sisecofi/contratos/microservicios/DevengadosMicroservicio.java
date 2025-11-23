package com.sisecofi.contratos.microservicios;

import com.sisecofi.contratos.configuration.FeignConfig;
import com.sisecofi.contratos.dto.FacturaContratoResponse;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;


import com.sisecofi.libreria.comunes.dto.dictamen.PenasContractualesByIdDto;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "devengadosholder", url = "${url.devengados}", configuration = FeignConfig.class)
public interface DevengadosMicroservicio {

	@GetMapping("obtener-contrato-id/{idContrato}")
	String obtenerDictamen(@PathVariable("idContrato") Long idContrato);

    @GetMapping("/obtener-dictamen-contrato/{idContrato}")
    List<DevengadoBusquedaResponse> obtenerDictamenesPorIdContrato(@PathVariable("idContrato") Long idContrato);

	@GetMapping("/obtener-facturas-contrato/{idContrato}")
	FacturaContratoResponse obtenerFacturasContrato(@PathVariable Long idContrato);

	@GetMapping("/estimacion/{idContrato}")
	List<EstimacionModel> obtenerEstimacionesPorIdContrato(@PathVariable Long idContrato);

	@PostMapping("/penas-contractuales")
	List<PenasContractualesModel> obtenerPenasContractuales(@RequestBody PenasContractualesByIdDto penasContractualesByIdDto);
	
	@GetMapping("/verificar-penas/{idContrato}/{idTipo}")
	Long validarPenas(@PathVariable Long idContrato, @PathVariable("idTipo") Integer idTipo);
}
