package com.sisecofi.proyectos.util.consumer;

import java.util.function.BiFunction;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoMetaDto;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@RequiredArgsConstructor
public class ProyectoMap implements BiFunction<ProyectoModel, Integer, ProyectoMetaDto> {

	private final CatalogoMicroservicio catalogoMicroservicio;

	@Override
	public ProyectoMetaDto apply(ProyectoModel t, Integer idAreaSolicitante) {
		ProyectoMetaDto nuevoProyecto = new ProyectoMetaDto();
		FichaTecnicaModel ficha = t.getFichaTecnicaModel();
		if (ficha!=null) {
			
			nuevoProyecto.setAreaResponsable(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
					CatalogosComunes.ADMINISTRACIONES.getIdCatalogo(), ficha.getIdAreaPlaneacion(), CatAdministracion.class));
			try {
				if(idAreaSolicitante!=null) {
					nuevoProyecto.setAreaSolicitante(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
							CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), idAreaSolicitante, CatAdmonCentral.class));
				}else if(!ficha.getIdAdmonCentrales().isEmpty()){
					nuevoProyecto.setAreaSolicitante(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
							CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), ficha.getIdAdmonCentrales().iterator().next(), CatAdmonCentral.class));
				}
			}catch (ProyectoException e) {
		        nuevoProyecto.setAreaSolicitante(null);
		    } catch (Exception e) {
		        throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
		    }

			nuevoProyecto.setFechaInicio(ficha.getFechaInicio());
			nuevoProyecto.setFechaFin(ficha.getFechaTermino());
			nuevoProyecto.setMonto(ficha.getMontoSolicitado());
			nuevoProyecto.setLiderProyecto(ficha.getNombreHistoricoActivo());
		
		}
		if (t.getFichaTecnicaModel() != null) {
			nuevoProyecto.setFicha(t.getFichaTecnicaModel());
		}
		CatEstatusProyecto estatus = t.getCatEstatusProyecto();
		nuevoProyecto.setIdProyecto(t.getIdProyecto());
		nuevoProyecto.setEstatus(estatus);
		nuevoProyecto.setNombreCorto(t.getNombreCorto());		
		return nuevoProyecto;
	}
}
