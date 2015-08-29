package loxal.epvin.datastore

import java.util.Date

public abstract class Data {
    // AppUser
    var mails = arrayOf("alexander.orlov@loxal.net", "kathryn.test@loxal.net", "tom.test@loxal.net", "tuvok.test@loxal.net", "kes.test@loxal.net", "b.elana.test@loxal.net", "neelix.test@loxal.net", "doctor.test@loxal.net", "chakotay.test@loxal.net", "seven.test@loxal.net")

    // Resource
    var names = arrayOf("Alex", "Kathryn.test", "Tom.test", "Tuvok.test", "Kes.test", "B'Elana.test", "Neelix.test", "Doctor.test", "Chakotay.test", "Seven.test")

    // Employee
    var firstNames = arrayOf("Alfa", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet")

    var lastNames = arrayOf("Aleksandro", "Bartolomeo", "Davido", "Eduardo", "Georgo", "Henriko", "Johano", "Karlo", "Lino", "Patriko")

    SuppressWarnings("deprecation")
    var births = arrayOf(Date(83, 5, 6), Date(84, 6, 7), Date(85, 7, 8), Date(86, 8, 9), Date(87, 9, 10), Date(88, 10, 11), Date(89, 11, 12), Date(90, 12, 13), Date(91, 1, 14), Date(92, 2, 15))
}