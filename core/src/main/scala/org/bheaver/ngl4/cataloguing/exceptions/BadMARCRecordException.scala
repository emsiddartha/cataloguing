package org.bheaver.ngl4.cataloguing.exceptions

import org.bheaver.ngl4.util.exceptions.HTTPException

class BadMARCRecordException(val message: String) extends HTTPException{
  override def statusCode = 400
}
