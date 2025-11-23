package com.sisecofi.proyectos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.RequestMultiple;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.ConsultaProveedorDto;
import com.sisecofi.proyectos.dto.ProyectoProveedorRequestDto;
import com.sisecofi.proyectos.dto.ProyectoProveedorResponseDto;
import com.sisecofi.proyectos.microservicio.ProveedorMicroservicio;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.model.ProyectoProveedorModel;
import com.sisecofi.proyectos.repository.ProyectoProveedorRepository;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ProyectoProveedorService;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProyectoProveedorServiceImpl implements ProyectoProveedorService {

	private final ProyectoProveedorRepository proyectoProveedorRepository;
	private final ProyectoRepository proyectoRepository;
	private final PistaService pistaService;
	private final ProveedorMicroservicio proveedorMicroservicio;
	private final ServicioProyecto servicioProyecto;

	@Override
public List<ProyectoProveedorResponseDto> getProveedoresAsignados(Long idProyecto) {
    log.info("Obteniendo lista de proveedores asignados al proyecto {}", idProyecto);

    List<ProyectoProveedorModel> proveedoresAsignados = proyectoProveedorRepository
            .findByProyectoModelIdProyectoAndEstatusOrderByIdProyectoProveedorAsc(idProyecto, true);

    for (ProyectoProveedorModel proyectoProveedorModel : proveedoresAsignados) {
    	log.info("proveedor: {}", proyectoProveedorModel.getProveedorModel().getNombreProveedor());
	}

    // Mapeo de datos a DTO
    List<ProyectoProveedorResponseDto> lista = proveedoresAsignados.stream()
            .map(data -> {
                ProyectoProveedorResponseDto dto = new ProyectoProveedorResponseDto();
                dto.setId(data.getIdProyectoProveedor());
                dto.setIdProyecto(data.getProyectoModel().getIdProyecto());
                dto.setIdProveedor(data.getProveedorModel().getIdProveedor());
                dto.setProveedorNombre(data.getProveedorModel().getNombreProveedor());
                dto.setProyectoNombreCorto(data.getProyectoModel().getNombreCorto());
                dto.setIdInvestigacionMercado(data.getIdInvestigacionMercado());
                dto.setFechaIm(data.getFechaIm());
                dto.setRespuestaIm(data.getIdRespuestaIm());
                dto.setFechaRespuestaIm(data.getFechaRespuestaIm());
                dto.setJuntaAclaracion(data.getIdJuntaAclaracion());
                dto.setFechaJa(data.getFechaJa());
                dto.setLicitacionPublica(data.getIdLicitacionPublica());
                dto.setFechaLp(data.getFechaLp());
                dto.setComentario(data.getComentario());
                return dto;
            })
            .toList(); 

    // Construcción de la cadena de proveedores
    String proveedores = lista.stream()
            .map(ProyectoProveedorResponseDto::getProveedorNombre)
            .collect(Collectors.joining(" |"));

    // Registro de pista de auditoría
    pistaService.guardarPista(
            ModuloPista.PROYECTOS.getId(),
            TipoMovPista.CONSULTA_REGISTRO.getId(),
            TipoSeccionPista.PROYECTO_DATOS_PARTICIPACION_PROVEDORES.getIdSeccionPista(),
            proveedores,
            Optional.empty()
    );

    return lista;
}

	@Transactional
	@Override
	public List<ProyectoProveedorResponseDto> guardarProveedoresAsignados(
			RequestMultiple<ProyectoProveedorRequestDto, Long> multiple) {
		List<ProyectoProveedorResponseDto> proveedoresAsignados = new ArrayList<>();
		for (ProyectoProveedorRequestDto pro : multiple.getAgregarModificar()) {
			ProyectoProveedorModel proveedorPorAsignar = new ProyectoProveedorModel();
			Optional<ProyectoModel> proyectoModel = proyectoRepository.findById(pro.getIdProyecto());
			if (proyectoModel.isPresent()) {
				proveedorPorAsignar.setIdProveedor(pro.getIdProveedor());
				proveedorPorAsignar.setIdProyectoProveedor(pro.getId());
				proveedorPorAsignar.setProyectoModel(proyectoModel.get());
				proveedorPorAsignar.setIdInvestigacionMercado(pro.getIdInvestigacionMercado());
				proveedorPorAsignar.setFechaIm(pro.getFechaIM());
				proveedorPorAsignar.setIdRespuestaIm(pro.getRespuestaIm());
				proveedorPorAsignar.setFechaRespuestaIm(pro.getFechaRespuestaIM());
				proveedorPorAsignar.setIdJuntaAclaracion(pro.getJuntaAclaracion());
				proveedorPorAsignar.setFechaJa(pro.getFechaJA());
				proveedorPorAsignar.setIdLicitacionPublica(pro.getLicitacionPublica());
				proveedorPorAsignar.setFechaLp(pro.getFechaLP());
				proveedorPorAsignar.setComentario(pro.getComentario());
				proveedorPorAsignar.setEstatus(true);
				validarCamposDependientes(proveedorPorAsignar);
				ProyectoProveedorModel proveedorDB = proyectoProveedorRepository.save(proveedorPorAsignar);
				ProyectoProveedorResponseDto dto = new ProyectoProveedorResponseDto();
				dto.setId(proveedorDB.getIdProyectoProveedor());
				dto.setIdProyecto(proveedorDB.getProyectoModel().getIdProyecto());
				dto.setProyectoNombreCorto(proveedorDB.getProyectoModel().getNombreCorto());
				ConsultaProveedorDto consultaProveedorDto = proveedorMicroservicio
						.buscarProveedorById(pro.getIdProveedor());
				dto.setIdProveedor(consultaProveedorDto.getIdProveedor());
				dto.setProveedorNombre(consultaProveedorDto.getNombreProveedor());
				dto.setIdInvestigacionMercado(proveedorDB.getIdInvestigacionMercado());
				dto.setFechaIm(proveedorDB.getFechaIm());
				dto.setRespuestaIm(proveedorDB.getIdRespuestaIm());
				dto.setFechaRespuestaIm(proveedorDB.getFechaRespuestaIm());
				dto.setJuntaAclaracion(proveedorDB.getIdJuntaAclaracion());
				dto.setFechaJa(proveedorDB.getFechaJa());
				dto.setLicitacionPublica(proveedorDB.getIdLicitacionPublica());
				dto.setFechaLp(proveedorDB.getFechaLp());
				proveedoresAsignados.add(dto);
				
				servicioProyecto.actualizarUltimaModificacion(proveedorDB.getProyectoModel().getIdProyecto());
				
				if (pro.getId() == null) {
					// Se inserto proveedor:
					pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),
							TipoSeccionPista.PROYECTO_DATOS_PARTICIPACION_PROVEDORES.getIdSeccionPista(),
							Constantes.getDatosProveedor()[8]+dto.getProveedorNombre()+ obtenerDatosPistas(dto),Optional.empty());
				} else {
					// Se actualizo proveedor:
					pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),
							TipoSeccionPista.PROYECTO_DATOS_PARTICIPACION_PROVEDORES.getIdSeccionPista(),
							Constantes.getDatosProveedor()[8]+dto.getProveedorNombre()+ obtenerDatosPistas(dto),Optional.empty());
				}
			}
		}
		for (Long idEliminar : multiple.getEliminar()) {
			if (idEliminar != null) {
				ProyectoProveedorModel prov= proyectoProveedorRepository.findByIdProyectoProveedor(idEliminar);
				prov.setEstatus(false);
				proyectoProveedorRepository.save(prov);
				pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
						TipoSeccionPista.PROYECTO_DATOS_PARTICIPACION_PROVEDORES.getIdSeccionPista(),
						Constantes.getDatosProveedor()[8] + 
						(prov.getProveedorModel() != null ? prov.getProveedorModel().getNombreProveedor() : "") + 
						obtenerDatosPistasModel(prov), Optional.empty());

			}
		} 
		return proveedoresAsignados;
	}

	private void validarCamposDependientes(ProyectoProveedorModel proveedor) {
	    validarPar(proveedor.getIdInvestigacionMercado(), proveedor.getFechaIm());
	    validarPar(proveedor.getIdRespuestaIm(), proveedor.getFechaRespuestaIm());
	    validarPar(proveedor.getIdJuntaAclaracion(), proveedor.getFechaJa());
	    validarPar(proveedor.getIdLicitacionPublica(), proveedor.getFechaLp());
	}

	private void validarPar(Integer id, Object fecha) {
		if (Objects.equals(id, 1) && fecha == null) {
	    	throw new ProyectoException(ErroresEnum.ERROR_DATOS_OBLIGATORIOS);
	    }
	}

	
	private String obtenerDatosPistasModel(ProyectoProveedorModel dto) {
	    StringBuilder datos = new StringBuilder();
	    String[] datosProveedor = Constantes.getDatosProveedor();
	    String[] respuestas = Constantes.getRespuesta();

	    Integer idInvestigacionMercado = dto.getIdInvestigacionMercado();
	    datos.append(agregarDato(datosProveedor[0], 
	        (idInvestigacionMercado != null && idInvestigacionMercado > 0 && idInvestigacionMercado <= respuestas.length) ? 
	        respuestas[idInvestigacionMercado - 1] : ""));
	    datos.append(agregarDato(datosProveedor[1], formatoFecha(dto.getFechaIm())));
	    Integer idRespuestaIm = dto.getIdRespuestaIm();
	    if (idRespuestaIm != null && idRespuestaIm > 0 && idRespuestaIm <= respuestas.length) {
	        datos.append(agregarDato(datosProveedor[2], respuestas[idRespuestaIm - 1]));
	    } else {
	        datos.append(agregarDato(datosProveedor[2], ""));
	    }	    datos.append(agregarDato(datosProveedor[3], formatoFecha(dto.getFechaRespuestaIm())));
	    Integer idJuntaAclaracion = dto.getIdJuntaAclaracion();
	    datos.append(agregarDato(datosProveedor[4], 
	        (idJuntaAclaracion != null && idJuntaAclaracion > 0 && idJuntaAclaracion <= respuestas.length) ? 
	        respuestas[idJuntaAclaracion - 1] : ""));

	    datos.append(agregarDato(datosProveedor[5], 
	        dto.getFechaJa() != null ? formatoFecha(dto.getFechaJa()) : ""));

	    Integer idLicitacionPublica = dto.getIdLicitacionPublica();
	    datos.append(agregarDato(datosProveedor[6], 
	        (idLicitacionPublica != null && idLicitacionPublica > 0 && idLicitacionPublica <= respuestas.length) ? 
	        respuestas[idLicitacionPublica - 1] : ""));

	    datos.append(agregarDato(datosProveedor[7], 
	        dto.getFechaLp() != null ? formatoFecha(dto.getFechaLp()) : ""));

	    datos.append(agregarDato(datosProveedor[9], 
	        dto.getComentario() != null ? dto.getComentario() : ""));
	    return datos.toString();
	}
	
	private int safeIndex(Integer value, String[] respuestas) {
        return (value != null) ? value - 1 : respuestas.length - 1;
    }
	
	private String obtenerDatosPistas(ProyectoProveedorResponseDto dto) {
	    StringBuilder datos = new StringBuilder();
	    String[] datosProveedor = Constantes.getDatosProveedor();
	    String[] respuestas = Constantes.getRespuesta();	    

	    datos.append(agregarDato(datosProveedor[0], respuestas[safeIndex(dto.getIdInvestigacionMercado(), respuestas)]));
	    datos.append(agregarDato(datosProveedor[1], formatoFecha(dto.getFechaIm() != null ? dto.getFechaIm() : null)));
	    datos.append(agregarDato(datosProveedor[2], respuestas[safeIndex(dto.getRespuestaIm(), respuestas)]));
	    datos.append(agregarDato(datosProveedor[3], formatoFecha(dto.getFechaRespuestaIm() != null ? dto.getFechaRespuestaIm() : null)));
	    datos.append(agregarDato(datosProveedor[4], respuestas[safeIndex(dto.getJuntaAclaracion(), respuestas)]));
	    datos.append(agregarDato(datosProveedor[5], formatoFecha(dto.getFechaJa() != null ? dto.getFechaJa() : null)));
	    datos.append(agregarDato(datosProveedor[6], respuestas[safeIndex(dto.getLicitacionPublica(), respuestas)]));
	    datos.append(agregarDato(datosProveedor[7], formatoFecha(dto.getFechaLp() != null ? dto.getFechaJa(): null)));
	    datos.append(agregarDato(datosProveedor[9], dto.getComentario()));

	    return datos.toString();

	}

	private String agregarDato(String campo, String valor) {
	    return " |" + campo + (valor != null ? valor : "");
	}
	
	private String formatoFecha(LocalDate fecha) {
	    if (fecha == null) {
	        return "";
	    }
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    return fecha.format(formatter);
	}
	
	@Transactional
	@Override
	public boolean eliminarProveedorAsignado(Long id) {
		ProyectoProveedorModel prov= proyectoProveedorRepository.findByIdProyectoProveedor(id);
		prov.setEstatus(false);
		
		servicioProyecto.actualizarUltimaModificacion(prov.getProyectoModel().getIdProyecto());
		
		proyectoProveedorRepository.save(prov);
		pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
				TipoSeccionPista.PROYECTO_DATOS_PARTICIPACION_PROVEDORES.getIdSeccionPista(),
				Constantes.getDatosProveedor()[8]+prov.getProveedorModel().getNombreProveedor()+" |"+ obtenerDatosPistasModel(prov) ,Optional.empty());
		return true;
	}

}
