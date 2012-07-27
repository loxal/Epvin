/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeePlace extends Place {
    private String token;

    public EmployeePlace(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Prefix("EmployeeA")
    public static class Tokenizer implements PlaceTokenizer<EmployeePlace> {
        @Override
        public String getToken(EmployeePlace place) {
            return place.getToken();
        }

        @Override
        public EmployeePlace getPlace(String token) {
            return new EmployeePlace(token);
        }
    }
}
