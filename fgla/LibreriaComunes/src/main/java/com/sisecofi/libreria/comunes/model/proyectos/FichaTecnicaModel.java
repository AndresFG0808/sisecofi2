package com.sisecofi.libreria.comunes.model.proyectos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatClasificacionProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatFinanciamiento;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoProcedimiento;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionIncompleta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "fichaTecnica")
@Getter
@Setter
public class FichaTecnicaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFichaTecnica;

	@JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
	@OneToOne(targetEntity = ProyectoModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ProyectoModel proyectoModel;

	@Column(name = "id_proyecto")
	private Long idProyecto;

	@JoinColumn(name = "id_admon_patrocinadora", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdmonGeneral.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatAdmonGeneral catAdmonPatrocinadora;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "id_admon_patrocinadora")
	private Integer idAdmonPatrocinadora;
	
	@JoinColumn(name = "id_admon_participante", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdmonGeneral.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatAdmonGeneral catAdmonParticipante;
 
	@Column(name = "id_admon_participante")
	private Integer idAdmonParticipante;
	
	@JoinColumn(name = "id_financiamiento", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatFinanciamiento.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatFinanciamiento catFinanciamiento;

	@Column(name = "id_financiamiento")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	private Integer idFinanciamiento;
	
	@JoinColumn(name = "id_tipo_procedimiento", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoProcedimiento.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatTipoProcedimiento catTipoProcedimiento;

	@Column(name = "id_tipo_procedimiento")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	private Integer idTipoProcedimiento;

	@NotEmpty(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	private Set<Integer> idAdmonCentrales;

	@JoinColumn(name = "id_clasificacion_proyecto", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatClasificacionProyecto.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatClasificacionProyecto catClasificacionProyecto;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "id_clasificacion_proyecto")
	private Integer idClasificacionProyecto;

	@JoinColumn(name = "id_area_planeacion", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdministracion.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatAdministracion catAreaPlaneacion;

	@Column(name = "id_area_planeacion")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	private Integer idAreaPlaneacion;

	@JoinColumn(name = "id_tipo_moneda", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoMoneda.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatTipoMoneda catTipoMoneda;

	@Column(name = "id_tipo_moneda")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	private Integer idTipoMoneda;

	@Column(name = "fecha_inicio")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionIncompleta.class)
	private LocalDate fechaInicio;

	@Column(name = "fecha_termino")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionIncompleta.class)
	private LocalDate fechaTermino;

	@Column(name = "objetivo_general")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	private String objetivoGeneral;

	@Column(name = "alcance")
	private String alcance;
 
	@Column(name = "monto_solicitado")
	private BigDecimal montoSolicitado;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "fichaTecnicaModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FichaAdmonCentral> fichasAdmonCentral;

	@JsonIgnore
	public CatAdmonCentral getAdmonCentral(Integer id) {
	    return fichasAdmonCentral.stream()
	            .map(FichaAdmonCentral::getCatAdmonCentral)
	            .filter(catAdmonCentral -> id == null || catAdmonCentral.getIdAdmonCentral().equals(id))
	            .findFirst()
	            .orElse(null);
	}
    
	@JsonIgnore
    @OneToMany(mappedBy = "fichaTecnicaModel", fetch = FetchType.LAZY)
    private List<HistoricoModel> historicos;
	
	@JsonIgnore
	public HistoricoModel getHistoricoModel() {
	    return historicos.stream()
	            .filter(HistoricoModel::isEstatus) 
	            .findFirst()                      
	            .orElse(null);                   
	}


    @JsonIgnore
    public String getNombreHistoricoActivo() {
        return historicos.stream()
                .filter(HistoricoModel::isEstatus) 
                .map(HistoricoModel::getNombre)    
                .findFirst()                     
                .orElse(null);                  
    }
    
    @JoinColumn(name = "id_administrador_patrocinador", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdministradorGeneral.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatAdministradorGeneral administradorPatrocinador;

	@Column(name = "id_administrador_patrocinador")
	private Integer idAdministradorPatrocinador;
	
	@JoinColumn(name = "id_administrador_participante", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdministradorGeneral.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatAdministradorGeneral administradorParticipante;

	@Column(name = "id_administrador_participante")
	private Integer idAdministradorParticipante;
	
	
	@JoinColumn(name = "id_administrador_planeacion", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatAdministradorAdministracion.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatAdministradorAdministracion administradorPlaneacion;

	@Column(name = "id_administrador_planeacion")
	private Integer idAdministradorPlaneacion;

}
