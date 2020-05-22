package Model.People

import Model.MapSites.Room

class Staff(
           private val ID: Int,
           private var infected: Boolean,
           private var infectionSince: Int,
           private var covidSymptoms: Boolean
           ) extends Person {

  private var room: Room = null

  def getID: Int = {
    this.ID
  }

  def isInfected: Boolean = {
    this.infected
  }

  def infectedSince: Int = {
    this.infectionSince
  }

  def showsCovidSymptoms: Boolean = {
    this.covidSymptoms
  }

  def getRoom: Room = {
    this.room
  }

  // new fuctions
  def goTo(room: Room): Unit = {
    this.room.goOut(this)

    this.room = room
    this.room.goIn(this)
  }

  def transformToPatient: StaffPatient = {
    new StaffPatient(ID, infected, infectionSince, covidSymptoms, this.getClass.toString)
  }
}
