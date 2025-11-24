package com.sisecofi.admindevengados.service.impl;

import com.sisecofi.admindevengados.dto.*;
import com.sisecofi.admindevengados.helper.SolicitudFacturaHelper;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.model.DescuentosModel;
import com.sisecofi.admindevengados.model.SolicitudFacturaModel;
import com.sisecofi.admindevengados.repository.BanderaPagadoFacturaRespository;
import com.sisecofi.admindevengados.repository.DescuentosRepository;
import com.sisecofi.admindevengados.repository.DictamenRepository;
import com.sisecofi.admindevengados.repository.SolicitudFacturaRepository;
import com.sisecofi.admindevengados.service.*;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.DateUtil;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.admindevengados.util.exception.CatalogoException;
import com.sisecofi.admindevengados.util.exception.DevengadosException;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.repository.cierre.ArchivoPlantillaDictamenRepository;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProformaServiceImpl implements ProformaService {

	private final PenasContractualesService penaContractualService;
	private final PenasConvencionalesService penasConvencionalesService;
	private final DeduccionesService deduccionesService;
	private final DictamenService dictamenService;
	private final DictaminadoService dictaminadoService;
	private final FileService fileService;
	private final PistaService pistaService;
	private final DescuentosRepository descuentosRepository;
	private final SolicitudFacturaRepository solicitudFacturaRepository;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final DictamenRepository dictamenRepository;
	private final SolicitudFacturaHelper solicitudFacturaHelper;
	private final ArchivoPlantillaDictamenRepository archivoRepo;
	private final BanderaPagadoFacturaRespository banderaPagadoFacturaRespository;

	int j = 1;

	/**
	 * Catálogo de Penas 1 Penas Convencionales 2 Penas Contractuales 3 Deducciones
	 * 4 Descuentos
	 */
	@Override
	public List<ResumenProformaDto> obtenerResumenProforma(Long idDictamen) {
		List<ResumenProformaDto> resumenProformaDtoList = new ArrayList<>();
		try {
			j = 1;
			dictamenDto dictamen = dictamenService.obtenerDictamenesId(idDictamen);
			List<ObtenerPenaContractualDto> penasContractuales = penaContractualService
					.obtenerPenasContractuales(idDictamen);
			List<ObtenerPenaContractualDto> penasConvencionales = penasConvencionalesService
					.obtenerPenasConvencional(idDictamen);
			List<ObtenerPenaContractualDto> deducciones = deduccionesService.obtenerDeducciones(idDictamen);
			List<ObtenerPenaContractualDto> descuentos = obtenerDescuentos(idDictamen);
			String moneda = dictamen.getNombreMoneda();

			if (deducciones != null)
				resumenProformaDtoList.addAll(cargarLista(deducciones, moneda, 3));
			if (penasContractuales != null)
				resumenProformaDtoList.addAll(cargarLista(penasContractuales, moneda, 2));
			if (penasConvencionales != null)
				resumenProformaDtoList.addAll(cargarLista(penasConvencionales, moneda, 1));
			if (descuentos != null)
				resumenProformaDtoList.addAll(cargarLista(descuentos, moneda, 4));

			return resumenProformaDtoList;
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	/**
	 * Desgloce 1 SAT , 2 CC
	 */
	public List<ResumenProformaDto> cargarLista(List<ObtenerPenaContractualDto> lista, String moneda, int idTipo) {
		log.info("Cargar lista : {} ", lista.size());
		List<ResumenProformaDto> resumenProformaDtoList = new ArrayList<>();
		List<DesgloceProformaDto> listDesgloce = obtenerCatalogoDesglose();

		if (!listDesgloce.isEmpty()) {
			int i = 0;
			BigDecimal sumaMontos = BigDecimal.valueOf(0.00);

			while (i < listDesgloce.size()) {
				int idDesgloce = listDesgloce.get(i).getIdDesgloce();
				List<ResumenProformaDto> listTemp = obtenerListaTemporal(lista, moneda, idTipo, idDesgloce, j);

				j = procesarListaTemporal(listTemp, resumenProformaDtoList, sumaMontos, moneda, idTipo, j);

				i++;
			}
		}

		return resumenProformaDtoList;
	}

	private List<ResumenProformaDto> obtenerListaTemporal(List<ObtenerPenaContractualDto> lista, String moneda,
			int idTipo, int idDesgloce, int j) {

		List<ResumenProformaDto> listTemp = new ArrayList<>();
		for (ObtenerPenaContractualDto pena : lista) {
			if (idDesgloce == pena.getIdDesglose()) {
				ResumenProformaDto resumenProformaDto = new ResumenProformaDto();
				resumenProformaDto.setNo(j);
				resumenProformaDto.setIdTipo(pena.getIdDesglose());
				resumenProformaDto.setMoneda(moneda);
				resumenProformaDto.setTipo(idTipo);
				resumenProformaDto.setMonto(pena.getMonto());
				listTemp.add(resumenProformaDto);
			}
		}
		return listTemp;
	}

	private int procesarListaTemporal(List<ResumenProformaDto> listTemp,
			List<ResumenProformaDto> resumenProformaDtoList, BigDecimal sumaMontos, String moneda, int idTipo, int j) {

		int contador = 1;
		for (ResumenProformaDto resumenDto : listTemp) {
			sumaMontos = sumaMontos.add(resumenDto.getMonto());
			if (contador == listTemp.size()) {
				ResumenProformaDto resumenProformaDto = new ResumenProformaDto();
				resumenProformaDto.setNo(j);
				resumenProformaDto.setIdTipo(resumenDto.getIdTipo());
				resumenProformaDto.setMoneda(moneda);
				resumenProformaDto.setTipo(idTipo);
				resumenProformaDto.setMonto(sumaMontos);
				resumenProformaDtoList.add(resumenProformaDto);
				j++;
				sumaMontos = BigDecimal.valueOf(0.00);
			}
			contador++;
		}

		return j;
	}

	@Override
	public PlantillaProformaDto obtenerPlantillaProforma(Long dictamenId) {
		PlantillaProformaDto plantillaProformaDto = new PlantillaProformaDto();
		try {
			plantillaProformaDto.setDictamen(dictamenService.obtenerDictamenesId(dictamenId));
			plantillaProformaDto.setResumenProformaDtoList(obtenerResumenProforma(dictamenId));
			plantillaProformaDto.setServiciosDictaminados(
					dictaminadoService.obtenerDictaminados(plantillaProformaDto.getDictamen().getIdContrato(),
							plantillaProformaDto.getDictamen().getIdDictamen()));
			plantillaProformaDto.setContrato(
					dictamenService.obtenerContratoDto(plantillaProformaDto.getDictamen().getIdContrato()));
			plantillaProformaDto.setDeduccionesDtoList(deduccionesService.obtenerDeducciones(dictamenId));
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
		return plantillaProformaDto;

	}

	@Override
	public List<ResumenProformaDto> guardarProforma(List<DescuentoDto> listDescuento) {
		List<DescuentosModel> descuentosModelList = new ArrayList<>();

		if (listDescuento != null && !listDescuento.isEmpty()) {
			try {
				log.info("Dictamen Id : {}", listDescuento.get(0).getIdDictamen());

				for (DescuentoDto descuentoDto : listDescuento) {
					DescuentosModel descuentosModel = new DescuentosModel();
					descuentosModel.setDictamenId(descuentoDto.getIdDictamen());
					descuentosModel.setIdDesgloce(descuentoDto.getIdDesgloce());
					descuentosModel.setMonto(descuentoDto.getMonto());
					descuentosModel.setMoneda(descuentoDto.getMoneda());
					descuentosModelList.add(descuentosModel);
				}

				descuentosRepository.saveAll(descuentosModelList);

				if (!listDescuento.isEmpty()) {

					// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

					// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(),

					// listDescuento.get(0).getIdDictamen() + "|" + "regisrotabla" + "|"

					// + listDescuento.get(0).getNombreDocumento(),

					// Optional.empty());
				}
			} catch (Exception e) {
				throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR, e);
			}
		} else {
			log.warn("La lista de descuentos está vacía o es nula.");
		}

		if (listDescuento != null && !listDescuento.isEmpty()) {
			return obtenerResumenProforma(listDescuento.get(0).getIdDictamen());
		} else {
			return Collections.emptyList();
		}
	}

	public void verDetallePenasDeduccionesProforma(DictamenId dictamenId) {
		try {
			// Lee documento almacenado en en la BD y lo prepara para su descarga FA19 VER
			String nombreDoc = "";

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(),

			// dictamenId.getIdDictamen() + "|" + nombreDoc, Optional.empty());
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
		}
	}

	public void recepcionFacturaProforma(DictamenId dictamenId) {
		try {
			// FA11 RECEPCION DE FACTURA
			// GUARDA EN EL CAMPO FACTURAS RECIBIDAS
			// RNA247 se debe guardar en alguna seccion ULTIMA MODIFICION + NOMBRE EMPLEADO
			// SAT + FECHA Y HORA

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_SOLICITUD_FACTURA.getIdSeccionPista(),

			// dictamenId.getIdDictamen() + "|" + "recepción de facturas", Optional.empty());
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
		}
	}

	public Dictamen actualizarUltimaModificacion(Long dictamenId) {
		Dictamen dictamen = dictamenRepository.findByIdDictamen(dictamenId)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.DICTAMEN_RESUMEN_NOT_FOUND));

		dictamen.setUltimaModificacion(dictamenService.ultimaModificacionGeneral());
		return dictamenRepository.save(dictamen);
	}

	@Transactional
	@Override
	public SolicitudFacturaResponseDto solicitudFacturaProformaGuardar(SolicitudFacturaCreateDto request) {
		Long idDictamen = request.idDictamen();

		String nombreArchivo = "";

		ArchivoPlantillaDictamenModel archivo = this.archivoRepo
				.findByDescripcionContainingAndCarpetaPlantillaModelIdDictamen(
						"Oficio de Solicitud al proveedor para emitir factura", idDictamen)
				.orElseThrow(() -> new CatalogoException(ErroresEnum.ARCHIVO_NOCARGADO));

		String rutaBase = archivo.getCarpeta();
		String rutaModificada;

		MultipartFile documento = request.documento();
		if (documento != null && !documento.isEmpty()) {
			nombreArchivo = (documento.getOriginalFilename()).replace(" ", "_");// <-- Espacios X guines
			rutaModificada = rutaBase + "/" + nombreArchivo;
			if (archivo.getRuta() == null || !archivo.getRuta().equalsIgnoreCase(rutaModificada))
				archivo.setRuta(rutaModificada);

			archivo.setCargado(Boolean.TRUE);
			archivo.setTamanoMb((double) documento.getSize() / (1024 * 1024));
			archivo.setFechaDocumento(request.fechaSolicitud().atStartOfDay());
			archivo.setFechaModificacion(DateUtil.horaActual());
		}
		comprobarObligatorio(archivo, (documento != null && !documento.isEmpty()) );

		// Manejo de la conexión con Nexus
		try {
			if (documento != null && !documento.isEmpty()) {
				this.fileService.subirArchivoANexus(documento, rutaBase, nombreArchivo);
			}
		} catch (Exception e) {

			if (e.getMessage() != null && e.getMessage().contains("Connection is closed")) {
				throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
			}
			throw new CatalogoException(ErroresEnum.CONEXION_PERDIDA, e);
		}

		SolicitudFacturaModel model = this.solicitudFacturaRepository.findByDictamenId(idDictamen)
				.orElse(this.solicitudFacturaHelper.mapToModelToSave(request, archivo));

		actualizarUltimaModificacion(idDictamen);

		StringBuilder builder = new StringBuilder();

		builder.append("Id del dictamen: ").append(request.idDictamen()).append(" | ")
				.append("Número de oficio de solictud de factura: ").append(model.getNoOficioSolicitud()).append(" | ")
				.append("Documento cargado: ").append(nombreArchivo);



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_SOLICITUD_FACTURA.getIdSeccionPista(), builder.toString(), Optional.empty());

		return this.solicitudFacturaHelper.mapToResponse(this.solicitudFacturaRepository.save(model));
	}

	private void comprobarObligatorio(Archivo archivo, boolean carga) {

		if (archivo != null && archivo.isObligatorio() && !carga && !archivo.isCargado()) {
			throw new CatalogoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	@Override
	public SolicitudFacturaResponseDto solicitudFacturaProformaActualizar(SolicitudFacturaUpdateDto request) {
		Long idDictamen = request.idDictamen();

		SolicitudFacturaModel model = this.solicitudFacturaRepository.findByDictamenId(idDictamen)
				.orElseThrow(() -> new DevengadosException(ErroresEnum.ERROR_SOLICITUD_FACTURA_NO_ENCONTRADO));

		if (request.noOficioSolicitud() != null && !request.noOficioSolicitud().isBlank())
			model.setNoOficioSolicitud(request.noOficioSolicitud());
		if (request.fechaSolicitud() != null)
			model.setFechaSolicitud(request.fechaSolicitud().atStartOfDay());
		if (request.fechaRecepcionFactura() != null)
			model.setFechaRecepcionFactura(request.fechaRecepcionFactura().atStartOfDay());
		if (request.banderaFactura() != null)
			model.setBanderaFactura(request.banderaFactura());
		if (request.documento() != null) {
			MultipartFile documento = request.documento();
			String nombreArchivo = (documento.getOriginalFilename()).replace(" ", "_"); // <-- Espacios X guines
			Dictamen dicta = this.dictamenRepository.findByIdDictamen(idDictamen)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_CONTRATO_VACIO));

			ArchivoPlantillaDictamenModel archivo = this.archivoRepo
					.findByDescripcionContainingAndCarpetaPlantillaModelIdDictamen(
							"Oficio de Solicitud al proveedor para emitir factura", idDictamen)
					.orElseThrow(() -> new CatalogoException(ErroresEnum.ARCHIVO_NOCARGADO));

			String rutaBase = this.dictamenService.generarRutaProforma(dicta.getIdDictamenVisual(),
					dicta.getContratoModel());
			String rutaModificada = rutaBase + nombreArchivo;

			if (archivo.getRuta() == null || !archivo.getRuta().equalsIgnoreCase(rutaModificada))
				archivo.setRuta(rutaModificada);

			archivo.setCargado(Boolean.TRUE);
			archivo.setTamanoMb((double) documento.getSize() / (1024 * 1024));
			archivo.setFechaDocumento(model.getFechaSolicitud());
			archivo.setFechaModificacion(DateUtil.horaActual());
			actualizarUltimaModificacion(idDictamen);
			this.fileService.subirArchivoANexus(documento, rutaBase, nombreArchivo);

			model.setArchivoPdf(archivo);
		}

		// Registrar pista de auditoría
		StringBuilder builder = new StringBuilder();
		builder.append("Id del dictamen: ").append(model.getDictamenId()).append(" | ")
				.append("Número de oficio de solicitud de factura: ").append(model.getNoOficioSolicitud()).append(" | ")
				.append("Fecha Recepción Factura: ").append(model.getFechaRecepcionFactura()).append(" | ");

		if (request.documento() != null) {
			builder.append("Documento cargado: ").append(request.documento().getOriginalFilename());
		}



		// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.DICTAMEN_SOLICITUD_FACTURA.getIdSeccionPista(), builder.toString(), Optional.empty());

		return this.solicitudFacturaHelper.mapToResponse(model);
	}

	public void validaDictamenProforma(DictamenId dictamenId) {
		try {
			// FA12 VALIDAR DICTAMEN
			// Cambia el estatus del dictamen a PROFORMA
			// RNA247 se debe guardar en alguna seccion ULTIMA MODIFICION + NOMBRE EMPLEADO
			// SAT + FECHA Y HORA

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_DATOS_GENERALES.getIdSeccionPista(), // se tiene que crear la seccion

			// // Deducciones, descuentos y

			// // penalizaciones

			// dictamenId.getIdDictamen() + "|" + "estatusProforma", Optional.empty());
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
		}
	}

	public void rechazarDictamenProforma(DictamenId dictamenId) {
		try {
			// FA04 RECHAZAR DICTAMEN
			// Guarda en Dictamen cambio de estatus a inicial y
			// DescripcionDictamen|Justificacion

			// pistaService.guardarPista(ModuloPista.DICTAMEN.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.DICTAMEN_DEDUCCIONES_DESCUENTOS.getIdSeccionPista(),

			// dictamenId.getIdDictamen() + "|" + "Inicial", Optional.empty());
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_GUARDAR_PISTA, e);
		}
	}

	@Override
	public List<DesgloceProformaDto> obtenerCatalogoDesglose() {
		List<DesgloceProformaDto> listaDesgloce = new ArrayList<>();
		try {
			List<BaseCatalogoModel> baseCatalogoModelList = catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.DESGLOCE.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
			String cadena = baseCatalogoModelList.toString();
			String[] arreglo = cadena.substring(1, cadena.length() - 1).split("}, ");
			for (String string : arreglo) {
				DesgloceProformaDto desgloceProformaDto = new DesgloceProformaDto();

				String idDesgloce = string.replaceAll(".*primaryKey=(.*?),.*", "$1");
				String nombre = string.replaceAll(".*nombre=(.*?),.*", "$1");
				String status = string.replaceAll(".*estatus=(.*?),.*", "$1");
				String descripcion = string.replaceAll(".*descripcion=(.*?),.*", "$1");

				desgloceProformaDto.setDescripcion(descripcion);
				desgloceProformaDto.setNombre(nombre);
				desgloceProformaDto.setIdDesgloce(Integer.parseInt(idDesgloce));
				desgloceProformaDto.setEstatus(Boolean.parseBoolean(status));
				listaDesgloce.add(desgloceProformaDto);
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
		return listaDesgloce;
	}

	public List<ObtenerPenaContractualDto> obtenerDescuentos(Long dictamenId) {
		List<ObtenerPenaContractualDto> listaDescuentos = new ArrayList<>();
		try {
			List<DescuentosModel> descuentosModelList = descuentosRepository.findByDictamenId(dictamenId);
			for (DescuentosModel descuentosModel : descuentosModelList) {
				ObtenerPenaContractualDto descuento = new ObtenerPenaContractualDto();
				descuento.setDictamenId(descuentosModel.getDictamenId());
				descuento.setIdDesglose(descuentosModel.getIdDesgloce());
				descuento.setMonto(descuentosModel.getMonto());
				descuento.setIdPenaPrimary(descuentosModel.getIdDescuento());
				listaDescuentos.add(descuento);
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
		return listaDescuentos;
	}

	// busqueda
	@Override
	public SolicitudFacturaBanderaDto buscarPorIdDictamen(Long idDictamen) {
		SolicitudFacturaModel solicitudFactura = solicitudFacturaRepository.findByDictamenId(idDictamen).orElse(null);

		if (solicitudFactura == null) {
			return null;
		}

		boolean tieneServicios = banderaPagadoFacturaRespository.existeServiciosParaDictamen(idDictamen).orElse(false);

		return new SolicitudFacturaBanderaDto(solicitudFactura, tieneServicios);

	}

	@Override
	public String solicitudFaturaDescargarOficio(String path) {
		if (path == null || path.isEmpty() || path.isBlank()) {
			return "";
		}

		String archivoDescargado = this.fileService.descargarArchivo(path);
		if (archivoDescargado == null || archivoDescargado.isEmpty()) {
			return "";
		}
		return archivoDescargado;
	}

	@Override
	public SolicitudFacturaModel validarSolicitudFacrura(Long idDictamen) {
		SolicitudFacturaModel solicitudFacturaModel = this.solicitudFacturaRepository.findByDictamenId(idDictamen)
				.orElseThrow(() -> new DevengadosException(ErroresEnum.ERROR_SOLICITUD_FACTURA_NO_ENCONTRADO));

		LocalDateTime fechaSolicitud = solicitudFacturaModel.getFechaSolicitud();
		LocalDateTime fechaRecepcion = solicitudFacturaModel.getFechaRecepcionFactura();

		if (fechaRecepcion == null || fechaRecepcion.isBefore(fechaSolicitud))
			throw new DevengadosException(ErroresEnum.ERROR_SOLICITUD_FACTURA_FECHA_RECEP_MENOR);

		solicitudFacturaModel.setBanderaFactura(Boolean.TRUE);
		return solicitudFacturaModel;
	}

}
