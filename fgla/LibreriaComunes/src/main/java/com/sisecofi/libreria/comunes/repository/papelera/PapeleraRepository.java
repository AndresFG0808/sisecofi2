package com.sisecofi.libreria.comunes.repository.papelera;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;

import jakarta.persistence.Tuple;

/**
 * 
 * @author omartinezj
 */

public interface PapeleraRepository extends JpaRepository<PapeleraModel, Long> {

	@Query(value = """
			select s1.id_papelera idPapelera,
			       s1.id_proyecto as idProyecto,
			       LPAD(CAST(s1.id_proyecto AS TEXT), 5, '0') as idProyectoFormateado,
			       s1.nombre_corto as nombreCorto,
			       s1.fase,
			       s1.plantilla,
			       s1.nombre_documento as nombreDocumento,
			       s1.descripcion,
			       TO_CHAR(s1.fecha, 'DD/MM/YYYY') as fechaEliminacion,
			       s1.id_user as idUsuario,
			       s1.usuario_elimina as usuarioElimina,
			       s1.tamano,
			       s1.path_nuevo as pathNuevo,
			       s1.path_original as pathOriginal,
			       s1.fecha,
			       s1.comentarios
			  from sscft_papelera s1
			 where s1.estatus = true and s1.id_user = :idUsuario
			 order by s1.id_papelera
			""", nativeQuery = true)
	List<Tuple> buscarPapeleraPorIdUsuario(@Param("idUsuario") Long idUsuario);

	@Query(value = """
			select s1.id_papelera idPapelera,
			       s1.id_proyecto as idProyecto,
			       LPAD(CAST(s1.id_proyecto AS TEXT), 5, '0') as idProyectoFormateado,
			       s1.nombre_corto as nombreCorto,
			       s1.fase,
			       s1.plantilla,
			       s1.nombre_documento as nombreDocumento,
			       s1.descripcion,
			       TO_CHAR(s1.fecha, 'DD/MM/YYYY') as fechaEliminacion,
			       s1.id_user as idUsuario,
			       s1.usuario_elimina as usuarioElimina,
			       s1.tamano,
			       s1.path_nuevo as pathNuevo,
			       s1.path_original as pathOriginal,
			       s1.fecha,
			       s1.comentarios
			  from sscft_papelera s1
			 where s1.estatus = true
			   and DATE_TRUNC('day', s1.fecha) between :fechaInicio and :fechaFinal
			 order by s1.id_papelera
			""", nativeQuery = true)
	List<Tuple> buscarPapeleraPorFecha(@Param("fechaInicio") Timestamp fechaInicio,
			@Param("fechaFinal") Timestamp fechaFinal);

	@Query(value = """
			select s1.id_papelera idPapelera,
			       s1.id_proyecto as idProyecto,
			       LPAD(CAST(s1.id_proyecto AS TEXT), 5, '0') as idProyectoFormateado,
			       s1.nombre_corto as nombreCorto,
			       s1.fase,
			       s1.plantilla,
			       s1.nombre_documento as nombreDocumento,
			       s1.descripcion,
			       TO_CHAR(s1.fecha, 'DD/MM/YYYY') as fechaEliminacion,
			       s1.id_user as idUsuario,
			       s1.usuario_elimina as usuarioElimina,
			       s1.tamano,
			       s1.path_nuevo as pathNuevo,
			       s1.path_original as pathOriginal,
			       s1.fecha,
			       s1.comentarios
			  from sscft_papelera s1
			 where s1.estatus = true
			   and s1.id_user = :idUsuario
			   and DATE_TRUNC('day', s1.fecha) between :fechaInicio and :fechaFinal
			 order by s1.id_papelera
			""", nativeQuery = true)
	List<Tuple> buscarPapeleraPorIdUsuarioYFecha(@Param("idUsuario") Long idUsuario,
			@Param("fechaInicio") Timestamp fechaInicio, @Param("fechaFinal") Timestamp fechaFinal);

	List<PapeleraModel> findByPathOriginal(String pathOriginal);

}
