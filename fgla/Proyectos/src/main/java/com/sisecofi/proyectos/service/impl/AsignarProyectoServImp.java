package com.sisecofi.proyectos.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.proyectos.dto.ProyectoUsuarioDto;
import com.sisecofi.proyectos.dto.UsuarioProyectoDto;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioAsignadoPk;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.repository.UsuarioProyectoRepository;
import com.sisecofi.proyectos.repository.specification.EstatusUsuarioProyectoSpec;
import com.sisecofi.proyectos.repository.specification.IdProyectoSpec;
import com.sisecofi.proyectos.repository.specification.ProyectoIdEstatusProyectoSpec;
import com.sisecofi.proyectos.repository.specification.ProyectoNombreCortoSpec;
import com.sisecofi.proyectos.service.AsignarProService;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.ProUsuConsumer;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsignarProyectoServImp implements AsignarProService {

	private static final String PISTA_GEN = "Pista generada: {}";

	private final ProyectoRepository proyectoRepository;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioProyectoRepository usuarioProyectoRepository;
	private final PistaService pistaService;
	private final ProUsuConsumer consumer;

	@Override
	public List<ProyectoNombreDto> obtenerProyectosNombreCorto(BaseCatalogoModel catalogoModel) {
		log.info("Catalogo id: {}", catalogoModel.getPrimaryKey());
		return proyectoRepository.findProyectosByEstatusDto(catalogoModel.getPrimaryKey());
	}

	@Override
	public ProyectoUsuarioDto buscarProyectoUsuario(ProyectoNombreDto dto) {
		Optional<ProyectoUsuarioDto> optional = buscarProyecto(dto, true);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new ProyectoException(ErroresEnum.ERROR_PROYECTO_NO_ENCONTRADO);
	}

	private Optional<ProyectoUsuarioDto> buscarProyecto(ProyectoNombreDto dto, boolean validar) {
		if (validar && dto.getIdProyecto() == null
				&& (dto.getNombreCorto() == null || dto.getIdEstatusProyecto() == null)) {
			throw new ProyectoException(ErroresEnum.ERROR_ASIGNAR_PROYECTO);
		}
	
		Optional<ProyectoModel> optional;
		if (dto.getIdProyecto() != null) {
			optional = proyectoRepository.findById(dto.getIdProyecto());
		} else {
			optional = proyectoRepository.findByNombreCortoAndIdEstatusProyecto(dto.getNombreCorto(),
					dto.getIdEstatusProyecto());
		}
	
		if (optional.isPresent()) {
			ProyectoUsuarioDto proyectoUsuarioDto = new ProyectoUsuarioDto();
			proyectoUsuarioDto.setIdProyecto(optional.get().getIdProyecto());
			proyectoUsuarioDto.setIdEstatusProyecto(optional.get().getIdEstatusProyecto());
			proyectoUsuarioDto.setNombreCorto(optional.get().getNombreCorto());
			
			List<Usuario> usuarios = usuarioRepository.findUsuariosNoAsignados(dto.getIdProyecto());
			
			proyectoUsuarioDto.setUsuarios(usuarios);
	
			proyectoUsuarioDto.setUsuariosAsignados(
					usuarioRepository.findUsuariosAsignados(dto.getIdProyecto()));
	
			pistaService.guardarPista(ModuloPista.ASIGNAR_PROYECTO.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),
					Constantes.getAtributosProyecto()[1] + optional.get().getNombreCorto(), Optional.empty());
	
			return Optional.of(proyectoUsuarioDto);
		}
		return Optional.empty();
	}

	@Override
@Transactional
public boolean guardarUsuariosAsignados(ProyectoUsuarioDto dto) {
    Optional<ProyectoModel> optional = proyectoRepository.findById(dto.getIdProyecto());
    if (optional.isPresent()) {
        log.info("Proyecto encontrado:{}", optional.get().getIdProyecto());
        usuarioProyectoRepository.actualizarProyectosUsuario(optional.get().getIdProyecto(), false);

        dto.getUsuariosAsignados().stream().forEach(user -> {
            UsuarioAsignadoPk pk = new UsuarioAsignadoPk();
            pk.setIdProyecto(optional.get().getIdProyecto());
            pk.setIdUser(user.getIdUser());
            UsuarioProyectoModel obj = new UsuarioProyectoModel();
            obj.setAsignadoPk(pk);
            obj.setEstatus(true);
            obj.setFecha(LocalDateTime.now());
            obj.setProyectoModel(optional.get());
            obj.setUsuario(user);
            usuarioProyectoRepository.save(obj);
        });

        // Construir el log y pista de auditoría
        StringBuilder builder = new StringBuilder();
        builder.append(Constantes.getAtributosProyecto()[1] + optional.get().getNombreCorto())
               .append("|")
               .append("usuarios asignados: "+dto.getUsuariosAsignados().stream()
                           .map(Usuario::getNombre)
                           .collect(Collectors.joining(", "))); // Convertir lista a cadena

        log.info(PISTA_GEN, builder.toString());
        pistaService.guardarPista(
            ModuloPista.ASIGNAR_PROYECTO.getId(),
            TipoMovPista.INSERTA_REGISTRO.getId(),
            TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),
            builder.toString(),
            Optional.empty()
        );
        return true;
    }
    return false;
}

	@Override
	public List<Usuario> buscarUsuarios() {
		return usuarioRepository.findUsuariosOrdenados();
	}

	@Override
