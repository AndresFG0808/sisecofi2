package com.sisecofi.admindevengados.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.sisecofi.admindevengados.microservicio.CatalogoMicroservicio;
import com.sisecofi.admindevengados.repository.ContratoRepository;
import com.sisecofi.admindevengados.repository.contrato.DatosGeneralesContratoRepository;
import com.sisecofi.admindevengados.service.CatalogoService;
import com.sisecofi.admindevengados.util.Constantes;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatClaveProducto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoPenaContractual;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.admindevengados.util.exception.CatalogoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final SeguridadService seguridadService;
	private final ContratoRepository contratoRepository;
	private final DatosGeneralesContratoRepository datosGeneralesContratoRepository;

	@Override
	public List<BaseCatalogoModel> obtenerEstatusDictamenes() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerEstatusFacturas() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_FACTURA.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerEstatusNotasCredito() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_NOTA_CREDITO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoPlantillador() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.PLANTILLA.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoNotificacionPago() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.TIPO_NOTIFICACION_PAGO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerDesglose() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.DESGLOCE.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerMoneda() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.TIPO_MONEDA.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoConsumos() {
		boolean acceso = seguridadService.validarRolVerificadorGeneral();

		try {
			if (acceso) {
				return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
						CatalogosComunes.TIPO_CONSUMO_DEVENGADOS.getIdCatalogo(), Constantes.FILTRO_DICTAMINADO);
			} else {
				return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
						CatalogosComunes.TIPO_CONSUMO_DEVENGADOS.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public BaseCatalogoModel obtenerEstatusInicial() {
		try {
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.ESTATUS_INICIAL);
			if (!lista.isEmpty()) {
				return catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
						CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), lista.get(0).getPrimaryKey(),
						CatEstatusProyecto.class);
			} else {
				return null;
			}

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoPenaDeduccion(DictamenId dictamenId) {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.TIPO_DEDUCCION.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}

	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoPenaContractual(DictamenId dictamenId) {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.TIPO_PENA_CONTRACTUAL.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}

	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoPenaConvencional(DictamenId dictamenId) {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.TIPO_PENA_CONVENCIONAL.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}

	}

	@Override
	public boolean aplicaCC(Long idContrato) {
		Boolean convenioValido = datosGeneralesContratoRepository.existeConvenioValido(idContrato);
		return convenioValido != null && convenioValido;

	}

	@Override
	public List<BaseCatalogoModel> obtenerDesglose(Long idContrato) {
		try {
			Boolean aplicaCC = aplicaCC(idContrato);
			if (Boolean.TRUE.equals(aplicaCC)) {
				return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.DESGLOCE.getIdCatalogo(),
						Constantes.VALIDACION_ESTATUS);
			} else {
				return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.DESGLOCE.getIdCatalogo(),
						Constantes.VALIDACION_ESTATUS_NOMBRE);
			}

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS,e);
		}
	}

	@Override
	public BaseCatalogoModel obtenerPenaContractual() {
		try {
			List<BaseCatalogoModel> lista = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
					CatalogosComunes.TIPO_DEDUCCION.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
			if (!lista.isEmpty()) {
				return catalogoMicroservicio.obtenerInformacionCatalogoIdEspecifico(
						CatalogosComunes.TIPO_DEDUCCION.getIdCatalogo(), lista.get(0).getPrimaryKey(),
						CatTipoPenaContractual.class);
			} else {
				return null;
			}

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS, e);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerContratoVigente() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.CONTRATO_VIGENTE.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerFasesDictamen() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.FASE_DICTAMEN.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerMeses() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.PERIODO_CONTROL_MES.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerPeriodoControlAnio() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.PERIODO_CONTROL_ANIO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	// SE DEBE AGREGAR LONG IDCONTRATO
	@Override
	public Set<BaseCatalogoModel> obtenerIva(Long idContrato) {
	    try {
	        Optional<ContratoModel> contrato = contratoRepository.findByIdContrato(idContrato);
	        Set<BaseCatalogoModel> lista= new LinkedHashSet<>();
	        
	        if (contrato.isPresent()) {
	        	if (contrato.get().getUltimoConvenioModificatorio()!=null) {
	        		lista.add(contrato.get().getUltimoConvenioModificatorio().getCatIva());
	        	}
	        	List<ConvenioModificatorioModel> conveniosModificatorios = contrato.get().getConveniosModificatorios();
	            if (conveniosModificatorios != null && !conveniosModificatorios.isEmpty()) {
	            	for (ConvenioModificatorioModel conv: conveniosModificatorios) {
	            		lista.add(conv.getCatIva());
	            	}
	            }
	            lista.add(contrato.get().getVigencia().getCatIva());

	            return lista;
	        } else {
	            throw new CatalogoException(ErroresEnum.CONTRATO_NO_ENCONTRADO);
	        }
	    } catch (Exception e) {
	        throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
	    }
	}

	@Override
	public List<BaseCatalogoModel> obtenerIvaLista() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.IVA.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}
	
	@Override
	public List<CatClaveProducto> obtenerCatalogoClaveProducto() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampoReference(
					CatalogosComunes.CLAVE_PRODUCTO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS, new CatClaveProducto());

		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR_CATALOGOS);
		}
	}

}
