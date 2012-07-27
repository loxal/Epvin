/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.auth;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public interface AuthInfoSvcAsync {
    /**
     * Google Authentication Service
     *
     * @param requestUri Request originator
     * @param callback   the callback to return Properties of the authenticated user
     */
    void getAuthInfo(String requestUri, AsyncCallback<AuthInfo> callback);
}