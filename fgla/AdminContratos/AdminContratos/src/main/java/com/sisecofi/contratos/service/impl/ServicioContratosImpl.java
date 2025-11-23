package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.dto.*;
import com.sisecofi.contratos.microservicios.*;
import com.sisecofi.contratos.repository.contrato.*;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.Configuration;
import com.sisecofi.contratos.util.consumer.ContratosPistasConsumer;
import com.sisecofi.contratos.util.consumer.CriteriaContrato;
import com.sisecofi.contratos.util.consumer.PathGenerator;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.contratos.util.validator.ContratoValidator;
import com.sisecofi.libreria.comunes.dto.contrato.*;
import com.sisecofi.libreria.comunes.dto.proveedor.ProveedorLigeroDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.*;
import com.sisecofi.libreria.comunes.model.contratos.*;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.contratos.service.ServicioContratos;
import com.sisecofi.contratos.service.ServicioDatosGeneralesContrato;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseReintegroRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseContratoRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaReintegroRepository;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Timestamp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioContratosImpl implements ServicioContratos {

	private final PistaService pistaService;
	private final ContratoRepository contratoRepository;
	private final ProveedorMicroservicio proveedorMicroservicio;
	private final ProyectosMicroservicio proyectoMicroservicio;
	private final ContratoValidator contratoValidator;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final VigenciaMontosRepository vigenciaMontosRepository;
	private final ServicioContratoRepository servicioContratoRepository;
	private final AsociacionContratoProovedorRepository asociacionContratoProovedorRepository;
	private final ProyectosMicroservicio proyectosMicroservicio;
	private final ParticipantesContratoRepository participantesContratoRepository;
	private final Session session;
	private final ConvenioModificatorioRepository convenioModificatorioRepository;
	private final ArchivoPlantillaContratoRepository archivoPlantillaContratoRepository;
	private final ArchivoPlantillaConvenioRepository archivoPlantillaConvenioRepository;
	private final ArchivoPlantillaReintegroRepository archivoPlantillaReintegroRepository;
	private final ArchivoOtroDocumentoFaseConvenioRepository archivoOtroDocumentoFaseConvenioRepository;
	private final ArchivoOtroDocumentoFaseReintegroRepository archivoOtroDocumentoFaseReintegroRepository;
	private final ArchivoOtroDocumentoFaseContratoRepository archivoOtroDocumentoFaseRepository;
	private final NexusImpl nexusImpl;
	private final SeguridadService seguridadService;
	private final ContratosPistasConsumer contratosPistasConsumer;
	private final ServicioDatosGeneralesContrato servicioDatosGeneralesContrato;

	private static final String ERROR = "Error";

	@Override
	public ContratoDto obtenerContratoPorId(Long idContrato) {
		ContratoModel pageCotrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

		return transformarContratoModelAContratoDto(pageCotrato);
	}

	@Override
	public List<ContratoModel> obtenerInfoContratos() {
		return contratoRepository.findAllByEstatusTrue()
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_CONTRATOS_NO_ENCONTRADOS));
	}

	@Override
	public List<ContratoNombreDto> obtenerInfoContratosDto() {
		return contratoRepository.findAllContratosAsDto();
	}

	@Override
	public List<ContratoNombreDto> obtenerInfoContratosDtoProyecto(Long idProyecto) {
		return contratoRepository.findContratosByProyectoId(idProyecto);
	}




