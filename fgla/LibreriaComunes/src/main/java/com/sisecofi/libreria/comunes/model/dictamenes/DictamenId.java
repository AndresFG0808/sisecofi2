package com.sisecofi.libreria.comunes.model.dictamenes;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DictamenId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "idDictamen", nullable = false)
    private Long idDictamen;

    public DictamenId() {}

    public DictamenId(Long idDictamen) {
        this.idDictamen = idDictamen;
    }

    @JsonCreator
    public static DictamenId fromJson(@JsonProperty("idDictamen") Long idDictamen) {
        return new DictamenId(idDictamen);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictamenId that = (DictamenId) o;
        return Objects.equals(idDictamen, that.idDictamen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDictamen);
    }

    @Override
    public String toString() {
        return ""+idDictamen;
    }
}
