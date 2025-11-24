package com.sisecofi.admindevengados.service.facturas.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sisecofi.admindevengados.dto.BanderaReponseDto;
import com.sisecofi.admindevengados.dto.ConceptoDto;
import com.sisecofi.admindevengados.dto.FacturaDto;
import com.sisecofi.admindevengados.dto.FacturaGuardarDto;
import com.sisecofi.admindevengados.dto.TamanioMbFacturasNotas;
import com.sisecofi.admindevengados.dto.solicitudpago.ReferenciaPagoMontos;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.microservicio.FacturaCFDIMicroservicio;

import com.sisecofi.admindevengados.model.SolicitudFacturaModel;
import com.sisecofi.admindevengados.repository.CatTipoMonedaRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.MontoSATYCCRepository;
import com.sisecofi.admindevengados.repository.SolicitudFacturaRepository;
import com.sisecofi.admindevengados.repository.facturas.FacturasRepository;
import com.sisecofi.admindevengados.repository.facturas.NotasCreditoRepository;
import com.sisecofi.admindevengados.repository.solicitudpago.ReferenciaPagoRepository;
import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.service.DictamenService;
import com.sisecofi.admindevengados.service.PistaService;
import com.sisecofi.admindevengados.service.facturas.FacturaService;
import com.sisecofi.admindevengados.service.impl.NexusImpl;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.consumer.CFDIValidator;
import com.sisecofi.admindevengados.util.consumer.Configuration;
import com.sisecofi.admindevengados.util.consumer.Factura;
import com.sisecofi.admindevengados.util.consumer.PathGenerator;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;

