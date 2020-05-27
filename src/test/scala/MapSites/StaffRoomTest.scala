package MapSites

import Model.MapSites.StaffRoom
import Model.People.Staff
import org.scalatest.FunSuite

class StaffRoomTest extends FunSuite {
  val staffRoom = new StaffRoom
  val staff1 = new Staff(100, false, 0, false)
  val staff2 = new Staff(101, false, 0, false)

  test("StaffRoomTest.goIn/Out") {
    assertResult(0) {
      staffRoom.getStaffList.length
    }
    staffRoom.goIn(staff1)
    assertResult(1) {
      staffRoom.getStaffList.length
    }
    staffRoom.goIn(staff2)
    assertResult(2) {
      staffRoom.getStaffList.length
    }
    staffRoom.goOut(staff1)
    assertResult(1) {
      staffRoom.getStaffList.length
    }
    staffRoom.goOut(staff2)
    assertResult(0) {
      staffRoom.getStaffList.length
    }
  }

  test("StaffRoomTest.canGoIn") {
    assert(staffRoom.canGoIn(staff2))
  }

  test("StaffRoomTest.getPerson") {
    staffRoom.goIn(staff1)
    assertResult(staff1) {
      staffRoom.getPerson(100)
    }
  }
}
