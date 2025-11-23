package com.sisecofi.proveedores.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.CatalogoProveedorDto;
import com.sisecofi.proveedores.dto.ConsultaProveedorDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;
import com.sisecofi.proveedores.dto.DirectorioProveedorDto;
import com.sisecofi.proveedores.dto.ProveedoGeneralDto;
import com.sisecofi.proveedores.dto.ProveedorDto;
import com.sisecofi.proveedores.dto.ProveedorRequestDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.proveedores.microservicio.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;
import com.sisecofi.libreria.comunes.model.proveedores.DictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;
import com.sisecofi.proveedores.repository.DictamenTecnicoRepository;
import com.sisecofi.proveedores.repository.DirectorioProveedorRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.repository.TituloServicioProveedorServiceRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.service.ProveedorService;
import com.sisecofi.proveedores.util.consumer.VigenciaScheduler;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author adtolentino
 * 
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

	private final PistaService pistaService;
	private final ProveedorRepository proveedorRepository;
	private final ModelMapper modelMapper;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private static final String PISTA_GEN = "Pista generada: {}";
	private static final String ID_PROVEEDOR = "Id del proveedor: ";
	private final DirectorioProveedorRepository directorioProveedorRepository;
	private final DictamenTecnicoRepository dictamenTecnicoRepository;
	private final TituloServicioProveedorServiceRepository tituloServicioProveedorServiceRepository;
	private final VigenciaScheduler vigenciaScheduler;

	@Transactional
	@Override
	public ProveedorModel crearProveedor(ProveedorDto proveedorDto) {

		validarNombreDuplicado(proveedorDto.getNombreProveedor());
		validarGiroEmpresarial(proveedorDto.getIdGiroEmpresarial());
		validarIdAgs(proveedorDto.getIdAgs());

		List<String> proveedoresConRfc = validarRFC(proveedorDto.getRfc());

		if (!proveedoresConRfc.isEmpty() && !proveedorDto.isDuplicado()) {
			String nombres = String.join(", ", proveedoresConRfc);
			throw new CatalogoException(ErroresEnum.ERROR_RFC_DUPLICADO, nombres);
		}

		ProveedorModel proveedorModel = modelMapper.map(proveedorDto, ProveedorModel.class);
		proveedorModel.setIdAgs(proveedorDto.getIdAgs());

		CatGiroEmpresarial giroEmpresarialModel = new CatGiroEmpresarial();
		giroEmpresarialModel.setIdGiroEmpresarialModel(Integer.parseInt("" + proveedorDto.getIdGiroEmpresarial()));
		proveedorModel.setGiroEmpresarialModel(giroEmpresarialModel);

		try {
			proveedorRepository.save(proveedorModel);
			log.info("Crear Proveedor {}", proveedorDto);

		} catch (CatalogoException e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR, e);

		}

		// pistas auditoria -Insertar
		StringBuilder builder = new StringBuilder();

		builder.append(ID_PROVEEDOR).append(proveedorModel.getIdProveedor()).append(" | ").append("Nombre Proveedor: ")
				.append(proveedorModel.getNombreProveedor()).append(" | ").append("Nombre Comercial: ")
				.append(proveedorModel.getNombreComercial()).append(" | ").append("RFC: ")
				.append(proveedorModel.getRfc()).append(" | ").append("Dirección: ")
				.append(proveedorModel.getDireccion()).append(" | ").append("Comentarios: ")
				.append(proveedorModel.getComentarios()).append(" | ").append("Estatus: ")
				.append(proveedorModel.getEstatus()).append(" | ").append("Id Ags: ").append(proveedorModel.getIdAgs())
				.append(" | ").append("Giro Empresarial: ").append(giroEmpresarialModel.getIdGiroEmpresarial());

		log.info("giro empresarial", giroEmpresarialModel.getIdGiroEmpresarial());
		log.info(PISTA_GEN, builder.toString());

		pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
				TipoSeccionPista.PROVEEDORES.getIdSeccionPista(), builder.toString(), Optional.empty());

		return proveedorModel;

	}

	// descartar
	@Override
	public Page<ConsultaProveedorDto> obtenerTodosLosProveedores(ProveedorRequestDto proveedorRequestDto) {
		int page = Math.max(proveedorRequestDto.getPage(), 0);
		int size = proveedorRequestDto.getSize();

		Pageable pageable = PageRequest.of(page, size);
		Page<ProveedorModel> proveedoresPage = proveedorRepository.findAllOrderedByIdProveedor(pageable);

		List<ConsultaProveedorDto> proveedoresDto = proveedoresPage.stream().map(proveedor -> {
			List<DirectorioProveedorDto> directorios = proveedor.getDirectorio().stream()
					.filter(DirectorioProveedorModel::isEstatus)
					.map(directorio -> modelMapper.map(directorio, DirectorioProveedorDto.class))
					.sorted(Comparator.comparing(DirectorioProveedorDto::getIdDirectorioContacto))
					.toList();

			List<TituloServicioResponseDto> titulos = proveedor.getTituloServicioProveedores().stream()
					.filter(TituloServicioProveedorModel::isEstatusEliminacionLogicaTitulo)
					.map(titulo -> modelMapper.map(titulo, TituloServicioResponseDto.class))
					.sorted(Comparator.comparing(TituloServicioResponseDto::getIdTituloServicioProveedor))
					.toList();

			ConsultaProveedorDto dto = modelMapper.map(proveedor, ConsultaProveedorDto.class);
			dto.setDirectorioContactos(directorios);
			dto.setTituloServicioProveedor(titulos);
			return dto;
		}).toList();

		pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROVEEDOR_DATOS_GENERALES.getIdSeccionPista(),
				TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

		return new PageImpl<>(proveedoresDto, pageable, proveedoresPage.getTotalElements());
	}

	@Override
	public ConsultaProveedorDto obtenerProveedorPorId(Long id) {
		ProveedorModel proveedorModel = proveedorRepository.findByIdProveedor(id)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_USUARIO));

		ConsultaProveedorDto proveedorDto = modelMapper.map(proveedorModel, ConsultaProveedorDto.class);

		ordenarYActualizarDirectorio(proveedorModel.getDirectorio());
		proveedorDto.setDirectorioContactos(mapearDirectorio(proveedorModel.getDirectorio()));

		ordenarYActualizarTituloServicio(proveedorModel.getTituloServicioProveedores());
		proveedorDto.setTituloServicioProveedor(mapearTituloServicio(proveedorModel.getTituloServicioProveedores()));

		ordenarYActualizarDictamenes(proveedorModel.getDictamenTecnico());
		proveedorDto.setDictamenTecnico(mapearDictamenes(proveedorModel.getDictamenTecnico()));

		guardarPistaConsulta(id);

		return proveedorDto;
	}

	@Override
	public List<DictamenTecnicoResponseDto> obtenerProveedorPorIdCumple(ProveedorRequestDto filtro) {
		log.info("Obteniendo dictámenes técnicos sin paginación con filtros: {}", filtro);

		List<DictamenTecnicoModel> dictamenes;

		if (filtro.getIdTituloServicio() != null && filtro.getIdCatResultadoDictamen() != null) {
			log.info("entra todos los filtros {}", 1);
			dictamenes = dictamenTecnicoRepository.findByProveedorTituloYResultado(filtro.getIdProveedor(),
					filtro.getIdTituloServicio(), filtro.getIdCatResultadoDictamen());
		} else if (filtro.getIdCatResultadoDictamen() != null) {
			log.info("filtros solo dictamen {}", 1);
			dictamenes = dictamenTecnicoRepository.findByProveedorYResultado(filtro.getIdProveedor(),
					filtro.getIdCatResultadoDictamen());
		} else if (filtro.getIdTituloServicio() != null) {
			log.info("filtros solo titulo {}", 1);
			dictamenes = dictamenTecnicoRepository.findByProveedorAndTituloServicio(filtro.getIdProveedor(),
					filtro.getIdTituloServicio());
		} else {
			log.info("filtros todos dictamen {}", 1);
			dictamenes = dictamenTecnicoRepository.findByProveedor(filtro.getIdProveedor());
		}
		log.info("cumple: {}", dictamenes.get(0).getCatResultadoDictamenTecnicoModel().getNombre());
		return dictamenes.stream().map(this::convertirADtoConModelMapper).toList();
	}

	@Override
	public List<TituloServicioResponseDto> obtenerProveedorTitulosGeneral(ProveedorRequestDto filtro) {
		log.info("Obteniendo titulos sin paginación con filtros: {}", filtro);

		List<TituloServicioProveedorModel> titulos;

		if (filtro.getIdTituloServicio() != null) {
			log.info("entra todos los filtros {}", 1);
			titulos = tituloServicioProveedorServiceRepository.findByProveedorYTitulo(filtro.getIdProveedor(),
					filtro.getIdTituloServicio());
		} else {
			log.info("filtros todos dictamen {}", 1);
			titulos = tituloServicioProveedorServiceRepository
					.findProveedorOrdenTituloAsc(Long.parseLong("" + filtro.getIdProveedor()));
		}
		return titulos.stream().map(this::convertirTituloADtoConModelMapper).toList();
	}

	private TituloServicioResponseDto convertirTituloADtoConModelMapper(TituloServicioProveedorModel obj) {
		log.info("Título de servicio: {}", obj.getTituloServicioModel().getNombre());

		TituloServicioResponseDto dto = modelMapper.map(obj, TituloServicioResponseDto.class);

		if (obj.getTituloServicioModel() != null) {
			dto.setIdTituloServicioProveedor(Long.parseLong("" + obj.getIdTituloServicioProveedor()));
			dto.setNombreTituloServicio(obj.getTituloServicioModel().getNombre());
		}
		dto.setIdEstatusTituloServicio(obj.getCatEstatusTitulosServicioModel().getIdEstatusTituloServicio());
		dto.setSemaforoEstatus(obj.getCatEstatusTitulosServicioModel().getColorSemaforoEstatus());
		dto.setNumeroTitulo(obj.getNumeroTitulo());
		dto.setVigencia(obj.getVigencia());
		dto.setColorVigencia(obj.getColorVigencia());

		return dto;
	}

	private DictamenTecnicoResponseDto convertirADtoConModelMapper(DictamenTecnicoModel obj) {
		log.info("cumple2: {}", obj.getCatResultadoDictamenTecnicoModel().getNombre());
		DictamenTecnicoResponseDto dto = modelMapper.map(obj, DictamenTecnicoResponseDto.class);

		if (obj.getCatTituloServicioModel() != null) {
			dto.setIdTituloServicio(obj.getIdTituloServicio());
			dto.setNombreTituloServicio(obj.getCatTituloServicioModel().getNombre());
		}

		if (obj.getCatResultadoDictamenTecnicoModel() != null) {
			dto.setIdResultadoDictamenTecnico(obj.getCatResultadoDictamenTecnicoModel().getPrimaryKey());
			String resultado;
			if (obj.getIdResultadoDictamenTecnico() == 1) {
				resultado = "Cumple";
			} else if (obj.getIdResultadoDictamenTecnico() == 2) {
				resultado = "No Cumple";
			} else {
				resultado = "";
			}
			dto.setResultado(resultado);

			dto.setIdResultadoDictamenTecnico(obj.getIdResultadoDictamenTecnico());
		}

		return dto;
	}

	private void ordenarYActualizarDirectorio(List<DirectorioProveedorModel> directorioResponse) {
		if (directorioResponse != null && !directorioResponse.isEmpty()) {
			Set<Integer> idsUnicos = new HashSet<>();
			int ordenDirectorio = 1;
			for (DirectorioProveedorModel directorio : directorioResponse) {
				while (idsUnicos.contains(directorio.getOrdenDirectorio())) {
					directorio.setOrdenDirectorio(ordenDirectorio++);
				}
				idsUnicos.add(directorio.getOrdenDirectorio());
			}
			directorioProveedorRepository.saveAll(directorioResponse);
		}
	}

	private List<DirectorioProveedorDto> mapearDirectorio(List<DirectorioProveedorModel> directorioResponse) {
		return directorioResponse.stream().filter(DirectorioProveedorModel::isEstatus)
				.map(directorio -> modelMapper.map(directorio, DirectorioProveedorDto.class))
				.toList();
	}

	private void ordenarYActualizarTituloServicio(List<TituloServicioProveedorModel> tituloServicioModelList) {
		Set<Integer> idsUnicosTitulo = new HashSet<>();
		int ordenTitulo = 1;
		for (TituloServicioProveedorModel servicio : tituloServicioModelList) {
			while (idsUnicosTitulo.contains(servicio.getOrdenTitulo())) {
				servicio.setOrdenTitulo(ordenTitulo++);
			}
			idsUnicosTitulo.add(servicio.getOrdenTitulo());
		}
		tituloServicioProveedorServiceRepository.saveAll(tituloServicioModelList);
	}

	private List<TituloServicioResponseDto> mapearTituloServicio(
			List<TituloServicioProveedorModel> tituloServicioModelList) {
		log.info("tamaño titulos consultar {}", tituloServicioModelList.size());
		tituloServicioModelList.forEach(servicio -> {
			vigenciaScheduler.calcularVigencia(servicio);
			servicio.setVigencia(servicio.getVigencia());
		});

		tituloServicioProveedorServiceRepository.saveAll(tituloServicioModelList);

		return tituloServicioModelList.stream().map(servicio -> {
			TituloServicioResponseDto dto = modelMapper.map(servicio, TituloServicioResponseDto.class);
			dto.setIdEstatusTituloServicio(
					Integer.parseInt("" + servicio.getCatEstatusTitulosServicioModel().getIdEstatusTituloServicio()));
			dto.setNombreTituloServicio(servicio.getTituloServicioModel().getNombre());
			dto.setSemaforoEstatus(servicio.getCatEstatusTitulosServicioModel().getColorSemaforoEstatus());
			return dto;
		}).toList();
	}

	public void ordenarYActualizarDictamenes(List<DictamenTecnicoModel> dictamenesModel) {
		Set<Integer> idsUnicosDictamen = new HashSet<>();
		int ordenDictamen = 1;
		for (DictamenTecnicoModel dictamen : dictamenesModel) {
			while (idsUnicosDictamen.contains(dictamen.getOrdenDictamenProveedor())) {
				dictamen.setOrdenDictamenProveedor(ordenDictamen++);
			}
			idsUnicosDictamen.add(dictamen.getOrdenDictamenProveedor());
		}
		dictamenTecnicoRepository.saveAll(dictamenesModel);
	}

	private List<DictamenTecnicoResponseDto> mapearDictamenes(List<DictamenTecnicoModel> dictamenesModel) {
		return dictamenesModel.stream().map(dictamen -> {
			DictamenTecnicoResponseDto dto = modelMapper.map(dictamen, DictamenTecnicoResponseDto.class);
			dto.setIdTituloServicio(Integer.parseInt("" + dictamen.getCatTituloServicioModel().getIdTituloServicio()));
			dto.setNombreTituloServicio(dictamen.getCatTituloServicioModel().getNombre());
			dto.setIdResultadoDictamenTecnico(Integer
					.parseInt("" + dictamen.getCatResultadoDictamenTecnicoModel().getIdResultadoDictamenTecnico()));
			dto.setResultado(dictamen.getCatResultadoDictamenTecnicoModel().getNombre());
			return dto;
		}).toList();
	}

	private void guardarPistaConsulta(Long id) {
		StringBuilder builder = new StringBuilder();
		builder.append(ID_PROVEEDOR).append(id);
		pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROVEEDOR_DETALLE.getIdSeccionPista(), builder.toString(), Optional.empty());
	}

	@Override
	public List<CatalogoProveedorDto> listarProveedores() {

		// pistas auditoria -Consulta
		pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROVEEDOR_DATOS_GENERALES.getIdSeccionPista(),
				TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

		List<ProveedorModel> proveedores = proveedorRepository.findAllByOrderByIdProveedorAsc();
		return proveedores.stream().map(proveedor -> modelMapper.map(proveedor, CatalogoProveedorDto.class))
				.toList();

	}

	@Override
	public List<CatalogoProveedorDto> listarProveedoresActivos() {

		// pistas auditoria -Consulta
		pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROVEEDOR_DATOS_GENERALES.getIdSeccionPista(),
				TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

		List<ProveedorModel> proveedores = proveedorRepository.findByEstatusOrderByNombreProveedorAsc(true);
		return proveedores.stream().map(proveedor -> modelMapper.map(proveedor, CatalogoProveedorDto.class))
				.toList();

	}

	// Actualizar proveedor
	@Transactional
	@Override
	public ProveedorDto actualizarProveedor(Long id, ProveedorDto proveedorDto) {
		ProveedorModel proveedorExiste = proveedorRepository.findByIdProveedor(id)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_USUARIO));

		// Solo validamos el RFC si ha cambiado respecto al que ya existía
		if (!Objects.equals(proveedorDto.getRfc(), proveedorExiste.getRfc())) {
			List<String> proveedoresConRfc = validarRFC(proveedorDto.getRfc());

			if (!proveedoresConRfc.isEmpty() && !proveedorDto.isDuplicado()) {
				String nombres = String.join(", ", proveedoresConRfc);
				throw new CatalogoException(ErroresEnum.ERROR_RFC_DUPLICADO, nombres);
			}
		}

		if (!proveedorDto.getNombreProveedor().equals(proveedorExiste.getNombreProveedor())) {
			validarNombreDuplicado(proveedorDto.getNombreProveedor());
		}

		if (!Objects.equals(proveedorDto.getIdAgs(), proveedorExiste.getIdAgs())) {
			validarIdAgs(proveedorDto.getIdAgs());
			proveedorExiste.setIdAgs(proveedorDto.getIdAgs());
		}

		validarGiroEmpresarial(proveedorDto.getIdGiroEmpresarial());

		modelMapper.map(proveedorDto, proveedorExiste);

		CatGiroEmpresarial giroEmpresarialModel = new CatGiroEmpresarial();
		giroEmpresarialModel.setIdGiroEmpresarialModel(Integer.parseInt("" + proveedorDto.getIdGiroEmpresarial()));

		proveedorExiste.setGiroEmpresarialModel(giroEmpresarialModel);

		try {
			proveedorRepository.save(proveedorExiste);
			log.info("Actualizar proveedor {} ", proveedorDto);
		} catch (CatalogoException e) {
			throw new CatalogoException(ErroresEnum.ERROR_ACTUALIZAR_PROVEEDOR, e);
		}

		ProveedorDto proveedorActualizadoDto = modelMapper.map(proveedorExiste, ProveedorDto.class);

		proveedorActualizadoDto.setIdGiroEmpresarial(
				Long.parseLong("" + proveedorExiste.getGiroEmpresarialModel().getIdGiroEmpresarial()));

		
		  // pistas auditoria - Modificación
		  StringBuilder builder = new StringBuilder();
		  
		  builder.append(ID_PROVEEDOR).append(proveedorExiste.getIdProveedor())
		  .append(" | ").append("Nombre Proveedor: ")
		  .append(proveedorExiste.getNombreProveedor()).append(" | ")
		  .append("Nombre Comercial: ")
		  .append(proveedorExiste.getNombreComercial()).append(" | ").append("RFC: ")
		  .append(proveedorExiste.getRfc()).append(" | ").append("Dirección: ")
		  .append(proveedorExiste.getDireccion()).append(" | ").append("Comentarios: ")
		  .append(proveedorExiste.getComentarios()).append(" | ").append("Estatus: ")
		  .append(proveedorExiste.getEstatus()).append(" | ").append("Id Ags: ")
		  .append(proveedorExiste.getIdAgs()).append(" | ").append("Giro Empresarial: "
		  )
		  .append(giroEmpresarialModel.getIdGiroEmpresarial());
		  log.info(PISTA_GEN, builder.toString());
		  
		  pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(),
		  TipoMovPista.ACTUALIZA_REGISTRO.getId(),
		  TipoSeccionPista.PROVEEDORES.getIdSeccionPista(), builder.toString(),
		  Optional.empty());
		  
		 

		return proveedorActualizadoDto;
	}

	// filtrado proveedores
	@Transactional
	@Override
	public Page<ProveedoGeneralDto> filtrarProveedores(ProveedorRequestDto filtro) {
		int page = Math.max(filtro.getPage(), 0);
		int size = filtro.getSize();
		Pageable pageable = PageRequest.of(page, size);
		List<Long> ids = proveedorRepository.obtenerIdsProveedores(filtro.getIdGiroEmpresarial(),
				filtro.getIdTituloServicio(), filtro.getIdCatResultadoDictamen());

		if (!ids.isEmpty()) {
			List<TituloServicioProveedorModel> titulos = new ArrayList<>();

			for (Long idProveedor : ids) {
				List<TituloServicioProveedorModel> titulosProveedor = proveedorRepository
						.obtenerTitulosPorProveedor(idProveedor);
				titulosProveedor.forEach(vigenciaScheduler::calcularVigencia);
				titulos.addAll(titulosProveedor);
			}
			tituloServicioProveedorServiceRepository.saveAll(titulos);
		}

		Page<Object[]> resultados = proveedorRepository.obtenerProveedoresConTituloAgrupado(
				filtro.getIdGiroEmpresarial(), filtro.getIdTituloServicio(), filtro.getIdCatResultadoDictamen(),
				pageable);

		List<ProveedoGeneralDto> dtoList = resultados.stream().map(resultado -> {

			Long idProveedor = ((Number) resultado[0]).longValue();
			String nombreProveedor = (String) resultado[1];
			String nombreComercial = (String) resultado[2];
			String idAgs =(String) resultado[3];
			String giroDeLaEmpresa = (String) resultado[4];
			String rfc = (String) resultado[5];
			String representanteLegal = (String) resultado[6];
			String tituloDeServicio = (String) resultado[7];
			String vigencia = (String) resultado[8];
			String colorVigencia = (String) resultado[9];
			Date vencimientoTitulo = (Date) resultado[10];
			Boolean estatus = (Boolean) resultado[11];
			String cumpleDictamen = (String) resultado[12];
			

			registrarPistaProveedor(giroDeLaEmpresa, tituloDeServicio, cumpleDictamen);

			return new ProveedoGeneralDto(idProveedor, nombreProveedor, nombreComercial,idAgs, giroDeLaEmpresa, rfc,
					representanteLegal, tituloDeServicio, vigencia, colorVigencia, vencimientoTitulo, estatus,
					cumpleDictamen);
		}).toList();
		// Retornar la página de DTOs construidos
		return new PageImpl<>(dtoList, pageable, resultados.getTotalElements());
	}

	@Transactional
	private void registrarPistaProveedor(String giroDeLaEmpresa, String tituloDeServicio, String cumpleDictamen) {
		StringBuilder builder = new StringBuilder();
		builder.append("Giro de la empresa: ").append(giroDeLaEmpresa).append(" | ").append("Título de servicio: ")
				.append(tituloDeServicio).append(" | ").append("Cumple dictamen: ").append(cumpleDictamen);

		pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.PROVEEDOR_DATOS_GENERALES.getIdSeccionPista(), builder.toString(), Optional.empty());
	}

	// Se agregan principios de SOLID

	private void validarNombreDuplicado(String nombreProveedor) {
		proveedorRepository.findByNombreProveedor(nombreProveedor).ifPresent(p -> {
			throw new CatalogoException(ErroresEnum.ERROR_NOMBRE_DUPLICADO);
		});

	}

	private void validarIdAgs(String idAgs) {

		if (idAgs == null || idAgs.isEmpty()) {
			return;
		}

		if (!idAgs.matches("^\\d+$")) {
			throw new CatalogoException(ErroresEnum.ERROR_IDAGS_INCOREECTO);
		}

		proveedorRepository.findByIdAgs(idAgs).ifPresent(p -> {
			throw new CatalogoException(ErroresEnum.ERROR_IDAGS_DUPLICADO);

		});

	}

	private List<String> validarRFC(String rfc) {
		if (rfc == null || rfc.isEmpty()) {
			return Collections.emptyList();
		}

		if (rfc.length() < 12 || rfc.length() > 13) {
			throw new CatalogoException(ErroresEnum.ERROR_CARACTERES_RFC);
		}
		List<ProveedorModel> proveedores = proveedorRepository.findAllByRfc(rfc);
		return proveedores.stream()
				.map(ProveedorModel::getNombreProveedor)
				.toList();

	}

	private void validarGiroEmpresarial(Long idGiroEmpresarial) {
		Integer idGiro = Integer.parseInt("" + idGiroEmpresarial);

		if (idGiroEmpresarial == null) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_GIRO_EMPRESARIAL);
		}

		CatGiroEmpresarial lista = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
				CatalogosComunes.PROVEEDOR_GIRO_EMPRESARIAL.getIdCatalogo(), idGiro, new CatGiroEmpresarial());

		if (lista == null) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_GIRO_EMPRESARIAL);
		}

	}

}
