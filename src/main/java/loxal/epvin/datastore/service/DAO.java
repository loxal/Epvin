/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DatastoreUtils;
import loxal.epvin.datastore.model.AppUser;
import loxal.epvin.datastore.model.Employee;
import loxal.epvin.datastore.model.Resource;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class DAO extends DatastoreUtils {
    static {
        ObjectifyService.register(AppUser.class);
        ObjectifyService.register(Employee.class);
        ObjectifyService.register(Resource.class);
    }

    public static boolean hasRight(final String authToken) {
        return false;
    }
}
