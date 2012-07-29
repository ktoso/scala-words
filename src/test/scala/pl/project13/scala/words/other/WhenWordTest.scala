package pl.project13.scala.words.other

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class WhenWordTest extends FlatSpec with ShouldMatchers
  with WhenWord {

  behavior of "WhenWord"

  it should "print '' instead of a String when(false)" in {
    // given
    val s = "Hello"
    val conditionIsMet = false

    // when
    val got = s.when(conditionIsMet)

    // then
    got should equal ("")
  }

  it should "print the String when(false)" in {
    // given
    val s = "Hello"
    val conditionIsMet = true

    // when
    val got = s.when(conditionIsMet)

    // then
    got should equal (s)
  }

  "on Option[A]" should "return None when(false)" in {
    // given
    val option = Option("Hello")
    val conditionIsMet = false

    // when
    val got = option.when(conditionIsMet)

    // then
    got should equal (None)
  }

  it should "return the Option when(true)" in {
    // given
    val option = Option("Hello")
    val conditionIsMet = true

    // when
    val got = option.when(conditionIsMet)

    // then
    got should equal (option)
  }
}
