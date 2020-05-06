package Model.People

import Model.MapSites.Room

trait Person {
//  def getFirstname(): String
//  def getLastname(): String
  def getID(): String
  def isInfected(): Boolean
  def infectedSince(): Int
  def showsCovidSymptoms(): Boolean

  def room(): Room
}
