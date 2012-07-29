package pl.project13.scala.words.other

trait SimpleValuesAreSome extends SomeWord {

  implicit def boolean2some(in: Boolean) = Some(in)

  implicit def byte2some(in: Byte) = Some(in)

  implicit def int2some(in: Int) = Some(in)
  implicit def long2some(in: Long) = Some(in)

  implicit def float2some(in: Float) = Some(in)
  implicit def double2some(in: Double) = Some(in)

  implicit def string2some(in: String) = Some(in)
}
