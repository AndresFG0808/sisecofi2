package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.libreria.comunes.dto.dinamico.Agrupacion;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.financiero.ConsultaEstadoFinancieroDTO;
import com.sisecofi.reportedocumental.repository.financiero.sql.ReporteEstadoFinancieroSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Repository
public class ReporteEstadoFinancieroRepository extends RFUtil implements ReporteEstadoFinancieroSQL {
	@PersistenceContext
	private EntityManager entityManager;

	public PageGeneric obtenerReporte(ConsultaEstadoFinancieroDTO dto, boolean paginacion) {
		if (dto.getIdConvenioColaboracion() == null) {
			dto.setIdConvenioColaboracion(2);
		}

		PageGeneric page = new PageGeneric();
		initPageGeneric(page);
		setEtiquetas(dto, page);

		Query countQuery = entityManager.createNativeQuery(getContadorSQL(dto));
		setParametrosSQL(getParametrosSQL(dto), countQuery);
		Long total = (Long) countQuery.getSingleResult();

		Query query = entityManager.createNativeQuery(getConsultaSQL(dto, paginacion));
		setParametrosSQL(getParametrosSQL(dto), query);

		if (paginacion) {
			query.setParameter("registros", dto.getPageSize());
			query.setParameter("pagina", getOffset(dto));
		}

		if ((long) dto.getPageNumber() * dto.getPageSize() >= total) {
			page.setContent(new ArrayList<>());
		} else {
			page.setContent(query.getResultList());
		}

		setPaginador(page, dto, total);

		formatearMontosReporte(dto, page);

		return page;
	}

	protected String getContadorSQL(ConsultaEstadoFinancieroDTO dto) {
		StringBuilder sql = new StringBuilder();
		sql.append("with dictaminado as (\n");
		sql.append(SELECT);
		sql.append(CAMPOS_BASE);
		sql.append(CAMPOS_DICTAMEN);
		sql.append(CAMPOS_DICTAMEN_PAGADO);
		sql.append(CAMPO_DICTAMEN_SAT);

		if (dto.getIdConvenioColaboracion() == 1) {
			sql.append(CAMPO_DICTAMEN_CC);
		}

		sql.append(CAMPO_PAGADO_SAT);

		if (dto.getIdConvenioColaboracion() == 1) {
			sql.append(CAMPO_PAGADO_CC);
		}

		sql.append(FROM);
		sql.append(JOINS_BASE);
		sql.append(JOINS_DICTAMEN);
		sql.append(WHERE);
		sql.append(getCondicionesSQL(dto));
		sql.append("\n),");

		sql.append("estimado as (\n");

		sql.append(SELECT);
		sql.append(CAMPOS_BASE);
		sql.append(CAMPOS_ESTIMACION);
		sql.append(FROM);
		sql.append(JOINS_BASE);
		sql.append(JOINS_ESTIMACION);
		sql.append(WHERE);
		sql.append(getCondicionesSQL(dto));
		sql.append(")\n");
		sql.append(FULL_JOIN_CONTADOR);

		log.info("SQL GENERADO: {}", sql);

		return sql.toString();
	}

