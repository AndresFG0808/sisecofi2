package com.sisecofi.contratos.controller.reintegros;

import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.contratos.dto.reintegros.CatTipoReintegrosDto;
import com.sisecofi.contratos.dto.reintegros.ConsultaContratoDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosConsultaDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosModificaDto;
import com.sisecofi.contratos.repository.reintegros.CatTipoReintegroRepository;
import com.sisecofi.contratos.service.reintegros.ReintegroConsultaService;
import com.sisecofi.contratos.service.reintegros.ReporteReintegroService;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoReintegro;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/" + Constantes.PATH_BASE)
public class ReintegrosCtrl {

    private final ReintegroConsultaService reintegroConsultaService;
    private final CatTipoReintegroRepository catTipoReintegroRepository;
    private final ReporteReintegroService reporteReintegroService;
    


    
    @PostMapping("/contratos-vigentes")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                  "@seguridad.validarRolAdminSistemaSecundario() || " +
                  "@seguridad.validarRolApoyoAcppi() || " +
                  "@seguridad.validarRolUsuarioConsulta() || " +
                  "@seguridad.validarRolLiderProyecto() || " +
                  "@seguridad.validarRolAdministradorContrato() || " +
                  "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                  "@seguridad.validarRolVerificadorGeneral() || " +
                  "@seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ContratoSimpleDto>> obtenerContratosVigentes(@RequestBody ConsultaContratoDto dto) {
        List<ContratoSimpleDto> contratos = reintegroConsultaService.obtenerContratosVigentes(dto.getVigencia());
        return ResponseEntity.ok(contratos);
    }



    // endpoint para crear
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                 "@seguridad.validarRolAdminSistemaSecundario() || " +
                 "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                 "@seguridad.validarRolVerificadorGeneral() || " + 
                 "@seguridad.validarRolVerificadorEspecificoContrato()")
    @PostMapping("/reintegros/crear-reintegro")
    public ResponseEntity<List<ReintegrosDto>> crearReintegros(@RequestBody List<ReintegrosDto> reintegrosDtoList) {
        List<ReintegrosDto> responseList = reintegroConsultaService.crearReintegros(reintegrosDtoList);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                 "@seguridad.validarRolAdminSistemaSecundario() || " +
                 "@seguridad.validarRolApoyoAcppi() || " +
                 "@seguridad.validarRolUsuarioConsulta() || " +
                 "@seguridad.validarRolLiderProyecto() || " +
                 "@seguridad.validarRolAdministradorContrato() || " +
                 "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                 "@seguridad.validarRolVerificadorGeneral() || " + 
                 "@seguridad.validarRolVerificadorEspecificoContrato()")
    @GetMapping("/reintegros/tipo-reintegros")
    public ResponseEntity<List<CatTipoReintegrosDto>> obtenerTiposReintegros() {
        List<CatTipoReintegro> tipoReintegros = catTipoReintegroRepository.findByEstatusTrue();

        List<CatTipoReintegrosDto> tipoReintegrosDto = tipoReintegros.stream()
                .map(tipo -> new CatTipoReintegrosDto(tipo.getIdTipoReintegro(), tipo.getNombre()))
                .toList();

        return ResponseEntity.ok(tipoReintegrosDto);

    }

    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                 "@seguridad.validarRolAdminSistemaSecundario() || " +
                 "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                 "@seguridad.validarRolVerificadorGeneral() || " + 
                 "@seguridad.validarRolVerificadorEspecificoContrato()")
    @PutMapping("/reintegros/modificar-reintegro")
    public ResponseEntity<List<ReintegrosModificaDto>> modificarReintegros(@RequestBody List<ReintegrosModificaDto> reintegrosDtoList) {
        List<ReintegrosModificaDto> responseList = reintegroConsultaService.modificarReintegros(reintegrosDtoList);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                 "@seguridad.validarRolAdminSistemaSecundario() || " +
                 "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                 "@seguridad.validarRolVerificadorGeneral() || " + 
                 "@seguridad.validarRolVerificadorEspecificoContrato()")
    @DeleteMapping("/reintegros/eliminar-reintegros")
    public ResponseEntity<Void> eliminarReintegros(@RequestBody List<Long> idReintegrosAsociadosList) {
        reintegroConsultaService.eliminarReintegros(idReintegrosAsociadosList);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                 "@seguridad.validarRolAdminSistemaSecundario() || " +
                 "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                 "@seguridad.validarRolVerificadorGeneral() || " + 
                 "@seguridad.validarRolVerificadorEspecificoContrato()")
    @GetMapping("/reintegros/generar-reporte-reintegros")
    public ResponseEntity<String> obtenerReporteReintegros(@RequestParam("idContrato") Long idReintegrosAsociados){
    byte[] contenido = reporteReintegroService.obtenerReporteReintegro(idReintegrosAsociados);

    String contenidoBase64 = Base64.getEncoder().encodeToString(contenido);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

    return ResponseEntity.ok().headers(headers).body(contenidoBase64);


        
    }
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
                 "@seguridad.validarRolAdminSistemaSecundario() || " +
                 "@seguridad.validarRolApoyoAcppi() || " +
                 "@seguridad.validarRolUsuarioConsulta() || " +
                 "@seguridad.validarRolLiderProyecto() || " +
                 "@seguridad.validarRolAdministradorContrato() || " +
                 "@seguridad.validarRolParticipantesAdmonVerificacion() || " +
                 "@seguridad.validarRolVerificadorGeneral() || " + 
                 "@seguridad.validarRolVerificadorEspecificoContrato()")
    @GetMapping("/reintegros/buscar-reintegros-asociados")
    public ResponseEntity<List<ReintegrosConsultaDto>> buscarReintegrosPorIdContrato(@RequestParam Long idContrato) {
        List<ReintegrosConsultaDto> reintegros = reintegroConsultaService.buscarReintegrosPorContratos(idContrato);
        return ResponseEntity.ok(reintegros);
    }


    



}
