@echo off
REM ========================================
REM Script de respaldo por tabla - SISECOFI
REM Base de datos: postgres
REM Esquema: sisecofi
REM ========================================

setlocal enabledelayedexpansion

REM Configurar colores ANSI
for /F %%a in ('echo prompt $E ^| cmd') do set "ESC=%%a"

REM Definir colores
set "RESET=%ESC%[0m"
set "BOLD=%ESC%[1m"
set "DIM=%ESC%[2m"

set "BLACK=%ESC%[30m"
set "RED=%ESC%[31m"
set "GREEN=%ESC%[32m"
set "YELLOW=%ESC%[33m"
set "BLUE=%ESC%[34m"
set "MAGENTA=%ESC%[35m"
set "CYAN=%ESC%[36m"
set "WHITE=%ESC%[37m"

set "BG_BLACK=%ESC%[40m"
set "BG_RED=%ESC%[41m"
set "BG_GREEN=%ESC%[42m"
set "BG_BLUE=%ESC%[44m"
set "BG_CYAN=%ESC%[46m"

cls
echo.
echo  %CYAN%%BOLD%┌─────────────────────────────────────────────────────────┐%RESET%
echo  %CYAN%%BOLD%│                                                         │%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%%BOLD%████████╗ ██╗ ███████╗ ███████╗  ██████╗  ██████╗ ███████╗ ██╗%RESET%  %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%%BOLD%██╔════╝ ██║ ██╔════╝ ██╔════╝ ██╔════╝ ██╔═══██╗██╔════╝ ██║%RESET%  %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%%BOLD%███████╗ ██║ ███████╗ █████╗   ██║      ██║   ██║█████╗   ██║%RESET%  %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%%BOLD%╚════██║ ██║ ╚════██║ ██╔══╝   ██║      ██║   ██║██╔══╝   ██║%RESET%  %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%%BOLD%███████║ ██║ ███████║ ███████╗ ╚██████╗ ╚██████╔╝██║      ██║%RESET%  %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%%BOLD%╚══════╝ ╚═╝ ╚══════╝ ╚══════╝  ╚═════╝  ╚═════╝ ╚═╝      ╚═╝%RESET%  %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│                                                         │%RESET%
echo  %CYAN%%BOLD%│%RESET%            %DIM%Sistema de Respaldo PostgreSQL%RESET%             %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%│                                                         │%RESET%
echo  %CYAN%%BOLD%└─────────────────────────────────────────────────────────┘%RESET%
echo.
echo  %DIM%Base de datos:%RESET% %WHITE%postgres%RESET% %DIM%│ Esquema:%RESET% %WHITE%sisecofi%RESET%
echo.
echo  %YELLOW%%BOLD%¿Qué tipo de respaldo desea realizar?%RESET%
echo.
echo    %GREEN%%BOLD%1%RESET%  %WHITE%Modo Rápido%RESET%              %DIM%• Crea respaldos + BDs vacías%RESET%
echo                                %DIM%  Ideal para estructura sin datos%RESET%
echo.
echo    %CYAN%%BOLD%2%RESET%  %WHITE%Modo Completo%RESET%            %DIM%• Crea respaldos + restaura datos%RESET%
echo                                %DIM%  Incluye toda la información%RESET%
echo.
echo    %RED%%BOLD%0%RESET%  %WHITE%Salir%RESET%
echo.
echo  %CYAN%%BOLD%─────────────────────────────────────────────────────────%RESET%
echo.
set /p OPCION="%BOLD%  Seleccione una opción [1/2/0]:%RESET% "

if "%OPCION%"=="1" (
    set RESTAURAR=NO
    echo.
    echo  %GREEN%%BOLD%✓%RESET% %WHITE%Modo seleccionado:%RESET% %GREEN%RÁPIDO%RESET% - Solo estructura de BDs
    timeout /t 2 /nobreak >nul
) else if "%OPCION%"=="2" (
    set RESTAURAR=SI
    echo.
    echo  %CYAN%%BOLD%✓%RESET% %WHITE%Modo seleccionado:%RESET% %CYAN%COMPLETO%RESET% - Con restauración de datos
    timeout /t 2 /nobreak >nul
) else if "%OPCION%"=="0" (
    echo.
    echo  %YELLOW%Operación cancelada%RESET%
    timeout /t 1 /nobreak >nul
    exit /b 0
) else (
    echo.
    echo  %RED%%BOLD%✗ Opción inválida%RESET%
    echo  %DIM%Por favor ejecute el script nuevamente%RESET%
    timeout /t 2 /nobreak >nul
    exit /b 1
)

cls

REM Configuración de conexión
set PGHOST=localhost
set PGPORT=5432
set PGDATABASE=postgres
set PGUSER=postgres
set PGPASSWORD=admin1

