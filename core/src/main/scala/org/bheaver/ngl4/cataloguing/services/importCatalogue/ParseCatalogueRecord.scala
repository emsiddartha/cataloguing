package org.bheaver.ngl4.cataloguing.services.importCatalogue

import java.io.ByteArrayInputStream

import org.bheaver.ngl4.cataloguing.exceptions.BadMARCRecordException
import org.bheaver.ngl4.cataloguing.model.importCatalogueModel._
import org.bheaver.ngl4.cataloguing.model.marcModel.{Controlfield => NGLControlfield, Datafield => NGLDatafield, Record => NGLRecord, Subfield => NGLSubfield}
import org.bheaver.ngl4.util.UUIDGenerator
import org.bheaver.ngl4.util.exceptions.BadRequestException
import org.bheaver.ngl4.util.StringUtil._
import org.marc4j.MarcStreamReader
import org.marc4j.marc.Record

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.jdk.CollectionConverters._
import scala.concurrent.ExecutionContext.Implicits.global

trait ParseCatalogueRecord {
  def parse(parseRequest: ParseRequest): Future[ParseResponse]
}

class ParseCatalogueRecordImpl extends ParseCatalogueRecord {
  val checkParseRequestForBadRecord: (ParseRequest => ParseRequest) = parseRequest => {
    Option(parseRequest.isoRecord) match {
      case Some(value) => if (isEmptyTrim(parseRequest.isoRecord)) throw new BadRequestException("ISO Record not found") else parseRequest
      case None => throw new BadRequestException("ISO Record not found")
    }
  }

  val checkIfRecordIsNotBadMARCRecord: (ParseRequest => (MarcStreamReader, String)) = parseRequest => {
    val isoRecord = parseRequest.isoRecord
    (new MarcStreamReader(new ByteArrayInputStream(isoRecord.getBytes)), parseRequest.requestId)
  }

  val convertMarcStreamToRecords: ((MarcStreamReader, String)) => ((List[Record], String)) = tuple => {
    val listBuffer = new ListBuffer[Record]
    val marcStream = tuple._1
    Try(while (marcStream.hasNext) listBuffer.addOne(marcStream.next())) match {
      case Failure(exception) => throw new BadMARCRecordException("Error reading MARC Record")
      case Success(value) => value
    }
    (listBuffer.toList, tuple._2)
  }

  val mapMARC4JRecordToNGLRecord: (Record => NGLRecord) = (record) => {
    val leader = record.getLeader.marshal()
    val controlFields = record.getControlFields.asScala.toList.map(controlField => NGLControlfield(controlField.getTag, controlField.getData))
    val dataFields = record.getDataFields.asScala.toList.map(dataField =>
      NGLDatafield(dataField.getTag, charToString(dataField.getIndicator1), charToString(dataField.getIndicator2),
        dataField.getSubfields.asScala.toList.map(subField => NGLSubfield(charToString(subField.getCode), subField.getData))
      )
    )
    NGLRecord(leader, controlFields, dataFields)
  }
  val convertMARCListToNGLList: ((List[Record], String)) => ((List[NGLRecord], String)) = tuple => {
    (tuple._1.map(mapMARC4JRecordToNGLRecord), tuple._2)
  }

  override def parse(parseRequest: ParseRequest): Future[ParseResponse] = {
    Future(parseRequest)
      .map(checkParseRequestForBadRecord)
      .map(checkIfRecordIsNotBadMARCRecord)
      .map(convertMarcStreamToRecords)
      .map(convertMARCListToNGLList)
      .map(tuple => ParseResponse(tuple._1, UUIDGenerator.generateReturnRequestId(tuple._2)))
  }
}

