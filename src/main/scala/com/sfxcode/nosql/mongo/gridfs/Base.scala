package com.sfxcode.nosql.mongo.gridfs

import java.io.OutputStream
import java.nio.ByteBuffer

import com.mongodb.client.gridfs.model.GridFSUploadOptions
import com.sfxcode.nosql.mongo.Converter
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.gridfs.{ GridFSBucket, GridFSDownloadObservable }
import org.mongodb.scala.{ Document, Observable, ReadConcern, ReadPreference, WriteConcern }

abstract class Base extends LazyLogging {

  protected def gridfsBucket: GridFSBucket

  def createMetadataKey(key: String): String = {
    var metadataKey = key
    if (!metadataKey.startsWith("metadata")) {
      metadataKey = "%s.%s".format("metadata", key)
    }
    metadataKey
  }

  def drop(): Observable[Void] = gridfsBucket.drop()

  def bucketName: String = gridfsBucket.bucketName

  def chunkSizeBytes: Int = gridfsBucket.chunkSizeBytes

  def writeConcern: WriteConcern = gridfsBucket.writeConcern

  def readPreference: ReadPreference = gridfsBucket.readPreference

  def readConcern: ReadConcern = gridfsBucket.readConcern

  def upload(
    fileName: String,
    source: Observable[ByteBuffer],
    metadata: AnyRef = Document(),
    chunkSizeBytes: Int = 1024 * 256): Observable[ObjectId] = {
    val metadataDocument = {
      metadata match {
        case document: Document => document
        case _ => Converter.toDocument(metadata)
      }
    }
    val options: GridFSUploadOptions = new GridFSUploadOptions()
      .chunkSizeBytes(chunkSizeBytes)
      .metadata(metadataDocument)
    gridfsBucket.uploadFromObservable(fileName, source, options)
  }

  def download(oid: ObjectId): GridFSDownloadObservable =
    gridfsBucket.downloadToObservable(oid)

  def downloadToStream(oid: ObjectId, outputStream: OutputStream): GridFSStreamObserver = {
    val observable: GridFSDownloadObservable = gridfsBucket.downloadToObservable(oid)
    val observer = GridFSStreamObserver(outputStream)
    observable.subscribe(observer)
    observer
  }

}
