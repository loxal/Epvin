/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.shared.EmployeeProxy;

import java.util.Date;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeeEditor extends Composite implements Editor<EmployeeProxy> {
  static DateBox.Format format;

  interface Binder extends UiBinder<VerticalPanel, EmployeeEditor> {
    static final Binder BINDER = GWT.create(Binder.class);
  }

  @UiField
  ValueBoxEditorDecorator<String> nameFirst;
  @UiField
  ValueBoxEditorDecorator<String> nameLast;
  @UiField
  ValueBoxEditorDecorator<String> mail;
  @UiField
  DateBox birth;

  public void focus() {
//    nameFirst.setFocus(true);
  }

  public void setDefaults() {
    @SuppressWarnings("deprecation")
    final Date presetBirth = new Date(84, 5, 15); // preset default for convenience reasons
    birth.setValue(presetBirth);
  }

  @UiConstructor
  public EmployeeEditor(ClientFactory cf) {
    initWidget(Binder.BINDER.createAndBindUi(this));
    format = new DateBox.DefaultFormat(cf.getClientResource().defaultDateFormat);
    birth.setFormat(format);

//    nameFirst.setAccessKey('1');
//    nameLast.setAccessKey('2');
//    mail.setAccessKey('3');
    birth.setAccessKey('4');
  }
}