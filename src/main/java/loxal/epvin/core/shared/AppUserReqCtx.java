/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import loxal.epvin.datastore.service.AppUserDAO;
import loxal.epvin.datastore.service.DAOServiceLocator;

import java.util.List;


@Service(value = AppUserDAO.class, locator = DAOServiceLocator.class)
public interface AppUserReqCtx extends RequestContext {
    Request<Void> put(AppUserProxy appUser);

    Request<AppUserProxy> get(Long id);

    Request<List<AppUserProxy>> retrieve();
}