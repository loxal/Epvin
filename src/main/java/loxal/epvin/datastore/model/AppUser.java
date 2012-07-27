/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model;

import com.googlecode.objectify.annotation.Entity;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@Entity
public class AppUser extends DatastoreEntity {
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String roleCtx;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
