#Sirve para editar una variable de entorno
@echo off
# para linux (#!/bin/sh)

# dirección y puerto del servidor Rabbit
set RABBIT_ADDRESSES=localhost:5672
#Para linux (export RABBIT_ADDRESSES=localhost:5672)

# Configuración de MySQL
set STORAGE_TYPE=mysql
set MYSQL_USER=zipkin
set MYSQL_PASS=zipkin

#Cuando se levanta localmente:
java -jar c:\Software\Zipkin\server-zipkin-2.23.4-exec.jar

#En caso de estar en la misma ruta
#java -jar .\server-zipkin-2.23.4-exec.jar
