/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.core.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import net.loxal.epvin.core.client.ui.editor.EmployeeEditor;
import net.loxal.epvin.core.shared.EmployeeProxy;

import java.util.List;


public interface EmployeeView extends IsWidget {
    void setName(String name);

    List<EmployeeProxy> getDataList();

    void setPresenter(Presenter presenter);

    RequestFactoryEditorDriver<EmployeeProxy, EmployeeEditor> getEditorDriver();

    interface Presenter {
        void goTo(Place place);

        void delete(Long id);

        void save();

        void refresh();
    }
}