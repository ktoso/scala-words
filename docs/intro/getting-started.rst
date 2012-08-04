Getting Started
===============

Dependencies
------------

ScalaWords is designed with the thought that you'll include all dependencies you'll need.

For example: don't need ``MongoStream``? Don't include mogo in your dependencies - you'll never bump into using a Mongo Verb by accident anyway ;-)

What we do require though is Google Guava.

So to include ScalaWords in your build use this snippet:

.. code-block:: file

  // TODO
  val guava = 
  val scalaWords = 

  val rainbow = "pl.project13.scala" % "rainbow" % "0.1"

  dependencies ++= Seq(scalaWords, guava)

How to use Words?
=================

Each Word (with one or two documented exceptions) can be used in one of the following ways:

Via Mixin
---------
The simplest way to use ScalaWords is to mixin the word you need. 
This also has the advantage of clearly letting everyone know you'll be using some word in this file.

Via Import
----------
You can simply import all methods from a word using such construct:

.. code-block:: scala

  import TimedVerb._

Please note that putting this in the imports section of your file may be handy, but it may confuse people with the
typical "*Where did that implicit come from?!*". Instead, try to import just near the place where you'll be using the Word like in the example bellow:

.. code-block:: scala

  class Something(magicIsReal: Boolean) {
    
    def doMagic() = {
      import UnlessWord._
      "Magic is " + "NOT ".unless(magicIsReal) + "real!"
    }
  }

Recommended when you use a lot: Vocabulary pattern
--------------------------------------------------
If you find yourself using the same words over and over again, it may be time to introduce a *Vocabulary*.

It's nothing else than a trait / object pair (like many Words) that will contain some words, 
for easier access - so you won't have to import all words manually each time you need them.

In practive it would look like this:

.. code-block:: scala

  trait Vocabulary extends WhenWord 
    with UnlessWord
    with DoToVerb
    with TimedVerb

  object Vocabulary extends Vocabulary

  class MyRepository extends Logging with Vocabulary

You can create sets of verbs, and mixin your own when needed. It's a nice idea that plays well with the contept
of "words".
