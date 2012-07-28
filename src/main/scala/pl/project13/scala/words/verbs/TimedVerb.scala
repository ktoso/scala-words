package pl.project13.scala.words.verbs

import com.google.common.base.{Ticker, Stopwatch}
import com.weiglewilczek.slf4s.Logger

// todo problem as we'd require akka to use duration... scala 2.10 would be cool i guess
trait TimedVerb {

  lazy val ticker: Ticker = Ticker.systemTicker

  def timed[T](block: => T): (Stopwatch, T) = {
    val stopwatch = new Stopwatch(ticker).start()
    val value = block
    (stopwatch.stop(), value)
  }

  def timedAndLoggedIfTime[T](test: Stopwatch => Boolean)
                             (log: Logger, blockName: String, warnIfLongerThan: Option[Duration] = None, stopwatch: Stopwatch = new Stopwatch(ticker))
                             (block: => T) = {
    stopwatch.start()

    log.debug("Starting execution of timed block: [" + blockName + "]")

    val it = block

    val took = stopwatch.stop().elapsedMillis()
    lazy val msg = "Timed [" + blockName + "] took: " + took + "ms"

    warnIfLongerThan match {
      case Some(duration) => if (took > duration.toMillis) log.warn(msg) else log.debug(msg)
      case _ if test(stopwatch) => log.info(msg)
      case _ => // ignore
    }

    it
  }

  def timedAndLoggedIfTimeNot[T](test: Stopwatch => Boolean)
                                (log: Logger, blockName: String, warnIfLongerThan: Option[Duration] = None, stopwatch: Stopwatch = new Stopwatch(ticker))
                                (block: => T) = {
    timedAndLoggedIfTime({ !test(_) })(log, blockName, warnIfLongerThan, stopwatch)(block)
  }

  def timedAndLogged[T](log: Logger, blockName: String, warnIfLongerThan: Option[Duration] = None, stopwatch: Stopwatch = new Stopwatch(ticker))
                       (block: => T) = {
    timedAndLoggedIfTime(_ => true)(log, blockName, warnIfLongerThan, stopwatch)(block)
  }
}

object TimedVerb extends TimedVerb
