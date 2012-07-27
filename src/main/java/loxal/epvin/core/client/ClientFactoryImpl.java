/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import loxal.epvin.core.client.mvp.AppActivityMapper;
import loxal.epvin.core.client.mvp.AppPlaceHistoryMapper;
import loxal.epvin.core.client.place.EmployeePlace;
import loxal.epvin.core.client.ui.EmployeeView;
import loxal.epvin.core.client.ui.EmployeeViewImpl;
import loxal.epvin.core.shared.ReqFactory;

import java.util.logging.Logger;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class ClientFactoryImpl implements ClientFactory {
    private final EventBus eb = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eb);
    private final EmployeeView employeeView = new EmployeeViewImpl();
    private final ReqFactory rf = GWT.create(ReqFactory.class);
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public EventBus getEb() {
        return eb;
    }

    @Override
    public ReqFactory getRF() {
        return rf;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    ClientFactoryImpl() {
        final Layout shell = new Layout();

        ClientResource.INSTANCE.design().ensureInjected();
        rf.initialize(eb);
        final ActivityMapper activityMapper = new AppActivityMapper(this);
        final ActivityManager activityManager = new ActivityManager(activityMapper, eb);
        activityManager.setDisplay(shell);
        RootLayoutPanel.get().add(shell);

        final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(historyMapper);
        final Place defaultPlace = new EmployeePlace("Alexander");
        placeHistoryHandler.register(placeController, eb, defaultPlace);

        employeeView.setCf(this);

        // Goes to the place represented on URL or default place
        placeHistoryHandler.handleCurrentHistory();

        Window.setTitle("Employee Import | " + ClientResource.INSTANCE.factory().create().companyDesignator);
    }
}
