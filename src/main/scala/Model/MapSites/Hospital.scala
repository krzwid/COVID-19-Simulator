package Model.MapSites

import Model.People.{Doctor, Nurse, Patient, Staff}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Hospital(howManyFloors: Int) {
  val floors: List[Floor] = List.fill(howManyFloors)(new Floor)
  val queue = new mutable.Queue[Patient]()
  val doctors = new mutable.ListBuffer[Doctor]
  val nurses = new mutable.ListBuffer[Nurse]

  def freeBeds: Int ={
    floors.map(_.freeBeds).sum
  }

  def addPatientToQueue(patient: Patient): Unit = {
    this.queue.append(patient)
  }

  def getQueue: mutable.Queue[Patient] = {
    this.queue
  }

  def addStaff(staff: Staff): Unit = {
    staff match {
      case doctor: Doctor => this.doctors.addOne(doctor)
      case nurse: Nurse => this.nurses.addOne(nurse)
      case null => throw new NullPointerException("Adding null reference to staff collections")
      case _ => throw new IllegalStateException("Illegal staff-object type ")
    }
  }
}
