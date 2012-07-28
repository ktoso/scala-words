package pl.project13.scala.words.verbs

trait DoToVerb {

  def doTo[T](it: T)(block: T => Unit): T = {
    block(it)
    it
  }

}

object DoToVerb extends DoToVerb