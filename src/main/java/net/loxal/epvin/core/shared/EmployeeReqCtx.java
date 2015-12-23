/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import net.loxal.epvin.datastore.service.DAOServiceLocator;
import net.loxal.epvin.datastore.service.EmployeeDAO;

import java.util.List;

@Service(value = EmployeeDAO.class, locator = DAOServiceLocator.class)
public interface EmployeeReqCtx extends RequestContext {
    Request<EmployeeProxy> get(Long id);

    Request<Void> delete(Long id);

    Request<List<EmployeeProxy>> retrieve();

    Request<Void> put(EmployeeProxy employee);
}
