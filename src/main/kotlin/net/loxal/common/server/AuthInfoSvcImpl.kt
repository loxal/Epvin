/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.common.server

import com.google.appengine.api.users.UserServiceFactory
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import net.loxal.common.auth.AuthInfo
import net.loxal.common.auth.AuthInfoSvc

public class AuthInfoSvcImpl : RemoteServiceServlet(), AuthInfoSvc {
    override fun getAuthInfo(requestUri: String): AuthInfo {
        val userService = UserServiceFactory.getUserService()
        val user = userService.currentUser
        val auth = AuthInfo()

        if (user == null) {
            auth.loggedIn = false
            auth.loginUrl = userService.createLoginURL(requestUri)
        } else {
            auth.loggedIn = true
            auth.admin = userService.isUserAdmin
            auth.email = user.email
            auth.nickname = user.nickname
            auth.logoutUrl = userService.createLogoutURL(requestUri)
        }
        return auth
    }
}
