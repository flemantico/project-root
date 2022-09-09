package com.project.microservices.library.commons.constants;

import static com.project.microservices.library.commons.constants.Paths.*;

/**
 * Paths of bus.
 */
public class PathsBus {

    /**
     *find by name end point path
     */
    public static final String PATH_BUS_FIND_BY_NAME_EPP = FIND_BY_NAME + VALUE_NAME;

    public static final String PATH_BUS_EXISTS_BY_ID_EPP = EXISTS_BY_ID + VALUE_ID;

    public static final String PATH_BUS_ALL_OBJECTS = PATH_ROOT;
    public static final String PATH_BUS_ALL_OBJECTS_PAGES = PATH_PAGES;
    public static final String PATH_BUS_OBJECT_BY_ID = PATH_ID;
    public static final String PATH_BUS_OBJECT_BY_NAME = PATH_NAME;
    public static final String PATH_BUS_OBJECT_FIND_BY_NAME = PATH_ROOT + PATH_BUS_FIND_BY_NAME_EPP;

    public static final String PATH_BUS_OBJECT_EXISTS_BY_ID = PATH_ROOT + PATH_BUS_EXISTS_BY_ID_EPP;

}
