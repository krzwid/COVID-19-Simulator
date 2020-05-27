package MapSites

import Model.MapSites.{Floor, PatientRoom}
import Model.People.{Doctor, Patient}
import org.scalatest.{BeforeAndAfter, FunSuite}

class FloorTest extends FunSuite with BeforeAndAfter {
  val floor = new Floor(3, 6)

  val patient1 = new Patient(1000, true, 2, true, true, 2)
  val patient2 = new Patient(1001, true, 3, true, true, 3)
  val patient3 = new Patient(1002, true, 4, true, true, 4)
  val patient4 = new Patient(1003, true, 4, true, true, 4)
  val patient5 = new Patient(1004, true, 4, true, true, 4)

  val staff1 = new Doctor(101)

  before {
    floor.getPatientRooms.head.putPatient(patient1)
    floor.getPatientRooms.head.putPatient(patient2)
    floor.getPatientRooms(1).putPatient(patient3)
    floor.getPatientRooms(1).putPatient(patient4)
  }

  test("FloorTest.freeBeds") {
    assertResult(14) {
      floor.freeBeds
    }

    //add one more patient
    floor.getPatientRooms(2).putPatient(patient5)

    assertResult(13) {
      floor.freeBeds
    }
  }

  test("FloorTest.addStaffToStaffRoom") {
    assertResult(true) {
      floor.getStaffRooms.head.getStaffList.isEmpty
    }
    floor.addStaffToStaffRoom(staff1)
    assertResult(false) {
      floor.getStaffRooms.head.getStaffList.isEmpty
    }
  }
}
