/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model

import java.util.*

class Vacation : DatastoreEntity() {
    var vacationer: AppUser? = null

    var day: Date? = null

    private var halfDay: Boolean = false

    fun isHalfDay(): Boolean {
        return halfDay
    }

    fun setHalfDay(halfDay: Boolean) {
        this.halfDay = halfDay
    }
}
