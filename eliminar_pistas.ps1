# Script para eliminar completamente las llamadas a pistaService.guardarPista

$archivosModificados = 0
$lineasEliminadas = 0

# Buscar todos los archivos Java en los microservicios
$archivosJava = Get-ChildItem -Path "C:\sisecofi2\fgla" -Filter "*.java" -Recurse

foreach ($archivo in $archivosJava) {
    $contenido = Get-Content -Path $archivo.FullName -Raw
    $contenidoOriginal = $contenido
    
    # Eliminar líneas que contienen pistaService.guardarPista (ya sea comentadas o no)
    # Esto incluye líneas que empiezan con // o están dentro de /* */
    $lineas = Get-Content -Path $archivo.FullName
    $lineasNuevas = @()
    $dentroDeComentarioPista = $false
    
    for ($i = 0; $i -lt $lineas.Count; $i++) {
        $linea = $lineas[$i]
        
        # Detectar si es una línea que contiene pistaService.guardarPista
        if ($linea -match "pistaService\.guardarPista") {
            $lineasEliminadas++
            # Si la línea tiene /* al inicio, buscar el */ de cierre
            if ($linea -match "/\*.*pistaService\.guardarPista") {
                $dentroDeComentarioPista = $true
            }
            continue
        }
        
        # Si estamos dentro de un comentario de bloque de pista, buscar el cierre
        if ($dentroDeComentarioPista) {
            if ($linea -match "\*/") {
                $dentroDeComentarioPista = $false
                $lineasEliminadas++
            } else {
                $lineasEliminadas++
            }
            continue
        }
        
        # Si la línea está comentada y parece ser parte de una llamada a pista
        if ($linea -match "^\s*//.*TipoMovPista|^\s*//.*TipoSeccionPista|^\s*//.*ModuloPista") {
            $lineasEliminadas++
            continue
        }
        
        $lineasNuevas += $linea
    }
    
    # Solo escribir si hubo cambios
    if ($lineasNuevas.Count -ne $lineas.Count) {
        $lineasNuevas | Set-Content -Path $archivo.FullName -Encoding UTF8
        $archivosModificados++
        Write-Host "Modificado: $($archivo.FullName)"
    }
}

Write-Host "`n==================================="
Write-Host "Resumen:"
Write-Host "Archivos modificados: $archivosModificados"
Write-Host "Líneas eliminadas: $lineasEliminadas"
Write-Host "===================================`n"
