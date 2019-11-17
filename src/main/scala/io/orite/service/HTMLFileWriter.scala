package io.orite.service
import java.nio.file.Path
import cats.effect.IO
import io.orite.domain.CreditLimit

object HTMLFileWriter {
	
	def createHTML(creditLimits: List[CreditLimit]): IO[Path] = {
		import java.io.File
		def createTemporaryFile: IO[Path] = IO {
			def fullRights = {
				import java.nio.file.attribute.PosixFilePermissions
				PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"))
			}
			import java.nio.file.Files
			import java.text.SimpleDateFormat
			import java.util.Calendar
			val currentDateTime = new SimpleDateFormat("dd-MM-yyyy-HHmmssSS").format(Calendar.getInstance().getTime)
			val tempDirectoryPath = Files.createTempDirectory(currentDateTime, fullRights).toFile.getAbsolutePath
			new File(tempDirectoryPath + File.separator + "credit-limits.html").toPath
		}
		def writeToFile(filePath: Path, content:String): Unit = IO.fromTry {
			import java.nio.file.Files
			import scala.util.Using
			Using(Files newBufferedWriter filePath) { _.write(content)}
		}.unsafeRunAsyncAndForget()
		
		
		import cats.implicits._
		import cats.effect.ContextShift
		import cats.implicits._
		import scala.concurrent.ExecutionContext
		implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
		
		(IO.suspend(createTemporaryFile),
			IO.suspend(IO.pure(template.html.credit_limits(creditLimits).toString()))
			)
			.parMapN((temporaryFile, content) => {
			writeToFile(temporaryFile, content)
			temporaryFile
		})
	}
	
}
