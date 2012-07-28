package pl.project13.scala.words.other

import org.scalatest.{FlatSpec, FunSuite, OneInstancePerTest}
import org.scalatest.matchers.ShouldMatchers
import pl.project13.scala.words.verbs.DoToVerb

class ProgressableTest extends FlatSpec with ShouldMatchers
  with OneInstancePerTest
  with Progressable {

  val MaxItems = 1000

  type CurrentItemsType = Long
  var progressPrinted: List[CurrentItemsType] = Nil

  override def printProgress(items: Long) {
    progressPrinted = items :: progressPrinted
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
