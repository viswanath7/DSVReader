package io.orite.service

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class PRNParserSpec extends AsyncFlatSpec with Matchers {
	"PRN parser" should "read bundled PRN file in resources directory and convert its contents to a list of credit limit" in {
		import java.time.LocalDate
		import io.orite.domain.CreditLimit
		PRNParser.readToCreditLimits.unsafeToFuture() map {
			resultList =>	assert(
				resultList.size == 7 &&
					resultList.contains(CreditLimit("Wicket, Steve", "Mendelssohnstraat 54d", "3423 ba", "0313-398475", 93400.0, LocalDate.of(1964,6,3)))
			)
		}
	}
}
