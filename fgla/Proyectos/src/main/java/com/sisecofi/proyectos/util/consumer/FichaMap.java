package com.sisecofi.proyectos.util.consumer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;


import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatClasificacionProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatFinanciamiento;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoProcedimiento;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.dto.proyecto.FichaTecnicaResponse;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.repository.FichaAdmonCentralRepository;
import com.sisecofi.proyectos.repository.administradores.AdministracionRepository;
import com.sisecofi.proyectos.repository.administradores.AdmonCentralRepository;
import com.sisecofi.proyectos.repository.administradores.AdmonGeneralRepository;
import com.sisecofi.libreria.comunes.model.proyectos.FichaAdmonCentral;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FichaMap implements Function<FichaTecnicaModel, FichaTecnicaResponse> {

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final FichaAdmonCentralRepository fichaAdmonCentralRepository;
	private final AdmonCentralRepository admonCentralRepository;
	private final AdmonGeneralRepository admonGeneralRepository;
	private final AdministracionRepository administracionRepository;

	@Override
	public FichaTecnicaResponse apply(FichaTecnicaModel t) {
		List<FichaAdmonCentral> listaCentrales= fichaAdmonCentralRepository.findByIdFichaTecnicaAndEstatusTrue(t.getIdFichaTecnica());
		Set<CatAdmonCentral> admonCentrales = new HashSet<>();
		
		FichaTecnicaResponse nuevaFicha = new FichaTecnicaResponse();
		for(FichaAdmonCentral cat: listaCentrales) {
			
			CatAdmonCentral admonCentral= catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
                    CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), 
                    cat.getIdAdmonCentral(), 
                    CatAdmonCentral.class
            );
			
			CatAdmonCentral temporal = new CatAdmonCentral();
			temporal.setIdAdmonCentral(admonCentral.getIdAdmonCentral());
			temporal.setAcronimo(admonCentral.getAcronimo()  );
			temporal.setAdministracion(admonCentral.getAdministracion()  );
			temporal.setNombre(admonCentral.getNombre() );
			temporal.setPrimaryKey(admonCentral.getPrimaryKey());
			
			Optional<CatAdministradorCentral> administradorCentralOptional= admonCentralRepository.findByIdAdministradorCentral(cat.getIdAdministradorCentral());
			
			if (administradorCentralOptional.isPresent()) {
				CatAdministradorCentral administradorCentral= administradorCentralOptional.get();
				CatAdministradorCentral temporalAdmin= new CatAdministradorCentral();
				temporalAdmin.setIdAdministradorCentral(administradorCentral.getIdAdministradorCentral());
				temporalAdmin.setAdministrador(administradorCentral.getAdministrador());
				temporalAdmin.setNombre(administradorCentral.getNombre());
				temporalAdmin.setPrimaryKey(administradorCentral.getPrimaryKey());
				temporalAdmin.setEstatus(true);
				
				List <CatAdministradorCentral> listaAdministradores = new ArrayList<>();
				listaAdministradores.add(temporalAdmin);
				temporal.setAdministradores(listaAdministradores);
			}else {
				temporal.setAdministradores(admonCentral.getAdministradores());
			}
			admonCentrales.add(temporal);
		}
		
		nuevaFicha.setFicha(t);
		
		try {
	        Integer idParticipante = t.getIdAdmonParticipante();
	        if (idParticipante!=null) {
	        	
	        	Optional <CatAdministradorGeneral> administradorParticipanteOptional= admonGeneralRepository.findByIdAdministradorGeneral(t.getIdAdministradorParticipante());
	    		CatAdmonGeneral admonParticipante= catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
	                        CatalogosComunes.ADMINISTRACION_GENERAL.getIdCatalogo(), 
	                        idParticipante, 
	                        CatAdmonGeneral.class
	                );
	    		
	    		CatAdmonGeneral temporal = new CatAdmonGeneral();
	    		temporal.setIdAdmonGeneral(admonParticipante.getIdAdmonGeneral());
	    		temporal.setAcronimo(admonParticipante.getAcronimo()  );
	    		temporal.setAdministracion(admonParticipante.getAdministracion()  );
	    		temporal.setNombre(admonParticipante.getNombre() );
	    		temporal.setPrimaryKey(admonParticipante.getPrimaryKey());
	    		
	    		
	    		if (administradorParticipanteOptional.isPresent()) {
	    			CatAdministradorGeneral administradorParticipante= administradorParticipanteOptional.get();
	    			List<CatAdministradorGeneral> listaAdministradorParticipante =new ArrayList<>();
	    			CatAdministradorGeneral temporalAdmin= new CatAdministradorGeneral();
	    			temporalAdmin.setIdAdministradorGeneral(administradorParticipante.getIdAdministradorGeneral());
	    			temporalAdmin.setAdministrador(administradorParticipante.getAdministrador());
	    			temporalAdmin.setNombre(administradorParticipante.getNombre());
	    			temporalAdmin.setPrimaryKey(administradorParticipante.getPrimaryKey());
	    			temporalAdmin.setEstatus(true);
	    			
	  
	    			listaAdministradorParticipante.add(temporalAdmin);
	    			temporal.setAdministradores(listaAdministradorParticipante);
	    		}else {
	    			temporal.setAdministradores(admonParticipante.getAdministradores());
	    		}
	        	
	        	 nuevaFicha.setAdmonParticipante(temporal);
	        }
	    } catch (ProyectoException e) {
	        nuevaFicha.setAdmonParticipante(null); 
	    } catch (Exception e) {
	        throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
	    }
		
		Optional<CatAdministradorGeneral> administradorPatrocinadorOptional= admonGeneralRepository.findByIdAdministradorGeneral(t.getIdAdministradorPatrocinador());
		CatAdmonGeneral admonPatrocinador= catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.ADMINISTRACION_GENERAL.getIdCatalogo(), t.getIdAdmonPatrocinadora(),CatAdmonGeneral.class);
		
		
		CatAdmonGeneral temporal = new CatAdmonGeneral();
		temporal.setIdAdmonGeneral(admonPatrocinador.getIdAdmonGeneral());
		temporal.setAcronimo(admonPatrocinador.getAcronimo()  );
		temporal.setAdministracion(admonPatrocinador.getAdministracion()  );
		temporal.setNombre(admonPatrocinador.getNombre() );
		temporal.setPrimaryKey(admonPatrocinador.getPrimaryKey());
		
		
		if (administradorPatrocinadorOptional.isPresent()) {
			CatAdministradorGeneral administradorPatrocinador= administradorPatrocinadorOptional.get();
			List<CatAdministradorGeneral> listaAdministradorPatrocinador =new ArrayList<>();
			CatAdministradorGeneral temporalAdmin= new CatAdministradorGeneral();
			temporalAdmin.setIdAdministradorGeneral(administradorPatrocinador.getIdAdministradorGeneral());
			temporalAdmin.setAdministrador(administradorPatrocinador.getAdministrador());
			temporalAdmin.setNombre(administradorPatrocinador.getNombre());
			temporalAdmin.setPrimaryKey(administradorPatrocinador.getPrimaryKey());
			temporalAdmin.setEstatus(true);
			
			listaAdministradorPatrocinador.add(temporalAdmin);
			temporal.setAdministradores(listaAdministradorPatrocinador);
		}else {
			temporal.setAdministradores(admonPatrocinador.getAdministradores());
		}
		
		nuevaFicha.setAdmonPatrocinadora(temporal);
		nuevaFicha.setClasificacionProyecto(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.CLASIFICACION_PROYECTO.getIdCatalogo(), t.getIdClasificacionProyecto(), CatClasificacionProyecto.class));
		
		Optional<CatAdministradorAdministracion> administradorPlaneacionOptional= administracionRepository.findByIdAdministradorAdministracion(t.getIdAdministradorPlaneacion());
		CatAdministracion admonPlaneacion= catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.ADMINISTRACIONES.getIdCatalogo(), t.getIdAreaPlaneacion(), CatAdministracion.class);
		
		CatAdministracion admonPlaneacionTemporal = new CatAdministracion();
		admonPlaneacionTemporal.setIdAdministracion(admonPlaneacion.getIdAdministracion());
		admonPlaneacionTemporal.setAcronimo(admonPlaneacion.getAcronimo()  );
		admonPlaneacionTemporal.setAdministracion(admonPlaneacion.getAdministracion()  );
		admonPlaneacionTemporal.setNombre(admonPlaneacion.getNombre() );
		admonPlaneacionTemporal.setPrimaryKey(admonPlaneacion.getPrimaryKey());
		
		
		if (administradorPlaneacionOptional.isPresent()) {
			CatAdministradorAdministracion administradorPlaneacion= administradorPlaneacionOptional.get();
			List<CatAdministradorAdministracion> listaAdministradorPlaneacion =new ArrayList<>();
			administradorPlaneacion.setEstatus(true);
			listaAdministradorPlaneacion.add(administradorPlaneacion);
			admonPlaneacionTemporal.setAdministradores(listaAdministradorPlaneacion);
		}else {
			admonPlaneacionTemporal.setAdministradores(admonPlaneacion.getAdministradores());
		}
		
		nuevaFicha.setAreaPlaneacion(admonPlaneacion);
		nuevaFicha.setFinanciamiento(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.FINANCIAMIENTO.getIdCatalogo(), t.getIdFinanciamiento(),CatFinanciamiento.class));
		nuevaFicha.setTipoMoneda(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.TIPO_MONEDA.getIdCatalogo(), t.getIdTipoMoneda(),CatTipoMoneda.class));
		nuevaFicha.setTipoProcedimiento(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
				CatalogosComunes.TIPO_PROCEDIMIENTO.getIdCatalogo(), t.getIdTipoProcedimiento(), CatTipoProcedimiento.class));
		nuevaFicha.setAdmonCentrales(admonCentrales);
		return nuevaFicha;
	}
}
