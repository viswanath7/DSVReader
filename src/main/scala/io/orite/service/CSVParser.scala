package io.orite.service

import scala.util.parsing.combinator._
import scala.util.matching.Regex
import scala.language.postfixOps
import io.orite.domain.CreditLimit

object CSVParser extends RegexParsers with Parser {
	
	override protected val whiteSpace: Regex = """[ \t]""".r
	
	private def parse(csvLine: String): List[String] = {
		
		def record: Parser[List[String]] = {
			val comma = ","
			val doubleQuotes = "\""
			val combinedDoubleQuotes = "\"\"" ^^ (_ => "\"") //  // combine 2 double quotes into one
			val text = "[^\",\r\n]".r
			val carriageReturn = "\r"
			val lineFeed = "\n"
			def field: Parser[String] = {
				def escaped: Parser[String] = (doubleQuotes ~> ((text | comma | carriageReturn | lineFeed | combinedDoubleQuotes) *) <~ doubleQuotes) ^^ (list => list.mkString(""))
				def nonEscaped: Parser[String] = (text *) ^^ (ls => ls.mkString(""))
				escaped | nonEscaped
			}
			rep1sep(field, comma)
		}
		
		/*
		// Parser for the entire file content as a string
		def file: Parser[List[List[String]]] = {
			val carriageReturnLineFeed = "\r\n"
			repsep(record, carriageReturnLineFeed) <~ opt(carriageReturnLineFeed)
		}
		*/
		
		parseAll(record, csvLine) match {
			case Success(result, _) => result
			case _ => List.empty
		}
	}
	
	def readToCreditLimits:List[CreditLimit] = {
		import java.time.format.DateTimeFormatter
		val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
		scala.io.Source.fromResource("workbook.csv")
		.getLines
		.drop(1)
		.map(parse)
		.map(_.take(6).toList)
		.map {
				case List (name, address, postCode, phone, limit, birthDay) =>
					import java.time.LocalDate
					CreditLimit(name, address, postCode, phone, limit.toDouble, LocalDate.parse(birthDay, dateFormat))
			}.toList
	}
}