public UsuarioProyectoDto buscarUsuarioProyecto(Usuario usuario) {
    List<UsuarioProyectoModel> p = usuarioProyectoRepository.findByUsuarioIdUserAndEstatus(usuario.getIdUser(), true);
    log.info("Proyectos usuarios encontrados:{}", p.size());

    UsuarioProyectoDto obj = new UsuarioProyectoDto();
    obj.setIdUser(usuario.getIdUser());
    obj.setAdministracion(usuario.getAdministracion());
    obj.setEnabled(usuario.isEnabled());
    obj.setEstatus(usuario.isEstatus());
    obj.setNombre(usuario.getNombre());
    obj.setRfcCorto(usuario.getRfcCorto());
    obj.setProyectosAsignados(
        p.stream()
         .map(UsuarioProyectoModel::getProyectoModel)
         .toList() 
    );

    if (obj.getProyectosAsignados().isEmpty()) {
        List<ProyectoModel> proyectos = proyectoRepository.findAllByEstatusTrue()
        	    .stream()
        	    .sorted(Comparator.comparing(ProyectoModel::getNombreCorto, String.CASE_INSENSITIVE_ORDER))
        	    .toList();

        obj.setProyectos(proyectos);
    } else {
    	List<ProyectoModel> proyectosNot = proyectoRepository.findByIdProyectoNotIn(
    	        obj.getProyectosAsignados().stream()
    	            .map(ProyectoModel::getIdProyecto)
    	            .toList()
    	    ).stream()
    	    .sorted(Comparator.comparing(ProyectoModel::getNombreCorto, String.CASE_INSENSITIVE_ORDER))
    	    .toList();

        obj.setProyectos(proyectosNot);
    }

    StringBuilder builder = new StringBuilder();
    builder.append("Usuario: " + usuario.getNombre());
    log.info(PISTA_GEN, builder.toString());

    pistaService.guardarPista(
        ModuloPista.ASIGNAR_PROYECTO.getId(),
        TipoMovPista.CONSULTA_REGISTRO.getId(),
        TipoSeccionPista.ASIGNAR_PROYECTO_POR_USUARIO.getIdSeccionPista(),
        builder.toString(),
        Optional.empty()
    );

    return obj;
}

@Override
@Transactional
public boolean guardarProyectoUsuario(UsuarioProyectoDto dto) {
    Optional<Usuario> op = usuarioRepository.findById(dto.getIdUser());
    if (op.isPresent()) {
        usuarioProyectoRepository.actualizarProyectos(op.get().getIdUser(), false);

        dto.getProyectosAsignados().stream().forEach(pro -> {
            UsuarioAsignadoPk pk = new UsuarioAsignadoPk();
            pk.setIdProyecto(pro.getIdProyecto());
            pk.setIdUser(op.get().getIdUser());
            UsuarioProyectoModel obj = new UsuarioProyectoModel();
            obj.setAsignadoPk(pk);
            obj.setEstatus(true);
            obj.setFecha(LocalDateTime.now());
            obj.setProyectoModel(pro);
            obj.setUsuario(op.get());
            usuarioProyectoRepository.save(obj);
        });

        // Crear la pista con proyectos asignados
        StringBuilder builder = new StringBuilder();
        builder.append("Usuario: " + op.get().getNombre()).append("|")
               .append("Proyectos asignados: " + dto.getProyectosAsignados().stream()
                        .map(ProyectoModel::getNombreCorto)
                        .collect(Collectors.joining(", "))); 
        log.info(PISTA_GEN, builder.toString());

        pistaService.guardarPista(
            ModuloPista.ASIGNAR_PROYECTO.getId(),
            TipoMovPista.INSERTA_REGISTRO.getId(),
            TipoSeccionPista.ASIGNAR_PROYECTO_POR_USUARIO.getIdSeccionPista(),
            builder.toString(),
            Optional.empty()
        );
    }
    return true;
}

@Override
public byte[] exportarProyectosUsuarios(ProyectoNombreDto dto) {
    Specification<UsuarioProyectoModel> specification = regresarSpec(dto);
    List<UsuarioProyectoModel> lista = usuarioProyectoRepository.findAll(specification);

    consumer.inializar("Proyecto usuarios");
    consumer.agregarCabeceras(Constantes.TITULO_REPORTE_PROYECTO_USUARIO);

    lista.stream().forEach(consumer);

    StringBuilder builder = new StringBuilder();
    builder.append(
        lista.stream()
             .map(p ->"Nombre corto del proyecto: "+ p.getProyectoModel().getNombreCorto())
             .collect(Collectors.joining("|")) 
    );
    pistaService.guardarPista(
        ModuloPista.ASIGNAR_PROYECTO.getId(),
        TipoMovPista.IMPRIME_REGISTRO.getId(),
        TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),
        builder.toString(),
        Optional.empty()
    );

    return consumer.cerrarBytes();
}

	private Specification<UsuarioProyectoModel> regresarSpec(ProyectoNombreDto busquedaIncidenteDto) {
		Specification<UsuarioProyectoModel> specification = Specification.where(null);
		specification = specification.and(new IdProyectoSpec(busquedaIncidenteDto));
		specification = specification.and(new ProyectoNombreCortoSpec(busquedaIncidenteDto));
		specification = specification.and(new ProyectoIdEstatusProyectoSpec(busquedaIncidenteDto));
		specification = specification.and(new EstatusUsuarioProyectoSpec(true));
		return specification;
	}

}
