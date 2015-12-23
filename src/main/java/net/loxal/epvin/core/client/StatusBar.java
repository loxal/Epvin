/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.ResettableEventBus;
import net.loxal.epvin.core.event.AutoDisappearanceEvent;
import net.loxal.epvin.core.event.DeleteEvent;
import net.loxal.epvin.core.event.DoneEvent;
import net.loxal.epvin.core.event.PreventSiblingEvent;

import java.util.logging.Level;
import java.util.logging.Logger;



// NTH make this a widget in the Layout.ui that gets its contents via NotificationEvent
public class StatusBar {
    private final Kind msgKind;
    @UiField
    DecoratedPopupPanel container;
    @UiField
    HTML msg;
    @UiField
    Button close;
    @UiField(provided = true)
    EventBus eb;
    @UiField
    Label kind;
    @UiField
    Button undo;
    Integer toDisplayDuration = 3000; // 3s
	ResettableEventBus reb;
	private DeleteEvent deleteEvent;

    public StatusBar(ClientFactory cf, SafeHtml statusMsg, Kind msgKind, String title) {
        this.msgKind = msgKind;
        this.eb = cf.getEb();
        Binder.BINDER.createAndBindUi(this);
        container.setTitle(title);
        show();

        msg.setHTML(statusMsg);
        switch (msgKind) {
            case SUCCESS:
                container.addStyleName(ClientResource.INSTANCE.design().success());
                kind.addStyleName("icon-ok-sign");
                addAutoDisappearing();
                break;
            case APP_ERROR:
                container.addStyleName(ClientResource.INSTANCE.design().error());
                kind.addStyleName("icon-warning-sign");
                break;
            case INFO:
                eb.addHandler(DoneEvent.TYPE, new DoneEvent.Handler() {
                    @Override
                    public void onDone() {
                        container.hide();
                    }
                });
                container.addStyleName(ClientResource.INSTANCE.design().info());
                kind.addStyleName("icon-info-sign");
                break;
            case USAGE_INFO:
                container.addStyleName(ClientResource.INSTANCE.design().info());
                kind.addStyleName("icon-question-sign");
                break;
            case SERVER_FAILURE:
                container.addStyleName(ClientResource.INSTANCE.design().failure());
                kind.addStyleName("icon-remove-sign");
                break;
            case REVERSIBLE_SUCCESS:
                this.toDisplayDuration = 2000; // 2s
                undo.setVisible(true);
                close.setVisible(false);
                container.addStyleName(ClientResource.INSTANCE.design().reversibleSuccess());
                kind.addStyleName("icon-info-sign");
                addAutoDisappearing();
                break;
        }
    }

    public StatusBar(ClientFactory cf, SafeHtml statusMsg, Kind msgKind, final DeleteEvent deleteEvent, String title) {
        this(cf, statusMsg, msgKind, title);
        this.deleteEvent = deleteEvent;

        reb = new ResettableEventBus(eb);
        reb.addHandler(AutoDisappearanceEvent.TYPE, new AutoDisappearanceEvent.Handler() {
		@Override
		public void onDisappear() {
			eb.fireEvent(deleteEvent);
		}
	});
    }

	private void addAutoDisappearing() {
		new Timer() {
			public void run() {
				container.hide();
				// using deleteEvent doesn't work because it's !=null after first successful deletion
				Logger.getLogger("deleteEvent: ").log(Level.INFO, deleteEvent + "");
				if (deleteEvent != null) {
					reb.fireEvent(new AutoDisappearanceEvent());
				}
			}
		}.schedule(toDisplayDuration);
	}

	// show "processing" status only when it takes a certain time for the process to finish
	private void showWithDelay() {
		new Timer() {
			@Override
			public void run() {
				// display widget
			}
		}.schedule(500); // 0.5s
	}

	private void preventSibling() {
		eb.fireEvent(new PreventSiblingEvent(msgKind));
		eb.addHandler(PreventSiblingEvent.TYPE, new PreventSiblingEvent.Handler() {
			@Override
			public void onSiblingExists(PreventSiblingEvent event) {
				if (event.getKind() instanceof Kind) {
					if (event.getKind().equals(msgKind)) {
						container.hide();
					} else if (msgKind.equals(Kind.APP_ERROR) && event.getKind().equals(Kind.SUCCESS)) {
						container.hide();
					}
				}
			}
		});
	}

	private void show() {
		container.center();
		preventSibling();
	}

    @UiHandler(value = "close")
    void onClose(ClickEvent event) {
        container.hide();
    }

    @UiHandler(value = "undo")
    void onUndo(ClickEvent event) {
        reb.removeHandlers();
        onClose(event);
    }

	public enum Kind {
		INFO, SUCCESS, APP_ERROR, USAGE_INFO, SERVER_FAILURE, REVERSIBLE_SUCCESS,
	}

	interface Binder extends UiBinder<DecoratedPopupPanel, StatusBar> {
		Binder BINDER = GWT.create(Binder.class);
	}
}
