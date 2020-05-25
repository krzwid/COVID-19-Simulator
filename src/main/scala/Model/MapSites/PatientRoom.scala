package Model.MapSites

import java.util.Objects

import Model.People.{Doctor, Patient, Person, Staff}

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
    Objects.requireNonNull(staff, "Null staff reference")
    if (staff.getRoom != null) throw new IllegalStateException("That person already is in some room")
    staffList.addOne(staff)
    staff.goTo(this)
    for( (key, _ ) <- daysSinceVisitedBy) {
      daysSinceVisitedBy(key) = 0
    }
  }

  override def canGoIn(staff: Staff): Boolean = {
    if (staff.isInstanceOf[Doctor]) {
      !staffList.exists(_.isInstanceOf[Doctor])
    } else true
  }

  override def goOut(staff: Staff): Unit = {
    Objects.requireNonNull(staff, "Null staff reference")
    if (!staffList.contains(staff)) throw new IllegalStateException("There is no staff " + staff.getID.toString + " in this room")
    staffList.subtractOne(staff)
    staff.goTo(null);
  }

  override def getPerson(ID: Int): Person = {
    patientsList.find(_.getID.equals(ID)).head
  }

//  additional methods to take care of patients
  def getPatientList: ListBuffer[Patient] = {
    this.patientsList
  }
  def getStaffList: ListBuffer[Staff] = {
    this.staffList
  }
  def freeBeds: Int = {
    this.capacity - patientsList.size
  }

  def putPatient(patient: Patient): Boolean = {
    if(this.canPutPatient) {
      patientsList.addOne(patient)
      return true
    }
    false
  }

  def removePatient(ID: Int): Unit = {
    patientsList = patientsList.filter(_.getID != ID)
  }

  def canPutPatient: Boolean = {
    this.freeBeds > 0
  }

  override def getAllPeople: List[Person] = {
    patientsList.toList ++ staffList.toList
  }
}