REM Directorio de salida
set OUTPUT_DIR=C:\sisecofi\docs_sisecofi\BD_RespaldosPlains
set SCHEMA=sisecofi

REM Directorios para respaldos completos
set SQL_OUTPUT_DIR=C:\Users\Luis Flores\Desktop\AreaDeTrabajo\DB\RespaldosDB\sql
set BACKUP_OUTPUT_DIR=C:\Users\Luis Flores\Desktop\AreaDeTrabajo\DB\RespaldosDB\backup

REM Crear directorios si no existen
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"
if not exist "%SQL_OUTPUT_DIR%" mkdir "%SQL_OUTPUT_DIR%"
if not exist "%BACKUP_OUTPUT_DIR%" mkdir "%BACKUP_OUTPUT_DIR%"

REM Timestamp para el respaldo
REM Formato: DD-MM-YY para archivos y DD-MM-YY(HH:MM) para carpetas
set DIA=%date:~0,2%
set MES=%date:~3,2%
set ANIO=%date:~8,2%
set HORA=%time:~0,2%
set MIN=%time:~3,2%
set SEG=%time:~6,2%

REM Eliminar espacios en blanco de la hora
set HORA=%HORA: =0%
set MIN=%MIN: =0%
set SEG=%SEG: =0%

REM Formato para archivos: resp-DD-MM-YY
set FILE_TIMESTAMP=resp-%DIA%-%MES%-%ANIO%

REM Formato para carpeta: backup-DD-MM-YY(HH:MM)
set FOLDER_TIMESTAMP=backup-%DIA%-%MES%-%ANIO%(%HORA%-%MIN%)

REM Crear subdirectorio con timestamp
set BACKUP_DIR=%OUTPUT_DIR%\%FOLDER_TIMESTAMP%
mkdir "%BACKUP_DIR%"

echo ========================================
echo Iniciando respaldo de tablas
echo Base de datos: %PGDATABASE%
echo Esquema: %SCHEMA%
echo Directorio: %BACKUP_DIR%
echo ========================================
echo.

REM Mostrar loader ASCII
echo.
echo  %CYAN%%BOLD%┌────────────────────────────────────────────────────────┐%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%📦 RESPALDO POR TABLA%RESET% %DIM%(archivos planos)%RESET%               %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%└────────────────────────────────────────────────────────┘%RESET%
echo.
call :ShowLoader
echo.

REM Obtener lista de tablas del esquema sisecofi
echo  %DIM%Obteniendo lista de tablas del esquema%RESET% %WHITE%"%SCHEMA%"%RESET%%DIM%...%RESET%
echo.

REM Crear archivo temporal con lista de tablas
set TEMP_FILE=%TEMP%\tablas_sisecofi.txt
psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %PGDATABASE% -t -c "SELECT tablename FROM pg_tables WHERE schemaname='%SCHEMA%' ORDER BY tablename;" > "%TEMP_FILE%"

REM Contador de tablas
set COUNT=0

REM Procesar cada tabla
for /f "tokens=*" %%T in ('type "%TEMP_FILE%"') do (
    set TABLE=%%T
    REM Eliminar espacios en blanco
    set TABLE=!TABLE: =!
    
    if not "!TABLE!"=="" (
        set /a COUNT+=1
        echo  %CYAN%[!COUNT!]%RESET% %DIM%Exportando:%RESET% %WHITE%!TABLE!%RESET%
        
        REM Exportar tabla con CREATE e INSERT
        pg_dump -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %PGDATABASE% -n %SCHEMA% -t %SCHEMA%.!TABLE! --clean --if-exists --inserts --column-inserts -f "%BACKUP_DIR%\!TABLE!.sql"
        
        if !ERRORLEVEL! EQU 0 (
            echo      %GREEN%%BOLD%✓%RESET% %DIM%!TABLE!.sql%RESET%
        ) else (
            echo      %RED%%BOLD%✗%RESET% %RED%ERROR%RESET%
        )
        echo.
    )
)

REM Limpiar archivo temporal
del "%TEMP_FILE%"

echo.
echo  %GREEN%%BOLD%✓ Respaldo completado%RESET%
echo  %DIM%Total de tablas exportadas:%RESET% %WHITE%!COUNT!%RESET%
echo  %DIM%Ubicación:%RESET% %WHITE%!BACKUP_DIR!%RESET%
echo.

REM Crear un respaldo completo adicional (todo el esquema en un solo archivo)
echo  %DIM%Creando respaldo completo del esquema%RESET% %WHITE%"%SCHEMA%"%RESET%%DIM%...%RESET%
pg_dump -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %PGDATABASE% -n %SCHEMA% --clean --if-exists --inserts -f "%BACKUP_DIR%\COMPLETO_esquema_%SCHEMA%.sql"

