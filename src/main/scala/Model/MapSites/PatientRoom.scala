package Model.MapSites

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class PatientRoom(private val capacity: Int) extends Room {
  private var patients = ListBuffer[Patient]()
  private var staff = ListBuffer[Staff]()

  // map from Class.getName to Int
  private val daysSinceVisitedBy: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int]()

  def this() {
    this(6)
  }

//  implemented interface
  override def goIn(staff: Staff): Unit = {
    this.staff.addOne(staff)
    for( (key, _) <- daysSinceVisitedBy) {
      daysSinceVisitedBy(key) = 0
    }
  }

  override def canGoIn(staff: Staff): Boolean = {
    this.staff.isEmpty
  }

  override def goOut(staff: Staff): Unit = {
    this.staff.filter( _ != staff)
  }

  override def getPerson(ID: Int): Person = {
    patients.find(_.getID.equals(ID)).head
  }

//  additional methods to take care of patients
  def freeBeds: Int = {
    this.capacity - this.patients.size
  }

  def putPatient(patient: Patient): Unit = {
    patients.addOne(patient)
  }

  def removePatient(ID: Int): Unit = {
    patients = patients.filter(_.getID != ID)
  }

  def canPutPatient: Boolean = {
    this.freeBeds > 0
  }
}
