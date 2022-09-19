package com.project.microservices.library.commons.constants;

public class Paths {
    public static final String PAGES = "pages";
    public static final String PATH_PARAMETER_NAME = "name/";

    public static final String VALUE_ID = "{id}";
    public static final String VALUE_NAME = "{name}";

    public static final String FIND_BY_NAME = "find-by-name/";
    public static final String FIND_BY_ID = "find-by-id/";
    public static final String EXISTS_BY_ID = "exists-by-id/";

    public static final String PATH_ROOT = "/";
    public static final String PATH_PAGES = PATH_ROOT + PAGES;
    public static final String PATH_ID = PATH_ROOT + VALUE_ID;
    public static final String PATH_NAME = PATH_ROOT + PATH_PARAMETER_NAME + VALUE_NAME;
}
