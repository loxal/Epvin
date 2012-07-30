/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import loxal.epvin.core.client.ui.EmployeeView;
import loxal.epvin.core.shared.ReqFactory;

import java.util.logging.Logger;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public interface ClientFactory {
    EventBus getEb();

    ReqFactory getRf();

    PlaceController getPlaceController();

    EmployeeView getEmployeeView();

    Logger getLogger();
}
