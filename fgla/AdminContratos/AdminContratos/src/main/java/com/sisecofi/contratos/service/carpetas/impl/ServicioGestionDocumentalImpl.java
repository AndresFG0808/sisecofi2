package com.sisecofi.contratos.service.carpetas.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;

import com.sisecofi.contratos.dto.ArchivoFaseDto;
import com.sisecofi.contratos.dto.DescargaSatCloudRequest;
import com.sisecofi.contratos.microservicios.PapeleraServicoControl;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.*;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoFaseReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.CarpetaPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseReintegroRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoConvenioRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoOtroDocumentoReintegroRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaContratoRepository;
import com.sisecofi.libreria.comunes.repository.ArchivoPlantillaConvenioRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoOtroDocumentoFaseContratoRepository;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaReintegroRepository;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaContratoRepository;
import com.sisecofi.contratos.repository.carpetas.CarpetaPlantillaConvenioRepository;
import com.sisecofi.contratos.repository.contrato.ContratoPlantillaRepository;
import com.sisecofi.contratos.repository.contrato.ConvenioModificatorioRepository;
import com.sisecofi.contratos.repository.reintegros.ReintegrosRepository;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.carpetas.ServicioArchivo;
import com.sisecofi.contratos.service.carpetas.ServicioGestionDocumental;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioPlantilla;
import com.sisecofi.contratos.service.reintegros.ReintegroConsultaService;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.dto.carpeta.ArchivoCargadoFaseDto;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class ServicioGestionDocumentalImpl implements ServicioGestionDocumental {

	private final ServicioArchivo servicioArchivo;
	private final ContratoRepository contratoRepository;
	private final ArchivoOtroDocumentoContratoRepository archivoOtroDocumentoContratoRepository;
	private final ArchivoOtroDocumentoFaseContratoRepository archivoOtroDocumentoFaseRepository;
	private final ArchivoPlantillaContratoRepository archivoPlantillaContratoRepository;
	private final ContratoPlantillaRepository contratoPlantillaRepository;
	private final CarpetaPlantillaContratoRepository carpetaPlantillaContratoRepository;
	private final CarpetaPlantillaConvenioRepository carpetaPlantillaConvenioRepository;
	private final ServicioConvenioPlantilla servicioConvenioPlantilla;
	private final ArchivoOtroDocumentoConvenioRepository archivoOtroDocumentoConvenioRepository;
	private final ArchivoOtroDocumentoFaseConvenioRepository archivoOtroDocumentoFaseConvenioRepository;
	private final ArchivoPlantillaConvenioRepository archivoPlantillaConvenioRepository;
	private final ConvenioModificatorioRepository convenioModificatorioRepository;
	private final ReintegrosRepository reintegrosRepository;
	private final ArchivoOtroDocumentoFaseReintegroRepository archivoOtroDocumentoFaseReintegroRepository;
	private final ArchivoOtroDocumentoReintegroRepository archivoOtroDocumentoReintegroRepository;
	private final ArchivoPlantillaReintegroRepository archivoPlantillaReintegroRepository;
	private final ReintegroConsultaService reintegroConsultaService;
	private final PistaService pistaService;
	private final PapeleraServicoControl papeleraServicoControl;
	private int indice;
	private int modulo;
	private int seccion;
	private static final String OTROS_DOC = "Otros Documentos";
	private static final String DESCRIPCION = "|descripcion: ";
	private static final String ID = "|id: ";

	@Override
	@Transactional
	@SuppressWarnings({ "unchecked" })
	public List<CarpetaDtoResponse> obtenerEstructuraDocumental(Long idContrato) {
		List<Archivo> archivos = new ArrayList<>();
		List<ContratoPlantilla> asociaciones = obtenerAsociaciones(idContrato);

		String nombreCorto = "";
		Long idProyecto = 0L;
		if (!asociaciones.isEmpty()) {
			nombreCorto = asociaciones.get(0).getContratoModel().getNombreCorto();
			idProyecto = asociaciones.get(0).getContratoModel().getProyecto().getIdProyecto();
		}

		List<CarpetaDtoResponse> estructura = new ArrayList<>();

		Map<PlantillaVigenteModel, List<CarpetaPlantillaContratoModel>> carpetasPorPlantilla = carpetaPlantillaContratoRepository
			    .findByContratoPlantillaIdContratoAndNivelAndCarpetaBaseEstatusTrueOrderByIdCarpetaPlantillaContrato(idContrato, 1)
			    .stream()
			    .collect(Collectors.groupingBy(
			        carpeta -> carpeta.getContratoPlantilla().getPlantillaVigenteModel(),
			        LinkedHashMap::new, 
			        Collectors.toList()
			    ));

		CarpetaDtoResponse carpetaEjecucion = new CarpetaDtoResponse();
		carpetaEjecucion.setNombre(Constantes.CARPETA_EJECUCION);
		String rutaEjecucion = ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + nombreCorto + "/FASES/" + "EJECUCION";
		carpetaEjecucion.setRuta(rutaEjecucion);
		carpetaEjecucion.setDescripcion(Constantes.CARPETA_EJECUCION);

		List<CarpetaDtoResponse> subRowsEjecucion = new ArrayList<>();

		for (Map.Entry<PlantillaVigenteModel, List<CarpetaPlantillaContratoModel>> entry : carpetasPorPlantilla
				.entrySet()) {
			PlantillaVigenteModel plantilla = entry.getKey();
			List<CarpetaPlantillaContratoModel> carpetas = entry.getValue();

			CarpetaDtoResponse carpetaPlantilla = new CarpetaDtoResponse();
			carpetaPlantilla.setNombre(plantilla.getNombre());
			carpetaPlantilla.setRuta(rutaEjecucion + "/" + plantilla.getNombre());
			carpetaPlantilla.setDescripcion(plantilla.getNombre());

			List<CarpetaDtoResponse> subRowsPlantilla = carpetas.stream()
					.map(carpeta -> convertirACarpetaDto(carpeta, archivos)).toList();

			carpetaPlantilla.setSubRows(subRowsPlantilla);
			subRowsEjecucion.add(carpetaPlantilla);
		}

		CarpetaDtoResponse otrosDocumentosEjecucion = new CarpetaDtoResponse();
		otrosDocumentosEjecucion.setNombre(OTROS_DOC);
		otrosDocumentosEjecucion
				.setRuta(rutaEjecucion + "/" + ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS_GENERAL);
		otrosDocumentosEjecucion.setDescripcion(OTROS_DOC);
		

		List<Archivo> otrosArchivosFase = archivoOtroDocumentoFaseRepository.findByIdContratoAndEstatusTrue(idContrato);
		otrosDocumentosEjecucion.setSubRows(otrosArchivosFase);
		subRowsEjecucion.add(otrosDocumentosEjecucion);

		carpetaEjecucion.setSubRows(subRowsEjecucion);
		estructura.add(carpetaEjecucion);

		List<Archivo> otrosArchivosProyecto = archivoOtroDocumentoContratoRepository
				.findByIdContratoAndEstatusTrue(idContrato);
		CarpetaDtoResponse carpetaOtrosDocumentosGeneral = new CarpetaDtoResponse();
		carpetaOtrosDocumentosGeneral.setNombre(OTROS_DOC);
		carpetaOtrosDocumentosGeneral.setRuta(ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + nombreCorto + "/FASES/"
				+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS_GENERAL);

		carpetaOtrosDocumentosGeneral.setSubRows(otrosArchivosProyecto);
		carpetaOtrosDocumentosGeneral.setDescripcion(OTROS_DOC);
		estructura.add(carpetaOtrosDocumentosGeneral);

		String resultado = archivos.stream()
				.map(archivo -> ID + archivo.getId() + DESCRIPCION + archivo.getDescripcion())
				.collect(Collectors.joining(" "));

		pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.CONTRATOS_GESTION_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getAtributosGenerales()[0] + idContrato + resultado, Optional.empty());

		return estructura;
	}

	@SuppressWarnings({ "unchecked" })
	private CarpetaDtoResponse convertirACarpetaDto(CarpetaPlantillaContratoModel carpeta, List<Archivo> archivosList) {
		CarpetaDtoResponse carpetaDto = new CarpetaDtoResponse();
		carpetaDto.setIdCarpetaPlantillaProyecto(carpeta.getIdCarpetaPlantillaContrato());
		carpetaDto.setNombre(carpeta.getNombre());
		carpetaDto.setRuta(carpeta.getRuta());
		carpetaDto.setDescripcion(carpeta.getNombre());
		List<CarpetaPlantillaContratoModel> subCarpetas = carpeta.getASubCarpetasActivas();
		List<Object> subCarpetaDtos = new ArrayList<>();

		if (subCarpetas != null) {
			subCarpetaDtos = subCarpetas.stream().map(subCarpeta -> convertirACarpetaDto(subCarpeta, archivosList))
					.collect(Collectors.toList());
		}

		List<ArchivoPlantillaContratoModel> archivos = carpeta.getArchivosActivos();
		if (archivos != null) {
			for (ArchivoPlantillaContratoModel archivo : archivos) {
				subCarpetaDtos.add(archivo);
				archivosList.add(archivo);
			}
		}

		carpetaDto.setSubRows(subCarpetaDtos);
		return carpetaDto;
	}

	private List<ContratoPlantilla> obtenerAsociaciones(Long idContrato) {
		return contratoPlantillaRepository.findByIdContratoAndEstatusTrue(idContrato);
	}

	private RutaConfiguracion obtenerRutaYConfiguracion(DescargaSatCloudRequest descargaSatCloudRequest) {
		this.indice = 0;
		this.modulo = ModuloPista.ADMIN_CONTRATOS.getId();
		this.seccion = TipoSeccionPista.CONTRATOS_GESTION_DOCUMENTAL.getIdSeccionPista();
		Long id = descargaSatCloudRequest.getIdContrato();
		String rutaGeneral = "";

		if (descargaSatCloudRequest.getPath() != null && !descargaSatCloudRequest.getPath().isEmpty()) {
			if (descargaSatCloudRequest.getPath().contains(ConstantesParaRutasSATCloud.PATH_BASE_CONVENIOS)) {
				this.indice = 1;
			}
			rutaGeneral = descargaSatCloudRequest.getPath();
		} else {
			if (descargaSatCloudRequest.getIdContrato() != null) {
				ContratoModel contrato = contratoRepository.findByIdContrato(descargaSatCloudRequest.getIdContrato())
						.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
				Long idProyecto = contrato.getProyecto().getIdProyecto();
				rutaGeneral = ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
						+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/FASES";

			} else if (descargaSatCloudRequest.getIdConvenio() != null) {
				ConvenioModificatorioModel convenio = convenioModificatorioRepository
						.findByIdConvenioModificatorioAndEstatusTrue(descargaSatCloudRequest.getIdConvenio())
						.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
				this.indice = 1;
				id = descargaSatCloudRequest.getIdConvenio();
				this.modulo = ModuloPista.CONVENIO_MODIFICATORIO.getId();
				this.seccion = TipoSeccionPista.CONVENIO_MODIFICATORIO_GESTION_DOCUMENTAL.getIdSeccionPista();

				ContratoModel contrato = convenio.getContratoModel();
				Long idProyecto = contrato.getProyecto().getIdProyecto();
				rutaGeneral = ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
						+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + contrato.getNombreCorto() + "/"
						+ ConstantesParaRutasSATCloud.PATH_BASE_CONVENIOS + "/" + convenio.getNumeroConvenio();
			} else {
				rutaGeneral = reintegroConsultaService
						.generarRutaCloud(descargaSatCloudRequest.getIdContratoReintegro());
				this.modulo = ModuloPista.REINTEGRO.getId();
				this.seccion = TipoSeccionPista.REINTEGRO_GESTION_DOCUMENTAL.getIdSeccionPista();
			}
		}

		return new RutaConfiguracion(rutaGeneral, id, this.modulo, this.seccion, this.indice);
	}

	private static class RutaConfiguracion {
		String rutaGeneral;
		Long id;
		int modulo;
		int seccion;
		int indice;

		RutaConfiguracion(String rutaGeneral, Long id, int modulo, int seccion, int indice) {
			this.rutaGeneral = rutaGeneral;
			this.id = id;
			this.modulo = modulo;
			this.seccion = seccion;
			this.indice = indice;
		}
	}

	@Override
	@Transactional
	public CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest) {
		RutaConfiguracion config = obtenerRutaYConfiguracion(descargaSatCloudRequest);

		pistaService.guardarPista(config.modulo, TipoMovPista.CONSULTA_REGISTRO.getId(), config.seccion,
				Constantes.getAtributosGenerales()[config.indice] + config.id, Optional.empty());

		return servicioArchivo.descargarFolderSatCloudGestion(config.rutaGeneral, config.id, config.modulo,
				config.seccion);
	}

	@Override
	@Transactional
	public String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest) {
		RutaConfiguracion config = obtenerRutaYConfiguracion(descargaSatCloudRequest);

		pistaService.guardarPista(config.modulo, TipoMovPista.CONSULTA_REGISTRO.getId(), config.seccion,
				Constantes.getAtributosGenerales()[config.indice] + config.id, Optional.empty());

		return servicioArchivo.descargarFolderGestion(config.rutaGeneral, config.id, config.modulo, config.seccion);
	}

	@Override
	@Transactional
	public boolean eliminarArchivos(List<Archivo> eliminados) {
		Long id;
		Long idCon;

		List<Archivo> eliminadosact = new ArrayList<>();

		Archivo eliminado = eliminados.get(0);

		this.indice = 0;
		this.modulo = ModuloPista.ADMIN_CONTRATOS.getId();
		this.seccion = TipoSeccionPista.CONTRATOS_GESTION_DOCUMENTAL.getIdSeccionPista();
		id = 1L;

		if (eliminado.getType().equals("tipoFase")) {
			eliminado.setType("tipoFaseContrato");
			ArchivoOtroDocumentoFaseContratoModel archivoEspecifico = archivoOtroDocumentoFaseRepository
					.findById(eliminado.getId())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR));
			id = archivoEspecifico.getIdContrato();
			eliminadosact.add(archivoEspecifico);
		} else if (eliminado.getType().equals("tipoProyecto") || eliminado.getType().equals("tipoContrato")) {
			eliminado.setType("tipoContrato");	
			ArchivoOtroDocumentoContratoModel archivoEspecifico = archivoOtroDocumentoContratoRepository
					.findById(eliminado.getId())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR));
			idCon = archivoEspecifico.getIdContrato();
			id = idCon;
			eliminadosact.add(archivoEspecifico);
		} else if (eliminado.getType().equals("tipoPlantilla") || eliminado.getType().equals("tipoPlantillaContrato")) {
			ArchivoPlantillaContratoModel archivoEspecifico = archivoPlantillaContratoRepository
					.findById(eliminado.getId())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR));
			eliminadosact.add(archivoEspecifico);
			id = archivoEspecifico.getCarpetaPlantillaModel().getContratoPlantilla().getIdContrato();
		} else if (eliminado instanceof ArchivoOtroDocumentoFaseConvenioModel
				|| eliminado instanceof ArchivoOtroDocumentoConvenioModel) {
			configurarValoresParaConvenio();
			if (eliminado instanceof ArchivoOtroDocumentoFaseConvenioModel archivoEspecifico) {
				eliminadosact.add(archivoEspecifico);
				id = archivoEspecifico.getIdConvenioModificatorio();
			} else if (eliminado instanceof ArchivoOtroDocumentoConvenioModel archivoEspecifico) {
				idCon = archivoEspecifico.getIdConvenioModificatorio();
				eliminadosact.add(archivoEspecifico);
				id = idCon;
			}
		} else if (eliminado instanceof ArchivoPlantillaConvenioModel) {
			ArchivoPlantillaConvenioModel archivoEspecifico = archivoPlantillaConvenioRepository
					.findById(eliminado.getId())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR));
			eliminadosact.add(archivoEspecifico);
			configurarValoresParaConvenio();
			id = archivoEspecifico.getCarpetaPlantillaConvenioModel().getConvenioPlantilla().getIdConvenio();
		} else if (eliminado instanceof ArchivoOtroDocumentoFaseReintegroModel archivoEspecifico) {
			configurarValoresParaReintegro();
			id = archivoEspecifico.getIdReintegrosAsociados();
			eliminadosact.add(archivoEspecifico);
		} else if (eliminado instanceof ArchivoOtroDocumentoReintegroModel archivoEspecifico) {
			configurarValoresParaReintegro();
			id = archivoEspecifico.getIdContrato();
			eliminadosact.add(archivoEspecifico);
		} else if (eliminado instanceof ArchivoPlantillaReintegroModel) {
			ArchivoPlantillaReintegroModel archivoEspecifico = archivoPlantillaReintegroRepository
					.findById(eliminado.getId())
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR));
			eliminadosact.add(archivoEspecifico);
			configurarValoresParaReintegro();
			id = archivoEspecifico.getCarpetaPlantillaModel().getReintegro().getIdReintegrosAsociados();
		}

		papeleraServicoControl.enviarPapelera(eliminadosact);

		String nombreArchivo = extraerNombreArchivo(eliminado.getRuta());
		pistaService.guardarPista(modulo, TipoMovPista.BORRA_REGISTRO.getId(), seccion,
				Constantes.getAtributosGenerales()[indice] + id + nombreArchivo, Optional.empty());

		return true;
	}

	private void configurarValoresParaConvenio() {
		this.indice = 1;
		this.modulo = ModuloPista.CONVENIO_MODIFICATORIO.getId();
		this.seccion = TipoSeccionPista.CONVENIO_MODIFICATORIO_GESTION_DOCUMENTAL.getIdSeccionPista();
	}

	private void configurarValoresParaReintegro() {
		this.indice = 2;
		this.modulo = ModuloPista.REINTEGRO.getId();
		this.seccion = TipoSeccionPista.REINTEGRO_GESTION_DOCUMENTAL.getIdSeccionPista();
	}

	private String extraerNombreArchivo(String ruta) {
		if (ruta == null || ruta.lastIndexOf("/") == -1) {
			return "";
		}
		return "|" + ruta.substring(ruta.lastIndexOf("/") + 1);
	}

	@Override
	@Transactional
	public Boolean cargarArchivoFaseIndividual(ArchivoCargadoFaseDto dto) {
		Integer idArch = null;
		try {
			idArch = Integer.parseInt(dto.getIdArchivo());
		} catch (NumberFormatException e) {
			log.warn("El valor proporcionado no es un número válido.");
			idArch = null;
		}
		String nombreCar = "";
		nombreCar = obtenerNombreArchivo(dto.getCarpeta());
		ArchivoFaseDto archivoDto = new ArchivoFaseDto();
		Archivo archivoEspecifico = obtenerArchivoPorTipo(dto.getType(), idArch, archivoDto, dto.getIdContrato(),
				nombreCar, dto.isNoAplica());
		actualizarArchivoConDatos(archivoEspecifico, dto.isNoAplica(), dto.getJustificacion(),
				dto.getCarpeta(), dto.getDescripcion());

		String nombreCompuesto = generarNombreCompuesto(archivoEspecifico, dto.getFile(), dto.getType());

		archivoDto.setArchivo(archivoEspecifico);
		archivoDto.setNombreFile(nombreCompuesto);
		archivoDto.setNombreFase(dto.getNombreFase());
		archivoDto.setFile(dto.getFile());
		archivoDto.setIdContrato(dto.getIdContrato());
		archivoDto.setIdReintegro(dto.getIdReintegro());

		if (dto.getType().contains("Convenio")) {
			archivoDto.setTipoRegistro(2);
		} else if (dto.getType().contains("Reintegro")) {
			archivoDto.setTipoRegistro(3);
		} else {
			archivoDto.setTipoRegistro(1);
		}

		servicioArchivo.cargarArchivoFase(archivoDto);
		return true;
	}

	private Archivo obtenerArchivoPorTipo(String type, Integer idArchivo, ArchivoFaseDto dto, Long idContrato,
			String carpetaReintegro, boolean aplica) {
		switch (type) {
		case Constantes.TIPO_PLANTILLA:
			ArchivoPlantillaContratoModel arc = archivoPlantillaContratoRepository.findById(idArchivo)
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR));
			
			arc.actualizaObligatorio(aplica, arc.getArchivoBase().isObligatorio());
			return arc;
		case Constantes.TIPO_FASE:
			return idArchivo == null ? new ArchivoOtroDocumentoFaseContratoModel()
					: archivoOtroDocumentoFaseRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoFaseContratoModel::new);
		case Constantes.TIPO_CONTRATO:
			return idArchivo == null ? new ArchivoOtroDocumentoContratoModel()
					: archivoOtroDocumentoContratoRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoContratoModel::new);
		// --------Para convenio
		case Constantes.TIPO_CONVENIO_T:
			return idArchivo == null ? new ArchivoOtroDocumentoConvenioModel()
					: archivoOtroDocumentoConvenioRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoConvenioModel::new);
		case Constantes.TIPO_FASE_CONVENIO:
			return idArchivo == null ? new ArchivoOtroDocumentoFaseConvenioModel()
					: archivoOtroDocumentoFaseConvenioRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoFaseConvenioModel::new);
		case Constantes.TIPO_PLANTILLA_CONVENIO:
			ArchivoPlantillaConvenioModel arcc= archivoPlantillaConvenioRepository.findById(idArchivo)
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR));
			arcc.actualizaObligatorio(aplica, arcc.getArchivoBase().isObligatorio());
			return arcc;
		// --------Para Reintegro
		case Constantes.TIPO_REINTEGRO:
			dto.setRuta(reintegroConsultaService.generarRutaBase(idContrato));
			dto.setIdMov(1);
			return idArchivo == null ? new ArchivoOtroDocumentoReintegroModel()
					: archivoOtroDocumentoReintegroRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoReintegroModel::new);
		case Constantes.TIPO_FASE_REINTEGRO:
			dto.setRuta(reintegroConsultaService.generarRutaFase(idContrato, carpetaReintegro));
			dto.setIdMov(1);
			return idArchivo == null ? new ArchivoOtroDocumentoFaseReintegroModel()
					: archivoOtroDocumentoFaseReintegroRepository.findById(idArchivo)
							.orElseGet(ArchivoOtroDocumentoFaseReintegroModel::new);
		case Constantes.TIPO_PLANTILLA_REINTEGRO:
			dto.setRuta(reintegroConsultaService.generarRuta(idContrato, carpetaReintegro));
			dto.setIdMov(3);
			ArchivoPlantillaReintegroModel ar= archivoPlantillaReintegroRepository.findById(idArchivo)
					.orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR));
			ar.actualizaObligatorio(aplica, ar.getArchivoBase().isObligatorio());
			return ar;
		default:
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	public String obtenerNombreArchivo(String ruta) {
		if (ruta == null) {
			return null;
		}
		int indiceUltimaBarra = ruta.lastIndexOf("/");
		if (indiceUltimaBarra != -1) {
			return ruta.substring(indiceUltimaBarra + 1);
		}
		return ruta;
	}

	private String generarNombreCompuesto(Archivo archivo, MultipartFile file, String type) {
		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			if (originalFilename != null) {
				String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

				if (type.equals(Constantes.TIPO_PLANTILLA)) {
					ArchivoPlantillaContratoModel archivoPlantilla = (ArchivoPlantillaContratoModel) archivo;
					return archivoPlantilla.getNombre() + "_" + archivoPlantilla.getCarpetaPlantillaModel()
							.getContratoPlantilla().getContratoModel().getNombreCorto() + extension;
				} else if (type.equals(Constantes.TIPO_PLANTILLA_CONVENIO)) {
					ArchivoPlantillaConvenioModel archivoPlantilla = (ArchivoPlantillaConvenioModel) archivo;
					return archivoPlantilla.getNombre() + "_" + archivoPlantilla.getCarpetaPlantillaConvenioModel()
							.getConvenioPlantilla().getConvenio().getNumeroConvenio() + extension;
				} else if (type.equals(Constantes.TIPO_PLANTILLA_REINTEGRO)) {
					ArchivoPlantillaReintegroModel archivoPlantilla = (ArchivoPlantillaReintegroModel) archivo;
					return archivoPlantilla.getNombre() + "_" + extension;
				} else {
					return originalFilename;
				}
			}
		}
		return "";
	}

	private void actualizarArchivoConDatos(Archivo archivo, boolean noAplica, String justificacion,
			String carpeta, String descripcion) {
		
		archivo.setNoAplica(noAplica);
		archivo.setJustificacion(justificacion);
		archivo.setFechaModificacion(horaActual());

		if (archivo instanceof ArchivoPlantillaContratoModel plantillaContrato) {
			plantillaContrato.setCarpeta(carpeta);
		} else if (archivo instanceof ArchivoPlantillaConvenioModel plantillaConvenio) {
			plantillaConvenio.setCarpeta(carpeta);
		} else if (archivo instanceof ArchivoPlantillaReintegroModel plantillaReintegro) {
			plantillaReintegro.setCarpeta(carpeta);
		} else if (archivo instanceof ArchivoOtroDocumentoFaseContratoModel
				|| archivo instanceof ArchivoOtroDocumentoContratoModel
				|| archivo instanceof ArchivoOtroDocumentoConvenioModel
				|| archivo instanceof ArchivoOtroDocumentoFaseConvenioModel) {
			archivo.setDescripcion(descripcion);
			archivo.setNombre(descripcion);
		}
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	// -------------------para convenio ------------------

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<CarpetaDtoResponse> obtenerEstructuraDocumentalConvenio(Long idConvenio) {
		List<Archivo> archivos = new ArrayList<>();
		List<ConvenioPlantilla> asociaciones = servicioConvenioPlantilla.obtenerAsociaciones(idConvenio);
		String numeroContrato = "";
		String nombreCorto = "";
		Long idProyecto = 0L;

		if (!asociaciones.isEmpty()) {
			numeroContrato = asociaciones.get(0).getConvenio().getNumeroConvenio();
			idProyecto = asociaciones.get(0).getConvenio().getContratoModel().getProyecto().getIdProyecto();
			nombreCorto = asociaciones.get(0).getConvenio().getContratoModel().getNombreCorto();
		}

		List<CarpetaDtoResponse> estructura = new ArrayList<>();

		// Agrupar carpetas por plantilla
		Map<PlantillaVigenteModel, List<CarpetaPlantillaConvenioModel>> carpetasPorPlantilla = carpetaPlantillaConvenioRepository
			    .findByConvenioPlantillaIdConvenioAndNivelAndCarpetaBaseEstatusTrueOrderByIdCarpetaPlantillaConvenio(idConvenio, 1)
			    .stream()
			    .collect(Collectors.groupingBy(
			        carpeta -> carpeta.getConvenioPlantilla().getPlantillaVigenteModel(),
			        LinkedHashMap::new, 
			        Collectors.toList()
			    ));

		CarpetaDtoResponse carpetaEjecucion = new CarpetaDtoResponse();
		carpetaEjecucion.setNombre(Constantes.CARPETA_EJECUCION);
		String rutaEjecucion = ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + nombreCorto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONVENIOS + "/" + numeroContrato + "/" + "EJECUCION";
		carpetaEjecucion.setRuta(rutaEjecucion);
		carpetaEjecucion.setDescripcion(Constantes.CARPETA_EJECUCION);

		List<CarpetaDtoResponse> subRowsEjecucion = new ArrayList<>();

		for (Map.Entry<PlantillaVigenteModel, List<CarpetaPlantillaConvenioModel>> entry : carpetasPorPlantilla
				.entrySet()) {
			PlantillaVigenteModel plantilla = entry.getKey();
			List<CarpetaPlantillaConvenioModel> carpetas = entry.getValue();

			CarpetaDtoResponse carpetaPlantilla = new CarpetaDtoResponse();
			carpetaPlantilla.setNombre(plantilla.getNombre());
			carpetaPlantilla.setRuta(rutaEjecucion + "/" + plantilla.getNombre());
			carpetaPlantilla.setDescripcion(plantilla.getNombre());

			List<CarpetaDtoResponse> subRowsPlantilla = carpetas.stream()
					.map(carpeta -> convertirACarpetaDtoConvenio(carpeta, archivos)).toList();

			carpetaPlantilla.setSubRows(subRowsPlantilla);
			subRowsEjecucion.add(carpetaPlantilla);
		}

		CarpetaDtoResponse otrosDocumentosEjecucion = new CarpetaDtoResponse();
		otrosDocumentosEjecucion.setNombre(OTROS_DOC);
		otrosDocumentosEjecucion
				.setRuta(rutaEjecucion + "/" + ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS_GENERAL);
		otrosDocumentosEjecucion.setDescripcion(OTROS_DOC);


		List<Archivo> otrosArchivosFase = archivoOtroDocumentoFaseConvenioRepository
				.findByIdConvenioModificatorioAndEstatusTrue(idConvenio);
		otrosDocumentosEjecucion.setSubRows(otrosArchivosFase);
		subRowsEjecucion.add(otrosDocumentosEjecucion);

		carpetaEjecucion.setSubRows(subRowsEjecucion);
		estructura.add(carpetaEjecucion);

		List<Archivo> otrosArchivosProyecto = archivoOtroDocumentoConvenioRepository
				.findByIdConvenioModificatorioAndEstatusTrue(idConvenio);
		CarpetaDtoResponse carpetaOtrosDocumentosGeneral = new CarpetaDtoResponse();
		carpetaOtrosDocumentosGeneral.setNombre(OTROS_DOC);
		carpetaOtrosDocumentosGeneral.setRuta(ConstantesParaRutasSATCloud.PATH_BASE + "/" + idProyecto + "/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONTRATOS + "/" + nombreCorto+"/"
				+ ConstantesParaRutasSATCloud.PATH_BASE_CONVENIOS + "/" + numeroContrato + "/"
				+ ConstantesParaRutasSATCloud.PATH_OTROS_DOCUMENTOS_GENERAL);
		
		carpetaOtrosDocumentosGeneral.setSubRows(otrosArchivosProyecto);
		carpetaOtrosDocumentosGeneral.setDescripcion(OTROS_DOC);
		estructura.add(carpetaOtrosDocumentosGeneral);

		String resultado = archivos.stream()
				.map(archivo -> ID + archivo.getId() + DESCRIPCION + archivo.getDescripcion())
				.collect(Collectors.joining(" "));

		pistaService.guardarPista(ModuloPista.CONVENIO_MODIFICATORIO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.CONVENIO_MODIFICATORIO_GESTION_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getAtributosGenerales()[1] + idConvenio + resultado, Optional.empty());

		return estructura;
	}

	@SuppressWarnings({ "unchecked" })
	private CarpetaDtoResponse convertirACarpetaDtoConvenio(CarpetaPlantillaConvenioModel carpeta,
			List<Archivo> archivosList) {
		CarpetaDtoResponse carpetaDto = new CarpetaDtoResponse();
		carpetaDto.setIdCarpetaPlantillaProyecto(carpeta.getIdCarpetaPlantillaConvenio());
		carpetaDto.setNombre(carpeta.getNombre());
		carpetaDto.setRuta(carpeta.getRuta());
		carpetaDto.setDescripcion(carpeta.getNombre());
		List<CarpetaPlantillaConvenioModel> subCarpetas = carpeta.getASubCarpetasActivas();
		List<Object> subCarpetaDtos = new ArrayList<>();

		if (subCarpetas != null) {
			subCarpetaDtos = subCarpetas.stream()
					.map(subCarpeta -> convertirACarpetaDtoConvenio(subCarpeta, archivosList))
					.collect(Collectors.toList());
		}

		List<ArchivoPlantillaConvenioModel> archivos = carpeta.getArchivosActivos();
		if (archivos != null) {
			for (ArchivoPlantillaConvenioModel archivo : archivos) {
				subCarpetaDtos.add(archivo);
				archivosList.add(archivo);
			}
		}

		carpetaDto.setSubRows(subCarpetaDtos);
		return carpetaDto;
	}

	// ------------------------------para
	// reintegros-----------------------------------------

	@Override
	@SuppressWarnings("unchecked")
	public List<CarpetaDtoResponse> obtenerEstructuraDocReintegros(Long idContrato) {
		List<Archivo> archivos = new ArrayList<>();
		List<ReintegrosAsociadosModel> listaReintegros = reintegrosRepository
				.findByIdContratoAndEstatusTrueOrderByIdReintegrosAsociadosAsc(idContrato);

		List<CarpetaDtoResponse> estructura = new ArrayList<>();
		for (ReintegrosAsociadosModel reintegro : listaReintegros) {
			if (reintegro.getCarpetaGestion() != null) {
				CarpetaPlantillaReintegroModel carpetaGestion = reintegro.getCarpetaGestion();
				CarpetaDtoResponse car = new CarpetaDtoResponse();
				car.setDescripcion(carpetaGestion.getNombre());
				car.setNombre(carpetaGestion.getNombre());
				car.setRuta(carpetaGestion.getRuta());
				car.setIdReintegro(carpetaGestion.getReintegro().getIdReintegrosAsociados());
				List<Object> subRows = new ArrayList<>();
				for (ArchivoPlantillaReintegroModel arc : carpetaGestion.getArchivosActivos()) {
					subRows.add(arc);
					archivos.add(arc);
				}
				CarpetaDtoResponse otrosDocs = new CarpetaDtoResponse();
				otrosDocs.setDescripcion(OTROS_DOC);
				otrosDocs.setNombre(OTROS_DOC);
				List<Archivo> listaOtros = archivoOtroDocumentoFaseReintegroRepository
						.findByIdReintegrosAsociadosAndEstatusTrue(reintegro.getIdReintegrosAsociados());
				archivos.addAll(listaOtros);
				otrosDocs.setSubRows(listaOtros);
				otrosDocs.setIdReintegro(reintegro.getIdReintegrosAsociados());
				otrosDocs
						.setRuta(reintegroConsultaService.generarRutaFase(idContrato, carpetaGestion.getDescripcion()));
				subRows.add(otrosDocs);

				car.setSubRows(subRows);

				estructura.add(car);
			}

		}
		CarpetaDtoResponse otrosDocs = new CarpetaDtoResponse();
		otrosDocs.setDescripcion(OTROS_DOC);
		otrosDocs.setNombre(OTROS_DOC);

		List<Archivo> listaOtros = archivoOtroDocumentoReintegroRepository.findByIdContratoAndEstatusTrue(idContrato);
		archivos.addAll(listaOtros);
		otrosDocs.setSubRows(listaOtros);
		otrosDocs.setRuta(reintegroConsultaService.generarRutaBase(idContrato));
		estructura.add(otrosDocs);

		String resultado = archivos.stream()
				.map(archivo -> ID + archivo.getId() + DESCRIPCION + archivo.getDescripcion())
				.collect(Collectors.joining(" "));

		pistaService.guardarPista(ModuloPista.REINTEGRO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.REINTEGRO_GESTION_DOCUMENTAL.getIdSeccionPista(),
				Constantes.getAtributosGenerales()[0] + idContrato + resultado, Optional.empty());

		return estructura;
	}
}
