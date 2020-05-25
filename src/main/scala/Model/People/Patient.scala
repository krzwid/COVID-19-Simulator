package Model.People

import Model.MapSites.Room

class Patient(
             private val ID: Int,
             private var infected: Boolean,
             private var daysSinceInfected: Int,
             private var covidSymptoms: Boolean,
             private var otherDisease: Boolean,
             private var otherDiseaseSince: Int
             ) extends Person {

  def this(id: Int) {
    this(id, infected = true, daysSinceInfected = 0, covidSymptoms = true, otherDisease = false, otherDiseaseSince = 0)
  }

  def this(a: Array[String]) = {
    this(a(0).toInt, a(1).toBoolean, a(2).toInt, a(3).toBoolean, a(4).toBoolean, a(5).toInt)
  }

  private var daysSinceTreated: scala.collection.mutable.Map[Class[_], Int] = scala.collection.mutable.Map(
    classOf[Doctor] -> 0,
    classOf[Nurse] -> 0
  )

  private var room: Room = _

  // implementations

  def getID: Int = {
    this.ID
  }

  def isInfected: Boolean = {
    this.infected
  }

  def infectedSince: Int = {
    this.daysSinceInfected
  }

  def showsCovidSymptoms(dayToShowSymptoms: Int): Boolean = {
    if(this.daysSinceInfected >= dayToShowSymptoms) {
      this.covidSymptoms = true
      return true
    }
    false
  }

  def getCovidSymptoms: Boolean = {
    this.covidSymptoms
  }

  def getRoom: Room = {
    this.room
  }

  def goTo(room: Room): Unit = {
//    this.room = room
    throw new UnsupportedOperationException("Patient cannot move anywhere")
  }

  // new functions

  def isDead(par: Int): Boolean = {
    val p = par.toDouble / 100
    this.infected && scala.util.Random.nextDouble() < p
  }

  def isRecovered(limit : Int): Boolean = {
    this.infectedSince > limit
  }

  def isInfected(par : Int): Boolean = {
    val p = par.toDouble / 100
    if(this.infected && scala.util.Random.nextDouble() < p){
      this.infected = true
      return true
    }
    false
  }

  override def setInfection(): Unit = {
    this.infected = true
  }

  def haveOtherDisease: Boolean = {
    this.otherDisease
  }

  def getOtherDiseaseSince: Int = {
    this.otherDiseaseSince
  }
}
