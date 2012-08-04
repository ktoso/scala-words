Timed
=====

Use Case
--------

You want to check how long some portion of your code takes to complete.

You can either keep this information as a number, or automatically log it if it's too high.


.. note:: **Duration** is available in both Akka and Scala 2.10, but sadly not before, so for now ScalaWords uses a copied version of the Scala 2.10 implementaiton. It will be removed once more projects are using Scala 2.10.

timed
-----
The simplest version of ``timed`` returns a tuple, with both the time (a ``Stopwatch`` instance) the code took to execute, and the computed value. Exceptions are not eaten, they'll be thrown up as you'd expect.

.. code:: scala

  val (time, value) = timed {
    Thread.sleep(100)
    2 + 2
  }

  println("Exection took: " + time)
  assert value == 4


timedAndLogged
--------------
One of many examples you might want to use this *Verb* is to log an information message if some block of code is taking too long to complete. You could do this on a method level using AOP, but just using this verb is way easier, and code weaving is something you have to think twice before using anyway ;-)

.. code:: scala

  import com.weiglewilczek.sfl4s.Logger

  object Test extends Logger with TimedVerb {

    val hardMathProblemSolution = timedAndLogger(logger, "Hard math problem") {
      // DEBUG: Starting execution of timed block: [Hard math problem]
      Thread.sleep(100)
      2 + 2
      // INFO: Timed [Hard math problem] took: 100ms
    }

    assert 4 == hardMathProblemSolution
  }

.. note::  ScalaWords currently supports the **com.weiglewilczek.slf4s.Logger**, I'm thinking about a way to allow any logger to work with this API (without the use of *slow* Structural Types).

Log when certain conditions are met
--------------------------------
Sometimes you have a LOT of log messages like the above, which you don't really care about.
Let's say most requests are handled within 5ms, but sometimes you get a spike and they take 100ms to process,
that's something worth logging as *WARN*.

.. code:: scala

  timedAndLoggedIf(_.elapsedMillis > 100)(logger, "Long Operation") { doThings() }

Testing with TimedVerb
--------------------
As with anything that's Time related, you're probably already thinking "how would I test such code?".
``Timed`` is smart enough to allow you to mock out the ticker it's using, like this:

.. code:: scala
  
  import com.google.common.base.Ticker

  trait TestTimedVerb extends TimedVerb {
    override lazy val ticker = new Ticker {
      def read() = 0L
    }
  }

Override the messages
---------------------
You can override the messages being created by this trait.

Setting loglevels manualy is not yet supported, but I'll work on a way to use any logger - and then via callbacks you'll be able to log on any level and any message.
