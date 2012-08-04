package pl.project13.scala.words.verbs

import org.scalatest.matchers.ShouldMatchers
import com.google.common.base.Ticker
import org.scalatest.FlatSpec
import com.weiglewilczek.slf4s.Logger

class TimedVerbTest extends FlatSpec with ShouldMatchers {

  behavior of "TimedVerb"

  it should "measure time spent in block" in {
    import TimedVerb._

    val (stopwatch, value) = timed {
      Thread.sleep(113)
      "string"
    }

    value should equal("string")
    info("took: " + stopwatch.elapsedMillis)
    (stopwatch.elapsedMillis > 0) should be(true)
  }

  it should "should log time spent in block on info" in {
    // given
    var debugLoggedMsg = ""
    var infoLoggedMsg = ""

    val myTicker = new Ticker { def read() = 1000 }
    val timedVerb = new TimedVerb {
      override lazy val ticker = myTicker
    }

    // when
    timedVerb.timedAndLogged(new Logger {
      protected val slf4jLogger = null

      override def debug(msg: => String) {
        debugLoggedMsg = msg
      }
      override def info(msg: => String) {
        infoLoggedMsg = msg
      }
    }, "MyBlock") {
      // act like you did something
    }

    // then
    infoLoggedMsg should include regex ("Timed \\[MyBlock\\] took: [0-9]+".r)
  }

  it should "not log if the time was 0ms" in {
    var debugLoggedMsg = ""
    var infoLoggedMsg = ""

    val myTicker = new Ticker { def read() = 0 }
    val timedVerb = new TimedVerb {
      override lazy val ticker = myTicker
    }

    // when
    timedVerb.timedAndLoggedIfNot(_.elapsedMillis == 0)(new Logger {
      protected val slf4jLogger = null

      override def debug(msg: => String) {
        debugLoggedMsg = msg
      }
      override def info(msg: => String) {
        infoLoggedMsg = msg
      }
    }, "MyBlock") {
      // act like you did something
    }

    // then
    infoLoggedMsg should equal ("")
  }

  it should "log if the time was above 10ms" in {
    var debugLoggedMsg = ""
    var infoLoggedMsg = ""

    val myTicker = new Ticker { def read() = 100 }
    val timedVerb = new TimedVerb {
      override lazy val ticker = myTicker
    }

    // when
    timedVerb.timedAndLoggedIfNot(_.elapsedMillis > 10 )(new Logger {
      protected val slf4jLogger = null

      override def debug(msg: => String) {
        debugLoggedMsg = msg
      }
      override def info(msg: => String) {
        infoLoggedMsg = msg
      }
    }, "MyBlock") {
      // act like you did something
    }

    // then
    infoLoggedMsg should include regex ("Timed \\[MyBlock\\] took: [0-9]+".r)
  }

}