	protected String getConsultaSQL(ConsultaEstadoFinancieroDTO dto, boolean paginacion) {
		StringBuilder sql = new StringBuilder();
		sql.append("with dictaminado as (\n");
		sql.append(SELECT);
		sql.append(CAMPOS_BASE);
		sql.append(CAMPOS_DICTAMEN);
		sql.append(CAMPOS_DICTAMEN_PAGADO);
		sql.append(CAMPO_DICTAMEN_SAT);

		if (dto.getIdConvenioColaboracion() == 1) {
			sql.append(CAMPO_DICTAMEN_CC);
		}

		sql.append(CAMPO_PAGADO_SAT);

		if (dto.getIdConvenioColaboracion() == 1) {
			sql.append(CAMPO_PAGADO_CC);
		}

		sql.append(FROM);
		sql.append(JOINS_BASE);
		sql.append(JOINS_DICTAMEN);
		sql.append(WHERE);
		sql.append(getCondicionesSQL(dto));
		sql.append("\n),");

		sql.append("estimado as (\n");

		sql.append(SELECT);
		sql.append(CAMPOS_BASE);
		sql.append(CAMPOS_ESTIMACION);
		sql.append(FROM);
		sql.append(JOINS_BASE);
		sql.append(JOINS_ESTIMACION);
		sql.append(WHERE);
		sql.append(getCondicionesSQL(dto));
		sql.append(")\n");
		sql.append(SELECT_FULL_JOIN);
		sql.append(CAMPO_DICTAMAEN_SAT_FULL_JOIN);
		if (dto.getIdConvenioColaboracion() == 1) {
			sql.append(CAMPO_DICTAMEN_CC_FULL_JOIN);
		}

		sql.append(CAMPO_PAGADO_SAT_FULL_JOIN);
		if (dto.getIdConvenioColaboracion() == 1) {
			sql.append(CAMPO_PAGADO_CC_FULL_JOIN);
		}

		sql.append(FROM_FULL_JOIN);

		if (paginacion) {
			sql.append(PAGINACION);
		}

		log.info("SQL GENERADO: {}", sql);

		return sql.toString();
	}

	protected String getCondicionesSQL(ConsultaEstadoFinancieroDTO dto) {
		StringBuilder sql = new StringBuilder();

		if (dto.getIdEstatusProyecto() != null) {
			sql.append("\n\t and sp.id_estatus_proyecto = :idEstatusProyecto");
		}

		if (dto.getIdDominiosTecnologicos() != null) {
			sql.append("\n\t and sdgc.id_dominios_tecnologicos = :idDominiosTecnologicos");
		}

		if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty()
				&& !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
			sql.append("\n\t and sp.nombre_corto = :nombreCortoProyecto");
		}

