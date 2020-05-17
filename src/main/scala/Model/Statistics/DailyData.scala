package Model.Statistics

// graphs to draw for each day:

//1) infected covid
//2) dead covid
//3) dead other
//4) dead all
//5) cured covid
//6) active covid
//7) alive patients
//8) alive staff
//9) mortality

class DailyData {
  private val countDeadAll: Int = countDeadCovid + countDeadOther
  private val countDeadPatientAll: Int = countDeadPatientCovid + countDeadPatientCovid
  private val countDeadStaffAll: Int = countDeadStaffCovid + countDeadStaffOther

  private val countDeadCovid: Int = countDeadStaffCovid + countDeadPatientCovid
  private var countDeadStaffCovid: Int = 0
  private var countDeadPatientCovid: Int = 0

  private val countDeadOther: Int = countDeadStaffOther + countDeadPatientOther
  private var countDeadStaffOther: Int = 0
  private var countDeadPatientOther: Int = 0

  private var countNewInfected: Int = countNewInfectedStaffCovid + countNewInfectedPatientCovid
  private var countNewInfectedStaffCovid: Int = 0
  private var countNewInfectedPatientCovid: Int = 0

  private val countCured: Int = countCuredStaff + countCuredPatient
  private var countCuredStaff: Int = 0
  private var countCuredPatient: Int = 0

  private val activeCovid: Int = activeStaffCovid + activePatientCovid
  private var activePatientCovid: Int = 0
  private var activeStaffCovid: Int = 0

  private var countPatient: Int = 0
  private var countStaff: Int = 0

  private var mortality: Double = 0
}
