/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;


public class Layout extends ResizeComposite implements AppShell {
    private static final Binder binder = GWT.create(Binder.class);
	@UiField
	DeckLayoutPanel contentContainer;
	@UiField
	SimpleLayoutPanel pieChartContainer;

//    private AuthInfo authInfo;
//
//    public void setAuthInfo(AuthInfo authInfo) {
//        this.authInfo = authInfo;
//    }
    @UiField
    DockLayoutPanel leftNav;
    @UiField
    Anchor signIn;
    @UiField
    ListBox localeSwitch;
    public Layout() {
        initWidget(binder.createAndBindUi(this));
        localeSwitch();
//        authentication();

        contentContainer.add(new HTML("listView"));
        contentContainer.add(new HTML("readView"));
        contentContainer.add(new HTML("editView"));
        contentContainer.setAnimationDuration(800);
    }

	@Override
	public void setWidget(IsWidget content) {
		contentContainer.setWidget(content);
	}

    void localeSwitch() {
        localeSwitch.setAccessKey('L');
        localeSwitch.setTabIndex(0);
        localeSwitch.setTitle("Choose your language" + " [Access Key: L]");
        localeSwitch.setFocus(true);
        String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName().equals("default") ? "en" : LocaleInfo.getCurrentLocale().getLocaleName();
        String[] localeNames = LocaleInfo.getAvailableLocaleNames();
        localeSwitch.addItem("English", "en");
        for (String localeName : localeNames) {
            if (!localeName.equals("default")) {
                String localeNative = LocaleInfo.getLocaleNativeDisplayName(localeName);
                localeSwitch.addItem(localeNative, localeName);
                if (localeName.equals(currentLocale)) {
                    localeSwitch.setSelectedIndex(localeSwitch.getItemCount() - 1);
                }
            }
        }
    }

    @UiHandler("localeSwitch")
    void onChange(ChangeEvent event) {
        String localeName = localeSwitch.getValue(localeSwitch.getSelectedIndex());
        UrlBuilder builder = Window.Location.createUrlBuilder().setParameter("locale",
                localeName);
        Window.Location.replace(builder.buildString());
    }

	interface Binder extends UiBinder<Widget, Layout> {
    }

//    void authentication() {
//        signIn.setAccessKey('A');
//        signIn.setTitle("[Access Key: A]");
//        signIn.setTabIndex(1);
//        if (authInfo.isLoggedIn()) {
//            signIn.setText(I18nConstants.INSTANCE.signOut() + ": " + authInfo.getEmail());
//            signIn.setHref(authInfo.getLogoutURL());
//            signIn.setTitle("Nickname: " + authInfo.getNickname() + (authInfo.isAdmin() ? " (Admin)" : "") + " [Access Key: A]");
//        } else {
//            signIn.setHref(authInfo.getLoginURL());
//            signIn.setText(I18nConstants.INSTANCE.signIn());
//            signIn.setFocus(true);
//        }
//    }
}
