package People

import Model.People.Patient
import org.scalatest.FunSuite

class PatientTest extends FunSuite{

  val patient = new Patient(1000, true, 4, true, true, 2)

  test("PatientTest.incrementDaysCounters") {
    assertResult(4) {
      patient.getdaysSinceInfected
    }
    assertResult(2) {
      patient.getOtherDiseaseSince
    }

    patient.incrementDaysCounters()
    assertResult(5) {
      patient.getdaysSinceInfected
    }
    assertResult(3) {
      patient.getOtherDiseaseSince
    }
  }

  test("PatientTest.isRecovered") {
    val dayToShowSymptoms:Int =6
    assertResult(false) {
      patient.revealCovidSymptoms(dayToShowSymptoms)
    }

    patient.incrementDaysCounters()
    assertResult(true) {
      patient.revealCovidSymptoms(dayToShowSymptoms)
    }
  }

}
