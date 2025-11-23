package com.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.AlineacionRequest;
import com.sisecofi.proyectos.dto.AsociacionGuardarRequest;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.proyectos.dto.FichaRequest;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoMetaDto;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import java.util.HashSet;
import java.util.LinkedHashMap;

import com.sisecofi.proyectos.model.FichaAlineacion;
import java.util.Set;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class FactoryClass {

	private FactoryClass() {
	}

	
	public static List<BaseCatalogoModel> regresarListaEstatus() {
		List<BaseCatalogoModel> mockList = new ArrayList<>();
		BaseCatalogoModel obj = new BaseCatalogoModel();
		obj.setPrimaryKey(1);
		obj.setNombre("Cancelar");
		mockList.add(obj);
		return mockList;
	}

	public static BaseCatalogoModel obtenerEstatus() {
		BaseCatalogoModel estatus = new BaseCatalogoModel();
		estatus.setEstatus(true);
		estatus.setNombre("Planeacion");
		estatus.setPrimaryKey(12);
		return estatus;
	}
	
	public static BaseCatalogoModel obtenerEstatusInicial() {
		BaseCatalogoModel estatus = new BaseCatalogoModel();
		estatus.setEstatus(true);
		estatus.setNombre("Inicial");
		estatus.setPrimaryKey(1);
		return estatus;
	}

	public static EstructuraProyectoMetaDto obtenerEstructuraProyecto() {
		EstructuraProyectoMetaDto estructura = new EstructuraProyectoMetaDto();
		estructura.setIdProyecto(1L);
		estructura.setIdEstatusProyecto(12);
		estructura.setPage(0);
		estructura.setNombreCorto("nombre");
		estructura.setSize(15);
		return estructura;
	}
	
	public static FichaTecnicaResponse obtenerFichaResponse() {
		FichaTecnicaResponse response = new FichaTecnicaResponse();
		response.setFicha(regresarFicha());
		return response;
	}
	
	public static FichaTecnicaModel regresarFicha() {
		FichaTecnicaModel ficha= new FichaTecnicaModel();
		ficha.setObjetivoGeneral("Objetivo");
		ficha.setIdFichaTecnica(1L);
		Set<Integer> admons = new HashSet<>();
		admons.add(1);
		admons.add(2);
		ficha.setIdAdmonCentrales(admons);
		return ficha;
	}
	

	public static Page<ProyectoModel> obtenerPaginacion() {
		Pageable paginacion = PageRequest.of(0, 15);
		return new PageImpl<ProyectoModel>(regresarListaProyectos(), paginacion, regresarListaProyectos().size());
	}

	public static List<ProyectoModel> regresarListaProyectos() {
		List<ProyectoModel> mockList = new ArrayList<>();
		ProyectoModel obj = regresarProyecto();
		mockList.add(obj);
		mockList.sort(Comparator.comparing(ProyectoModel::getNombreCorto));
		return mockList;
	}

	
	public static ProyectoModel regresarProyecto() {
		ProyectoModel proyecto= new ProyectoModel("nombre", "nombre completo", "tttt-0000-01", 12);
		proyecto.setIdProyecto(1L);
		return proyecto;
	}
	
	public static ProyectoModel regresarProyectoActualizado() {
		return new ProyectoModel("nombre", "nombre completo", "tttt-0000-02", 12);
	}
	
	
	public static List<ProyectoMetaDto> regresarListaProyectosMeta() {
		List<ProyectoMetaDto> mockList = new ArrayList<>();
		ProyectoMetaDto obj = new ProyectoMetaDto();
		obj.setEstatus(null);
		obj.setLiderProyecto("lider");
		obj.setNombreCorto("nombre");
		obj.setEstatus(obtenerEstatusCatalogo());
		mockList.add(obj);
		return mockList;
	}

	public static Optional<ProyectoModel> optionalProyecto() {
		return Optional.of(regresarProyecto());
	}
	
	public static Optional<FichaTecnicaModel> optionalFicha() {
		return Optional.of(regresarFicha());
	} 
	
	public static Page<HistoricoModel> obtenerHistorico() {
		Pageable paginacion = PageRequest.of(0, 15);
		List<HistoricoModel> lista= new ArrayList<HistoricoModel>();
		HistoricoModel historico= new HistoricoModel();
		historico.setNombre("nombreHistorico");
		lista.add(historico);
		return new PageImpl<HistoricoModel>(lista, paginacion, regresarListaProyectos().size());
	}
	
	public static Set<HistoricoModel> obtenerHistoricoSet() {
		Set<HistoricoModel> lista= new HashSet<>();
		HistoricoModel historico= new HistoricoModel();
		historico.setNombre("nombreHistorico");
		lista.add(historico);
		return lista;
	}
	
	public static List<HistoricoModel> obtenerHistoricoLista() {
		List<HistoricoModel> lista= new ArrayList<HistoricoModel>();
		HistoricoModel historico= new HistoricoModel();
		historico.setNombre("nombreHistorico");
		historico.setFechaInicio(LocalDateTime.of(2024, 5, 10, 0, 0));
		historico.setFechaFin(LocalDateTime.of(2024, 5, 15, 0, 0));
		lista.add(historico);
		return lista;
	}
	
	public static Optional<HistoricoModel> historicoOptional(){
		return Optional.of(obtenerHistoricoLista().get(0));
	}
	
	public static Optional<HistoricoModel> historicoOptionalNuevo(){
		HistoricoModel historico = obtenerHistoricoLista().get(0);
		historico.setFechaInicio(LocalDateTime.of(2024, 5, 16, 0, 0));
		historico.setFechaFin(null);
		historico.setNombre("nuevo lider");;
		return Optional.of(historico);
	}
	
	public static List<FichaAlineacion> obtenerAlineaciones() {
		List<FichaAlineacion> lista= new ArrayList<FichaAlineacion>();
		FichaAlineacion fichaAlineacion= new FichaAlineacion();
		fichaAlineacion.setIdObjetivo(1);
		lista.add(fichaAlineacion);
		return lista;
	}
	
	public static List<AlineacionRequest> obtenerAlineacionesRequest(){
		List<AlineacionRequest> lista = new ArrayList<>();
		AlineacionRequest alineacion= new AlineacionRequest();
		alineacion.setIdFichaAlineacion(1L);
		alineacion.setIdObjetivo(1);
		alineacion.setIdPeriodo(1);
		alineacion.setIdMapa(1);
		lista.add(alineacion);
		return new ArrayList<>(lista);
	}
	
	public static Optional <Usuario> obtenerUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNombre("NombreUsuario");
		return Optional.of(usuario);
	}
	
	public static FichaRequest estructuraFicha() {
		FichaRequest fichaRequest= new FichaRequest();
		fichaRequest.setFicha(regresarFicha());
		fichaRequest.setAlineaciones(obtenerAlineacionesRequest());
		fichaRequest.setLideres(new HashSet<>(obtenerHistoricoLista()));
		return fichaRequest;
	}
	
	public static Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNombre("usuario");
		return usuario;
	}
	
	public static List<Usuario> listaUsuarios(){
		List<Usuario> lista = new ArrayList<>();
		lista.add(getUsuario());
		return lista;
	}
	
	
	public static Optional<FichaAlineacion> getAlineacion() {
		FichaAlineacion alineacion = new FichaAlineacion();
		alineacion.setIdAliniacion(1);
		alineacion.setIdFichaTecnica(1L);
		//alineacion.setObjetivo("objetivo alineacion");
		return Optional.of(alineacion);
	}
	
	public static Set<FichaAlineacion> getSetAlineacion(){
		Set<FichaAlineacion> coleccion= new HashSet<>();
		coleccion.add(getAlineacion().get());
		return coleccion;
	}
	
	public static AsociacionesModel getAsociacion() {
		AsociacionesModel asociacion = new AsociacionesModel();
		asociacion.setIdProyecto(1L);
		asociacion.setFechaAsignacion(LocalDate.now());
		asociacion.setIdPlantillaVigente(1);
		asociacion.setIdAsociacion(1L);
		return asociacion;
	}
	
	public static List<AsociacionesModel> getAsociaciones() {
		List<AsociacionesModel> lista= new ArrayList<>();
		lista.add(getAsociacion());
		return lista;
	}
	
	public static PlantillaDto getPlantillaDto() {
		PlantillaDto plantillaDto = new PlantillaDto();
		plantillaDto.setIdFase(1);
		PlantillaVigenteModel plantillaVigenteModel = new PlantillaVigenteModel();
		plantillaVigenteModel.setIdFaseProyecto(1);
		plantillaVigenteModel.setNombre("Nombre");
		plantillaDto.setPlantillaVigenteModel(plantillaVigenteModel);
		return plantillaDto;
	}
	
	public static AsociacionGuardarRequest getAsociacionGuardarRequest() {
		AsociacionGuardarRequest request= new AsociacionGuardarRequest();
		Set<Long> eliminados = new HashSet<>();
		eliminados.add(1L);
		request.setAsociacionesEliminadas(eliminados);
		Set <AsociacionesModel> nuevas= new HashSet<>();
		nuevas.add(getAsociacion());
		request.setAsociacionesNuevas(nuevas);
		request.setAsociacionesModificadas(nuevas);
		return request;
	}

	public static CatAdmonCentral regresarAdmonCentral() {
		CatAdmonCentral mock = new CatAdmonCentral();
		mock.setPrimaryKey(1);
		mock.setNombre("nombre");
		return mock;
	}
	
	public static CatAdmonGeneral regresarAdmonGeneral() {
		CatAdmonGeneral mock = new CatAdmonGeneral();
		mock.setPrimaryKey(1);
		mock.setNombre("nombre");
		return mock;
	}
	
	public static CatEstatusProyecto regresarEstatusInicial() {
		CatEstatusProyecto mock = new CatEstatusProyecto();
		mock.setPrimaryKey(1);
		mock.setNombre("Planeacion");
		return mock;
	}
	
	public static CatAdministracion regresarAdministracion() {
		CatAdministracion mock = new CatAdministracion();
		mock.setPrimaryKey(1);
		mock.setNombre("Planeacion");
		return mock; 
	}
	
	public static CatEstatusProyecto obtenerEstatusCatalogo() {
		CatEstatusProyecto estatus = new CatEstatusProyecto();
		estatus.setEstatus(true);
		estatus.setNombre("Planeacion");
		estatus.setPrimaryKey(12);
		return estatus;
	}
}
