Unless
======

Use case
--------
Unless is simply the inverse of ``if``. There is also an alias available, if you're not too comfortable with "unless", you can use "``ifNot``". That's the boring use case, and surely, not always recommended - just use if.

The nicer usecase comes with the implicit on ``String`` and ``AnyRef`` for which you can "return the given value unless something" (for strings "" will be returned, for other values None).

Unless for strings
==================

.. code-block:: scala

  import UnlessWord._

  val itIsALie = false
  val msg = "the cake is %sa lie.".format("NOT ".unless(itIsALie))
  assert { msg == "the cake is NOT a lie." }

Unless instead of if
====================

.. code-block:: scala

  import UnlessWord._
  
  val really = false
  val executed = "executed".unless(really)
  assert(executed == Some(true))

Unless on values
================

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
