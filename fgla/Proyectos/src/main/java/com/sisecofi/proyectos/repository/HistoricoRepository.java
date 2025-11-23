package com.sisecofi.proyectos.repository;


import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sisecofi.libreria.comunes.model.proyectos.HistoricoModel;


public interface HistoricoRepository extends JpaRepository<HistoricoModel, Long> {
	
	Optional<HistoricoModel>  findByIdFichaTecnicaAndEstatusAndEstatusHistorico(Long idFichaTecnica, boolean estatus, boolean estatusHistorico);
	
	Set<HistoricoModel> findByIdFichaTecnica(Long idFichaTecnica);
	
	Set<HistoricoModel> findByIdFichaTecnicaAndEstatusHistorico(Long idFichaTecnica, boolean estatusHitorico);
	
	Set<HistoricoModel> findByIdFichaTecnicaAndEstatusHistoricoOrderByIdHistoricoAsc(Long idFichaTecnica, boolean estatusHitorico);
	
	Optional<HistoricoModel>  findByIdHistoricoAndEstatusHistorico(Long idHistorico, boolean estatusHistorico);
	
	Optional<HistoricoModel> findTopByIdFichaTecnicaAndEstatusHistoricoAndEstatusOrderByIdHistoricoDesc(Long idFicha, boolean estatusHistorico, boolean estatus);
	
}
