/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import loxal.epvin.datastore.model.Employee;
import loxal.epvin.datastore.model.OfyLocator;

import java.util.Date;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@ProxyFor(value = Employee.class, locator = OfyLocator.class)
public interface EmployeeProxy extends EntityProxy {
    Long getId();

    void setNameFirst(String nameFirst);

    String getNameFirst();

    void setNameLast(String nameLast);

    String getNameLast();

    void setMail(String mail);

    String getMail();

    void setBirth(Date birth);

    Date getBirth();
}
