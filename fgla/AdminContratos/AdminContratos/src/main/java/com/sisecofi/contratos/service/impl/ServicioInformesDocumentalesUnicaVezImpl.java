package com.sisecofi.contratos.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.contratos.util.exception.ContratoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sisecofi.contratos.repository.contrato.InformesDocumentalesUnicaVezRepository;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesUnicaVez;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ReporteInformesDocumentalesUniVezConsumer;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.validator.InformesValidador;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioInformesDocumentalesUnicaVezImpl implements ServicioInformesDocumentalesUnicaVez {
	private final InformesDocumentalesUnicaVezRepository informesDao;
	private final InformesValidador validador;
	private final ReporteInformesDocumentalesUniVezConsumer reporteConsumer;
	private final ContratoService contratoService;
    private final InformeDocumentalServiceHelper informeDocumentalServiceHelper;

	private static final Logger LOGGER = LogManager.getLogger(ServicioInformesDocumentalesUnicaVezImpl.class);
	private static final String ERROR_ACTUALIZAR = "Ocurrió un error al actualizar los informes documentales.";
	private static final String ERROR_EXPORTAR = "Ocurrió un error al generar el archivo Excel Informes Documentales";
	private static final String MENSAJE = "al actualizar el Informe Documental Servicios";

	@Override
	public String guardarInformeDocumentalUnicaVez(List<InformesDocumentalesUnicaVezModel> informes) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (InformesDocumentalesUnicaVezModel informeModel : informes) {
				if (validador.validaInformesDocumentalesUniVez(informeModel)) { // verificar en el ecu si se puede
																				// validar desde el modelo
					informeModel.setEstatus(true);
					informesDao.save(informeModel);
					resultado.append("Ok\n");
					this.contratoService.actualizarUltimaMod(informeModel.getIdContrato());
				} else {
					resultado.append("Bad Request\n");
				}
			}
		} catch (Exception e) {
			log.error("Ocurrió un error al guardar el informe documental");
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}

		return resultado.toString();
	}

	@Override
	public List<InformesDocumentalesUnicaVezModel> obtenerInformesDocumentalesUnicaVez(Long idContrato) {
		List<InformesDocumentalesUnicaVezModel> listaInformes = new ArrayList<>();
		try {
			// Obtener todos los informes que coinciden con el idContrato desde JPA
			listaInformes = informesDao.findAllByIdContratoAndEstatusTrueOrderById(idContrato);
		} catch (DataAccessException e) {
			LOGGER.error("Error de acceso a datos al buscar los Informes Documentales");
		} catch (IllegalArgumentException e) {
			LOGGER.error("Argumento inválido al buscar los Informes Documentales");
		}

		return listaInformes;
	}
	
	@Override
	public InformesDocumentalesUnicaVezModel obtenerInformeDocumentalUv(Long idInforme) {
		InformesDocumentalesUnicaVezModel informe = new InformesDocumentalesUnicaVezModel();
			Optional<InformesDocumentalesUnicaVezModel> informeOp = informesDao.findById(idInforme);
			if (informeOp.isPresent()) {
				return informeOp.get();
			}
		return informe;
	}

	@Override
    public String eliminarInformeDocumentalUnicaVez(List<Long> ids) {
        return informeDocumentalServiceHelper.eliminarInformes(
            ids,
            informesDao::existsById,
            informesDao::findById,
            informesDao::save,
            InformesDocumentalesUnicaVezModel::getIdContrato,
            contratoService::actualizarUltimaMod
        );
    }

	@Override
	public String actualizarInformeDocumentalUnicaVez(List<InformesDocumentalesUnicaVezModel> informes) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (InformesDocumentalesUnicaVezModel informe : informes) {
				if (informesDao.existsById(informe.getId())) {
					InformesDocumentalesUnicaVezModel informesModel = informesDao.findById(informe.getId())
							.orElse(null);
					if (informesModel != null) {
						informesModel.setFase(informe.getFase());
						informesModel.setInformeDocumental(informe.getInformeDocumental());
						informesModel.setFechaEntrega(informe.getFechaEntrega());
						informesModel.setPenasDeduccionesAplicables(informe.getPenasDeduccionesAplicables());
						informesModel.setDescripcion(informe.getDescripcion());
						informesDao.save(informesModel);
						resultado.append("Ok\n");
						this.contratoService.actualizarUltimaMod(informe.getIdContrato());
					}
				} else {
					resultado.append("No existe ningún registro con ID ").append(informe.getId())
							.append(" en la base de datos.\n");
				}
			}

		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
		    informeDocumentalServiceHelper.logExcepcion(e, MENSAJE);
		    return ERROR_ACTUALIZAR;
		}
		return resultado.toString().trim();
	}

	@Override
	public String exportarExcel(Long idContrato) {
		List<InformesDocumentalesUnicaVezModel> listaInformes = obtenerInformesDocumentalesUnicaVez(idContrato);

		if (listaInformes.isEmpty()) {
			return Constantes.SIN_INFORMACION_EXCEL;
		}

		try {
			reporteConsumer.inializar("Informes Documentales por Única Vez");
			reporteConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_INFORMES_DOCUMENTALES_UNICA_VEZ);
			listaInformes.forEach(reporteConsumer);
			byte[] reporte = reporteConsumer.cerrarBytes();

			return Base64.getEncoder().encodeToString(reporte);

		} catch (DataAccessException e) {
			LOGGER.error("Error de acceso a datos al generar el archivo Excel Informes Documentales");
			return ERROR_EXPORTAR;
		} catch (IllegalArgumentException e) {
			LOGGER.info(ERROR_EXPORTAR);
			return ERROR_EXPORTAR;
		}
	}

}
