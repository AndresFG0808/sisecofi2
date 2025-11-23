package com.sisecofi.contratos.util.consumer;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private Map<String, String> propiedades;

    public Configuration() {
        propiedades = new HashMap<>();
    }

    public void setProperty(String key, String value) {
        propiedades.put(key, value);
    }

    public String getProperty(String key) {
        return propiedades.get(key);
    }
}
