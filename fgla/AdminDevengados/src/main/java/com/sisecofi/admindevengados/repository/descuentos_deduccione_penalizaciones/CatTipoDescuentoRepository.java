package com.sisecofi.admindevengados.repository.descuentos_deduccione_penalizaciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDescuento;


@Repository
public interface CatTipoDescuentoRepository extends JpaRepository<CatTipoDescuento, Integer> {
	
	List<CatTipoDescuento> findByEstatusTrue();

}
