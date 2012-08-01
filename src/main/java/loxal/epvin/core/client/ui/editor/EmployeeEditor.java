/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.shared.EmployeeProxy;

import java.util.Date;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeeEditor extends Composite implements Editor<EmployeeProxy> {
    static DateBox.Format format;

    interface Binder extends UiBinder<VerticalPanel, EmployeeEditor> {
        static final Binder BINDER = GWT.create(Binder.class);
    }

    @UiField
    TextBox nameFirst;
    @UiField
    TextBox nameLast;
    @UiField
    TextBox mail;
    @UiField
    DateBox birth;

    @UiConstructor
    public EmployeeEditor(ClientFactory cf) {
        initWidget(Binder.BINDER.createAndBindUi(this));
        format = new DateBox.DefaultFormat(cf.getClientResource().defaultDateFormat);

        nameFirst.setAccessKey('1');
        nameLast.setAccessKey('2');
        mail.setAccessKey('3');
        birth.setAccessKey('4');

        {
            @SuppressWarnings("deprecation")
            final Date presetBirth = new Date(84, 5, 15); // preset for convenience reasons
            birth.setFormat(format);
            birth.setValue(presetBirth);
        }
    }
}