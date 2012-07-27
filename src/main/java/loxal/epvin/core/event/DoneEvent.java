/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class DoneEvent extends GwtEvent<DoneEvent.Handler> {
    public static final Type<Handler> TYPE = new Type<Handler>();

    /**
     * Handles {@link loxal.epvin.core.event.DoneEvent}
     */
    public interface Handler extends EventHandler {
        void onDone();
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onDone();
    }
}