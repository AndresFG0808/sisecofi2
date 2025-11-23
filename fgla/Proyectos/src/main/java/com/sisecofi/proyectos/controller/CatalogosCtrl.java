package com.sisecofi.proyectos.controller;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.proyectos.service.ServicioCatalogos;
import com.sisecofi.proyectos.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class CatalogosCtrl {
    private final ServicioCatalogos catalogos;


    @GetMapping("/contrato-convenio")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> contratoConvenio() {

        return new ResponseEntity<>(catalogos.obtenerContratoConvenioInfo(), HttpStatus.OK);
    }

    @GetMapping("/contratos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity< List<ContratoNombreDto>> contrato() {
        return new ResponseEntity<>( catalogos.obtenerContratosInfo(), HttpStatus.OK);
    }
    
    @GetMapping("/contratos/{idProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity< List<ContratoNombreDto>> contratoIdProyecto(@PathVariable Long idProyecto) {
        return new ResponseEntity<>( catalogos.obtenerContratosInfoIdProyecto(idProyecto), HttpStatus.OK);
    }

    @GetMapping("/comite")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> comite() {

        return new ResponseEntity<>(catalogos.obtenetComitesInfo(), HttpStatus.OK);
    }

    @GetMapping("/afectacion")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> afectacion() {

        return new ResponseEntity<>(catalogos.obtenerAfectacionInfo(), HttpStatus.OK);
    }

    @GetMapping("/sesion-clasificacion")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> sesion() {

        return new ResponseEntity<>(catalogos.obtenerSesionInfo(), HttpStatus.OK);
    }

	@GetMapping("/sesion-numero")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> sesionNumero() {

		return new ResponseEntity<>(catalogos.obtenerSesionNumeroInfo(), HttpStatus.OK);
	}

    @GetMapping("/plantilla")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> plantilla() {

        return new ResponseEntity<>(catalogos.obtenerPlantillaInfo(), HttpStatus.OK);
    }

    @GetMapping("/tipo-moneda")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<BaseCatalogoModel>> tipoMoneda() {
        return new ResponseEntity<>(catalogos.obtenerTipoDeMonedaInfo(), HttpStatus.OK);
    }
    
    @GetMapping("/administraciones-generales")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerAdminPatrocinadora() {
		return new ResponseEntity<>(catalogos.obtenerAdmonGenerales(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/administraciones-centrales")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerAdminCentralPatrocinadora() {
		return new ResponseEntity<>(catalogos.obtenerAdmonCentrales(), org.springframework.http.HttpStatus.OK);
	}
	
	
	@GetMapping("/clasificacion-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerClasificacionProyecto() {
		return new ResponseEntity<>(catalogos.obtenerClasificacionProyecto(), org.springframework.http.HttpStatus.OK);
	}
	 
	@GetMapping("/financiamiento")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerFinanciamiento() {
		return new ResponseEntity<>(catalogos.obtenerFinanciamiento(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/estatus-proyecto")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerestatus() {
		return new ResponseEntity<>(catalogos.obtenerEstatus(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/periodos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerPeriodo() {
		return new ResponseEntity<>(catalogos.obtenerPeriodo(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/estatus-inicial")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<BaseCatalogoModel> obtenerEstatusInicial() {
		return new ResponseEntity<>(catalogos.obtenerEstatusInicial(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/tipos-procedimientos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerTipoProcedimiento() {
		return new ResponseEntity<>(catalogos.obtenerTipoProcedimiento(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/alineaciones") 
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerAlineaciones() {
		return new ResponseEntity<>(catalogos.obtenerAlineaciones(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/fases")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerFases() {
		return new ResponseEntity<>(catalogos.obtenerFases(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/respuesta-proveedor")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerInvestigacionMercado() {
		return new ResponseEntity<>(catalogos.obtenerInvestigacionMercado(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/objetivos-alineacion/{idAlineacion}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerOjetivosAlineacion(@PathVariable Integer idAlineacion) {
		return new ResponseEntity<>(catalogos.obtenerOjetivosAlineacion(idAlineacion), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/admon-central-por-general/{idAdmonGeneral}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerAdmonCentralPorGeneral(@PathVariable Integer idAdmonGeneral) {
		return new ResponseEntity<>(catalogos.obtenerAdmonCentralPorGeneral(idAdmonGeneral), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/areas-planeacion")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerAdmistracionPorCentrales() {
		return new ResponseEntity<>(catalogos.obtenerAdmistracionPorCentrales(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/area-responsable/{idAdmoncentral}")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerAdmistracionPorCentral(@PathVariable Integer idAdmoncentral) {
		return new ResponseEntity<>(catalogos.obtenerAdmistracionPorCentral(idAdmoncentral), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/estatus-RCP")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obteneREstatusRCP() {
		return new ResponseEntity<>(catalogos.obteneREstatusRCP(), HttpStatus.OK);
	}
	
	@GetMapping("/estatus-inicial-proceso")
	@PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolUsuarioConsulta() " +
			"|| @seguridad.validarRolLiderProyecto() " +
			"|| @seguridad.validarRolAdministradorContrato() " +
			"|| @seguridad.validarRolParticipantesAdmonDictamen() " +
			"|| @seguridad.validarRolParticipantesAdmonVerificacion() " +
			"|| @seguridad.validarRolVerificadorGeneral() " +
			"|| @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obteneEstatusEnProceso() {
		return new ResponseEntity<>(catalogos.obteneEstatusEnProceso(), HttpStatus.OK);
	}
	
	@GetMapping("/estatus-proyecto-todos")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerTodosEstatus() {
		return new ResponseEntity<>(catalogos.obtenerTodosEstatus(), org.springframework.http.HttpStatus.OK);
	}
	
	@GetMapping("/url-power-bi")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerUrl() {
		return new ResponseEntity<>(catalogos.obtenerUrl(), org.springframework.http.HttpStatus.OK);
	}
	
}
