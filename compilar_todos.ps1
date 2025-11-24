# Script para ejecutar mvn clean install en todos los microservicios
$microservicios = @(
    "fgla\AdminContratos\AdminContratos",
    "fgla\AdminDevengados\AdminDevengados",
    "fgla\AdminGeneral\AdminGeneral",
    "fgla\Catalogos\Catalogos",
    "fgla\Proveedores\Proveedores",
    "fgla\Proyectos\Proyectos",
    "fgla\ReporteDocumental\ReporteDocumental"
)

$exitosos = 0
$fallidos = 0
$resultado = @()

foreach ($micro in $microservicios) {
    Write-Host "`n============================================" -ForegroundColor Cyan
    Write-Host "Compilando: $micro" -ForegroundColor Cyan
    Write-Host "============================================" -ForegroundColor Cyan
    
    Push-Location $micro
    
    mvn clean install -DskipTests
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "`n✓ $micro compilado exitosamente" -ForegroundColor Green
        $exitosos++
        $resultado += [PSCustomObject]@{
            Microservicio = $micro
            Estado = "EXITOSO"
        }
    } else {
        Write-Host "`n✗ $micro falló la compilación" -ForegroundColor Red
        $fallidos++
        $resultado += [PSCustomObject]@{
            Microservicio = $micro
            Estado = "FALLIDO"
        }
    }
    
    Pop-Location
}

Write-Host "`n`n============================================" -ForegroundColor Yellow
Write-Host "RESUMEN DE COMPILACIÓN" -ForegroundColor Yellow
Write-Host "============================================" -ForegroundColor Yellow
$resultado | Format-Table -AutoSize
Write-Host "Total exitosos: $exitosos" -ForegroundColor Green
Write-Host "Total fallidos: $fallidos" -ForegroundColor Red
