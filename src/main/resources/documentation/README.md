# Specifications

>Nota: Este apartado contiene especificaciones acerca del uso de los microservicios.  

## Content
- [1. Considerations](#Considerations)
- [2. Installation](#Installation)
  - [2.1 Sources](#Sources)
  - [2.2 Build](#Build)
  - [2.3 Database](#Database)
- [3. IDE](#IDE)
- [4. Docker](#Docker)
  - [4.1 DockerCompose](#DockerCompose)
- [5. Test](#Test)
- [6. API](#API)
- [7. Author](#Author)

## Considerations
>Nota: El proyecto está configurado para correr localmente o en contenedores docker en un ambiente local.

## Installation

##### Sources
- Descargar el proyeco de microservicios desde el [repositorio GITHUB](https://github.com/flemantico/microservicios)
- Descargar el proyeco de configuración desde el [repositorio GITHUB](https://github.com/flemantico/microservicios-configuration)

##### Build
Podemos invocar el siguiente comando desde la raíz del repositorio si queremos construir el repositorio completo con las Pruebas unitarias habilitadas:

```bash
gradle build --refresh-dependencies
```

##### Corriendo spring boot  
Para ejecutar un módulo Spring Boot, ejecute el comando: `gradle bootRun` en el directorio del proyecto.

##### Database

#### Ejecutar dump
Para crear la base de datos debe ejecutar el dum de cada entidad en una base de datos MySQL:  
`"/service-user/target/classes/import.sql"`  
`"/service-product/target/classes/import.sql"`

El user y password de la base de datos se debe definir en el IDE correspondiente, setear las siguientes variables:  
`DB_USER_NAME`,
`DB_PASSWORD`

## IDE
Este repositorio contiene solamente un módulo.
Simplemente, puede importar ese módulo en particular en IntelliJ IDEA o Eclipse.

## Environment
Setear las siguientes variables de entorno en el microservicio correspondiente [environment](../../../../src/main/resources/documentation/environment/README.md)

## Docker
Puede dockerizar los microservicios:

1 Generar el jar de cada microservicio:
Se deben saltar los test debido a que la configuración dockerizada generaría errores al querer levantar la configuración o las bases de datos.
```bash
.\mvnw clean package -DskipTest
```

2 Generar el docker file para cada microservicio:
Los archivos Dockerfile están ubicados en el microservicio correspondiente.

3 Crear una red para los microservicios:
```bash
docker network create springcloud
```

4 Generar la imagen Docker para cada microservicio con la configuración del archivo Dockerfile:
```bash
docker build -t config-server:v1 .
```

```bash
docker build -t eureka-server:v1 .
```

```bash
docker build -t user-server:v1 .
```

```bash
docker build -t oauth-server:v1 .
```

```bash
docker build -t product-server:v1 .
```

```bash
docker build -t item-server:v1 .
```

```bash
docker build -t zuul-server:v1 .
```

```bash
docker build -t gateway-server:v1 .
```

5 Generar la imagen Docker de MySQL:
Obtener los pasos desde la página de Docker [link](https://hub.docker.com/). Buscar mysql.
```bash
docker pull mysql:8
```

5.1 Generar la imagen Docker de RabbitMQ:
Obtener los pasos desde la página de RabbitMQ [link](https://www.rabbitmq.com).
```bash
docker pull rabbitmq:3.8-management-alpine
```

5.2 Generar la imagen Docker de Zipkin:
Obtener los pasos desde la página de Zipkin [link](https://www.zipkin.io).
```bash
docker build -t server-zipkin:v1 .
```

7 Ejecutar el contenedor de MySQL:
```bash
docker run -p 3306:3306 --name mysql8-server --network springcloud
	-e MYSQL_DATABASE=db-springboot_cloud -d
	-e MYSQL_ROOT_PASSWORD=sasa	
	mysql:8
```
Ahora se podría conectar a la base de datos mysql del contenedor Docker.

7.1 Ejecutar el contenedor de RabbitMQ:
```bash
docker run -p 15672:15672 -p 5672:5672 --name rabbitmq-server --network springcloud -d rabbitmq:3.8-management-alpine
```

7.2 Ejecutar el contenedor de Zipkin:
```bash
docker run -p 9411:9411 --name server-zipkin --network springcloud 
	-e RABBIT_ADDRESSES=rabbitmq-server:5672
	-e STORAGE_TYPE=mysql
	-e MYSQL_USER=zipkin
	-e MYSQL_PASS=zipkin
	-e MYSQL_HOST=mysql8-server
	server-zipkin:v1
```

8 Ejecutar los contenedores de cada microservicio:
Los microservicios user-server, product-server son escalables, debido a esto tienen puertos aleatorios.

```bash
docker run -p 8888:8888 --name config-server --network springcloud config-server:v1
```
--name es el nombre del contenedor que se ejecutara y config-server:v1 es el nombre de la imagen.

```bash
docker run -p 8761:8761 --name eureka-server --network springcloud eureka-server:v1
```

```bash
docker run -P --name user-server --network springcloud user-server:v1
```

```bash
docker run -p 9100:9100 --name oauth-server --network springcloud oauth-server:v1
```

```bash
docker run -P --name product-server --network springcloud product-server:v1
```

```bash
docker run -p 8002:8002 --name item-server --network springcloud item-server:v1
```

```bash
docker run -p 8090:8090 --name zuul-server --network springcloud zuul-server:v1
```

```bash
docker run -p 8090:8090 --name gateway-server --network springcloud gateway-server:v1
```



##### DockerCompose
Esta es una opción más organizada de desplegar los contenedores. Revisar el archivo docker-compose.yml
```bash
docker-compose up
```

Es conveniente levantar primero los servicios críticos. Se pueden levantar los servicios de a uno.
Se puede hacer con el comando:
```bash
docker-compose up -d config-server
docker-compose up -d eureka-server
docker-compose up -d mysql8-server
```

Verificar el despliegue con:
```bash
docker-compose logs -f
```
-f: folow (seguir)
Recién cuando finalice se podrán levantar los otros microservicios.

## Test
El comando `gradle install` desde dentro de un módulo ejecutará las pruebas unitarias en ese módulo.
Para los módulos Spring, esto también ejecutar el `SpringContextTest` si está presente.

Despliegue con:
```bash
docker-compose up -d product-server
docker-compose up -d item-server
docker-compose up -d user-server
docker-compose up -d oauth-server
docker-compose up -d zuul-server
```
## API

##### Login:
- Puede usar el usuario "admin (ROLE_ADMIN)" o "user (ROLE_USER)" con el mismo pass.
Generar el token de acceso y reemplazarlo en las peticiones ([TOKEN]) a la API.

```curl
curl --location --request POST 'localhost:8090/api/security/oauth/token' \
--header 'Authorization: Basic ZnJvbnRlbmRhcHA6MTIzNDU=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=F89D79D32FE551BB16AA7EF4A393A9BD' \
--data-urlencode 'username=admin' \
--data-urlencode 'password=12345' \
--data-urlencode 'grant_type=password'
}'
```

##### Candidates:

###### Get one:
Reemplazar [ID] por el ID deseado para obtener la información completa de un candidato en particular.

```curl
curl --location --request GET 'http://localhost:8020/candidates/[ID]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

###### Get pages:
Obtener la información completa de todos los candidatos.

```curl
curl --location --request GET 'http://localhost:8020/candidates/' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

###### Get pages filter:
Reemplazar [DATA] por el documento o nombre y apellido, para obtener una paginación de los candidatos (nombre completo y DNI).

```curl
curl --location --request GET 'http://127.0.0.1:8020/candidates/filter/[DATA]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

###### Get by email:
Reemplazar [EMAIL] por el email deseado, para obtener la información completa de un candidato en particular filtrado por email.
.

```curl
curl --location --request GET 'http://localhost:8020/candidates/email/[EMAIL]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

###### Get by document:
Reemplazar [DOCUMENT] por el documento deseado, para obtener la información completa de un candidato en particular filtrado por documento.

```curl
curl --location --request GET 'http://localhost:8020/candidates/document/[DOCUMENT]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

###### Get by fullName:
Reemplazar [FULL_NAME] por el nombre y apellido deseado, para obtener la información completa de un candidato en particular filtrado por nombre y apellido.

```curl
curl --location --request GET 'http://localhost:8020/candidates/fullName/[FULL_NAME]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

###### Post one:
Reemplazar "[FULL_NAME]", [DOCUMENT], "[BIRTH]", "[ADDRESS]", "[PHONE]", "[EMAIL]" por los valores deseados, para crear un nuevo canditato.

```curl
curl --location --request POST 'http://localhost:8020/candidates/' \
--header 'Authorization: Bearer Bearer [TOKEN]' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B643E07BDD945A65EAE810C244276DA8' \
--data-raw '{
    "fullName": "[FULL_NAME]",
    "document": [DOCUMENT],
    "birth": "[BIRTH]",
    "address": "[ADDRESS]",
    "phone": "[PHONE]",
    "email": "[EMAIL]"
}'
```

###### Put one:
Reemplazar [ID] por el id a modificar y "[FULL_NAME]", [DOCUMENT], "[BIRTH]", "[ADDRESS]", "[PHONE]", "[EMAIL]" por los valores deseados, para modificar un canditato.

```curl
curl --location --request PUT 'http://localhost:8020/candidates/[ID]' \
--header 'Authorization: Bearer Bearer [TOKEN]' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B643E07BDD945A65EAE810C244276DA8' \
--data-raw '{
    "fullName": "[FULL_NAME]",
    "document": [DOCUMENT],
    "birth": "[BIRTH]",
    "address": "[ADDRESS]",
    "phone": "[PHONE]",
    "email": "[EMAIL]"
}'
```

###### Delete one:
Reemplazar [ID] por el ID deseado, para eliminar un candidato.

```curl
curl --location --request DELETE 'http://localhost:8020/candidates/[ID]' \
--header 'Authorization: Bearer Bearer [TOKEN]' \
--header 'Cookie: JSESSIONID=B643E07BDD945A65EAE810C244276DA8'
```

##### Collection de Postman:
Link para descargar la colección [Acceso a la API Test (Postman)](https://www.getpostman.com/collections/2cf183635d36807cb4e3)

## Author
Para más detalles sobre el autor, ingresa al [Author](../../../../src/main/resources/documentation/author/README.md).

***
#### [content](#content)
### [Home](../../../../README.md)


```xml
<header>
    <name>
      ssssssssss
    </name>
</header>
```
