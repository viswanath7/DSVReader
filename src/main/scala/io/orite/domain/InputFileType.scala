package io.orite.domain
import enumeratum._

sealed trait InputFileType extends EnumEntry

object InputFileType extends Enum[InputFileType] {
	override def values = findValues
	case object CSV extends InputFileType
	case object PRN extends InputFileType
	
	
	implicit class InputFileTypeOps(inputFileType: InputFileType) {
		import io.orite.service._
		def parser:Parser = inputFileType match {
			case CSV => CSVParser
			case PRN => PRNParser
		}
	}
}
