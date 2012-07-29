package pl.project13.scala.words.other

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

class SimpleValuesAreSomeTest extends FlatSpec with ShouldMatchers
  with SimpleValuesAreSome {

  behavior of "SimpleValuesAreSome"

  it should "convert Int to some ints" in {
    val a: Some[Int] = 23
  }

  it should "convert Long to Some" in {
    val a: Some[Long] = 34L
  }

  it should "convert Byte to Some" in {
    val a: Some[Byte] = Byte.MinValue
  }

  it should "convert String to Some" in {
    val a: Some[String] = "23456"
  }
}
