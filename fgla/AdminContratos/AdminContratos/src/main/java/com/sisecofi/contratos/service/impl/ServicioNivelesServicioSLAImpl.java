package com.sisecofi.contratos.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import com.sisecofi.contratos.service.ContratoService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sisecofi.contratos.repository.contrato.NivelesServicioSlaRepository;
import com.sisecofi.contratos.service.ServicioNivelesServicioSLA;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ReporteNivelesServicioSLAConsumer;
import com.sisecofi.contratos.util.validator.InformesValidador;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioNivelesServicioSLAImpl implements ServicioNivelesServicioSLA {
	private final NivelesServicioSlaRepository nivelesServicioDao;
	private final InformesValidador validador;
	private final ReporteNivelesServicioSLAConsumer reporteConsumer;
	private final ContratoService contratoService;
	private final InformeDocumentalServiceHelper informeDocumentalServiceHelper;
	private static final String ERROR_NIVELES = "Ocurrio un error al buscar los Niveles de Servicio SLA";
	private static final String ERROR_ACTUALIZAR = "Ocurrio un error al actualizar el Nivel de Servicio SLA";
	private static final String ERROR_EXCEL = "Ocurrio un error al generar el archivo Excel Niveles Servicio SLA";
	private static final String ERROR_GUARDAR = "Ocurrio un error al guardar los Niveles de Servicio SLA";
	private static final String ERROR_ELIMINAR = "Ocurrio un error al elimnar el Niveles Servicio SLA";

	@Override
	public List<NivelesServicioSLAModel> obtenerNivelesServicioSLA(Long idContrato) {
		List<NivelesServicioSLAModel> listaNiveles = new ArrayList<>();
		try {
			listaNiveles = nivelesServicioDao.findAllByIdContratoAndEstatusTrueOrderByIdServiciosSla(idContrato);

		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			log.error(ERROR_NIVELES);
			listaNiveles = Collections.emptyList();
		}
		return listaNiveles;
	}

	@Override
	public String guardarNivelesServicioSLA(List<NivelesServicioSLAModel> niveles) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (NivelesServicioSLAModel nivelesModel : niveles) {
				if (validador.validaNivelesServicioSLA(nivelesModel)) {
					nivelesModel.setEstatus(true);
					nivelesServicioDao.save(nivelesModel);
					resultado.append("Ok\n");
					this.contratoService.actualizarUltimaMod(nivelesModel.getIdContrato());
				} else {
					resultado.append("Bad Request\n");
				}
			}

		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al guardar los Niveles de Servicio SLA");
			return ERROR_GUARDAR;
		} catch (IllegalArgumentException e) {
			log.error("Argumento inválido al guardar los Niveles de Servicio SLA");
			return ERROR_GUARDAR;
		} catch (NullPointerException e) {
			log.error(ERROR_GUARDAR);
			return ERROR_GUARDAR;
		}
		return resultado.toString();
	}

	@Override
	public String eliminarNivelesServicioSLA(List<Long> ids) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (Long id : ids) {
				if (nivelesServicioDao.existsById(id)) {
					NivelesServicioSLAModel nivelesModel = nivelesServicioDao.findById(id).orElse(null);
					if (nivelesModel != null) {
						nivelesModel.setEstatus(false);
						nivelesServicioDao.save(nivelesModel);
						resultado.append("Ok\n");
						this.contratoService.actualizarUltimaMod(nivelesModel.getIdContrato());
					}

				} else {
					resultado.append("No existe ningún registro con ID ").append(id).append(" en la base de datos.\n");
				}
			}

		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al eliminar los Niveles de Servicio SLA");
			return ERROR_ELIMINAR;
		} catch (IllegalArgumentException e) {
			log.error(ERROR_ELIMINAR);
			return ERROR_ELIMINAR;
		}
		return resultado.toString();
	}

	@Override
	public String actualizarNivelesServicioSLA(List<NivelesServicioSLAModel> ids) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (NivelesServicioSLAModel informe : ids) {
				if (nivelesServicioDao.existsById(informe.getIdServiciosSla())) {
					NivelesServicioSLAModel informesModel = nivelesServicioDao.findById(informe.getIdServiciosSla())
							.orElse(null);
					if (informesModel != null) {
						informesModel.setDeduccionesAplicables(informe.getDeduccionesAplicables());
						informesModel.setObjectivoMinimo(informe.getObjectivoMinimo());
						informesModel.setSla(informe.getSla());
						informesModel.setDescripcion(informe.getDescripcion());
						nivelesServicioDao.save(informesModel);
						resultado.append("Ok\n");
						this.contratoService.actualizarUltimaMod(informe.getIdContrato());
					}
				} else {
					resultado.append("No existe ningún registro con ID ").append(informe.getIdServiciosSla())
							.append(" en la base de datos.\n");
				}
			}
		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			informeDocumentalServiceHelper.logExcepcion(e, "al actualizar el Nivel de Servicio SLA");
			resultado.append(ERROR_ACTUALIZAR);
		}
		return resultado.toString();
	}

	@Override
	public String exportarExcel(Long idContrato) {
		List<NivelesServicioSLAModel> listaNiveles = obtenerNivelesServicioSLA(idContrato);
		if (listaNiveles.isEmpty()) {
			return Constantes.SIN_INFORMACION_EXCEL;
		}

		try {
			reporteConsumer.inializar("Niveles de servicio SLA");
			reporteConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_NIVELES_SERVICIO_SLA);
			listaNiveles.forEach(reporteConsumer);
			byte[] reporte = reporteConsumer.cerrarBytes();
			return Base64.getEncoder().encodeToString(reporte);

		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al generar el archivo Excel Niveles Servicio SLA");
			return ERROR_EXCEL;
		} catch (IllegalArgumentException e) {
			log.error(ERROR_EXCEL);
			return ERROR_EXCEL;
		}

	}

}
