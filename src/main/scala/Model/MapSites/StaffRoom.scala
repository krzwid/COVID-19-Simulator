package Model.MapSites

import Model.People.{Patient, Person, Staff}

import scala.collection.mutable.ListBuffer

class StaffRoom extends Room {
  private val staffs = ListBuffer[Staff]()

  def goIn(staff: Staff): Unit = {
    staffs.addOne(staff)
  }

  def canGoIn(staff: Staff): Boolean = {
    true //capacity of StaffRoom is inf
  }

  def goOut(staff: Staff): Unit = {
    staffs.filter(_.getID != staff.getID)
  }

  def getPerson(ID: Int): Person = {
    staffs.find(_.getID.equals(ID)).head
  }
}
