package com.sisecofi.contratos.controller;
import com.sisecofi.contratos.dto.ActualizarContratoDto;
import com.sisecofi.contratos.dto.CierreContratoDto;
import com.sisecofi.contratos.dto.ContratoDtoLigero;
import com.sisecofi.contratos.dto.CriteriosDeBusquedaContratoDto;
import com.sisecofi.contratos.dto.InicialContratoDto;
import com.sisecofi.contratos.service.ServicioContratos;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import org.apache.commons.text.StringEscapeUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class ContratosCtrl {

    private final ServicioContratos contratos;

    @GetMapping("/contrato/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContrato()"
    )
    public ResponseEntity<ContratoDto> obtenerContrato(@PathVariable("idContrato") Long idContrato) {

        return new ResponseEntity<>(contratos.obtenerContratoPorId(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/contrato-ultima-mod/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContrato()"
    )
    public ResponseEntity<String> obtenerUltimaModificacion(@PathVariable("idContrato") Long idContrato) {

    	String ultimaMod = contratos.utlimaMod(idContrato);
    	String safeValue = StringEscapeUtils.escapeHtml4(ultimaMod);
    	return new ResponseEntity<>(safeValue, HttpStatus.OK);
    }

    @PostMapping("/buscar-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolVerificadorEspecificoContrato() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() ||" +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() ||"+
            "@seguridad.validarRolGestorDocumentalContrato() ||" +
            "@seguridad.validarRolUsuarioConsulta() ||" +
            "@seguridad.validarRolLiderProyecto() ||" +
            "@seguridad.validarRolAdministradorContrato()  ||" +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() ||"+
            "@seguridad.validarRolParticipantesAdmonDictamen() ||"+
            "@seguridad.validarRolParticipantesAdmonVerificacion() ||"+
            "@seguridad.validarRolVerificadorGeneral() ||" +
            "@seguridad.validarRolVerificadorEspecificoContrato()"
    )
    public ResponseEntity<Page<ContratoDtoLigero>> buscarontrato(@Valid @RequestBody CriteriosDeBusquedaContratoDto criterios){
        return new ResponseEntity<>(contratos.buscarContratos(criterios), HttpStatus.CREATED);
    }

    @PutMapping("/editar-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> editarContrato(@Valid @RequestBody ActualizarContratoDto criterios){
        return new ResponseEntity<>(contratos.actualizarContrato(criterios), HttpStatus.CREATED);
    }

    @PutMapping("/ejecutar-contrato/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> ejecutarContrato(@PathVariable("idContrato") Long idContrato) {

        return new ResponseEntity<>(contratos.ejecutarContrato(idContrato), HttpStatus.OK);
    }

    @PostMapping("/inicial-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " + "@seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<ContratoDto> inicialContrato(@Valid @RequestBody InicialContratoDto inicial){
        return new ResponseEntity<>(contratos.iniciarContrato(inicial), HttpStatus.OK);
    }

    @PutMapping("/cancelar-contrato/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> eliminarContrato(@PathVariable("idContrato") Long idContrato) {

        return new ResponseEntity<>(contratos.cancelarContrato(idContrato), HttpStatus.OK);
    }

    @GetMapping("/proyectos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolGestorDocumentalContrato() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()" +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ProyectoSimpleDto>> obtenerProyectos() {

        return new ResponseEntity<>(contratos.obtenerProyectos(), HttpStatus.OK);
    }
    
    @GetMapping("/proyectos-completos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolGestorDocumentalContrato() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones()" +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ProyectoSimpleDto>> obtenerProyectosCompletos() {

        return new ResponseEntity<>(contratos.obtenerProyectosCompletos(), HttpStatus.OK);
    }

    @PutMapping("/regresar-contrato-inicial/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> cambiarEstatusInicial(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(contratos.regresarEstatusInicialContrato(idContrato), HttpStatus.OK);
    }

    @PostMapping("/cierre-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> cierreContrato(@Valid @RequestBody CierreContratoDto inicial){
        return new ResponseEntity<>(contratos.cierreContrato(inicial), HttpStatus.OK);
    }

    @PutMapping("/cierre-contrato/{idContrato}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario()")
    public ResponseEntity<String> cerrarContrato(@PathVariable("idContrato") Long idContrato) {

        return new ResponseEntity<>(contratos.cierreEstatusContrato(idContrato), HttpStatus.OK);
    }

    @GetMapping("/prueba-version-integracion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<String> obtenerVersion() {
		return new ResponseEntity<>("Version 18/06/2025", HttpStatus.OK);
	}

}