if %ERRORLEVEL% EQU 0 (
    echo  %GREEN%%BOLD%✓%RESET% %DIM%COMPLETO_esquema_%SCHEMA%.sql%RESET%
) else (
    echo  %RED%%BOLD%✗ ERROR%RESET% al crear respaldo completo
)

echo.
echo  %CYAN%%BOLD%┌────────────────────────────────────────────────────────┐%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%📦 RESPALDOS COMPLETOS%RESET% %DIM%(.sql y .backup)%RESET%               %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%└────────────────────────────────────────────────────────┘%RESET%
echo.
call :ShowLoader
echo.

REM ========================================
REM RESPALDO COMPLETO EN FORMATO SQL
REM ========================================
echo.
echo  %CYAN%[1/2]%RESET% %WHITE%Generando respaldo SQL completo...%RESET%
echo       %DIM%→%RESET% %SQL_OUTPUT_DIR%
echo.

set SQL_FILE=%SQL_OUTPUT_DIR%\%FILE_TIMESTAMP%.sql
REM Usar --inserts para incluir los datos en formato INSERT
pg_dump -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %PGDATABASE% -n %SCHEMA% --clean --if-exists --inserts --column-inserts -f "%SQL_FILE%"

if %ERRORLEVEL% EQU 0 (
    echo  %GREEN%%BOLD%✓ COMPLETADO%RESET% - Archivo SQL creado %DIM%(CON DATOS)%RESET%
    echo    %WHITE%%FILE_TIMESTAMP%.sql%RESET%
) else (
    echo  %RED%%BOLD%✗ ERROR%RESET% al crear respaldo SQL
)

echo.

REM ========================================
REM RESPALDO COMPLETO EN FORMATO CUSTOM (.backup)
REM ========================================
echo  %CYAN%[2/2]%RESET% %WHITE%Generando respaldo CUSTOM (.backup)...%RESET%
echo       %DIM%→%RESET% %BACKUP_OUTPUT_DIR%
echo.

set BACKUP_FILE=%BACKUP_OUTPUT_DIR%\%FILE_TIMESTAMP%.backup
pg_dump -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %PGDATABASE% -n %SCHEMA% --clean --if-exists -F c -f "%BACKUP_FILE%"

if %ERRORLEVEL% EQU 0 (
    echo  %GREEN%%BOLD%✓ COMPLETADO%RESET% - Archivo BACKUP creado
    echo    %WHITE%%FILE_TIMESTAMP%.backup%RESET%
) else (
    echo  %RED%%BOLD%✗ ERROR%RESET% al crear respaldo BACKUP
)

echo.
echo  %CYAN%%BOLD%─────────────────────────────────────────────────────────%RESET%
echo.
echo  %YELLOW%%BOLD%📊 RESUMEN DE RESPALDOS GENERADOS%RESET%
echo.
echo  %CYAN%1.%RESET% Respaldos por tabla %DIM%(planos)%RESET%
echo     %DIM%→%RESET% %BACKUP_DIR%
echo     %DIM%Tablas:%RESET% %WHITE%!COUNT!%RESET%
echo.
echo  %CYAN%2.%RESET% Respaldo completo %DIM%(SQL)%RESET%
echo     %DIM%→%RESET% %SQL_FILE%
echo.
echo  %CYAN%3.%RESET% Respaldo completo %DIM%(CUSTOM)%RESET%
echo     %DIM%→%RESET% %BACKUP_FILE%
echo.
echo  %CYAN%%BOLD%─────────────────────────────────────────────────────────%RESET%
echo.
echo  %CYAN%%BOLD%┌────────────────────────────────────────────────────────┐%RESET%
echo  %CYAN%%BOLD%│%RESET%  %WHITE%🗄️  CREANDO BASES DE DATOS DE PRUEBA%RESET%                 %CYAN%%BOLD%│%RESET%
echo  %CYAN%%BOLD%└────────────────────────────────────────────────────────┘%RESET%
echo.

REM ========================================
REM CREAR BASE DE DATOS DESDE ARCHIVO .SQL
REM ========================================
set DB_NAME_SQL=%DIA%-%MES%-%ANIO%-sql
echo  %CYAN%[1/2]%RESET% %WHITE%Creando base de datos:%RESET% %YELLOW%%DB_NAME_SQL%%RESET%
echo       %DIM%Desde:%RESET% %FILE_TIMESTAMP%.sql
echo.

REM Verificar si la BD ya existe y eliminarla
psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d postgres -c "DROP DATABASE IF EXISTS \"%DB_NAME_SQL%\";" >nul 2>&1

REM Crear nueva base de datos
psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d postgres -c "CREATE DATABASE \"%DB_NAME_SQL%\" ENCODING='UTF8';"

