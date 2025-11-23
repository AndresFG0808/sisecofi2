package com.sisecofi.libreria.comunes.dto.dictamen;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.springframework.format.annotation.DateTimeFormat;

import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class dictamenDto {

	private Long idDictamen;
	private Integer idEstatusDictamen;

	private Boolean estatus;

	private Long idContrato;

	private Long idProovedor;

	private String nombreCortoContrato;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private LocalDateTime periodoInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private LocalDateTime periodoFin;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private Integer periodoMes;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private Integer periodoAnio;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private int mes;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private int anio;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private Long idIva;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private BigDecimal tipoCambioReferencial;

	private Integer idTipoMoneda;

	private String nombreMoneda;

	private String descripcion;
	private String ultimaModificacion;

	private Boolean checkContractual;

	private Boolean checkConvencional;

	private Boolean checkDeducciones;
	
	private Boolean plantillaAsignada;
	
	private Long idProyecto;
	
	private Integer consecutivo;
	
	private BigDecimal ieps;
	
	private Long idConvenioModificatorio;

	public LocalDateTime getPeriodoControl() {
		log.info("Fecha mes periodo Control: {}", mes);
		log.info("Fecha año periodo Control: {}", anio);

		return LocalDateTime.of(anio, mes, 1, 0, 0).with(TemporalAdjusters.lastDayOfMonth());
	}
	
	public String getIdDictamenVisual() {
    		return nombreCortoContrato+"|"+  String.format("%05d", idProovedor) + "|" + String.format("%05d", consecutivo);
    }

}
