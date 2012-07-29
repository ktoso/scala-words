package pl.project13.scala.words.verbs

/**
 *
 *
 * The idea for this verb is taken from Clojure's doto:
 * ```(doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2))```
 */
trait DoToVerb {

  def doTo[T](it: T)(block: T => Unit): T = {
    block(it)
    it
  }

}

object DoToVerb extends DoToVerb