package Model.People

import Model.MapSites.Room

trait Person {
  def goTo(room: Room): Unit
  def revealCovidSymptoms(): Unit

  def setInfection(infected: Boolean): Unit

  def isInfected: Boolean
  def getdaysSinceInfected: Int
  def getID: Int
  def getRoom: Room
}
