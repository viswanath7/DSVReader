
name := "assignment"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies ++= {
	val catsVersion = "2.0.0"
	val catsRetryVersion = "0.3.1"
	Seq(
		"org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
		"org.typelevel" %% "cats-core" % catsVersion,
		"org.typelevel" %% "cats-effect" % catsVersion,
		"dev.profunktor" %% "console4cats" % "0.8.0",
		"com.github.cb372" %% "cats-retry-core" % catsRetryVersion,
		"com.github.cb372" %% "cats-retry-cats-effect" % catsRetryVersion,
		"com.beachape" %% "enumeratum" % "1.5.13",
		"com.chuusai" %% "shapeless" % "2.3.3",
		"org.apache.commons" % "commons-lang3" % "3.9",
		"ch.qos.logback" % "logback-classic" % "1.2.3",
		"com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
		"org.scalatest" %% "scalatest" % "3.2.0-M1" % Test,
		"org.mockito" % "mockito-core" % "3.1.0" % Test,
		"com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.3" % Test,
		"com.47deg" %% "scalacheck-toolbox-datetime" % "0.3.1" % Test,
		"com.47deg" %% "scalacheck-toolbox-magic" % "0.3.1" % Test,
		"com.47deg" %% "scalacheck-toolbox-combinators" % "0.3.1" % Test,
		"org.typelevel" %% "cats-testkit" % catsVersion % Test
	)
}

enablePlugins(SbtTwirl)

scalacOptions ++= Seq(
	"-deprecation", // Emit warning and location for usages of deprecated APIs.
	"-explaintypes", // Explain type errors in more detail.
	"-feature", // Emit warning and location for usages of features that should be imported explicitly.
	"-language:existentials", // Existential types (besides wildcard types) can be written and inferred
	"-language:experimental.macros", // Allow macro definition (besides implementation and application)
	"-language:higherKinds", // Allow higher-kinded types
	"-language:implicitConversions", // Allow definition of implicit functions called views
	"-unchecked", // Enable additional warnings where generated code depends on assumptions.
	"-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
	"-Xfatal-warnings", // Fail the compilation if there are any warnings.
	"-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
	"-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
	"-Xlint:delayedinit-select", // Selecting member of DelayedInit.
	"-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
	"-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
	"-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
	"-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
	"-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
	"-Xlint:nullary-unit", // Warn when nullary methods return Unit.
	"-Xlint:option-implicit", // Option.apply used implicit view.
	"-Xlint:package-object-classes", // Class or object defined in package object.
	"-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
	"-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
	"-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
	"-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
	"-Ywarn-dead-code", // Warn when dead code is identified.
	"-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
	"-Ywarn-numeric-widen", // Warn when numerics are widened.
	"-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
	//"-Ywarn-unused:imports", // Warn if an import selector is not referenced.
	"-Ywarn-unused:locals", // Warn if a local definition is unused.
	// "-Ywarn-unused:params", // Warn if a value parameter is unused.
	// "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
	"-Ywarn-unused:privates", // Warn if a private member is unused.
	"-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
	"-Ybackend-parallelism", "8", // Enable paralellisation — change to desired number!
	"-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
	"-Ycache-macro-class-loader:last-modified", // and macro definitions. This can lead to performance improvements.
)

// set the main class for packaging the main jar
mainClass in(Compile, packageBin) := Some("io.orite.Application")

// set the main class for the main 'sbt run' task
mainClass in(Compile, run) := Some("io.orite.Application")