package pl.project13.scala.words.verbs

import _root_.tv.yap.mongo._
import net.liftweb.mongodb.record.MongoRecord
import net.liftweb.mongodb.record.MongoMetaRecord
import com.mongodb.DBObject
import net.liftweb.mongodb.MongoDB
import scala.Some
import com.mongodb.Bytes

class StreamedVerb {

}

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
}
