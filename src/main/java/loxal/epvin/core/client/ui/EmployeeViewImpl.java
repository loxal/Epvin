/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.core.client.ui;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import loxal.epvin.core.client.ClientFactory;
import loxal.epvin.core.client.ClientResource;
import loxal.epvin.core.client.ui.editor.EmployeeEditor;
import loxal.epvin.core.event.RefreshEvent;
import loxal.epvin.core.shared.EmployeeProxy;
import loxal.epvin.core.shared.EmployeeReqCtx;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeeViewImpl extends Composite implements EmployeeView {
  interface Binder extends UiBinder<ScrollPanel, EmployeeViewImpl> {
    static final Binder BINDER = GWT.create(Binder.class);
  }

  private final SingleSelectionModel<EmployeeProxy> selection = new SingleSelectionModel<EmployeeProxy>();

  private void edit(EmployeeProxy employee) {  // TODO move this to EmployeeActivity and make usage of EmployeeActivities Cf
    final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
    reqCtx.put(employee); // necessary to persist the entity later
//        driver.edit(employee, reqCtx);
  }

  private void create() { // TODO move this to EmployeeActivity and make usage of EmployeeActivities Cf
    final EmployeeReqCtx reqCtx = cf.getRf().employeeReqCtx();
    final EmployeeProxy entity = reqCtx.create(EmployeeProxy.class);
    reqCtx.put(entity); // necessary to persist the entity later
//        driver.edit(entity, reqCtx);
  }

  @UiField
  CellTable<EmployeeProxy> employeeTable;
  @UiField(provided = true)
  final ListDataProvider<EmployeeProxy> dataProvider = new ListDataProvider<EmployeeProxy>();
  @UiField(provided = true)
  final List<EmployeeProxy> dataList = dataProvider.getList();
  @UiField
  SimplePager pager;
  @UiField
  PageSizePager pageSizer;
  @UiField
  Button add;

  private Presenter presenter;
  private String name;

  @UiHandler("add")
  void onAdd(ClickEvent event) {
    new EmployeeEditorWorkflow(cf, true);
  }

  private ClientFactory cf;

  public EmployeeViewImpl(ClientFactory cf) {
    this.cf = cf;
    initWidget(Binder.BINDER.createAndBindUi(this));
    attachHandler();
    init();
  }

  private void attachHandler() {
    cf.getEb().addHandler(RefreshEvent.TYPE, new RefreshEvent.Handler() {
      @Override
      public void onDone() {
        presenter.refresh();
      }
    });

    selection.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        EmployeeProxy employee = selection.getSelectedObject();
        if (employee == null) return;
        new EmployeeEditorWorkflow(cf, employee);
      }
    });
  }

  private void init() {
    dataProvider.addDataDisplay(employeeTable);
    pager.setDisplay(employeeTable);
    pageSizer.setDisplay(employeeTable);

    employeeTable.setEmptyTableWidget(new HTML(SafeHtmlUtils.fromSafeConstant("<span class='icon-ban-circle'/> " + I18nConstants.INSTANCE.noEmployeesAvailable())));
    employeeTable.setSelectionModel(selection);

    final Column<EmployeeProxy, String> nameFirst = new Column<EmployeeProxy, String>(new ClickableTextCell()) {
      @Override
      public String getValue(EmployeeProxy object) {
        return object.getNameFirst();
      }
    };
    final SafeHtmlHeader nameFirstHeader = new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("<span class='icon-tag'> " + I18nConstants.INSTANCE.firstName()));
    employeeTable.addColumn(nameFirst, nameFirstHeader, nameFirstHeader);

    final Column<EmployeeProxy, String> nameLast = new Column<EmployeeProxy, String>(new ClickableTextCell()) {
      @Override
      public String getValue(EmployeeProxy object) {
        return object.getNameLast();
      }
    };
    final SafeHtmlHeader nameLastHeader = new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("<span class='icon-tag'> " + I18nConstants.INSTANCE.lastName()));
    employeeTable.addColumn(nameLast, nameLastHeader, nameLastHeader);

    final Column<EmployeeProxy, String> mail = new Column<EmployeeProxy, String>(new ClickableTextCell()) {
      @Override
      public String getValue(EmployeeProxy object) {
        return object.getMail();
      }
    };
    final SafeHtmlHeader mailCol = new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("<span class='icon-envelope'/> " + I18nConstants.INSTANCE.mail()));
    employeeTable.addColumn(mail, mailCol, mailCol);

    final Header<SafeHtml> birthColHeader = new Header<SafeHtml>(new SafeHtmlCell()) {
      @Override
      public SafeHtml getValue() {
        return SafeHtmlUtils.fromSafeConstant("<span class='icon-calendar'/> " + I18nConstants.INSTANCE.birthDate());
      }
    };
    final Header<SafeHtml> avgAgeFooter = new Header<SafeHtml>(new SafeHtmlCell()) {
      @SuppressWarnings("deprecation")
      @Override
      public SafeHtml getValue() {
        final List<EmployeeProxy> visibleEmployees = employeeTable.getVisibleItems();
        if (visibleEmployees.size() == 0) {
          return SafeHtmlUtils.EMPTY_SAFE_HTML;
        } else {
          long totalAge = 0;
          int ageIdx = 0;
          for (EmployeeProxy employee : visibleEmployees) {
            if (employee.getBirth() != null) {
              ageIdx++;
              totalAge += employee.getBirth().getTime();
            }
          }

          int avgAge = 0;
          if (ageIdx != 0) {
            final Date avgBirthYear = new Date(totalAge / ageIdx);
            avgAge = new Date().getYear() - avgBirthYear.getYear();
          }
          return SafeHtmlUtils.fromSafeConstant(I18nConstants.INSTANCE.avgAge() + ": " + avgAge);
        }
      }
    };
    // NTH move ClientResource.INSTANCE.factory().create() to CF
    final Column<EmployeeProxy, Date> birth = new Column<EmployeeProxy, Date>(new DateCell(ClientResource.INSTANCE.factory().create().defaultDateFormat)) {
      @Override
      public Date getValue(EmployeeProxy object) {
        return object.getBirth();
      }
    };
    employeeTable.addColumn(birth, birthColHeader, avgAgeFooter);

    ButtonCell previewButton = new ButtonCell();


    Column<EmployeeProxy, String> delete = new Column<EmployeeProxy, String>(previewButton) {
      public String getValue(EmployeeProxy object) {
//        DOM.eventCancelBubble(Event.getCurrentEvent(), true);
//        DOM.eventCancelBubble(DOM.eventGetCurrentEvent(), true);
        return "Preview";
      }
    };
    delete.setFieldUpdater(new FieldUpdater<EmployeeProxy, String>() {
      @Override
      public void update(int index, EmployeeProxy object, String value) {
        DOM.eventCancelBubble(Event.getCurrentEvent(), false);
//        DOM.eventCancelBubble(DOM.eventGetCurrentEvent(), true);

        // The user clicked on the button for the passed auction.
        Logger.getLogger("\"Yes!\": ").log(Level.INFO, "Yes!" + "");
      }
    });

