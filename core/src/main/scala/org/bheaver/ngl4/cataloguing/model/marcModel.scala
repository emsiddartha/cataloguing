package org.bheaver.ngl4.cataloguing.model

package object marcModel {

  case class Subfield(identifier: Char, data: String)

  case class Datafield(tag: String, i1: Char, i2: Char, subfields: List[Subfield]) {
    def getSubfieldsByIdentifier(identifier: Char): List[Subfield] = subfields.filter(subfield => subfield.identifier == identifier)
  }

  case class Controlfield(tag: String, data: String)

  case class Record(leader: String, controlfields: List[Controlfield], datafields: List[Datafield]) {
    def getDatafieldsByTag(tag: String): List[Datafield] = datafields.filter(datafield => datafield.tag.equals(tag))
  }

}
