package com.sisecofi.libreria.comunes.model.proyectos;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoDto;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedNativeQuery;
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
@Table(name = ConstantesComunes.PREFIX_REL + "usuario_asignado")
@Getter
@Setter
@NamedNativeQuery(name = "UsuarioProyectoModel.buscarProyectosUsuarioIds", query = "select p.id_proyecto as idProyecto,p.id_user as idUser from sisecofi.sscfr_usuario_asignado p where p.id_user=:idUser and p.estatus=true", resultSetMapping = "proyectoDto")
@NamedNativeQuery(name = "UsuarioProyectoModel.buscarProyectosUsuario", query = "select p.id_proyecto as idProyecto,p.id_user as idUser from sisecofi.sscfr_usuario_asignado p inner join sisecofi.sscft_proyecto a on a.id_proyecto=p.id_proyecto where p.id_user=:idUser and p.estatus=true and a.id_estatus_proyecto IN (:estatusProyecto)", resultSetMapping = "proyectoDto")
@SqlResultSetMapping(name = "proyectoDto", classes = @ConstructorResult(targetClass = ProyectoDto.class, columns = {
		@ColumnResult(name = "idProyecto"), @ColumnResult(name = "idUser") }))
public class UsuarioProyectoModel {

	@EmbeddedId
	private UsuarioAsignadoPk asignadoPk;

	@ManyToOne
	@MapsId("idProyecto")
	@JoinColumn(name = "id_proyecto")
	private ProyectoModel proyectoModel;

	@ManyToOne
	@MapsId("idUser")
	@JoinColumn(name = "id_user")
	private Usuario usuario;

	@Column(name = "fecha")
	private LocalDateTime fecha;

	@Column(name = "estatus")
	private boolean estatus;
}
