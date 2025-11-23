package com.sisecofi.libreria.comunes.model.contratos;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "penaContractualContrato")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PenaContractualContratoModel extends BaseInformesModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPenaContractualContrato;

	@NotBlank
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "informe_documento_oncepto")
	private String informeDocumentoConcepto;

	@NotBlank
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "plazo_entrega")
	private String plazoEntrega;

	@NotBlank
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "pena_aplicable")
	private String penaAplicable;
	
	@Override
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@NotBlank
    public String getDescripcion() {
        return super.getDescripcion();
    }

   
	

}
