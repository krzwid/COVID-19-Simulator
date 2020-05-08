package Model.MapSites

import Model.People.{Patient, Person}

import scala.collection.mutable.ListBuffer

class PatientRoom(
                 private val capacity: Int
                 ) extends Room {
  private val patients = ListBuffer[Patient]()

  // map from Class.getName to Int
  private val daysSinceVisitedBy: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int]()

  def this() {
    this(10)
//    daysSinceVisitedBy.put
  }

  def goIn(person: Person): Unit = {
    for( (key, _) <- daysSinceVisitedBy) {
      daysSinceVisitedBy(key) = 0
    }
    println("person comes in: " + person.getID())
  }

  def canGoIn(person: Person): Boolean = {
    freeBeds > 0
  }

  def goOut(person: Person): Unit = {
    println("person goes out: " + person.getID())
  }

  def getPerson(ID: Int): Person = {
    patients.find(_.getID.equals(ID))
  }

  def freeBeds: Int = {
    capacity - patients.size
  }

  def putPatient(patient: Patient): Unit = {
    patients += patient
    println("patient put: " = patient.getID())
  }

  def removePatient(ID: Int): Unit = {
    patients.filter(_.getID != ID)
    println("remove patient, ID: " + ID)
  }
}
