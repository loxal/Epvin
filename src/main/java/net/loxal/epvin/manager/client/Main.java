/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.manager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import net.loxal.epvin.core.client.ClientFactory;


class Main implements EntryPoint {
    @Override
    public void onModuleLoad() {
        // init done via ClientFactory
        GWT.create(ClientFactory.class);
    }
}
