/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;


public interface I18nConstants extends Constants {
    static final I18nConstants INSTANCE = GWT.create(I18nConstants.class);

    @DefaultStringValue("Loading...")
    String loading();

    @DefaultStringValue("Employee deleted.")
    String employeeDeleted();

    @DefaultStringValue("Employee updated.")
    String employeeUpdated();

    @DefaultStringValue("Employee created.")
    String employeeCreated();

    @DefaultStringValue("Oops, something went wrong!")
    String oopsError();

    @DefaultStringValue("Deleting employee...")
    String deletingEmployee();

    @DefaultStringValue("Updating...")
    String updating();

    @DefaultStringValue("Editor has errors.")
    String editorHasErrors();

    @DefaultStringValue("No Employees Available.")
    String noEmployeesAvailable();

    @DefaultStringValue("First name")
    String firstName();

    @DefaultStringValue("Last name")
    String lastName();

    @DefaultStringValue("Mail")
    String mail();

    @DefaultStringValue("Birth date")
    String birthDate();

    @DefaultStringValue("avg. age")
    String avgAge();

    @DefaultStringValue("Creating...")
    String creating();

    @DefaultStringValue("Violations")
    String violations();

    @DefaultStringValue("Sign-in")
    String signIn();

    @DefaultStringValue("Sign out")
    String signOut();
}