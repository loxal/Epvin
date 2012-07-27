/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import loxal.epvin.datastore.model.Resource;

import java.util.List;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class ResourceDAO extends DAO {
    final Objectify ofy = ObjectifyService.begin();

    public void put(final Resource resource) {
        ofy.save().entity(resource).now();
    }

    public Resource get(final Long id) {
        return ofy.load().type(Resource.class).id(id).get();
    }

    public List<Resource> retrieve() {
        return ofy.load().type(Resource.class).list();
    }
}
