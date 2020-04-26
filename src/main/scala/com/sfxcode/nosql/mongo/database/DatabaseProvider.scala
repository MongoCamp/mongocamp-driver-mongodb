package com.sfxcode.nosql.mongo.database

import com.sfxcode.nosql.mongo._
import com.sfxcode.nosql.mongo.bson.codecs.CustomCodecProvider
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala._
import org.mongodb.scala.gridfs.GridFSBucket

import scala.collection.mutable
import scala.reflect.ClassTag

class DatabaseProvider(val config: MongoConfig, val registry: CodecRegistry) extends Serializable {
  private val cachedDatabaseMap = new mutable.HashMap[String, MongoDatabase]()
  private val cachedMongoDAOMap = new mutable.HashMap[String, MongoDAO[Document]]()

  val DefaultDatabaseName: String = config.database

  private var cachedClient: Option[MongoClient] = None

  def client: MongoClient = {
    if (isClosed) {
      cachedDatabaseMap.clear()
      cachedMongoDAOMap.clear()
      cachedClient = Some(MongoClient(config.clientSettings))
    }
    cachedClient.get
  }

  def isClosed: Boolean = cachedClient.isEmpty

  def closeClient(): Unit = {
    client.close()
    cachedClient = None
  }

  def databases: ListDatabasesObservable[Document] =
    client.listDatabases()

  def databaseInfos: List[DatabaseInfo] = databases.resultList().map(doc => DatabaseInfo(doc)).sortBy(_.name)

  def databaseNames: List[String] = databaseInfos.map(info => info.name)

  def dropDatabase(databaseName: String = DefaultDatabaseName): SingleObservable[Void] = database(databaseName).drop()

  def database(databaseName: String = DefaultDatabaseName): MongoDatabase = {
    if (!cachedDatabaseMap.contains(databaseName)) {
      cachedDatabaseMap.put(databaseName, client.getDatabase(databaseName).withCodecRegistry(registry))
    }
    cachedDatabaseMap(databaseName)
  }

  def addChangeObserver(
      observer: ChangeObserver[Document],
      databaseName: String = DefaultDatabaseName
  ): ChangeObserver[Document] = {
    database(databaseName).watch().subscribe(observer)
    observer
  }

  def collections(databaseName: String = DefaultDatabaseName): ListCollectionsObservable[Document] =
    database(databaseName).listCollections()

  def collectionInfos(databaseName: String = DefaultDatabaseName): List[CollectionInfo] =
    collections(databaseName).resultList().map(doc => CollectionInfo(doc)).sortBy(_.name)

  def collectionNames(databaseName: String = DefaultDatabaseName): List[String] =
    collectionInfos(databaseName).map(info => info.name)

  def runCommand(document: Document, databaseName: String = DefaultDatabaseName): SingleObservable[Document] =
    database(databaseName).runCommand(document)

  def collectionStatus(
      collectionName: String,
      databaseName: String = DefaultDatabaseName
  ): Observable[CollectionStatus] =
    runCommand(Map("collStats" -> collectionName), databaseName).map(document => CollectionStatus(document))

  def collection[A](collectionName: String)(implicit ct: ClassTag[A]): MongoCollection[A] =
    if (collectionName.contains(DatabaseProvider.CollectionSeparator)) {
      val newDatabaseName: String   = guessDatabaseName(collectionName)
      val newCollectionName: String = guessName(collectionName)
      database(newDatabaseName).getCollection[A](newCollectionName)
    }
    else {
      database().getCollection[A](collectionName)
    }

  def guessDatabaseName(maybeSeparatedName: String): String =
    if (maybeSeparatedName.contains(DatabaseProvider.CollectionSeparator)) {
      maybeSeparatedName.substring(0, maybeSeparatedName.indexOf(DatabaseProvider.CollectionSeparator))
    }
    else {
      DefaultDatabaseName
    }

  def guessName(maybeSeparatedName: String): String =
    if (maybeSeparatedName.contains(DatabaseProvider.CollectionSeparator)) {
      maybeSeparatedName.substring(maybeSeparatedName.indexOf(DatabaseProvider.CollectionSeparator) + 1)
    }
    else {
      DefaultDatabaseName
    }

  def bucket(bucketName: String): GridFSBucket =
    if (bucketName.contains(DatabaseProvider.CollectionSeparator)) {
      val newDatabaseName = guessDatabaseName(bucketName)
      val newBucketName   = guessName(bucketName)
      GridFSBucket(database(newDatabaseName), newBucketName)
    }
    else {
      GridFSBucket(database(), bucketName)
    }

  def dao(collectionName: String): MongoDAO[Document] = {
    if (!cachedMongoDAOMap.contains(collectionName)) {
      cachedMongoDAOMap.put(collectionName, DocumentDao(this, collectionName))
    }
    cachedMongoDAOMap(collectionName)
  }

  def usedDatabaseNames(): List[String] = cachedDatabaseMap.keys.toList

  def usedCollectionNames(): List[String] = cachedMongoDAOMap.keys.toList

  case class DocumentDao(provider: DatabaseProvider, collectionName: String)
      extends MongoDAO[Document](this, collectionName)

}

object DatabaseProvider {
  val CollectionSeparator = ":"

  private val CustomRegistry = fromProviders(CustomCodecProvider())

  private val codecRegistry: CodecRegistry =
    fromRegistries(CustomRegistry, DEFAULT_CODEC_REGISTRY)

  def apply(config: MongoConfig, registry: CodecRegistry = codecRegistry): DatabaseProvider =
    new DatabaseProvider(config, fromRegistries(registry, CustomRegistry, DEFAULT_CODEC_REGISTRY))

  def fromPath(
      configPath: String = MongoConfig.DefaultConfigPathPrefix,
      registry: CodecRegistry = codecRegistry
  ): DatabaseProvider =
    apply(MongoConfig.fromPath(configPath), fromRegistries(registry, CustomRegistry, DEFAULT_CODEC_REGISTRY))

}
