Unless
======

Use case
--------
Unless is simply the inverse of ``if``. There is also an alias available, if you're not too comfortable with "unless", you can use "``ifNot``". That's the boring use case, and surely, not always recommended - just use if.

The nicer usecase comes with the implicit on ``String`` and ``AnyRef`` for which you can "return the given value unless something" (for strings "" will be returned, for other values None).

Unless for strings
------------------

.. code-block:: scala

  import UnlessWord._

  val itIsALie = false
  val msg = "the cake is %sa lie.".format("NOT ".unless(itIsALie))
  assert { msg == "the cake is NOT a lie." }

Unless instead of if
--------------------

Unless can be used as simple replacemet for ``if(!test)``:

.. code-block:: scala

  import UnlessWord._
  
  val result: Option[String] = unless (false) {
    "It"
  }

  result should equal (Some("It"))

As it returns an option, you can use the following idiom to implement something like an "else":

.. code-block:: scala

  // when
  val it = unless (true) {
    "Value"
  } orElse {
    Some("Else")
  }

  // then
  it should equal (Some("Else"))

Although personally I would discurrage using this construct, in favor of a plain if.
Take a look at using blocks with unless, more "ruby style" to see it shine a bit more.

Unless on values
----------------

On other values, it will generate an option, depenting on wether the condition was met or not.

.. code-block:: scala

  // given
  val value = 12345
  val errorsOccurred = true

  // when
  val got = value unless errorsOccurred

  // then
  got should equal (None)

.. code-block:: scala

  // given
  val value = 200
  val errorsOccurred = false

  // when
  val got = value unless errorsOccurred

  // then
  got should equal (Some(200))

Unless on code blocks
---------------------
Using this trick, you may use UnlessWord just like you would the `Ruby unless modifier syntax`_.

.. _Ruby unless modifier syntax: http://www.tutorialspoint.com/ruby/ruby_if_else.htm

.. code-block:: scala

  import Unless._

  { logger.error("We had some errors!") } unless computationWentFine
  { logger.error("We had some errors!") } ifNot computationWentFine 

The above code will execute only if the computationWentFine value is `false`.
This syntax may sometimes seem a bit misleading, so use it with caution.

Such code block, returns the value returned by the block, but obviously wrapped in an Option, as it may not have been executed at all:

.. code-block:: scala

  import Unless._

  val noErrorsFound = true
  val value: Option[String] = { "Errors!" } unless noErrorsFound

  value should equal (None)
