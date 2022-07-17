# Environment (server-oauth)

Este archivo contiene información acerca de las variables de entorno del microservicio server-oauth.

## Content
- [1. Considerations](#Considerations)
- [2. Set](#Set)

## Considerations
>Nota: Setear las variables en el microservicio server-oauth.

## Set

- ### Configuration
Se deben setear en el microservicio **server-oauth**  
`APPLICATION_MESSAGE_LOG: ${APPLICATION_NAME}`: Ayuda al agrupamiento de los logs.  
`SERVER_OAUTH_SECRET: 12345`: Se usa desde el frontend, para poder acceder al microservicio.  
`SERVER_OAUTH_ID: appmicorservices`: Se usa desde el frontend, para poder acceder al microservicio.  
`SERVER_OAUTH_KEY: algun_codigo_secreto_aeiou`: Sirve para generar el token.  
`APPLICATION_NAME: server-oauth`: Nombre del microservicio.  
`SERVER_CONFIGURATION_SECURITY_USER: root`: Usuario de la configuración.  
`SERVER_CONFIGURATION_SECURITY_PASSWORD: s3cr3t`: Contraseña de la configuración.  

***
#### [content](#content)
### [Home](../../../../../README.md)
