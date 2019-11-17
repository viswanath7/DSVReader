package io.orite.service

import cats.effect.IO
import io.orite.domain.CreditLimit

trait Parser {
	/**
	 * Parses the pre-defined bundled classpath resource and returns a list of credit limits provided the file contains valid data
	 *
	 * @return List of credit limits
	 */
	def readToCreditLimits:IO[List[CreditLimit]]
}