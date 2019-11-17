package io.orite.domain

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class InputFileTypeSpec extends AnyFlatSpec with Matchers {
	"InputFileType enumeration's extension method" should "return a parser" in {
		import io.orite.service.Parser
		InputFileType.values.foreach( _.parser shouldBe a[Parser])
	}
}
