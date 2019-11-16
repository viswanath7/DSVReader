package io.orite
import cats.effect._
import cats.implicits._
import cats.effect.Console.io._
import scala.concurrent.duration._
import com.typesafe.scalalogging.Logger
import io.orite.domain.InputFileType
import io.orite.domain.InputFileType._

object Application extends IOApp {
	
	import io.orite.domain.CreditLimit
	
	private[this] val logger = Logger("io.orite.Application")
	
	def obtainUserInput: IO[InputFileType] = {
		import retry._
		import retry.RetryPolicies._
		import cats.syntax.semigroup._
		import cats.effect.Timer

		import scala.concurrent.ExecutionContext.global
		import retry.CatsEffect._
		import retry.RetryDetails._
		
		implicit val timer: Timer[IO] = IO timer global
		
		// retry with a 100ms delay 5 times
		val retryPolicy = limitRetries[IO](5) |+| constantDelay[IO](100.milliseconds)
		
		def logError(err: Throwable, details: RetryDetails): IO[Unit] = details match {
			case WillDelayAndRetry(nextDelay: FiniteDuration, retriesSoFar: Int, cumulativeDelay: FiniteDuration) =>
				IO {
					logger error "Failed to obtain correct user-input!"
					logger info s"Retried $retriesSoFar time(s). Retrying in $nextDelay ..."
				}
			case GivingUp(totalRetries: Int, totalDelay: FiniteDuration) => IO {
				logger error s"Giving up after $totalRetries retries. Total delay between retries is $totalDelay"
			}
		}
		
		def handleRawUserInput(userInput:String) = userInput match {
			case "1" =>
				putStrLn(s"You have chosen to process the CSV file") *> IO.pure(CSV)
			case "2" => putStrLn(s"You have chosen to process the PRN file") *> IO.pure(PRN)
			case _ =>
				val errorMessage = "Only 1 or 2 are acceptable choices!"
				putError(errorMessage) *> IO.raiseError(new IllegalArgumentException(errorMessage))
		}
		
		val gatherValidUserInput = for {
			_ <- putStrLn("Please indicate if you would like to process the CSV or PRN file")
			_ <- putStrLn("Enter 1 for CSV or 2 for PRN")
			rawUserInput <- readLn
			validatedUserInput <- handleRawUserInput(rawUserInput)
		} yield validatedUserInput
		
		retryingOnAllErrors[InputFileType](
			policy = retryPolicy,
			onError = logError
		)(gatherValidUserInput)
	}
	
	private def readFile(inputFileType: InputFileType):IO[List[CreditLimit]] = IO {
		inputFileType.parser.readToCreditLimits
	}
	
	override def run(arguments: List[String]): IO[ExitCode] = {
		import io.orite.service.HTMLFileWriter
		logger info "Executing the application ..."
		for {
			inputFileType <- obtainUserInput
			creditLimits <- readFile(inputFileType)
			htmlFilePath <- HTMLFileWriter.createHTML(creditLimits)
			_     <- putStrLn(s"HTML file containing credit limits is available at $htmlFilePath")
		} yield ExitCode.Success
	}
	
	
}
