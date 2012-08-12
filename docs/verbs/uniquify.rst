Uniquify
========

Use case
--------
You want to get an unique ``List``, and sadly a Set just won't cut it because uniqueness is determined by some key inside the data structure.

Uniquify a List, based on some Key
----------------------------------
Given some list of ``Data``, you want to get all unique Data elements, where uniqueness is determined by it's ``a`` value.

.. code-block:: scala

  case class Data(a: Int, b: Int)

  import UniquifyVerb._

  // given
  val list = Data(1, 1) :: Data(1, 2) :: Data(1, 3) :: Data(2, 3) :: Nil

  // when
  val uniques = list.uniquifyOn(_.a)

  // then
  uniques should have length (2)
  uniques should (contain (Data(1, 3)) and contain (Data(2, 3)))

Uniquify by merging data
------------------------
Imagine you have a stream of events, such as shopping cart operations you want to play back and uniquify on some key.
You may need to merge some events, using some kind of business logic ("don't remove items, just add them all" - for example),
using ``uniquifyByMerge`` you can easily do this:

.. code-block:: scala

  // given
  val list = Data(1, date = 1) :: Data(1, date = 2) :: Data(1, date = 3) :: Data(2, date = 3) :: Nil

  // when
  // the merge strategy here is "select newest"
  val uniques = list.uniquifyByMerge(onKey = _.a) { case (l, r) =>
    info("Merge: [%s] and [%s] ".format(l, r))
    if(l.date > r.date) l else r
  }

  // then
  uniques should have length (2)
  uniques should (contain (Data(1, 3)) and contain (Data(2, 3)))


Uniquify other collections
--------------------------
A basic uniquify is also available for other collections, such as Queues etc.
Just use a plain ``uniquified``.

.. code-block:: scala

  // given
  val nums = 1 :: 2 :: 2 :: 3 :: Nil

  // when
  val uniquified = nums.uniquified

  // then
  uniquified should have length (3)
  uniquified should (contain (1) and contain (2) and contain (3))
