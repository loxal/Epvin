/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.google.web.bindery.event.shared.EventBus
import com.google.web.bindery.event.shared.SimpleEventBus
import com.google.web.bindery.requestfactory.server.ServiceLayer
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor
import com.google.web.bindery.requestfactory.server.testing.InProcessRequestTransport
import com.google.web.bindery.requestfactory.shared.Receiver
import com.google.web.bindery.requestfactory.vm.RequestFactorySource
import com.googlecode.objectify.ObjectifyService
import net.loxal.epvin.core.shared.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PopulateDatastore : Data() {
    val serviceLayer: ServiceLayer? = ServiceLayer.create()
    val processor = SimpleRequestProcessor(serviceLayer)
    val rf: ReqFactory = RequestFactorySource.create<ReqFactory>(ReqFactory::class.java)
    val eb: EventBus = SimpleEventBus()
    private val helper = LocalServiceTestHelper(LocalDatastoreServiceTestConfig())

    @Before fun setUp() {
        helper.setUp()
        rf.initialize(eb, InProcessRequestTransport(processor))
        ObjectifyService.begin()
    }

    @After fun tearDown() {
        helper.tearDown()
    }

    @Test
    @Throws(Exception::class)
    fun populateAndVerifyUsers() {
        for (email in mails) {
            createUser(email)
        }
        getUser()
        retrieveUsers()
    }

    fun createUser(email: String) {
        val appUserReqCtx = rf.appUserReqCtx()
        val appUser = appUserReqCtx.create<AppUserProxy>(AppUserProxy::class.java)
        appUser.email = email

        val onSuccess = booleanArrayOf(false)
        appUserReqCtx.put(appUser).fire(object : Receiver<Void>() {
            override fun onSuccess(response: Void) {
                onSuccess[0] = true
            }
        })
        if (!onSuccess[0]) {
            println("email = " + email)
        }
        assertTrue(onSuccess[0])
    }

    fun retrieveUsers() {
        val numOfUsers = 10

        val appUserReqCtx = rf.appUserReqCtx()
        appUserReqCtx.retrieve().fire(object : Receiver<List<AppUserProxy>>() {
            override fun onSuccess(response: List<AppUserProxy>) {
                assertEquals(numOfUsers.toLong(), response.size.toLong())
                var idx = 0
                for (appUser in response) {
                    assertEquals(mails[idx], response[idx++].email)
                }
            }
        })
    }

    fun getUser() {
        val appUserReqCtx = rf.appUserReqCtx()

        appUserReqCtx.get(1).fire(object : Receiver<AppUserProxy>() {
            override fun onSuccess(response: AppUserProxy) {
                assertEquals(mails[0], response.email)
            }
        })
    }

    @Test
    @Throws(Exception::class)
    fun populateAndVerifyResources() {
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
                assertEquals(numOfResources.toLong(), response.size.toLong())
                var idx = 0
                for (resource in response) {
                    assertEquals(names[idx], response[idx++].name)
                }
            }
        })
    }

    private fun createResource(name: String) {
        val resourceReqCtx = rf.resourceReqCtx()
        val resource = resourceReqCtx.create<ResourceProxy>(ResourceProxy::class.java)
        resource.name = name

        val onSuccess = booleanArrayOf(false)
        resourceReqCtx.put(resource).fire(object : Receiver<Void>() {
            override fun onSuccess(response: Void) {
                onSuccess[0] = true
            }
        })
        if (!onSuccess[0]) {
            println("name = " + name)
        }
        assertTrue(onSuccess[0])
    }

    fun getResource() {
        val resourceReqCtx = rf.resourceReqCtx()

        resourceReqCtx.get(1).fire(object : Receiver<ResourceProxy>() {
            override fun onSuccess(response: ResourceProxy) {
                assertEquals(names[0], response.name)
            }
        })
    }

    @Test
    @Throws(Exception::class)
    fun populateAndVerifyEmployees() {
        for (i in 0..10 - 1) {
            val reqCtx = rf.employeeReqCtx()
            val entity = reqCtx.create<EmployeeProxy>(EmployeeProxy::class.java)
            entity.nameFirst = firstNames[i]
            entity.nameLast = lastNames[i]
            entity.mail = mails[i]
            entity.birth = births[i]

            createEmployee(entity, reqCtx)
        }
        retrieveEmployee()
        retrieveEmployees()
    }

    private fun createEmployee(employee: EmployeeProxy, reqCtx: EmployeeReqCtx) {
        val onSuccess = booleanArrayOf(false)
        reqCtx.put(employee).fire(object : Receiver<Void>() {
            override fun onSuccess(response: Void) {
                onSuccess[0] = true
            }
        })
        assertTrue(onSuccess[0])
    }

    fun retrieveEmployee() {
        val reqCtx = rf.employeeReqCtx()

        reqCtx.get(1).fire(object : Receiver<EmployeeProxy>() {
            override fun onSuccess(response: EmployeeProxy) {
                assertEquals(firstNames[0], response.nameFirst)
                assertEquals(lastNames[0], response.nameLast)
                assertEquals(mails[0], response.mail)
                assertEquals(births[0], response.birth)
            }
        })
    }

    private fun retrieveEmployees() {
        val numOfEntities = 10

        val employeeReqCtx = rf.employeeReqCtx()
        employeeReqCtx.retrieve().fire(object : Receiver<List<EmployeeProxy>>() {
            override fun onSuccess(response: List<EmployeeProxy>) {
                assertEquals(numOfEntities.toLong(), response.size.toLong())
                for (idx in response.indices) {
                    println("idx = " + idx)
                    assertEquals(firstNames[idx], response[idx].nameFirst)
                    assertEquals(lastNames[idx], response[idx].nameLast)
                    assertEquals(mails[idx], response[idx].mail)
                    assertEquals(births[idx], response[idx].birth)
                }
            }
        })
    }

}
