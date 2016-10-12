/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.OnSave

@Entity
open class DatastoreEntity {
    @OnSave
    fun prePersist() {
        this.version++
    }

    @Id var id: Long? = null

    var version: Int = 0
}