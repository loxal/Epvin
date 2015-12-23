/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;


public class AutoDisappearanceEvent extends GwtEvent<AutoDisappearanceEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onDisappear();
  }

	public interface Handler extends EventHandler {
		void onDisappear();
  }
}