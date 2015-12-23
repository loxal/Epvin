/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.service

import com.googlecode.objectify.ObjectifyService
import net.loxal.epvin.datastore.model.Resource


public class ResourceDAO : DAO() {
    val ofy = ObjectifyService.ofy()

    public fun put(resource: Resource) {
        ofy.save().entity<Resource>(resource).now()
    }

    operator public fun get(id: Long?): Resource {
        return ofy.load().type<Resource>(Resource::class.java).id(id!!).now()
    }

    public fun retrieve(): List<Resource> {
        return ofy.load().type<Resource>(Resource::class.java).list()
    }
}