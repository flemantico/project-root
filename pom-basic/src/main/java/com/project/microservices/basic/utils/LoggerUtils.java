package com.project.microservices.basic.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import static com.project.microservices.basic.constants.Logs.*;

public class LoggerUtils {
    private static final String INFO_MSG = "Mensaje enviado: ";
    private static final String NULL_REQUEST = "Mensaje nulo.";
    private static final String LOGSTR1 = " {requested_by: ";
    private static final String LOGSTR2 = " , card: ";
    private static final String LOGSTR3 = "}";

    private LoggerUtils() {
    }

    public static <T> void logInfo(Logger loggerProject, T... request) {
        StringBuilder infoMsg = new StringBuilder().append(INFO_MSG);

        if (Objects.nonNull(request)) {
            for(Object cadena : Arrays.stream(request).toArray()) {
                infoMsg.append(sanitize(cadena));
            }
        } else {
            infoMsg.append(NULL_REQUEST);
        }

        String finalMessage = infoMsg.toString();
        loggerProject.info(sanitize(finalMessage));
    }

    public static <T> void logWarn(Logger loggerProject, T... request) {
        StringBuilder infoMsg = new StringBuilder().append(INFO_MSG);

        if (Objects.nonNull(request)) {
            for(Object cadena : Arrays.stream(request).toArray()) {
                infoMsg.append(sanitize(cadena));
            }
        } else {
            infoMsg.append(NULL_REQUEST);
        }

        String finalMessage = infoMsg.toString();
        loggerProject.warn(sanitize(finalMessage));
    }

    public static void logError(Logger logger) {
        logger.error("");
    }

    public static void logError(Logger logger, Object request, String errorMsg, Exception ex) {
        StringBuilder strBuild = new StringBuilder().append(errorMsg);

        if (request != null) {
            String className = request.getClass().toString();

            strBuild.append(LOGSTR1).append(LOGSTR2).append(className).append(LOGSTR3);
        }

        if (ex != null) {
            strBuild.append(BLANK_SPACE).append(ex);
        }

        String finalString = StringEscapeUtils.escapeJava(strBuild.toString());
        logger.error(finalString);
    }

    private static String sanitize(String uncleanString) {
        return StringEscapeUtils.escapeJava(uncleanString);
    }

    private static String sanitize(Object uncleanObject) {
        return StringEscapeUtils.escapeJava(uncleanObject.toString());
    }

    public static void logApplicationRunning(Logger loggerProject, String name, String main, Environment env, String port, String hostAddress, String port1, String profile) {
        String protocol = env.getProperty("server.ssl.key-store") != null? "https": "http";

        loggerProject.warn(
                "\n\n----------------------------------------------------------\n\t" +
                        "Application '{}' ({}) is running! Access URLs:\n\t" +
                        "Local: \t\t\t{}://localhost:{}\n\t" +
                        "External: \t\t{}://{}:{}\n\t" +
                        "Profile(s): \t{}" +
                        "\n----------------------------------------------------------\n\t",
                name,
                main,
                protocol,
                port,
                protocol,
                hostAddress,
                port,
                profile);

        //String finalMessage = infoMsg.toString();
        //loggerProject.warn(sanitize(finalMessage));

    }
}
