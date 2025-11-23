package com.sisecofi.proyectos.util.consumer;

import org.springframework.data.jpa.domain.Specification;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.model.proyectos.UsuarioProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.EstructuraProyectoMetaDto;
import com.sisecofi.libreria.comunes.model.proyectos.FichaAdmonCentral;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProyectoSpecification {

	private static final Logger log = LoggerFactory.getLogger(ProyectoSpecification.class);
	private static final String FICHA = "idFichaTecnica";
	private static final String ID_PROYECTO = "idProyecto";
	private static final String ESTATUS = "estatus";
	private static final String FICHA_TECNICA = "fichaTecnicaModel";
	private static final String ASIGNADOPK = "asignadoPk";

	private ProyectoSpecification() {
		throw new UnsupportedOperationException("Utility class");
	}

	public static Specification<ProyectoModel> byCriteria(
			EstructuraProyectoMetaDto criteria,
	        Usuario user,
	        boolean filtroUsuario) {

		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();

			predicate = addSimplePredicate(predicate, root, cb, "idEstatusProyecto", criteria.getIdEstatusProyecto());
			predicate = addLikePredicate(predicate, root, cb, "nombreCorto", criteria.getNombreCorto());
			predicate = addSimplePredicate(predicate, root, cb, ID_PROYECTO, criteria.getIdProyecto());
			predicate = cb.and(predicate, cb.equal(root.get(ESTATUS), criteria.isEstatus()));

			if (criteria.getAreaResponsable() != null && criteria.getAreaResponsable() != 0) {
				log.debug("Adding predicate for idAreaResponsable: {}", criteria.getAreaResponsable());
				Join<ProyectoModel, FichaTecnicaModel> fichaTecnicaJoin = root.join(FICHA_TECNICA, JoinType.LEFT);
				predicate = cb.and(predicate, cb.equal(fichaTecnicaJoin.get("idAreaPlaneacion"), criteria.getAreaResponsable()));
			}

			if (criteria.getAreaSolicitante() != null && criteria.getAreaSolicitante() != 0) {
				predicate = cb.and(predicate, createAreaSolicitantePredicate(criteria.getAreaSolicitante(), root, query, cb));
			}

			if (criteria.getLiderProyecto() != null && !criteria.getLiderProyecto().isEmpty()) {
				predicate = cb.and(predicate, createLiderPredicate(criteria.getLiderProyecto(), root, query, cb));
			}

			if (user != null && filtroUsuario) {
				predicate = cb.and(predicate, createUserPredicate(user, root, query, cb));
			}

			query.orderBy(cb.asc(cb.lower(root.get("nombreCorto"))));

			return predicate;
		};
	}

	private static Predicate addSimplePredicate(Predicate predicate, Root<?> root, CriteriaBuilder cb, String attribute,
			Object value) {
		if (value != null) {
			log.debug("Adding predicate for {}: {}", attribute, value);
			return cb.and(predicate, cb.equal(root.get(attribute), value));
		}
		return predicate;
	}

	private static Predicate addLikePredicate(Predicate predicate, Root<?> root, CriteriaBuilder cb, String attribute,
			String value) {
		if (value != null && !value.isEmpty()) {
			log.debug("Adding predicate for {}: {}", attribute, value);
			return cb.and(predicate, cb.like(root.get(attribute), "%" + value + "%"));
		}
		return predicate;
	}

	private static Predicate createAreaSolicitantePredicate(Integer idAreaSolicitante, Root<?> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<FichaAdmonCentral> subRoot = subquery.from(FichaAdmonCentral.class);
		subquery.select(subRoot.get(FICHA));

		Predicate subPredicate = cb.conjunction();
		subPredicate = cb.and(subPredicate, cb.equal(subRoot.get(FICHA), root.get(FICHA_TECNICA).get(FICHA)));
		subPredicate = cb.and(subPredicate, cb.equal(subRoot.get("idAdmonCentral"), idAreaSolicitante));
		subPredicate = cb.and(subPredicate, cb.isTrue(subRoot.get(ESTATUS)));

		subquery.where(subPredicate);
		return cb.exists(subquery);
	}

	private static Predicate createLiderPredicate(String lider, Root<?> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<HistoricoModel> subRoot = subquery.from(HistoricoModel.class);
		subquery.select(subRoot.get(FICHA));

		Predicate subPredicate = cb.conjunction();
		subPredicate = cb.and(subPredicate, cb.equal(subRoot.get(FICHA), root.get(FICHA_TECNICA).get(FICHA)));
		subPredicate = cb.and(subPredicate, cb.equal(subRoot.get("nombre"), lider));
		subPredicate = cb.and(subPredicate, cb.isTrue(subRoot.get(ESTATUS)));
		subPredicate = cb.and(subPredicate, cb.isTrue(subRoot.get("estatusHistorico")));

		subquery.where(subPredicate);
		return cb.exists(subquery);
	}

	private static Predicate createUserPredicate(Usuario user, Root<?> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<UsuarioProyectoModel> subRoot = subquery.from(UsuarioProyectoModel.class);
		subquery.select(subRoot.get(ASIGNADOPK).get(ID_PROYECTO));

		Predicate subPredicate = cb.conjunction();
		subPredicate = cb.and(subPredicate, cb.equal(subRoot.get(ASIGNADOPK).get("idUser"), user.getIdUser()));
		subPredicate = cb.and(subPredicate, cb.isTrue(subRoot.get(ESTATUS)));
		subPredicate = cb.and(subPredicate,
				cb.equal(subRoot.get(ASIGNADOPK).get(ID_PROYECTO), root.get(ID_PROYECTO)));

		subquery.where(subPredicate);
		return cb.exists(subquery);
	}

}