/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.GwtCreateResource;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public interface ClientResource extends ClientBundle {
    final ClientResource INSTANCE = GWT.create(ClientResource.class);

    @Source("design.css")
    DesignCss design();

    @Source("main.css")
    MainCss main();

    @GwtCreateResource.ClassType(Property.class)
    GwtCreateResource<Property> factory();
}
