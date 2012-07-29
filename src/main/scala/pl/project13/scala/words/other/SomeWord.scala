package pl.project13.scala.words.other

/**
 * The `SomeWord` may be used to ease creating Some[A].
 *
 */
trait SomeWord {

  implicit def any2canBeSome[T](in: T) = new CanBeSome(in)

  // todo not sure if any2some is a good idea
}

object SomeWord extends SomeWord

class CanBeSome[T] (in: T) {
  def some = Some(in)
}
