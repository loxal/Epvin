/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore.model;

import java.util.Date;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class Vacation extends DatastoreEntity {
  private AppUser vacationer;

  public AppUser getVacationer() {
    return vacationer;
  }

  public void setVacationer(AppUser vacationer) {
    this.vacationer = vacationer;
  }

  private Date day;

  public Date getDay() {
    return day;
  }

  public void setDay(Date day) {
    this.day = day;
  }

  private boolean halfDay;

  public boolean isHalfDay() {
    return halfDay;
  }

  public void setHalfDay(boolean halfDay) {
    this.halfDay = halfDay;
  }
}
