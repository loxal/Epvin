/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.service

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService
import net.loxal.epvin.datastore.model.AppUser


class AppUserDAO : DAO() {
    val ofy: Objectify = ObjectifyService.ofy()

    fun put(appUser: AppUser) {
        ofy.save().entity<AppUser>(appUser).now()
    }

    operator fun get(id: Long?): AppUser {
        return ofy.load().type<AppUser>(AppUser::class.java).id(id!!).now()
    }

    fun retrieve(): List<AppUser> {
        return ofy.load().type<AppUser>(AppUser::class.java).list()
    }
}