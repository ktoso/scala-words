package pl.project13.scala.words.other

trait ProgressableAdverb {

  def TotalItems: Long

  def InitialPrint = true

  private var lastPercent = 0L
  private var progressAt = 0L

  if (InitialPrint) printProgress(0, 0, TotalItems)

  def notifyProgress() {
    notifyProgress(1)
  }

  def notifyProgress(howMuchProgress: Long) {
    progressAt += howMuchProgress
    val currentPercent = progressAt * 100L / TotalItems

    if (currentPercent != lastPercent) {
      printProgress(currentPercent, progressAt, TotalItems)
      lastPercent = currentPercent

      onProgressedPercent(currentPercent)
    }
  }

  def printProgress(currentPercent: Long, progressAt: Long, total: Long) {
    println(currentPercent + "% (" + progressAt + "/" + total + ")")
  }

  /**
   * Extension point - will be called each time progress of 1% more has been made.
   * In theory should be called 100 times, but that is not guarenteed as you may do more progress than 100%!
   */
  def onProgressedPercent(currentPercent: Long) = ()
}
