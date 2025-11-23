package com.sisecofi.contratos.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.sisecofi.contratos.service.ContratoService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sisecofi.contratos.repository.contrato.InformesDocumentalesServiciosRepository;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesServicios;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ReporteInformeDocumentalesServiciosConsumer;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioInformesDocumentalesServiciosImpl implements ServicioInformesDocumentalesServicios {
	private final InformesDocumentalesServiciosRepository informesServiciosDao;
	private final ReporteInformeDocumentalesServiciosConsumer reporteConsumer;
	private final ContratoService contratoService;
	private final InformeDocumentalServiceHelper informeDocumentalServiceHelper;
	private static final String ERROR = "Ocurrió un error inesperado al eliminar el Informe Documental Servicios.";
	private static final String EXCEPTION = "Ocurrio un error al generar el archivo Excel Informes Servicio";
	private static final String EXCEPTION2 = "Ocurrio un error al guardar el Informe Documental Servicios";
	private static final String ERROR_OBTENER = "Ocurrio un error al buscar los Informes Documentales Periodicos";
	private static final String ERROR_ACTUALIZAR = "Ocurrio un error al actulizar el Informe Documental Servicios";

	@Override
	public List<InformesDocumentalesServiciosModel> obtenerInformesDocumentalesServicios(Long idContrato) {
		List<InformesDocumentalesServiciosModel> listaInformes = new ArrayList<>();
		try {
			listaInformes = informesServiciosDao.findAllByIdContratoAndEstatusTrueOrderByIdServicios(idContrato);

		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			log.error(ERROR_OBTENER);
		}
		return listaInformes;
	}
	
	@Override
	public InformesDocumentalesServiciosModel obtenerInformeDocumental(Long idInforme) {
		InformesDocumentalesServiciosModel informe = new InformesDocumentalesServiciosModel();
		try {
			Optional<InformesDocumentalesServiciosModel> informeOp = informesServiciosDao.findById(idInforme);
			if (informeOp.isPresent()) {
				return informeOp.get();
			}

		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			log.error(ERROR_OBTENER);
		}
		return informe;
	}

	@Override
	public String guardarInformeDocumentalServicios(List<InformesDocumentalesServiciosModel> informe) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (InformesDocumentalesServiciosModel informesModel : informe) {
				// verificar en el ecu si se puede validar desde el modelo
				informesModel.setEstatus(true);
				informesServiciosDao.save(informesModel);
				resultado.append("Ok\n");
				this.contratoService.actualizarUltimaMod(informesModel.getIdContrato());
			}

		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al guardar el Informe Documental Servicios");
			resultado.append(EXCEPTION2);
		} catch (IllegalArgumentException e) {
			log.error("Argumento inválido al guardar el Informe Documental Servicios");
			resultado.append(EXCEPTION2);
		} catch (NullPointerException e) {
			log.error("Valor nulo encontrado al guardar el Informe Documental Servicios");
			resultado.append(EXCEPTION2);
		}
		return resultado.toString();
	}

	@Override
	public String eliminarInformeDocumentalServicios(List<Long> ids) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (Long id : ids) {
				if (informesServiciosDao.existsById(id)) {
					InformesDocumentalesServiciosModel informesModel = informesServiciosDao.findById(id).orElse(null);
					if (informesModel != null) {
						informesModel.setEstatus(false);
						informesServiciosDao.save(informesModel);
						resultado.append("Ok\n");
						this.contratoService.actualizarUltimaMod(informesModel.getIdContrato());
					}

				} else {
					resultado.append("No existe ningún registro con ID ").append(id).append(" en la base de datos.\n");
				}
			}
		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al eliminar el Informe Documental Servicios");
			resultado.append(ERROR);
		} catch (IllegalArgumentException e) {
			log.error("Argumento inválido al eliminar el Informe Documental Servicios");
			resultado.append(ERROR);
		} catch (Exception e) {
			log.error("Error inesperado al eliminar el Informe Documental Servicios");
			resultado.append(ERROR);
		}
		return resultado.toString();
	}

	@Override
	public String actualizarInformeDocumentalServicios(List<InformesDocumentalesServiciosModel> ids) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (InformesDocumentalesServiciosModel informe : ids) {
				if (informesServiciosDao.existsById(informe.getIdServicios())) {
					InformesDocumentalesServiciosModel informesModel = informesServiciosDao
							.findById(informe.getIdServicios()).orElse(null);
					if (informesModel != null) {
						informesModel.setInformeDocumental(informe.getInformeDocumental());
						informesModel.setFechaEntrega(informe.getFechaEntrega());
						informesModel.setPenasDeduccionesAplicables(informe.getPenasDeduccionesAplicables());
						informesModel.setIdPeriodicidad(informe.getIdPeriodicidad());
						informesModel.setDescripcion(informe.getDescripcion());
						informesServiciosDao.save(informesModel);
						resultado.append("Ok\n");
						this.contratoService.actualizarUltimaMod(informesModel.getIdContrato());
					}
				} else {
					resultado.append("No existe ningún registro con ID ").append(informe.getIdServicios())
							.append(" en la base de datos.\n");
				}
			}
		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			informeDocumentalServiceHelper.logExcepcion(e, "al actualizar el Informe Documental Servicios");
			resultado.append(ERROR_ACTUALIZAR);
		}
		return resultado.toString();
	}

	@Override
	public String exportarExcel(Long idContrato) {
		List<InformesDocumentalesServiciosModel> listaInformes = obtenerInformesDocumentalesServicios(idContrato);
		if (listaInformes.isEmpty()) {
			return Constantes.SIN_INFORMACION_EXCEL;
		}

		try {
			reporteConsumer.inializar("Informes documentales de los servicios");
			reporteConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_INFORMES_DOCUMENTALES_SERVICIOS);
			listaInformes.forEach(reporteConsumer);
			byte[] reporte = reporteConsumer.cerrarBytes();
			return Base64.getEncoder().encodeToString(reporte);

		} catch (DataAccessException e) {
			log.error("Error de acceso a datos al generar el archivo Excel Informes Documentales");
			return EXCEPTION;
		} catch (IllegalArgumentException e) {
			log.error("Parámetro inválido al generar el archivo Excel Informes Documentales");
			return EXCEPTION;
		} catch (NullPointerException e) {
			log.error("Se encontró un valor nulo al generar el archivo Excel Informes Documentales");
			return EXCEPTION;
		}
	}

}
