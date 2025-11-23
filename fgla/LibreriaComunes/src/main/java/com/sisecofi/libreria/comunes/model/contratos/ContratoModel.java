package com.sisecofi.libreria.comunes.model.contratos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusContrato;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "contrato", indexes = {
		@Index(name = "IDX_ESTATUS_CONTRATO", columnList = "id_estatus_contrato", unique = false) })
@Getter
@Setter
@NamedNativeQuery(name = "ContratoModel.findByIdEstatusContrato", query = "select p.id_contrato as idContrato,p.nombre_corto as nombreCorto from sisecofi.sscft_contrato p where p.id_estatus_contrato IN(:estatusContrato)", resultSetMapping = "contratoNombreDto")
@SqlResultSetMapping(name = "contratoNombreDto", classes = @ConstructorResult(targetClass = ContratoNombreDto.class, columns = {
		@ColumnResult(name = "idContrato"), @ColumnResult(name = "nombreCorto") }))

public class ContratoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContrato;

	@Column(name = "nombre_contrato", nullable = false, unique = true)
	private String nombreContrato;

	@Column(name = "nombre_corto", length = 30, unique = true)
	private String nombreCorto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "id_proyecto", foreignKey = @ForeignKey(name = "FK_id_proyecto"), nullable = false)

	private ProyectoModel proyecto;

	@Column(name = "fecha_ultima_modificacion")
	private LocalDateTime fechaUltimaModificacion;

	@Column(name = "estatus")
	private Boolean estatus;

	@JoinColumn(name = "id_estatus_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatEstatusContrato.class, fetch = FetchType.LAZY)
	private CatEstatusContrato catEstatusContrato;

	@Column(name = "id_estatus_contrato")
	private Integer idEstatusContrato;

	@Column(name = "ultimo_modificador")
	private String ultimoModificador;

	@Column(name = "acta_cierre_ruta")
	private String actaCierreRuta;

	@OneToOne(mappedBy = "contratoModel", fetch = FetchType.LAZY)
	private VigenciaMontosModel vigencia;

	@OneToOne(mappedBy = "contratoModel", fetch = FetchType.LAZY)
	@JsonIgnore
	private DatosGeneralesContratoModel datosGenerales;

	@OneToMany(mappedBy = "contratoModel")
	@JsonIgnore
	private List<ConvenioModificatorioModel> conveniosModificatorios;

	@Column(name = "ultimo_cm")
	private String ultimoCm;

	@JsonIgnore
	public ConvenioModificatorioModel getUltimoConvenioModificatorio() {
		return conveniosModificatorios.stream()
				.max(Comparator.comparing(ConvenioModificatorioModel::getIdConvenioModificatorio)).orElse(null);
	}

	public String getNumeroContrato() {
		if (datosGenerales != null) {
			return datosGenerales.getNumeroContrato();
		}
		return null;
	}

	@JsonIgnore
	public ConvenioModificatorioModel getPenultimoConvenioModificatorio() {
		return conveniosModificatorios.stream()
				.sorted(Comparator.comparing(ConvenioModificatorioModel::getIdConvenioModificatorio).reversed()).skip(1)
				.findFirst().orElse(null);
	}

	@JsonIgnore
	public BigDecimal getSubtotal() {
		ConvenioModificatorioModel ultimoConvenio = getUltimoConvenioModificatorio();
		BigDecimal subtotal = BigDecimal.ZERO;
		if (ultimoConvenio != null) {
			subtotal = ultimoConvenio.getSubtotal();
		} else if (vigencia != null) {
			subtotal = vigencia.getMontoMaximoSinImpuestos();
		}
		return subtotal;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "contratoModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ServicioContratoModel> serviciosContrato;

	public List<ServicioContratoModel> getServiciosContrato() {
		return serviciosContrato;
	}

	public boolean getTieneIeps() {
		if (serviciosContrato == null || serviciosContrato.isEmpty()) {
			return false;
		}
		return serviciosContrato.stream().anyMatch(servicio -> Boolean.TRUE.equals(servicio.getIeps()));
	}

	public boolean isEjecucion() {
	    return catEstatusContrato != null &&
	           ("Ejecución".equals(catEstatusContrato.getNombre()) ||
	            "Ejecucion".equals(catEstatusContrato.getNombre()));
	}


}