if %ERRORLEVEL% EQU 0 (
    echo  %GREEN%%BOLD%✓%RESET% Base de datos creada: %YELLOW%%DB_NAME_SQL%%RESET%
    
    if "%RESTAURAR%"=="SI" (
        REM Restaurar el respaldo SQL
        echo  %CYAN%%BOLD%↻%RESET% %DIM%Restaurando datos...%RESET%
        psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %DB_NAME_SQL% -f "%SQL_FILE%" >nul 2>&1
        
        if !ERRORLEVEL! EQU 0 (
            echo  %GREEN%%BOLD%✓ COMPLETADO%RESET% - Datos restaurados en %YELLOW%%DB_NAME_SQL%%RESET%
        ) else (
            echo  %RED%%BOLD%✗ ERROR%RESET% al restaurar datos
        )
    ) else (
        echo  %GREEN%%BOLD%✓ COMPLETADO%RESET% - Base de datos vacía
        echo  %DIM%Puede restaurar con:%RESET%
        echo  %DIM%psql -U %PGUSER% -d %DB_NAME_SQL% -f "%SQL_FILE%"%RESET%
    )
) else (
    echo  %RED%%BOLD%✗ ERROR%RESET% al crear base de datos
)

echo.

REM ========================================
REM CREAR BASE DE DATOS DESDE ARCHIVO .BACKUP
REM ========================================
set DB_NAME_BACKUP=%DIA%-%MES%-%ANIO%-backup
echo  %CYAN%[2/2]%RESET% %WHITE%Creando base de datos:%RESET% %YELLOW%%DB_NAME_BACKUP%%RESET%
echo       %DIM%Desde:%RESET% %FILE_TIMESTAMP%.backup
echo.

REM Verificar si la BD ya existe y eliminarla
psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d postgres -c "DROP DATABASE IF EXISTS \"%DB_NAME_BACKUP%\";" >nul 2>&1

REM Crear nueva base de datos
psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d postgres -c "CREATE DATABASE \"%DB_NAME_BACKUP%\" ENCODING='UTF8';"

if %ERRORLEVEL% EQU 0 (
    echo  %GREEN%%BOLD%✓%RESET% Base de datos creada: %YELLOW%%DB_NAME_BACKUP%%RESET%
    
    if "%RESTAURAR%"=="SI" (
        REM Restaurar el respaldo BACKUP usando pg_restore con opciones optimizadas
        echo  %CYAN%%BOLD%↻%RESET% %DIM%Restaurando datos...%RESET%
        
        REM Usar -j para paralelizar (ajusta el número según tus cores)
        pg_restore -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %DB_NAME_BACKUP% -j 4 --no-owner --no-privileges "%BACKUP_FILE%" 2>nul
        
        if !ERRORLEVEL! LEQ 1 (
            echo  %GREEN%%BOLD%✓ COMPLETADO%RESET% - Datos restaurados en %YELLOW%%DB_NAME_BACKUP%%RESET%
        ) else (
            echo  %RED%%BOLD%✗ ERROR%RESET% al restaurar datos
        )
    ) else (
        echo  %GREEN%%BOLD%✓ COMPLETADO%RESET% - Base de datos vacía
        echo  %DIM%Puede restaurar con:%RESET%
        echo  %DIM%pg_restore -U %PGUSER% -d %DB_NAME_BACKUP% "%BACKUP_FILE%"%RESET%
    )
) else (
    echo  %RED%%BOLD%✗ ERROR%RESET% al crear base de datos
)

echo.
echo  %CYAN%%BOLD%═════════════════════════════════════════════════════════%RESET%
echo.
echo  %GREEN%%BOLD%✓ PROCESO COMPLETADO%RESET%
echo.
echo  %WHITE%%BOLD%📦 Archivos de respaldo:%RESET%
echo  %DIM%• Tablas planas:%RESET%  %BACKUP_DIR%
echo  %DIM%• Archivo SQL:%RESET%    %SQL_FILE%
echo  %DIM%• Archivo BACKUP:%RESET% %BACKUP_FILE%
echo.
echo  %WHITE%%BOLD%🗄️  Bases de datos creadas:%RESET%
echo  %DIM%• %RESET%%YELLOW%%DB_NAME_SQL%%RESET% %DIM%(desde .sql)%RESET%
echo  %DIM%• %RESET%%YELLOW%%DB_NAME_BACKUP%%RESET% %DIM%(desde .backup)%RESET%
echo.
echo  %CYAN%%BOLD%═════════════════════════════════════════════════════════%RESET%
echo.
goto :end

REM ========================================
REM Función para mostrar loader animado
REM ========================================
:ShowLoader
echo  %CYAN%%BOLD%⠋%RESET% %DIM%Procesando...%RESET%
timeout /t 1 /nobreak >nul
goto :eof

:end
pause
