package com.sisecofi.proveedores.util.enums;

public enum VigenciaEnum {
    
    MENOR_TRES_MESES("rojo", "m"),
    MAYOR_TRES_MESES("blanco", "m");

    private final String color;
    private final String  union;


    VigenciaEnum(String color, String union){
        this.color = color;
        this.union = union;
    }

    public String getColor(){
        return color;
    }

    public String getUnion(){
        return union;
    }


}
