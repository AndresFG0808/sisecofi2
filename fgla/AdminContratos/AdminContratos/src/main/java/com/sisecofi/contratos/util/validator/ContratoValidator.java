package com.sisecofi.contratos.util.validator;

import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.microservicios.DevengadosMicroservicio;
import com.sisecofi.contratos.repository.contrato.*;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.libreria.comunes.dto.dictamen.PenasContractualesByIdDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.contratos.*;
import com.sisecofi.libreria.comunes.model.estimacion.EstimacionModel;
import com.sisecofi.libreria.comunes.model.penasContractuales.PenasContractualesModel;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContratoValidator {

	private static final String STATUS_CANCELADO = "Cancelado";
	private static final String LOG_VIOLATIONS = "violations {}";

	private final EstimacionRepository estimacionRepository;
	private final GrupoServicioContratoRepository grupoServicioContratoRepository;
	private final DevengadosMicroservicio devengadosMicroservicio;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoRepository contratoRepository;
	private final InformesDocumentalesUnicaVezRepository informesDocumentalesUnicaVezRepository;
	private final InformesDocumentalesPeriodicosRepository informesDocumentalesPeriodicosRepository;
	private final PenaContractualContratoRepository penaContractualContratoRepository;
	private final InformesDocumentalesServiciosRepository informesDocumentalesServiciosRepository;
	private final NivelesServicioSlaRepository nivelesServicioSlaRepository;
	private final AtrasoPresentacionRepository atrasoPresentacionRepository;
	private final Validator validator;

	public boolean fechaValidacion(LocalDateTime fechaTermino, LocalDateTime fechaTerminoCM) {

		try {
			LocalDate fechaActual = LocalDate.now();
			LocalDate fechaFormateada = fechaTermino.toLocalDate();
			LocalDate fechaTerminoCm = fechaTerminoCM.toLocalDate();

			if (fechaFormateada.isBefore(fechaActual) || fechaTerminoCm.isBefore(fechaActual)) {
				log.info("Error, fecha de sesion incorrecta: {}", fechaFormateada);
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	public boolean fechaValidacionContrato(LocalDateTime fechaTermino) {

		try {
			LocalDate fechaActual = LocalDate.now();
			LocalDate fechaFormateada = fechaTermino.toLocalDate();

			if (fechaFormateada.isBefore(fechaActual)) {
				log.info("Error, fecha de sesion incorrecta: {}", fechaFormateada);
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_CONTRATO_CADUCADO);
		}

	}

	public boolean nombresValidacion(String nombreCorto, String nombreContrato, ContratoModel contrato) {

		if (!Objects.equals(nombreCorto, contrato.getNombreCorto())
				&& !Objects.equals(nombreContrato, contrato.getNombreContrato())) {
			return true;
		} else {
			throw new ContratoException(ErroresEnum.ERROR_NOMBRES_REPETIDOS);
		}
	}

	public boolean eliminarRefistroValidacion(Long idContrato) {
		List<DevengadoBusquedaResponse> dictamenes = devengadosMicroservicio.obtenerDictamenesPorIdContrato(idContrato);
		if (!dictamenes.isEmpty()) {
			boolean existeDictamenNoCancelado = dictamenes.stream().map(DevengadoBusquedaResponse::getEstatus)
					.anyMatch(estatus -> !STATUS_CANCELADO.equals(estatus));

			if (existeDictamenNoCancelado) {
				throw new ContratoException(ErroresEnum.DICTAMEN_ASOCIADO);
			}
		}

		List<EstimacionModel> estimacionModels = devengadosMicroservicio.obtenerEstimacionesPorIdContrato(idContrato);
		if (!estimacionModels.isEmpty()) {
			for (EstimacionModel estimacionModel : estimacionModels) {
				Integer idEstatusEstimacion = estimacionModel.getIdEstatusEstimacion();

				CatEstatusEstimacion estimacion = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.ESTATUS_ESTIMACION.getIdCatalogo(), idEstatusEstimacion,
						new CatEstatusEstimacion());

				if (!Objects.equals(estimacion.getNombre(), STATUS_CANCELADO)) {
					throw new ContratoException(ErroresEnum.ESTIMACION_ASOCIADA);
				}
			}
		}

		return true;
	}

	public void validarNombresContrato(String nombreContrato, String nombreCorto, Long idContrato) {

		if (Objects.equals(nombreCorto, nombreContrato)) {
			throw new ContratoException(ErroresEnum.ERROR_NOMBRES_CONTRATO);
		}

		Optional<ContratoModel> contra = contratoRepository.findByNombreCortoOrNombreContratoAndEstatusTrue(nombreCorto,
				nombreContrato);
		if (contra.isPresent() && !contra.get().getIdContrato().equals(idContrato)) {
				throw new ContratoException(ErroresEnum.ERROR_NOMBRES_CONTRATO_EN_DB);
		
		}
	}

	public void validarInformesDocumentalesUnicaVez(List<String> nombresInformes, Long idContrato) {

		try {
			List<InformesDocumentalesUnicaVezModel> informes = informesDocumentalesUnicaVezRepository
					.findByInformeDocumentalInAndEstatusTrueAndIdContrato(nombresInformes, idContrato);
			
			if (!informes.isEmpty()) {
				for (InformesDocumentalesUnicaVezModel informesDocumentalesUnicaVez : informes) {
					Long idInformeDoc = informesDocumentalesUnicaVez.getId();

					PenasContractualesByIdDto penasContractualesByIdDto = new PenasContractualesByIdDto();
					penasContractualesByIdDto.setIdInfoDocUnicaVez(idInformeDoc);
					List<PenasContractualesModel> penasContractuales = devengadosMicroservicio
							.obtenerPenasContractuales(penasContractualesByIdDto);

					if (!penasContractuales.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
					}
				}
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_INTERNO);
		}
	}

	public void validarInformesDocumentalesPeriodicos(List<String> nombresInformes, Long idContrato) {
		try {
			List<InformesDocumentalesPeriodicosModel> informes = informesDocumentalesPeriodicosRepository
					.findByInformeDocumentalInAndEstatusTrueAndIdContrato(nombresInformes, idContrato); // el nbombre es
																										// unico??
			if (!informes.isEmpty()) {
				for (InformesDocumentalesPeriodicosModel informesDocumentalesUnicaVez : informes) {
					Long idInformeDoc = informesDocumentalesUnicaVez.getIdPeriodicos();

					PenasContractualesByIdDto penasContractualesByIdDto = new PenasContractualesByIdDto();
					penasContractualesByIdDto.setIdInfoDocPeriodicos(idInformeDoc);
					List<PenasContractualesModel> penasContractuales = devengadosMicroservicio
							.obtenerPenasContractuales(penasContractualesByIdDto);

					if (!penasContractuales.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
					}
				}
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_INTERNO);
		}
	}

	public void validarPenasContractuales(List<String> nombresPenas, Long idContrato) {
		try {
			List<PenaContractualContratoModel> informes = penaContractualContratoRepository
					.findByInformeDocumentalInAndEstatusTrueAndIdContrato(nombresPenas, idContrato); // el nbombre es
																										// unico??
			if (!informes.isEmpty()) {
				for (PenaContractualContratoModel informesDocumentalesUnicaVez : informes) {
					Long idPena = informesDocumentalesUnicaVez.getIdPenaContractualContrato();

					PenasContractualesByIdDto penasContractualesByIdDto = new PenasContractualesByIdDto();
					penasContractualesByIdDto.setIdPenaContractual(idPena);
					List<PenasContractualesModel> penasContractuales = devengadosMicroservicio
							.obtenerPenasContractuales(penasContractualesByIdDto);

					if (!penasContractuales.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
					}
				}
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_INTERNO);
		}
	}

	public void validarInfoDocServicios(List<String> nombresInformes, Long idContrato) {
		try {

			List<InformesDocumentalesServiciosModel> informes = informesDocumentalesServiciosRepository
					.findByInformeDocumentalInAndEstatusTrueAndIdContrato(nombresInformes, idContrato); // el nbombre es
																										// unico??
			if (!informes.isEmpty()) {
				for (InformesDocumentalesServiciosModel informesDocumentalesUnicaVez : informes) {
					Long idPena = informesDocumentalesUnicaVez.getIdServicios();

					PenasContractualesByIdDto penasContractualesByIdDto = new PenasContractualesByIdDto();
					penasContractualesByIdDto.setIdInfoDocServicios(idPena);
					List<PenasContractualesModel> penasContractuales = devengadosMicroservicio
							.obtenerPenasContractuales(penasContractualesByIdDto);

					if (!penasContractuales.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
					}
				}
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_INTERNO);
		}
	}

	public void validarServiciosSla(List<String> nombresInformes, Long idContrato) {
		try {

			List<NivelesServicioSLAModel> informes = nivelesServicioSlaRepository
					.findByInformeDocumentalInAndEstatusTrueAndIdContrato(nombresInformes, idContrato);
			if (!informes.isEmpty()) {
				for (NivelesServicioSLAModel informesDocumentalesUnicaVez : informes) {
					Long idSla = informesDocumentalesUnicaVez.getIdServiciosSla();

					PenasContractualesByIdDto penasContractualesByIdDto = new PenasContractualesByIdDto();
					penasContractualesByIdDto.setIdServicioSla(idSla);
					List<PenasContractualesModel> penasContractuales = devengadosMicroservicio
							.obtenerPenasContractuales(penasContractualesByIdDto);

					if (!penasContractuales.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
					}
				}
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_INTERNO);
		}
	}

	public void validarAtrasoPrestacion(List<String> nombresInformes, Long idContrato) {
		try {
			List<AtrasoPrestacionModel> informes = atrasoPresentacionRepository
					.findByPenasDeduccionesInAndEstatusTrueAndIdContrato(nombresInformes, idContrato); // el nbombre es
																										// unico??
			if (!informes.isEmpty()) {
				for (AtrasoPrestacionModel informesDocumentalesUnicaVez : informes) {
					Long idAtraso = informesDocumentalesUnicaVez.getIdAtrasoPrestacion();

					PenasContractualesByIdDto penasContractualesByIdDto = new PenasContractualesByIdDto();
					penasContractualesByIdDto.setIdAtraso(idAtraso);
					List<PenasContractualesModel> penasContractuales = devengadosMicroservicio
							.obtenerPenasContractuales(penasContractualesByIdDto);

					if (!penasContractuales.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_DICTAMENES_ASOCIADOS);
					}
				}
			}
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_INTERNO);
		}
	}

	public void validarModeloInformeDocUnicaVez(InformesDocumentalesUnicaVezModel modeloInformeDoc) {
		Set<ConstraintViolation<InformesDocumentalesUnicaVezModel>> violations = validator.validate(modeloInformeDoc);

		if (!violations.isEmpty()) {
			log.info(LOG_VIOLATIONS, violations);
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void validarModeloInfoDocPeriodico(InformesDocumentalesPeriodicosModel infoDocPeriodico) {
		Set<ConstraintViolation<InformesDocumentalesPeriodicosModel>> violations = validator.validate(infoDocPeriodico);

		if (!violations.isEmpty()) {
			log.info(LOG_VIOLATIONS, violations);
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void validarModeloPenasContractuales(PenaContractualContratoModel penaContractualContratoModel) {
		Set<ConstraintViolation<PenaContractualContratoModel>> violations = validator
				.validate(penaContractualContratoModel);

		if (!violations.isEmpty()) {
			log.info(LOG_VIOLATIONS, violations);
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void validarModeloInfoDocServicios(InformesDocumentalesServiciosModel informesDocumentalesServiciosModel) {
		Set<ConstraintViolation<InformesDocumentalesServiciosModel>> violations = validator
				.validate(informesDocumentalesServiciosModel);

		if (!violations.isEmpty()) {
			log.info(LOG_VIOLATIONS, violations);
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void validarModeloServiciosSla(NivelesServicioSLAModel nivelesServicioSLAModel) {
		Set<ConstraintViolation<NivelesServicioSLAModel>> violations = validator.validate(nivelesServicioSLAModel);

		if (!violations.isEmpty()) {
			log.info(LOG_VIOLATIONS, violations);
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void validarModeloAtrasoPrestacion(AtrasoPrestacionModel atrasoPrestacionModel) {
		Set<ConstraintViolation<AtrasoPrestacionModel>> violations = validator.validate(atrasoPrestacionModel);

		if (!violations.isEmpty()) {
			log.info(LOG_VIOLATIONS, violations);
			throw new ContratoException(ErroresEnum.DATOS_OBLIGATORIOS);
		}
	}

	public void validarEstimaciones(Long idContrato) {
		List<EstimacionModel> estimacionModel = estimacionRepository
				.findByIdContratoAndEstatusTrueAndCatEstatusEstimacionNombreNotIn(idContrato,
						List.of(STATUS_CANCELADO, "Estimado"));

		if (!estimacionModel.isEmpty()) {
			throw new ContratoException(ErroresEnum.ESTIMACION_NO_CANCELADA);
		}

	}

	public void validarDictamenes(Long idContrato) {
		List<DevengadoBusquedaResponse> dictamenes = devengadosMicroservicio.obtenerDictamenesPorIdContrato(idContrato);

		if (!dictamenes.isEmpty()) {
			for (DevengadoBusquedaResponse dictamen : dictamenes) {
				String estatusDictamen = dictamen.getEstatus();
				if (!estatusDictamen.equals(STATUS_CANCELADO) && !estatusDictamen.equals("Pagado")) {
					throw new ContratoException(ErroresEnum.DICTAMEN_NO_CANCELADO);
				}
			}
		}
	}

	public void validarMontosBolsa(List<ServicioContratoModel> servicioContratoModelList) {

		List<BaseCatalogoModel> catalogos = catalogoMicroservicio.obtenerInformacionCatalogoCampoEspecifico(
				CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(), Constantes.TIPO_CONSUMO_BOLSA);

		Integer idTipoConsumo = catalogos.get(0).getPrimaryKey();

		List<ServicioContratoModel> serviciosBolsa = new ArrayList<>();
		for (ServicioContratoModel servicioContrato : servicioContratoModelList) {
			Long idGrupo = servicioContrato.getIdGrupoServicio();
			GrupoServiciosModel grupoServiciosModel = grupoServicioContratoRepository
					.findByIdGrupoServicioAndEstatusTrue(idGrupo);
			Integer idTipoConsumoServicio = grupoServiciosModel.getIdTipoConsumo();
			if (Objects.equals(idTipoConsumo, idTipoConsumoServicio)) {
				serviciosBolsa.add(servicioContrato);
			}
		}

		serviciosBolsa.stream()
	    .collect(Collectors.groupingBy(ServicioContratoModel::getIdGrupoServicio))
	    .values()
	    .forEach(grupo -> {
	        if (grupo.size() > 1) {
	            long montosCount = grupo.stream()
	                .map(ServicioContratoModel::getMontoMaximo)
	                .distinct()
	                .count();
	            if (montosCount > 1) {
	                throw new ContratoException(ErroresEnum.ERROR_SERVICIO_TIPO_BOLSA);
	            }
	        }
	    });

	}
}
