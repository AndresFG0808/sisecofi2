# Script para comentar llamadas a pistaService.guardarPista correctamente
$archivosModificados = 0
$lineasComentadas = 0

# Buscar todos los archivos Java en los microservicios
$archivosJava = Get-ChildItem -Path "C:\sisecofi2\fgla" -Filter "*.java" -Recurse | Where-Object { $_.FullName -notmatch "\\test\\" }

foreach ($archivo in $archivosJava) {
    $lineas = Get-Content -Path $archivo.FullName
    $lineasNuevas = New-Object System.Collections.ArrayList
    $modificado = $false
    
    for ($i = 0; $i -lt $lineas.Count; $i++) {
        $linea = $lineas[$i]
        
        # Si encontramos una línea con pistaService.guardarPista
        if ($linea -match "pistaService\.guardarPista") {
            # Comentar esta línea
            $lineaComentada = $linea -replace "^(\s*)", "`$1// "
            [void]$lineasNuevas.Add($lineaComentada)
            $lineasComentadas++
            $modificado = $true
            
            # Buscar líneas siguientes que son continuación
            $j = $i + 1
            while ($j -lt $lineas.Count) {
                $lineaSiguiente = $lineas[$j]
                
                # Si la línea está indentada y no es una línea de código nueva
                if ($lineaSiguiente -match "^\s+[^\s]" -and $lineaSiguiente -notmatch "^\s*$" -and $lineaSiguiente -notmatch "^\s*(//|/\*|\*)" -and $lineaSiguiente -notmatch "^\s*\}") {
                    # Verificar si es parte de la llamada (contiene parámetros o cierre de paréntesis)
                    if ($lineaSiguiente -match "^\s+[A-Z]|^\s+Optional|^\s+\);|^\s+\"") {
                        $lineaComentada = $lineaSiguiente -replace "^(\s*)", "`$1// "
                        [void]$lineasNuevas.Add($lineaComentada)
                        $lineasComentadas++
                        $i = $j
                        $j++
                        
                        # Si encontramos el cierre de paréntesis con punto y coma, terminamos
                        if ($lineaSiguiente -match "\);") {
                            break
                        }
                    } else {
                        break
                    }
                } else {
                    break
                }
            }
        } else {
            [void]$lineasNuevas.Add($linea)
        }
    }
    
    # Solo escribir si hubo cambios
    if ($modificado) {
        $lineasNuevas | Set-Content -Path $archivo.FullName -Encoding UTF8
        $archivosModificados++
        Write-Host "Modificado: $($archivo.FullName)"
    }
}

Write-Host "`n==================================="
Write-Host "Resumen:"
Write-Host "Archivos modificados: $archivosModificados"
Write-Host "Líneas comentadas: $lineasComentadas"
Write-Host "===================================`n"
