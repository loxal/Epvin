/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 */

package loxal.epvin.datastore;

import java.util.Date;

public abstract class Data {
  // AppUser
  String[] mails = {
      "alexander.orlov@loxal.net", "kathryn.test@loxal.net", "tom.test@loxal.net",
      "tuvok.test@loxal.net", "kes.test@loxal.net", "b.elana.test@loxal.net",
      "neelix.test@loxal.net", "doctor.test@loxal.net", "chakotay.test@loxal.net",
      "seven.test@loxal.net",
  };

  // Resource
  String[] names = {
      "Alex", "Kathryn.test", "Tom.test",
      "Tuvok.test", "Kes.test", "B'Elana.test",
      "Neelix.test", "Doctor.test", "Chakotay.test",
      "Seven.test",
  };

  // Employee
  String[] firstNames = {
      "Alfa", "Bravo", "Charlie",
      "Delta", "Echo", "Foxtrot",
      "Golf", "Hotel", "India",
      "Juliet",
  };

  String[] lastNames = {
      "Aleksandro", "Bartolomeo", "Davido",
      "Eduardo", "Georgo", "Henriko",
      "Johano", "Karlo", "Lino",
      "Patriko",
  };

  @SuppressWarnings("deprecation")
  Date[] births = {
      new Date(83, 5, 6), new Date(84, 6, 7), new Date(85, 7, 8),
      new Date(86, 8, 9), new Date(87, 9, 10), new Date(88, 10, 11),
      new Date(89, 11, 12), new Date(90, 12, 13), new Date(91, 1, 14),
      new Date(92, 2, 15),
  };
}
