package pl.project13.scala.words.other

import pl.project13.scala.words.annotation.aliasFor

trait WhenWord {

  import SomeWord._

  implicit def stringWhen(s: String) = new StringWhen(s)

  implicit def stringWhen[A](s: Option[A]) = new OptionWhen(s)

  implicit def blockWithWhen[T](execute: => T) = new HasBlockWithWhen[T](execute)

  class StringWhen(s: String) {
    def when(test: Boolean): String = if (test) s else ""
  }

  class OptionWhen[A](o: Option[A]) {
    def when(test: Boolean): Option[A] = if (test) o else None
  }

  class HasBlockWithWhen[T](block: => T) {
    def when(test: => Boolean) = if (test) block.some else None
  }
}

object WhenWord extends WhenWord