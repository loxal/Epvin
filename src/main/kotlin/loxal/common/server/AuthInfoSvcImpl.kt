/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.server

import com.google.appengine.api.users.User
import com.google.appengine.api.users.UserService
import com.google.appengine.api.users.UserServiceFactory
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import loxal.common.auth.AuthInfo
import loxal.common.auth.AuthInfoSvc

public class AuthInfoSvcImpl : RemoteServiceServlet(), AuthInfoSvc {
    override fun getAuthInfo(requestUri: String): AuthInfo {
        val userService = UserServiceFactory.getUserService()
        val user = userService.getCurrentUser()
        val auth = AuthInfo()

        if (user == null) {
            auth.loggedIn = false
            auth.loginUrl = userService.createLoginURL(requestUri)
        } else {
            auth.loggedIn = true
            auth.admin = userService.isUserAdmin()
            auth.email = user.getEmail()
            auth.nickname = user.getNickname()
            auth.logoutUrl = userService.createLogoutURL(requestUri)
        }
        return auth
    }
}
