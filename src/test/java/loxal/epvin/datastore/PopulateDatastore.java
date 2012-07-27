/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor;
import com.google.web.bindery.requestfactory.server.testing.InProcessRequestTransport;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;
import loxal.epvin.core.shared.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class PopulateDatastore extends Data {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    final ServiceLayer serviceLayer = ServiceLayer.create();
    final SimpleRequestProcessor processor = new SimpleRequestProcessor(serviceLayer);
    final ReqFactory rf = RequestFactorySource.create(ReqFactory.class);
    final EventBus eb = new SimpleEventBus();

    @Before
    public void setUp() {
        helper.setUp();
        rf.initialize(eb, new InProcessRequestTransport(processor));
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void populateAndVerifyUsers() throws Exception {
        for (String email : mails) {
            createUser(email);
        }
        getUser();
        retrieveUsers();
    }

    public void createUser(String email) {
        AppUserReqCtx appUserReqCtx = rf.appUserReqCtx();
        final AppUserProxy appUser = appUserReqCtx.create(AppUserProxy.class);
        appUser.setEmail(email);

        final boolean[] onSuccess = {false};
        appUserReqCtx.put(appUser).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                onSuccess[0] = true;
            }
        });
        if (!onSuccess[0]) {
            System.out.println("email = " + email);
        }
        assertTrue(onSuccess[0]);
    }

    public void retrieveUsers() {
        final int numOfUsers = 10;

        AppUserReqCtx appUserReqCtx = rf.appUserReqCtx();
        appUserReqCtx.retrieve().fire(new Receiver<List<AppUserProxy>>() {
            @Override
            public void onSuccess(List<AppUserProxy> response) {
                assertEquals(numOfUsers, response.size());
                int idx = 0;
                for (AppUserProxy appUser : response) {
                    assertEquals(mails[idx], response.get(idx++).getEmail());
                }
            }
        });
    }

    public void getUser() {
        AppUserReqCtx appUserReqCtx = rf.appUserReqCtx();

        appUserReqCtx.get(1L).fire(new Receiver<AppUserProxy>() {
            @Override
            public void onSuccess(AppUserProxy response) {
                assertEquals(mails[0], response.getEmail());
            }
        });
    }

    @Test
    public void populateAndVerifyResources() throws Exception {
        for (String name : names) {
            createResource(name);
        }
        getResource();
        retrieveResources();
    }

    private void retrieveResources() {
        final int numOfResources = 10;

        ResourceReqCtx resourceReqCtx = rf.resourceReqCtx();
        resourceReqCtx.retrieve().fire(new Receiver<List<ResourceProxy>>() {
            @Override
            public void onSuccess(List<ResourceProxy> response) {
                assertEquals(numOfResources, response.size());
                int idx = 0;
                for (ResourceProxy resource : response) {
                    assertEquals(names[idx], response.get(idx++).getName());
                }
            }
        });
    }

    private void createResource(String name) {
        ResourceReqCtx resourceReqCtx = rf.resourceReqCtx();
        ResourceProxy resource = resourceReqCtx.create(ResourceProxy.class);
        resource.setName(name);

        final boolean[] onSuccess = {false};
        resourceReqCtx.put(resource).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                onSuccess[0] = true;
            }
        });
        if (!onSuccess[0]) {
            System.out.println("name = " + name);
        }
        assertTrue(onSuccess[0]);
    }

    public void getResource() {
        ResourceReqCtx resourceReqCtx = rf.resourceReqCtx();

        resourceReqCtx.get(1L).fire(new Receiver<ResourceProxy>() {
            @Override
            public void onSuccess(ResourceProxy response) {
                assertEquals(names[0], response.getName());
            }
        });
    }

    @Test
    public void populateAndVerifyEmployees() throws Exception {
        for (int i = 0; i < 10; i++) {
            EmployeeReqCtx reqCtx = rf.employeeReqCtx();
            EmployeeProxy entity = reqCtx.create(EmployeeProxy.class);
            entity.setNameFirst(namesFirst[i]);
            entity.setNameLast(namesLast[i]);
            entity.setMail(mails[i]);
            entity.setBirth(births[i]);

            createEmployee(entity, reqCtx);
        }
        retrieveEmployee();
        retrieveEmployees();
        editEmployee();
    }

    private void createEmployee(final EmployeeProxy employee, EmployeeReqCtx reqCtx) {
        final boolean[] onSuccess = {false};
        reqCtx.put(employee).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                onSuccess[0] = true;
            }

            @Override
            public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
                System.out.println(violations.size());
                ConstraintViolation<?> cv = violations.iterator().next();
                System.out.println(cv.getMessage());
                System.out.println(cv.getPropertyPath());
                System.out.println(cv.getInvalidValue());
                System.out.println("employee = " + employee.getMail());
            }
        });
        assertTrue(onSuccess[0]);
    }

    public void retrieveEmployee() {
        EmployeeReqCtx reqCtx = rf.employeeReqCtx();

        reqCtx.get(1L).fire(new Receiver<EmployeeProxy>() {
            @Override
            public void onSuccess(EmployeeProxy response) {
                assertEquals(namesFirst[0], response.getNameFirst());
                assertEquals(namesLast[0], response.getNameLast());
                assertEquals(mails[0], response.getMail());
                assertEquals(births[0], response.getBirth());
            }
        });
    }

    private void retrieveEmployees() {
        final int numOfEntities = 10;

        EmployeeReqCtx employeeReqCtx = rf.employeeReqCtx();
        employeeReqCtx.retrieve().fire(new Receiver<List<EmployeeProxy>>() {
            @Override
            public void onSuccess(List<EmployeeProxy> response) {
                assertEquals(numOfEntities, response.size());
                for (int idx = 0; idx < response.size(); idx++) {
                    System.out.println("idx = " + idx);
                    assertEquals(namesFirst[idx], response.get(idx).getNameFirst());
                    assertEquals(namesLast[idx], response.get(idx).getNameLast());
                    assertEquals(mails[idx], response.get(idx).getMail());
                    assertEquals(births[idx], response.get(idx).getBirth());
                }
            }
        });
    }

    private void editEmployee() {
        final String alterationSuffix = " CHANGED";
        final Long id = 1L;

        final EmployeeReqCtx reqCtx = rf.employeeReqCtx();
        reqCtx.get(id).fire(new Receiver<EmployeeProxy>() {
            @Override
            public void onSuccess(EmployeeProxy response) {
                EmployeeReqCtx reqCtxEdit = rf.employeeReqCtx();
                EmployeeProxy employee = reqCtxEdit.edit(response);
                employee.setNameFirst(response.getNameFirst() + alterationSuffix);
                employee.setNameLast(response.getNameLast());
                employee.setMail(response.getMail());
                employee.setBirth(response.getBirth());

                reqCtxEdit.put(employee).fire(new Receiver<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        EmployeeReqCtx reqCtxEditVerify = rf.employeeReqCtx();
                        reqCtxEditVerify.get(id).fire(new Receiver<EmployeeProxy>() {
                            @Override
                            public void onSuccess(EmployeeProxy response) {
                                assertEquals(namesFirst[0] + alterationSuffix, response.getNameFirst());
                                assertEquals(namesLast[0], response.getNameLast());
                                assertEquals(mails[0], response.getMail());
                                assertEquals(births[0], response.getBirth());
                            }
                        });
                    }
                });
            }
        });
    }

    @Test
    public void deleteEmployee() throws Exception {
        EmployeeReqCtx reqCtx = rf.employeeReqCtx();
        final boolean[] onSuccess = {false};
        reqCtx.delete(10L).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                onSuccess[0] = true;
            }

            @Override
            public void onFailure(ServerFailure failure) {
                System.err.println("failure.getMessage() = " + failure.getMessage());
            }
        });
        assertTrue(onSuccess[0]);
    }

}
