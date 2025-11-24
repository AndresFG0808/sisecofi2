package com.sisecofi.proyectos.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAliniacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatEmpleadoAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatMapaObjetivo;
import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodo;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.AlineacionRequest;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.dto.proyecto.AlineacionResponse;
import com.sisecofi.proyectos.dto.FichaRequest;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.LiderDto;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.model.FichaAlineacion;
import com.sisecofi.libreria.comunes.model.proyectos.FichaAdmonCentral;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.repository.FichaTecnicaRepository;
import com.sisecofi.proyectos.repository.HistoricoRepository;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.repository.administradores.AdministracionRepository;
import com.sisecofi.proyectos.repository.administradores.AdmonGeneralRepository;
import com.sisecofi.proyectos.repository.administradores.CatEmpleadoAdministracionRepository;
import com.sisecofi.proyectos.repository.administradores.AdmonCentralRepository;
import com.sisecofi.proyectos.repository.FichaAdmonCentralRepository;
import com.sisecofi.proyectos.repository.FichaAlineacionRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.ServicioAlineacion;
import com.sisecofi.proyectos.service.ServicioFichaTecnica;
import com.sisecofi.proyectos.service.ServicioHistorico;
import com.sisecofi.proyectos.service.ServicioProyecto;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.consumer.FichaMap;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class ServicioFichaTecnicaImpl implements ServicioFichaTecnica {

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final FichaTecnicaRepository fichaRepository;
	private final ProyectoRepository proyectoRepository;
	private final HistoricoRepository historicoRepository;
	private final FichaAlineacionRepository fichaAlineacionRepository;
	private final FichaAdmonCentralRepository fichaAdmonCentralRepository;
	private final PistaService pistaService;
	private final ServicioHistorico servicioHistorico;
	private final UsuarioRepository usuarioRepository;
	private final ServicioAlineacion servicioAlineacion;
    private final ServicioProyecto servicioProyecto;
    private final AdmonCentralRepository admonCentralRepository;
    private final AdmonGeneralRepository admonGeneralRepository;
    private final AdministracionRepository administracionRepository;
    private final CatEmpleadoAdministracionRepository empleadoRepository;
    private final ModelMapper modelMapper;

	@Override
	@Transactional
	public FichaTecnicaResponse obtenerFicha(Long id) {
		ProyectoModel proyecto =  obtenerProyecto(id);
		if (proyecto.getFichaTecnicaModel() == null) {
			return new FichaTecnicaResponse();
		}else {
			return agruparRespuesta(proyecto.getFichaTecnicaModel());	
		}
		

	}

	@Override
	@Transactional
	public FichaTecnicaResponse guardarFicha(FichaRequest fichaRequest, Long idProyecto, boolean completo) {
	    FichaTecnicaModel ficha = fichaRequest.getFicha();
	    List<AlineacionRequest> alineaciones = fichaRequest.getAlineaciones();
	    Set<HistoricoModel> historicos = fichaRequest.getLideres();
	    Set<Integer> admonsOginales = new HashSet<>();

	    validarUnicoLider(historicos);
	    
	    boolean exists = proyectoRepository.existsById(idProyecto);
	    if (!exists) {
		    throw new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO);
		}
	    boolean nuevo= false;
	    
	    Optional<FichaTecnicaModel> fichaModificada = fichaRepository.findByIdProyecto(idProyecto);
	  
	    if(!fichaModificada.isPresent()) {
	    	nuevo=true;
	    	validarDatosObligatorios(alineaciones, historicos);
	    }
	    eliminarLideresYalineaciones(fichaRequest, idProyecto);
	    
	    ficha = actualizarFicha(ficha, fichaModificada, completo, admonsOginales);
	    
	    ficha.setIdProyecto(idProyecto);
	    fichaRepository.save(ficha);
	    
	    servicioHistorico.agregarHistoricos(historicos, ficha.getIdFichaTecnica());
	    
	    servicioAlineacion.agregarAlineaciones(alineaciones, ficha.getIdFichaTecnica());
	    nuevosAdmons(admonsOginales, ficha.getIdAdmonCentrales(), ficha.getIdFichaTecnica());
	    
	    FichaTecnicaResponse response = agruparRespuesta(ficha);
	    
	    registrarPista(ficha, idProyecto, nuevo);
	    
	    servicioProyecto.actualizarUltimaModificacion(idProyecto);
	    
	    return response;
	}
	

	private void validarUnicoLider(Set<HistoricoModel> historicos) {
	    if (historicos.stream().filter(HistoricoModel::isEstatus).count() > 1) {
	        throw new ProyectoException(ErroresEnum.LIDER_UNICO);
	    }
	}

	private ProyectoModel obtenerProyecto(Long idProyecto) {
	    return proyectoRepository.findByIdProyectoAndEstatus(idProyecto, true)
	        .orElseThrow(() -> new ProyectoException(ErroresEnum.PROYECTO_NO_ENCONTRADO));
	}

	private FichaTecnicaModel actualizarFicha(FichaTecnicaModel ficha, Optional<FichaTecnicaModel> fichaModificada, boolean completo, Set<Integer> admonsOginales) {
	    if (fichaModificada.isPresent() && completo) {
	        admonsOginales.addAll(fichaModificada.get().getIdAdmonCentrales());
	        ficha.setIdFichaTecnica(fichaModificada.get().getIdFichaTecnica());
	    } else if (fichaModificada.isPresent()) {
	        FichaTecnicaModel fichaOriginal = fichaModificada.get();
	        fichaOriginal.setFechaInicio(ficha.getFechaInicio());
	        fichaOriginal.setFechaTermino(ficha.getFechaTermino());
	        fichaOriginal.setMontoSolicitado(ficha.getMontoSolicitado());
	        ficha = fichaOriginal;
	    }
	    Integer idAdministradorPatrocinador = admonGeneralRepository
                .findIdAdministradorGeneralByCatAdmonGeneralId(ficha.getIdAdmonPatrocinadora())
                .orElse(null);
	    
	    Integer idAdministradorParticipante = admonGeneralRepository
                .findIdAdministradorGeneralByCatAdmonGeneralId(ficha.getIdAdmonParticipante())
                .orElse(null);
	    
	    Integer idAdministradorPlaneacion = administracionRepository
                .findIdAdministradorAdministracionByCatAdministracionId(ficha.getIdAreaPlaneacion())
                .orElse(null);
	    
	    ficha.setIdAdministradorPatrocinador(idAdministradorPatrocinador);
	    ficha.setIdAdministradorParticipante(idAdministradorParticipante);
	    ficha.setIdAdministradorPlaneacion(idAdministradorPlaneacion);
	    
	    return ficha;
	}

	private void validarDatosObligatorios(List<AlineacionRequest> alineaciones, Set<HistoricoModel> historicos) {
	    if (alineaciones.isEmpty() || historicos.isEmpty()) {
	        throw new ProyectoException(ErroresEnum.ERROR_DATOS_OBLIGATORIOS);
	    }
	}

	private void eliminarLideresYalineaciones(FichaRequest fichaRequest, Long idProyecto) {
	    if (fichaRequest.getLideresEliminados() != null)
	        fichaRequest.getLideresEliminados().forEach(i -> {
	            servicioHistorico.eliminarHistorico(i);
	            // pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
	            // 	TipoSeccionPista.PROYECTO_DATOS_FICHA_TECNICA.getIdSeccionPista(),
	            // 	Constantes.getAtributosProyecto()[0] + idProyecto, Optional.of(servicioHistorico.eliminarHistorico(i)));
	        });

	    if (fichaRequest.getAlineacionesEliminadas() != null)
	        fichaRequest.getAlineacionesEliminadas().forEach(i -> {
	            servicioAlineacion.eliminarAlineacion(i);
	            // pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),
	            // 	TipoSeccionPista.PROYECTO_DATOS_FICHA_TECNICA.getIdSeccionPista(),
	            // 	Constantes.getAtributosProyecto()[0] + idProyecto, Optional.of(servicioAlineacion.eliminarAlineacion(i)));
	        });
	}




	private void registrarPista(FichaTecnicaModel fichaModificada, Long idProyecto, boolean nuevo) {
	    if (nuevo) {

	    	// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

	    	// TipoSeccionPista.PROYECTO_DATOS_FICHA_TECNICA.getIdSeccionPista(),

	    	// Constantes.getAtributosProyecto()[0] + idProyecto,Optional.of(fichaModificada));
	    } else {

	    	// pistaService.guardarPista(ModuloPista.PROYECTOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

	    	// TipoSeccionPista.PROYECTO_DATOS_FICHA_TECNICA.getIdSeccionPista(),

	    	// Constantes.getAtributosProyecto()[0] + idProyecto,Optional.of(fichaModificada));
	    }
	}

	private Set<Integer> nuevosAdmons(Set<Integer> originales, Set<Integer> actualizados, Long idFicha) {
		Set<Integer> nuevos = new HashSet<>(actualizados);
		if (originales != null) {
			nuevos.removeAll(originales);
			Set<Integer> eliminados = new HashSet<>(originales);
			eliminados.removeAll(actualizados);
			for (Integer i : eliminados) {
				Optional<FichaAdmonCentral> admonOptional = fichaAdmonCentralRepository
						.findByIdFichaTecnicaAndIdAdmonCentral(idFicha, i);
				if (admonOptional.isPresent()) {
					FichaAdmonCentral admon = admonOptional.get();
					admon.setEstatus(false);
					fichaAdmonCentralRepository.save(admon);
				}
			}
		}
		for (Integer i : nuevos) {
			Optional<FichaAdmonCentral> admonOptional = fichaAdmonCentralRepository
					.findByIdFichaTecnicaAndIdAdmonCentral(idFicha, i);
			FichaAdmonCentral admon = new FichaAdmonCentral();
			admon.setEstatus(true);
			admon.setIdFichaTecnica(idFicha);
			admon.setIdAdmonCentral(i);
			
			Integer idAdministradorCentral = admonCentralRepository
	                .findIdAdministradorCentralByCatAdmonCentralId(i)
	                .orElse(null);
		    
				admon.setIdAdministradorCentral(idAdministradorCentral);	
			
			if (admonOptional.isPresent()) {
				admon = admonOptional.get();
				admon.setEstatus(true);
			}
			fichaAdmonCentralRepository.save(admon);
		}
		return actualizados;

	}

	private FichaTecnicaResponse agruparRespuesta(FichaTecnicaModel ficha) {
		FichaTecnicaResponse response = new FichaMap(catalogoMicroservicio, fichaAdmonCentralRepository, admonCentralRepository, admonGeneralRepository, administracionRepository).apply(ficha);
		List<FichaAlineacion> lista = fichaAlineacionRepository
				.findByIdFichaTecnicaAndEstatusAlineacion(ficha.getIdFichaTecnica(), true);
		List<AlineacionResponse> alineaciones = new ArrayList<>();
		for (FichaAlineacion alineacion : lista) {
			AlineacionResponse alineacionResponse = new AlineacionResponse();
			alineacionResponse.setId(alineacion.getIdFichaAlineacion());
			alineacionResponse.setMapa(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.ALINIACION.getIdCatalogo(), alineacion.getIdAliniacion(), CatAliniacion.class));
			alineacionResponse.setObjetivo(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.MAPA_OBJETIVO.getIdCatalogo(), alineacion.getIdObjetivo(), CatMapaObjetivo.class));
			alineacionResponse.setPeriodo(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.PERIDO.getIdCatalogo(), alineacion.getIdPeriodo(), CatPeriodo.class));
			alineaciones.add(alineacionResponse);
		}
		response.setAlineaciones(alineaciones);
		response.setLideres(obtenerLideres(ficha.getIdFichaTecnica()));
		return response;
	}
	
	private Set<LiderDto> obtenerLideres(Long idFicha){
		Set<LiderDto> listaResponse = new LinkedHashSet<>();
		Set <HistoricoModel> historico = historicoRepository.findByIdFichaTecnicaAndEstatusHistoricoOrderByIdHistoricoAsc(idFicha, true);
		for (HistoricoModel lider: historico) {
			listaResponse.add(obtenerLiderDto(lider));
		}
		return listaResponse;
	}
	
	private LiderDto obtenerLiderDto(HistoricoModel lider) {
		LiderDto liderDto;
		Integer nivel = lider.getNivel();
		Integer idReferencia = lider.getIdReferencia();
		UsuarioInfoDto dto = obtenerUsuarioDto (idReferencia, nivel);
		
		liderDto= modelMapper.map(lider, LiderDto.class);
		liderDto.setUsuario(dto);
		liderDto.setPuesto(dto.getPuesto());
		return liderDto;
	}
	
	private UsuarioInfoDto obtenerUsuarioDto(Integer idReferencia, Integer nivel) {
		UsuarioInfoDto dto= new UsuarioInfoDto();
		if (idReferencia != null && nivel != null) {
			dto.setIdUsuario(idReferencia);
			switch (nivel) {
			case 1:
				CatAdministradorGeneral general = admonGeneralRepository.findById(idReferencia).orElseThrow(() -> new ProyectoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto= convertirGeneral(general);
				break;

			case 2:
				CatAdministradorCentral central = admonCentralRepository.findById(idReferencia).orElseThrow(() -> new ProyectoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto= convertirCentral(central);
				break;

			case 3:
				CatAdministradorAdministracion area = administracionRepository.findById(idReferencia).orElseThrow(() -> new ProyectoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto= convertirAdministrador(area);
				break;

			case 4:
				CatEmpleadoAdministracion empleado = empleadoRepository.findById(idReferencia).orElseThrow(() -> new ProyectoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto= convertirEmpleado(empleado);
				break;
				
			default:
				break;
			}
			
		}
		return dto;
	}
	
	private String obtenerIdCompuesto(Integer idReferencia, Integer nivel, Integer idAdmon, String separador) {
		if (idReferencia !=null && nivel!=null && idAdmon!=null) {
			return idReferencia+separador+ nivel+separador+idAdmon;
		}return null;
	}
	
	 private UsuarioInfoDto convertirGeneral(CatAdministradorGeneral general) {
	        UsuarioInfoDto dto = new UsuarioInfoDto();
	        dto.setIdUsuario(general.getIdAdministrador());
	        dto.setCorreo(general.getCorreo());
	        dto.setNombre(general.getAdministrador());
	        dto.setTelefono(general.getTelefono());
	        dto.setNivel(1);
	        dto.setPuesto(general.getCatAdmonGeneral().getPuesto());
	        dto.setIdUsuarioStr(obtenerIdCompuesto(dto.getIdUsuario(), dto.getNivel(), general.getCatAdmonGeneral().getIdAdmonGeneral(), "-"));
	        return dto;
	    }

	    private UsuarioInfoDto convertirCentral(CatAdministradorCentral central) {
	        UsuarioInfoDto dto = new UsuarioInfoDto();
	        dto.setIdUsuario(central.getIdAdministrador());
	        dto.setCorreo(central.getCorreo());
	        dto.setNombre(central.getAdministrador());
	        dto.setTelefono(central.getTelefono());
	        dto.setNivel(2);
	        dto.setPuesto(central.getCatAdmonCentral().getPuesto());
	        dto.setIdUsuarioStr(obtenerIdCompuesto(dto.getIdUsuario(), dto.getNivel(), central.getCatAdmonCentral().getIdAdmonCentral(), "-"));
	        return dto;
	    }

	    private UsuarioInfoDto convertirAdministrador(CatAdministradorAdministracion admin) {
	        UsuarioInfoDto dto = new UsuarioInfoDto();
	        dto.setIdUsuario(admin.getIdAdministrador());
	        dto.setCorreo(admin.getCorreo());
	        dto.setNombre(admin.getAdministrador());
	        dto.setTelefono(admin.getTelefono());
	        dto.setNivel(3);
	        dto.setPuesto(admin.getCatAdministracion().getPuesto());
	        dto.setIdUsuarioStr(obtenerIdCompuesto(dto.getIdUsuario(), dto.getNivel(), admin.getCatAdministracion().getIdAdministracion(), "-"));
	        return dto;
	    }

	    private UsuarioInfoDto convertirEmpleado(CatEmpleadoAdministracion empleado) {
	        UsuarioInfoDto dto = new UsuarioInfoDto();
	        dto.setIdUsuario(empleado.getIdEmpleadoAdministracion());
	        dto.setCorreo(empleado.getCorreo());
	        dto.setNombre(empleado.getNombre());
	        dto.setTelefono(empleado.getTelefono());
	        dto.setNivel(4);
	        dto.setPuesto(empleado.getCatTipoEmpleado().getNombre() + " - "+ empleado.getCatAdministracion().getAdministracion());
	        dto.setIdUsuarioStr(obtenerIdCompuesto(dto.getIdUsuario(), dto.getNivel(), empleado.getCatAdministracion().getIdAdministracion(), "-"));
	        return dto;
	    }
	
	
	@Override
	public List<UsuarioInfoDto> obtenerTodosLosUsuarios() {
	    List<UsuarioInfoDto> usuarios = new ArrayList<>();

	    // Administradores Generales
	    List<CatAdministradorGeneral> generales = admonGeneralRepository.findAll();
	    generales.forEach(general -> usuarios.add(convertirGeneral(general)));

	    // Administradores Centrales
	    List<CatAdministradorCentral> centrales = admonCentralRepository.findAll();
	    centrales.forEach(central -> usuarios.add(convertirCentral(central)));

	    // Administradores de Área
	    List<CatAdministradorAdministracion> administradores = administracionRepository.findAll();
	    administradores.forEach(admin -> usuarios.add(convertirAdministrador(admin)));

	    // Empleados
	    List<CatEmpleadoAdministracion> empleados = empleadoRepository.findAll();
	    empleados.forEach(emp -> usuarios.add(convertirEmpleado(emp)));

	    return usuarios;
	}

	@Override
	public boolean agregarLider(Long id) {
		Optional<FichaTecnicaModel> fichaOptional = fichaRepository.findByIdProyecto(id);
		if (fichaOptional.isPresent()) {
			FichaTecnicaModel ficha = fichaOptional.get();
			Optional<HistoricoModel> historico = historicoRepository
					.findByIdFichaTecnicaAndEstatusAndEstatusHistorico(ficha.getIdFichaTecnica(), true, true);
			if (historico.isPresent()) {
				throw new ProyectoException(ErroresEnum.LIDER_ACTUAL_ACTIVO);
			}
		}
		return true;
	}


	@Override
	public List<Usuario> enlistarUsuarios() {
		return usuarioRepository.findByEstatusOrderByNombreAsc(true);
	}

}
