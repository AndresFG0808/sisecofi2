package com.sisecofi.catalogos.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.catalogos.dto.AdminGeneric;
import com.sisecofi.catalogos.repository.AdministradorAdministracionRepository;
import com.sisecofi.catalogos.repository.AdministradorCentralRepository;
import com.sisecofi.catalogos.repository.AdministradorGeneralRepository;
import com.sisecofi.catalogos.repository.CatAdministracionRepository;
import com.sisecofi.catalogos.repository.CatAdmonCentralRepository;
import com.sisecofi.catalogos.repository.CatAdmonGeneralRepository;
import com.sisecofi.catalogos.repository.FichaAdmonCentralRepository;
import com.sisecofi.catalogos.service.PistaService;
import com.sisecofi.catalogos.service.ServicioCatalogo;
import com.sisecofi.catalogos.service.ServicioCatalogoComp;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.catalogos.util.enums.ErroresEnum;
import com.sisecofi.catalogos.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.interfaces.AdministradorBase;

import jakarta.transaction.Transactional;
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
public class ServicioCatalogoCompImpl implements ServicioCatalogoComp {

	private final AdministradorGeneralRepository administradorGeneralRepository;
	private final AdministradorCentralRepository administradorCentralRepository;
	private final AdministradorAdministracionRepository administradorAdministracionRepository;
	private final ServicioCatalogo servicioCatalogo;
	private final PistaService pistaService;
	private final CatAdministracionRepository catAdministracionRepository;
	private final CatAdmonCentralRepository catAdmonCentralRepository;
	private final CatAdmonGeneralRepository admonGeneralRepository;
	private final FichaAdmonCentralRepository fichaAdmonCentralRepository;
	private static final String FECHA_CREACION = "fechaCreacion: ";
	private static final String FECHA_MODIFI = "fechaModificacion: ";
	private static final String ESTATUS = "estatus: ";
	private static final String ADMINISTRACION = "administracion: ";
	private static final String ACRONIMO = "acronimo: ";
	private static final String PUESTO = "puesto: ";
	private static final String NOMBRE = "Nombre completo: ";
	private static final String FECHA_INICIO = "Fecha inicio de vigencia: ";
	private static final String FECHA_FIN = "Fecha fin de vigencia: ";
	private static final String FECHA_ULTIMA = "Última modificación: ";
	private static final String ESTADO = "Estatus: ";

	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerAdministracion(int id) {
		String peticion = "{\"catAdmonCentral\":{\"idAdmonCentral\":%d}}";
		String json = String.format(peticion, id);
		return servicioCatalogo.obtenerInformacionCampoCompleta(CatalogosComunes.ADMINISTRACIONES.getIdCatalogo(),
				json);
	}

	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerCentral(int id) {
		String peticion = "{\"catAdmonGeneral\":{\"idAdmonGeneral\":%d}}";
		String json = String.format(peticion, id);
		return servicioCatalogo.obtenerInformacionCampoCompleta(CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(),
				json);
	}

	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerMapas(int id) {
		String peticion = "{\"catAliniacion\":{\"idAliniacion\":%d}}";
		String json = String.format(peticion, id);
		return servicioCatalogo.obtenerInformacionCampoCompleta(CatalogosComunes.MAPA_OBJETIVO.getIdCatalogo(), json);
	}

	@Override
	@Transactional
	public AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> guardarAdministracionesGenerales(
			AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto) {

		validarAdministradoresActivos(adminGralDto);

		if (!adminGralDto.getAdmon().isEstatus()) {
			adminGralDto.getAdministradores().forEach(d -> d.setEstatus(false));
		}

		CatAdmonGeneral tmp = guardarOActualizarAdmonGeneral(adminGralDto);
		procesarAdministradores(adminGralDto.getAdministradores(), tmp);

		adminGralDto.setAdmon(tmp);
		return adminGralDto;
	}

	private void validarAdministradoresActivos(AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto) {
		long contador = adminGralDto.getAdministradores().stream().filter(BaseCatalogoModel::isEstatus).count();
		log.info("Administradores activos CatAdmonGeneral: {}", contador);
		if (contador > 1) {
			throw new CatalogoException(ErroresEnum.ERROR_MSJ_ADMINISTRADORES);
		}
	}

