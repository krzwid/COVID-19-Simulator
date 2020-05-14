package Model.MapSites

import Model.People.{Patient, Staff}

import scala.collection.mutable

class Hospital {
  private val floors = List[Floor]()
  private val newPatients = new mutable.Queue[Patient]()
  private val staff = new mutable.ListBuffer[Staff]

  def freeBeds: Int ={
    floors.map(_.freeBeds).sum
  }

  def addPatientToQueue(patient: Patient): Unit = {
    newPatients.append(patient)
  }

  def getQueue: mutable.Queue[Patient] = {
    this.newPatients
  }

  def addStaff(staff: Staff): Unit = {
    this.staff.addOne(staff)
  }

  def getStaff: mutable.ListBuffer[Staff] = {
    this.staff
  }

}
