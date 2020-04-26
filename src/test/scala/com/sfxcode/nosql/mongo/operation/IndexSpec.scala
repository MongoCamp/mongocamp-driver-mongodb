package com.sfxcode.nosql.mongo.operation

import java.util.concurrent.TimeUnit

import com.sfxcode.nosql.mongo.test.TestDatabase._
import com.sfxcode.nosql.mongo._
import com.sfxcode.nosql.mongo.dao.PersonSpecification
import com.sfxcode.nosql.mongo.database.MongoIndex

import scala.concurrent.duration.Duration

class IndexSpec extends PersonSpecification {

  "Base Index Operations" should {

    "return an index list" in {

      val list = PersonDAO.indexList
      list must haveSize(1)

      val mongoIndex: MongoIndex = list.head
      mongoIndex.name mustEqual "_id_"
      mongoIndex.key mustEqual "_id"
      mongoIndex.ascending mustEqual 1
      mongoIndex.namespace mustEqual "simple-mongo-unit-test.people"
      mongoIndex.version mustEqual 2
      mongoIndex.keys must haveSize(1)
      mongoIndex.keys.head._1 mustEqual "_id"
      mongoIndex.keys.head._2 mustEqual 1

    }

    "create / drop indexes for key" in {

      var createIndexResult: String = PersonDAO.createIndexForField("name")

      createIndexResult mustEqual "name_1"

      PersonDAO.indexList must haveSize(2)
      val index: MongoIndex = PersonDAO.indexForName("name_1").get
      index.expire must beFalse

      val dropIndexResult: Void = PersonDAO.dropIndexForName(createIndexResult)

      PersonDAO.indexList must haveSize(1)
    }

    "evaluate has index" in {

      PersonDAO.hasIndexForField("_id") must beTrue

      PersonDAO.hasIndexForField("unknown") must beFalse
    }

    "create descending index for key" in {

      var createIndexResult: String = PersonDAO.createIndexForFieldWithName("name", sortAscending = false, "myIndex")

      createIndexResult mustEqual "myIndex"

      PersonDAO.indexList must haveSize(2)
      val index: MongoIndex = PersonDAO.indexForName("myIndex").get

      PersonDAO.dropIndexForName(createIndexResult).result()

      PersonDAO.indexList must haveSize(1)
    }

    "create unique index for key" in {

      var createIndexResult: String =
        PersonDAO.createUniqueIndexForField("id", sortAscending = false, Some("myUniqueIndex"))

      createIndexResult mustEqual "myUniqueIndex"

      PersonDAO.indexList must haveSize(2)
      val index: MongoIndex = PersonDAO.indexForName("myUniqueIndex").get

      PersonDAO.dropIndexForName(createIndexResult).result()

      PersonDAO.indexList must haveSize(1)
    }

    "create text index for key" in {

      var createIndexResult: String = PersonDAO.createTextIndexForField("email")

      createIndexResult mustEqual "email_text"

      PersonDAO.indexList must haveSize(2)
      val index: MongoIndex = PersonDAO.indexForName("email_text").get

      PersonDAO.dropIndexForName(createIndexResult).result()

      PersonDAO.indexList must haveSize(1)
    }

    "create hashed index for key" in {

      var createIndexResult: String = PersonDAO.createHashedIndexForField("email")

      createIndexResult mustEqual "email_hashed"

      PersonDAO.indexList must haveSize(2)
      val index: MongoIndex = PersonDAO.indexForName("email_hashed").get

      PersonDAO.dropIndexForName(createIndexResult).result()

      PersonDAO.indexList must haveSize(1)
    }

    "create expiring index for key" in {

      var createIndexResult: String = PersonDAO.createExpiringIndexForField("email", Duration(1, TimeUnit.SECONDS))

      createIndexResult mustEqual "email_1"

      PersonDAO.indexList must haveSize(2)

      val indes: MongoIndex = PersonDAO.indexForName("email_1").get
      indes.expire must beTrue

      PersonDAO.dropIndexForName(createIndexResult).result()

      PersonDAO.indexList must haveSize(1)
    }

  }

}
