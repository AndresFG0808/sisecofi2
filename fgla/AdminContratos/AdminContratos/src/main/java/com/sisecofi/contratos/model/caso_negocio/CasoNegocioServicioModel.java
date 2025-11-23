package com.sisecofi.contratos.model.caso_negocio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "casoNegocioServicioContrato")
@Getter
@Setter
public class CasoNegocioServicioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCasoNegocioServicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_caso_negocio", nullable = false)
    private CasoNegocioModel casoNegocio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servicio_contrato", nullable = false)
    private ServicioContratoModel servicioContrato;

    @Column(name = "volumetria")
    private double[] volumetria;
    
    @Column(name = "periodos")
	private String[] periodos;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    
    public CasoNegocioServicioModel() {}

    public CasoNegocioServicioModel(CasoNegocioModel casoNegocio, ServicioContratoModel servicioContrato, 
    		double[] volumetria, LocalDateTime fechaModificacion, String[] periodos) {
        this.casoNegocio = casoNegocio;
        this.servicioContrato = servicioContrato;
        this.volumetria = volumetria;
        this.fechaModificacion = fechaModificacion;
        this.periodos= periodos;
    }

    public void actualizarVolumetria(double[] nuevaVolumetria) {
        this.volumetria = nuevaVolumetria;
        this.fechaModificacion = LocalDateTime.now();
    }
    
    public BigDecimal getVolumetriaSum() {
        if (volumetria == null) {
            return BigDecimal.ZERO;
        }
        
        return Arrays.stream(volumetria)
                     .mapToObj(BigDecimal::valueOf) 
                     .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public boolean isVolumetriaWithinMaximo() {
        return !(servicioContrato.getGrupoServiciosModel().getCatTipoConsumo().getNombre().equals("Volumetría") 
                && getVolumetriaSum().compareTo(servicioContrato.getCantidadMaxima()) != 0);
    }

    
    @Override
	public String toString() {
		return "id concepto de servicio" + servicioContrato.getIdServicioContrato() + "| volumetria="
				+ Arrays.toString(volumetria) + "| periodos=" + Arrays.toString(periodos) + "| fechaModificacion=" + fechaModificacion;
	}
    
}
