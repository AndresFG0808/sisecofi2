package com.sisecofi.libreria.comunes.model.proveedores;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "dictamenTecnicoProveedor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictamenTecnicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDictamenTecnicoProveedor;

    // Forzar la carga de la relación con `EAGER`
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idServicioTitulo", foreignKey = @ForeignKey(name = "FK_idServicioTitulo"), nullable = false)
    private CatTituloServicio catTituloServicioModel;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idServicioTitulo", insertable = false, updatable = false)
    private Integer idTituloServicio;


    @NotBlank(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Size(max = 4)
    @Column(name = "anio")
    public String anio;

    @NotBlank(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Size(max = 100)
    @Column(name = "responsable")
    private String responsable;

    // Forzar la carga de la relación con `EAGER`
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idResultadoDictamenTecnico", foreignKey = @ForeignKey(name = "FK_idResultadoDictamenTecnico"), nullable = false)
    private CatResultadoDictamenTecnicoModel catResultadoDictamenTecnicoModel;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idResultadoDictamenTecnico", insertable = false, updatable = false)
    private Integer idResultadoDictamenTecnico;

    @NotBlank(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Size(max = 250)
    @Column(name = "observacion")
    private String observacion;

    // Forzar la carga de la relación con `EAGER`
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProveedor", foreignKey = @ForeignKey(name = "FK_idProveedor"), nullable = true)
    @JsonBackReference
    private ProveedorModel proveedorModel;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean estatusEliminacionLogicaDictamen;

    @Column(name = "fechaBajaDictamen")
    private LocalDateTime fechaBajaDictamen;

    @Column(name = "orden_dictamen_proveedor")
    private Integer ordenDictamenProveedor;
}
