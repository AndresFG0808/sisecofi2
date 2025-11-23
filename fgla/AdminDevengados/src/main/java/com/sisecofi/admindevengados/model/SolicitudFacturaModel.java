package com.sisecofi.admindevengados.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "solicitud_factura")
@Getter
@Setter
public class SolicitudFacturaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitudFactura;

    @JoinColumn(name = "dictamenId", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private Dictamen dictamen;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "dictamenId")
    private Long dictamenId;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "noOficioSolicitud")
    private String noOficioSolicitud;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "fechaSolicitud")
    private LocalDateTime fechaSolicitud;

   

    @ManyToOne
	@JoinColumn(name = "id_archivo_pdf", foreignKey = @ForeignKey(name = "FK_id_archivo_pdf"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoPdf;



    //se agrega nuevo campo para regristro de fecha recpecion factura
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy")
    @Column(name = "fechaRecepcionFactura")
    private  LocalDateTime fechaRecepcionFactura;

    //agregamos bandera
    @Column(name ="banderaFactura")
    private Boolean banderaFactura= false;    


    public String getRuta(){
        if(archivoPdf!=null){
            return archivoPdf.getRuta();
        }else{
            return "";
        }
    }

    public String getNombre(){
        if(archivoPdf!=null){
            return archivoPdf.getNombre() + ".pdf";
        }else{
            return null;
        }
    }






}
