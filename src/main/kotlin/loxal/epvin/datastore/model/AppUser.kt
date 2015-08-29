/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity

Entity
public class AppUser : DatastoreEntity() {
    public var role: String = ""

    private val roleCtx: String = ""

    public var email: String = ""
}