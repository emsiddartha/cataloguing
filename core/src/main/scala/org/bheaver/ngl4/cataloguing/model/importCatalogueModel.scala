package org.bheaver.ngl4.cataloguing.model

import marcModel._

package object importCatalogueModel {

  case class ParseRequest(isoRecord: String, requestId: String)

  case class ParseResponse(records: List[Record], requestId: String)

}
