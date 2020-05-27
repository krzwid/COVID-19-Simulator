package Model.MapSites

import Model.People.Staff

class Floor(howManyRoomsOnFloor: Int, patientRoomCapacity: Int) {
  private val staffRooms = List.fill(1)(new StaffRoom)
  private val patientRooms = List.fill(howManyRoomsOnFloor)(new PatientRoom(patientRoomCapacity))

  def addStaffToStaffRoom(staff: Staff): Unit = {
    this.staffRooms.head.goIn(staff)
  }

  def freeBeds: Int ={
    patientRooms.map(_.freeBeds).sum
  }

  //getters
  def getPatientRooms: List[PatientRoom] = {
    patientRooms
  }

  def getStaffRooms: List[StaffRoom] = {
    this.staffRooms
  }
}
