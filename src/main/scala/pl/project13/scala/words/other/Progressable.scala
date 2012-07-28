package pl.project13.scala.words.other

trait Progressable {

  def TotalItems: Long

  def InitialPrint = true

  private var lastPercent = 0L
  private var progressAt = 0L

  printProgress(0)

  def notifyProgress() {
    notifyProgress(1)
  }
  def notifyProgress(howMuchProgress: Long) {
    progressAt += howMuchProgress
    val currentPercent = progressAt * 100L / total

    if (currentPercent != lastPercent) {
      printProgress(currentPercent)
      lastPercent = currentPercent

      onProgressedPercent(currentPercent)
    }
  }

  def printProgress(items: Long) {
    println(currentPercent + "% (" + counter + "/" + total + ")")
  }

  /**
   * Extension point - will be called each time progress of 1% more has been made.
   * In theory should be called 100 times, but that is not guarenteed as you may do more progress than 100%!
   */
  def progressedPercent(currentPercent: Long) = ()
}

object Progressable extends Progressable