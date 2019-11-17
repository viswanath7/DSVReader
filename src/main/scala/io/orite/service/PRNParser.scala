package io.orite.service

import cats.effect.IO
import io.orite.domain.CreditLimit
import java.time.format.DateTimeFormatter

object PRNParser extends Parser {
	
	private[this] val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
	
	override def readToCreditLimits: IO[List[CreditLimit]] = IO.suspend(IO.fromTry {
		import scala.util.Using
		Using(scala.io.Source.fromResource("workbook.prn")) { bufferedSource =>
			bufferedSource.getLines
				.toList
				.drop(1)
				.map(_.split("\\t")
					.filter(_.nonEmpty)
					.take(6)
					.toList)
				.map {
					case List(name, address, postCode, phone, limit, birthDay) =>
						import java.time.LocalDate
						CreditLimit(name, address, postCode, phone, limit.toDouble, LocalDate.parse(birthDay, dateFormat))
					case _ => throw new IllegalStateException("Classpath resource 'workbook.prn' seems to contain invalid or incomplete data")
				}
		}
	})
}
