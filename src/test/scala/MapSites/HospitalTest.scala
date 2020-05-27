package MapSites

import Model.MapSites.Hospital
import Model.People.{Doctor, Nurse, Patient}
import org.scalatest.FunSuite

class HospitalTest extends FunSuite {
  val hospital: Hospital = new Hospital(2,3, 4)


  test("HospitalTest.addStaff") {
    assertResult(0) {
      hospital.doctors.length
    }
    val doctor1 = new Doctor(101)
    assertResult(1) {
      hospital.addStaff(doctor1)
      hospital.doctors.length
    }

    val doctor2 = new Doctor(102)
    assertResult(2) {
      hospital.addStaff(doctor2)
      hospital.doctors.length
    }

    assertResult(0) {
      hospital.nurses.length
    }
    val nurse1 = new Nurse(201)
    assertResult(1) {
      hospital.addStaff(nurse1)
      hospital.nurses.length
    }

    val nurse2 = new Nurse(102)
    assertResult(2) {
      hospital.addStaff(nurse2)
      hospital.nurses.length
    }
  }

  test("HospitalTest.freeBeds") {
    assertResult(24) {
      hospital.freeBeds
    }

    val patient1 = new Patient(1000, true, 2, true, true, 2)
    val patient2 = new Patient(1001, true, 3, true, true, 3)

    hospital.floors.head.getPatientRooms.head.putPatient(patient1)
    hospital.floors.head.getPatientRooms.head.putPatient(patient2)
    assertResult(22) {
      hospital.freeBeds
    }
  }
}
