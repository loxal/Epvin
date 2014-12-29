package loxal.epvin.datastore.service

import com.googlecode.objectify.util.DatastoreUtils
import loxal.epvin.datastore.model.AppUser
import loxal.epvin.datastore.model.Employee
import loxal.epvin.datastore.model.Resource
import com.googlecode.objectify.ObjectifyService


open class DAO : DatastoreUtils() {
    class object {
        {
            ObjectifyService.register(javaClass<AppUser>())
            ObjectifyService.register(javaClass<Employee>())
            ObjectifyService.register(javaClass<Resource>())
        }

        public fun hasRight(authToken: String): Boolean {
            println(authToken)
            return false
        }
    }
}