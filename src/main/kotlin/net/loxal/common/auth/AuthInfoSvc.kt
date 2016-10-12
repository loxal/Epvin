/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.common.auth

import com.google.gwt.user.client.rpc.RemoteService
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath

@RemoteServiceRelativePath("AuthInfo") interface AuthInfoSvc : RemoteService {
    /**
     * Google Authentication Service
     *
     * @param requestUri Request originator
     * @return Properties of the authenticated user
     */
    fun getAuthInfo(requestUri: String): AuthInfo
}