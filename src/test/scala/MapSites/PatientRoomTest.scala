package MapSites

import Model.MapSites.PatientRoom
import Model.People.{Doctor, Nurse, Patient, Staff}
import org.scalatest.{BeforeAndAfter, FunSuite}

class PatientRoomTest extends FunSuite with BeforeAndAfter{

  val patientRoom = new PatientRoom(3)
  val staff1 = new Doctor(100)
  val staff2 = new Doctor(101)
  val staff3 = new Nurse(200)
  val staff4 = new Nurse(201)
  val patient1 = new Patient(1000, true, 2, true, true, 2)
  val patient2 = new Patient(1001, true, 3, true, true, 3)
  val patient3 = new Patient(1002, true, 4, true, true, 4)
  val patient4 = new Patient(1003, true, 4, true, true, 4)

  before {
    patientRoom.putPatient(patient1)
    patientRoom.putPatient(patient2)
  }

  after {
    patientRoom.removePatient(patient1.getID)
    patientRoom.removePatient(patient2.getID)
  }

  test("PatientRoomTest.goIn") {
    assertResult(true) {
      patientRoom.goIn(staff1)
    }
    //test if nurse go in while there is already a doctor
    assertResult(true) {
      patientRoom.goIn(staff3)
    }
    //test if 2 doctors can be in room
    assertResult(false) {
      patientRoom.goIn(staff2)
      }
    //test if another nurse go in
    assertResult(true) {
      patientRoom.goIn(staff4)
    }
  }

  test("PatientRoomTest.goOut") {
    //we have already 1 doctor and 2 nurses from previous test
    assertResult(3) {
      patientRoom.getStaffList.length
    }

    //everybody out
    patientRoom.goOut(staff1)
    patientRoom.goOut(staff3)
    patientRoom.goOut(staff4)

    assertResult(0) {
      patientRoom.getStaffList.length
    }
  }

  test("PatientRoomTest.canGoIn") {
    assert(patientRoom.canGoIn(staff1))
    patientRoom.goIn(staff1)
    assert(!patientRoom.canGoIn(staff2))
    patientRoom.goOut(staff1)
    assert(patientRoom.canGoIn(staff1))
  }

  test("PatientRoomTest.getPerson") {
    assertResult(patient1){
      patientRoom.getPerson(1000)
    }
  }

  test("PatientRoomTest.putPatient") {
    assertResult(2) {
      patientRoom.getPatientList.length
    }

    //one more patient
    assertResult(true) {
      patientRoom.putPatient(patient3)
    }

    //room is full
    assertResult(false) {
      patientRoom.putPatient(patient4)
    }
    patientRoom.removePatient(patient3.getID)
    patientRoom.removePatient(patient4.getID)
  }

  test("PatientRoomTest.canPutPatient") {
    assert(patientRoom.canPutPatient)
    patientRoom.putPatient(patient3)
    assert(!patientRoom.canPutPatient)
    patientRoom.removePatient(patient3.getID)
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
}
