package com.sisecofi.libreria.comunes.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum Mes {
	ENERO("01","enero"),
	FEBRERO("02","febrero"),
	MARZO("03","marzo"),
	ABRIL("04","abril"),
	MAYO("05","mayo"),
	JUNIO("06","junio"),
	JULIO("07","julio"),
	AGOSTO("08","agosto"),
	SEPTIEMBRE("09","septiembre"),
	OCTUBRE("10","octubre"),
	NOVIEMBRE("11","noviembre"),
	DICIEMBRE("12","diciembre");
	
	private String numero;
	private String mes;

	Mes(String numero,String mes){
		this.numero=numero;
		this.mes=mes;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
	
	public Mes retornarMesNumero(String mes) {
		for(Mes m:values()) {
			if(mes.equals(m.getMes())) {
				return m;
			}
		}
		return Mes.ENERO;
	}
	
}
