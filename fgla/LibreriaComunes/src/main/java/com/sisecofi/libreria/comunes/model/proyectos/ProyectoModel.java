package com.sisecofi.libreria.comunes.model.proyectos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.IdProyectoGenerador;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionIncompleta;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "proyecto", indexes = {
		@Index(name = "IDX_ESTATUS_PROYECTO", columnList = "id_estatus_proyecto", unique = false) })
@Getter
@Setter
@NamedNativeQuery(name = "ProyectoModel.findByIdEstatusProyecto", query = "select p.id_proyecto as idProyecto,p.nombre_proyecto as nombreProyecto,p.nombre_corto as nombreCorto from sisecofi.sscft_proyecto p where p.id_estatus_proyecto IN (:estatusProyecto)", resultSetMapping = "proyectoNombreDto")
@NamedNativeQuery(name = "ProyectoModel.findByIdProyecto", query = "select p.id_proyecto as idProyecto,p.nombre_proyecto as nombreProyecto,p.nombre_corto as nombreCorto from sisecofi.sscft_proyecto p where p.id_proyecto IN (:idProyecto)", resultSetMapping = "proyectoNombreDto")
@SqlResultSetMapping(name = "proyectoNombreDto", classes = @ConstructorResult(targetClass = ProyectoNombreDto.class, columns = {
		@ColumnResult(name = "idProyecto"),@ColumnResult(name = "nombreProyecto"), @ColumnResult(name = "nombreCorto") }))
public class ProyectoModel implements IdProyectoGenerador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProyecto;

	@JoinColumn(name = "id_estatus_proyecto", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatEstatusProyecto.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CatEstatusProyecto catEstatusProyecto;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionIncompleta.class)
	@Column(name = "id_estatus_proyecto")
	private Integer idEstatusProyecto; // ESTATUS

	@Column(name = "nombreProyecto") // Nombre completo
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionCompleta.class)
	@Size(max = 250, message = ErroresEnum.MensajeValidation.LONGITUD_NOMBRE_COMPLETO, groups = ValidacionCompleta.class)
	private String nombreProyecto;

	@Column(name = "nombreCorto") // Nombre Corto
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionIncompleta.class)
	@Size(max = 50, message = ErroresEnum.MensajeValidation.LONGITUD_NOMBRE_CORTO, groups = ValidacionIncompleta.class)
	private String nombreCorto;

	@Column(name = "idAgp", unique = true) // id AGP
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups = ValidacionCompleta.class)
	private String idAgp;

	@OneToOne(mappedBy = "proyectoModel", fetch = FetchType.LAZY)
	@JsonIgnore
	private FichaTecnicaModel fichaTecnicaModel;

	@Column(name = "fechaCreacion")
	private LocalDateTime fechaCreacion;

	@Column(name = "fechaModificacion")
	private LocalDateTime fechaModificacion;

	@Column(name = "estatus")
	private boolean estatus;

	@Column(name = "nombre_empleado")
	private String nombreEmpleado;
	

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = ConstantesComunes.PREFIX_REL
			+ "usuario_asignado", joinColumns = @JoinColumn(name = "id_proyecto", referencedColumnName = "idProyecto"), inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"))
	private List<Usuario> usuarioAsignado;

	public ProyectoModel() {
	}

	public ProyectoModel(String nombreCorto, String nombreProyecto, String idAgp, Integer idEstatusProyecto) {
		this.idEstatusProyecto = idEstatusProyecto;
		this.nombreCorto = nombreCorto;
		this.nombreProyecto = nombreProyecto;
		this.idAgp = idAgp;

	}

	@Override
	public String getIdFormateado() {
		String formato = "%05d";
		return String.format(formato, this.idProyecto);
	}	
	
}
