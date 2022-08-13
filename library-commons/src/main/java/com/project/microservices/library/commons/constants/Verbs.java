package com.project.microservices.library.commons.constants;

public class Verbs {
    /**
     * Http Verbs
     */
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String CONNECT = "CONNECT";
    public static final String OPTIONS = "OPTIONS";
    public static final String TRACE = "TRACE";
    public static final String PATCH = "PATCH";

    /**
     * Project Verbs
     */
    public static final String GET_ALL = "GET_ALL";
    public static final String GET_BY_ID = "GET_BY_ID";
    public static final String GET_BY_NAME = "GET_BY_NAME";

    public static final String CREATE = "CREATE";
    public static final String EDIT = "EDIT";
    public static final String DELETE_BY_ID = "DELETE_BY_ID";
    public static final String DELETE_BY_NAME = "DELETE_BY_NAME";

    private static final String ROOT_PATH = "/";
    private static final String PAGES_PATH = "pages";
    private static final String ID_PATH = "{id}";
    private static final String NAME_PATH = "name/{name}";
    /*
    find by name end point path
    */
    public static final String FIND_BY_NAME_EPP = "find-by-name/{name}";

    public static final String ALL_OBJECTS = ROOT_PATH;
    public static final String ALL_OBJECTS_PAGES = ROOT_PATH + PAGES_PATH;
    public static final String OBJECT_BY_ID = ROOT_PATH + ID_PATH;
    public static final String OBJECT_BY_NAME = ROOT_PATH + NAME_PATH;

    public static final String OBJECT_FIND_BY_NAME = ROOT_PATH + FIND_BY_NAME_EPP;

    /**
     * Project Verbs end points path
     */

    public static final String PAGES_EPP = "pages";
    public static final String ALL_EPP = "all";
    public static final String FIND_EPP = "find";
    public static final String EXISTS_EPP = "exists";
    public static final String SAVE_EPP = "save";
    public static final String DELETE_EPP = "delete";
}
