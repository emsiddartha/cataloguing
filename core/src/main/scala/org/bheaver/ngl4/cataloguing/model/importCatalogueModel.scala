package org.bheaver.ngl4.cataloguing.model

import marcModel._

package object importCatalogueModel {

  case class ParseRequest(val isoRecord: String, val requestId: String)

  case class ParseResponse(val records: List[Record], val requestId: String)

}
