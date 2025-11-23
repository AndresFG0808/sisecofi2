package com.sisecofi.libreria.comunes.model.proveedores;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "directorioProveedor")
@Getter
@Setter
public class DirectorioProveedorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDirectorioContacto;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Size(max = 250)
	@Column(name = "nombreContacto")
	private String nombreContacto;

	@Size(max = 100)
	@Column(name = "telefonoOficina")
	private String telefonoOficina;

	@Size(max = 100)
	@Column(name = "telefonoCelular")
	private String telefonoCelular;

	
	@Size(max = 200)
	@Column(name = "correoElectronico")
	private String correoElectronico;

	@Column(name = "representanteLegal")
	private Boolean representanteLegal;

	@Column(name = "comentarios")
	@Size(max = 250)
	private String comentarios;

	@Column ( nullable = false, columnDefinition = "boolean default false" )
	private boolean estatus;

	@Column(name="fechaModificacion")
	private LocalDateTime fechaModificacion;

	@Column(name="orden_directorio")
	private Integer ordenDirectorio;

	
	@ManyToOne
	@JoinColumn(name = "idProveedor", foreignKey = @ForeignKey(name = "FK_idProveedor"), nullable = true)
	@JsonBackReference
	private ProveedorModel proveedorModel;
}
