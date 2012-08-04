Ask
===

Use Case
--------
In simple command line applications you often find yourself writing `println("Do you want to continue?")` then read a boolean or String, and match on it... So far so good, but this code tends to duplicate around the codebase, that's what ``AskVerb`` is for.

Ask's goal is to: Simplify typesafe command line interaction with the user.

Ask the user
------------
For each of those methods, if the input the user gives is not valid, he'll be asked again.
When nothing is typed in by the user, the default is used - if there is no default, he'll be asked again.

Also note that the default value will be added ather your question automatically so when you ask:

.. code-block:: scala

  ask("Hello?", false) // prints "Hello? [y/N]"

Other examples of ask are listed bellow, but it's always a good idea to take a look at the sourcecode if in doubt.

.. code-block:: scala

  object Test extends App with AskVerb {
    ask("Are you OK?") // asks until "y" or "n" are given

    ask("Yes or No?", No) // Yes or No? [y/N]
    ask("Yes or No?", Yes) // Yes or No? [Y/n]

    ask("Number?", 12) // Number? [12]

    ask("Long?", 12L)
  }

Limiting choice
---------------

You can limit the choices the user can make on a range of numbers, set of strings or better - based an Enumeration.

.. code-block:: scala

  object Test extends App with AskVerb {
    
    val theNumber = askForIntIn(1 to 10, "Enter a number?") 
    // prints "Enter a number? (x ∈ [1; 10])"

    assert(theNumber > 0)
    assert(theNumber < 11)
  }

Limiting choice by using  Enumerations
--------------------------------------
Instead of ``Sets`` and ``Ranges`` which are quite fine moste of the time, you can also use Enumerations,
to stay a bit more verbose about what you're actually selecting.

Note that the returned type is the selected Enumeration value - we're typesafe about it.

.. code-block:: scala

  object Test extends App with AskVerb {
    
    object Mode extends Enumeration {
      type Mode = Value
      val Single, Double = Value
    }

    val selected: Mode.Value = askForEnum(Mode, "Which mode?")
    // prints "Which mode? (x ∈ [0:Single, 1:Double])"
    // the user may select by 1, 2 or "Single", "Double"

    selected match {
      case Mode.Single => // ...
      case Mode.Double => // ...
    }

  }

