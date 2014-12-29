/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Must be in the same package as the DAOService classes.
 */
public class DAOServiceLocator implements ServiceLocator {
    @Override
    public Object getInstance(final Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (final InstantiationException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}