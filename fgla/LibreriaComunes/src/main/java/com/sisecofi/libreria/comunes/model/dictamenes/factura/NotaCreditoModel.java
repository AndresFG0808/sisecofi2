package com.sisecofi.libreria.comunes.model.dictamenes.factura;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusNotaCredito;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "notas_credito")
@Getter
@Setter
public class NotaCreditoModel extends FacturaNotaBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotaCredito;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "fechaGeneracion")
	private LocalDateTime fechaGeneracion;
	
	@JoinColumn(name = "idEstatusNotaCredito", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatEstatusNotaCredito.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatEstatusNotaCredito catEstatusNotaCredito;
	
	@Column(name = "idEstatusNotaCredito")
	private Long idEstatusNotaCredito;
	

}
