/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.EventBus;
import loxal.epvin.core.event.DoneEvent;
import loxal.epvin.core.event.PreventSiblingEvent;


/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class StatusBar extends Composite {
    private final Kind msgKind;

    private static final Binder binder = GWT.create(Binder.class);

    interface Binder extends UiBinder<DecoratedPopupPanel, StatusBar> {
    }

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

    public enum Kind {
        INFO, SUCCESS, APP_ERROR, USAGE_INFO, SERVER_FAILURE,
    }

    private void addAutoDisappearing() {
        final int toDisplayDuration = 3000; // 3s
        new Timer() {
            public void run() {
                removeFromParent();
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
                        removeFromParent();
                    }
                }
            }
        });
    }

    public StatusBar(ClientFactory cf, SafeHtml statusMsg, Kind msgKind) {
        this.msgKind = msgKind;
        this.eb = cf.getEb();
        initWidget(binder.createAndBindUi(this));
        preventSibling();
        RootPanel.get().add(this);

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
                        removeFromParent();
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
        }
    }

    @UiHandler(value = "close")
    void onClose(ClickEvent event) {
        removeFromParent();
    }
}
