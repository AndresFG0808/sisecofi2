package com.sisecofi.proyectos.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;
import com.sisecofi.proyectos.dto.ProyectoNombreDto;

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
public class ProyectoNombreCortoSpec implements Specification<UsuarioProyectoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final transient ProyectoNombreDto criteria;

	public ProyectoNombreCortoSpec(ProyectoNombreDto criteria) {
		super();
		this.criteria = criteria;
	}

	@SuppressWarnings("null")
	@Override
	public Predicate toPredicate(Root<UsuarioProyectoModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		if (criteria.getNombreCorto() != null && !criteria.getNombreCorto().equals("")) {
			log.info("Agregando filtro nombreCorto: {}", criteria.getNombreCorto());
			return criteriaBuilder.equal(root.<String>get("proyectoModel").get("nombreCorto"),
					criteria.getNombreCorto());
		}
		return null;
	}
}
