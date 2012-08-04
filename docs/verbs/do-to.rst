Do To
==========

Use Case
--------

You want to apply some operation to an object and then return it.
Sadly, the operation returns Unit (like a setter for example).

You'll typically find such cases when working with  not functional code,
a common example would be non-fluent Plain Old Java Objects from external libraries.

Simple application
------------------
Although the reducing of used linenumbers here depends on how you format your code,
sometimes you may find using the ``doTo`` verb more readable. We don't suggest over using it though.

With the ``doTo`` verb:

.. code-block:: scala

  def makeAwesomeThing =
    doTo(new Thing) { _.setAwesome(true) }

Without the verb it would be 3 lines:

.. code-block:: scala

  def makeAwesomeThing = {
    val t = new Thing
    t.setAwesome(true)
    t
  }

Multiple application
--------------------
Using this ``doTo`` style allows you to use POJOs which dont expose fluent APIs in a "almost like fluet" manner:

.. code-block:: scala

  val modifiedPojo = doTo(new LongPojo) (
    _.trim(),
    _.setAvailable(false),
    _.setUsable(true)
  )

.. note:: You have to use () braces instead of curly braces {} in order to use ``DoToVerb`` in this fashion.
