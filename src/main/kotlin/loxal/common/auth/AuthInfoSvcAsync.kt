/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.auth

import com.google.gwt.user.client.rpc.AsyncCallback

public interface AuthInfoSvcAsync {
    /**
     * Google Authentication Service
     *
     * @param requestUri Request originator
     * @param callback   the callback to return Properties of the authenticated user
     */
    public fun getAuthInfo(requestUri: String, callback: AsyncCallback<AuthInfo>)
}