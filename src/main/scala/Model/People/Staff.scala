package Model.People

import Model.MapSites.{PatientRoom, Room}

class Staff extends Person {


  def getID(): String = {
    "0"
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
