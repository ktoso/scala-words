package pl.project13.scala.words.verbs

import collection.mutable

/**
 * Enables you to create unique collections of any type, such as Lists, Maps etc,
 * by simply proving a "key" predicate, so you can filter arbitrary complex objects for your
 * own definition of uniqueness.
 */
trait UniquifyVerb {

  implicit def list2uniquifiable[A](list: List[A]) = new UniquifiableList(list)

  private def identity[A](a: A): A = a

  class UniquifiableList[A](list: List[A]) {
    def uniquify: List[A] = UniquifyVerb.uniquifyBy[A, A](list)(identity)
    def uniquified: List[A] = UniquifyVerb.uniquifyBy[A, A](list)(identity)
    def uniquifyOn[B](onKey: A => B): List[A] = UniquifyVerb.uniquifyBy[A, B](list)(onKey)
    def uniquifyByMerge[B](onKey: A => B)(merge: (A, A) => A): List[A] = UniquifyVerb.uniquifyByMerge[A, B](list)(onKey, merge)
  }

  def uniquify[A](list: List[A]): List[A] =
    uniquifyBy(list)(a => a).toList

  def uniquify[A](traversable: Traversable[A]): Traversable[A] =
      uniquifyBy(traversable) { a => a }

  /**
   * Create a unique copy of the given list. Uniqueness is determined by the `onKey` predicate.
   *
   * You should NOT depend on imlpementation details about which (first? last?) item will be kept
   * in the output collection.
   */
  def uniquifyBy[A, B](list: Traversable[A])(onKey: A => B): List[A] = {
    (mutable.Map[B, A]() ++ list.map(el => (onKey(el) -> el))).values.toList
  }

  def uniquifyByMerge[A, B](list: List[A])(onKey: A => B, merge: (A, A) => A): List[A] = {
    val uniques = uniquifyBy(list)(onKey)
    val uniqueKeys = uniques.map(onKey).toSet

    if (uniqueKeys.size == list.size) {
      return uniques
    }

    // todo not preformant alg
    val notDuplicatedElements = uniques.filter(a => list.count(k => onKey(k) == onKey(a)) == 1).map(a => (onKey(a), a))
    val res = collection.mutable.HashMap[B, A](notDuplicatedElements: _*)

    for(u <- uniqueKeys; if list.count(a => onKey(a) == u) > 1) { // if there are duplicates for this key
      val dups = list.filter(a => onKey(a) == u)
      res(u) = dups.drop(1).foldLeft(dups.head)(merge)
    }

    res.values.toList
  }

}

object UniquifyVerb extends UniquifyVerb
