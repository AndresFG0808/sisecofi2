package com.sisecofi.proyectos.service.impl;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.proyectos.microservicio.CatalogoMicroservicio;
import com.sisecofi.proyectos.microservicio.ContratoMicroservicio;
import com.sisecofi.proyectos.service.ServicioCatalogos;
import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioCatalogosImpl implements ServicioCatalogos {

    @PersistenceContext
    private EntityManager entityManager;

    private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoMicroservicio contratoMicroservicio;


    @Override
    public List<BaseCatalogoModel> obtenerContratoConvenioInfo() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.CONTRATO_CONVENIO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);

        }catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    }

    @Override
    public List<ContratoNombreDto> obtenerContratosInfo() {

        try {
			return contratoMicroservicio.obtenerContratos();

		}catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    } 
    
    @Override
    public List<ContratoNombreDto> obtenerContratosInfoIdProyecto(Long idProyecto) {

        try {
			return contratoMicroservicio.obtenerContratosIdProyecto(idProyecto);
		}catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS,e);
        }
    } 

    @Override
    public List<BaseCatalogoModel> obtenetComitesInfo() {
        try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.COMITE.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);

		}catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    }

    @Override
    public List<BaseCatalogoModel> obtenerAfectacionInfo() {

        try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.AFECTACION.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);

		}catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    }

    @Override
    public List<BaseCatalogoModel> obtenerSesionInfo() {
        try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.CLASIFICACION_SESION.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);

		}catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    }

	@Override
	public List<BaseCatalogoModel> obtenerSesionNumeroInfo() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.NUMERO_SESION.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		}catch (Exception e){
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}

	}

	@Override
    public List<BaseCatalogoModel> obtenerPlantillaInfo() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.PLANTILLA.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
        }catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    }

    @Override
    public List<BaseCatalogoModel> obtenerTipoDeMonedaInfo() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.TIPO_MONEDA.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
        }catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
        }
    }
    
    @Override
	public List<BaseCatalogoModel> obtenerAdmonGenerales() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.ADMINISTRACION_GENERAL.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		} 
	}

	@Override
	public List<BaseCatalogoModel> obtenerAdmonCentrales() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}


	@Override
	public List<BaseCatalogoModel> obtenerClasificacionProyecto() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.CLASIFICACION_PROYECTO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obtenerFinanciamiento() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.FINANCIAMIENTO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obtenerEstatus() {
		try {
			List<BaseCatalogoModel> lista2= catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS).stream()
		            .sorted(Comparator.comparing(BaseCatalogoModel::getPrimaryKey))
		            .toList();
			List<BaseCatalogoModel> lista3 = new ArrayList<>();
			for (BaseCatalogoModel base: lista2) {
				lista3.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), base.getPrimaryKey(), CatEstatusProyecto.class));
			}
			return lista3;
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obtenerTodosEstatus() {
		try {
			List<BaseCatalogoModel> lista2= catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS).stream()
		            .sorted(Comparator.comparing(BaseCatalogoModel::getPrimaryKey))
		            .toList();
			List<BaseCatalogoModel> lista3 = new ArrayList<>();
			for (BaseCatalogoModel base: lista2) {
				lista3.add(catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), base.getPrimaryKey(), CatEstatusProyecto.class));
			}
			CatEstatusProyecto todos= new CatEstatusProyecto();
			todos.setEstatus(true);
			todos.setPrimaryKey(0);
			todos.setNombre("Todos");
			todos.setIdEstatusProyecto(0);
			todos.setDescripcion("Todos");
			lista3.add(todos);
			return lista3;
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	

	@Override
	public BaseCatalogoModel obtenerEstatusInicial() {
		try {
			
				return catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), 1, CatEstatusProyecto.class);	
					
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoProcedimiento() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.TIPO_PROCEDIMIENTO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerPeriodo() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.PERIDO.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerFases() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.FASES_PROYECTOS.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerAlineaciones() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.ALINIACION.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obtenerInvestigacionMercado() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.RESPUESTA_PROVEEDOR.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerOjetivosAlineacion(Integer idAlineacion) {
		try {
			String json= String.format(Constantes.BASE_JSON_ALINEACION, idAlineacion);
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.MAPA_OBJETIVO.getIdCatalogo(),json);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerAdmonCentralPorGeneral(Integer idAdmonGeneral) {
		try {
			String json= String.format(Constantes.BASE_JSON_ADMON_CENTRAL, idAdmonGeneral);
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(),json);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerAdmistracionPorCentrales() {
		try {
			Integer idAdmonCentral;
			String json= Constantes.ADMON_CENTRAL;
			List <BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(),json);
			
			if(!lista.isEmpty()) {
				idAdmonCentral = lista.get(0).getPrimaryKey();
				String json2= String.format(Constantes.BASE_JSON_AREA_PLANEACION, idAdmonCentral);
				return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.ADMINISTRACIONES.getIdCatalogo(),json2);
			}
			return new ArrayList<>();
		}catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
		
	}

	@Override
	public List<BaseCatalogoModel> obtenerAdmistracionPorCentral(Integer idAdmoncentral) {
		try {
			String json= String.format(Constantes.BASE_JSON_AREA_PLANEACION, idAdmoncentral);
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.ADMINISTRACIONES.getIdCatalogo(),json);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obteneREstatusRCP() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obteneEstatusEnProceso() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_RCP.getIdCatalogo(), Constantes.ESTATUS_EN_PROCESO);
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<BaseCatalogoModel> obtenerUrl(){
		try {
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.URL_PORWER_BI.getIdCatalogo(),Constantes.VALIDACION_ESTATUS);
			if (lista.isEmpty()) {
				throw new ProyectoException(ErroresEnum.ERROR_URL_POWERBY);
			}
			return lista;
		} catch (Exception e) {
			throw new ProyectoException(ErroresEnum.ERROR_URL_POWERBY);
		}
	}

}
