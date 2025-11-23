package com.sisecofi.reportedocumental.repository.financiero;

import com.sisecofi.reportedocumental.model.CatEstatusVolumetria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatEstatusVolumetriaRepository extends JpaRepository<CatEstatusVolumetria, Integer> {
    List<CatEstatusVolumetria> findAllByEstatusIsTrue();
}
