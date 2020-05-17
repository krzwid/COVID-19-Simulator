package Model.MapSites

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class PatientRoom(private val capacity: Int) extends Room {
  private var patientsList = ListBuffer[Patient]()
  private val staffList = ListBuffer[Staff]()

  // map from Class.getName to Int
  private val daysSinceVisitedBy: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int]()

  def this() {
    this(6)
  }

//  implemented interface
  override def goIn(staff: Staff): Unit = {
    staffList.addOne(staff)
    for( (key, _ ) <- daysSinceVisitedBy) {
      daysSinceVisitedBy(key) = 0
    }
  }

  override def canGoIn(staff: Staff): Boolean = {
    staffList.isEmpty
  }

  override def goOut(staff: Staff): Unit = {
    staffList.subtractOne(staff)
  }

  override def getPerson(ID: Int): Person = {
    patientsList.find(_.getID.equals(ID)).head
  }

//  additional methods to take care of patients
  def freeBeds: Int = {
    this.capacity - patientsList.size
  }

  def putPatient(patient: Patient): Unit = {
    if(this.canPutPatient) {
      patientsList.addOne(patient)
    }
  }

  def removePatient(ID: Int): Unit = {
    patientsList = patientsList.filter(_.getID != ID)
  }

  def canPutPatient: Boolean = {
    this.freeBeds > 0
  }
}
