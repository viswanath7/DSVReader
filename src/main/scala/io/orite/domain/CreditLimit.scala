package io.orite.domain

import java.time.LocalDate

case class CreditLimit(name:String, address:String, postCode: String, phoneNumber:String, limit:Double, dateOfBirth:LocalDate)

