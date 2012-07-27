/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import loxal.common.auth.AuthInfo;
import loxal.common.auth.AuthInfoSvc;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class AuthInfoSvcImpl extends RemoteServiceServlet implements AuthInfoSvc {
    @Override
    public AuthInfo getAuthInfo(final String requestUri) {
        final UserService userService = UserServiceFactory.getUserService();
        final User user = userService.getCurrentUser();
        final AuthInfo auth = new AuthInfo();

        if (user != null) {
            auth.setLoggedIn(true);
            auth.setAdmin(userService.isUserAdmin());
            auth.setEmail(user.getEmail());
            auth.setNickname(user.getNickname());
            auth.setLogoutUrl(userService.createLogoutURL(requestUri));
        } else {
            auth.setLoggedIn(false);
            auth.setLoginURL(userService.createLoginURL(requestUri));
        }
        return auth;
    }
}
