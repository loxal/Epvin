/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;


public interface ReqFactory extends RequestFactory {

    AppUserReqCtx appUserReqCtx();

    ResourceReqCtx resourceReqCtx();

    EmployeeReqCtx employeeReqCtx();

}