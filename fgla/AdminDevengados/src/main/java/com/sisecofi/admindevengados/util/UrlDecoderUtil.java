package com.sisecofi.admindevengados.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecoderUtil {

    public static String decodeId(String encodedId) {
        try {
            return URLDecoder.decode(encodedId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Error al decodificar el ID: Codificación no soportada", e);
        }
    }
    
    private UrlDecoderUtil() {
    }
}
