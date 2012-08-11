First
=====

Use Case
--------
You have multiple providers, returning ``Option[String]`` and you want to try them, in order, until one of them returns ``Some[String]``.

This may be useful during "fallback" constructions.

First not None
--------------
Typically you would have some data providers and

.. code-block:: scala

  import SomeWord._

  // given
  var getSomeCalledTimes = 0
  def getNone(): Option[String] = None
  def getSome(): Option[String] = {
    getSomeCalledTimes += 1
    "some".some
  }

  // when
  val got = firstNotNone(getNone, getNone, getSome, getSome)

  // then
  got should equal (Some("some"))
  getSomeCalledTimes should equal (1)
