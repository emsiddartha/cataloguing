package org.bheaver.ngl4.cataloguing.conf

import com.google.inject.AbstractModule
import org.bheaver.ngl4.aa.protocol.authentication.{JWTService, JWTServiceImpl}
import org.bheaver.ngl4.cataloguing.services.importCatalogue.{ParseCatalogueRecord, ParseCatalogueRecordImpl}

class ProdModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[JWTService]).to(classOf[JWTServiceImpl])
    bind(classOf[ParseCatalogueRecord]).to(classOf[ParseCatalogueRecordImpl])
  }
}
