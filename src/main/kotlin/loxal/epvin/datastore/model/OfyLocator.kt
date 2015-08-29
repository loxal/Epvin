/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model

import com.google.web.bindery.requestfactory.shared.Locator
import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

public class OfyLocator : Locator<DatastoreEntity, Long>() {
    override fun create(cls: Class<out DatastoreEntity>): DatastoreEntity {
        try {
            return cls.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }

    override fun find(cls: Class<out DatastoreEntity>, id: Long): DatastoreEntity {
        val ofy = ObjectifyService.ofy()
//        return ofy.load().type(cls).id(id).now()  // TODO fix this and do not use the expression below
        return DatastoreEntity()
    }

    // never called
    override fun getDomainType(): Class<DatastoreEntity> {
        return javaClass<DatastoreEntity>() // could also return null
    }

    override fun getId(domainEntity: DatastoreEntity): Long? {
        return domainEntity.id
    }

    override fun getIdType(): Class<Long> {
        return javaClass<Long>()
    }

    override fun getVersion(domainEntity: DatastoreEntity): Any {
        return domainEntity.version
    }
}
