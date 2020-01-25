package org.bheaver.ngl4.cataloguing.conf

import com.google.inject.Guice
import org.bheaver.ngl4.aa.protocol.authentication.JWTService
import org.bheaver.ngl4.cataloguing.services.importCatalogue.ParseCatalogueRecord

object BeanRegistry {
  val injector = Guice.createInjector(new ProdModule)
  def getParseCatalogueRecordService: ParseCatalogueRecord = injector.getInstance(classOf[ParseCatalogueRecord])
  def getJWTService: JWTService = injector.getInstance(classOf[JWTService])
}
