package com.sisecofi.contratos.repository.reintegros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisecofi.libreria.comunes.model.catalogo.CatTipoReintegro;

@Repository
public interface CatTipoReintegroRepository  extends JpaRepository<CatTipoReintegro, Integer>{

	
	List<CatTipoReintegro> findByEstatusTrue();
	
}
