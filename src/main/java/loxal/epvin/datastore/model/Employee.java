/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model;

import com.googlecode.objectify.annotation.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@Entity
public class Employee extends DatastoreEntity {
    @Past(message = "Birth date must be a past date.")
    private Date birth;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Pattern(
            message = "Invalid format.",
            regexp = "^[a-zA-Z0-9.!#$%&amp;'*+/=?\\^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$" // according to HTML5 spec
    )
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @NotNull(message = "Required field.")
    @Size(message = "Invalid length.", min = 1, max = 35)
    private String nameFirst;

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    @NotNull(message = "Required field.")
    @Size(message = "Invalid length.", min = 1, max = 35)
    private String nameLast;

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    private Date joining;

    public Date getJoining() {
        return joining;
    }

    public void setJoining(Date joining) {
        this.joining = joining;
    }

    // make this a relation to another entity
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
