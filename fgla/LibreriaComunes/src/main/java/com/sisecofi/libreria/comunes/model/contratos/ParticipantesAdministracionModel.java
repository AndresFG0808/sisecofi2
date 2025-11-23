package com.sisecofi.libreria.comunes.model.contratos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatResponsabilidad;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "participantes_administracion_contrato")
@Getter
@Setter
public class ParticipantesAdministracionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipantesAdministracionContrato;

    @JoinColumn(name = "id_responsabilidad", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatResponsabilidad.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatResponsabilidad catResponsabilidad;

    @Column(name = "id_responsabilidad")
    private Integer idResponsabilidad;

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private ContratoModel contratoModel;

    @Column(name = "id_contrato")
    private Long idContrato;

    @JoinColumn(name = "id_admon_central", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatAdmonCentral.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatAdmonCentral catAdmonCentral;

    @Column(name = "id_admon_central")
    private Integer idAdmonCentral;

    @JoinColumn(name = "id_admon_general", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatAdmonGeneral.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatAdmonGeneral catAdmonGeneral;

    @Column(name = "id_admon_general")
    private Integer idAdmonGeneral;

    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_nombre_servidor_publico")
    private Integer idNombreServicodPublico;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDateTime fechaTermino;

    @Column(name = "vigente")
    private Boolean vigente;

    @Column(name = "estatus")
    private Boolean estatus;
    
    @Column(name = "nivel")
    private Integer nivel;
    
    @Column(name = "id_referencia")
    private Integer idReferencia;
}
