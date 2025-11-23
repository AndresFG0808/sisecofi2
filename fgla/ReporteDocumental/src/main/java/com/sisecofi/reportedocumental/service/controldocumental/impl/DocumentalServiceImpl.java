package com.sisecofi.reportedocumental.service.controldocumental.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoDto;
import com.sisecofi.libreria.comunes.model.usuario.RolModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.dinamico.DinamicoRepository;
import com.sisecofi.libreria.comunes.service.NexusService;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.PistaUtil;
import com.sisecofi.libreria.comunes.util.RolesConstantes;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.enums.TypeObject;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaFaseDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaFaseDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaFaseDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaNoDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaNoDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.BusquedaNoDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.PageDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.PageDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.ProyectoDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.comite.BusquedaComiteDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.comite.BusquedaComiteDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.comite.BusquedaComiteDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.comite.BusquedaComiteOtroDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.comite.BusquedaComiteOtroDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.comite.BusquedaComiteOtroDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoFaseDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoFaseDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoFaseDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoOtroDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoOtroDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.contrato.BusquedaContratoOtroDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioFaseDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioFaseDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioFaseDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioOtroDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioOtroDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.convenio.BusquedaConvenioOtroDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenFaseDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenFaseDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenFaseDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenOtroDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenOtroDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.dictamen.BusquedaDictamenOtroDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.facturas.BusquedaFactura;
import com.sisecofi.reportedocumental.dto.controldocumental.facturas.BusquedaFacturaData;
import com.sisecofi.reportedocumental.dto.controldocumental.facturas.BusquedaFacturaDto;
import com.sisecofi.reportedocumental.dto.controldocumental.notascredito.BusquedaNotas;
import com.sisecofi.reportedocumental.dto.controldocumental.notascredito.BusquedaNotasData;
import com.sisecofi.reportedocumental.dto.controldocumental.notascredito.BusquedaNotasDto;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroFaseDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroFaseDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroFaseDocumentalDto;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroOtroDocumental;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroOtroDocumentalData;
import com.sisecofi.reportedocumental.dto.controldocumental.reintegros.BusquedaReintegroOtroDocumentalDto;
import com.sisecofi.reportedocumental.microservicio.MatrizProyectoMicroservicio;
import com.sisecofi.reportedocumental.microservicio.MatrizContratoMicroservicio;
import com.sisecofi.reportedocumental.microservicio.MatrizDictamenMicroservicio;
import com.sisecofi.reportedocumental.repository.controldoc.ControlDocumentalRepository;
import com.sisecofi.reportedocumental.repository.controldoc.UsuarioProyectoRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.controldocumental.DocumentalService;
import com.sisecofi.reportedocumental.util.MatrizCriteriaService;
import com.sisecofi.reportedocumental.util.UtilZip;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.PistaException;
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
public class DocumentalServiceImpl implements DocumentalService {

	private static final String IN = " IN(VALORES)";
	private static final String AND = " AND ";
	private final SeguridadService seguridadService;
	private final Object lockNombre = new Object();
	private final Object lockArchivosInputStream = new Object();

	private final DinamicoRepository dinamicoRepository;
	private final ReportExportGenericService exportGenericService;
	private final NexusService nexusService;
	private final UsuarioProyectoRepository usuarioProyectoRepository;
	private final Session session;
	private final PistaService microservicio;
	private final ControlDocumentalRepository controlDocumentalRepository;
	private final MatrizProyectoMicroservicio matrizProyectoMicroservicio;
	private final MatrizContratoMicroservicio matrizContratoMicroservicio;
	private final MatrizDictamenMicroservicio matrizDictamenMicroservicio;
	private final MatrizCriteriaService matrizCriteriaService;
	
	

	@Qualifier("mapperControlDocumental")
	private final MapperControlDocumental controlDocumental;

