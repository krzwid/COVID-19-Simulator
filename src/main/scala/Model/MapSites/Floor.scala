package Model.MapSites

class Floor {
  private val staffRooms = List[StaffRoom]()
  private val patientRooms = List[PatientRoom]()

  def freeBeds: Int ={
    patientRooms.map(_.freeBeds).sum
  }
}


