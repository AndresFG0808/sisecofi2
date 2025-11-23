package com.sisecofi.reportedocumental.repository.financiero;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import com.sisecofi.reportedocumental.dto.financiero.VerificadorContratoDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class VerificadorContratoRepository {
	@Autowired
	private EntityManager entityManager;

	public List<VerificadorContratoDto> listarVerificadoresPorContrato(String nombreCortoContrato) {
		String sql = """
        SELECT  
            pac.id_referencia as id_verificador,
            COALESCE(
                CASE WHEN pac.nivel = 1 THEN ag.administrador END,       
                CASE WHEN pac.nivel = 2 THEN ac.administrador END,      
                CASE WHEN pac.nivel = 3 THEN aa.administrador END,       
                CASE WHEN pac.nivel = 4 THEN ea.nombre END               
            ) AS nombre
        FROM sisecofi.sscft_participantes_administracion_contrato pac 
        LEFT JOIN sisecofi.sscfc_administracion ad 
            ON ad.id_administracion = pac.id_admon_general
        LEFT JOIN sisecofi.sscfc_responsabilidad res 
            ON res.id_responsabilidad = pac.id_responsabilidad
        LEFT JOIN sisecofi.sscft_contrato con 
            ON con.id_contrato = pac.id_contrato
        LEFT JOIN sisecofi.sscfc_administrador_general ag
            ON pac.nivel = 1 AND ag.id_admon_general = pac.id_referencia
        LEFT JOIN sisecofi.sscfc_administrador_central ac
            ON pac.nivel = 2 AND ac.id_admon_central = pac.id_referencia
        LEFT JOIN sisecofi.sscfc_administrador_administracion aa
    ON pac.nivel = 3 AND aa.id_administrador_administracion = pac.id_referencia
        LEFT JOIN sisecofi.sscfc_empleado_administracion ea
            ON pac.nivel = 4 AND ea.id_empleado_administracion = pac.id_referencia
        WHERE 
            con.nombre_corto = :nombre_corto
            AND LOWER(res.nombre) = LOWER('Verificador del contrato')
            AND pac.estatus = TRUE
            AND pac.vigente = TRUE
			AND (
        pac.nivel <> 2 
        OR (pac.nivel = 2 AND ac.fecha_fin_vigencia IS NULL)
    )
        """;

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("nombre_corto", nombreCortoContrato);

		List<Object[]> resultados = query.getResultList();

		List<VerificadorContratoDto> verificadores = new ArrayList<>();

		for (Object[] fila : resultados) {
			try {
				// Casting seguro
				Long idVerificador = null;
				if (fila[0] != null) {
					if (fila[0] instanceof BigInteger) {
						idVerificador = ((BigInteger) fila[0]).longValue();
					} else if (fila[0] instanceof Integer) {
						idVerificador = ((Integer) fila[0]).longValue();
					} else if (fila[0] instanceof Long) {
						idVerificador = (Long) fila[0];
					}
				}
				
				String nombre = fila[1] != null ? (String) fila[1] : null;
				
				if (idVerificador != null && nombre != null) {
					VerificadorContratoDto dto = new VerificadorContratoDto(idVerificador, nombre);
					verificadores.add(dto);
				}
			} catch (Exception e) {
				
			}
		}
	
		return verificadores;
	}
	
}