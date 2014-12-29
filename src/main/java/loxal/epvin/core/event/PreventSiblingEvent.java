/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.event;


import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;


public class PreventSiblingEvent extends GwtEvent<PreventSiblingEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();
	private Object kind;

	public PreventSiblingEvent(Object kind) {
		this.kind = kind;
  }

  public Object getKind() {
    return kind;
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onSiblingExists(this);
  }

	public interface Handler extends EventHandler {
		void onSiblingExists(PreventSiblingEvent event);
  }
}