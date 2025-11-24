# Script para comentar correctamente las llamadas a pistaService.guardarPista
# que pueden ocupar múltiples líneas

$archivosJava = Get-ChildItem -Path ".\fgla" -Filter "*.java" -Recurse | Where-Object { $_.FullName -notlike "*\test\*" }

$totalArchivos = 0
$totalLineasComentadas = 0

foreach ($archivo in $archivosJava) {
    $contenido = Get-Content $archivo.FullName -Raw -Encoding UTF8
    
    if ($contenido -match 'pistaService\.guardarPista') {
        # Reemplazar todas las llamadas a pistaService.guardarPista con su versión comentada
        # Busca desde "pistaService.guardarPista" hasta el punto y coma que cierra la instrucción
        $nuevoContenido = $contenido -replace '(?m)^(\s*)(pistaService\.guardarPista\([^;]*\);)', '$1// $2'
        
        # También manejar el caso de llamadas que no terminan en la misma línea
        $nuevoContenido = $nuevoContenido -replace '(?ms)^(\s*)(pistaService\.guardarPista\((?:[^;]|\n)*?\);)', {
            param($match)
            $indent = $match.Groups[1].Value
            $code = $match.Groups[2].Value
            $lines = $code -split '\r?\n'
            $commentedLines = $lines | ForEach-Object { 
                if ($_ -match '^\s*$') { $_ } 
                else { $indent + '// ' + $_.TrimStart() }
            }
            $commentedLines -join "`r`n"
        }
        
        if ($contenido -ne $nuevoContenido) {
            Set-Content -Path $archivo.FullName -Value $nuevoContenido -Encoding UTF8 -NoNewline
            $totalArchivos++
            $lineasModificadas = ([regex]::Matches($nuevoContenido, '// pistaService\.guardarPista')).Count
            $totalLineasComentadas += $lineasModificadas
            Write-Host "Modificado: $($archivo.FullName) - $lineasModificadas llamadas comentadas"
        }
    }
}

Write-Host "`nResumen:"
Write-Host "Total de archivos modificados: $totalArchivos"
Write-Host "Total de llamadas a pistaService.guardarPista comentadas: $totalLineasComentadas"
