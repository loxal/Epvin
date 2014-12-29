package loxal.epvin.datastore.service

import loxal.epvin.datastore.model.Resource
import com.googlecode.objectify.ObjectifyService


public class ResourceDAO : DAO() {
    val ofy = ObjectifyService.ofy()

    public fun put(resource: Resource) {
        ofy.save().entity<Resource>(resource).now()
    }

    public fun get(id: Long?): Resource {
        return ofy.load().type<Resource>(javaClass<Resource>()).id(id!!).now()
    }

    public fun retrieve(): List<Resource> {
        return ofy.load().type<Resource>(javaClass<Resource>()).list()
    }
}