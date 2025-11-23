package com.sisecofi.contratos.model.caso_negocio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sisecofi.contratos.util.consumer.BigDecimalMatrixToJsonConverter;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "casoNegocioConvenio")
@Getter
@Setter
public class CasoNegocioConvenioModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCasoNegocioConvenio;

	@Column(name = "estatus")
    private boolean estatus;
    
    @Column(name = "fechaModificacion")
	private LocalDateTime fechaModificacion;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_convenio_modificatorio", nullable = false)
    private ConvenioModificatorioModel convenioModificatorioModel;    
	
	@OneToMany(mappedBy = "casoNegocio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CasoNegocioServicioConvenioModel> casoNegocioServicios;
	
	@Column(name = "volumeria")
    @Convert(converter = BigDecimalMatrixToJsonConverter.class)
    private BigDecimal[][] volumeria;
	
	

	public CasoNegocioConvenioModel(boolean estatus, ConvenioModificatorioModel convenioModificatorioModel,
			BigDecimal[][] volumeria) {
		super();
		this.estatus = estatus;
		this.convenioModificatorioModel = convenioModificatorioModel;
		this.volumeria = volumeria;
	}

	public CasoNegocioConvenioModel() {
		super();
	}

   
}
