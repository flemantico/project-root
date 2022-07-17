package com.project.microservices.library.commons.environments;

public class Environments {
    public static final String JWT_KEY = "${config.security.oauth.jwt.key}";

    public static final String CLIENT_ID = "${config.security.oauth.client.id}";

    public static final String CLIENT_SECRET = "${config.security.oauth.client.secret}";

    /***
     * Message to identify the execution logs
     * TODO: Deben agregarse a los application.properties, pero este proyecto no es ejecutable. Ver donde agregarlos
     */
    public static final String MESSAGE_LOG = "${microservices.message.log}";

}
