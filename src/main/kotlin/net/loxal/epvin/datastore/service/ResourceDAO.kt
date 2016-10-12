/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.service

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService
import net.loxal.epvin.datastore.model.Resource


class ResourceDAO : DAO() {
    val ofy: Objectify = ObjectifyService.ofy()

    fun put(resource: Resource) {
        ofy.save().entity<Resource>(resource).now()
    }

    operator fun get(id: Long?): Resource {
        return ofy.load().type<Resource>(Resource::class.java).id(id!!).now()
    }

    fun retrieve(): List<Resource> {
        return ofy.load().type<Resource>(Resource::class.java).list()
    }
}