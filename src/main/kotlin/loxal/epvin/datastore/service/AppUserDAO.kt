/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service

import com.googlecode.objectify.ObjectifyService
import loxal.epvin.datastore.model.AppUser


public class AppUserDAO : DAO() {
    val ofy = ObjectifyService.ofy()

    public fun put(appUser: AppUser) {
        ofy.save().entity<AppUser>(appUser).now()
    }

    operator public fun get(id: Long?): AppUser {
        return ofy.load().type<AppUser>(AppUser::class.java).id(id!!).now()
    }

    public fun retrieve(): List<AppUser> {
        return ofy.load().type<AppUser>(AppUser::class.java).list()
    }
}