/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import loxal.epvin.datastore.model.AppUser;
import loxal.epvin.datastore.model.OfyLocator;


@ProxyFor(value = AppUser.class, locator = OfyLocator.class)
public interface AppUserProxy extends EntityProxy {
    String getEmail();

    void setEmail(String email);
}
