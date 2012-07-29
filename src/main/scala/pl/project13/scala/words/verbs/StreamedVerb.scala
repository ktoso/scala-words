package pl.project13.scala.words.verbs

import net.liftweb.mongodb.record.MongoRecord
import net.liftweb.mongodb.record.MongoMetaRecord
import com.mongodb.{DBCursor, DBObject, Bytes}
import net.liftweb.mongodb.MongoDB
import scala.Some

trait StreamedVerb extends MongoStreamedVerb

trait MongoStreamedVerb {

  def stream[T <: MongoRecord[T]](meta: MongoMetaRecord[T], query: DBObject)(callback: T => Unit) {
    stream(meta, Some(query))(callback)
  }

  def stream[T <: MongoRecord[T]](meta: MongoMetaRecord[T], query: Option[DBObject] = None)(callback: T => Unit) {
    MongoDB.useCollection(meta.mongoIdentifier, meta.collectionName) { coll =>
      withCursor(coll.find(query.getOrElse(null))) { cursor =>
        // Making the cursor non-expireable
        cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT)

        while(cursor.hasNext) {
          val next = cursor.next()
          val obj = meta.fromDBObject(next)

          callback(obj)
        }
      }
    }
  }

  def withCursor[T](createCursor: => DBCursor)(f: DBCursor => T) = {
    val c = createCursor
    try {
      f(c)
    } finally {
      c.close()
    }
  }
}

object StreamedVerb extends StreamedVerb