package com.project.microservices.app.server.configuration;

import com.project.microservices.basic.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

@EnableConfigServer
@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static ReadProperties object;

    @Autowired
    public void setA(ReadProperties object){
        Main.object = object;
    }

    public static void main(String[] args) throws UnknownHostException {
        System.setProperty("log_file_name", "server-configuration");
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

        @Value("${spring.profiles.active}")
        private static String profile;

        @Autowired
        Environment env;

        public void getProperties() throws UnknownHostException {
            LoggerUtils.logApplicationRunning(logger, name.toUpperCase(Locale.ROOT), Main.class.getSimpleName(), env, port, InetAddress.getLocalHost().getHostAddress(), port, profile);
        }
    }
}
