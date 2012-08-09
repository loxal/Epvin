/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class OfyLocator extends Locator<DatastoreEntity, Long> {
    @Override
    public DatastoreEntity create(final Class<? extends DatastoreEntity> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DatastoreEntity find(final Class<? extends DatastoreEntity> cls, final Long id) {
        final Objectify ofy = ObjectifyService.begin();
        return ofy.load().type(cls).id(id).get();
    }

    // never called
    @Override
    public Class<DatastoreEntity> getDomainType() {
        return DatastoreEntity.class; // could also return null
    }

    @Override
    public Long getId(final DatastoreEntity domainEntity) {
        return domainEntity.getId();
    }

    @Override
    public Class<Long> getIdType() {
        return Long.class;
    }

    @Override
    public Object getVersion(final DatastoreEntity domainEntity) {
        return domainEntity.getVersion();
    }
}