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

    private static final Driver driver = GWT.create(Driver.class);

    @UiField
    EmployeeEditor employeeEditor;
    @UiField
    DialogBox dialog;
    @UiField
    Button ok;
    @UiField
    Button cancel;

    private ClientFactory cf;
    private EmployeeProxy employee;


    // Called by some UI action
    private void save() {
        RequestContext edited = driver.flush();
        if (driver.hasErrors()) {
            // A sub-editor reported errors
        }
    }

    @UiHandler("ok")
    public void onOk(ClickEvent event) {
        driver.initialize(cf.getRf(), employeeEditor);

        final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
        final EmployeeProxy entity = reqCtx.edit(reqCtx.create(EmployeeProxy.class));
//        entity.setNameFirst("Alex");
//        entity.setNameLast("Orlov");
        reqCtx.put(entity); // necessary to persist the entity later
        driver.edit(entity, reqCtx);

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

    @UiHandler("cancel")
    public void onCancel(ClickEvent event) {
        dialog.hide();
    }

    static void register(EventBus eb, final ClientFactory cf) {
        eb.addHandler(EditEmployeeEvent.TYPE, new EditEmployeeEvent.Handler() {
            public void startEdit(EmployeeProxy employee, RequestContext requestContext) {
                new EmployeeEditorWorkflow(employee, cf).edit(requestContext);
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

    EmployeeEditorWorkflow() {
        Binder.BINDER.createAndBindUi(this);

        dialog.center();
    }

    EmployeeEditorWorkflow(EmployeeProxy employee, ClientFactory cf) {
        this.employee = employee;
        this.cf = cf;
    }

    EmployeeEditorWorkflow(ClientFactory cf) {
        this.cf = cf;
        Binder.BINDER.createAndBindUi(this);
//        driver.initialize(employeeEditor);

        dialog.center();
    }

    void edit(EmployeeProxy employee) {
        // PersonEditor is a DialogBox that extends Editor<Person>
        EmployeeEditor editor = new EmployeeEditor();
        // Initialize the driver with the top-level editor
        driver.initialize(editor);


        // Copy the data in the object into the UI
//    driver.edit(employee);
        // Put the UI on the screen.
//    editor.center();
    }

    private void edit(RequestContext requestContext) {
//        driver = GWT.create(Driver.class);
        driver.initialize(cf.getRf(), employeeEditor);

        if (requestContext == null) {
            return;
        }

        driver.edit(employee, requestContext);
//        employeeEditor.show();
        dialog.center();
    }

    //    @Override
    public RequestFactoryEditorDriver<EmployeeProxy, EmployeeEditor> getEditorDriver() {
        return driver;
    }
}