package com.project.microservices.library.commons.utils;

//import Environments;
import com.project.microservices.library.commons.model.entity.http.ResponseClass;
import com.project.microservices.library.commons.constants.Errors;
import com.project.microservices.library.commons.model.entity.http.ResponseData;
import com.project.microservices.library.commons.model.entity.http.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.project.microservices.library.commons.constants.Messages.*;

public final class GlobalsFunctions {
    static final Logger LOGGER = LoggerFactory.getLogger(GlobalsFunctions.class);

    /**
     * Retorna el puerto actualmente utilizado.
     * @return Integer
     */
    public static Integer getPort(Environment env){
        try {
            return Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port")));
        }catch(NullPointerException e){
            return 0;
        }
    }

     public static ResponseError createError(String code, String description, ResponseClass response) {
        ResponseError error = new ResponseError();
        error.setCode(code);
        error.setDetail(String.format("Error: %s", description));
        response.addErrorsItem(error);
        return error;
    }

    public static ResponseData createData(String code, String description, Object object) {
        ResponseData data = new ResponseData();
        if (code != null) data.statusCode(code);
        if (description != null) data.contentMessage(description);
        if (object != null) data.object(object);
        return data;
    }

    /**
     * Add data item.
     * @param object Object
     * @param response ResponseClass
     * @return HttpStatus
     */
    public static HttpStatus verifyIsFoundEmptyResponse(Object object, ResponseClass response) {

        if (isEmpty(object)) {
            LOGGER.info(MESSAGE_ERROR, createError(Errors.NOT_FOUND_CODE, Errors.NOT_FOUND_DETAIL, response));
            return HttpStatus.NOT_FOUND;
        } else {
            switch (HttpMethod.valueOf(response.getMeta().getMethod())){
                case GET:
                    response.addDataItem(RESOURCE_FOUND_CODE, RESOURCE_FOUND_DETAIL, object);
                    return HttpStatus.FOUND;
                case POST:
                    response.addDataItem(RESOURCE_CREATED_CODE, RESOURCE_CREATED_DETAIL, object);
                    return HttpStatus.CREATED;
                case PUT:
                    response.addDataItem(RESOURCE_UPDATE_CODE, RESOURCE_UPDATE_DETAIL, object);
                    return HttpStatus.CREATED;
                case DELETE:
                    response.addDataItem(RESOURCE_DELETE_CODE, RESOURCE_DELETE_DETAIL, object);
                    return HttpStatus.NO_CONTENT;
                default:
                    response.addDataItem(METHOD_NOT_IMPLEMENTED_CODE, METHOD_NOT_IMPLEMENTED_DETAIL, object);
                    return HttpStatus.NOT_IMPLEMENTED;
            }
        }
    }

    public static boolean verifyIsFoundEmpty(Object object, ResponseClass response) {
        if (isEmpty(object)) {
            LOGGER.info(MESSAGE_ERROR, createError(Errors.NOT_FOUND_CODE, Errors.NOT_FOUND_DETAIL, response));
            return true;
        }
        return false;
    }

    public static HttpStatus verifyIsFoundEmpty(Object object) {
        if (isEmpty(object)) {
            LOGGER.info(MESSAGE_ERROR, Errors.NOT_FOUND_CODE, Errors.NOT_FOUND_DETAIL);
            return HttpStatus.NOT_FOUND;
        }else{
            return HttpStatus.OK;
        }
    }


    public static Boolean isEmpty(Object object) {
        return object == null;
    }

    public static boolean isNull(Object obj) {
        return null == obj;
    }

    public static boolean isNotNull(Object obj) {
        return null != obj;
    }

    public static boolean isNotNullAndNotEmpty(Object obj) {
        return null != obj && obj.toString().isEmpty();
    }


    /**
     * Copies encapsulated properties from one object to another of the same type.
     *
     * @param <T>             T
     * @param source:         Source object.
     * @param target:         Target object.
     * @param noTargetMethod: Array of methods that will not be used.
     */
    //public static final <T> void copyAvailableFields(@NotNull T source, @NotNull T target, String[] noTargetMethod) {
    public static <T> void copyAvailableFields(T source, T target, String[] noTargetMethod) {
        if (isEmpty(source) || isEmpty(target)) {
            return;
        }

        Field[] fields = source.getClass().getDeclaredFields();
        Method[] methods = target.getClass().getDeclaredMethods();

        for (Method method : methods) {
            for (Field field : fields) {
                try {
                    if (method.getName().toUpperCase().contains(field.getName().toUpperCase()) && method.getName().toUpperCase().contains("SET") && !method.getName().toUpperCase().contains("SETID")) {
                        boolean tm = true;
                        if (noTargetMethod != null) {
                            for (String ntm : noTargetMethod) {
                                if (method.getName().substring(3, method.getName().length()).toUpperCase().equals(ntm.toUpperCase())) {
                                    tm = false;
                                    break;
                                }
                            }
                        }
                        if (tm) {
                            Method setMethod = target.getClass().getDeclaredMethod(method.getName(), field.getType());
                            Method getMethod = source.getClass().getDeclaredMethod(method.getName().replace("set", "get"));
                            setMethod.invoke(target, getMethod.invoke(source));
                        }
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    /***
     * We centralize the log to be able to personalize and sanitize it.
     * @param cls Class<?>
     * @return CommonsLogger
     */
    public static Logger loggerFactory(Class<?> cls){
        return LoggerFactory.getLogger(cls.getName());
    }

    public static void addInfo(String str, Logger log){
        //log.info(message_log.concat(str));
        log.info(" <<Mensaje personalizado>> ".concat(str));
    }

}
