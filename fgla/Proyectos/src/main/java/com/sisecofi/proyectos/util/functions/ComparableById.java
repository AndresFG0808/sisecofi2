package com.sisecofi.proyectos.util.functions;

public interface ComparableById extends Comparable<ComparableById> {
	Integer getIdTarea();

    @Override
    default int compareTo(ComparableById o) {
        return Integer.compare(o.getIdTarea(), this.getIdTarea());
    }
}
