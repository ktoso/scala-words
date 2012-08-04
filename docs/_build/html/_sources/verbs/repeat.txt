Retry
=====

Use Case
--------
You want to retry a certain operation which may fail. An example would be making a request to some webservice, which like to timeout once a while and a retry will successfully processa after such failure.

You want to perform some operation multiple times.

Retry some operation
--------------------
The most basic use is to try something up to 3 times:

.. code-block:: scala

  object Example extends App with RetryVerb {
    val res: Either[Seq[Throwable], Source] = retry(times = 3) {
      io.Source.fromURI("http://www.example.com")
    }

   res match {
     case Left(errors) => errors foreach { err => sys.error("failed with: " + err) }
     case Right(source) => println("We got the source!")
   }
  }

Note that if the block of code will complete without errors the first time around, retry will not call it again.
Another thing to note is, that if the block failed only the first time, the ``Either`` will not contain errors - it's an Either - so it either failed (3 times) or you got your expected result.

.. note:: In idiomatic Scala code, ``Left`` is considered as the "errors" side of an ``Either`` and ``Right`` is the expected value. Scala Words follows this convention wherever an Either is used. For more details, check out this link: http://www.scala-lang.org/api/current/index.html#scala.Either

Simple tip, how to treat Either as Option
-----------------------------------------
If you don't care about the exceptions being thrown, you can simply map over the *right value*.

.. code-block:: scala
  
  val exceptionsOrString = retry(times = 2) { "Hello" }
  exceptionsOrString.right map { println ("Got : " + _) }

Reacting to failures
--------------------
Since you may be interested in the faulilures, ``Retry`` has a callback you can define to get this info:

.. code-block:: scala

  // with UnlessWord

  val onException = (n: Int, willRetry: Boolean, ex: Throwable) => {
    println("Oh no! Failed %sth time, and will %sretry: %s"
      .format(n, "NOT ".unless(willRetry), ex.getMessage))
  }

  retry(
    times = 3, 
    onException = onException
  ) { doThings() }

Which, as expected, will call ``onException``  whenever doThings throws an exception.

Other hooks: beforeEach and onSuccess
-------------------------------------
These methods give you information about the state of processing the ``Retry``.
You may for example use the ``beforeEach`` hook to log information like "retrying for the nth time...",
or do something else with the number of retries given to this function.

Similarily, onSuccess would be used to log the number of retries that were used to obtain the value.
It will not be called when all calls caused an exception - then onException will be notified for each of them, and the retult will be an ``Left[Seq[Exception]]``.
