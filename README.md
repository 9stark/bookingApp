# BIENVENIDO AL API de Reservas

## Descripción
Esta API permite reservar una casa, validando códigos de descuento y guardando los datos en una base de datos PostgreSQL.
## Requisitos
- Java 11+
- Maven
- PostgreSQL
## Configuración
1. Clona el repositorio.
2. Configura la base de datos en `src/main/resources/application.properties`.
3. Ejecuta `mvn spring-boot:run` para iniciar la aplicación.
## Manejo de reintentos y Timeout
- El manejo de retry fue realizado en la clase DiscountService, maximo numero de reintentos 5
- El manejo del timeout fue realizado en DiscountService, con 5 segundos.
## Endpoints
- `POST /api/book`: Crear una nueva reserva.
## Endpoints con swagger.
- http://localhost:8080/swagger-ui.html

# Pasos para la creacion de la base de datos en postGres para uso local del aplicativo desde Idle (Intellij, Eclipse,etc)
1. Descarga PostgreSQL (En caso ya lo tengas instalado saltarse al paso 4)
2. Poner de usuario "postgres"  
3. Poner de password admin 

4. Asegurar de actualizar el archivo properties en java
spring.datasource.username=postgres (O el usuario de su instalacion)
spring.datasource.password=admin  (O el usuario de su instalacion)

5.Crear DATABASE reservations_db
6.Crear tabla book

CREATE TABLE public.book (
    id character varying(10) NOT NULL,
    name character varying(50) NOT NULL,
    lastname character varying(50) NOT NULL,
    age integer NOT NULL,
    phone_number character varying(20) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    house_id character varying(15) NOT NULL,
    discount_code character varying(8),
    CONSTRAINT book_age_check CHECK (((age >= 18) AND (age <= 100)))
);

# Pasos para usar el aplicativo con Docker (Etapa 1: configuracion de PostgresSQL)
1.Descargar o crear el archivo backup

2.Poner el archivo en una carpeta y usando la terminal y los comando cd posicionarse en la carpeta
Ejemplo:   cd C:\Users\Oscar\Downloads\docker

3) Usar el siguiente comando para crear la imagen de docker de postgress
docker run --name postgres-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=reservations_db -p 5432:5432 -d postgres:latest

4) Ejecutar el siguiente comando para realizar la restauracion del backup
docker cp backup.sql postgres-db:/backup.sql

# Pasos para usar el aplicativo con Docker (Etapa 2: Crear imagen y despligue del aplicativo en docker)
1.Usando la terminal de windows CMD posicionarte en la ruta raiz del proyecto
Ejemplo:   cd C:\JavaProjects\bookingApp


2) Construya la imagen de Docker, En el directorio que contiene el Dockerfile, ejecute:
docker build -t booking .

3) Corre la aplicacion en el puerto 8080, ejecute:
docker run -p 8080:8080 booking

3) Verificar la pagina (http://localhost:8080/swagger-ui.html) puedes usar el comando: curl  http://localhost:8080/swagger-ui.html


