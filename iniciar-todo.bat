@echo off

echo Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)...
cd eureka
start "EUREKA SERVER" cmd /k "mvnw spring-boot:run"

echo Esperando 12 segundos a que Eureka se estabilice...
timeout /t 12 /nobreak > nul

echo Iniciando API Gateway...
cd ../gateway
start "API GATEWAY" cmd /k "mvnw spring-boot:run"

echo.
echo Esperando 5 segundos antes del primer microservicio...
timeout /t 5 /nobreak > nul

echo Iniciando Microservicio cliente...
cd ../cliente-pedido-depacho
start cmd /k "mvnw spring-boot:run"

echo.
timeout /t 4 /nobreak > nul

echo Iniciando Microservicio Productos...
cd ../item-productos-ubicacion
start cmd /k "mvnw spring-boot:run"

echo.
timeout /t 4 /nobreak > nul

echo Iniciando Microservicio Recepcion...
cd ../RecepcionMicroService
start cmd /k "mvnw spring-boot:run"

echo.
timeout /t 4 /nobreak > nul

echo Ecosistema lanzado. Dashboard disponible en http://localhost:8761