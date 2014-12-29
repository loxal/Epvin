/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.client.activity.EmployeeActivity;
import loxal.epvin.core.client.place.EmployeePlace;


public class AppActivityMapper implements ActivityMapper {

    private ClientFactory cf;

    /**
     * DemoActivityMapper associates each Place with its corresponding
     * {@link com.google.gwt.activity.shared.Activity}
     *
     * @param cf Factory to be passed to activities
     */
    public AppActivityMapper(ClientFactory cf) {
        this.cf = cf;
    }

    /**
     * Map each Place to its corresponding Activity. This would be a great use
     * for GIN.
     */
    @Override
    public Activity getActivity(Place place) {
        // This is begging for GIN
        if (place instanceof EmployeePlace)
            return new EmployeeActivity((EmployeePlace) place, cf);

        return null;
    }

}
