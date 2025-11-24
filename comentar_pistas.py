#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import os
import re
from pathlib import Path

def comentar_llamadas_pista(archivo_path):
    """Comenta todas las llamadas a pistaService.guardarPista en un archivo"""
    with open(archivo_path, 'r', encoding='utf-8') as f:
        contenido = f.read()
    
    # Patron para encontrar llamadas a pistaService.guardarPista que pueden estar en múltiples líneas
    # Busca desde "pistaService.guardarPista" hasta el punto y coma que cierra la llamada
    patron = r'(\s*)(pistaService\.guardarPista\([^;]*?;)'
    patron_multilinea = r'(\s*)(pistaService\.guardarPista\((?:[^;]|\n)*?\);)'
    
    # Primero intentar con el patrón multilinea
    nuevo_contenido = re.sub(
        patron_multilinea,
        lambda m: '\n'.join([
            m.group(1) + '// ' + linea.lstrip() if linea.strip() else linea
            for linea in m.group(0).split('\n')
        ]),
        contenido,
        flags=re.MULTILINE | re.DOTALL
    )
    
    return nuevo_contenido if nuevo_contenido != contenido else None

def main():
    # Directorio raíz donde buscar archivos Java
    root_dir = Path('./fgla')
    
    archivos_modificados = 0
    total_comentados = 0
    
    # Buscar todos los archivos .java excepto los de test
    for archivo_java in root_dir.rglob('*.java'):
        if 'test' in str(archivo_java).lower():
            continue
            
        resultado = comentar_llamadas_pista(archivo_java)
        
        if resultado:
            # Escribir el archivo modificado
            with open(archivo_java, 'w', encoding='utf-8', newline='') as f:
                f.write(resultado)
            
            # Contar cuántas llamadas se comentaron
            num_comentados = resultado.count('// pistaService.guardarPista')
            total_comentados += num_comentados
            archivos_modificados += 1
            
            print(f"Modificado: {archivo_java} - {num_comentados} llamadas comentadas")
    
    print(f"\nResumen:")
    print(f"Total de archivos modificados: {archivos_modificados}")
    print(f"Total de llamadas comentadas: {total_comentados}")

if __name__ == '__main__':
    main()