		if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
			sql.append("\n\t and sc.nombre_corto = :nombreCortoContrato");
		}

		if (dto.getIdContratoVigente() != null && dto.getIdContratoVigente() > 0) {
		    String FECHA_FIN_EFECTIVA_CAST =
		        "CAST(coalesce((" +
		        "  select cm.fecha_fin " +
		        "  from sisecofi.sscft_convenio_modificatorio cm " +
		        "  where cm.id_contrato = sc.id_contrato " +
		        "  order by cm.id_convenio_modificatorio desc " +
		        "  limit 1" +
		        "), svm.fecha_fin_vigencia_contrato) AS date)";

		    if (dto.getIdContratoVigente() == 1) { // Vigentes
		        sql.append("\n\t and ").append(FECHA_FIN_EFECTIVA_CAST)
		           .append(" >= CAST(:fechaFinVigenciaContrato AS date)");
		    } else if (dto.getIdContratoVigente() == 2) { // No vigentes
		        sql.append("\n\t and ").append(FECHA_FIN_EFECTIVA_CAST)
		           .append(" < CAST(:fechaFinVigenciaContrato AS date)");
		    }
		}


		Date periodoInicio = dto.getPeriodoInicio();
		Date periodoFin = dto.getPeriodoFin();

		if (periodoInicio != null && periodoFin != null) {
			sql.append("""
					 and to_date(('01/' || (
					    select
					        case spcm.nombre
					            when 'Enero' then '01'
					            when 'Febrero' then '02'
					            when 'Marzo' then '03'
					            when 'Abril' then '04'
					            when 'Mayo' then '05'
					            when 'Junio' then '06'
					            when 'Julio' then '07'
					            when 'Agosto' then '08'
					            when 'Septiembre' then '09'
					            when 'Octubre' then '10'
					            when 'Noviembre' then '11'
					            when 'Diciembre' then '12'
					        end
					) || '/' || spca.nombre), 'DD/MM/YYYY')
					between to_date(:fecha_inicio, 'dd/MM/YYYY') and to_date(:fecha_fin, 'dd/MM/YYYY')
					""");
		}

		return sql.toString();
	}

	protected Map<String, Object> getParametrosSQL(ConsultaEstadoFinancieroDTO dto) {
		Map<String, Object> params = new HashMap<>();

		if (dto.getIdEstatusProyecto() != null) {
			params.put("idEstatusProyecto", dto.getIdEstatusProyecto());
		}

		if (dto.getIdDominiosTecnologicos() != null) {
			params.put("idDominiosTecnologicos", dto.getIdDominiosTecnologicos());
		}

		if (dto.getNombreCortoProyecto() != null && !dto.getNombreCortoProyecto().isEmpty()
				&& !dto.getNombreCortoProyecto().equalsIgnoreCase("Todos")) {
			params.put("nombreCortoProyecto", dto.getNombreCortoProyecto());
		}

		if (dto.getNombreCortoContrato() != null && !dto.getNombreCortoContrato().isEmpty()) {
			params.put("nombreCortoContrato", dto.getNombreCortoContrato());
		}

		if (dto.getIdContratoVigente() != null
				&& (dto.getIdContratoVigente() == 1 || dto.getIdContratoVigente() == 2)) {
			params.put("fechaFinVigenciaContrato", new Date());
		}

		Date periodoInicio = dto.getPeriodoInicio();
		Date periodoFin = dto.getPeriodoFin();

		if (periodoInicio != null && periodoFin != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("01/MM/yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

			params.put("fecha_inicio", sdf.format(dto.getPeriodoInicio()));
			params.put("fecha_fin", sdf.format(dto.getPeriodoFin()));
		}

		return params;
	}

	protected void setEtiquetas(ConsultaEstadoFinancieroDTO dto, PageGeneric page) {
		page.getGrupoEtiquetas().add(new Agrupacion("", 8));
		page.getGrupoEtiquetas().add(new Agrupacion("Estimado", 3));
		page.getGrupoEtiquetas().add(new Agrupacion("Dictamen", 3));
		page.getGrupoEtiquetas().add(new Agrupacion("Dictamen pagado", 3));

		page.getEtiquetas().addAll(new ArrayList<>(Arrays.asList(ETIQUETAS)));

		if (dto.getIdConvenioColaboracion() != null && dto.getIdConvenioColaboracion() == 2) {
			page.getEtiquetas().removeIf(etiqueta -> etiqueta.endsWith("CC"));
			page.getGrupoEtiquetas().add(new Agrupacion("", 2));
		} else {
			page.getGrupoEtiquetas().add(new Agrupacion("", 4));
		}
	}

	protected void formatearMontosReporte(ConsultaEstadoFinancieroDTO dto, PageGeneric page) {
		page.setContent(page.getContent().stream().map(item -> {
			int indice = 0;

			indice = incrementarIndice(indice, 8);

			item[indice] = getMontoConFormato(item[indice++]);
			item[indice] = getMontoConFormato(item[indice++]);
			item[indice] = getMontoConFormato(item[indice++]);

			item[indice] = getMontoConFormato(item[indice++]);
			item[indice] = getMontoConFormato(item[indice++]);
			item[indice] = getMontoConFormato(item[indice++]);

			item[indice] = getMontoConFormato(item[indice++]);
			item[indice] = getMontoConFormato(item[indice++]);
			item[indice] = getMontoConFormato(item[indice++]);

			item[indice] = getMontoConFormato(item[indice++]);

			if (dto.getIdConvenioColaboracion() == 1) {
				item[indice] = getMontoConFormato(item[indice++]);
			}

			item[indice] = getMontoConFormato(item[indice++]);

			if (dto.getIdConvenioColaboracion() == 1) {
				item[indice] = getMontoConFormato(item[indice]);
			}

			return item;
		}).toList());
	}

}
