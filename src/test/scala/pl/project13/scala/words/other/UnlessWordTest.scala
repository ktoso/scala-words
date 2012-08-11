package pl.project13.scala.words.other

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class UnlessWordTest extends FlatSpec with ShouldMatchers
  with UnlessWord {

  behavior of "UnlessWord"

  it should "print '' instead of a String when(false)" in {
    // given
    val s = "Hello"
    val conditionIsMet = false

    // when
    val got = s.unless(conditionIsMet)

    // then
    got should equal(s)
  }

  it should "print the String when(false)" in {
    // given
    val s = "Hello"
    val conditionIsMet = true

    // when
    val got = s.unless(conditionIsMet)

    // then
    got should equal("")
  }

  "on Option[A]" should "return None when(false)" in {
    // given
    val option = "Hello"
    val conditionIsMet = false

    // when
    val got = option.unless(conditionIsMet)

    // then
    got should equal(option)
  }

  it should "return the Option when(true)" in {
    // given
    val option = 12344
    val conditionIsMet = true

    // when
    val got = option unless conditionIsMet

    // then
    got should equal (None)
  }
}
