package loxal.epvin.datastore.service

import loxal.epvin.datastore.model.Employee
import com.googlecode.objectify.ObjectifyService


public class EmployeeDAO : DAO() {
    val ofy = ObjectifyService.ofy()

    public fun put(employee: Employee) {
        ofy.save().entity<Employee>(employee).now()
    }

    public fun delete(id: Long?) {
        ofy.delete().type(javaClass<Employee>()).id(id!!)
    }

    public fun get(id: Long?): Employee {
        return ofy.load().type<Employee>(javaClass<Employee>()).id(id!!).now()
    }

    public fun retrieve(): List<Employee> {
        return ofy.load().type<Employee>(javaClass<Employee>()).list()
    }
}