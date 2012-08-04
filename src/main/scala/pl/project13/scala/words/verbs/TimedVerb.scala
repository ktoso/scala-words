package pl.project13.scala.words.verbs

import com.google.common.base.{Ticker, Stopwatch}
import com.weiglewilczek.slf4s.Logger
import pl.project13.scala.concurrent.util.Duration

trait TimedVerb {

  lazy val ticker: Ticker = Ticker.systemTicker

  def timed[T](block: => T): (Stopwatch, T) = {
    val stopwatch = new Stopwatch(ticker).start()
    val value = block
    (stopwatch.stop(), value)
  }

  def timedAndLoggedIf[T](test: Stopwatch => Boolean)
                             (log: Logger, blockName: String, warnIfLongerThan: Option[Duration] = None, stopwatch: Stopwatch = new Stopwatch(ticker))
                             (block: => T) = {
    stopwatch.start()

    log.debug(createTimedBlockStartInfoMessage(blockName))

    val it = block

    val took = stopwatch.stop().elapsedMillis()
    lazy val msg = createDoneBlockInfoMessage(blockName, took)

    warnIfLongerThan match {
      case Some(duration) => if (took > duration.toMillis) log.warn(msg) else log.debug(msg)
      case _ if test(stopwatch) => log.info(msg)
      case _ => // ignore
    }

    it
  }

  def timedAndLoggedIfNot[T](test: Stopwatch => Boolean)
                                (log: Logger, blockName: String, warnIfLongerThan: Option[Duration] = None, stopwatch: Stopwatch = new Stopwatch(ticker))
                                (block: => T) = {
    timedAndLoggedIf({ !test(_) })(log, blockName, warnIfLongerThan, stopwatch)(block)
  }

  def timedAndLogged[T](log: Logger, blockName: String, warnIfLongerThan: Option[Duration] = None, stopwatch: Stopwatch = new Stopwatch(ticker))
                       (block: => T) = {
    timedAndLoggedIf(_ => true)(log, blockName, warnIfLongerThan, stopwatch)(block)
  }

  def createTimedBlockStartInfoMessage[T](blockName: String): String = {
    "Starting execution of timed block: [" + blockName + "]"
  }

  def createDoneBlockInfoMessage[T](blockName: String, took: Long): String = {
    "Timed [" + blockName + "] took: " + took + "ms"
  }


}

object TimedVerb extends TimedVerb
