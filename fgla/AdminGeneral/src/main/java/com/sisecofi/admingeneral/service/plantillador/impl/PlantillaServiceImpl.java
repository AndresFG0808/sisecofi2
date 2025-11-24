package com.sisecofi.admingeneral.service.plantillador.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.sisecofi.admingeneral.util.exception.PlantilladorException;
import org.springframework.stereotype.Service;

import com.sisecofi.admingeneral.dto.plantillador.ContenidoPlantillaDto;
import com.sisecofi.admingeneral.dto.plantillador.PlantilladorDto;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.libreria.comunes.model.plantillador.CatSubTipoPlantillador;
import com.sisecofi.admingeneral.model.plantillador.ContenidoSubPlantilladorBaseModel;
import com.sisecofi.admingeneral.model.plantillador.ContenidoVariblesAyuda;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoSubPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorDatosModel;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import com.sisecofi.admingeneral.repository.plantillador.CatSubTipoPlantilladorRepository;
import com.sisecofi.admingeneral.repository.plantillador.CatTipoPlantillaRepository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoPlantillaBaseRespository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoPlantillaRepository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoSubPlantillaBaseRespository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoSubPlantilladorRespository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoVariablesRespository;
import com.sisecofi.admingeneral.repository.plantillador.PlantillaRespository;
import com.sisecofi.admingeneral.repository.plantillador.SubPlantilladorDatosRespository;
import com.sisecofi.admingeneral.repository.plantillador.SubPlantilladorRespository;
import com.sisecofi.admingeneral.service.adminpistas.PistaService;
import com.sisecofi.admingeneral.service.plantillador.HtmlWordService;
import com.sisecofi.admingeneral.service.plantillador.PlantillaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.exception.PlantillaException;
import com.sisecofi.libreria.comunes.model.plantillador.CatTipoPlantillador;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorBaseModel;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoBase;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantillaServiceImpl implements PlantillaService {

	private static final String CARPETA = "carpeta";
	private static final String SUB_CARPETA = "subCarpeta";
	private static final String PLANTILLA = "plantilla";
	private static final String PLANTILLA_HOJA = "plantilla-hoja";
	private static final String PLANTILLADOR = "Plantillador";

	private final PlantillaRespository plantillaRespository;
	private final HtmlWordService htmlWordService;
	private final ContenidoPlantillaRepository contenidoPlantillaRepository;
	private final CatTipoPlantillaRepository catTipoPlantillaRepository;
	private final CatSubTipoPlantilladorRepository catSubTipoPlantilladorRepository;
	private final SubPlantilladorRespository subPlantilladorRespository;
	private final ContenidoSubPlantilladorRespository contenidoSubPlantilladorRespository;
	private final ContenidoPlantillaBaseRespository contenidoPlantillaBaseRespository;
	private final ContenidoSubPlantillaBaseRespository contenidoSubPlantillaBaseRespository;
	private final SubPlantilladorDatosRespository subPlantilladorDatosRespository;
	private final ContenidoVariablesRespository contenidoVariablesRespository;
	private final PistaService pistaService;

	@Override
	public PlantilladorDto buscarPlantillas(Integer idTipoPlantilla) {
		PlantilladorDto pp = new PlantilladorDto();
		Optional<CatTipoPlantillador> op = catTipoPlantillaRepository.findById(idTipoPlantilla);
		if (op.isPresent()) {
			PlantilladorDto p = new PlantilladorDto();
			p.setTipo(CARPETA);
			p.setComentarios(op.get().getDescripcion());
			p.setEstatus(op.get().isEstatus());
			p.setFechaModificacion(op.get().getFechaModificacion());
			p.setNombre(op.get().getNombre());
			p.setIdTipoPlantillador(op.get().getIdTipoPlantillador());
			List<CatSubTipoPlantillador> sub = catSubTipoPlantilladorRepository
					.findByCatTipoPlantilladorIdTipoPlantillador(op.get().getIdTipoPlantillador());
			if (!sub.isEmpty()) {
				p.setTipo(CARPETA);
				p.setComentarios(op.get().getDescripcion());
				p.setEstatus(op.get().isEstatus());
				p.setFechaModificacion(op.get().getFechaModificacion());
				p.setNombre(op.get().getNombre());
				p.setIdTipoPlantillador(op.get().getIdTipoPlantillador());

				List<PlantilladorDto> p2 = new ArrayList<>();
				p.setSubRows(p2);
				return p;

			} else {
				pp.setTipo(CARPETA);
				pp.setComentarios(op.get().getDescripcion());
				pp.setEstatus(op.get().isEstatus());
				pp.setFechaModificacion(op.get().getFechaModificacion());
				pp.setIdTipoPlantillador(op.get().getIdTipoPlantillador());
				pp.setNombre(op.get().getNombre());
				List<PlantilladorDto> subRows = new ArrayList<>();
				for (PlantilladorModel m : plantillaRespository
						.findByCatTipoPlantilladorIdTipoPlantillador(op.get().getIdTipoPlantillador())) {
					PlantilladorDto plantilla = new PlantilladorDto();
					plantilla.setTipo(PLANTILLA);
					plantilla.setComentarios(m.getComentarios());
					plantilla.setEstatus(m.isStatus());
					plantilla.setFechaModificacion(m.getFechaModificacion());
					plantilla.setIdPlantillador(m.getIdPlantillador());
					plantilla.setNombre(m.getNombre());
					subRows.add(plantilla);
				}
				pp.setSubRows(subRows);
			}
		}
		return pp;
	}

	@Override
	public List<PlantilladorDto> buscarPlantillas() {
		List<PlantilladorDto> result = new ArrayList<>();
		List<CatTipoPlantillador> cat = catTipoPlantillaRepository.findAll();

		for (CatTipoPlantillador d : cat) {
			List<SubPlantilladorModel> op = subPlantilladorRespository
					.findByCatTipoPlantilladorIdTipoPlantillador(d.getIdTipoPlantillador());
			log.info("Tamaño sutipo: {}", op.size());

			if (!op.isEmpty()) {
				result.add(createPlantilladorWithSubPlantillas(d, op));
			} else {
				result.add(createPlantilladorWithoutSubPlantillas(d));
			}
		}

		return result;
	}

	private PlantilladorDto createPlantilladorWithSubPlantillas(CatTipoPlantillador d, List<SubPlantilladorModel> op) {
		PlantilladorDto p = new PlantilladorDto();
		p.setTipo(CARPETA);
		p.setComentarios(d.getDescripcion());
		p.setEstatus(d.isEstatus());
		p.setFechaModificacion(d.getFechaModificacion());
		p.setNombre(d.getNombre());
		p.setIdTipoPlantillador(d.getIdTipoPlantillador());

		List<PlantilladorDto> p2 = new ArrayList<>();
		for (SubPlantilladorModel a : op) {
			PlantilladorDto p11 = new PlantilladorDto();
			p11.setIdSubPlantillador(a.getIdSubPlantillador());
			p11.setComentarios(a.getComentarios());
			p11.setTipo(SUB_CARPETA);
			p11.setEstatus(a.isStatus());
			p11.setFechaModificacion(a.getFechaModificacion());
			p11.setNombre(a.getNombre());
			p11.setIdTipoPlantillador(a.getCatTipoPlantillador().getIdTipoPlantillador());

			List<PlantilladorDto> p3 = new ArrayList<>();
			for (SubPlantilladorDatosModel sub : a.getSubPlantillas()) {
				PlantilladorDto p1 = createPlantillaHoja(sub, a);
				p3.add(p1);
			}

			p11.setSubRows(p3);
			p2.add(p11);
		}

		p.setSubRows(p2);
		return p;
	}

	private PlantilladorDto createPlantilladorWithoutSubPlantillas(CatTipoPlantillador d) {
		PlantilladorDto pp = new PlantilladorDto();
		pp.setTipo(CARPETA);
		pp.setComentarios(d.getDescripcion());
		pp.setEstatus(d.isEstatus());
		pp.setFechaModificacion(d.getFechaModificacion());
		pp.setIdTipoPlantillador(d.getIdTipoPlantillador());
		pp.setNombre(d.getNombre());

		List<PlantilladorDto> subRows = new ArrayList<>();
		for (PlantilladorModel m : plantillaRespository
				.findByCatTipoPlantilladorIdTipoPlantillador(d.getIdTipoPlantillador())) {
			PlantilladorDto plantilla = new PlantilladorDto();
			plantilla.setComentarios(m.getComentarios());
			plantilla.setTipo(PLANTILLA);
			plantilla.setEstatus(m.isStatus());
			plantilla.setFechaModificacion(m.getFechaModificacion());
			plantilla.setIdPlantillador(m.getIdPlantillador());
			plantilla.setNombre(m.getNombre());
			plantilla.setIdTipoPlantillador(d.getIdTipoPlantillador());
			subRows.add(plantilla);
		}

		pp.setSubRows(subRows);
		pp.setMaxVersion(plantillaRespository
				.findFirstByCatTipoPlantilladorIdTipoPlantilladorOrderByVersionDesc(d.getIdTipoPlantillador())
				.map(PlantilladorModel::getVersion).orElse("0"));

		return pp;
	}

	private PlantilladorDto createPlantillaHoja(SubPlantilladorDatosModel sub, SubPlantilladorModel a) {
		PlantilladorDto p1 = new PlantilladorDto();
		p1.setTipo(PLANTILLA_HOJA);
		p1.setIdTipoPlantillador(a.getCatTipoPlantillador().getIdTipoPlantillador());
		p1.setComentarios(sub.getComentarios());
		p1.setEstatus(sub.isStatus());
		p1.setFechaModificacion(sub.getFechaModificacion());
		p1.setIdSubPlantillador(sub.getIdSubPlantilladorDatos());
		p1.setNombre(sub.getNombre());

		ContenidoSubPlantilladorModel obj = sub.getContenido();
		if (obj != null) {
			p1.setIdContenidoSubPlantillador(obj.getIdContenidoSubPlantillador());
		}

		return p1;
	}

	@Override
	public ContenidoPlantillaDto obtenerContenidoPlantilladorDto(PlantilladorDto dto) {
		Long idSubPlantillador = dto.getIdSubPlantillador();
		Long idPlantillador = dto.getIdPlantillador();

		ContenidoPlantillaDto contenidoDto = new ContenidoPlantillaDto();
		ContenidoBase contenido = new ContenidoBase();

		if (idPlantillador != null && idSubPlantillador == null) {
			contenido = contenidoPlantillaRepository.findByplantillaModelPlantilladorModelIdPlantillador(idPlantillador)
					.orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
			contenidoDto.setId(idPlantillador);
			Optional<PlantilladorModel> datos = plantillaRespository.findById(idPlantillador);
			if (datos.isPresent()) {
				contenidoDto.setNombre(datos.get().getNombre());
				contenidoDto.setComentarios(datos.get().getComentarios());

			}
			contenidoDto.setTipo(PLANTILLADOR);

		} else if (idSubPlantillador != null && idPlantillador == null) {
			contenido = contenidoSubPlantilladorRespository
					.findBySubPlantilladorDatosModelIdSubPlantilladorDatos(idSubPlantillador);
			Optional<SubPlantilladorDatosModel> datos = subPlantilladorDatosRespository.findById(idSubPlantillador);
			if (datos.isPresent()) {
				contenidoDto.setNombre(datos.get().getNombre());
				contenidoDto.setComentarios(datos.get().getComentarios());

			}
			contenidoDto.setId(idSubPlantillador);
			contenidoDto.setTipo("SubPlantillador");
		}
		contenidoDto.setHeader(contenido.getHeader());
		contenidoDto.setFooter(contenido.getFooter());
		contenidoDto.setContenido(contenido.getContenido());

		return contenidoDto;
	}

	@Override
	public ContenidoPlantillaDto guardarInformacionPlantilla(ContenidoPlantillaDto dto) {
		ContenidoBase contenido;
		String nombrePlantilla = "";

		if (dto.getTipo().equals(PLANTILLADOR)) {
			contenido = contenidoPlantillaRepository.findByplantillaModelPlantilladorModelIdPlantillador(dto.getId())
					.orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
			contenido.setContenido(dto.getContenido());
			contenido.setHeader(dto.getHeader());
			contenido.setFooter(dto.getFooter());
			ContenidoPlantilladorModel contenidoCon = (ContenidoPlantilladorModel) contenido;
			nombrePlantilla = contenidoCon.getPlantillaModelPlantilladorModel().getNombre();
			contenidoPlantillaRepository.save(contenidoCon);

			Optional<PlantilladorModel> datosOp = plantillaRespository.findById(dto.getId());
			if (datosOp.isPresent()) {
			    PlantilladorModel datos = datosOp.get();

			    Optional<PlantilladorModel> existente = plantillaRespository
			        .findByNombre(dto.getNombre());

			    if (existente.isPresent() && !Objects.equals(existente.get().getIdPlantillador(), datos.getIdPlantillador())) {
			    	throw new PlantilladorException(ErroresEnum.ERROR_NOMBRE_PLANTILLA_DUPLICADO);
			    } else {
			        datos.setComentarios(dto.getComentarios());
			        datos.setNombre(dto.getNombre());
			        datos.setFechaModificacion(horaActual());
			        plantillaRespository.save(datos);
			    }
			}


		} else if (dto.getTipo().equals("SubPlantillador")) {
			nombrePlantilla= guardarSubPlantillador(dto);

		}



		// pistaService.guardarPista(ModuloPista.PLANTILLAS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


		// TipoSeccionPista.PLANTILLAS.getIdSeccionPista(),


		// Constantes.getDescargarPlantillaFase()[1] + nombrePlantilla + Constantes.getDescargarPlantillaFase()[4],


		// Optional.empty());
		return dto;
	}
	
	
	private String guardarSubPlantillador( ContenidoPlantillaDto dto) {
		ContenidoBase contenido = contenidoSubPlantilladorRespository
				.findBySubPlantilladorDatosModelIdSubPlantilladorDatos(dto.getId());
		contenido.setContenido(dto.getContenido());
		contenido.setHeader(dto.getHeader());
		contenido.setFooter(dto.getFooter());
		ContenidoSubPlantilladorModel contenidoCon = (ContenidoSubPlantilladorModel) contenido;
		String nombrePlantilla = contenidoCon.getSubPlantilladorDatosModel().getNombre();
		contenidoSubPlantilladorRespository.save(contenidoCon);

		Optional<SubPlantilladorDatosModel> datosOp = subPlantilladorDatosRespository.findById(dto.getId());
		if (datosOp.isPresent()) {
		    SubPlantilladorDatosModel datos = datosOp.get();

		    Optional<SubPlantilladorDatosModel> existente = subPlantilladorDatosRespository
		        .findByNombre(dto.getNombre());

		    if (existente.isPresent() && !Objects.equals(existente.get().getIdSubPlantilladorDatos(), datos.getIdSubPlantilladorDatos())) {
		    	throw new PlantilladorException(ErroresEnum.ERROR_NOMBRE_PLANTILLA_DUPLICADO);
		    } else {
		        datos.setComentarios(dto.getComentarios());
		        datos.setNombre(dto.getNombre());
		        datos.setFechaModificacion(horaActual());
		        subPlantilladorDatosRespository.save(datos);
		    }
		}
		return nombrePlantilla;
	}
	@Override
	public List<SubPlantilladorModel> obtenerSubPlantilladores() {
		try {
			return subPlantilladorRespository.findByStatusTrue();
		} catch (Exception e) {
			throw new PlantilladorException(ErroresEnum.ERROR_AL_CONSULTAR_TITULO);
		}
	}

	@Override
	public ContenidoBase obtenerContenidoPlantillador(Long idPlantillador, Long idSubPlantillador) {

		if (idPlantillador != null) {
			return contenidoPlantillaRepository.findByplantillaModelPlantilladorModelIdPlantillador(idPlantillador)
					.orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
		} else if (idSubPlantillador != null) {
			return contenidoSubPlantilladorRespository
					.findBySubPlantilladorDatosModelIdSubPlantilladorDatos(idSubPlantillador);
		}

		return null;
	}

	@Override
	@Transactional
	public boolean guardarPlantillas(RequestPlantilla requestPlantilla) {
		Optional<PlantilladorModel> op1;
		if (requestPlantilla.getIdPlantillador() != null) {
			op1 = plantillaRespository.findById(requestPlantilla.getIdPlantillador());

			PlantilladorModel plantilladorModel = plantillaRespository
					.findByIdPlantillador(requestPlantilla.getIdPlantillador()).orElse(null);

			if (plantilladorModel != null) {
				// falta guardar comentarios y nombre
				plantilladorModel.setNombre(requestPlantilla.getNombre());
				plantilladorModel.setComentarios(requestPlantilla.getComentarios());
				plantilladorModel.setFechaModificacion(LocalDateTime.now());
				plantillaRespository.save(plantilladorModel);

				ContenidoPlantilladorModel s = new ContenidoPlantilladorModel();
				if (plantilladorModel.getContenidoPlantilladorModel() != null) {
					s = plantilladorModel.getContenidoPlantilladorModel();
				}
				s.setContenido(requestPlantilla.getContenido());
				s.setFooter(requestPlantilla.getFooter());
				s.setHeader(requestPlantilla.getHeader());
				if (op1.isPresent()) {
					s.setPlantillaModelPlantilladorModel(op1.get());
				} else {
					s.setPlantillaModelPlantilladorModel(null);
				}

				contenidoPlantillaRepository.save(s);
			}
		} else {
			guardarPlantillasProforma(requestPlantilla);
		}
		return true;
	}

	private void guardarPlantillasProforma(RequestPlantilla requestPlantilla) {
		Optional<SubPlantilladorDatosModel> op2;
		if (requestPlantilla.getIdSubPlantillador() != null) {
			op2 = subPlantilladorDatosRespository.findById(requestPlantilla.getIdSubPlantillador());

			if (op2.isPresent()) {
				Optional<CatSubTipoPlantillador> tip = catSubTipoPlantilladorRepository
						.findByIdSubTipoPlantillador(requestPlantilla.getIdSubTipoPlantillador());

				SubPlantilladorDatosModel plantilladorModel = op2.get();
				plantilladorModel.setNombre(requestPlantilla.getNombre());
				plantilladorModel.setComentarios(requestPlantilla.getComentarios());
				plantilladorModel.setFechaModificacion(LocalDateTime.now());
				subPlantilladorDatosRespository.save(plantilladorModel);

				if (tip.isPresent()) {
					ContenidoSubPlantilladorModel factura = plantilladorModel.getContenido();
					factura.setContenido(requestPlantilla.getContenido());
					factura.setFooter(requestPlantilla.getFooter());
					factura.setHeader(requestPlantilla.getHeader());
					factura.setSubPlantilladorDatosModel(plantilladorModel);
					plantilladorModel.setCatSubTipoPlantillador(tip.get());
					contenidoSubPlantilladorRespository.save(factura);
				}

			}

		}
	}

	private void crearHojasProforma(SubPlantilladorModel plantilladorModel, PlantilladorDto p) {
		Optional<CatSubTipoPlantillador> tip1 = catSubTipoPlantilladorRepository.findByIdSubTipoPlantillador(1L);
		Optional<CatSubTipoPlantillador> tip2 = catSubTipoPlantilladorRepository.findByIdSubTipoPlantillador(2L);
		Optional<CatSubTipoPlantillador> tip3 = catSubTipoPlantilladorRepository.findByIdSubTipoPlantillador(3L);

		Optional<ContenidoSubPlantilladorBaseModel> base1 = contenidoSubPlantillaBaseRespository
				.findByCatTipoPlantilladorIdSubTipoPlantillador(1L);
		Optional<ContenidoSubPlantilladorBaseModel> base2 = contenidoSubPlantillaBaseRespository
				.findByCatTipoPlantilladorIdSubTipoPlantillador(2L);
		Optional<ContenidoSubPlantilladorBaseModel> base3 = contenidoSubPlantillaBaseRespository
				.findByCatTipoPlantilladorIdSubTipoPlantillador(3L);

		List<PlantilladorDto> lista = p.getSubRows();

		if (tip1.isPresent() && base1.isPresent()) {
			SubPlantilladorDatosModel sub = plantilladorModel.getContenidoByCatSubTipoPlantillador(1L);
			sub.setNombre(lista.get(0).getNombre());
			sub.setComentarios(lista.get(0).getComentarios());
			sub.setFechaModificacion(horaActual());
			sub.setVersion(lista.get(0).getVersion());
			sub.setStatus(true);
			sub.setSubPlantilladorModel(plantilladorModel);
			sub = subPlantilladorDatosRespository.save(sub);

			ContenidoSubPlantilladorBaseModel base = base1.get();
			ContenidoSubPlantilladorModel factura = sub.getContenido();
			factura.setContenido(base.getContenido());
			factura.setFooter(base.getFooter());
			factura.setHeader(base.getHeader());
			factura.setSubPlantilladorDatosModel(sub);
			factura.setOrden(1);
			sub.setCatSubTipoPlantillador(tip1.get());

			contenidoSubPlantilladorRespository.save(factura);
		}

		if (tip2.isPresent() && base2.isPresent()) {
			ContenidoSubPlantilladorBaseModel base = base2.get();
			SubPlantilladorDatosModel sub = plantilladorModel.getContenidoByCatSubTipoPlantillador(2L);
			sub.setNombre(lista.get(1).getNombre());
			sub.setComentarios(lista.get(1).getComentarios());
			sub.setFechaModificacion(horaActual());
			sub.setVersion(lista.get(1).getVersion());
			sub.setStatus(true);
			sub.setSubPlantilladorModel(plantilladorModel);
			sub = subPlantilladorDatosRespository.save(sub);

			ContenidoSubPlantilladorModel nota = sub.getContenido();
			nota.setContenido(base.getContenido());
			nota.setFooter(base.getFooter());
			nota.setHeader(base.getHeader());
			nota.setSubPlantilladorDatosModel(sub);
			nota.setOrden(2);
			sub.setCatSubTipoPlantillador(tip2.get());

			contenidoSubPlantilladorRespository.save(nota);
		}

		if (tip3.isPresent() && base3.isPresent()) {
			ContenidoSubPlantilladorBaseModel base = base3.get();
			SubPlantilladorDatosModel sub = plantilladorModel.getContenidoByCatSubTipoPlantillador(3L);
			sub.setNombre(lista.get(2).getNombre());
			sub.setComentarios(lista.get(2).getComentarios());
			sub.setFechaModificacion(horaActual());
			sub.setVersion(lista.get(2).getVersion());
			sub.setStatus(true);
			sub.setSubPlantilladorModel(plantilladorModel);
			sub = subPlantilladorDatosRespository.save(sub);

			ContenidoSubPlantilladorModel pena = sub.getContenido();
			pena.setContenido(base.getContenido());
			pena.setFooter(base.getFooter());
			pena.setHeader(base.getHeader());
			pena.setSubPlantilladorDatosModel(sub);
			pena.setOrden(3);
			sub.setCatSubTipoPlantillador(tip3.get());

			contenidoSubPlantilladorRespository.save(pena);
		}
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	@Override
	public byte[] vistaPreviaPlantillas(RequestPlantilla requestPlantilla) {
		return htmlWordService.convertirHmltPdf(requestPlantilla);
	}

	@Override
	@Transactional
	public boolean guardarNuevasPlantillas(List<PlantilladorDto> requestPlantilla) {
		requestPlantilla.stream().forEach(p -> {
			if (p.getIdTipoPlantillador().equals(2)) {
				guardarSubPlantillador(p);
			} else {
				guardarPlantillador(p);
			}
		});
		return true;
	}

	private void guardarSubPlantillador(PlantilladorDto p) {
		SubPlantilladorModel nuevaProforma = obtenerSubPlantilladorExistente(p);

		Optional<CatTipoPlantillador> c = catTipoPlantillaRepository.findById(p.getIdTipoPlantillador());
		if (c.isPresent()) {
			Optional<SubPlantilladorModel> existente = subPlantilladorRespository
			        .findByNombre(p.getNombre());

			    if (existente.isPresent() && !Objects.equals(existente.get().getIdSubPlantillador(), p.getIdSubPlantillador())) {
			    	throw new PlantilladorException(ErroresEnum.ERROR_NOMBRE_PLANTILLA_DUPLICADO);
			    }
			nuevaProforma.setNombre(p.getNombre());
			nuevaProforma.setComentarios(p.getComentarios());
			nuevaProforma.setFechaModificacion(LocalDateTime.now());
			nuevaProforma.setCatTipoPlantillador(c.get());
			nuevaProforma.setStatus(p.isEstatus());
			subPlantilladorRespository.save(nuevaProforma);
			crearHojasProforma(nuevaProforma, p);
		}



		// pistaService.guardarPista(ModuloPista.PLANTILLAS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.PLANTILLAS.getIdSeccionPista(),


		// Constantes.getDescargarPlantillaFase()[1] + nuevaProforma.getNombre()


		// + Constantes.getDescargarPlantillaFase()[5] + nuevaProforma.isStatus()


		// + nuevaProforma.toString(),


		// Optional.empty());
	}

	private SubPlantilladorModel obtenerSubPlantilladorExistente(PlantilladorDto p) {
		if (p.getIdSubPlantillador() != null) {
			return subPlantilladorRespository.findById(p.getIdSubPlantillador()).orElse(new SubPlantilladorModel());
		}
		return new SubPlantilladorModel();
	}

	private void guardarPlantillador(PlantilladorDto p) {
		Optional<CatTipoPlantillador> c = catTipoPlantillaRepository.findById(p.getIdTipoPlantillador());
		if (c.isPresent()) {
			Optional<PlantilladorModel> existente = plantillaRespository
			        .findByNombre(p.getNombre());

			if (existente.isPresent() && !Objects.equals(existente.get().getIdPlantillador(), p.getIdPlantillador())) {
			    throw new PlantilladorException(ErroresEnum.ERROR_NOMBRE_PLANTILLA_DUPLICADO);
			}
			    
			PlantilladorModel plantilla = new PlantilladorModel();
			plantilla.setCatTipoPlantillador(c.get());
			plantilla.setComentarios(p.getComentarios());
			plantilla.setFechaModificacion(LocalDateTime.now());
			plantilla.setNombre(p.getNombre());
			plantilla.setStatus(p.isEstatus());
			plantilla.setVersion(p.getVersion());
			plantilla.setIdPlantillador(p.getIdPlantillador());
			plantillaRespository.save(plantilla);

			if (!p.getIdTipoPlantillador().equals(4)) {
				guardarContenidoPlantilla(plantilla, c.get().getIdTipoPlantillador());
			}



			// pistaService.guardarPista(ModuloPista.PLANTILLAS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


			// TipoSeccionPista.PLANTILLAS.getIdSeccionPista(),


			// Constantes.getDescargarPlantillaFase()[1] + plantilla.getNombre()


			// + Constantes.getDescargarPlantillaFase()[5] + plantilla.isStatus() + plantilla.toString(),


			// Optional.empty());
		}
	}

	private void guardarContenidoPlantilla(PlantilladorModel plantilla, Integer idTipoPlantillador) {
		Optional<ContenidoPlantilladorBaseModel> optional = contenidoPlantillaBaseRespository
				.findByCatTipoPlantilladorIdTipoPlantillador(idTipoPlantillador);

		if (optional.isPresent()) {
			ContenidoPlantilladorModel contenido = new ContenidoPlantilladorModel();
			contenido.setHeader(optional.get().getHeader());
			contenido.setContenido(optional.get().getContenido());
			contenido.setFooter(optional.get().getFooter());
			contenido.setPlantillaModelPlantilladorModel(plantilla);
			contenidoPlantillaRepository.save(contenido);
		}
	}

	@Override
	@Transactional
	public boolean guardarPlantillas(List<PlantilladorDto> requestPlantilla) {
		requestPlantilla.stream().forEach(p -> {
			if (p.getIdTipoPlantillador().equals(2)) {
				procesarSubPlantillador(p);
			} else {
				procesarPlantillador(p);
			}
		});
		return true;
	}

	private void procesarSubPlantillador(PlantilladorDto p) {
		SubPlantilladorModel nuevaProforma = obtenerSubPlantilladorExistente(p);

		if (nuevaProforma.getIdSubPlantillador() != null) {
			actualizarSubPlantillador(nuevaProforma, p);
		} else {
			crearNuevoSubPlantillador(nuevaProforma, p);
		}
	}

	private void actualizarSubPlantillador(SubPlantilladorModel subPlantillador, PlantilladorDto p) {
		subPlantillador.setStatus(p.isEstatus());
		subPlantillador.setFechaModificacion(LocalDateTime.now());
		
		if (p.getSubRows()!=null && !p.getSubRows().isEmpty()) {
			p.getSubRows().stream().forEach(dd -> {
				SubPlantilladorDatosModel sub = subPlantillador.getContenidoByCatSubTipoPlantillador(dd.getIdTipo());
				sub.setStatus(dd.isEstatus());
				sub.setFechaModificacion(LocalDateTime.now());
				subPlantilladorDatosRespository.save(sub);
			});
		}
		
		subPlantilladorRespository.save(subPlantillador);
		

		
		// pistaService.guardarPista(ModuloPista.PLANTILLAS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		
		// TipoSeccionPista.PLANTILLAS.getIdSeccionPista(),

		
		// Constantes.getDescargarPlantillaFase()[1] + subPlantillador.getNombre()

		
		// + Constantes.getDescargarPlantillaFase()[5] + subPlantillador.isStatus()

		
		// + subPlantillador.toString(),

		
		// Optional.empty());
	}

	private void crearNuevoSubPlantillador(SubPlantilladorModel subPlantillador, PlantilladorDto p) {
		Optional<CatTipoPlantillador> c = catTipoPlantillaRepository.findById(p.getIdTipoPlantillador());
		if (c.isPresent()) {
			subPlantillador.setNombre(p.getNombre());
			subPlantillador.setComentarios(p.getComentarios());
			subPlantillador.setFechaModificacion(LocalDateTime.now());
			subPlantillador.setCatTipoPlantillador(c.get());
			subPlantilladorRespository.save(subPlantillador);
			crearHojasProforma(subPlantillador, p);



			// pistaService.guardarPista(ModuloPista.PLANTILLAS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


			// TipoSeccionPista.PLANTILLAS.getIdSeccionPista(),


			// Constantes.getDescargarPlantillaFase()[1] + subPlantillador.getNombre()


			// + Constantes.getDescargarPlantillaFase()[5] + subPlantillador.isStatus()


			// + subPlantillador.toString(),


			// Optional.empty());
		}
	}

	private void procesarPlantillador(PlantilladorDto p) {

			Optional<CatTipoPlantillador> c = catTipoPlantillaRepository.findById(p.getIdTipoPlantillador());
			if (c.isPresent()) {
				PlantilladorModel plantilla = obtenerPlantilladorExistente(p);

				plantilla.setFechaModificacion(LocalDateTime.now());
				plantilla.setStatus(p.isEstatus());
				plantillaRespository.save(plantilla);



				// pistaService.guardarPista(ModuloPista.PLANTILLAS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


				// TipoSeccionPista.PLANTILLAS.getIdSeccionPista(),


				// Constantes.getDescargarPlantillaFase()[1] + plantilla.getNombre()


				// + Constantes.getDescargarPlantillaFase()[5] + plantilla.isStatus()


				// + plantilla.toString(),


				// Optional.empty());
			}
	}

	private PlantilladorModel obtenerPlantilladorExistente(PlantilladorDto p) {
		if (p.getIdPlantillador() != null) {
			return plantillaRespository.findByIdPlantillador(p.getIdPlantillador()).orElse(new PlantilladorModel());
		}
		return new PlantilladorModel();
	}

	@Override
	public ContenidoPlantilladorModel obtenerContenidoPlantilladorPorId(Long idContenidoPlantillador) {
		return contenidoPlantillaRepository.findByIdContenidoPlantillador(idContenidoPlantillador)
				.orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
	}

	@Override
	public List<PlantilladorModel> obtenerPlantilladoresPorTipoPlantillador(Integer idTipoPlantillador) {

		return plantillaRespository.findByCatTipoPlantilladorIdTipoPlantillador(idTipoPlantillador);
	}

	@Override
	public PlantilladorModel obtenerPlantilladoresPorIdPlantillador(Long idPlantillador) {

		return plantillaRespository.findByIdPlantillador(idPlantillador).orElse(null);
	}

	@Override
	public ContenidoPlantilladorBaseModel obtenerBase(Integer idTipo) {
		Optional<ContenidoPlantilladorBaseModel> base = contenidoPlantillaBaseRespository
				.findByCatTipoPlantilladorIdTipoPlantillador(idTipo);
		if (base.isPresent()) {
			return base.get();
		} else {
			return null;
		}
	}

	@Override
	public byte[] obtenerVariablesAyuda(ContenidoPlantillaDto dto) {
		try {
			int hoja = determinarHoja(dto);
			Optional<ContenidoVariblesAyuda> fileOptional = contenidoVariablesRespository.findById(1);

			if (fileOptional.isEmpty()) {
				throw new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
			}

			ContenidoVariblesAyuda excelFile = fileOptional.get();
			byte[] fileBytes = Base64.getDecoder().decode(excelFile.getContenido());

			return extraerHojaComoExcel(fileBytes, hoja);
		} catch (Exception e) {
			throw new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
		}
	}

	private int determinarHoja(ContenidoPlantillaDto dto) {
		if (dto.getTipo().equals(PLANTILLADOR)) {
			ContenidoPlantilladorModel base = contenidoPlantillaRepository
					.findByplantillaModelPlantilladorModelIdPlantillador(dto.getId())
					.orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));

			return base.getPlantillaModelPlantilladorModel().getCatTipoPlantillador().getIdTipoPlantillador() == 1 ? 1
					: 5;
		} else {
			SubPlantilladorDatosModel base = subPlantilladorDatosRespository.findById(dto.getId())
					.orElseThrow(() -> new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA));
			Long idSubTipoPlantillador = base.getCatSubTipoPlantillador().getIdSubTipoPlantillador();

			if (idSubTipoPlantillador != null && idSubTipoPlantillador <= Integer.MAX_VALUE) {
				return (int) (idSubTipoPlantillador + 1);
			} else {
				throw new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
			}
		}
	}

	private byte[] extraerHojaComoExcel(byte[] fileBytes, int hoja) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(fileBytes));
				Workbook newWorkbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			if (hoja < 1 || hoja > workbook.getNumberOfSheets()) {
				throw new PlantillaException(ErroresEnum.ERROR_PLANTILLA_NO_ENCONTRADA);
			}

			copiarHoja(workbook.getSheetAt(hoja - 1), newWorkbook.createSheet());
			newWorkbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}

	private void copiarHoja(Sheet sourceSheet, Sheet targetSheet) {
		for (Row sourceRow : sourceSheet) {
			Row targetRow = targetSheet.createRow(sourceRow.getRowNum());
			for (Cell sourceCell : sourceRow) {
				Cell targetCell = targetRow.createCell(sourceCell.getColumnIndex());
				copiarCelda(sourceCell, targetCell);
			}
		}
	}

	private void copiarCelda(Cell sourceCell, Cell targetCell) {
		switch (sourceCell.getCellType()) {
		case STRING -> targetCell.setCellValue(sourceCell.getStringCellValue());
		case NUMERIC -> targetCell.setCellValue(sourceCell.getNumericCellValue());
		case BOOLEAN -> targetCell.setCellValue(sourceCell.getBooleanCellValue());
		case FORMULA -> targetCell.setCellFormula(sourceCell.getCellFormula());
		case BLANK -> targetCell.setBlank();
		default -> throw new IllegalArgumentException("Unhandled cell type: " + sourceCell.getCellType());
		}

		if (sourceCell.getCellStyle() != null) {
			CellStyle newCellStyle = targetCell.getSheet().getWorkbook().createCellStyle();
			newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
			targetCell.setCellStyle(newCellStyle);
		}

		if (sourceCell.getCellComment() != null) {
			targetCell.setCellComment(sourceCell.getCellComment());
		}
	}

}
