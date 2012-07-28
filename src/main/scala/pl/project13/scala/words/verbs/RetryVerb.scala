package pl.project13.scala.words.verbs


import scala.collection._

trait RetryVerb {

  /**
   * Try to execute a block (for a result) {@code times} times, and return the first successful result.
   * Otherwise, collect thrown exceptions and return their list (with max length = {@code times})
   *
   * @param times how many times we should try to execute the function
   * @param beforeEach will be called before every try to call {@code block}
   * @param onException will be called on each failure (thrown exception), use it to log messages for example
   */
  def retry[T](times: Long,
               beforeEach: (Int) => Unit = {(n) =>},
               onException: (Int, Boolean, Throwable) => Unit = {(n, willRetry, exception) =>})
              (block: => T): Either[Seq[Throwable], T] = {
    val exceptions = new mutable.ListBuffer[Throwable]
    var n = 1

    while (n <= times) {
      try {
        beforeEach(n)
        return Right(block)
      } catch {
        case e =>
          onException(n, n != times, e)
          exceptions += e
      }

      n += 1
    }

    Left(exceptions.toSeq)
  }

}

object RetryVerb extends RetryVerb