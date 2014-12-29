package loxal.epvin.datastore.service

import loxal.epvin.datastore.model.AppUser
import com.googlecode.objectify.ObjectifyService


public class AppUserDAO : DAO() {
    val ofy = ObjectifyService.ofy()

    public fun put(appUser: AppUser) {
        ofy.save().entity<AppUser>(appUser).now()
    }

    public fun get(id: Long?): AppUser {
        return ofy.load().type<AppUser>(javaClass<AppUser>()).id(id!!).now()
    }

    public fun retrieve(): List<AppUser> {
        return ofy.load().type<AppUser>(javaClass<AppUser>()).list()
    }
}