package Model.MapSites

import java.util.Objects

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class StaffRoom extends Room {
  private val staffList = ListBuffer[Staff]()

  //implemented interface
  override def goIn(staff: Staff): Boolean = {
    Objects.requireNonNull(staff, "Null staff reference")
    if (staff.getRoom != null){
      throw new IllegalStateException("That person already is in some room")
    }
    staffList.addOne(staff)
    staff.goTo(this)
    true
  }

  override def goOut(staff: Staff): Unit = {
    Objects.requireNonNull(staff, "Null staff reference")
    if (!staffList.contains(staff)) throw new IllegalStateException("There is no staff " + staff.getID.toString + " in this room")
    staffList.subtractOne(staff)
    staff.goTo(null);
  }

  override def canGoIn(staff: Staff): Boolean = {
    true //capacity of StaffRoom is inf
  }

  override def getPerson(ID: Int): Person = {
    staffList.find(_.getID.equals(ID)).head
  }

  override def getAllPeople: List[Person] = {
    staffList.toList
  }

  //getters
  def getStaffList: ListBuffer[Staff] = {
    staffList
  }
}
