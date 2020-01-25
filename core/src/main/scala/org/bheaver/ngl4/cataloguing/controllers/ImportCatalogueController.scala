package org.bheaver.ngl4.cataloguing.controllers

import java.util.concurrent.CompletionStage

import javax.servlet.http.HttpServletResponse
import org.bheaver.ngl4.cataloguing.conf.BeanRegistry
import org.bheaver.ngl4.cataloguing.services.importCatalogue.ParseCatalogueRecord
import org.springframework.web.bind.annotation._
import org.bheaver.ngl4.cataloguing.model.importCatalogueModel._
import org.bheaver.ngl4.util.exceptions.HTTPException

import scala.compat.java8.FutureConverters
import org.bheaver.ngl4.util.json.ExceptionJSONGenerator._
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import org.springframework.http.MediaType

@RestController
@RequestMapping(Array("/cataloguing/importCatalogue"))
class ImportCatalogueController {

  var parseCatalogueRecord: ParseCatalogueRecord = BeanRegistry.getParseCatalogueRecordService

  @GetMapping(value = Array("/parse"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def parse(@RequestParam isoRecord: String, @RequestParam requestId: String, httpServletResponse: HttpServletResponse): CompletionStage[String] =
    FutureConverters.toJava {
      implicit val global = scala.concurrent.ExecutionContext.global
      implicit val formats = DefaultFormats
      parseCatalogueRecord.parse(ParseRequest(isoRecord,requestId))
        .map(response => write(response))
        .recover{
        case e: HTTPException => JSONGenerator.toJSON(e,httpServletResponse)
      }
    }

  @PostMapping(value = Array("/save"))
  def save: CompletionStage[String] = ???

  @GetMapping(value = Array("/view"))
  def view(@RequestParam id: String): CompletionStage[String] = ???

  @DeleteMapping(value = Array("/remove"))
  def remove(@RequestParam id: String): CompletionStage[String] = ???

  @GetMapping(value = Array("/list"))
  def list: CompletionStage[String] = ???
}
