package dev.mongocamp.driver.mongodb.sync

import com.typesafe.scalalogging.LazyLogging
import dev.mongocamp.driver.mongodb._
import dev.mongocamp.driver.mongodb.database.{ ConfigHelper, DatabaseProvider }
import dev.mongocamp.driver.mongodb.sync.SyncDirection.SyncDirection
import dev.mongocamp.driver.mongodb.sync.SyncStrategy.SyncStrategy
import org.mongodb.scala.Document
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Updates._

import java.util.Date

object SyncStrategy extends Enumeration {
  type SyncStrategy = Value
  val SyncAll = Value
}

object SyncDirection extends Enumeration {
  type SyncDirection = Value
  val SourceToTarget, TargetToSource, TwoWay = Value
}

case class MongoSyncException(message: String) extends Exception(message)

case class MongoSyncOperation(
    collectionName: String,
    syncDirection: SyncDirection = SyncDirection.SourceToTarget,
    syncStrategy: SyncStrategy = SyncStrategy.SyncAll,
    idColumnName: String = DatabaseProvider.ObjectIdKey
) extends LazyLogging
    with Filter {
  val includes = include(idColumnName, MongoSyncOperation.SyncColumnLastSync, MongoSyncOperation.SyncColumnLastUpdate)

  def excecute(source: DatabaseProvider, target: DatabaseProvider): List[MongoSyncResult] =
    try {
      val sourceInfos: Seq[Document] =
        source.dao(collectionName).find().projection(includes).results(MongoSyncOperation.MaxWait)
      val targetInfos: Seq[Document] =
        target.dao(collectionName).find().projection(includes).results(MongoSyncOperation.MaxWait)

      if (SyncDirection.SourceToTarget == syncDirection) {
        val diff = sourceInfos.diff(targetInfos)
        List(syncInternal(source, target, targetInfos.size, diff))
      }
      else if (SyncDirection.TargetToSource == syncDirection) {
        val diff = targetInfos.diff(sourceInfos)
        List(syncInternal(target, source, sourceInfos.size, diff))
      }
      else if (SyncDirection.TwoWay == syncDirection)
        List(
          syncInternal(source, target, targetInfos.size, sourceInfos.diff(targetInfos)),
          syncInternal(target, source, sourceInfos.size, targetInfos.diff(sourceInfos))
        )
      else
        List(MongoSyncResult(collectionName))
    }
    catch {
      case e: Exception =>
        logger.error(e.getMessage, e)
        List(MongoSyncResult(collectionName, exception = Some(e)))
    }

  private def syncInternal(
      left: DatabaseProvider,
      right: DatabaseProvider,
      countBefore: Int,
      documentsToSync: Seq[Document],
      maxWait: Int = DefaultMaxWait
  ): MongoSyncResult = {
    val start                   = System.currentTimeMillis()
    val syncDate                = new Date()
    var filteredDocumentsToSync = Seq[Document]()
    if (documentsToSync.nonEmpty) {
      val idSet: Set[ObjectId] = documentsToSync.map(doc => doc.getObjectId(idColumnName)).toSet
      filteredDocumentsToSync = left.dao(collectionName).find(valueFilter(idColumnName, idSet)).results(maxWait)
      right.dao(collectionName).bulkWriteMany(filteredDocumentsToSync).result(maxWait)
      val update = combine(
        set(MongoSyncOperation.SyncColumnLastSync, syncDate),
        set(MongoSyncOperation.SyncColumnLastUpdate, syncDate)
      )
      left.dao(collectionName).updateMany(Map(), update).result(maxWait)
      right.dao(collectionName).updateMany(Map(), update).result(maxWait)
    }
    val countAfter: Int = right.dao(collectionName).count().result(maxWait).toInt
    MongoSyncResult(
      collectionName,
      syncDate,
      true,
      filteredDocumentsToSync.size,
      countBefore,
      countAfter,
      (System.currentTimeMillis() - start)
    )
  }
}

object MongoSyncOperation extends ConfigHelper {
  val MaxWaitDefault = 600
  val MaxWait: Int   = intConfig(configPath = "dev.mongocamp.mongodb.sync", key = "maxWait", default = MaxWaitDefault)

  val SyncColumnLastSync: String =
    stringConfig(configPath = "dev.mongocamp.mongodb.sync", key = "syncColumnLastSync", default = "_lastSync").get
  val SyncColumnLastUpdate: String =
    stringConfig(configPath = "dev.mongocamp.mongodb.sync", key = "syncColumnLastUpdate", default = "_lastUpdate").get

  val WriteSyncLogOnMaster = booleanConfig(configPath = "dev.mongocamp.mongodb.sync", key = "writeSyncLogOnMaster")
  val SyncLogTableName: String =
    stringConfig(
      configPath = "dev.mongocamp.mongodb.sync",
      key = "syncLogTableName",
      default = "mongodb-sync-log"
    ).get
}

//case class MongoSyncInfo(id: Any = new ObjectId(), syncDate: Date = new Date(), updateDate: Date = new Date())

case class MongoSyncResult(
    collectionName: String,
    syncDate: Date = new Date(),
    acknowleged: Boolean = false,
    synced: Int = -1,
    countBefore: Int = -1,
    countAfter: Int = -1,
    syncTime: Long = -1,
    exception: Option[Exception] = None
)
