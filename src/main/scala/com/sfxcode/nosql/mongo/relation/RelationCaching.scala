package com.sfxcode.nosql.mongo.relation

trait RelationCaching {

  def addCachedValue(key: String, value: AnyRef): Unit

  def getCachedValue[B <: AnyRef](key: String): B

  def hasCachedValue(key: String): Boolean

  def removeCachedValue(key: String): Unit

}
