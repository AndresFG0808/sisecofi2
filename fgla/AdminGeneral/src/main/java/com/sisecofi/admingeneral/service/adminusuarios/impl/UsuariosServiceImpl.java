package com.sisecofi.admingeneral.service.adminusuarios.impl;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.directorio.activo.dto.BusquedaDirectorioDto;
import com.directorio.activo.dto.UsuarioDirectorioDto;
import com.directorio.activo.service.DirectorioActivoService;
import com.directorio.activo.util.exception.DirectorioActivoException;
import com.sisecofi.admingeneral.dto.adminusuarios.BusquedaUsuarioDto;
import com.sisecofi.admingeneral.service.adminpistas.PistaService;
import com.sisecofi.admingeneral.service.adminusuarios.UsuariosService;
import com.sisecofi.admingeneral.util.consumers.ReporteUsuarioConsumer;
import com.sisecofi.admingeneral.util.enums.ErroresAdminUsuarioEnum;
import com.sisecofi.admingeneral.util.exception.UsuarioException;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuariosServiceImpl implements UsuariosService {

	private final UsuarioRepository repository;
	private final DirectorioActivoService activoService;
	private final ReporteUsuarioConsumer consumer;
	private final PistaService pistaService;

	@Override
	public List<Usuario> obtenerUsuario(BusquedaUsuarioDto bus) {
	    if ((bus.getNombre() == null && bus.getApellidoPaterno() == null && bus.getApellidoMaterno() == null)
	         || bus.getRfcCorto() == null || bus.getEstatus() == null) {
	      return repository.findAll();
	    }
	      
	     return repository.findByNombreOrRfcCortoAndEstatusAcumulativo(
	         bus.getNombre(), 
	         bus.getApellidoPaterno(), 
	         bus.getApellidoMaterno(),
	         bus.getRfcCorto(),
	              bus.getEstatus()
	          );
	  }

	@Override
	public List<Usuario> buscarUsuarioDirectorioActivo(BusquedaUsuarioDto dto) {
	    try {
	      BusquedaDirectorioDto bus = new BusquedaDirectorioDto();
	      bus.setRfcCorto(quitarEspacios(dto.getRfcCorto()));
	      bus.setNombre(quitarEspacios(dto.getNombre()));
	      bus.setApellidoPaterno(quitarEspacios(dto.getApellidoPaterno()));
	      bus.setApellidoMaterno(quitarEspacios(dto.getApellidoMaterno()));
	      
	      log.info("Parametros de busqueda: {}", bus);
	      List<UsuarioDirectorioDto> usu = activoService.buscar(bus);
	      log.info("Servicio regreso datos: {}", usu);
	      if (!usu.isEmpty()) {
	        return usu.stream().map(e -> {
	          Usuario u = new Usuario();
	          u.setNombre(e.getNombreCompleto());
	          u.setNombres(u.getNombres());
	          u.setApellidoPaterno(u.getApellidoPaterno());
	          u.setApellidoMaterno(u.getApellidoMaterno());
	          u.setRfcCorto(e.getRfcCorto());
	          u.setAdministracion(e.getAdministracion());
	          u.setEstatus(true);
	          return u;
	        }).toList();
	      }
	      throw new UsuarioException(ErroresAdminUsuarioEnum.ERROR_NO_ENCONTRADO);
	    } catch (DirectorioActivoException e) {
	      throw new UsuarioException(ErroresAdminUsuarioEnum.ERROR_DIRECTORIO_ACTIVO, e);
	    }
	  }
	
	private String quitarEspacios(String value) {
	    return (value != null) ? value.trim().toLowerCase() : "";
	}


	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		/**
		 * Estos metodos solo son para local setEnabled, setUserName y setPassword para
		 * simular el OAUTH2.0
		 */
		return repository.save(usuario);
	}

	@Override
	@Transactional
	public boolean guardarUsuarios(List<Usuario> usuario) {
		/**
		 * Estos metodos solo son para local setEnabled, setUserName y setPassword para
		 * simular el OAUTH2.0
		 */
		repository.saveAll(usuario);
		return true;
	}

	@Override
	public byte[] obtenerUsuarioReporte(BusquedaUsuarioDto busquedaUsuarioDto) {
		List<Usuario> lista;
		if (busquedaUsuarioDto.getActionType().equals("directorio")) {
			lista = buscarUsuarioDirectorioActivo(busquedaUsuarioDto);
		} else {
			lista = obtenerUsuario(busquedaUsuarioDto);
		}

		consumer.inializar("Usuarios");
		consumer.agregarCabeceras("Nombre,Rfc corto,Administración,Estatus");
		lista.stream().forEach(consumer);

		return consumer.cerrarBytes();
	}

	@Override
	public boolean guardarPista() {
		pistaService.guardarPista(ModuloPista.ACCESO_SISTEMA.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
				TipoSeccionPista.ACCESO_SISTEMA.getIdSeccionPista(), "Menú principal", Optional.empty());
		return true;
	}

}
