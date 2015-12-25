/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.model;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Logger;

public class OfyLocator extends Locator<DatastoreEntity, Long> {
    private static final Logger LOG = Logger.getLogger(OfyLocator.class.getName());

    public DatastoreEntity create(final Class<? extends DatastoreEntity> clazz) {
        try {
            return clazz.newInstance();
        } catch (final InstantiationException | RuntimeException | IllegalAccessException e) {
            LOG.warning(e.getMessage());
        }
        return new DatastoreEntity();
    }

    @Override
    public DatastoreEntity find(final Class<? extends DatastoreEntity> clazz, final Long id) {
        ObjectifyService.begin();
        return ObjectifyService.ofy().load().type(clazz).id(id).now();
    }

    @Override
    public Class<DatastoreEntity> getDomainType() {
        return DatastoreEntity.class;
    }

    @Override
    public Long getId(final DatastoreEntity domainObject) {
        return domainObject.getId();
    }

    @Override
    public Class<Long> getIdType() {
        return Long.class;
    }

    @Override
    public Object getVersion(final DatastoreEntity domainObject) {
        return domainObject.getVersion();
    }
}
