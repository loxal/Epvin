/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model;

import com.googlecode.objectify.annotation.Entity;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@Entity
public class Resource extends DatastoreEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // employee / tennis court
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
