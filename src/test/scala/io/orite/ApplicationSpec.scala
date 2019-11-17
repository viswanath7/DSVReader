package io.orite

import cats.data.Chain
import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.effect.test.TestConsole
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ApplicationSpec extends AnyFlatSpec with Matchers {
	
	"Command line application" should "work correctly" in {
		(for {
			outputLine <- Ref[IO].of(Chain.empty[String])
			outputWord <- Ref[IO].of(Chain.empty[String])
			outputError <- Ref[IO].of(Chain.empty[String])
			userInput <- TestConsole.inputs.sequenceAndDefault[IO](Chain("1"), "2")
			testConsole = TestConsole.make(outputLine, outputWord, outputError, userInput)
			_ <- Application.executeSideEffect(testConsole)
			firstResult <- outputLine.get
			secondResult <- outputWord.get
			errorResult <- outputError.get
		} yield {
			assert(
				firstResult
					.iterator
					.toList
					.diff {
						List("Please indicate if you would like to process the CSV or PRN file",
							"Enter 1 for CSV or 2 for PRN",
							"You have chosen to process the CSV file")
					}.isEmpty)
			assert {
				secondResult
					.headOption
					.exists(output => output.startsWith("HTML file containing credit limits is available at")
						&& output.endsWith("credit-limits.html"))
			}
			assert(errorResult == Chain.empty)
		}).unsafeRunSync()
	}
}
