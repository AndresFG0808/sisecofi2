package com.sisecofi.libreria.comunes.model.penasContractuales;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoPenaContractual;
import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
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
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "penas_Contractuales_Dictamenes")
@Getter
@Setter
public class PenasContractualesModel extends BasePenas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPenaContractual;
	
	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;
	
	@JoinColumn(name = "idTipoPenaContractual", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoPenaContractual.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatTipoPenaContractual catTipoPenaContractual;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idTipoPenaContractual")
	private Integer idTipoPenaContractual; // ESTATUS
	
	@JoinColumn(name = "idPenaContractualContrato", insertable = false, updatable = false)
    @ManyToOne(targetEntity = PenaContractualContratoModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private PenaContractualContratoModel penaContractualContratoModel;

    @Column(name = "idPenaContractualContrato")
    private Long idPenaContractualContrato;
	
	@Column(name = "monto")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal monto;

	@Column(name = "estatus")
	private Boolean estatus;

}
