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

import com.sisecofi.contratos.repository.contrato.InformesDocumentalesPeriodicosRepository;
import com.sisecofi.contratos.service.ServicioInformesDocumentalesPeriodicos;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ReporteInformesDocumentalesPeriodicosConsumer;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.validator.InformesValidador;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioInformesDocumentalesPeriodicosImpl implements ServicioInformesDocumentalesPeriodicos {
	private final InformesDocumentalesPeriodicosRepository informesPeriodicosDao;
	private static final Logger LOGGER = LogManager.getLogger(ServicioInformesDocumentalesPeriodicosImpl.class);
	private final ReporteInformesDocumentalesPeriodicosConsumer reporteConsumer;
	private final InformesValidador validador;
	private final ContratoService contratoService;
    private final InformeDocumentalServiceHelper informeDocumentalServiceHelper;
	private static final String ERROR = "Ocurrió un error inesperado al actualizar los informes documentales Periódicos.";
	private static final String ERROR2 = "Ocurrió un error al buscar los Informes Documentales Periodicos";
	private static final String ERROR_EXPORTAR = "Ocurrió un error al generar el archivo Excel Informes Documentales";

	@Override
	public List<InformesDocumentalesPeriodicosModel> obtenerInformesDocumentalesPeriodicos(Long idContrato) {
		List<InformesDocumentalesPeriodicosModel> listaInformes = new ArrayList<>();
		try {
			listaInformes = informesPeriodicosDao.findAllByIdContratoAndEstatusTrueOrderByIdPeriodicos(idContrato);
		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			log.error(ERROR2);
		}
		return listaInformes;
	}
	
	@Override
	public InformesDocumentalesPeriodicosModel obtenerInformeDocumentalPeriodico(Long idInforme) {
		InformesDocumentalesPeriodicosModel informe = new InformesDocumentalesPeriodicosModel();
		try {
			Optional<InformesDocumentalesPeriodicosModel> informeOp = informesPeriodicosDao.findById(idInforme);
			if (informeOp.isPresent()) {
				return informeOp.get();
			}

		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			log.error(ERROR2);
		}
		return informe;
	}

	// aqui falta
	@Override
	public String guardarInformeDocumentalPeridicos(List<InformesDocumentalesPeriodicosModel> informe) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (InformesDocumentalesPeriodicosModel informesModel : informe) {
				log.info(informesModel.toString());
				if (validador.validaInformesDocumentalesPeriodicos(informesModel)) { // verificar en el ecu si se puede
																						// validar desde el modelo
					informesModel.setEstatus(true);
					informesPeriodicosDao.save(informesModel);
					resultado.append("Ok\n");
					this.contratoService.actualizarUltimaMod(informesModel.getIdContrato());
				} else {
					resultado.append("Bad Request\n");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al guardar el informe documental periodicos");
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
		return resultado.toString();
	}

	@Override
    public String eliminarInformeDocumentalPeriodicos(List<Long> ids) {
        return informeDocumentalServiceHelper.eliminarInformes(
            ids,
            informesPeriodicosDao::existsById,
            informesPeriodicosDao::findById,
            informesPeriodicosDao::save,
            InformesDocumentalesPeriodicosModel::getIdContrato,
            contratoService::actualizarUltimaMod
        );
    }

	@Override
	public String actualizarInformeDocumentalPeriodicos(List<InformesDocumentalesPeriodicosModel> ids) {
		StringBuilder resultado = new StringBuilder();

		try {
			for (InformesDocumentalesPeriodicosModel informe : ids) {
				if (informesPeriodicosDao.existsById(informe.getIdPeriodicos())) {
					InformesDocumentalesPeriodicosModel informesModel = informesPeriodicosDao
							.findById(informe.getIdPeriodicos()).orElse(null);
					if (informesModel != null) {
						informesModel.setInformeDocumental(informe.getInformeDocumental());
						informesModel.setIdPeriodicidad(informe.getIdPeriodicidad());
						informesModel.setPenaConvencionalDeductiva(informe.getPenaConvencionalDeductiva());
						informesModel.setDescripcion(informe.getDescripcion());
						informesPeriodicosDao.save(informesModel);
						resultado.append("Ok\n");
						this.contratoService.actualizarUltimaMod(informe.getIdContrato());
					}
				} else {
					resultado.append("No existe ningún registro con ID ").append(informe.getIdPeriodicos())
							.append(" en la base de datos.\n");
				}
			}
		} catch (DataAccessException | IllegalArgumentException | NullPointerException e) {
			informeDocumentalServiceHelper.logExcepcion(e, "al actualizar el Informe Documental Periódicos");
		    resultado.append(ERROR);
		}
		
		return resultado.toString();
	}

	@Override
	public String exportarExcel(Long idContrato) {
		List<InformesDocumentalesPeriodicosModel> listaInformes = obtenerInformesDocumentalesPeriodicos(idContrato);

		if (listaInformes.isEmpty()) {
			return Constantes.SIN_INFORMACION_EXCEL;
		}

		try {
			reporteConsumer.inializar("Informes documentales periódicos");
			reporteConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_INFOMES_DOCUMENTALES_PERIODICOS);
			listaInformes.forEach(reporteConsumer);
			byte[] reporte = reporteConsumer.cerrarBytes();
			return Base64.getEncoder().encodeToString(reporte);

		} catch (IllegalStateException e) {
			LOGGER.error("Estado inválido al generar el archivo Excel Informes Documentales");
			return ERROR_EXPORTAR;
		} catch (NullPointerException e) {
			LOGGER.error("Se encontró un valor nulo al generar el archivo Excel Informes Documentales",
					e);
			return ERROR_EXPORTAR;
		}

	}

}
