package Model.MapSites

import Model.People.{Patient, Person}

class PatientRoom(
                 private val capacity: Int
                 ) extends Room {
  private val patients = List()

  // map from Class.getName to Int
  private val daysSinceVisitedBy: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int]()

  def this() {
    this(10)
    daysSinceVisitedBy.put()
  }

  def goIn(person: Person): Unit = {
    println("person comes in: " + person.getID())
  }

  def canGoIn(person: Person): Boolean = {
    true
  }

  def goOut(person: Person): Unit = {
    println("person goes out: " + person.getID())
  }

  def getPerson(ID: Int): Person = {
    new Patient()
  }

  def freeBeds(): Int = {
    0
  }

  def putPatient(patient: Patient): Unit = {
    println("patient put: " = patient.getID())
  }

  def removePatient(ID: Int): Unit = {
    println("remove patient, ID: " + ID)
  }
}
