package com.sisecofi.reportedocumental.microservicio;

import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "contratoholder", url = "${url.contratos}")
public interface MatrizContratoMicroservicio {

    @SuppressWarnings("rawtypes")
	@GetMapping("/matriz-documental-contrato/{idContrato}")
    List<CarpetaDtoResponse> obtenerMatrizContrato(@PathVariable("idContrato") Long idContrato);
    
    @SuppressWarnings("rawtypes")
	@GetMapping("/matriz-documental-convenio/{idConvenio}")
    List<CarpetaDtoResponse> obtenerMatrizConvenio(@PathVariable("idConvenio") Long idConvenio);
    
    @SuppressWarnings("rawtypes")
	@GetMapping("/matriz-documental-reintegro/{idContrato}")
    List<CarpetaDtoResponse> obtenerMatrizReintegro(@PathVariable("idContrato") Long idContrato);


}
