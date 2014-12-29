/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client;

import com.google.gwt.i18n.client.DateTimeFormat;


public class Property {
    public final String companyDesignator = "Loxal";
    public final DateTimeFormat defaultDateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);
}
