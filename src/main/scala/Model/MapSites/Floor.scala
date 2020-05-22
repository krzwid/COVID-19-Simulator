package Model.MapSites

import Model.People.Staff

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

  def addStaffRoom(staffRoom: StaffRoom): Unit = {
    staffRooms.addOne(staffRoom)
  }

  def getStaffRooms: ListBuffer[StaffRoom] = {
    this.staffRooms
  }

  def addStaffToStaffRoom(staff: Staff): Unit = {
    this.staffRooms.head.goIn(staff)
  }
}


