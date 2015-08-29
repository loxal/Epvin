/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import loxal.epvin.datastore.model.Employee;
import loxal.epvin.datastore.model.OfyLocator;

import java.util.Date;

@ProxyFor(value = Employee.class, locator = OfyLocator.class)
public interface EmployeeProxy extends EntityProxy {
    Long getId();

    String getNameFirst();

	void setNameFirst(String nameFirst);

    String getNameLast();

	void setNameLast(String nameLast);

    String getMail();

	void setMail(String mail);

    Date getBirth();

	void setBirth(Date birth);
}
