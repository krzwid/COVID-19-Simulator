package MapSites

import Model.MapSites.PatientRoom
import Model.People.{Patient, Staff}
import org.scalatest.{BeforeAndAfter, FunSuite}

class PatientRoomTest extends FunSuite with BeforeAndAfter{

  val patientRoom = new PatientRoom(3)
  val staff1 = new Staff(100, false, 0, false)
  val staff2 = new Staff(101, false, 0, false)
  val patient1 = new Patient(1000, true, 2, true, true, 2)
  val patient2 = new Patient(1001, true, 3, true, true, 3)
  val patient3 = new Patient(1002, true, 4, true, true, 4)

  before {
    patientRoom.putPatient(patient1)
    patientRoom.putPatient(patient2)
  }

  after {
    patientRoom.removePatient(patient1.getID)
    patientRoom.removePatient(patient2.getID)
  }

  test("PatientRoomTest.canGoIn") {
    assert(patientRoom.canGoIn(staff1))
    patientRoom.goIn(staff1)
    assert(!patientRoom.canGoIn(staff2))
    patientRoom.goOut(staff1)
    assert(patientRoom.canGoIn(staff1))
  }

  test("PatientRoomTest.freeBeds") {
    assertResult(1) {
      patientRoom.freeBeds
    }
    patientRoom.putPatient(patient3)
    assertResult(0) {
      patientRoom.freeBeds
    }
    patientRoom.removePatient(patient3.getID)
  }

  test("PatientRoomTest.canPutPatient") {
    assert(patientRoom.canPutPatient)
    patientRoom.putPatient(patient3)
    assert(!patientRoom.canPutPatient)
  }

  test("PatientRoomTest.getPerson") {
    assertResult(patient1){
      patientRoom.getPerson(1000)
    }
  }
}
