package com.sisecofi.libreria.comunes.model.dictamenes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB +"solicitud_pago")
@Getter
@Setter
public class SolicitudPagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitudPago;

    @JoinColumn(name = "id_dictamen", insertable = false, updatable = false)
    @OneToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private Dictamen dictamen;

    @Column(name = "id_dictamen")
    private Long dictamenId;

    @Column(name = "oficio_solicitud_pago")
    private String oficioSoliciturPago;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @Column(name = "ruta_solicitud_pago")
    private String rutaSolicitudPago;

    @Column(name = "aplica_convenio_colaboracion")
    private Boolean aplicaConvenioColaboracion;

    @Column(name = "estatus")
    private Boolean estatus;

    @Column(name = "documento_generado")
    private Boolean documentoGenerado;
    
    @OneToOne
	@JoinColumn(name = "id_archivo_pdf", foreignKey = @ForeignKey(name = "FK_id_archivo_pdf"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoPdf;
}
