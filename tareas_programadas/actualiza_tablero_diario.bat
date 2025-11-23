@echo off
REM Tarea programada: Actualizar fecha del tablero SISECOFI
REM Ejecuta diariamente a las 17:00
REM Host: 99.95.29.29:5433
REM Base de datos: postgres5
REM Usuario: user_sisecofi


REM Ejecutar tareas en windows:
REM Start-Process powershell -Verb RunAs -ArgumentList "-NoExit","-Command","schtasks /create /tn 'SISECOFI_Actualizar_Tablero' /tr 'c:\sisecofi\tareas_programadas\actualiza_tablero_diario.bat' /sc daily /st 17:00 /f"

REM Configurar la contraseña de PostgreSQL
SET PGPASSWORD=sisecofi$2024$

REM Ejecutar el procedimiento en la base de datos remota
echo Ejecutando procedimiento en servidor remoto...
"C:\Program Files\PostgreSQL\16\bin\psql.exe" -h 99.95.29.29 -p 5433 -U user_sisecofi -d postgres5 -c "CALL sisecofi.fecha_actualiza_tablero();"

IF %ERRORLEVEL% EQU 0 (
    echo EXITO: Procedimiento ejecutado correctamente
    echo [%date% %time%] Tarea ejecutada correctamente >> "c:\sisecofi\tareas_programadas\log_tablero.txt"
) ELSE (
    echo ERROR: Fallo al ejecutar el procedimiento. Codigo de error: %ERRORLEVEL%
    echo [%date% %time%] ERROR - Codigo: %ERRORLEVEL% >> "c:\sisecofi\tareas_programadas\log_tablero.txt"
)

REM Limpiar la variable de entorno por seguridad
SET PGPASSWORD=
