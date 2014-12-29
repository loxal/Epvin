/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.OnSave

Entity
public open class DatastoreEntity {
    OnSave
    fun prePersist() {
        this.version++
    }

    Id
    public var id: Long? = null

    public var version: Int = 0
}