package com.sisecofi.proyectos.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.proyectos.dto.ProyectoDtoLigero;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoMetaDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.microservicio.cierre.DictamenRestMicroservicio;
import com.sisecofi.proyectos.model.ProyectoProveedorModel;
import com.sisecofi.proyectos.model.cierre.CierreModel;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.repository.AsociacionRepository;
import com.sisecofi.proyectos.repository.ComiteRepository;
import com.sisecofi.proyectos.repository.ContratoRepository;
import com.sisecofi.proyectos.repository.ProyectoProveedorRepository;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.repository.UsuarioProyectoRepository;
import com.sisecofi.proyectos.repository.adminplantrabajo.PlanTrabajoRepository;
import com.sisecofi.proyectos.repository.cierre.CierreRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.consumer.ProyectoMap;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ProyectoSpecification;
import com.sisecofi.proyectos.util.consumer.OpcionesEstatus;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServicioProyectoImpl implements ServicioProyecto {

	private final PistaService pistaService;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ProyectoRepository proyectoRepository;
	private final Session session;
	private final ContratoRepository contratoRepository;
	private final UsuarioProyectoRepository usuarioProyectoRepository;
	private final SeguridadService seguridadService;
	private final ComiteRepository comiteRepository;
	private final ProyectoProveedorRepository proyectoProveedorRepository;
	private final AsociacionRepository asociacionRepository;
	private final CierreRepository cierreRepository;
	private final PlanTrabajoRepository planTrabajoRepository;
	private final DictamenRestMicroservicio dictamenRestMicroservicio;
	private static final String CERRADO = "Cerrado";
	private static final String CANCELADO = "Cancelado";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String obtnerUltimoId() {
		try {
			Long ultimoId = proyectoRepository.findTopByOrderByIdProyectoDesc().getIdProyecto() + 1;
			String formato = "%05d";
			log.info("Ultimo id obtenido: {}", ultimoId);
			return String.format(formato, ultimoId);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_OBTENER_ID);
		}
	}

	@Override
	@Transactional
	public ProyectoResponse crearProyecto(ProyectoModel proyecto) {
		if (proyecto.getIdAgp() != null) {
			Optional<ProyectoModel> existingAgp = proyectoRepository.findByIdAgp(proyecto.getIdAgp());
			if (existingAgp.isPresent() && proyecto.getIdAgp() != null) {
				throw new ProyectoException(ErroresEnum.ERROR_IDAGP_OCUPADO);
			}
		}
		proyecto.setFechaCreacion(horaActual());
		proyecto.setEstatus(true);
		actualizarDatosUsuario(proyecto);
		proyectoRepository.save(proyecto);

		ProyectoResponse response = agruparRespuesta(proyecto, null);
		pistaService
				.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
						TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),
						Constantes.getAtributosProyecto()[0] + proyecto.getIdProyecto() + "|"
								+ Constantes.getAtributosProyecto()[1] + proyecto.getNombreCorto(),
						Optional.of(proyecto));
		return response;
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	private Usuario obtenerUsuario() {
		Optional<Usuario> usuario = session.retornarUsuario();
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			throw new ProyectoException(ErroresEnum.ERROR_USUARIO_NO_ENCONTRADO);
		}
	}

	@Override
	@Transactional
	public ProyectoResponse actualizarProyecto(ProyectoModel proyecto, Long idProyecto) {
		ProyectoModel proyectoExistente = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));

		if (!proyectoExistente.getIdAgp().equals(proyecto.getIdAgp())) {
			Optional<ProyectoModel> existingAgp = proyectoRepository.findByIdAgp(proyecto.getIdAgp());
			if (existingAgp.isPresent() && !existingAgp.get().getIdAgp().equals(proyectoExistente.getIdAgp())) {
				throw new ProyectoException(ErroresEnum.ERROR_IDAGP_OCUPADO);
			}
		}
		if (!proyectoExistente.getNombreCorto().equals(proyecto.getNombreCorto())) {
			throw new ProyectoException(ErroresEnum.NOMBRE_CORTO_NO_EDITABLE);
		}
		if (!proyectoExistente.getNombreProyecto().equals(proyecto.getNombreProyecto())) {
			CatEstatusProyecto estatusActual = proyectoExistente.getCatEstatusProyecto();
			if (!estatusActual.getNombre().equals("Ejecución")) {
				proyectoExistente.setNombreProyecto(proyecto.getNombreProyecto());
			} else {
				throw new ProyectoException(ErroresEnum.NOMBRE_NO_EDITABLE);
			}
		}

		actualizarDatosProyecto(proyectoExistente, proyecto);
		proyectoRepository.save(proyectoExistente);

		ProyectoResponse response = agruparRespuesta(proyectoExistente, null);

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),

		// Constantes.getAtributosProyecto()[0] + proyectoExistente.getIdProyecto() + "|"

		// + Constantes.getAtributosProyecto()[1] + proyectoExistente.getNombreCorto(),

		// Optional.of(proyectoExistente));
		return response;
	}

	private void actualizarDatosProyecto(ProyectoModel proyectoExistente, ProyectoModel proyecto) {
		proyectoExistente.setNombreCorto(proyecto.getNombreCorto());
		proyectoExistente.setIdAgp(proyecto.getIdAgp());
		proyectoExistente.setFechaModificacion(horaActual());
		actualizarDatosUsuario(proyectoExistente);
	}

	private void actualizarDatosUsuario(ProyectoModel proyecto) {
		Usuario usuario = obtenerUsuario();
		if (usuario != null) {
			proyecto.setNombreEmpleado(usuario.getNombre());
		}
	}

	@Override
	public ProyectoResponse obtenerProyecto(Long idProyecto) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));
		ProyectoResponse response = agruparRespuesta(proyecto, null);
		pistaService.guardarPistaSimple(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROYECTOS_REGISTRADOS.getIdSeccionPista(),
				Constantes.getAtributosProyecto()[0] + proyecto.getIdProyecto() + "|"
						+ Constantes.getAtributosProyecto()[1] + proyecto.getNombreCorto(),
				Optional.empty());
		return response;
	}

	@Override
	@Transactional
	public ProyectoResponse actualizarEstatus(CatEstatusProyecto estatus, Long idProyecto) {
		if (!estatus.getNombre().equals("Proceso de cierre") && seguridadService.validarRolApoyoAcppi()) {
			throw new ProyectoException(ErroresEnum.ACCESO_DENEGADO);
		}

		ProyectoModel proyectoExistente = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));
		CatEstatusProyecto actual = proyectoExistente.getCatEstatusProyecto();
		validarTransicionEstatus(estatus, actual, proyectoExistente);
		proyectoExistente.setIdEstatusProyecto(estatus.getIdEstatusProyecto());

		ultimaMod(proyectoExistente);
		proyectoRepository.save(proyectoExistente);

		// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.PROYECTO_DATOS_GENERALES.getIdSeccionPista(),

		// Constantes.getAtributosProyecto()[2] + actual.getNombre() + "|" + Constantes.getAtributosProyecto()[3]

		// + estatus.getNombre() + "|" + Constantes.getAtributosProyecto()[1]

		// + proyectoExistente.getNombreCorto(),

		// Optional.of(proyectoExistente));
		return agruparRespuesta(proyectoExistente, estatus);
	}

	private void ultimaMod(ProyectoModel proyecto) {
		if (obtenerUsuario() != null) {
			proyecto.setNombreEmpleado(obtenerUsuario().getNombre());
		}
		proyecto.setFechaModificacion(horaActual());
	}

	private ProyectoResponse agruparRespuesta(ProyectoModel proyecto, CatEstatusProyecto estatusNuev) {
		ProyectoResponse response = new ProyectoResponse();
		if (proyecto.getIdEstatusProyecto() == null) {
			throw new ProyectoException(ErroresEnum.ERROR_DATOS_OBLIGATORIOS);
		}
		CatEstatusProyecto estatus;
		if (estatusNuev != null) {
			estatus = estatusNuev;
		} else {
			estatus = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), proyecto.getIdEstatusProyecto(),
					CatEstatusProyecto.class);
		}
		Set<BaseCatalogoModel> opciones = new OpcionesEstatus(catalogoMicroservicio, seguridadService).apply(estatus);
		response.setOpcionesEstatus(opciones);
		response.setProyecto(proyecto);
		response.setNombreUsuario(proyecto.getNombreEmpleado());
		if (proyecto.getFechaModificacion() == null) {
			response.setUltimaFechaModificacion(proyecto.getFechaCreacion());
		} else {
			response.setUltimaFechaModificacion(proyecto.getFechaModificacion());
		}
		boolean acceso = seguridadService.validarRolAdminSistema()
				|| seguridadService.validarRolAdminSistemaSecundario();

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), Constantes.ESTATUS_CANCELAR);
		if (!lista.isEmpty() && acceso) {
			response.setEstatusCancelado(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), lista.get(0).getPrimaryKey(),
					CatEstatusProyecto.class));
		}
		response.setEstatus(estatus);
		return response;
	}

	@Override
	public Page<ProyectoMetaDto> buscarProyectos(EstructuraProyectoMetaDto proyecto) {
		if (proyecto.getIdEstatusProyecto() == null && proyecto.getIdProyecto() == null) {
			throw new ProyectoException(ErroresEnum.ERROR_DATOS_OBLIGATORIOS);
		}
		boolean acceso = validarAccesos();
		// limpiando la estructura de peticion si se busca por id
		limpiarCriterios(proyecto);

		int size = 15;
		if (proyecto.getSize() > 0) {
			size = proyecto.getSize();
		}

		Pageable paginacion = PageRequest.of(proyecto.getPage(), size, Sort.by("nombreCorto").ascending());

		Page<ProyectoModel> lista = proyectoRepository
				.findAll(ProyectoSpecification.byCriteria(proyecto, obtenerUsuario(), acceso), paginacion);

		pistaService.guardarPistaSimple(ModuloPista.PROYECTOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROYECTO_BUSQUEDA.getIdSeccionPista(), proyecto.criterios(), Optional.empty());
		return lista.map(proyectoModel -> new ProyectoMap(catalogoMicroservicio).apply(proyectoModel,
				proyecto.getAreaSolicitante()));
	}

	@Override
	public Page<ProyectoDtoLigero> buscarProyectosModel(EstructuraProyectoMetaDto proyecto) {
		if (proyecto.getIdEstatusProyecto() == null && proyecto.getIdProyecto() == null) {
			throw new ProyectoException(ErroresEnum.ERROR_DATOS_OBLIGATORIOS);
		}
		boolean acceso = validarAccesos();

		// limpiando la estructura de peticion si se busca por id
		boolean idP = limpiarCriterios(proyecto);
		int size = 15;
		if (proyecto.getSize() > 0) {
			size = proyecto.getSize();
		}
		Pageable paginacion = PageRequest.of(proyecto.getPage(), size);
		
		Page<Object[]> resultados= obtenerElementosPaginados(idP,proyecto,acceso,paginacion);

		List<ProyectoDtoLigero> listaConvertida = resultados.stream().map(ProyectoDtoLigero::new).toList();

		return new PageImpl<>(listaConvertida, paginacion, resultados.getTotalElements());
	}
	
	private Page<Object[]> obtenerElementosPaginados(boolean idP, EstructuraProyectoMetaDto proyecto, boolean acceso, Pageable paginacion){
		if (idP) {
			return proyectoRepository.findAllOptimizedRaw(
					proyecto.getIdEstatusProyecto(),
					proyecto.getNombreCorto() != null && !proyecto.getNombreCorto().isEmpty()
							? proyecto.getNombreCorto()
							: null,
					proyecto.getAreaResponsable(),
					proyecto.getLiderProyecto() != null && !proyecto.getLiderProyecto().isEmpty()
							? proyecto.getLiderProyecto()
							: null,
					proyecto.getAreaSolicitante() != null && proyecto.getAreaSolicitante() != 0
							? proyecto.getAreaSolicitante()
							: null,
					(obtenerUsuario() != null && acceso) ? obtenerUsuario().getIdUser() : null, paginacion);
		}else {
			return proyectoRepository.findByIdProyectoOp(proyecto.getIdProyecto(), paginacion);
		}
	
	}

	private boolean limpiarCriterios(EstructuraProyectoMetaDto proyecto) {
		if (proyecto.getIdProyecto() != null) {
			proyecto.setIdEstatusProyecto(null);
			proyecto.setNombreCorto(null);
			proyecto.setAreaResponsable(null);
			proyecto.setAreaSolicitante(null);
			proyecto.setLiderProyecto(null);
			return false;
		}
		return true;
	}

	@Override
	public String obtenerCriterios(EstructuraProyectoMetaDto proyecto) {
		if (proyecto == null) {
			return "";
		}

		if (proyecto.getIdProyecto() != null) {
			return "Id proyecto: " + proyecto.getIdProyecto();
		}

		StringBuilder criterios = new StringBuilder();

		agregarCriterioEstatus(proyecto, criterios);
		agregarCriterioNombreCorto(proyecto, criterios);
		agregarCriterioAreaSolicitante(proyecto, criterios);
		agregarCriterioAreaResponsable(proyecto, criterios);
		agregarCriterioLiderProyecto(proyecto, criterios);

		eliminarSeparadorFinal(criterios);

		return criterios.toString();
	}

	private void agregarCriterioEstatus(EstructuraProyectoMetaDto proyecto, StringBuilder criterios) {
		if (proyecto.getIdEstatusProyecto() != null) {
			String estatus = Constantes.getEstatusProyecto()[proyecto.getIdEstatusProyecto() - 1];
			criterios.append("Estatus: ").append(estatus).append("|");
		}
	}

	private void agregarCriterioNombreCorto(EstructuraProyectoMetaDto proyecto, StringBuilder criterios) {
		if (proyecto.getNombreCorto() != null && !proyecto.getNombreCorto().isEmpty()) {
			criterios.append("Nombre corto del proyecto: ").append(proyecto.getNombreCorto()).append("|");
		}
	}

	private void agregarCriterioAreaSolicitante(EstructuraProyectoMetaDto proyecto, StringBuilder criterios) {
		if (proyecto.getAreaSolicitante() != null) {
			CatAdmonCentral admonC = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), proyecto.getAreaSolicitante(),
					CatAdmonCentral.class);
			if (admonC != null) {
				criterios.append("Área solicitante: ").append(admonC.getAdministracion()).append("|");
			}
		}
	}

	private void agregarCriterioAreaResponsable(EstructuraProyectoMetaDto proyecto, StringBuilder criterios) {
		if (proyecto.getAreaResponsable() != null) {
			CatAdministracion admonC = catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.ADMINISTRACIONES.getIdCatalogo(), proyecto.getAreaResponsable(),
					CatAdministracion.class);
			if (admonC != null) {
				criterios.append("Área responsable: ").append(admonC.getAdministracion()).append("|");
			}
		}
	}

	private void agregarCriterioLiderProyecto(EstructuraProyectoMetaDto proyecto, StringBuilder criterios) {
		if (proyecto.getLiderProyecto() != null && !proyecto.getLiderProyecto().isEmpty()) {
			criterios.append("Líder de proyecto: ").append(proyecto.getLiderProyecto());
		}
	}

	private void eliminarSeparadorFinal(StringBuilder criterios) {
		if (criterios.length() > 0 && criterios.charAt(criterios.length() - 1) == '|') {
			criterios.deleteCharAt(criterios.length() - 1);
		}
	}

	@Override
	public List<ProyectoDtoLigero> buscarProyectosLista(EstructuraProyectoMetaDto proyecto) {
		if (proyecto.getIdEstatusProyecto() == null) {
			log.info("No se detecto el campo idEstatusPoryecto: {}", proyecto);
			throw new ProyectoException(ErroresEnum.ERROR_DATOS_OBLIGATORIOS);
		}
		limpiarCriterios(proyecto);

		boolean acceso = validarAccesos();

		List<Object[]> resultados = proyectoRepository.findAllOptimizedRaw(
				proyecto.getIdEstatusProyecto() != null ? proyecto.getIdEstatusProyecto() : null,
				proyecto.getNombreCorto() != null && !proyecto.getNombreCorto().isEmpty() ? proyecto.getNombreCorto()
						: null,
				proyecto.getIdProyecto() != null ? proyecto.getIdProyecto() : null,
				proyecto.getAreaResponsable() != null && proyecto.getAreaResponsable() != 0
						? proyecto.getAreaResponsable()
						: null,
				proyecto.getLiderProyecto() != null && !proyecto.getLiderProyecto().isEmpty()
						? proyecto.getLiderProyecto()
						: null,
				proyecto.getAreaSolicitante() != null && proyecto.getAreaSolicitante() != 0
						? proyecto.getAreaSolicitante()
						: null,
				(obtenerUsuario() != null && acceso) ? obtenerUsuario().getIdUser() : null);

		return resultados.stream().map(ProyectoDtoLigero::new).toList();
	}

	private boolean validarAccesos() {
		return !(seguridadService.validarRolVerificadorGeneral() || seguridadService.validarRolAdminSistema()
				|| seguridadService.validarRolAdminSistemaSecundario() || seguridadService.validarRolTodosProyectos()
				|| seguridadService.validarRolAdminMatrizDocumental());
	}

	private void validarTransicionEstatus(CatEstatusProyecto estatus, CatEstatusProyecto estatusActual,
			ProyectoModel proyectoExistente) {
		String estatusNombre = estatus.getNombre().trim();
		String estatusActualNombre = estatusActual.getNombre().trim();
		if (!estatus.getNombre().equals(estatusActual.getNombre())) {

			Map<String, String[]> transicionesValidas = Map.of(Constantes.getEstatus()[0],
					new String[] { Constantes.getEstatus()[1], Constantes.getEstatus()[2] }, Constantes.getEstatus()[2],
					new String[] { Constantes.getEstatus()[0], Constantes.getEstatus()[3] }, Constantes.getEstatus()[3],
					new String[] { Constantes.getEstatus()[2], Constantes.getEstatus()[4] }, Constantes.getEstatus()[4],
					new String[] { Constantes.getEstatus()[1], Constantes.getEstatus()[2], Constantes.getEstatus()[3],
							Constantes.getEstatus()[4], Constantes.getEstatus()[0] },
					Constantes.getEstatus()[5], new String[] { Constantes.getEstatus()[3] });
			if (transicionesValidas.containsKey(estatusNombre) || estatusNombre.equals(Constantes.getEstatus()[5])) {
				if (Arrays.asList(transicionesValidas.get(estatusNombre)).contains(estatusActualNombre)
						|| estatusNombre.equals(Constantes.getEstatus()[5])) {
					validarCondicionesEspeciales(estatusNombre, estatusActualNombre, proyectoExistente);
				} else {
					throw new ProyectoException(ErroresEnum.AVANCE_NO_PERMITIDO);
				}
			} else if (!(estatusNombre.equals(Constantes.getEstatus()[1])
					|| estatusNombre.equals(Constantes.getEstatus()[5]))) {
				throw new ProyectoException(ErroresEnum.AVANCE_NO_PERMITIDO);
			}
		}
	}

	private void validarCondicionesEspeciales(String estatusNombre, String estatusActualNombre,
			ProyectoModel proyectoExistente) {

		boolean plan = planTrabajoRepository.existsByProyectoModelIdProyecto(proyectoExistente.getIdProyecto());

		String[] estatus = Constantes.getEstatus();

		// Condición: Avanzar a Planeación
		if (estatusNombre.equals(estatus[0]) && estatusActualNombre.equals(estatus[1])) {
			validarAvancePlaneacion(proyectoExistente);
		}
		// Condición: Avanzar a Ejecución
		else if (estatusNombre.equals(estatus[2]) && estatusActualNombre.equals(estatus[0]) && !plan) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_AVAZAR_A_EJECUCUCION);
		}
		// Condición: Avanzar a Proceso de Cierre
		else if (estatusNombre.equals(estatus[3]) && estatusActualNombre.equals(estatus[2])) {
			validarAvanceProcesoCierre(proyectoExistente);
		}
		// Condición: Avanzar a Cierre
		else if (estatusNombre.equals(estatus[4]) && estatusActualNombre.equals(estatus[3])) {
			validarSecciones(proyectoExistente.getIdProyecto());
		}
		// Condición: Cancelar
		else if (estatusNombre.equals(estatus[5])) {
			validarCancelado(proyectoExistente);
		}

	}

	private void validarAvancePlaneacion(ProyectoModel proyectoExistente) {
		FichaTecnicaModel fichaTecnica = proyectoExistente.getFichaTecnicaModel();
		if (fichaTecnica == null) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_AVAZAR_A_PLANEACION);
		}
	}

	private void validarAvanceProcesoCierre(ProyectoModel proyectoExistente) {
		List<String> estatusExcluidos = Arrays.asList(CERRADO, CANCELADO);
		Long contratos = contratoRepository.countByEstatusTrueAndCatEstatusContratoNombreNotInAndProyectoIdProyecto(
				estatusExcluidos, proyectoExistente.getIdProyecto());
		if (contratos > 0) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_AVAZAR_A_PROCESO_DE_CIERRE);
		}
	}

	private void validarCancelado(ProyectoModel proyectoExistente) {
		List<String> estatusExcluidos = Arrays.asList(CERRADO, CANCELADO);
		Long contratos = contratoRepository.countByEstatusTrueAndCatEstatusContratoNombreNotInAndProyectoIdProyecto(
				estatusExcluidos, proyectoExistente.getIdProyecto());
		Boolean resultado = dictamenRestMicroservicio.validarCancelacionProyecto(proyectoExistente.getIdProyecto());
		if (contratos > 0 || Boolean.FALSE.equals(resultado)) {
			throw new ProyectoException(ErroresEnum.ERROR_CANCELAR_PROYECTO);
		}
	}

	private void validarSecciones(Long idProyecto) {

		List<ComiteProyectoModel> lista1 = comiteRepository.findByIdProyectoAndEstatusTrue(idProyecto);
		List<ProyectoProveedorModel> lista2 = proyectoProveedorRepository
				.findByProyectoModelIdProyectoAndEstatusTrue(idProyecto);
		List<AsociacionesModel> lista3 = asociacionRepository.findByIdProyectoAndEstatusAsociacionTrue(idProyecto);

		if (lista1.isEmpty() || lista2.isEmpty() || lista3.isEmpty()) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_AVAZAR_A_CERRADO);
		}

		Optional<CierreModel> cierre = cierreRepository.findByIdProyectoAndEstatusTrue(idProyecto);

		if (cierre.isPresent()) {
			CierreModel cr = cierre.get();
			if (cr.getPorcentajeReal() < 100 || cr.getCatEstatusRcp().getNombre().equals("RCP entregado")) {
				throw new ProyectoException(ErroresEnum.ERROR_AL_AVAZAR_A_CERRADO);
			}
		} else {
			throw new ProyectoException(ErroresEnum.ERROR_AL_AVAZAR_A_CERRADO);
		}

	}

	@Override
	public List<ProyectoNombreDto> obtenerNombresCortos(Integer idEstatus) {
		if (idEstatus == 0) {
			return proyectoRepository.findAllProyectosActivosDto();
		} else if (!validarAccesos()) {
			return proyectoRepository.findProyectosByEstatusDto(idEstatus);
		}
		return usuarioProyectoRepository.findProyectosDtoByUsuarioAndEstatusTrueAndIdEstatusProyecto(obtenerUsuario(),
				idEstatus);
	}

	@Override
	public List<ProyectoModel> obtenerProyectosEstatus(Integer idEstatus) {
		Usuario usuarioLogueado = obtenerUsuario();
		Long userIdLogueado = (usuarioLogueado != null) ? usuarioLogueado.getIdUser() : null;

		if (!validarAccesos()) {
			if (idEstatus == 0) {
				return proyectoRepository.findAll();
			}
			return proyectoRepository.findAllByIdEstatusProyectoAndEstatusTrue(idEstatus);
		}

		if (idEstatus == 0) {
			return proyectoRepository.findByUserAndEstatusTrue(userIdLogueado);
		}

		return proyectoRepository.findByIdEstatusProyectoAndUserAndEstatusTrue(idEstatus, userIdLogueado);

	}

	@Override
	public boolean eliminarProyecto(Long idProyecto) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));
		proyecto.setEstatus(false);
		proyectoRepository.save(proyecto);
		return proyecto.isEstatus();
	}

	@Override
	public List<ProyectoSimpleDto> obtenerProyectosLista() {
		List<BaseCatalogoModel> estatusProyectoCat = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), Constantes.VALIDACION_PROYECTO_EJECUCION);

		BaseCatalogoModel estatus = estatusProyectoCat.get(0);
		Integer idEstatusProyecto = estatus.getPrimaryKey();

		List<ProyectoSimpleDto> lista = proyectoRepository.findProyectosByIdEstatusProyecto(idEstatusProyecto);
		lista.sort(Comparator.comparing(
				proyectoL -> proyectoL.getNombreCorto() != null ? proyectoL.getNombreCorto().toLowerCase() : "",
				Comparator.nullsLast(Comparator.naturalOrder()) // Manejo de valores nulos correctamente
		));
		return lista;
	}

	@Override
	public List<ProyectoSimpleDto> obtenerProyectosListaCompleto() {

		List<ProyectoSimpleDto> lista = proyectoRepository.findProyectosConEstatusTrue();
		lista.sort(Comparator.comparing(
				proyectoL -> proyectoL.getNombreCorto() != null ? proyectoL.getNombreCorto().toLowerCase() : ""));
		return lista;
	}

	@Override
	public ProyectoModel obtenerProyectoModel(Long idProyecto) {
		return proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));
	}

	@Override
	public Integer obtenerIdPorNombreCorto(String nombreCorto) {
		ProyectoModel proyecto = proyectoRepository.findByNombreCorto(nombreCorto)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));

		return proyecto.getIdProyecto() != null ? proyecto.getIdProyecto().intValue() : null;
	}

	@Override
	public void actualizarUltimaModificacion(Long idProyecto) {
		ProyectoModel proyecto = obtenerProyectoModel(idProyecto);
		ultimaMod(proyecto);
		proyectoRepository.save(proyecto);
	}

	@Override
	public String obtenerUltimaMod(Long idProyecto) {
		ProyectoModel proyecto = proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
				.orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));

		LocalDateTime ultimaMod = proyecto.getFechaModificacion();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		String formattedDateTime = ultimaMod.format(formatter);

		String result = "";

		result += proyecto.getNombreEmpleado() + " " + formattedDateTime;

		return result;

	}

	@Override
	public Boolean verificarCancelado(Long idProyecto) {
		List<String> estatusExcluidos = Arrays.asList(CERRADO, CANCELADO);
		Long contratos = contratoRepository
				.countByEstatusTrueAndCatEstatusContratoNombreNotInAndProyectoIdProyecto(estatusExcluidos, idProyecto);
		Boolean resultado = dictamenRestMicroservicio.validarCancelacionProyecto(idProyecto);
		return !(contratos > 0 || Boolean.FALSE.equals(resultado));
	}
}
