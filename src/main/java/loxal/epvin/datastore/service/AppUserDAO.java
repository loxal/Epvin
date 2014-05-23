/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import loxal.epvin.datastore.model.AppUser;

import java.util.List;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class AppUserDAO extends DAO {
    final Objectify ofy = ObjectifyService.ofy();

    public void put(final AppUser appUser) {
        ofy.save().entity(appUser).now();
    }

    public AppUser get(final Long id) {
        return ofy.load().type(AppUser.class).id(id).now();
    }

    public List<AppUser> retrieve() {
        return ofy.load().type(AppUser.class).list();
    }
}
