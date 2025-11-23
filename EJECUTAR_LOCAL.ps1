#!/usr/bin/env powershell
# Scripts para ejecutar SISECOFI en LOCAL

# ============================================
# 1. CONFIGURACIÓN DE BASE DE DATOS
# ============================================

echo "=== PASO 1: Crear Base de Datos PostgreSQL ==="
echo "
Ejecuta en PostgreSQL (psql o pgAdmin):
  CREATE DATABASE sisecofi_auth;
  CREATE DATABASE sisecofi; -- Si no existe
"

# ============================================
# 2. CARGAR SCRIPT SQL INICIAL
# ============================================

echo "=== PASO 2: Cargar Script SQL ==="
echo "
Desde PowerShell:
"

# Comando para Windows (asume que psql está en PATH)
$psqlCmd = @"
psql -U postgres -d sisecofi_auth -f "c:\sisecofi\authorization-server\src\main\resources\init-auth-db.sql"
"@

echo $psqlCmd

# ============================================
# 3. COMPILAR PROYECTOS
# ============================================

echo "=== PASO 3: Compilar Proyectos ==="

Write-Host "3.1. Compilar authorization-server..." -ForegroundColor Cyan
cd "c:\sisecofi\authorization-server"
.\mvnw.cmd clean compile
if ($LASTEXITCODE -eq 0) { Write-Host "✅ authorization-server compilado" -ForegroundColor Green } 
else { Write-Host "❌ Error en compilación" -ForegroundColor Red; exit 1 }

Write-Host "3.2. Compilar LibreriaComunes..." -ForegroundColor Cyan
cd "c:\sisecofi\fgla\LibreriaComunes"
.\mvnw.cmd clean compile
if ($LASTEXITCODE -eq 0) { Write-Host "✅ LibreriaComunes compilado" -ForegroundColor Green } 
else { Write-Host "❌ Error en compilación" -ForegroundColor Red; exit 1 }

Write-Host "3.3. Compilar AdminContratos..." -ForegroundColor Cyan
cd "c:\sisecofi\fgla\AdminContratos\AdminContratos"
.\mvnw.cmd clean compile
if ($LASTEXITCODE -eq 0) { Write-Host "✅ AdminContratos compilado" -ForegroundColor Green } 
else { Write-Host "❌ Error en compilación" -ForegroundColor Red; exit 1 }

# ============================================
# 4. INICIAR SERVICIOS
# ============================================

echo "`n=== PASO 4: Iniciar Servicios ==="
echo "
OPCIÓN A: Iniciar en terminales separadas

Terminal 1 - Authorization-Server:
"

$authServerCmd = @"
cd "c:\sisecofi\authorization-server"
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
"@

echo $authServerCmd

echo "
Terminal 2 - AdminContratos:
"

$adminContratosCmd = @"
cd "c:\sisecofi\fgla\AdminContratos\AdminContratos"
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
"@

echo $adminContratosCmd

# ============================================
# 5. PROBAR ENDPOINTS
# ============================================

echo "`n=== PASO 5: Probar Endpoints ==="

echo "
5.1. Login (obtener JWT):
"

$loginCmd = @"
curl -X POST http://localhost:9000/auth/api/login `
  -H "Content-Type: application/json" `
  -d '{
    \"username\": \"admin\",
    \"password\": \"admin123\"
  }'
"@

echo $loginCmd

echo "
Respuesta esperada:
{
  \"token\": \"eyJhbGciOiJSUzI1NiIs...\",
  \"username\": \"admin\",
  \"rfc\": \"AAA000000AAA\",
  \"nombreCompleto\": \"Administrador Sistema\",
  \"email\": \"admin@sisecofi.gob.mx\",
  \"administracion\": \"SISECOFI\",
  \"fechaExpiracion\": \"2025-11-22T20:45:30.123456\",
  \"roles\": [...],
  \"permisos\": [...]
}
"

echo "
5.2. Validar Token:
"

$validateCmd = @"
curl -X GET http://localhost:9000/auth/api/validate `
  -H "Authorization: Bearer <TOKEN_DEL_LOGIN>"
"@

echo $validateCmd

echo "
Respuesta esperada:
{
  \"valid\": true
}
"

echo "
5.3. Listar Usuarios (requiere ADMIN):
"

$listUsersCmd = @"
curl -X GET http://localhost:9000/auth/admin/usuarios `
  -H "Authorization: Bearer <TOKEN_DEL_LOGIN>"
"@

echo $listUsersCmd

# ============================================
# 6. VERIFICACIÓN
# ============================================

echo "`n=== PASO 6: Verificación ==="
echo "
URLs esperadas:
  Authorization-Server: http://localhost:9000/auth
  AdminContratos: http://localhost:9292
  
Base de Datos:
  Host: localhost
  Port: 5432
  Database: sisecofi_auth
  User: postgres
  Password: admin1
  
Usuario de Prueba:
  Username: admin
  Password: admin123
  RFC: AAA000000AAA
  Rol: ADMIN
"

# ============================================
# 7. TROUBLESHOOTING
# ============================================

echo "`n=== PASO 7: Si hay errores ==="
echo "
Error: \"Connection refused\"
  → Verificar que PostgreSQL está corriendo
  → psql -U postgres para conectar
  
Error: \"Cannot find symbol\"
  → Limpiar y recompilar: mvnw clean compile -U
  
Error: \"User inactivo\"
  → UPDATE USUARIOS SET ESTADO='ACTIVO' WHERE USERNAME='admin';
  
Error: \"Token inválido\"
  → Verificar que no está expirado (1 hora de validez)
  → Verificar header: Authorization: Bearer <token>
"

# ============================================
# 8. LOGS
# ============================================

echo "`n=== PASO 8: Ver Logs ==="
echo "
Authorization-Server (stdout mientras se ejecuta)
AdminContratos (stdout mientras se ejecuta)

Nivel DEBUG habilitado en application.yml para autorización
"

echo "`n=== PROCEDIMIENTO COMPLETO ===" -ForegroundColor Green
echo "✅ Preparado para ejecutar SISECOFI en local"
echo "¡Sigue los pasos 1-6 para iniciar!"
