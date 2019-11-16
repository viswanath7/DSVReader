package io.orite.service

import io.orite.domain.CreditLimit
object PRNParser extends Parser {
	override def readToCreditLimits: List[CreditLimit] = {
		import java.time.format.DateTimeFormatter
		val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
		scala.io.Source.fromResource("workbook.prn")
			.getLines
			.toList
			.drop(1)
			.map(_.split("\\t")
				.filter(_.nonEmpty)
				.take(6)
				.toList)
			.map {
				case List (name, address, postCode, phone, limit, birthDay) =>
					import java.time.LocalDate
					CreditLimit(name, address, postCode, phone, limit.toDouble, LocalDate.parse(birthDay, dateFormat))
			}
	}
}
