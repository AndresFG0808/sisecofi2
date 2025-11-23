package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.reportedocumental.dto.dinamico.FiltroNombreContratoSDRequestDTO;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoBaseDTO;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoResumenDTO;
import com.sisecofi.reportedocumental.dto.financiero.NombreContratoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class ContratoRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String FECHA = "fecha";

    public List<NombreContratoDTO> filtrarContratos(FiltroNombreContratoBaseDTO filtro) {
        StringBuilder sql = new StringBuilder();

        sql.append("select\n");
        sql.append("\tsc.id_contrato,\n");
        sql.append("\tsc.nombre_corto,\n");
        sql.append("\t(case when lower(scc.nombre) = 'aplica' then true else false end) convenioColaboracion\n");
        sql.append("from sisecofi.sscft_datos_generales_contrato sdgc\n");
        sql.append("left join sisecofi.sscft_contrato sc on sc.id_contrato = sdgc.id_contrato\n");
        sql.append("left join sisecofi.sscfc_convenio_colaboracion scc on scc.id_convenio_colaboracion = sdgc.id_convenio_colaboracion\n");

        StringBuilder joins = new StringBuilder();
        StringBuilder condiciones = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filtro.getIdProyecto() != null && filtro.getIdProyecto() != 0) {
            condiciones.append("\tand sc.id_proyecto = :idProyecto\n");
            params.put("idProyecto", filtro.getIdProyecto());
        }

        if (filtro.getIdContratoVigente() != null) {
            
            joins.append("left join sisecofi.sscft_vigencia_montos svm on svm.id_contrato = sc.id_contrato\n");
            joins.append("""
					left join lateral (
					   select cmm.fecha_fin
					   from sisecofi.sscft_convenio_modificatorio cmm
					   where cmm.id_contrato = sc.id_contrato
					   order by cmm.id_convenio_modificatorio desc
					   limit 1
					) cm on true
					""");
            
            String fechaEfectiva = "coalesce(cm.fecha_fin, svm.fecha_fin_vigencia_contrato)";

            if (filtro.getIdContratoVigente() == 1) { // Vigentes
                condiciones.append("\tand date(").append(fechaEfectiva).append(") >= date(:fecha)\n");
                params.put(FECHA, new Date());
            } else if (filtro.getIdContratoVigente() == 2) { // No vigentes
                condiciones.append("\tand date(").append(fechaEfectiva).append(") < date(:fecha)\n");
                params.put(FECHA, new Date());
            }
        }

        if (filtro instanceof FiltroNombreContratoResumenDTO filtroResumen && filtroResumen.getIdDominioTecnologico() != null) {
            joins.append("left join sisecofi.sscfc_dominios_tecnologicos sdt on sdt.id_dominios_tecnologicos = sdgc.id_dominios_tecnologicos\n");
            condiciones.append("\tand sdt.id_dominios_tecnologicos = :id_dominios_tecnologicos\n");
            params.put("id_dominios_tecnologicos", filtroResumen.getIdDominioTecnologico());
        }


        sql.append(joins);
        sql.append("where 1 = 1\n");
        sql.append(condiciones);
        sql.append("order by sc.nombre_corto asc");

        log.info("SQL GENERADO: {}", sql);

        Query query = entityManager.createNativeQuery(sql.toString());
        setParams(params, query);

        return parse(query.getResultList());
    }

    private void setParams(Map<String, Object> params, Query query) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
    }

    private List<NombreContratoDTO> parse(List<Object[]> results) {
        List<NombreContratoDTO> lista = new ArrayList<>();
        results.forEach(item -> lista.add(new NombreContratoDTO((Long) item[0], (String) item[1], (boolean) item[2])));
        return lista;
    }

}