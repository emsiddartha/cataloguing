package org.bheaver.ngl4.cataloguing.conf

import org.bheaver.ngl4.aa.protocol.authentication.JWTServiceImpl
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class BeanFactory {
  @Bean(Array("JWTService"))
  def getJWTService: JWTServiceImpl = new JWTServiceImpl

}
