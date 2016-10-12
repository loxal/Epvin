/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.common.auth

import com.google.gwt.user.client.rpc.AsyncCallback

interface AuthInfoSvcAsync {
    /**
     * Google Authentication Service
     *
     * @param requestUri Request originator
     * @param callback   the callback to return Properties of the authenticated user
     */
    fun getAuthInfo(requestUri: String, callback: AsyncCallback<AuthInfo>)
}