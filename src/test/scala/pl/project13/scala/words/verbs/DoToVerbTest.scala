package pl.project13.scala.words.verbs

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class DoToVerbTest extends FlatSpec with ShouldMatchers
  with DoToVerb {

  behavior of "DoToVerb"

  it should "do things to it" in {
    // given
    val input: String = "name"

    // when
    var buildMe: String = ""
    val returned = doTo(input) (
      _.foreach { it => buildMe += it.toUpper }
    )

    // then
    returned should equal (input)
    buildMe should equal (input.toUpperCase)
  }

  it should "allow multiple operations on the same object" in {
    // given
    class It(var counter: Int)
    val it = new It(1)

    // when
    doTo(it) (
      _.counter += 1,
      _.counter *= 3
    )

    // then
    it.counter should equal (6)
  }
}