	private CatAdmonGeneral guardarOActualizarAdmonGeneral(
			AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto) {
		Optional<CatAdmonGeneral> cat = Optional.ofNullable(adminGralDto.getAdmon().getIdAdmonGeneral())
				.flatMap(admonGeneralRepository::findById);

		if (cat.isPresent()) {
			CatAdmonGeneral tmp = cat.get();
			validarDuplicadoAdministracionesGeneralesId(adminGralDto, tmp.getIdAdmonGeneral());
			actualizarAdmonGeneral(tmp, adminGralDto.getAdmon());
			admonGeneralRepository.save(tmp);
			registrarPista(tmp, false);
			return tmp;
		} else {
			validarDuplicadoAdministracionesGenerales(adminGralDto);
			adminGralDto.getAdmon().setFechaCreacion(LocalDateTime.now());
			CatAdmonGeneral nuevoAdmon = admonGeneralRepository.save(adminGralDto.getAdmon());
			registrarPista(nuevoAdmon, true);
			return nuevoAdmon;
		}
	}

	private void actualizarAdmonGeneral(CatAdmonGeneral existente, CatAdmonGeneral nuevo) {
		existente.setAcronimo(nuevo.getAcronimo());
		existente.setAdministracion(nuevo.getAdministracion());
		existente.setEstatus(nuevo.isEstatus());
		existente.setFechaModificacion(LocalDateTime.now());
		existente.setNombre(nuevo.getNombre());
		existente.setPuesto(nuevo.getPuesto());
	}

	private void registrarPista(CatAdmonGeneral admon, boolean nuevo) {
		StringBuilder textoPista = new StringBuilder();
		textoPista.append(FECHA_CREACION).append(admon.getFechaCreacion()).append("|").append(FECHA_MODIFI)
				.append(admon.getFechaModificacion()).append("|").append(ESTATUS).append(admon.isEstatus()).append("|")
				.append(ADMINISTRACION).append(admon.getAdministracion()).append("|").append(ACRONIMO)
				.append(admon.getAcronimo()).append("|").append(PUESTO).append(admon.getPuesto()).append("|");



		// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(),


		// nuevo ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(),


		// Constantes.getAtributosComplementarios()[0] + admon.getAdministracion()


		// + Constantes.getAtributosComplementarios()[1] + admon.getIdAdmonGeneral() + "|" + textoPista,


		// Optional.empty());
	}

	private void procesarAdministradores(List<CatAdministradorGeneral> administradores, CatAdmonGeneral admonGeneral) {
	    List<CatAdministradorGeneral> nuevos = new ArrayList<>();
	    List<CatAdministradorGeneral> actualizados = new ArrayList<>();

	    for (CatAdministradorGeneral admin : administradores) {
	        if (admin.getFechaFinVigencia() != null
	                && admin.getFechaInicioVigencia().isAfter(admin.getFechaFinVigencia())) {
	            throw new CatalogoException(ErroresEnum.ERROR_FECHA_VIGENCIA);
	        }
	        admin.setCatAdmonGeneral(admonGeneral);

	        if (admin.getIdAdministradorGeneral() == null) { 
	            // Es un nuevo registro
	            admin.setFechaCreacion(LocalDateTime.now());
	            nuevos.add(admin);
	        } else { 
	        	actualizarAdministradorGeneral(admin, actualizados);
	        }
	    }

	    administradorGeneralRepository.saveAll(nuevos);
	    administradorGeneralRepository.saveAll(actualizados);

	    guardarPistasAdministradores(nuevos, true);
	    guardarPistasAdministradores(actualizados, false);
	}
	
	private void actualizarAdministradorGeneral(CatAdministradorGeneral admin, List<CatAdministradorGeneral> actualizados) {
		Optional<CatAdministradorGeneral> adminDBOpt = administradorGeneralRepository.findById(admin.getIdAdministradorGeneral());
        if (adminDBOpt.isPresent()) {
            CatAdministradorGeneral adminDB = adminDBOpt.get();
            
            if (!Objects.equals(admin.getFechaModificacion(), adminDB.getFechaModificacion())) {
            	admin.setFechaModificacion(null);
            }

            if (!esIgual(admin, adminDB)) { 
                admin.setFechaModificacion(LocalDateTime.now());
                actualizados.add(admin);
            }
        } else { 
            admin.setFechaModificacion(LocalDateTime.now());
            actualizados.add(admin);
        }
	}


