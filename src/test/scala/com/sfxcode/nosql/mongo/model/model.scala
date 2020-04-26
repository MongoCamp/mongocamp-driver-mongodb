package com.sfxcode.nosql.mongo.model

import java.util.Date

import org.bson.codecs.pojo.annotations.BsonId
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.conversions.Bson

case class Book(
    _id: Double,
    title: String,
    isbn: String,
    pageCount: Double,
    publishedDate: Date,
    thumbnailUrl: String,
    shortDescription: String,
    longDescription: String,
    status: String,
    authors: List[String],
    categories: List[String]
)

case class Person(
    id: Long,
    guid: String,
    isActive: Boolean,
    balance: Double,
    picture: String,
    age: Int,
    name: String,
    gender: String,
    email: String,
    phone: String,
    address: String,
    about: String,
    registered: Date,
    tags: List[String],
    friends: List[Friend],
    greeting: String,
    favoriteFruit: String,
    _id: ObjectId = new ObjectId()
)

case class Friend(id: Long, name: String)

case class Product(
    _id: String,
    name: String,
    brand: String,
    `type`: String,
    price: Double,
    rating: Double,
    warranty_years: Double,
    available: Boolean
)

// #model_student

case class Student(_id: Long, name: String, scores: List[Score], image: Option[ObjectId])

case class Score(score: Double, `type`: String)

case class Grade(_id: ObjectId, student_id: Long, class_id: Long, scores: List[Score])

// #model_student

case class CodecTest(
    id: Long = 1,
    bd: BigDecimal = BigDecimal(BigDecimal.getClass.getSimpleName.length.toDouble),
    bi: BigInt = BigInt(BigInt.getClass.getSimpleName.length)
)

case class Restaurant(address: String, name: String)

case class ImageMetadata(name: String, group: String = "logos", version: Int = 1, indexSet: Set[Int] = Set(1, 2, 3))