@Override
public Page<ContratoDtoLigero> buscarContratos(CriteriosDeBusquedaContratoDto contrato) {
    Long idContrato = contrato.getIdContrato();
	int page = Math.max(contrato.getPage(), 0);
		int size = contrato.getSize();
		Pageable pageable = PageRequest.of(page, size);

    boolean acceso = !(seguridadService.validarRolAdminSistema()
            || seguridadService.validarRolAdminSistemaSecundario()
            || seguridadService.validarRolGestorDocumentalContrato()
            || seguridadService.validarRolAdminMatrizDocumental() || seguridadService.validarRolTodosProyectos()
            || seguridadService.validarRolVerificadorGeneral());

    if (idContrato != null) {
        CriteriosDeBusquedaContratoDto nuevaEstructura = new CriteriosDeBusquedaContratoDto();
        nuevaEstructura.setIdContrato(contrato.getIdContrato());
        nuevaEstructura.setPage(contrato.getPage());
        nuevaEstructura.setSize(contrato.getSize());
        contrato = nuevaEstructura;
    }


     CriteriaContrato criteria = crearCriterios(contrato, idContrato, acceso);

    Timestamp fechaInicioTimestamp = (criteria.getFechaInicio() != null)
            ? Timestamp.valueOf(criteria.getFechaInicio())
            : null;

    Timestamp fechaTerminoTimestamp = (criteria.getFechaTermino() != null)
            ? Timestamp.valueOf(criteria.getFechaTermino())
            : null;

    // OBTENER TODOS LOS ELEMENTOS SIN PAGINACIÓN para calcular el total correcto
    Page<Object[]> todosLosResultados = contratoRepository.findAllOptimizedRaw(criteria.getIdContrato(),
            criteria.getIdEstatusContrato(), true, criteria.getIdAdministracionCentral(), criteria.getIdProveedor(),
            (criteria.getUser() != null) ? criteria.getUser().getIdUser() : null, 
        pageable); // Sin paginación

    // Aplicar el mismo filtro de fechas a TODOS los elementos
    List<ContratoDtoLigero> todosLosFiltrados = todosLosResultados.stream().map(ContratoDtoLigero::new).filter(dto -> {
        LocalDateTime fechaInicioContrato = dto.getFechaInicio();
        LocalDateTime fechaFinContrato = dto.getFechaTermino();

        if (fechaInicioTimestamp == null && fechaTerminoTimestamp == null) {
            return true;
        }

        boolean cumpleFechaInicio = (fechaInicioTimestamp == null || (fechaInicioContrato != null
                && !fechaInicioContrato.isBefore(fechaInicioTimestamp.toLocalDateTime())));
        boolean cumpleFechaTermino = (fechaTerminoTimestamp == null || (fechaFinContrato != null
                && !fechaFinContrato.isAfter(fechaTerminoTimestamp.toLocalDateTime())));

        return cumpleFechaInicio && cumpleFechaTermino;
    }).toList();

   

    if (todosLosFiltrados.isEmpty()) {
        throw new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO);
    }

	String movimiento = obtenerCriterios(contrato);
		pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.CONTRATOS_TABLA.getIdSeccionPista(), movimiento, Optional.empty());

    
    return new PageImpl<>(todosLosFiltrados, pageable, todosLosResultados.getTotalElements());
}


	private CriteriaContrato crearCriterios(CriteriosDeBusquedaContratoDto contrato, Long idContrato, boolean acceso) {
		CriteriaContrato criteria = new CriteriaContrato();
		criteria.setIdContrato(idContrato);
		criteria.setFechaInicio(contrato.getFechaInicio());
		criteria.setFechaTermino(contrato.getFechaTermino());
		criteria.setIdAdministracionCentral(contrato.getIdAdministracionCentral());
		criteria.setIdEstatusContrato(contrato.getIdEstatusContrato());
		criteria.setIdProveedor(contrato.getIdProveedor());
		criteria.setEstatus(true);
		if (acceso) {
			criteria.setUser(obtenerUsuario());
		}
		criteria.setFiltroUsuario(acceso);

		return criteria;
	}

	private String obtenerCriterios(CriteriosDeBusquedaContratoDto contrato) {
		String criterios = "";
		if (contrato.getIdEstatusContrato() != null) {
			criterios += Constantes.getAtributosBuscarContratos()[0]
					+ Constantes.getEstatusContrato()[contrato.getIdEstatusContrato() - 1];
		}
		if (contrato.getFechaInicio() != null) {
			criterios += Constantes.getAtributosBuscarContratos()[1] + formatLocalDateTime(contrato.getFechaInicio());
		}
		if (contrato.getFechaTermino() != null) {
			criterios += Constantes.getAtributosBuscarContratos()[2] + formatLocalDateTime(contrato.getFechaTermino());
		}
		if (contrato.getIdProveedor() != null) {
			ProveedorModel proveedor = proveedorMicroservicio.buscarProveedor(contrato.getIdProveedor());
			criterios += Constantes.getAtributosBuscarContratos()[3] + proveedor.getNombreProveedor();
		}
		if (contrato.getIdAdministracionCentral() != null) {
			CatAdmonCentral cat = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), contrato.getIdAdministracionCentral(),
					new CatAdmonCentral());
			criterios += Constantes.getAtributosBuscarContratos()[4] + cat.getAdministracion();
		}

		return criterios;
	}

	private String formatLocalDateTime(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return dateTime.format(formatter);
	}

	@Override
	public List<ContratoDtoLigero> buscarContratosReporte(CriteriosDeBusquedaContratoDto contrato) {
		Long idContrato = contrato.getIdContrato();

		boolean acceso = !(seguridadService.validarRolAdminSistema()
				|| seguridadService.validarRolAdminSistemaSecundario()
				|| seguridadService.validarRolGestorDocumentalContrato()
				|| seguridadService.validarRolAdminMatrizDocumental() || seguridadService.validarRolTodosProyectos()
				|| seguridadService.validarRolVerificadorGeneral());

		if (idContrato != null) {
			CriteriosDeBusquedaContratoDto nuevaEstructura = new CriteriosDeBusquedaContratoDto();
			nuevaEstructura.setIdContrato(contrato.getIdContrato());
			nuevaEstructura.setPage(contrato.getPage());
			nuevaEstructura.setSize(contrato.getSize());
			contrato = nuevaEstructura;
		}

		CriteriaContrato criteria = crearCriterios(contrato, idContrato, acceso);

		Timestamp fechaInicioTimestamp = (criteria.getFechaInicio() != null)
				? Timestamp.valueOf(criteria.getFechaInicio())
				: null;

		Timestamp fechaTerminoTimestamp = (criteria.getFechaTermino() != null)
				? Timestamp.valueOf(criteria.getFechaTermino())
				: null;

		List<Object[]> resultados = contratoRepository.findAllOptimizedRaw(criteria.getIdContrato(),
				criteria.getIdEstatusContrato(), true, criteria.getIdAdministracionCentral(), criteria.getIdProveedor(),
				(criteria.getUser() != null) ? criteria.getUser().getIdUser() : null

		);

		

		return resultados.stream().map(ContratoDtoLigero::new).filter(dto -> {
			LocalDateTime fechaInicioContrato = dto.getFechaInicio();
			LocalDateTime fechaFinContrato = dto.getFechaTermino();

			if (fechaInicioTimestamp == null && fechaTerminoTimestamp == null) {
				return true;
			}

			boolean cumpleFechaInicio = (fechaInicioTimestamp == null || (fechaInicioContrato != null
					&& !fechaInicioContrato.isBefore(fechaInicioTimestamp.toLocalDateTime())));
			boolean cumpleFechaTermino = (fechaTerminoTimestamp == null || (fechaFinContrato != null
					&& !fechaFinContrato.isAfter(fechaTerminoTimestamp.toLocalDateTime())));

			return cumpleFechaInicio && cumpleFechaTermino;
		}).toList();
	}

	@Override
	public String actualizarContrato(ActualizarContratoDto contratoDto) {

		try {
			Usuario usuario = obtenerUsuario();

			contratoValidator.validarNombresContrato(contratoDto.getNombreContrato(),
					contratoDto.getNombreCortoContrato(), contratoDto.getIdContrato());

			ContratoModel contratoModel = contratoRepository.findByIdContrato(contratoDto.getIdContrato())
					.orElseThrow(null);

			contratoModel.getProyecto().setIdProyecto(contratoDto.getIdProyecto());
			contratoModel.setNombreContrato(contratoDto.getNombreContrato());
			contratoModel.setNombreCorto(contratoDto.getNombreCortoContrato());
			contratoModel.setFechaUltimaModificacion(LocalDateTime.now());
			if (usuario != null) {
				contratoModel.setUltimoModificador(usuario.getNombre());
			}

			contratoRepository.save(contratoModel);

			String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
					TipoSeccionPista.CONTRATOS_IDENTIFICACION.getIdSeccionPista(), movimiento,
					Optional.of(contratoModel));

			return "OK";

		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}

	}

	private String actualizarEstatusContrato(Long idContrato, String estatusContratoEsperado,
			String nuevoEstatusContrato, TipoSeccionPista tipoSeccionPista) {

		Usuario usuario = obtenerUsuario();

		try {
			ContratoModel contratoModel = contratoRepository.findByIdContrato(idContrato).orElseThrow(null);

			List<BaseCatalogoModel> catalogos = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), nuevoEstatusContrato);

			BaseCatalogoModel catalogo = catalogos.get(0);

			CatEstatusContrato estatusContrato = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), catalogo.getPrimaryKey(),
					new CatEstatusContrato());

			if (!estatusContrato.getNombre().equals(estatusContratoEsperado)) {
				throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
			}

			contratoModel.setIdEstatusContrato(estatusContrato.getIdEstatusContrato());
			contratoModel.setFechaUltimaModificacion(LocalDateTime.now());
			if (usuario != null) {
				contratoModel.setUltimoModificador(usuario.getNombre());
			}

			contratoRepository.save(contratoModel);

			String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
					tipoSeccionPista.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public String cancelarContrato(Long idContrato) {
		contratoValidator.eliminarRefistroValidacion(idContrato);
		return actualizarEstatusContrato(idContrato, "Cancelado", Constantes.ESTATUS_CONTRATO_CANCELADO,
				TipoSeccionPista.CONTRATOS_IDENTIFICACION);
	}

	@Override
	public String cierreEstatusContrato(Long idContrato) {
		contratoValidator.validarEstimaciones(idContrato);
		contratoValidator.validarDictamenes(idContrato);

		return actualizarEstatusContrato(idContrato, "Cerrado", Constantes.ESTATUS_CONTRATO_CERRADO,
				TipoSeccionPista.CONTRATO_DATOS_GENERALES);
	}

	@Override
	public ContratoDto iniciarContrato(InicialContratoDto inicialContratoDto) {

		Usuario usuario = obtenerUsuario();
		contratoValidator.validarNombresContrato(inicialContratoDto.getNombreContrato(),
				inicialContratoDto.getNombreCortoContrato(), inicialContratoDto.getIdContrato());

		try {
			Long idProyecto = inicialContratoDto.getIdProyecto();
			String nombreContrato = inicialContratoDto.getNombreContrato();
			String nombreCorto = inicialContratoDto.getNombreCortoContrato();

			ProyectoResponse proyectoResponse = proyectoMicroservicio.obtenerProyecto(idProyecto);
			ProyectoModel proyectoModel = proyectoResponse.getProyecto();

			ContratoModel contratoModel = new ContratoModel();
			contratoModel.setProyecto(proyectoModel);
			contratoModel.setNombreContrato(nombreContrato);
			contratoModel.setNombreCorto(nombreCorto);
			contratoModel.setFechaUltimaModificacion(LocalDateTime.now());

			assert usuario != null;
			if (usuario.getNombre() != null) {
				contratoModel.setUltimoModificador(usuario.getNombre());
			}
			contratoModel.setEstatus(true);

			List<BaseCatalogoModel> catalogos = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), Constantes.ESTATUS_CONTRATO_INICIAL);
			BaseCatalogoModel catalogoModel = catalogos.get(0);

			CatEstatusContrato catalogo = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), catalogoModel.getPrimaryKey(),
					new CatEstatusContrato());

			contratoModel.setIdEstatusContrato(catalogo.getIdEstatusContrato());
			contratoRepository.save(contratoModel);

			Long idContrato = contratoModel.getIdContrato();
			String idCcontratoFormato = formatearIdContrato(idContrato);
			String nombreEstatusContrato = catalogo.getNombre();

			ContratoDto contratoDto = new ContratoDto();

			contratoDto.setIdProyecto(idProyecto);
			contratoDto.setIdContrato(idContrato);
			contratoDto.setIdContratoFormato(idCcontratoFormato);
			contratoDto.setNombreContrato(nombreContrato);
			contratoDto.setNombreCorto(nombreCorto);
			contratoDto.setEstatusContrato(nombreEstatusContrato);

			String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.CONTRATOS_IDENTIFICACION.getIdSeccionPista(), movimiento, Optional.empty());

			return contratoDto;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	@Override
	public String ejecutarContrato(Long idContrato) {
		Usuario usuario = obtenerUsuario();

		vigenciaMontosRepository.findByIdContratoAndEstatusTrue(idContrato)
				.ifPresentOrElse(vigencia -> log.info("Registro encontrado"), () -> {
					throw new ContratoException(ErroresEnum.ERROR_VIGENCIA_MONTOS);
				});

		List<ServicioContratoModel> servicioContratoModels = servicioContratoRepository.findByIdContrato(idContrato);

		if (servicioContratoModels.isEmpty()) {
			throw new ContratoException(ErroresEnum.ERROR_REGISTRO_SERVICIOS);
		}

		try {
			ContratoModel contratoModel = contratoRepository.findByIdContrato(idContrato).orElseThrow(null);
			List<BaseCatalogoModel> catalogos = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), Constantes.ESTATUS_CONTRATO_EJECUCION);

			BaseCatalogoModel estatusContrato = catalogos.get(0);

			contratoModel.setFechaUltimaModificacion(LocalDateTime.now());

			if (usuario != null) {
				contratoModel.setUltimoModificador(usuario.getNombre());
			}
			contratoModel.setIdEstatusContrato(estatusContrato.getPrimaryKey());
			contratoRepository.save(contratoModel);

			String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
					TipoSeccionPista.CONTRATOS_IDENTIFICACION.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public Boolean esContratoInicial(Long idContrato) {
		try {
			ContratoModel contratoModel = contratoRepository.findByIdContrato(idContrato).orElseThrow(null);

			CatEstatusContrato estatusContrato = catalogoMicroservicio.obtenerInformacionCatalogoId(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), contratoModel.getIdEstatusContrato());

			return Objects.equals(estatusContrato.getNombre(), Constantes.TIPO_CONTRATO_INICIAL);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<ProveedorDto> obtenerProovedores(Long idContrato) {
		try {
			List<ProveedorDto> proveedores = asociacionContratoProovedorRepository
					.findProveedoresByContrato(idContrato);

			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(),
					TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());
			return proveedores;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public List<ProyectoSimpleDto> obtenerProyectos() {
		try {
			return proyectosMicroservicio.obtenerProyectos();
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<ProyectoSimpleDto> obtenerProyectosCompletos() {
		try {
			return proyectosMicroservicio.obtenerProyectosCompletos();
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public ContratoProveedorDto obtenerContratoProveedor(Long idContrato, Long idProveedor) {
		try {
			ContratoProveedorDto dto = new ContratoProveedorDto();
			dto.setProveedor(proveedorMicroservicio.buscarProveedor(idProveedor));
			Optional<ContratoModel> opcional = contratoRepository.findByIdContratoAndEstatusTrue(idContrato);
			opcional.ifPresent(dto::setContrato);
			Optional<VigenciaMontosModel> vigencia = vigenciaMontosRepository
					.findByIdContratoAndEstatusTrue(idContrato);
			vigencia.ifPresent(vigenciaMontosModel -> dto.setIdIva(vigenciaMontosModel.getId_iva()));

			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(),
					TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());
			return dto;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public ContratoConvenioModDto obtenerContratoNombreCorto(String nombreCorto) {
		return contratoRepository.findByNombreCortoAndEstatusTrue(nombreCorto).map(contrato -> {
			ContratoConvenioModDto dto = new ContratoConvenioModDto();
			dto.setContrato(contrato);

			contrato.getConveniosModificatorios().stream()
					.max(Comparator.comparingLong(ConvenioModificatorioModel::getIdConvenioModificatorio))
					.ifPresent(dto::setCm);

			return dto;
		}).orElseGet(ContratoConvenioModDto::new);
	}

	@Override
	public String reegresarInicial(Long idContrato) {

		List<BaseCatalogoModel> catalogos = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), Constantes.ESTATUS_CONTRATO_INICIAL);

		BaseCatalogoModel estatusContratoCat = catalogos.get(0);

		CatEstatusContrato estatusContrato = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), estatusContratoCat.getPrimaryKey(),
				new CatEstatusContrato());

		String nombreEstatusContratoCancelado = estatusContrato.getNombre();

		if (!nombreEstatusContratoCancelado.equals("Inicial")) {
			throw new ContratoException(ErroresEnum.ERROR_ESTATUS_CONTRATO);
		}

		ContratoModel contratoModel = contratoRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

		contratoModel.setIdEstatusContrato(estatusContratoCat.getPrimaryKey());
		contratoRepository.save(contratoModel);

		return "OK";
	}

	private ContratoDto transformarContratoModelAContratoDto(ContratoModel contratoModel) {
		try {
			Long idContrato = contratoModel.getIdContrato();

			List<ProveedorLigeroDto> listaProveedores = asociacionContratoProovedorRepository
					.findByIdContratoAndEstatusTrue(idContrato).stream()
					.map(AsociacionContratoProveedorModel::getProveedorModel).toList();

			DatosGeneralesContratoModel datosGenerales = contratoModel.getDatosGenerales();
			VigenciaMontosModel vigenciaMontosModel = contratoModel.getVigencia();

			ContratoDto contratoDto = new ContratoDto();
			contratoDto.setIdContrato(idContrato);
			contratoDto.setNombreContrato(contratoModel.getNombreContrato());
			contratoDto.setNombreCorto(contratoModel.getNombreCorto());
			contratoDto.setProveedores(listaProveedores);
			contratoDto.setIdProyecto(contratoModel.getProyecto().getIdProyecto());
			contratoDto.setIdContratoFormato(formatearIdContrato(idContrato));
			contratoDto.setEstatusContrato(contratoModel.getCatEstatusContrato().getNombre());

			if (datosGenerales != null) {
				contratoDto.setTipoProcedimiento(datosGenerales.getCatTipoProcedimiento().getNombre());
				contratoDto.setNumeroContrato(datosGenerales.getNumeroContrato());
				contratoDto.setAcuerdo(datosGenerales.getAcuerdo());
			}

			if (vigenciaMontosModel != null) {
				contratoDto.setFecha_inicio(vigenciaMontosModel.getFechaInicioVigenciaContrato());
				contratoDto.setFecha_termino(vigenciaMontosModel.getFechaFinVigenciaContrato());
				contratoDto.setMontoMaximo(vigenciaMontosModel.getMontoMaximoConImpuestos());
				contratoDto.setMontoPesos(vigenciaMontosModel.getMontoPesosConImpuestos());
				contratoDto.setIdIva(vigenciaMontosModel.getId_iva());
				if (vigenciaMontosModel.getCatTipoMoneda() != null
						&& vigenciaMontosModel.getCatTipoMoneda().getIdTipoMoneda().equals(1)) {
					contratoDto.setTipoMoneda("MXN");
				} else {
					contratoDto.setTipoMoneda(vigenciaMontosModel.getCatTipoMoneda().getNombre());
				}
				contratoDto.setTipoCambio(vigenciaMontosModel.getTipoCambioMaximo());
			}

			ConvenioModificatorioModel convenio = contratoModel.getUltimoConvenioModificatorio();
			if (convenio != null) {
				contratoDto.setUltimoContratoModificatorio(convenio);
				contratoDto.setMontoPesos(convenio.getMontoPesos());
				contratoDto.setMontoMaximoUltimoCm(convenio.getMontoMaximoConImpuestos());
				contratoDto.setFecha_termino(convenio.getFechaFin());
				contratoDto.setUltimoCm(convenio.getNumeroConvenio());
			}

			List<ParticipantesAdministracionModel> lista = participantesContratoRepository
					.findByIdContratoAndEstatusTrue(idContrato);
			if (!lista.isEmpty()) {
				List<CatAdmonCentralDto> admonsCentralesDto = lista.stream()
						.map(ParticipantesAdministracionModel::getCatAdmonCentral).filter(Objects::nonNull)
						.map(this::convertToDto).toList();

				List<CatAdmonGeneralDto> admonsGenerales = lista.stream()
						.map(ParticipantesAdministracionModel::getCatAdmonGeneral).filter(Objects::nonNull)
						.map(this::convertToDtoG).toList();

				contratoDto.setAdministracionCentral(admonsCentralesDto);
				contratoDto.setAdministracionGeneral(admonsGenerales);

				Integer idReponsabilidad = obtenerResponsabilidadId(Constantes.VALIDACION_RESPONSABILIDAD);
				Optional<ParticipantesAdministracionModel> adminContrato = buscarResponsable(lista, idReponsabilidad);
				adminContrato.ifPresent(admin -> contratoDto.setAdministradorContrato(servicioDatosGeneralesContrato.obtenerUsuarioDto(admin).getNombre()));

				Integer idReponsabilidadVerificador = obtenerResponsabilidadId(
						Constantes.VALIDACION_RESPONSABILIDAD_VERIFICADOR);
				Optional<ParticipantesAdministracionModel> verificadorContrato = buscarResponsable(lista,
						idReponsabilidadVerificador);
				verificadorContrato.ifPresent(
						verificador -> contratoDto.setVerificadorContrato(servicioDatosGeneralesContrato.obtenerUsuarioDto(verificador).getNombre()));
			}

			contratoDto.setEjecucion(contratoModel.isEjecucion());

			return contratoDto;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	private Integer obtenerResponsabilidadId(String tipoResponsabilidad) {
		List<BaseCatalogoModel> baseCatalogo = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.RESPONSABILIDAD.getIdCatalogo(), tipoResponsabilidad);
		return baseCatalogo.isEmpty() ? null : baseCatalogo.get(0).getPrimaryKey();
	}

	private Optional<ParticipantesAdministracionModel> buscarResponsable(List<ParticipantesAdministracionModel> lista,
			Integer idResponsabilidad) {
		return lista.stream().filter(admin -> idResponsabilidad.equals(admin.getIdResponsabilidad())).findFirst();
	}

	private Usuario obtenerUsuario() {
		return session.retornarUsuario().orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
	}

	private static String formatearIdContrato(Long idContrato) {
		StringBuilder numeroStr = new StringBuilder(String.valueOf(idContrato));

		while (numeroStr.length() < 5) {
			numeroStr.insert(0, "0");
		}
		return numeroStr.toString();
	}

	private CatAdmonCentralDto convertToDto(CatAdmonCentral admon) {
		CatAdmonCentralDto dto = new CatAdmonCentralDto();
		dto.setIdAdmonCentral(admon.getIdAdmonCentral());
		dto.setAdministracion(admon.getAdministracion());
		dto.setPuesto(admon.getPuesto());
		dto.setAcronimo(admon.getAcronimo());
		return dto;
	}

	private CatAdmonGeneralDto convertToDtoG(CatAdmonGeneral admon) {
		CatAdmonGeneralDto dto = new CatAdmonGeneralDto();
		dto.setIdAdmonGeneral(admon.getIdAdmonGeneral());
		dto.setAdministracion(admon.getAdministracion());
		dto.setPuesto(admon.getPuesto());
		dto.setAcronimo(admon.getAcronimo());
		return dto;
	}

	@Override
	public List<Archivo> obtenerArchivosSeccion(Long idProyecto) {
		List<ArchivoPlantillaConvenioModel> listaConvenio = archivoPlantillaConvenioRepository
				.findByCarpetaPlantillaConvenioModelConvenioPlantillaConvenioContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(
						idProyecto);

		List<Archivo> otrosDocuConvenio = archivoOtroDocumentoFaseConvenioRepository
				.findByConvenioModificatorioModel_ContratoModel_idProyectoAndEstatusTrue(idProyecto);

		List<ArchivoPlantillaContratoModel> listaContrato = archivoPlantillaContratoRepository
				.findByCarpetaPlantillaModelContratoPlantillaContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(
						idProyecto);

		List<Archivo> otrosDocuConcontrato = archivoOtroDocumentoFaseRepository
				.findByContratoModel_idProyectoAndEstatusTrue(idProyecto);

		List<ArchivoPlantillaReintegroModel> listaReintegros = archivoPlantillaReintegroRepository
				.findByCarpetaPlantillaModelReintegroContratoModelProyectoIdProyectoAndArchivoBaseEstatusTrue(
						idProyecto);

		List<Archivo> otrosDocuReintegro = archivoOtroDocumentoFaseReintegroRepository
				.findByReintegrosAsociadosModel_ContratoModel_idProyectoAndEstatusTrue(idProyecto);

		List<Archivo> lista = new ArrayList<>();
		lista.addAll(listaConvenio);
		lista.addAll(otrosDocuConvenio);
		lista.addAll(listaContrato);
		lista.addAll(otrosDocuConcontrato);
		lista.addAll(listaReintegros);
		lista.addAll(otrosDocuReintegro);

		return lista;
	}

	@Override
	public String regresarEstatusInicialContrato(Long idContrato) {
		ContratoModel contratoModel = contratoRepository.findByIdContrato(idContrato).orElseThrow(null);

		List<BaseCatalogoModel> catalogos = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), Constantes.ESTATUS_CONTRATO_INICIAL);

		BaseCatalogoModel catalogo = catalogos.get(0);

		CatEstatusContrato estatusContrato = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), catalogo.getPrimaryKey(), new CatEstatusContrato());

		String nombreEstatusContratoCancelado = estatusContrato.getNombre();

		if (!nombreEstatusContratoCancelado.equals("Inicial")) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
		contratoModel.setIdEstatusContrato(estatusContrato.getIdEstatusContrato());
		contratoModel.setFechaUltimaModificacion(LocalDateTime.now());

		contratoRepository.save(contratoModel);

		String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
		pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
				TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), movimiento, Optional.empty());

		return "OK";
	}

	@Override
	public ConvenioModificatorioModel obtenerConvenio(String numero) {
		String decodedId = URLDecoder.decode(numero, StandardCharsets.UTF_8);
		return convenioModificatorioRepository.findByNumeroConvenioAndEstatusTrue(decodedId).orElseThrow(null);
	}

	@Override
	public String cierreContrato(CierreContratoDto contratoDto) {

		File file = null;
		try {
			PathGenerator pathGenerator = new PathGenerator();
			Configuration config = new Configuration();

			String nombre = contratoDto.getNombreArchivo();
			String archivo = contratoDto.getActaCierre();

			config.setProperty(Constantes.BASE_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS);
			config.setProperty(Constantes.CONTRATO_FOLDER, ConstantesParaRutasSATCloud.PATH_BASE_CIERRE_CONTRATOS);

			String path = pathGenerator.generarPathCierre(contratoDto.getIdContrato(), config);

			file = base64ToFile(archivo, nombre);

			cargarArchivosConInformacion(file, path);

			String ruta = pathGenerator.generarRuta(path, nombre);

			ContratoModel contratoModel = contratoRepository.findByIdContrato(contratoDto.getIdContrato())
					.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

			contratoModel.setActaCierreRuta(ruta);
			contratoRepository.save(contratoModel);

			String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
			pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), movimiento, Optional.empty());

			return "OK";

		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		} finally {
		    if (file != null && file.exists()) {
		        Path filePath = file.toPath();
		        try {
		            Files.delete(filePath);
		            log.info("Archivo temporal eliminado: {}", filePath);
		        } catch (IOException e) {
		            log.warn("No se pudo eliminar el archivo temporal: {}", filePath, e);
		        }
		    }
		}
	}

	public File base64ToFile(String base64String, String fileName) {
	    try {
	        // Sanitizar el nombre del archivo antes de usarlo
	        String safeFileName = sanitizeFileName(fileName);

	        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

	        Path tempDir = Files.createTempDirectory("secureTempDir");
	        setSecurePermissions(tempDir);

	        // Crear archivo temporal usando nombre sanitizado
	        Path tempFile = Files.createTempFile(tempDir, safeFileName, ".tmp");
	        setSecurePermissions(tempFile);

	        Files.write(tempFile, decodedBytes, StandardOpenOption.TRUNCATE_EXISTING);

	        return tempFile.toFile();
	    } catch (IllegalArgumentException | SecurityException | IOException e) {
	        throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
	    } 
	}

	/**
	 * Sanitiza el nombre del archivo para evitar inyección de recursos.
	 * Permite letras (incluyendo acentuadas), números, espacios, guiones, guiones bajos y puntos.
	 */
	private String sanitizeFileName(String input) {
	    if (input == null || input.trim().isEmpty()) {
	        throw new IllegalArgumentException("Nombre de archivo inválido");
	    }

	    // Permitir letras unicode (\p{L}), números (\p{N}), espacio (\s), guión, guión bajo y punto
	    String sanitized = input.replaceAll("[^\\p{L}\\p{N}\\s._-]", "");

	    // Truncar para evitar nombres demasiado largos
	    return sanitized.length() > 50 ? sanitized.substring(0, 50) : sanitized;
	}


	/**
	 * Aplica permisos restrictivos a archivos y directorios para evitar accesos no
	 * autorizados.
	 */
	private void setSecurePermissions(Path path) {
		try {
			if (Files.getFileStore(path).supportsFileAttributeView("posix")) {
				Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rwx------"));
			} else {
				Files.setAttribute(path, "dos:hidden", true);
			}
		} catch (IOException e) {
			log.warn("No se pudieron aplicar permisos seguros en {}", path);
		}
	}

	private void cargarArchivosConInformacion(File file, String path) {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			boolean archivo = nexusImpl.cargarArchivo(fileInputStream, path, file.getName());
			log.info("Archivo cargado: {}", archivo);
		} catch (Exception e) {
			log.error(ERROR);
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	public List<ContratoSimpleDto> obtenerContratosVigencia(ConsultaContratoDto dto) {
		LocalDateTime date = LocalDateTime.now();
		Long idUser = dto.isTodos() ? null : obtenerUsuario().getIdUser();
		boolean estatus = true;

		switch (dto.getVigencia()) {
		case "Si", "Sí":
			return idUser == null ? contratoRepository.findContratosConFechaSuperiorYEstadoDto(date, estatus)
					: contratoRepository.findContratosConFechaSuperiorYEstadoYUsuarioProyectoDto(date, estatus, idUser);

		case "No":
			return idUser == null ? contratoRepository.findContratosConFechaMenorYEstadoDto(date, estatus)
					: contratoRepository.findContratosConFechaMenorYEstadoYUsuarioProyectoDto(date, estatus, idUser);

		case "Todos":
			return idUser == null ? contratoRepository.findByEstatusDto(estatus)
					: contratoRepository.findByEstatusAndUsuarioProyectoDto(estatus, idUser);

		default:
			return new ArrayList<>();
		}
	}

	@Override
	public List<ContratoSimpleDto> obtenerContratosVig(int rol) {
		LocalDateTime date = LocalDateTime.now();
		if (rol > 0) {
			return contratoRepository.findContratosConFechaSuperiorYEstadoDto(date, true);
		} else {
			return contratoRepository.findContratosConFechaSuperiorYEstadoYUsuarioDto(date, true,
					Optional.ofNullable(obtenerUsuario()).map(Usuario::getIdUser).orElse(null));
		}

	}

	@Override
	public List<ContratoSimpleDto> obtenerContratosNoVig(int rol) {
		LocalDateTime date = LocalDateTime.now();
		if (rol > 0) {
			return contratoRepository.findContratosConFechaMenorYEstadoDto(date, true);
		} else {
			return contratoRepository.findContratosConFechaMenorYEstadoYUsuarioDto(date, true,
					Optional.ofNullable(obtenerUsuario()).map(Usuario::getIdUser).orElse(null));
		}

	}

	@Override
	public List<ContratoSimpleDto> obtenerContratosModel(int rol) {
		if (rol > 0) {
			return contratoRepository.findByEstatusDto(true);
		} else {
			return contratoRepository.findByEstatusAndUsuarioDto(true,
					Optional.ofNullable(obtenerUsuario()).map(Usuario::getIdUser).orElse(null));
		}

	}

	@Override
	public String utlimaMod(Long idContrato) {
		try {
			Usuario usuario = obtenerUsuario();
			LocalDateTime now = LocalDateTime.now();
			contratoRepository.updateFechaUltimaModificacion(idContrato, now, usuario.getNombre());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String formattedDate = now.format(formatter);
			return usuario.getNombre() + " " + formattedDate;

		} catch (Exception e) {
			log.info("Error al actualizar la fecha de última modificación");
			throw new ContratoException(ErroresEnum.ERROR_AL_ACTUALIZAR_MODIFICADOR_CONTRATO);
		}

	}

	@Override
	public ContratoDtoLigeroComun obtenerContratoDtoLigero(Long idContrato) {
		ContratoDtoLigeroComun ct= contratoRepository.findContratoById(idContrato).orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		ct.setProveedores(asociacionContratoProovedorRepository
				.findProveedoresByContrato(idContrato));
		return ct;
	}

}