	private void guardarPistasAdministradores(List<CatAdministradorGeneral> administradores, boolean esNuevo) {
		administradores.forEach(admin -> {
			String textoPista = textoPista(admin);

			// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(),

			// esNuevo ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), textoPista, Optional.empty());
		});
	}

	private void validarDuplicadoAdministracionesGeneralesId(
			AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto, Integer idAdmonGeneral) {
		CatAdmonGeneral admon = admonGeneralRepository.findByAdministracionAndAcronimoAndPuestoId(
				adminGralDto.getAdmon().getAdministracion(), adminGralDto.getAdmon().getAcronimo(),
				adminGralDto.getAdmon().getPuesto(), idAdmonGeneral);
		if (admon != null) {
			throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
		}
	}

	private void validarDuplicadoAdministracionesGenerales(
			AdminGeneric<CatAdmonGeneral, CatAdministradorGeneral> adminGralDto) {
		CatAdmonGeneral admon = admonGeneralRepository.findByAdministracionAndAcronimoAndPuesto(
				adminGralDto.getAdmon().getAdministracion(), adminGralDto.getAdmon().getAcronimo(),
				adminGralDto.getAdmon().getPuesto());
		if (admon != null) {
			throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
		}
	}

	@Override
	@Transactional
	public AdminGeneric<CatAdmonCentral, CatAdministradorCentral> guardarAdministracionesCentral(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		log.info("Objeto a guardar: {}", adminGralDto);

		validarCantidadAdministradoresActivos(adminGralDto);

		if (!adminGralDto.getAdmon().isEstatus()) {
			desactivarAdministradores(adminGralDto);
		}

		CatAdmonCentral admonCentral = guardarOActualizarAdmonCentral(adminGralDto);
		procesarAdministradores(adminGralDto.getAdministradores(), admonCentral);

		adminGralDto.setAdmon(admonCentral);
		return adminGralDto;
	}

	private void validarCantidadAdministradoresActivos(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		long contador = adminGralDto.getAdministradores().stream().filter(BaseCatalogoModel::isEstatus).count();
		log.info("Administradores activos CatAdmonCentral: {}", contador);
		if (contador > 1) {
			throw new CatalogoException(ErroresEnum.ERROR_MSJ_ADMINISTRADORES);
		}
	}

	private void desactivarAdministradores(AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		adminGralDto.getAdministradores().forEach(d -> d.setEstatus(false));
	}

	private CatAdmonCentral guardarOActualizarAdmonCentral(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		CatAdmonCentral admonCentral;
		boolean nuevo = adminGralDto.getAdmon().getIdAdmonCentral() == null;

		if (nuevo) {
			validarDuplicadoAdministracionesCentral(adminGralDto);
			adminGralDto.getAdmon().setFechaCreacion(LocalDateTime.now());
			adminGralDto.getAdmon().setFechaModificacion(null);
			admonCentral = catAdmonCentralRepository.save(adminGralDto.getAdmon());
		} else {
			admonCentral = actualizarAdmonCentralExistente(adminGralDto);
		}

		guardarPistaAdmonCentral(adminGralDto, admonCentral, nuevo);
		return admonCentral;
	}

