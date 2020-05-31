package com.sfxcode.nosql.mongo.sync

import com.sfxcode.nosql.mongo.server.LocalServer
import com.sfxcode.nosql.mongo.sync.TestSync._
import com.sfxcode.nosql.mongo.test.UniversityDatabase
import org.specs2.mutable.Specification
import org.specs2.specification.{AfterAll, BeforeAll}

class SyncSpec extends Specification with BeforeAll with AfterAll {

  sequential

  override def beforeAll(): Unit =
    UniversityDatabase.LocalTestServer = LocalServer.fromPath("unit.test.local.mongo.server")

  override def afterAll(): Unit = {
    TestSync.mongoSyncer.terminate()
    UniversityDatabase.LocalTestServer.shutdown()
  }

  "Collection" should {

    "be synced from source to target" in {
      var result: MongoSyncResult = TestSync.mongoSyncer.sync(TestCollectionSourceTargetName).head
      result.acknowleged must beTrue
      TestSync.insertIntoSource(500, TestCollectionSourceTargetName)
      result = TestSync.mongoSyncer.sync(TestCollectionSourceTargetName).head
      result.acknowleged must beTrue
      result.synced mustEqual 500
      result.countBefore mustEqual 0
      result.countAfter mustEqual 500
      TestSync.targetCount(TestCollectionSourceTargetName) mustEqual 500
      result = TestSync.mongoSyncer.sync(TestCollectionSourceTargetName).head
      result.acknowleged must beTrue
      result.countBefore mustEqual 500
      result.synced mustEqual 0

      TestSync.insertIntoSource(5, TestCollectionSourceTargetName)
      result = TestSync.mongoSyncer.sync(TestCollectionSourceTargetName).head
      result.acknowleged must beTrue
      result.countBefore mustEqual 500
      result.countAfter mustEqual 505
      result.synced mustEqual 5
      TestSync.targetCount(TestCollectionSourceTargetName) mustEqual 505

      TestSync.insertIntoTarget(5, TestCollectionSourceTargetName)
      TestSync.targetCount(TestCollectionSourceTargetName) mustEqual 510
      val resultList = TestSync.mongoSyncer.sync(TestCollectionSourceTargetName)
      result = resultList.head
      result.acknowleged must beTrue
      result.countBefore mustEqual 510
      result.synced mustEqual 0
      TestSync.sourceCount(TestCollectionSourceTargetName) mustEqual 505
    }

    "be synced two way" in {
      var result: MongoSyncResult = TestSync.mongoSyncer.sync(TestCollectionTwoWayName).head
      result.acknowleged must beTrue
      TestSync.insertIntoSource(500, TestCollectionTwoWayName)
      result = TestSync.mongoSyncer.sync(TestCollectionTwoWayName).head
      result.acknowleged must beTrue
      result.synced mustEqual 500
      result.countBefore mustEqual 0
      result.countAfter mustEqual 500
      TestSync.targetCount(TestCollectionTwoWayName) mustEqual 500
      result = TestSync.mongoSyncer.sync(TestCollectionTwoWayName).head
      result.acknowleged must beTrue
      result.countBefore mustEqual 500
      result.synced mustEqual 0

      TestSync.insertIntoSource(5, TestCollectionTwoWayName)
      result = TestSync.mongoSyncer.sync(TestCollectionTwoWayName).head
      result.acknowleged must beTrue
      result.countBefore mustEqual 500
      result.countAfter mustEqual 505
      result.synced mustEqual 5
      TestSync.targetCount(TestCollectionTwoWayName) mustEqual 505

      TestSync.insertIntoTarget(5, TestCollectionTwoWayName)
      TestSync.targetCount(TestCollectionTwoWayName) mustEqual 510
      val resultList = TestSync.mongoSyncer.sync(TestCollectionTwoWayName)
      result = resultList.head
      result.acknowleged must beTrue
      result.countBefore mustEqual 510
      result.synced mustEqual 0
      TestSync.sourceCount(TestCollectionTwoWayName) mustEqual 510
    }

  }
}
