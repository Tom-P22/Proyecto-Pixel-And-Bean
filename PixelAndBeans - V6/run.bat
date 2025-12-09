@echo off
echo ========================================
echo    Pixel and Beans - Sistema de Gestion
echo ========================================
echo.

REM Verificar Java
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java no esta instalado o no esta en el PATH
    echo Por favor instale Java 17 o superior
    pause
    exit /b 1
)

REM Verificar JAR
if not exist "PixelAndBeans.jar" (
    echo ERROR: No se encuentra PixelAndBeans.jar
    echo Asegurese de ejecutar este script desde el directorio correcto
    pause
    exit /b 1
)

REM Verificar configuracion
if not exist "application.properties" (
    echo ADVERTENCIA: No se encuentra application.properties
    echo Se usara configuracion por defecto
    echo.
)

REM Ejecutar aplicacion
echo Iniciando aplicacion...
echo.
java -jar PixelAndBeans.jar

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La aplicacion termino con errores
    pause
)