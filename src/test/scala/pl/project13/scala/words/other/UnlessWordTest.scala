package pl.project13.scala.words.other

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class UnlessWordTest extends FlatSpec with ShouldMatchers
  with UnlessWord {

  behavior of "UnlessWord"

  it should "print '' instead of a String when(false)" in {
    // given
    val s = "Hello"

    // when
    val got = s.unless(false)

    // then
    got should equal(s)
  }

  it should "print the String when(false)" in {
    // given
    val s = "Hello"

    // when
    val got = s.unless(true)

    // then
    got should equal("")
  }

  "on Option[A]" should "return None when(false)" in {
    // given
    val option = "Hello"

    // when
    val got = option.unless(false)

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

  it should "execute a block of code when unless(true)" in {
    // given

    // when
    val value = { info("Executing..."); Some(true) } unless true

    // then
    value should equal (None)
  }

  it should "not execute a block of code when unless(true)" in {
    // given

    // when
    val value = { info("Executing..."); true } unless false

    // then
    value should equal (Some(true))
  }
}
