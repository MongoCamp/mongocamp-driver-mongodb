package com.sfxcode.nosql.mongo

import com.sfxcode.nosql.mongo.database.DatabaseProvider
import com.sfxcode.nosql.mongo.model._
import com.sfxcode.nosql.mongo.operation.CrudObserver
import org.mongodb.scala._
import org.mongodb.scala.result.InsertManyResult

object TestDatabase extends ObservableImplicits {

  val mongoClient: MongoClient = MongoClient()

  import org.bson.codecs.configuration.CodecRegistries._
  import org.mongodb.scala.bson.codecs.Macros._

  private val bookRegistry = fromProviders(classOf[Book], classOf[Author])

  private val personRegistry = fromProviders(classOf[Person], classOf[Friend])

  private val lineRegistry      = fromProviders(classOf[Line], classOf[Position])
  private val codecTestRegistry = fromProviders(classOf[CodecTest])

  val provider =
    DatabaseProvider.fromPath("unit.test.mongo",
                              fromRegistries(bookRegistry, personRegistry, lineRegistry, codecTestRegistry))

  object BookDAO extends MongoDAO[Book](provider, "books")

  object LineDAO extends MongoDAO[Line](provider, "lines") with CrudObserver[Line]

  object PersonDAO extends MongoDAO[Person](provider, "person")

  object CodecDao extends MongoDAO[CodecTest](provider, "codec-test")

  val dropResult: Void = PersonDAO.drop()

  val persons: List[Person] = Person.personList

  val insertResult: InsertManyResult = PersonDAO.insertMany(persons)

  def printDatabaseStatus(): Unit = {
    val count: Long = PersonDAO.count()
    printDebugValues("Database Status", "%s rows for collection person found".format(count))
  }

  def printDebugValues(name: String, result: Any): Unit = {
    println()
    println(name)
    println(result)
  }

}
