package com.sisecofi.proveedores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;

public interface CatEstatusTituloServicioRepository  extends JpaRepository<CatEstatusTituloServicio, Integer>{

    Optional<CatEstatusTituloServicio> findByIdEstatusTituloServicio(Integer idEstatusTituloServicio);

    Optional<CatEstatusTituloServicio> findByColorSemaforoEstatus(String semaforoEstatus);


}
