package Model.People

class StaffPatient(
                  private val realClass: Class[_]
                  ) extends Patient {

  def transformToStaff(): Staff = {
    new Staff()
  }
}
