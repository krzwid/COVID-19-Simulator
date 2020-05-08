package Model.People

import Model.MapSites.{PatientRoom, Room}

class Staff(
           ID: Int,
           isInfected: Boolean,
           infectedSince: Int,
           covidSymptoms: Boolean
           ) extends Person {


  def getID(): Int = {
    this.ID
  }

  def isInfected(): Boolean = {
    true
  }

  def infectedSince(): Int = {
    10
  }

  def showsCovidSymptoms(): Boolean = {
    true
  }

  def room(): Room = {
    new PatientRoom()
  }

  // new fuctions
  def goTo(room: Room): Unit = {
    println("room")
  }

  def transformToPatient(): StaffPatient = {
    new StaffPatient()
  }
}
