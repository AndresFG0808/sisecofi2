package com.sisecofi.reportedocumental.dto.financiero.pages;

public class PageReporteFinancieroBase {
    protected String getMontoConFormato(Object obj) {
        return "$ ".concat((String) obj);
    }

    protected String getPeriodoControlConFormatoFecha(Object obj) {
        if (obj instanceof String fecha) {
            String[] partes = fecha.split("/");

            if (partes.length == 3) {
                switch (partes[1]) {
                    case "Enero":
                        partes[1] = "01";
                        break;
                    case "Febrero":
                        partes[1] = "02";
                        break;
                    case "Marzo":
                        partes[1] = "03";
                        break;
                    case "Abril":
                        partes[1] = "04";
                        break;
                    case "Mayo":
                        partes[1] = "05";
                        break;
                    case "Junio":
                        partes[1] = "06";
                        break;
                    case "Julio":
                        partes[1] = "07";
                        break;
                    case "Agosto":
                        partes[1] = "08";
                        break;
                    case "Septiembre":
                        partes[1] = "09";
                        break;
                    case "Octubre":
                        partes[1] = "10";
                        break;
                    case "Noviembre":
                        partes[1] = "11";
                        break;
                    case "Diciembre":
                        partes[1] = "12";
                        break;
                    default:
                        return "";
                }

                return partes[0] + "/" + partes[1] + "/" + partes[2];
            }
        }
        return "";
    }
}
