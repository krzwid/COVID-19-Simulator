package Model.MapSites

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class StaffRoom extends Room {
  private val staffs = ListBuffer[Person]()

  def goIn(person: Person): Unit = {
    staffs +=  person
  }

  def canGoIn(person: Person): Boolean = {
    true
  }

  def goOut(person: Person): Unit = {
    staffs.filter(_.getID != person.getID)
  }
  def getPerson(ID: Int): Person = {
    staffs.find(_.getID.equals(ID)).head
  }
}
