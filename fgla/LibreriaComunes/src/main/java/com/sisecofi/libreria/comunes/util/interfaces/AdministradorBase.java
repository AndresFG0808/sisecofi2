package com.sisecofi.libreria.comunes.util.interfaces;

import java.time.LocalDateTime;

public interface AdministradorBase {
    
    Integer getIdAdministrador(); 
    
    String getAdministrador();
    
    LocalDateTime getFechaInicioVigencia();
    
    LocalDateTime getFechaFinVigencia();
    
    String getTelefono();
    
    String getCorreo();
    
    boolean isEstatus();
    
    void setFechaModificacion(LocalDateTime fechaModificacion); 
    
}
