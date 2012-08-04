package pl.project13.scala.words.verbs

import org.scalatest.{OneInstancePerTest, FlatSpec}
import org.scalatest.matchers.ShouldMatchers
import collection.mutable

class AskVerbTest extends FlatSpec with ShouldMatchers
  with OneInstancePerTest
  with AskVerb {

  val waitingForIn = mutable.Queue[String]()

  override private[verbs] def readLine(msg: String, args: Any*) = {
    val line = waitingForIn.dequeue()
    info(msg + " << " + line)
    line
  }

  behavior of "AskVerb"

  it should "should ask for an Enum (by name)" in {
    // given
    object Enum extends Enumeration {
      type Enum = Value
      val A, B, C = Value
    }

    waitingForIn.enqueue("B")

    waitingForIn.length should equal (1)

    // when
    val gotEnum = askForEnum(Enum, "Select one: ")

    // then
    gotEnum should equal (Enum.B)
  }

  it should "should ask for an Enum (by id)" in {
    // given
    object Enum2 extends Enumeration {
      type Enum = Value
      val A, B, C = Value
    }

    waitingForIn.enqueue("2")

    // when
    val gotEnum = askForEnum(Enum2, "Select one: ")

    // then
    gotEnum should equal (Enum2.C)
  }

  it should "ask for a number in range [1; 10]" in {
    // given
    val nums = 1 to 10

    waitingForIn.enqueue("12", "10")

    // when
    val num = askForIntIn(nums, "Number?")

    // then
    num should equal (10)
  }


}
