/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import loxal.epvin.datastore.service.DAOServiceLocator;
import loxal.epvin.datastore.service.EmployeeDAO;

import java.util.List;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@Service(value = EmployeeDAO.class, locator = DAOServiceLocator.class)
public interface EmployeeReqCtx extends RequestContext {
    Request<EmployeeProxy> get(Long id);

    Request<Void> delete(Long id);

    Request<List<EmployeeProxy>> retrieve();

    Request<Void> put(EmployeeProxy employee);

}
