package org.bheaver.ngl4.cataloguing.conf

import org.bheaver.ngl4.aa.protocol.authentication.JWTServiceImpl
import org.bheaver.ngl4.cataloguing.services.importCatalogue.{ParseCatalogueRecord, ParseCatalogueRecordImpl}
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class BeanFactory {
  @Bean(Array("JWTService"))
  def getJWTService: JWTServiceImpl = new JWTServiceImpl

  @Bean(Array("ParseCatalogueRecord"))
  def getParseCatalogueRecord: ParseCatalogueRecord = new ParseCatalogueRecordImpl
}
