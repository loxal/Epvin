/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model

import java.util.*

public class Vacation : DatastoreEntity() {
    public var vacationer: AppUser? = null

    public var day: Date? = null

    private var halfDay: Boolean = false

    public fun isHalfDay(): Boolean {
        return halfDay
    }

    public fun setHalfDay(halfDay: Boolean) {
        this.halfDay = halfDay
    }
}
