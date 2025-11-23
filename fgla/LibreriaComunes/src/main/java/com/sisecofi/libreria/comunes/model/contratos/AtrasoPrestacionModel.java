package com.sisecofi.libreria.comunes.model.contratos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = ConstantesComunes.PREFIX_TRAB + "atraso_prestacion")
@Entity
public class AtrasoPrestacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtrasoPrestacion;

    @JoinColumn(name = "id_contrato_convenio", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private ContratoModel contratoModel;

    @Column(name = "id_contrato")
    private Long idContrato;

    @NotBlank
    @NotNull(message = "descripcion obligatorio")
    @Column(name = "descripcion")
    private String descripcion;

    @NotBlank
    @NotNull(message = "fecha prestacion obligatoria")
    @Column(name = "fecha_prestacion")
    private String fechaPrestacion;

    @NotBlank
    @NotNull(message = "penas deducciones obligatoria")
    @Column(name = "penas_deducciones")
    private String penasDeducciones;

    @Column(name = "estatus")
    private Boolean estatus;
    
    public String getInformeDocumental() {
		return descripcion;
	}
}
