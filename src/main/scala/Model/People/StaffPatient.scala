package Model.People

class StaffPatient(
                  private val ID: Int,
                  private var infected: Boolean,
                  private var daysSinceInfected: Int,
                  private var covidSymptoms: Boolean,
                  private val classAsString: String
                  )
  extends Patient (
    ID = ID,
    infected = infected,
    daysSinceInfected = daysSinceInfected,
    covidSymptoms = covidSymptoms,
    otherDisease = false,
    otherDiseaseSince = 0
  ) {

  def transformToStaff(): Staff = {
    var newStaff: Staff = null

    if (classAsString == classOf[Doctor].toString) {
      newStaff = new Doctor(ID, infected, daysSinceInfected, covidSymptoms)
    }

    if (classAsString == classOf[Nurse].toString) {
      newStaff = new Nurse(ID, infected, daysSinceInfected, covidSymptoms)
    }

    newStaff
  }
}
