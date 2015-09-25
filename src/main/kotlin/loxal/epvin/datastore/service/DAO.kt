/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.util.DatastoreUtils
import loxal.epvin.datastore.model.AppUser
import loxal.epvin.datastore.model.Employee
import loxal.epvin.datastore.model.Resource

open class DAO : DatastoreUtils() {
    companion object {
        init {
            ObjectifyService.register(AppUser::class.java)
            ObjectifyService.register(Employee::class.java)
            ObjectifyService.register(Resource::class.java)
        }

        public fun hasRight(authToken: String): Boolean {
            println(authToken)
            return false
        }
    }
}