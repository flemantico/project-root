# Environment (server-configuration)

Este archivo contiene información acerca de las variables de entorno del microservicio server-configuration.

## Content
- [1. Considerations](#Considerations)
- [2. Set](#Set)

## Considerations
>Nota: Setear las variables en el microservicio server-configuration.

## Set

- ### Configuration
Se deben setear en el microservicio **server-configuration**  
`APPLICATION_MESSAGE_LOG: ${APPLICATION_NAME}`: Ayuda al agrupamiento de los logs.  
`APPLICATION_NAME: server-configuration`: Nombre del microservicio.  
`SERVER_CONFIGURATION_DEFAULT_LABEL: master`: Especificación de la rama (por defecto: master).  
`SERVER_CONFIGURATION_CONFIGURATION_ENVIRONMENT: dev`: Indica el ámbito de la configuración (dev, int, qas, prd).  
`SERVER_CONFIGURATION_SSL_VALIDATION: true`:  Para saltar la validación ssl de GitHub.  
`SERVER_CONFIGURATION_GIT_URI: https://github.com/[USER_NAME]/[REPOSITORY].git`: URL del repositorio de GitHub.  
`SERVER_CONFIGURATION_GIT_USER: [GIT_USER]`: Usuario de GitHub.  
`SERVER_CONFIGURATION_GIT_PASSWORD: [GIT_PASSWORD]`: Password de GitHub.  
`SERVER_CONFIGURATION_SECURITY_USER: root`: Usuario de la configuración.  
`SERVER_CONFIGURATION_SECURITY_PASSWORD: s3cr3t`: Contraseña de la configuración.  
`SERVER_CONFIGURATION_SERVER_PORT: 8888`: Puerto donde se ejecutará el microservicio.  

***
#### [content](#content)
### [Home](../../../../../README.md)
