package pl.project13.scala.words.other

import org.scalatest.{FlatSpec, FunSuite, OneInstancePerTest}
import org.scalatest.matchers.ShouldMatchers
import pl.project13.scala.words.verbs.DoToVerb

class ProgressableAdverbTest extends FlatSpec with ShouldMatchers
  with OneInstancePerTest
  with ProgressableAdverb {

  val TotalItems = 1000L

  var progressPrinted: List[Long] = Nil

  override def printProgress(currentPercent: Long, progressAt: Long, total: Long) {
    progressPrinted = currentPercent :: Option(progressPrinted).getOrElse(Nil)
  }

  behavior of "Progressable"

  it should "print progress for each percent of progress" in {
    // given

    // when
    1 to 1000 foreach { item => notifyProgress() }

    // then
    progressPrinted should have length (100)
  }
}
