package com.sisecofi.libreria.comunes.service.security;

import org.springframework.security.core.Authentication;

import com.sisecofi.libreria.comunes.util.RolesConstantes;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Clase general para validar los roles de la sesion
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public abstract class SeguridadService {

	private static final String CADENA = "Validando roles usuario: {} roles requeridos: {} ";
	protected final Session session;

	protected SeguridadService(Session session) {
		super();
		this.session = session;
	}

	public boolean hasPermissions(String rol) {
		Authentication auth = session.retornarNombreUsuario();
		if (auth == null) {
			return false;
		}
		log.info(CADENA, auth.getAuthorities(), rol);
		return auth.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().contains(rol));
	}

	public boolean validarRolAdminSistema() {
		return hasPermissions(RolesConstantes.ROL_ADMINISTRADOR_SISTEMA);
	}

	public boolean validarRolAdminSistemaSecundario() {
		return hasPermissions(RolesConstantes.ROL_ADMINISTRADOR_SISTEMA_SECUNDARIO);
	}

	public boolean validarRolAdminMatrizDocumental() {
		return hasPermissions(RolesConstantes.ROL_ADMINISTRADOR_MATRIZ_DOCUMENTAL);
	}

	public boolean validarRolApoyoAcppi() {
		return hasPermissions(RolesConstantes.ROL_APOYO_ACPPIL);
	}

	public boolean validarRolApoyoAlLiderTecnicoProyeto() {
		return hasPermissions(RolesConstantes.ROL_APOYO_AL_LIDER_DE_PROYECTO);
	}

	public boolean validarRolGestorTitutulos() {
		return hasPermissions(RolesConstantes.ROL_GESTOR_TITULOS_DE_AUTORIZACION);
	}

	public boolean validarRolGestorDocumentalContrato() {
		return hasPermissions(RolesConstantes.ROL_GESTOR_DOCUMENTAL_CONTRATO);
	}

	public boolean validarRolUsuarioConsulta() {
		return hasPermissions(RolesConstantes.ROL_USUARIO_CONSULTA);
	}

	public boolean validarRolLiderProyecto() {
		return hasPermissions(RolesConstantes.ROL_LIDER_DE_PROYECTO);
	}

	public boolean validarRolAdministradorContrato() {
		return hasPermissions(RolesConstantes.ROL_ADMINISTRADOR_DEL_CONTRATO);
	}

	public boolean validarRolParticipantesAdmonEstimaciones() {
		return hasPermissions(RolesConstantes.ROL_PARTICIPANTES_EN_LA_ADMINISTRACION_DE_ESTIMACIONES);
	}

	public boolean validarRolParticipantesAdmonDictamen() {
		return hasPermissions(RolesConstantes.ROL_PARTICIPANTES_EN_LA_ADMINISTRACION_DE_DICTAMEN);
	}

	public boolean validarRolParticipantesAdmonVerificacion() {
		return hasPermissions(RolesConstantes.ROL_PARTICIPANTES_EN_LA_ADMINISTRACION_DE_LA_VERIFICACION);
	}

	public boolean validarRolVerificadorGeneral() {
		return hasPermissions(RolesConstantes.ROL_VERIFICADOR_GENERAL);
	}

	public boolean validarRolVerificadorEspecificoContrato() {
		return hasPermissions(RolesConstantes.ROL_VERIFICADOR_ESPECIFICO_DEL_CONTRATO);
	}

	public boolean validarRolTodosProyectos() {
		return hasPermissions(RolesConstantes.ROL_TODOS_LOS_PROYECTOS);
	}

}
