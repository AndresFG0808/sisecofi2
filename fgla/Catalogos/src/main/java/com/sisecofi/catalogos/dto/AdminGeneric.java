package com.sisecofi.catalogos.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
public class AdminGeneric<T extends BaseCatalogoModel, M extends BaseCatalogoModel> {

	private T admon;
	private List<M> administradores;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminGeneric<?, ?> that = (AdminGeneric<?, ?>) o;
        return Objects.equals(admon, that.admon) &&
               Objects.equals(administradores, that.administradores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admon, administradores);
    }
    
    public void setAdministradores(List<M> administradores) {
        if (administradores != null) {
            // Validar que la lista no tenga elementos nulos o valores inválidos
            for (M admin : administradores) {
                if (admin == null) {
                    throw new IllegalArgumentException("Administrador inválido");
                }
            }
         // copia defensiva
            this.administradores = new ArrayList<>(administradores); 
        } else {
            this.administradores = new ArrayList<>();
        }
    }

}
