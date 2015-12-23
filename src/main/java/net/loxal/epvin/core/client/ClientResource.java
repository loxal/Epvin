/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.GwtCreateResource;


public interface ClientResource extends ClientBundle {
    ClientResource INSTANCE = GWT.create(ClientResource.class);

    @Source("design.css")
    DesignCss design();

    @Source("main.css")
    MainCss main();

    @GwtCreateResource.ClassType(Property.class)
    GwtCreateResource<Property> factory();
}
