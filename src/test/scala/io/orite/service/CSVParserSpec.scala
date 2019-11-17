package io.orite.service

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class CSVParserSpec extends AsyncFlatSpec with Matchers {
	
	"CSV Parser" should "read bundled CSV file in resources directory and convert its contents to a list of credit limit" in {
		import java.time.LocalDate
		import io.orite.domain.CreditLimit
		CSVParser.readToCreditLimits.unsafeToFuture() map {
			result =>
				assert(result.size == 7 &&
					result.contains(CreditLimit("Johnson,John", "Voorstraat32", "3122gg", "0203849381", 10000.0, LocalDate.of(1987, 1, 1)))
				)
		}
	}
	
}
