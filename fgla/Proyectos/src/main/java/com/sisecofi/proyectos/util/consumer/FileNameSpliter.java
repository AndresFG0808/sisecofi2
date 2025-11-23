package com.sisecofi.proyectos.util.consumer;

import kotlin.Pair;

public class FileNameSpliter {

    public static Pair<String, String> splitFileName(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');

        if (lastIndex == -1 || lastIndex == fileName.length() - 1) {
            return new Pair<>(fileName, "");
        }

        return new Pair<>(fileName.substring(0, lastIndex), fileName.substring(lastIndex));
    }

	private FileNameSpliter() {
		super();
	}    
    
}
