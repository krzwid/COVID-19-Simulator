package Model.MapSites

import scala.collection.mutable.ListBuffer

class Floor {
  private val staffRooms = ListBuffer[StaffRoom]()
  private val patientRooms = ListBuffer[PatientRoom]()

  def freeBeds: Int ={
    patientRooms.map(_.freeBeds).sum
  }

  def getPatientRooms: ListBuffer[PatientRoom] = {
    patientRooms
  }

  def addPatientRoom(patientRoom: PatientRoom): Unit = {
    patientRooms.addOne(patientRoom)
  }
}


