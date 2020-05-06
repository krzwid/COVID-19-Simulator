package Model.MapSites

import Model.People.{Patient, Person}

class StaffRoom extends Room {
  def goIn(person: Person): Unit = {

  }

  def canGoIn(person: Person): Boolean = {
    true
  }

  def goOut(person: Person): Unit = {

  }
  def getPerson(ID: Int): Person = {
    new Patient()
  }
}
