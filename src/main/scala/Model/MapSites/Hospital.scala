package Model.MapSites

import Model.People.{Doctor, Nurse, Patient, Staff}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Hospital {
  var floors: List[Floor] = null
  val queue = new mutable.Queue[Patient]()
  val doctors = new mutable.ListBuffer[Doctor]
  val nurses = new mutable.ListBuffer[Nurse]

  def this(howManyFloors: Int, patientsDatabase: List[Patient], howMany: Int) = {
    this()

    val floorsBuilder = new ListBuffer[Floor]()
    for (_ <- (0 until howManyFloors).toList) floorsBuilder.append(new Floor)
    this.floors = floorsBuilder.toList

    patientsDatabase.take(howMany).foreach(this.queue.enqueue)
  }

  def freeBeds: Int ={
    floors.map(_.freeBeds).sum
  }

  def addPatientToQueue(patient: Patient): Unit = {
    queue.append(patient)
  }

  def getQueue: mutable.Queue[Patient] = {
    this.queue
  }

  def addStaff(staff: Staff): Unit = {
    staff match {
      case doctor: Doctor => this.doctors.addOne(doctor)
      case nurse: Nurse => this.nurses.addOne(nurse)
      case _ =>
    }
  }
}
