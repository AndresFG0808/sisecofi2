package com.sisecofi.libreria.comunes.model.dictamenes.factura;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusFactura;
import com.sisecofi.libreria.comunes.model.dictamenes.ReferenciaPagoModel;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "facturas")
@Getter
@Setter
public class FacturaModel extends FacturaNotaBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFactura;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "fechaFacturacion")
	private LocalDateTime fechaFacturacion;
	
	@JoinColumn(name = "idEstatusFactura", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatEstatusFactura.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatEstatusFactura catEstatusFactura;
	
	@Column(name = "idEstatusFactura")
	private Long idEstatusFactura;
	
	@OneToMany(mappedBy = "facturaModel", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ReferenciaPagoModel> referenciaPago;
	

}
