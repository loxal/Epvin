/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import net.loxal.epvin.core.client.ui.EmployeeView;
import net.loxal.epvin.core.shared.ReqFactory;

import java.util.logging.Logger;


public interface ClientFactory {
    Property getClientResource();

    EventBus getEb();

    ReqFactory getRf();

    PlaceController getPlaceController();

    EmployeeView getEmployeeView();

    Logger getLogger();
}
