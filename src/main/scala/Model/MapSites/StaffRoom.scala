package Model.MapSites

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class StaffRoom extends Room {
  private val staffList = ListBuffer[Staff]()

  override def goIn(staff: Staff): Unit = {
    staffList.addOne(staff)
  }

  override def canGoIn(staff: Staff): Boolean = {
    true //capacity of StaffRoom is inf
  }

  override def goOut(staff: Staff): Unit = {
    staffList.subtractOne(staff)
  }

  override def getPerson(ID: Int): Person = {
    staffList.find(_.getID.equals(ID)).head
  }

  def getStaffList: ListBuffer[Staff] = {
    staffList
  }
}
