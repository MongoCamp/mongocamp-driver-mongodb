package dev.mongocamp.driver.mongodb.schema

import io.circe.Decoder.Result
import io.circe.{ Decoder, Encoder, HCursor, Json }
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.mongodb.scala.Document

import java.util.Date

trait CirceProductSchema {

  def productElementNames(internalProduct: Product): Iterator[String] = {
    internalProduct.productElementNames
  }

}
