package com.sisecofi.libreria.comunes.model.contratos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatConvenioColaboracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatDominiosTecnologicos;
import com.sisecofi.libreria.comunes.model.catalogo.CatFondeoContrato;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoProcedimiento;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "datos_generales_contrato")
@Getter
@Setter
public class DatosGeneralesContratoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDatosGnerales;

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
    @OneToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private ContratoModel contratoModel;

    @Column(name = "id_contrato", nullable = false)
    private Long idContrato;

    @Column(name = "numero_contrato")
    private String numeroContrato;

    @Column(name = "numero_contrato_compra_net")
    private String numetoContratoCompraNet;

    @Column(name = "acuerdo")
    private String acuerdo;

    @JoinColumn(name = "id_tipo_procedimiento", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatTipoProcedimiento.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatTipoProcedimiento catTipoProcedimiento;

    @Column(name = "id_tipo_procedimiento")
    private Integer idTipoProcedimiento;

    @Column(name = "numero_prodecimiento")
    private String numeroProcedimiento;

    @Column(name = "convenio_colaboracion")
    private  Boolean convenioColaboracion;

    @JoinColumn(name = "id_convenio_colaboracion", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatConvenioColaboracion.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatConvenioColaboracion catConvenioColaboracion;

    @Column(name = "id_convenio_colaboracion")
    private Integer idConvenioColaboracion;

    @JoinColumn(name = "id_dominios_tecnologicos", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatDominiosTecnologicos.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatDominiosTecnologicos catDominoTecnologico;

    @Column(name = "id_dominios_tecnologicos")
    private Integer idDominosTecnologicos;

    @JoinColumn(name = "id_fondeo_contrato", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatFondeoContrato.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatFondeoContrato catFondeoContrato;

    @Column(name = "id_fondeo_contrato")
    private Integer idFondeoContrato;

    @Column(name = "objetivo_servicio")
    private String objetivoServicio;

    @Column(name = "alcance_servicio")
    private String alcanceServicio;

    @Column(name = "titulo_servicio", nullable = false)
    private String tituloServicio;

    @Column(name = "estatus")
    private Boolean estatus;
}
