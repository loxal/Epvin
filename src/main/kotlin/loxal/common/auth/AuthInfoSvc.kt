/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.common.auth

RemoteServiceRelativePath("AuthInfo")
public interface AuthInfoSvc : RemoteService {
    /**
     * Google Authentication Service
     *
     * @param requestUri Request originator
     * @return Properties of the authenticated user
     */
    public fun getAuthInfo(requestUri: String): AuthInfo
}