/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.client.StatusBar;
import loxal.epvin.core.client.ui.editor.EmployeeEditor;
import loxal.epvin.core.event.DoneEvent;
import loxal.epvin.core.event.PreventSiblingEvent;
import loxal.epvin.core.event.RefreshEvent;
import loxal.epvin.core.shared.EmployeeProxy;
import loxal.epvin.core.shared.EmployeeReqCtx;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
  }

  private void save() {
    new StatusBar(
        cf,
        SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.updating()),
        StatusBar.Kind.INFO,
        null);

    final RequestContext driverCtx = Driver.DRIVER.flush();
    if (Driver.DRIVER.hasErrors()) {
      dialog.setText("Invalid input");

      new StatusBar(
          cf,
          SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.editorHasErrors()),
          StatusBar.Kind.APP_ERROR,
          null);
    }

    driverCtx.fire(new Receiver<Void>() {
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        dialog.setText("Errors detected");
        Driver.DRIVER.setConstraintViolations(violations);
        List<EditorError> editorErrors = Driver.DRIVER.getErrors();
        for (EditorError editorError : editorErrors) {
          Logger.getLogger("editorError.getMessage(): ").log(Level.INFO, editorError.getMessage() + "");
        }

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
            StatusBar.Kind.APP_ERROR,
            I18nConstants.INSTANCE.violations() + ": " + violations.size()
        );
      }

      @Override
      public void onSuccess(Void response) {
        dialog.hide();

        cf.getEb().fireEvent(new RefreshEvent());
        cf.getEb().fireEvent(new DoneEvent());
        new StatusBar(
            cf,
            SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.employeeUpdated()),
            StatusBar.Kind.SUCCESS,
            null);
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

  private void fetchAndEdit() {
    Request<EmployeeProxy> fetchRequest = (Request<EmployeeProxy>) cf.getRf().find(employee.stableId());

    fetchRequest.with(Driver.DRIVER.getPaths());

    fetchRequest.to(new Receiver<EntityProxy>() {
      @Override
      public void onSuccess(EntityProxy person) {
        EmployeeReqCtx context = cf.getRf().employeeReqCtx();
        edit(context);
        context.put(employee);
      }
    }).fire();
  }

  private void preventSibling() {
    cf.getEb().fireEvent(new PreventSiblingEvent(this));
    cf.getEb().addHandler(PreventSiblingEvent.TYPE, new PreventSiblingEvent.Handler() {
      @Override
      public void onSiblingExists(PreventSiblingEvent event) {
        if (event.getKind() instanceof EmployeeEditorWorkflow) {
          dialog.hide();
        }
      }
    });
  }

  private void assignShortcuts() {
    dialog.addDomHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        switch (event.getNativeKeyCode()) {
          case KeyCodes.KEY_ESCAPE:
            onCancel(null);
            break;
          case KeyCodes.KEY_ENTER:
            onOk(null);
            break;
        }
      }
    }, KeyUpEvent.getType());
  }

  EmployeeEditorWorkflow(ClientFactory cf, Boolean isNew) {
    this.cf = cf;
    Binder.BINDER.createAndBindUi(this);
    assignShortcuts();
    if (isNew) create();
  }

  EmployeeEditorWorkflow(ClientFactory cf, EmployeeProxy employee) {
    this(cf, false);
    this.employee = employee;

    edit(null);
  }

  private void show() {
    dialog.center();
    preventSibling();
    employeeEditor.focus();
    if (employee.getBirth() == null) {
      employeeEditor.setDefaults();
    }
  }

  void edit(EmployeeReqCtx reqCtx) {
    Driver.DRIVER.initialize(cf.getRf(), employeeEditor);

    if (reqCtx == null) {
      fetchAndEdit();
      return;
    }

    Driver.DRIVER.edit(employee, reqCtx);
    show();

    reqCtx.put(employee);
  }
}