//        final ActionCell.Delegate<Long> deleteDelegate = new ActionCell.Delegate<Long>() {
//          @Override
//          public void execute(Long object) {
//    //        presenter.delete(object);
//          }
//        };
//    final Column<EmployeeProxy, Long> delete = new Column<EmployeeProxy, Long>(new ActionCell<Long>(SafeHtmlUtils.fromSafeConstant("<span class='icon-remove'/>"), deleteDelegate)) {
//      @Override
//      public Long getValue(EmployeeProxy object) {
//        return object.getId();
//      }
//    };

    employeeTable.addColumn(delete);

    final ActionCell.Delegate<EmployeeProxy> editDelegate = new ActionCell.Delegate<EmployeeProxy>() {
      @Override
      public void execute(EmployeeProxy object) {
        new EmployeeEditorWorkflow(cf, object);
      }
    };

    final SafeHtml editCol = SafeHtmlUtils.fromSafeConstant("<span class='icon-edit'/>");
    final Column<EmployeeProxy, EmployeeProxy> edit = new Column<EmployeeProxy, EmployeeProxy>(new ActionCell<EmployeeProxy>(editCol, editDelegate)) {
      @Override
      public EmployeeProxy getValue(EmployeeProxy object) {
        return object;
      }
    };
    employeeTable.addColumn(edit);

    final ColumnSortEvent.ListHandler<EmployeeProxy> sortFirstNameHandler = new ColumnSortEvent.ListHandler<EmployeeProxy>(dataProvider.getList());
    sortFirstNameHandler.setComparator(nameFirst, new Comparator<EmployeeProxy>() {
      @Override
      public int compare(EmployeeProxy employeeProxy, EmployeeProxy employeeProxy1) {
        if (employeeProxy.getNameFirst() != null) {
          return (employeeProxy1.getNameFirst() != null) ? employeeProxy.getNameFirst().compareTo(employeeProxy1.getNameFirst()) : 1;
        }
        return -1;
      }
    });
    employeeTable.addColumnSortHandler(sortFirstNameHandler);
    nameFirst.setSortable(true);

    final ColumnSortEvent.ListHandler<EmployeeProxy> sortLastNameHandler = new ColumnSortEvent.ListHandler<EmployeeProxy>(dataProvider.getList());
    sortLastNameHandler.setComparator(nameLast, new Comparator<EmployeeProxy>() {
      @Override
      public int compare(EmployeeProxy employeeProxy, EmployeeProxy employeeProxy1) {
        if (employeeProxy.getNameLast() != null) {
          return (employeeProxy1.getNameLast() != null) ? employeeProxy.getNameLast().compareTo(employeeProxy1.getNameLast()) : 1;
        }
        return -1;
      }
    });
    employeeTable.addColumnSortHandler(sortLastNameHandler);
    employeeTable.getColumnSortList().push(nameLast);
    nameLast.setSortable(true);

    final ColumnSortEvent.ListHandler<EmployeeProxy> sortMailHandler = new ColumnSortEvent.ListHandler<EmployeeProxy>(dataProvider.getList());
    sortMailHandler.setComparator(mail, new Comparator<EmployeeProxy>() {
      @Override
      public int compare(EmployeeProxy employeeProxy, EmployeeProxy employeeProxy1) {
        if (employeeProxy.getMail() != null) {
          return (employeeProxy1.getMail() != null) ? employeeProxy.getMail().compareTo(employeeProxy1.getMail()) : 1;
        }
        return -1;
      }
    });
    employeeTable.addColumnSortHandler(sortMailHandler);
    mail.setSortable(true);

    final ColumnSortEvent.ListHandler<EmployeeProxy> sortBirthHandler = new ColumnSortEvent.ListHandler<EmployeeProxy>(dataProvider.getList());
    sortBirthHandler.setComparator(birth, new Comparator<EmployeeProxy>() {
      @Override
      public int compare(EmployeeProxy employeeProxy, EmployeeProxy employeeProxy1) {
        if (employeeProxy.getBirth() != null) {
          return (employeeProxy1.getBirth() != null) ? employeeProxy.getBirth().compareTo(employeeProxy1.getBirth()) : 1;
        }
        return -1;
      }
    });
    employeeTable.addColumnSortHandler(sortBirthHandler);
    birth.setSortable(true);
  }

  @Override
  public void setName(String name) {
    this.name = name;
//        nameFirst.setText(name);
//        nameLast.setText("Orlov");
//        mail.setText("alexander.orlov@loxal.net");
  }

  @Override
  public List<EmployeeProxy> getDataList() {
    return dataList;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public RequestFactoryEditorDriver<EmployeeProxy, EmployeeEditor> getEditorDriver() {
//        return driver;
    return null;
  }
}
