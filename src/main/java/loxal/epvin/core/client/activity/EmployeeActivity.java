/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.client.StatusBar;
import loxal.epvin.core.client.place.EmployeePlace;
import loxal.epvin.core.client.ui.EmployeeView;
import loxal.epvin.core.client.ui.I18nConstants;
import loxal.epvin.core.client.ui.I18nMessages;
import loxal.epvin.core.event.DoneEvent;
import loxal.epvin.core.shared.EmployeeProxy;
import loxal.epvin.core.shared.EmployeeReqCtx;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeeActivity extends AbstractActivity implements EmployeeView.Presenter {
  // Used to obtain views, eventBus, placeController
  // Alternatively, could be injected via GIN
  private ClientFactory cf;
  private String name;

  public EmployeeActivity(EmployeePlace place, ClientFactory cf) {
    this.cf = cf;
    this.name = place.getToken();
  }

  /**
   * Invoked by the ActivityManager to start a new Activity
   */
  @Override
  public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
    refresh();
    final EmployeeView employeeView = getView();
    employeeView.setName(name);
    employeeView.setPresenter(this);
    containerWidget.setWidget(employeeView.asWidget());
  }

  private EmployeeView getView() {
    return cf.getEmployeeView();
  }

  /**
   * Ask user before stopping this activity
   */
  @Override
  public String mayStop() {
    // return only if .isDirty()
//        return "Are you sure you want to discard these changes?";
    return null;
  }

  @Override
  public void onCancel() {
    cf.getLogger().log(Level.INFO, "onCancel() called");
  }

  @Override
  public void onStop() {
    cf.getLogger().log(Level.INFO, "onStop() called");
  }

  /**
   * Navigate to a new Place in the browser
   */
  @Override
  public void goTo(Place place) {
    cf.getLogger().log(Level.INFO, "goTo(Place place) called");
    cf.getPlaceController().goTo(place);
  }

  @Override
  public void save() { // moved to EmployeeEditorWorkflow, so this can be deleted?
    new StatusBar(
        cf,
        SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.updating()),
        StatusBar.Kind.INFO,
        null);

    final RequestContext reqCtx = getView().getEditorDriver().flush();
    if (getView().getEditorDriver().hasErrors()) {
      new StatusBar(
          cf,
          SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.editorHasErrors()),
          StatusBar.Kind.APP_ERROR,
          null);
    }

    reqCtx.fire(new Receiver<Void>() {
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
            StatusBar.Kind.APP_ERROR,
            I18nConstants.INSTANCE.violations() + ": " + violations.size()
        );
      }

      @Override
      public void onSuccess(Void response) {
        refresh();
        new StatusBar(
            cf,
            SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.employeeUpdated()),
            StatusBar.Kind.SUCCESS,
            null);
        cf.getEb().fireEvent(new DoneEvent());
      }
    });
  }

  @Override
  public void delete(Long id) {
    new StatusBar(
        cf,
        SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.deletingEmployee()),
        StatusBar.Kind.INFO,
        null);

    final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
    reqCtx.delete(id).fire(new Receiver<Void>() {
      @Override
      public void onSuccess(Void response) {
        cf.getEb().fireEvent(new DoneEvent());
        new StatusBar(
            cf,
            SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.employeeDeleted()),
            StatusBar.Kind.SUCCESS,
            null);
        refresh();
      }

      @Override
      public void onFailure(ServerFailure failure) {
        cf.getEb().fireEvent(new DoneEvent());

        new StatusBar(
            cf,
            SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.oopsError()),
            StatusBar.Kind.SERVER_FAILURE,
            failure.getMessage()
        );
      }
    });
  }

  @Override
  public void refresh() {
    new StatusBar(
        cf,
        SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.loading()),
        StatusBar.Kind.INFO,
        null
    );

    final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
    reqCtx.retrieve().fire(new Receiver<List<EmployeeProxy>>() {
      @Override
      public void onSuccess(List<EmployeeProxy> response) {
        getView().getDataList().clear();
        getView().getDataList().addAll(response);

        cf.getEb().fireEvent(new DoneEvent());
      }

      @Override
      public void onFailure(ServerFailure failure) {
        cf.getEb().fireEvent(new DoneEvent());

        new StatusBar(
            cf,
            SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.oopsError()),
            StatusBar.Kind.SERVER_FAILURE,
            failure.getMessage()
        );
      }
    });
  }
}