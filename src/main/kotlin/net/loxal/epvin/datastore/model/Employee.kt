/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
public class Employee : DatastoreEntity() {
    @Past(message = "Birth date must be a past date.")
    public var birth: Date? = null

    @Pattern(message = "Invalid format.", regexp = ".*@.*")
    public var mail: String = ""

    @NotNull(message = "Required field.")
    @Size(message = "Invalid length.", min = 1, max = 35)
    public var nameFirst: String = ""

    @NotNull(message = "Required field.")
    @Size(message = "Invalid length.", min = 1, max = 35)
    public var nameLast: String = ""

    public var joining: Date? = null

    // make this a relation to another entity
    public var department: String = ""
}
