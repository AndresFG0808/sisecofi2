package com.sisecofi.proyectos.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.sisecofi.proyectos.dto.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
public class ProyectoIdEstatusProyectoSpec implements Specification<UsuarioProyectoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final transient ProyectoNombreDto criteria;

	public ProyectoIdEstatusProyectoSpec(ProyectoNombreDto criteria) {
		super();
		this.criteria = criteria;
	}

	@SuppressWarnings("null")
	@Override
	public Predicate toPredicate(Root<UsuarioProyectoModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		if (criteria.getIdEstatusProyecto() != null && criteria.getIdEstatusProyecto() > 0) {
			log.info("Agregando filtro IdEstatusProyecto: {}", criteria.getIdEstatusProyecto());
			return criteriaBuilder.equal(root.<String>get("proyectoModel").get("idEstatusProyecto"),
					criteria.getIdEstatusProyecto());
		}
		return null;
	}
}
