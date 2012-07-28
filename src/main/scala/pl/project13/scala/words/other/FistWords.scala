package pl.project13.scala.words.other

package tv.yap.common.util

object FirstVerb {

  /**
   * Tries to obtain a T instance by trying all obtainers (sequentially).
   * If one of the obtainers returns 'Some[T]' it will shortcircut (and not call the remaining obtainers)
   * and return this Some.
   *
   * If after checking all obtainers, still no Some was found, None is returned.
   */
  def firstNotNone[T](obtainers: (() => Option[T])*): Option[T] = {
    for(obtainer <- obtainers)
      obtainer() match {
        case Some(it) => return Some(it)
        case _ =>
      }

    None
  }

}

