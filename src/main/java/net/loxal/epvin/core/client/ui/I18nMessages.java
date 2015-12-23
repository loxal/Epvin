/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;


public interface I18nMessages extends Messages {
    I18nMessages INSTANCE = GWT.create(I18nMessages.class);

    @DefaultMessage("Field {em,<em>}{0}{em,</em>} is {em,<em>}{1}{em,</em>} " +
            "and does not fulfill this condition: {strong,<strong>}{2}{strong,</strong>}")
    String constraintViolation(String propertyPath, String invalidValue, String msg);
}