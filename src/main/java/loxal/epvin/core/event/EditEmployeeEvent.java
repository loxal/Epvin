/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import loxal.epvin.core.shared.EmployeeProxy;

/**
 * Begin editing a person.
 * <p/>
 * TODO: Make this an Activity.
 */
public class EditEmployeeEvent extends GwtEvent<EditEmployeeEvent.Handler> {
    public static final Type<Handler> TYPE = new Type<Handler>();

    /**
     * Handles {@link loxal.epvin.core.event.EditEmployeeEvent}.
     */
    public interface Handler extends EventHandler {
        void startEdit(EmployeeProxy person, RequestContext requestContext);
    }

    private final EmployeeProxy person;
    private final RequestContext request;

    public EditEmployeeEvent(EmployeeProxy person) {
        this(person, null);
    }

    public EditEmployeeEvent(EmployeeProxy person, RequestContext request) {
        this.person = person;
        this.request = request;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.startEdit(person, request);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }
}
