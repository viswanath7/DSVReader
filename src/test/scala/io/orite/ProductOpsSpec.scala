package io.orite

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ProductOpsSpec extends AnyFlatSpec with Matchers {
	
	"A regular case class" must "be converted to Map collection" in {
		case class Address(houseNumber: Int, streetName: String, town: String, postalCode: String, country: String)
		Address(34, "Blaak", "Rotterdam", "3011 TA", "The Netherlands").toMap should have size 5
	}
	
	"Fields names of a case class" must "be returned in a list " in {
		case class Address(houseNumber: Int, streetName: String, town: String, postalCode: String, country: String)
		Address(34, "Blaak", "Rotterdam", "3011 TA", "The Netherlands").fieldNames should contain inOrder ("houseNumber", "streetName", "town", "postalCode", "country")
	}
}
