/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import net.loxal.epvin.datastore.service.DAOServiceLocator;
import net.loxal.epvin.datastore.service.ResourceDAO;

import java.util.List;


@Service(value = ResourceDAO.class, locator = DAOServiceLocator.class)
public interface ResourceReqCtx extends RequestContext {
    Request<Void> put(ResourceProxy resource);

    Request<ResourceProxy> get(Long id);

    Request<List<ResourceProxy>> retrieve();
}
