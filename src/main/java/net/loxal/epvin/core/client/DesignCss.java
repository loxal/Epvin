/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client;

import com.google.gwt.resources.client.CssResource;


public interface DesignCss extends CssResource {
  String error();

  String success();

  String failure();

  String info();

  String reversibleSuccess();
}
