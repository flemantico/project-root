# Environment (server-eureka)

Este archivo contiene información acerca de las variables de entorno del microservicio server-eureka.

## Content
- [1. Considerations](#Considerations)
- [2. Set](#Set)

## Considerations
>Nota: Setear las variables en el microservicio server-eureka.

## Set

- ### Configuration
Se deben setear en el microservicio **server-eureka**
`APPLICATION_MESSAGE_LOG: ${APPLICATION_NAME}`: Ayuda al agrupamiento de los logs.  
`APPLICATION_NAME: server-eureka`: Nombre del microservicio.  
`SERVER_EUREKA_HOST_NAME: localhost`: Si no lo ponemos genera problemas con el nombre del host en el server-gateway.  
`SERVER_CONFIGURATION_SECURITY_USER: root`: Usuario de la configuración.  
`SERVER_CONFIGURATION_SECURITY_PASSWORD: s3cr3t`: Contraseña de la configuración.  

***
#### [content](#content)
### [Home](../../../../../README.md)
