package com.sisecofi.admindevengados.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusEstimacion;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode( callSuper = false)
public class EstimacionNuevaDto {
	
	private String nombreCortoContrato;
	
	private String numeroContrato;
	
	private String nombreProveedor;
	
	private String idEstimacion;
	
	private CatEstatusEstimacion catEstatusEstimacion;
	
	private CatEstatusDictamen catEstatusDictamen;
	
	private CatIva catIva;
	
	private String moneda;
	
	private LocalDateTime fechaInicio;

    private LocalDateTime fechaTermino;
    
    private String idDictamen;
    
    @JsonIgnore
    ContratoModel contrato;
    
    @JsonIgnore
    private VigenciaMontosModel vigencia;
    
    @JsonIgnore
    private ProveedorModel proveedor;

	public EstimacionNuevaDto(ContratoModel contrato, VigenciaMontosModel vigencia, ProveedorModel proveedor) {
		super();
		this.contrato = contrato;
		this.vigencia = vigencia;
		this.proveedor = proveedor;
		this.nombreCortoContrato = contrato.getNombreCorto();
		this.numeroContrato = contrato.getNumeroContrato();
		this.nombreProveedor = proveedor.getNombreProveedor();
		this.moneda = vigencia.getCatTipoMoneda().getNombre();
		this.fechaInicio = vigencia.getFechaInicioVigenciaContrato();
		this.fechaTermino= vigencia.getFechaFinVigenciaContrato();
		
	}
    
    
    
	
}
