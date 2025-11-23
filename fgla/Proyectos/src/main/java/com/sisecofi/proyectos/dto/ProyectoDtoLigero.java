package com.sisecofi.proyectos.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusProyecto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class ProyectoDtoLigero {
	private Long idProyecto;
	private String nombreProyecto;
	private String nombreCorto;
	private String idAgp;
	private Integer idEstatusProyecto;
	private String estatusProyecto;
	private String areaSolicitante;
	private String liderProyecto;
	private String areaResponsableSt;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private BigDecimal monto;
	private CatEstatusProyecto catEstatus;
	boolean plan;
	private CatAdministracion areaResponsable;

	public ProyectoDtoLigero(Object[] row) {
        this.idProyecto = (row[0] != null) ? ((Number) row[0]).longValue() : null;
        this.nombreProyecto = (row[1] != null) ? row[1].toString() : "";
        this.nombreCorto = (row[2] != null) ? row[2].toString() : "";
        this.idAgp = (row[3] != null) ? row[3].toString() : "";
        this.idEstatusProyecto = (row[4] != null) ? ((Number) row[4]).intValue() : null;
        this.estatusProyecto = (row[5] != null) ? row[5].toString() : "";
        this.areaSolicitante = (row[6] != null) ? row[6].toString() : "";
        this.liderProyecto = (row[7] != null) ? row[7].toString() : "";
        this.areaResponsableSt = (row[8] != null) ? row[8].toString() : "";
        this.fechaInicio = (row[9] != null) ? LocalDate.parse(row[9].toString()) : null;
        this.fechaFin = (row[10] != null) ? LocalDate.parse(row[10].toString()) : null;
        this.monto = (row[11] != null) 
        	    ? BigDecimal.valueOf(((Number) row[11]).doubleValue()).setScale(2, RoundingMode.HALF_UP) 
        	    : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.catEstatus= new CatEstatusProyecto();
        catEstatus.setNombre(estatusProyecto);
        catEstatus.setEstatus(true);
        catEstatus.setIdEstatusProyecto(idEstatusProyecto);
        this.plan = (row[12] != null) && ((Boolean) row[12]);
        this.areaResponsable = new CatAdministracion();
        areaResponsable.setNombre(areaResponsableSt);
        areaResponsable.setAdministracion(areaResponsableSt);
        areaResponsable.setEstatus(true);
        
    }
}
