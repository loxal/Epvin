/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import loxal.epvin.datastore.model.Employee;

import java.util.List;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class EmployeeDAO extends DAO {
    final Objectify ofy = ObjectifyService.begin();

    public void put(final Employee employee) {
        ofy.save().entity(employee).now();
    }

    public void delete(final Long id) {
        final Key<Employee> key = ofy.load().type(Employee.class).id(id).getKey();
        ofy.delete().key(key);
    }

    public Employee get(final Long id) {
        return ofy.load().type(Employee.class).id(id).get();
    }

    public List<Employee> retrieve() {
        return ofy.load().type(Employee.class).list();
    }
}
