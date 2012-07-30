/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import loxal.epvin.core.client.ClientResource;
import loxal.epvin.core.shared.EmployeeProxy;

import java.util.Date;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeeEditor extends Composite implements Editor<EmployeeProxy> {
    // NTH move ClientResource.INSTANCE.factory().create() to CF
    private static final DateBox.Format format = new DateBox.DefaultFormat(ClientResource.INSTANCE.factory().create().defaultDateFormat);

    interface Binder extends UiBinder<Widget, EmployeeEditor> {
    }

    private static final Binder binder = GWT.create(Binder.class);

    @UiField
    TextBox nameFirst;
    @UiField
    TextBox nameLast;
    @UiField
    TextBox mail;
    @UiField
    DateBox birth;

    public EmployeeEditor() {
        initWidget(binder.createAndBindUi(this));

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