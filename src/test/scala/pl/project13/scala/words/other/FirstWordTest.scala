package pl.project13.scala.words.other

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class FirstWordTest extends FlatSpec with ShouldMatchers
  with FirstWord {

  behavior of "FirstWord"

  it should "find first not none and not call other providers" in {
    // given
    import SomeWord._

    var getSomeCalledTimes = 0
    def getNone(): Option[String] = None
    def getSome(): Option[String] = {
      getSomeCalledTimes += 1
      "some".some
    }

    // when
    val got = firstNotNone(getNone, getNone, getSome, getSome)

    // then
    got should equal (Some("some"))
    getSomeCalledTimes should equal (1)
  }


}
