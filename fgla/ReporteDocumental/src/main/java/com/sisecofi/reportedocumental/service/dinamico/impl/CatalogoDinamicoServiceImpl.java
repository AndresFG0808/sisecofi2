package com.sisecofi.reportedocumental.service.dinamico.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProveedorRazonSocialDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.TituloServicioPreveedorDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.usuario.RolModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.RolesConstantes;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.reportedocumental.dto.dinamico.Ids;
import com.sisecofi.reportedocumental.microservicio.CatalogoMicroservicio;
import com.sisecofi.reportedocumental.microservicio.impl.ContratoRepository;
import com.sisecofi.reportedocumental.repository.controldoc.UsuarioProyectoRepository;
import com.sisecofi.reportedocumental.repository.dinamico.ProveedorRepository;
import com.sisecofi.reportedocumental.repository.dinamico.ProyectoRepository;
import com.sisecofi.reportedocumental.repository.dinamico.TituloServicioProveedorRepository;
import com.sisecofi.reportedocumental.service.dinamico.CatalogoDinamicoService;
import com.sisecofi.reportedocumental.util.Constantes;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.ReporteException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogoDinamicoServiceImpl implements CatalogoDinamicoService {

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ProyectoRepository proyectoRepository;
	private final ContratoRepository contratoRepository;
	private final ProveedorRepository proveedorRepository;
	private final TituloServicioProveedorRepository tituloServicioProveedorRepository;
	private final UsuarioProyectoRepository usuarioProyectoRepository;
	private final Session session;

	@Override
	public List<BaseCatalogoModel> faseProyecto() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
		}
	}

	@Override
	public List<BaseCatalogoModel> estatusProyecto() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
		}
	}

	@Override
	public List<BaseCatalogoModel> estatusContrato() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
		}
	}

	@Override
	public List<BaseCatalogoModel> dominioTecnologico() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.DOMINIOS.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
		}
	}

	@Override
	public List<ProyectoNombreDto> buscarProyecto(Ids<Integer> estatusProyecto) {
		Optional<Usuario> op = session.retornarUsuario();
		if (op.isPresent()) {
			RolModel admin = new RolModel();
			admin.setNombre(RolesConstantes.ROL_ADMINISTRADOR_SISTEMA);
			RolModel subadmin = new RolModel();
			subadmin.setNombre(RolesConstantes.ROL_ADMINISTRADOR_SISTEMA_SECUNDARIO);
			if (op.get().getRolModels().contains(admin) || op.get().getRolModels().contains(subadmin)) {
				log.info("Ids a buscar: {}", estatusProyecto.getId());
				return proyectoRepository.findByIdEstatusProyecto(estatusProyecto.getId());
			}
			List<ProyectoDto> pr = usuarioProyectoRepository.buscarProyectosUsuario(op.get().getIdUser(),
					estatusProyecto.getId());
			log.info("Ids a buscar2: {}", pr.stream().map(ProyectoDto::getIdProyecto).toList());
			return proyectoRepository
					.findByIdProyecto(pr.stream().map(ProyectoDto::getIdProyecto).toList());
		}
		return Collections.emptyList();
	}

	@Override
	public List<ContratoNombreDto> buscarContrato(Ids<Integer> estatusContrato) {
		return contratoRepository.findByIdEstatusContrato(estatusContrato.getId());
	}

	@Override
	public List<ProveedorRazonSocialDto> razonSocial() {
		return proveedorRepository.findByEstatus(true);
	}

	@Override
	public List<TituloServicioPreveedorDto> buscarTituloServicio(Ids<Long> idProveedor) {
		return tituloServicioProveedorRepository.findByIdProveedor(idProveedor.getId());
	}

	@Override
	public List<BaseCatalogoModel> convenioColoboracion() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.CONVENIO_COLABORACION.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
		}
	}

}
