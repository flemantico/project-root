package com.project.microservices.library.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
/*
 * Deshabilitar la autoconfiguración del data source.
 * Porque está la dependencia jpa, pero no una datasource y con esto evitamos la autoconfiguración de un datasource.
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Main { }
