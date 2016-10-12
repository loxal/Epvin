/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity

@Entity class AppUser : DatastoreEntity() {
    var role: String = ""

    private val roleCtx: String = ""

    var email: String = ""
}