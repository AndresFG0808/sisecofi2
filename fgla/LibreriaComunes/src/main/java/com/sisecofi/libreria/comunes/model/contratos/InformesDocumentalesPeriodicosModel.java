package com.sisecofi.libreria.comunes.model.contratos;

import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodicidad;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "informes_documentales_periodicos")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class InformesDocumentalesPeriodicosModel extends BaseInformesModel {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idPeriodicos;

@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
@Column(name = "informe_Documental", nullable = false)
private String informeDocumental;

@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
@Column(name = "pena_convencional_deductiva", nullable = false)
private String penaConvencionalDeductiva;

@JoinColumn(name = "id_periodicidad", insertable = false, updatable = false)
@ManyToOne(targetEntity = CatPeriodicidad.class, fetch = FetchType.EAGER)
private CatPeriodicidad catPeriodicidad;

@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
@Column(name = "id_periodicidad")
private Integer idPeriodicidad;
}
