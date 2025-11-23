package com.sisecofi.proyectos.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class EstructuraProyectoMetaDto extends RepresentationModel<EstructuraProyectoMetaDto> {
	@EqualsAndHashCode.Include
	private Long idProyecto;
	private Integer idEstatusProyecto;
    private String nombreCorto;
    private Integer areaSolicitante;
    private Integer areaResponsable;
    private String liderProyecto;
    private boolean estatus = true;
    private int page;
    private int size;
    
    
    
    public String criterios() {
    	String criterios="";
    	if(idProyecto != null) {
    		criterios= "id proyecto :"+idProyecto;
    	}else {
    		if(idEstatusProyecto != null) {
    			criterios+= "id estatus de proyecto :"+idEstatusProyecto+"|";
    		}
        	if(nombreCorto != null && !nombreCorto.isEmpty()) {
        		criterios+= "Nombre corto :"+nombreCorto+"|";
        	}
        	if(areaSolicitante != null) {
        		criterios+= "Id area solicitante :"+areaSolicitante+"|";
        	}	
        	if(areaResponsable != null) {
        		criterios+= "Id area responsable :"+areaResponsable+"|";
        	}	
        	if(liderProyecto != null && !liderProyecto.isEmpty()) {
        		criterios+= "Lider del proyecto :"+liderProyecto;
        	}		
    	}
    		return criterios;
    }



	public EstructuraProyectoMetaDto() {
		super();
	}



	public EstructuraProyectoMetaDto(Long idProyecto, int page, int size) {
		super();
		this.idProyecto = idProyecto;
		this.page = page;
		this.size = size;
	}
	
	

}