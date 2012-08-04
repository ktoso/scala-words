package pl.project13.scala.words.verbs

import annotation.tailrec
import scala.Int
import com.google.common.annotations.VisibleForTesting

trait AskVerb {

  @VisibleForTesting
  private[verbs] def readLine(msg: String, args: Any*): String =
    scala.Predef.readLine(msg, args)

  val Yes = true
  val No = false

  private val NumberPattern = "(\\d+)".r

  def askForInt(prompt: String): Int = askForLong(prompt).toInt

  def askForString(prompt: String): String = readLine(prompt + " ")

  @tailrec
  final def askForLong(prompt: String): Long = {
    val text = readLine(prompt + " ").trim
    if (text == "" || text == null)
      askForLong(prompt)
    else {
      val num = try { Some(text.toLong) } catch { case _: Exception => None }

      if (num.isDefined) num.get else askForLong(prompt)
    }
  }

  @tailrec
  final def askForIntIn(range: Range, prompt: String): Int = {
    val promptWithRange = prompt + " (x ∈ [%d; %d])".format(range.start, range.end)
    val got = askForInt(promptWithRange)

    if (range.contains(got)) got
    else askForIntIn(range, prompt)
  }

  @tailrec
  final def askForLongIn(range: Range, prompt: String): Int = {
    val promptWithRange = prompt + " (x ∈ [%d; %d])".format(range.start, range.end)
    val got = askForInt(promptWithRange)

    if (range.contains(got)) got
    else askForLongIn(range, prompt)
  }

  @tailrec
  final def askForStringIn(range: Set[String], prompt: String): String = {
    val promptWithRange = prompt + " (x ∈ %s)".format(range.mkString("[", ", ", "]"))
    val got = askForString(promptWithRange)

    if (range.contains(got)) got
    else askForStringIn(range, prompt)
  }

  @tailrec
  final def askForEnum[Enum <: Enumeration](range: Enum, prompt: String): Enum#Value = {
    import pl.project13.scala.words.other.SomeWord._

    val validEnumIds = 0 until range.maxId
    val validEnums = range.values

    val printableEnumChoices = validEnums.zipWithIndex.map(i => "%d:%s".format(i._2, i._1)).mkString(",")
    val promptWithRange = prompt + " (x ∈ %s)".format(printableEnumChoices)

    val got = askForString(promptWithRange) match {
      case NumberPattern(num) if validEnumIds.contains(num.toInt) => range(num.toInt).some
      case s if validEnums.map(_.toString).contains(s) => range.withName(s).some
      case s => None
    }

    if(got.isDefined) got.get else askForEnum(range, prompt)
  }

  def ask(prompt: String, default: String) = {
    val text = readLine(prompt + " [" + default + "] ")
    if (text == "" || text == null) default else text
  }

  def ask(prompt: String, default: Long) = {
    val text = readLine(prompt + " [" + default + "] ")
    if (text == "" || text == null) default else text.toLong
  }

  @tailrec
  final def ask(prompt: String): Boolean = {
    val tip = "[y/N]"
    val text = readLine(prompt + " " + tip + " ")
    text.toLowerCase match {
      case "y" => true
      case "n" => false
      case _ => ask(prompt)
    }
  }

  def ask(prompt: String, default: Boolean) = {
    val tip = if (default) "[Y/n]" else "[y/N]"
    val text = readLine(prompt + " " + tip + " ")
    if (text == "" || text == null) default
    else text.toLowerCase match {
      case "y" | "yes" => true
      case "n" | "no" => false
      case _ => default
    }
  }

}

object AskVerb extends AskVerb
