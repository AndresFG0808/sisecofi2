package com.sisecofi.libreria.comunes.model.contratos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.proveedor.ProveedorLigeroDto;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_REL + "asociacion_contrato_proveedor")
@Getter
@Setter
public class AsociacionContratoProveedorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratoProveedor;

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private ContratoModel contratoModel;

    @Column(name = "id_contrato")
    private Long idContrato;

    @JoinColumn(name = "id_proveedor", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ProveedorLigeroDto.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private ProveedorLigeroDto proveedorModel;

    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(name = "estatus")
    private Boolean estatus;
}
