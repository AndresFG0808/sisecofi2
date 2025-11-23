package com.sisecofi.contratos.model.caso_negocio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "casoNegocioServicioConvenio")
@Getter
@Setter
public class CasoNegocioServicioConvenioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCasoNegocioServicio;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_caso_negocio_convenio", nullable = false)
	private CasoNegocioConvenioModel casoNegocio;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_servicio_convenio", nullable = false)
	private ServicioConvenioModel servicioConvenio;

	@Column(name = "volumetria")
	private double[] volumetria;

	@Column(name = "periodos")
	private String[] periodos;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	public CasoNegocioServicioConvenioModel() {
	}

	public CasoNegocioServicioConvenioModel(CasoNegocioConvenioModel casoNegocio,
			ServicioConvenioModel servicioConvenio, double[] volumetria, LocalDateTime fechaModificacion) {
		this.casoNegocio = casoNegocio;
		this.servicioConvenio = servicioConvenio;
		this.volumetria = volumetria;
		this.fechaModificacion = fechaModificacion;

	}

	public void actualizarVolumetria(double[] nuevaVolumetria) {
		this.volumetria = nuevaVolumetria;
		this.fechaModificacion = LocalDateTime.now();
	}

	public BigDecimal getVolumetriaSum() {
		if (volumetria == null) {
			return BigDecimal.ZERO;
		}

		return Arrays.stream(volumetria).mapToObj(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean isVolumetriaWithinMaximo() {
	    return !(servicioConvenio.getServicioBase().getGrupoServiciosModel().getCatTipoConsumo().getNombre()
	            .equals("Volumetría") && getVolumetriaSum().compareTo(servicioConvenio.getNumeroTotalServicios()) > 0);
	}


	@Override
	public String toString() {
		return "id concepto de servicio" + servicioConvenio.getIdServicioConvenio() + "| volumetria="
				+ Arrays.toString(volumetria) + "| periodos=" + Arrays.toString(periodos) + "| fechaModificacion=" + fechaModificacion;
	}

}
