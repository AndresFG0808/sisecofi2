package com.sisecofi.libreria.comunes.model.papelera;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "Papelera")
@Data
public class PapeleraModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPapelera;

	@Column(name = "fecha")
	private LocalDateTime fecha;

	@Column(name = "pathOriginal")
	private String pathOriginal;

	@Column(name = "pathNuevo")
	private String pathNuevo;

	@Column(name = "comentarios")
	private String comentarios;

	@Column(name = "id_proyecto")
	private Long idProyecto;

	@Column(name = "id_user")
	private Long idUsuario;

	@Column(name = "nombre_corto")
	private String nombreCorto;

	@Column(name = "fase")
	private String fase;

	@Column(name = "plantilla")
	private String plantilla;

	@Column(name = "nombre_documento")
	private String nombreDocumento;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "usuario_elimina")
	private String usuarioElimina;

	@Column(name = "tamano")
	private String tamano;

	@Column(name = "estatus")
	private boolean estatus;

	@Column(name = "id_archivo")
	private Integer idArchivo;

	@Column(name = "tipo_archivo")
	private String tipoArchivo;
	
}
