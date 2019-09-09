package org.bheaver.ngl4.cataloguing.model

package object marcModel {

  case class Subfield(identifier: String, data: String)

  case class Datafield(tag: String, i1: String, i2: String, subfields: List[Subfield]) {
    def getSubfieldsByIdentifier(identifier: String): List[Subfield] = subfields.filter(subfield => subfield.identifier == identifier)
  }

  case class Controlfield(tag: String, data: String)

  case class Record(leader: String, controlfields: List[Controlfield], datafields: List[Datafield]) {
    def getDatafieldsByTag(tag: String): List[Datafield] = datafields.filter(datafield => datafield.tag == tag)
  }

}
