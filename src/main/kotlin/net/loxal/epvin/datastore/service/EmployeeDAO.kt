/*
 * Copyright 2016 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.service

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService
import net.loxal.epvin.datastore.model.Employee

class EmployeeDAO : DAO() {
    val ofy: Objectify = ObjectifyService.ofy()

    fun put(employee: Employee) {
        ofy.save().entity<Employee>(employee).now()
    }

    fun delete(id: Long?) {
        ofy.delete().type(Employee::class.java).id(id!!)
    }

    operator fun get(id: Long?): Employee {
        return ofy.load().type<Employee>(Employee::class.java).id(id!!).now()
    }

    fun retrieve(): List<Employee> {
        return ofy.load().type<Employee>(Employee::class.java).list()
    }

    companion object {
        init {
            ObjectifyService.begin()
        }
    }
}