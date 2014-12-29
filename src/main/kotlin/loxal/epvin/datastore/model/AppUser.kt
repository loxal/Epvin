package loxal.epvin.datastore.model

import com.googlecode.objectify.annotation.Entity

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
Entity
public class AppUser : DatastoreEntity() {
    public var role: String? = null

    private val roleCtx: String? = null

    public var email: String? = null
}