package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.reportedocumental.dto.financiero.ConceptoServicioDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConceptoServicioRepository {

    @Autowired
    private EntityManager entityManager;

    public List<ConceptoServicioDTO> buscar(Long idContrato) {
        Query query = entityManager.createNativeQuery("select ssc.id_servicio_contrato, ssc.concepto from sisecofi.sscft_servicio_contrato ssc where ssc.id_contrato = :id_contrato");

        query.setParameter("id_contrato", idContrato);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(result -> {
                    ConceptoServicioDTO dto = new ConceptoServicioDTO();
                    dto.setIdServicioContrato((Long) result[0]);
                    dto.setConcepto((String) result[1]);
                    return dto;
                })
                .toList();
    }
}