package pl.project13.scala.words.verbs

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class UniquifyVerbTest extends FlatSpec with ShouldMatchers
  with UniquifyVerb {

  behavior of "UniquifyVerb"

  case class Data(a: Int, b: Int)

  it should "uniquify a list given a predicate (by implicits)" in {
    // given
    val list = Data(1, 1) :: Data(1, 2) :: Data(1, 3) :: Data(2, 3) :: Nil

    // when
    val uniques = list.uniquifyOn(_.a)

    // then
    uniques should have length (2)
    uniques should (contain (Data(1, 3)) and contain (Data(2, 3)))
  }


  "uniquifyByMerge" should "use the merge function to resolve conflicts" in {
    // given
    val list = Data(1, 1) :: Data(1, 2) :: Data(1, 3) :: Data(2, 3) :: Nil

    // when
    val uniques = list.uniquifyByMerge(onKey = _.a) { case (l, r) =>
      info("Merge: [%s] and [%s] ".format(l, r))
      l
    }

    // then
    uniques should have length (2)
    uniques should (contain (Data(1, 1)) and contain (Data(2, 3)))
  }

  it should "resolve conflicts as expected - 'in order'" in {
    // given
    val list = Data(1, 1) :: Data(2, 1) :: Data(1, 2) :: Data(1, 3) :: Data(2, 3) :: Data(2, 53) :: Nil

    // when
    val uniques = list.uniquifyByMerge(onKey = _.a) { case (l, r) =>
      info("Merge: [%s] and [%s] ".format(l, r))
      l
    }

    // then
    uniques should have length (2)
    uniques should (contain (Data(1, 1)) and contain (Data(2, 1)))
  }
}

