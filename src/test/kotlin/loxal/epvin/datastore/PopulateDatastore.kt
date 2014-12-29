/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.google.web.bindery.event.shared.EventBus
import com.google.web.bindery.event.shared.SimpleEventBus
import com.google.web.bindery.requestfactory.server.ServiceLayer
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor
import com.google.web.bindery.requestfactory.server.testing.InProcessRequestTransport
import com.google.web.bindery.requestfactory.shared.Receiver
import com.google.web.bindery.requestfactory.vm.RequestFactorySource
import loxal.epvin.core.shared.AppUserProxy
import loxal.epvin.core.shared.AppUserReqCtx
import loxal.epvin.core.shared.EmployeeProxy
import loxal.epvin.core.shared.EmployeeReqCtx
import loxal.epvin.core.shared.ReqFactory
import loxal.epvin.core.shared.ResourceProxy
import loxal.epvin.core.shared.ResourceReqCtx
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

public class PopulateDatastore : Data() {
    val serviceLayer = ServiceLayer.create()
    val processor = SimpleRequestProcessor(serviceLayer)
    val rf = RequestFactorySource.create<ReqFactory>(javaClass<ReqFactory>())
    val eb: EventBus = SimpleEventBus()
    private val helper = LocalServiceTestHelper(LocalDatastoreServiceTestConfig())

    Before
    public fun setUp() {
        helper.setUp()
        rf.initialize(eb, InProcessRequestTransport(processor))
    }

    After
    public fun tearDown() {
        helper.tearDown()
    }

    Test
    throws(javaClass<Exception>())
    public fun populateAndVerifyUsers() {
        for (email in mails) {
            createUser(email)
        }
        getUser()
        retrieveUsers()
    }

    public fun createUser(email: String) {
        val appUserReqCtx = rf.appUserReqCtx()
        val appUser = appUserReqCtx.create<AppUserProxy>(javaClass<AppUserProxy>())
        appUser.setEmail(email)

        val onSuccess = booleanArray(false)
        appUserReqCtx.put(appUser).fire(object : Receiver<Void>() {
            override fun onSuccess(response: Void) {
                onSuccess[0] = true
            }
        })
        if (!onSuccess[0]) {
            System.out.println("email = " + email)
        }
        assertTrue(onSuccess[0])
    }

    public fun retrieveUsers() {
        val numOfUsers = 10

        val appUserReqCtx = rf.appUserReqCtx()
        appUserReqCtx.retrieve().fire(object : Receiver<List<AppUserProxy>>() {
            override fun onSuccess(response: List<AppUserProxy>) {
                assertEquals(numOfUsers.toLong(), response.size().toLong())
                var idx = 0
                for (appUser in response) {
                    assertEquals(mails[idx], response.get(idx++).getEmail())
                }
            }
        })
    }

    public fun getUser() {
        val appUserReqCtx = rf.appUserReqCtx()

        appUserReqCtx.get(1).fire(object : Receiver<AppUserProxy>() {
            override fun onSuccess(response: AppUserProxy) {
                assertEquals(mails[0], response.getEmail())
            }
        })
    }

    Test
    throws(javaClass<Exception>())
    public fun populateAndVerifyResources() {
        for (name in names) {
            createResource(name)
        }
        getResource()
        retrieveResources()
    }

    private fun retrieveResources() {
        val numOfResources = 10

        val resourceReqCtx = rf.resourceReqCtx()
        resourceReqCtx.retrieve().fire(object : Receiver<List<ResourceProxy>>() {
            override fun onSuccess(response: List<ResourceProxy>) {
                assertEquals(numOfResources.toLong(), response.size().toLong())
                var idx = 0
                for (resource in response) {
                    assertEquals(names[idx], response.get(idx++).getName())
                }
            }
        })
    }

    private fun createResource(name: String) {
        val resourceReqCtx = rf.resourceReqCtx()
        val resource = resourceReqCtx.create<ResourceProxy>(javaClass<ResourceProxy>())
        resource.setName(name)

        val onSuccess = booleanArray(false)
        resourceReqCtx.put(resource).fire(object : Receiver<Void>() {
            override fun onSuccess(response: Void) {
                onSuccess[0] = true
            }
        })
        if (!onSuccess[0]) {
            System.out.println("name = " + name)
        }
        assertTrue(onSuccess[0])
    }

    public fun getResource() {
        val resourceReqCtx = rf.resourceReqCtx()

        resourceReqCtx.get(1).fire(object : Receiver<ResourceProxy>() {
            override fun onSuccess(response: ResourceProxy) {
                assertEquals(names[0], response.getName())
            }
        })
    }

    Test
    throws(javaClass<Exception>())
    public fun populateAndVerifyEmployees() {
        for (i in 0..10 - 1) {
            val reqCtx = rf.employeeReqCtx()
            val entity = reqCtx.create<EmployeeProxy>(javaClass<EmployeeProxy>())
            entity.setNameFirst(firstNames[i])
            entity.setNameLast(lastNames[i])
            entity.setMail(mails[i])
            entity.setBirth(births[i])

            createEmployee(entity, reqCtx)
        }
        retrieveEmployee()
        retrieveEmployees()
    }

    private fun createEmployee(employee: EmployeeProxy, reqCtx: EmployeeReqCtx) {
        val onSuccess = booleanArray(false)
        reqCtx.put(employee).fire(object : Receiver<Void>() {
            override fun onSuccess(response: Void) {
                onSuccess[0] = true
            }
        })
        assertTrue(onSuccess[0])
    }

    public fun retrieveEmployee() {
        val reqCtx = rf.employeeReqCtx()

        reqCtx.get(1).fire(object : Receiver<EmployeeProxy>() {
            override fun onSuccess(response: EmployeeProxy) {
                assertEquals(firstNames[0], response.getNameFirst())
                assertEquals(lastNames[0], response.getNameLast())
                assertEquals(mails[0], response.getMail())
                assertEquals(births[0], response.getBirth())
            }
        })
    }

    private fun retrieveEmployees() {
        val numOfEntities = 10

        val employeeReqCtx = rf.employeeReqCtx()
        employeeReqCtx.retrieve().fire(object : Receiver<List<EmployeeProxy>>() {
            override fun onSuccess(response: List<EmployeeProxy>) {
                assertEquals(numOfEntities.toLong(), response.size().toLong())
                for (idx in response.indices) {
                    System.out.println("idx = " + idx)
                    assertEquals(firstNames[idx], response.get(idx).getNameFirst())
                    assertEquals(lastNames[idx], response.get(idx).getNameLast())
                    assertEquals(mails[idx], response.get(idx).getMail())
                    assertEquals(births[idx], response.get(idx).getBirth())
                }
            }
        })
    }

}
