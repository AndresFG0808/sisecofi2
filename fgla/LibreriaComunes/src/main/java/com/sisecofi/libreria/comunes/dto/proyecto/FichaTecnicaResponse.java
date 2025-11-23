package com.sisecofi.libreria.comunes.dto.proyecto;

import java.util.List;
import java.util.Set;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatClasificacionProyecto;
import com.sisecofi.libreria.comunes.model.catalogo.CatFinanciamiento;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoProcedimiento;
import com.sisecofi.libreria.comunes.model.proyectos.FichaTecnicaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FichaTecnicaResponse {

	private FichaTecnicaModel ficha;
	private CatAdministracion areaPlaneacion;
	private CatAdmonGeneral admonPatrocinadora;
	private CatAdmonGeneral admonParticipante;
	private CatFinanciamiento financiamiento;
	private CatTipoMoneda tipoMoneda;
	private CatTipoProcedimiento tipoProcedimiento;
	private CatClasificacionProyecto clasificacionProyecto;
	private Set<CatAdmonCentral> admonCentrales;
	private Set<LiderDto> lideres;
	private List<AlineacionResponse>  alineaciones;
}
