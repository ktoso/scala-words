package pl.project13.scala.words.verbs

import org.scalatest.{OneInstancePerTest, FlatSpec}
import org.scalatest.matchers.ShouldMatchers
import collection.mutable

class AskVerbTest extends FlatSpec with ShouldMatchers
  with OneInstancePerTest
  with AskVerb {

  var waitingForIn = mutable.Queue[String]()

  override private[verbs] def readLine(msg: String, args: Any*) = waitingForIn.dequeue()

  behavior of "AskVerb"

  it should "should ask for an Enum" in {
    // given
    object Enum extends Enumeration {
      type Enum = Value
      val A, B, C = Value
    }

    waitingForIn.enqueue("B")

    // when
    val gotEnum = askForEnum(Enum, "Select one: ")

    // then
    gotEnum should equal (Enum.B)
  }
}
