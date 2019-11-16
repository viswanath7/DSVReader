package io.orite.service

import io.orite.domain.CreditLimit

trait Parser {
	def readToCreditLimits:List[CreditLimit]
}