package com.sisecofi.contratos.service.reintegros.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.contratos.dto.reintegros.ReintegrosConsultaDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosModificaDto;
import com.sisecofi.contratos.microservicios.PlantillaMicroRest;
import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaReintegroRepository;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.repository.reintegros.CatTipoReintegroRepository;
import com.sisecofi.contratos.repository.reintegros.ReintegrosRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioContratos;
import com.sisecofi.contratos.service.impl.NexusImpl;
import com.sisecofi.contratos.service.reintegros.ReintegroConsultaService;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.contrato.ConsultaContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoReintegro;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.CarpetaPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ReintegroConsultaServiceImpl implements ReintegroConsultaService {

	private final ServicioContratos servicioContratos;
	private final ReintegrosRepository reintegrosRepository;
	private final CatTipoReintegroRepository catTipoReintegroRepository;
	private final PistaService pistaService;
	private final PlantillaMicroRest plantillaMicroRest;
	private final NexusImpl nexusImpl;
	private final ContratoRepository contratoRepository;
	private final CarpetaPlantillaReintegroRepository carpetaPlantillaReintegroRepository;
	private final SeguridadService seguridadService;
	private static final String PISTA_GEN = "Pista Generada: {}";
	private static final String REGISTRO_CONSULTA_ID_REINTEGRO_ASOCIADO = "Id reintegro asociado: ";
	private static final String REGISTRO_CONSULTA_TIPO = "Tipo: ";
	private static final String REGISTRO_CONSULTA_IMPORTE = "Importe: ";
	private static final String REGISTRO_CONSULTA_INTERES = "Interes: ";
	private static final String REGISTRO_CONSULTA_TOTAL = "Total: ";
	private static final String REGISTRO_CONSULTA_FECHA_REINTEGRO = "Fecha reintegro: ";

	@Override
	public List<ContratoSimpleDto> obtenerContratosVigentes(String vigente) {

		ConsultaContratoDto dto = new ConsultaContratoDto();
		dto.setVigencia(vigente);

		boolean acceso = seguridadService.validarRolAdminSistema()
				|| seguridadService.validarRolAdminSistemaSecundario() || seguridadService.validarRolTodosProyectos();
		dto.setTodos(acceso);
		
			List<ContratoSimpleDto> lista = servicioContratos.obtenerContratosVigencia(dto);
			
			lista.forEach(elemento -> elemento.setEjecucion(elemento.getIdEstatusContrato() == 2));

			return lista;
		
	}
	

	// servicio alta reintegro

	@Transactional
	@Override
	public List<ReintegrosDto> crearReintegros(List<ReintegrosDto> reintegrosDtoList) {
		List<ReintegrosDto> responseList = new ArrayList<>();

		for (ReintegrosDto reintegrosDto : reintegrosDtoList) {

			// Buscar el tipo de reintegro
			Optional<CatTipoReintegro> tipoReintegroOpt = catTipoReintegroRepository
					.findById(reintegrosDto.getIdTipoReintegro());

			if (tipoReintegroOpt.isEmpty()) {
				throw new ContratoException(ErroresEnum.ERROR_BUSCAR_CAT_TIPOREINTEGRO);
			}

			// Crear una instancia del modelo
			ReintegrosAsociadosModel nuevoReintegro = new ReintegrosAsociadosModel();
			nuevoReintegro.setImporte(reintegrosDto.getImporte());
			nuevoReintegro.setInteres(reintegrosDto.getInteres());
			nuevoReintegro.setFechaReintegro(reintegrosDto.getFechaReintegro());
			nuevoReintegro.setTipoReintegro(tipoReintegroOpt.get());
			nuevoReintegro.setAsignado(false);
			if (reintegrosDto.getIdContrato() == null) {
				throw new ContratoException(ErroresEnum.ERROR_CONTRATO_VACIO);
			}

			nuevoReintegro.setIdContrato(reintegrosDto.getIdContrato());

			ContratoModel contratoModel = contratoRepository.findByIdContrato(reintegrosDto.getIdContrato())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_CONTRATO_VACIO));

			nuevoReintegro.setContratoModel(contratoModel);

			// Calcular el valor de ordenContrato para este contrato específico
			Integer maxOrdenContrato = reintegrosRepository
					.findMaxOrdenContratoByIdContrato(reintegrosDto.getIdContrato());
			nuevoReintegro.setOrdenContrato(maxOrdenContrato + 1);

			// Calcular total
			BigDecimal total = reintegrosDto.getImporte().add(reintegrosDto.getInteres());
			nuevoReintegro.setTotal(total);

			// Guardar el reintegro
			ReintegrosAsociadosModel reintegroGuardado = reintegrosRepository.save(nuevoReintegro);

			crearArchivos(nuevoReintegro);
			reintegrosRepository.save(nuevoReintegro);

			// lista
			ReintegrosDto responseDto = new ReintegrosDto();
			responseDto.setOrdenContrato(reintegroGuardado.getOrdenContrato());
			responseDto.setIdReintegrosAsociados(reintegroGuardado.getIdReintegrosAsociados());
			responseDto.setIdContrato(reintegroGuardado.getIdContrato());
			responseDto.setIdTipoReintegro(reintegroGuardado.getTipoReintegro().getIdTipoReintegro());
			responseDto.setNombreTipoReintegro(reintegroGuardado.getTipoReintegro().getNombre());
			responseDto.setImporte(reintegroGuardado.getImporte());
			responseDto.setInteres(reintegroGuardado.getInteres());
			responseDto.setTotal(reintegroGuardado.getTotal());
			responseDto.setFechaReintegro(reintegroGuardado.getFechaReintegro());

			responseList.add(responseDto);

			// pistas auditoria -Insertar
			StringBuilder builder = new StringBuilder();

			builder.append("Nombre corto contrato: ").append(nuevoReintegro.getContratoModel().getNombreCorto())
					.append(" | ").append(REGISTRO_CONSULTA_ID_REINTEGRO_ASOCIADO)
					.append(nuevoReintegro.getIdReintegrosAsociados()).append(" | ").append(REGISTRO_CONSULTA_TIPO)
					.append(nuevoReintegro.getTipoReintegro().getNombre()).append(" | ")
					.append(REGISTRO_CONSULTA_IMPORTE).append(nuevoReintegro.getImporte()).append(" | ")
					.append(REGISTRO_CONSULTA_INTERES).append(nuevoReintegro.getInteres()).append(" | ")
					.append(REGISTRO_CONSULTA_TOTAL).append(nuevoReintegro.getTotal()).append(" | ")
					.append(REGISTRO_CONSULTA_FECHA_REINTEGRO).append(nuevoReintegro.getFechaReintegro()).append(" |");
			log.info(PISTA_GEN, builder.toString());

			pistaService.guardarPista(ModuloPista.REINTEGRO.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
					TipoSeccionPista.REINTEGROS.getIdSeccionPista(), builder.toString(), Optional.empty());

		}

		return responseList;
	}

	// modificacion con descartar cambio

	@Transactional
	@Override
	public List<ReintegrosModificaDto> modificarReintegros(List<ReintegrosModificaDto> reintegrosDtoList) {
		List<ReintegrosModificaDto> responseList = new ArrayList<>();

		for (ReintegrosModificaDto reintegrosDto : reintegrosDtoList) {

			ReintegrosAsociadosModel reintegroExiste = reintegrosRepository
					.findByIdReintegrosAsociadosAndEstatusTrue(reintegrosDto.getIdReintegrosAsociados())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));

			CarpetaPlantillaReintegroModel carpetaGestion = reintegroExiste.getCarpetaGestion();

			CatTipoReintegro tipoReintegro = catTipoReintegroRepository.findById(reintegrosDto.getIdTipoReintegro())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));
			LocalDateTime fechaReintegroOriginal = reintegroExiste.getFechaReintegro();
			reintegroExiste.setTipoReintegro(tipoReintegro);
			reintegroExiste.setImporte(reintegrosDto.getImporte());
			reintegroExiste.setInteres(reintegrosDto.getInteres());

			reintegroExiste.setFechaReintegro(reintegrosDto.getFechaReintegro());

			// Calcular el total
			BigDecimal total = reintegrosDto.getImporte().add(reintegrosDto.getInteres());
			reintegroExiste.setTotal(total);

			ReintegrosAsociadosModel reintegroModificado = reintegrosRepository.save(reintegroExiste);

			if (fechaReintegroOriginal != reintegrosDto.getFechaReintegro()
					&& Boolean.TRUE.equals(reintegroExiste.getAsignado()) && carpetaGestion != null) {
					carpetaGestion.setRenombrar();
					carpetaPlantillaReintegroRepository.save(carpetaGestion);
				}
			

			ReintegrosModificaDto responseDto = mapeaarReintegrosDto(reintegroModificado);
			responseList.add(responseDto);

			// pistas auditoria -Insertar
			StringBuilder builder = new StringBuilder();

			builder.append("Nombre corto del contrato:  ").append(reintegroExiste.getContratoModel().getNombreCorto())
					.append(" | ").append(REGISTRO_CONSULTA_ID_REINTEGRO_ASOCIADO)
					.append(reintegroExiste.getIdReintegrosAsociados()).append(" | ").append(REGISTRO_CONSULTA_TIPO)
					.append(reintegroExiste.getTipoReintegro().getNombre()).append(" | ")
					.append(REGISTRO_CONSULTA_IMPORTE).append(reintegroExiste.getImporte()).append(" | ")
					.append(REGISTRO_CONSULTA_INTERES).append(reintegroExiste.getInteres()).append(" | ")
					.append(REGISTRO_CONSULTA_TOTAL).append(reintegroExiste.getTotal()).append(" | ")
					.append(REGISTRO_CONSULTA_FECHA_REINTEGRO).append(reintegroExiste.getFechaReintegro()).append(" |");
			log.info(PISTA_GEN, builder.toString());

			pistaService.guardarPista(ModuloPista.REINTEGRO.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
					TipoSeccionPista.REINTEGROS.getIdSeccionPista(), builder.toString(), Optional.empty());

		}

		return responseList;
	}

	// metodo auxiliar para mapear de entidad a DTO

	private ReintegrosModificaDto mapeaarReintegrosDto(ReintegrosAsociadosModel reintegrosModel) {
		ReintegrosModificaDto dto = new ReintegrosModificaDto();
		dto.setIdTipoReintegro(reintegrosModel.getTipoReintegro().getIdTipoReintegro());
		dto.setNombreTipoReintegro(reintegrosModel.getTipoReintegro().getNombre());
		dto.setIdReintegrosAsociados(reintegrosModel.getIdReintegrosAsociados());
		dto.setImporte(reintegrosModel.getImporte());
		dto.setInteres(reintegrosModel.getInteres());
		dto.setTotal(reintegrosModel.getTotal());
		dto.setFechaReintegro(reintegrosModel.getFechaReintegro());

		return dto;
	}

	// soft delete reintegros

	@Transactional
	@Override
	public void eliminarReintegros(List<Long> idReintegrosAsociadosList) {

		for (Long idReintegrosAsociados : idReintegrosAsociadosList) {

			// Buscar el reintegro
			ReintegrosAsociadosModel reintegro = reintegrosRepository
					.findByIdReintegrosAsociadosAndEstatusTrue(idReintegrosAsociados)
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));

			Long idContrato = reintegro.getIdContrato();

			// Marcar el reintegro como inactivo
			reintegro.setEstatus(false);
			reintegrosRepository.save(reintegro);

			// Reenumerar los reintegros
			List<ReintegrosAsociadosModel> reintegrosRestantes = reintegrosRepository
					.findByContratoModelIdContrato(idContrato);

			// Actualizar el ordenContrato para los reintegros restantes
			int nuevoOrden = 1;
			for (ReintegrosAsociadosModel reintegroRestante : reintegrosRestantes) {
				reintegroRestante.setOrdenContrato(nuevoOrden++);
				reintegrosRepository.save(reintegroRestante);
			}

			// pistas auditoria -Insertar
			StringBuilder builder = new StringBuilder();

			builder.append("Nombre corto del contrato:  ").append(reintegro.getContratoModel().getNombreCorto())
					.append(" | ").append(REGISTRO_CONSULTA_ID_REINTEGRO_ASOCIADO)
					.append(reintegro.getIdReintegrosAsociados()).append(" | ").append(REGISTRO_CONSULTA_TIPO)
					.append(reintegro.getTipoReintegro().getNombre()).append(" | ").append(REGISTRO_CONSULTA_IMPORTE)
					.append(reintegro.getImporte()).append(" | ").append(REGISTRO_CONSULTA_INTERES)
					.append(reintegro.getInteres()).append(" | ").append(REGISTRO_CONSULTA_TOTAL)
					.append(reintegro.getTotal()).append(" | ").append(REGISTRO_CONSULTA_FECHA_REINTEGRO)
					.append(reintegro.getFechaReintegro()).append(" |");
			log.info(PISTA_GEN, builder.toString());

			pistaService.guardarPista(ModuloPista.REINTEGRO.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
					TipoSeccionPista.REINTEGROS.getIdSeccionPista(), builder.toString(), Optional.empty());

		}
	}

	@Override
	public List<ReintegrosConsultaDto> buscarReintegrosPorContratos(Long idContrato) {
		List<ReintegrosAsociadosModel> rintegros = reintegrosRepository.findReintegrosByIdContrato(idContrato);
		rintegros.sort(Comparator.comparing(
				reintegro -> reintegro.getOrdenContrato() != null ? reintegro.getOrdenContrato() : Integer.MAX_VALUE));

		int orden = 1;
		for (ReintegrosAsociadosModel reintegro : rintegros) {
			crearArchivos(reintegro);

			Long idReintegrosAsociados = reintegro.getIdReintegrosAsociados();
			String catTipoReintegro = reintegro.getTipoReintegro().getNombre();
			String nombreCorto = reintegro.getContratoModel().getNombreCorto();
			BigDecimal importe = reintegro.getImporte();
			BigDecimal interes = reintegro.getInteres();
			BigDecimal total = reintegro.getTotal();
			LocalDateTime fechaReintegro = reintegro.getFechaReintegro();

			reintegro.setOrdenContrato(orden);
			orden++;
			reintegrosRepository.save(reintegro);
			registrarPistaReintegro(idReintegrosAsociados, catTipoReintegro, nombreCorto, importe, interes, total,
					fechaReintegro);

		}

		if (rintegros.isEmpty()) {
			throw new ContratoException(ErroresEnum.ERROR_CONTRATO_SIN_REINTEGRO);
		}

		// Pista auditoria- Consulta

		return rintegros.stream().map(this::mapeaarReintegrosConsultaDto).toList();

	}

	private void registrarPistaReintegro(Long idReintegrosAsociados, String catTipoReintegro, String nombreCorto,
			BigDecimal importe, BigDecimal interes, BigDecimal total, LocalDateTime fechaReintegro) {
		StringBuilder builder = new StringBuilder();
		builder.append("Nombre corto contrato: ").append(nombreCorto).append(" | ")
				.append(REGISTRO_CONSULTA_ID_REINTEGRO_ASOCIADO).append(idReintegrosAsociados).append(" | ")
				.append("Tipo Reintegros: ").append(catTipoReintegro).append(" | ").append(REGISTRO_CONSULTA_IMPORTE)
				.append(importe).append(" | ").append(REGISTRO_CONSULTA_INTERES).append(interes).append(" | ")
				.append(REGISTRO_CONSULTA_TOTAL).append(total).append(" | ").append(REGISTRO_CONSULTA_FECHA_REINTEGRO)
				.append(fechaReintegro).append(" | ");

		pistaService.guardarPista(ModuloPista.REINTEGRO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.REINTEGROS.getIdSeccionPista(), builder.toString(), Optional.empty());
	}

	private ReintegrosConsultaDto mapeaarReintegrosConsultaDto(ReintegrosAsociadosModel reintegrosModel) {
		ReintegrosConsultaDto dto = new ReintegrosConsultaDto();
		dto.setOrdenContrato(reintegrosModel.getOrdenContrato());
		dto.setIdTipoReintegro(reintegrosModel.getTipoReintegro().getIdTipoReintegro());
		dto.setIdReintegrosAsociados(reintegrosModel.getIdReintegrosAsociados());
		dto.setNombreTipoReintegro(reintegrosModel.getTipoReintegro().getNombre());
		dto.setImporte(reintegrosModel.getImporte());
		dto.setInteres(reintegrosModel.getInteres());
		dto.setTotal(reintegrosModel.getTotal());
		dto.setFechaReintegro(reintegrosModel.getFechaReintegro());
		dto.setEstatus(reintegrosModel.getEstatus());

		if (reintegrosModel.getContratoModel() != null && reintegrosModel.getContratoModel().getNombreCorto() != null) {
			dto.setNombreCorto(reintegrosModel.getContratoModel().getNombreCorto());

		}

		return dto;
	}

	public String movimientosReintegros(ReintegrosAsociadosModel reintegrosModel) {
		Long idReintegrosAsociados = reintegrosModel.getIdReintegrosAsociados();

		return new StringBuilder().append("Id Reintegros Asociados: ").append(idReintegrosAsociados).toString();

	}

	@Override
	public void crearArchivos(ReintegrosAsociadosModel reintegroGuardado) {
		try {
			CarpetaPlantillaModel car = plantillaMicroRest.obtenerArchivosReintegros();
			if (car != null && !reintegroGuardado.getAsignado()) {
				reintegroGuardado.setAsignado(true);
				CarpetaPlantillaReintegroModel carpeta = new CarpetaPlantillaReintegroModel(car, reintegroGuardado);

				String nombreBaseCarpeta = "Reintegro_"
						+ reintegroGuardado.getFechaReintegro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				String nombreCarpeta = nombreBaseCarpeta;
				int contador = 1;

				while (carpetaPlantillaReintegroRepository.existsByDescripcion(nombreCarpeta)) {
					contador++;
					nombreCarpeta = nombreBaseCarpeta + "_" + contador;
				}

				carpeta.setNombre(nombreCarpeta);
				carpeta.setDescripcion(nombreCarpeta);

				String rutaActual = generarRuta(reintegroGuardado.getIdContrato(), carpeta.getNombre());
				carpeta.setRuta(rutaActual);

				nexusImpl.generarCarpetasVacias(rutaActual + "/pb");

				for (ArchivoPlantillaModel arc : car.getArchivos()) {
					carpeta.addArchivo(crearArchivo(arc, rutaActual));
				}
				carpetaPlantillaReintegroRepository.save(carpeta);
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ASIGNAR_PLANTILLA, e);
		}

	}

	private ArchivoPlantillaReintegroModel crearArchivo(ArchivoPlantillaModel arc, String carpeta) {
		ArchivoPlantillaReintegroModel archivo = new ArchivoPlantillaReintegroModel();
		archivo.setDescripcion(arc.getDescripcion());
		archivo.setEstatus(arc.isEstatus());
		archivo.setArchivoBase(arc);
		archivo.setNombre(arc.getNombre());
		archivo.setNivel(arc.getNivel());
		archivo.setNoAplica(false);
		archivo.setTipo(arc.getTipo());
		archivo.setObligatorio(arc.isObligatorio());
		archivo.setCarpeta(carpeta);
		return archivo;
	}

	@Override
	public String generarRuta(Long idContrato, String carpeta) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		String nombreCloud = carpeta.replace("/", "-");
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_REINTEGROS + "/" + nombreCloud;
	}

	@Override
	public String generarRutaFase(Long idContrato, String carpeta) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		String nombreCloud = carpeta.replace("/", "-");
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_REINTEGROS + "/" + nombreCloud + "/Otros Documentos";
	}

	@Override
	public String generarRutaBase(Long idContrato) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_REINTEGROS + "/Otros Documentos";
	}

	@Override
	public String generarRutaCloud(Long idContrato) {
		ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_BUSCAR_REINTEGRO));
		Long idProyecto = contrato.getProyecto().getIdProyecto();
		return ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_REINTEGROS;
	}

}
