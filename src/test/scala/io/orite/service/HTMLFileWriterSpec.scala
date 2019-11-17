package io.orite.service

import io.orite.domain.CreditLimit
import org.scalacheck.ScalacheckShapeless._
import com.fortysevendeg.scalacheck.datetime.jdk8.ArbitraryJdk8._
import com.fortysevendeg.scalacheck.datetime.instances.jdk8._
import com.fortysevendeg.scalacheck.datetime.jdk8.granularity.years
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class HTMLFileWriterSpec extends AsyncFlatSpec with Matchers {
	
	"Given a list of credit limits, HTMLFileWriter" should "create a temporary HTML file with those credit limits" in {
		val creditLimit = random[CreditLimit]
		val input = creditLimit +: random[List[CreditLimit]]
		HTMLFileWriter.createHTML(input).unsafeToFuture().map( path => {
			import scala.io.Source
			assert(path.toFile.exists)
			assert{
				import scala.util.Using
				Using(Source.fromURI(path.toFile.toURI)){
					bufferedSource =>
						bufferedSource.getLines().toList.mkString("")
				}.toOption.getOrElse("") contains(s"<td>${creditLimit.name}</td>")
			}
		})
	}
}
