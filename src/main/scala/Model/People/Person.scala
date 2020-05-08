package Model.People

import Model.MapSites.Room

trait Person {
  def getID: Int
  def isInfected: Boolean
  def infectedSince: Int
  def showsCovidSymptoms: Boolean

  def goTo(room: Room): Unit
  def getRoom: Room
}
