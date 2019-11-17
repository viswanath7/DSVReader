package io.orite.service

import scala.util.parsing.combinator._
import scala.util.matching.Regex
import scala.language.postfixOps
import io.orite.domain.CreditLimit
import java.time.format.DateTimeFormatter
import cats.effect.IO

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
	
	private[this] val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
	
	def readToCreditLimits: IO[List[CreditLimit]] = {
		import scala.util.Using
		IO.fromTry {
			Using(scala.io.Source.fromResource("workbook.csv")) { bufferedSource =>
				bufferedSource
					.getLines
					.drop(1)
					.map(parse)
					.map(_.take(6).toList)
					.map {
						case List(name, address, postCode, phone, limit, birthDay) =>
							import java.time.LocalDate
							CreditLimit(name, address, postCode, phone, limit.toDouble, LocalDate.parse(birthDay, dateFormat))
						case _ => throw new IllegalStateException("Classpath resource 'workbook.csv' seems to contain invalid or incomplete data")
					}.toList
			}
		}
	}
}