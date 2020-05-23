package Model.MapSites

import java.util.Objects

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class StaffRoom extends Room {
  private val staffList = ListBuffer[Staff]()

  override def goIn(staff: Staff): Unit = {
    Objects.requireNonNull(staff, "Null staff reference")
    if (staff.getRoom != null) throw new IllegalStateException("That person already is in some room")
    staffList.addOne(staff)
    staff.goTo(this)
  }

  override def canGoIn(staff: Staff): Boolean = {
    true //capacity of StaffRoom is inf
  }

  override def goOut(staff: Staff): Unit = {
    Objects.requireNonNull(staff, "Null staff reference")
    if (!staffList.contains(staff)) throw new IllegalStateException("There is no staff " + staff.getID.toString + " in this room")
    staffList.subtractOne(staff)
    staff.goTo(null);
  }

  override def getPerson(ID: Int): Person = {
    staffList.find(_.getID.equals(ID)).head
  }

  def getStaffList: ListBuffer[Staff] = {
    staffList
  }
}
