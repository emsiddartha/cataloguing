package org.bheaver.ngl4.cataloguing.controllers

import java.util.concurrent.CompletionStage

import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(Array("/cataloguing/importCatalogue"))
class ImportCatalogueController {

  @GetMapping(value = Array("/parse"))
  def parse: CompletionStage[String] = ???

  @PostMapping(value = Array("/save"))
  def save: CompletionStage[String] = ???

  @GetMapping(value = Array("/view"))
  def view(@RequestParam id: String): CompletionStage[String] = ???

  @DeleteMapping(value = Array("/remove"))
  def remove(@RequestParam id: String): CompletionStage[String] = ???

  @GetMapping(value = Array("/list"))
  def list: CompletionStage[String] = ???
}
