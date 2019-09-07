package org.bheaver.ngl4.cataloguing.controllers

import java.util.concurrent.{CompletableFuture, CompletionStage}

import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

import scala.compat.java8.FutureConverters
import scala.concurrent.Future




@RestController
@RequestMapping(Array("/cataloguing/importCatalogue"))
class ImportCatalogueController {
  @GetMapping(value = Array("/import"))
  def importCatalogue: CompletionStage[String] = {
    implicit val global = scala.concurrent.ExecutionContext.global
    FutureConverters.toJava(
    Future("Hello")
    )
  }
}
