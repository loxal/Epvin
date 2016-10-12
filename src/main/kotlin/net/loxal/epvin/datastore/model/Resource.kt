/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity

@Entity class Resource : DatastoreEntity() {
    var name: String = ""

    // employee / tennis court
    var type: String = ""
}
