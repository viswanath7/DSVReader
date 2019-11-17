package io.orite
import scala.reflect.ClassTag
import org.scalacheck.Arbitrary

package object service {
	
	/**
	 * Generates a random instances of type T
	 *
	 * @param arbitrary instance of arbitrary
	 * @param classTag  class tag of type T
	 * @tparam T type of instance to generate
	 * @return
	 */
	def random[T](implicit arbitrary: Arbitrary[T], classTag: ClassTag[T]): T = {
		arbitrary.arbitrary.sample.getOrElse(throw new InstantiationException(s"Failed to create an instance of type '${classTag.runtimeClass.getTypeName}'"))
	}
}
