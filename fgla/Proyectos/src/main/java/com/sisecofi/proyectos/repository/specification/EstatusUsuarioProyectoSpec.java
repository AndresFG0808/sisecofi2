package com.sisecofi.proyectos.repository.specification;

import org.springframework.data.jpa.domain.Specification;

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
public class EstatusUsuarioProyectoSpec implements Specification<UsuarioProyectoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final boolean estatus;

	public EstatusUsuarioProyectoSpec(boolean estatus) {
		super();
		this.estatus = estatus;
	}

	@SuppressWarnings("null")
	@Override
	public Predicate toPredicate(Root<UsuarioProyectoModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		log.info("Agregando filtro estatus: {}", estatus);
		return criteriaBuilder.equal(root.<String>get("estatus"), estatus);
	}
}