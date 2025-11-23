package com.sisecofi.libreria.comunes.model.contratoModificatorio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "contrato_modificatorio")
@Getter
@Setter
public class ContratoModificatorioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratoModificatorio;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "nombreCorto")
    private String nombreCorto;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDateTime fechaTermino;

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "id_contrato")
	private Long idContrato;

    @Column(name = "monto_maximo")
    private BigDecimal montoMaximo;

    @Column(name = "monto_pesos")
    private BigDecimal montoPesos;
}
