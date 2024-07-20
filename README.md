# BIENVENIDO AL API de Reservas

## Descripción
Esta API permite reservar una casa, validando códigos de descuento y guardando los datos en una base de datos PostgreSQL.
## Requisitos
- Java 11+
- Maven
- PostgreSQL
- Docker(Opcional)

## Manejo de reintentos y Timeout
- El manejo de retry fue realizado en la clase DiscountService, maximo numero de reintentos 5
- El manejo del timeout fue realizado en DiscountService, con 5 segundos.
## Endpoints
- `POST /api/book`: Crear una nueva reserva.
## Endpoints con swagger.
- http://localhost:8080/swagger-ui.html

## Configuraciones
- Se puede usar el aplicativo de manera local o con docker
- A continuacion la explicacion para ambas maneras

-Opcion 1: Usando docker composer, le creara un container que contiene la base de datos y el backup de las tablas y el app.
-Opcion 2: Usando el idle y su base de datos de manera local
-Opcion 3: Creando de manera manual los container sin docker composer.

Independiende de las opciones primero
- Clona el repositorio.  usa el comando en la carpeta de su preferencia

 git clone https://github.com/9stark/bookingApp.git 
 
## Opcion 1: Crear imagen y despligue del aplicativo usando docker composer)

1. Configura la conexion `src/main/resources/application.properties`.

spring.datasource.url=jdbc:postgresql://postgres-db:5432/reservations_db 
spring.datasource.url=jdbc:postgresql://localhost:5432/reservations_db     <==Comentar esta linea

2. Usando la terminal de windows CMD posicionarte en la ruta raiz del proyecto
Ejemplo:   cd C:\JavaProjects\bookingApp

3. Construya la imagen de Docker, En el directorio que contiene el Dockerfile (C:\JavaProjects\bookingApp), ejecute:

docker build -t booking .

4. Ejecute Los siguientes comando en el siguiente orden
docker-compose up --build



5. Verificar la pagina (http://localhost:8080/swagger-ui.html) puedes usar el comando: curl  http://localhost:8080/swagger-ui.html

# Opcionales

(Ejecuta un select de la base de datos)
docker exec -it postgres-db psql -U postgres -d reservations_db -c "SELECT * FROM book;"


docker network create my-network
docker-compose run restore (Restaura la base de datos)
docker run -p 8080:8080 booking (Corre el aplicativo)






## Opcion 2 Configuracion del app local usando el Idle de su preferencia

# Paso 1: Instalacion de postgres o base de datos y tablas

Pasos para la creacion de la base de datos en postGres para uso local del aplicativo desde Idle (Intellij, Eclipse,etc)

1. Descarga PostgreSQL (En caso ya lo tengas instalado saltarse al paso 4)

2. Poner de usuario "postgres"  

3. Poner de password admin 

4. Asegurar de actualizar el archivo properties en java

spring.datasource.username=postgres (con usuario del anterior paso)
spring.datasource.password=admin (con la password del anterior paso)

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

# Paso 2: Ejecucion de la app

1. Configura la base de datos en `src/main/resources/application.properties`.

En el archivo properties  si trabajas de manera local comentar  el spring datasource con la palabra //postgres-db:5432
#spring.datasource.url=jdbc:postgresql://postgres-db:5432/reservations_db <==Comentar esta linea
spring.datasource.url=jdbc:postgresql://localhost:5432/reservations_db

2. Ejecuta `mvn spring-boot:run` para iniciar la aplicación.



## Opcion 3: configurar usando una instalacion PostgresSQL de manera local.

1. Descargar o crear el archivo backup ubicado en /backups/backup.sql docker network create my-network

2. Poner el archivo en una carpeta y usando la terminal y el comando cd posicionarse en la carpeta

Ejemplo:   cd C:\dockerFiles\backup.sql

3. Usar los siguiente comando para crear la imagen de docker de postgress

docker network create my-network

docker run --name postgres-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=reservations_db -p 5432:5432 -d postgres:latest


4. Ejecutar el siguiente comando para realizar la restauracion del backup

docker cp backup.sql postgres-db:/backup.sql

5. Ejecuta los siguiente comando para usar el aplicativo
Posicionarse en el lugar del aplicativo  y ejecutar los siguientes comandos cd C:\JavaProjects\bookingApp

docker run -p 8080:8080 booking (Corre el aplicativo)
