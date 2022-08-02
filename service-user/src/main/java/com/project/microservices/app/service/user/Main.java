package com.project.microservices.app.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;

import java.util.Locale;

@EnableEurekaClient
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EntityScan({"com.project.microservices.library.commons.model.entity"})
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static ReadProperties object;

    @Autowired
    public void setA(ReadProperties object){
        Main.object = object;
    }

    public static void main(String[] args) {
        System.setProperty("log_file_name", "service-user");
        SpringApplication.run(Main.class, args);

        object.getProperties();
    }

    @Component
    public static class ReadProperties
    {
        @Value("${spring.application.name}")
        private String name;
        @Value("${server.port}")
        private String port;

        public void getProperties()
        {
            logger.warn("***** "+ name.toUpperCase(Locale.ROOT) +": " + port + " -> " + Main.class.getSimpleName() + " is started *****");
        }
    }
}
