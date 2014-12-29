/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.auth;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthInfo implements IsSerializable {

    private boolean loggedIn;
    private boolean admin;
    private String loginUrl;
    private String logoutUrl;
    private String email;
    private String nickname;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getLoginURL() {
        return loginUrl;
    }

    public void setLoginURL(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutURL() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}