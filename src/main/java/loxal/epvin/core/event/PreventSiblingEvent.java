/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class PreventSiblingEvent extends GwtEvent<PreventSiblingEvent.Handler> {
    public static final Type<Handler> TYPE = new Type<Handler>();

    /**
     * Handles {@link loxal.epvin.core.event.PreventSiblingEvent}.
     */
    public interface Handler extends EventHandler {
        void onSiblingExists(PreventSiblingEvent event);
    }

    private Object kind;

    public Object getKind() {
        return kind;
    }

    public PreventSiblingEvent(Object kind) {
        this.kind = kind;
    }

    public PreventSiblingEvent() {
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onSiblingExists(this);
    }
}