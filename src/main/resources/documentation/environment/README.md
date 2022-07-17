# Environment

Este archivo contiene información acerca de las variables de entorno.

## Content
- [1. Considerations](#Considerations)
- [2. Set](#Set)

## Considerations
>Nota: Setear las variables en el microservicio correspondiente.

## Set

- ### Configuration
* Variables globales:  
Se deben configurar en todos los microservicios.
`APPLICATION_MESSAGE_LOG: ${APPLICATION_NAME}`: Ayuda al agrupamiento de los logs.  
`APPLICATION_NAME: [APPLICATION_NAME]`: Nombre del microservicio.  
`SERVER_CONFIGURATION_CONFIGURATION_ENVIRONMENT: dev`: Indica el ámbito de la configuración (dev, int, qas, prd).  
`APPLICATION_MESSAGE_LOG: [APPLICAION_NAME]`: Sirve para especificar la ruta de los logs (se recomienda usar el nombre del microservicio).  

* Especificación de las variables de los microservicios:  
**[server-configuration](server-configuration.md)**  
**[server-eureka](server-eureka.md)**  
**[server-oauth](server-oauth.md)**  
**[service-item](server-gateway.md)**  
**[service-user](service-user.md)**  
**[service-product](service-product.md)**  
**[service-item](service-item.md)**  

- ### Logs
#### APPLICATION_NAME:
Esta variable se utiliza en los logs, para identificar la aplicación.  
**server-configuration** - `APPLICATION_NAME=server-configuration`  
**server-eureka** - `APPLICATION_NAME=server-eureka`
**server-oauth** - `APPLICATION_NAME=server-oauth`
**server-gateway** - `APPLICATION_NAME=server-gateway`
**service-user** - `APPLICATION_NAME=service-user`
**service-product** - `APPLICATION_NAME=service-product`
**service-item** - `APPLICATION_NAME=service-item`

- ### Database
#### DB_USER_NAME:
Esta variable representa el usuario de la base de datos.  
`DB_USER_NAME=root`  

#### DB_PASSWORD:
Esta variable representa la contraseña de la base de datos.  
`DB_PASSWORD=Fluxit.0101*`  

Se debe setear en todos los microservicios que tengan acceso a datos, por ejemplo:  
service-user  
service-product  

***
#### [content](#content)
### [Home](../../../../../README.md)
