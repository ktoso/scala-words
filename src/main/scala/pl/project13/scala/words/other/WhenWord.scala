package pl.project13.scala.words.other

import pl.project13.scala.words.annotation.aliasFor

trait WhenWord {

  import SomeWord._

  implicit def stringWhen(s: String) = new StringWhen(s)

  def blockWithWhen[T](execute: => T) = new HasBlockWithWhen[T](execute)

  class DoNotDoBlockUnlessTest[T](block: => T) {

    @aliasFor("unless")
    def ifNot(test: => Boolean) = unless(test)

    def unless(test: => Boolean) = if(!test) block.some else None
  }

  class StringWhen(s: String) {
    def when(test: Boolean) = if (test) s else ""
  }

  class HasBlockWithWhen[T](block: => T) {
    def when(test: => Boolean) =
      if (test) block.some else None
  }
}

object WhenWord extends WhenWord