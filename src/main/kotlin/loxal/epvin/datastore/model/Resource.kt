/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity

@Entity
public class Resource : DatastoreEntity() {
    var name: String = ""

    // employee / tennis court
    var type: String = ""
}
