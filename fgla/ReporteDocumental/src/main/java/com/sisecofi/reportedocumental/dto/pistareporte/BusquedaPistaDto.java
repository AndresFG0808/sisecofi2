package com.sisecofi.reportedocumental.dto.pistareporte;

import java.time.LocalDate;
import java.util.List;

import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.TypeObject;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class BusquedaPistaDto implements GenericReport {

	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	@FilterField(filter = "s5.id_user", type = TypeObject.TYPE_LIST)
	private List<Integer> idEmpleado;

	// @Valid
	// @NotEmpty(message = ErroresEnum.MensajeValidation.MSJ)
	// @NotNull(message = ErroresEnum.MensajeValidation.MSJ)
	@FilterField(filter = "s2.id_modulo_pista", type = TypeObject.TYPE_LIST)
	private List<Integer> idModulo;

	// @Valid
	// @NotNull(message = ErroresEnum.MensajeValidation.MSJ)
	// @NotEmpty(message = ErroresEnum.MensajeValidation.MSJ)
	@FilterField(filter = "s3.id_seccion_pista", type = TypeObject.TYPE_LIST)
	private List<Integer> idSeccion;

	@FilterField(filter = "s4.id_tipo_mov_pista", type = TypeObject.TYPE_LIST)
	private List<Integer> tipoMovimiento;

	private BusquedaPista dataReporteDto;
	private boolean acumulada;
	private int page;
	private int size;

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Integer> getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(List<Integer> idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public List<Integer> getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(List<Integer> idModulo) {
		this.idModulo = idModulo;
	}

	public List<Integer> getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(List<Integer> idSeccion) {
		this.idSeccion = idSeccion;
	}

	public List<Integer> getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(List<Integer> tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public BusquedaPista getDataReporteDto() {
		return dataReporteDto;
	}

	public void setDataReporteDto(BusquedaPista dataReporteDto) {
		this.dataReporteDto = dataReporteDto;
	}

	public boolean isAcumulada() {
		return acumulada;
	}

	public void setAcumulada(boolean acumulada) {
		this.acumulada = acumulada;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
