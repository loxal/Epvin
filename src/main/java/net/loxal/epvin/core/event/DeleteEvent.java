/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;


public class DeleteEvent extends GwtEvent<DeleteEvent.Handler> {
	public static final Type<Handler> TYPE = new Type<>();
	private final Long id;

	public DeleteEvent(Long id) {
		this.id = id;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onDelete(id);
	}

	public interface Handler extends EventHandler {
		void onDelete(Long id);
	}
}