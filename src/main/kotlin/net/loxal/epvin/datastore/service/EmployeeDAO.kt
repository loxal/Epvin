/*
 * Copyright 2015 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package net.loxal.epvin.datastore.service

import com.googlecode.objectify.ObjectifyService
import net.loxal.epvin.datastore.model.Employee

public class EmployeeDAO : DAO() {
    val ofy = ObjectifyService.ofy()

    public fun put(employee: Employee) {
        ofy.save().entity<Employee>(employee).now()
    }

    public fun delete(id: Long?) {
        ofy.delete().type(Employee::class.java).id(id!!)
    }

    operator public fun get(id: Long?): Employee {
        return ofy.load().type<Employee>(Employee::class.java).id(id!!).now()
    }

    public fun retrieve(): List<Employee> {
        return ofy.load().type<Employee>(Employee::class.java).list()
    }

    companion object {
        init {
            ObjectifyService.begin()
        }
    }
}