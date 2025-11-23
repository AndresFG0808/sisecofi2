package com.sisecofi.libreria.comunes.dto.contrato;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ContratoDtoLigeroComun {
	private Long idContrato;

	private String nombreContrato;

	private String nombreCorto;

	private String numeroContrato;

	private LocalDateTime fecha_inicio;

	private LocalDateTime fecha_termino;

	private Integer idIva;

	private BigDecimal tipoCambio;

	private List<ProveedorDto> proveedores;

	private String tipoMoneda;

	private boolean ejecucion;

	public ContratoDtoLigeroComun(Long idContrato, LocalDateTime fechaInicio, LocalDateTime fechaTermino, Integer idIva,
			BigDecimal tipoCambio, String tipoMoneda, boolean ejecucion, String nombreContrato, String nombreCorto,
			String numeroContrato) {
		this.idContrato = idContrato;
		this.fecha_inicio = fechaInicio;
		this.fecha_termino = fechaTermino;
		this.idIva = idIva;
		this.tipoCambio = tipoCambio;
		this.tipoMoneda = tipoMoneda;
		this.ejecucion = ejecucion;
		this.nombreContrato = nombreContrato;
		this.nombreCorto = nombreCorto;
		this.numeroContrato = numeroContrato;
	}

	public ContratoDtoLigeroComun() {
	}

	public String getIdContratoFormato() {
		String formato = "%05d";
		return String.format(formato, this.idContrato);
	}	
	
}