import com.sisecofi.libreria.comunes.dto.dictamen.FacturaContratoDto;
import com.sisecofi.libreria.comunes.dto.dictamen.FacturasInformacion;
import com.sisecofi.libreria.comunes.dto.factura.WebServiceDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaNotaBase;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.ConstantesParaRutasSATCloud;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.admindevengados.util.consumer.Factura.Impuestos.Traslados.Traslado;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.webservice.cfdi.soap.ConsultaCfdiResponse;
import com.webservice.cfdi.util.exception.CfdiException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

	private final FacturasRepository facturasRepository;
	private final NotasCreditoRepository notasCreditoRepository;
	private final DictamenRepository dictamenRepository;
	private final NexusImpl nexusImpl;
	private final CatalogoService catalogoService;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final FacturaCFDIMicroservicio facturaCFDIMicroservicio;
	private final DictamenService dictamenService;
	private final ArchivoPlantillaDictamenRepository archivoRepo;
	private final ReferenciaPagoRepository referenciaPagoRepository;
	private final DictamenRepository dictamenRepositoy;
	private final PistaService pistaService;
	private final SolicitudFacturaRepository solicitudFacturaRepository;
	private static final Logger logger = LoggerFactory.getLogger(FacturaServiceImpl.class);
	private static final String CONEXION_CERRADO = "Connection is closed";
	private final CatTipoMonedaRepository catTipoMonedaRepository;
	private final MontoSATYCCRepository montoSATYCCRepository;
	private static final String REGEX = "[-\\s]";

	private LocalDateTime obtenerFechaYHoraActual() {
		ZonedDateTime utcTime = ZonedDateTime.now(ZoneOffset.UTC);
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		return utcTime.withZoneSameInstant(zoneId).toLocalDateTime();
	}

	@Override
	public String cargarArchivo(String dictamenId, MultipartFile files) {
		try {
			Configuration config = new Configuration();
			config.setProperty("baseFolder", ConstantesParaRutasSATCloud.PATH_DICTAMEN);
			config.setProperty("FacturaFilesFolder", ConstantesParaRutasSATCloud.PATH_FACTURA);
			PathGenerator pathGenerator = new PathGenerator();
			String path = pathGenerator.generarPathFactura(dictamenId, config);
			cargarArchivoConInformacion(files, path);

			return path + "/" + files.getOriginalFilename();
		} catch (Exception e) {
			log.error("Error al cargar archivos: {}");
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	private String crearConfiguracion(String dictamenId) {
		Configuration config = new Configuration();
		config.setProperty("baseFolder", ConstantesParaRutasSATCloud.PATH_DICTAMEN);
		config.setProperty("FacturaFilesFolder", ConstantesParaRutasSATCloud.PATH_FACTURA);
		PathGenerator pathGenerator = new PathGenerator();
		return pathGenerator.generarPathFactura(dictamenId, config);
	}

	public void cargarArchivoConInformacionRenombrado(MultipartFile file, String path, String nombre) {
		try {

			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, nombre);
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	public void cargarArchivoConInformacion(MultipartFile file, String path) {
		try {

			boolean archivo = nexusImpl.cargarArchivo(file.getInputStream(), path, file.getOriginalFilename());
			log.info("Archivo cargado: {},", archivo);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
		}
	}

	private BigDecimal validarCampo(BigDecimal valor) {
		return (valor != null) ? valor : BigDecimal.ZERO;
	}

	private String extraerFolioUUID(String comprobanteFiscal) {
		String[] partes = comprobanteFiscal.split("-");
		return partes[partes.length - 1];
	}

	@Override
	public FacturaDto obtenerDatosXML(MultipartFile archivoXml, Long idContrato, Long idProveedor, Long dictamenId,
			String seccion) throws CfdiException {
		validarArchivoXml(archivoXml);
		Factura factura = parsearFactura(archivoXml);
		FacturaDto facturaDto = new FacturaDto();
		validarEstructuraYWebService(archivoXml, factura);
		validarSeccionYTipoComprobante(seccion, factura);
		Dictamen dictamen = obtenerDictamen(dictamenId);
		validarMoneda(dictamen, factura);
		facturaDto.setFolio(obtenerFolio(factura));
		facturaDto.setComprobanteFiscal(factura.getComplemento().getTimbreFiscalDigital().getUuid());
		if ("I".equals(factura.getTipoDeComprobante())) {
			validarFactura(facturaDto, factura, dictamen, idContrato, idProveedor);
		} else if ("E".equals(factura.getTipoDeComprobante())) {
			validarNotaCredito(facturaDto, factura, dictamen);
		}
		return completarFacturaDto(facturaDto, factura, dictamen);
	}

	private void validarArchivoXml(MultipartFile archivoXml) {
		if (archivoXml == null || archivoXml.isEmpty()) {
			throw new CatalogoException(ErroresEnum.EXTENSION_XML);
		}

		String nombreArchivo = archivoXml.getOriginalFilename();
		if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".xml")) {
			throw new CatalogoException(ErroresEnum.EXTENSION_XML);
		}
	}

	private Factura parsearFactura(MultipartFile archivoXml) {
		try {
			XmlMapper xmlMapper = new XmlMapper();
			return xmlMapper.readValue(archivoXml.getInputStream(), Factura.class);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ESTRUCTUTA_CFDI_INCORRECTA);
		}
	}

	private void validarEstructuraYWebService(MultipartFile archivoXml, Factura factura){
		CFDIValidator validarCFDI = new CFDIValidator(catalogoService);
		String estructuraCFDI = validarCFDI.validarCFDI(archivoXml);

		if (!"Validación exitosa".equals(estructuraCFDI)) {
			throw new CatalogoException(ErroresEnum.ESTRUCTUTA_CFDI_INCORRECTA);
		}

		WebServiceDto webService = new WebServiceDto();
		webService.setRfcEmisor(factura.getEmisor().getRfc());
		webService.setRfcReceptor(factura.getReceptor().getRfc());
		webService.setTotalFacturado(factura.getTotal());
		webService.setUuidTimbrado(factura.getComplemento().getTimbreFiscalDigital().getUuid());
		/*
		ConsultaCfdiResponse response;
		try {
			response = validarWebService(webService);	
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_FLUJO_WEB_SERVICE);
		} 
		
		if ("Cancelado".equals(response.getConsultaResult().getEstado())) {
			throw new CatalogoException(ErroresEnum.VALIDAR_WEB_SERVICE);
		}*/
	}

	private void validarSeccionYTipoComprobante(String seccion, Factura factura) {
		if ("Nota".equals(seccion) && "I".equals(factura.getTipoDeComprobante())) {
			throw new CatalogoException(ErroresEnum.SECCION_ERRONEA_NOTA);
		} else if (Constantes.FACTURA.equals(seccion) && "E".equals(factura.getTipoDeComprobante())) {
			throw new CatalogoException(ErroresEnum.SECCION_ERRONEA_FACTURA);
		}
	}

	private Dictamen obtenerDictamen(Long dictamenId) {
		return dictamenRepository.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
	}

	private void validarMoneda(Dictamen dictamen, Factura factura) {
		if ((BigDecimal.ZERO.compareTo(dictamen.getTipoCambioReferencial()) == 0 && !"MXN".equals(factura.getMoneda()))
				|| (BigDecimal.ZERO.compareTo(dictamen.getTipoCambioReferencial()) != 0
						&& "MXN".equals(factura.getMoneda()))) {
			throw new CatalogoException(ErroresEnum.MONEDA_DISTINTA);
		}
	}

	private String obtenerFolio(Factura factura) {
		return factura.getFolio() != null ? factura.getFolio()
				: extraerFolioUUID(factura.getComplemento().getTimbreFiscalDigital().getUuid());
	}

	private void validarFactura(FacturaDto facturaDto, Factura factura, Dictamen dictamen, Long idContrato,
			Long idProveedor) {
		validarFolioComprobante(facturaDto, idContrato, idProveedor);
		if (!factura.getEmisor().getNombre().equals(dictamen.getProveedorModel().getNombreProveedor())
				|| !factura.getEmisor().getRfc().equals(dictamen.getProveedorModel().getRfc())) {
			throw new CatalogoException(ErroresEnum.PROVEEDOR_NO_COINCIDE);
		}
	}

	private void validarNotaCredito(FacturaDto facturaDto, Factura factura, Dictamen dictamen) {
		validarFolioYComprobanteFiscal(facturaDto);

		if (!factura.getEmisor().getNombre().equals(dictamen.getProveedorModel().getNombreProveedor())
				|| !factura.getEmisor().getRfc().equals(dictamen.getProveedorModel().getRfc())) {
			throw new CatalogoException(ErroresEnum.PROVEEDOR_MO_COINCIDE_CONTRATO_DICTAMEN);
		}
	}

	private void validarFolioYComprobanteFiscal(FacturaDto facturaDto) {
		if (notasCreditoRepository
				.findByFolioAndComprobanteFiscal(facturaDto.getFolio(), facturaDto.getComprobanteFiscal())
				.isPresent()) {
			throw new CatalogoException(ErroresEnum.FOLIO_COMP_UTILIZADO_OTRO_CONTRATO);
		}
	}

	private void validarFolioComprobante(FacturaDto facturaDto, Long idContrato, Long idProveedor) {
		Optional<FacturaModel> existeFolio = facturasRepository
				.findByFolioAndComprobanteFiscalAndDictamenContratoModelIdContratoAndDictamenIdProveedor(
						facturaDto.getFolio(), facturaDto.getComprobanteFiscal(), idContrato, idProveedor);
		if (existeFolio.isPresent() || facturasRepository
				.findByFolioAndComprobanteFiscalAndDictamenIdProveedorNotInContrato(facturaDto.getFolio(),
						facturaDto.getComprobanteFiscal(), idProveedor, idContrato)
				.isPresent()) {
			throw new CatalogoException(ErroresEnum.FOLIO_COMPROBANTE_REGISTRADO);
		}
	}

	private FacturaDto completarFacturaDto(FacturaDto facturaDto, Factura factura, Dictamen dictamen) {
		facturaDto.setEmisorRFC(factura.getEmisor().getRfc());
		facturaDto.setEmisorNombre(factura.getEmisor().getNombre());
		facturaDto.setFecha(LocalDateTime.parse(factura.getFechaFacturacion()));
		facturaDto.setMoneda(factura.getMoneda() != null ? factura.getMoneda() : "");

		facturaDto.setTasaoCuota(calcularTasaCuota(factura));
		facturaDto.setSubTotal(validarCampo(factura.getSubtotal() != null ? factura.getSubtotal() : BigDecimal.ZERO));
		facturaDto.setTotal(validarCampo(factura.getTotal() != null ? factura.getTotal() : BigDecimal.ZERO));

		completarImpuestos(facturaDto, factura);
		completarConceptos(facturaDto, factura);

		facturaDto.setArchivoCargar(facturaDto.getArchivoCargar());
		facturaDto.setIdContrato(dictamen.getIdContrato());
		facturaDto.setTotalPesos(calcularTotalEnPesos(factura, dictamen));

		return facturaDto;
	}

	private String calcularTasaCuota(Factura factura) {
		if (factura.getImpuestos() != null && factura.getImpuestos().getTraslados() != null
				&& !factura.getImpuestos().getTraslados().getTrasladoList().isEmpty()) {

			for (Traslado traslado : factura.getImpuestos().getTraslados().getTrasladoList()) {
				if (traslado.getTasaCuota() != null && traslado.getTasaCuota().compareTo(BigDecimal.ZERO) > 0) {
					return traslado.getTasaCuota().multiply(new BigDecimal("100")).toString();
				}
			}

			return factura.getImpuestos().getTraslados().getTrasladoList().get(0).getTasaCuota()
					.multiply(new BigDecimal("100")).toString();
		}

		return "0";
	}

	private void completarImpuestos(FacturaDto facturaDto, Factura factura) {
		facturaDto.setIva(calcularImpuesto(factura, "002"));
		facturaDto.setIeps(calcularImpuesto(factura, "003"));
	}

	private BigDecimal calcularImpuesto(Factura factura, String codigoImpuesto) {
		if (factura.getImpuestos() == null || factura.getImpuestos().getTraslados() == null) {
			return BigDecimal.ZERO;
		}

		for (Factura.Impuestos.Traslados.Traslado traslado : factura.getImpuestos().getTraslados().getTrasladoList()) {
			if (codigoImpuesto.equals(traslado.getImpuesto()) && traslado.getTasaCuota() != null
					&& traslado.getTasaCuota().compareTo(BigDecimal.ZERO) > 0) {
				return traslado.getImporte() != null ? traslado.getImporte() : BigDecimal.ZERO;
			}
		}

		for (Factura.Impuestos.Traslados.Traslado traslado : factura.getImpuestos().getTraslados().getTrasladoList()) {
			if (codigoImpuesto.equals(traslado.getImpuesto())) {
				return traslado.getImporte() != null ? traslado.getImporte() : BigDecimal.ZERO;
			}
		}

		return BigDecimal.ZERO;
	}

	private void completarConceptos(FacturaDto facturaDto, Factura factura) {
		List<ConceptoDto> conceptoDtos = new ArrayList<>();
		if (factura.getConceptos() != null) {
			for (Factura.Concepto concepto : factura.getConceptos()) {
				if ("E48".equalsIgnoreCase(concepto.getClaveUnidad())) {
					ConceptoDto conceptoDto = new ConceptoDto();
					conceptoDto.setClaveProdServ(concepto.getClaveProdServ());
					conceptoDto.setClaveUnidad(concepto.getClaveUnidad());
					conceptoDtos.add(conceptoDto);
				}
			}
		}
		facturaDto.setConceptos(conceptoDtos);
	}

	private BigDecimal calcularTotalEnPesos(Factura factura, Dictamen dictamen) {
		if ("MXN".equals(factura.getMoneda())) {
			return factura.getTotal().setScale(2, RoundingMode.HALF_UP);
		}
		BigDecimal tipoCambio = dictamen.getTipoCambioReferencial() != null ? dictamen.getTipoCambioReferencial()
				: BigDecimal.ZERO;
		return factura.getTotal().multiply(tipoCambio).setScale(2, RoundingMode.HALF_UP);
	}

	private ArchivoPlantillaDictamenModel crearYGuardarArchivo(MultipartFile archivoXml, String nombre, String pathPdf,
			String rutaCompleta) {
		ArchivoPlantillaDictamenModel archivo = new ArchivoPlantillaDictamenModel();
		double tamaniooEnMB = archivoXml.getSize() / (1024.0 * 1024.0);

		archivo.setNombre(nombre);
		archivo.setDescripcion(nombre);
		archivo.setObligatorio(false);
		archivo.setCargado(true);
		archivo.setNoAplica(false);
		archivo.setCarpeta(pathPdf);
		archivo.setTamanoMb(tamaniooEnMB);
		archivo.setFechaModificacion(obtenerFechaYHoraActual());
		archivo.setRuta(rutaCompleta);
		archivoRepo.save(archivo);
		return archivo;
	}

	@Transactional
	@Override
	@Valid
	public FacturaModel guardarFactura(@Valid FacturaGuardarDto facturaDto, MultipartFile archivoXml, MultipartFile pdf)
			throws CfdiException {

		Long dictamenId = facturaDto.getDictamenId();

		FacturaDto datosXml = obtenerDatosXML(archivoXml, facturaDto.getIdContrato(), facturaDto.getIdProveedor(),
				dictamenId, Constantes.FACTURA);

		validarArchivoPdf(pdf);

		validarUuidEnPdf(datosXml.getComprobanteFiscal(), pdf);

		FacturaModel factura = crearFactura(datosXml, facturaDto, dictamenId);

		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		String pathPdf = dictamenService.generarRuta(dictamen.getIdDictamenVisual(), dictamen.getContratoModel());
		Integer ultimoConsecutivo = facturasRepository.obtenerMaxConsecutivoActivoPorDictamen(dictamenId);
		String idFormato = dictamen.getIdDictamenVisual().replace('|', '-');
		int nuevoConsecutivo = (ultimoConsecutivo != null) ? ultimoConsecutivo + 1 : 1;
		factura.setConsecutivo(nuevoConsecutivo);
		String nombreFactura = "07_" + nuevoConsecutivo + Constantes.FACTURA_ARCHIVO + datosXml.getFolio() + ".pdf";
		String nombreXml = "08_" + nuevoConsecutivo + Constantes.FACTURA_ARCHIVO + datosXml.getFolio() + ".xml";
		String path = crearConfiguracion(idFormato);
		try {
			cargarArchivoConInformacionRenombrado(archivoXml, pathPdf, nombreXml);
			cargarArchivoConInformacionRenombrado(pdf, pathPdf, nombreFactura);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

		factura.setPathArchivoXml(path + "/" + facturaDto.getArchivoCargar());
		factura.setPathPdf(path + "/" + facturaDto.getArchivoPdf());
		log.info("path xml {}", factura.getPathArchivoXml());
		log.info("path pdf {}", factura.getPathPdf());
		factura.setArchivoCargar(facturaDto.getArchivoCargar());
		factura.setPdf(facturaDto.getArchivoPdf());

		ArchivoPlantillaDictamenModel archivoPdfModel = crearYGuardarArchivo(pdf, nombreFactura, pathPdf,
				pathPdf + "/" + nombreFactura);
		ArchivoPlantillaDictamenModel archivoXmlModel = crearYGuardarArchivo(archivoXml, nombreXml, pathPdf,
				pathPdf + "/" + nombreXml);

		factura.setArchivoPdf(archivoPdfModel);
		factura.setArchivoXml(archivoXmlModel);

		// EN LA SECCION FALTA REGISTRAR FACTURA
		try {
			validarFolioComprobanteFactura(datosXml);
			facturasRepository.save(factura);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.FOLIO_COMPROBANTE_REGISTRADO);
		}



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.FACTURAS.getIdSeccionPista(),


		// Constantes.getAtributosFactura()[0] + facturaDto.getDictamenId() + "|"


		// + Constantes.getAtributosFactura()[1] + datosXml.getFolio() + "|"


		// + Constantes.getAtributosFactura()[2] + datosXml.getComprobanteFiscal() + "|",


		// Optional.of(factura));

		actualizarEstatusDictamen(dictamen);
		return factura;

	}

	public void actualizarEstatusDictamen(Dictamen dictamen) {
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_FACTURADO);

		if (lista != null && !lista.isEmpty()) {
			dictamen.setIdEstatusDictamen(lista.get(0).getPrimaryKey());
			dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
			dictamenRepository.save(dictamen);
		} else {
			throw new IllegalStateException("No se encontró el estatus facturado en el catálogo.");
		}
	}

	private void validarArchivoPdf(MultipartFile pdf) {
		if (pdf == null || pdf.isEmpty()) {
			throw new CatalogoException(ErroresEnum.EXTENSION_PDF);
		}

		String originalFilename = pdf.getOriginalFilename();
		if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
			throw new CatalogoException(ErroresEnum.EXTENSION_PDF);
		}

	}

	private void validarUuidEnPdf(String uuidXml, MultipartFile pdf) {
		try (PDDocument document = PDDocument.load(pdf.getInputStream())) {
			PDFTextStripper stripper = new PDFTextStripper();

			stripper.setStartPage(1);
			stripper.setEndPage(document.getNumberOfPages());

			String textoPdf = stripper.getText(document);

			boolean encontrado = textoPdf.contains(uuidXml);

			if (!encontrado) {

				String uuidLimpio = uuidXml.replaceAll(REGEX, "").toUpperCase();
				String textoLimpio = textoPdf.replaceAll(REGEX, "").toUpperCase();
				encontrado = textoLimpio.contains(uuidLimpio);

				if (encontrado) {
					log.info("UUID encontrado después de normalización de texto");
				}
			}

			if (!encontrado) {
				java.util.regex.Pattern pattern = java.util.regex.Pattern
						.compile("[A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12}");
				java.util.regex.Matcher matcher = pattern.matcher(textoPdf);
				if (matcher.find()) {
					log.warn("UUID encontrado en PDF pero diferente: [{}]", matcher.group());
				}

				throw new CatalogoException(ErroresEnum.UUID_PDF_NO_CORRESPONDE);
			}

		} catch (CatalogoException e) {
			throw e;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.UUID_PDF_NO_CORRESPONDE, e);
		}
	}

	private void validarFolioComprobanteFactura(FacturaDto facturaDto) {
		if (facturasRepository.findByFolioAndComprobanteFiscal(facturaDto.getFolio(), facturaDto.getComprobanteFiscal())
				.isPresent()) {
			throw new CatalogoException(ErroresEnum.FOLIO_COMPROBANTE_REGISTRADO);
		}

	}

	private FacturaModel crearFactura(FacturaDto datosXml, FacturaGuardarDto facturaDto, Long dictamenId) {
		FacturaModel factura = new FacturaModel();

		factura.setIdDictamen(dictamenId);
		factura.setFolio(datosXml.getFolio());
		factura.setComprobanteFiscal(datosXml.getComprobanteFiscal());
		factura.setFechaFacturacion(datosXml.getFecha());
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_FACTURA.getIdCatalogo(), Constantes.ESTATUS_ACCPI);
		logger.info("catalogo estatus: {}", lista.get(0).getPrimaryKey());
		factura.setIdEstatusFactura(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		factura.setEstatus(true);
		Integer idTipoMonedaXml = obtenerIdTipoMonedaDesdeXml(datosXml.getMoneda());
		factura.setIdTipoMoneda(idTipoMonedaXml);
		factura.setIdIva(facturaDto.getIdIva());
		factura.setSubTotal(datosXml.getSubTotal());
		factura.setIeps(datosXml.getIeps());
		factura.setIva(datosXml.getIva());
		factura.setOtrosImpuestos(datosXml.getOtrosImpuestos());
		factura.setTotal(datosXml.getTotal());
		factura.setTotalPesos(datosXml.getTotalPesos() != null ? datosXml.getTotalPesos() : BigDecimal.ZERO);
		factura.setComentarios(facturaDto.getComentarios());
		factura.setArchivoCargar(facturaDto.getArchivoCargar());
		factura.setPdf(facturaDto.getArchivoPdf());
		factura.setMontoCC(facturaDto.getMontoCC());
		factura.setMontoPesosCC(facturaDto.getMontoPesosCC());
		factura.setPorcentajeCC(facturaDto.getPorcentajeCC());
		factura.setPorcentajeSat(facturaDto.getPorcentajeSat());
		factura.setMontoSat(facturaDto.getMontoSat());
		factura.setMontoPesosSat(facturaDto.getMontoPesoSat());

		return factura;
	}

	private Integer obtenerIdTipoMonedaDesdeXml(String monedaXml) {
		if (monedaXml == null || monedaXml.trim().isEmpty()) {
			throw new CatalogoException(ErroresEnum.MONEDA_DISTINTA);
		}

		try {
			CatTipoMoneda tipoMoneda = catTipoMonedaRepository.findByNombre(monedaXml.trim().toUpperCase())
					.orElseThrow(() -> new CatalogoException(ErroresEnum.MONEDA_DISTINTA));

			return tipoMoneda.getIdTipoMoneda();

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MONEDA_DISTINTA, e);
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	@Valid
	public <T> T editarFactura(@Valid FacturaGuardarDto facturaDto, MultipartFile archivoXml, MultipartFile pdf)
			throws CfdiException {
		log.info("Editando Factura ID: {}", facturaDto.getIdFacturaNota());

		FacturaNotaBase facturaExistente = obtenerFacturaExistente(facturaDto);
		Dictamen dictamen = obtenerDictamen(facturaExistente);
		String pathPdf = dictamenService.generarRuta(dictamen.getIdDictamenVisual(), dictamen.getContratoModel());
		FacturaDto datosXml = null;
		double tamaniooEnMBPdf = 0.0;
		double tamaniooEnMBXml = 0.0;
		if (archivoXml != null && !archivoXml.isEmpty()) {
			datosXml = obtenerDatosXML(archivoXml, facturaExistente.getDictamen().getIdContrato(),
					facturaExistente.getDictamen().getIdProveedor(), facturaExistente.getDictamen().getIdDictamen(),
					facturaDto.getTipoDocumento());
			tamaniooEnMBXml = archivoXml.getSize() / (1024.0 * 1024.0);
			procesarArchivoXml(facturaDto, archivoXml, pathPdf, facturaExistente, datosXml);
		} else {
			datosXml = new FacturaDto();
		}

		// BUSCAR FACTURAS POR ID

		FacturaModel factura = facturasRepository.findByIdFacturaAndEstatusActivo(facturaDto.getIdFacturaNota());

		if (pdf != null && !pdf.isEmpty()) {

			if (archivoXml != null && !archivoXml.isEmpty()) {
				validarUuidEnPdf(datosXml.getComprobanteFiscal(), pdf);
			} else {

				validarUuidEnPdf(factura.getComprobanteFiscal(), pdf);
			}

			tamaniooEnMBPdf = pdf.getSize() / (1024.0 * 1024.0);
			datosXml.setFolio(factura.getFolio());
			procesarArchivoPdf(pdf, pathPdf, facturaExistente, datosXml);
		}
		actualizarFacturaDetalles(facturaDto, facturaExistente);

		ArchivoPlantillaDictamenModel archivoXmlOriginal = facturaExistente.getArchivoXml();
		ArchivoPlantillaDictamenModel archivoPdfOriginal = facturaExistente.getArchivoPdf();

		TamanioMbFacturasNotas tamanioMbs = new TamanioMbFacturasNotas();
		tamanioMbs.setTamaniooEnMBXml(tamaniooEnMBXml);
		tamanioMbs.setTamaniooEnMBPdf(tamaniooEnMBPdf);

		actualizarArchivoDatos(archivoXmlOriginal, archivoPdfOriginal, pathPdf, facturaExistente, datosXml, tamanioMbs,
				facturaDto);

		guardarFactura(facturaDto, facturaExistente);

		boolean esFactura = facturaDto.getTipoDocumento().equals("Factura");



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// esFactura ? TipoSeccionPista.FACTURAS.getIdSeccionPista()


		// : TipoSeccionPista.NOTA_CREDITO.getIdSeccionPista(),


		// Constantes.getAtributosFactura()[0] + facturaDto.getDictamenId() + "|"


		// + Constantes.getAtributosFactura()[1] + facturaExistente.getFolio() + "|"


		// + Constantes.getAtributosFactura()[2] + facturaExistente.getComprobanteFiscal() + "|",


		// Optional.of(facturaExistente));

		actualizarEstatusDictamen(dictamen);

		return (T) facturaExistente;
	}

	private FacturaNotaBase obtenerFacturaExistente(FacturaGuardarDto facturaDto) {
		return facturaDto.getTipoDocumento().equals(Constantes.FACTURA)
				? facturasRepository.findById(facturaDto.getIdFacturaNota())
						.orElseThrow(() -> new CatalogoException(ErroresEnum.FACTURA_NOT_FOUND))
				: notasCreditoRepository.findById(facturaDto.getIdFacturaNota())
						.orElseThrow(() -> new CatalogoException(ErroresEnum.FACTURA_NOT_FOUND));
	}

	private Dictamen obtenerDictamen(FacturaNotaBase facturaExistente) {
		return dictamenRepositoy.findByIdDictamen(facturaExistente.getIdDictamen())
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));
	}

	private void procesarArchivoXml(FacturaGuardarDto facturaDto, MultipartFile archivoXml, String pathPdf,
			FacturaNotaBase facturaExistente, FacturaDto datosXml) {
		String nombreXml = construirNombreArchivoXml(datosXml, facturaExistente);

		try {
			cargarArchivoConInformacionRenombrado(archivoXml, pathPdf, nombreXml);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

		facturaExistente.setPathArchivoXml(pathPdf + "/" + nombreXml);
		facturaExistente.setFolio(datosXml.getFolio());
		facturaExistente.setComprobanteFiscal(datosXml.getComprobanteFiscal());

		if (Constantes.FACTURA.equals(facturaDto.getTipoDocumento())) {
			((FacturaModel) facturaExistente).setFechaFacturacion(datosXml.getFecha());
		} else if ("Nota".equals(facturaDto.getTipoDocumento())) {
			((NotaCreditoModel) facturaExistente).setFechaGeneracion(datosXml.getFecha());
		}

		actualizarTotales(facturaExistente, datosXml);
	}

	private String construirNombreArchivoXml(FacturaDto datosXml, FacturaNotaBase facturaExistente) {
		int consecutivo = facturaExistente.getConsecutivo();
		String tipo = facturaExistente instanceof FacturaModel ? "08_" : "10_";
		String prefijo = facturaExistente instanceof FacturaModel ? Constantes.FACTURA_ARCHIVO
				: Constantes.NOTA_ARCHIVO;
		return tipo + consecutivo + prefijo + datosXml.getFolio() + ".xml";
	}

	private void procesarArchivoPdf(MultipartFile pdf, String pathPdf, FacturaNotaBase facturaExistente,
			FacturaDto datosXml) {
		validarArchivoPdf(pdf);

		String nombrePdf = construirNombreArchivoPdf(facturaExistente, datosXml);
		try {
			cargarArchivoConInformacionRenombrado(pdf, pathPdf, nombrePdf);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

		facturaExistente.setPathPdf(pathPdf + "/" + nombrePdf);
	}

	private String construirNombreArchivoPdf(FacturaNotaBase facturaExistente, FacturaDto datosXml) {
		int consecutivo = facturaExistente.getConsecutivo();
		String tipo = facturaExistente instanceof FacturaModel ? "07_" : "09_";
		String prefijo = facturaExistente instanceof FacturaModel ? Constantes.FACTURA_ARCHIVO
				: Constantes.NOTA_ARCHIVO;
		return tipo + consecutivo + prefijo + datosXml.getFolio() + ".pdf";
	}

	private void actualizarFacturaDetalles(FacturaGuardarDto facturaDto, FacturaNotaBase facturaExistente) {
		facturaExistente.setComentarios(facturaDto.getComentarios());
		facturaExistente.setMontoCC(facturaDto.getMontoCC());
		facturaExistente.setMontoPesosCC(facturaDto.getMontoPesosCC());
		facturaExistente.setPorcentajeCC(facturaDto.getPorcentajeCC());
		facturaExistente.setPorcentajeSat(facturaDto.getPorcentajeSat());
		facturaExistente.setMontoSat(facturaDto.getMontoSat());
		facturaExistente.setMontoPesosSat(facturaDto.getMontoPesoSat());
		facturaExistente.setIdTipoMoneda(facturaDto.getIdTipoMoneda());
	}

	private void actualizarArchivoDatos(ArchivoPlantillaDictamenModel archivoXmlOriginal,
			ArchivoPlantillaDictamenModel archivoPdfOriginal, String pathPdf, FacturaNotaBase facturaExistente,
			FacturaDto datosXml, TamanioMbFacturasNotas tamanioMbs, FacturaGuardarDto facturaDto) {
		String nuevoNombreXml = construirNombreArchivoXml(datosXml, facturaExistente);
		String nuevoNombrePdf = construirNombreArchivoPdf(facturaExistente, datosXml);

		if (archivoXmlOriginal != null && !archivoXmlOriginal.getNombre().equals(nuevoNombreXml)) {
			archivoXmlOriginal.setNombre(nuevoNombreXml);
			archivoXmlOriginal.setDescripcion(nuevoNombreXml);
			archivoXmlOriginal.setRuta(pathPdf + "/" + nuevoNombreXml);
			archivoRepo.save(archivoXmlOriginal);
			facturaExistente.setArchivoCargar(facturaDto.getArchivoCargar());
			facturaExistente.setArchivoXml(archivoXmlOriginal);
		} else {
			archivoXmlOriginal = new ArchivoPlantillaDictamenModel();

			archivoXmlOriginal.setNombre(nuevoNombreXml);
			archivoXmlOriginal.setDescripcion(nuevoNombreXml);
			archivoXmlOriginal.setObligatorio(false);
			archivoXmlOriginal.setCargado(true);
			archivoXmlOriginal.setNoAplica(false);
			archivoXmlOriginal.setCarpeta(pathPdf);
			archivoXmlOriginal.setTamanoMb(tamanioMbs.getTamaniooEnMBXml());
			archivoXmlOriginal.setFechaModificacion(obtenerFechaYHoraActual());
			archivoXmlOriginal.setRuta(pathPdf + "/" + nuevoNombreXml);
			archivoRepo.save(archivoXmlOriginal);
			facturaExistente.setArchivoCargar(facturaDto.getArchivoCargar());
			facturaExistente.setArchivoXml(archivoXmlOriginal);
		}

		if (archivoPdfOriginal != null && !archivoPdfOriginal.getNombre().equals(nuevoNombrePdf)) {
			archivoPdfOriginal.setNombre(nuevoNombrePdf);
			archivoPdfOriginal.setDescripcion(nuevoNombrePdf);
			archivoPdfOriginal.setRuta(pathPdf + "/" + nuevoNombrePdf);
			archivoRepo.save(archivoPdfOriginal);
			facturaExistente.setPdf(facturaDto.getArchivoPdf());
			facturaExistente.setArchivoPdf(archivoPdfOriginal);
		} else {
			archivoPdfOriginal = new ArchivoPlantillaDictamenModel();

			archivoPdfOriginal.setNombre(nuevoNombrePdf);
			archivoPdfOriginal.setDescripcion(nuevoNombrePdf);
			archivoPdfOriginal.setObligatorio(false);
			archivoPdfOriginal.setCargado(true);
			archivoPdfOriginal.setNoAplica(false);
			archivoPdfOriginal.setCarpeta(pathPdf);
			archivoPdfOriginal.setTamanoMb(tamanioMbs.getTamaniooEnMBPdf());
			archivoPdfOriginal.setFechaModificacion(obtenerFechaYHoraActual());
			archivoPdfOriginal.setRuta(pathPdf + "/" + nuevoNombrePdf);
			archivoRepo.save(archivoPdfOriginal);
			facturaExistente.setPdf(facturaDto.getArchivoPdf());
			facturaExistente.setArchivoPdf(archivoPdfOriginal);
		}
	}

	private void guardarFactura(FacturaGuardarDto facturaDto, FacturaNotaBase facturaExistente) {
		if (Constantes.FACTURA.equals(facturaDto.getTipoDocumento())) {
			try {
				facturasRepository.save((FacturaModel) facturaExistente);
			} catch (Exception e) {
				throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
			}
		} else if ("Nota".equals(facturaDto.getTipoDocumento())) {
			try {
				notasCreditoRepository.save((NotaCreditoModel) facturaExistente);
			} catch (Exception e) {
				throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_ARCHIVO, e);
			}
		}
	}

	private void actualizarTotales(FacturaNotaBase facturaExistente, FacturaDto datosXml) {
		facturaExistente.setSubTotal(datosXml.getSubTotal());
		facturaExistente.setIeps(datosXml.getIeps());
		facturaExistente.setIva(datosXml.getIva());
		facturaExistente.setOtrosImpuestos(datosXml.getOtrosImpuestos());
		facturaExistente.setTotal(datosXml.getTotal());
		facturaExistente.setTotalPesos(datosXml.getTotalPesos());
	}

	@Override
	public List<FacturaModel> obtenerFacturas(Long dictamenId) {
		List<FacturaModel> facturas = facturasRepository.findByIdDictamenAndEstatusTrue(dictamenId);

		if (facturas == null || facturas.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			facturas.sort(Comparator.comparing(FacturaModel::getIdFactura));

			int maxConsecutivo = facturas.stream().map(FacturaModel::getConsecutivo).filter(Objects::nonNull)
					.mapToInt(Integer::intValue).max().orElse(0);

			for (FacturaModel factura : facturas) {
				if (factura.getConsecutivo() == null) {
					factura.setConsecutivo(++maxConsecutivo);
				}
			}
			facturasRepository.saveAll(facturas);
			return facturas;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN, e);
		}
	}

	@Override
	public List<NotaCreditoModel> obtenerNotasCredito(Long dictamenId) {

		log.info("dictamen id {}", dictamenId);
		List<NotaCreditoModel> facturas = notasCreditoRepository.findByIdDictamenAndEstatusTrue(dictamenId);
		log.info("tamaño: {}", facturas.size());
		if (facturas.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			int maxConsecutivo = facturas.stream().mapToInt(NotaCreditoModel::getConsecutivo).max().orElse(1);

			for (NotaCreditoModel factura : facturas) {
				List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
						CatalogosComunes.ESTATUS_NOTA_CREDITO.getIdCatalogo(), Constantes.ESTATUS_CANCELADA_NOTA);
				if (Integer.parseInt("" + factura.getIdEstatusNotaCredito()) != lista.get(0).getPrimaryKey()) {
					factura.setConsecutivo(++maxConsecutivo);
				}
			}
			return facturas;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN);
		}

	}

	@Override
	public List<FacturaContratoDto> obtenerFacturasContrato(Long idContrato) {
		List<FacturasInformacion> facturas = facturasRepository.findFacturaDataByContrato(idContrato);
		return facturasAsociadas(facturas);

	}

	@Override
	public List<FacturaContratoDto> obtenerFacturasEstimacion(List<Dictamen> lista) {
		List<FacturasInformacion> facturas = facturasRepository.findFacturaDataByDictamen(lista);
		return facturasAsociadas(facturas);

	}

	private List<FacturaContratoDto> facturasAsociadas(List<FacturasInformacion> facturas) {
		if (facturas == null || facturas.isEmpty()) {
			return Collections.emptyList();
		}

		List<FacturaContratoDto> resultado = new ArrayList<>();

		try {
			Map<Long, Boolean> convenioMap = obtenerConvenioMap(facturas);

			for (FacturasInformacion facturaModel : facturas) {
				List<FacturaContratoDto> facturaDtos = crearFacturaDtos(facturaModel, convenioMap);
				resultado.addAll(facturaDtos);
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.MSJ_NOT_FOUND_DICTAMEN, e);
		}

		return resultado;
	}

	private Map<Long, Boolean> obtenerConvenioMap(List<FacturasInformacion> facturas) {
		return facturas.stream().map(FacturasInformacion::getIdContrato).distinct()
				.collect(Collectors.toMap(idContrato -> idContrato, catalogoService::aplicaCC));
	}

	private List<FacturaContratoDto> crearFacturaDtos(FacturasInformacion facturaModel,
			Map<Long, Boolean> convenioMap) {
		List<FacturaContratoDto> facturaDtos = new ArrayList<>();
		Boolean convenio = convenioMap.get(facturaModel.getIdContrato());

		FacturaContratoDto facturaDto = construirFacturaDto(facturaModel, false);
		facturaDtos.add(facturaDto);

		if (Boolean.TRUE.equals(convenio)) {
			FacturaContratoDto facturaCCDto = construirFacturaDto(facturaModel, true);
			facturaDtos.add(facturaCCDto);
		}

		return facturaDtos;
	}

	private FacturaContratoDto construirFacturaDto(FacturasInformacion facturaModel, boolean esConvenio) {
		FacturaContratoDto facturaDto = new FacturaContratoDto();
		facturaDto.setIdDictamen(facturaModel.getIdDictamen());
		facturaDto.setComprobanteFiscal(facturaModel.getComprobanteFiscal());
		facturaDto.setConvenioColaboracion(esConvenio);

		String estatusFactura = facturaModel.getEstatusFactura();
		String estatusDictamen = facturaModel.getEstatusDictamen();

		if ("Pagado".equals(estatusDictamen)) {
			procesarFacturaPagada(facturaModel, esConvenio, facturaDto);
		} else {
			facturaDto.setMonto(esConvenio ? facturaModel.getMontoCC() : facturaModel.getMontoSat());
			facturaDto.setMontoPesos(esConvenio ? facturaModel.getMontoPesosCC() : facturaModel.getMontoPesosSat());
			facturaDto.setTipoCambioReferencial(obtenerTipoCambioReferencial(facturaModel, estatusDictamen));
		}

		facturaDto.setEstatus(estatusFactura);
		facturaDto.setIdProveedor(facturaModel.getIdProveedor());
		return facturaDto;
	}

	private void procesarFacturaPagada(FacturasInformacion facturaModel, boolean esConvenio,
			FacturaContratoDto facturaDto) {
		log.info("id factura: {}, estatus {}", facturaModel.getIdFactura(), esConvenio);

		ReferenciaPagoMontos referenciasPago = referenciaPagoRepository
				.findReferenciaByFacturaAndConvenio(facturaModel.getIdFactura(), esConvenio);
		BigDecimal tipoCambio = null;

		if (referenciasPago != null) {
			BigDecimal montoTotal = BigDecimal.ZERO;
			BigDecimal montoPesosTotal = BigDecimal.ZERO;

			tipoCambio = (referenciasPago.getTipoCambioPagado() != null) ? referenciasPago.getTipoCambioPagado()
					: BigDecimal.ZERO;

			BigDecimal pagado = referenciasPago.getPagadoNAFIN() != null ? referenciasPago.getPagadoNAFIN()
					: BigDecimal.ZERO;
			montoTotal = montoTotal.add(pagado);
			montoPesosTotal = montoPesosTotal.add(pagado);

			facturaDto.setMonto(montoTotal);
			facturaDto.setMontoPesos(montoPesosTotal);
			facturaDto.setTipoCambioReferencial(tipoCambio);
		}
	}

	private BigDecimal obtenerTipoCambioReferencial(FacturasInformacion facturaModel, String estatusFactura) {

		if (!estatusFactura.equals("Pagado")) {
			return facturaModel.getTipoCambioReferencial().compareTo(BigDecimal.ZERO) != 0
					? facturaModel.getTipoCambioReferencial()
					: null;
		} else {
			List<ReferenciaPagoModel> referenciasPago = referenciaPagoRepository
					.findByIdFacturaAndEstatusTrue(facturaModel.getIdFactura());

			if (referenciasPago == null || referenciasPago.isEmpty()) {
				return null;
			}

			for (ReferenciaPagoModel referencia : referenciasPago) {
				if (referencia.getTipoCambioPagado() != null) {
					return referencia.getTipoCambioPagado();
				}
			}

			return null;
		}
	}

	@Override
	public FacturaModel cancelarFactura(Long idFactura, String justificacion) {
		FacturaModel factura = facturasRepository.findById(idFactura)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.FACTURA_NOT_FOUND));

		Long totalFactura = facturasRepository.countByIdDictamen(factura.getIdDictamen());
		if (totalFactura == 1) {
			Dictamen dictamen = factura.getDictamen();
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_PROFORMA);
			dictamen.setIdEstatusDictamen(lista.get(0).getPrimaryKey());
		}
		factura.setIdFactura(idFactura);
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_FACTURA.getIdCatalogo(), Constantes.ESTATUS_CANCELADO);
		factura.setIdEstatusFactura(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		factura.setComentarios("Motivo de cancelación: " + justificacion + "|" + factura.getComentarios());

		// EN LA SECCION FALTA FACTURA

		Dictamen dictamenUltimaModificacion = factura.getDictamen();
		dictamenUltimaModificacion.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

		FacturaModel facturaModel = facturasRepository.save(factura);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.FACTURAS.getIdSeccionPista(),


		// Constantes.getAtributosCancelarFactura()[0] + factura.getIdDictamen() + "|"


		// + Constantes.getAtributosCancelarFactura()[1] + factura.getComprobanteFiscal() + "|"


		// + Constantes.getAtributosCancelarFactura()[2] + factura.getFolio() + "|"


		// + Constantes.getAtributosCancelarFactura()[3] + "|",


		// Optional.of(facturaModel));

		return facturaModel;
	}

	@Override
	public NotaCreditoModel cancelarNota(Long idNota, String justificacion) {
		NotaCreditoModel notaCredito = notasCreditoRepository.findById(idNota)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.FACTURA_NOT_FOUND));

		notaCredito.setIdNotaCredito(idNota);
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_NOTA_CREDITO.getIdCatalogo(), Constantes.ESTATUS_CANCELADA_NOTA);
		notaCredito.setIdEstatusNotaCredito(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		notaCredito.setComentarios("Motivo de cancelación: " + justificacion + "|" + notaCredito.getComentarios());

		Dictamen dictamenUltimaModificacion = notaCredito.getDictamen();
		dictamenUltimaModificacion.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

		notasCreditoRepository.save(notaCredito);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.NOTA_CREDITO.getIdSeccionPista(),


		// Constantes.getAtributosCancelarNota()[0] + notaCredito.getIdDictamen() + "|"


		// + Constantes.getAtributosCancelarNota()[1] + notaCredito.getFolio() + "|"


		// + Constantes.getAtributosCancelarNota()[2] + notaCredito.getComprobanteFiscal()


		// + Constantes.getAtributosCancelarNota()[3],


		// Optional.of(notaCredito));

		return notaCredito;
	}

	@Override
	@Valid
	public NotaCreditoModel guardarNotaCredito(@Valid FacturaGuardarDto notaCreditoDto, MultipartFile archivoXml,
			MultipartFile pdf) throws CfdiException {

		log.info("Dictamen ID: {}", notaCreditoDto.getDictamenId());
		Long dictamenId = notaCreditoDto.getDictamenId();

		FacturaDto datosXml = obtenerDatosXML(archivoXml, notaCreditoDto.getIdContrato(),
				notaCreditoDto.getIdProveedor(), dictamenId, "Nota");

		validarArchivoPdf(pdf);
		validarUuidEnPdf(datosXml.getComprobanteFiscal(), pdf);

		NotaCreditoModel notaCredito = crearNotaCredito(datosXml, notaCreditoDto, dictamenId);

		Integer ultimoConsecutivo = notasCreditoRepository.obtenerMaxConsecutivoActivoPorDictamen(dictamenId);
		int nuevoConsecutivo = (ultimoConsecutivo != null) ? ultimoConsecutivo + 1 : 1;

		notaCredito.setConsecutivo(nuevoConsecutivo);

		Dictamen dictamen = dictamenRepositoy.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		String pathPdf = dictamenService.generarRuta(dictamen.getIdDictamenVisual(), dictamen.getContratoModel());

		String nombrePdf = "09_" + nuevoConsecutivo + Constantes.NOTA_ARCHIVO + datosXml.getFolio() + ".pdf";
		String nombreXml = "10_" + nuevoConsecutivo + Constantes.NOTA_ARCHIVO + datosXml.getFolio() + ".xml";

		String path = dictamenService.generarRuta(dictamen.getIdDictamenVisual(), dictamen.getContratoModel());
		log.info("path xml {}", path);

		try {
			cargarArchivoConInformacionRenombrado(archivoXml, path, nombreXml);
			cargarArchivoConInformacionRenombrado(pdf, path, nombrePdf);
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().contains(CONEXION_CERRADO)) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

		notaCredito.setPathArchivoXml(path + "/" + notaCreditoDto.getArchivoCargar());
		notaCredito.setPathPdf(path + "/" + notaCreditoDto.getArchivoPdf());

		ArchivoPlantillaDictamenModel archivoPdfModel = crearYGuardarArchivo(pdf, nombrePdf, pathPdf,
				pathPdf + "/" + nombrePdf);
		ArchivoPlantillaDictamenModel archivoXmlModel = crearYGuardarArchivo(archivoXml, nombreXml, pathPdf,
				pathPdf + "/" + nombreXml);

		notaCredito.setArchivoPdf(archivoPdfModel);
		notaCredito.setArchivoXml(archivoXmlModel);

		Optional<Dictamen> dictamenUltimaModificacion = dictamenRepository.findByIdDictamen(dictamenId);
		if (dictamenUltimaModificacion.isPresent()) {
			dictamenUltimaModificacion.get().setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
		}
		try {
			validarFolioYComprobanteFiscal(datosXml);
			notasCreditoRepository.save(notaCredito);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.FOLIO_COMP_UTILIZADO_OTRO_CONTRATO, e);
		}



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.NOTA_CREDITO.getIdSeccionPista(),


		// Constantes.getAtributosNotaCredito()[0] + notaCreditoDto.getDictamenId() + "|"


		// + Constantes.getAtributosNotaCredito()[1] + notaCredito.getFolio() + "|"


		// + Constantes.getAtributosNotaCredito()[2] + notaCredito.getComprobanteFiscal(),


		// Optional.of(notaCredito));

		return notaCredito;
	}

	private NotaCreditoModel crearNotaCredito(FacturaDto datosXml, FacturaGuardarDto notaCreditoDto, Long dictamenId) {
		NotaCreditoModel notaCredito = new NotaCreditoModel();

		notaCredito.setIdDictamen(dictamenId);
		notaCredito.setFolio(datosXml.getFolio());
		notaCredito.setComprobanteFiscal(datosXml.getComprobanteFiscal());
		notaCredito.setFechaGeneracion(datosXml.getFecha());
		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_NOTA_CREDITO.getIdCatalogo(), Constantes.ESTATUS_APROBADA_NOTA);
		notaCredito.setIdEstatusNotaCredito(Long.parseLong("" + lista.get(0).getPrimaryKey()));
		notaCredito.setEstatus(true);
		notaCredito.setIdTipoMoneda(notaCreditoDto.getIdTipoMoneda());
		notaCredito.setIdIva(notaCreditoDto.getIdIva());
		notaCredito.setSubTotal(datosXml.getSubTotal());
		notaCredito.setIeps(datosXml.getIeps());
		notaCredito.setIva(datosXml.getIva());
		notaCredito.setOtrosImpuestos(datosXml.getOtrosImpuestos());
		notaCredito.setTotal(datosXml.getTotal());
		notaCredito.setTotalPesos(datosXml.getTotalPesos());
		notaCredito.setComentarios(notaCreditoDto.getComentarios());
		notaCredito.setArchivoCargar(notaCreditoDto.getArchivoCargar());
		notaCredito.setPdf(notaCreditoDto.getArchivoPdf());
		notaCredito.setMontoCC(notaCreditoDto.getMontoCC());
		notaCredito.setMontoPesosCC(notaCreditoDto.getMontoPesosCC());
		notaCredito.setPorcentajeCC(notaCreditoDto.getPorcentajeCC());
		notaCredito.setPorcentajeSat(notaCreditoDto.getPorcentajeSat());
		notaCredito.setMontoSat(notaCreditoDto.getMontoSat());
		notaCredito.setMontoPesosSat(notaCreditoDto.getMontoPesoSat());

		return notaCredito;
	}

	@Override
	public boolean validarFacturasNotasAsociadas(Long dictamenId) {
		boolean existeFactura = facturasRepository.existsByDictamenIdAndEstatusNombre(dictamenId);
		boolean existeNota = notasCreditoRepository.existsByDictamenIdAndEstatusNombre(dictamenId);
		log.info("existe facturas: {}", existeFactura);
		log.info("existe notas de credito: {}", existeNota);

		return existeFactura || existeNota;
	}

	@Override
	public boolean regresarProforma(Long dictamenId) {

		Dictamen dictamen = dictamenRepository.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_PROFORMA);

		dictamen.setIdEstatusDictamen(lista.get(0).getPrimaryKey());

		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

		SolicitudFacturaModel solicitud = solicitudFacturaRepository.findByDictamenIdAndDictamenEstatusTrue(dictamenId);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),


		// Constantes.getAtributosRegresarProforma()[0] + dictamen.getIdDictamen() + "|"


		// + Constantes.getAtributosRegresarProforma()[1],


		// Optional.of(dictamen));

		solicitud.setBanderaFactura(false);
		solicitudFacturaRepository.save(solicitud);

		return true;

	}

	@Override
	public ConsultaCfdiResponse validarWebService(WebServiceDto webServiceDto) throws CfdiException {
		return facturaCFDIMicroservicio.consultarCfdi(webServiceDto);
	}

	/*
	 * Nueva bandera en solicitud de facturas, este se reutiliza en Referencia de
	 * pago
	 */

	@Override
	public BanderaReponseDto banderaPagadofactura(Long idDictamen) {
		BanderaReponseDto bandera = new BanderaReponseDto();

		if (idDictamen == null) {
			bandera.setTieneSAT(false);
			bandera.setTieneCC(false);

		}

		List<FacturaModel> dictaminados = montoSATYCCRepository.findByIdDictamen(idDictamen);

		if (dictaminados.isEmpty()) {
			bandera.setTieneSAT(false);
			bandera.setTieneCC(false);
			return bandera;
		}

		boolean tieneSAT = dictaminados.stream()
				.anyMatch(d -> d.getMontoPesosSat() != null && d.getMontoPesosSat().compareTo(BigDecimal.ZERO) > 0);

		boolean tieneCC = dictaminados.stream()
				.anyMatch(d -> d.getMontoPesosCC() != null && d.getMontoPesosCC().compareTo(BigDecimal.ZERO) > 0);

		bandera.setTieneSAT(tieneSAT);
		bandera.setTieneCC(tieneCC);

		return bandera;

	}

	/*
	 * SERVICIO PARA CAMBIAR DE ESTATUS PROFROMA A PAGADO CON LA SOLICTUD FACTURA y
	 * SERVICIOS EN 0
	 */

	@Override
	public boolean pagarDictamen(Long idDictamen) {

		// 1. Buscar el dictamen por ID
		Dictamen dictamen = dictamenRepository.findByIdDictamen(idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		// 2. Validar que el dictamen esté en estatus PROFORMA
		List<BaseCatalogoModel> listaProforma = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_PROFORMA);

		if (listaProforma == null || listaProforma.isEmpty()) {
			throw new CatalogoException(ErroresEnum.NO_FOUND_ESTATUS);
		}

		// Verificar que el dictamen esté actualmente en estatus PROFORMA
		if (!dictamen.getIdEstatusDictamen().equals(listaProforma.get(0).getPrimaryKey())) {
			throw new CatalogoException(ErroresEnum.ESTATUS_PROFORMA);
		}

		// 3. Obtener el estatus "PAGADO" del catálogo
		List<BaseCatalogoModel> listaPagado = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_PAGADO);

		// 4. Validar que se encontró el estatus PAGADO
		if (listaPagado == null || listaPagado.isEmpty()) {
			throw new CatalogoException(ErroresEnum.ESTATUS_PAGDO_NO_FOUND);
		}

		// 5. Actualizar el dictamen con el nuevo estatus
		dictamen.setIdEstatusDictamen(listaPagado.get(0).getPrimaryKey());

		// 6. Actualizar fecha de última modificación
		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());

		// 7. Registrar en pista de auditoría

		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(),

		// "Dictamen ID: " + dictamen.getIdDictamen() + " | Estatus cambiado a PAGADO", Optional.of(dictamen));

		// 8. Guardar el dictamen actualizado
		dictamenRepository.save(dictamen);

		return true;

	}

}
