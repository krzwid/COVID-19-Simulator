package Model.People

import Model.MapSites.Room

class Staff(
           private val ID: Int,
           private var infected: Boolean,
           private var daysSinceInfected: Int,
           private var covidSymptoms: Boolean
           ) extends Person {

  private var room: Room = null

  //implemented interface
  def goTo(room: Room): Unit = {
    this.room = room
  }

  override def revealCovidSymptoms(): Unit = {
    this.covidSymptoms = true
  }

  override def setInfection(infected: Boolean): Unit = {
    this.infected = true
  }

  def isInfected: Boolean = {
    this.infected
  }

  override def getdaysSinceInfected: Int = {
    this.daysSinceInfected
  }

  def getID: Int = {
    this.ID
  }

  def getRoom: Room = {
    this.room
  }

  //other methods checking health of staff
  def showsCovidSymptoms: Boolean = {
    this.covidSymptoms
  }

  def transformToPatient: StaffPatient = {
    new StaffPatient(ID, infected, getdaysSinceInfected, covidSymptoms, this.getClass.toString)
  }
}
