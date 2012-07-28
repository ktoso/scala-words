package pl.project13.scala.words.verbs

import annotation.tailrec

trait AskVerb {

  val Yes = true
  val No = false

  def askForInt(prompt: String) = askForLong(prompt).toInt

  @tailrec
  final def askForLong(prompt: String): Long = {
    val text = readLine(prompt + " ").trim
    if (text == "" || text == null)
      askForLong(prompt)
    else
      text.toLong
  }

  @tailrec
  final def askForIntWithin(prompt: String, range: Range): Int = {
    val promptWithRange = prompt + " (x ∈ [%d; %d])".format(range.start, range.end)
    val got = askForInt(promptWithRange)

    if (range.contains(got)) got
    else askForIntWithin(prompt, range)
  }

  def ask(prompt: String, default: String) = {
    val text = readLine(prompt + " [" + default + "] ")
    if (text == "" || text == null) default else text
  }

  def ask(prompt: String, default: Long) = {
    val text = readLine(prompt + " [" + default + "] ")
    if (text == "" || text == null) default else text.toLong
  }

  def ask(prompt: String, default: Boolean) = {
    val tip = if (default) "[Y/n]" else "[y/N]"
    val text = readLine(prompt + " " + tip + " ")
    if (text == "" || text == null) default
    else text.toLowerCase match {
      case "y" => true
      case "n" => false
      case _ => default
    }
  }

}

object AskVerb extends AskVerb
