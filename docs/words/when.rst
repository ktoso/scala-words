When
====

Use case
--------
When should be treated as an extension of the traditional ``if`` statement, and as such, it should not be over used when a simple if is enough.

It can however do more than if, like having an automatic else clause for values, turning them into Options or for Strings. Another notable feature is allowing a syntax similiar to Ruby's if modifier (code if condition).

.. note:: ``When`` and ``Unless`` share functionality. All you can do with Unless works with When too. You can think of When like an if statement, and unless like an negated if statement.

When for strings
------------------

.. code-block:: scala

  import WhenWord._

  val itIsALie = false
  val msg = "the cake is %sa lie.".format("NOT ".when(itIsALie))
  assert { msg == "the cake is a lie." }

When on values
----------------

On other values, it will generate an option, depenting on wether the condition was met or not.

.. code-block:: scala

  // given
  val value = 12345
  val allGreen = true

  // when
  val got = value when allGreen

  // then
  got should equal (Some(12345))

.. code-block:: scala

  // given
  val value = 200
  val allGreen = false

  // when
  val got = value when allGreen

  // then
  got should equal (None)

When on code blocks
---------------------
Using this trick, you may use ``WhenWord`` just like you would use the ruby if modifier (code if something).

.. code-block:: scala

  import WhenWord._

  val noErrors = true
  { logger.info("Uff, no errors found!") } when noErrors

The above code will execute only if the computationWentFine value is `false`.
This syntax may sometimes seem a bit misleading, so use it with caution.

Such code block, returns the value returned by the block, but obviously wrapped in an Option, as it may not have been executed at all:

.. code-block:: scala

  import WhenWord._

  val noErrors = false
  val value: Option[String] = { "Errors!" } unless noErrors

  value should equal (None)
