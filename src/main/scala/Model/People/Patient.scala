package Model.People

import Model.MapSites.{PatientRoom, Room}

class Patient(
             private var otherDisease: Boolean,
             private var otherDiseaseSince: Int,
             ) extends Person {
  private var daysSinceTreated: scala.collection.mutable.Map[Class[_], Int] = scala.collection.mutable.Map()

  def this() {
    this(false, 0)
    daysSinceTreated.put(classOf[Doctor], 0)
    daysSinceTreated.put(classOf[Nurse], 0)

    println(daysSinceTreated)
    daysSinceTreated(classOf[Doctor]) += 1
    println(daysSinceTreated)
  }

  // implementations

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

  // new functions

  def ifDies(): Boolean = {
    true
  }

  def ifRecover(): Boolean = {
    true
  }
}
