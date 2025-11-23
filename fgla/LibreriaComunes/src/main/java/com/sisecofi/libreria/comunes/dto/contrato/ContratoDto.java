package com.sisecofi.libreria.comunes.dto.contrato;


import com.sisecofi.libreria.comunes.dto.proveedor.ProveedorLigeroDto;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ContratoDto {
    private Long idContrato;

    private String idContratoFormato;

    private String nombreContrato;

    private String nombreCorto;

    private String numeroContrato;

    private Long idProyecto;

    private String tipoProcedimiento;

    private LocalDateTime fecha_inicio;

    private LocalDateTime fecha_termino;

    private String ultimoCm; //contratoModificatorio

    private BigDecimal montoMaximo;

    private BigDecimal montoPesos;

    private BigDecimal montoMaximoUltimoCm; //contratoModificatorio

    private List<CatAdmonCentralDto> administracionCentral;

    private List<CatAdmonGeneralDto> administracionGeneral;

   // private List<CatAdministradorCentral> administradorContrato; //idUsuario **puede ser nulo

    private ConvenioModificatorioModel ultimoContratoModificatorio;

    private String administradorContrato;

    private String verificadorContrato;

    private Integer idIva;

    //Contrato Modificatorio

    private BigDecimal tipoCambio; //estimacion....

    private String nombreProyecto;

    private  String estatusContrato;

    //CAMPOSE NEW ...........
    private String grupoServicio;

    private String conseptosServico;

    private String unidadMedida;

    private String tipoConsumo;

    private BigDecimal precioUnitario;

    private Integer cantidadServiciosVigente;

    private Integer montoMaximoServicio;

    //...........................

    private List<ProveedorLigeroDto> proveedores;

    private String tipoMoneda;

    private String acuerdo;

    //bandera id_estatus_contrato
    private boolean ejecucion; 
}
