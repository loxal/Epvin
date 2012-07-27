/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.manager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import loxal.epvin.core.client.ClientFactory;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
class Main implements EntryPoint {
    @Override
    public void onModuleLoad() {
        // init done via ClientFactory
        GWT.create(ClientFactory.class);
    }
}
