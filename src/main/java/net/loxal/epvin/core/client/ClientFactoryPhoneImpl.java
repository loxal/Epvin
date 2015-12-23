/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client;

import com.google.gwt.user.client.Window;


public class ClientFactoryPhoneImpl extends ClientFactoryImpl {
  ClientFactoryPhoneImpl() {
    super();
    ClientResource.INSTANCE.main().ensureInjected();
    Window.setTitle("PHONE Employee Import | " + super.getClientResource().companyDesignator);
  }
}