	@Override
	public PageDocumental buscarControlDocumental(BusquedaDocumentalDto busquedaDocumentalDto) {
		try {

			validarProyectosAsignados(busquedaDocumentalDto);

			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaDocumentalDto);
			log.info("Total de pages merge: {}", pageMerge.getPage().getContent().size());

			PageDocumental pageDocumental = new PageDocumental(pageMerge.getPage());

			pageDocumental.setDocumentosCargados(pageDocumental.getContent().stream()
					.filter(d -> d.isCargado() && !d.isNoAplica()).toList().size());
			pageDocumental.setDocumentosQueNoAplican(pageDocumental.getContent().stream()
					.filter(ProyectoDocumental::isNoAplica).toList().size());
			pageDocumental.setDocumentosPendientes(pageDocumental.getContent().stream()
					.filter(d -> !d.isCargado() && !d.isNoAplica()).toList().size());					
			pageDocumental.setDocumentosRequeridos(
				pageDocumental.getContent().stream()
				.filter(d-> "si".equalsIgnoreCase(d.getRequerido()))
				.toList()
				.size());
				

		
			
			pageDocumental.setEtiquetas(pageMerge.getEtiquetas());

			if (pageDocumental.getContent().isEmpty()) {
				throw new ReporteException(ErroresEnum.ERROR_NO_RESULT);
			}

			 String s = PistaUtil.cadenaPistasObjecto(busquedaDocumentalDto);
			 microservicio.guardarPista(ModuloPista.REPORTE_CONTROL_DOCUMENTAL.getId(),
			 		TipoMovPista.CONSULTA_REGISTRO.getId(),
			 		TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(), s);

			return pageDocumental;
		} catch (ReporteException | PistaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	private void validarProyectosAsignados(BusquedaDocumentalDto busquedaDocumentalDto) {
		Optional<Usuario> op = session.retornarUsuario();
		if (op.isPresent()) {
			RolModel admin = new RolModel();
			admin.setNombre(RolesConstantes.ROL_ADMINISTRADOR_SISTEMA);
			RolModel subadmin = new RolModel();
			subadmin.setNombre(RolesConstantes.ROL_ADMINISTRADOR_SISTEMA_SECUNDARIO);
			RolModel todosProyectos = new RolModel();
			todosProyectos.setNombre(RolesConstantes.ROL_TODOS_LOS_PROYECTOS);
			if (!op.get().getRolModels().contains(admin) && !op.get().getRolModels().contains(subadmin)
					&& !op.get().getRolModels().contains(todosProyectos)) {
				List<ProyectoDto> pr;
				if (busquedaDocumentalDto.getIdEstatusProyecto() != null
						&& !busquedaDocumentalDto.getIdEstatusProyecto().isEmpty()) {
					pr = usuarioProyectoRepository.findProyectosConEstatusTrueByIdUserAndEstatusProyecto(
							op.get().getIdUser(), busquedaDocumentalDto.getIdEstatusProyecto());
				} else {
					pr = usuarioProyectoRepository.findProyectosConEstatusTrueAndByIdUser(op.get().getIdUser());
				}
				log.info("Proyectos que corresponde al usuario: {} ids: {}", op.get().getRfcCorto(),
						pr.stream().map(ProyectoDto::getIdProyecto).toList());
				if (pr.isEmpty()) {
					throw new ReporteException(ErroresEnum.ERROR_SIN_PROYECTOS);
				}
				busquedaDocumentalDto
						.setIdProyecto(pr.stream().map(ProyectoDto::getIdProyecto).toList());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private PageDocumentalDto<Object[]> generaPage(BusquedaDocumentalDto busquedaDocumentalDto) {
		long inicio = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(10); // Ajusta el número de hilos según sea
																			// necesario
		List<Future<PageGeneric>> futures = new ArrayList<>();
		List<Page> pages = new ArrayList<>();

		try {
			// Crear una lista de tareas asíncronas para cada consulta
			futures.add(executorService.submit(() -> {
				// Identificador 1 -> si tiene plantilla_vigente ->
				// sscft_archivo_plantilla_proyecto
				log.info("Identificador 1");
				busquedaDocumentalDto.setDataReporteDto(new BusquedaDocumental(new BusquedaDocumentalData()));
				return dinamicoRepository.generarData(busquedaDocumentalDto, generarWhere1(busquedaDocumentalDto),
						false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 2 -> no tiene fase -> sscft_archivo_otro_documento_proyecto
				log.info("Identificador 2");
				BusquedaNoDocumentalDto bus = new BusquedaNoDocumentalDto();
				bus.setPageNoDocumental(busquedaDocumentalDto.getPage());
				bus.setSizeNoDocumental(busquedaDocumentalDto.getSize());
				bus.setBusquedaNoDocumental(new BusquedaNoDocumental(new BusquedaNoDocumentalData()));
				return dinamicoRepository.generarData(bus, generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 3 -> no tiene fase -> sscft_archivo_otro_documento_fase
				log.info("Identificador 3");
				BusquedaFaseDocumentalDto fase = new BusquedaFaseDocumentalDto();
				fase.setPageFaseDocumental(busquedaDocumentalDto.getPage());
				fase.setSizeFaseDocumental(busquedaDocumentalDto.getSize());
				fase.setBusquedaFaseDocumental(new BusquedaFaseDocumental(new BusquedaFaseDocumentalData()));
				return dinamicoRepository.generarData(fase, generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 4 -> si tiene plantilla_vigente ->
				// sscft_archivo_plantilla_comite
				log.info("Identificador 4");
				BusquedaComiteDocumentalDto busquedaComiteDocumentalDto = new BusquedaComiteDocumentalDto();
				busquedaComiteDocumentalDto.setPageComite(busquedaDocumentalDto.getPage());
				busquedaComiteDocumentalDto.setSizeComite(busquedaDocumentalDto.getSize());
				busquedaComiteDocumentalDto
						.setBusquedaComiteDocumental(new BusquedaComiteDocumental(new BusquedaComiteDocumentalData()));
				return dinamicoRepository.generarData(busquedaComiteDocumentalDto, generarWhere1(busquedaDocumentalDto),
						false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 5 -> no tiene fase -> sscft_archivo_otros_documentos_comite
				log.info("Identificador 5");
				BusquedaComiteOtroDocumentalDto busquedaComiteNoDocumentalDto = new BusquedaComiteOtroDocumentalDto();
				busquedaComiteNoDocumentalDto.setPageComiteOtro(busquedaDocumentalDto.getPage());
				busquedaComiteNoDocumentalDto.setSizeComiteOtro(busquedaDocumentalDto.getSize());
				busquedaComiteNoDocumentalDto.setBusquedaComiteNoDocumental(
						new BusquedaComiteOtroDocumental(new BusquedaComiteOtroDocumentalData()));
				return dinamicoRepository.generarData(busquedaComiteNoDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 6 -> si tiene plantilla_vigente ->
				// sscft_archivo_plantilla_contrato
				log.info("Identificador 6");
				BusquedaContratoDocumentalDto busquedaContratoDocumentalDto = new BusquedaContratoDocumentalDto();
				busquedaContratoDocumentalDto.setPageContrato(busquedaDocumentalDto.getPage());
				busquedaContratoDocumentalDto.setSizeContrato(busquedaDocumentalDto.getSize());
				busquedaContratoDocumentalDto
						.setCotratoDocumental(new BusquedaContratoDocumental(new BusquedaContratoDocumentalData()));
				return dinamicoRepository.generarData(busquedaContratoDocumentalDto,
						generarWhere1(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 7 -> no tiene fase ->
				// sscft_archivo_otro_documento_fase_contrato
				log.info("Identificador 7");
				BusquedaContratoFaseDocumentalDto busquedaContratoFaseDocumentalDto = new BusquedaContratoFaseDocumentalDto();
				busquedaContratoFaseDocumentalDto.setPageContratoFase(busquedaDocumentalDto.getPage());
				busquedaContratoFaseDocumentalDto.setSizeContratoFase(busquedaDocumentalDto.getSize());
				busquedaContratoFaseDocumentalDto.setBusquedaContratoFaseDocumental(
						new BusquedaContratoFaseDocumental(new BusquedaContratoFaseDocumentalData()));
				return dinamicoRepository.generarData(busquedaContratoFaseDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 8 -> no tiene fase ->
				// sscft_archivo_otro_documento_fase_contrato
				log.info("Identificador 8");
				BusquedaContratoOtroDocumentalDto busquedaContratoOtroDocumentalDto = new BusquedaContratoOtroDocumentalDto();
				busquedaContratoOtroDocumentalDto.setPageContratoOtro(busquedaDocumentalDto.getPage());
				busquedaContratoOtroDocumentalDto.setSizeContratoOtro(busquedaDocumentalDto.getSize());
				busquedaContratoOtroDocumentalDto.setBusquedaContratoOtroDocumental(
						new BusquedaContratoOtroDocumental(new BusquedaContratoOtroDocumentalData()));
				return dinamicoRepository.generarData(busquedaContratoOtroDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 9 -> si tiene plantilla_vigente ->
				// sscft_archivo_plantilla_convenio
				log.info("Identificador 9");
				BusquedaConvenioDocumentalDto busquedaConvenioDocumentalDto = new BusquedaConvenioDocumentalDto();
				busquedaConvenioDocumentalDto.setPageConvenio(busquedaDocumentalDto.getPage());
				busquedaConvenioDocumentalDto.setSizeConvenio(busquedaDocumentalDto.getSize());
				busquedaConvenioDocumentalDto
						.setConvenioDocumental(new BusquedaConvenioDocumental(new BusquedaConvenioDocumentalData()));
				return dinamicoRepository.generarData(busquedaConvenioDocumentalDto,
						generarWhere1(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 10 -> no tiene fase ->
				// sscft_archivo_otro_documento_fase_convenio
				log.info("Identificador 10");
				BusquedaConvenioFaseDocumentalDto busquedaConvenioFaseDocumentalDto = new BusquedaConvenioFaseDocumentalDto();
				busquedaConvenioFaseDocumentalDto.setPageConvenioFase(busquedaDocumentalDto.getPage());
				busquedaConvenioFaseDocumentalDto.setSizeConvenioFase(busquedaDocumentalDto.getSize());
				busquedaConvenioFaseDocumentalDto.setConvenioFaseDocumental(
						new BusquedaConvenioFaseDocumental(new BusquedaConvenioFaseDocumentalData()));
				return dinamicoRepository.generarData(busquedaConvenioFaseDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 11 -> no tiene fase -> sscft_archivo_otro_documento_convenio
				log.info("Identificador 11");
				BusquedaConvenioOtroDocumentalDto busquedaConvenioOtroDocumentalDto = new BusquedaConvenioOtroDocumentalDto();
				busquedaConvenioOtroDocumentalDto.setPageConvenioOtro(busquedaDocumentalDto.getPage());
				busquedaConvenioOtroDocumentalDto.setSizeConvenioOtro(busquedaDocumentalDto.getSize());
				busquedaConvenioOtroDocumentalDto.setBusquedaConvenioOtroDocumental(
						new BusquedaConvenioOtroDocumental(new BusquedaConvenioOtroDocumentalData()));
				return dinamicoRepository.generarData(busquedaConvenioOtroDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 12 -> si tiene plantilla_vigente ->
				// sscft_archivo_plantilla_reintegro
				log.info("Identificador 12");
				BusquedaReintegroDocumentalDto busquedaReintegroDocumentalDto = new BusquedaReintegroDocumentalDto();
				busquedaReintegroDocumentalDto.setPageReintegro(busquedaDocumentalDto.getPage());
				busquedaReintegroDocumentalDto.setSizeReintegro(busquedaDocumentalDto.getSize());
				busquedaReintegroDocumentalDto.setBusquedaReintegroDocumental(
						new BusquedaReintegroDocumental(new BusquedaReintegroDocumentalData()));
				return dinamicoRepository.generarData(busquedaReintegroDocumentalDto,
						generarWhere1(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 13 -> no tiene fase ->
				// sscft_archivo_otro_documento_fase_reintegro
				log.info("Identificador 13");
				BusquedaReintegroFaseDocumentalDto busquedaReintegroFaseDocumentalDto = new BusquedaReintegroFaseDocumentalDto();
				busquedaReintegroFaseDocumentalDto.setPageReintegroFase(busquedaDocumentalDto.getPage());
				busquedaReintegroFaseDocumentalDto.setSizeReintegroFase(busquedaDocumentalDto.getSize());
				busquedaReintegroFaseDocumentalDto.setBusquedaReintegroFaseDocumental(
						new BusquedaReintegroFaseDocumental(new BusquedaReintegroFaseDocumentalData()));
				return dinamicoRepository.generarData(busquedaReintegroFaseDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 14 -> no tiene fase -> sscft_archivo_otro_documento_reintegro
				log.info("Identificador 14");
				BusquedaReintegroOtroDocumentalDto busquedaReintegroOtroDocumentalDto = new BusquedaReintegroOtroDocumentalDto();
				busquedaReintegroOtroDocumentalDto.setPageReintegroOtro(busquedaDocumentalDto.getPage());
				busquedaReintegroOtroDocumentalDto.setSizeReintegroOtro(busquedaDocumentalDto.getSize());
				busquedaReintegroOtroDocumentalDto.setBusquedaReintegroOtroDocumental(
						new BusquedaReintegroOtroDocumental(new BusquedaReintegroOtroDocumentalData()));
				return dinamicoRepository.generarData(busquedaReintegroOtroDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 15 -> si tiene plantilla -> sscft_archivo_plantilla_dictamen
				log.info("Identificador 15");
				BusquedaDictamenDocumentalDto busquedaDictamenDocumentalDto = new BusquedaDictamenDocumentalDto();
				busquedaDictamenDocumentalDto.setPageDictamen(busquedaDocumentalDto.getPage());
				busquedaDictamenDocumentalDto.setSizeDictamen(busquedaDocumentalDto.getSize());
				busquedaDictamenDocumentalDto.setBusquedaDictamenDocumental(
						new BusquedaDictamenDocumental(new BusquedaDictamenDocumentalData()));
				return dinamicoRepository.generarData(busquedaDictamenDocumentalDto,
						generarWhere1(busquedaDocumentalDto), false);
			}));

			futures.add(executorService.submit(() -> {
				// Identificador 16 -> no tiene fase ->
				// sscft_archivo_otro_documento_fase_dictamen
				log.info("Identificador 16");
				BusquedaDictamenFaseDocumentalDto busquedaDictamenFaseDocumentalDto = new BusquedaDictamenFaseDocumentalDto();
				busquedaDictamenFaseDocumentalDto.setPageDictamenFase(busquedaDocumentalDto.getPage());
				busquedaDictamenFaseDocumentalDto.setSizeDictamenFase(busquedaDocumentalDto.getSize());
				busquedaDictamenFaseDocumentalDto.setBusquedaDictamenFaseDocumental(
						new BusquedaDictamenFaseDocumental(new BusquedaDictamenFaseDocumentalData()));
				return dinamicoRepository.generarData(busquedaDictamenFaseDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));
			futures.add(executorService.submit(() -> {
				// Identificador 17 -> no tiene fase -> sscft_archivo_otro_documento_dictamen
				log.info("Identificador 17");
				BusquedaDictamenOtroDocumentalDto busquedaDictamenOtroDocumentalDto = new BusquedaDictamenOtroDocumentalDto();
				busquedaDictamenOtroDocumentalDto.setPageDictamenOtro(busquedaDocumentalDto.getPage());
				busquedaDictamenOtroDocumentalDto.setSizeDictamenOtro(busquedaDocumentalDto.getSize());
				busquedaDictamenOtroDocumentalDto.setBusquedaDictamenOtroDocumental(
						new BusquedaDictamenOtroDocumental(new BusquedaDictamenOtroDocumentalData()));
				return dinamicoRepository.generarData(busquedaDictamenOtroDocumentalDto,
						generarWhere2(busquedaDocumentalDto), false);
			}));
			futures.add(executorService.submit(() -> {
				// Identificador 18 -> no tiene fase -> sscft_archivo_plantilla_dictamen
				log.info("Identificador 18");
				BusquedaFacturaDto busquedaFacturaDto = new BusquedaFacturaDto();
				busquedaFacturaDto.setPageFactura(busquedaDocumentalDto.getPage());
				busquedaFacturaDto.setSizeFactura(busquedaDocumentalDto.getSize());
				busquedaFacturaDto.setBusquedaFactura(new BusquedaFactura(new BusquedaFacturaData()));
				return dinamicoRepository.generarData(busquedaFacturaDto, generarWhere2(busquedaDocumentalDto), false);
			}));
			futures.add(executorService.submit(() -> {
				// Identificador 19 -> no tiene fase -> sscft_archivo_plantilla_dictamen
				log.info("Identificador 19");
				BusquedaNotasDto busquedaNotasDto = new BusquedaNotasDto();
				busquedaNotasDto.setPageNotas(busquedaDocumentalDto.getPage());
				busquedaNotasDto.setSizeNotas(busquedaDocumentalDto.getSize());
				busquedaNotasDto.setBusquedaNotas(new BusquedaNotas(new BusquedaNotasData()));
				return dinamicoRepository.generarData(busquedaNotasDto, generarWhere2(busquedaDocumentalDto), false);
			}));

			// Esperar que todas las tareas terminen y recolectar los resultados
			for (Future<PageGeneric> future : futures) {
				pages.add(future.get()); // Espera a que cada tarea se complete y agrega la página al resultado
			}

			// Combinamos todas las páginas en una sola
			Page<Object[]> pageFinal = mergePages(pages);
			PageDocumentalDto<Object[]> dto = new PageDocumentalDto<>();
			dto.setPage(pageFinal);
			dto.setEtiquetas(((PageGeneric) pages.get(0)).getEtiquetas());
			long fin = System.currentTimeMillis();
			log.info("Tiempo de procesamiento: {}mls", (fin - inicio));
			
			return dto;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		} finally {
		    executorService.shutdown();
		    try {
		        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
		            executorService.shutdownNow();
		        }
		    } catch (InterruptedException ie) {
		        executorService.shutdownNow();
		        Thread.currentThread().interrupt();
		    }
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Page<T> mergePages(List<Page> pages) {
		List<T> allElements = new ArrayList<>();
		for (Page<T> page : pages) {
			allElements.addAll(page.getContent());
		}
		Pageable pageable = pages.isEmpty() ? Pageable.unpaged() : pages.get(0).getPageable();// FALSO POSITIVO DE
																								// FORTIFY
		return new PageImpl<>(allElements, pageable, allElements.size());
	}

	public static <T> Page<T> mergePages(Page<T> page1, Page<T> page2, Page<T> page3) {
		List<T> combinedContent = new ArrayList<>();
		combinedContent.addAll(page1.getContent());
		combinedContent.addAll(page2.getContent());
		combinedContent.addAll(page3.getContent());
		long totalElements = page1.getTotalElements() + page2.getTotalElements() + page3.getTotalElements();// FALSO
																											// POSITIVO
																											// DE
																											// FORTIFY
		return new PageImpl<>(combinedContent, page1.getPageable(), totalElements);
	}

	private String generarWhere1(BusquedaDocumentalDto busquedaDocumentalDto) {
		try {
			StringBuilder where = new StringBuilder();
			for (Field field : busquedaDocumentalDto.getClass().getDeclaredFields()) {
				ReflectionUtils.makeAccessible(field);
				if (field.get(busquedaDocumentalDto) != null) {
					FilterField filter = AnnotationUtils.findAnnotation(field, FilterField.class);
					if (filter != null) {
						generarWhere1Complemento(filter, field, where, busquedaDocumentalDto);
					}
				}
			}
			log.info("Where generado: {}", where.toString());
			return where.toString();
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	@SuppressWarnings("unchecked")
	private void generarWhere1Complemento(FilterField filter, Field field, StringBuilder where,
			BusquedaDocumentalDto busquedaDocumentalDto) {
		try {
			if (filter.type().equals(TypeObject.TYPE_LIST)) {
				List<Integer> lista = (List<Integer>) field.get(busquedaDocumentalDto);
				if (!lista.isEmpty()) {
					where.append(AND);
					where.append(filter.filter());
					where.append(IN.replace("VALORES", String.valueOf(field.get(busquedaDocumentalDto)))
							.replace("[", "").replace("]", ""));
				}
			} else if (field.get(busquedaDocumentalDto) != null
					&& !field.get(busquedaDocumentalDto).toString().isEmpty()) {
				where.append(AND);
				where.append(filter.filter());
				where.append("=");
				where.append("'");
				where.append(String.valueOf(field.get(busquedaDocumentalDto)));
				where.append("'");
			}
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	private String generarWhere2(BusquedaDocumentalDto busquedaDocumentalDto) {
		try {

			if (busquedaDocumentalDto.getIdFase() != null && 
            !busquedaDocumentalDto.getIdFase().isEmpty()) {
            log.info("Filtrando por fase - excluyendo documentos sin fase");
            return " AND 1 = 0"; 
        }
			StringBuilder where = new StringBuilder();
			String filters = "s2.id_estatus_proyecto,s1.id_proyecto,s7.descripcion,s4.id_fase_proyecto,s4.id_plantilla_vigente";
			for (Field field : busquedaDocumentalDto.getClass().getDeclaredFields()) {
				ReflectionUtils.makeAccessible(field);
				if (field.get(busquedaDocumentalDto) != null) {
					FilterField filter = AnnotationUtils.findAnnotation(field, FilterField.class);
					if (filter != null) {
						String filterValue = filter.filter();
						if (filters.contains(filterValue) && !"s4.id_fase_proyecto".equals(filterValue)
								&& !"s4.id_plantilla_vigente".equals(filterValue)) {

							generarWhere2Complemento(filter, field, where, busquedaDocumentalDto);
						}
					}
				}
			}
			log.info("Where generado: {}", where.toString());
			return where.toString();
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	@SuppressWarnings("unchecked")
	private void generarWhere2Complemento(FilterField filter, Field field, StringBuilder where,
			BusquedaDocumentalDto busquedaDocumentalDto) {
		try {
			if (filter.type().equals(TypeObject.TYPE_LIST)) {
				List<Integer> lista = (List<Integer>) field.get(busquedaDocumentalDto);
				if (!lista.isEmpty()) {
					where.append(AND);
					where.append(filter.filter());
					where.append(IN.replace("VALORES", String.valueOf(field.get(busquedaDocumentalDto)))
							.replace("[", "").replace("]", ""));
				}
			} else if (field.get(busquedaDocumentalDto) != null
					&& !field.get(busquedaDocumentalDto).toString().isEmpty()) {
				where.append(AND);
				where.append("s7.nombre");
				where.append("=");
				where.append("'");
				where.append(String.valueOf(field.get(busquedaDocumentalDto)));
				where.append("'");
			}
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	@Override
	public byte[] descargarArchivo(BusquedaDocumentalDto busquedaDocumentalDto) {
		try {
			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaDocumentalDto);
			List<String> etiquetasBase = pageMerge.getEtiquetas();
			List<String> etiquetas = new ArrayList<>(etiquetasBase != null ? etiquetasBase : List.of());
			log.info("Etiquetess: {}", etiquetas);
			etiquetas.remove(0);
			etiquetas.remove(etiquetas.size() - 1);
			etiquetas.remove(etiquetas.size() - 1);
			etiquetas.remove(etiquetas.size() - 1);
			etiquetas.remove(etiquetas.size() - 1);
			etiquetas.remove(etiquetas.size() - 1);

			byte[] archivo = exportGenericService.exportarReporte(new PageGeneric(pageMerge.getPage(), etiquetas),
					"Control documental", controlDocumental);

			String s = PistaUtil.cadenaPistasObjecto(busquedaDocumentalDto);
			microservicio.guardarPista(ModuloPista.REPORTE_CONTROL_DOCUMENTAL.getId(),
					TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(), s);

			return archivo;
		} catch (PistaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	@Override
	public byte[] descargarZip(BusquedaDocumentalDto busquedaDocumentalDto) {
		try {
			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaDocumentalDto);
			long inicio = System.currentTimeMillis();
			Set<String> archivos = pageMerge.getPage().getContent().stream().filter(d -> d[12] != null)
					.map(d -> String.valueOf(d[12])).collect(Collectors.toSet());
			log.info("archivos: {}", archivos);

			List<String> nombre = new ArrayList<>();
			List<InputStream> archivosInputStream = new ArrayList<>();

			ExecutorService executorService = Executors.newFixedThreadPool(10); // Usar 10 hilos
			List<Future<Void>> futures = new ArrayList<>();

			int[] contador = { 1 }; // Usar un array para que se pueda modificar dentro de la lambda

			for (String d : archivos) {
				// Ejecutar la descarga de archivos en paralelo
				futures.add(executorService.submit(() -> {
					descargarArchivos(d, nombre, archivosInputStream, contador);
					log.info("agregando hilos: {}", contador);
					return null; // Future<Void> necesita devolver algo, usamos null aquí
				}));
			}

			int con = 0;
			// Esperar a que todas las descargas terminen
			for (Future<Void> future : futures) {
				log.info("Leyendo hilos: {}", con++);
				future.get(); // Esto bloquea hasta que se complete cada tarea
			}
			log.info("Archivos generados: {}", archivosInputStream.size());

			// Ahora creamos el archivo zip a partir de los InputStreams
			byte[] bytes = UtilZip.createZip(nombre, archivosInputStream);

			String s = PistaUtil.cadenaPistasObjecto(busquedaDocumentalDto);
			microservicio.guardarPista(ModuloPista.REPORTE_CONTROL_DOCUMENTAL.getId(),
					TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(), s);

			long fin = System.currentTimeMillis();
			log.info("Tiempo de procesamiento: {}mls", (fin - inicio));

			return bytes;
		} catch (PistaException | ReporteException e) {
		    throw e;
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt(); 
		    throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_CONTRASENNIA, e);
		} catch (Exception e) {
		    throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_CONTRASENNIA, e);
		}
	}

	public void descargarArchivos(String d, List<String> nombre, List<InputStream> archivosInputStream,
			int[] contador) {
		try {
			// Conexión con el servicio Nexus
			InputStream archivo = nexusService.descargarArchivo(d);

			// Sincronización para evitar problemas de concurrencia con la lista 'nombre'
			synchronized (lockNombre) { // Sincronizamos sobre un objeto específico
				if (!nombre.contains(UtilZip.getLastSegment(d))) {
					nombre.add(UtilZip.getLastSegment(d));
				} else {
					nombre.add(contador[0] + "_" + UtilZip.getLastSegment(d));
					contador[0]++;
					log.info("contador {}", contador[0]);
				}
			}

			// Sincronización para evitar problemas con la lista 'archivosInputStream'
			synchronized (lockArchivosInputStream) { // Sincronizamos sobre un objeto específico
				archivosInputStream.add(archivo); // No cerramos el InputStream aquí
			}
		} catch (Exception e) {
			log.error("Error archivo no se pudo generar al zip: {}", e.getMessage());
		}
	}

	@Override
	public byte[] descargarArchivos(Integer id, int identificador) {
		try {
			String ruta = controlDocumentalRepository.obtenerRuta(id, identificador);
			byte[] archivo = {};
			if (!ruta.equals("")) {
				log.info("Ruta: {}", ruta);
				archivo = nexusService.descargarArchivo(ruta).readAllBytes();
			}

			String s = PistaUtil.cadenaPistasObjecto(id);
			microservicio.guardarPista(ModuloPista.REPORTE_CONTROL_DOCUMENTAL.getId(),
					TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(), s);

			return archivo;
		} catch (PistaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_EXPORTAR, e);
		}
	}

	@Override
	public List<CompartidoCloudModel> descargarCloud(BusquedaDocumentalDto busquedaDocumentalDto) {
		try {
			PageDocumentalDto<Object[]> pageMerge = generaPage(busquedaDocumentalDto);

			Set<String> archivos = pageMerge.getPage().getContent().stream().filter(d -> d[12] != null)
					.map(d -> String.valueOf(d[12])).collect(Collectors.toSet());

			log.info("archivos: {}", archivos);

			String s = PistaUtil.cadenaPistasObjecto(busquedaDocumentalDto);
			microservicio.guardarPista(ModuloPista.REPORTE_CONTROL_DOCUMENTAL.getId(),
					TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PROYECTO_DATOS_GESTION_DOCUMENTAL.getIdSeccionPista(), s);

			return Collections.emptyList();
		} catch (PistaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_CONTRASENNIA, e);
		}
	}

	@Override
	public boolean comprobarProyectos() {
		boolean acceso = seguridadService.validarRolAdminSistema()
				|| seguridadService.validarRolAdminSistemaSecundario() || seguridadService.validarRolTodosProyectos();
		Optional<Usuario> op = session.retornarUsuario();
		if (op.isPresent() && !acceso) {
			 List<ProyectoDto> pr = usuarioProyectoRepository.findProyectosConEstatusTrueAndByIdUser(op.get().getIdUser());
			if (pr.isEmpty()) {
				throw new ReporteException(ErroresEnum.ERROR_SIN_PROYECTOS);
			}
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CarpetaDtoResponse> obtenerMatriz(Integer identificador, Long idReferencia) {
		Optional <Long> idMatrizOp = matrizCriteriaService.findIdMatriz(identificador, idReferencia);
		
		if (idMatrizOp.isPresent()) {
			Long idMatriz= idMatrizOp.get();
			if (identificador<=3) {
				return matrizProyectoMicroservicio.obtenerMatrizDoc(idMatriz);
			}else if(identificador <=5) {
				// agregar comites (modificar el servicio interno)
			}else if(identificador <=8) {
				return matrizContratoMicroservicio.obtenerMatrizContrato(idMatriz);
			}else if(identificador <=11) {
				return matrizContratoMicroservicio.obtenerMatrizConvenio(idMatriz);
			}else if(identificador <=14) {
				return matrizContratoMicroservicio.obtenerMatrizReintegro(idMatriz);
			}else{
				return matrizDictamenMicroservicio.obtenerMatrizDoc(idMatriz);
			}
		}
		
		return Collections.emptyList();
	}

}