	private CatAdmonCentral actualizarAdmonCentralExistente(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		CatAdmonCentral admonCentral = catAdmonCentralRepository.findById(adminGralDto.getAdmon().getIdAdmonCentral())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_REGISTRO_NO_ENCONTRADO));

		validarDuplicadoAdministracionesCentralId(adminGralDto, admonCentral.getIdAdmonCentral());
		admonCentral.setAcronimo(adminGralDto.getAdmon().getAcronimo());
		admonCentral.setAdministracion(adminGralDto.getAdmon().getAdministracion());
		admonCentral.setEstatus(adminGralDto.getAdmon().isEstatus());
		admonCentral.setFechaModificacion(LocalDateTime.now());
		admonCentral.setNombre(adminGralDto.getAdmon().getNombre());
		admonCentral.setPuesto(adminGralDto.getAdmon().getPuesto());

		return catAdmonCentralRepository.save(admonCentral);
	}

	private void guardarPistaAdmonCentral(AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto,
			CatAdmonCentral admonCentral, boolean nuevo) {
		String textoPista = generarTextoPistaAdmon(adminGralDto, admonCentral);

		// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(),

		// nuevo ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), textoPista, Optional.empty());
	}

	private String generarTextoPistaAdmon(AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto,
			CatAdmonCentral admonCentral) {
		return new StringBuilder().append(FECHA_CREACION).append(admonCentral.getFechaCreacion()).append("|")
				.append(FECHA_MODIFI).append(admonCentral.getFechaModificacion()).append("|").append(ESTATUS)
				.append(adminGralDto.getAdmon().isEstatus()).append("|").append(ADMINISTRACION)
				.append(adminGralDto.getAdmon().getAdministracion()).append("|").append(ACRONIMO)
				.append(adminGralDto.getAdmon().getAcronimo()).append("|").append(PUESTO)
				.append(adminGralDto.getAdmon().getPuesto()).toString();
	}

	private void procesarAdministradores(List<CatAdministradorCentral> administradores, CatAdmonCentral admonCentral) {
	    List<CatAdministradorCentral> administradoresNuevos = new ArrayList<>();
	    List<CatAdministradorCentral> administradoresActualizados = new ArrayList<>();

	    for (CatAdministradorCentral administrador : administradores) {
	        validarFechasVigencia(administrador);
	        administrador.setCatAdmonCentral(admonCentral);

	        if (administrador.getIdAdministradorCentral() == null) { 
	            administrador.setFechaCreacion(LocalDateTime.now());
	            administradoresNuevos.add(administrador);
	        } else { 
	        	actualizarAdministradorCentral(administrador, administradoresActualizados);
	        }
	    }

	    administradorCentralRepository.saveAll(administradoresNuevos);
	    administradorCentralRepository.saveAll(administradoresActualizados);

	    // ACTUALIZAR FICHAS TÉCNICAS: Cuando se actualiza un administrador central activo,
	    // actualizar todas las fichas técnicas que pertenecen a esa administración central
	    actualizarFichasTecnicasConAdministradorActivo(administradores, admonCentral);

	    guardarPistasAdministradores(administradoresNuevos, TipoMovPista.INSERTA_REGISTRO);
	    guardarPistasAdministradores(administradoresActualizados, TipoMovPista.ACTUALIZA_REGISTRO);
	}
	
	private void actualizarAdministradorCentral(CatAdministradorCentral administrador, List<CatAdministradorCentral> administradoresActualizados) {
		Optional<CatAdministradorCentral> administradorDBOpt = administradorCentralRepository.findById(administrador.getIdAdministradorCentral());
        if (administradorDBOpt.isPresent()) {
            CatAdministradorCentral administradorDB = administradorDBOpt.get();
            
            if (!Objects.equals(administrador.getFechaModificacion(), administradorDB.getFechaModificacion())) {
                administrador.setFechaModificacion(null);
            }

            if (!esIgual(administrador, administradorDB)) { 
                administrador.setFechaModificacion(LocalDateTime.now());
                administradoresActualizados.add(administrador);
            }
        } else { 
            administrador.setFechaModificacion(LocalDateTime.now());
            administradoresActualizados.add(administrador);
        }
	}

	/**
	 * Compara dos objetos para verificar si tienen los mismos valores en los campos relevantes.
	 */

	
	public static <T extends AdministradorBase> boolean esIgual(T nuevo, T original) {
	    return Objects.equals(nuevo.getAdministrador(), original.getAdministrador()) &&
	           Objects.equals(nuevo.getFechaInicioVigencia(), original.getFechaInicioVigencia()) &&
	           Objects.equals(nuevo.isEstatus(), original.isEstatus()) &&
	           Objects.equals(nuevo.getFechaFinVigencia(), original.getFechaFinVigencia());
	}



	private void validarFechasVigencia(CatAdministradorCentral administrador) {
		if (administrador.getFechaFinVigencia() != null
				&& administrador.getFechaInicioVigencia().isAfter(administrador.getFechaFinVigencia())) {
			throw new CatalogoException(ErroresEnum.ERROR_FECHA_VIGENCIA);
		}
	}

	private void guardarPistasAdministradores(List<CatAdministradorCentral> administradores, TipoMovPista tipoMov) {
		for (CatAdministradorCentral administrador : administradores) {
			String textoPista = textoPista(administrador);

			// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), tipoMov.getId(),

			// TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), textoPista, Optional.empty());
		}
	}

	/**
	 * Actualiza las fichas técnicas cuando se modifica un administrador central.
	 * 
	 * Este método busca el administrador activo (estatus=true) de la administración central,
	 * y actualiza todas las fichas técnicas activas de esa administración para que
	 * apunten al nuevo administrador activo, sin importar su vigencia.
	 * 
	 * @param administradores Lista de administradores que se están procesando
	 * @param admonCentral Administración central a la que pertenecen
	 */
	private void actualizarFichasTecnicasConAdministradorActivo(
			List<CatAdministradorCentral> administradores, 
			CatAdmonCentral admonCentral) {
		
		try {
			log.info("=== INICIANDO ACTUALIZACIÓN DE FICHAS TÉCNICAS ===");
			log.info("Administración Central ID: {} - {}", admonCentral.getIdAdmonCentral(), admonCentral.getAcronimo());
			log.info("Total de administradores recibidos: {}", administradores.size());
			
			// Buscar el administrador activo (solo por estatus, sin validar vigencia)
			Optional<CatAdministradorCentral> adminActivoOpt = administradores.stream()
					.filter(admin -> {
						boolean activo = admin.isEstatus();
						log.info("  - Administrador ID: {} - {} - Estatus: {}", 
								admin.getIdAdministradorCentral(), 
								admin.getAdministrador(), 
								activo ? "ACTIVO" : "INACTIVO");
						return activo;
					})
					.findFirst();
			
			if (adminActivoOpt.isPresent()) {
				CatAdministradorCentral adminActivo = adminActivoOpt.get();
				
				log.info("✓ Administrador activo encontrado: ID={}, Nombre={}", 
						adminActivo.getIdAdministradorCentral(), 
						adminActivo.getAdministrador());
				
				log.info(">>> EJECUTANDO UPDATE en BD: id_admon_central={}, nuevo_id_administrador_central={}", 
						admonCentral.getIdAdmonCentral(), 
						adminActivo.getIdAdministradorCentral());
				
				// Actualizar todas las fichas técnicas activas de esta administración central
				// con el nuevo administrador activo
				fichaAdmonCentralRepository.actualizarAdministradorEnFichasActivas(
						admonCentral.getIdAdmonCentral(),
						adminActivo.getIdAdministradorCentral()
				);
				
				log.info("✓ UPDATE ejecutado correctamente. Fichas actualizadas para administración central ID: {} con administrador ID: {} ({})",
						admonCentral.getIdAdmonCentral(), 
						adminActivo.getIdAdministradorCentral(),
						adminActivo.getAdministrador());
			} else {
				log.warn("✗ No se encontró administrador activo (estatus=true) para la administración central ID: {}. " +
						"No se actualizaron las fichas técnicas.",
						admonCentral.getIdAdmonCentral());
			}
			log.info("=== FIN ACTUALIZACIÓN DE FICHAS TÉCNICAS ===");
		} catch (Exception e) {
			log.error("✗✗✗ ERROR al actualizar fichas técnicas para administración central ID: {}. Error: {}",
					admonCentral.getIdAdmonCentral(), e.getMessage(), e);
			e.printStackTrace();
			// No lanzamos excepción para no interrumpir el flujo principal
			// Las fichas se actualizarán cuando el usuario las edite manualmente
		}
	}

	private void validarDuplicadoAdministracionesCentralId(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto, Integer idAdmonCentral) {
		CatAdmonCentral admon = catAdmonCentralRepository.buscarRegistroId(adminGralDto.getAdmon().getAdministracion(),
				adminGralDto.getAdmon().getAcronimo(), adminGralDto.getAdmon().getPuesto(),
				adminGralDto.getAdmon().getCatAdmonGeneral().getIdAdmonGeneral(), idAdmonCentral);
		log.info("CatAdmonCentral: {}", admon);
		if (admon != null) {
			throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
		}
	}

	private void validarDuplicadoAdministracionesCentral(
			AdminGeneric<CatAdmonCentral, CatAdministradorCentral> adminGralDto) {
		CatAdmonCentral admon = catAdmonCentralRepository.buscarRegistro(adminGralDto.getAdmon().getAdministracion(),
				adminGralDto.getAdmon().getAcronimo(), adminGralDto.getAdmon().getPuesto(),
				adminGralDto.getAdmon().getCatAdmonGeneral().getIdAdmonGeneral());
		log.info("CatAdmonCentral: {}", admon);
		if (admon != null) {
			throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
		}
	}

	@Override
	@Transactional
	public AdminGeneric<CatAdministracion, CatAdministradorAdministracion> guardarAdministraciones(
	        AdminGeneric<CatAdministracion, CatAdministradorAdministracion> adminGralDto) {

	    validarAdministradoresActivos(adminGralDto.getAdministradores());

	    if (!adminGralDto.getAdmon().isEstatus()) {
	        adminGralDto.getAdministradores().forEach(adm -> adm.setEstatus(false));
	    }

	    CatAdministracion administracion = guardarOActualizarAdministracion(adminGralDto);

	    List<CatAdministradorAdministracion> administradores = adminGralDto.getAdministradores();
	    validarFechasVigencia(administradores);
	    asociarAdministracionAdministradores(administracion, administradores);

	    List<CatAdministradorAdministracion> nuevos = new ArrayList<>();
	    List<CatAdministradorAdministracion> actualizados = new ArrayList<>();

	    for (CatAdministradorAdministracion administrador : administradores) {
	        if (administrador.getIdAdministradorAdministracion() == null) {
	            administrador.setFechaCreacion(LocalDateTime.now());
	            nuevos.add(administrador);
	        } else {
	        	actualizarAdministradorAdministracion(administrador, actualizados);
	        }
	    }

	    guardarAdministradores(nuevos);
	    guardarAdministradores(actualizados);

	    registrarPistas(administracion, nuevos);
	    registrarPistas(administracion, actualizados);

	    adminGralDto.setAdmon(administracion);
	    return adminGralDto;
	}
	
	private void actualizarAdministradorAdministracion(CatAdministradorAdministracion administrador, List<CatAdministradorAdministracion> actualizados) {
		Optional<CatAdministradorAdministracion> administradorDBOpt =
                administradorAdministracionRepository.findById(administrador.getIdAdministradorAdministracion());

        if (administradorDBOpt.isPresent()) {
            CatAdministradorAdministracion administradorDB = administradorDBOpt.get();
            
            if (!Objects.equals(administrador.getFechaModificacion(), administradorDB.getFechaModificacion())) {
                administrador.setFechaModificacion(null);
            }

            if (!esIgual(administrador, administradorDB)) {
                administrador.setFechaModificacion(LocalDateTime.now());
                actualizados.add(administrador);
            }
        } else {
            administrador.setFechaModificacion(LocalDateTime.now());
            actualizados.add(administrador);
        }
	}

	/**
	 * Compara dos objetos para verificar si tienen los mismos valores en los campos relevantes.
	 */



	private void validarAdministradoresActivos(List<CatAdministradorAdministracion> administradores) {
		long activos = administradores.stream().filter(BaseCatalogoModel::isEstatus).count();
		log.info("Administradores activos CatAdministracion: {}", activos);
		if (activos > 1) {
			throw new CatalogoException(ErroresEnum.ERROR_MSJ_ADMINISTRADORES);
		}
	}

	private CatAdministracion guardarOActualizarAdministracion(
			AdminGeneric<CatAdministracion, CatAdministradorAdministracion> adminGralDto) {

		Optional<CatAdministracion> existente = Optional.ofNullable(
				adminGralDto.getAdmon().getIdAdministracion() != null
						? catAdministracionRepository.findById(adminGralDto.getAdmon().getIdAdministracion())
								.orElse(null)
						: null);

		if (existente.isPresent()) {
			CatAdministracion actual = existente.get();
			actualizarAdministracion(actual, adminGralDto.getAdmon());
			return catAdministracionRepository.save(actual);
		} else {
			validarDuplicadoAdministraciones(adminGralDto);
			adminGralDto.getAdmon().setFechaCreacion(LocalDateTime.now());
			return catAdministracionRepository.save(adminGralDto.getAdmon());
		}
	}

	private void actualizarAdministracion(CatAdministracion actual, CatAdministracion nueva) {
		actual.setAcronimo(nueva.getAcronimo());
		actual.setAdministracion(nueva.getAdministracion());
		actual.setEstatus(nueva.isEstatus());
		actual.setFechaModificacion(LocalDateTime.now());
		actual.setNombre(nueva.getNombre());
		actual.setPuesto(nueva.getPuesto());
	}

	private void validarFechasVigencia(List<CatAdministradorAdministracion> administradores) {
		administradores.forEach(adm -> {
			if (adm.getFechaFinVigencia() != null && adm.getFechaInicioVigencia().isAfter(adm.getFechaFinVigencia())) {
				throw new CatalogoException(ErroresEnum.ERROR_FECHA_VIGENCIA);
			}
		});
	}

	private void asociarAdministracionAdministradores(CatAdministracion administracion,
			List<CatAdministradorAdministracion> administradores) {
		administradores.forEach(adm -> {
			adm.setCatAdministracion(administracion);
			if (adm.getIdAdministradorAdministracion() == null) {
				adm.setFechaCreacion(LocalDateTime.now());
			} else if (adm.getFechaModificacion() != null) { 
				adm.setFechaModificacion(LocalDateTime.now());
			}
		});
	}


	private void guardarAdministradores(List<CatAdministradorAdministracion> administradores) {
		administradorAdministracionRepository.saveAll(administradores);
	}

	private void registrarPistas(CatAdministracion administracion,
			List<CatAdministradorAdministracion> administradores) {
		boolean esNuevo = administracion.getFechaModificacion() == null;

		// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(),

		// esNuevo ? TipoMovPista.INSERTA_REGISTRO.getId() : TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), construirTextoPista(administracion),

		// Optional.empty());

		administradores.forEach(adm -> {
			String pista = textoPista(adm);

			// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(),

			// adm.getFechaCreacion() != null ? TipoMovPista.INSERTA_REGISTRO.getId()

			// : TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), pista, Optional.empty());
		});
	}

	private String construirTextoPista(CatAdministracion admon) {
		return String.format("%s%s|%s%s|%s%s|%s%s|%s%s|%s%s|", FECHA_CREACION, admon.getFechaCreacion(), FECHA_MODIFI,
				admon.getFechaModificacion(), ESTATUS, admon.isEstatus(), ADMINISTRACION, admon.getAdministracion(),
				ACRONIMO, admon.getAcronimo(), PUESTO, admon.getPuesto());
	}

	private String textoPista(CatAdministradorAdministracion adm) {
		StringBuilder textoPista = new StringBuilder();
		textoPista.append("Id: ").append(adm.getIdAdministradorAdministracion()).append("|");
		textoPista.append(NOMBRE).append(adm.getAdministrador()).append("|");
		textoPista.append(FECHA_INICIO).append(adm.getFechaInicioVigencia()).append("|");
		textoPista.append(FECHA_FIN).append(adm.getFechaFinVigencia()).append("|");
		textoPista.append(FECHA_ULTIMA).append(adm.getFechaModificacion()).append("|");
		textoPista.append(ESTADO).append(adm.isEstatus()).append("|");
		return textoPista.toString();
	}

	private String textoPista(CatAdministradorGeneral adm) {
		StringBuilder textoPista = new StringBuilder();
		textoPista.append("Id: ").append(adm.getIdAdministradorGeneral()).append("|");
		textoPista.append(NOMBRE).append(adm.getAdministrador()).append("|");
		textoPista.append(FECHA_INICIO).append(adm.getFechaInicioVigencia()).append("|");
		textoPista.append(FECHA_FIN).append(adm.getFechaFinVigencia()).append("|");
		textoPista.append(FECHA_ULTIMA).append(adm.getFechaModificacion()).append("|");
		textoPista.append(ESTADO).append(adm.isEstatus()).append("|");
		return textoPista.toString();
	}

	private String textoPista(CatAdministradorCentral adm) {
		StringBuilder textoPista = new StringBuilder();
		textoPista.append("Id: ").append(adm.getIdAdministradorCentral()).append("|");
		textoPista.append(NOMBRE).append(adm.getAdministrador()).append("|");
		textoPista.append(FECHA_INICIO).append(adm.getFechaInicioVigencia()).append("|");
		textoPista.append(FECHA_FIN).append(adm.getFechaFinVigencia()).append("|");
		textoPista.append(FECHA_ULTIMA).append(adm.getFechaModificacion()).append("|");
		textoPista.append(ESTADO).append(adm.isEstatus()).append("|");
		return textoPista.toString();
	}

	private void validarDuplicadoAdministraciones(
			AdminGeneric<CatAdministracion, CatAdministradorAdministracion> adminGralDto) {
		CatAdministracion admon = catAdministracionRepository.buscarRegistro(
				adminGralDto.getAdmon().getAdministracion(), adminGralDto.getAdmon().getAcronimo(),
				adminGralDto.getAdmon().getPuesto(), adminGralDto.getAdmon().getCatAdmonCentral().getIdAdmonCentral());
		if (admon != null) {
			throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
		}
	}

}
