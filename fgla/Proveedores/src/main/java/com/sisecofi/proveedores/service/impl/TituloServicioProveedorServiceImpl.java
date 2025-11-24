package com.sisecofi.proveedores.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.ConsultaTituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;
import com.sisecofi.proveedores.repository.CatEstatusTituloServicioRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.repository.TituloServicioProveedorServiceRepository;
import com.sisecofi.proveedores.repository.TituloServicioRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.service.TituloServicioProveedorService;
import com.sisecofi.proveedores.util.consumer.VigenciaScheduler;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TituloServicioProveedorServiceImpl implements TituloServicioProveedorService {

	private final PistaService pistaService;
	private final TituloServicioProveedorServiceRepository tituloServicioProveedorServiceRepository;
	private final TituloServicioRepository tituloServicioRepository;
	private final CatEstatusTituloServicioRepository catEstatusTituloServicioRepository;
	private final ProveedorRepository proveedorRepository;
	private final VigenciaScheduler vigenciaScheduler;
	private final ModelMapper modelMapper;
	private static final String PISTA_GEN = "Pista generada: {}";
	private static final String ID_PROVEEDOR = "Id Proveedor: ";
	private static final String ID_TITULO_SERVICIO = "Id del titulo de servicio: ";
	private static final String NUMERO_TITULO = "Número de título: ";
	private static final String TITULO_SERVICIO = "Título de servicio: ";
	private static final String ESTATUS = "Estatus: ";
	private static final String VENCIMIENTO_TITULO = "Vencimiento de título: ";
	private static final String COMENTARIOS = "Comentarios: ";

	@Transactional
	@Override
	public TituloServicioResponseDto crearTituloServicioProveedor(
			TituloServicioProveedorDto tituloServicioProveedorDto) {

		// Crear nueva instancia del modelo TituloServicioProveedor
		TituloServicioProveedorModel tituloServicioProveedorModel = new TituloServicioProveedorModel();
		tituloServicioProveedorModel.setNumeroTitulo(tituloServicioProveedorDto.getNumeroTitulo());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(tituloServicioProveedorDto.getVencimientoTitulo(), formatter);

		tituloServicioProveedorModel.setVencimientoTitulo(Date.valueOf(localDate));
		tituloServicioProveedorModel.setComentarios(tituloServicioProveedorDto.getComentarios());
		vigenciaScheduler.calcularVigencia(tituloServicioProveedorModel);
		tituloServicioProveedorModel.setVigencia(tituloServicioProveedorModel.getVigencia());
		log.info("vigencia: {}", tituloServicioProveedorDto.getVigencia());
		log.info("vigenciaaaaa: {}", tituloServicioProveedorDto.getVigencia());
		log.info("vigenciaaaaa2: {}", tituloServicioProveedorModel.getVigencia());
		tituloServicioProveedorModel.setColorVigencia(tituloServicioProveedorDto.getColorVigencia());
		tituloServicioProveedorModel.setEstatusEliminacionLogicaTitulo(true);

		// Establecer relación con el catálogo de títulos de servicio
		if (tituloServicioProveedorDto.getIdServicioTitulo() == null) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_TITULOSERVICIO);
		}
		CatTituloServicio catTituloServicio = tituloServicioRepository
				.findByIdTituloServicio(tituloServicioProveedorDto.getIdServicioTitulo())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_TITULOSERVICIO));
		tituloServicioProveedorModel.setTituloServicioModel(catTituloServicio);

		// Establecer relación con el catálogo de estatus de título de servicio
		if (tituloServicioProveedorDto.getIdEstatusTituloServicio() == null) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_SEMAFOROESTATUS);
		}
		CatEstatusTituloServicio catEstatusTituloServicio = catEstatusTituloServicioRepository
				.findByIdEstatusTituloServicio(tituloServicioProveedorDto.getIdEstatusTituloServicio())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_SEMAFOROESTATUS));
		tituloServicioProveedorModel.setCatEstatusTitulosServicioModel(catEstatusTituloServicio);

		// Establecer relación con el proveedor
		if (tituloServicioProveedorDto.getIdProveedor() == null) {
			throw new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_USUARIO);
		}
		ProveedorModel proveedorModel = proveedorRepository
				.findByIdProveedor(tituloServicioProveedorDto.getIdProveedor())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_USUARIO));
		tituloServicioProveedorModel.setProveedorModel(proveedorModel);

		// orden titulo
		Integer maxOrdenTitulo = tituloServicioProveedorServiceRepository
				.findMaxOrdenTituloByIdProveedor(tituloServicioProveedorDto.getIdProveedor());
		tituloServicioProveedorModel.setOrdenTitulo(maxOrdenTitulo + 1);

		try {
			// Guardar el registro de título de servicio proveedor
			log.info("Creando Título Servicio Proveedor: {}", tituloServicioProveedorDto);
			tituloServicioProveedorServiceRepository.save(tituloServicioProveedorModel);
		} catch (CatalogoException e) {
			throw new CatalogoException(ErroresEnum.ERROR_ALGUADAR_TITULO, e);
		}

		// Crear y llenar el objeto de respuesta
		TituloServicioResponseDto responseDto = construirDto(tituloServicioProveedorModel, catTituloServicio,
				catEstatusTituloServicio);
		responseDto.setOrdenTitulo(tituloServicioProveedorModel.getOrdenTitulo());

		// pistas auditoria -Insertar
		StringBuilder builder = new StringBuilder();

		builder.append(ID_PROVEEDOR).append(proveedorModel.getIdProveedor()).append(" | ").append(ID_TITULO_SERVICIO)
				.append(tituloServicioProveedorModel.getIdTituloServicioProveedor()).append(" | ").append(NUMERO_TITULO)
				.append(tituloServicioProveedorModel.getNumeroTitulo()).append(" | ").append(TITULO_SERVICIO)
				.append(catTituloServicio.getNombre()).append(" | ").append(ESTATUS)
				.append(catEstatusTituloServicio.getNombre()).append(" | ").append(VENCIMIENTO_TITULO)
				.append(tituloServicioProveedorModel.getVencimientoTitulo()).append(" | ").append(COMENTARIOS)
				.append(tituloServicioProveedorModel.getComentarios());

		log.info(PISTA_GEN, builder.toString());



		// pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.PROVEEDOR_TITULO_SERVICIO.getIdSeccionPista(), builder.toString(), Optional.empty());

		return responseDto;
	}

	private TituloServicioResponseDto construirDto(TituloServicioProveedorModel tituloServicioProveedorModel,
			CatTituloServicio catTituloServicio, CatEstatusTituloServicio catEstatusTituloServicio) {
		TituloServicioResponseDto responseDto = new TituloServicioResponseDto();
		responseDto.setIdTituloServicioProveedor(tituloServicioProveedorModel.getIdTituloServicioProveedor());
		responseDto.setNumeroTitulo(tituloServicioProveedorModel.getNumeroTitulo());
		responseDto.setNombreTituloServicio(catTituloServicio.getNombre());
		responseDto.setIdEstatusTituloServicio(catEstatusTituloServicio.getIdEstatusTituloServicio());
		responseDto.setSemaforoEstatus(catEstatusTituloServicio.getColorSemaforoEstatus());
		responseDto.setVencimientoTitulo(tituloServicioProveedorModel.getVencimientoTitulo());
		responseDto.setVigencia(tituloServicioProveedorModel.getVigencia());
		responseDto.setColorVigencia(tituloServicioProveedorModel.getColorVigencia());
		responseDto.setComentarios(tituloServicioProveedorModel.getComentarios());

		return responseDto;
	}

	@Transactional
	@Override
	public TituloServicioResponseDto actualizarTituloServicioProveedor(Long idTituloServicioProveedor,
			TituloServicioProveedorDto tituloServicioProveedorDto) {

		TituloServicioProveedorModel tituloServicioProveedorModel = tituloServicioProveedorServiceRepository
				.findByIdTituloServicioProveedor(idTituloServicioProveedor)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_TITULO));

		tituloServicioProveedorModel.setNumeroTitulo(tituloServicioProveedorDto.getNumeroTitulo());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(tituloServicioProveedorDto.getVencimientoTitulo(), formatter);

		tituloServicioProveedorModel.setVencimientoTitulo(Date.valueOf(localDate));
		tituloServicioProveedorModel.setComentarios(tituloServicioProveedorDto.getComentarios());
		tituloServicioProveedorModel.setVigencia(tituloServicioProveedorDto.getVigencia());
		tituloServicioProveedorModel.setColorVigencia(tituloServicioProveedorDto.getColorVigencia());

		// Establecer relación con el catálogo de títulos de servicio
		if (tituloServicioProveedorDto.getIdServicioTitulo() == null) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_TITULOSERVICIO);
		}
		CatTituloServicio catTituloServicio = tituloServicioRepository
				.findByIdTituloServicio(tituloServicioProveedorDto.getIdServicioTitulo())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_TITULOSERVICIO));
		tituloServicioProveedorModel.setTituloServicioModel(catTituloServicio);

		// Estatus

		// Establecer relación con el catálogo de estatus de título de servicio
		if (tituloServicioProveedorDto.getIdEstatusTituloServicio() == null) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_SEMAFOROESTATUS);
		}
		CatEstatusTituloServicio catEstatusTituloServicio = catEstatusTituloServicioRepository
				.findByIdEstatusTituloServicio(tituloServicioProveedorDto.getIdEstatusTituloServicio())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_SEMAFOROESTATUS));
		tituloServicioProveedorModel.setCatEstatusTitulosServicioModel(catEstatusTituloServicio);

		vigenciaScheduler.calcularVigencia(tituloServicioProveedorModel);

		try {
			tituloServicioProveedorServiceRepository.save(tituloServicioProveedorModel);
			log.info("Actualizar Titulo Servicio Provedor {} ", tituloServicioProveedorDto);

		} catch (CatalogoException e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_ACTUALIZAR_TITULOPROVEEDOR, e);
		}

		TituloServicioResponseDto responseDto = construirDto(tituloServicioProveedorModel, catTituloServicio,
				catEstatusTituloServicio);

		// pistas auditoria - Actualizar
		StringBuilder builder = new StringBuilder();

		builder.append(ID_PROVEEDOR).append(tituloServicioProveedorModel.getProveedorModel().getIdProveedor())
				.append(" | ").append(ID_TITULO_SERVICIO)
				.append(tituloServicioProveedorModel.getIdTituloServicioProveedor()).append(" | ").append(NUMERO_TITULO)
				.append(tituloServicioProveedorModel.getNumeroTitulo()).append(" | ").append(TITULO_SERVICIO)
				.append(catTituloServicio.getNombre()).append(" | ").append(ESTATUS)
				.append(catEstatusTituloServicio.getNombre()).append(" | ").append(VENCIMIENTO_TITULO)
				.append(tituloServicioProveedorModel.getVencimientoTitulo()).append(" | ").append(COMENTARIOS)
				.append(tituloServicioProveedorModel.getComentarios());

		log.info(PISTA_GEN, builder.toString());



		// pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.PROVEEDOR_TITULO_SERVICIO.getIdSeccionPista(), builder.toString(), Optional.empty());

		return responseDto;
	}

	@Transactional
	@Override
	public void eliminacionLogicaTituloServicioProveedor(Long idTituloServicioProveedor) {
		TituloServicioProveedorModel tituloServicioProveedorModel = tituloServicioProveedorServiceRepository
				.findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTituloTrue(idTituloServicioProveedor)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_TITULO));

		tituloServicioProveedorModel.setEstatusEliminacionLogicaTitulo(false);
		tituloServicioProveedorModel.setFechaBajaTitulo(LocalDateTime.now());
		tituloServicioProveedorServiceRepository.save(tituloServicioProveedorModel);

		Long idProveedor = tituloServicioProveedorModel.getProveedorModel().getIdProveedor();

		List<TituloServicioProveedorModel> tituloRestantes = tituloServicioProveedorServiceRepository
				.findProveedorOrdenTituloAsc(idProveedor);

		int nuevoOrden = 1;
		for (TituloServicioProveedorModel titulo : tituloRestantes) {
			titulo.setOrdenTitulo(nuevoOrden++);
			tituloServicioProveedorServiceRepository.save(titulo);
		}

		// pistas auditoria - eliminar
		StringBuilder builder = new StringBuilder();

		builder.append(ID_PROVEEDOR).append(tituloServicioProveedorModel.getProveedorModel().getIdProveedor())
				.append(" | ").append(ID_TITULO_SERVICIO)
				.append(tituloServicioProveedorModel.getIdTituloServicioProveedor()).append(" | ").append(NUMERO_TITULO)
				.append(tituloServicioProveedorModel.getNumeroTitulo()).append(" | ").append(TITULO_SERVICIO)
				.append(tituloServicioProveedorModel.getTituloServicioModel().getNombre()).append(" | ").append(ESTATUS)
				.append(tituloServicioProveedorModel.getCatEstatusTitulosServicioModel().getNombre()).append(" | ")
				.append(VENCIMIENTO_TITULO).append(tituloServicioProveedorModel.getVencimientoTitulo()).append(" | ")
				.append(COMENTARIOS).append(tituloServicioProveedorModel.getComentarios());

		log.info(PISTA_GEN, builder.toString());



		// pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


		// TipoSeccionPista.PROVEEDOR_TITULO_SERVICIO.getIdSeccionPista(), builder.toString(), Optional.empty());

	}

	@Override
	public ConsultaTituloServicioProveedorDto obtenerTituloServicioProveedorPorId(Long idTituloServicioProveedor) {

		TituloServicioProveedorModel tituloServicioProveedorModel = tituloServicioProveedorServiceRepository
				.findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTituloTrue(idTituloServicioProveedor)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_TITULO));
		// pistas auditoria - consultar

		StringBuilder builder = new StringBuilder();
		builder.append(ID_PROVEEDOR).append(tituloServicioProveedorModel.getProveedorModel().getIdProveedor());



		// pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.PROVEEDOR_TITULO_SERVICIO.getIdSeccionPista(), builder.toString(), Optional.empty());

		return modelMapper.map(tituloServicioProveedorModel, ConsultaTituloServicioProveedorDto.class);
	}

	// obtener reporte excel
	public List<TituloServicioResponseDto> obtenerDatosReporteServicioPorProveedor(Long idProveedor) {
		// Filtrar por idProveedor directamente en la base de datos
		List<TituloServicioProveedorModel> tituloServicioProveedorModelList = tituloServicioProveedorServiceRepository
				.findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTrue();

		return tituloServicioProveedorModelList.stream().map(model -> {
			TituloServicioResponseDto dto = new TituloServicioResponseDto();
			dto.setOrdenTitulo(model.getOrdenTitulo());
			dto.setNumeroTitulo(model.getNumeroTitulo());
			dto.setVencimientoTitulo(model.getVencimientoTitulo());
			dto.setComentarios(model.getComentarios());
			dto.setVigencia(model.getVigencia());

			if (model.getProveedorModel() != null) {
				dto.setIdProveedor(model.getProveedorModel().getIdProveedor());
			}

			if (model.getTituloServicioModel() != null) {
				dto.setNombreTituloServicio(model.getTituloServicioModel().getNombre());
			}

			if (model.getCatEstatusTitulosServicioModel() != null) {
				dto.setSemaforoEstatus(model.getCatEstatusTitulosServicioModel().getNombre());
			}

			return dto;
		}).filter(dto -> dto.getIdProveedor().equals(idProveedor)).toList();
	}

	@Override
	public List<ConsultaTituloServicioProveedorDto> obtenerTodosLosTitulos() {



		// pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


		// TipoSeccionPista.PROVEEDOR_TITULO_SERVICIO.getIdSeccionPista(),


		// TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

		List<TituloServicioProveedorModel> tituloServicioProveedorModel = tituloServicioProveedorServiceRepository
				.findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTrue();
		return tituloServicioProveedorModel.stream()
				.map(model -> modelMapper.map(model, ConsultaTituloServicioProveedorDto.class))
				.toList();

	}

}
