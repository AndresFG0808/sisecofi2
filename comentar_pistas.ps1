# Script para comentar todas las llamadas a pistaService.guardarPista

$directorios = @(
    "c:\sisecofi2\fgla\AdminContratos",
    "c:\sisecofi2\fgla\AdminDevengados",
    "c:\sisecofi2\fgla\AdminGeneral",
    "c:\sisecofi2\fgla\Catalogos",
    "c:\sisecofi2\fgla\Proveedores",
    "c:\sisecofi2\fgla\Proyectos",
    "c:\sisecofi2\fgla\ReporteDocumental"
)

$contador = 0

foreach ($dir in $directorios) {
    if (Test-Path $dir) {
        Write-Host "Procesando: $dir" -ForegroundColor Cyan
        
        Get-ChildItem -Path $dir -Filter "*.java" -Recurse | ForEach-Object {
            $archivo = $_.FullName
            $contenido = Get-Content $archivo -Raw
            $contenidoOriginal = $contenido
            
            # Comentar lineas que contienen pistaService.guardarPista y NO estan ya comentadas
            $contenido = $contenido -replace '(\s*)(pistaService\.guardarPista)', '$1// $2'
            $contenido = $contenido -replace '(\s*)(pistaService\.guardarPistaSimple)', '$1// $2'
            
            if ($contenido -ne $contenidoOriginal) {
                Set-Content -Path $archivo -Value $contenido -NoNewline
                Write-Host "  Modificado: $($_.Name)" -ForegroundColor Green
                $contador++
            }
        }
    }
}

Write-Host ""
Write-Host "Total de archivos modificados: $contador" -ForegroundColor Yellow
