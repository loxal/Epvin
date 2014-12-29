/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.auth

import com.google.gwt.user.client.rpc.RemoteService
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath

RemoteServiceRelativePath("AuthInfo")
public trait AuthInfoSvc : RemoteService {
    /**
     * Google Authentication Service
     *
     * @param requestUri Request originator
     * @return Properties of the authenticated user
     */
    public fun getAuthInfo(requestUri: String): AuthInfo
}