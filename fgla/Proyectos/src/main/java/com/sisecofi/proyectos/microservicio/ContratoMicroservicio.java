package com.sisecofi.proyectos.microservicio;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "contratoholder", url = "${url.contratos}")
public interface ContratoMicroservicio {

        @GetMapping("/admin-contratos-internos/obtener-contratos")
        List<ContratoNombreDto> obtenerContratos();
        
        @GetMapping("/admin-contratos-internos/obtener-contratos/{idProyecto}")
        List<ContratoNombreDto> obtenerContratosIdProyecto(@PathVariable Long idProyecto);

        @GetMapping("/admin-contratos-internos/obtener-contrato/{idContrato}")
        ContratoDto obtenerContatoPorId(@PathVariable("idContrato") Long idContrato);

}
