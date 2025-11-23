package com.sisecofi.admindevengados.util.consumer;


public class ConvertInXHTMLFile {

	

    public static String replaceHtmlEntities(String input) {
        return input.replace("&aacute;", "á")
                .replace("&lt;", "<") 
                .replace("&gt;", ">")
                .replaceAll("<col(?=[^/>]*>)", "<col/>")
                .replaceAll("<table(?=[^/>]*>)", "<table/>")
                .replaceAll("<figure(?=[^/>]*>)", "<figure/>")
                .replaceAll("<img(?=[^/>]*>)", "<img/>")
                .replace("&nbsp;", " ")
                .replace("\u00A0", " ")
                .replace("&eacute;", "é")
                .replace("&iacute;", "í")
                .replace("&oacute;", "ó")
                .replace("&uacute;", "ú")
                .replace("&ntilde;", "ñ")
                .replace("&quot;", "\"")
                .replace("&amp;", "&");
    }
    
    private ConvertInXHTMLFile() {
        throw new IllegalStateException("Utility class");
      }
}