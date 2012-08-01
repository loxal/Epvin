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
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.client.StatusBar;
import loxal.epvin.core.client.ui.editor.EmployeeEditor;
import loxal.epvin.core.event.DoneEvent;
import loxal.epvin.core.event.EditEmployeeEvent;
import loxal.epvin.core.shared.EmployeeProxy;
import loxal.epvin.core.shared.EmployeeReqCtx;

import javax.validation.ConstraintViolation;
import java.util.Set;

// A simple demonstration of the overall wiring
public class EmployeeEditorWorkflow {
    interface Binder extends UiBinder<DialogBox, EmployeeEditorWorkflow> {
        Binder BINDER = GWT.create(Binder.class);
    }

    // Empty interface declaration, similar to UiBinder
    interface Driver extends RequestFactoryEditorDriver<EmployeeProxy, EmployeeEditor> {
    }

    //    private static final Driver driver = GWT.create(Driver.class);
    private Driver driver;

    @UiField
    EmployeeEditor employeeEditor;
    @UiField
    DialogBox dialog;
    @UiField
    Button ok;
    @UiField
    Button cancel;
    @UiField
    ClientFactory cf;

    private EmployeeProxy employee;

    public void create() {
        final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
        employee = reqCtx.edit(reqCtx.create(EmployeeProxy.class));
        edit(reqCtx);
        reqCtx.put(employee); // necessary to persist the entity later
    }

    private void save() {
        final RequestContext driverCtx = driver.flush();
        if (driver.hasErrors()) {
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
                driver.setConstraintViolations(violations);

//                cf.getEb().fireEvent(new DoneEvent());
//
//                final StringBuilder sb = new StringBuilder();
//                sb.append("<ol>");
//                for (final ConstraintViolation<?> violation : violations) {
//                    sb.append("<li>").
//                            append(I18nMessages.INSTANCE.constraintViolation(
//                                    violation.getPropertyPath().toString(),
//                                    (String) violation.getInvalidValue(),
//                                    violation.getMessage()
//                            )).
//                            append("</li>");
//                }
//                sb.append("<ol>");
//
//                new StatusBar(
//                        cf,
//                        SafeHtmlUtils.fromSafeConstant(sb.toString()),
//                        StatusBar.Kind.APP_ERROR
//                ).setTitle(I18nConstants.INSTANCE.violations() + ": " + violations.size());
            }

            @Override
            public void onSuccess(Void response) {
//                refresh();
                new StatusBar(
                        cf,
                        SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.employeeUpdated()),
                        StatusBar.Kind.SUCCESS
                );
                cf.getEb().fireEvent(new DoneEvent());
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

    static void register(EventBus eb, final ClientFactory cf) {
        eb.addHandler(EditEmployeeEvent.TYPE, new EditEmployeeEvent.Handler() {
            public void startEdit(EmployeeProxy employee, RequestContext requestContext) {
//                new EmployeeEditorWorkflow(employee, cf).edit(requestContext);
            }
        });
    }

    public static void register1(final ClientFactory cf) {
        cf.getEb().addHandler(EditEmployeeEvent.TYPE, new EditEmployeeEvent.Handler() {
            public void startEdit(EmployeeProxy employee, RequestContext requestContext) {
                new EmployeeEditorWorkflow(cf);
            }
        });
    }

//    EmployeeEditorWorkflow() {
//        Binder.BINDER.createAndBindUi(this);
//
//        dialog.center();
//        create();
//    }

//    EmployeeEditorWorkflow(EmployeeProxy employee, ClientFactory cf) {
//        this.employee = employee;
//        this.cf = cf;
//    }

    EmployeeEditorWorkflow(ClientFactory cf) {
        Binder.BINDER.createAndBindUi(this);
        this.cf = cf;

        dialog.center();
        create();
    }

    private void edit(RequestContext requestContext) {
        driver = GWT.create(Driver.class);
        driver.initialize(cf.getRf(), employeeEditor);

        driver.edit(employee, requestContext);
    }
}