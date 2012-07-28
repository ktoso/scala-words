package pl.project13.scala.words.verbs

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, FlatSpec}

class RetryVerbTest extends FlatSpec with ShouldMatchers
  with BeforeAndAfter {

  import RetryVerb._

  val N = 3

  var incCountCalled = 0
  var incBeforeCalled = 0
  var incExceptCalled = 0

  var throwedTimes = 0
  def throwsTwoTimesThenIncCount = () => {
    if(throwedTimes < 2) {
      throwedTimes += 1
      throw new Exception("I failed")
    }

    incCount()
  }

  def incCount = () => {
    incCountCalled += 1
    incCountCalled
  }

  def incExcept = (n: Int, willRetry: Boolean, ex: Throwable) => incExceptCalled += 1
  def incBefore = (n: Int) => incBeforeCalled += 1

  behavior of "RetryVerb"

  before {
    incCountCalled = 0
    incExceptCalled = 0
    incBeforeCalled = 0
    throwedTimes = 0
  }

  it should "not retry if it's not required (function doesn't throw)" in {
    // when
    val it = retry(N) { incCount() }

    // then
    it should equal (Right(1))
  }

  it should "call onException 2 times, but finish OK and return Left" in {
    // when
    val it = retry(N, onException = incExcept) { throwsTwoTimesThenIncCount() }

    // then
    incExceptCalled should equal (2)
    it should equal (Right(1))
  }

  it should "call beforeEach 3 times" in {
    // when
    val it = retry(N, beforeEach = incBefore) { throw new Exception("") }

    // then
    incBeforeCalled should equal (3)
  }

  it should "return 3 exceptions" in {
    // given
    val exception = new Exception("I failed")

    // when
    val it = retry(N) { throw exception }

    // then
    it should equal (Left(List(exception, exception, exception)))
  }

}
