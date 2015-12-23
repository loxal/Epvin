/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import net.loxal.epvin.datastore.model.OfyLocator;
import net.loxal.epvin.datastore.model.Resource;


@ProxyFor(value = Resource.class, locator = OfyLocator.class)
public interface ResourceProxy extends EntityProxy {

    String getName();

	void setName(String name);

}
