/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiField;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import loxal.epvin.core.client.ui.editor.EmployeeEditor;
import loxal.epvin.core.shared.EmployeeProxy;

// A simple demonstration of the overall wiring
public class EmployeeEditorWorkflow {
    // Empty interface declaration, similar to UiBinder
    interface Driver extends RequestFactoryEditorDriver<EmployeeProxy, EmployeeEditor> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);
    @UiField
    EmployeeEditor employeeEditor;

    EmployeeEditorWorkflow() {
    }

    void edit(EmployeeProxy employee) {
        // PersonEditor is a DialogBox that extends Editor<Person>
        EmployeeEditor editor = new EmployeeEditor();
        // Initialize the driver with the top-level editor
        driver.initialize(editor);
        // Copy the data in the object into the UI
//    driver.edit(employee);
        // Put the UI on the screen.
//    editor.center();
    }

    // Called by some UI action
    void save() {
        RequestContext edited = driver.flush();
        if (driver.hasErrors()) {
            // A sub-editor reported errors
        }
    }
}