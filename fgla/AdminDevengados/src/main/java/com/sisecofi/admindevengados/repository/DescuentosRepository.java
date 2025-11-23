package com.sisecofi.admindevengados.repository;

import com.sisecofi.admindevengados.model.DescuentosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescuentosRepository extends JpaRepository<DescuentosModel, Long> {
    List<DescuentosModel> findByDictamenId(Long dictamenId);
}
