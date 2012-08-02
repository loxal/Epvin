/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.client.StatusBar;
import loxal.epvin.core.client.ui.editor.EmployeeEditor;
import loxal.epvin.core.event.DoneEvent;
import loxal.epvin.core.event.RefreshEvent;
import loxal.epvin.core.shared.EmployeeProxy;
import loxal.epvin.core.shared.EmployeeReqCtx;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class EmployeeEditorWorkflow {
    interface Binder extends UiBinder<DialogBox, EmployeeEditorWorkflow> {
        static final Binder BINDER = GWT.create(Binder.class);
    }

    interface Driver extends RequestFactoryEditorDriver<EmployeeProxy, EmployeeEditor> {
        static final Driver DRIVER = GWT.create(Driver.class);
    }

    @UiField
    EmployeeEditor employeeEditor;
    @UiField
    DialogBox dialog;
    @UiField
    Button ok;
    @UiField
    Button cancel;
    @UiField(provided = true)
    ClientFactory cf;

    private EmployeeProxy employee;

    public void create() { // TODO move this to EmployeeActivity and make usage of EmployeeActivities Cf?
        final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
        employee = reqCtx.create(EmployeeProxy.class);
        edit(reqCtx);
//        // necessary to persist the entity later
//        reqCtx.put(employee);
    }

    private void save() {
        new StatusBar(
                cf,
                SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.updating()),
                StatusBar.Kind.INFO
        );

        final RequestContext driverCtx = Driver.DRIVER.flush();
        if (Driver.DRIVER.hasErrors()) {
            dialog.setText("Invalid input");

            new StatusBar(
                    cf,
                    SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.editorHasErrors()),
                    StatusBar.Kind.APP_ERROR
            );
        }

        driverCtx.fire(new Receiver<Void>() {
            @Override
            public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
                dialog.setText("Errors detected");
                Driver.DRIVER.setConstraintViolations(violations);

                cf.getEb().fireEvent(new DoneEvent());

                final StringBuilder sb = new StringBuilder();
                sb.append("<ol>");
                for (final ConstraintViolation<?> violation : violations) {
                    sb.append("<li>").
                            append(I18nMessages.INSTANCE.constraintViolation(
                                    violation.getPropertyPath().toString(),
                                    (String) violation.getInvalidValue(),
                                    violation.getMessage()
                            )).
                            append("</li>");
                }
                sb.append("<ol>");

                new StatusBar(
                        cf,
                        SafeHtmlUtils.fromSafeConstant(sb.toString()),
                        StatusBar.Kind.APP_ERROR
                ).setTitle(I18nConstants.INSTANCE.violations() + ": " + violations.size());
            }

            @Override
            public void onSuccess(Void response) {
                dialog.hide();

                new StatusBar(
                        cf,
                        SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.employeeUpdated()),
                        StatusBar.Kind.SUCCESS
                );
                cf.getEb().fireEvent(new DoneEvent());
                cf.getEb().fireEvent(new RefreshEvent());
            }
        });
    }

    @UiHandler("ok")
    public void onOk(ClickEvent event) {
        save();
    }

    @UiHandler("cancel")
    public void onCancel(ClickEvent event) {
        dialog.hide();
    }

    EmployeeEditorWorkflow(ClientFactory cf, Boolean isNew) {
        this.cf = cf;
        Binder.BINDER.createAndBindUi(this);
        if (isNew) create();
    }

    EmployeeEditorWorkflow(ClientFactory cf, EmployeeProxy employee) {
        this(cf, false);
        this.employee = employee;
    }

    private void edit(EmployeeReqCtx reqCtx) {
        Driver.DRIVER.initialize(cf.getRf(), employeeEditor);
        Driver.DRIVER.edit(employee, reqCtx);
        dialog.center();

        // necessary to persist the entity later
        reqCtx.put(employee);
    }
}