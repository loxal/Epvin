package loxal.epvin.datastore

import java.util.*

public abstract class Data {
    // AppUser
    var mails = arrayOf("alexander.orlov@loxal.net", "kathryn.test@loxal.net", "tom.test@loxal.net", "tuvok.test@loxal.net", "kes.test@loxal.net", "b.elana.test@loxal.net", "neelix.test@loxal.net", "doctor.test@loxal.net", "chakotay.test@loxal.net", "seven.test@loxal.net")

    // Resource
    var names = arrayOf("Alex", "Kathryn.test", "Tom.test", "Tuvok.test", "Kes.test", "B'Elana.test", "Neelix.test", "Doctor.test", "Chakotay.test", "Seven.test")

    // Employee
    var firstNames = arrayOf("Alfa", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet")

    var lastNames = arrayOf("Aleksandro", "Bartolomeo", "Davido", "Eduardo", "Georgo", "Henriko", "Johano", "Karlo", "Lino", "Patriko")

    var births = arrayOf(GregorianCalendar(83, 5, 6).getTime(), GregorianCalendar(84, 6, 7).getTime(), GregorianCalendar(85, 7, 8).getTime(), GregorianCalendar(86, 8, 9).getTime(), GregorianCalendar(87, 9, 10).getTime(), GregorianCalendar(88, 10, 11).getTime(), GregorianCalendar(89, 11, 12).getTime(), GregorianCalendar(90, 12, 13).getTime(), GregorianCalendar(91, 1, 14).getTime(), GregorianCalendar(92, 2, 15).getTime())
}