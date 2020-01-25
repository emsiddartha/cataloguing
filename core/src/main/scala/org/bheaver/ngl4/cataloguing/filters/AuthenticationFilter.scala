package org.bheaver.ngl4.cataloguing.filters

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet._
import org.bheaver.ngl4.aa.protocol.authentication.JWTService
import org.bheaver.ngl4.aa.protocol.model.DecodeRequest
import org.bheaver.ngl4.cataloguing.conf.BeanRegistry
import org.bheaver.ngl4.util.exceptions.HTTPException
import org.bheaver.ngl4.util.json.ExceptionJSONGenerator.JSONGenerator
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

import scala.util.{Failure, Success, Try}

@Configuration
@Order(1)
class AuthenticationFilter extends Filter {
  var jwtService: JWTService = BeanRegistry.getJWTService

  override def doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain): Unit = {
    val request = servletRequest.asInstanceOf[HttpServletRequest]
    val response = servletResponse.asInstanceOf[HttpServletResponse]
    val token = request.getHeader("token")
    val patronId = request.getHeader("patronId")
    val libCode = request.getHeader("libCode")
    val requestId = request.getHeader("requestId")
    Try(jwtService.renewToken(DecodeRequest(token, patronId, libCode, Option(true), Option(requestId)))) match {
      case Failure(exception: HTTPException) => {
        val str = JSONGenerator.toJSON(exception, response)
        response.getWriter.println(str)
      }
      case Success(value) => {
        response.addHeader("requestId", value.requestId)
        response.addHeader("token", value.token)
        chain.doFilter(request,response)
      }
    }
  }

  override def init(filterConfig: FilterConfig): Unit = {

  }

  override def destroy(): Unit = {

  }
}
