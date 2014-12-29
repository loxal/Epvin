/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service

import com.google.web.bindery.requestfactory.shared.ServiceLocator

/**
 * Must be in the same package as the DAOService classes.
 */
public class DAOServiceLocator : ServiceLocator {
    override fun getInstance(cls: Class<*>): Any? {
        try {
            return cls.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }
}