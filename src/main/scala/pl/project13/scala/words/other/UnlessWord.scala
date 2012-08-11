package pl.project13.scala.words.other

import pl.project13.scala.words.annotation.aliasFor

trait UnlessWord {

  import SomeWord._

  implicit def stringUnless(s: String) = new StringUnless(s)

  implicit def anyUnless[T](s: AnyRef) = new AnyUnless(s)

  implicit def blockUnless[T](execute: => T) = new DoNotDoBlockUnlessTest(execute)

  class DoNotDoBlockUnlessTest[T](block: => T) {

    @aliasFor("unless")
    def ifNot(test: => Boolean) = unless(test)

    def unless(test: => Boolean) = if(!test) block.some else None
  }

  class StringUnless(s: String) {
    def unless(test: Boolean): String = if (!test) s else ""
  }

  class AnyUnless[A](o: A) {
    def unless(test: Boolean): Option[A] = if (!test) Some(o) else None
  }

}
object UnlessWord extends UnlessWord