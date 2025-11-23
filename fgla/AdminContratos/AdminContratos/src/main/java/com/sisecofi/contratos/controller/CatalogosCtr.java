package com.sisecofi.contratos.controller;


import com.sisecofi.contratos.dto.CatalogoProveedorDto;
import com.sisecofi.contratos.dto.FiltroSelect;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.service.ServicioCatalogos;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class CatalogosCtr {
    private final ServicioCatalogos catalogos;
    private final CatalogoMicroservicio catalogoMicroservicio;

    @GetMapping("/administracion-central")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> administracionCentral() {
        return new ResponseEntity<>(catalogos.obtenerAdministracionCentral(), HttpStatus.OK);
    }

    @GetMapping("/estatus-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> estatusContrato() {

        return new ResponseEntity<>(catalogos.obtenerEstatatusContrato(), HttpStatus.OK);
    }

    @GetMapping("/proovedor")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<CatalogoProveedorDto>> proovedor() {

        return new ResponseEntity<>(catalogos.obtenerProovedor(), HttpStatus.OK);
    }

    @GetMapping("/tipo-consumo")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> tipoConsumo() {

        return new ResponseEntity<>(catalogos.obtenerTipoConsumo(), HttpStatus.OK);
    }

    @GetMapping("/prueba-tipo-consumo")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<BaseCatalogoModel> pruebaTipoBYid() {
        return new ResponseEntity<>(catalogoMicroservicio.obtenerInformacionCatalogoId(CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(),1), HttpStatus.OK);
    }


    @GetMapping("/tipo-unidad")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> tipoUnidad() {
        return new ResponseEntity<>(catalogos.obtenerTipoUnidad(), HttpStatus.OK);
    }

    @GetMapping("/tipo-moneda")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> tipoMoneda() {
        return new ResponseEntity<>(catalogos.obtenerTipoMoneda(), HttpStatus.OK);
    }

    @GetMapping("/porcentaje-ieps")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> obtenerPorcentajeIeps() {
        return new ResponseEntity<>(catalogos.obtenerIeps(), HttpStatus.OK);
    }

    @GetMapping("/porcentaje-iva")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> porcentajeIva() {
        return new ResponseEntity<>(catalogos.obtenerIva(), HttpStatus.OK);
    }

    @GetMapping("/tipo-procedimiento")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> tipoProcedimiento() {
        return new ResponseEntity<>(catalogos.obtenetTipoProcedimiento(), HttpStatus.OK);
    }

    @GetMapping("/dominio-tecnologico")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> dominioTecnologico() {
        return new ResponseEntity<>(catalogos.obtenerDominioTecnologico(), HttpStatus.OK);
    }

    @GetMapping("/fondeo-contrato")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> fondeoContrato() {
        return new ResponseEntity<>(catalogos.obtenerFondeContrato(), HttpStatus.OK);
    }

    @GetMapping("/usuarios")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return new ResponseEntity<>(catalogos.obtenerUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/administracion-general")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> administracionGeneral() {

        return new ResponseEntity<>(catalogos.obtenerAdministracionGeneral(), HttpStatus.OK);
    }

    @GetMapping("/responsabilidad")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> obtenerResponsabilidad() {

        return new ResponseEntity<>(catalogos.obtenerResponsabilidad(), HttpStatus.OK);
    }
    
    @GetMapping("/cat-periodicidad")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
	public ResponseEntity<List<BaseCatalogoModel>> obtenerPeriodicidad() {
		return new ResponseEntity<>(catalogos.obtenerPeriodicidad(), HttpStatus.OK);
	}

    @GetMapping("/convenio-colaboracion")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<BaseCatalogoModel>> obtenerConvenioColaboracion() {
        return new ResponseEntity<>(catalogos.obtenerConvenioColaboracion(), HttpStatus.OK);
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
    
    
    @PostMapping("/administradores-filtro")
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
	public ResponseEntity<List<UsuarioInfoDto>> obtenerAdministradoresCentrales(@RequestBody FiltroSelect filtro) {
		return new ResponseEntity<>(catalogos.obtenerAdministradoresCentrales(filtro), org.springframework.http.HttpStatus.OK);
	}
    
    
    @GetMapping("/usuarios-organigrama")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato() || @seguridad.validarRolTodosProyectos()")
    public ResponseEntity<List<UsuarioInfoDto>> obtenerTodosLosUsuarios() {
        return new ResponseEntity<>(catalogos.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }
    
}
